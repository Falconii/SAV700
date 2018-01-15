package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.io.PrintWriter;
import java.io.StringWriter;

import br.com.brotolegal.sav700.VerificaWeb.ConexaoAtiva;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.UsuarioDAO;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionSavePedido;
import br.com.brotolegal.savdatabase.regrasdenegocio.PedidoBusiness;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CONEXOES;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;

public class UsuarioActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView txt_codigo_800;
    private TextView txt_expira_800;
    private TextView txt_class_800;
    private TextView txt_vendedor_800;
    private TextView txt_gsp_800;

    private CallBack callBack;
    private Dialog   dialog;
    private Usuario  usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);


        try {

            toolbar = (Toolbar) findViewById(R.id.tb_usuario);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Usuários Do SAV");
            toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.inflateMenu(R.menu.menu_usuario);

            UsuarioDAO dao = new UsuarioDAO();

            dao.open();

            usuario = dao.seek(new String[]{App.user.getCOD()});

            dao.close();

            if (usuario == null) {

                Toast.makeText(getApplicationContext(), "Não encontrei o Usuário !", Toast.LENGTH_SHORT).show();

                finish();

            }




            txt_codigo_800   = (TextView) findViewById(R.id.txt_codigo_800);
            txt_expira_800   = (TextView) findViewById(R.id.txt_expira_800);
            txt_class_800    = (TextView) findViewById(R.id.txt_class_800);
            txt_vendedor_800 = (TextView) findViewById(R.id.txt_vendedor_800);
            txt_gsp_800      = (TextView) findViewById(R.id.txt_gsp_800);

            txt_codigo_800.setText(usuario.getCOD());
            txt_expira_800.setText(usuario.getEXPIRA());
            txt_class_800.setText(usuario.get_CLASSE());
            txt_vendedor_800.setText(usuario.getCODVEN()+"-"+usuario.getNOME());
            txt_gsp_800.setText(usuario.get_GPS());


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;


            case R.id.menu_usuario_cancelar: {

                finish();

                break;
            }

            case R.id.menu_usuario_sincronizar: {

                BuscaConexaoAtiva();

                break;
            }

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void toast(String mensagem){

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }



    private void BuscaConexaoAtiva(){

        callBack = new CallBack();

        AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerRede, App.getCustomAppContext(), App.user, "GETSTATUS", "GETSTATUS",RETORNO_TIPO_ESTUTURADO,PROCESSO_CUSTOM, null, callBack,-1);

        acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

        acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());


        acessoWeb.start();

    }

    private Handler mHandlerRede = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    toast("NAO CONTEM AS CHAVES..");

                    return;

                }


                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(UsuarioActivity.this, "Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
                    dialog.setCancelable(false);
                    dialog.show();

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    try {

                        callBack.processa();

                        if ((dialog != null)) {

                            if (dialog.isShowing()) {

                                dialog.dismiss();

                            }

                        }


                    } catch (Exception e) {


                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));
                        String exceptionAsString = sw.toString();

                        Log.i("ADAPTADOR",exceptionAsString);

                        if ((dialog != null)) {

                            if (dialog.isShowing()) {

                                dialog.dismiss();

                            }

                        }


                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }


            }
            catch (Exception e){


                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                Log.i("ADAPTADOR",exceptionAsString);


                toast(e.getMessage());

                if ((dialog != null)) {

                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }

                }


            }
        }
    };

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
                String MV_GPS      = result.getPropertyAsString("GPS");
                cmsgerro = "Conexão Ativa.";

                {
                    StatusDAO dao = new StatusDAO();

                    dao.open();

                    Status st = dao.seek(null);

                    if (st == null) {

                        dao.insert(new Status("N", MV_ZBLEPED, MV_ZDTEPED, MV_ZHREPED, MV_ZBLECRG, MV_ZDTECRG, MV_ZHRECRG, "N", "", "", "", "0"));

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


                }


                {
                    usuario.setGPS(MV_GPS);

                    UsuarioDAO dao = new UsuarioDAO();

                    dao.open();

                    dao.Update(usuario);

                    dao.close();

                    App.user.setGPS(MV_GPS);

                    txt_gsp_800.setText(usuario.get_GPS());

                }

            } else {

                if (cmsgerro.contains("failed to connect")) {

                    cmsgerro = "Falha de Conexão.";

                } else {

                    cmsgerro = cmsgerro;

                }
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

