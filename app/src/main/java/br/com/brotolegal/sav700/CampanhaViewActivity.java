package br.com.brotolegal.sav700;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.entities.Campanha_fast;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;

public class CampanhaViewActivity extends AppCompatActivity {

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

    List<String[]> lsPeriodos            = new ArrayList<>();

    TextView UltimaAtualizacao;

    ListView Campanha;

    private  int     Indice = 2;
    private  String  PeriodoInicial = "201807";
    private  String  PeriodoFinal   = "201808";

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_campanha_fechada_sincronizar:

                finish();

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

        SharedPreferences prefs = getSharedPreferences("CAMPANHA_2018", MODE_PRIVATE);

        Indice         = prefs.getInt("Indice", Indice);
        PeriodoInicial = prefs.getString("PeriodoInicial",PeriodoInicial);
        PeriodoFinal   = prefs.getString("PeriodoFinal"  ,PeriodoFinal);


        spPeriodo.setSelection(Indice);


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

        super.finish();

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

            adapter = new Adapter(CampanhaViewActivity.this,PeriodoInicial,PeriodoFinal,lsLista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e){



        }


    }

    private void toast(String mensagem){

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

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

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

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

}
