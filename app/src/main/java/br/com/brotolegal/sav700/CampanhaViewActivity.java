package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import br.com.brotolegal.sav700.Campanha.Adaptadores.Adapter;
import br.com.brotolegal.sav700.Campanha.Entities.ParametrosCampanha;
import br.com.brotolegal.sav700.Campanha.Entities.PeriodoCampanha;
import br.com.brotolegal.sav700.VerificaWeb.ConexaoAdapter;
import br.com.brotolegal.sav700.VerificaWeb.Parametros;
import br.com.brotolegal.sav700.VerificaWeb.ParametrosAdapter;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.CampanhaDAO;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.OcorrenciaDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.TaskDAO;
import br.com.brotolegal.savdatabase.entities.Campanha_fast;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Ocorrencia;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Task;
import br.com.brotolegal.savdatabase.eventbus.NotificationCarga;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;
import br.com.brotolegal.savdatabase.internet.SoapServEnv;
import br.com.brotolegal.savdatabase.regrasdenegocio.ObjProcesso;
import br.com.brotolegal.savdatabase.wsentities.TASK;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;

public class CampanhaViewActivity extends AppCompatActivity {

    private String LOG = "CAMPANHA" ;

    private Spinner spConexao;

    private List<Config> conexoes;

    private ConexaoAdapter conexaoadapter;

    private defaultAdapter PeriodosAdapter;

    private Adapter adapter;

    private String StatusRede = "Status Não Verificado.";

    private Config config;

    private Toolbar toolbar;

    private ListView lv;

    private List<Object> lsLista;

    private List<ParametrosCampanha> lsparametrosCampanha;

    private int CampanhaAtiva         = -1;

    private Dialog dialog;

    private CallBack callBack;

    Spinner spPeriodo;

    List<String[]> lsPeriodos = new ArrayList<>();

    TextView UltimaAtualizacao;

    ListView Campanha;

    private  int     Indice = 2;
    private  String  PeriodoInicial = "201803";
    private  String  PeriodoFinal   = "201804";

    String CodCliente = "";
    String LojCliente = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha_view);
        try {

            toolbar = (Toolbar) findViewById(R.id.tb_campanha_497);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Acompanhamento Das Campanhas");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            spConexao         = (Spinner) findViewById(R.id.spConexao_497);

            UltimaAtualizacao = (TextView) findViewById(R.id.txt_atualizacao_497);

            spPeriodo         = (Spinner) findViewById(R.id.spPeriodos_497);

            UltimaAtualizacao.setText("");

            lv = (ListView) findViewById(R.id.lvCampanha_497);

            loadCampanhas();

            verWeb();

            lsPeriodos = new ArrayList<>();

            lsPeriodos.addAll(lsparametrosCampanha.get(0).getOpcoes());

            PeriodosAdapter = new defaultAdapter(CampanhaViewActivity.this,R.layout.choice_default_row, lsPeriodos, "");

            spPeriodo.setAdapter(PeriodosAdapter);

            spPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    PeriodosAdapter.setEscolha(position);

                    Object lixo = spPeriodo.getSelectedItem();

                    if (lixo != null){

                        Indice         = position;
                        PeriodoInicial = lsparametrosCampanha.get(0).getPeriodo(position).getPeriodoInicial();
                        PeriodoFinal   = lsparametrosCampanha.get(0).getPeriodo(position).getPeriodoFinal();

                        adapter.setPeriodoInicial(PeriodoInicial);

                        adapter.setPeriodoFinal(PeriodoFinal);

                        SharedPreferences prefs = getSharedPreferences("CAMPANHA_2018", MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefs.edit();

                        editor.clear();

                        editor.putInt("Indice"           , Indice);
                        editor.putString("PeriodoInicial", PeriodoInicial);
                        editor.putString("PeriodoFinal"  , PeriodoFinal);

                        editor.commit();

                        if (lsLista != null){

                            loadCampanhas();

                        }

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });



            toolbar.inflateMenu(R.menu.menu_campanha_main);


        } catch (Exception e){

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            Log.i("SAV",exceptionAsString);

            toast(e.getMessage());

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_campanha_main, menu);

        MenuItem iFechada  = menu.findItem(R.id.action_campanha_fechada_sincronizar);
        MenuItem iAberta   = menu.findItem(R.id.action_campanha_aberta_sincronizar);
        iFechada.setVisible(false);
        iAberta.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_campanha_fechada_sincronizar:

                try {

                    OcorrenciaDAO dao = new OcorrenciaDAO();

                    dao.open();

                    Ocorrencia ocorrencia = dao.seekByCodigo(new String[] {"000018"});

                    dao.close();

                    if (ocorrencia != null) {

                        ResetOcorrencia("000018");

                        carga01(ocorrencia);

                    } else {

                        Toast("Não Encontrada Ocorrência 000018 - Campanha Fechada");

                    }

                } catch (Exception e){

                    Toast(e.getMessage());

                }
                break;

            case R.id.action_campanha_aberta_sincronizar:

                finish();

                break;


            case R.id.action_campanha_voltar:

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {

        try {

            SharedPreferences prefs = getSharedPreferences("CAMPANHA_2018", MODE_PRIVATE);

            Indice         = prefs.getInt("Indice", Indice);
            PeriodoInicial = prefs.getString("PeriodoInicial",PeriodoInicial);
            PeriodoFinal   = prefs.getString("PeriodoFinal"  ,PeriodoFinal);


            spPeriodo.setSelection(Indice);



            //if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);


        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


        super.onResume();
    }

    @Override
    public void finish() {

        SharedPreferences prefs = getSharedPreferences("CAMPANHA_2018", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();

        editor.putInt("Indice"           , Indice);
        editor.putString("PeriodoInicial", PeriodoInicial);
        editor.putString("PeriodoFinal"  , PeriodoFinal);

        editor.commit();

        lsLista            = new ArrayList<Object>();

        lsPeriodos        = new ArrayList<>();

        if (dialog != null){

            if ( dialog.isShowing() ) dialog.dismiss();


        }

        super.finish();

    }

    @Override
    protected void onStop() {

        //if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);

        super.onStop();

    }

    public void loadCampanhas(){

        try {


            lsparametrosCampanha = new ArrayList<>();

            ParametrosCampanha tempo = new ParametrosCampanha("000000","GERAL");

            tempo.addPeriodo( new PeriodoCampanha("03","2018","04","2018"));
            tempo.addPeriodo( new PeriodoCampanha("05","2018","06","2018"));
            tempo.addPeriodo( new PeriodoCampanha("07","2018","08","2018"));
            tempo.addPeriodo( new PeriodoCampanha("09","2018","10","2018"));
            tempo.addPeriodo( new PeriodoCampanha("11","2018","12","2018"));


            lsparametrosCampanha.add(tempo);

            SharedPreferences prefs = getSharedPreferences("CAMPANHA_2018", MODE_PRIVATE);

            Indice         = prefs.getInt("Indice", Indice);
            PeriodoInicial = prefs.getString("PeriodoInicial",PeriodoInicial);
            PeriodoFinal   = prefs.getString("PeriodoFinal"  ,PeriodoFinal);


            spPeriodo.setSelection(Indice);

            lsLista = new ArrayList<>();

            lsLista.add("CABEC");

            CampanhaDAO dao = new CampanhaDAO();

            dao.open();

            lsLista.addAll(dao.getCampanhas("2018",new String[] {PeriodoInicial,PeriodoFinal}));

            if (lsLista.size() == 1){

                lsLista.add(new NoData("Nehuma Campanha No Arquivo !!"));

            }

            adapter = new Adapter(CampanhaViewActivity.this,PeriodoInicial,PeriodoFinal,lsLista , new ClicProcesso(CampanhaViewActivity.this));

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e){

            toast(e.getMessage());

        }


    }

    private void toast(String mensagem){

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }

    private void carga01(Ocorrencia ocorrencia) throws  Exception {

        try {

            if ( ocorrencia.getSTATUS().equals("0") || ocorrencia.getSTATUS().equals("9")) {


                TASK task = new TASK("", "", ocorrencia.getCODIGO(), ocorrencia.getDESCRICAO(), "", "");

                PropertyInfo pi = new PropertyInfo();
                pi.setName("TASK");
                pi.setValue(task);
                pi.setType(task.getClass());

                AccessWebInfo acessoWeb2 = new AccessWebInfo(mHandlerCarga01, getBaseContext(), App.user, "PUTTASKS", "PUTTASKS", AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO, AccessWebInfo.PROCESSO_FILE, config, null, -1);

                acessoWeb2.addInfo(pi);
                acessoWeb2.addParam("CCODUSER", App.user.getCOD());
                acessoWeb2.addParam("CPASSUSER", App.user.getSENHA());
                acessoWeb2.addObjeto("TASK", new TASK());

                acessoWeb2.start();

            }
            if (ocorrencia.getSTATUS().equals("1")) {

                TASK task = new TASK("", "", ocorrencia.getCODIGO(), ocorrencia.getDESCRICAO(), "COM", ocorrencia.getARQUIVO());

                PropertyInfo pi = new PropertyInfo();
                pi.setName("TASK");
                pi.setValue(task);
                pi.setType(task.getClass());

                AccessWebInfo acessoWeb2 = new AccessWebInfo(null, getBaseContext(), App.user, "PROCTASKS", "PROCTASKS", AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO, AccessWebInfo.PROCESSO_FILE, config, null,-1);

                acessoWeb2.addInfo(pi);
                acessoWeb2.addParam("CCODUSER", App.user.getCOD());
                acessoWeb2.addParam("CPASSUSER", App.user.getSENHA());
                acessoWeb2.addObjeto("TASK", new TASK());
                acessoWeb2.setCodocorrencia(ocorrencia.getCODIGO());

                acessoWeb2.start();

            }

        } catch (Exception e) {

            throw new Exception("Falha Na Gravação Das Tarrefas!\n"+e.getMessage());

        }

    }


    //Event Bus

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onReceiverNotification(NotificationCarga notification){
//
//        Log.i(LOG,"Notification Carga");
//
//        Log.i(LOG,notification.getMSGERRO());
//
//        UltimaAtualizacao.setText(notification.getMSGERRO());
//
//        lsLista.clear();
//
//        lsLista.add("CABEC");
//
//        lsLista.add(new ObjProcesso("TITULO",notification.getMSGERRO(),"OBS"));
//
//        adapter = new Adapter(CampanhaViewActivity.this,PeriodoInicial,PeriodoFinal,lsLista,new ClicProcesso(CampanhaViewActivity.this));
//
//        lv.setAdapter(adapter);
//
//        adapter.notifyDataSetChanged();
//
//
//    }

    private void refreshCarga(String CodTarefa) {

        Timer timer = new Timer();

        timer.scheduleAtFixedRate( new refresh(CodTarefa) , 20000, 20000);

        Log.i(LOG,"Refresh Ativado...");

    }

    private void Toast(String Mensa){

        Toast.makeText(this, Mensa, Toast.LENGTH_SHORT).show();

    }

    private void ResetOcorrencia(String codigo){

        try {

            OcorrenciaDAO dao = new OcorrenciaDAO();

            dao.open();

            Ocorrencia ocorrencia = dao.seekByCodigo(new String[] {codigo});

            if (ocorrencia != null) {

                ocorrencia.setSTATUS("0");
                ocorrencia.setOBS("");
                ocorrencia.setARQUIVO("");

                dao.Update(ocorrencia);

            }

            dao.close();

        } catch(Exception e){

            toast(e.getMessage());

        }



    }

    private Handler mHandlerCarga01 = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }



                if (msg.getData().getString("CERRO").equals("---")) {

                    if (!(dialog.isShowing())) dialog.show();

                    processado = true;

                    Log.i(LOG, "Mostrando Dialog...");

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


                    if (dialog != null){

                        if (dialog.isShowing()){

                            dialog.dismiss();

                        }

                    }


                    if (!msg.getData().getString("CMSGERRO").isEmpty()) {

                        Toast( msg.getData().getString("CMSGERRO"));

                    }

                    refreshCarga("000018");

                    processado = true;
                }


                if (!processado) {


                    Toast("Erro: " + msg.getData().getString("CMSGERRO"));


                }

            } catch (Exception E) {

                Log.d(LOG, "MENSAGEM", E);

                Toast("Erro Handler: " + E.getMessage());

            }
        }
    };

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

                        Toast( e.getMessage());

                    }

                }


            }
            catch (Exception e){

                toast(e.getMessage());

            }
        }
    };



    private void verWeb(){

        int IndiceConexao = 0;


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

            Toast( e.getMessage() );

            finish();

        }

        conexaoadapter = new ConexaoAdapter(CampanhaViewActivity.this, R.layout.conexoes_opcoes, conexoes);

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

                        Toast("Não Atualizada A Conexão !!\n" + e.getMessage());
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spConexao.setSelection(IndiceConexao);


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

            } else {
                //Altera algumas mensagens
                if (cmsgerro.contains("failed to connect")) {

                    cmsgerro = "Falha de Conexão.";

                } else {

                    cmsgerro = cmsgerro;

                }

                conexaoadapter.setStatusRede(false,cmsgerro,false);
            }

        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

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

    private  class refresh extends TimerTask {

        String CodTarefa = "";

        int vezes = 0;

        public refresh(String codTarefa) {

            CodTarefa = codTarefa;

            vezes = 0;
        }

        @Override
        public void run() {

            try {

                OcorrenciaDAO dao = new OcorrenciaDAO();

                dao.open();

                Ocorrencia ocorrencia = dao.seekByCodigo(new String[] {CodTarefa});

                dao.close();

                if (ocorrencia != null && ocorrencia.getSTATUS().equals("1")) {

                    vezes++;

                    if (vezes == 15) cancel();

                    Log.i("TIME", "Processando...Tarefa " + CodTarefa + " Vez " + String.valueOf(vezes));

                    carga01(ocorrencia);

                } else {

                    Log.i("TIME", "Finalizando...Tarefa " + CodTarefa );

                    cancel();

                }
            } catch (Exception e){

                Log.i("TIME", "Finalizando...Tarefa " + CodTarefa );

                Log.i("TIME",e.getMessage());

                cancel();

            }
        }
    }

    public class TransparentProgressDialog extends Dialog {

        private TextView txt_estagio_127;

        private TextView txt_obs_127;

        private Button bt_cancela_127;

        public TransparentProgressDialog(Context context ) {
            super(context, R.style.AppTheme);

            WindowManager.LayoutParams wlmp = getWindow().getAttributes();

            wlmp.height  = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(wlmp);
            setTitle("");
            setCancelable(false);
            setOnCancelListener(null);
            View view = LayoutInflater.from(context).inflate(R.layout.showprocessocarga, null);

            setContentView(view);

            TextView txt_obs_127       = (TextView) findViewById(R.id.txt_obs_127);

            TextView txt_estagio_127   = (TextView) findViewById(R.id.txt_estagio_127);

            Button   bt_cancela_127    = (Button)   findViewById(R.id.bt_cancela_127);

            bt_cancela_127.setOnClickListener( new ClicProcesso(context));

        }


        public String getTxt_estagio_127() {
            return txt_estagio_127.getText().toString();
        }

        public void setTxt_estagio_127(String value) {
            txt_estagio_127.setText(value);
        }

        public String getTxt_obs_127() {
            return txt_obs_127.getText().toString();
        }

        public void setTxt_obs_127(String value) {
            this.txt_obs_127.setText(value);
        }
    }

    public class ClicProcesso implements  View.OnClickListener {

        Context context;

        public ClicProcesso(Context context) {

            this.context = context;

        }

        @Override
        public void onClick(View v) {


            try {

                OcorrenciaDAO dao = new OcorrenciaDAO();

                dao.open();

                Ocorrencia ocorrencia = dao.seekByCodigo(new String[] {"000018"});

                if (ocorrencia != null) {

                    ocorrencia.setSTATUS("0");
                    ocorrencia.setOBS("");
                    ocorrencia.setARQUIVO("");

                    dao.Update(ocorrencia);

                }

                dao.close();

                loadCampanhas();

            } catch(Exception e){

                toast(e.getMessage());

            }

            return;

        }



    }


}
