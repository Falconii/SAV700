package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.UsuarioDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

public class LoginActivity extends AppCompatActivity {

    Spinner spConexoes;

    private int Result = -1;

    private List<Config> conexoes;

    private AdapterConexao adapterconexao;

    private Config config;

    private int IndiceConexao;

    private verRede verrede;

    private verLogin verlogin;

    private String LOG = "LOGIN";

    private EditText edUser_335;

    private EditText edPass_335;

    private Toolbar toolbar;

    private Dialog dialog;

    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.tb_login_335);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Rotina Login");
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        toolbar.inflateMenu(R.menu.menu_carga);

        spConexoes = (Spinner) findViewById(R.id.spConexao_335);

        edUser_335 = (EditText) findViewById(R.id.edUser_335);

        edPass_335 = (EditText) findViewById(R.id.edPass_335);


        Intent i = getIntent();

        if (i != null){

            Bundle params = i.getExtras();

            edUser_335.setText(params.getString("CODIGO",""));

            edPass_335.setText(params.getString("SENHA" ,"12345"));

        }

        edUser_335.setEnabled(false);


        try {

            UsuarioDAO dao = new UsuarioDAO();

            dao.open();

            user = dao.seek(new String[] {edUser_335.getText().toString().trim()});

            dao.close();

            if (user == null){

                Toast.makeText(LoginActivity.this, "Usuário Não Encontrado.", Toast.LENGTH_SHORT).show();

                finish();

            }

        } catch (Exception e) {

            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // action with ID action_refresh was selected
            case android.R.id.home:

                finish();

                break;

            case R.id.ac_login_cancelar: {

                finish();

                break;
            }
            case R.id.ac_login_logar: {

                try {

                    verlogin = new verLogin();

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerLogin, getBaseContext(), user, "GETLOGIN", "GETLOGIN", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_CUSTOM, config, verlogin,-1);

                    acessoWeb.addParam("CCODUSER", edUser_335.getText().toString());

                    acessoWeb.addParam("CPASSUSER", edPass_335.getText().toString());

                    acessoWeb.start();


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
    protected void onResume() {

        try {

            ConfigDAO dao = new ConfigDAO();

            dao.open();

            config = dao.seek(new String[]{"000"});

            conexoes = dao.getConexoes();

            Config padrao = dao.seekByDescricao(new String[]{config.getDESCRICAO()});

            if (padrao != null) {

                IndiceConexao = 0;

                for (int x = 0; x < conexoes.size(); x++) {

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
        try {

            adapterconexao = new AdapterConexao(LoginActivity.this, R.layout.conexoes_opcoes, conexoes);

            spConexoes.setAdapter(adapterconexao);

            spConexoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if ((adapterconexao.getEscolha() == -2)) {

                        adapterconexao.setEscolha(-1);

                    } else {

                        adapterconexao.setEscolha(position);

                        config = conexoes.get(position);

                        config.setCODIGO("000");

                        adapterconexao.setStatusRede(true, "Verificando...", null);

                        verrede = new verRede();

                        AccessWebInfo acessoWeb = new AccessWebInfo(mHandler, getBaseContext(), user, "GETSTATUS", "GETSTATUS", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_CUSTOM, config, verrede,-1);

                        acessoWeb.addParam("CCODUSER", user.getCOD().trim());

                        acessoWeb.addParam("CPASSUSER", user.getSENHA().trim());

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


            spConexoes.setSelection(IndiceConexao);


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }

        super.onResume();
    }

    @Override
    public void finish() {

        if (Result != -1) {

            Intent data = new Intent();

            data.putExtra("CODIGO", edUser_335.getText().toString());

            setResult(Result, data);


        } else {

            Intent data = new Intent();

            data.putExtra("CODIGO", "0");

            setResult(Result, data);


        }

        super.finish();

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

    }


    private Handler mHandlerLogin = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(LoginActivity.this, "Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
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

                        verlogin.processa();

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

                        Toast.makeText(LoginActivity.this, "Erro: " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();

                    }
                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(LoginActivity.this, "Erro: " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            } catch (Exception E) {

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(LoginActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

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


            } catch (Exception E) {

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(LoginActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };


    private class verLogin extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro = result.getPropertyAsString("CERRO");

            String cmsgerro = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")) {

                SoapObject status = (SoapObject) result.getProperty("STATUS");

                String MV_ZBLECRG = App.TotvsSN(status.getPropertyAsString("MV_ZBLECRG"));
                String MV_ZBLEPED = App.TotvsSN(status.getPropertyAsString("MV_ZBLEPED"));
                String MV_ZDTECRG = App.aaaammddToddmmaaaa(status.getPropertyAsString("MV_ZDTECRG"));
                String MV_ZDTEPED = App.aaaammddToddmmaaaa(status.getPropertyAsString("MV_ZDTEPED"));
                String MV_ZHRECRG = status.getPropertyAsString("MV_ZHRECRG");
                String MV_ZHREPED = status.getPropertyAsString("MV_ZHREPED");

                //registra o usuario
                UsuarioDAO dao = new UsuarioDAO();

                dao.open();

                Usuario user = dao.seek(new String[]{edUser_335.getText().toString()});

                if (user == null) {

                    user = new Usuario();
                    user.setCOD(result.getPropertyAsString("U50_COD"));
                    user.setNOME(result.getPropertyAsString("U50_NOME"));
                    user.setSENHA(result.getPropertyAsString("U50_SENHA"));
                    user.setEXPIRA(result.getPropertyAsString("U50_EXPIRA"));
                    user.setCODPRO(result.getPropertyAsString("U50_CODPRO"));
                    user.setCODVEN(result.getPropertyAsString("U50_CODVEN"));
                    user.setNIVEL(result.getPropertyAsString("U50_NIVEL"));
                    user.setCLASS(result.getPropertyAsString("U50_CLASS"));
                    user.setMODULO(result.getPropertyAsString("U50_MODULO"));
                    user.setCODDIS(result.getPropertyAsString("U50_CODDIS"));
                    user.setSTATUS("M");
                    user.setCODSUP(result.getPropertyAsString("U50_CODSUP"));

                    if (dao.insert(user) == null) {

                        throw new Exception("Falha Na Gravação Do Usuário No Tablete");

                    }

                } else {

                    user.setSTATUS("M");

                    if (!dao.Update(user)) {

                        throw new Exception("Falha Na Atualização Do Usuário No Tablete");

                    }
                }


                dao.close();

                App.user = user;

                Result = 1;

                StatusDAO daoST = new StatusDAO();

                daoST.open();

                Status st = daoST.seek(null);

                if (st == null) {

                    st.setLOGADO("S");
                    st.setHORALOG("");
                    st.setUSERLOG(user.getCOD());

                    daoST.Update(st);

                } else {


                    App.user = null;

                }

                daoST.close();

                finish();

            } else {

                Toast.makeText(getBaseContext(),"Falha No Login/n"+cmsgerro,Toast.LENGTH_LONG).show();

            }

        }


        @Override
        public void processaArray() throws Exception {

            SoapObject registro;

            if (this.result == null) {

                return;

            }

        }
    }


    private class verRede extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro = result.getPropertyAsString("CERRO");
            String cmsgerro = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")) {

                String MV_ZBLECRG = App.TotvsSN(result.getPropertyAsString("MV_ZBLECRG"));
                String MV_ZBLEPED = App.TotvsSN(result.getPropertyAsString("MV_ZBLEPED"));
                String MV_ZDTECRG = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTECRG"));
                String MV_ZDTEPED = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTEPED"));
                String MV_ZHRECRG = result.getPropertyAsString("MV_ZHRECRG");
                String MV_ZHREPED = result.getPropertyAsString("MV_ZHREPED");

                cmsgerro = "Conexão Ativa.";

                adapterconexao.setStatusRede(false, cmsgerro, true);

                StatusDAO dao = new StatusDAO();

                dao.open();

                Status st = dao.seek(null);

                if (st == null) {

                    dao.insert(new Status("N", MV_ZBLEPED, MV_ZDTEPED, MV_ZHREPED, MV_ZBLECRG, MV_ZDTECRG, MV_ZHRECRG, "N", "", "", "","0"));

                } else {

                    st.setAPPBLOCK("N");
                    st.setPEDIDO(MV_ZBLEPED);
                    st.setPEDDATA(MV_ZDTEPED);
                    st.setPEDHORA(MV_ZHREPED);
                    st.setCARGA(MV_ZBLECRG);
                    st.setCARDATA(MV_ZDTECRG);
                    st.setCARHORA(MV_ZHRECRG);
                    st.setLOGADO("S");
                    st.setHORALOG("");
                    st.setUSERLOG(edUser_335.getText().toString());


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

                adapterconexao.setStatusRede(false, cmsgerro, false);
            }


        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro;

            if (this.result == null) {

                return;

            }

        }


    }

}
