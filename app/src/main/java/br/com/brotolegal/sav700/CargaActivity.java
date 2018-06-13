package br.com.brotolegal.sav700;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.Timer;
import java.util.TimerTask;

import br.com.brotolegal.sav700.util.MyToast;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.OcorrenciaDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.TaskDAO;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Ocorrencia;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Task;
import br.com.brotolegal.savdatabase.eventbus.NotificationCarga;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;
import br.com.brotolegal.savdatabase.internet.SoapServEnv;
import br.com.brotolegal.savdatabase.regrasdenegocio.UsuarioTST;
import br.com.brotolegal.savdatabase.wsentities.TASK;

public class CargaActivity extends AppCompatActivity {

    private int Result = 1;

    private Spinner spConexao;

    private Spinner spParamentros;

    private List<Config> conexoes;

    private conexaoAdapter conexaoadapter;

    private parametrosAdapter parametrosadapter;

    private String StatusRede = "Status Não Verificado.";

    private verRede verrede;

    private Dialog dialog;

    private String LOG = "CARGA";

    private Toolbar toolbar;

    private ListView lv;

    private ProgressBar pbBarra;

    private List<Object> lsLista = new ArrayList<Object>();

    private List<parametros> lsParametros = new ArrayList<>();

    private Adapter adapter;

    private Tasking tasking;

    private Config config;

    private WEBThread web;

    private String CodigoConfig = "";

    private int    IndiceConexao = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        try {

            toolbar = (Toolbar) findViewById(R.id.tb_carga);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Rotina De Atualização De Arquivos");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            toolbar.inflateMenu(R.menu.menu_carga);

            spConexao   = (Spinner) findViewById(R.id.spConexao_carga);

            spParamentros = (Spinner) findViewById(R.id.spParametro_carga);

            lv = (ListView) findViewById(R.id.lvOcorrencias);

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

                        tempo = "Até Dia:"+st.getCARDATA() + " Até As: "+st.getCARHORA();

                    } else {

                        tempo = "";

                    }

                    lsParametros.add(new parametros(1,st.getPedidoStatus(),tempo));

                } else {

                    lsParametros.add(new parametros(0,"SEM INFORMAÇÃO",""));
                }

                dao.close();

                parametrosadapter = new parametrosAdapter(CargaActivity.this, R.layout.conexoes_opcoes, lsParametros);

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

                spParamentros.setSelection(0);

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

            conexaoadapter = new conexaoAdapter(CargaActivity.this, R.layout.conexoes_opcoes, conexoes);

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

                            Toast.makeText(getBaseContext(), "Não Atualizada A Conexão !!\n"+e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });


            spConexao.setSelection(IndiceConexao);



        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carga, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // action with ID action_refresh was selected
            case android.R.id.home:

                finish();

                break;

            case R.id.ac_carga_cancelar: {

                finish();

                break;
            }
            case  R.id.ac_carga_refresh_tarefas: {

                try {

                    refresh();

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }

                break;
            }

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void finish() {

        lsLista = new ArrayList<Object>();

        Result = 1;

        Intent data = new Intent();

        data.putExtra("ROTINA","CARGA");

        setResult(Result, data);


        super.finish();

    }


    @Override
    protected void onStop() {

        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);

        super.onStop();

    }

    @Override
    public void onResume() {

        try {

            if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);


        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


        LoadOcorrencias();

        super.onResume();
    }


    private void LoadStatus(){

        try {

            lsParametros = new ArrayList<>();

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

                    tempo = "Até o Dia:"+st.getPEDDATA() + " Até As: "+st.getPEDHORA();

                } else {

                    tempo = "";

                }

                lsParametros.add(new parametros(1,st.getPedidoStatus(),tempo));

            } else {

                lsParametros.add(new parametros(0,"SEM INFORMAÇÃO",""));
            }

            dao.close();

            parametrosadapter = new parametrosAdapter(CargaActivity.this, R.layout.conexoes_opcoes, lsParametros);

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

    private void refresh() throws Exception {

        try {

            tasking = new Tasking();

            AccessWebInfo acessoWeb3 = new AccessWebInfo(mHandlerAlocar, getBaseContext(), App.user, "GETTASK", "GETTASK", AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO, AccessWebInfo.PROCESSO_CUSTOM, config, tasking,-1);

            acessoWeb3.addParam("CCODUSER",  App.user.getCOD().trim());

            acessoWeb3.addParam("CPASSUSER", App.user.getSENHA().trim());

            acessoWeb3.addParam("CCODMODULE","COM");

            acessoWeb3.start();

        } catch (Exception e) {

            throw new Exception(e.getMessage());
        }

    }


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

                Toast.makeText(CargaActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };


    private Handler mHandlerAlocar = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(CargaActivity.this, "Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
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


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    try {

                        tasking.processaArray();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

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

                        Toast.makeText(CargaActivity.this, "Erro: " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();

                    }
                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(CargaActivity.this, "Erro: " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            } catch (Exception E) {

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(CargaActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };

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

                    dialog = ProgressDialog.show(CargaActivity.this, "Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
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

                        Toast.makeText(CargaActivity.this, msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();

                    }

                    LoadOcorrencias();

                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(CargaActivity.this, "Erro: " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            } catch (Exception E) {

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(CargaActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };


    private void LoadOcorrencias() {

        try {

            lsLista = new ArrayList<Object>();

            lsLista.add("TAREFAS");

            OcorrenciaDAO dao = new OcorrenciaDAO();

            dao.open();

            for (Ocorrencia ocorrencia : dao.getAll()) {

                lsLista.add(ocorrencia);

            }

            dao.close();

            if (lsLista.size() == 1) {

                lsLista.add(new NoData("Nenhuma Ocorrência Encontrada !!"));

                refresh();

            }

            adapter = new Adapter(CargaActivity.this, lsLista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e) {

            Toast.makeText(this, "Erro Na Carga: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

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

                refreshCarga(ocorrencia.getCODIGO());

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverNotification(NotificationCarga notification){

        LoadOcorrencias();

    }

    private void refreshCarga(String CodTarefa) {

        Timer timer = new Timer();

        timer.scheduleAtFixedRate( new refresh(CodTarefa) , 20000, 20000);
    }


    //INNER CLASS

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

    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        Context context;

        final int ITEM_VIEW_CABEC = 0;
        final int ITEM_VIEW_DETALHE = 1;
        final int ITEM_VIEW_NO_DATA = 2;
        final int ITEM_VIEW_COUNT = 3;

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


                if (obj instanceof Ocorrencia) {

                    qtd = qtd + 1;

                }

            }


            retorno = "Total de Ocorrencias: " + String.valueOf(qtd);

            return retorno;
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

            if (lsObjetos.get(position) instanceof Ocorrencia) {

                retorno = ITEM_VIEW_DETALHE;

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

        public void deleteitem(int position) {

            this.lsObjetos.remove(position);
            //Atualizar a lista caso seja adicionado algum item
            notifyDataSetChanged();

            return;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            try {

                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.ocorrencia_row, null);

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

                        final Ocorrencia obj = (Ocorrencia) lsObjetos.get(pos);

                        ImageView tvVisualizar = (ImageView) convertView.findViewById(R.id.img_20);

                        if (conexaoadapter.getConnected()){

                            tvVisualizar.setImageResource(R.drawable.conectado);

                        } else {

                            tvVisualizar.setImageResource(R.drawable.desconectado);

                        }

                        TextView tvlinha01 = (TextView) convertView.findViewById(R.id.tvLinha_01_20);

                        tvlinha01.setText(obj.getCODIGO() + "-" + obj.getDESCRICAO());

                        TextView tvlinha02 = (TextView) convertView.findViewById(R.id.tvLinha_02_20);

                        tvlinha02.setText(obj.getOBS());

                        TextView tvLinha03 = (TextView) convertView.findViewById(R.id.tvLinha_03_20);

                        tvLinha03.setText("Status: "+obj.getStatusDescri()+" Última Execução Demorou: "+App.diferencaDatas(obj.getHORASOL(),obj.getHORAEXE()));

                        ImageButton ibProcessar_20 = (ImageButton) convertView.findViewById(R.id.ibProcessar_20);

                        ImageButton ibCancelar_20 = (ImageButton) convertView.findViewById(R.id.ibCancelar_20);

                        ProgressBar progressBar   = (ProgressBar) convertView.findViewById(R.id.progress_bar_20);

                        progressBar.setVisibility(View.INVISIBLE);

                        if (obj.getSTATUS().equals("3")) {

                            ibProcessar_20.setVisibility(View.INVISIBLE);

                            ibCancelar_20.setVisibility(View.INVISIBLE);

                            progressBar.setVisibility(View.INVISIBLE);

                        } else {

                            if (obj.getSTATUS().equals("1")) progressBar.setVisibility(View.VISIBLE);

                            if (obj.getSTATUS().compareTo("X") == 0 || obj.getSTATUS().compareTo("0") == 0 || obj.getSTATUS().isEmpty()) {

                                ibProcessar_20.setImageResource(R.drawable.play_iniciar);

                            } else {

                                ibProcessar_20.setImageResource(R.drawable.play_reiniciar);

                                progressBar.setVisibility(View.VISIBLE);

                            }

                            ibProcessar_20.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {

                                        carga01(obj);

                                    } catch (Exception e) {

                                        toast(e.getMessage());

                                    }

                                }
                            });

                            if (obj.getSTATUS().compareTo("0") == 0 || obj.getSTATUS().isEmpty()) {

                                ibCancelar_20.setVisibility(View.INVISIBLE);

                            } else {

                                ibCancelar_20.setVisibility(View.VISIBLE);

                            }
                            ibCancelar_20.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {

                                        obj.setSTATUS("0");
                                        obj.setOBS("");
                                        obj.setARQUIVO("");

                                        OcorrenciaDAO dao = new OcorrenciaDAO();

                                        dao.open();

                                        dao.Update(obj);

                                        dao.close();

                                        LoadOcorrencias();


                                    } catch (Exception e) {

                                        toast(e.getMessage());

                                    }

                                }
                            });


                            if (conexaoadapter.getConnected()) {

                                ibProcessar_20.setVisibility(View.VISIBLE);


                            } else {

                                ibProcessar_20.setVisibility(View.INVISIBLE);

                                ibCancelar_20.setVisibility(View.INVISIBLE);

                            }
                        }
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

    }

    private class WEBThread extends Thread{
        private Handler mHandler;
        private SoapObject request;
        private Bundle params = new Bundle();

        public WEBThread(Handler handler){

            super();

            mHandler=handler;

        }

        @Override

        public void run(){

            HttpTransportSE androidHttpTransport;

            try {

                request = new SoapObject(config.getUrlFull(),"PUTTASKS");
                request.addProperty("CCODUSER" , "000090");
                request.addProperty("CPASSUSER", "121212");

                SoapObject tasks   = new SoapObject("PUTTASKS", "TASKS");
                SoapObject task    = new SoapObject("PUTTASKS", "TASK");
                SoapObject wstask  = new SoapObject("PUTTASKS", "WSTASK");
                wstask.addProperty("CERRO"        , "000");
                wstask.addProperty("CMSGERRO"     , "OK");
                wstask.addProperty("CCODIGO"      ,  "000001");
                wstask.addProperty("CDESCRICAO"   ,  "CARGA GERAL");
                wstask.addProperty("CMODULO"      ,  "COM");
                wstask.addProperty("CNOMEARQ", "TESTE.TXT");
                task.addSoapObject(wstask);
                tasks.addProperty("TASK",task);
                request.addProperty("TASKS",tasks);
                SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);
                envelope.implicitTypes = true;
                envelope.setAddAdornments(false);
                envelope.dotNet        = true;
                envelope.setOutputSoapObject(request);


                androidHttpTransport = new HttpTransportSE(config.getUrlFull());
                androidHttpTransport.debug = true;
                androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                androidHttpTransport.call("GETTASK", envelope);
                SoapObject result = (SoapObject) envelope.getResponse();
                Log.i(LOG,androidHttpTransport.requestDump);


            }catch (Exception e)
            {
                Log.i(LOG,e.getMessage());

            }


        }

        public void setHandler(Handler handler){

            mHandler=handler;

        }


        public void sendmsg(Bundle value){

            if ( value != null)
            {
                Message msgObj = mHandler.obtainMessage();
                msgObj.setData(value);
                mHandler.sendMessage(msgObj);
            }


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

            LoadOcorrencias();
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

            parametros obj = null;

            try {

                if (position > (lista.size()-1)){

                    position--;

                }

                obj = (parametros) lista.get(position);

            } catch (Exception e){

                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                Log.i("todo",exceptionAsString);

            }

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

            try {

                if (position == 3){

                    position--;

                }
                return getEscolhaView(position, convertView, parent);

            } catch (Exception e) {

                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                Log.i("111",exceptionAsString);

            }

            return getEscolhaView(0, convertView, parent);

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

