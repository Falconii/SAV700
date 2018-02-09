package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Palete;
import br.com.brotolegal.savdatabase.entities.Perfil;
import br.com.brotolegal.savdatabase.entities.PreCliente;
import br.com.brotolegal.savdatabase.regrasdenegocio.ValidadorPreCliente;

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


    private TextView tvID;
    private TextView tvCODIGO;
    private TextView tvCNPJ;
    private TextView tvIE;
    private TextView tvENTREGA;
    private TextView tvSTATUS;
    private TextView tvRAZAO;

    private CheckBox cb_seg_500;
    private CheckBox cb_ter_500;
    private CheckBox cb_qua_500;
    private CheckBox cb_qui_500;
    private CheckBox cb_sex_500;
    private CheckBox cb_sab_500;
    private CheckBox cb_dom_500;

    private TextView tvAGENDAMENTO;
    private TextView tvHORARECEB;
    private TextView tvUNIDDESCARGA;
    private TextView tvVLRDESCARGA;
    private TextView tvFORMAPAGTO;
    private TextView tvMISTO;
    private TextView tvPERFILCARGA;
    private ListView lvPalete;
    private ListView lvVeiculo;
    private TextView tvCiente;

    private List<Object> lsVeiculos       = new ArrayList<Object>();
    private List<Object> lsPaletizacao    = new ArrayList<Object>();
    private AdapterVeiculo  adapterveiculo;
    private AdapterPalete   adapterPalete;
    private DecimalFormat format_02 = new DecimalFormat(",##0.00");

    private ValidadorPreCliente validador;



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

            validador    = new ValidadorPreCliente(App.precliente,PreClienteLogisticaActivity.this);

            im_comercial = (ImageView) findViewById(R.id.im_comercial);

            im_logistica = (ImageView) findViewById(R.id.im_logistica);

            im_documentos = (ImageView) findViewById(R.id.im_documentos);

            tv_comercial = (TextView) findViewById(R.id.tv_comercial);

            tv_logistica = (TextView) findViewById(R.id.tv_logistica);

            tv_documentos = (TextView) findViewById(R.id.tv_documentos);

            navegador();

            Init();

            refresh();

            Edicao();

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

    private void Init(){

        tvID            = (TextView) findViewById(R.id.txt_id_500);

        tvCODIGO        = (TextView) findViewById(R.id.txt_codigo_500);

        tvCNPJ          = (TextView) findViewById(R.id.txt_cnpj_500);

        tvIE            = (TextView) findViewById(R.id.txt_ie_500);

        tvENTREGA       = (TextView) findViewById(R.id.txt_clienteentrega_500);

        tvSTATUS        = (TextView) findViewById(R.id.txt_status_500);

        tvRAZAO         = (TextView) findViewById(R.id.txt_razao_500);

        tvAGENDAMENTO   = (TextView) findViewById(R.id.txt_agendar_500);

        cb_seg_500      = (CheckBox) findViewById(R.id.cb_seg_500);

        cb_ter_500      = (CheckBox) findViewById(R.id.cb_ter_500);

        cb_qua_500      = (CheckBox) findViewById(R.id.cb_qua_500);

        cb_qui_500      = (CheckBox) findViewById(R.id.cb_qui_500);

        cb_sex_500      = (CheckBox) findViewById(R.id.cb_sex_500);

        cb_sab_500      = (CheckBox) findViewById(R.id.cb_sab_500);

        cb_dom_500      = (CheckBox) findViewById(R.id.cb_dom_500);

        tvHORARECEB     = (TextView) findViewById(R.id.txt_horario_500);

        tvUNIDDESCARGA  = (TextView) findViewById(R.id.txt_unid_descarga_500);

        tvVLRDESCARGA   = (TextView) findViewById(R.id.txt_vlr_descarga_500);

        tvFORMAPAGTO    = (TextView) findViewById(R.id.txt_forma_pagto_500);

        tvMISTO         = (TextView) findViewById(R.id.txt_palete_misto_500);

        tvPERFILCARGA   = (TextView) findViewById(R.id.txt_perfil_carga_500);

        lvVeiculo       = (ListView) findViewById(R.id.lvPerfil_veiculo);

        lvPalete        = (ListView) findViewById(R.id.lvPaletizacao);

        tvCiente        = (TextView) findViewById(R.id.txt_ciente_500);

        LoadVeiculos();

        LoadPaletizacao();

        //Inibe checkbox
        if (App.precliente.getSTATUS().compareTo("4") > 0) {

            cb_seg_500.setEnabled(false);

            cb_ter_500.setEnabled(false);

            cb_qua_500.setEnabled(false);

            cb_qui_500.setEnabled(false);

            cb_sex_500.setEnabled(false);

            cb_sab_500.setEnabled(false);

            cb_dom_500.setEnabled(false);

        }




    }

    private void Edicao(){

        cb_seg_500.setOnCheckedChangeListener(new clickSemana());

        cb_ter_500.setOnCheckedChangeListener(new clickSemana());

        cb_qua_500.setOnCheckedChangeListener(new clickSemana());

        cb_qui_500.setOnCheckedChangeListener(new clickSemana());

        cb_sex_500.setOnCheckedChangeListener(new clickSemana());

        cb_sab_500.setOnCheckedChangeListener(new clickSemana());

        cb_dom_500.setOnCheckedChangeListener(new clickSemana());


        tvAGENDAMENTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickSN(v,"AGENDAMENTO","AGENDAMENTO");

            }
        });

        tvHORARECEB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickTexto(v, "HORÁRIO", "HORARECEB", 11);

            }
        });

        tvUNIDDESCARGA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickUnidDesgarda(v, "UNIDADE DE DESCARGA", "UNIDDESCARG");

            }

        });

        tvVLRDESCARGA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickTexto(v,"VALOR DA DESCARGA","VLRDESCARG",6);

            }

        });

        tvFORMAPAGTO.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickFormaPgto(v, "Formas de Pagamento", "FORMAPAGTO");

            }


        });

        tvPERFILCARGA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickPerfilCarg(v, "PERFIL DA CARGA", "PERFILCARG");

            }

        });

        tvMISTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickSN(v, "ACEITA PALETE MISTO", "MISTO");

            }
        });



        tvCiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickSN(v, "Estou Ciente De Ter Informado Este Dados.", "CIENTE");

            }


        });






    }

    public void refresh() {

        cb_seg_500.setChecked(App.precliente.getRestricao(1));

        cb_ter_500.setChecked(App.precliente.getRestricao(2));

        cb_qua_500.setChecked(App.precliente.getRestricao(3));

        cb_qui_500.setChecked(App.precliente.getRestricao(4));

        cb_sex_500.setChecked(App.precliente.getRestricao(5));

        cb_sab_500.setChecked(App.precliente.getRestricao(6));

        cb_dom_500.setChecked(App.precliente.getRestricao(0));

        tvHORARECEB.setText(App.precliente.getHORARECEB());

        tvAGENDAMENTO.setText(App.precliente.getAGENDAMENTO());

        validador.Validadador(tvHORARECEB,"HORARECEB",App.precliente.getHORARECEB());

        validador.Validadador(tvUNIDDESCARGA,"UNIDDESCARG",App.precliente.getUNIDDESCARG());

        validador.Validadador(tvFORMAPAGTO,"FORMAPAGTO",App.precliente.getFORMAPAGTO());

        validador.Validadador(tvMISTO,"MISTO",App.precliente.getMISTO());

        validador.Validadador(tvCiente,"CIENTE",App.precliente.getCIENTE());

        validador.Validadador(tvPERFILCARGA,"PERFILCARG",App.precliente.getPERFILCARG());

    }

    private void clickSN(View v,String label,final String campo){

        if (App.precliente.getSTATUS().compareTo("4") > 0) {

            return;

        }

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.dlgsimnao);

        //define o titulo do Dialog

        dialog.setTitle(label);

        //instancia os objetos que estao no dlmudaverba.xml

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

        tvtexto1.setText("Escolha Uma Das Opções:");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //finaliza o dialog
                dialog.dismiss();

                if (campo.equals("AGENDAMENTO"))    tvAGENDAMENTO.setText((String) spSimNao.getSelectedItem());

                App.precliente.setFieldByName(campo, (String) spSimNao.getSelectedItem());

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

    private void clickUnidDesgarda(View v,String label,final String campo){

        if (App.precliente.getSTATUS().compareTo("4") > 0) {

            return;

        }

        List<String> unidades = new ArrayList<String>();

        unidades.add("1 - FD      ");
        unidades.add("2 - TONELADA");
        unidades.add("3 - CHAPA   ");
        unidades.add("4 - PALETE  ");

        int id;

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.dlgsimnao);

        dialog.setTitle(label);

        final Button confirmar    = (Button)   dialog.findViewById(R.id.btn_040_ok);
        final Button cancelar     = (Button)   dialog.findViewById(R.id.btn_040_can);
        final TextView tvtexto1   = (TextView) dialog.findViewById(R.id.txt_040_texto1);
        final Spinner spSimNao    = (Spinner)  dialog.findViewById(R.id.spSimNao);

        List<String> list = new ArrayList<String>();

        for(String u : unidades){

            list.add(u);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSimNao.setAdapter(dataAdapter);

        id = -1;

        if (id == -1){

            spSimNao.setSelection(0);

        } else {

            spSimNao.setSelection(id);
        }

        tvtexto1.setText("Escolha Uma Das Opcoes:");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

                App.precliente.setFieldByName(campo, (String) spSimNao.getSelectedItem());

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

    private void clickFormaPgto(View v,String label,final String campo){

        if (App.precliente.getSTATUS().compareTo("4") > 0) {

            return;

        }

        List<String> forma = new ArrayList<String>();

        forma.add("1-DINHEIRO");
        forma.add("2-BOLETO  ");

        int id;

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.dlgsimnao);

        dialog.setTitle(label);

        final Button confirmar    = (Button)   dialog.findViewById(R.id.btn_040_ok);
        final Button cancelar     = (Button)   dialog.findViewById(R.id.btn_040_can);
        final TextView tvtexto1   = (TextView) dialog.findViewById(R.id.txt_040_texto1);
        final Spinner  spSimNao   = (Spinner)  dialog.findViewById(R.id.spSimNao);

        List<String> list = new ArrayList<String>();

        for(String f :forma){

            list.add(f);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSimNao.setAdapter(dataAdapter);

        id = -1;


        for(int x = 0;x < forma.size();x++){


            if (forma.get(x).equals(App.precliente.getFORMAPAGTO())) {

                id = x;

            }

        }

        if (id == -1){

            spSimNao.setSelection(0);

        } else {

            spSimNao.setSelection(id);
        }

        tvtexto1.setText("Escolha Uma Das Opcoes:");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

                App.precliente.setFieldByName(campo, (String) spSimNao.getSelectedItem());

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

    private void clickPerfilCarg(View v,String label,final String campo){

        if (App.precliente.getSTATUS().compareTo("4") > 0) {

            return;

        }

        List<String> perfis = new ArrayList<String>();


        perfis.add("1-PADRÃO");
        perfis.add("2-ESTRECHADO");
        perfis.add("3-ESTRECHADO (CARGA BATIDA 12,00)");
        perfis.add("4-ESTRECHADO / ETIQUETADO");
        perfis.add("5-ESTRECHADO / ENVELOPADO");
        perfis.add("6-ESTRECHADO / ETIQUETADO/ENVELOPADO");
        perfis.add("7-CHAPA DO MERCADO");

        int id;

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.dlgsimnao);

        dialog.setTitle(label);

        final Button confirmar    = (Button)   dialog.findViewById(R.id.btn_040_ok);
        final Button cancelar     = (Button)   dialog.findViewById(R.id.btn_040_can);
        final TextView tvtexto1   = (TextView) dialog.findViewById(R.id.txt_040_texto1);
        final Spinner  spSimNao   = (Spinner)  dialog.findViewById(R.id.spSimNao);

        List<String> list = new ArrayList<String>();

        for(String p : perfis){

            list.add(p);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSimNao.setAdapter(dataAdapter);

        id = -1;


        for(int x = 0;x < perfis.size();x++){


            if (perfis.get(x).equals(App.precliente.getPERFILCARG())) {

                id = x;

            }

        }


        if (id == -1){

            spSimNao.setSelection(0);

        } else {

            spSimNao.setSelection(id);
        }

        tvtexto1.setText("Escolha Uma Das Opcoes:");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

                App.precliente.setFieldByName(campo, (String) spSimNao.getSelectedItem());

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

    public void clickVlrDescarga(View v){

        clickTexto(v,"VALOR DA DESCARGA","VLRDESCARG",6);

    }

    public void clickForma(View v){

        clickFormaPgto(v, "Formas de Pagamento", "FORMAPAGTO");

    }

    private void clickTexto(View v,String label,final String campo, int tipo){

        if (App.precliente.getSTATUS().compareTo("4") > 0) {

            return;

        }

        final Dialog dialog = new Dialog(v.getContext());

        dialog.setContentView(R.layout.gettexttopadrao);

        dialog.setTitle(label);

        final Button confirmar    = (Button) dialog.findViewById(R.id.btn_570_ok);
        final Button cancelar     = (Button) dialog.findViewById(R.id.btn_570_can);
        final TextView tvtexto1   = (TextView) dialog.findViewById(R.id.txt_570_texto1);
        final EditText edCampo    = (EditText) dialog.findViewById(R.id.edCampo_570);




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

                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                edCampo.addTextChangedListener(Mask.insert("##:##", edCampo));

                break;


            default:

                edCampo.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                break;
        }

        if (campo.equals("VLRDESCARG")) {

            if (App.precliente.getVLRDESCARG() == 0) {

                edCampo.setText("");
                edCampo.setHint("0,00");

            } else {

                edCampo.setText(format_02.format(App.precliente.getVLRDESCARG()));

            }

        }

        else if (campo.equals("HORARECEB")) {

            edCampo.setHint("HH:MM");

            if (App.precliente.getHORARECEB().equals("00:00")) {

                edCampo.setText("");

            } else {

                edCampo.setText(App.precliente.getHORARECEB());

            }

        }

        else {

            edCampo.setText((String) App.precliente.getFieldByName(campo));

        }

        try {

            edCampo.setSelection(edCampo.getText().toString().length());

        }

        catch (Exception e) {


        }


        tvtexto1.setText("Digite O Nome Conteúdo do Campo");

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //finaliza o dialog
                dialog.dismiss();

                if (campo.equals("VLRDESCARG")) {

                    Float numero = 0f;

                    try {

                        String nro = edCampo.getText().toString().replaceAll("[,]", ".");

                        numero = Float.valueOf(nro);

                    } catch (Exception e) {

                        numero = 0f;
                    }

                    App.precliente.setVLRDESCARG(numero);


                } else {

                    App.precliente.setFieldByName(campo, edCampo.getText().toString());
                }


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



    private void LoadVeiculos(){

        try {


            lsVeiculos = new ArrayList<Object>();

            lsVeiculos.add("PERFIL DE VEÍCULOS");

            for(Perfil p : App.precliente.getPERFIS() ){

                lsVeiculos.add(p);

            }

            adapterveiculo = new AdapterVeiculo(PreClienteLogisticaActivity.this, lsVeiculos);

            lvVeiculo.setAdapter(adapterveiculo);

            adapterveiculo.notifyDataSetChanged();

        } catch (Exception e){

            Toast.makeText(this, "Erro Na Carga: "+e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    private void LoadPaletizacao(){

        try {


            lsPaletizacao     = new ArrayList<Object>();

            lsPaletizacao.add("PALETIZAÇÃO");

            for(Palete p : App.precliente.getPALETES() ){

                lsPaletizacao.add(p);

            }

            adapterPalete = new AdapterPalete(PreClienteLogisticaActivity.this, lsPaletizacao);

            lvPalete.setAdapter(adapterPalete);

            adapterPalete.notifyDataSetChanged();

        } catch (Exception e){

            Toast.makeText(this, "Erro Na Carga: "+e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }


    class clickSemana implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

            int index = 0;

            if (buttonView == cb_dom_500) index = 0;
            if (buttonView == cb_seg_500) index = 1;
            if (buttonView == cb_ter_500) index = 2;
            if (buttonView == cb_qua_500) index = 3;
            if (buttonView == cb_qui_500) index = 4;
            if (buttonView == cb_sex_500) index = 5;
            if (buttonView == cb_sab_500) index = 6;

            App.precliente.setRestricao(index,isChecked);

        }
    }

    private  class AdapterVeiculo extends BaseAdapter
    {


        private List<Object> lsDados;

        Context context;
        final int ITEM_VIEW_CABEC          = 0;
        final int ITEM_VIEW_DETALHE        = 1;
        final int ITEM_VIEW_NO_DATA        = 2;
        final int ITEM_VIEW_COUNT          = 3;


        private LayoutInflater inflater;

        public AdapterVeiculo(Context context, List<Object> pObjects) {
            this.lsDados = pObjects;
            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        private String Cabec(){
            String retorno = "";

            retorno = "VEÍCULOS NÃO AUTORIZADOS PARA ENTREGA";

            return retorno;
        }

        public void addItem(final Perfil item) {

            this.lsDados.add(item);

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return lsDados.size();
        }

        @Override
        public Object getItem(int position) {
            return lsDados.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public int getViewTypeCount()       {
            return ITEM_VIEW_COUNT;
        }

        @Override
        public int getItemViewType(int position) {

            int retorno = -1;

            if (lsDados.get(position) instanceof String){

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsDados.get(position) instanceof Perfil){

                retorno = ITEM_VIEW_DETALHE;

            }


            if (lsDados.get(position) instanceof NoData){

                retorno = ITEM_VIEW_NO_DATA;

            }


            return retorno;


        }


        @Override
        public boolean isEnabled(int position) {
            boolean retorno = false;
            return retorno;
        }

        public void deleteitem(int position) {

            this.lsDados.remove(position);
            notifyDataSetChanged();

            return;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.perfil_row, null);

                            break;


                        case ITEM_VIEW_NO_DATA:


                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                    switch (type) {

                        case ITEM_VIEW_CABEC: {

                            TextView tvMensagem = (TextView) convertView.findViewById(R.id.separador);

                            tvMensagem.setText(Cabec());

                            break;
                        }


                        case ITEM_VIEW_DETALHE: {

                            final Perfil perfil = (Perfil) lsDados.get(pos);

                            TextView tvTexto1 = (TextView) convertView.findViewById(R.id.txt_texto1_135);

                            tvTexto1.setText(perfil.getTexto1());

                            CheckBox cb = (CheckBox) convertView.findViewById(R.id.cbProcessar_135);

                            cb.setChecked(perfil.getProcessar());

                            if (App.precliente.getSTATUS().compareTo("4") > 0) {

                                cb.setEnabled(false);

                            } else {
                                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                                  @Override
                                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                                      perfil.setProcessar(isChecked);

                                                                      notifyDataSetChanged();

                                                                      App.precliente.upDatePERFILVEIC(perfil);

                                                                  }
                                                              }
                                );
                            }

                            break;
                        }
                        case ITEM_VIEW_NO_DATA: {

                            final NoData obj = (NoData) lsDados.get(pos);

                            TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                            tvTexto.setText(obj.getMensagem());

                            break;

                        }
                        default:
                            break;
                    }

                }

            }

            catch (Exception e) {

                toast("Erro : " + e.getMessage());

            }

            return convertView;
        }

        private String sim_nao(String perfilentrega) {
            String retorno = "NAO";

            if (perfilentrega.trim().equals("1")) retorno = "SIM";

            return retorno;
        }


        public void toast (String msg)    {
            Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();
        }

        private void trace (String msg)     {}


    }

    private  class AdapterPalete extends BaseAdapter
    {

        private List<Object> lsDados;

        Context context;
        final int ITEM_VIEW_CABEC          = 0;
        final int ITEM_VIEW_DETALHE        = 1;
        final int ITEM_VIEW_NO_DATA        = 2;
        final int ITEM_VIEW_COUNT          = 3;


        private LayoutInflater inflater;

        public AdapterPalete(Context context, List<Object> pObjects) {
            this.lsDados = pObjects;
            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        private String Cabec(){

            String retorno = "QUANTIDADES DE PALETIZAÇÃO";

            return retorno;

        }

        public void addItem(final Palete item) {

            this.lsDados.add(item);

            notifyDataSetChanged();
        }



        @Override
        public int getCount() {
            return lsDados.size();
        }

        @Override
        public Object getItem(int position) {
            return lsDados.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_COUNT;
        }

        public void updateItem(Palete item){

            for(Object p : this.lsDados){

                if (p instanceof Palete){

                    if (((Palete) p).getGrupo().equals(item.getGrupo())){

                        ((Palete) p).setUnidade(item.getUnidade());

                        break;

                    }

                }

            }

            notifyDataSetChanged();

            return;

        }

        @Override
        public int getItemViewType(int position) {

            int retorno = -1;

            if (lsDados.get(position) instanceof String){

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsDados.get(position) instanceof Palete){

                retorno = ITEM_VIEW_DETALHE;

            }

            if (lsDados.get(position) instanceof NoData){

                retorno = ITEM_VIEW_NO_DATA;

            }

            return retorno;

        }


        @Override
        public boolean isEnabled(int position) {
            boolean retorno = false;
            return retorno;
        }

        public void deleteitem(int position) {

            this.lsDados.remove(position);

            notifyDataSetChanged();

            return;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.paletizacao_row, null);

                            break;


                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                    switch (type) {

                        case ITEM_VIEW_CABEC: {

                            TextView tvMensagem = (TextView) convertView.findViewById(R.id.separador);

                            tvMensagem.setText(Cabec());

                            break;
                        }


                        case ITEM_VIEW_DETALHE: {

                            int id = 0;

                            final Palete palete = (Palete) lsDados.get(pos);

                            TextView tvTexto1 = (TextView) convertView.findViewById(R.id.txt_texto1_135);

                            tvTexto1.setText(palete.getGrupo() + " " + palete.getDescricao());

                            TextView tvTexto2 = (TextView) convertView.findViewById(R.id.txt_texto2_135);

                            tvTexto2.setText("Unid: " + palete.getUnid());

                            Spinner spunidades  = (Spinner) convertView.findViewById(R.id.sp_unidades_paletizacao_136);

                            final List<String> list = App.precliente.getUNIDADES();

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(PreClienteLogisticaActivity.this,android.R.layout.simple_spinner_item, list);

                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spunidades.setAdapter(dataAdapter);

                            if (App.precliente.getSTATUS().compareTo("4") > 0) {

                                spunidades.setEnabled(false);

                            } else {

                                spunidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        palete.setUnidade(list.get(position));

                                        App.precliente.upDatePALETIZACAO(palete);

                                        notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                            id = -1;

                            for (int x = 0; x < list.size(); x++ ){

                                if (palete.getUnidade().equals(list.get(x))){

                                    id = x;

                                    break;

                                }

                            }

                            if ( id == -1 ) {

                                spunidades.setSelection(0);

                            } else {

                                spunidades.setSelection(id);

                            }

                            break;

                        }
                        case ITEM_VIEW_NO_DATA: {

                            final NoData obj = (NoData) lsDados.get(pos);

                            TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                            tvTexto.setText(obj.getMensagem());

                            break;

                        }
                        default:

                            break;
                    }

                }

            }

            catch (Exception e) {

                toast("Erro : " + e.getMessage());

            }

            return convertView;
        }

        private String sim_nao(String perfilentrega) {
            String retorno = "NAO";

            if (perfilentrega.trim().equals("1")) retorno = "SIM";

            return retorno;
        }


        public void toast (String msg)    {
            Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();
        }

        private void trace (String msg)     {}


    }




}

