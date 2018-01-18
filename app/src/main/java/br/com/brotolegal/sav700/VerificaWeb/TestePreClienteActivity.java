package br.com.brotolegal.sav700.VerificaWeb;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.sav700.PreClienteCadastroActiviy;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.PreClienteDAO;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.PreCliente;

public class TestePreClienteActivity extends AppCompatActivity {

    private PreCliente cliente;
    private String CODIGO;
    private String TAG = "PRECLIENTECADASTRO";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_pre_cliente);

        try {

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CODIGO = params.getString("CODIGO");

            }


            if (CODIGO.trim() == ""){

                cliente = new PreCliente();

            } else {

                PreClienteDAO dao = new PreClienteDAO();

                dao.open();

                cliente = dao.seek(new String[]{CODIGO});

                dao.close();

                if (cliente == null) {

                    toast("Não Encontrei O Pré-Cliente");

                }

            }

            PlaceholderFragment.setCliente(cliente);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

        } catch (Exception e){

            Log.i(TAG,e.getMessage());


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_teste_pre_cliente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        private static PreCliente cliente;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

            DecimalFormat format_02 = new DecimalFormat(",##0.00");

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){


                TextView tvID;
                TextView tvCODIGO;
                TextView tvCNPJ;
                TextView tvIE;
                TextView tvENTREGA;
                TextView tvSTATUS;
                TextView tvMENSAGEM;
                TextView tvRAZAO;
                TextView tvFANTASIA;
                TextView tvPESSOA;
                TextView tvENDERECO;
                TextView tvCOMPLEMENTO;
                TextView tvBAIRRO;
                TextView tvCODIGO_CIDADE;
                TextView tvESTADO;
                TextView tvCIDADE;
                TextView tvCEP;
                TextView tvTELEFONE;
                TextView tvHOME;
                TextView tvEMAILNFE;
                TextView tvEMAIL;
                TextView tvFUNDACAO;
                TextView tvCANAL;
                TextView tvREDE;
                TextView tvREDEDESCRI;
                TextView tvCANALDESCRI;
                TextView tvTABPRECO;
                TextView tvTABPRECODESCRI;
                TextView tvPOLITICA;
                TextView tvPOLITICADESCRI;
                TextView tvCOND;
                TextView tvCONDDESCRI;
                TextView tvBoleto;
                TextView tvSimplesOP;
                TextView tvIsentoST;
                TextView tvLIMITE;
                TextView tvICMS;
                TextView tvRISCO;
                TextView tvVENCLIM;
                TextView tvOBS;



                View rootView = inflater.inflate(R.layout.fragment_fragmet_cliente_comercial, container, false);

                tvID = (TextView) rootView.findViewById(R.id.txt_id_500);

                tvCODIGO = (TextView) rootView.findViewById(R.id.txt_codigo_500);

                tvCNPJ = (TextView) rootView.findViewById(R.id.txt_cnpj_500);

                tvIE = (TextView) rootView.findViewById(R.id.txt_ie_500);

                tvENTREGA = (TextView) rootView.findViewById(R.id.txt_clienteentrega_500);

                tvSTATUS = (TextView) rootView.findViewById(R.id.txt_status_500);

                tvMENSAGEM = (TextView) rootView.findViewById(R.id.txt_mensagem_500);

                tvRAZAO = (TextView) rootView.findViewById(R.id.txt_razao_500);

                tvFANTASIA = (TextView) rootView.findViewById(R.id.txt_fantasia_500);

                tvPESSOA = (TextView) rootView.findViewById(R.id.txt_pessoa_500);

                tvENDERECO = (TextView) rootView.findViewById(R.id.txt_endereco_500);

                tvCOMPLEMENTO = (TextView) rootView.findViewById(R.id.txt_complemento_500);

                tvBAIRRO = (TextView) rootView.findViewById(R.id.txt_bairro_500);

                tvCODIGO_CIDADE = (TextView) rootView.findViewById(R.id.txt_codigo_cidade_500);

                tvESTADO = (TextView) rootView.findViewById(R.id.txt_estado_500);

                tvCIDADE = (TextView) rootView.findViewById(R.id.txt_cidade_500);

                tvCEP       = (TextView) rootView.findViewById(R.id.txt_cep_500);

                tvTELEFONE  = (TextView) rootView.findViewById(R.id.txt_telefone_500);

                tvHOME      = (TextView) rootView.findViewById(R.id.txt_home_500);

                tvEMAILNFE  = (TextView) rootView.findViewById(R.id.txt_emailnfe_500);

                tvEMAIL     = (TextView) rootView.findViewById(R.id.txt_email_500);

                tvFUNDACAO  = (TextView) rootView.findViewById(R.id.txt_fundacao_500);

                tvCANAL     = (TextView) rootView.findViewById(R.id.txt_cod_canal_500);

                tvREDE      = (TextView) rootView.findViewById(R.id.txt_cod_rede_500);

                tvREDEDESCRI = (TextView) rootView.findViewById(R.id.txt_rede_500);

                tvCANALDESCRI = (TextView) rootView.findViewById(R.id.txt_canal_500);

                tvTABPRECO = (TextView) rootView.findViewById(R.id.txt_cod_tabpreco_500);

                tvTABPRECODESCRI = (TextView) rootView.findViewById(R.id.txt_tabpreco_500);

                tvPOLITICA = (TextView) rootView.findViewById(R.id.txt_cod_politica_500);

                tvPOLITICADESCRI = (TextView) rootView.findViewById(R.id.txt_politica_500);

                tvCOND = (TextView) rootView.findViewById(R.id.txt_cod_condpgto_500);

                tvCONDDESCRI = (TextView) rootView.findViewById(R.id.txt_condpgto_500);

                tvBoleto = (TextView) rootView.findViewById(R.id.txt_boleto_500);

                tvSimplesOP = (TextView) rootView.findViewById(R.id.txt_simples_500);

                tvIsentoST = (TextView) rootView.findViewById(R.id.txt_isento_st_500);

                tvLIMITE = (TextView) rootView.findViewById(R.id.txt_limite_500);

                tvICMS = (TextView) rootView.findViewById(R.id.txt_icms_500);


                if (cliente != null) {

                    tvCODIGO.setText(cliente.getCODIGO() + "-" + cliente.getLOJA());

                    tvCNPJ.setText(App.cnpj_cpf(cliente.getCNPJ()));

                    tvIE.setText(cliente.getIE());

                    tvENTREGA.setText(App.TotvsSIMNAO(cliente.getCLIENTEENTREGA()));

                    tvRAZAO.setText(cliente.getRAZAO());

                    tvFANTASIA.setText(cliente.getFANTASIA());

                    tvPESSOA.setText(cliente.getPESSOA());

                    tvENDERECO.setText(cliente.getENDERECO());

                    tvCOMPLEMENTO.setText(" ");

                    tvBAIRRO.setText(cliente.getBAIRRO());

                    tvCODIGO_CIDADE.setText(cliente.getCODCIDADE());

                    tvESTADO.setText(cliente.getESTADO());

                    tvCIDADE.setText(cliente.getCIDADE());

                    tvCEP.setText(App.cep(cliente.getCEP()));

                    tvTELEFONE.setText("("+cliente.getDDD()+")"+cliente.getTELEFONE());

                    tvHOME.setText(cliente.getHOMEPAGE());

                    tvEMAILNFE.setText(cliente.getEMAILNFE());

                    tvEMAIL.setText(cliente.getEMAIL());

                    tvFUNDACAO.setText(App.aaaammddToddmmaaaa(cliente.getFUNDACAO()));

                    tvCANAL.setText(cliente.getCANAL());

                    tvREDE.setText(cliente.getREDE());

                    tvREDEDESCRI.setText(cliente.getREDEDESCRI());

                    tvCANALDESCRI.setText(cliente.getCANALDESCRI());

                    tvTABPRECO.setText(cliente.getTABPRECO());

                    tvTABPRECODESCRI.setText(cliente.getTABPRECODESCRI());

                    tvPOLITICA.setText(cliente.getPOLITICA());

                    tvPOLITICADESCRI.setText(cliente.getPOLITICADESCRI());

                    tvCOND.setText(cliente.getCONDPAGTO());

                    tvCONDDESCRI.setText(cliente.getCONDPAGTODESCRI());

                    tvBoleto.setText(App.TotvsSIMNAO(cliente.getBOLETO()));

                    tvSimplesOP.setText(App.TotvsSIMNAO(cliente.getOPSIMPLES()));

                    tvIsentoST.setText(App.TotvsSIMNAO(cliente.getISENTOST()));

                    tvLIMITE.setText(format_02.format(cliente.getLIMITE()));

                    tvICMS.setText(App.TotvsSIMNAO(cliente.getICMS()));

                }
                return rootView;

            }
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){

                TextView tvID;
                TextView tvCODIGO;
                TextView tvCNPJ;
                TextView tvIE;
                TextView tvENTREGA;
                TextView tvSTATUS;
                TextView tvRAZAO;

                CheckBox cb_seg_500;
                CheckBox cb_ter_500;
                CheckBox cb_qua_500;
                CheckBox cb_qui_500;
                CheckBox cb_sex_500;
                CheckBox cb_sab_500;
                CheckBox cb_dom_500;

                TextView tvAGENDAMENTO;
                TextView tvHORARECEB;
                TextView tvUNIDDESCARGA;
                TextView tvVLRDESCARGA;
                TextView tvFORMAPAGTO;
                TextView tvMISTO;
                TextView tvPERFILCARGA;

                TextView tvCiente;


                ListView lvPalete;
                ListView lvVeiculo;


                List<Object> lsVeiculos       = new ArrayList<Object>();
                List<Object> lsPaletizacao    = new ArrayList<Object>();

                //PreClienteCadastroActiviy.AdapterVeiculo adapterveiculo;
                //PreClienteCadastroActiviy.AdapterPalete adapterPalete;



                View rootView = inflater.inflate(R.layout.fragment_fragmet_cliente_logistica, container, false);

                tvID            = (TextView) rootView.findViewById(R.id.txt_id_500);

                tvCODIGO        = (TextView) rootView.findViewById(R.id.txt_codigo_500);

                tvCNPJ          = (TextView) rootView.findViewById(R.id.txt_cnpj_500);

                tvIE            = (TextView) rootView.findViewById(R.id.txt_ie_500);

                tvENTREGA       = (TextView) rootView.findViewById(R.id.txt_clienteentrega_500);

                tvSTATUS        = (TextView) rootView.findViewById(R.id.txt_status_500);

                tvRAZAO         = (TextView) rootView.findViewById(R.id.txt_razao_500);

                tvAGENDAMENTO   = (TextView) rootView.findViewById(R.id.txt_agendar_500);

                cb_seg_500      = (CheckBox) rootView.findViewById(R.id.cb_seg_500);

                cb_ter_500      = (CheckBox) rootView.findViewById(R.id.cb_ter_500);

                cb_qua_500      = (CheckBox) rootView.findViewById(R.id.cb_qua_500);

                cb_qui_500      = (CheckBox) rootView.findViewById(R.id.cb_qui_500);

                cb_sex_500      = (CheckBox) rootView.findViewById(R.id.cb_sex_500);

                cb_sab_500      = (CheckBox) rootView.findViewById(R.id.cb_sab_500);

                cb_dom_500      = (CheckBox) rootView.findViewById(R.id.cb_dom_500);


                tvHORARECEB     = (TextView) rootView.findViewById(R.id.txt_horario_500);

                tvUNIDDESCARGA  = (TextView) rootView.findViewById(R.id.txt_unid_descarga_500);

                tvVLRDESCARGA   = (TextView) rootView.findViewById(R.id.txt_vlr_descarga_500);

                tvFORMAPAGTO    = (TextView) rootView.findViewById(R.id.txt_forma_pagto_500);

                tvMISTO         = (TextView) rootView.findViewById(R.id.txt_palete_misto_500);

                tvPERFILCARGA   = (TextView) rootView.findViewById(R.id.txt_perfil_carga_500);

                lvVeiculo       = (ListView) rootView.findViewById(R.id.lvPerfil_veiculo);

                lvPalete        = (ListView) rootView.findViewById(R.id.lvPaletizacao);

                tvCiente        = (TextView) rootView.findViewById(R.id.txt_ciente_500);






                if (cliente != null) {

                    tvCODIGO.setText(cliente.getCODIGO() + "-" + cliente.getLOJA());

                    tvCNPJ.setText(App.cnpj_cpf(cliente.getCNPJ()));

                    tvIE.setText(cliente.getIE());

                    tvENTREGA.setText(App.TotvsSIMNAO(cliente.getCLIENTEENTREGA()));

                    tvRAZAO.setText(cliente.getRAZAO());


                    //Inibe checkbox


//                    cb_seg_500.setEnabled(false);
//
//                    cb_ter_500.setEnabled(false);
//
//                    cb_qua_500.setEnabled(false);
//
//                    cb_qui_500.setEnabled(false);
//
//                    cb_sex_500.setEnabled(false);
//
//                    cb_sab_500.setEnabled(false);
//
//                    cb_dom_500.setEnabled(false);
//
//                    cb_seg_500.setChecked(cliente.getRestricao(0));
//
//                    cb_ter_500.setChecked(cliente.getRestricao(1));
//
//                    cb_qua_500.setChecked(cliente.getRestricao(2));
//
//                    cb_qui_500.setChecked(cliente.getRestricao(3));
//
//                    cb_sex_500.setChecked(cliente.getRestricao(4));
//
//                    cb_sab_500.setChecked(cliente.getRestricao(5));
//
//                    cb_dom_500.setChecked(cliente.getRestricao(6));
//
//                    tvAGENDAMENTO.setText(App.TotvsSIMNAO(cliente.getAGENDAR()));
//
//                    tvHORARECEB.setText(cliente.getRESTRHORA());
//
//                    tvUNIDDESCARGA.setText(cliente.get_Unidade());
//
//                    tvVLRDESCARGA.setText(format_02.format(cliente.getVLRDESCARGA()));
//
//                    tvFORMAPAGTO.setText(cliente.get_FormaPagto());
//
//                    tvPERFILCARGA.setText(cliente.get_PerfilCarga());
//
//                    tvMISTO.setText(App.TotvsSIMNAO(cliente.getCARGAMISTA()));
//
//                    try {
//
//
//                        lsVeiculos = new ArrayList<Object>();
//
//                        lsVeiculos.add("PERFIL DE VEÍCULOS");
//
//                        for(Perfil p : cliente.getPERFIS() ){
//
//                            lsVeiculos.add(p);
//
//                        }
//
//                        adapterveiculo = new AdapterVeiculo(container.getContext(), lsVeiculos);
//
//                        lvVeiculo.setAdapter(adapterveiculo);
//
//                        adapterveiculo.notifyDataSetChanged();
//
//                    } catch (Exception e){
//
//                        Toast.makeText(container.getContext(), "Erro Na Carga: "+e.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }



                }

                return rootView;

            } else {
                View rootView = inflater.inflate(R.layout.fragment_cliente_view_ativity, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
            }
        }

        public static void setCliente(PreCliente cli){

            cliente = cli;

        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }

    private void toast(String mensa){

        Toast.makeText(this, mensa, Toast.LENGTH_LONG).show();

    }

    private void clickTexto(View v, String label, final String campo, int tipo, String mensa, int maxlenght) {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");

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


        if (cliente.getTypeByName(campo).equals(ObjRegister._float)){

            Float value = (Float) cliente.getFieldByName(campo);

            if (value == 0){

                edCampo.setText("");

            } else {

                edCampo.setText(format_02.format(value));

            }



        } else {

            edCampo.setText((String) cliente.getFieldByName(campo));

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


                if ("FUNDACAO".contains(campo)) {

                    try {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                        sdf.setLenient(false);

                        sdf.parse(edCampo.getText().toString());


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

                        cliente.setLIMITE(value);

                        dialog.dismiss();

                        cliente.setFieldByName(campo, edCampo.getText().toString());

                        //refresh();

                    } catch (Exception e){

                        tvMensagem.setText("Valor Inválido !!");

                    }

                    return;
                }


                dialog.dismiss();

                cliente.setFieldByName(campo, edCampo.getText().toString());

                //refresh();

            }


        });

        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
            }

        });


        dialog.show();


    }

}
