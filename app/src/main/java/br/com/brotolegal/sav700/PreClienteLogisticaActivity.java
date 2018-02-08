package br.com.brotolegal.sav700;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PreClienteLogisticaActivity extends AppCompatActivity {



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
        setContentView(R.layout.activity_pre_cliente_logistica);


        try {

            toolbar = (Toolbar) findViewById(R.id.tb_precliente_logistica);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Pré-Clientes");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            toolbar.inflateMenu(R.menu.menu_precliente_02);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_precliente_02, menu);

        MenuItem iGravar   = menu.findItem(R.id.action_precliente_gravar);
        MenuItem iCancelar = menu.findItem(R.id.action_precliente_cancelar);
        MenuItem iVoltar   = menu.findItem(R.id.action_precliente_voltar);

        if (OPERACAO.equals("NOVO")) {

            iVoltar.setVisible(false);

        }

        if (OPERACAO.equals("ALTERACAO")) {

            iVoltar.setVisible(false);

        }

        if (OPERACAO.equals("CONSULTA")) {

            iGravar.setVisible(false);
            iCancelar.setVisible(false);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_precliente_gravar:

                finish();

                break;

            case R.id.action_precliente_cancelar:

                finish();

                break;

            case R.id.action_precliente_voltar:

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private void toast(String Mensagem){


        Toast.makeText(this, Mensagem, Toast.LENGTH_SHORT).show();



    }

    private void navegador(){

        tv_comercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreClienteLogisticaActivity.this, PreClienteComercialActivity.class);
                Bundle params = new Bundle();
                params.putString("CODCLIENTE", CODCLIENTE);
                params.putString("OPERACAO"  , OPERACAO);
                intent.putExtras(params);
                startActivity(intent);
                finish();

            }
        });



        tv_documentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreClienteLogisticaActivity.this,PreClienteDocumentosActivity.class);
                Bundle params = new Bundle();
                params.putString("CODCLIENTE", CODCLIENTE);
                params.putString("OPERACAO"  , OPERACAO);
                intent.putExtras(params);
                startActivity(intent);
                finish();
            }
        });

    }



}

