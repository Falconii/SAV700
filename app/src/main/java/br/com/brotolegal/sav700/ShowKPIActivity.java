package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.sav700.VerificaWeb.ConexaoAdapter;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;

public class ShowKPIActivity extends AppCompatActivity {

    private List<Config> conexoes;

    private ConexaoAdapter conexaoadapter;

    Toolbar toolbar;

    Spinner spConexao;

    TextView UltimaAtualizacao;

    private Dialog dialog;

    private CallBack callBack;

    private Config config;

    private WebView webView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_kpi);

        try {

            toolbar = (Toolbar) findViewById(R.id.tb_kpi_498);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Acompanhamento dos Kpi's");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.inflateMenu(R.menu.menu_campanha_main);

            spConexao         = (Spinner) findViewById(R.id.spConexao_498);

            UltimaAtualizacao = (TextView) findViewById(R.id.txt_atualizacao_498);

            UltimaAtualizacao.setText("");

            webView = (WebView) findViewById(R.id.wv_kpi_498);

            verWeb();


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

        getMenuInflater().inflate(R.menu.menu_show_kpi, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // action with ID action_refresh was selected
            case android.R.id.home:

                finish();

                break;

            case R.id.action_kpi_sincronizar: {

                try {

                    getKPI();

                } catch (Exception e) {

                    toast(e.getMessage());

                }

                break;
            }

            case R.id.action_kpi_voltar: {

                finish();

                break;
            }


            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void toast(String Mensa){

        Toast.makeText(this, Mensa, Toast.LENGTH_SHORT).show();

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

                        toast( e.getMessage());

                    }

                }


            }
            catch (Exception e){

                toast(e.getMessage());

            }
        }
    };




    private Handler mHandlerKpi = new Handler() {
        @Override
        public void handleMessage(Message msg){

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    toast("NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {


                    dialog = ProgressDialog.show(ShowKPIActivity.this, msg.getData().getString("CMSGERRO"), "Acessando Servidores.Aguarde !!", false, true);
                    dialog.setTitle("Buscando Informações...");
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


                if (msg.getData().getString("CERRO").equals("000")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.setTitle("Informações Encontradas !!!");

                        }

                    }

                    UltimaAtualizacao.setText(msg.getData().getString("CMSGERRO"));

                    showKpi(msg.getData().getString("CHTML"));

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

                    //loadAcordo();

                    processado = true;
                }


                if (!processado) {

                    toast( msg.getData().toString());

                }

            } catch (Exception e) {


                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                Log.i("ADAPTADOR",exceptionAsString);


                toast("Erro Handler: " + e.getMessage());

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

            toast( e.getMessage() );

            finish();

        }

        conexaoadapter = new ConexaoAdapter(ShowKPIActivity.this, R.layout.conexoes_opcoes, conexoes);

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

                        toast("Não Atualizada A Conexão !!\n" + e.getMessage());
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spConexao.setSelection(IndiceConexao);


    }

    private void getKPI(){

        AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerKpi, getBaseContext(), App.user, "GETKPI", "GETKPI", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_GET_KPI, config, null, -1);

        acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

        acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

        acessoWeb.start();

    }

    private void showKpi(String html){

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");


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


}
