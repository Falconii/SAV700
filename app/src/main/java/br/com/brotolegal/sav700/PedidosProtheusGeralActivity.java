package br.com.brotolegal.sav700;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.PedCabTvsDAO;
import br.com.brotolegal.savdatabase.dao.PedDetTvsDAO;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;

public class PedidosProtheusGeralActivity extends AppCompatActivity {

    private Boolean OrdemRefresh = true;

    private Toolbar toolbar;

    private ListView lv;

    private List<Object> lsLista;

    Spinner spFiliais;

    Spinner spSituacoes;

    Spinner spOrdem;

    EditText edPesquisa;

    Map<String,String> mpSituacao     = new TreeMap<String, String >();

    List<String[]> lsSituacoes          = new ArrayList<>();

    List<String[]> lsFiliais          = new ArrayList<>();

    List<String[]> lsOrdens           = new ArrayList<>();

    defaultAdapter situacaoadapter;

    defaultAdapter filialadapter;

    defaultAdapter ordensadapter;

    Adapter adapter;

    String CODCLIENTE = "";
    String LOJCLIENTE  = "";
    String PEDIDO     = "";
    String FILIAL     = "";
    String ACORDO     = "";

    /*
       PEDIDO
       CLIENTE
       ACORDO

     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_protheus_geral);

        try {
            toolbar = (Toolbar) findViewById(R.id.tb_pedidos_protheus_480);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Pedidos Protheus");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.inflateMenu(R.menu.menu_pedido_protheus);

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CODCLIENTE = params.getString("CODCLIENTE","");

                LOJCLIENTE = params.getString("LOJCLIENTE","");

                PEDIDO     = params.getString("PEDIDO","");

                FILIAL     = params.getString("FILIAL","");

                ACORDO     = params.getString("ACORDO","");


            }

            lv = (ListView) findViewById(R.id.lvPedidosProtheus_480);

            spFiliais = (Spinner) findViewById(R.id.sp_cidade_334);

            spSituacoes = (Spinner) findViewById(R.id.sp_rede_334);

            spOrdem = (Spinner) findViewById(R.id.sp_ordem_334);

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

            lsOrdens.add(new String[]{"01", "Nº Pedido Protheus"});
            lsOrdens.add(new String[]{"02", "Razão Social"});
            lsOrdens.add(new String[]{"03", "Código Cliente"});
            lsOrdens.add(new String[]{"04", "CNPJ"});
            lsOrdens.add(new String[]{"05", "Nº Pedido Tablete"});


            ordensadapter = new defaultAdapter(PedidosProtheusGeralActivity.this, R.layout.choice_default_row, lsOrdens, "");

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
                        case 0:  //numerico

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                            break;

                        case 1:  //texto maiusculo

                            edPesquisa.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                            break;

                        case 2:  //numerico

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                            break;

                        case 3:  //numerico

                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                            break;

                    }

                    if (!(lsLista == null)) {

                        loadPedidos();

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spOrdem.setSelection(0);



            loadPedidos();

        } catch (Exception e){

            showToast(e.getMessage());

            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedido_geral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;
            case R.id.ac_geral_cancelar:

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {

        //codigo aqui

        super.onResume();
    }


    @Override
    public void finish() {

        lsLista            = new ArrayList<Object>();

        lsSituacoes        = new ArrayList<>();

        lsFiliais          = new ArrayList<>();

        lsOrdens           = new ArrayList<>();

        super.finish();

    }


    private void loadPedidos(){

        try {

            lsLista = new ArrayList<>();

            lsSituacoes        = new ArrayList<>();

            lsFiliais          = new ArrayList<>();

            lsOrdens           = new ArrayList<>();

            lsFiliais.add(new String[] {""  ,"TODAS"});
            lsFiliais.add(new String[] {"02","PORTO"});
            lsFiliais.add(new String[] {"07","MATRIZ"});

            lsSituacoes.add(new String[] {""  ,"TODAS"});

            lsLista.add("Pedidos");

            Object lixo      = spOrdem.getSelectedItem();

            PedCabTvsDAO dao = new PedCabTvsDAO();

            dao.open();

            if  (ACORDO.trim().isEmpty() ){

                lsLista.addAll(dao.getAllWithFiltro(new String[] {CODCLIENTE,LOJCLIENTE,"D",FILIAL,PEDIDO}));

            } else {

                lsLista.addAll(dao.getPedidoByAcordo(new String[] {ACORDO,"D"} ));

            }


            dao.close();

            if (lsLista.size() == 1){

                lsLista.add(new NoData("Nenhm Pedido Encontrado !!!"));

            } else {

                for (Object obj : lsLista) {

                    if (obj instanceof PedCabTvs) {

                        try {

                            mpSituacao.put(((PedCabTvs) obj).getSITUACAO(), ((PedCabTvs) obj).getSITUACAO());


                        } catch (Exception e) {

                            //

                        }

                    }

                }

                for(Map.Entry<String, String> values : mpSituacao.entrySet()){

                    lsSituacoes.add(new String[] {values.getKey(),values.getValue()});

                }
            }

            filialadapter = new defaultAdapter(PedidosProtheusGeralActivity.this, R.layout.choice_default_row, lsFiliais,"Filial:");

            spFiliais.setAdapter(filialadapter);

            spFiliais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    filialadapter.setEscolha(position);

                    Object lixo = spFiliais.getSelectedItem();

                    adapter.setFilial(((String[]) lixo)[0]);

                    adapter.refresh();

                    OrdemRefresh = false;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spFiliais.setSelection(0);

            situacaoadapter = new defaultAdapter(PedidosProtheusGeralActivity.this, R.layout.choice_default_row, lsSituacoes,"Sit.:");

            spSituacoes.setAdapter(situacaoadapter);

            spSituacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    situacaoadapter.setEscolha(position);

                    Object lixo = spSituacoes.getSelectedItem();

                    adapter.setSituacao(((String[]) lixo)[0]);

                    adapter.refresh();

                    OrdemRefresh = false;


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spSituacoes.setSelection(0);


            edPesquisa.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,	int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {

                        if (spFiliais.getSelectedItemPosition() != 0) spFiliais.setSelection(0);

                        if (spSituacoes.getSelectedItemPosition() != 0) spSituacoes.setSelection(0);


                        adapter.setFilial("");

                        if (!OrdemRefresh){

                            adapter.refresh();

                        }

                    } catch (Exception e) {

                        Log.i("SAV", e.getMessage());

                    }

                }
            });

            adapter = new Adapter(getBaseContext(),lsLista);

            adapter.setOrdem(((String[]) lixo)[0]);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        } catch (Exception e){

            showToast(e.getMessage());

        }

    }


    private void showToast(String mensagem){

        Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();

    }


    //Inner class

    private class Adapter extends BaseAdapter {


        private String _Filial = "";
        private String _Situacao = "";
        private String _Ordem = "";

        private DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private DecimalFormat format_03 = new DecimalFormat(",##0.000");
        private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
        private DecimalFormat format_05 = new DecimalFormat(",##0.00000");
        private Map<String,String>  lsTipos  = new TreeMap<String, String >();


        private List<Object> lsObjetos;

        private Context context;

        final int ITEM_VIEW_CABEC = 0;
        final int ITEM_VIEW_DETALHE = 1;
        final int ITEM_VIEW_PRODUTOS = 2;
        final int ITEM_VIEW_TROCA = 3;
        final int ITEM_VIEW_NO_DATA = 4;
        final int ITEM_VIEW_COUNT = 5;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos = filtro();

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            loadTipoDescricao();

        }

        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof PedCabTvs) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Pedidos: " + String.valueOf(qtd);

            return retorno;
        }

        public void setFilial(String filtro) {

            _Filial = filtro;

        }

        public void setSituacao(String filtro) {

            _Situacao = filtro;

        }

        public void setOrdem(String filtro) {

            _Ordem = filtro;

        }

        public void refresh() {


            this.lsObjetos = filtro();

            notifyDataSetChanged();

        }


        private List<Object> filtro() {

            List<Object> result = null;

            if (edPesquisa.getText().toString().trim().isEmpty() && _Filial.equals("") && _Situacao.equals("")) {

                return new ArrayList(lsLista);

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsLista.size(); x++) {

                    if (lsLista.get(x) instanceof PedCabTvs) {

                        if ((edPesquisa.getText().toString().trim().isEmpty() || filtroPesquisa(x)) &&
                                (_Filial.equals("") || (_Filial.equals(((PedCabTvs) lsLista.get(x)).getFILIAL()))) &&
                                (_Situacao.equals("") || _Situacao.equals(((PedCabTvs) lsLista.get(x)).getSITUACAO()))) {

                            result.add(lsLista.get(x));

                        }
                    } else {

                        result.add(lsLista.get(x));

                    }

                }

            }

            if (result.size() == 1) {

                result.add(new NoData("Nenhum Cliente Para O Filtro !!!"));

            }
            return result;
        }


        private Boolean filtroPesquisa(int x) {

            Boolean retorno = false;

            if (_Ordem.equals("01")) {//nro do pedido

                if (((PedCabTvs) lsLista.get(x)).getPEDIDO().contains(edPesquisa.getText().toString().trim())) {

                    retorno = true;

                }


            }
            if (_Ordem.equals("02")) {//razao

                if (((PedCabTvs) lsLista.get(x)).getRAZAO().contains(edPesquisa.getText().toString().trim())) {

                    retorno = true;

                }

            }

            if (_Ordem.equals("03")) {//codigo cliente

                if (((PedCabTvs) lsLista.get(x)).getCLIENTE().contains(edPesquisa.getText().toString().trim())) {

                    retorno = true;

                }
            }
            if (_Ordem.equals("04")) {//cnpj

                if (((PedCabTvs) lsLista.get(x)).get_CNPJ().contains(edPesquisa.getText().toString().trim())) {

                    retorno = true;

                }

            }

            if (_Ordem.equals("05")) {//nro pedido tablete

                if (((PedCabTvs) lsLista.get(x)).getPEDIDOMOBILE().contains(edPesquisa.getText().toString().trim())) {

                    retorno = true;

                }

            }

            return retorno;


        }

        private void insereDetalhe(int pos, String filial, String pedido) {

            PedCabTvs cab = ((PedCabTvs) lsObjetos.get(pos));

            List<PedDetTvs> produtos = new ArrayList<>();

            try {

                PedDetTvsDAO dao = new PedDetTvsDAO();

                dao.open();

                produtos = dao.seekByPedido(new String[]{filial, pedido});

                dao.close();

                if (produtos.size() == 0) {

                    toast("Não Encontrei Produtos Para Este Pedido !");

                    return;

                }

                if (!(cab.getView_pedido())) {

                    lsObjetos.addAll(pos + 1, produtos);


                } else {

                    for (int x = 0; x < lsObjetos.size(); x++) {

                        if (lsObjetos.get(x) instanceof PedDetTvs) {

                            if (((PedDetTvs) lsObjetos.get(x)).getFILIAL().equals(filial) && ((PedDetTvs) lsObjetos.get(x)).getPEDIDO().equals(pedido)) {

                                lsObjetos.remove(x);

                                x--;

                            }


                        }
                    }
                }

                cab.setView_pedido(!cab.getView_pedido());

                notifyDataSetChanged();

            } catch (Exception e) {

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

                retorno = ITEM_VIEW_DETALHE;

            }

            if (lsObjetos.get(position) instanceof PedDetTvs) {


                if (((PedDetTvs) lsObjetos.get(position)).getFILIAL().trim().isEmpty()) {

                    retorno = ITEM_VIEW_TROCA;

                } else {

                    retorno = ITEM_VIEW_PRODUTOS;

                }

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


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.pedido_protheus_row, null);

                            break;


                        case ITEM_VIEW_PRODUTOS:

                            convertView = inflater.inflate(R.layout.pedido_totvs_det_row, null);

                            break;

                        case ITEM_VIEW_TROCA:

                            convertView = inflater.inflate(R.layout.pedido_totvs_troca_row, null);

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

                    case ITEM_VIEW_DETALHE: {

                        final PedCabTvs obj = (PedCabTvs) lsObjetos.get(pos);


                        ImageButton bt_cadastro = (ImageButton) convertView.findViewById(R.id.bt_cadastro_497);
                        ImageButton bt_nf = (ImageButton) convertView.findViewById(R.id.bt_nf_497);
                        ImageButton bt_pedido = (ImageButton) convertView.findViewById(R.id.bt_alteracao_497);
                        ImageButton bt_pdf_497 = (ImageButton) convertView.findViewById(R.id.bt_pdf_497);

                        if (obj.getView_pedido()) {

                            bt_pedido.setImageResource(R.drawable.volta_carrinho_26);

                        } else {

                            bt_pedido.setImageResource(R.drawable.carrinho_26);

                        }

                        TextView txt_situacao_497 = (TextView) convertView.findViewById(R.id.txt_situacao_497);

                        txt_situacao_497.setText("Tipo: " + get_Tipo(obj.getTIPO()) + " Sit.: " + obj.getSITUACAO());

                        TextView txt_nro_pedido_497 = (TextView) convertView.findViewById(R.id.txt_nro_pedido_497);

                        txt_nro_pedido_497.setText("Nº Pedido: " + obj.getFILIAL() + "-" + obj.getPEDIDO() + " Ped. Cliente: " + obj.getPEDIDOCLIENTE());

                        TextView txt_nro_tablete_497 = (TextView) convertView.findViewById(R.id.txt_nro_tablete_497);

                        txt_nro_tablete_497.setText("Pedido Tablet: " + obj.getPEDIDOMOBILE());

                        TextView txt_emissao_497 = (TextView) convertView.findViewById(R.id.txt_emissao_497);

                        txt_emissao_497.setText("Emissao: " + App.aaaammddToddmmaaaa(obj.getEMISSAO()));

                        TextView txt_entrega_497 = (TextView) convertView.findViewById(R.id.txt_entrega_497);

                        txt_entrega_497.setText("Entrega: " + App.aaaammddToddmmaaaa(obj.getENTREGA()));

                        TextView txt_cliente_497 = (TextView) convertView.findViewById(R.id.txt_cliente_497);

                        txt_cliente_497.setText("Cliente: " + obj.getCLIENTE() + "-" + obj.getLOJA() + " " + obj.getRAZAO().trim());

                        TextView txt_cnpj_497 = (TextView) convertView.findViewById(R.id.txt_cnpj_497);

                        txt_cnpj_497.setText("CNPJ/CPF: " + obj.get_CNPJ());

                        TextView txt_IE_497 = (TextView) convertView.findViewById(R.id.txt_ie_497);

                        txt_IE_497.setText("I.E.: " + obj.get_IE());

                        TextView txt_cidade_497 = (TextView) convertView.findViewById(R.id.txt_cidade_497);

                        txt_cidade_497.setText("Cidade: " + obj.get_CIDADE());

                        TextView txt_telefone_497 = (TextView) convertView.findViewById(R.id.txt_telefone_497);

                        txt_telefone_497.setText("Tel.: (" + obj.get_DDD() + ") " + obj.get_TELEFONE());

                        TextView txt_condpagto_497 = (TextView) convertView.findViewById(R.id.txt_condpagto_497);

                        TextView txt_cliente_entrega_497 = (TextView) convertView.findViewById(R.id.txt_cliente_entrega_497);

                        txt_cliente_entrega_497.setText("Cliente Entrega: " + obj.getCODIGOENTREGA() + "-" + obj.getLOJAENTREGA() + " " + obj.get_RAZAOENTREGA());

                        TextView txt_cidade_entrega_497 = (TextView) convertView.findViewById(R.id.txt_cidade_entrega_497);

                        txt_cidade_entrega_497.setText("Cidade: " + obj.get_CIDADEENTREGA());

                        TextView txt_telefone_entrega_497 = (TextView) convertView.findViewById(R.id.txt_telefone_entrega_497);

                        txt_telefone_entrega_497.setText("Tel: " + obj.get_TELEENTREGA());

                        if (obj.getCONDPAGTO().equals("033")) {

                            txt_condpagto_497.setTextColor(Color.RED);

                        } else {

                            txt_condpagto_497.setTextColor(Color.BLACK);
                        }

                        txt_condpagto_497.setText("Cond. Pagto: " + obj.getCONDPAGTO() + "-" + obj.getCPDESCRICAO());

                        TextView txt_tabpreco_497 = (TextView) convertView.findViewById(R.id.txt_tabpreco_497);

                        txt_tabpreco_497.setText("");

                        TextView txt_obs_pedido_497 = (TextView) convertView.findViewById(R.id.txt_obs_pedido_497);

                        txt_obs_pedido_497.setText("Obs. Ped. " + obj.getOBSPED());

                        TextView txt_obs_nf_497 = (TextView) convertView.findViewById(R.id.txt_obs_nf_497);

                        txt_obs_nf_497.setText("Obs. NF " + obj.getOBSNOTA());

                        TextView txt_frete_497 = (TextView) convertView.findViewById(R.id.txt_frete_497);

                        txt_frete_497.setText("FRETE: " + obj.get_TipoFrete());

                        TextView txt_frete_desc_retira_497 = (TextView) convertView.findViewById(R.id.txt_frete_desc_retira_497);

                        if (obj.getTIPOFRETE().equals("F")) {

                            txt_frete_desc_retira_497.setText("Desconto Retira: " + format_02.format(obj.getDESCFRETE()));

                        } else {


                            txt_frete_desc_retira_497.setText("");

                        }

                        TextView txt_total_497 = (TextView) convertView.findViewById(R.id.txt_total_497);

                        txt_total_497.setText("Total Do Pedido: " + format_02.format(obj.getTOTALPEDIDO()));
//
                        bt_cadastro.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               Intent intent = new Intent(context, ClienteViewAtivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                               Bundle params = new Bundle();
                                                               params.putString("CODIGO", obj.getCLIENTE());
                                                               params.putString("LOJA", obj.getLOJA());
                                                               intent.putExtras(params);
                                                               context.startActivity(intent);

                                                           }
                                                       }
                        );


                        bt_nf.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {

                                                         Intent intent = new Intent(context, ConsultaNFTotvsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                         Bundle params = new Bundle();
                                                         params.putString("FILIAL", obj.getFILIAL());
                                                         params.putString("PEDIDO", obj.getPEDIDO());
                                                         intent.putExtras(params);
                                                         context.startActivity(intent);

                                                     }
                                                 }
                        );


                        bt_pedido.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                insereDetalhe(pos, obj.getFILIAL(), obj.getPEDIDO());


                            }
                        });

                        bt_pdf_497.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    PedidoTotvsToHtml pedidoTotvsToHtml = new PedidoTotvsToHtml();
                                    Document document = new Document(PageSize.A4);
                                    String fileName = "PEDIDO.PDF";
                                    String path = App.BasePath + "/" + App.AppPath + "/" + App.user.getCOD();
                                    File pdfFile = new File(path + "/" + fileName);

                                    if (pdfFile.exists()) {

                                        pdfFile.delete();

                                    }


                                    PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(path + "/" + fileName));


                                    document.open();
                                    document.addAuthor("MARCOS RENATO FALCONI");
                                    document.addCreator("SAV 7.00");
                                    document.addSubject("PEDIDO DO PROTHEUS");
                                    document.addCreationDate();
                                    document.addTitle("TESTE");

                                    XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

                                    worker.parseXHtml(pdfWriter, document, new StringReader(pedidoTotvsToHtml.PedidoToHtml2()));

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


                        break;

                    }

                    case ITEM_VIEW_PRODUTOS: {

                        final PedDetTvs obj = (PedDetTvs) lsObjetos.get(pos);

                        TextView txt_produto_017 = (TextView) convertView.findViewById(R.id.txt_produto_017);

                        TextView txt_qtd_017 = (TextView) convertView.findViewById(R.id.txt_qtd_017);
                        TextView txt_prcven_017 = (TextView) convertView.findViewById(R.id.txt_prcven_017);
                        TextView txt_desconto_017 = (TextView) convertView.findViewById(R.id.txt_desconto_017);
                        TextView txt_desconto_verba_017 = (TextView) convertView.findViewById(R.id.txt_desconto_verba_017);
                        TextView txt_doc_017 = (TextView) convertView.findViewById(R.id.txt_doc_017);
                        TextView txt_total_017 = (TextView) convertView.findViewById(R.id.txt_total_017);
                        TextView txt_acordo_017 = (TextView) convertView.findViewById(R.id.txt_acordo_017);


                        TextView txt_verba_descricao_017 = (TextView) convertView.findViewById(R.id.txt_verba_descricao_017);
                        txt_produto_017.setText(obj.getITEM() + " " + obj.getPRODUTO().trim() + " " + obj.getDESCRICAO());
                        txt_qtd_017.setText(format_02.format(obj.getQTD()));
                        txt_prcven_017.setText(format_04.format(obj.getPRCVEN()));
                        txt_desconto_017.setText(format_05.format(obj.getDESCONTO()));
                        txt_desconto_verba_017.setText(format_05.format(obj.getDESCVER()));
                        txt_doc_017.setText(obj.getPDFILIAL() + obj.getPDNUMERO() + obj.getSIMULADOR());
                        txt_total_017.setText(format_02.format(obj.getTOTAL()));

                        txt_verba_descricao_017.setText(obj.getCODVERBA() + "-" + obj.getDESCRICAOVERBA());

                        if (!obj.getACORDO().trim().isEmpty())
                            txt_acordo_017.setText("Acordo: " + obj.getACORDO());

                        if (!obj.getCOTA().trim().isEmpty())
                            txt_acordo_017.setText("Cota: " + obj.getCOTA());

                        break;

                    }

                    case ITEM_VIEW_TROCA: {

                        final PedDetTvs obj = (PedDetTvs) lsObjetos.get(pos);

                        TextView txt_produto_017 = (TextView) convertView.findViewById(R.id.txt_produto_017);

                        TextView txt_qtd_017 = (TextView) convertView.findViewById(R.id.txt_qtd_017);
                        TextView txt_prcven_017 = (TextView) convertView.findViewById(R.id.txt_prcven_017);
                        TextView txt_total_017 = (TextView) convertView.findViewById(R.id.txt_total_017);
                        TextView txt_lote_017 = (TextView) convertView.findViewById(R.id.txt_lote_017);
                        TextView txt_empacotamento_017 = (TextView) convertView.findViewById(R.id.txt_empacotamento_017);
                        TextView txt_venc_017 = (TextView) convertView.findViewById(R.id.txt_venc_017);
                        TextView txt_verba_descricao_017 = (TextView) convertView.findViewById(R.id.txt_verba_descricao_017);
                        TextView txt_obs_017 = (TextView) convertView.findViewById(R.id.txt_obs_017);


                        txt_produto_017.setText(obj.getITEM() + " " + obj.getPRODUTO().trim() + " " + obj.getDESCRICAO());
                        txt_qtd_017.setText(format_02.format(obj.getQTD()));
                        txt_prcven_017.setText(format_04.format(obj.getPRCVEN()));
                        txt_total_017.setText(format_02.format(obj.getTOTAL()));
                        txt_lote_017.setText(obj.getPDNUMERO());
                        txt_empacotamento_017.setText(App.aaaammddToddmmaaaa(obj.getSIMULADOR()));
                        txt_venc_017.setText(App.aaaammddToddmmaaaa(obj.getCOTA()));
                        txt_verba_descricao_017.setText(obj.getCODVERBA()+"-"+obj.getDESCRICAOVERBA());
                        txt_obs_017.setText(obj.getACORDO());
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

        public String  get_Tipo(String tipo){

            String retorno = "";

            try {

                retorno = lsTipos.get(tipo);

            } catch (Exception e) {

                retorno = "Tipo Não Definido !!!";

            }

            return retorno;
        }

        private void loadTipoDescricao() {

            lsTipos = new TreeMap<String, String>();
            lsTipos.put("001", "Venda");
            lsTipos.put("003", "Bonificação");
            lsTipos.put("005", "Troca");
            lsTipos.put("006", "Devolução");
            lsTipos.put("007", "Amostra");
            lsTipos.put("010", "Dist. Venda");
            lsTipos.put("011", "Dist. Bonif");
            lsTipos.put("012", "Comp. Carga Venda");
            lsTipos.put("013", "Comp. Carga Bonif");

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


}
