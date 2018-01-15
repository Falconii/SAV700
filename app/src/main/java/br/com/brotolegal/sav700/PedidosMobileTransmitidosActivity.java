package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.MotivoDAO;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.dao.PedidoDetMbDAO;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.Motivo;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb_fast;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;
import br.com.brotolegal.savdatabase.eventbus.NotificationPedido;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CADASTRO_PREACORDO;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_EMAIL;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;


public class PedidosMobileTransmitidosActivity extends AppCompatActivity {


    static <K, V extends Comparable<? super V>>
    List<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

        List<Map.Entry<K, V>> sortedEntries = new ArrayList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(sortedEntries,
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        return App.aaaammddToddmmaaaa((String) e2.getValue()).compareTo(App.aaaammddToddmmaaaa((String) e1.getValue()));
                    }
                }
        );

        return sortedEntries;
    }

    Boolean OrdemRefresh = true;

    Toolbar toolbar;

    ListView lv;

    Spinner spData;

    Spinner spRede;

    Spinner spOrdem;

    EditText edPesquisa;

    Dialog dialog;

    List<Object> lsLista;

    List<Motivo> lsMotivos = new ArrayList<>();

    Map<String, String> mpDatas = new TreeMap<String, String>();

    Map<String, String> mpRedes = new TreeMap<String, String>();

    List<String[]> lsDatas = new ArrayList<>();

    List<String[]> lsRedes = new ArrayList<>();

    List<String[]> lsOrdens = new ArrayList<>();

    defaultAdapter cidadeadapter;

    defaultAdapter redeadapter;

    defaultAdapter ordensadapter;

    defaultAdapter motivoadapter;

    Adapter adapter;

    private String rotina = "";

    private String nropedido = "";

    private String CODIGO = "";

    private String LOJA = "";

    private String LOG = "LANCAPEDIDO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_geral);


        toolbar = (Toolbar) findViewById(R.id.tb_pedidos_449);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Pedidos Mobile Transmitidos");
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.menu_pedido_geral);


        try {

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                rotina = params.getString("ROTINA");

            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

        try {

            MotivoDAO motivodao = new MotivoDAO();

            motivodao.open();

            lsMotivos = motivodao.getAll();

            motivodao.close();

        } catch (Exception e) {

            Toast.makeText(this, "Falha Ao Carregar Os Motivos", Toast.LENGTH_SHORT).show();

            finish();


        }


        spData = (Spinner) findViewById(R.id.sp_cidade_334);

        spRede = (Spinner) findViewById(R.id.sp_rede_334);

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

        lv = (ListView) findViewById(R.id.lvClientesSales_449);

        lsOrdens = new ArrayList<>();

        lsOrdens.add(new String[]{"01", "Razão Social"});
        lsOrdens.add(new String[]{"02", "Código"});
        lsOrdens.add(new String[]{"03", "CNPJ"});
        lsOrdens.add(new String[]{"04", "Nome Fantasia"});
        lsOrdens.add(new String[]{"05", "Pedido Tablet"});
        lsOrdens.add(new String[]{"06", "Pedido Protheus"});
        ordensadapter = new defaultAdapter(PedidosMobileTransmitidosActivity.this, R.layout.choice_default_row, lsOrdens, "");

        spOrdem.setAdapter(ordensadapter);

        spOrdem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ordensadapter.setEscolha(position);

                edPesquisa.setText("");

                switch (position) {
                    case 0:  //texto maiusculo

                        edPesquisa.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                        break;

                    case 1:  //numerico

                        edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                        break;

                    case 2:  //numerico

                        edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                        break;

                    case 3:  //texto maisculo

                        edPesquisa.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                        break;
                    case 4:  //numerico

                        edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                        break;
                    case 5:  //numerico

                        edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                        break;

                }

                if (!(lsLista == null)) {

                    LoadPedidos();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spOrdem.setSelection(0);


        LoadPedidos();
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
    protected void onStop() {

        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    public void onResume() {

        try {

            if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        } catch (Exception e) {

            Toast.makeText(PedidosMobileTransmitidosActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


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

                        adapter.refresh2();

                    }

                } catch (Exception e) {

                    Log.i("SAV", e.getMessage());

                }

            }
        });


        OrdemRefresh = false;

        super.onResume();
    }

    @Override
    public void finish() {

        lsLista = new ArrayList<Object>();

        lsDatas = new ArrayList<>();

        lsRedes = new ArrayList<>();

        super.finish();

    }

    private void Toast(String Mensagem) {

        Toast.makeText(this, Mensagem, Toast.LENGTH_LONG).show();

    }

    private void LoadPedidos() {

        String ordem = "cliente.razao";

        try {

            lsLista = new ArrayList<Object>();

            lsDatas = new ArrayList<>();

            lsRedes = new ArrayList<>();

            lsLista.add("Pedidos");

            Object lixo = spOrdem.getSelectedItem();

            if (lixo == null) {

                ordem = " ( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2))   desc , cliente.razao";

            } else {

                ordem = " ( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2))   desc , cliente.razao";

                if (((String[]) lixo)[0].equals("01")) {

                    ordem = " ( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2))   desc , cliente.razao";

                }
                if (((String[]) lixo)[0].equals("02")) {

                    ordem = "( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2))   desc , cliente.codigo";

                }
                if (((String[]) lixo)[0].equals("03")) {

                    ordem = "( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2))   desc, cliente.cnpj";

                }
                if (((String[]) lixo)[0].equals("04")) {

                    ordem = "( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2))   desc, cliente.fantasia";

                }

                if (((String[]) lixo)[0].equals("05")) {

                    ordem = "( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2))   desc, pedidocabmb.nro desc ";

                }
                if (((String[]) lixo)[0].equals("06")) {

                    ordem = "( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2))   desc, pedidocabmb.cprotheus desc ";

                }

            }
            PedidoCabMbDAO dao = new PedidoCabMbDAO();

            dao.open();

            lsLista.addAll(dao.getAllFast(ordem));

            dao.close();

            mpRedes.put("", "TODAS");

            if (lsLista.size() == 1) {

                lsLista.add(new NoData("Nenhum Cliente Encontrado !!"));

            } else {

                for (Object obj : lsLista) {

                    if (obj instanceof PedidoCabMb_fast) {

                        try {

                            mpDatas.put(App.ddmmaaaaatoaaaammdd(((PedidoCabMb_fast) obj).getEMISSAO()), (((PedidoCabMb_fast) obj).getEMISSAO()));

                            mpRedes.put(((PedidoCabMb_fast) obj).get_ClienteCodRede(), ((PedidoCabMb_fast) obj).get_Rede());

                        } catch (Exception e) {

                            //

                        }

                    }

                }


                lsDatas.add(new String[]{"","TODAS"});

                for(int x = 0; x < entriesSortedByValues(mpDatas).size(); x++){

                    lsDatas.add(new String[]{entriesSortedByValues(mpDatas).get(x).getKey(), entriesSortedByValues(mpDatas).get(x).getValue()});

                }


//                for (Map.Entry<String, String> values : mpDatas.entrySet()) {
//
//
//                    lsDatas.add(new String[]{values.getKey(), values.getValue()});
//
//                }

                for (Map.Entry<String, String> values : mpRedes.entrySet()) {


                    lsRedes.add(new String[]{values.getKey(), values.getValue()});

                }
            }


            cidadeadapter = new defaultAdapter(PedidosMobileTransmitidosActivity.this, R.layout.choice_default_row, lsDatas, "Data:");

            spData.setAdapter(cidadeadapter);

            spData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

                    cidadeadapter.setEscolha(position);

                    Object lixo = spData.getSelectedItem();

                    adapter.setEmissao(((String[]) lixo)[1]);

                    adapter.refresh();

                    OrdemRefresh = false;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spData.setSelection(0);

            redeadapter = new defaultAdapter(PedidosMobileTransmitidosActivity.this, R.layout.choice_default_row, lsRedes, "Rede");

            spRede.setAdapter(redeadapter);

            spRede.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

                    redeadapter.setEscolha(position);

                    Object lixo = spRede.getSelectedItem();

                    adapter.setRede(((String[]) lixo)[0]);

                    adapter.refresh();

                    OrdemRefresh = false;


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spRede.setSelection(0);


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

                            spData.setSelection(0);

                            spRede.setSelection(0);

                            adapter.refresh2();

                        }

                    } catch (Exception e) {

                        Log.i("SAV", e.getMessage());

                    }

                }
            });


            adapter = new Adapter(PedidosMobileTransmitidosActivity.this, lsLista);

            adapter.setOrdem(((String[]) lixo)[0]);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            OrdemRefresh = false;


        } catch (Exception e) {

            Toast.makeText(PedidosMobileTransmitidosActivity.this, "Erro Na Carga: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }


    private String[] getMotivos(String tipo, String codigo) {

        String[] retorno = new String[]{"", ""};

        if (tipo.trim().isEmpty()) {


            return retorno;


        }

        for (Motivo mot : lsMotivos) {

            if (mot.getTIPO().trim().equals(tipo.trim()) && mot.getCODIGO().trim().equals(codigo.trim())) {

                retorno = new String[]{mot.getCODIGO(), mot.getDESCRICAO()};

                break;

            }

        }

        return retorno;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverNotification(NotificationPedido notificationPedido) {

        Log.i("MENSAGENS", "Cliente: " + notificationPedido.getCLIENTE());


    }



    @Override
    public void onStart() {
        super.onStart();

    }

    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");

        private List<Object> lsOriginal;

        private List<Object> lsObjetos;

        Context context;

        private String _Emissao = "";
        private String _Rede = "";
        private String _Ordem = "";

        final int ITEM_VIEW_HEADER  = 0;
        final int ITEM_VIEW_CABEC   = 1;
        final int ITEM_VIEW_DETALHE = 2;
        final int ITEM_VIEW_TROCA   = 3;
        final int ITEM_VIEW_NO_DATA = 4;
        final int ITEM_VIEW_COUNT   = 5;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsOriginal = pObjects;

            this.lsObjetos = filtro();

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void setEmissao(String filtro) {

            if (filtro.equals("TODAS")) filtro = "";

            _Emissao = filtro;

        }

        public void setRede(String filtro) {

            _Rede = filtro;

        }

        public void setOrdem(String filtro) {

            _Ordem = filtro;

        }

        public void refresh() {


            this.lsObjetos = filtro();

            notifyDataSetChanged();

        }

        public void refresh2() {

            this.lsObjetos = filtro2();

            notifyDataSetChanged();

        }


        private List<Object> filtro() {

            List<Object> result = null;

            if (_Emissao.equals("") && _Rede.equals("")) {

                return new ArrayList(lsLista);

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsOriginal.size(); x++) {

                    if (lsOriginal.get(x) instanceof PedidoCabMb_fast) {

                        if (    (_Emissao.equals("") || (_Emissao.equals(((PedidoCabMb_fast) lsOriginal.get(x)).getEMISSAO()))) &&
                                (_Rede.equals("")    ||  _Rede.equals(((PedidoCabMb_fast) lsOriginal.get(x)).get_ClienteCodRede()))) {

                            ((PedidoCabMb_fast) lsOriginal.get(x)).set_ViewPedido(false);

                            result.add(lsOriginal.get(x));

                        }
                    } else {

                        result.add(lsOriginal.get(x));

                    }

                }

            }

            if (result.size() == 1) {

                result.add(new NoData("Nenhum Pedido Para O Filtro !!!"));

            }
            return result;
        }


        private List<Object> filtro2() {

            List<Object> result = null;

            if (edPesquisa.getText().toString().trim().isEmpty()) {

                return new ArrayList(lsLista);

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsOriginal.size(); x++) {

                    if (lsOriginal.get(x) instanceof PedidoCabMb_fast) {


                        if (_Ordem.equals("01")) {//razao

                            if (((PedidoCabMb_fast) lsOriginal.get(x)).get_ClienteFatRazao().toUpperCase().contains(edPesquisa.getText().toString().trim())) {

                                ((PedidoCabMb_fast) lsOriginal.get(x)).set_ViewPedido(false);

                                result.add(lsOriginal.get(x));

                            }


                        }
                        if (_Ordem.equals("02")) {//codigo

                            if (((PedidoCabMb_fast) lsOriginal.get(x)).getCODIGOFAT().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsOriginal.get(x));

                            }

                        }
                        if (_Ordem.equals("03")) {//cnpj

                            if (((PedidoCabMb_fast) lsOriginal.get(x)).get_ClienteFatCnpj().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsOriginal.get(x));

                            }

                        }

                        if (_Ordem.equals("04")) {//fantasia

                            if (((PedidoCabMb_fast) lsOriginal.get(x)).get_ClienteFatFantasia().toUpperCase().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsOriginal.get(x));

                            }
                        }

                        if (_Ordem.equals("05")) {//tablet

                            if (((PedidoCabMb_fast) lsOriginal.get(x)).getNRO().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsOriginal.get(x));

                            }

                        }

                        if (_Ordem.equals("06")) {//protheus

                            if (((PedidoCabMb_fast) lsOriginal.get(x)).getCPROTHEUS().contains(edPesquisa.getText().toString().trim())) {

                                result.add(lsOriginal.get(x));

                            }

                        }


                    } else {

                        result.add(lsOriginal.get(x));

                    }

                }

            }

            if (result.size() == 1) {

                result.add(new NoData("Nenhum Cliente Para O Filtro !!!"));

            }
            return result;
        }


        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof PedidoCabMb_fast) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Pedidos: " + String.valueOf(qtd);

            return retorno;
        }

        private void insereDetalhe(int pos, String nro) {

            PedidoCabMb_fast cab = ((PedidoCabMb_fast) lsObjetos.get(pos));

            List<PedidoDetMB_fast> produtos = new ArrayList<>();

            try {

                PedidoDetMbDAO dao = new PedidoDetMbDAO();

                dao.open();

                produtos = dao.getDetalheFast(new String[]{cab.getNRO(),cab.getCODIGOFAT(),cab.getLOJAFAT()});

                dao.close();

                if (produtos.size() == 0) {

                    toast("Não Encontrei Produtos Para Este Pedido !");

                    return;

                }

                for(PedidoDetMB_fast p : produtos) p.set_tipo(cab.getTIPO());

                if (!(cab.get_ViewPedido())) {

                    lsObjetos.addAll(pos + 1, produtos);


                } else {

                    for (int x = 0; x < lsObjetos.size(); x++) {

                        if (lsObjetos.get(x) instanceof PedidoDetMB_fast) {

                            if (((PedidoDetMB_fast) lsObjetos.get(x)).getNRO().equals(nro)) {

                                lsObjetos.remove(x);

                                x--;

                            }

                        }
                    }
                }

                cab.set_ViewPedido(!cab.get_ViewPedido());

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

                retorno = ITEM_VIEW_HEADER;
            }

            if (lsObjetos.get(position) instanceof PedidoCabMb_fast) {

                retorno = ITEM_VIEW_CABEC;

            }

            if (lsObjetos.get(position) instanceof PedidoDetMB_fast) {


                if ("005#006".contains(((PedidoDetMB_fast) lsObjetos.get(position)).get_tipo().trim())){

                    retorno = ITEM_VIEW_TROCA;

                } else {

                    retorno = ITEM_VIEW_DETALHE;

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

                        case ITEM_VIEW_HEADER:

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.pedido_cabec_transmitidos_row, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.pedido_mb_det_row, null);

                            break;

                        case ITEM_VIEW_TROCA:

                            convertView = inflater.inflate(R.layout.pedido_mb_det_troca_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_HEADER: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_CABEC: {

                        final PedidoCabMb_fast obj = (PedidoCabMb_fast) lsObjetos.get(pos);


                        ImageButton bt_cadastro = (ImageButton) convertView.findViewById(R.id.bt_cadastro_498);

                        ImageButton bt_email = (ImageButton) convertView.findViewById(R.id.bt_email_498);

                        ImageButton bt_produtos_498 = (ImageButton) convertView.findViewById(R.id.bt_produtos_498);

                        TextView txt_upload_498 = (TextView) convertView.findViewById(R.id.txt_upload_498);

                        txt_upload_498.setText(obj.getDTTRANS() + " " + obj.getHOTRANS());

                        TextView txt_situacao_498 = (TextView) convertView.findViewById(R.id.txt_situacao_498);

                        txt_situacao_498.setText("Tipo :" + App.TipoPedido(obj.getTIPO()) + "  Situação: " + App.StatusDescricao(obj.getSTATUS()));

                        TextView txt_nro_pedido_498 = (TextView) convertView.findViewById(R.id.txt_nro_pedido_498);

                        txt_nro_pedido_498.setText("Pedido Protheus: " + obj.getCPROTHEUS() + "/" + obj.getCPROTHEUSB() + " Pedido Do Cliente: " + obj.getPEDCLIENTE());

                        TextView txt_nro_tablete_498 = (TextView) convertView.findViewById(R.id.txt_nro_tablete_498);

                        txt_nro_tablete_498.setText("Pedido Tablet: " + obj.getNRO());

                        TextView txt_emissao_498 = (TextView) convertView.findViewById(R.id.txt_emissao_498);

                        txt_emissao_498.setText("DATA EMISSÃO: " + obj.getEMISSAO());

                        TextView txt_entrega_498 = (TextView) convertView.findViewById(R.id.txt_entrega_498);

                        txt_entrega_498.setText("DATA ENTREGA: " + obj.getENTREGA());

                        TextView txt_cliente_498 = (TextView) convertView.findViewById(R.id.txt_cliente_498);

                        txt_cliente_498.setText("CÓDIGO: "+obj.getCODIGOFAT()+"-"+obj.getLOJAFAT()+" RAZÃO SOCIAL: " + obj.get_ClienteFatRazao());

                        TextView txt_fantasia_498 = (TextView) convertView.findViewById(R.id.txt_fantasia_498);

                        txt_fantasia_498.setText("NOME FANTASIA: " + obj.get_ClienteFatFantasia());

                        TextView txt_cnpj_498 = (TextView) convertView.findViewById(R.id.txt_cnpj_498);

                        txt_cnpj_498.setText(App.cnpj_cpf("CNPJ: " + obj.get_ClienteFatCnpj()));

                        TextView txt_ie_498 = (TextView) convertView.findViewById(R.id.txt_ie_498);

                        txt_ie_498.setText(("INSC. EST.: " + obj.get_ClienteFatIE()));

                        TextView txt_condpagto_498 = (TextView) convertView.findViewById(R.id.txt_condpagto_498);

                        txt_condpagto_498.setText("COND. PAGTO: "+obj.getCOND()+"-"+obj.get_Cond());

                        TextView txt_cliente_entrega_498 = (TextView) convertView.findViewById(R.id.txt_cliente_entrega_498);

                        txt_cliente_entrega_498.setText("CLIENTE ENTREGA: " + obj.get_ClienteEntRazao());

                        TextView txt_cidade_entrega_498 = (TextView) convertView.findViewById(R.id.txt_cidade_entrega_498);

                        txt_cidade_entrega_498.setText("CIDADE: " + obj.get_ClienteEntCidade());

                        TextView txt_telefone_entrega_498 = (TextView) convertView.findViewById(R.id.txt_telefone_entrega_498);

                        txt_telefone_entrega_498.setText("TELEFONE: " + obj.get_ClienteEntTelefone());

                        TextView txt_obs_pedido_498 = (TextView) convertView.findViewById(R.id.txt_obs_pedido_498);

                        txt_obs_pedido_498.setText("OBS. PED. " + obj.getOBSPED());

                        TextView txt_obs_nf_498 = (TextView) convertView.findViewById(R.id.txt_obs_nf_498);

                        txt_obs_nf_498.setText("OBS. NF. " + obj.getOBSNF());

                        TextView txt_frete_desc_retira_498 = (TextView) convertView.findViewById(R.id.txt_frete_desc_retira_498);

                        txt_frete_desc_retira_498.setText("DESCONTO RETIRA: " + obj.get_Retira());

                        TextView txt_total_498 = (TextView) convertView.findViewById(R.id.txt_total_498);

                        txt_total_498.setText("TOTAL DO PEDIDO: " + format_02.format(obj.getTOTALPEDIDO()));

                        TextView txt_rede_498 = (TextView) convertView.findViewById(R.id.txt_rede_498);

                        txt_rede_498.setText("REDE: " + obj.get_ClienteCodRede() + "-" + obj.get_Rede());

                        bt_cadastro.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               Intent intent = new Intent(context, ClienteViewAtivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                               Bundle params = new Bundle();
                                                               params.putString("CODIGO", obj.getCODIGOFAT());
                                                               params.putString("LOJA", obj.getLOJAFAT());
                                                               intent.putExtras(params);
                                                               context.startActivity(intent);

                                                           }
                                                       }
                        );

                        bt_email.setOnClickListener(new ClickEmail(context, obj));

                        bt_produtos_498.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                insereDetalhe(pos, obj.getNRO());


                            }
                        });


                        break;

                    }

                    case ITEM_VIEW_DETALHE: {

                        final PedidoDetMB_fast obj = (PedidoDetMB_fast) lsObjetos.get(pos);

                        TextView txt_produto_023 = (TextView) convertView.findViewById(R.id.txt_produto_023);

                        TextView txt_venda_023 = (TextView) convertView.findViewById(R.id.txt_venda_023);
                        TextView txt_qtd_023 = (TextView) convertView.findViewById(R.id.txt_qtd_023);
                        TextView txt_prcven_023 = (TextView) convertView.findViewById(R.id.txt_prcven_023);
                        TextView txt_desconto_023 = (TextView) convertView.findViewById(R.id.txt_desconto_023);
                        TextView txt_desconto_verba_023 = (TextView) convertView.findViewById(R.id.txt_desconto_verba_023);
                        TextView txt_doc_023 = (TextView) convertView.findViewById(R.id.txt_doc_023);
                        TextView txt_total_023 = (TextView) convertView.findViewById(R.id.txt_total_023);
                        TextView txt_preco_base_023 = (TextView) convertView.findViewById(R.id.txt_preco_base_023);
                        TextView txt_verba_descricao_023 = (TextView) convertView.findViewById(R.id.txt_verba_descricao_023);
                        TextView txt_acordo_023 = (TextView) convertView.findViewById(R.id.txt_acordo_023);


                        TextView txt_venda_02_023 = (TextView) convertView.findViewById(R.id.txt_venda_02_023);
                        TextView txt_qtd_02_023 = (TextView) convertView.findViewById(R.id.txt_qtd_02_023);
                        TextView txt_prcven_02_023 = (TextView) convertView.findViewById(R.id.txt_prcven_02_023);
                        TextView txt_desconto_02_023 = (TextView) convertView.findViewById(R.id.txt_desconto_02_023);
                        TextView txt_desconto_verba_02_023 = (TextView) convertView.findViewById(R.id.txt_desconto_verba_02_023);
                        TextView txt_doc_02_023 = (TextView) convertView.findViewById(R.id.txt_doc_02_023);
                        TextView txt_total_02_023 = (TextView) convertView.findViewById(R.id.txt_total_02_023);
                        TextView txt_preco_base_02_023 = (TextView) convertView.findViewById(R.id.txt_preco_base_02_023);
                        TextView txt_verba_descricao_02_023 = (TextView) convertView.findViewById(R.id.txt_verba_descricao_02_023);
                        TextView txt_acordo_02_023 = (TextView) convertView.findViewById(R.id.txt_acordo_02_023);


                        txt_produto_023.setText(obj.getPRODUTO() + "-" + obj.get_Produto());

                        if (obj.getQTD().compareTo(0f) == 0) {

                            txt_qtd_023.setText("");
                            txt_prcven_023.setText("");
                            txt_desconto_023.setText("");
                            txt_desconto_verba_023.setText("");
                            txt_doc_023.setText("");
                            txt_total_023.setText("");
                            txt_preco_base_023.setText("");
                            txt_verba_descricao_023.setText("");
                            txt_acordo_023.setText("");


                        } else {

                            String texto = "";

                            if (!obj.getSIMULADOR().trim().isEmpty())
                                texto = "S-" + obj.getSIMULADOR();
                            if (!obj.getPEDDIST().trim().isEmpty()) texto = "D-" + obj.getPEDDIST();
                            if (!obj.getACORDO().trim().isEmpty()) texto = "A-" + obj.getACORDO();

                            txt_qtd_023.setText(format_02.format(obj.getQTD()));
                            txt_prcven_023.setText(format_02.format(obj.getPRCVEN()));
                            txt_desconto_023.setText(format_02.format(obj.getDESCON()));
                            txt_desconto_verba_023.setText(format_02.format(obj.getDESCVER()));
                            txt_doc_023.setText(texto);
                            txt_total_023.setText(format_02.format(obj.getTOTAL()));
                            txt_preco_base_023.setText(format_02.format(obj.getPRECOFORMACAO()));
                            txt_verba_descricao_023.setText(obj.getCODVERBA() + "-" + obj.get_Verba());
                            txt_acordo_023.setText("");

                        }


                        if (obj.getBONIQTD().compareTo(0f) == 0) {


                            txt_qtd_02_023.setText("");
                            txt_prcven_02_023.setText("");
                            txt_desconto_02_023.setText("");
                            txt_desconto_verba_02_023.setText("");
                            txt_doc_02_023.setText("");
                            txt_total_02_023.setText("");
                            txt_preco_base_02_023.setText("");
                            txt_verba_descricao_02_023.setText("");
                            txt_acordo_02_023.setText("");


                        } else {


                            String texto = "";

                            if (!obj.getSIMULADOR2().trim().isEmpty())
                                texto = "S-" + obj.getSIMULADOR2();
                            if (!obj.getPEDDIST2().trim().isEmpty())
                                texto = "D-" + obj.getPEDDIST2();
                            if (!obj.getACORDO2().trim().isEmpty()) texto = "A-" + obj.getACORDO2();

                            txt_qtd_02_023.setText(format_02.format(obj.getBONIQTD()));
                            txt_prcven_02_023.setText(format_02.format(obj.getBONIPREC()));
                            txt_desconto_02_023.setText(format_02.format(0f));
                            txt_desconto_verba_02_023.setText(format_02.format(obj.getDESCVER2()));
                            txt_doc_02_023.setText(texto);
                            txt_total_02_023.setText(format_02.format(obj.getBONITOTAL()));
                            txt_preco_base_02_023.setText(format_02.format(obj.getPRECOFORMACAO()));
                            txt_verba_descricao_02_023.setText(obj.getCODVERBA2() + "-" + obj.get_Verba2());
                            txt_acordo_02_023.setText("");


                        }


                        break;

                    }


                    case ITEM_VIEW_TROCA:{

                        final PedidoDetMB_fast obj = (PedidoDetMB_fast) lsObjetos.get(pos);

                        TextView txt_produto_023  = (TextView) convertView.findViewById(R.id.txt_produto_023);
                        TextView txt_qtd_023      = (TextView) convertView.findViewById(R.id.txt_qtd_023);
                        TextView txt_prcven_023   = (TextView) convertView.findViewById(R.id.txt_prcven_023);
                        TextView txt_total_023    = (TextView) convertView.findViewById(R.id.txt_total_023);

                        TextView txt_lote_023              = (TextView) convertView.findViewById(R.id.txt_lote_023);
                        TextView txt_empacotamento_023     = (TextView) convertView.findViewById(R.id.txt_empacotamento_023);
                        TextView txt_vencimento_023        = (TextView) convertView.findViewById(R.id.txt_vencimento_023);

                        TextView txt_motivo_descricao_023  = (TextView) convertView.findViewById(R.id.txt_motivo_descricao_023);
                        TextView txt_obs_023               = (TextView) convertView.findViewById(R.id.txt_obs_023);


                        txt_produto_023.setText(obj.getPRODUTO() + "-" + obj.get_Produto());
                        txt_qtd_023.setText(format_02.format(obj.getQTD()));
                        txt_prcven_023.setText(format_02.format(obj.getPRCVEN()));
                        txt_total_023.setText(format_02.format(obj.getTOTAL()));

                        txt_lote_023.setText(obj.getLOTE());
                        txt_empacotamento_023.setText(obj.getEMPACOTAMENTO());
                        txt_vencimento_023.setText(obj.getVENCIMENTO());

                        txt_motivo_descricao_023.setText(obj.getCODVERBA()+"-"+obj.get_MotDev());
                        txt_obs_023.setText(obj.getOBS());

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

    private List<String[]> loadMotivos(String tipo) {

        List<String[]> retorno = new ArrayList<>();

        retorno.clear();

        retorno.add(new String[]{"000", "Escolha Um Motivo"});

        for (Motivo mot : lsMotivos) {

            if (mot.getTIPO().trim().equals(tipo)) {

                retorno.add(new String[]{mot.getCODIGO(), mot.getDESCRICAO()});

            }

        }

        return retorno;

    }

    private void toast(String msg) {

        Toast.makeText(PedidosMobileTransmitidosActivity.this, msg, Toast.LENGTH_SHORT).show();

    }


    private Handler mHandlerTrasmissao = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    toast("NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(PedidosMobileTransmitidosActivity.this, msg.getData().getString("CMSGERRO"), "Acessando Servidores.Aguarde !!", false, true);
                    dialog.setCancelable(false);
                    dialog.show();

                    processado = true;
                }


                if (msg.getData().getString("CERRO").equals("MMM")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.setTitle(msg.getData().getString("CMSGERRO"));

                        }

                    }

                    processado = true;

                }


                if ((msg.getData().getString("CERRO").equals("000"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    if (!msg.getData().getString("CMSGERRO").isEmpty()) {

                        toast(msg.getData().getString("CMSGERRO"));

                    }


                    processado = true;
                }

                if ((msg.getData().getString("CERRO").equals("FEC"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }


                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(PedidosMobileTransmitidosActivity.this, "Erro:" + msg.getData().getString("CERRO") + " " + msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }
                    }


                }

            } catch (Exception E) {

                toast("Erro Handler: " + E.getMessage());

            }
        }


    };


    private class defaultAdapter extends ArrayAdapter {

        private int escolha = 0;

        private List<String[]> lista;

        private String label;

        private boolean isInicializacao = true;

        private Context context;

        public defaultAdapter(Context context, int textViewResourceId, List<String[]> objects, String label) {

            super(context, textViewResourceId, objects);

            this.lista = objects;

            this.label = label;

            this.context = context;
        }


        public String getOpcao(int pos) {


            if ((pos < this.lista.size())) {


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


            TextView label = (TextView) layout.findViewById(R.id.lbl_titulo_22);

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

            TextView tvlabel = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            tvlabel.setText(this.label);

            if (this.label.isEmpty()) {

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

    private class ClickEmail implements View.OnClickListener {

        private Context context = null;

        private PedidoCabMb_fast obj = null;


        public ClickEmail(Context context, PedidoCabMb_fast obj) {

            this.context = context;
            this.obj = obj;
        }

        @Override
        public void onClick(View v) {

            Cliente cliente = null;

            final ClienteDAO clientedao = null;

            try {

                ClienteDAO dao = new ClienteDAO();

                dao.open();

                cliente = dao.seek(new String[] {obj.getCODIGOFAT(),obj.getLOJAFAT()});

                dao.close();

                if (cliente == null){

                    toast("Cliente Não Encontrado !! "+obj.get_ClienteFatRazao());

                    return;

                }

            } catch (Exception e) {

                toast(e.getMessage());

                return;

            }

            final Dialog dialog = new Dialog(context);

            dialog.setContentView(R.layout.dlemail);

            final TextView titulo   = (TextView) dialog.findViewById(R.id.titulo_121);

            final CheckBox cb_envia_email_121 = (CheckBox) dialog.findViewById(R.id.cb_enviar_email_121);

            final EditText endereco = (EditText) dialog.findViewById(R.id.email_121);

            titulo.setText("E-MAIL Será Enviado Na Confirmação!");

            if (obj.getCCOPIAPEDIDO().equals("S")) {

                cb_envia_email_121.setChecked(true);

            }
            else {

                cb_envia_email_121.setChecked(false);

            }

            if(obj.getCEMAILCOPIAPEDIDO().trim().isEmpty()){

                obj.setCEMAILCOPIAPEDIDO(cliente.getEMAILTROCA());

            }

            endereco.setText(obj.getCEMAILCOPIAPEDIDO());

            final Button confirmar = (Button) dialog.findViewById(R.id.bt_confirma_121);

            final Button cancelar = (Button) dialog.findViewById(R.id.bt_cancela_121);

            cancelar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            confirmar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {


                    if (!cb_envia_email_121.isChecked()){

                        return;

                    }

                    if (endereco.getText().toString().trim().isEmpty()) {


                        toast("Digite Um E-Mail Por Favor !");

                        return;

                    }

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            View view = dialog.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }

                            dialog.dismiss();

                        }

                        try {

                            {

                                PedidoCabMbDAO dao = new PedidoCabMbDAO();

                                dao.open();

                                PedidoCabMb ped = dao.seek(new String[] {obj.getNRO()});

                                ped.setCCOPIAPEDIDO(obj.getCCOPIAPEDIDO());

                                ped.setCEMAILCOPIAPEDIDO(obj.getCEMAILCOPIAPEDIDO());

                                dao.Update(ped);

                                dao.close();

                            }
                            {
                                ClienteDAO dao = new ClienteDAO();

                                dao.open();

                                Cliente cliente = dao.seek(new String[]{obj.getCODIGOFAT(), obj.getLOJAFAT()});

                                if (cliente == null) {

                                    toast("Cliente Não Encontrado !! " + obj.get_ClienteFatRazao());

                                    dao.close();

                                    return;

                                }

                                cliente.setEMAILTROCA(endereco.getText().toString().trim());

                                dao.Update(cliente);

                                dao.close();

                            }
                        } catch (Exception e) {

                            toast(e.getMessage());

                            return;

                        }


                    }

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerTrasmissao, getBaseContext(), App.user, "MAILSALEORDER", "MAILSALEORDER", RETORNO_TIPO_ESTUTURADO, PROCESSO_EMAIL, null, null, -1);

                    acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

                    acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

                    acessoWeb.addParam("CPARA", endereco.getText().toString().trim().toLowerCase());

                    acessoWeb.addParam("CNUMPEDMOB", obj.getNRO());

                    acessoWeb.start();

                }

            });

            dialog.show();

        }

    }

}



