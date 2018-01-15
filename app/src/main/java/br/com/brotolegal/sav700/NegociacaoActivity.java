package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.sav700.PDF.PedidoTotvsToHtml;
import br.com.brotolegal.sav700.VerificaWeb.ConexaoAdapter;
import br.com.brotolegal.sav700.VerificaWeb.Parametros;
import br.com.brotolegal.sav700.VerificaWeb.ParametrosAdapter;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.NegociacaoDAO;
import br.com.brotolegal.savdatabase.dao.PedCabTvsDAO;
import br.com.brotolegal.savdatabase.dao.PedDetTvsDAO;
import br.com.brotolegal.savdatabase.dao.PreAcordoDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Negociacao;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeAgendamentoAtrasado;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeTabelaPreco;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CADASTRO_PREACORDO;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;

public class NegociacaoActivity extends AppCompatActivity {

    private Spinner spConexao;

    private Spinner spParamentros;

    private List<Config> conexoes;

    private ConexaoAdapter conexaoadapter;

    private ParametrosAdapter parametrosadapter;

    private String StatusRede = "Status Não Verificado.";

    private List<Parametros> lsParametros = new ArrayList<>();

    private Config config;

    private Dialog dialog;

    private Boolean OrdemRefresh = true;

    private Toolbar toolbar;

    private ListView lv;

    private List<Object> lsLista;

    Spinner spAlcada;

    Spinner spVerba;

    Spinner spOrdem;

    EditText edPesquisa;

    Map<String,String> mpAlcada        = new TreeMap<String, String >();
    Map<String,String> mpVerba         = new TreeMap<String, String >();

    List<String[]> lsAlcada               = new ArrayList<>();

    List<String[]> lsVerba                = new ArrayList<>();

    List<String[]> lsOrdens               = new ArrayList<>();

    FloatingActionButton fab;

    defaultAdapter situacaoadapter;

    defaultAdapter alcadaadapter;

    defaultAdapter ordensadapter;

    Adapter adapter;

    CallBack callBack;

    String CodCliente = "";
    String LojCliente = "";

    PedCabTvs POS = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negociacao);

        try {

            toolbar = (Toolbar) findViewById(R.id.tb_negociacao_495);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Negociações");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            spConexao     = (Spinner) findViewById(R.id.spConexao_495);

            spParamentros = (Spinner) findViewById(R.id.spParametro_495);

            toolbar.inflateMenu(R.menu.menu_negociacao);

//            Intent i = getIntent();
//
//            if (i != null) {
//
//                Bundle params = i.getExtras();
//
//                CodCliente = params.getString("CODCLIENTE");
//
//                LojCliente = params.getString("LOJCLIENTE");
//
//            }

            verWeb();

            fab = (FloatingActionButton) findViewById(R.id.plus_negociacao_495);

            lv = (ListView) findViewById(R.id.lvNegociacao_495);

            spAlcada   = (Spinner) findViewById(R.id.sp_cidade_334);

            spVerba   = (Spinner) findViewById(R.id.sp_rede_334);

            spOrdem   = (Spinner) findViewById(R.id.sp_ordem_334);

            edPesquisa = (EditText) findViewById(R.id.edpesquisa_334);

            edPesquisa.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    v.setFocusable(true);
                    v.setFocusableInTouchMode(true);
                    return false;
                }
            });


            lsOrdens = new ArrayList<>();

            lsOrdens.add(new String[]{"01", "Nº Tablet     "});
            lsOrdens.add(new String[]{"02", "Rede          "});
            lsOrdens.add(new String[]{"03", "Razão Social  "});
            lsOrdens.add(new String[]{"04", "Código Cliente"});


            ordensadapter = new defaultAdapter(NegociacaoActivity.this, R.layout.choice_default_row, lsOrdens, "");

            spOrdem.setAdapter(ordensadapter);

            spOrdem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ordensadapter.setEscolha(position);

                    Object lixo = spOrdem.getSelectedItem();

                    if (lixo != null){

                        if (adapter != null){

                            adapter.setOrdem(((String[]) lixo)[0]);

                        }

                    }

                    edPesquisa.setText("");

                    switch (position) {
                        case 0:  //numerico tablet

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                            break;

                        case 1:  //numerico protheus

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);


                            break;

                        case 2:  //alpha razao social

                            edPesquisa.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);


                            break;

                        case 3:  //numerico codigo do cliente

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                            break;

                    }

                    if (!(lsLista == null)) {

                       loadNegociacao();

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spOrdem.setSelection(0);

            loadNegociacao();

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NegociacaoActivity.this,PedidosGeralActivity.class);
                    Bundle params = new Bundle();
                    params.putString("ROTINA"   , "CONSULTANEGOCIACAO");
                    intent.putExtras(params);
                    startActivity(intent);
                }
            });


        } catch (Exception e){

            toast(e.getMessage());

            finish();

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_negociacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_negociacao_sincronizar:{

                break;

            }


            default:

                finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {

        loadNegociacao();

        super.onResume();
    }


    @Override
    public void finish() {

        lsLista            = new ArrayList<Object>();

        lsAlcada           = new ArrayList<>();

        lsVerba            = new ArrayList<>();

        lsOrdens           = new ArrayList<>();

        super.finish();

    }


    private void loadNegociacao(){

        try {

            lsLista = new ArrayList<>();

            lsAlcada           = new ArrayList<>();

            lsVerba            = new ArrayList<>();

            lsOrdens           = new ArrayList<>();

            lsAlcada.add(new String[] {""  ,"TODAS"});

            lsVerba.add(new String[]  {""  ,"TODA"});

            lsLista.add("Pedidos De Negociação");

            Object lixo      = spOrdem.getSelectedItem();

            PedCabTvsDAO dao = new PedCabTvsDAO();

            dao.open();

            lsLista.addAll(dao.getAllPedidosComposicaoCarga());

            dao.close();

            if (lsLista.size() == 1){

                lsLista.add(new NoData("Nenhum Pedido De Negociação Encontrado !!!"));

            } else {

//                for (Object obj : lsLista) {
//
//                    if (obj instanceof PedCabTvs) {
//
//                        try {
//
//                            mpAlcada.put(((PedCabTvs) obj).getSITUACAO(), ((PedCabTvs) obj).getSITUACAO());
//
//                        } catch (Exception e) {
//
//                            //
//
//                        }
//
//                    }
//
//                }
//
//                for(Map.Entry<String, String> values : mpAlcada.entrySet()){
//
//                    lsAlcada.add(new String[] {values.getKey(),values.getValue()});
//
//                }
//
//                for(Map.Entry<String, String> values : mpVerba.entrySet()){
//
//                    lsVerba.add(new String[] {values.getKey(),values.getValue()});
//
//                }
            }

//            alcadaadapter = new defaultAdapter(NegociacaoActivity.this, R.layout.choice_default_row, lsAlcada,"Alçada:");
//
//            spAlcada.setAdapter(alcadaadapter);
//
//            spAlcada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                    OrdemRefresh = true;
//
//                    edPesquisa.setText("");
//
//                    alcadaadapter.setEscolha(position);
//
//                    Object lixo = spAlcada.getSelectedItem();
//
//                    adapter.setAlcada(((String[]) lixo)[0]);
//
//                    adapter.refresh();
//
//                    OrdemRefresh = false;
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//
//                }
//            });
//
//            spVerba.setSelection(0);
//
//            situacaoadapter = new defaultAdapter(NegociacaoActivity.this, R.layout.choice_default_row, lsVerba,"Verba.:");
//
//            spVerba.setAdapter(situacaoadapter);
//
//            spVerba.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                    OrdemRefresh = true;
//
//                    edPesquisa.setText("");
//
//                    situacaoadapter.setEscolha(position);
//
//                    Object lixo = spVerba.getSelectedItem();
//
//                    adapter.setVerba(((String[]) lixo)[0]);
//
//                    adapter.refresh();
//
//                    OrdemRefresh = false;
//
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//
//                }
//            });
//
//            spVerba.setSelection(0);
//
//            edPesquisa.addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                    try {
//
//
//                        if (!OrdemRefresh){
//
//                            spAlcada.setSelection(0);
//
//                            spVerba.setSelection(0);
//
//                            adapter.refresh2();
//
//                        }
//
//                    } catch (Exception e) {
//
//                        Log.i("SAV", e.getMessage());
//
//                    }
//
//                }
//            });

            adapter = new Adapter(getBaseContext(),lsLista);

            //adapter.setOrdem(((String[]) lixo)[0]);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        } catch (Exception e){

            toast(e.getMessage());

        }

    }


    private void toast(String mensagem){

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();



    }

    private Handler mHandlerRede = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    toast("NAO CONTEM AS CHAVES..");

                    return;

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    try {

                        callBack.processa();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }


            }
            catch (Exception e){

                toast(e.getMessage());

            }
        }
    };


    private void verWeb(){

        int IndiceConexao = 0;

        try {

            StatusDAO dao = new StatusDAO();

            dao.open();

            Status st = dao.seek(null);

            if (st != null) {

                String tempo;

                if (st.getCARGA().equals("N")){

                    tempo = "No Dia:"+st.getCARDATA() + " A Partir Das: "+st.getCARHORA();

                } else {

                    tempo = "";

                }


                lsParametros.add(new Parametros(0,st.getCargaStatus() ,tempo));

                if (st.getPEDIDO().equals("N")){

                    tempo = "Até Dia:"+st.getPEDDATA() + " Até As: "+st.getPEDHORA();

                } else {

                    tempo = "";

                }

                lsParametros.add(new Parametros(1,st.getPedidoStatus(),tempo));

            } else {

                lsParametros.add(new Parametros(0,"SEM INFORMAÇÃO",""));
            }

            dao.close();

            parametrosadapter = new ParametrosAdapter(NegociacaoActivity.this, R.layout.conexoes_opcoes, lsParametros);

            spParamentros.setAdapter(parametrosadapter);

            spParamentros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    parametrosadapter.setEscolha(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spParamentros.setSelection(1);

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

        try {

            ConfigDAO dao = new ConfigDAO();

            dao.open();

            Config config = dao.seek(new String[]{"000"});

            conexoes = dao.getConexoes();

            Config padrao = dao.seekByDescricao( new String[]{config.getDESCRICAO()});

            if (padrao != null) {

                IndiceConexao = 0;

                for( int x = 0; x < conexoes.size() ; x++){

                    if (padrao.getDESCRICAO().equals(conexoes.get(x).getDESCRICAO())) {

                        IndiceConexao = x;

                        break;
                    }
                }

            } else {

                IndiceConexao = 0;

            }

            dao.close();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

        conexaoadapter = new ConexaoAdapter(NegociacaoActivity.this, R.layout.conexoes_opcoes, conexoes);

        spConexao.setAdapter(conexaoadapter);

        spConexao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if ((conexaoadapter.getEscolha() == -2)) {

                    conexaoadapter.setEscolha(-1);

                } else {

                    conexaoadapter.setEscolha(position);

                    config = conexoes.get(position);

                    config.setCODIGO("000");

                    conexaoadapter.setStatusRede(true, "Verificando...", null);

                    callBack = new CallBack();

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerRede, getBaseContext(), App.user, "GETSTATUS", "GETSTATUS",RETORNO_TIPO_ESTUTURADO,PROCESSO_CUSTOM, config, callBack,-1);

                    acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

                    acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

                    acessoWeb.start();

                    try {

                        ConfigDAO dao = new ConfigDAO();

                        dao.open();

                        dao.Update(config);

                        dao.close();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), "Não Atualizada A Conexão !!\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spConexao.setSelection(IndiceConexao);


    }

    private class Adapter extends BaseAdapter {


        private String _Alcada     = "";
        private String _Verba      = "";
        private String _Ordem      = "";

        private DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private DecimalFormat format_03 = new DecimalFormat(",##0.000");
        private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
        private DecimalFormat format_05 = new DecimalFormat(",##0.00000");

        private List<Object> lsObjetos;

        private Context context;

        final int ITEM_VIEW_CABEC        = 0;
        final int ITEM_VIEW_PEDIDO       = 1;
        final int ITEM_VIEW_PRODUTOS     = 2;
        final int ITEM_VIEW_NO_DATA      = 3;
        final int ITEM_VIEW_COUNT        = 4;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos  = filtro();

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Negociacao) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total De Pedidos De Negociações: " + String.valueOf(qtd);

            return retorno;
        }

        public void setVerba(String filtro){

            _Verba = filtro;

        }

        public void setAlcada(String filtro){

            _Alcada = filtro;

        }

        public void setOrdem(String filtro){

            _Ordem = filtro;

        }

        public void refresh(){


            this.lsObjetos  = filtro();

            notifyDataSetChanged();

        }

        public void refresh2(){

            this.lsObjetos  = filtro2();

            notifyDataSetChanged();

        }

        private List<Object> filtro() {

            List<Object> result = null;

            if (_Alcada.equals("") && _Verba.equals("")) {

                return new ArrayList(lsLista);

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsLista.size(); x++) {

                    if (lsLista.get(x) instanceof PedCabTvs) {

//                        if (    (_Alcada.equals("") || (_Alcada.equals(((PreAcordo) lsLista.get(x)).getSTATUS()))) &&
//                                (_Verba.equals("") || _Verba.equals(((PreAcordo) lsLista.get(x)).getCODVERB())) ) {

                            result.add(lsLista.get(x));

//                        }
                    } else {

                        result.add(lsLista.get(x));

                    }

                }

            }

            if (result.size() == 1){

                result.add(new NoData("Nenhum Pedido De Negociação Para O Filtro !!!"));

            }
            return result;
        }

        private List<Object> filtro2() {

            List<Object> result = null;

            if (edPesquisa.getText().toString().trim().isEmpty()) {

                return new ArrayList(lsLista);

            }
            else {

                result = new ArrayList<>();

                for (int x = 0; x < lsLista.size(); x++) {

                    if (lsLista.get(x) instanceof PreAcordo) {


                        if (_Ordem.equals("01")) {//nro do tablet

                            if (((PreAcordo) lsLista.get(x)).getCODMOBILE().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }


                        }
                        if (_Ordem.equals("02")) {//nro protheus

                            if (((PreAcordo) lsLista.get(x)).getNUM().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }

                        }

                        if (_Ordem.equals("03")) {//razao social

                            if (((PreAcordo) lsLista.get(x)).get_RAZAO().toUpperCase().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }

                        }

                        if (_Ordem.equals("04")) {//codigo cliente

                            if (((PreAcordo) lsLista.get(x)).getCLIENTE().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }
                        }



                    } else {

                        result.add(lsLista.get(x));

                    }

                }

            }

            if (result.size() == 1){

                result.add(new NoData("Nenhum Cliente Para O Filtro !!!"));

            }
            return result;
        }

        private void insereDetalhe(int pos, String filial,String pedido) {

            PedCabTvs cab = ((PedCabTvs) lsObjetos.get(pos));

            List<PedDetTvs> produtos = new ArrayList<>();

            try {

                PedDetTvsDAO dao = new PedDetTvsDAO();

                dao.open();

                produtos = dao.seekByPedido(new String[]{filial, pedido});

                dao.close();

                if (produtos.size() == 0) {

                    toast("Não Encontrei Produtos Para Este Pedido !");

                    return ;

                }

                if (!(cab.getView_pedido())) {

                    lsObjetos.addAll(pos+1,produtos);


                } else {

                    for(int x = 0; x < lsObjetos.size(); x++){

                        if (lsObjetos.get(x) instanceof PedDetTvs) {

                            if ( ((PedDetTvs) lsObjetos.get(x)).getFILIAL().equals(filial) && ((PedDetTvs) lsObjetos.get(x)).getPEDIDO().equals(pedido)  ) {

                                lsObjetos.remove(x);

                                x--;

                            }


                        }
                    }
                }

                cab.setView_pedido(!cab.getView_pedido());

                notifyDataSetChanged();

            } catch (Exception e){

                toast(e.getMessage());

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

            if (lsObjetos.get(position) instanceof PedCabTvs) {

                retorno = ITEM_VIEW_PEDIDO;
            }

            if (lsObjetos.get(position) instanceof PedDetTvs) {

                retorno = ITEM_VIEW_PRODUTOS;
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

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_PEDIDO:

                            convertView = inflater.inflate(R.layout.pedido_protheus_row, null);

                            break;


                        case ITEM_VIEW_PRODUTOS:

                            convertView = inflater.inflate(R.layout.pedido_totvs_det_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_PEDIDO: {

                        final PedCabTvs obj = (PedCabTvs) lsObjetos.get(pos);


                        ImageButton bt_cadastro   = (ImageButton) convertView.findViewById(R.id.bt_cadastro_497);
                        ImageButton bt_nf         = (ImageButton) convertView.findViewById(R.id.bt_nf_497);
                        ImageButton bt_pedido     = (ImageButton) convertView.findViewById(R.id.bt_alteracao_497);
                        ImageButton bt_pdf_497    = (ImageButton) convertView.findViewById(R.id.bt_pdf_497);
                        ImageButton bt_pedido_497 = (ImageButton) convertView.findViewById(R.id.bt_pedido_497);
                        ImageButton bt_browse_497 = (ImageButton) convertView.findViewById(R.id.bt_browse_497);

                        if (obj.getView_pedido()){

                            bt_pedido.setImageResource(R.drawable.volta_carrinho_26);

                        } else {

                            bt_pedido.setImageResource(R.drawable.carrinho_26);

                        }

                        TextView txt_situacao_497 = (TextView) convertView.findViewById(R.id.txt_situacao_497);

                        txt_situacao_497.setText("Situação: "+obj.getSITUACAO());

                        TextView txt_nro_pedido_497 = (TextView) convertView.findViewById(R.id.txt_nro_pedido_497);

                        txt_nro_pedido_497.setText("Nº Pedido: " + obj.getFILIAL()+"-"+obj.getPEDIDO()+" Ped. Cliente: "+ obj.getPEDIDOCLIENTE());

                        TextView txt_nro_tablete_497 = (TextView) convertView.findViewById(R.id.txt_nro_tablete_497);

                        txt_nro_tablete_497.setText("Pedido Tablet: "+obj.getPEDIDOMOBILE());

                        TextView txt_emissao_497 = (TextView) convertView.findViewById(R.id.txt_emissao_497);

                        txt_emissao_497.setText("Emissao: "+App.aaaammddToddmmaaaa(obj.getEMISSAO()));

                        TextView txt_entrega_497 = (TextView) convertView.findViewById(R.id.txt_entrega_497);

                        txt_entrega_497.setText("Entrega: "+App.aaaammddToddmmaaaa(obj.getENTREGA()));

                        TextView txt_cliente_497 = (TextView) convertView.findViewById(R.id.txt_cliente_497);

                        txt_cliente_497.setText("Cliente: "+obj.getCLIENTE()+"-"+obj.getLOJA()+" "+obj.getRAZAO().trim());

                        TextView txt_cnpj_497 = (TextView) convertView.findViewById(R.id.txt_cnpj_497);

                        txt_cnpj_497.setText("CNPJ/CPF: "+obj.get_CNPJ());

                        TextView txt_IE_497   = (TextView) convertView.findViewById(R.id.txt_ie_497);

                        txt_IE_497.setText("I.E.: "+obj.get_IE());

                        TextView txt_cidade_497   = (TextView) convertView.findViewById(R.id.txt_cidade_497);

                        txt_cidade_497.setText("Cidade: "+obj.get_CIDADE());

                        TextView txt_telefone_497   = (TextView) convertView.findViewById(R.id.txt_telefone_497);

                        txt_telefone_497.setText("Tel.: ("+obj.get_DDD()+") "+obj.get_TELEFONE());

                        TextView txt_condpagto_497  = (TextView) convertView.findViewById(R.id.txt_condpagto_497);

                        TextView txt_cliente_entrega_497   =   (TextView) convertView.findViewById(R.id.txt_cliente_entrega_497);

                        txt_cliente_entrega_497.setText("Cliente Entrega: "+obj.getCODIGOENTREGA()+"-"+obj.getLOJAENTREGA()+" "+obj.get_RAZAOENTREGA());

                        TextView txt_cidade_entrega_497    =   (TextView) convertView.findViewById(R.id.txt_cidade_entrega_497);

                        txt_cidade_entrega_497.setText("Cidade: "+obj.get_CIDADEENTREGA());

                        TextView txt_telefone_entrega_497  =    (TextView) convertView.findViewById(R.id.txt_telefone_entrega_497);

                        txt_telefone_entrega_497.setText("Tel: "+obj.get_TELEENTREGA());

                        if (obj.getCONDPAGTO().equals("033")) {

                            txt_condpagto_497.setTextColor(Color.RED);

                        } else {

                            txt_condpagto_497.setTextColor(Color.BLACK);
                        }

                        txt_condpagto_497.setText("Cond. Pagto: "+obj.getCONDPAGTO()+"-"+obj.getCPDESCRICAO());

                        TextView txt_tabpreco_497 =  (TextView) convertView.findViewById(R.id.txt_tabpreco_497);

                        txt_tabpreco_497.setText("");

                        TextView txt_obs_pedido_497 = (TextView) convertView.findViewById(R.id.txt_obs_pedido_497);

                        txt_obs_pedido_497.setText("Obs. Ped. "+obj.getOBSPED());

                        TextView txt_obs_nf_497 = (TextView) convertView.findViewById(R.id.txt_obs_nf_497);

                        txt_obs_nf_497.setText("Obs. NF "+obj.getOBSNOTA());

                        TextView txt_frete_497 = (TextView) convertView.findViewById(R.id.txt_frete_497);

                        txt_frete_497.setText("FRETE: "+obj.get_TipoFrete());

                        TextView txt_frete_desc_retira_497 = (TextView) convertView.findViewById(R.id.txt_frete_desc_retira_497);

                        if (obj.getTIPOFRETE().equals("F")){

                            txt_frete_desc_retira_497.setText("Desconto Retira: "+format_02.format(obj.getDESCFRETE()));

                        } else {


                            txt_frete_desc_retira_497.setText("");

                        }

                        TextView txt_total_497 = (TextView) convertView.findViewById(R.id.txt_total_497);

                        txt_total_497.setText("Total Do Pedido: "+format_02.format(obj.getTOTALPEDIDO()));
//
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



                        bt_nf.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {

                                                         Intent intent=new Intent(context,ConsultaNFTotvsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                         Bundle params = new Bundle();
                                                         params.putString("FILIAL"  ,  obj.getFILIAL());
                                                         params.putString("PEDIDO"   , obj.getPEDIDO());
                                                         intent.putExtras(params);
                                                         context.startActivity(intent);

                                                     }
                                                 }
                        );


                        bt_pedido.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                insereDetalhe(pos,obj.getFILIAL(),obj.getPEDIDO());


                            }
                        });

                        bt_pdf_497.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    PedidoTotvsToHtml pedidoTotvsToHtml = new PedidoTotvsToHtml();
                                    Document document = new Document(PageSize.A4);
                                    String fileName = "PEDIDO.PDF";
                                    String path     = App.BasePath + "/" + App.AppPath + "/" + App.user.getCOD();
                                    File pdfFile    = new File(path+"/"+fileName);

                                    if (pdfFile.exists()){

                                        pdfFile.delete();

                                    }



                                    PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(path+"/"+fileName));


                                    document.open();
                                    document.addAuthor("MARCOS RENATO FALCONI");
                                    document.addCreator("SAV 7.00");
                                    document.addSubject("PEDIDO DO PROTHEUS");
                                    document.addCreationDate();
                                    document.addTitle("TESTE");

                                    XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

                                    worker.parseXHtml(pdfWriter,document, new StringReader(pedidoTotvsToHtml.PedidoToHtml2()));

                                    document.close();

                                    toast("Documento Gerado !!!!");

                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                        bt_pedido_497.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {

                                    App.tabelaValida();

                                } catch (ExceptionValidadeTabelaPreco e) {

                                    toast(e.getMessage());

                                    return;
                                }
                                catch (ExceptionValidadeAgendamentoAtrasado e) {

                                    Intent intent = new Intent(NegociacaoActivity.this,AgendamentosAtrasadosActivity.class);

                                    startActivity(intent);

                                    return;

                                } catch (Exception e) {

                                    toast(e.getMessage());

                                    return;

                                }

                                POS = obj;

                                Intent intent = new Intent(context, PedidoV10Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();
                                params.putString("CODIGO", obj.getCLIENTE());
                                params.putString("LOJA", obj.getLOJA());
                                params.putString("OPERACAO", "NOVO");
                                params.putString("NROPEDIDO", "");
                                params.putString("IDAGE",""); //todo
                                params.putString("NEGOCIACAO","S");
                                intent.putExtras(params);
                                context.startActivity(intent);

                            }
                        });


                        bt_browse_497.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                POS = obj;
                                Intent intent = new Intent(context, PedidosActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();
                                params.putString("CODIGO", obj.getCLIENTE());
                                params.putString("LOJA", obj.getLOJA());
                                params.putString("ROTINA", "CONSULTANEGOCIACAO");
                                params.putString("IDAGE",""); //todo
                                intent.putExtras(params);
                                context.startActivity(intent);

                            }
                        });
                        break;

                    }

                    case ITEM_VIEW_PRODUTOS: {

                        final PedDetTvs obj = (PedDetTvs) lsObjetos.get(pos);

                        TextView txt_produto_017                = (TextView) convertView.findViewById(R.id.txt_produto_017);

                        TextView txt_qtd_017                    = (TextView) convertView.findViewById(R.id.txt_qtd_017);
                        TextView txt_prcven_017                 = (TextView) convertView.findViewById(R.id.txt_prcven_017);
                        TextView txt_desconto_017               = (TextView) convertView.findViewById(R.id.txt_desconto_017);
                        TextView txt_desconto_verba_017         = (TextView) convertView.findViewById(R.id.txt_desconto_verba_017);
                        TextView txt_doc_017                    = (TextView) convertView.findViewById(R.id.txt_doc_017);
                        TextView txt_total_017                  = (TextView) convertView.findViewById(R.id.txt_total_017);
                        TextView txt_acordo_017                 = (TextView) convertView.findViewById(R.id.txt_acordo_017);


                        TextView txt_verba_descricao_017        = (TextView) convertView.findViewById(R.id.txt_verba_descricao_017);
                        txt_produto_017.setText(obj.getITEM()+" "+obj.getPRODUTO().trim()+" "+obj.getDESCRICAO());
                        txt_qtd_017.setText(format_02.format(obj.getQTD()));
                        txt_prcven_017.setText(format_04.format(obj.getPRCVEN()));
                        txt_desconto_017.setText(format_05.format(obj.getDESCONTO()));
                        txt_desconto_verba_017.setText(format_05.format(obj.getDESCVER()));
                        txt_doc_017.setText(obj.getPDFILIAL()+obj.getPDNUMERO()+obj.getSIMULADOR());
                        txt_total_017.setText(format_02.format(obj.getTOTAL()));

                        txt_verba_descricao_017.setText(obj.getCODVERBA()+"-"+obj.getDESCRICAOVERBA());

                        if (!obj.getACORDO().trim().isEmpty())  txt_acordo_017.setText("Acordo: "+obj.getACORDO());

                        if (!obj.getCOTA().trim().isEmpty())  txt_acordo_017.setText("Cota: "+obj.getCOTA());

                        break;

                    }

                    case ITEM_VIEW_NO_DATA: {

                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

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

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);


            TextView label   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            label.setVisibility(View.GONE);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setTextSize(18f);

            tvOpcao.setText(obj);

            tvOpcao.setTextColor(Color.RED);

            tvOpcao.setBackgroundResource(R.color.white);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            img.setVisibility(View.GONE);

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

    private void LoadStatus() {

        try {

            lsParametros = new ArrayList<>();

            StatusDAO dao = new StatusDAO();

            dao.open();

            Status st = dao.seek(null);

            if (st != null) {

                String tempo;

                if (st.getCARGA().equals("N")) {

                    tempo = "No Dia:" + st.getCARDATA() + " A Partir Das: " + st.getCARHORA();

                } else {

                    tempo = "";

                }


                lsParametros.add(new Parametros(0, st.getCargaStatus(), tempo));

                if (st.getPEDIDO().equals("N")) {

                    tempo = "Até o Dia:" + st.getCARDATA() + " Até As: " + st.getCARHORA();

                } else {

                    tempo = "";

                }

                lsParametros.add(new Parametros(1, st.getPedidoStatus(), tempo));

            } else {

                lsParametros.add(new Parametros(0, "SEM INFORMAÇÃO", ""));
            }

            dao.close();

            parametrosadapter = new ParametrosAdapter(NegociacaoActivity.this, R.layout.conexoes_opcoes, lsParametros);

            spParamentros.setAdapter(parametrosadapter);

            spParamentros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    parametrosadapter.setEscolha(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            if (conexaoadapter == null){


            } else {

                spParamentros.setSelection(conexaoadapter.getEscolha());

            }


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

    }

    private class CallBack extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro       = result.getPropertyAsString("CERRO");
            String cmsgerro    = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                String MV_ZBLECRG  = App.TotvsSN(result.getPropertyAsString("MV_ZBLECRG"));
                String MV_ZBLEPED  = App.TotvsSN(result.getPropertyAsString("MV_ZBLEPED"));
                String MV_ZDTECRG  = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTECRG"));
                String MV_ZDTEPED  = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTEPED"));
                String MV_ZHRECRG  = result.getPropertyAsString("MV_ZHRECRG");
                String MV_ZHREPED  = result.getPropertyAsString("MV_ZHREPED");

                cmsgerro = "Conexão Ativa.";

                conexaoadapter.setStatusRede(false, cmsgerro, true);

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

                LoadStatus();

            } else {
                //Altera algumas mensagens
                if (cmsgerro.contains("failed to connect")) {

                    cmsgerro = "Falha de Conexão.";

                } else {

                    cmsgerro = cmsgerro;

                }

                conexaoadapter.setStatusRede(false,cmsgerro,false);
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

        }
    }

}
