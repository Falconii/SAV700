package br.com.brotolegal.sav700.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.ksoap2.serialization.SoapObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.brotolegal.sav700.CadastroPreAcordoActivity;
import br.com.brotolegal.sav700.ClienteViewAtivity;
import br.com.brotolegal.sav700.PedidosActivity;
import br.com.brotolegal.sav700.PedidosProtheusGeralActivity;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.sav700.UpdateVersionActivity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.NotificacaoDAO;
import br.com.brotolegal.savdatabase.dao.PedCabTvsDAO;
import br.com.brotolegal.savdatabase.dao.PreAcordoDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Notificacao;
import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;
import br.com.brotolegal.savdatabase.regrasdenegocio.ResumoAgendamento;


public class DashBoard_Frag extends Fragment {


    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    public  String conection_state = "V";

    private ListView lv_db_01a;
    private ListView lv_db_01b;
    private ListView lv_db_02a;
    private ListView lv_db_02b;
    private Spinner  sp_tabela_01a;
    private Spinner  sp_tabela_01b;
    private ProgressBar progressBar;
    private ImageView img_conexao;
    //private TextView  lbl_conexao;
    private TextView  lbl_tabela;
    private TextView  lbl_fechamento;
    private ImageButton img_refresh;
    private Spinner     spConexao;


    private BarChart mChart;

    private List<Object> lsClientes     = new ArrayList<>();
    private List<Object> lsPedidos      = new ArrayList<>();
    private List<Object> lsNotificacao  = new ArrayList<>();
    private List<Object> lsNotificacaob = new ArrayList<>();

    private AdapterCliente adapterCliente;
    private AdapterPedido  adapterPedido;

    private List<String[]> lsClientesResumo        = new ArrayList<>();

    private List<String[]> lsPedidosResumo          = new ArrayList<>();

    private AdapterNotificacao adapterNotificacao;

    private AdapterNotificacao adapterNotificacaob;

    private defaultAdapterClientes filtroAdapterCliente;

    private defaultAdapter FiltroAdapterPedido;

    private conexaoAdapter AdapterConexao;

    private List<Config> lsConexoes;

    private verRede verrede;

    private List<PieEntry> pieEntryMetas;

    public  Boolean loader = false;

    public Config config = null;

    public static  int vezes = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        lv_db_01a = (ListView) rootView.findViewById(R.id.lv_db_01a);

        lv_db_01b = (ListView) rootView.findViewById(R.id.lv_db_01b);

        //@TODO VERSÃO NOVA
        //lv_db_02a = (ListView) rootView.findViewById(R.id.lv_db_02a);

        //lv_db_02b = (ListView) rootView.findViewById(R.id.lv_db_02b);

        sp_tabela_01a = (Spinner) rootView.findViewById(R.id.sp_tabela_01a);

        sp_tabela_01b = (Spinner) rootView.findViewById(R.id.sp_tabela_01b);

        progressBar = (ProgressBar) rootView.findViewById(R.id.dasboard_progress);

        img_conexao = (ImageView) rootView.findViewById(R.id.img_conexao);

        //lbl_conexao = (TextView) rootView.findViewById(R.id.lbl_conexao);

        lbl_tabela = (TextView) rootView.findViewById(R.id.lbl_tabela);

        lbl_fechamento = (TextView) rootView.findViewById(R.id.lbl_fechamento);

        img_refresh  = (ImageButton) rootView.findViewById(R.id.img_refresh);

        spConexao    = (Spinner) rootView.findViewById(R.id.spConexao);

        progressBar.setVisibility(View.INVISIBLE);

        //mChart = (BarChart) rootView.findViewById(R.id.chart1);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        StatusDAO dao;

        super.onActivityCreated(savedInstanceState);

        if (App.user == null){

            return;

        }



        if (!(((AppCompatActivity) getActivity()).getSupportActionBar() == null)){

            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("DashBoard "+getResources().getString(R.string.app_versao));

            loadClientes();

            loadPedidosEmAberto();

            loadNotificacoes();

            VerConexoes();

            //LoadBar();

            loader = true;

        }

    }

    private void LoadBar() {

        List<BarEntry> barEntry = new ArrayList<>();

        List<String>  lsLabels = new ArrayList<>();

        float values[] = {150f,50f,200f,300f};

        String labels[] = {"REAL","CARTEIRA","REAL+CART","META"};

        for(int x = 0; x < values.length ; x++){

            barEntry.add(new BarEntry(values[x],x));

            lsLabels.add(labels[x]);
        }

        BarDataSet barDataSet = new BarDataSet(barEntry,"FEIJÃO");

        barDataSet.setDrawIcons(false);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setStackLabels(new String[] {"REAL","CARTEIRA","REAL+CART","META"});


        BarData barData  = new BarData(barDataSet);

        mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());

        mChart.notifyDataSetChanged();

        mChart.setData(barData);

        mChart.setFitBars(true);

        mChart.invalidate();


    }

    private void LoadPie() {

//        pieEntryMetas = new ArrayList<>();
//
//        float values[] = {200f,250f,450f};
//
//        String labels[] = {"ARROZ","FEIJAO","AZEITE"};
//
//        try {
//
//            MetaDAO dao = new MetaDAO();
//
//            dao.open();
//
//            List<MetaCategoria> metas = dao.getMetaByClienteCateroria("391685","01");
//
//            dao.close();
//
//            for(MetaCategoria meta : metas ) {
//
//                pieEntryMetas.add(new PieEntry(meta.getOBJETIVO(),meta.getDESCCATEGORIA()));
//
//            }
//
//            mChart.setBackgroundColor(Color.CYAN);
//
//            //moveOffScreen();
//
//            mChart.setUsePercentValues(true);
//            mChart.getDescription().setEnabled(false);
//
//            //mChart.setCenterTextTypeface(mTfLight);
//            mChart.setCenterText(generateCenterSpannableText());
//
//            mChart.setDrawHoleEnabled(true);
//            mChart.setHoleColor(Color.CYAN);
//
//            mChart.setTransparentCircleColor(Color.WHITE);
//            mChart.setTransparentCircleAlpha(110);
//
//            mChart.setHoleRadius(58f);
//            mChart.setTransparentCircleRadius(61f);
//
//            mChart.setDrawCenterText(true);
//
//            mChart.setRotationEnabled(false);
//            mChart.setHighlightPerTapEnabled(true);
//
//            mChart.setMaxAngle(180f); // HALF CHART
//            mChart.setRotationAngle(180f);
//            mChart.setCenterTextOffset(0, -20);
//
//            setData(4, 100);
//
//            mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//
//            Legend l = mChart.getLegend();
//            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//            l.setOrientation(Legend.LegendOrientation.VERTICAL);
//            l.setDrawInside(false);
//            l.setXEntrySpace(7f);
//            l.setYEntrySpace(0f);
//            l.setYOffset(0f);
//
//            // entry label styling
//            mChart.setEntryLabelColor(Color.WHITE);
//            //mChart.setEntryLabelTypeface(mTfRegular);
//            mChart.setEntryLabelTextSize(12f);
//
//        } catch (Exception e){
//
//            Toast.makeText(getActivity().getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//        }

    }




    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    Log.i("SAV", "NAO CONTEM AS CHAVES..");

                    return;

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    try {

                        verrede.processa();

                    } catch (Exception e) {

                        //Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }

            }
            catch (Exception E){

                Log.d("SAV", "MENSAGEM", E);

                Toast.makeText(getContext(), "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };

    public void VerConexoes(){

        int IndiceConexao = 0;

        try {

            ConfigDAO dao = new ConfigDAO();

            dao.open();

            config = dao.seek(new String[]{"000"});

            lsConexoes = dao.getConexoes();

            Config padrao = dao.seekByDescricao( new String[]{config.getDESCRICAO()});

            if (padrao != null) {

                IndiceConexao = 0;

                for( int x = 0; x < lsConexoes.size() ; x++){

                    if (padrao.getDESCRICAO().equals(lsConexoes.get(x).getDESCRICAO())) {

                        IndiceConexao = x;

                        break;
                    }
                }

            } else {

                IndiceConexao = 0;

            }

            dao.close();

        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        AdapterConexao = new conexaoAdapter(getContext(), R.layout.conexoes_opcoes,lsConexoes);

        spConexao.setAdapter(AdapterConexao);

        spConexao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if ((AdapterConexao.getEscolha() == -2)) {

                    AdapterConexao.setEscolha(-1);

                } else {

                    AdapterConexao.setEscolha(position);

                    config = lsConexoes.get(position);

                    config.setCODIGO("000");

                    AdapterConexao.setStatusRede(true, "Verificando...", null);

                    verrede = new verRede();

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandler, getContext(), App.user, "GETSTATUS", "GETSTATUS", 1, 1, config, verrede,-1);

                    acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

                    acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

                    acessoWeb.start();

                    try {

                        ConfigDAO dao = new ConfigDAO();

                        dao.open();

                        dao.Update(config);

                        dao.close();

                    } catch (Exception e) {

                        Toast.makeText(getContext(), "Não Atualizada A Conexão !!\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spConexao.setSelection(IndiceConexao);



    }

    private class verRede extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            Log.i("VERREDE","Processa...");

            String cerro       = result.getPropertyAsString("CERRO");
            String cmsgerro    = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                String MV_ZBLECRG  = App.TotvsSN(result.getPropertyAsString("MV_ZBLECRG"));
                String MV_ZBLEPED  = App.TotvsSN(result.getPropertyAsString("MV_ZBLEPED"));
                String MV_ZDTECRG  = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTECRG"));
                String MV_ZDTEPED  = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTEPED"));
                String MV_ZHRECRG  = result.getPropertyAsString("MV_ZHRECRG");
                String MV_ZHREPED  = result.getPropertyAsString("MV_ZHREPED");
                String MV_SAV700   = result.getPropertyAsString("MV_SAV700");

                AdapterConexao.setStatusRede(false, "Conexão Ativa.", true);

                StatusDAO dao = new StatusDAO();

                dao.open();

                Status st = dao.seek(null);

                if (st == null){

                    dao.insert(new Status("N",MV_ZBLEPED,MV_ZDTEPED,MV_ZHREPED,MV_ZBLECRG,MV_ZDTECRG,MV_ZHRECRG,"N","","","","0"));

                } else {

                    st.setAPPBLOCK("N");
                    st.setPEDIDO(MV_ZBLEPED);
                    st.setPEDDATA(MV_ZDTEPED);
                    st.setPEDHORA(MV_ZHREPED);
                    st.setCARGA(MV_ZBLECRG);
                    st.setCARDATA(MV_ZDTECRG);
                    st.setCARHORA(MV_ZHRECRG);


                    dao.Update(st);

                }

                dao.close();

                img_conexao.setImageResource(R.drawable.conectado);

                AdapterConexao.setStatusRede(false, "Conexão Ativa.", true);

                setarCabec();

                //Força atualização de versão

                if (vezes == 0) {


                    if (!getActivity().getResources().getString(R.string.app_versao).trim().equals(MV_SAV700.trim())) {

                        Intent intent = new Intent(getActivity(), UpdateVersionActivity.class);
                        Bundle params = new Bundle();
                        params.putBoolean("isUpdate"   , true);
                        intent.putExtras(params);
                        getActivity().startActivityForResult(intent, HelpInformation.UpdateVersion);

                    }
                }

                vezes++;

            } else {
                //Altera algumas mensagens
                if (cmsgerro.contains("failed to connect")) {

                    cmsgerro = "Falha de Conexão.";

                } else {

                    cmsgerro = cmsgerro;

                }

                AdapterConexao.setStatusRede(false,cmsgerro,false);


                img_conexao.setImageResource(R.drawable.desconectado);

            }


        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

        }
    }

    public void loadPedidosEmAberto(){

        if (App.user == null || App.user.getCOD().trim().isEmpty()){

            lsPedidos    = new ArrayList<>();

            lsPedidos.add("cabec");

            return;

        }


        try {

            lsPedidos    = new ArrayList<>();

            lsPedidos.add("cabec");

            //Pedidos
            {
                PedCabTvsDAO dao = new PedCabTvsDAO();

                dao.open();

                lsPedidos.addAll(dao.getAllNaoFaturados(new String[]{"", "", "D"}));

                dao.close();
            }
            if (lsPedidos.size() == 1) {

                lsPedidos    = new ArrayList<>();

                lsPedidos.add(new NoData("Nenhum Pedido Encontrado !!"));

            }


            adapterPedido = new AdapterPedido(getContext(), lsPedidos);

            lv_db_01b.setAdapter(adapterPedido);

            adapterPedido.notifyDataSetChanged();

            lsPedidosResumo = new ArrayList<>();

            lsPedidosResumo.addAll(adapterPedido.getStatus());

            FiltroAdapterPedido = new defaultAdapter(getContext(), R.layout.choice_default_row, lsPedidosResumo,"BROTO:");

            sp_tabela_01b.setAdapter(FiltroAdapterPedido);

            sp_tabela_01b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    FiltroAdapterPedido.setEscolha(position);

                    Object lixo = sp_tabela_01b.getSelectedItem();

                    adapterPedido.setFiltro(((String[]) lixo)[0]);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            sp_tabela_01b.setSelection(0);


        } catch (Exception e) {

            Toast.makeText(getContext(), "Erro Na Carga Pedidos: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }





    }

    public void loadClientes(){


        if (App.user == null || App.user.getCOD().trim().isEmpty()){

            lsClientes    = new ArrayList<>();

            lsClientes.add("cabec");

            return;

        }


        try {

            lsClientes    = new ArrayList<>();

            lsClientes.add("cabec");

            //Agendamentos
            {
                AgendamentoDAO dao = new AgendamentoDAO();

                dao.open();

                ResumoAgendamento resumoAgendamento = dao.getAllByByStatus("T");

                dao.close();

                if (resumoAgendamento.getQtd() != 0)  lsClientes.add(resumoAgendamento);

            }
            ClienteDAO dao = new ClienteDAO();

            dao.open();

            lsClientes.addAll(dao.getAll_fastByOnlyFlags("razao"));

            dao.close();

            if (lsClientes.size() == 1) {

                lsClientes    = new ArrayList<>();

                lsClientes.add(new NoData("Nenhum Pedido Encontrado !!"));

            }


            adapterCliente = new AdapterCliente(getContext(), lsClientes);


            lv_db_01a.setAdapter(adapterCliente);

            adapterCliente.notifyDataSetChanged();

            lsClientesResumo = new ArrayList<>();

            lsClientesResumo.addAll(adapterCliente.getStatus());

            filtroAdapterCliente = new defaultAdapterClientes(getContext(), R.layout.choice_default_row, lsClientesResumo,"Tablet:");

            sp_tabela_01a.setAdapter(filtroAdapterCliente);

            sp_tabela_01a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    filtroAdapterCliente.setEscolha(position);

                    Object lixo = sp_tabela_01a.getSelectedItem();

                    adapterCliente.setFiltro(((String[]) lixo)[0]);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            sp_tabela_01a.setSelection(0);




        } catch (Exception e) {

            Toast.makeText(getActivity().getBaseContext(), "Erro Na Carga Clientes: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }



    }

    public void loadNotificacoes(){


        /*
        try {

            lsNotificacao    = new ArrayList<>();

            lsNotificacao.add("cabec");

            NotificacaoDAO dao = new NotificacaoDAO();

            dao.open();

            lsNotificacao.addAll(dao.getAllByToday(App.getHoje(),"007"));

            dao.close();

            if (lsNotificacao.size() == 1) {

                lsNotificacao    = new ArrayList<>();

                lsNotificacao.add(new NoData("Nenhuma Ocorrência\nEncontrada.\nNo Dia De Hoje!!"));

            }


            adapterNotificacao = new AdapterNotificacao(getContext(), lsNotificacao);

            lv_db_02a.setAdapter(adapterNotificacao);

            adapterNotificacao.notifyDataSetChanged();

            dao.open();

            lsNotificacaob    = new ArrayList<>();

            lsNotificacaob.add("cabec");

            lsNotificacaob.addAll(dao.getAllByToday(App.getHoje(),"005"));

            dao.close();

            if (lsNotificacaob.size() == 1) {

                lsNotificacaob    = new ArrayList<>();

                lsNotificacaob.add(new NoData("Nenhuma Ocorrência\nEncontrada.\nNo Dia De Hoje!!"));

            }

            adapterNotificacaob = new AdapterNotificacao(getContext(), lsNotificacaob);

            lv_db_02b.setAdapter(adapterNotificacaob);

            adapterNotificacaob.notifyDataSetChanged();

        } catch (Exception e) {

            Toast.makeText(getActivity().getBaseContext(), "Erro Na Carga Notificações: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

*/

    }

    public void setarCabec(){

        try {

            Calendar c = Calendar.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String hoje  = format.format(c.getTime());

            StatusDAO dao = new StatusDAO();

            dao.open();

            Status st = dao.seek(null);

            dao.close();

            if (!(st == null)) {

                String texto = "";

                if (hoje.equals(st.getCARDATA())){

                    texto =  "Carga Liberada Hoje";

                } else {

                    texto = "Carga Não Liberada Hoje";

                }
                if (hoje.equals(st.getULTATUAL())) {

                    texto +=  "\n Tabela Atualizada.";

                } else {

                    texto +=  "\n Tabela Fora Validade.";

                }

                lbl_tabela.setText(texto);

                lbl_fechamento.setText("FECHAMENTO DO MÊS\n"+st.getPEDDATA()+" às "+st.getPEDHORA());

            } else {

                lbl_tabela.setText("");

                lbl_fechamento.setText("");

            }

        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        img_refresh.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                VerConexoes();

            }

        });
    }

    private class AdapterCliente extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        private List<Object> lsOriginal;

        Context context;

        final int ITEM_VIEW_CABEC       = 0;
        final int ITEM_VIEW_DETALHE     = 1;
        final int ITEM_VIEW_AGENDAMENTO = 2;
        final int ITEM_VIEW_NO_DATA     = 3;
        final int ITEM_VIEW_COUNT       = 4;

        private int yellow    = 0;
        private int red       = 0;
        private int black     = 0;
        private String filtro = "0";

        private LayoutInflater inflater;

        public AdapterCliente(Context context, List<Object> pObjects) {


            this.lsOriginal = pObjects;
            this.lsObjetos  = filtro();

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void setFiltro(String filtro) {

            this.filtro = filtro;

            this.lsObjetos = filtro();

            notifyDataSetChanged();

        }

        private List<Object> filtro(){

            List<Object> retorno = new ArrayList<>();

            if (filtro.equals("0")) {

                return lsOriginal;

            }

            for(Object obj : lsOriginal){

                if (obj instanceof Cliente_fast ){

                    if (filtro.equals("1")){


                        if (((Cliente_fast) obj).get_yellow() > 0 ){


                            retorno.add(obj);


                        }

                    }

                    if (filtro.equals("2")){

                        if (((Cliente_fast) obj).get_red() > 0 ){


                            retorno.add(obj);


                        }

                    }


                    if (filtro.equals("3")) {


                    }


                } else {


                    retorno.add(obj);

                }

            }


            return retorno;

        }

        private void Cabec() {

            yellow = 0;
            red    = 0;
            black  = 0;



            for (Object obj : lsObjetos) {

                if (obj instanceof Cliente_fast) {

                    yellow += (((Cliente_fast) obj).get_yellow());

                    red    += (((Cliente_fast) obj).get_red());

                }

            }


        }


        @Override
        public int getCount() {
            return lsObjetos.size();
        }

        @Override
        public Object getItem(int position) {
            return lsObjetos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_COUNT;
        }


        @Override
        public int getItemViewType(int position) {

            int retorno = -1;


            if (lsObjetos.get(position) instanceof String) {

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsObjetos.get(position) instanceof Cliente_fast) {

                retorno = ITEM_VIEW_DETALHE;

            }

            if (lsObjetos.get(position) instanceof ResumoAgendamento) {

                retorno = ITEM_VIEW_AGENDAMENTO;

            }


            if (lsObjetos.get(position) instanceof NoData) {

                retorno = ITEM_VIEW_NO_DATA;

            }

            return retorno;


        }

        @Override
        public boolean isEnabled(int position) {

            boolean retorno = false;

            return retorno;

        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            try {

                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.cliat_row_2_cabec, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.cliat_row_2_detalhe, null);

                            break;


                        case ITEM_VIEW_AGENDAMENTO:

                            convertView = inflater.inflate(R.layout.agendamento2_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row2, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {


                        Cabec();
                        ImageView flag_verde_401    = (ImageView) convertView.findViewById(R.id.flag_verde_401);
                        ImageView flag_amarela_401  = (ImageView) convertView.findViewById(R.id.flag_amarela_401);
                        ImageView flag_vermelha_401 = (ImageView) convertView.findViewById(R.id.flag_vermelha_401);
                        ImageView flag_preta_401    = (ImageView) convertView.findViewById(R.id.flag_preta_401);

                        TextView txt_verde_401      = (TextView) convertView.findViewById(R.id.txt_verde_401);
                        TextView txt_amarela_401    = (TextView) convertView.findViewById(R.id.txt_amarela_401);
                        TextView txt_vermelha_401   = (TextView) convertView.findViewById(R.id.txt_vermelha_401);
                        TextView txt_preta_401      = (TextView) convertView.findViewById(R.id.txt_preta_401);
                        TextView txt_mensagem_401   = (TextView) convertView.findViewById(R.id.txt_mensagem_401);


                        flag_verde_401.setVisibility(View.GONE);
                        txt_verde_401.setVisibility(View.GONE);
                        flag_preta_401.setVisibility(View.GONE);
                        txt_preta_401.setVisibility(View.GONE);

                        if (this.yellow > 0) {

                            flag_amarela_401.setVisibility(View.VISIBLE);
                            txt_amarela_401.setVisibility(View.VISIBLE);
                            txt_amarela_401.setText(String.valueOf(this.yellow));


                        } else {

                            flag_amarela_401.setVisibility(View.INVISIBLE);
                            txt_amarela_401.setVisibility(View.INVISIBLE);
                            txt_amarela_401.setText("");

                        }

                        if (this.red > 0) {

                            flag_vermelha_401.setVisibility(View.VISIBLE);
                            txt_vermelha_401.setVisibility(View.VISIBLE);
                            txt_vermelha_401.setText(String.valueOf(this.yellow));


                        } else {

                            flag_vermelha_401.setVisibility(View.INVISIBLE);
                            txt_vermelha_401.setVisibility(View.INVISIBLE);
                            txt_vermelha_401.setText("");

                        }

                        if ( (this.yellow + this.red) == 0 ){

                            txt_mensagem_401.setVisibility(View.VISIBLE);

                            txt_mensagem_401.setText("TRANSMITA ESSES AGENDAMENTOS !");

                        }


                        break;
                    }

                    case ITEM_VIEW_DETALHE: {

                        final Cliente_fast obj = (Cliente_fast) lsObjetos.get(pos);

                        ImageButton bt_cadastro = (ImageButton) convertView.findViewById(R.id.bt_enviar_111);

                        ImageButton bt_pedidos = (ImageButton) convertView.findViewById(R.id.bt_pedidos_402);

                        ImageView flag_amarela_402  = (ImageView) convertView.findViewById(R.id.flag_amarela_402);
                        ImageView flag_vermelha_402 = (ImageView) convertView.findViewById(R.id.flag_vermelha_402);
                        ImageView flag_preta_402    = (ImageView) convertView.findViewById(R.id.flag_preta_402);

                        TextView txt_amarela_402    = (TextView) convertView.findViewById(R.id.txt_amarela_402);
                        TextView txt_vermelha_402   = (TextView) convertView.findViewById(R.id.txt_vermelha_402);
                        TextView txt_preta_402      = (TextView) convertView.findViewById(R.id.txt_preta_402);



                        TextView txt_cliente_402 = (TextView) convertView.findViewById(R.id.txt_cliente_402);

                        txt_cliente_402.setText("Código: "+obj.getCODIGO()+"-"+obj.getLOJA()+"\n"+obj.getRAZAO());

                        flag_preta_402.setVisibility(View.GONE);
                        txt_preta_402.setVisibility(View.GONE);

                        if (obj.get_yellow() > 0) {

                            flag_amarela_402.setVisibility(View.VISIBLE);
                            txt_amarela_402.setVisibility(View.VISIBLE);
                            txt_amarela_402.setText(String.valueOf(obj.get_yellow())+ " A TRANSMITIR");


                        } else {

                            flag_amarela_402.setVisibility(View.GONE);
                            txt_amarela_402.setVisibility(View.GONE);
                            txt_amarela_402.setText("");

                        }

                        if (obj.get_red() > 0) {

                            flag_vermelha_402.setVisibility(View.VISIBLE);
                            txt_vermelha_402.setVisibility(View.VISIBLE);
                            txt_vermelha_402.setText(String.valueOf(obj.get_red())+" COM PROBLEMAS");


                        } else {

                            flag_vermelha_402.setVisibility(View.GONE);
                            txt_vermelha_402.setVisibility(View.GONE);
                            txt_vermelha_402.setText("");

                        }



                        bt_cadastro.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               Intent intent=new Intent(context,ClienteViewAtivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                               Bundle params = new Bundle();
                                                               params.putString("CODIGO"  , obj.getCODIGO());
                                                               params.putString("LOJA"    , obj.getLOJA());
                                                               intent.putExtras(params);
                                                               context.startActivity(intent);

                                                           }
                                                       }
                        );


                        bt_pedidos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(context, PedidosActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();
                                params.putString("CODIGO", obj.getCODIGO());
                                params.putString("LOJA"  , obj.getLOJA());
                                params.putString("ROTINA", "CONSULTA");
                                intent.putExtras(params);
                                context.startActivity(intent);

                            }
                        });

                        break;

                    }

                    case ITEM_VIEW_AGENDAMENTO: {

                        final ResumoAgendamento obj = (ResumoAgendamento) lsObjetos.get(pos);

                        ImageButton bt_enviar_111 = (ImageButton)convertView.findViewById(R.id.bt_enviar_111);

                        TextView txt_agendamentos_111 = (TextView) convertView.findViewById(R.id.txt_agendamentos_111);

                        txt_agendamentos_111.setText("Existem Agendamentos Não Transmitidos\nNum total de "+String.valueOf(obj.getQtd())+" Agendamentos.");

                        bt_enviar_111.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(context, PedidosActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();
                                params.putString("CODIGO", "");
                                params.putString("LOJA"  , "");
                                params.putString("ROTINA", "AGENDAMENTO");
                                intent.putExtras(params);
                                context.startActivity(intent);

                            }
                        });
                        break;
                    }

                    case ITEM_VIEW_NO_DATA: {

                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto2);

                        tvTexto.setText(obj.getMensagem());

                        break;

                    }


                    default:
                        break;
                }

            } catch (Exception e) {

                toast("Erro No Adapdador =>" + e.getMessage());

            }

            return convertView;

        }


        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }


        public List<String[]> getStatus(){

            Cabec();

            List<String[]> retorno = new ArrayList<>();


            if (lsObjetos.size() == 1){

                retorno.add(new String[]{"X", "NENHUM PEDIDO COM PROBLEMA"});


            }else {

                retorno.add(new String[]{"0", "TODOS"});

                if (this.yellow > 0) retorno.add(new String[]{"1", "FAVOR TRANSMITIR"+String.valueOf(this.yellow)});
                if (this.red   > 0)  retorno.add(new String[]{"2", "PEDIDOS COM PROBLEMAS"+String.valueOf(this.red)});

            }
            return retorno;

        }

    }

    private class AdapterPedido extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        private List<Object> lsOriginal;

        Context context;

        final int ITEM_VIEW_CABEC       = 0;
        final int ITEM_VIEW_DETALHE     = 1;
        final int ITEM_VIEW_NO_DATA     = 3;
        final int ITEM_VIEW_COUNT       = 4;

        private int verba      = 0;
        private int estoque    = 0;
        private int credito    = 0;
        private int pendente   = 0;

        private String filtro  = "0";

        private LayoutInflater inflater;

        public AdapterPedido(Context context, List<Object> pObjects) {

            this.lsOriginal  = pObjects;

            this.lsObjetos   = filtro();

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private void Cabec() {

            verba      = 0;
            estoque    = 0;
            credito    = 0;
            pendente   = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof PedCabTvs) {


                    if (((PedCabTvs) obj).getSITUACAO().trim().equals("PED DIST - BLOQUEADO VERBA") || ((PedCabTvs) obj).getSITUACAO().trim().equals("BLOQUEADO VERBA") ){


                        this.verba++;


                    }


                    if (((PedCabTvs) obj).getSITUACAO().trim().equals("PED DIST - BLOQUEADO CREDITO") || ((PedCabTvs) obj).getSITUACAO().trim().equals("BLOQUEADO CREDITO") ){


                        this.credito++;


                    }


                    if (((PedCabTvs) obj).getSITUACAO().trim().equals("PED DIST - BLOQUEADO ESTOQUE") || ((PedCabTvs) obj).getSITUACAO().trim().equals("BLOQUEADO ESTOQUE") ){


                        this.estoque++;


                    }


                    if (((PedCabTvs) obj).getSITUACAO().trim().equals("PENDENTE") ){


                        this.pendente++;


                    }


                }

            }


        }

        private List<Object> filtro(){

            List<Object> retorno = new ArrayList<>();

            if (filtro.equals("0")) {

                return lsOriginal;

            }

            for(Object obj : lsOriginal){

                if (obj instanceof PedCabTvs ){

                    if (filtro.equals("1")){


                        if (((PedCabTvs) obj).getSITUACAO().trim().equals("PENDENTE") ){


                            retorno.add(obj);


                        }

                    }

                    if (filtro.equals("2")){

                        if (((PedCabTvs) obj).getSITUACAO().trim().equals("PED DIST - BLOQUEADO VERBA") || ((PedCabTvs) obj).getSITUACAO().trim().equals("BLOQUEADO VERBA") ){


                            retorno.add(obj);


                        }

                    }



                    if (filtro.equals("3")) {


                        if (((PedCabTvs) obj).getSITUACAO().trim().equals("PED DIST - BLOQUEADO CREDITO") || ((PedCabTvs) obj).getSITUACAO().trim().equals("BLOQUEADO CREDITO")) {


                            retorno.add(obj);

                        }

                    }



                    if (filtro.equals("4")) {

                        if (((PedCabTvs) obj).getSITUACAO().trim().equals("PED DIST - BLOQUEADO ESTOQUE") || ((PedCabTvs) obj).getSITUACAO().trim().equals("BLOQUEADO ESTOQUE")) {


                            retorno.add(obj);


                        }
                    }
                } else {


                    retorno.add(obj);

                }

            }


            return retorno;

        }

        public void setFiltro(String filtro) {

            this.filtro = filtro;

            this.lsObjetos = filtro();

            notifyDataSetChanged();

        }

        @Override
        public int getCount() {
            return lsObjetos.size();
        }

        @Override
        public Object getItem(int position) {
            return lsObjetos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_COUNT;
        }

        @Override
        public int getItemViewType(int position) {

            int retorno = -1;


            if (lsObjetos.get(position) instanceof String) {

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsObjetos.get(position) instanceof PedCabTvs) {

                retorno = ITEM_VIEW_DETALHE;

            }

            if (lsObjetos.get(position) instanceof NoData) {

                retorno = ITEM_VIEW_NO_DATA;

            }

            return retorno;


        }

        @Override
        public boolean isEnabled(int position) {

            boolean retorno = false;

            return retorno;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            try {

                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.cliat_row_2_cabec  , null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.cliat_row_3_detalhe, null);

                            break;


                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row2       , null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {


                        Cabec();
                        ImageView flag_verde_401     = (ImageView) convertView.findViewById(R.id.flag_verde_401);
                        ImageView flag_amarela_401   = (ImageView) convertView.findViewById(R.id.flag_amarela_401);
                        ImageView flag_vermelha_401  = (ImageView) convertView.findViewById(R.id.flag_vermelha_401);
                        ImageView flag_preta_401     = (ImageView) convertView.findViewById(R.id.flag_preta_401);

                        TextView txt_verde_401    = (TextView) convertView.findViewById(R.id.txt_verde_401);
                        TextView txt_amarela_401  = (TextView) convertView.findViewById(R.id.txt_amarela_401);
                        TextView txt_vermelha_401 = (TextView) convertView.findViewById(R.id.txt_vermelha_401);
                        TextView txt_preta_401    = (TextView) convertView.findViewById(R.id.txt_preta_401);

                        if (this.pendente > 0) {

                            flag_verde_401.setVisibility(View.VISIBLE);
                            txt_verde_401.setVisibility(View.VISIBLE);
                            txt_verde_401.setText(String.valueOf(this.pendente));


                        } else {

                            flag_verde_401.setVisibility(View.GONE);
                            txt_verde_401.setVisibility(View.GONE);
                            txt_verde_401.setText("");

                        }

                        if (this.verba > 0) {

                            flag_amarela_401.setVisibility(View.VISIBLE);
                            txt_amarela_401.setVisibility(View.VISIBLE);
                            txt_amarela_401.setText(String.valueOf(this.verba));


                        } else {

                            flag_amarela_401.setVisibility(View.GONE);
                            txt_amarela_401.setVisibility(View.GONE);
                            txt_amarela_401.setText("");

                        }

                        if (this.credito > 0) {

                            flag_vermelha_401.setVisibility(View.VISIBLE);
                            txt_vermelha_401.setVisibility(View.VISIBLE);
                            txt_vermelha_401.setText(String.valueOf(this.credito));


                        } else {

                            flag_vermelha_401.setVisibility(View.GONE);
                            txt_vermelha_401.setVisibility(View.GONE);
                            txt_vermelha_401.setText("");

                        }

                        if (this.estoque > 0) {

                            flag_preta_401.setVisibility(View.VISIBLE);
                            txt_preta_401.setVisibility(View.VISIBLE);
                            txt_preta_401.setText(String.valueOf(this.estoque));


                        } else {

                            flag_preta_401.setVisibility(View.GONE);
                            txt_preta_401.setVisibility(View.GONE);
                            txt_preta_401.setText("");

                        }

                        break;
                    }

                    case ITEM_VIEW_DETALHE: {

                        final PedCabTvs obj = (PedCabTvs) lsObjetos.get(pos);

                        ImageButton bt_cadastro = (ImageButton) convertView.findViewById(R.id.bt_enviar_111);

                        ImageButton bt_pedidos = (ImageButton) convertView.findViewById(R.id.bt_pedidos_403);
                        ImageView flag_default_403  = (ImageView) convertView.findViewById(R.id.flag_default_403);
                        TextView txt_cliente_403    = (TextView) convertView.findViewById(R.id.txt_cliente_403);
                        TextView txt_default_403  = (TextView) convertView.findViewById(R.id.txt_default_403);

                        txt_cliente_403.setText(obj.getFILIAL()+"-"+obj.getPEDIDO()+" Entr: "+App.aaaammddToddmmaaaa(obj.getENTREGA())+" Total: "+format_02.format(obj.getTOTALPEDIDO())+" "+obj.getRAZAO());

                        txt_default_403.setText(obj.getSITUACAO());

                        bt_cadastro.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               Intent intent=new Intent(context,ClienteViewAtivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                               Bundle params = new Bundle();
                                                               params.putString("CODIGO"  , obj.getCLIENTE());
                                                               params.putString("LOJA"    , obj.getLOJA());
                                                               intent.putExtras(params);
                                                               context.startActivity(intent);

                                                           }
                                                       }
                        );


                        bt_pedidos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getContext(),PedidosProtheusGeralActivity.class);
                                Bundle params = new Bundle();
                                params.putString("CODCLIENTE", "");
                                params.putString("LOJCLIENTE", "");
                                params.putString("FILIAL"  ,obj.getFILIAL());
                                params.putString("PEDIDO",obj.getPEDIDO());
                                intent.putExtras(params);
                                startActivity(intent);

                            }
                        });

                        break;

                    }

                    case ITEM_VIEW_NO_DATA: {

                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto2);

                        tvTexto.setText(obj.getMensagem());

                        break;

                    }


                    default:
                        break;
                }

            } catch (Exception e) {

                toast("Erro No Adapdador =>" + e.getMessage());

            }

            return convertView;

        }


        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }


        public List<String[]> getStatus(){

            Cabec();

            List<String[]> retorno = new ArrayList<>();


            if (lsObjetos.size() == 1){

                retorno.add(new String[]{"X", "NENHUM PEDIDO PARADO NA BROTO"});


            }else {

                retorno.add(new String[]{"0", "TODOS"});

                if (this.pendente > 0) retorno.add(new String[]{"1", "PENDENTE "+String.valueOf(this.pendente)});
                if (this.verba > 0) retorno.add(new String[]{"2", "VERBA "+String.valueOf(this.verba)});
                if (this.credito > 0) retorno.add(new String[]{"3", "CREDITO "+String.valueOf(this.credito)});
                if (this.estoque > 0) retorno.add(new String[]{"4", "ESTOQUE "+String.valueOf(this.estoque)});

            }
            return retorno;

        }


    }

    private class AdapterNotificacao extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;


        Context context;

        final int ITEM_VIEW_CABEC       = 0;
        final int ITEM_VIEW_DETALHE     = 1;
        final int ITEM_VIEW_NO_DATA     = 3;
        final int ITEM_VIEW_COUNT       = 4;


        private LayoutInflater inflater;

        public AdapterNotificacao(Context context, List<Object> pObjects) {

            this.lsObjetos   = pObjects;

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Notificacao) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Ocorrências: " + String.valueOf(qtd);

            return retorno;
        }

        @Override
        public int getCount() {
            return lsObjetos.size();
        }

        @Override
        public Object getItem(int position) {
            return lsObjetos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_COUNT;
        }

        @Override
        public int getItemViewType(int position) {

            int retorno = -1;


            if (lsObjetos.get(position) instanceof String) {

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsObjetos.get(position) instanceof Notificacao) {

                retorno = ITEM_VIEW_DETALHE;

            }

            if (lsObjetos.get(position) instanceof NoData) {

                retorno = ITEM_VIEW_NO_DATA;

            }

            return retorno;


        }

        @Override
        public boolean isEnabled(int position) {

            boolean retorno = false;

            return retorno;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            try {

                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.defaultdivider  , null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.notificacao_row, null);

                            break;


                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row2       , null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_DETALHE: {

                        final Notificacao obj   = (Notificacao) lsObjetos.get(pos);


                        ImageButton im_doc_408  = (ImageButton) convertView.findViewById(R.id.im_doc_408);

                        TextView txt_pedido_408 = (TextView) convertView.findViewById(R.id.txt_pedido_408);
                        TextView txt_data_408   = (TextView) convertView.findViewById(R.id.txt_data_408);
                        TextView txt_nota_408   = (TextView) convertView.findViewById(R.id.txt_nota_408);


                        if (obj.getCODIGO().equals("005")){

                            im_doc_408.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {

                                        PreAcordoDAO dao = new PreAcordoDAO();

                                        dao.open();

                                        PreAcordo preAcordo = dao.seek(new String[] {obj.getDOC()});

                                        dao.close();

                                        if (!(preAcordo == null) ) {

                                            Intent intent = new Intent(getContext(), CadastroPreAcordoActivity.class);
                                            Bundle params = new Bundle();
                                            params.putString("ID", preAcordo.getCODMOBILE());
                                            params.putString("CODVERBA", preAcordo.getCODVERB());
                                            params.putString("STATUS", preAcordo.getSTATUS());
                                            intent.putExtras(params);
                                            startActivity(intent);

                                        } else {

                                            toast("Pré-Acordo Não Encontrado !");

                                        }
                                    } catch (Exception e){

                                        toast(e.getMessage());


                                    }
                                }
                            });


                        }

                        txt_pedido_408.setText(obj.get_Pedido()+"\n"+obj.get_Cliente());
                        txt_data_408.setText(obj.get_Hora());
                        txt_nota_408.setText(obj.getMENSAGEM());


                        break;

                    }

                    case ITEM_VIEW_NO_DATA: {

                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto2);

                        tvTexto.setText(obj.getMensagem());

                        break;

                    }


                    default:
                        break;
                }

            } catch (Exception e) {

                toast("Erro No Adapdador =>" + e.getMessage());

            }

            return convertView;

        }


        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }


    }

    private class defaultAdapter extends ArrayAdapter {

        private int escolha = 0;

        private List<String[]> lista;

        private String label;

        private boolean isInicializacao = true;

        private Context context;

        public defaultAdapter(Context context, int textViewResourceId, List<String[]> objects,String label) {

            super(context, textViewResourceId,objects);

            this.lista = objects;

            this.label = label;

            this.context = context;
        }


        public String getOpcao(int pos){


            if ( (pos < this.lista.size() )){


                return lista.get(pos)[1];

            }

            return "";

        }

        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }

        public View getOpcoesView(int position, View convertView, ViewGroup parent) {

            String obj = lista.get(position)[1];

            String op  = lista.get(position)[0];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            layout.setBackgroundResource(R.color.white);

            TextView label   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            label.setVisibility(View.GONE);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setTextSize(18f);

            tvOpcao.setText(obj);

            tvOpcao.setTextColor(Color.RED);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            img.setVisibility(View.VISIBLE);

            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) img.getLayoutParams();

            params.width = 120; params.height = 80;

            img.setLayoutParams(params);

            switch (op.charAt(0)){

                case '1':img.setImageResource(R.drawable.ic_action_flag_verde); break;
                case '2':img.setImageResource(R.drawable.ic_action_flag_amarela); break;
                case '3':img.setImageResource(R.drawable.ic_action_flag_vermelha); break;
                case '4':img.setImageResource(R.drawable.ic_action_flag_preta); break;
                default:img.setVisibility(View.INVISIBLE);
            }

            if (position == escolha) {

                tvOpcao.setTextColor(Color.BLACK);
            }

            return layout;
        }

        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            String obj = lista.get(position)[1];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            tvlabel.setText(this.label);

            if (this.label.isEmpty()){

                tvlabel.setVisibility(View.GONE);

            }

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setText(obj);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            img.setVisibility(View.GONE);

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
    }

    private class defaultAdapterClientes extends ArrayAdapter {

        private int escolha = 0;

        private List<String[]> lista;

        private String label;

        private boolean isInicializacao = true;

        private Context context;

        public defaultAdapterClientes(Context context, int textViewResourceId, List<String[]> objects,String label) {

            super(context, textViewResourceId,objects);

            this.lista = objects;

            this.label = label;

            this.context = context;
        }


        public String getOpcao(int pos){


            if ( (pos < this.lista.size() )){


                return lista.get(pos)[1];

            }

            return "";

        }

        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }

        public View getOpcoesView(int position, View convertView, ViewGroup parent) {

            String obj = lista.get(position)[1];

            String op  = lista.get(position)[0];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            layout.setBackgroundResource(R.color.white);

            TextView label   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            label.setVisibility(View.GONE);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setTextSize(18f);

            tvOpcao.setText(obj);

            tvOpcao.setTextColor(Color.RED);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            img.setVisibility(View.VISIBLE);

            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) img.getLayoutParams();

            params.width = 120; params.height = 80;

            img.setLayoutParams(params);

            switch (op.charAt(0)){

                case '1':img.setImageResource(R.drawable.ic_action_flag_amarela); break;
                case '2':img.setImageResource(R.drawable.ic_action_flag_vermelha); break;
                default:img.setVisibility(View.INVISIBLE);
            }

            if (position == escolha) {

                tvOpcao.setTextColor(Color.BLACK);
            }

            return layout;
        }

        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            String obj = lista.get(position)[1];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            tvlabel.setText(this.label);

            if (this.label.isEmpty()){

                tvlabel.setVisibility(View.GONE);

            }

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setText(obj);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            img.setVisibility(View.GONE);

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
    }

    private class conexaoAdapter extends ArrayAdapter {

        private int escolha = -1;

        private String status = "";

        private Boolean visible = false;

        private boolean connected = false;

        public conexaoAdapter(Context context, int textViewResourceId, List<Config> objects) {

            super(context, textViewResourceId, objects);

        }

        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }

        public int getEscolha() {
            return escolha;
        }

        public void setStatusRede(Boolean visible,String msg, Boolean connected){

            if (!(connected == null)){

                this.connected = connected;

            }

            this.visible = visible;
            this.status  = msg;
            notifyDataSetChanged();

        }

        public boolean getConnected(){

            return this.connected;
        }

        public View getOpcoesView(final int position, View convertView, ViewGroup parent) {

            // Infla layout customizado
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.conexoes_opcoes, parent, false);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_15);

            tvOpcao.setText(lsConexoes.get(position).getDESCRICAO());

            tvOpcao.setTextColor(Color.rgb(75, 180, 225));

            ImageView img = (ImageView) layout.findViewById(R.id.img_15);

            if (position == 0) {

                img.setImageResource(R.drawable.wifi);

            } else {

                img.setImageResource(R.drawable.nuvem_ok);

            }

            return layout;
        }

        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            // Infla layout customizado
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.conexoes_escolha_db, parent, false);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_16);
            TextView tvRede  = (TextView) layout.findViewById(R.id.tvAtualizando_16);
            ProgressBar bpProcesso = (ProgressBar) layout.findViewById(R.id.img_atualizando_16);

            if (escolha == -1) {

                tvOpcao.setText("Escolha Uma Conexão");
                tvRede.setText("");
                if (visible) {
                    bpProcesso.setVisibility(View.VISIBLE);
                } else {
                    bpProcesso.setVisibility(View.INVISIBLE);
                }

            } else {

                String  label;

                if (lsConexoes.get(escolha).getDESCRICAO().contains("WIFI")){

                    label = lsConexoes.get(escolha).getDESCRICAO();

                } else {

                    int space = lsConexoes.get(escolha).getDESCRICAO().indexOf(' ')+1;
                    label = lsConexoes.get(escolha).getDESCRICAO().substring(space);

                }

                tvOpcao.setText(label);

                tvRede.setText(this.status);

                if (visible) {
                    bpProcesso.setVisibility(View.VISIBLE);
                    img_conexao.setVisibility(View.INVISIBLE);
                } else {
                    bpProcesso.setVisibility(View.INVISIBLE);
                    img_conexao.setVisibility(View.VISIBLE);
                }

            }


            tvOpcao.setTextColor(Color.rgb(75, 180, 225));

            ImageView img = (ImageView) layout.findViewById(R.id.img_16);

            if (position == 0) {

                img.setImageResource(R.drawable.wifi);

            } else {

                img.setImageResource(R.drawable.nuvem_ok);

            }

            // Setting Special atrributes for 1st element
            if (position == escolha) {

                tvOpcao.setTextSize(12f);

                tvOpcao.setTextColor(Color.BLACK);
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
    }



}
