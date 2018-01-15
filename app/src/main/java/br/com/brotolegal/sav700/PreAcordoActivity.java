package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.ksoap2.serialization.SoapObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.sav700.VerificaWeb.ConexaoAdapter;
import br.com.brotolegal.sav700.VerificaWeb.Parametros;
import br.com.brotolegal.sav700.VerificaWeb.ParametrosAdapter;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.PreAcordoDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.eventbus.NotificationPedido;
import br.com.brotolegal.savdatabase.eventbus.NotificationPreAcordo;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CADASTRO_PREACORDO;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;

public class PreAcordoActivity extends AppCompatActivity {

    private Spinner spConexao;

    private Spinner spParamentros;

    private List<Config> conexoes;

    private ConexaoAdapter conexaoadapter;

    private ParametrosAdapter parametrosadapter;

    private String StatusRede = "Status Não Verificado.";

    private List<Parametros> lsParametros = new ArrayList<>();

    private Config config;

    private Dialog dialog;

    private Boolean OrdemRefresh = true;

    private Toolbar toolbar;

    private ListView lv;

    private List<Object> lsLista;

    Spinner spAlcada;

    Spinner spVerba;

    Spinner spOrdem;

    EditText edPesquisa;

    Map<String,String> mpAlcada           = new TreeMap<String, String >();

    Map<String,String> mpVerba            = new TreeMap<String, String >();

    List<String[]> lsAlcada               = new ArrayList<>();

    List<String[]> lsVerba                = new ArrayList<>();

    List<String[]> lsOrdens               = new ArrayList<>();

    FloatingActionButton fab;

    defaultAdapter situacaoadapter;

    defaultAdapter alcadaadapter;

    defaultAdapter ordensadapter;

    Adapter adapter;

    CallBack callBack;

    CallBackPreAcordo callbackpreacordo;

    String CodCliente = "";

    String LojCliente = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_acordo);

        try {
            toolbar = (Toolbar) findViewById(R.id.tb_preacordo_485);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Pré-Acordos");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            spConexao     = (Spinner) findViewById(R.id.spConexao_485);

            spParamentros = (Spinner) findViewById(R.id.spParametro_485);

            toolbar.inflateMenu(R.menu.menu_preacordo);

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CodCliente = params.getString("CODCLIENTE");

                LojCliente = params.getString("LOJCLIENTE");

            }

            verWeb();

            fab = (FloatingActionButton) findViewById(R.id.plus_preacordo);

            lv = (ListView) findViewById(R.id.lvPreAcordo_485);

            spAlcada   = (Spinner) findViewById(R.id.sp_cidade_334);

            spVerba   = (Spinner) findViewById(R.id.sp_rede_334);

            spOrdem   = (Spinner) findViewById(R.id.sp_ordem_334);

            edPesquisa = (EditText) findViewById(R.id.edpesquisa_334);

            edPesquisa.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    v.setFocusable(true);
                    v.setFocusableInTouchMode(true);
                    return false;
                }
            });


            lsOrdens = new ArrayList<>();

            lsOrdens.add(new String[]{"01", "Nº Tablet     "});
            lsOrdens.add(new String[]{"02", "Nº Protheus   "});
            lsOrdens.add(new String[]{"03", "Razão Social  "});
            lsOrdens.add(new String[]{"04", "Código Cliente"});


            ordensadapter = new defaultAdapter(PreAcordoActivity.this, R.layout.choice_default_row, lsOrdens, "");

            spOrdem.setAdapter(ordensadapter);

            spOrdem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ordensadapter.setEscolha(position);

                    Object lixo = spOrdem.getSelectedItem();

                    if (lixo != null){

                        if (adapter != null){

                            adapter.setOrdem(((String[]) lixo)[0]);

                        }

                    }

                    edPesquisa.setText("");

                    switch (position) {
                        case 0:  //numerico tablet

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                            break;

                        case 1:  //numerico protheus

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);


                            break;

                        case 2:  //alpha razao social

                            edPesquisa.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);


                            break;

                        case 3:  //numerico codigo do cliente

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                            break;

                    }

                    if (!(lsLista == null)) {

                        loadPreAcordo();

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spOrdem.setSelection(0);

            loadPreAcordo();

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PreAcordoActivity.this,CadastroPreAcordoActivity.class);
                    Bundle params = new Bundle();
                    params.putString("CODCLIENTE", "");
                    params.putString("LOJCLIENTE", "");
                    intent.putExtras(params);
                    startActivity(intent);
                }
            });


        } catch (Exception e){

            showToast(e.getMessage());

            finish();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preacordo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_preacordo_sincronizar:{

                if (!conexaoadapter.getConnected()){

                    toast("Sem Conexão Ativa !");

                } else {

                    callbackpreacordo = new CallBackPreAcordo();

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerTrasmissao, getBaseContext(), App.user, "PUTACORDOS", "PUTACORDOS", RETORNO_TIPO_ESTUTURADO, PROCESSO_CADASTRO_PREACORDO, config, callbackpreacordo,-1);

                    acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

                    acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

                    acessoWeb.start();

                }

                break;

            }


            default:

                finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {

        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);

        super.onStop();
    }


    @Override
    public void onResume() {

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        loadPreAcordo();

        super.onResume();
    }


    @Override
    public void finish() {

        lsLista            = new ArrayList<Object>();

        lsAlcada           = new ArrayList<>();

        lsVerba            = new ArrayList<>();

        lsOrdens           = new ArrayList<>();

        super.finish();

    }


    private void loadPreAcordo(){

        try {

            lsLista = new ArrayList<>();

            lsAlcada           = new ArrayList<>();

            lsVerba            = new ArrayList<>();

            lsOrdens           = new ArrayList<>();

            lsAlcada.add(new String[] {""  ,"TODAS"});

            lsVerba.add(new String[]  {""  ,"TODA"});

            lsLista.add("Pré-Acordo");

            Object lixo      = spOrdem.getSelectedItem();

            PreAcordoDAO dao = new PreAcordoDAO();

            dao.open();

            lsLista.addAll(dao.getAllWithComplemento());

            dao.close();

            if (lsLista.size() == 1){

                lsLista.add(new NoData("Nenhm Pré-Acordo Encontrado !!!"));

            } else {

                for (Object obj : lsLista) {

                    if (obj instanceof PreAcordo) {

                        try {

                            mpAlcada.put(((PreAcordo) obj).getSTATUS(), ((PreAcordo) obj).get_Status());

                            mpVerba.put(((PreAcordo) obj).getCODVERB(), ((PreAcordo) obj).get_VERBA());

                        } catch (Exception e) {

                            //

                        }

                    }

                }

                for(Map.Entry<String, String> values : mpAlcada.entrySet()){

                    lsAlcada.add(new String[] {values.getKey(),values.getValue()});

                }

                for(Map.Entry<String, String> values : mpVerba.entrySet()){

                    lsVerba.add(new String[] {values.getKey(),values.getValue()});

                }
            }

            alcadaadapter = new defaultAdapter(PreAcordoActivity.this, R.layout.choice_default_row, lsAlcada,"Alçada:");

            spAlcada.setAdapter(alcadaadapter);

            spAlcada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

                    alcadaadapter.setEscolha(position);

                    Object lixo = spAlcada.getSelectedItem();

                    adapter.setAlcada(((String[]) lixo)[0]);

                    adapter.refresh();

                    OrdemRefresh = false;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spVerba.setSelection(0);

            situacaoadapter = new defaultAdapter(PreAcordoActivity.this, R.layout.choice_default_row, lsVerba,"Verba.:");

            spVerba.setAdapter(situacaoadapter);

            spVerba.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

                    situacaoadapter.setEscolha(position);

                    Object lixo = spVerba.getSelectedItem();

                    adapter.setVerba(((String[]) lixo)[0]);

                    adapter.refresh();

                    OrdemRefresh = false;


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spVerba.setSelection(0);

            edPesquisa.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,	int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {


                        if (!OrdemRefresh){

                            spAlcada.setSelection(0);

                            spVerba.setSelection(0);

                            adapter.refresh2();

                        }

                    } catch (Exception e) {

                        Log.i("SAV", e.getMessage());

                    }

                }
            });

            adapter = new Adapter(getBaseContext(),lsLista);

            adapter.setOrdem(((String[]) lixo)[0]);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        } catch (Exception e){

            showToast(e.getMessage());

        }

    }


    private void showToast(String mensagem){

        Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();

    }


    private void verWeb(){

        int IndiceConexao = 0;

        try {

            StatusDAO dao = new StatusDAO();

            dao.open();

            Status st = dao.seek(null);

            if (st != null) {

                String tempo;

                if (st.getCARGA().equals("N")){

                    tempo = "No Dia:"+st.getCARDATA() + " A Partir Das: "+st.getCARHORA();

                } else {

                    tempo = "";

                }


                lsParametros.add(new Parametros(0,st.getCargaStatus() ,tempo));

                if (st.getPEDIDO().equals("N")){

                    tempo = "Até Dia:"+st.getPEDDATA() + " Até As: "+st.getPEDHORA();

                } else {

                    tempo = "";

                }

                lsParametros.add(new Parametros(1,st.getPedidoStatus(),tempo));

            } else {

                lsParametros.add(new Parametros(0,"SEM INFORMAÇÃO",""));
            }

            dao.close();

            parametrosadapter = new ParametrosAdapter(PreAcordoActivity.this, R.layout.conexoes_opcoes, lsParametros);

            spParamentros.setAdapter(parametrosadapter);

            spParamentros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    parametrosadapter.setEscolha(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spParamentros.setSelection(1);

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

        try {

            ConfigDAO dao = new ConfigDAO();

            dao.open();

            Config config = dao.seek(new String[]{"000"});

            conexoes = dao.getConexoes();

            Config padrao = dao.seekByDescricao( new String[]{config.getDESCRICAO()});

            if (padrao != null) {

                IndiceConexao = 0;

                for( int x = 0; x < conexoes.size() ; x++){

                    if (padrao.getDESCRICAO().equals(conexoes.get(x).getDESCRICAO())) {

                        IndiceConexao = x;

                        break;
                    }
                }

            } else {

                IndiceConexao = 0;

            }

            dao.close();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

        conexaoadapter = new ConexaoAdapter(PreAcordoActivity.this, R.layout.conexoes_opcoes, conexoes);

        spConexao.setAdapter(conexaoadapter);

        spConexao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if ((conexaoadapter.getEscolha() == -2)) {

                    conexaoadapter.setEscolha(-1);

                } else {

                    conexaoadapter.setEscolha(position);

                    config = conexoes.get(position);

                    config.setCODIGO("000");

                    conexaoadapter.setStatusRede(true, "Verificando...", null);

                    callBack = new CallBack();

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerRede, getBaseContext(), App.user, "GETSTATUS", "GETSTATUS",RETORNO_TIPO_ESTUTURADO,PROCESSO_CUSTOM, config, callBack,-1);

                    acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

                    acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

                    acessoWeb.start();

                    try {

                        ConfigDAO dao = new ConfigDAO();

                        dao.open();

                        dao.Update(config);

                        dao.close();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), "Não Atualizada A Conexão !!\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spConexao.setSelection(IndiceConexao);


    }

    private Handler mHandlerTrasmissao=new Handler(){

        @Override
        public void handleMessage(Message msg){

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    toast("NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(PreAcordoActivity.this, msg.getData().getString("CMSGERRO"), "Acessando Servidores.Aguarde !!", false, true);
                    dialog.setCancelable(false);
                    dialog.show();

                    processado = true;
                }


                if (msg.getData().getString("CERRO").equals("MMM")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.setTitle(msg.getData().getString("CMSGERRO"));

                        }

                    }

                    processado = true;

                }


                if ((msg.getData().getString("CERRO").equals("FEC"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    if (!msg.getData().getString("CMSGERRO").isEmpty()) {

                        toast("Erro: " + msg.getData().getString("CMSGERRO"));

                    }

                    loadPreAcordo();

                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(PreAcordoActivity.this, "Erro: ???" + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            } catch (Exception E) {

                toast("Erro Handler: " + E.getMessage());

            }
        }




    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onReceiverNotification(NotificationPreAcordo notificationPreAcordo) {

        if (notificationPreAcordo.getACORDO().isEmpty()){

            loadPreAcordo();

            return;

        };

        try {

            PreAcordoDAO dao = new PreAcordoDAO();

            dao.open();

            PreAcordo preacordo = dao.getOneWithComplemento(notificationPreAcordo.getACORDO());

            if (preacordo != null) {

                adapter.setPreAcordo(preacordo);

            }

            dao.close();

        } catch (Exception e) {

            Toast.makeText(PreAcordoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }


    private Handler mHandlerRede = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    toast("NAO CONTEM AS CHAVES..");

                    return;

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    try {

                        callBack.processa();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }


            }
            catch (Exception e){

                toast(e.getMessage());

            }
        }
    };


    //inner class

    private class Adapter extends BaseAdapter {


        private String _Alcada     = "";
        private String _Verba      = "";
        private String _Ordem      = "";

        private DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private DecimalFormat format_03 = new DecimalFormat(",##0.000");
        private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
        private DecimalFormat format_05 = new DecimalFormat(",##0.00000");

        private List<Object> lsObjetos;

        private Context context;

        final int ITEM_VIEW_CABEC    = 0;
        final int ITEM_VIEW_ACORDO   = 1;
        final int ITEM_VIEW_NO_DATA  = 2;
        final int ITEM_VIEW_COUNT    = 4;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos  = filtro();

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof PreAcordo) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Pré-Acordos: " + String.valueOf(qtd);

            return retorno;
        }


        public void setPreAcordo(PreAcordo preacordo){

            int x = 0;

            for(Object obj : lsLista){

                if (obj instanceof PreAcordo){

                    if (((PreAcordo) obj).getCODMOBILE().equals(preacordo.getCODMOBILE())  ){

                        lsLista.set(x,preacordo);

                        break;

                    }

                }

                x++;

            }

            x = 0;

            for(Object obj : lsObjetos){

                if (obj instanceof PreAcordo){

                    if (((PreAcordo) obj).getCODMOBILE().equals(preacordo.getCODMOBILE()) ){

                        lsObjetos.set(x,preacordo);

                        break;

                    }

                }

                x++;

            }


            this.notifyDataSetChanged();

        }


        public void setVerba(String filtro){

            _Verba = filtro;

        }

        public void setAlcada(String filtro){

            _Alcada = filtro;

        }

        public void setOrdem(String filtro){

            _Ordem = filtro;

        }

        public void refresh(){


            this.lsObjetos  = filtro();

            notifyDataSetChanged();

        }

        public void refresh2(){

            this.lsObjetos  = filtro2();

            notifyDataSetChanged();

        }

        private List<Object> filtro() {

            List<Object> result = null;

            if (_Alcada.equals("") && _Verba.equals("")) {

                return new ArrayList(lsLista);

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsLista.size(); x++) {

                    if (lsLista.get(x) instanceof PreAcordo) {

                        if (    (_Alcada.equals("") || (_Alcada.equals(((PreAcordo) lsLista.get(x)).getSTATUS()))) &&
                                (_Verba.equals("") || _Verba.equals(((PreAcordo) lsLista.get(x)).getCODVERB())) ) {

                            result.add(lsLista.get(x));

                        }
                    } else {

                        result.add(lsLista.get(x));

                    }

                }

            }

            if (result.size() == 1){

                result.add(new NoData("Nenhum Pré-Acordo Para O Filtro !!!"));

            }
            return result;
        }

        private List<Object> filtro2() {

            List<Object> result = null;

            if (edPesquisa.getText().toString().trim().isEmpty()) {

                return new ArrayList(lsLista);

            }
            else {

                result = new ArrayList<>();

                for (int x = 0; x < lsLista.size(); x++) {

                    if (lsLista.get(x) instanceof PreAcordo) {


                        if (_Ordem.equals("01")) {//nro do tablet

                            if (((PreAcordo) lsLista.get(x)).getCODMOBILE().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }


                        }
                        if (_Ordem.equals("02")) {//nro protheus

                            if (((PreAcordo) lsLista.get(x)).getNUM().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }

                        }

                        if (_Ordem.equals("03")) {//razao social

                            if (((PreAcordo) lsLista.get(x)).get_RAZAO().toUpperCase().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }

                        }

                        if (_Ordem.equals("04")) {//codigo cliente

                            if (((PreAcordo) lsLista.get(x)).getCLIENTE().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }
                        }



                    } else {

                        result.add(lsLista.get(x));

                    }

                }

            }

            if (result.size() == 1){

                result.add(new NoData("Nenhum Cliente Para O Filtro !!!"));

            }
            return result;
        }

        @Override
        public int getCount() {
            return lsObjetos.size();
        }

        @Override
        public Object getItem(int position) {
            return lsObjetos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_COUNT;
        }

        @Override
        public int getItemViewType(int position) {

            int retorno = -1;


            if (lsObjetos.get(position) instanceof String) {

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsObjetos.get(position) instanceof PreAcordo) {

                retorno = ITEM_VIEW_ACORDO;

            }


            if (lsObjetos.get(position) instanceof NoData) {

                retorno = ITEM_VIEW_NO_DATA;

            }

            return retorno;


        }

        @Override
        public boolean isEnabled(int position) {

            boolean retorno = false;

            return retorno;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            try {

                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_ACORDO:

                            convertView = inflater.inflate(R.layout.preacordo_row, null);

                            break;


                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_ACORDO: {

                        final PreAcordo obj = (PreAcordo) lsObjetos.get(pos);

                        ImageButton bt_cadastro      = (ImageButton) convertView.findViewById(R.id.bt_cadastro_437);

                        ImageButton bt_alcadas       = (ImageButton) convertView.findViewById(R.id.bt_alcadas_437);

                        ImageButton bt_financeiro    = (ImageButton) convertView.findViewById(R.id.bt_financeiro_437);

                        ImageButton bt_pedidos       = (ImageButton) convertView.findViewById(R.id.bt_pedidos_437);

                        ImageButton bt_descricao     = (ImageButton) convertView.findViewById(R.id.bt_descricao_437);

                        ImageButton bt_visualizar    = (ImageButton) convertView.findViewById(R.id.bt_visualizar_437);


                        TextView lbl_saldo_437 = (TextView) convertView.findViewById(R.id.lbl_saldo_437);

                        lbl_saldo_437.setText("Valor: "+format_02.format(obj.getSLDINI()));

                        TextView lbl_status_437 = (TextView) convertView.findViewById(R.id.lbl_status_437);

                        lbl_status_437.setText("Status: "+obj.get_Status());

                        TextView txt_mobile_437 = (TextView) convertView.findViewById(R.id.txt_mobile_437);

                        txt_mobile_437.setText("Pré-Acordo Tablet: "+obj.getCODMOBILE());

                        TextView txt_emissao_437 = (TextView) convertView.findViewById(R.id.txt_emissao_437);

                        txt_emissao_437.setText("Emissao: "+obj.getDATA());

                        TextView txt_protheus_437 = (TextView) convertView.findViewById(R.id.txt_protheus_437);

                        txt_protheus_437.setText("Cód. Protheus: "+obj.getNUM());

                        TextView txt_cliente_437 = (TextView) convertView.findViewById(R.id.txt_cliente_437);

                        if (obj.getREDE().trim().isEmpty()) {

                            txt_cliente_437.setText("Cliente: " + obj.getCLIENTE() + "-" + obj.getLOJA() + " " + obj.get_RAZAO());

                        } else {

                            txt_cliente_437.setText("Rede:  " + obj.getREDE() + " " + obj.get_REDE());

                        }
                        TextView txt_verba_437 = (TextView) convertView.findViewById(R.id.txt_verba_437);

                        txt_verba_437.setText("Verba: "+obj.getCODVERB()+"-"+obj.get_VERBA());

                        TextView txt_tipopagto_437   = (TextView) convertView.findViewById(R.id.txt_tipopagto_437);

                        txt_tipopagto_437.setText("Pagto: "+obj.getTIPOPAG()+"-"+obj.get_FormaPagto());

                        TextView txt_dtinicio_437   = (TextView) convertView.findViewById(R.id.txt_dtinicio_437);

                        txt_dtinicio_437.setText("Data Inicial: "+obj.getDATAINI());

                        TextView txt_dtfim_437   = (TextView) convertView.findViewById(R.id.txt_dtfim_437);

                        txt_dtfim_437.setText("Data Final: "+obj.getDATAFIM());

                        TextView txt_dtpagto_437 = (TextView) convertView.findViewById(R.id.txt_dtpagto_437);

                        txt_dtpagto_437.setText("Data Pagto: "+obj.getDATAPAGTO());

                        TextView txt_descricao_437 = (TextView) convertView.findViewById(R.id.txt_obs_437);

                        txt_descricao_437.setText("Observação: "+obj.getOBS());


                        if (obj.getREDE().trim().isEmpty()) {

                            bt_cadastro.setVisibility(View.VISIBLE);

                            bt_cadastro.setOnClickListener(new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View v) {

                                                                   Intent intent = new Intent(context, ClienteViewAtivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                   Bundle params = new Bundle();
                                                                   params.putString("CODIGO", obj.getCLIENTE());
                                                                   params.putString("LOJA", obj.getLOJA());
                                                                   intent.putExtras(params);
                                                                   context.startActivity(intent);

                                                               }
                                                           }
                            );
                        }else {

                            bt_cadastro.setVisibility(View.INVISIBLE);

                        }

                        bt_alcadas.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               clickMemo("HISTÓRICO DAS ALÇADAS","HISTLIB" ,obj);

                                                           }
                                                       }
                        );


                        bt_financeiro.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {

//                                                                 Intent intent=new Intent(context,Receber_View_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                                 Bundle params = new Bundle();
//                                                                 params.putString("CODIGO"  , obj.getCODIGO());
//                                                                 params.putString("LOJA"    , obj.getLOJA());
//                                                                 intent.putExtras(params);
//                                                                 context.startActivity(intent);

                                                             }
                                                         }
                        );


                        bt_pedidos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                            }
                        });

                        bt_descricao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            clickMemo("DESCRIÇÃO DO ACORDO","DESCRIC" ,obj);

                            }
                        });


                        bt_visualizar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(PreAcordoActivity.this,CadastroPreAcordoActivity.class);
                                Bundle params = new Bundle();
                                params.putString("ID"      , obj.getCODMOBILE());
                                params.putString("CODVERBA", obj.getCODVERB());
                                params.putString("STATUS"  , obj.getSTATUS());
                                intent.putExtras(params);
                                startActivity(intent);

                            }
                        });

                        break;

                    }


                    case ITEM_VIEW_NO_DATA: {

                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                        tvTexto.setText(obj.getMensagem());

                        break;

                    }


                    default:
                        break;
                }

            } catch (Exception e) {

                toast("Erro No Adapdador =>" + e.getMessage());

            }

            return convertView;

        }


        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }


        private void clickMemo(String label, final String campo,PreAcordo preacordo) {

            final Dialog dialog = new Dialog(PreAcordoActivity.this);

            dialog.setContentView(R.layout.getmemoviewpadrao);

            dialog.setTitle(label);

            final Button confirmar = (Button) dialog.findViewById(R.id.btn_577_ok);
            final TextView edCampo = (TextView) dialog.findViewById(R.id.txt_edcampo_577);


            edCampo.setText((String) preacordo.getFieldByName(campo));

            confirmar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    dialog.dismiss();

                }


            });

            dialog.show();
        }

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

            String obj = lista.get(position)[1];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);


            TextView label   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            label.setVisibility(View.GONE);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setTextSize(18f);

            tvOpcao.setText(obj);

            tvOpcao.setTextColor(Color.RED);

            tvOpcao.setBackgroundResource(R.color.white);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            img.setVisibility(View.GONE);

            if (position == escolha) {

                tvOpcao.setTextColor(Color.BLACK);
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

    private void LoadStatus() {

        try {

            lsParametros = new ArrayList<>();

            StatusDAO dao = new StatusDAO();

            dao.open();

            Status st = dao.seek(null);

            if (st != null) {

                String tempo;

                if (st.getCARGA().equals("N")) {

                    tempo = "No Dia:" + st.getCARDATA() + " A Partir Das: " + st.getCARHORA();

                } else {

                    tempo = "";

                }

                lsParametros.add(new Parametros(0, st.getCargaStatus(), tempo));

                if (st.getPEDIDO().equals("N")) {

                    tempo = "Até o Dia:" + st.getCARDATA() + " Até As: " + st.getCARHORA();

                } else {

                    tempo = "";

                }

                lsParametros.add(new Parametros(1, st.getPedidoStatus(), tempo));

            } else {

                lsParametros.add(new Parametros(0, "SEM INFORMAÇÃO", ""));
            }

            dao.close();

            parametrosadapter = new ParametrosAdapter(PreAcordoActivity.this, R.layout.conexoes_opcoes, lsParametros);

            spParamentros.setAdapter(parametrosadapter);

            spParamentros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    parametrosadapter.setEscolha(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            if (conexaoadapter == null){


            } else {

                spParamentros.setSelection(conexaoadapter.getEscolha());

            }


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

    }

    private void toast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    private class CallBack extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro       = result.getPropertyAsString("CERRO");
            String cmsgerro    = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                String MV_ZBLECRG  = App.TotvsSN(result.getPropertyAsString("MV_ZBLECRG"));
                String MV_ZBLEPED  = App.TotvsSN(result.getPropertyAsString("MV_ZBLEPED"));
                String MV_ZDTECRG  = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTECRG"));
                String MV_ZDTEPED  = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTEPED"));
                String MV_ZHRECRG  = result.getPropertyAsString("MV_ZHRECRG");
                String MV_ZHREPED  = result.getPropertyAsString("MV_ZHREPED");

                cmsgerro = "Conexão Ativa.";

                conexaoadapter.setStatusRede(false, cmsgerro, true);

                StatusDAO dao = new StatusDAO();

                dao.open();

                Status st = dao.seek(null);

                if (st == null){

                    dao.insert(new Status("N",MV_ZBLEPED,MV_ZDTEPED,MV_ZHREPED,MV_ZBLECRG,MV_ZDTECRG,MV_ZHRECRG,"N","","","","0"));

                } else {

                    st.setAPPBLOCK("N");
                    st.setPEDIDO(MV_ZBLEPED);
                    st.setPEDDATA(MV_ZDTEPED);
                    st.setPEDHORA(MV_ZHREPED);
                    st.setCARGA(MV_ZBLECRG);
                    st.setCARDATA(MV_ZDTECRG);
                    st.setCARHORA(MV_ZHRECRG);


                    dao.Update(st);

                }

                dao.close();

                LoadStatus();

            } else {
                //Altera algumas mensagens
                if (cmsgerro.contains("failed to connect")) {

                    cmsgerro = "Falha de Conexão.";

                } else {

                    cmsgerro = cmsgerro;

                }

                conexaoadapter.setStatusRede(false,cmsgerro,false);
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

        }
    }

    private class CallBackPreAcordo extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

        }

        @Override
        public void processaArray() throws Exception {

            if (this.result == null) {

                return;

            }

        }
    }




}
