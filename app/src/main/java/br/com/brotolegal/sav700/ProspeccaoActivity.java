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
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.sav700.help20.Help20Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.config.MaskFormatter;
import br.com.brotolegal.savdatabase.dao.CidadeDAO;
import br.com.brotolegal.savdatabase.dao.ProspeccaoDAO;
import br.com.brotolegal.savdatabase.entities.Cidade;
import br.com.brotolegal.savdatabase.entities.Prospeccao;
import br.com.brotolegal.savdatabase.util.Logradouro;
import br.com.brotolegal.savdatabase.util.Logradouros;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProspeccaoActivity extends AppCompatActivity {

    private TextView tvID;
    private TextView tvPRECLIENTE;
    private TextView tvPROTHEUS;
    private TextView tvCNPJ;
    private TextView tvIE;
    private TextView tvSTATUS;
    private TextView tvRAZAO;
    private TextView tvFANTASIA;
    private TextView tvLOGRADOURO;
    private TextView tvENDERECO;
    private TextView tvNRO;
    private TextView tvCOMPLEMENTO;
    private TextView tvBAIRRO;
    private TextView tvCODIGO_CIDADE;
    private TextView tvESTADO;
    private TextView tvCIDADE;
    private TextView tvCEP;
    private TextView tvDDD;
    private TextView tvTELEFONE;
    private TextView tvCONTATO;
    private TextView tvOBS;
    private TextView tvEMAIL;

    private TextView tvCONTADOR;

    private String ID;

    private Prospeccao prospeccao = new Prospeccao();

    private final int OperacaoInclusao  = 0;
    private final int OperacaoAlteracao = 1;
    private final int OperacaoConsulta  = 2;

    private int Operacao           = 2;

    private BUSCAThread BuscaThread;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospeccao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbProspeccao);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Prospeção de Clientes");
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent i = getIntent();

        if (i != null) {

            Bundle extras = i.getExtras();

            ID = extras.getString("ID", "");

        } else {

            finish();

        }

        try {

            Init();

            loadProspeccao();

        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            finish();


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prospeccao, menu);

        MenuItem iGravar  = menu.findItem(R.id.prospecao_menu_ok);
        MenuItem iExcluir = menu.findItem(R.id.prospecao_menu_excluir);


        if (prospeccao.getSTATUS().compareTo("1") > 0){

            iGravar.setVisible(false);

            iExcluir.setVisible(false);

        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.prospecao_menu_ok:


                try {

                    switch (Operacao) {

                        case OperacaoInclusao: {

                            if (prospeccao.ValidaAll()){

                                prospeccao.setSTATUS("1");

                            } else {

                                prospeccao.setSTATUS("0");

                            };

                            ProspeccaoDAO dao = new ProspeccaoDAO();

                            dao.open();

                            Prospeccao pro = dao.insert(prospeccao);

                            dao.close();

                            if (pro != null) {

                                Toast.makeText(ProspeccaoActivity.this, "Prospecção Gravada Com Sucesso !", Toast.LENGTH_SHORT).show();

                                finish();

                            } else {

                                Toast.makeText(ProspeccaoActivity.this, "Falha Na Gravação Da Prospecção", Toast.LENGTH_SHORT).show();

                            }

                            break;
                        }

                        case OperacaoAlteracao: {

                            if (prospeccao.ValidaAll()){

                                prospeccao.setSTATUS("1");

                            } else {

                                prospeccao.setSTATUS("0");

                            };

                            ProspeccaoDAO dao = new ProspeccaoDAO();

                            dao.open();

                            Boolean lok  = dao.Update(prospeccao);

                            dao.close();

                            if (lok) {

                                Toast.makeText(ProspeccaoActivity.this, "Prospecção Alterada Com Sucesso !", Toast.LENGTH_SHORT).show();

                                finish();

                            } else {

                                Toast.makeText(ProspeccaoActivity.this, "Falha Na Alteração Da Prospecção", Toast.LENGTH_SHORT).show();

                            }

                            break;
                        }


                        default: {

                            break;
                        }
                    }

                } catch (Exception e){


                    Toast.makeText(ProspeccaoActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                break;


            case R.id.prospecao_menu_cancela:

                finish();

                break;


            case R.id.prospecao_menu_excluir: {

                try {

                    ProspeccaoDAO dao = new ProspeccaoDAO();

                    dao.open();

                    dao.Delete(new String[] {prospeccao.getID()});

                    dao.close();

                    finish();

                    break;
                }
                catch (Exception e){


                    Toast.makeText(ProspeccaoActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Boolean refresh = false;

        if (resultCode == 1 && requestCode == HelpInformation.HelpCidade) {

            String codigo = "";
            String estado = "";
            String cidade = "";

            try {

                if (data.hasExtra("CODIGO")) {

                    codigo = data.getExtras().getString("CODIGO");

                }

                if (data.hasExtra("ESTADO")) {

                    estado = data.getExtras().getString("ESTADO");

                }

                if (data.hasExtra("CIDADE")) {

                    cidade = data.getExtras().getString("CIDADE");

                }

                prospeccao.setCODCIDADE(codigo);
                prospeccao.setESTADO(estado);
                prospeccao.setCIDADE(cidade);

                refresh();

            } catch (Exception e) {

                Toast.makeText(ProspeccaoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }



    private void Init() {


        tvID = (TextView) findViewById(R.id.txt_id_555);

        tvPRECLIENTE = (TextView) findViewById(R.id.txt_precliente_555);

        tvPROTHEUS = (TextView) findViewById(R.id.txt_protheus_555);

        tvCNPJ = (TextView) findViewById(R.id.txt_cnpj_555);

        tvIE = (TextView) findViewById(R.id.txt_ie_555);

        tvSTATUS = (TextView) findViewById(R.id.txt_status_555);

        tvRAZAO = (TextView) findViewById(R.id.txt_razao_555);

        tvNRO = (TextView) findViewById(R.id.txt_numero_555);

        tvFANTASIA = (TextView) findViewById(R.id.txt_fantasi_555);

        tvLOGRADOURO = (TextView) findViewById(R.id.txt_logradouro_555);

        tvENDERECO = (TextView) findViewById(R.id.txt_endereco_555);

        tvCOMPLEMENTO = (TextView) findViewById(R.id.txt_complemento_555);

        tvBAIRRO = (TextView) findViewById(R.id.txt_bairro_555);

        tvCODIGO_CIDADE = (TextView) findViewById(R.id.txt_codigo_cidade_555);

        tvESTADO = (TextView) findViewById(R.id.txt_estado_555);

        tvCIDADE = (TextView) findViewById(R.id.txt_cidade_555);

        tvCEP = (TextView) findViewById(R.id.txt_cep_555);

        tvDDD = (TextView) findViewById(R.id.txt_ddd_555);

        tvTELEFONE = (TextView) findViewById(R.id.txt_telefone_555);

        tvCONTATO = (TextView) findViewById(R.id.txt_contato_555);

        tvOBS = (TextView) findViewById(R.id.txt_obs_555);

        tvEMAIL = (TextView) findViewById(R.id.txt_email_555);

    }

    private void loadProspeccao() throws Exception {

        try {

            if (ID.isEmpty()) {

                prospeccao = new Prospeccao();

                prospeccao.setID(App.getNewID());

                prospeccao.setSTATUS("0");

                prospeccao.setDATA(App.getHoje());

                Operacao = OperacaoInclusao;


            } else {


                ProspeccaoDAO dao = new ProspeccaoDAO();

                dao.open();

                prospeccao = dao.seek(new String[]{ID});

                dao.close();

                if (prospeccao == null) {


                    throw new Exception("Registro Não Encontrado !!");

                }


                Operacao = OperacaoAlteracao;
            }

            refresh();

        } catch (Exception e) {

            throw new Exception(e.getMessage());


        }
    }

    public void clickRAZAO(View v) {


        clickTexto(v, "Razão Social.", "RAZAO", 0, "Tamanho Máximo Permitido 40 letras");


    }

    public void clickFANTASIA(View v) {


        clickTexto(v, "Nome Fantasia", "FANTASIA", 0, "Tamanho Máximo Permitido 20 letras");


    }

    public void clickCNPJ(View v) {


        clickTexto(v, "CNPJ", "CNPJ", 7, "Campo Não é OBRIGATÓRIO.");


    }

    public void clickIE(View v) {


        clickTexto(v, "Inscrição Estadual", "IE", 1, "Campo Não é OBRIGATÓRIO.");


    }

    public void clickLOGRADOURO(View v) {

        clickLogra(v, "Logradouro", "LOGRADOURO");

    }

    public void clickENDERECO(View v) {

        clickTexto(v, "Endereço", "ENDERECO", 0, "Campo OBRIGATÓRIO.");

    }

    public void clickNro(View v) {

        clickTexto(v, "Nº:", "NRO", 0, "Campo OBRIGATÓRIO.");

    }

    public void clickComplemento(View v) {

        clickTexto(v, "Complemnto", "COMPLEMENTO", 0, "Campo LIVRE.");

    }

    public void clickCodCidade(View v){


        if (prospeccao.getSTATUS().compareTo("1") > 0) {

            return;

        }


        Intent i = new Intent(ProspeccaoActivity.this, Help20Activity.class);
        Bundle params = new Bundle();
        params.putString("ARQUIVO", "CIDADE");
        params.putString("TITULO", "CADASTRO DE CIDADES");
        params.putString("MULTICHOICE", "N");
        params.putString("ALIAS", "CIDADE");
        params.putString("ALIASVALUES", "");
        i.putExtras(params);
        startActivityForResult(i, HelpInformation.HelpCidade);

    }

    public void clickBAIRRO(View v){


        clickTexto(v, "Bairro:", "BAIRRO", 0, "Campo LIVRE.");

    }

    public void clickCEP(View v){


        clickTexto(v, "CEP:", "CEP",3, "Campo LIVRE.");


    }

    public void clickDDD(View v){

        clickTexto(v, "DDD:", "DDD", 1, "Campo LIVRE.");


    }

    public void clickTelefone(View v){

        clickTexto(v, "Telefone:", "TELEFONE", 3, "Campo LIVRE.");

    }

    public void clickContato(View v){

        clickTexto(v, "Contato:", "CONTATO", 0, "Campo LIVRE.");

    }

    public void clickOBS(View v){

        clickTexto(v, "Observação:", "OBS", 0, "Campo LIVRE.");

    }

    public void clickEmail(View v){

        clickTexto(v, "Email:", "EMAIL", 5, "Campo LIVRE.");

    }

    private void clickTexto(View v, String label, final String campo, int tipo, String mensa) {

        if (prospeccao.getSTATUS().compareTo("1") > 0) {

            return;

        }

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.gettexttopadrao);

        dialog.setTitle(label);

        final Button confirmar = (Button) dialog.findViewById(R.id.btn_570_ok);
        final Button cancelar = (Button) dialog.findViewById(R.id.btn_570_can);
        final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_570_texto1);
        final EditText edCampo = (EditText) dialog.findViewById(R.id.edCampo_570);
        final TextView tvCONTADOR = (TextView) dialog.findViewById(R.id.lbl_570_contador);
        final TextView tvMensagem = (TextView) dialog.findViewById(R.id.txt_570_error);

        tvMensagem.setText(mensa);


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

                edCampo.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                //edCampo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

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

            default:

                edCampo.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                break;
        }


        edCampo.setText((String) prospeccao.getFieldByName(campo));


        try {

            edCampo.setSelection(edCampo.getText().toString().length());


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


        tvtexto1.setText("Digite O Nome Conteúdo do Campo");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //finaliza o dialog
                dialog.dismiss();

                prospeccao.setFieldByName(campo, edCampo.getText().toString());

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

    private void clickLogra(View v, String label, final String campo) {

        if (prospeccao.getSTATUS().compareTo("1") > 0) {

            return;

        }

        Logradouros logra = new Logradouros();

        int id;

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.dlgsimnao);

        dialog.setTitle(label);

        final Button confirmar = (Button) dialog.findViewById(R.id.btn_040_ok);
        final Button cancelar = (Button) dialog.findViewById(R.id.btn_040_can);
        final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);
        final Spinner spSimNao = (Spinner) dialog.findViewById(R.id.spSimNao);

        List<String> list = new ArrayList<String>();

        for (Logradouro l : logra.getLsLogradouros()) {

            list.add(l.getAbreDescri());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSimNao.setAdapter(dataAdapter);

        id = logra.getIndice((String) prospeccao.getFieldByName(campo));

        if (id == -1) {

            spSimNao.setSelection(0);

        } else {

            spSimNao.setSelection(id);
        }

        tvtexto1.setText("Escolha Uma Das Opções:");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

                tvLOGRADOURO.setText((String) spSimNao.getSelectedItem());

                prospeccao.setFieldByName(campo, (String) spSimNao.getSelectedItem());

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

    public void clickBUSCA(View v){

        if (prospeccao.getSTATUS().compareTo("1") > 0) {

            return;

        }


        try{

            BuscaThread=new BUSCAThread(mHandler,prospeccao);

            BuscaThread.start();



        }catch (Exception e)
        {

            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

        }



    }

    private void refresh() {


        if (prospeccao.getSTATUS().compareTo("1") <= 0) {
            if (prospeccao.ValidaAll()){

                prospeccao.setSTATUS("1");

            } else {

                prospeccao.setSTATUS("0");

            };
        }
        Drawable img = getResources().getDrawable(R.drawable.error5);

        img.setBounds(0, 0, 30, 30);

        tvID.setText(prospeccao.getID());

        if (!prospeccao.Validadador("ID")) tvID.setTextColor(getResources().getColor(R.color.red));

        else tvID.setTextColor(getResources().getColor(R.color.dark_blue));


        tvPRECLIENTE.setText(prospeccao.getPRECLIENTE());

        tvPROTHEUS.setText(prospeccao.getPROTHEUS());

        tvSTATUS.setText(prospeccao.get_STATUS());


        if (prospeccao.getSTATUS().equals("0")){

            tvSTATUS.setCompoundDrawables(img, null, null, null);


        } else {

            tvSTATUS.setCompoundDrawables(null, null, null, null);

        }



        tvRAZAO.setText(prospeccao.getRAZAO());

        if (!prospeccao.Validadador("RAZAO")) {

            tvRAZAO.setTextColor(getResources().getColor(R.color.red));

            tvRAZAO.setCompoundDrawables(img, null, null, null);

        } else {


            tvRAZAO.setTextColor(getResources().getColor(R.color.dark_blue));

            tvRAZAO.setCompoundDrawables(null, null, null, null);
        }

        tvFANTASIA.setText(prospeccao.getFANTASIA());


        if (!prospeccao.Validadador("FANTASIA")) {

            tvFANTASIA.setTextColor(getResources().getColor(R.color.red));

            tvFANTASIA.setCompoundDrawables(img, null, null, null);

        } else {


            tvFANTASIA.setTextColor(getResources().getColor(R.color.dark_blue));

            tvFANTASIA.setCompoundDrawables(null, null, null, null);
        }



        tvCNPJ.setText(prospeccao.getCNPJ());

        if (!prospeccao.Validadador("CNPJ")) {

            tvCNPJ.setTextColor(getResources().getColor(R.color.red));

            tvCNPJ.setCompoundDrawables(img, null, null, null);

        } else {

            tvCNPJ.setTextColor(getResources().getColor(R.color.dark_blue));

            tvCNPJ.setCompoundDrawables(null, null, null, null);

        }

        tvIE.setText(prospeccao.getIE());


        if (!prospeccao.Validadador("IE")) {

            tvIE.setTextColor(getResources().getColor(R.color.red));

            tvIE.setCompoundDrawables(img, null, null, null);

        } else {

            tvIE.setTextColor(getResources().getColor(R.color.dark_blue));

            tvIE.setCompoundDrawables(null, null, null, null);

        }
        tvLOGRADOURO.setText(prospeccao.getLOGRADOURO());

        if (!prospeccao.Validadador("LOGRADOURO")) {

            tvLOGRADOURO.setTextColor(getResources().getColor(R.color.red));

            tvLOGRADOURO.setCompoundDrawables(img, null, null, null);

        } else {


            tvLOGRADOURO.setTextColor(getResources().getColor(R.color.dark_blue));

            tvLOGRADOURO.setCompoundDrawables(null, null, null, null);
        }


        tvENDERECO.setText(prospeccao.getENDERECO());

        if (!prospeccao.Validadador("ENDERECO")) {

            tvENDERECO.setTextColor(getResources().getColor(R.color.red));

            tvENDERECO.setCompoundDrawables(img, null, null, null);

        } else {


            tvENDERECO.setTextColor(getResources().getColor(R.color.dark_blue));

            tvENDERECO.setCompoundDrawables(null, null, null, null);
        }


        tvNRO.setText(prospeccao.getNRO());

        if (!prospeccao.Validadador("NRO")) {

            tvNRO.setTextColor(getResources().getColor(R.color.red));

            tvNRO.setCompoundDrawables(img, null, null, null);

        } else {


            tvNRO.setTextColor(getResources().getColor(R.color.dark_blue));

            tvNRO.setCompoundDrawables(null, null, null, null);
        }


        tvBAIRRO.setText(prospeccao.getBAIRRO());

        if (!prospeccao.Validadador("BAIRRO")) {

            tvBAIRRO.setTextColor(getResources().getColor(R.color.red));

            tvBAIRRO.setCompoundDrawables(img, null, null, null);

        } else {


            tvBAIRRO.setTextColor(getResources().getColor(R.color.dark_blue));

            tvBAIRRO.setCompoundDrawables(null, null, null, null);
        }


        tvCOMPLEMENTO.setText(prospeccao.getCOMPLEMENTO());

        if (!prospeccao.Validadador("COMPLEMENTO")) {

            tvCOMPLEMENTO.setTextColor(getResources().getColor(R.color.red));

            tvCOMPLEMENTO.setCompoundDrawables(img, null, null, null);

        } else {


            tvCOMPLEMENTO.setTextColor(getResources().getColor(R.color.dark_blue));

            tvCOMPLEMENTO.setCompoundDrawables(null, null, null, null);
        }

        tvCODIGO_CIDADE.setText(prospeccao.getCODCIDADE());

        if (!prospeccao.Validadador("CODCIDADE")) {

            tvCODIGO_CIDADE.setTextColor(getResources().getColor(R.color.red));

            tvCODIGO_CIDADE.setCompoundDrawables(img, null, null, null);

        } else {


            tvCODIGO_CIDADE.setTextColor(getResources().getColor(R.color.dark_blue));

            tvCODIGO_CIDADE.setCompoundDrawables(null, null, null, null);
        }


        tvESTADO.setText(prospeccao.getESTADO());

        if (!prospeccao.Validadador("ESTADO")) {

            tvESTADO.setTextColor(getResources().getColor(R.color.red));

            tvESTADO.setCompoundDrawables(img, null, null, null);

        } else {


            tvESTADO.setTextColor(getResources().getColor(R.color.dark_blue));

            tvESTADO.setCompoundDrawables(null, null, null, null);
        }


        tvCIDADE.setText(prospeccao.getCIDADE());

        if (!prospeccao.Validadador("CIDADE")) {

            tvCIDADE.setTextColor(getResources().getColor(R.color.red));

            tvCIDADE.setCompoundDrawables(img, null, null, null);

        } else {

            tvCIDADE.setTextColor(getResources().getColor(R.color.dark_blue));

            tvCIDADE.setCompoundDrawables(null, null, null, null);
        }


        tvCEP.setText(prospeccao.getCEP());

        if (!prospeccao.Validadador("CEP")) {

            tvCEP.setTextColor(getResources().getColor(R.color.red));

            tvCEP.setCompoundDrawables(img, null, null, null);

        } else {


            tvCEP.setTextColor(getResources().getColor(R.color.dark_blue));

            tvCEP.setCompoundDrawables(null, null, null, null);
        }


        tvDDD.setText(prospeccao.getDDD());

        if (!prospeccao.Validadador("DDD")) {

            tvDDD.setTextColor(getResources().getColor(R.color.red));

            tvDDD.setCompoundDrawables(img, null, null, null);

        } else {


            tvDDD.setTextColor(getResources().getColor(R.color.dark_blue));

            tvDDD.setCompoundDrawables(null, null, null, null);
        }

        tvTELEFONE.setText(prospeccao.getTELEFONE());

        if (!prospeccao.Validadador("TELEFONE")) {

            tvTELEFONE.setTextColor(getResources().getColor(R.color.red));

            tvTELEFONE.setCompoundDrawables(img, null, null, null);

        } else {


            tvTELEFONE.setTextColor(getResources().getColor(R.color.dark_blue));

            tvTELEFONE.setCompoundDrawables(null, null, null, null);
        }


        tvCONTATO.setText(prospeccao.getCONTATO());

        if (!prospeccao.Validadador("CONTATO")) {

            tvCONTATO.setTextColor(getResources().getColor(R.color.red));

            tvCONTATO.setCompoundDrawables(img, null, null, null);

        } else {


            tvCONTATO.setTextColor(getResources().getColor(R.color.dark_blue));

            tvCONTATO.setCompoundDrawables(null, null, null, null);
        }

        tvEMAIL.setText(prospeccao.getEMAIL());


        if (!prospeccao.Validadador("EMAIL")) {

            tvEMAIL.setTextColor(getResources().getColor(R.color.red));

            tvEMAIL.setCompoundDrawables(img, null, null, null);

        } else {


            tvEMAIL.setTextColor(getResources().getColor(R.color.dark_blue));

            tvEMAIL.setCompoundDrawables(null, null, null, null);
        }


        tvOBS.setText(prospeccao.getOBS());

        if (!prospeccao.Validadador("OBS")) {

            tvOBS.setTextColor(getResources().getColor(R.color.red));

            tvOBS.setCompoundDrawables(img, null, null, null);

        } else {


            tvOBS.setTextColor(getResources().getColor(R.color.dark_blue));

            tvOBS.setCompoundDrawables(null, null, null, null);
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {



            try {


                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(ProspeccaoActivity.this, "Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
                    dialog.setCancelable(false);
                    dialog.show();

                }


                if ((msg.getData().getString("CERRO").equals("FEC"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    if (!msg.getData().getString("CMSGERRO").isEmpty()) {

                        Toast.makeText(ProspeccaoActivity.this, msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();

                    }

                    refresh();
                }

            } catch (Exception E) {

                Toast.makeText(ProspeccaoActivity.this,  E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };


    private class BUSCAThread extends Thread{
        private Handler mHandler;
        private Prospeccao cli;
        private Bundle params = new Bundle();

        public BUSCAThread(Handler handler,Prospeccao cli){

            super();

            mHandler=handler;

            this.cli = cli;

        }

        @Override

        public void run(){

            String responseBody = "";

            JSONObject result;

            OkHttpClient client = new OkHttpClient();

            try {

                params.putString("CERRO","---");
                params.putString("CMSGERRO","");
                sendmsg(params);



                URL url = new URL("http://webservice.kinghost.net/web_cep.php?auth=295f27ba46a8d6f28ba813c0342465f7&formato=json&cep="+cli.getCEP().replaceAll("[-]",""));

                client = new OkHttpClient();

                Request request = new Request.Builder().url(url).build();

                Response response = client.newCall(request).execute();

                String jsonDeResposta = response.body().string();

                try
                {
                    final String s = new String(jsonDeResposta.getBytes(), "ISO-8859-1");

                    jsonDeResposta = s;

                }
                catch (UnsupportedEncodingException e)
                {
                    throw new  Exception(e.getMessage());

                }

                try {

                    result  = new JSONObject(jsonDeResposta);

                } catch (JSONException e1) {

                    throw new  Exception(e1.getMessage());

                }

                Cidade jcidade;

                Logradouros logra = new Logradouros() ;

                String retorno          = result.getString("resultado_txt").replace("sucesso - ","");
                String codcidade        = "";
                String logradouro       = removeAcentos(result.getString("logradouro"));
                String tipoDeLogradouro = result.getString("tipo_logradouro");
                String Abre_logradouro  = logra.get(logra.getValueByDescricao(tipoDeLogradouro)).getAbreDescri();
                String bairro           = removeAcentos(result.getString("bairro").toUpperCase());
                String cidade           = removeAcentos(result.getString("cidade").toUpperCase());
                String estado           = result.getString("uf").toLowerCase();


                if (retorno.equals("cep não encontrado")){

                    throw new  Exception("cep não encontrado");

                }

                /* procura o codigo da cidade */

                try

                {

                    CidadeDAO dao = new CidadeDAO();

                    dao.open();

                    jcidade = dao.seekCidade(removeAcentos(cidade.toUpperCase()),estado.toUpperCase());

                    dao.close();

                    if (jcidade == null){


                        throw new  Exception("Cidade Não Encontrada No Arquivo Do Tablet.");

                    }

                } catch (Exception e) {


                    throw new  Exception(e.getMessage());

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

                params.putString("CERRO"     , "FEC");
                params.putString("CMSGERRO"  , retorno);
                sendmsg(params);

            } catch (Exception e) {

                params.putString("CERRO"     , "FEC");
                params.putString("CMSGERRO"  , e.getMessage());
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

        public String removeAcentos(String str) {

            str = Normalizer.normalize(str, Normalizer.Form.NFD);
            str = str.replaceAll("[^\\p{ASCII}]", "");
            return str;

        }


        private String format(String pattern, Object value) {
            MaskFormatter mask;
            try {
                mask = new MaskFormatter(pattern);
                return mask.valueToString(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
