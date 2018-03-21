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
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.Rede;
import br.com.brotolegal.savdatabase.entities.Verba;

public class CadastroNegociacaoActivity extends AppCompatActivity {


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

    private TextView txt_mensagem_714;
    private TextView txt_id_714;
    private TextView txt_emissao_714;
    private TextView txt_status_714;
    private TextView txt_protheus_714;
    private Switch   swClienteRede_714;
    private TextView txt_codcli_714;
    private TextView txt_razao_714;
    private TextView txt_cnpj_714;
    private TextView txt_ie_714;
    private Spinner  sp_condpagto_714;
    private Spinner  sp_tabpreco_714;
    private TextView lbl_codcli_714;
    private List<String[]> lscondpagto;
    private TextView txt_qtd_fardos_714;
    private TextView txt_sld_fardos_714;
    private TextView txt_qtd_aproveitamento_714;
    private TextView txt_sld_politica_714;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_negociacao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbnegociacao_cadastro_714);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Negociações");
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


        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            finish();


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cadastro_negociacao, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            default: {

                break;
            }
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

        //lsVerba = new ArrayList<String[]>();

        super.finish();

    }

    private void Init() {

        txt_mensagem_714           = (TextView) findViewById(R.id.txt_mensagem_714);
        txt_id_714                 = (TextView) findViewById(R.id.txt_id_714);
        txt_emissao_714            = (TextView) findViewById(R.id.txt_emissao_714);
        txt_status_714             = (TextView) findViewById(R.id.txt_status_714);
        txt_protheus_714           = (TextView) findViewById(R.id.txt_protheus_714);
        swClienteRede_714          = (Switch)   findViewById(R.id.swClienteRede_714);
        lbl_codcli_714             = (TextView) findViewById(R.id.lbl_codcli_714);
        txt_codcli_714             = (TextView) findViewById(R.id.txt_codcli_714);
        txt_razao_714              = (TextView) findViewById(R.id.txt_razao_714);
        txt_cnpj_714               = (TextView) findViewById(R.id.txt_cnpj_714);
        txt_ie_714                 = (TextView) findViewById(R.id.txt_ie_714);
        sp_condpagto_714           = (Spinner) findViewById(R.id.sp_condpagto_714);
        sp_tabpreco_714            = (Spinner) findViewById(R.id.sp_tabpreco_714);
        lbl_codcli_714             = (TextView) findViewById(R.id.txt_ie_714);
        txt_qtd_fardos_714         = (TextView) findViewById(R.id.txt_qtd_fardos_714);
        txt_sld_fardos_714         = (TextView) findViewById(R.id.txt_sld_fardos_714);
        txt_qtd_aproveitamento_714 = (TextView) findViewById(R.id.txt_qtd_aproveitamento_714) ;
        txt_sld_politica_714       = (TextView) findViewById(R.id.txt_sld_politica_714);

    }



    private void loadPedidos() throws Exception {

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

            //return;

        }

        if ((!swClienteRede_714.isChecked())){
            Intent i = new Intent(CadastroNegociacaoActivity.this, Help20Activity.class);
            Bundle params = new Bundle();
            params.putString("ARQUIVO", "CLIENTE");
            params.putString("TITULO", "CADASTRO DE CLIENTES");
            params.putString("MULTICHOICE", "N");
            params.putString("ALIAS", "CLIENTE");
            params.putString("ALIASVALUES", "");
            i.putExtras(params);
            startActivityForResult(i, HelpInformation.HelpCliente);
        } else {
            Intent i = new Intent(CadastroNegociacaoActivity.this, Help20Activity.class);
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

    public void clickPagto(View v) {

        if (Operacao == OperacaoConsulta) {

            return;

        }

    }

    public void clickQtdFardos(View v){


    }

    public void clickEntrega(View v){


    }



    private void refresh() {
/*
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

        txt_mensagem_714.setText(preacordo.getMENSAGEM());

        if (preacordo.getSTATUS().equals("0")){

            txt_status_714.setCompoundDrawables(img, null, null, null);


        } else {

            txt_status_714.setCompoundDrawables(null, null, null, null);

        }

        txt_id_714.setText(preacordo.getCODMOBILE());

        txt_emissao_714.setText((preacordo.getDATA()));

        txt_status_714.setText(preacordo.get_Status());

        txt_protheus_714.setText(preacordo.getNUM());

        if (Operacao == OperacaoConsulta){

            if (preacordo.getCLIENTE().trim().isEmpty() && preacordo.getREDE().trim().isEmpty()) {

                swClienteRede_714.setChecked(false);

                lbl_codcli_714.setText("Cód. CLIENTE");

            } else {

                if (!preacordo.getCLIENTE().trim().isEmpty()) {

                    swClienteRede_714.setChecked(false);

                    lbl_codcli_714.setText("Cód. CLIENTE");

                } else {

                    swClienteRede_714.setChecked(true);

                    lbl_codcli_714.setText("Cód. REDE");
                }
            }
            swClienteRede_714.setEnabled(false);

            if (!preacordo.getREDE().trim().isEmpty()) {

                txt_codcli_714.setText(preacordo.getREDE());


                txt_razao_714.setText(preacordo.get_REDE());


                txt_cnpj_714.setText("");


                txt_ie_714.setText("");

            } else {


                txt_codcli_714.setText(preacordo.getCLIENTE() + "-" + preacordo.getLOJA());


                txt_razao_714.setText(preacordo.get_RAZAO());


                txt_cnpj_714.setText(preacordo.get_CNPJ());


                txt_ie_714.setText(preacordo.get_IE());
            }



        } else {

            swClienteRede_714.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    if (isChecked) {

                        lbl_codcli_714.setText("Cód. REDE");

                        preacordo.setCLIENTE("");

                        preacordo.set_RAZAO("");

                        preacordo.set_IE("");

                        preacordo.set_CNPJ("");

                    } else {

                        lbl_codcli_714.setText("Cód. CLIENTE");

                        preacordo.setREDE("");

                        preacordo.set_REDE("");

                    }

                    txt_codcli_714.setText("");


                    txt_razao_714.setText("");


                    txt_cnpj_714.setText("");


                    txt_ie_714.setText("");

                    if (preacordo.ValidaAll()) {

                        preacordo.setSTATUS("1");

                    } else {

                        preacordo.setSTATUS("0");

                    }
                    ;


                    if (preacordo.getSTATUS().equals("0")) {

                        txt_status_714.setCompoundDrawables(img, null, null, null);


                    } else {

                        txt_status_714.setCompoundDrawables(null, null, null, null);

                    }

                    txt_status_714.setText(preacordo.get_Status());

                }

            });

            if (preacordo.getCLIENTE().trim().isEmpty() && preacordo.getREDE().trim().isEmpty()) {

                swClienteRede_714.setChecked(false);

            } else {

                if (!preacordo.getCLIENTE().trim().isEmpty()) {

                    swClienteRede_714.setChecked(false);

                } else {

                    swClienteRede_714.setChecked(true);
                }


            }

            if (swClienteRede_714.isChecked()) {

                txt_codcli_714.setText(preacordo.getREDE());


                txt_razao_714.setText(preacordo.get_REDE());


                txt_cnpj_714.setText("");


                txt_ie_714.setText("");

            } else {

                txt_codcli_714.setText(preacordo.getCLIENTE() + "-" + preacordo.getLOJA());


                txt_razao_714.setText(preacordo.get_RAZAO());


                txt_cnpj_714.setText(preacordo.get_CNPJ());


                txt_ie_714.setText(preacordo.get_IE());
            }


            if (swClienteRede_714.isChecked()) {

                if (!preacordo.Validadador("REDE")) {

                    txt_codcli_714.setTextColor(getResources().getColor(R.color.red));
                    txt_codcli_714.setCompoundDrawables(img, null, null, null);
                } else {

                    txt_codcli_714.setTextColor(getResources().getColor(R.color.dark_blue));
                    txt_codcli_714.setCompoundDrawables(null, null, null, null);
                }


            } else {

                if (!preacordo.Validadador("CLIENTE")) {

                    txt_codcli_714.setTextColor(getResources().getColor(R.color.red));
                    txt_codcli_714.setCompoundDrawables(img, null, null, null);
                } else {

                    txt_codcli_714.setTextColor(getResources().getColor(R.color.dark_blue));
                    txt_codcli_714.setCompoundDrawables(null, null, null, null);
                }
            }

        }


        if (Operacao == OperacaoConsulta){

            sp_condpagto_714.setEnabled(false);

            verbasadapter = new defaultAdapter(CadastroNegociacaoActivity.this, R.layout.choice_default_row, lsVerba, "Verba", preacordo.Validadador("CODVERB"));

            sp_verba_714.setAdapter(verbasadapter);


            sp_verba_714.setSelection(index);



        } else {

            sp_verba_714.setEnabled(true);

            verbasadapter = new defaultAdapter(CadastroNegociacaoActivity.this, R.layout.choice_default_row, lsVerba, "Verba", preacordo.Validadador("CODVERB"));

            sp_verba_714.setAdapter(verbasadapter);

            sp_verba_714.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

            {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    verbasadapter.setEscolha(position);

                    Object lixo = sp_verba_714.getSelectedItem();

                    preacordo.setCODVERB(((String[]) lixo)[0]);

                    verbasadapter.setValido(preacordo.Validadador("CODVERB"));

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            verbasadapter.setIsInicializacao(true);

            sp_verba_714.setSelection(index);

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

            sp_pagamento_714.setEnabled(false);

            pagtoadapter = new defaultAdapter(CadastroNegociacaoActivity.this, R.layout.choice_default_row, preacordo.get_formaspagto(), "Pagto:", preacordo.Validadador("TIPOPAG"));

            sp_pagamento_714.setAdapter(pagtoadapter);

            sp_pagamento_714.setSelection(index);

        } else {

            sp_pagamento_714.setEnabled(true);

            pagtoadapter = new defaultAdapter(CadastroNegociacaoActivity.this, R.layout.choice_default_row, preacordo.get_formaspagto(), "Pagto:", preacordo.Validadador("TIPOPAG"));

            sp_pagamento_714.setAdapter(pagtoadapter);

            sp_pagamento_714.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

            {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    pagtoadapter.setEscolha(position);

                    Object lixo = sp_pagamento_714.getSelectedItem();

                    preacordo.setTIPOPAG(((String[]) lixo)[0]);

                    pagtoadapter.setValido(preacordo.Validadador("TIPOPAG"));

                    if (!preacordo.Validadador("DATAPAGTO")) {

                        txt_dtpagto_714.setTextColor(getResources().getColor(R.color.red));
                        txt_dtpagto_714.setCompoundDrawables(img, null, null, null);
                    } else {

                        txt_dtpagto_714.setTextColor(getResources().getColor(R.color.dark_blue));
                        txt_dtpagto_714.setCompoundDrawables(null, null, null, null);
                    }

                    txt_dtpagto_714.setText(preacordo.getDATAPAGTO());

                    if (preacordo.ValidaAll()){

                        preacordo.setSTATUS("1");

                    } else {

                        preacordo.setSTATUS("0");

                    };

                    txt_status_714.setText(preacordo.get_Status());


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            pagtoadapter.setIsInicializacao(true);

            sp_pagamento_714.setSelection(index);

        }
        if (!preacordo.Validadador("DATAINI")) {

            txt_dtinicial_714.setTextColor(getResources().getColor(R.color.red));
            txt_dtinicial_714.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_dtinicial_714.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_dtinicial_714.setCompoundDrawables(null, null, null, null);
        }

        txt_dtinicial_714.setText(preacordo.getDATAINI());

        if (!preacordo.Validadador("DATAFIM")) {

            txt_dtfinal_714.setTextColor(getResources().getColor(R.color.red));
            txt_dtfinal_714.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_dtfinal_714.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_dtfinal_714.setCompoundDrawables(null, null, null, null);
        }

        txt_dtfinal_714.setText(preacordo.getDATAFIM());


        if (!preacordo.Validadador("DATAPAGTO")) {

            txt_dtpagto_714.setTextColor(getResources().getColor(R.color.red));
            txt_dtpagto_714.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_dtpagto_714.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_dtpagto_714.setCompoundDrawables(null, null, null, null);
        }

        txt_dtpagto_714.setText(preacordo.getDATAPAGTO());

        if (!preacordo.Validadador("SLDINI")) {

            txt_valor_714.setTextColor(getResources().getColor(R.color.red));
            txt_valor_714.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_valor_714.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_valor_714.setCompoundDrawables(null, null, null, null);
        }

        txt_valor_714.setText(format_02.format(preacordo.getSLDINI()));

        if (!preacordo.Validadador("DESCRIC")) {

            txt_descricao_714.setTextColor(getResources().getColor(R.color.red));
            txt_descricao_714.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_descricao_714.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_descricao_714.setCompoundDrawables(null, null, null, null);
        }

        txt_descricao_714.setText(preacordo.getDESCRIC());

        if (!preacordo.Validadador("OBS")) {

            txt_obs_714.setTextColor(getResources().getColor(R.color.red));
            txt_obs_714.setCompoundDrawables(img, null, null, null);
        }
        else {

            txt_obs_714.setTextColor(getResources().getColor(R.color.dark_blue));
            txt_obs_714.setCompoundDrawables(null, null, null, null);
        }

        txt_obs_714.setText(preacordo.getOBS());

*/
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