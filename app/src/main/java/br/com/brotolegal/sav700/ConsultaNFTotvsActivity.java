package br.com.brotolegal.sav700;

/*

http://www.mysamplecode.com/2012/07/android-listview-load-more-data.html


 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.sav700.background.IRefreshScreen;
import br.com.brotolegal.sav700.background.LoadDB;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.NotaFiscalCabDAO;
import br.com.brotolegal.savdatabase.dao.NotaFiscalDetDAO;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.NotaFiscalCab;
import br.com.brotolegal.savdatabase.entities.NotaFiscalCab_fast;
import br.com.brotolegal.savdatabase.entities.NotaFiscalDet;

public class ConsultaNFTotvsActivity extends AppCompatActivity implements IRefreshScreen<Object> {

    private Boolean OrdemRefresh = true;

    private Toolbar toolbar;

    private ListView lv;

    private ProgressBar progressBar;

    private List<Object> lsLista;

    Spinner spFiliais;

    Spinner spSituacoes;

    Spinner spOrdem;

    EditText edPesquisa;

    Map<String,String> mpSituacao       = new TreeMap<String, String >();

    List<String[]> lsSituacoes          = new ArrayList<>();

    List<String[]> lsFiliais            = new ArrayList<>();

    List<String[]> lsOrdens             = new ArrayList<>();

    defaultAdapter situacaoadapter;

    defaultAdapter filialadapter;

    defaultAdapter ordensadapter;

    Adapter adapter;

    int Pagina        = -1;

    String CodCliente = "";
    String LojCliente = "";
    String PEDIDO     = "";
    String FILIAL     = "";


    int pageAtual   = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_nf_totvs);

        try {
            toolbar = (Toolbar) findViewById(R.id.tb_nfs_protheus_481);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Notas Fiscais Protheus");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.inflateMenu(R.menu.menu_nf_protheus);

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CodCliente = params.getString("CODCLIENTE","");

                LojCliente = params.getString("LOJCLIENTE","");

                FILIAL     = params.getString("FILIAL","");

                PEDIDO     = params.getString("PEDIDO","");

            }

            lv = (ListView) findViewById(R.id.lvnfsProtheus_481);

            progressBar = (ProgressBar) findViewById(R.id.progress_481);

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

            lsOrdens.add(new String[]{"01", "Nº Nota Fiscal"});
            lsOrdens.add(new String[]{"02", "Razão Social"});
            lsOrdens.add(new String[]{"03", "Código Cliente"});
            lsOrdens.add(new String[]{"04", "CNPJ"});
            lsOrdens.add(new String[]{"05", "Nº Pedido Protheus"});


            ordensadapter = new defaultAdapter(ConsultaNFTotvsActivity.this, R.layout.choice_default_row, lsOrdens, "");

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

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spOrdem.setSelection(0);

            loadNfs();

        } catch (Exception e){

            showToast(e.getMessage());

            finish();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nf_protheus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;
            case R.id.ac_nf_protheus_cancelar:

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


    private void loadNfs(){

        try {

            LoadDB load = new LoadDB(this,this,null);

            load.execute();


        }catch (Exception e){

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            Log.i("ADAPTADOR",exceptionAsString);

            showToast(e.getMessage());

        }
    }


    private void showToast(String mensagem){

        Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();

    }

    @Override
    public void refresh(ArrayList<Object> result) {

        Boolean ItsEnd = false;

        try {

            if (result.size() == 0){

                ItsEnd = true;

            }

            lsLista = new ArrayList<>();

            lsSituacoes        = new ArrayList<>();

            lsFiliais          = new ArrayList<>();

            lsOrdens           = new ArrayList<>();

            lsFiliais.add(new String[] {""  ,"TODAS"});
            lsFiliais.add(new String[] {"02","PORTO"});
            lsFiliais.add(new String[] {"07","MATRIZ"});

            lsSituacoes.add(new String[] {""   ,"TODAS"});
            lsSituacoes.add(new String[] {"V"  ,"VENDAS"});
            lsSituacoes.add(new String[] {"D"  ,"DEVOLUÇÕES"});

            lsLista.add("Notas");

            Object lixo = spOrdem.getSelectedItem();

            lsLista.addAll(result);

            if (lsLista.size() == 1){

                lsLista.add(new NoData("Nenhm Nota Encontrada !!!"));

            }

            filialadapter = new defaultAdapter(ConsultaNFTotvsActivity.this, R.layout.choice_default_row, lsFiliais,"Filial:");

            spFiliais.setAdapter(filialadapter);

            spFiliais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

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

            situacaoadapter = new defaultAdapter(ConsultaNFTotvsActivity.this, R.layout.choice_default_row, lsSituacoes,"Sit.:");

            spSituacoes.setAdapter(situacaoadapter);

            spSituacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

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


                        if (!OrdemRefresh){

                            spFiliais.setSelection(0);

                            spSituacoes.setSelection(0);

                            adapter.refresh2();

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

            if (!ItsEnd) {

                LoadDB load = new LoadDB(this, this, progressBar);

                load.execute();
            }

        } catch (Exception e){

            showToast(e.getMessage());

        }


    }

    @Override
    public void refreshOver(ArrayList<Object> result) {

        Boolean ItsEnd = false;

        try {

            if (result.size() == 0) {

                if (progressBar != null) {

                    progressBar.setVisibility(View.INVISIBLE);

                }


                return;

            }

            lsSituacoes = new ArrayList<>();

            lsFiliais = new ArrayList<>();

            lsOrdens = new ArrayList<>();

            lsFiliais.add(new String[]{"", "TODAS"});
            lsFiliais.add(new String[]{"02", "PORTO"});
            lsFiliais.add(new String[]{"07", "MATRIZ"});

            lsSituacoes.add(new String[]{"", "TODAS"});
            lsSituacoes.add(new String[]{"V", "VENDAS"});
            lsSituacoes.add(new String[]{"D", "DEVOLUÇÕES"});

            Object lixo = spOrdem.getSelectedItem();

            lsLista.addAll(result);



            filialadapter = new defaultAdapter(ConsultaNFTotvsActivity.this, R.layout.choice_default_row, lsFiliais, "Filial:");

            spFiliais.setAdapter(filialadapter);

            spFiliais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

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

            situacaoadapter = new defaultAdapter(ConsultaNFTotvsActivity.this, R.layout.choice_default_row, lsSituacoes, "Sit.:");

            spSituacoes.setAdapter(situacaoadapter);

            spSituacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

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
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {


                        if (!OrdemRefresh) {

                            spFiliais.setSelection(0);

                            spSituacoes.setSelection(0);

                            adapter.refresh2();

                        }

                    } catch (Exception e) {

                        Log.i("SAV", e.getMessage());

                    }

                }
            });

            adapter.addPage(result);


            if (progressBar != null) {

                progressBar.setVisibility(View.INVISIBLE);

            }

            if (!ItsEnd) {

                LoadDB load = new LoadDB(this, this, progressBar);

                load.execute();

            }


        } catch (Exception e){

            showToast(e.getMessage());

        }




    }

    @Override
    public List<Object> Loading() {

        List<Object> retorno = new ArrayList<>();

        try {

            NotaFiscalCabDAO dao = new NotaFiscalCabDAO();

            dao.open();

            if (lsLista == null) {

                pageAtual = 0;

            } else {

                pageAtual++;

            }

            if (!PEDIDO.isEmpty()) {

                retorno.addAll(dao.getNotaByPedido(new String[]{FILIAL, PEDIDO, "D", String.valueOf(pageAtual)}));

            } else {

                retorno.addAll(dao.getAllWithFiltro(new String[]{CodCliente, LojCliente, "D",String.valueOf(pageAtual)}));

            }

            dao.close();

        } catch (Exception e){

            retorno = null;

        }

        return retorno;

    }


    //Inner class

    private class Adapter extends BaseAdapter {


        private String _Filial     = "";
        private String _Situacao   = "";
        private String _Ordem      = "";

        private DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private DecimalFormat format_03 = new DecimalFormat(",##0.000");
        private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
        private DecimalFormat format_05 = new DecimalFormat(",##0.00000");

        private List<Object> lsObjetos;

        private Context context;

        final int ITEM_VIEW_CABEC    = 0;
        final int ITEM_VIEW_DETALHE  = 1;
        final int ITEM_VIEW_PRODUTOS = 2;
        final int ITEM_VIEW_NO_DATA  = 3;
        final int ITEM_VIEW_COUNT    = 4;

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

                if (obj instanceof NotaFiscalCab_fast) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Notas: " + String.valueOf(qtd);

            return retorno;
        }

        public void setFilial(String filtro){

            _Filial = filtro;

        }

        public void setSituacao(String filtro){

            _Situacao = filtro;

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

            if (_Filial.equals("") && _Situacao.equals("")) {

                return new ArrayList(lsLista);

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsLista.size(); x++) {

                    if (lsLista.get(x) instanceof NotaFiscalCab_fast) {

                        if (    (_Filial.equals("") || (_Filial.equals(((NotaFiscalCab_fast) lsLista.get(x)).getFILIAL()))) &&
                                (_Situacao.equals("") || _Situacao.equals(((NotaFiscalCab_fast) lsLista.get(x)).getTIPODOC())) ) {

                            result.add(lsLista.get(x));

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

        private List<Object> filtro2() {

            List<Object> result = null;

            if (edPesquisa.getText().toString().trim().isEmpty()) {

                return new ArrayList(lsLista);

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsLista.size(); x++) {

                    if (lsLista.get(x) instanceof NotaFiscalCab_fast) {


                        if (_Ordem.equals("01")) {//nro nota

                            if (((NotaFiscalCab_fast) lsLista.get(x)).getNOTAFISCAL().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }


                        }
                        if (_Ordem.equals("02")) {//razao

                            if (((NotaFiscalCab_fast) lsLista.get(x)).getNOMECLI().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }

                        }

                        if (_Ordem.equals("03")) {//codigo cliente

                            if (((NotaFiscalCab_fast) lsLista.get(x)).getCODCLI().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }
                        }
                        if (_Ordem.equals("04")) {//cnpj

                            if (((NotaFiscalCab_fast) lsLista.get(x)).get_CNPJ().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsLista.get(x));

                            }

                        }

                        if (_Ordem.equals("05")){//nro pedido protheus

                            if (((NotaFiscalCab_fast) lsLista.get(x)).getNUMPEDIDO().contains(edPesquisa.getText().toString().trim())){

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

        private void insereDetalhe(int pos, String filial,String serie,String nota) {

            NotaFiscalCab_fast cab = ((NotaFiscalCab_fast) lsObjetos.get(pos));

            List<NotaFiscalDet> produtos = new ArrayList<>();

            try {

                NotaFiscalDetDAO dao = new NotaFiscalDetDAO();

                dao.open();

                produtos = dao.seekByNota(new String[]{filial, serie, nota});

                dao.close();

                if (produtos.size() == 0) {

                    toast("Não Encontrei Produtos Para Esta Nota !");

                    return ;

                }

                if (!(cab.getView_nota())) {

                    lsObjetos.addAll(pos+1,produtos);


                } else {

                    for(int x = 0; x < lsObjetos.size(); x++){

                        if (lsObjetos.get(x) instanceof NotaFiscalDet) {

                            if ( ((NotaFiscalDet) lsObjetos.get(x)).getFILIAL().equals(filial) && ((NotaFiscalDet) lsObjetos.get(x)).getSERIE().equals(serie) && ((NotaFiscalDet) lsObjetos.get(x)).getNOTAFISCAL().equals(nota)  ) {

                                lsObjetos.remove(x);

                                x--;

                            }

                        }
                    }
                }

                cab.setView_nota(!cab.getView_nota());

                notifyDataSetChanged();

            } catch (Exception e){

                toast(e.getMessage());

            }

        }

        public void addPage(List<Object> lsPage){

            this.lsObjetos.addAll(lsPage);

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

            if (lsObjetos.get(position) instanceof NotaFiscalCab_fast) {

                retorno = ITEM_VIEW_DETALHE;

            }

            if (lsObjetos.get(position) instanceof NotaFiscalDet) {

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


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.nota_protheus_row, null);

                            break;


                        case ITEM_VIEW_PRODUTOS:

                            convertView = inflater.inflate(R.layout.nota_totvs_det_row, null);

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

                        final NotaFiscalCab_fast obj = (NotaFiscalCab_fast) lsObjetos.get(pos);

                        ImageButton bt_cadastro   = (ImageButton) convertView.findViewById(R.id.bt_cadastro_597);

                        ImageButton bt_pedidos    = (ImageButton) convertView.findViewById(R.id.bt_pedidos_597);

                        ImageButton bt_financeiro = (ImageButton) convertView.findViewById(R.id.bt_financeiro_597);

                        ImageButton bt_pedido     = (ImageButton) convertView.findViewById(R.id.bt_produtos_597);

                        if (obj.getView_nota()){

                            bt_pedido.setImageResource(R.drawable.volta_carrinho_26);

                        } else {

                            bt_pedido.setImageResource(R.drawable.carrinho_26);

                        }

                        TextView txt_situacao_597 = (TextView) convertView.findViewById(R.id.txt_situacao_597);

                        txt_situacao_597.setText(obj.get_Tipo());

                        TextView txt_nro_nota_597 = (TextView) convertView.findViewById(R.id.txt_nro_nota_597);

                        txt_nro_nota_597.setText("Nº : " + obj.getFILIAL()+ "-" + obj.getSERIE()+" " + obj.getNOTAFISCAL());

                        TextView txt_nro_protheus_597 = (TextView) convertView.findViewById(R.id.txt_nro_protheus_597);

                        txt_nro_protheus_597.setText("Pedido Protheus: "+obj.getNUMPEDIDO());

                        TextView txt_emissao_597 = (TextView) convertView.findViewById(R.id.txt_emissao_597);

                        txt_emissao_597.setText("Emissao: "+ App.aaaammddToddmmaaaa(obj.getDTEMISSAO()));

                        TextView txt_entrega_597 = (TextView) convertView.findViewById(R.id.txt_entrega_597);

                        txt_entrega_597.setText("Entrega: "+App.aaaammddToddmmaaaa(obj.getDTENTREGA()));

                        TextView txt_cliente_597 = (TextView) convertView.findViewById(R.id.txt_cliente_597);

                        txt_cliente_597.setText("Cliente: "+obj.getCODCLI()+"-"+obj.getCODLOJA()+" "+obj.getNOMECLI().trim());

                        TextView txt_cnpj_597 = (TextView) convertView.findViewById(R.id.txt_cnpj_597);

                        txt_cnpj_597.setText("CNPJ/CPF: "+obj.get_CNPJ());

                        TextView txt_IE_597   = (TextView) convertView.findViewById(R.id.txt_ie_597);

                        txt_IE_597.setText("I.E.: "+obj.get_IE());

                        TextView txt_cidade_597   = (TextView) convertView.findViewById(R.id.txt_cidade_597);

                        txt_cidade_597.setText("Cidade: "+obj.get_CIDADE());

                        TextView txt_telefone_597   = (TextView) convertView.findViewById(R.id.txt_telefone_597);

                        txt_telefone_597.setText("Tel.: ("+obj.get_DDD()+")"+obj.get_TELEFONE());

                        TextView txt_condpagto_597 = (TextView) convertView.findViewById(R.id.txt_condpagto_597);

                        txt_condpagto_597.setText("Cond. Pagto: "+obj.getCONDICAO());


                        TextView txt_romaneio_597 = (TextView) convertView.findViewById(R.id.txt_romaneio_597);

                        txt_romaneio_597.setText("Romaneio: "+obj.getROMANEIO());

                        TextView txt_transportadora_597 = (TextView) convertView.findViewById(R.id.txt_transportadora_597);

                        txt_transportadora_597.setText("Transportadora: "+obj.getCODTRANSP()+"-"+obj.getNOMTRANSP().trim()+" Fone: "+obj.getTELTRANSP());


                        TextView txt_chave_eletronica_597 = (TextView) convertView.findViewById(R.id.txt_chave_eletronica_597);

                        txt_chave_eletronica_597.setText("Chave Eletrônica "+obj.getCHAVE());

                        TextView txt_obs_nf_597 = (TextView) convertView.findViewById(R.id.txt_obs_nf_597);

                        txt_obs_nf_597.setText("Obs. NF "+obj.getOBSERVACAO());


                        TextView txt_total_597 = (TextView) convertView.findViewById(R.id.txt_total_597);

                        txt_total_597.setText("Total Da Nota: "+format_02.format(obj.getTOTALNF()));


                        TextView txt_canhoto_597 = (TextView) convertView.findViewById(R.id.txt_canhoto_597);

                        String texto = "";

                        if (!obj.getTIPODOC().equals("D")) {

                            if (obj.getDTCANHOTO().trim().isEmpty()) {

                                texto = "Canhoto Ainda Não Assinado!";

                            } else {

                                texto = "Canhoto Assinada Em " + App.aaaammddToddmmaaaa(obj.getDTCANHOTO());
                            }
                        }
                        txt_canhoto_597.setText(texto);


                        bt_cadastro.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               Intent intent=new Intent(context,ClienteViewAtivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                               Bundle params = new Bundle();
                                                               params.putString("CODIGO"  , obj.getCODCLI());
                                                               params.putString("LOJA"    , obj.getCODLOJA());
                                                               intent.putExtras(params);
                                                               context.startActivity(intent);

                                                           }
                                                       }
                        );

                        if (obj.getTIPODOC().equals("V")) {

                            bt_financeiro.setVisibility(View.VISIBLE);

                            bt_financeiro.setOnClickListener(new View.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(View v) {

                                                                     Intent intent = new Intent(context, Receber_View_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                     Bundle params = new Bundle();
                                                                     params.putString("FILIAL", obj.getFILIAL());
                                                                     params.putString("PREFIXO", obj.getSERIE());
                                                                     params.putString("NUM", obj.getNOTAFISCAL());
                                                                     intent.putExtras(params);
                                                                     context.startActivity(intent);

                                                                 }
                                                             }
                            );

                        } else {

                            bt_financeiro.setVisibility(View.INVISIBLE);


                        }
                        bt_pedido.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                insereDetalhe(pos,obj.getFILIAL(),obj.getSERIE(),obj.getNOTAFISCAL());


                            }
                        });

                        break;

                    }

                    case ITEM_VIEW_PRODUTOS: {

                        final NotaFiscalDet obj = (NotaFiscalDet) lsObjetos.get(pos);

                        TextView txt_produto_022                = (TextView) convertView.findViewById(R.id.txt_produto_022);

                        TextView txt_qtd_022                    = (TextView) convertView.findViewById(R.id.txt_qtd_022);
                        TextView txt_prcven_022                 = (TextView) convertView.findViewById(R.id.txt_prcven_022);
                        TextView txt_desconto_022               = (TextView) convertView.findViewById(R.id.txt_desconto_022);
                        TextView txt_total_022                  = (TextView) convertView.findViewById(R.id.txt_total_022);

                        txt_produto_022.setText(obj.getITEM()+" "+obj.getCODPRODUTO().trim()+" "+obj.getDESCRICAOPRODUTO());
                        txt_qtd_022.setText(format_02.format(obj.getQUANTIDADE()));
                        txt_prcven_022.setText(format_04.format(obj.getPRECOUNITARIO()));
                        txt_desconto_022.setText(format_05.format(obj.getDESCONTO()));
                        txt_total_022.setText(format_02.format(obj.getTOTAL()));

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


}
