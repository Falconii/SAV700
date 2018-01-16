package br.com.brotolegal.sav700;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.brotolegal.sav700.firebase.FireBaseDataBase;
import br.com.brotolegal.sav700.fragments.DashBoard_Frag;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.DispositivoDAO;
import br.com.brotolegal.savdatabase.dao.MarcaDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.UsuarioDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Dispositivo;
import br.com.brotolegal.savdatabase.entities.Marca;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;
import br.com.brotolegal.savdatabase.entities.Usuario_fast;
import br.com.brotolegal.savdatabase.eventbus.NotificationCarga;
import br.com.brotolegal.savdatabase.eventbus.NotificationConexao;
import br.com.brotolegal.savdatabase.eventbus.NotificationPedido;
import br.com.brotolegal.savdatabase.eventbus.NotificationSincronizacao;
import br.com.brotolegal.savdatabase.regrasdenegocio.Empresa;
import br.com.brotolegal.savdatabase.regrasdenegocio.PedidoBusinessV10;
import br.com.brotolegal.savdatabase.regrasdenegocio.UsuarioTST;
import br.com.brotolegal.savdatabase.util.Filtro_Categoria;
import br.com.brotolegal.savdatabase.util.Filtro_Cliente;
import br.com.brotolegal.savdatabase.util.Filtro_Data;
import br.com.brotolegal.savdatabase.util.Filtro_Marca;
import br.com.brotolegal.savdatabase.util.Filtro_Produto;
import br.com.brotolegal.savdatabase.util.ManagerPreferencias;
import br.com.brotolegal.savdatabase.util.Rel_Topicos;
import br.com.brotolegal.savdatabase.util.Rel_Visao;

import static br.com.brotolegal.savdatabase.app.App.user;

public class SAVActivity extends AppCompatActivity {

    public static final int FIRST_LOGIN    = 1; //Primeiro Login No Sistema
    public static final int NEW_USER       = 2; //Troca de Usuário
    public static final int TRANSMISSAO    = 3; //Transmissao de Pedidos
    public static final int CARGA          = 4; //CARGA
    public static final int SINCRONIZACAO  = 5; //SINCRONIZACAO


    public static final int MANUPLANEJAMENTO  = 6; //MANUTENÇÃO DAS AGENDAS

    private Toolbar toolbar;

    private Bundle  savebundle;

    private final int HOME_ITEM       = 5;
    private DashBoard_Frag              dashboard_frag;

    private Drawer.Result navigationDrawerLeft;
    private Drawer.Result navigationDrawerRight;
    private AccountHeader.Result headerNavigationLeft;
    private int mPositionClicked;
    private String TAG = "SAVACTIVITY";
    private TextView lbUsuario;
    private defaultAdapter conexaoadapter;
    private Intent serviceIntent;

    private Filtro_Data      fData;
    private Filtro_Cliente   fCliente;
    private Filtro_Categoria fCategoria;
    private Filtro_Marca     fMarca;
    private Filtro_Produto   fProduto;
    private Rel_Visao        rVisao;
    private Rel_Topicos      rTopicos;

//    public  static  FirebaseDatabase  firebaseDatabase;
//    public  static  DatabaseReference databaseReference;
//
//    static {
//
//        try {
//
//            FirebaseApp.initializeApp(App.getCustomAppContext());
//            firebaseDatabase = FirebaseDatabase.getInstance();
//            firebaseDatabase.setPersistenceEnabled(true);
//            databaseReference = firebaseDatabase.getReference();
//
//        } catch (Exception e) {
//
//            Log.i("SAVACTIVITY",e.getMessage());
//        }
//
//
//    }



    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem iDrawerItem, CompoundButton compoundButton, boolean b) {
            Toast.makeText(SAVActivity.this, "onCheckedChanged: " + (b ? "true" : "false"), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sav);
        lbUsuario = (TextView) findViewById(R.id.usuario_000);

        lbUsuario.setText("Nehum Usuário Logado.");

        savebundle = savedInstanceState;

        if (App.getItsOK()) {

            //Abre arquivos basicos EMPRESA
            try {

                /* Dispositivo */

                TelephonyManager tMgr;

                tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                DispositivoDAO dao = new DispositivoDAO();

                dao.open();

                Dispositivo dispo = dao.seek(new String[]{"000000"});

                if (dispo == null) {

                    dispo = new Dispositivo(

                            "000000",

                            App.version,

                            "API: " + String.valueOf(Build.VERSION.SDK_INT),

                            tMgr.getDeviceId(), //IMEI

                            "", //chapa

                            Build.MODEL,

                            "", //marca

                            "I", //status

                            Build.MANUFACTURER.toUpperCase(),

                            FirebaseInstanceId.getInstance().getToken()

                    );

                    dispo = dao.insert(dispo);

                    if (dispo == null) {

                        dao.close();

                        throw new Exception("Falha Na Criação Do Registro Dispositivo");

                    }


                } else {

                    dispo = new Dispositivo(

                            "000000",

                            App.version,

                            "API: " + String.valueOf(Build.VERSION.SDK_INT),

                            tMgr.getDeviceId(), //IMEI

                            "", //chapa

                            Build.MODEL,

                            "", //marca

                            "I", //status

                            Build.MANUFACTURER.toUpperCase(),

                            FirebaseInstanceId.getInstance().getToken()

                    );

                    dao.Update(dispo);

                }

                dao.close();

                /* Configuração */

                ConfigDAO daoConf = new ConfigDAO();

                daoConf.open();

                Config conf = daoConf.seek(new String[]{"000"});

                daoConf.close();

                if (conf == null) {

                    LayoutInflater li = LayoutInflater.from(SAVActivity.this);

                    View view = li.inflate(R.layout.dlconfiguracao, null);

                    List<String[]> opcoes = new ArrayList<>();

                    opcoes.add(new String[]{"001", "PRODUÇÃO"});
                    opcoes.add(new String[]{"002", "HOMOLOGAÇÃO"});
                    opcoes.add(new String[]{"003", "PROTHEUS 12"});

                    final Spinner spconexoes = (Spinner) view.findViewById(R.id.sp_bases_127);

                    conexaoadapter = new defaultAdapter(getBaseContext(), R.layout.choice_default_row, opcoes, "Opçoes:");

                    spconexoes.setAdapter(conexaoadapter);

                    spconexoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            conexaoadapter.setEscolha(position);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {


                        }
                    });

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SAVActivity.this);

                    alertDialogBuilder.setView(view);

                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Continua...",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    try {

                                        String porta = "9999";

                                        Object lixo = spconexoes.getSelectedItem();

                                        if ((((String[]) lixo)[0]).equals("001")){

                                            porta = "9999";//Produção

                                        }

                                        if ((((String[]) lixo)[0]).equals("002")) {


                                            porta = "9877";//Homologacao


                                        }

                                        if ((((String[]) lixo)[0]).equals("003")) {

                                            porta = "9876";//Protheus 12

                                        }

                                        App.base = porta;

                                        /* Configuração */

                                        ConfigDAO dao = new ConfigDAO();

                                        dao.open();

                                        Config conf = dao.seek(new String[]{"000"});

                                        dao.DeleteAll();

                                        /* GRAVA CONFIGURAÇÕES PADRAO 000 */

                                        String padrao[] = Config.getDefault(0);

                                        conf = new Config("000", padrao[1], padrao[2], padrao[3], padrao[4], padrao[5], padrao[6], padrao[7], padrao[8], padrao[9], padrao[10], padrao[11], porta);

                                        conf = dao.insert(conf);

                                        if (conf != null) {

                                            /* GRAVA AS CONFIGURAÇÕES OPCIONAIS */

                                            for (int x = 0; x < Config.getConexoes().length; x++) {

                                                String values[] = Config.getDefault(x);

                                                conf = new Config(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11], porta);

                                                conf = dao.insert(conf);

                                                if (conf == null) {

                                                    break;
                                                }

                                            }
                                        }

                                        dao.close();

                                        if (conf == null) {

                                            throw new Exception("Falha Na Criação Do Registro CONFIG");

                                        }

                                        try {

                                            init();

                                        } catch (Exception e){

                                            firstLogin();

                                        }

                                    } catch (Exception e) {

                                        finish();

                                    }

                                }

                            });

                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                } else {


                    App.base = conf.getPORTA();

                    init();
                }


                DashBoard_Frag frag = (DashBoard_Frag) getSupportFragmentManager().findFragmentByTag("dashboard_frag");
                if (frag == null) {

                    if (dashboard_frag == null) {

                        dashboard_frag = new DashBoard_Frag();

                        frag = dashboard_frag;

                    } else {


                        frag = dashboard_frag;

                    }

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.rl_fragment_container, frag, "dashboard_frag");
                    ft.commit();
                }

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                finish();
            }
        } else {

            Toast.makeText(getApplicationContext(),"Problemas Na Instação...", Toast.LENGTH_LONG).show();

            finish();

        }


//        serviceIntent = new Intent(SAVActivity.this, ProcessosBackGround.class);
//
//        Bundle params = new Bundle();
//        params.putString("ROTINA", "CONEXAO");
//        serviceIntent.putExtras(params);
//
//        startService(serviceIntent);

        try {

            List<Marca> lsMarcas;

            try {

                MarcaDAO dao = new MarcaDAO();

                dao.open();

                lsMarcas = dao.getAll();

                dao.close();

            }catch (Exception e){

                lsMarcas = new ArrayList<>();

            }


            fData = new Filtro_Data();

            fCliente = new Filtro_Cliente(false, "C", "", "", "");

            fCategoria = new Filtro_Categoria(false, "");

            fMarca = new Filtro_Marca(false, "", "", lsMarcas);

            fProduto = new Filtro_Produto(false, "", "", "", "", "");

            rVisao = new Rel_Visao();

            rVisao.addVisao("BT", "Pedido Tablet");
            rVisao.addVisao("BP", "Pedido Protheus");

            rTopicos = new Rel_Topicos("");

            rTopicos.addOpcoes("DT", true, "Datas", "01", "02");
            rTopicos.addOpcoes("CL", true, "Clientes Ou Redes", "02", "03");
            rTopicos.addOpcoes("CT", true, "Categoris", "04", "05");
            rTopicos.addOpcoes("MC", true, "Marcas", "06", "07");
            rTopicos.addOpcoes("PR", true, "Produtos", "08", "09");

            App.manager_filtro_01.setFiltro_data(fData);
            App.manager_filtro_01.setFiltro_cliente(fCliente);
            App.manager_filtro_01.setFiltro_categoria(fCategoria);
            App.manager_filtro_01.setFiltro_marca(fMarca);
            App.manager_filtro_01.setFiltro_produto(fProduto);
            App.manager_filtro_01.setRel_visao(rVisao);
            App.manager_filtro_01.setRel_topicos(rTopicos);

            ManagerPreferencias man = new ManagerPreferencias(SAVActivity.this);

            man.loadPreferenciasRel_01();

        } catch (Exception e ){

            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    private void init() throws Exception{

        Status status = null;

        Usuario user  = null;

        try {

            //Abre arquivo de status

            StatusDAO dao = new StatusDAO();

            dao.open();

            status = dao.seek(null);

            dao.close();

            if (status == null) {

                status = new Status("N","","","","","","","N","","","","0");

            } else {

                if (status.getLOGADO().equals("S")){

                    UsuarioDAO daoUS = new UsuarioDAO();

                    daoUS.open();

                    user = daoUS.seek(new String[] {status.getUSERLOG()});



                    //Cria a Estrutura do Usuário se necessário

                    if (user != null){

                        CreateUserStructure(user.getCOD());

                    }

                    App.user =  user;

                    App.setDataBaseUser();


                } else {

                }

            }



        } catch (Exception e){

            user = null;
        }

        if (user == null || user.getCOD().equals("")) {

            firstLogin();

        }

        try {

            toolbar = (Toolbar) findViewById(R.id.tb_main);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle(getResources().getString(R.string.app_versao));
            toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);

            adicionaDrawer(savebundle);

            adicionaLeftNavigation(status);

            adicionaHeaderNavigation();

            adicionaUsuarios();

            if (savebundle != null) navigationDrawerLeft.setSelection(HOME_ITEM);

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());

        }


        SharedPreferences prefs = getSharedPreferences("ShortCutPrefs", MODE_PRIVATE);
        if(!prefs.getBoolean("isFirstTime", false)){
            addShortcut();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstTime", true);
            editor.commit();
        }


        try {


            adicionaLeftNavigation(status);

            adicionaHeaderNavigation();

            if (status.getLOGADO().equals("S")){

                lbUsuario.setText(user.getNOME());

            } else {

                lbUsuario.setText("Nenhum Usuário Logado.");

            }

        } catch (Exception e){


            finish();

        }



    }

    private void remove_frags(){


        try {

//            //Ativa dashboard
//

            DashBoard_Frag frag = (DashBoard_Frag) getSupportFragmentManager().findFragmentByTag("dashboard_frag");

            if (frag == null) {

                dashboard_frag = new DashBoard_Frag();

                frag = dashboard_frag;

            } else {


                frag = dashboard_frag;

            }


            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "dashboard_frag");
            ft.commit();

        } catch (Exception e){

            Toast.makeText(SAVActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    protected void onResume() {

        if (!EventBus.getDefault().isRegistered(this)){

            EventBus.getDefault().register(this);
        }

        if (!(dashboard_frag == null) && dashboard_frag.loader) {

            refresh();


        }

        super.onResume();
    }

    private void refresh(){

        dashboard_frag.loadClientes();

        dashboard_frag.loadPedidosEmAberto();

        dashboard_frag.loadNotificacoes();

        dashboard_frag.setarCabec();

    }

    @Override
    protected void onStop() {

        if (EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().unregister(this);
        }

        super.onStop();

    }

    public void adicionaDrawer(final Bundle savedInstanceState){

        Status status = null;

        try {

            StatusDAO dao = new StatusDAO();

            dao.open();

            status = dao.seek(null);

            if (status == null){

                status = new Status("N","","","","","","","N","","","","0");

            }

        } catch (Exception e){

            Toast.makeText(SAVActivity.this, "Falha Na Leitura Do Status !!!", Toast.LENGTH_SHORT).show();

            return;

        }

        adicionaLeftNavigation(status);

        adicionaHeaderNavigation();

        adicionaUsuarios();

    }

    private void adicionaLeftNavigation(Status status){

        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savebundle)
                .withSelectedItem(HOME_ITEM)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {

                        mPositionClicked = i;

                        String opcao = (String) iDrawerItem.getTag();

                        navigationDrawerLeft.getAdapter().notifyDataSetChanged();

                        //selecao

                        if (opcao.equals("login")) {

                            for(int count = 0; count < navigationDrawerLeft.getDrawerItems().size(); count++){

                                if (navigationDrawerLeft.getDrawerItems().get(count) instanceof PrimaryDrawerItem && navigationDrawerLeft.getDrawerItems().get(count).getTag().equals("login") ) {

                                    PrimaryDrawerItem aux = (PrimaryDrawerItem) navigationDrawerLeft.getDrawerItems().get(count);

                                    navigationDrawerLeft.getAdapter().notifyDataSetChanged();

                                    break;


                                }

                            }

                        }

                        if (opcao.equals("logoff")) {

                            lbUsuario.setText("Nenhum Usuário Logado.");

                            for(int count = 0; count < navigationDrawerLeft.getDrawerItems().size(); count++){

                                if (navigationDrawerLeft.getDrawerItems().get(count) instanceof PrimaryDrawerItem && navigationDrawerLeft.getDrawerItems().get(count).getTag().equals("login") ) {

                                    PrimaryDrawerItem aux = (PrimaryDrawerItem) navigationDrawerLeft.getDrawerItems().get(count);

                                    navigationDrawerLeft.getAdapter().notifyDataSetChanged();

                                    break;


                                }

                            }

                        }


                        if (opcao.equals("base")) {

                            Config config = null;

                            try {
                                ConfigDAO dao = new ConfigDAO();

                                dao.open();

                                config = dao.seek(new String[]{"000"});

                                dao.close();

                            } catch (Exception e){

                                config = null;

                            }

                            if (config != null) {


                                LayoutInflater li = LayoutInflater.from(SAVActivity.this);

                                View dlview = li.inflate(R.layout.dlconfiguracao, null);

                                List<String[]> opcoes = new ArrayList<>();

                                opcoes.add(new String[]{"001", "PRODUÇÃO"});
                                opcoes.add(new String[]{"002", "HOMOLOGAÇÃO"});
                                opcoes.add(new String[]{"003", "PROTHEUS 12"});

                                final Spinner spconexoes = (Spinner) dlview.findViewById(R.id.sp_bases_127);

                                conexaoadapter = new defaultAdapter(getBaseContext(), R.layout.choice_default_row, opcoes, "Opçoes:");

                                spconexoes.setAdapter(conexaoadapter);

                                spconexoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        conexaoadapter.setEscolha(position);


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {


                                    }
                                });

                                if (config.getPORTA().equals(HelpInformation.BaseProducao)){

                                    spconexoes.setSelection(0);

                                } else {


                                    spconexoes.setSelection(1);

                                }

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SAVActivity.this);

                                alertDialogBuilder.setView(dlview);

                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //não faz nada

                                    }

                                });
                                alertDialogBuilder.setPositiveButton("Continua...",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                try {

                                                    String porta = "9999";

                                                    Object lixo = spconexoes.getSelectedItem();

                                                    if ((((String[]) lixo)[0]).equals("001")) {

                                                        porta = "9999";//Produção

                                                    }

                                                    if ((((String[]) lixo)[0]).equals("002")) {


                                                        porta = "9877";//Homologacao


                                                    }

                                                    if ((((String[]) lixo)[0]).equals("003")) {


                                                        porta = "9876";//Protheus 12


                                                    }

                                                    App.base = porta;

                                                /* Configuração */

                                                    ConfigDAO dao = new ConfigDAO();

                                                    dao.open();

                                                    Config conf = dao.seek(new String[]{"000"});

                                                    dao.DeleteAll();

                                                /* GRAVA CONFIGURAÇÕES PADRAO 000 */

                                                    String padrao[] = Config.getDefault(0);

                                                    conf = new Config("000", padrao[1], padrao[2], padrao[3], padrao[4], padrao[5], padrao[6], padrao[7], padrao[8], padrao[9], padrao[10], padrao[11], porta);

                                                    conf = dao.insert(conf);

                                                    if (conf != null) {

                                                    /* GRAVA AS CONFIGURAÇÕES OPCIONAIS */

                                                        for (int x = 0; x < Config.getConexoes().length; x++) {

                                                            String values[] = Config.getDefault(x);

                                                            conf = new Config(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11], porta);

                                                            conf = dao.insert(conf);

                                                            if (conf == null) {

                                                                break;
                                                            }

                                                        }
                                                    }

                                                    dao.close();

                                                    if (conf == null) {

                                                        throw new Exception("Falha Na Criação Do Registro CONFIG");

                                                    }

                                                    try {

                                                        init();

                                                    } catch (Exception e) {

                                                        firstLogin();

                                                    }

                                                } catch (Exception e) {

                                                    finish();

                                                }

                                            }

                                        });

                                final AlertDialog alertDialog = alertDialogBuilder.create();

                                alertDialog.show();

                            }
                        }


                        if (opcao.equals("update")) {

                            Intent intent = new Intent(getApplicationContext(), UpdateVersionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle params = new Bundle();
                            intent.putExtras(params);
                            startActivity(intent);


                        }


                        if (opcao.equals("sair")) {

                            SAVActivity.this.finish();


                        }

                        if (opcao.equals("dashboard")) {

                            DashBoard_Frag frag = (DashBoard_Frag) getSupportFragmentManager().findFragmentByTag("dashboard_frag");
                            if (frag == null) {

                                if (dashboard_frag == null) {

                                    dashboard_frag = new DashBoard_Frag();

                                    frag = dashboard_frag;

                                } else {


                                    frag = dashboard_frag;

                                }

                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.rl_fragment_container, frag, "dashboard_frag");
                                ft.commit();
                            }

                        }


                        if (opcao.equals("dispositivo")) {


                            Intent intent = new Intent(SAVActivity.this,DispositivoActivity.class);
                            startActivity(intent);

                        }

                        if (opcao.equals("usuario")) {

                            Intent intent = new Intent(SAVActivity.this,UsuarioActivity.class);
                            startActivity(intent);

                        }

                        if (opcao.equals("pedidocomposicaodecarga")) {

                            Intent intent = new Intent(SAVActivity.this,NegociacaoActivity.class);
                            startActivity(intent);

                        }


                        if (opcao.equals("pedidodistribuicao")) {

                            Intent intent = new Intent(SAVActivity.this,MenAtWorkingActivity.class);
                            startActivity(intent);


                        }

                        if (opcao.equals("pedidoplanejamento")) {

                            Intent intent = new Intent(SAVActivity.this,PedidosAgendaActivity.class);
                            Bundle params = new Bundle();
                            params.putString("ROTINA"   , "CONSULTA");
                            intent.putExtras(params);
                            startActivity(intent);


                        }


                        if (opcao.equals("pedidoavulso")) {

                            Intent intent = new Intent(SAVActivity.this,PedidosGeralActivity.class);
                            Bundle params = new Bundle();
                            params.putString("ROTINA"   , "CONSULTA");
                            intent.putExtras(params);
                            startActivity(intent);


                        }

                        if (opcao.equals("pedidotransmitido")) {

                            Intent intent = new Intent(SAVActivity.this,PedidosMobileTransmitidosActivity.class);
                            Bundle params = new Bundle();
                            params.putString("ROTINA"   , "CONSULTAGERAL");
                            intent.putExtras(params);
                            startActivity(intent);

                        }


                        if (opcao.equals("precliente")) {

                            Intent intent = new Intent(SAVActivity.this,MenAtWorkingActivity.class);
                            startActivity(intent);

                        }


                        if (opcao.equals("metas")) {

                            Intent intent = new Intent(SAVActivity.this,ShowMetasActivity.class);
                            startActivity(intent);


                        }

                        if (opcao.equals("campanhas")) {

                            Intent intent = new Intent(SAVActivity.this,CampanhaViewActivity.class);
                            startActivity(intent);


                        }

                        if (opcao.equals("agenda01")) {

                            Intent intent = new Intent(SAVActivity.this,MenAtWorkingActivity.class);
                            startActivity(intent);

                        }


                        if (opcao.equals("agenda02")) {

                            Intent intent = new Intent(SAVActivity.this,MenAtWorkingActivity.class);
                            startActivity(intent);

                        }


                        if (opcao.equals("gerencial01")) {

                            Intent intent = new Intent(SAVActivity.this,Parametros_01Activity.class);
                            Bundle params = new Bundle();
                            params.putString("BASE", "MOBILE");
                            intent.putExtras(params);
                            startActivity(intent);



                        }

                        if (opcao.equals("pedidosprotheus")) {

                            Intent intent = new Intent(SAVActivity.this,PedidosProtheusGeralActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODCLIENTE", "");
                            params.putString("LOJCLIENTE", "");
                            intent.putExtras(params);
                            startActivity(intent);

                        }


                        if (opcao.equals("nfsprotheus")) {

                            Intent intent = new Intent(SAVActivity.this,ConsultaNFTotvsActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODCLIENTE", "");
                            params.putString("LOJCLIENTE", "");
                            intent.putExtras(params);
                            startActivity(intent);

                        }

                        if (opcao.equals("acordosprotheus")) {

                            Intent intent = new Intent(SAVActivity.this,ConsultaAcordoActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODCLIENTE", "");
                            params.putString("LOJCLIENTE", "");
                            intent.putExtras(params);
                            startActivity(intent);

                        }

                        if (opcao.equals("preacordo")) {

                            Intent intent = new Intent(SAVActivity.this,PreAcordoActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODCLIENTE", "");
                            params.putString("LOJCLIENTE", "");
                            intent.putExtras(params);
                            startActivity(intent);
                        }


                        if (opcao.equals("prospeccao")) {

                            Intent intent = new Intent(SAVActivity.this,MenAtWorkingActivity.class);
                            startActivity(intent);

                        }


                        if (opcao.equals("CC")) {

                            Intent intent = new Intent(SAVActivity.this,CCActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODCLIENTE", "");
                            params.putString("LOJCLIENTE", "");
                            intent.putExtras(params);
                            startActivity(intent);
                        }


                        if (opcao.equals("sincronizacao")) {

                            remove_frags();

                            Intent intent = new Intent(SAVActivity.this,PedidosActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODIGO"   , "");
                            params.putString("LOJA"     , "");
                            params.putString("ROTINA"   , "CONSULTA");
                            intent.putExtras(params);
                            startActivityForResult(intent, TRANSMISSAO);

                        }


                        if (opcao.equals("carga")) {

                            remove_frags();

                            Intent intent = new Intent(SAVActivity.this, CargaActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODIGO"   , "");
                            params.putString("LOJA"     , "");
                            intent.putExtras(params);
                            startActivityForResult(intent, CARGA);


                        }


                        if (opcao.equals("treinamento01")) {

                            Intent intent = new Intent(SAVActivity.this,TreinamentoMenuViewAtivity.class);
                            startActivity(intent);

                        }

                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {

                        Toast.makeText(SAVActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();

                        return false;
                    }
                })
                .build();

        /*

          define a base

         */


        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Aplicação").withTextColor(getResources().getColor(R.color.md_red_200)).withTag("aplicacao"));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName(App.BaseNome()).withIcon(getResources().getDrawable(R.drawable.new_version)).withTag("base"));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Atualização").withIcon(getResources().getDrawable(R.drawable.new_version)).withTag("update"));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIcon(getResources().getDrawable(R.drawable.menu_saida)).withTag("sair"));


        int indice = HOME_ITEM;

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Principal").withTextColor(getResources().getColor(R.color.md_red_200)).withTag("main"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Dash Board").withIcon(getResources().getDrawable(R.drawable.menu_dashboard)).withTag("dashboard"));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Usuário").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Dispositivo").withIcon(getResources().getDrawable(R.drawable.menu_dispositivo)).withTag("dispositivo"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Usuário").withIcon(getResources().getDrawable(R.drawable.menu_user)).withTag("usuario"));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Tablet").withTextColor(getResources().getColor(R.color.md_red_200)));
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedido De Composição De Carga").withIcon(getResources().getDrawable(R.drawable.caminhao)).withTag("pedidocomposicaodecarga"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedidos de roteiro").withIcon(getResources().getDrawable(R.drawable.mapa_rota)).withTag("pedidoplanejamento"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedidos fora do roteiro").withIcon(getResources().getDrawable(R.drawable.ic_action_order_sales_i)).withTag("pedidoavulso"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedido Transmitidos").withIcon(getResources().getDrawable(R.drawable.ic_action_order_sales_i)).withTag("pedidotransmitido"));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pré-Cliente").withIcon(getResources().getDrawable(R.drawable.pre_cliente_30)).withTag("precliente"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pré-Acordo").withIcon(getResources().getDrawable(R.drawable.ic_action_order_sales_i)).withTag("preacordo"));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Planejamento").withTextColor(getResources().getColor(R.color.md_red_200)));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Visualização Das Agendas").withIcon(getResources().getDrawable(R.drawable.calendar_30)).withTag("agenda01"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Relatório Das Agendas").withIcon(getResources().getDrawable(R.drawable.calendar_30)).withTag("agenda02"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Protheus").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedidos").withIcon(getResources().getDrawable(R.drawable.ic_action_order_sales_i)).withTag("pedidosprotheus"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Notas Fiscais").withIcon(getResources().getDrawable(R.drawable.ic_action_order_sales_i)).withTag("nfsprotheus"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Acordos").withIcon(getResources().getDrawable(R.drawable.ic_action_order_sales_i)).withTag("acordosprotheus"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Metas").withIcon(getResources().getDrawable(R.drawable.icon_bar_40)).withTag("metas"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Campanhas").withIcon(getResources().getDrawable(R.drawable.icon_bar_40)).withTag("campanhas"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Gerencial").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Rel. Pedidos").withIcon(getResources().getDrawable(R.drawable.ic_action_order_sales_i)).withTag("gerencial01"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Clientes").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Prospecção").withIcon(getResources().getDrawable(R.drawable.cliente_prospeccao_30)).withTag("prospeccao"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Clientes").withIcon(getResources().getDrawable(R.drawable.cliente_30)).withTag("cliente"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Conta Corrente").withIcon(getResources().getDrawable(R.drawable.financeiro_30)).withTag("CC"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Transmissão").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sincronizar Pedidos").withIcon(getResources().getDrawable(R.drawable.processo)).withTag("sincronizacao"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carga De Dados").withIcon(getResources().getDrawable(R.drawable.ic_action_down_cloud_i)).withTag("carga"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Treinamentos").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Treinamento Por Video").withIcon(getResources().getDrawable(R.drawable.youtube_02_48)).withTag("treinamento01"));

        Boolean ativo = false;

        if (status.getLOGADO().equals("S")){

            ativo = true;


        } else {


            ativo = false;

        }


        for(int count = indice; count < navigationDrawerLeft.getDrawerItems().size(); count++) {

            if (navigationDrawerLeft.getDrawerItems().get(count) instanceof PrimaryDrawerItem) {

                ( (PrimaryDrawerItem) navigationDrawerLeft.getDrawerItems().get(count)).setEnabled(ativo);

            }

        }

        navigationDrawerLeft.getAdapter().notifyDataSetChanged();



    }

    private void adicionaHeaderNavigation(){

        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savebundle)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(R.drawable.sav_logo)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        try {

                            lbUsuario.setText("Nenhum Usuário Logado.");

                            if (iProfile.getName().equals("000000")){

                                remove_frags();

                                StatusDAO dao = new StatusDAO();

                                dao.open();

                                Status status = dao.seek(null);

                                if (status != null) {

                                    status.setLOGADO("N");
                                    status.setUSERLOG(" ");
                                    status.setHORALOG(" ");

                                    dao.Update(status);

                                }

                                dao.close();

                                adicionaDrawer(savebundle);

                                adicionaLeftNavigation(status);

                                adicionaHeaderNavigation();

                                adicionaUsuarios();

                                if (savebundle != null) navigationDrawerLeft.setSelection(HOME_ITEM);

                                return false;

                            }


                            //Busca usuário

                            UsuarioDAO dao = new UsuarioDAO();

                            dao.open();

                            Usuario user = dao.seek(new String[] {iProfile.getName()});

                            dao.close();

                            if (user == null){

                                throw new Exception("Usuário Não Cadastrado No Tablet.");

                            }

                            Intent i = new Intent(SAVActivity.this, LoginActivity.class);

                            Bundle params = new Bundle();

                            params.putString("CODIGO",user.getCOD());

                            params.putString("SENHA" ,user.getSENHA());

                            i.putExtras(params);

                            startActivityForResult(i, NEW_USER);

                        } catch (Exception e) {

                            Toast.makeText(SAVActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        return false;
                    }
                })
                .build();




    }

    private void adicionaUsuarios(){

        List<Usuario> users = null;

        Status status;

        int ativo = 0;

        try {

            adicionaHeaderNavigation();

            StatusDAO daoST = new StatusDAO();

            daoST.open();

            status = daoST.seek(null);

            daoST.close();

            if (status == null){

                status = new Status();

                status.setLOGADO("N");

            }


            UsuarioDAO dao = new UsuarioDAO();

            dao.open();

            users = dao.getAll();

            dao.close();

            ProfileDrawerItem item = new ProfileDrawerItem()
                    .withName("000000")
                    .withEmail("Log OFF");

            item.setTextColorRes(R.color.black);
            item.setTextColor(R.color.black);

            headerNavigationLeft.addProfiles(item);

            for(Usuario us : users){

                ProfileDrawerItem item1 = new ProfileDrawerItem()
                        .withName(us.getCOD())
                        .withEmail(us.getNOME())
                        .withIcon(getResources().getDrawable(R.drawable.person_2));
                item1.setTextColorRes(R.color.black);
                item1.setTextColor(R.color.black);

                headerNavigationLeft.addProfiles(item1);

                if ((status.getLOGADO().equals("S")) && (us.getCOD().equals(status.getUSERLOG()))){

                    ativo = headerNavigationLeft.getProfiles().size() - 1;

                }

            }

            //headerNavigationLeft.getProfiles().get(ativo).setSelectable(true);

            headerNavigationLeft.setActiveProfile(headerNavigationLeft.getProfiles().get(ativo));

        } catch (Exception e){

            Toast.makeText(SAVActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();


        }

    }

    private void addShortcut() {

        Intent shortcutIntent = new Intent(getApplicationContext(),SAVActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "SAV 7.00");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(),R.mipmap.ic_launcher));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }

    private void atalho(){

        Application application = this.getApplication();
        String appLabel = "SAV 7.00";

        PackageInfo pInfo;
        try {
            pInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent shortcutIntent;
        shortcutIntent = new Intent();
        shortcutIntent.setComponent(new ComponentName(this.getPackageName(), ".SavActivity"));

        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final Intent putShortCutIntent = new Intent();
        putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,shortcutIntent);

        // Sets the custom shortcut's title
        putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,appLabel);
        putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(this,R.mipmap.ic_launcher));
        putShortCutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        sendBroadcast(putShortCutIntent);


    }

    @Override
    public void finish(){

//        if (serviceIntent != null){
//
//            stopService(serviceIntent);
//
//
//        }

        EventBus.getDefault().unregister(this);

        super.finish();




    }

    @Override
    protected void onDestroy() {

        Process.killProcess(Process.myPid());

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.sav, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.sav_manual) {

            Intent i = new Intent(SAVActivity.this, ManualActivity.class);

            Bundle params = new Bundle();

            i.putExtras(params);

            startActivity(i);


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == 1 && requestCode == FIRST_LOGIN) {

            if (data.hasExtra("CODIGO")) {

                try {

                    StatusDAO dao = new StatusDAO();

                    dao.open();

                    Status status = dao.seek(null);

                    dao.close();

                    if (status != null) {

                        App.setDataBaseUser();

                        adicionaDrawer(savebundle);

                        adicionaLeftNavigation(status);

                        adicionaHeaderNavigation();

                        adicionaUsuarios();

                        lbUsuario.setText(user.getNOME());

                        Intent intent = new Intent(SAVActivity.this, CargaActivity.class);

                        startActivityForResult(intent, CARGA);

                    } else {

                        lbUsuario.setText("Nenhum Usuário Logado");

                    }

                    if (savebundle != null) navigationDrawerLeft.setSelection(HOME_ITEM);


                    if (!(dashboard_frag == null)) {

                        dashboard_frag.loader = true;

                    }



                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }

        if (resultCode == 1 && requestCode == NEW_USER) {

            if (data.hasExtra("CODIGO")) {

                try {

                    UsuarioDAO dao = new UsuarioDAO();

                    dao.open();

                    Usuario user = dao.seek(new String[]{data.getExtras().getString("CODIGO")});

                    dao.close();

                    if (user != null) {

                        user = user;

                        App.setDataBaseUser();

                        StatusDAO daoST = new StatusDAO();

                        daoST.open();

                        Status status = daoST.seek(null);

                        daoST.close();

                        if (status == null) {

                            status = new Status("N", "", "", "", "", "", "", "N", "", "", "", "0");


                        }

                        adicionaDrawer(savebundle);

                        adicionaLeftNavigation(status);

                        adicionaHeaderNavigation();

                        adicionaUsuarios();
                        Log.i(TAG,"PONTO B");
                        if (status.getLOGADO().equals("S")) {

                            lbUsuario.setText(user.getNOME());

                        } else {

                            lbUsuario.setText("Nenhum Usuário Logado.");

                        }


                    } else {

                        user = null;

                    }

                    if (savebundle != null) {
                        navigationDrawerLeft.setSelection(HOME_ITEM);
                    }

                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


        }


        if (resultCode == 0 && requestCode == HelpInformation.UpdateVersion) {

            finish();

        }

    }

    public void UpdateVersion(){

        Intent intent = new Intent(SAVActivity.this, UpdateVersionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle params = new Bundle();
        params.putBoolean("isUpdate"   , false);
        intent.putExtras(params);
        startActivityForResult(intent, HelpInformation.UpdateVersion);

    }

    private void CreateUserStructure(String codigo) throws Exception {

        String path = App.BasePath + "/" + App.AppPath + "/" + codigo;

        try {

            if (!isFirstAgente(codigo)) {

                File dir = new File(path);

                if (!dir.exists()) {

                    if (!dir.mkdirs()) {

                        throw new Exception("Erro Na Criação Da Pasta Do Usuário.");

                    }

                }

            } else {

                //throw new Exception("Pasta Do Usuário Não Existe.");

            }

        } catch (Exception e) {

            throw new Exception("Erro Na Criação Da Pasta Do Usuário.");

        }

    }

    private Boolean isFirstAgente(String codigo) {

        Boolean retorno = false;

        String path = App.BasePath + "/" + App.AppPath + "/" + codigo;

        /* Verifica a existencia do diretorio */

        File dir = new File(path);

        try {

            retorno = dir.exists();

        } catch (Exception e) {

            retorno = true;

        }

        return retorno;
    }

    private void firstLogin() {

        Intent i = new Intent(SAVActivity.this, BemVindoActivity.class);
        Bundle params = new Bundle();
        i.putExtras(params);
        startActivityForResult(i, FIRST_LOGIN);

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("SAV", "http://[ENTER-YOUR-URL-HERE]");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }


    private class defaultAdapter extends ArrayAdapter {

        private int escolha = 0;

        private List<String[]> lista;

        private String label;

        private boolean isInicializacao = true;

        private Context context;

        public defaultAdapter(Context context, int textViewResourceId, List<String[]> objects,String label) {

            super(context, textViewResourceId,objects);

            this.lista = objects;

            this.label = label;

            this.context = context;
        }


        public String getOpcao(int pos){


            if ( (pos < this.lista.size() )){


                return lista.get(pos)[1];

            }

            return "";

        }
        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }

        public View getOpcoesView(int position, View convertView, ViewGroup parent) {

            String col01 = lista.get(position)[0];
            String col02 = lista.get(position)[1];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_01_row, parent, false);

            TextView tvcol01 = (TextView) layout.findViewById(R.id.txt_col01_898);

            TextView tvcol02 = (TextView) layout.findViewById(R.id.txt_col02_898);

            tvcol01.setTextSize(14f);

            tvcol02.setTextSize(14f);


            tvcol01.setText(col01);

            tvcol02.setText(col02);

            tvcol01.setTextColor(Color.RED);

            tvcol02.setTextColor(Color.RED);

            ImageView img = (ImageView) layout.findViewById(R.id.im_flag_898);

            if (position == escolha) {

                img.setVisibility(View.VISIBLE);

                tvcol01.setTextColor(Color.BLACK);

                tvcol02.setTextColor(Color.BLACK);

            }

            return layout;
        }

        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            String obj = lista.get(position)[1];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            tvlabel.setText(this.label);

            if (this.label.isEmpty()){

                tvlabel.setVisibility(View.GONE);

            }

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setText(obj);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            img.setVisibility(View.GONE);

            if (position == escolha) {

                tvOpcao.setTextColor(getResources().getColor(R.color.dark_blue));

                tvOpcao.setGravity(Gravity.CENTER);

            }

            return layout;
        }

        // Mostra as Opções
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            return getOpcoesView(position, convertView, parent);

        }

        // Mostra o item selecionado
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getEscolhaView(position, convertView, parent);

        }

        public boolean isInicializacao() {
            return isInicializacao;
        }

        public void setIsInicializacao(boolean isInicializacao) {
            this.isInicializacao = isInicializacao;
        }
    }


//    private void eventoDataBaseUSUARIOS() {
//
//        databaseReference.child("USUARIOS").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot obj : dataSnapshot.getChildren()){
//
//                    try {
//
//                        Usuario_fast user = obj.getValue(Usuario_fast.class);
//
//                        if (user.getCOD().equals(user.getCOD())) {
//
//                            Toast.makeText(App.getCustomAppContext(), " Usuário Alterado " + user.getNOME(), Toast.LENGTH_LONG).show();
//
//                        }
//                    } catch (Exception e ){
//
//                        StringWriter sw = new StringWriter();
//
//                        e.printStackTrace(new PrintWriter(sw));
//
//                        String exceptionAsString = sw.toString();
//
//                        Log.i("SAV",exceptionAsString);
//
//                        Toast.makeText(App.getCustomAppContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }
//
//    private void eventoDataBaseEMPRESA() {
//
//        databaseReference.child("EMPRESA").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot obj : dataSnapshot.getChildren()){
//
//                    try {
//
//                        Empresa empresa = obj.getValue(Empresa.class);
//
//                        if (empresa.getCODIGO().equals("001")) {
//
//                            Toast.makeText(App.getCustomAppContext(), " ALTERADO HORÁRIO DE CARGA " + empresa.getPEDDATA()+" A Partir De "+empresa.getPEDHORA(), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    } catch (Exception e ){
//
//                        StringWriter sw = new StringWriter();
//
//                        e.printStackTrace(new PrintWriter(sw));
//
//                        String exceptionAsString = sw.toString();
//
//                        Log.i("SAV",exceptionAsString);
//
//                        Toast.makeText(App.getCustomAppContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }

    //Event Bus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverNotification(NotificationCarga notificationCarga){


        refresh();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverNotification(NotificationSincronizacao notificationSincronizacao){


        refresh();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverNotification(NotificationPedido notificationPedido){


        refresh();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverNotification(NotificationConexao notificationConexao){

        dashboard_frag.config = notificationConexao.getCONFIG();

        dashboard_frag.conection_state = notificationConexao.getSTATUS();

        dashboard_frag.setarCabec();

    }

}
