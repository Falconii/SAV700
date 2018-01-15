package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.sav700.help20.Help20Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.PreAcordoDAO;
import br.com.brotolegal.savdatabase.dao.RedeDAO;
import br.com.brotolegal.savdatabase.dao.VerbaDAO;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.Rede;
import br.com.brotolegal.savdatabase.entities.Verba;

public class CadastroPreAcordoActivity extends AppCompatActivity {


    private String ID;
    private String CODVERBA;
    private String STATUS;

    private PreAcordo preacordo;

    private DecimalFormat format_02 = new DecimalFormat(",##0.00");
    private final int OperacaoInclusao  = 0;
    private final int OperacaoAlteracao = 1;
    private final int OperacaoConsulta  = 2;

    private int Operacao = 2;

    private Dialog dialog;

    defaultAdapter verbasadapter;
    defaultAdapter pagtoadapter;

    private TextView txt_mensagem_713;
    private TextView txt_id_713;
    private TextView txt_emissao_713;
    private TextView txt_status_713;
    private TextView txt_protheus_713;
    private Switch   swClienteRede_713;
    private TextView txt_codcli_713;
    private TextView txt_razao_713;
    private TextView txt_cnpj_713;
    private TextView txt_ie_713;
    private Spinner  sp_verba_713;
    private Spinner  sp_pagamento_713;
    private TextView lbl_codcli_713;
    private List<String[]> lsVerba;

    private TextView txt_dtinicial_713;
    private TextView txt_dtfinal_713;
    private TextView txt_dtpagto_713;
    private TextView txt_valor_713;
    private TextView txt_descricao_713;
    private TextView txt_obs_713;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_preacordo);

        preacordo =  new PreAcordo();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbpreacordo_cadastro_713);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Lançamento Pré-Acordo");
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent i = getIntent();

        if (i != null) {

            Bundle extras = i.getExtras();

            ID       = extras.getString("ID"      , "");
            CODVERBA = extras.getString("CODVERBA", "");
            STATUS   = extras.getString("STATUS"  , "");

        } else {

            finish();

        }

        try {

            Init();

            loadVerbas();

            loadPreAcordo();

            if (lsVerba.size() == 0){

                toast("Cadastro de Verbas Inválido !!!");

                finish();

            }

        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            finish();


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cadastro_preacordo, menu);

        MenuItem iGravar  = menu.findItem(R.id.preacordo_cadastro_menu_ok);
        MenuItem iExcluir = menu.findItem(R.id.preacordo_cadastro_menu_excluir);

        if (Operacao == OperacaoInclusao){

            iExcluir.setVisible(false);

        }


        if (Operacao == OperacaoConsulta){

            iGravar.setVisible(false);

            if (!STATUS.equals("8")) iExcluir.setVisible(false);

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

            case R.id.preacordo_cadastro_menu_ok:

                try {

                    switch (Operacao) {

                        case OperacaoInclusao: {

                            if (preacordo.ValidaAll()) {

                                preacordo.setSTATUS("1");

                            } else {

                                preacordo.setSTATUS("0");

                            }

                            if (preacordo.getCLIENTE().trim().isEmpty() && preacordo.getREDE().trim().isEmpty()){


                                throw  new Exception("Digite Pelo Menos O Cliente Ou A Rede !");


                            }

                            PreAcordoDAO dao = new PreAcordoDAO();

                            dao.open();

                            PreAcordo pre = dao.insert(preacordo);

                            dao.close();

                            if (pre != null) {

                                toast("Pré-Acordo  Gravado Com Sucesso !");

                                finish();

                            } else {

                                toast("Falha Na Gravação Do Pré-Acordo  !");

                            }

                            break;
                        }

                        case OperacaoAlteracao: {

                            if (preacordo.ValidaAll()) {

                                preacordo.setSTATUS("1");

                            } else {

                                preacordo.setSTATUS("0");

                            }

                            if (preacordo.getCLIENTE().trim().isEmpty() && preacordo.getREDE().trim().isEmpty()){


                                throw  new Exception("Digite Pelo Menos O Cliente Ou A Rede !");


                            }


                            PreAcordoDAO dao = new PreAcordoDAO();

                            dao.open();

                            Boolean lok = dao.Update(preacordo);

                            dao.close();

                            if (lok) {

                                toast("Pré-Acordo Alterado Com Sucesso !");

                                finish();

                            } else {

                                toast("Falha Na Alteração Do Pré-Acordo  !");


                            }

                            break;
                        }


                        default: {

                            break;
                        }
                    }

                } catch (Exception e) {

                    toast(e.getMessage());

                }

                break;

            case R.id.preacordo_cadastro_cancela:

                finish();

                break;


            case R.id.preacordo_cadastro_menu_excluir: {


                try {

                    final Dialog dialog = new Dialog(CadastroPreAcordoActivity.this);

                    dialog.setContentView(R.layout.dlglibped);

                    dialog.setTitle("EXCLUSÃO DE PRÉ-ACORDO");

                    final Button confirmar = (Button) dialog.findViewById(R.id.btn_040_ok);
                    final Button cancelar = (Button) dialog.findViewById(R.id.btn_040_can);
                    final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);
                    final TextView tvtexto2 = (TextView) dialog.findViewById(R.id.txt_040_texto2);

                    tvtexto1.setText("CONFIRMA A EXCLUSÃO");
                    tvtexto2.setText("PRÉ-ACORDO Nº "+preacordo.getCODMOBILE());

                    cancelar.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            //finaliza o dialog
                            dialog.dismiss();

                        }
                    });

                    confirmar.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            try

                            {

                                PreAcordoDAO dao = new PreAcordoDAO();

                                dao.open();

                                dao.Delete(new String[] {preacordo.getCODMOBILE()});

                                dao.close();

                                toast("Pré-Acordo Excluído !");

                                finish();

                            } catch (Exception e) {

                                toast(e.getMessage());

                            }


                            //finaliza o dialog
                            dialog.dismiss();


                        }

                    });


                    dialog.show();


                } catch (Exception e) {

                    toast(e.getMessage());

                }



            }
            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1 && requestCode == HelpInformation.HelpCliente) {

            String codigo = null;

            String loja = null;

            try {

                if (data.hasExtra("CODIGO")) {

                    codigo = data.getExtras().getString("CODIGO");

                }
                if (data.hasExtra("LOJA")) {

                    loja = data.getExtras().getString("LOJA");

                }

                ClienteDAO dao = new ClienteDAO();

                dao.open();

                Cliente cliente = dao.seek(new String[] {codigo,loja});

                dao.close();

                preacordo.setREDE("");
                preacordo.set_REDE("");

                if (cliente != null){

                    preacordo.setCLIENTE(codigo);
                    preacordo.setLOJA(loja);
                    preacordo.set_RAZAO(cliente.getRAZAO());
                    preacordo.set_CNPJ(App.cnpj_cpf(cliente.getCNPJ()));
                    preacordo.set_IE(cliente.getIE());

                } else {

                    preacordo.setCLIENTE("");
                    preacordo.setLOJA("");
                    preacordo.set_RAZAO("");
                    preacordo.set_CNPJ("");
                    preacordo.set_IE("");

                }

                refresh();


            } catch (Exception e) {

                toast(e.getMessage());
            }

        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpRede) {

            String codigo = null;


            try {

                if (data.hasExtra("CODIGO")) {

                    codigo = data.getExtras().getString("CODIGO");

                }

                RedeDAO dao = new RedeDAO();

                dao.open();

                Rede rede = dao.seek(new String[] {codigo});

                dao.close();

                preacordo.setCLIENTE("");
                preacordo.setLOJA("");
                preacordo.set_RAZAO("");
                preacordo.set_CNPJ("");
                preacordo.set_IE("");

                if (rede != null){

                    preacordo.setREDE(rede.getCODIGO());
                    preacordo.set_REDE(rede.getDESCRICAO());

                } else {

                    preacordo.setREDE("");
                    preacordo.set_REDE("");

                }

                refresh();


            } catch (Exception e) {

                toast(e.getMessage());
            }

        }

    }

    private void toast(String mensagem){

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void finish() {

        lsVerba = new ArrayList<String[]>();

        super.finish();

    }

    private void Init() {

        txt_mensagem_713    = (TextView) findViewById(R.id.txt_mensagem_713);
        txt_id_713          = (TextView) findViewById(R.id.txt_id_713);
        txt_emissao_713     = (TextView) findViewById(R.id.txt_emissao_713);
        txt_status_713      = (TextView) findViewById(R.id.txt_status_713);
        txt_protheus_713    = (TextView) findViewById(R.id.txt_protheus_713);
        swClienteRede_713   = (Switch)   findViewById(R.id.swClienteRede_713);
        lbl_codcli_713      = (TextView) findViewById(R.id.lbl_codcli_713);
        txt_codcli_713      = (TextView) findViewById(R.id.txt_codcli_713);
        txt_razao_713       = (TextView) findViewById(R.id.txt_razao_713);
        txt_cnpj_713        = (TextView) findViewById(R.id.txt_cnpj_713);
        txt_ie_713          = (TextView) findViewById(R.id.txt_ie_713);
        sp_verba_713        = (Spinner) findViewById(R.id.sp_verba_713);
        sp_pagamento_713    = (Spinner) findViewById(R.id.sp_pagamento_713);

        txt_dtinicial_713   = (TextView) findViewById(R.id.txt_dtinicial_713);
        txt_dtfinal_713     = (TextView) findViewById(R.id.txt_dtfinal_713);
        txt_dtpagto_713     = (TextView) findViewById(R.id.txt_dtpagto_713);

        txt_valor_713       = (TextView) findViewById(R.id.txt_valor_713);
        txt_descricao_713   = (TextView) findViewById(R.id.txt_descricao_713);
        txt_obs_713         = (TextView) findViewById(R.id.txt_obs_713);
        txt_descricao_713.setMovementMethod(new ScrollingMovementMethod());

    }

    private void loadVerbas() throws Exception{

        List<Verba> lsregistros = null;

        lsVerba = new ArrayList<>();

        try {

            VerbaDAO dao = new VerbaDAO();

            dao.open();

            lsregistros = dao.getAllByTipo("A");

            dao.close();

            lsVerba.add(new String[]{"", "Defina Uma Verba Por Favor !!!"});

            if (lsregistros.size() > 0){

                for( Verba ver :lsregistros){

                    lsVerba.add(new String[]{ver.getCODIGO(),ver.getDESCRICAO()});

                }

            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }

    }

    private void loadPreAcordo() throws Exception {

        try {

            if (ID.isEmpty()) {

                preacordo = new PreAcordo();

                preacordo.setCODMOBILE(App.getNewID());

                preacordo.setSTATUS("0");

                preacordo.setDATA(App.aaaammddToddmmaaaa(App.getHoje()));

                preacordo.setCODVEND(App.user.getCODVEN());

                preacordo.setNOMVEND(App.user.getNOME());

                Operacao = OperacaoInclusao;


            } else {


                PreAcordoDAO dao = new PreAcordoDAO();

                dao.open();

                preacordo = dao.seek(new String[]{ID});

                dao.close();

                if (preacordo == null) {


                    throw new Exception("Registro Não Encontrado !!");

                }

                if (preacordo.getSTATUS().compareTo("1") > 0) Operacao = OperacaoConsulta;
                else Operacao = OperacaoAlteracao;
            }

            refresh();

        } catch (Exception e) {

            throw new Exception(e.getMessage());


        }
    }

    public void clickCliente(View v) {

        if (Operacao == OperacaoConsulta) {

            return;

        }

         if ((!swClienteRede_713.isChecked())){
            Intent i = new Intent(CadastroPreAcordoActivity.this, Help20Activity.class);
            Bundle params = new Bundle();
            params.putString("ARQUIVO", "CLIENTE");
            params.putString("TITULO", "CADASTRO DE CLIENTES");
            params.putString("MULTICHOICE", "N");
            params.putString("ALIAS", "CLIENTE");
            params.putString("ALIASVALUES", "");
            i.putExtras(params);
            startActivityForResult(i, HelpInformation.HelpCliente);
        } else {
            Intent i = new Intent(CadastroPreAcordoActivity.this, Help20Activity.class);
            Bundle params = new Bundle();
            params.putString("ARQUIVO", "REDE");
            params.putString("TITULO", "CADASTRO DE REDES");
            params.putString("MULTICHOICE", "N");
            params.putString("ALIAS", "REDECLIENTE");
            params.putString("ALIASVALUES", "");
            i.putExtras(params);
            startActivityForResult(i, HelpInformation.HelpRede);
        }


    }

    public void clickInicial(View v) {

        if (Operacao == OperacaoConsulta) {

            return;

        }

        if (preacordo.getDATA().equals("")){

            toast("Informe Data Emissão Primeiro!");

            return ;

        }

        clickTexto(v, "Data Inicial", "DATAINI", 9, "Campo OBRIGATÓRIO.",0);


    }

    public void clickFinal(View v) {


        if (Operacao == OperacaoConsulta) {

            return;

        }

        if (preacordo.getDATAINI().equals("")){

            toast("Informe Data Inicial Primeiro!");

            return ;

        }


        clickTexto(v, "Data Final", "DATAFIM", 9, "Campo OBRIGATÓRIO.",0);


    }

    public void clickPagto(View v) {

        if (Operacao == OperacaoConsulta) {

            return;

        }

        if (!preacordo.getTIPOPAG().equals("T")){

            toast("Use Essa Data Apenas Para DEPÓSITO !");

            return ;

        }

        if (preacordo.getDATAINI().equals("")){

            toast("Informe Data Inicial Primeiro!");

            return ;

        }

        if (preacordo.getDATAFIM().equals("")){

            toast("Informe Data Final Primeiro!");

            return ;

        }

        clickTexto(v, "Data Pagto", "DATAPAGTO", 9, "",0);


    }

    public void clickValor(View v) {


        clickTexto(v, "Valor", "SLDINI", 6, "",0);


    }

    public void clickDescricao(View v) {

        clickMemo(v, "Descrição Do Acordo", "DESCRIC", "Digite Mensagem até 300 caracteres !",300);

    }

    public void clickObs(View v) {

        clickTexto(v, "Observação Do Contrato", "OBS", 0,"Digite Mensagem até 100 caracteres !",100);

    }

    private void clickTexto(View v, String label, final String campo, int tipo, String mensa, int maxlenght) {

        if (Operacao == OperacaoConsulta) {

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


        if (preacordo.getTypeByName(campo).equals(ObjRegister._float)){

            Float value = (Float) preacordo.getFieldByName(campo);

            if (value == 0){

                edCampo.setText("");

            } else {

                edCampo.setText(format_02.format(value));

            }



        } else {

            edCampo.setText((String) preacordo.getFieldByName(campo));

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


                if ("DATAINI#DATAFIM#DATAPAGTO".contains(campo)) {

                    try {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                        sdf.setLenient(false);

                        sdf.parse(edCampo.getText().toString());

                        if (campo.equals("DATAINI")){

                            Date emissao  = sdf.parse(preacordo.getDATA());

                            if (sdf.parse(edCampo.getText().toString()).compareTo(emissao) < 0){

                                tvMensagem.setText("Data Deverá Ser Posterior A Data Emissão ");

                                return;

                            }

                        }

                        if (campo.equals("DATAFIM")){

                            Date emissao  = sdf.parse(preacordo.getDATAINI());

                            if (sdf.parse(edCampo.getText().toString()).compareTo(emissao) < 0){

                                tvMensagem.setText("Data Deverá Ser Posterior A Data Inicial ");

                                return;

                            }

                        }


                        if (campo.equals("DATAPAGTO")){

                            Date emissao  = sdf.parse(preacordo.getDATAINI());

                            if (sdf.parse(edCampo.getText().toString()).compareTo(emissao) < 0){

                                tvMensagem.setText("Data Deverá Ser Posterior A Data Inicial ");

                                return;

                            }

                        }

                    } catch (java.text.ParseException e) {


                        tvMensagem.setText("Data Inválida !!");


                        return;

                    }
                }

                if (campo.equals("SLDINI")) {

                    Float value;

                    try {

                        value = Float.valueOf(edCampo.getText().toString().replaceAll(",", "."));

                        if (value.isNaN()){

                            tvMensagem.setText("Valor Inválido !!");

                            return;

                        }

                        preacordo.setSLDINI(value);

                        dialog.dismiss();

                        preacordo.setFieldByName(campo, edCampo.getText().toString());

                        refresh();

                    } catch (Exception e){

                        tvMensagem.setText("Valor Inválido !!");

                    }

                    return;
                }


                dialog.dismiss();

                preacordo.setFieldByName(campo, edCampo.getText().toString());

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

    private void clickMemo(View v, String label, final String campo,String mensa, int maxlenght) {

        if (Operacao == OperacaoConsulta) {

            return;

        }

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.getmemopadrao);

        dialog.setTitle(label);

        final Button confirmar = (Button) dialog.findViewById(R.id.btn_576_ok);
        final Button cancelar = (Button) dialog.findViewById(R.id.btn_576_can);
        final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_576_texto1);
        final EditText edCampo = (EditText) dialog.findViewById(R.id.edCampo_576);
        final TextView tvCONTADOR = (TextView) dialog.findViewById(R.id.lbl_576_contador);
        final TextView tvMensagem = (TextView) dialog.findViewById(R.id.txt_576_error);

        if (maxlenght > 0) edCampo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxlenght)});

        tvMensagem.setText(mensa);

        edCampo.setText((String) preacordo.getFieldByName(campo));


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


        tvtexto1.setText("");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

                preacordo.setFieldByName(campo, edCampo.getText().toString());

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

    private void refresh() {

        int index = 0;

        if (preacordo.getSTATUS().compareTo("1") <= 0) {

            if (preacordo.ValidaAll()){

                preacordo.setSTATUS("1");

            } else {

                preacordo.setSTATUS("0");

            };
        }

        final Drawable img = getResources().getDrawable(R.drawable.error5);

        img.setBounds(0, 0, 30, 30);

        txt_mensagem_713.setText(preacordo.getMENSAGEM());

        if (preacordo.getSTATUS().equals("0")){

            txt_status_713.setCompoundDrawables(img, null, null, null);


        } else {

            txt_status_713.setCompoundDrawables(null, null, null, null);

        }

        txt_id_713.setText(preacordo.getCODMOBILE());

        txt_emissao_713.setText((preacordo.getDATA()));

        txt_status_713.setText(preacordo.get_Status());

        txt_protheus_713.setText(preacordo.getNUM());

        if (Operacao == OperacaoConsulta){

            if (preacordo.getCLIENTE().trim().isEmpty() && preacordo.getREDE().trim().isEmpty()) {

                swClienteRede_713.setChecked(false);

                lbl_codcli_713.setText("Cód. CLIENTE");

            } else {

                if (!preacordo.getCLIENTE().trim().isEmpty()) {

                    swClienteRede_713.setChecked(false);

                    lbl_codcli_713.setText("Cód. CLIENTE");

                } else {

                    swClienteRede_713.setChecked(true);

                    lbl_codcli_713.setText("Cód. REDE");
                }
            }
            swClienteRede_713.setEnabled(false);

            if (!preacordo.getREDE().trim().isEmpty()) {

                txt_codcli_713.setText(preacordo.getREDE());


                txt_razao_713.setText(preacordo.get_REDE());


                txt_cnpj_713.setText("");


                txt_ie_713.setText("");

            } else {


                txt_codcli_713.setText(preacordo.getCLIENTE() + "-" + preacordo.getLOJA());


                txt_razao_713.setText(preacordo.get_RAZAO());


                txt_cnpj_713.setText(preacordo.get_CNPJ());


                txt_ie_713.setText(preacordo.get_IE());
            }



        } else {

            swClienteRede_713.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    if (isChecked) {

                        lbl_codcli_713.setText("Cód. REDE");

                        preacordo.setCLIENTE("");

                        preacordo.set_RAZAO("");

                        preacordo.set_IE("");

                        preacordo.set_CNPJ("");

                    } else {

                        lbl_codcli_713.setText("Cód. CLIENTE");

                        preacordo.setREDE("");

                        preacordo.set_REDE("");

                    }

                    txt_codcli_713.setText("");


                    txt_razao_713.setText("");


                    txt_cnpj_713.setText("");


                    txt_ie_713.setText("");

                    if (preacordo.ValidaAll()) {

                        preacordo.setSTATUS("1");

                    } else {

                        preacordo.setSTATUS("0");

                    }
                    ;


                    if (preacordo.getSTATUS().equals("0")) {

                        txt_status_713.setCompoundDrawables(img, null, null, null);


                    } else {

                        txt_status_713.setCompoundDrawables(null, null, null, null);

                    }

                    txt_status_713.setText(preacordo.get_Status());

                }

            });

            if (preacordo.getCLIENTE().trim().isEmpty() && preacordo.getREDE().trim().isEmpty()) {

                swClienteRede_713.setChecked(false);

            } else {

                if (!preacordo.getCLIENTE().trim().isEmpty()) {

                    swClienteRede_713.setChecked(false);

                } else {

                    swClienteRede_713.setChecked(true);
                }


            }

            if (swClienteRede_713.isChecked()) {

                txt_codcli_713.setText(preacordo.getREDE());


                txt_razao_713.setText(preacordo.get_REDE());


                txt_cnpj_713.setText("");


                txt_ie_713.setText("");

            } else {

                txt_codcli_713.setText(preacordo.getCLIENTE() + "-" + preacordo.getLOJA());


                txt_razao_713.setText(preacordo.get_RAZAO());


                txt_cnpj_713.setText(preacordo.get_CNPJ());


                txt_ie_713.setText(preacordo.get_IE());
            }


            if (swClienteRede_713.isChecked()) {

                if (!preacordo.Validadador("REDE")) {

                    txt_codcli_713.setTextColor(getResources().getColor(R.color.red));
                    txt_codcli_713.setCompoundDrawables(img, null, null, null);
                } else {

                    txt_codcli_713.setTextColor(getResources().getColor(R.color.dark_blue));
                    txt_codcli_713.setCompoundDrawables(null, null, null, null);
                }


            } else {

                if (!preacordo.Validadador("CLIENTE")) {

                    txt_codcli_713.setTextColor(getResources().getColor(R.color.red));
                    txt_codcli_713.setCompoundDrawables(img, null, null, null);
                } else {

                    txt_codcli_713.setTextColor(getResources().getColor(R.color.dark_blue));
                    txt_codcli_713.setCompoundDrawables(null, null, null, null);
                }
            }

        }

        if (preacordo.getCODVERB().equals("")){

            index = 0;

        } else {

            for(int x=0;x < lsVerba.size();x++){

                if (lsVerba.get(x)[0].equals(preacordo.getCODVERB())){

                    index = x;

                    break;

                }

            }
        }

        if (Operacao == OperacaoConsulta){

            sp_verba_713.setEnabled(false);

            verbasadapter = new defaultAdapter(CadastroPreAcordoActivity.this, R.layout.choice_default_row, lsVerba, "Verba", preacordo.Validadador("CODVERB"));

            sp_verba_713.setAdapter(verbasadapter);


            sp_verba_713.setSelection(index);



        } else {

            sp_verba_713.setEnabled(true);

            verbasadapter = new defaultAdapter(CadastroPreAcordoActivity.this, R.layout.choice_default_row, lsVerba, "Verba", preacordo.Validadador("CODVERB"));

            sp_verba_713.setAdapter(verbasadapter);

            sp_verba_713.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

            {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    verbasadapter.setEscolha(position);

                    Object lixo = sp_verba_713.getSelectedItem();

                    preacordo.setCODVERB(((String[]) lixo)[0]);

                    verbasadapter.setValido(preacordo.Validadador("CODVERB"));

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            verbasadapter.setIsInicializacao(true);

            sp_verba_713.setSelection(index);

        }

        if (preacordo.getTIPOPAG().equals("")){

            index = 0;

        } else {

            for(int x=0;x < preacordo.get_formaspagto().size();x++){

                if (preacordo.get_formaspagto().get(x)[0].equals(preacordo.getTIPOPAG())){

                    index = x;

                    break;

                }

            }
        }

        if (Operacao == OperacaoConsulta){

            sp_pagamento_713.setEnabled(false);

            pagtoadapter = new defaultAdapter(CadastroPreAcordoActivity.this, R.layout.choice_default_row, preacordo.get_formaspagto(), "Pagto:", preacordo.Validadador("TIPOPAG"));

            sp_pagamento_713.setAdapter(pagtoadapter);

            sp_pagamento_713.setSelection(index);

        } else {

            sp_pagamento_713.setEnabled(true);

            pagtoadapter = new defaultAdapter(CadastroPreAcordoActivity.this, R.layout.choice_default_row, preacordo.get_formaspagto(), "Pagto:", preacordo.Validadador("TIPOPAG"));

            sp_pagamento_713.setAdapter(pagtoadapter);

            sp_pagamento_713.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

            {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    pagtoadapter.setEscolha(position);

                    Object lixo = sp_pagamento_713.getSelectedItem();

                    preacordo.setTIPOPAG(((String[]) lixo)[0]);

                    pagtoadapter.setValido(preacordo.Validadador("TIPOPAG"));

                    if (!preacordo.Validadador("DATAPAGTO")) {

                        txt_dtpagto_713.setTextColor(getResources().getColor(R.color.red));
                        txt_dtpagto_713.setCompoundDrawables(img, null, null, null);
                    } else {

                        txt_dtpagto_713.setTextColor(getResources().getColor(R.color.dark_blue));
                        txt_dtpagto_713.setCompoundDrawables(null, null, null, null);
                    }

                    txt_dtpagto_713.setText(preacordo.getDATAPAGTO());

                    if (preacordo.ValidaAll()){

                        preacordo.setSTATUS("1");

                    } else {

                        preacordo.setSTATUS("0");

                    };

                    txt_status_713.setText(preacordo.get_Status());


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            pagtoadapter.setIsInicializacao(true);

            sp_pagamento_713.setSelection(index);

        }
        if (!preacordo.Validadador("DATAINI")) {

            txt_dtinicial_713.setTextColor(getResources().getColor(R.color.red));
            txt_dtinicial_713.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_dtinicial_713.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_dtinicial_713.setCompoundDrawables(null, null, null, null);
        }

        txt_dtinicial_713.setText(preacordo.getDATAINI());

        if (!preacordo.Validadador("DATAFIM")) {

            txt_dtfinal_713.setTextColor(getResources().getColor(R.color.red));
            txt_dtfinal_713.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_dtfinal_713.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_dtfinal_713.setCompoundDrawables(null, null, null, null);
        }

        txt_dtfinal_713.setText(preacordo.getDATAFIM());


        if (!preacordo.Validadador("DATAPAGTO")) {

            txt_dtpagto_713.setTextColor(getResources().getColor(R.color.red));
            txt_dtpagto_713.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_dtpagto_713.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_dtpagto_713.setCompoundDrawables(null, null, null, null);
        }

        txt_dtpagto_713.setText(preacordo.getDATAPAGTO());

        if (!preacordo.Validadador("SLDINI")) {

            txt_valor_713.setTextColor(getResources().getColor(R.color.red));
            txt_valor_713.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_valor_713.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_valor_713.setCompoundDrawables(null, null, null, null);
        }

        txt_valor_713.setText(format_02.format(preacordo.getSLDINI()));

        if (!preacordo.Validadador("DESCRIC")) {

            txt_descricao_713.setTextColor(getResources().getColor(R.color.red));
            txt_descricao_713.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_descricao_713.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_descricao_713.setCompoundDrawables(null, null, null, null);
        }

        txt_descricao_713.setText(preacordo.getDESCRIC());

        if (!preacordo.Validadador("OBS")) {

            txt_obs_713.setTextColor(getResources().getColor(R.color.red));
            txt_obs_713.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_obs_713.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_obs_713.setCompoundDrawables(null, null, null, null);
        }

        txt_obs_713.setText(preacordo.getOBS());


    }

    private class defaultAdapter extends ArrayAdapter {

        private int escolha = 0;

        private List<String[]> lista;

        private String label;

        private boolean valido;

        private boolean isInicializacao = true;

        private defaultAdapter(Context context, int textViewResourceId, List<String[]> objects,String label, boolean valido) {

            super(context, textViewResourceId, objects);

            this.lista = objects;

            this.label = label;

            this.valido = valido;
        }

        public String getOpcao(int pos){


            if ( (pos < this.lista.size() )){


                return lista.get(pos)[0];

            }

            return "";

        }

        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }

        public View getOpcoesView(int position, View convertView, ViewGroup parent) {

            String col01 = lista.get(position)[0];
            String col02 = lista.get(position)[1];

            LayoutInflater inflater =  getLayoutInflater();

            View layout = inflater.inflate(R.layout.choice_01_row, parent, false);

            TextView tvcol01 = (TextView) layout.findViewById(R.id.txt_col01_898);

            TextView tvcol02 = (TextView) layout.findViewById(R.id.txt_col02_898);

            tvcol01.setTextSize(14f);

            tvcol02.setTextSize(14f);


            tvcol01.setText(col01);

            tvcol02.setText(col02);

            tvcol01.setTextColor(Color.RED);

            tvcol02.setTextColor(Color.RED);

            ImageView img = (ImageView) layout.findViewById(R.id.im_flag_898);

            if (position == escolha) {

                img.setVisibility(View.VISIBLE);

                tvcol01.setTextColor(Color.BLACK);

                tvcol02.setTextColor(Color.BLACK);

            }

            return layout;
        }

        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            String obj = lista.get(position)[1];

            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            tvlabel.setText(this.label);

            if (this.label.isEmpty()){

                tvlabel.setVisibility(View.GONE);

            }

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setText(obj);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            if (valido){

                img.setVisibility(View.GONE);

            }   else
            {
                img.setImageResource(R.drawable.erro_20_vermelho);
            }

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

        public void setValido(boolean valido) {
            this.valido = valido;
        }
    }

}