package br.com.brotolegal.sav700;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PreClienteDocumentosActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView im_comercial;
    private ImageView im_logistica;
    private ImageView im_documentos;

    private TextView tv_comercial;
    private TextView tv_logistica;
    private TextView tv_documentos;

    private String CODCLIENTE = "";
    private String OPERACAO   = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_cliente_documentos);
        try {
            toolbar = (Toolbar) findViewById(R.id.tb_precliente_documentos);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Pré-Clientes");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            toolbar.inflateMenu(R.menu.menu_precliente);

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CODCLIENTE = params.getString("CODCLIENTE");

                OPERACAO  = params.getString("OPERACAO");


            }

            im_comercial = (ImageView) findViewById(R.id.im_comercial);

            im_logistica = (ImageView) findViewById(R.id.im_logistica);

            im_documentos = (ImageView) findViewById(R.id.im_documentos);

            tv_comercial = (TextView) findViewById(R.id.tv_comercial);

            tv_logistica = (TextView) findViewById(R.id.tv_logistica);

            tv_documentos = (TextView) findViewById(R.id.tv_documentos);

            navegador();

        } catch (Exception e) {

            finish();
        }

    }

    private void toast(String Mensagem){


        Toast.makeText(this, Mensagem, Toast.LENGTH_SHORT).show();



    }

    private void navegador(){

        tv_comercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreClienteDocumentosActivity.this, PreClienteComercialActivity.class);
                Bundle params = new Bundle();
                params.putString("CODIGO", "");
                intent.putExtras(params);
                startActivity(intent);
                finish();

            }
        });

        tv_logistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreClienteDocumentosActivity.this, PreClienteLogisticaActivity.class);
                Bundle params = new Bundle();
                params.putString("CODCLIENTE", "");
                params.putString("OPERACAO"  , "DIGITANDO");
                intent.putExtras(params);
                startActivity(intent);
                finish();
            }
        });



    }


}
