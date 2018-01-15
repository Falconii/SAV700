package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.barteksc.pdfviewer.PDFView;

import org.ksoap2.serialization.SoapObject;

import java.io.File;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.UsuarioDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

public class ManualActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Config  config;
    private callBackAtualizar callbackatualizar;
    private ProgressDialog dialog;
    //private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_manual_777);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Manual De Vendas");
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {

            //pdfView = (PDFView) findViewById(R.id.pdf_viewer_000);


            ConfigDAO dao = new ConfigDAO();

            dao.open();

            config = dao.seek(new String[]{"000"});

            Config padrao = dao.seekByDescricao(new String[]{config.getDESCRICAO()});

            config = padrao;

            dao.close();

            File pdf = new File(App.BasePath + "/" + App.AppPath + "/manual.pdf") ;

            try {

                if (pdf.exists() == false) {

                    atualizar();

                } else {

                    //pdfView.fromFile(pdf).load();

                }

            } catch (Exception e) {

                showToast("Falha Na Abetura Do Arquivo MANUAL.PDF");

                finish();

            }


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }


    }

    private void showToast(String mensagem){


        Toast.makeText(ManualActivity.this, mensagem, Toast.LENGTH_LONG).show();

    }

    private void atualizar(){


        try {

            callbackatualizar = new callBackAtualizar();

            AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerAtualizar, getBaseContext(), App.user, "", "", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_DOWNLOAD, config, callbackatualizar,-1);

            acessoWeb.addParam("CCODUSER" , App.user.getCOD());

            acessoWeb.addParam("CPASSUSER", App.user.getSENHA());

            acessoWeb.setFILENAME("manual.pdf");

            acessoWeb.start();


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_manual, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_manual_sincronizar:

                atualizar();

                break;

            case R.id.action_manual_voltar:

                finish();

                break;


            default: {

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {


        try {

            ConfigDAO dao = new ConfigDAO();

            dao.open();

            config = dao.seek(new String[]{"000"});

            Config padrao = dao.seekByDescricao(new String[]{config.getDESCRICAO()});

            config = padrao;

            dao.close();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

        super.onResume();
    }

    private Handler mHandlerAtualizar = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(ManualActivity.this, "Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
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

                        callbackatualizar.processa();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

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

                        Toast.makeText(ManualActivity.this, "Erro: " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();

                    }
                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(ManualActivity.this, "Erro: " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            } catch (Exception E) {

                showToast("Erro Handler: " + E.getMessage());

            }
        }
    };


    private class callBackAtualizar extends HandleSoap {

        File file;

        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro = result.getPropertyAsString("CERRO");

            String cmsgerro = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")) {

                try{

                    File pdf = new File(App.BasePath + "/" + App.AppPath + "/manual.pdf") ;

                    if (pdf.exists() == false) {

                        showToast("Arquivo Não Encontrado !!");

                    } else {

                        //pdfView.fromFile(pdf).load();

                    }

                }catch (Exception e){


                    showToast(e.getMessage());

                }
            }

            if (cerro.equals("999")) {

                finish();

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
