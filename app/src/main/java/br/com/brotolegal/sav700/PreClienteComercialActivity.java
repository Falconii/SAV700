package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.sav700.help20.Help20Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.config.MaskFormatter;
import br.com.brotolegal.savdatabase.dao.CidadeDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.PreClienteDAO;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.entities.Cidade;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.PreCliente;
import br.com.brotolegal.savdatabase.regrasdenegocio.Pessoa;
import br.com.brotolegal.savdatabase.regrasdenegocio.Pessoas;
import br.com.brotolegal.savdatabase.regrasdenegocio.ValidadorPreCliente;
import br.com.brotolegal.savdatabase.util.Logradouros;

public class PreClienteComercialActivity extends AppCompatActivity {

    private Toolbar   toolbar;
    private ImageView im_comercial;
    private ImageView im_logistica;
    private ImageView im_documentos;

    private TextView tv_comercial;
    private TextView tv_logistica;
    private TextView tv_documentos;

    private TextView tvID;
    private TextView tvCODIGO;
    private TextView tvCNPJ;
    private TextView tvIE;
    private TextView tvENTREGA;
    private TextView tvSTATUS;
    private TextView tvMENSAGEM;
    private TextView tvRAZAO;
    private TextView tvFANTASIA;
    private TextView tvPESSOA;
    private TextView tvENDERECO;
    private TextView tvCOMPLEMENTO;
    private TextView tvBAIRRO;
    private TextView tvCODIGO_CIDADE;
    private TextView tvESTADO;
    private TextView tvCIDADE;
    private TextView tvCEP;
    private TextView tvTELEFONE;
    private TextView tvHOME;
    private TextView tvEMAILNFE;
    private TextView tvEMAIL;
    private TextView tvFUNDACAO;
    private TextView tvCANAL;
    private TextView tvREDE;
    private TextView tvREDEDESCRI;
    private TextView tvCANALDESCRI;
    private TextView tvTABPRECO;
    private TextView tvTABPRECODESCRI;
    private TextView tvPOLITICA;
    private TextView tvPOLITICADESCRI;
    private TextView tvCOND;
    private TextView tvCONDDESCRI;
    private TextView tvBoleto;
    private TextView tvTaxa;
    private TextView tvSimplesOP;
    private TextView tvIsentoST;
    private TextView tvLIMITE;
    private TextView tvICMS;
    private TextView tvEAN;
    private TextView tvSUFRAMA;
    private TextView tvOBS;


    private TextView lblID;
    private TextView lblCODIGO;
    private TextView lblCNPJ;
    private TextView lblIE;
    private TextView lblENTREGA;
    private TextView lblSTATUS;
    private TextView lblMENSAGEM;
    private TextView lblRAZAO;
    private TextView lblFANTASIA;
    private TextView lblPESSOA;
    private TextView lblENDERECO;
    private TextView lblCOMPLEMENTO;
    private TextView lblBAIRRO;
    private TextView lblCODIGO_CIDADE;
    private TextView lblCEP;
    private TextView lblBusca;
    private TextView lblTELEFONE;
    private TextView lblHOME;
    private TextView lblEMAILNFE;
    private TextView lblEMAIL;
    private TextView lblFUNDACAO;
    private TextView lblCANAL;
    private TextView lblREDE;
    private TextView lblTABPRECO;
    private TextView lblPOLITICA;
    private TextView lblCOND;
    private TextView lblTAXA;
    private TextView lblBoleto;
    private TextView lblSimplesOP;
    private TextView lblIsentoST;
    private TextView lblLIMITE;
    private TextView lblICMS;
    private TextView lblEAN;
    private TextView lblSUFRAMA;

    private String CODCLIENTE = "";
    private String OPERACAO   = "";
    private BUSCAThread BuscaThread;
    private Dialog dialog;

    private DecimalFormat format_02 = new DecimalFormat(",##0.00");

    private ValidadorPreCliente validador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_cliente_comercial);

        try {
            toolbar = (Toolbar) findViewById(R.id.tb_precliente_comercial);
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

            if (OPERACAO.equals("NOVO")){

                App.precliente = new PreCliente();

            }

            if ("ALTERACAO|CONSULTA".contains(OPERACAO)){

                try {

                    PreClienteDAO dao = new PreClienteDAO();

                    dao.open();

                    App.precliente = dao.seek(new String[] {CODCLIENTE});

                    if (App.precliente == null){

                        App.precliente = new PreCliente();

                        toast("CLIENTE NÃO ENCONTRADO !!!");

                        finish();

                    }

                } catch (Exception e){

                    toast("CLIENTE NÃO ENCONTRADO !!!");

                    finish();

                }
            }


            validador = new ValidadorPreCliente(App.precliente,PreClienteComercialActivity.this);

            im_comercial  = (ImageView) findViewById(R.id.im_comercial);

            im_logistica  = (ImageView) findViewById(R.id.im_logistica);

            im_documentos = (ImageView) findViewById(R.id.im_documentos);

            tv_comercial  = (TextView) findViewById(R.id.tv_comercial);

            tv_logistica  = (TextView) findViewById(R.id.tv_logistica);

            tv_documentos = (TextView) findViewById(R.id.tv_documentos);

            navegador();

            init();

            edicao();

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

    private void init(){


        lblID            = (TextView) findViewById(R.id.lbl_id_500);

        lblCODIGO        = (TextView) findViewById(R.id.lbl_codigo_500);

        lblCNPJ          = (TextView) findViewById(R.id.lbl_cnpj_500);

        lblIE            = (TextView) findViewById(R.id.lbl_ie_500);

        lblENTREGA       = (TextView) findViewById(R.id.lbl_clienteentrega_500);

        lblSTATUS        = (TextView) findViewById(R.id.lbl_status_500);

        lblMENSAGEM      = (TextView) findViewById(R.id.lbl_mensagem_500);

        lblRAZAO         = (TextView) findViewById(R.id.lbl_razao_500);

        lblFANTASIA      = (TextView) findViewById(R.id.lbl_fantasia_500);

        lblPESSOA        = (TextView) findViewById(R.id.lbl_pessoa_500);

        lblENDERECO      = (TextView) findViewById(R.id.lbl_endereco_500);

        lblCOMPLEMENTO   = (TextView) findViewById(R.id.lbl_complemento_500);

        lblBAIRRO        = (TextView) findViewById(R.id.lbl_bairro_500);

        lblCODIGO_CIDADE = (TextView) findViewById(R.id.lbl_codigo_cidade_500);

        lblCEP           = (TextView) findViewById(R.id.lbl_cep_500);

        lblBusca         = (TextView) findViewById(R.id.lbl_busca_500);

        lblTELEFONE      = (TextView) findViewById(R.id.lbl_telefone_500);

        lblHOME          = (TextView) findViewById(R.id.lbl_home_500);

        lblEMAILNFE      = (TextView) findViewById(R.id.lbl_emailnfe_500);

        lblEMAIL         = (TextView) findViewById(R.id.lbl_email_500);

        lblFUNDACAO      = (TextView) findViewById(R.id.lbl_fundacao_500);

        lblCANAL         = (TextView) findViewById(R.id.lbl_canal_500);

        lblREDE          = (TextView) findViewById(R.id.lbl_rede_500);

        lblTABPRECO      = (TextView) findViewById(R.id.lbl_tabela_500);

        lblPOLITICA          = (TextView) findViewById(R.id.lbl_politica_500);

        lblCOND              = (TextView) findViewById(R.id.lbl_condpgto_500);

        lblTAXA              = (TextView) findViewById(R.id.lbl_taxa_500);

        lblBoleto            = (TextView) findViewById(R.id.lbl_boleto_500);

        lblSimplesOP         = (TextView) findViewById(R.id.lbl_simples_500);

        lblIsentoST          = (TextView) findViewById(R.id.lbl_isento_st_500);

        lblLIMITE            = (TextView) findViewById(R.id.lbl_limite_500);

        lblICMS              = (TextView) findViewById(R.id.lbl_icms_500);

        lblEAN              = (TextView) findViewById(R.id.lbl_ean_500);

        lblSUFRAMA          = (TextView) findViewById(R.id.lbl_suframa_500);

        tvID                = (TextView) findViewById(R.id.txt_id_500);

        tvCODIGO            = (TextView) findViewById(R.id.txt_codigo_500);

        tvCNPJ              = (TextView) findViewById(R.id.txt_cnpj_500);

        tvIE                = (TextView) findViewById(R.id.txt_ie_500);

        tvENTREGA           = (TextView) findViewById(R.id.txt_clienteentrega_500);

        tvSTATUS            = (TextView) findViewById(R.id.txt_status_500);

        tvMENSAGEM          = (TextView) findViewById(R.id.txt_mensagem_500);

        tvRAZAO             = (TextView) findViewById(R.id.txt_razao_500);

        tvFANTASIA          = (TextView) findViewById(R.id.txt_fantasia_500);

        tvPESSOA            = (TextView) findViewById(R.id.txt_pessoa_500);

        tvENDERECO          = (TextView) findViewById(R.id.txt_endereco_500);

        tvCOMPLEMENTO       = (TextView) findViewById(R.id.txt_complemento_500);

        tvBAIRRO            = (TextView) findViewById(R.id.txt_bairro_500);

        tvCODIGO_CIDADE     = (TextView) findViewById(R.id.txt_codigo_cidade_500);

        tvESTADO            = (TextView) findViewById(R.id.txt_estado_500);

        tvCIDADE            = (TextView) findViewById(R.id.txt_cidade_500);

        tvCEP               = (TextView) findViewById(R.id.txt_cep_500);

        tvTELEFONE          = (TextView) findViewById(R.id.txt_telefone_500);

        tvHOME              = (TextView) findViewById(R.id.txt_home_500);

        tvEMAILNFE          = (TextView) findViewById(R.id.txt_emailnfe_500);

        tvEMAIL             = (TextView) findViewById(R.id.txt_email_500);

        tvFUNDACAO          = (TextView) findViewById(R.id.txt_fundacao_500);

        tvCANAL             = (TextView) findViewById(R.id.txt_cod_canal_500);

        tvREDE              = (TextView) findViewById(R.id.txt_cod_rede_500);

        tvREDEDESCRI        = (TextView) findViewById(R.id.txt_rede_500);

        tvCANALDESCRI       = (TextView) findViewById(R.id.txt_canal_500);

        tvTABPRECO          = (TextView) findViewById(R.id.txt_cod_tabpreco_500);

        tvTABPRECODESCRI    = (TextView) findViewById(R.id.txt_tabpreco_500);

        tvPOLITICA          = (TextView) findViewById(R.id.txt_cod_politica_500);

        tvPOLITICADESCRI    = (TextView) findViewById(R.id.txt_politica_500);

        tvCOND              = (TextView) findViewById(R.id.txt_cod_condpgto_500);

        tvCONDDESCRI        = (TextView) findViewById(R.id.txt_condpgto_500);

        tvBoleto            = (TextView) findViewById(R.id.txt_boleto_500);

        tvTaxa              = (TextView) findViewById(R.id.txt_taxa_500);

        tvSimplesOP         = (TextView) findViewById(R.id.txt_simples_500);

        tvIsentoST          = (TextView) findViewById(R.id.txt_isento_st_500);

        tvEAN               = (TextView) findViewById(R.id.txt_ean_500);

        tvLIMITE            = (TextView) findViewById(R.id.txt_limite_500);

        tvSUFRAMA           = (TextView) findViewById(R.id.txt_suframa_500);

        tvICMS              = (TextView) findViewById(R.id.txt_icms_500);


        if (App.precliente != null) {

            refresh();
        }



    }

    private void refresh() {


        tvCODIGO.setText(App.precliente.getCODIGO() + "-" + App.precliente.getLOJA());

        tvSTATUS.setText(App.precliente.getSTATUSDESCRI());

        validador.Validadador(tvCNPJ,"CNPJ",App.cnpj_cpf(App.precliente.getCNPJ()));

        validador.Validadador(tvIE,"IE",App.precliente.getIE());

        validador.Validadador(tvENTREGA,"CLIENTEENTREGA",App.TotvsSIMNAO(App.precliente.getCLIENTEENTREGA()));

        validador.Validadador(tvRAZAO,"RAZAO",App.precliente.getRAZAO());

        validador.Validadador(tvFANTASIA,"FANTASIA",App.precliente.getFANTASIA());

        validador.Validadador(tvPESSOA,"PESSOA",App.precliente.getPESSOA());

        validador.Validadador(tvENDERECO,"ENDERECO",App.precliente.getENDERECO());

        validador.Validadador(tvCOMPLEMENTO,"COMPLEMENTO",App.precliente.getCOMPLEMENTO());

        validador.Validadador(tvBAIRRO,"BAIRRO",App.precliente.getBAIRRO());

        validador.Validadador(tvCODIGO_CIDADE,"CODCIDADE",App.precliente.getCODCIDADE());

        validador.Validadador(tvESTADO,"ESTADO",App.precliente.getESTADO());

        validador.Validadador(tvCIDADE,"CIDADE",App.precliente.getCIDADE());

        validador.Validadador(tvCEP,"CEP",App.precliente.getCEP());

        validador.Validadador(tvTELEFONE,"TELEFONE","("+App.precliente.getDDD()+")"+App.precliente.getTELEFONE());

        validador.Validadador(tvHOME,"HOMEPAGE",App.precliente.getHOMEPAGE());

        validador.Validadador(tvEMAILNFE,"EMAILNFE",App.precliente.getEMAILNFE());

        validador.Validadador(tvEMAIL,"EMAIL",App.precliente.getEMAIL());

        validador.Validadador(tvFUNDACAO,"FUNDACAO",App.precliente.getFUNDACAO());

        validador.Validadador(tvCANAL,"CANAL",App.precliente.getCANAL());

        validador.Validadador(tvCANALDESCRI,"CANALDESCRI",App.precliente.getCANALDESCRI());

        validador.Validadador(tvREDE,"REDE",App.precliente.getREDE());

        validador.Validadador(tvREDEDESCRI,"REDEDESCRI",App.precliente.getREDEDESCRI());

        validador.Validadador(tvTABPRECO,"TABPRECO",App.precliente.getTABPRECO());

        validador.Validadador(tvTABPRECODESCRI,"TABPRECODESCRI",App.precliente.getTABPRECODESCRI());

        validador.Validadador(tvPOLITICA,"POLITICA",App.precliente.getPOLITICA());

        validador.Validadador(tvPOLITICADESCRI,"POLITICADESCRI",App.precliente.getPOLITICADESCRI());

        validador.Validadador(tvCOND,"CONDPAGTO",App.precliente.getCONDPAGTO());

        validador.Validadador(tvCONDDESCRI,"CONDPAGTODESCRI",App.precliente.getCONDPAGTODESCRI());

        validador.Validadador(tvBoleto,"BOLETO",App.TotvsSIMNAO(App.precliente.getBOLETO()));

        validador.Validadador(tvTaxa,"TAXA",App.TotvsSIMNAO(App.precliente.getTAXA()));

        validador.Validadador(tvSimplesOP,"OPSIMPLES",App.TotvsSIMNAO(App.precliente.getOPSIMPLES()));

        validador.Validadador(tvIsentoST,"ISENTOST",App.TotvsSIMNAO(App.precliente.getISENTOST()));

        validador.Validadador(tvEAN,"EAN",App.precliente.getEAN());

        validador.Validadador(tvLIMITE,"LIMITE",format_02.format(App.precliente.getLIMITE()));

        validador.Validadador(tvSUFRAMA,"SUFRAMA",App.precliente.getSUFRAMA());

        validador.Validadador(tvICMS,"ICMS",App.TotvsSIMNAO(App.precliente.getICMS()));

    }

    private void edicao(){



        lblCNPJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "CNPJ", "CNPJ", 7, "Campo OBRIGATÓRIO.",0);

            }
        });

        lblIE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "INSC. ESTADUAL", "IE", 1, "Campo OBRIGATÓRIO.",0);

            }
        });

        lblENTREGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickClienteEntrega(v);

            }
        });

        lblRAZAO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "RAZAO", "RAZAO", 0, "Campo OBRIGATÓRIO.",40);

            }
        });




        lblFANTASIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "FANTASIA", "FANTASIA", 0, "Campo OBRIGATÓRIO.",40);

            }
        });


       lblPESSOA.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               clickTi(v,"TIPO DE CLIENTE","PESSOA");

           }
       });





        lblENDERECO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "ENDEREÇO", "ENDERECO", 0, "Campo OBRIGATÓRIO.",40);

            }
        });


        lblCOMPLEMENTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "COMPLEMENTO", "COMPLEMENTO", 0, "Campo OBRIGATÓRIO.",40);

            }
        });


        lblBAIRRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "BAIRRO", "BAIRRO", 0, "Campo OBRIGATÓRIO.",20);

            }
        });


        lblCODIGO_CIDADE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickPesquisaCodCidade(v,"CÓDIGO DA CIDADE","CODCIDADE");

            }
        });




        lblCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v,"CEP","CEP",8,"Campo OBRIGATÓRIO.",0);

            }
        });



        lblBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{

                    BuscaThread=new BUSCAThread(mHandler,App.precliente);

                    BuscaThread.start();

                }catch (Exception e)
                {

                    toast(e.getMessage());

                }

            }
        });


        lblTELEFONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "TELEFONE", "TELEFONE", 0, "Campo OBRIGATÓRIO.",20);

            }
        });

        lblHOME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "SITE DO CLIENTE", "HOMEPAGE", 4, "",20);

            }
        });

        lblEMAILNFE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "E-MAIL NFE", "EMAILNFE", 5, "Campo OBRIGATÓRIO.",255);

            }
        });

        lblEMAIL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "E-MAIL", "EMAIL", 5, "Campo OBRIGATÓRIO.",255);

            }
        });

        lblFUNDACAO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "DATA DAFUNDAÇÃO", "FUNDACAO", 9, "Campo OBRIGATÓRIO.",0);

            }
        });

        lblCANAL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickPesquisaCodCanal();

            }
        });

        lblREDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickPesquisaCodRede();

            }
        });


        lblTABPRECO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickPesquisaTabPreco();

            }
        });

        lblPOLITICA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickPesquisaPolitica();

            }
        });

        lblCOND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickPesquisaCondPagto();

            }
        });


        lblBoleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickSN(v,"Emite Boleto Bancário?","BOLETO");

            }
        });


        lblTAXA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickSN(v,"Aplica Taxa Financeira?","TAXA");

            }
        });

        lblSimplesOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickSN(v,"Regime Da Empresa é o SIMPLES?","OPSIMPLES");

            }
        });

        lblIsentoST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickSN(v,"Empresa é Isenta de ST?","ISENTOST");

            }
        });


        lblLIMITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "Limite De Crédito?", "LIMITE", 6, "Campo OBRIGATÓRIO.",0);

            }
        });

        lblICMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (App.precliente.getESTADO().equals("MG"))
                {
                    clickSN(v,"ICMS MG","ICMS");

                } else {

                    toast("Somente Para Clientes De Minas Gerais.");

                }

            }
        });

        lblEAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "CÓDIGO EAN", "EAN", 1, "Campo Não Obrigatório.",0);

            }
        });

        lblSUFRAMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTexto(v, "CÓDIGO SUFRAMA", "SUFRAMA", 1, "Campo Obrigatório Somente Para  Zona Franca De Manaus.",0);

            }
        });


    }

    private void toast(String Mensagem){


        Toast.makeText(this, Mensagem, Toast.LENGTH_SHORT).show();



    }

    private void navegador(){


        tv_logistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreClienteComercialActivity.this, PreClienteLogisticaActivity.class);
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

                Intent intent = new Intent(PreClienteComercialActivity.this,PreClienteDocumentosActivity.class);
                Bundle params = new Bundle();
                params.putString("CODCLIENTE", "");
                params.putString("OPERACAO"  , "DIGITANDO");
                intent.putExtras(params);
                startActivity(intent);
                finish();
            }
        });

    }

    private void clickTexto(View v, String label, final String campo, int tipo, String mensa, int maxlenght) {

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.gettexttopadrao);

        dialog.setTitle(label);

        final Button confirmar = (Button) dialog.findViewById(R.id.btn_570_ok);
        final Button cancelar = (Button) dialog.findViewById(R.id.btn_570_can);
        final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_570_texto1);
        final EditText edCampo = (EditText) dialog.findViewById(R.id.edCampo_570);
        final TextView tvCONTADOR = (TextView) dialog.findViewById(R.id.lbl_570_contador);
        final TextView tvMensagem = (TextView) dialog.findViewById(R.id.txt_570_error);

        tvMensagem.setText("");

        if (maxlenght > 0) edCampo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxlenght)});


        switch (tipo) {

            case 0:  //texto maiusculo

                edCampo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                break;

            case 1:  //numerico

                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                break;

            case 2: //data

                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                edCampo.addTextChangedListener(Mask.insert("##/##/####", edCampo));

                break;

            case 3: //telefone

                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                break;

            case 4: //url

                edCampo.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

                break;

            case 5: //email

                edCampo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                break;

            case 6: //FLOAT

                edCampo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                break;

            case 7: //cnpj

                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                edCampo.addTextChangedListener(Mask.insert("##.###.###/####-##", edCampo));

                break;

            case 8: //CEP

                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                edCampo.addTextChangedListener(Mask.insert("#####-###", edCampo));

                break;

            case 9: //DATA

                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                edCampo.addTextChangedListener(Mask.insert("##/##/####", edCampo));

                edCampo.setHint("dd/mm/aaaa");

                break;

            case 10:

                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                edCampo.addTextChangedListener(Mask.insert("###.###.###.####", edCampo));

                break;

            case 11:

                edCampo.setHeight(500);

                edCampo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                break;

            default:

                edCampo.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                break;
        }


        if (App.precliente.getTypeByName(campo).equals(ObjRegister._float)){

            Float value = (Float) App.precliente.getFieldByName(campo);

            if (value == 0){

                edCampo.setText("");

            } else {

                edCampo.setText(format_02.format(value));

            }



        } else {

            edCampo.setText((String) App.precliente.getFieldByName(campo));

        }


        try {

            edCampo.setSelection(0,edCampo.getText().toString().length());

            edCampo.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {

                        Integer com = s.toString().trim().length();

                        tvCONTADOR.setText(String.valueOf(com));


                    } catch (Exception e) {

                        Log.i("SAV", e.getMessage());

                    }

                }
            });


        } catch (Exception e) {


        }


        tvtexto1.setText(mensa);

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if ("CADASTRO".contains(campo)) {

                    try {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                        sdf.setLenient(false);

                        sdf.parse(edCampo.getText().toString());


                        Date emissao  = sdf.parse(App.precliente.getCADASTRO());

                        if (sdf.parse(edCampo.getText().toString()).compareTo(emissao) < 0){

                            tvMensagem.setText("Data Não Poderá Ficar Em branco.");

                            return;

                        }


                    } catch (java.text.ParseException e) {


                        tvMensagem.setText("Data Inválida !!");


                        return;

                    }
                }

                if (campo.equals("LIMITE")) {

                    Float value;

                    try {

                        value = Float.valueOf(edCampo.getText().toString().replaceAll(",", "."));

                        if (value.isNaN()){

                            tvMensagem.setText("Valor Inválido !!");

                            return;

                        }

                        dialog.dismiss();

                        App.precliente.setFieldByName(campo, value);

                        refresh();

                    } catch (Exception e){

                        tvMensagem.setText("Valor Inválido !!");

                    }

                    return;
                }


                dialog.dismiss();

                App.precliente.setFieldByName(campo, edCampo.getText().toString());

                refresh();

            }


        });

        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
            }

        });


        dialog.show();


    }

    private void clickPesquisaCodCidade(View v,String label,String campo){

        Intent i = new Intent(PreClienteComercialActivity.this, Help20Activity.class);
        Bundle params = new Bundle();
        params.putString("ARQUIVO", "CIDADE");
        params.putString("TITULO", "CADASTRO DE CIDADES");
        params.putString("MULTICHOICE", "N");
        params.putString("ALIAS", "CIDADE");
        params.putString("ALIASVALUES", "");
        i.putExtras(params);
        startActivityForResult(i, HelpInformation.HelpCidade);


    }

    private void clickPesquisaCodCanal(){

        Intent i = new Intent(PreClienteComercialActivity.this, Help20Activity.class);
        Bundle params = new Bundle();
        params.putString("ARQUIVO", "CANAL");
        params.putString("TITULO", "CADASTRO DE CANAIS");
        params.putString("MULTICHOICE", "N");
        params.putString("ALIAS", "CANAL");
        params.putString("ALIASVALUES", "");
        i.putExtras(params);
        startActivityForResult(i, HelpInformation.HelpCanal);


    }

    private void clickPesquisaTabPreco(){

        Intent i = new Intent(PreClienteComercialActivity.this, Help20Activity.class);
        Bundle params = new Bundle();
        params.putString("ARQUIVO", "TABPRECOCABEC");
        params.putString("TITULO", "CADASTRO TABELA DE PREÇOS");
        params.putString("MULTICHOICE", "N");
        params.putString("ALIAS", "TABPRECO");
        params.putString("ALIASVALUES", "");
        i.putExtras(params);
        startActivityForResult(i, HelpInformation.HelpTabPreco);

    }

    private void clickPesquisaCodRede(){

        Intent i = new Intent(PreClienteComercialActivity.this, Help20Activity.class);
        Bundle params = new Bundle();
        params.putString("ARQUIVO", "REDE");
        params.putString("TITULO", "CADASTRO DE REDE");
        params.putString("MULTICHOICE", "N");
        params.putString("ALIAS", "REDE");
        params.putString("ALIASVALUES", "");
        i.putExtras(params);
        startActivityForResult(i, HelpInformation.HelpRede);

    }

    private void clickPesquisaCondPagto(){

        Intent i = new Intent(PreClienteComercialActivity.this, Help20Activity.class);
        Bundle params = new Bundle();
        params.putString("ARQUIVO"    , "CONDPAGTO");
        params.putString("TITULO"     , "CADASTRO DE COND. DE PAGTO");
        params.putString("MULTICHOICE", "N");
        params.putString("ALIAS"      , "CONDPAGTO");
        params.putString("ALIASVALUES", "");
        i.putExtras(params);
        startActivityForResult(i, HelpInformation.HelpCondPagto);

    }

    private void clickPesquisaPolitica(){

        Intent i = new Intent(PreClienteComercialActivity.this, Help20Activity.class);
        Bundle params = new Bundle();
        params.putString("ARQUIVO"    , "REGIAO");
        params.putString("TITULO"     , "CADASTRO DE REGIÕES");
        params.putString("MULTICHOICE", "N");
        params.putString("ALIAS"      , "REGIAO");
        params.putString("ALIASVALUES", "");
        i.putExtras(params);
        startActivityForResult(i, HelpInformation.HelpRegiao);

    }

    private void clickSN(View v,String label,final String campo){


        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.dlgsimnao);

        dialog.setTitle(label);

        final Button confirmar    = (Button) dialog.findViewById(R.id.btn_040_ok);
        final Button cancelar     = (Button) dialog.findViewById(R.id.btn_040_can);
        final TextView tvtexto1   = (TextView) dialog.findViewById(R.id.txt_040_texto1);
        final Spinner spSimNao   = (Spinner) dialog.findViewById(R.id.spSimNao);

        List<String> list = new ArrayList<String>();
        list.add("SIM");
        list.add("NAO");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSimNao.setAdapter(dataAdapter);

        if (App.precliente.getFieldByName(campo).equals("SIM")){

            spSimNao.setSelection(0);

        } else {

            spSimNao.setSelection(1);

        }

        tvtexto1.setText("Escolha Uma Das Opcoes:");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

                if (campo.equals("BOLETO"))      tvBoleto.setText((String) spSimNao.getSelectedItem());

                if (campo.equals("TAXA"))        tvTaxa.setText((String) spSimNao.getSelectedItem());

                if (campo.equals("OPSIMPLES"))   tvSimplesOP.setText((String) spSimNao.getSelectedItem());

                if (campo.equals("ISENTOST"))    tvIsentoST.setText((String) spSimNao.getSelectedItem());

                App.precliente.setFieldByName(campo,(String) spSimNao.getSelectedItem());

                refresh();

            }



        });

        cancelar.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {

                //finaliza o dialog
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    private void clickTi(View v,String label,final String campo){

        Pessoas pessoas = new Pessoas();

        int id;

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.dlgsimnao);

        dialog.setTitle(label);

        final Button confirmar    = (Button)   dialog.findViewById(R.id.btn_040_ok);
        final Button cancelar     = (Button)   dialog.findViewById(R.id.btn_040_can);
        final TextView tvtexto1   = (TextView) dialog.findViewById(R.id.txt_040_texto1);
        final Spinner  spSimNao   = (Spinner)  dialog.findViewById(R.id.spSimNao);

        List<String> list = new ArrayList<String>();

        for(Pessoa p : pessoas.getLsPessoas()){

            list.add(p.getDESCRICAO());

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSimNao.setAdapter(dataAdapter);

        id = pessoas.getIndice((String) App.precliente.getFieldByName(campo));

        if (id == -1){

            spSimNao.setSelection(0);

        } else {

            spSimNao.setSelection(id);
        }

        tvtexto1.setText("Escolha Uma Das Opcoes:");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

                App.precliente.setFieldByName(campo,(String) spSimNao.getSelectedItem());

                refresh();

            }

        });

        cancelar.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {

                //finaliza o dialog
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    public void clickClienteEntrega(View v){

        Intent i = new Intent(PreClienteComercialActivity.this, Help20Activity.class);
        Bundle params = new Bundle();
        params.putString("ARQUIVO", "CLIENTE");
        params.putString("TITULO", "CADASTRO DE CLIENTES");
        params.putString("MULTICHOICE", "N");
        params.putString("ALIAS", "CLIENTE");
        params.putString("ALIASVALUES", "");
        i.putExtras(params);
        startActivityForResult(i, HelpInformation.HelpCliente);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1 && requestCode == HelpInformation.HelpCidade) {

            if (data.hasExtra("CODIGO")) {

                App.precliente.setCODCIDADE(data.getExtras().getString("CODIGO"));

                App.precliente.setESTADO(data.getExtras().getString("ESTADO"));

                App.precliente.setCIDADE(data.getExtras().getString("CIDADE"));

                App.precliente.setDDD(data.getExtras().getString("DDD"));

                refresh();

            }
        }




        if (resultCode == 1 && requestCode == HelpInformation.HelpCanal) {

            if (data.hasExtra("CODIGO")) {

                App.precliente.setCANAL(data.getExtras().getString("CODIGO"));

                App.precliente.setCANALDESCRI(data.getExtras().getString("DESCRICAO"));

                if (data.getExtras().getString("TAXAFIN").equals("S"))  {

                    App.precliente.setTAXA("SIM");

                }

                else {

                    App.precliente.setTAXA("NÃO");
                }

                refresh();

            }
        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpTabPreco) {

            if (data.hasExtra("CODIGO")) {

                App.precliente.setTABPRECO(data.getExtras().getString("CODIGO"));

                App.precliente.setTABPRECODESCRI(data.getExtras().getString("DESCRICAO"));

                refresh();

            }
        }


        if (resultCode == 1 && requestCode == HelpInformation.HelpRegiao) {

            if (data.hasExtra("CODIGO")) {

                App.precliente.setPOLITICA(data.getExtras().getString("CODIGO"));
                App.precliente.setPOLITICADESCRI(data.getExtras().getString("DESCRICAO"));


                refresh();

            }
        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpRede) {

            if (data.hasExtra("CODIGO")) {

                App.precliente.setREDE(data.getExtras().getString("CODIGO"));

                App.precliente.setREDEDESCRI(data.getExtras().getString("DESCRICAO"));

                refresh();

            }
        }


        if (resultCode == 1 && requestCode == HelpInformation.HelpCondPagto) {

            if (data.hasExtra("CODIGO")) {

                App.precliente.setCONDPAGTO(data.getExtras().getString("CODIGO"));
                App.precliente.setCONDPAGTODESCRI(data.getExtras().getString("DESCRICAO"));

                refresh();

            }
        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpCliente) {

            if (data.hasExtra("CODIGO")) {

                String codigo = data.getExtras().getString("CODIGO");

                String loja   = data.getExtras().getString("LOJA");

                Cliente_fast cliente = null;

                try {

                     ClienteDAO dao = new ClienteDAO();

                     dao.open();

                    cliente = dao.seek_fast(codigo,loja,"");

                     dao.close();

                }  catch (Exception e)
                {
                    toast("Erro: " + e.getMessage());
                }

                if ( cliente != null ) {


                    Pessoas pes = new Pessoas();

                    try {
                        App.precliente.setRAZAO(cliente.getRAZAO());
                        App.precliente.setFANTASIA(cliente.getFANTASIA());
                        App.precliente.setPESSOA(pes.getPessoa(pes.getIndiceLetra(cliente.getPESSOA().trim())));
                        App.precliente.setCNPJ(format("##.###.###/####-##",cliente.getCNPJ()));
                        App.precliente.setRG("");
                        App.precliente.setIE(format("###.###.###.###",cliente.getIE()));
                        App.precliente.setCLIENTEENTREGA("1");
                        App.precliente.setIM("");
                        App.precliente.setLOGRADOURO("");
                        App.precliente.setENDERECO("");
                        App.precliente.setNRO("");
                        App.precliente.setCOMPLEMENTO("");
                        App.precliente.setBAIRRO("");
                        App.precliente.setCODCIDADE(cliente.getCODCIDADE());
                        App.precliente.setCEP("");
                        App.precliente.setDDD("");
                        App.precliente.setTELEFONE("");
                        App.precliente.setCELULAR("");
                        App.precliente.setHOMEPAGE("");
                        if (!cliente.getEMAILNFE().trim().isEmpty()){

                            App.precliente.setEMAILNFE(cliente.getEMAILNFE());

                        } else {

                            App.precliente.setEMAILNFE(cliente.getEMAIL());

                        }
                        App.precliente.setEMAIL(cliente.getEMAIL());
                        if (cliente.getFUNDACAO().trim().length() == 0){

                            App.precliente.setFUNDACAO("");

                        } else {

                            App.precliente.setFUNDACAO(cliente.getFUNDACAO().substring(6, 8)+"/"+cliente.getFUNDACAO().substring(4, 6)+"/"+cliente.getFUNDACAO().substring(0, 4));

                        }
                        App.precliente.setCANAL(cliente.getCANAL());
                        App.precliente.setCANALDESCRI(cliente.get_CANAL());

                        App.precliente.setREDE(cliente.getREDE());
                        App.precliente.setREDEDESCRI(cliente.get_REDE());

                        App.precliente.setPOLITICA(cliente.getREGIAO());
                        App.precliente.setPOLITICADESCRI(cliente.get_POLITICA());


                        App.precliente.setTABPRECO(cliente.getTABELA());
                        App.precliente.setTABPRECODESCRI(cliente.get_TABELA());

                        App.precliente.setCONDPAGTO(cliente.getCONDPAGTO());
                        App.precliente.setCONDPAGTODESCRI(cliente.get_COND());

                        App.precliente.setBOLETO(cliente.getBOLETO());
                        App.precliente.setTAXA(cliente.getTAXAFIN());
                        App.precliente.setLIMITE(cliente.getLIMITE());
                        App.precliente.setISENTOST(cliente.getISENTOST());
                        App.precliente.setOPSIMPLES(cliente.getSIMPLES());
                        App.precliente.setICMS(cliente.getICMS());

                        //cliente.ValidaAll(CadClienteActivity.this);

                        refresh();


                    } catch (Exception e){

                        toast(e.getMessage());

                    }

                }
            }
        }
    }

    private class BUSCAThread extends Thread{
        private Handler mHandler;

        private PreCliente    	cli;
        private Bundle params = new Bundle();

        public BUSCAThread(Handler handler,PreCliente cli){

            super();

            mHandler=handler;

            this.cli = cli;

        }

        @Override

        public void run(){

            String responseBody = "";

            try {

                params.putString("erro","---");
                params.putString("msgerro","");
                sendmsg(params);



                HttpClient httpclient = new DefaultHttpClient();
                try {

                    HttpGet httpget = new HttpGet("http://webservice.kinghost.net/web_cep.php?auth=295f27ba46a8d6f28ba813c0342465f7&formato=json&cep="+cli.getCEP().replaceAll("[-]",""));
                    // Create a response handler
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();

                    responseBody = httpclient.execute(httpget, responseHandler);

                    JSONObject result = new JSONObject(responseBody);

                    Cidade jcidade;

                    Logradouros logra = new Logradouros() ;

                    String codcidade        = "";
                    String logradouro       = result.getString("logradouro");
                    String tipoDeLogradouro = result.getString("tipo_logradouro");
                    String Abre_logradouro  = logra.get(logra.getValueByDescricao(tipoDeLogradouro)).getAbreDescri();
                    String bairro           = result.getString("bairro").toUpperCase();
                    String cidade           = result.getString("cidade").toUpperCase();
                    String estado           = result.getString("uf").toLowerCase();

                    /* procura o codigo da cidade */

                    try

                    {

                        CidadeDAO dao = new CidadeDAO();

                        dao.open();

                        jcidade = dao.seekCidade(removeAcentos(cidade.toUpperCase()),estado.toUpperCase());

                        dao.close();

                        if (jcidade == null){

                            jcidade = new Cidade();

                        }

                    } catch (Exception e) {


                        jcidade  = new Cidade("","","","");

                    }


                    cli.setLOGRADOURO(tipoDeLogradouro.toUpperCase());
                    cli.setENDERECO(logradouro.toUpperCase());
                    cli.setBAIRRO(removeAcentos(bairro.toUpperCase()));

                    if (jcidade.getCODIGO().equals("")){

                        cli.setCODCIDADE("");
                        cli.setCIDADE(removeAcentos(cidade.toUpperCase()));
                        cli.setESTADO(estado.toUpperCase());


                    } else {

                        cli.setLOGRADOURO(Abre_logradouro);
                        cli.setCODCIDADE(jcidade.getCODIGO());
                        cli.setCIDADE(removeAcentos(jcidade.getCIDADE()));
                        cli.setESTADO(jcidade.getESTADO());
                        if (cli.getDDD().equals("")) {

                            cli.setDDD(jcidade.getDDD());

                        }

                    }


                } catch (ClientProtocolException e) {
                    params.putString("erro"     , "020");
                    params.putString("msgerro"  , "ENDEREÇO NAO ENCONTRADO !!!");
                    sendmsg(params);
                } catch (IOException e) {
                    params.putString("erro"     , "020");
                    params.putString("msgerro"  , "ENDEREÇO NAO ENCONTRADO");
                    sendmsg(params);
                } finally {

                    httpclient.getConnectionManager().shutdown();

                    params.putString("erro"     , "000");
                    params.putString("msgerro"  , "");
                    sendmsg(params);

                }


            } catch (Exception e)
            {

                params.putString("erro"     , "020");
                params.putString("msgerro"  , e.getMessage());
                sendmsg(params);

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

    private String removeAcentos(String str) {

        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;

    }

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg){

            if (msg.getData().getString("erro").equals("---")){

                dialog = ProgressDialog.show(PreClienteComercialActivity.this,"Broto Legal","Acessando Servidores.Aguarde !!", false, true);
                dialog.setCancelable(false);
                dialog.show();


            } else {

                if (dialog != null)

                    if (dialog.isShowing()){


                        dialog.dismiss();


                    }

                if ( msg.getData().getString("erro").equals("000") ) {


                    refresh();


                }
                else

                {

                    Toast.makeText(PreClienteComercialActivity.this, msg.getData().getString("msgerro"), Toast.LENGTH_LONG).show();

                }

            }


        }

    };



    private static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            return mask.valueToString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
