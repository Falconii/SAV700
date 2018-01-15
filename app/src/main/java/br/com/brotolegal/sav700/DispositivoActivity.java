package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.ksoap2.serialization.SoapObject;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.DispositivoDAO;
import br.com.brotolegal.savdatabase.entities.Dispositivo;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

import static br.com.brotolegal.sav700.R.id.toolbar;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;
import static java.lang.Thread.sleep;
import static java.security.AccessController.getContext;

public class DispositivoActivity extends AppCompatActivity {

    private Dialog dialog;

    private String LOG="DISPOSITIVO";

    private Dispositivo dispositivo;

    private  Toolbar toolbar;

    private callbackDispositivo callbackdispositivo;

    private TextView txtUsuario;
    private TextView txtAtivo;
    private TextView txtlinha01;
    private TextView txtlinha02;
    private TextView txtlinha03;
    private TextView txtlinha04;
    private TextView txtlinha05;
    private TextView txtlinha06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivo);

        toolbar = (Toolbar) findViewById(R.id.tb_dispositivo);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Dispositivo");
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.menu_dispositivo);


        TextView txtUsuario = (TextView) findViewById(R.id.txt_user_333);

        TextView txtAtivo   = (TextView) findViewById(R.id.txt_ativo_333);

        TextView txtlinha01 = (TextView) findViewById(R.id.txt_linha_01_333);

        TextView txtlinha02 = (TextView) findViewById(R.id.txt_linha_02_333);

        TextView txtlinha03 = (TextView) findViewById(R.id.txt_linha_03_333);

        TextView txtlinha04 = (TextView) findViewById(R.id.txt_linha_04_333);

        TextView txtlinha05 = (TextView) findViewById(R.id.txt_linha_05_333);

        TextView txtlinha06 = (TextView) findViewById(R.id.txt_linha_06_333);


        getDispositivo();

        txtUsuario.setText(App.user.getNOME());

        txtAtivo.setText(dispositivo.getCHAPA());

        txtlinha01.setText("Fabrincante: "+dispositivo.getFABRICANTE());

        txtlinha02.setText("Modelo: "+dispositivo.getMODELO());

        txtlinha03.setText("S.O:  "+dispositivo.getVERSAO());

        txtlinha04.setText("API:  "+dispositivo.getBUILD());

        txtlinha05.setText("IMEI: "+dispositivo.getIMEI());

        txtlinha06.setText("TOKEN: "+dispositivo.get_TOKEN());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dispositivo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;
            case R.id.dispositivo_menu_newtoken:

                //Busca arquivo dispositivo
                try {

                    dispositivo.setTOKEN(FirebaseInstanceId.getInstance().getToken());

                    getDispositivo();

                    callbackdispositivo = new callbackDispositivo();

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerToken, DispositivoActivity.this, App.user, "SETTOKEN", "SETTOKEN", RETORNO_TIPO_ESTUTURADO, PROCESSO_CUSTOM, null, callbackdispositivo,-1);

                    acessoWeb.addParam("CCODUSER" , App.user.getCOD().trim());

                    acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

                    acessoWeb.addParam("CIMEI"    ,dispositivo.getIMEI() );

                    acessoWeb.addParam("CTOKEN"   ,dispositivo.getTOKEN());

                    acessoWeb.start();

                } catch (Exception e) {

                    toast(e.getMessage());

                }

                break;

            case R.id.dispositivo_menu_boasvindas:

                //Busca arquivo dispositivo
                try {

                    getDispositivo();

                    callbackdispositivo = new callbackDispositivo();

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerToken, DispositivoActivity.this, App.user, "ENVIAMSGPUSH", "ENVIAMSGPUSH", RETORNO_TIPO_ESTUTURADO, PROCESSO_CUSTOM, null, callbackdispositivo,-1);

                    acessoWeb.addParam("CCODUSER" , App.user.getCOD().trim());

                    acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

                    acessoWeb.addParam("CIMEI"    ,dispositivo.getIMEI() );

                    acessoWeb.start();

                } catch (Exception e) {

                    toast(e.getMessage());

                }

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        getDispositivo();

        super.onResume();
    }

    private void toast(String mensa){

        Toast.makeText(this,mensa, Toast.LENGTH_SHORT).show();


    }

    private void getDispositivo(){


        DispositivoDAO dao = null;

        try {

            dao = new DispositivoDAO();

            dao.open();

            dispositivo = dao.seek(new String[] {"000000"});

            dao.close();

        } catch (Exception e) {

            toast(e.getMessage());
        }

        if (dispositivo == null){

            dispositivo = new Dispositivo();

        }


    }


    private Handler mHandlerToken = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }

                if ((msg.getData().getString("CERRO").equals("EXE"))) {

                    callbackdispositivo.processa();

                }

            } catch (Exception E) {

                toast("Erro Handler: " + E.getMessage());

            }
        }
    };



    private class callbackDispositivo extends HandleSoap {

        private Bundle params = new Bundle();

        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro       = result.getPropertyAsString("CERRO");
            String cmsgerro    = result.getPropertyAsString("CMSGERRO");

            if (!cerro.equals("000")) toast(cmsgerro);


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
