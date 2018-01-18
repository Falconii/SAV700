package br.com.brotolegal.sav700;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Palete;
import br.com.brotolegal.savdatabase.entities.Perfil;

public class PreClienteCadastroActiviy extends AppCompatActivity {

    private static  String CODIGO;
    private static  String LOJA;
    private String TAG = "CLIENTEVIEW";

    private Cliente_fast cliente;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_cliente_cadastro);


        try {

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CODIGO = params.getString("CODIGO");
                LOJA = params.getString("LOJA");

            }


            ClienteDAO dao = new ClienteDAO();

            dao.open();

            cliente = dao.seek_fast(CODIGO,LOJA,"");

            if (cliente == null){

                finish();

            }

            PlaceholderFragment2.setCliente(cliente);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprecliente);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle(getResources().getString(R.string.app_versao));
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.containerprecliente);
            mViewPager.setAdapter(mSectionsPagerAdapter);

        } catch (Exception e){

            Log.i(TAG,e.getMessage());


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_settings:

                break;

            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * will work. You can also use that in Fragments that are attached to ActionBarActivities you can use it like this:

     ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

     If you are not using ActionBarActivities or if you want to get the back arrow on a Toolbar that's not set as your SupportActionBar then you can use the following:

     mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
     mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    //What to do on back clicked
    }
    });

     */
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment2 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        private static Cliente_fast cliente;

        public PlaceholderFragment2() {
        }

        public static PlaceholderFragment2 newInstance(int sectionNumber) {
            PlaceholderFragment2 fragment = new PlaceholderFragment2();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        public static void setCliente(Cliente_fast cli){

            cliente = cli;

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

                tvRISCO    = (TextView) rootView.findViewById(R.id.txt_risco_500);

                tvVENCLIM  = (TextView) rootView.findViewById(R.id.txt_venc_lim_500);

                tvICMS = (TextView) rootView.findViewById(R.id.txt_icms_500);

                tvOBS   = (TextView) rootView.findViewById(R.id.txt_obs_500);


                if (cliente != null) {

                    tvCODIGO.setText(cliente.getCODIGO() + "-" + cliente.getLOJA());

                    tvCNPJ.setText(App.cnpj_cpf(cliente.getCNPJ()));

                    tvIE.setText(cliente.getIE());

                    tvENTREGA.setText(App.TotvsSIMNAO(cliente.getCLIENTEENTREGA()));

                    tvRAZAO.setText(cliente.getRAZAO());

                    tvFANTASIA.setText(cliente.getFANTASIA());

                    tvPESSOA.setText(cliente.get_Tipo());

                    tvENDERECO.setText(cliente.getENDERECO());

                    tvCOMPLEMENTO.setText(" ");

                    tvBAIRRO.setText(cliente.getBAIRRO());

                    tvCODIGO_CIDADE.setText(cliente.getCODCIDADE());

                    tvESTADO.setText(cliente.getESTADO());

                    tvCIDADE.setText(cliente.getCIDADE());

                    tvCEP.setText(App.cep(cliente.getCEP()));

                    tvTELEFONE.setText("("+cliente.getDDD()+")"+cliente.getTELEFONE());

                    tvHOME.setText(cliente.getSITE());

                    tvEMAILNFE.setText(cliente.getEMAILNFE());

                    tvEMAIL.setText(cliente.getEMAIL());

                    tvFUNDACAO.setText(App.aaaammddToddmmaaaa(cliente.getFUNDACAO()));

                    tvCANAL.setText(cliente.getCANAL());

                    tvREDE.setText(cliente.getREDE());

                    tvREDEDESCRI.setText(cliente.get_REDE());

                    tvCANALDESCRI.setText(cliente.get_CANAL());

                    tvTABPRECO.setText(cliente.getTABELA());

                    tvTABPRECODESCRI.setText(cliente.get_TABELA());

                    tvPOLITICA.setText(cliente.getREGIAO());

                    tvPOLITICADESCRI.setText(cliente.get_POLITICA());

                    tvCOND.setText(cliente.getCONDPAGTO());

                    tvCONDDESCRI.setText(cliente.get_COND());

                    tvBoleto.setText(App.TotvsSIMNAO(cliente.getBOLETO()));

                    tvSimplesOP.setText(App.TotvsSIMNAO(cliente.getSIMPLES()));

                    tvIsentoST.setText(App.TotvsSIMNAO(cliente.getISENTOST()));

                    tvLIMITE.setText(format_02.format(cliente.getLIMITE()));

                    tvRISCO.setText(cliente.getRISCO());

                    tvVENCLIM.setText(App.aaaammddToddmmaaaa(cliente.getVENCLC()));

                    tvICMS.setText(App.TotvsSIMNAO(cliente.getICMS()));

                    tvOBS.setText(cliente.getOBS());

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

                AdapterVeiculo  adapterveiculo;
                AdapterPalete   adapterPalete;



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
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment2.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Principal";
                case 1:
                    return "Comercial";
                case 2:
                    return "Logistica";
            }
            return null;
        }
    }




    private void LoadPaletizacao(){

//        try {
//
//
//              lsPaletizacao     = new ArrayList<Object>();
//
//            lsPaletizacao.add("PALETIZAÇÃO");
//
//            for(Palete p : cliente.getPALETES() ){
//
//                lsPaletizacao.add(p);
//
//            }
//
//            adapterPalete = new AdapterPalete(ClienteViewAtivity.this, lsPaletizacao);
//
//            lvPalete.setAdapter(adapterPalete);
//
//            adapterPalete.notifyDataSetChanged();
//
//        } catch (Exception e){
//
//            Toast.makeText(this, "Erro Na Carga: "+e.getMessage(), Toast.LENGTH_LONG).show();
//
//        }

    }

    private static class AdapterVeiculo extends BaseAdapter
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

                            cb.setEnabled(false);

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

                            final List<String> list = cliente.getUNIDADES();

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, list);

                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spunidades.setAdapter(dataAdapter);


                            spunidades.setEnabled(false);

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
