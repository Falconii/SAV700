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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.OcorrenciaDAO;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.TaskDAO;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Ocorrencia;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Task;
import br.com.brotolegal.savdatabase.eventbus.NotificationPedido;
import br.com.brotolegal.savdatabase.eventbus.NotificationSincronizacao;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;
import br.com.brotolegal.savdatabase.internet.SoapServEnv;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeAgendamentoAtrasado;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeTabelaPreco;
import br.com.brotolegal.savdatabase.regrasdenegocio.PedidoBusinessV10;

public class PedidosActivity extends AppCompatActivity {

    FloatingActionButton fab;

    private int Result = 1;

    private Spinner spConexao;

    private Spinner spParamentros;

    private List<Config> conexoes;

    private conexaoAdapter conexaoadapter;

    private parametrosAdapter parametrosadapter;

    private String StatusRede = "Status Não Verificado.";

    private verRede verrede;

    private Dialog dialog;

    private String LOG = "PEDIDOS";

    private Toolbar toolbar;

    private ListView lv;

    private ProgressBar pbBarra;

    private List<Object> lsLista = new ArrayList<Object>();

    private List<parametros> lsParametros = new ArrayList<>();

    private Adapter adapter;

    private Tasking tasking;

    private Config config;

    private String CodigoConfig  = "";

    private int    IndiceConexao = 0;

    private String CODIGO        = "";

    private String LOJA          = "";

    private String ROTINA        = "";

    private String IDAGE         = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        try {

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CODIGO     = params.getString("CODIGO","");

                LOJA       = params.getString("LOJA"  ,"");

                ROTINA     = params.getString("ROTINA","");

                IDAGE      = params.getString("IDAGE" ,"");


            }


            toolbar = (Toolbar) findViewById(R.id.tb_pedido_24);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Sincronização Dos Documentos." + ( ROTINA.equals("AGENDAMENTO") ? "AGENDAMENTOS" : ""));
            toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);

            toolbar.inflateMenu(R.menu.menu_pedido);

            spConexao     = (Spinner) findViewById(R.id.spConexao_24);

            spParamentros = (Spinner) findViewById(R.id.spParametro_24);

            lv = (ListView) findViewById(R.id.lvPedidos_24);

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


                    lsParametros.add(new parametros(0,st.getCargaStatus() ,tempo));

                    if (st.getPEDIDO().equals("N")){

                        tempo = "Até Dia:"+st.getPEDDATA() + " Até As: "+st.getPEDHORA();

                    } else {

                        tempo = "";

                    }

                    lsParametros.add(new parametros(1,st.getPedidoStatus(),tempo));

                } else {

                    lsParametros.add(new parametros(0,"SEM INFORMAÇÃO",""));
                }

                dao.close();

                parametrosadapter = new parametrosAdapter(PedidosActivity.this, R.layout.conexoes_opcoes, lsParametros);

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

                config = dao.seek(new String[]{"000"});

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

            conexaoadapter = new conexaoAdapter(PedidosActivity.this, R.layout.conexoes_opcoes, conexoes);

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

                        verrede = new verRede();

                        AccessWebInfo acessoWeb = new AccessWebInfo(mHandler, getBaseContext(), App.user, "GETSTATUS", "GETSTATUS", 1, 1, config, verrede,-1);

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


            fab = (FloatingActionButton) findViewById(R.id.plus_pedido);

            if (CODIGO.equals("")){

                fab.hide();

            } else {

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {

                            App.tabelaValida();

                        } catch (ExceptionValidadeTabelaPreco e) {

                            toast(e.getMessage());

                            return;
                        }
                        catch (ExceptionValidadeAgendamentoAtrasado e) {

                            Intent intent = new Intent(PedidosActivity.this,AgendamentosAtrasadosActivity.class);

                            startActivity(intent);

                            return;

                        } catch (Exception e) {

                            toast(e.getMessage());

                            return;

                        }


                        try {

                            Intent intent = new Intent(getApplicationContext(), PedidoV10Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle params = new Bundle();
                            params.putString("CODIGO"   , CODIGO);
                            params.putString("LOJA"     , LOJA);
                            params.putString("OPERACAO" , "NOVO");
                            params.putString("NROPEDIDO", "");
                            params.putString("IDAGE"    , IDAGE);
                            params.putString("NEGOCIACAO",(ROTINA.equals("CONSULTANEGOCIACAO") ? "S" : "N"));
                            intent.putExtras(params);
                            getApplicationContext().startActivity(intent);

                        } catch (Exception e) {

                            toast(e.getMessage());

                        }
                    }
                });



            }




        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedido, menu);


        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // action with ID action_refresh was selected
            case android.R.id.home:

                finish();

                break;

            case R.id.pedido_enviar_ok: {

                try {

                    transmitir();

                } catch (Exception e) {

                    toast(e.getMessage());

                }

                break;
            }

            case R.id.pedido_enviar_cancelar: {

                finish();

                break;
            }


            default:

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
    protected void onResume() {


        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        LoadPedidos();

        super.onResume();
    }


    @Override
    public  void finish(){

        lsLista = new ArrayList<Object>();

        Result = 1;

        Intent data = new Intent();

        data.putExtra("ROTINA","SINCRONIZACAO");

        setResult(Result, data);


        super.finish();

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

        lsLista = new ArrayList<Object>();

    }


    private void LoadPedidos(){


        List<PedidoCabMb> lista;

        try {

            lsLista = new ArrayList<Object>();

            lsLista.add("Pedidos");


            if (ROTINA.equals("CONSULTAGERAL")) {

                PedidoCabMbDAO dao = new PedidoCabMbDAO();

                dao.open();

                lista = dao.getPedidosPosSincronizacao();

                dao.close();

                if (lista.size() != 0) lsLista.addAll(lista);


            }

            if (ROTINA.equals("AGENDAMENTO")){

                List<Agendamento> agendamentos;

                AgendamentoDAO daoAge = new AgendamentoDAO();

                daoAge.open();

                agendamentos = daoAge.getAllAvulsoToSinc();

                daoAge.close();

                if (agendamentos.size() != 0) lsLista.addAll(agendamentos);


            }


            if (ROTINA.equals("CONSULTA") || ROTINA.equals("CONSULTANEGOCIACAO")){

                PedidoCabMbDAO dao = new PedidoCabMbDAO();

                dao.open();

                if (CODIGO.equals("")) {

                    lista = dao.getPedidosBySincronizacao();

                    dao.close();

                    if (lista.size() != 0) lsLista.addAll(lista);

                } else {

                    lista = dao.getPedidosByCliente(CODIGO, LOJA);

                    dao.close();

                    if (lista.size() != 0) lsLista.addAll(lista);

                }
            }



            if (lsLista.size() == 1) {

                lsLista.add(new NoData("Nenhum Pedido Encontrado !!"));

            }

            adapter = new Adapter(PedidosActivity.this, lsLista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e) {

            Toast.makeText(this, "Erro Na Carga: " + e.getMessage(), Toast.LENGTH_LONG).show();

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


                lsParametros.add(new parametros(0, st.getCargaStatus(), tempo));

                if (st.getPEDIDO().equals("N")) {

                    tempo = "Até o Dia:" + st.getCARDATA() + " Até As: " + st.getCARHORA();

                } else {

                    tempo = "";

                }

                lsParametros.add(new parametros(1, st.getPedidoStatus(), tempo));

            } else {

                lsParametros.add(new parametros(0, "SEM INFORMAÇÃO", ""));
            }

            dao.close();

            parametrosadapter = new parametrosAdapter(PedidosActivity.this, R.layout.conexoes_opcoes, lsParametros);

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

            spParamentros.setSelection(IndiceConexao);

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

    }


    private void toast(String msg){


        Toast.makeText(PedidosActivity.this, msg , Toast.LENGTH_LONG).show();

    }

    private Handler mHandlerTrasmissao=new Handler(){

        @Override
        public void handleMessage(Message msg){

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(PedidosActivity.this, msg.getData().getString("CMSGERRO"), "Acessando Servidores.Aguarde !!", false, true);
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



                //ERRO 10 - Dispositivo Não Cadastrado

                if ((msg.getData().getString("CERRO").equals("010"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

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

                        Toast.makeText(PedidosActivity.this, "Erro: " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();

                    }

                    LoadPedidos();

                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(PedidosActivity.this, "Erro: ???" + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            } catch (Exception E) {

                Log.d(LOG, "MENSAGEM", E);

                toast("Erro Handler: " + E.getMessage());

            }
        }




    };

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    try {

                        verrede.processa();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }


            }
            catch (Exception E){

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(PedidosActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };

    private Handler mHandlerRede = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    try {

                        verrede.processa();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }


            }
            catch (Exception E){

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(PedidosActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onReceiverNotification(NotificationPedido notificationPedido){

        LoadPedidos();


    }

    private Handler mHandlerAtualizaPedido = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                toast("NAO CONTEM AS CHAVES..");

                return;

            }

            if (msg.getData().getString("CERRO").equals("---")) {

                dialog = ProgressDialog.show(PedidosActivity.this, "RECALCULANDO PEDIDO", "Processando !!", false, true);
                dialog.setCancelable(false);
                dialog.show();
            }


            if ((msg.getData().getString("CERRO").equals("FECT"))) {

                if ((dialog != null)) {

                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }
                }


            }


            if ((msg.getData().getString("CERRO").equals("FECC"))) {

                if ((dialog != null)) {

                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }
                }

                try {

                    LoadPedidos();

                } catch (Exception e) {

                    toast(e.getMessage());

                }


            }


        }

    };


    //INNER CLASS


    private class AtualizaPedidoThread extends Thread {

        private Handler mHandler;

        private Bundle params = new Bundle();

        private int position;

        private String nropedido;

        public AtualizaPedidoThread(Handler handler,int position, String nropedido) {

            super();

            mHandler       = handler;

            this.position  = position;

            this.nropedido = nropedido;

        }

        @Override

        public void run() {

            try {

                params.putString("CERRO"   , "---");

                params.putString("CMSGERRO", "");

                sendmsg(params);

                PedidoBusinessV10 pedido = new PedidoBusinessV10();

                pedido.load(nropedido,true);

                pedido.save("","");

                params.putString("CERRO"   , "FECC");

                params.putString("CMSGERRO", "");

                sendmsg(params);


            } catch (Exception e){

                params.putString("CERRO"   , "FECT");

                params.putString("CMSGERRO", e.getMessage());

                sendmsg(params);

            }


        }


        public void sendmsg(Bundle value) {

            if (value != null) {
                Message msgObj = mHandler.obtainMessage();
                msgObj.setData(value);
                mHandler.sendMessage(msgObj);
            }

        }

    }


    private void transmitir() throws  Exception {


        if (!(conexaoadapter.connected)){

            toast("Problemas Na Conexão !!");

            return;

        }

        if (ROTINA.equals("AGENDAMENTO")){

            AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerTrasmissao, getBaseContext(), App.user, "PUTAGENDAMENTOS", "PUTAGENDAMENTOS", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_AGENDAMENTO_JUSTIFICATICAS, config, null, -1);

            acessoWeb.setCODIGO(CODIGO);

            acessoWeb.setLOJA(LOJA);

            acessoWeb.start();


        } else {

            AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerTrasmissao, getBaseContext(), App.user, "PUTSALESORDERMB", "PUTSALESORDERMB", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_PEDIDOS, config, null, -1);

            acessoWeb.setCODIGO(CODIGO);

            acessoWeb.setLOJA(LOJA);

            acessoWeb.start();
        }
    }

    private class callbackTransmissao extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro       = result.getPropertyAsString("CERRO");
            String cmsgerro    = result.getPropertyAsString("CMSGERRO");
            String nro         = "";
            String cprotheus   = "";
            String cprotheusb   = "";
            if (!cerro.equals("099")){

                nro            = result.getPropertyAsString("NRO");
                cprotheus      = result.getPropertyAsString("CPROTHEUS");
                cprotheusb     = result.getPropertyAsString("CPROTHEUSB");
            }



            toast(cmsgerro);

            if (cerro.equals("000")){

                PedidoCabMbDAO dao = new PedidoCabMbDAO();

                dao.open();

                PedidoCabMb cab = dao.seek(new String[] {nro.trim()});

                if (!(cab == null)){

                    cab.setSTATUS("4");

                    cab.setMENSAGEM("Aguardando retorno...");

                    dao.Update(cab);

                } else {

                    toast("Não Encontrado Pedido "+nro);

                }

                dao.close();

            } else {

                if  (!(cerro.equals("099"))) {

                    PedidoCabMbDAO dao = new PedidoCabMbDAO();

                    dao.open();

                    PedidoCabMb cab = dao.seek(new String[] {nro.trim()});

                    if (!(cab == null)) {

                        cab.setSTATUS("3"); //FORÇA NOVA TRANSMISSÃO

                        cab.setMENSAGEM(cmsgerro);

                        dao.Update(cab);

                    } else {

                        toast("Não Encontrado Pedido " + nro);

                    }

                    dao.close();
                }

            }

            LoadPedidos();

        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

        }
    }

    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        Context context;

        final int ITEM_VIEW_CABEC       = 0;
        final int ITEM_VIEW_DETALHE     = 1;
        final int ITEM_VIEW_AGENDAMENTO = 2;
        final int ITEM_VIEW_NO_DATA     = 3;
        final int ITEM_VIEW_COUNT       = 4;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos = pObjects;
            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof PedidoCabMb || obj instanceof Agendamento ) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total De Itens: " + String.valueOf(qtd);

            return retorno;
        }

        public  void refresh(PedidoCabMb obj,int pos){

            this.lsObjetos.set(pos,obj);

            notifyDataSetChanged();


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

            if (lsObjetos.get(position) instanceof PedidoCabMb) {

                retorno = ITEM_VIEW_DETALHE;

            }

            if (lsObjetos.get(position) instanceof Agendamento) {

                retorno = ITEM_VIEW_AGENDAMENTO;

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

        public void deleteitem(int position) throws Exception{

            PedidoCabMbDAO dao = new PedidoCabMbDAO();

            dao.open();

            List<String> pedidos = dao.getPedidosByClienteAndDate(((PedidoCabMb) lsObjetos.get(position)).getCODIGOFAT(),((PedidoCabMb) lsObjetos.get(position)).getLOJAFAT(),((PedidoCabMb) lsObjetos.get(position)).getEMISSAO());

            dao.DeletePedido(((PedidoCabMb) lsObjetos.get(position)).getNRO(),pedidos);

            dao.close();

            this.lsObjetos.remove(position);

            notifyDataSetChanged();

            return;

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


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.pedidomb_cabec_lista, null);

                            break;

                        case ITEM_VIEW_AGENDAMENTO:

                            convertView = inflater.inflate(R.layout.agenda_planejamento_row, null);

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

                    case ITEM_VIEW_DETALHE: {

                        final PedidoCabMb obj = (PedidoCabMb) lsObjetos.get(pos);

                        ImageButton bt_email_002        = (ImageButton) convertView.findViewById(R.id.bt_email_002);

                        TextView  txt_email_002         = (TextView) convertView.findViewById(R.id.txt_email_002);

                        ImageView im_email_check        = (ImageView) convertView.findViewById(R.id.im_email_check_002);

                        ImageView item_trash_002        = (ImageView) convertView.findViewById(R.id.item_trash_002);

                        ImageView item_pedido_002       = (ImageView) convertView.findViewById(R.id.item_pedido_002);

                        ImageView item_refresh_002      = (ImageView) convertView.findViewById(R.id.item_refresh_002);

                        ImageView item_erros_002        = (ImageView) convertView.findViewById(R.id.item_erros_002);

                        TextView txt_upload_002         = (TextView) convertView.findViewById(R.id.txt_upload_002);
                        TextView txt_nro_002            = (TextView) convertView.findViewById(R.id.txt_nro_002);
                        TextView txt_protheus_002       = (TextView) convertView.findViewById(R.id.txt_protheus_002);
                        TextView txt_status_002         = (TextView) convertView.findViewById(R.id.txt_status_002);
                        TextView txt_mensagem_002       = (TextView) convertView.findViewById(R.id.txt_mensagem_002);
                        TextView txt_cliente_002        = (TextView) convertView.findViewById(R.id.txt_cliente_002);
                        TextView txt_cnpj_002           = (TextView) convertView.findViewById(R.id.txt_cnpj_002);
                        TextView txt_ie_002             = (TextView) convertView.findViewById(R.id.txt_ie_002);
                        TextView txt_telefone_002       = (TextView) convertView.findViewById(R.id.txt_telefone_002);
                        TextView txt_cliente_retira_002 = (TextView) convertView.findViewById(R.id.txt_cliente_retira_002);
                        TextView txt_emissao_002        = (TextView) convertView.findViewById(R.id.txt_emissao_002);
                        TextView txt_entrega_002        = (TextView) convertView.findViewById(R.id.txt_entrega_002);
                        TextView txt_tabpreco_002       = (TextView) convertView.findViewById(R.id.txt_tabpreco_002);
                        TextView txt_condpagto_002      = (TextView) convertView.findViewById(R.id.txt_condpagto_002);
                        TextView txt_total_pedido_002   = (TextView) convertView.findViewById(R.id.txt_total_pedido_002);
                        TextView txt_clienteentrega_002 = (TextView) convertView.findViewById(R.id.txt_clienteentrega_002);
                        TextView txt_pedcli_002         = (TextView) convertView.findViewById(R.id.txt_pedido_cliente_002);
                        TextView txt_obsped_002         = (TextView) convertView.findViewById(R.id.txt_obsped_002);
                        TextView txt_obsnf_002          = (TextView) convertView.findViewById(R.id.txt_obsnf_002);


                        if (obj.getSTATUS().equals("3")) {

                            bt_email_002.setVisibility(View.VISIBLE);

                            bt_email_002.setOnClickListener(new ClickEmail(PedidosActivity.this, obj, pos));

                            if (obj.getCCOPIAPEDIDO().equals("S")) {

                                im_email_check.setVisibility(View.VISIBLE);

                                txt_email_002.setText(obj.getCEMAILCOPIAPEDIDO());

                            } else {

                                im_email_check.setVisibility(View.INVISIBLE);

                                txt_email_002.setText("");

                            }

                        }else {

                            bt_email_002.setVisibility(View.INVISIBLE);

                            im_email_check.setVisibility(View.INVISIBLE);

                            txt_email_002.setText("");

                        }

                        if (  (obj.getSTATUS().equals("98") || obj.getSTATUS().equals("6")  || obj.getSTATUS().equals("0") ) ) {

                            item_refresh_002.setVisibility(View.VISIBLE);

                            if  ( (obj.getSTATUS().equals("98") || obj.getSTATUS().equals("6") ) ) item_erros_002.setVisibility(View.VISIBLE);

                            item_refresh_002.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {

                                        final Dialog dialog = new Dialog(PedidosActivity.this);

                                        if (obj.getSTATUS().equals("0")){

                                            dialog.setContentView(R.layout.dlgtrocastatus);

                                            dialog.setTitle("TROCA DE STATUS");

                                            final Button confirmar = (Button) dialog.findViewById(R.id.btn_040_ok);
                                            final Button cancelar = (Button) dialog.findViewById(R.id.btn_040_can);
                                            final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);


                                            tvtexto1.setText("STATUS MUDARÁ DE AUTOMÁTICO PARA A TRANSMITIR");


                                            cancelar.setOnClickListener(new View.OnClickListener() {

                                                public void onClick(View v) {

                                                    //finaliza o dialog
                                                    dialog.dismiss();

                                                }
                                            });

                                            confirmar.setOnClickListener(new View.OnClickListener() {

                                                public void onClick(View v) {

                                                    try

                                                    {

                                                        PedidoCabMbDAO dao = new PedidoCabMbDAO();

                                                        dao.open();

                                                        PedidoCabMb cab = dao.seek(new String[]{((PedidoCabMb) lsObjetos.get(position)).getNRO()});

                                                        cab.setSTATUS("3");

                                                        dao.Update(cab);

                                                        dao.close();

                                                        adapter.refresh(cab,pos);


                                                    } catch (Exception e) {

                                                        StringWriter sw = new StringWriter();

                                                        e.printStackTrace(new PrintWriter(sw));

                                                        String exceptionAsString = sw.toString();

                                                        Log.i("SAV", exceptionAsString);

                                                        toast(e.getMessage());
                                                    }

                                                    //finaliza o dialog
                                                    dialog.dismiss();


                                                }

                                            });


                                        } else {
                                            dialog.setContentView(R.layout.dlglibped);

                                            dialog.setTitle("RECUPERAÇÃO DE PEDIDO");

                                            final Button confirmar = (Button) dialog.findViewById(R.id.btn_040_ok);
                                            final Button cancelar = (Button) dialog.findViewById(R.id.btn_040_can);
                                            final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);
                                            final TextView tvtexto2 = (TextView) dialog.findViewById(R.id.txt_040_texto2);

                                            tvtexto1.setText("CONFIRMA A RECUPERAÇÃO DO PEDIDO:");
                                            tvtexto2.setText(obj.getNRO() + "-" + obj.get_ClienteFatRazao());

                                            cancelar.setOnClickListener(new View.OnClickListener() {

                                                public void onClick(View v) {

                                                    //finaliza o dialog
                                                    dialog.dismiss();

                                                }
                                            });

                                            confirmar.setOnClickListener(new View.OnClickListener() {

                                                public void onClick(View v) {

                                                    try

                                                    {

                                                        PedidoCabMbDAO dao = new PedidoCabMbDAO();

                                                        dao.open();

                                                        PedidoCabMb cab = dao.seek(new String[]{((PedidoCabMb) lsObjetos.get(position)).getNRO()});

                                                        cab.setSTATUS("3");

                                                        dao.Update(cab);

                                                        dao.close();

                                                        AtualizaPedidoThread atualizar = new AtualizaPedidoThread(mHandlerAtualizaPedido, pos, obj.getNRO());

                                                        atualizar.start();


                                                    } catch (Exception e) {

                                                        StringWriter sw = new StringWriter();

                                                        e.printStackTrace(new PrintWriter(sw));

                                                        String exceptionAsString = sw.toString();

                                                        Log.i("SAV", exceptionAsString);

                                                        toast(e.getMessage());
                                                    }

                                                    //finaliza o dialog
                                                    dialog.dismiss();


                                                }

                                            });

                                        }

                                        dialog.show();


                                    } catch (Exception e) {


                                        StringWriter sw = new StringWriter();
                                        e.printStackTrace(new PrintWriter(sw));
                                        String exceptionAsString = sw.toString();

                                        Log.i("SAV",exceptionAsString);

                                        toast(e.getMessage());
                                    }

                                }
                            });

                            item_erros_002.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getApplicationContext(), showErrosActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Bundle params = new Bundle();
                                    params.putString("PEDIDO", obj.getNRO());
                                    intent.putExtras(params);
                                    getApplicationContext().startActivity(intent);


                                }
                            });

                        } else {


                            item_refresh_002.setVisibility(View.GONE);

                            item_erros_002.setVisibility(View.GONE);
                        }

                        item_pedido_002.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getApplicationContext(), PedidoV10Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();
                                params.putString("CODIGO", CODIGO);
                                params.putString("LOJA", LOJA);
                                if ((obj.getSTATUS().equals("1")) || (obj.getSTATUS().equals("2")) || (obj.getSTATUS().equals("3"))) {
                                    params.putString("OPERACAO", "ALTERACAO");
                                } else {
                                    params.putString("OPERACAO", "VIEW");
                                }
                                params.putString("NROPEDIDO", obj.getNRO());
                                params.putString("IDAGE",IDAGE);
                                intent.putExtras(params);
                                getApplicationContext().startActivity(intent);

                            }
                        });

                        if ( (obj.getSTATUS().equals("2")) || (obj.getSTATUS().equals("3")) || (obj.getSTATUS().equals("6") )  ){

                            item_trash_002.setVisibility(View.VISIBLE);

                            item_trash_002.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {

                                        final Dialog dialog = new Dialog(PedidosActivity.this);

                                        dialog.setContentView(R.layout.dlglibped);

                                        dialog.setTitle("EXCLUSÃO DE PEDIDO");

                                        final Button confirmar = (Button) dialog.findViewById(R.id.btn_040_ok);
                                        final Button cancelar = (Button) dialog.findViewById(R.id.btn_040_can);
                                        final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);
                                        final TextView tvtexto2 = (TextView) dialog.findViewById(R.id.txt_040_texto2);

                                        tvtexto1.setText("CONFIRMA A EXCLUSÃO DO PEDIDO:");
                                        tvtexto2.setText(obj.getNRO()+"-"+obj.get_ClienteFatRazao());

                                        cancelar.setOnClickListener(new View.OnClickListener() {

                                            public void onClick(View v) {

                                                //finaliza o dialog
                                                dialog.dismiss();

                                            }
                                        });

                                        confirmar.setOnClickListener(new View.OnClickListener() {

                                            public void onClick(View v) {

                                                try

                                                {

                                                    adapter.deleteitem(pos);


                                                } catch (Exception e) {

                                                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                                                }


                                                //finaliza o dialog
                                                dialog.dismiss();


                                            }

                                        });


                                        dialog.show();


                                    } catch (Exception e) {

                                        StringWriter sw = new StringWriter();
                                        e.printStackTrace(new PrintWriter(sw));
                                        String exceptionAsString = sw.toString();

                                        Log.i("SAV",exceptionAsString);

                                        toast(e.getMessage());

                                    }

                                }
                            });

                        } else {

                            item_trash_002.setVisibility(View.INVISIBLE);

                        }

                        txt_upload_002.setText(obj.getDTTRANS() + " " + obj.getHOTRANS());
                        txt_nro_002.setText(obj.getNRO()+"-"+obj.get_Tipo());
                        txt_protheus_002.setText(obj.getCPROTHEUS()+"/"+obj.getCPROTHEUSB());
                        txt_status_002.setText(obj.get_Status());
                        txt_mensagem_002.setText(obj.getMENSAGEM());
                        txt_cliente_002.setText(obj.getCODIGOFAT()+"-"+obj.getLOJAFAT()+" "+obj.get_ClienteFatRazao());
                        txt_cnpj_002.setText(obj.get_ClienteFatCnpj());
                        txt_ie_002.setText(obj.get_ClienteFatIE());
                        txt_telefone_002.setText("");
                        txt_clienteentrega_002.setText(obj.getCODIGOENT()+"-"+obj.getLOJAENT()+" "+obj.get_ClienteEntRazao());
                        txt_emissao_002.setText(obj.getEMISSAO());
                        txt_entrega_002.setText(obj.getENTREGA());
                        txt_tabpreco_002.setText(obj.get_TabPreco());
                        txt_condpagto_002.setText(obj.get_Cond());
                        txt_total_pedido_002.setText(format_02.format(obj.getTOTALPEDIDO()+obj.getVLRBONIFICADO()));
                        txt_cliente_retira_002.setText(obj.get_Retira());
                        txt_pedcli_002.setText(obj.getPEDCLIENTE());
                        txt_obsped_002.setText(obj.getOBSPED());
                        txt_obsnf_002.setText(obj.getOBSNF());

                        break;
                    }

                    case ITEM_VIEW_AGENDAMENTO: {

                        final Agendamento obj = (Agendamento) lsObjetos.get(pos);

                        ImageButton bt_alteracao_402 = (ImageButton) convertView.findViewById(R.id.bt_alteracao_402);


                        TextView txt_id_402           = (TextView) convertView.findViewById(R.id.txt_id_402);
                        TextView txt_data_402          = (TextView) convertView.findViewById(R.id.txt_data_402);
                        TextView txt_hora_402          = (TextView) convertView.findViewById(R.id.txt_hora_402);
                        TextView txt_tipo_402          = (TextView) convertView.findViewById(R.id.txt_tipo_402);
                        TextView txt_situacao_402      = (TextView) convertView.findViewById(R.id.txt_situacao_402);
                        TextView txt_cliente_402       = (TextView) convertView.findViewById(R.id.txt_cliente_402);
                        TextView txt_pedido_402        = (TextView) convertView.findViewById(R.id.txt_pedido_402);
                        TextView txt_motivo_402        = (TextView) convertView.findViewById(R.id.txt_motivo_402);
                        TextView txt_obs_402           = (TextView) convertView.findViewById(R.id.txt_obs_402);

                        bt_alteracao_402.setVisibility(View.GONE);

                        txt_id_402.setText("ID: "+obj.getID());
                        txt_data_402.setText("DATA: "+App.aaaammddToddmmaa(obj.getDATA()));
                        txt_hora_402.setText("HORA: "+obj.getHORA());
                        txt_tipo_402.setText("TIPO: "+obj.get_TIPO());
                        txt_situacao_402.setText("SIT.: "+obj.get_Situacao());
                        txt_pedido_402.setText("PEDIDO: "+obj.getMOBILE());
                        txt_motivo_402.setText("MOTIVO: "+obj._motivo());
                        txt_cliente_402.setText("CLIENTE: "+obj.getCLIENTE()+"-"+obj.getLOJA()+" "+obj.get_RAZAO());
                        txt_obs_402.setText("OBS: "+obj.getOBS());

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

                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                Log.i("SAV",exceptionAsString);

                toast(e.getMessage());

            }

            return convertView;

        }


        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }

    }


    private class ClickEmail implements View.OnClickListener {

        private Context context = null;

        private PedidoCabMb obj;

        private int     pos;


        public ClickEmail(Context context,PedidoCabMb obj,int pos) {

            this.context = context;

            this.obj     = obj;

            this.pos     = pos;

        }

        @Override
        public void onClick(View v) {

            Cliente cliente = null;

            final ClienteDAO clientedao = null;

            try {

                ClienteDAO dao = new ClienteDAO();

                dao.open();

                cliente = dao.seek(new String[] {obj.getCODIGOFAT(),obj.getLOJAFAT()});

                dao.close();

                if (cliente == null){

                    toast("Cliente Não Encontrado !! "+obj.get_ClienteFatRazao());

                    return;

                }

            } catch (Exception e) {

                toast(e.getMessage());

                return;

            }

            final Dialog dialog = new Dialog(context);

            dialog.setContentView(R.layout.dlemail);

            dialog.setTitle("ENVIAR PEDIDO POR E-MAIL");

            final TextView titulo   = (TextView) dialog.findViewById(R.id.titulo_121);

            final CheckBox cb_envia_email_121 = (CheckBox) dialog.findViewById(R.id.cb_enviar_email_121);

            final EditText endereco = (EditText) dialog.findViewById(R.id.email_121);

            titulo.setText("E-MAIL Será Enviado Na Sincronização.");

            if (obj.getCCOPIAPEDIDO().equals("S")) {

                cb_envia_email_121.setChecked(true);

            }
            else {

                cb_envia_email_121.setChecked(false);

            }
            if(obj.getCEMAILCOPIAPEDIDO().trim().isEmpty()){

                obj.setCEMAILCOPIAPEDIDO(cliente.getEMAILTROCA());

            }

            endereco.setText(obj.getCEMAILCOPIAPEDIDO());

            final Button confirmar = (Button) dialog.findViewById(R.id.bt_confirma_121);

            final Button cancelar = (Button) dialog.findViewById(R.id.bt_cancela_121);

            cancelar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            confirmar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    if (endereco.getText().toString().trim().isEmpty()) {


                        toast("Digite Um E-Mail Por Favor !");

                        return;

                    }

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            View view = dialog.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }

                            dialog.dismiss();

                        }

                    }

                    if (cb_envia_email_121.isChecked()) {

                        obj.setCCOPIAPEDIDO("S");

                    } else {

                        obj.setCCOPIAPEDIDO("N");

                    }

                    obj.setCEMAILCOPIAPEDIDO(endereco.getText().toString());



                    if (!endereco.getText().toString().trim().isEmpty()){

                        try {

                            {

                                PedidoCabMbDAO dao = new PedidoCabMbDAO();

                                dao.open();

                                dao.Update(obj);

                                dao.close();

                            }
                            {
                                ClienteDAO dao = new ClienteDAO();

                                dao.open();

                                Cliente cliente = dao.seek(new String[]{obj.getCODIGOFAT(), obj.getLOJAFAT()});

                                if (cliente == null) {

                                    toast("Cliente Não Encontrado !! " + obj.get_ClienteFatRazao());

                                    dao.close();

                                    return;

                                }

                                cliente.setEMAILTROCA(endereco.getText().toString().trim());

                                dao.Update(cliente);

                                dao.close();

                                adapter.refresh(obj,pos);
                            }
                        } catch (Exception e) {

                            toast(e.getMessage());

                            return;

                        }


                    }


                }

            });

            dialog.show();

        }

    }

    private class Tasking extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

        }

        @Override
        public void processaArray() throws Exception {

            DecimalFormat format = new DecimalFormat("000");

            SoapObject registro;

            Boolean lOK = true;

            List<Task> lsTask = new ArrayList<>();

            if (this.result == null) {

                return;

            }


            TaskDAO taskdao = new TaskDAO();

            taskdao.open();

            taskdao.DeleteAll();

            for (int x = 0; x < this.result.getPropertyCount(); x++) {

                registro = (SoapObject) this.result.getProperty(x);

                if (registro.getProperty("CERRO").toString().equals("000")) {

                    taskdao.insert(new Task(registro.getProperty("CCODIGO").toString(), registro.getProperty("CDESCRICAO").toString()));


                } else {

                    lOK = false;
                }

            }

            lsTask = taskdao.getAll();

            taskdao.close();

            OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();

            ocorrenciaDAO.open();


            if (lOK) {

                ocorrenciaDAO.DeleteAll();

                for( double x = 0; x < lsTask.size();x++){
                    ocorrenciaDAO.insert(new Ocorrencia(format.format(x),lsTask.get((int) x).getCODIGO(),
                            lsTask.get((int) x).getDESCRI(),
                            "","","","","","0","",""));
                }


            }

            ocorrenciaDAO.close();


        }
    }

    private class Processando extends HandleSoap {

        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro       = result.getPropertyAsString("CERRO");
            String cmsgerro    = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                cmsgerro = "Conexão Ativa.";


            } else {
                //Altera algumas mensagens
                if (cmsgerro.contains("failed to connect")) {

                    cmsgerro = "Falha de Conexão.";

                }
            }



        }

        @Override
        public void processaArray() throws Exception {

            if (this.result == null) {

                return;

            }
        }
    }

    private class conexaoAdapter extends ArrayAdapter {

        private int escolha = -1;

        private String status = "";

        private Boolean visible = false;

        private boolean connected = false;

        public conexaoAdapter(Context context, int textViewResourceId, List<Config> objects) {

            super(context, textViewResourceId, objects);

        }

        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }

        public int getEscolha() {
            return escolha;
        }

        public void setStatusRede(Boolean visible,String msg, Boolean connected){

            if (!(connected == null)){

                this.connected = connected;

            }

            this.visible = visible;
            this.status  = msg;
            notifyDataSetChanged();

        }

        public boolean getConnected(){

            return this.connected;
        }
        public View getOpcoesView(final int position, View convertView, ViewGroup parent) {

            // Infla layout customizado
            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.conexoes_opcoes, parent, false);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_15);

            tvOpcao.setText(conexoes.get(position).getDESCRICAO());

            tvOpcao.setTextColor(Color.rgb(75, 180, 225));

            ImageView img = (ImageView) layout.findViewById(R.id.img_15);

            if (position == 0) {

                img.setImageResource(R.drawable.wifi);

            } else {

                img.setImageResource(R.drawable.nuvem_ok);

            }

            return layout;
        }


        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            // Infla layout customizado
            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.conexoes_escolha, parent, false);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_16);
            TextView tvRede  = (TextView) layout.findViewById(R.id.tvAtualizando_16);
            ProgressBar bpProcesso = (ProgressBar) layout.findViewById(R.id.img_atualizando_16);

            if (escolha == -1) {

                tvOpcao.setText("Escolha Uma Conexão");
                tvRede.setText("");
                if (visible) {
                    bpProcesso.setVisibility(View.VISIBLE);
                } else {
                    bpProcesso.setVisibility(View.INVISIBLE);
                }

            } else {

                tvOpcao.setText(conexoes.get(escolha).getDESCRICAO());
                tvRede.setText(this.status);
                if (visible) {
                    bpProcesso.setVisibility(View.VISIBLE);
                } else {
                    bpProcesso.setVisibility(View.INVISIBLE);
                }

            }


            tvOpcao.setTextColor(Color.rgb(75, 180, 225));

            ImageView img = (ImageView) layout.findViewById(R.id.img_16);

            if (position == 0) {

                img.setImageResource(R.drawable.wifi);

            } else {

                img.setImageResource(R.drawable.nuvem_ok);

            }

            // Setting Special atrributes for 1st element
            if (position == escolha) {

                tvOpcao.setTextSize(20f);

                tvOpcao.setTextColor(Color.BLACK);
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
    }

    private class parametrosAdapter extends ArrayAdapter {

        private int escolha = 0;

        private List<parametros> lista;

        public parametrosAdapter(Context context, int textViewResourceId, List<parametros> objects) {

            super(context, textViewResourceId, objects);

            lista = objects;

        }


        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }



        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            parametros obj = (parametros) lista.get(position);

            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.conexoes_escolha, parent, false);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_16);

            TextView tvRede  = (TextView) layout.findViewById(R.id.tvAtualizando_16);

            ProgressBar bpProcesso = (ProgressBar) layout.findViewById(R.id.img_atualizando_16);

            tvOpcao.setText(obj.getLinha01());

            tvRede.setText(obj.getLinha02());

            bpProcesso.setVisibility(View.INVISIBLE);

            tvOpcao.setTextColor(Color.rgb(75, 180, 225));

            ImageView img = (ImageView) layout.findViewById(R.id.img_16);

            if (position == 0) {

                img.setImageResource(R.drawable.ic_action_down_cloud_i);

            } else {

                img.setImageResource(R.drawable.ic_action_order_sales_i);

            }

            if (position == escolha) {

                tvOpcao.setTextSize(20f);

                tvOpcao.setTextColor(Color.BLACK);
            }

            return layout;
        }

        // Mostra as Opções
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            return getEscolhaView(position, convertView, parent);

        }

        // Mostra o item selecionado
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getEscolhaView(position, convertView, parent);

        }
    }

    private class verRede extends HandleSoap {


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

                //LoadStatus();

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

    private class Carga01_processa extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }
        }
        @Override
        public void processaArray() throws Exception {

            SoapObject registro;

            Boolean lOK = true;


            if (this.result == null) {

                return;

            }


            for (int x = 0; x < this.result.getPropertyCount(); x++) {

                registro = (SoapObject) this.result.getProperty(x);

                if (registro.getProperty("CERRO").toString().equals("000")) {


                    try{

                        String codocorrencia = registro.getProperty("CCODIGO").toString();
                        String filename      = registro.getProperty("CNOMEARQ").toString();




                    } catch (Exception e){

                        Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(getBaseContext(),registro.getProperty("CMSGERRO").toString(),Toast.LENGTH_SHORT).show();
                }

            }


        }
    }

    private class parametros {

        private  int figura;
        private  String linha01;
        private  String linha02;

        public parametros(int figura, String linha01, String linha02) {
            this.figura  = figura;
            this.linha01 = linha01;
            this.linha02 = linha02;
        }

        public int getFigura() {

            return figura;
        }

        public void setFigura(int figura) {
            this.figura = figura;
        }

        public String getLinha01() {
            return linha01;
        }

        public void setLinha01(String linha01) {
            this.linha01 = linha01;
        }

        public String getLinha02() {
            return linha02;
        }

        public void setLinha02(String linha02) {
            this.linha02 = linha02;
        }


    }
}
