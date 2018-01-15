package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.FreteDAO;
import br.com.brotolegal.savdatabase.entities.CondPagto;
import br.com.brotolegal.savdatabase.entities.Frete;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.NoDataProgress;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;
import br.com.brotolegal.savdatabase.entities.TabPrecoCabec;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionItemProduto;
import br.com.brotolegal.savdatabase.regrasdenegocio.PedidoBusinessV10;

import static br.com.brotolegal.savdatabase.app.App.getNewID;

public class PrePedidoActivity extends AppCompatActivity {

    private DecimalFormat format_02 = new DecimalFormat(",##0.00");
    private DecimalFormat format_03 = new DecimalFormat(",##0.000");
    private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
    private DecimalFormat format_05 = new DecimalFormat(",##0.00000");

    private Toolbar toolbar;

    private ListView lv;

    private TextView txt_cliente_568;

    private Spinner  sp_condpagto_568;

    private Spinner  sp_tabpreco_568;

    private Spinner  sp_view_568;

    private Spinner  sp_marca_568;

    private Spinner  sp_categoria_568;

    private View inc_pedidomb_filtro;

    private Spinner spCategoria;

    private Spinner spMarcas;

    private TextView lbl_fardos_previstos_568;

    private TextView txt_fardos_previstos_568;

    private List<Object> lista;

    private String CodCliente;

    private String LojCliente;

    private Float  FdsPrevisto;

    private PedidoBusinessV10 pedido;

    private Adapter adapter;

    private defaultAdapter condadapter;

    private defaultAdapter tabprecoadapter;

    private defaultAdapter viewadapter;

    private defaultAdapter categoriaadapter;

    private defaultAdapter marcaadapter;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_pedido);

        try {

            toolbar = (Toolbar) findViewById(R.id.tb_prepedido_568);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Tabela De Preços");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.inflateMenu(R.menu.menu_prepedido);

            txt_cliente_568 = (TextView) findViewById(R.id.txt_cliente_568);

            lv = (ListView) findViewById(R.id.lvprepedido_568);

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CodCliente = params.getString("CODIGO","");

                LojCliente = params.getString("LOJA","");

                FdsPrevisto = params.getFloat("FDSPREVISTO",0f);

            }

            pedido = new PedidoBusinessV10();

            pedido.setAgendamento(App.getNewIDAgendamento());

            pedido.getCabec().setTIPO("001");

            pedido.getCabec().loadTipoDescricao();

            pedido.getCabec().setNRO(getNewID());

            pedido.setCliente(CodCliente, LojCliente);

            pedido.getCabec().setFDSPREVISTO(FdsPrevisto);

            pedido.getCabec().setQTDENTREGA(1);

            Float qtd = 0f;

            try {

                qtd = FdsPrevisto / pedido.getCabec().getQTDENTREGA();

            } catch (Exception e){

                qtd = FdsPrevisto;
            }

            FreteDAO fretedao = new FreteDAO();

            fretedao.open();

            Frete freteArroz = fretedao.getFretArroz(qtd,pedido.getClienteEntrega().getREDE(),pedido.getClienteEntrega().getESTADO(),pedido.getClienteEntrega().getCODCIDADE());

            fretedao.close();

            if (freteArroz == null){

                freteArroz = new Frete();

                freteArroz.setFRETE(3.50f);

            }

            Frete freteFeijao = fretedao.getFreteFeijao(qtd,pedido.getClienteEntrega().getREDE(),pedido.getClienteEntrega().getESTADO(),pedido.getClienteEntrega().getCODCIDADE());

            if (freteFeijao == null){

                freteFeijao = new Frete();

                freteFeijao.setFRETE(3.50f);

            }

            pedido.setFRETEARROZ(freteArroz.getFRETE());

            pedido.setFRETEFEIJAO(freteFeijao.getFRETE());

            pedido.Validar();

            lista = new ArrayList<>();

            cabec_init();

            cabec_popula();

            cabec_onClick();

            try {

                LOADThread loadItens = new LOADThread(mHandlerLoad);

                loadItens.start();

                lista.add("CABEC");

                lista.add(new NoDataProgress("Carregando os produtos..."));

                adapter = new Adapter(getBaseContext(), lista);

                lv.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                toast(e.getMessage());
            }

            loadTabela();

        } catch (Exception e){

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            Log.i("SAV",exceptionAsString);

            toast(e.getMessage());

            showToast(e.getMessage());

            finish();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prepedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            default:

                finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {

        loadTabela();

        super.onResume();
    }

    @Override
    public void finish() {

        lista   =  new ArrayList<Object>();

        super.finish();

    }

    private void showToast(String mensagem){

        Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();

    }

    private void loadTabela() {





    }

    private void cabec_init() {


         /*
          *
          * Defines os campos
          *
          */

        //lbl_fardos_previstos_001 = (TextView) findViewById(R.id.lbl_fardos_previstos_001);
        //lbl_qtd_entrega_001 = (TextView) findViewById(R.id.lbl_qtd_entrega_001);


        sp_view_568                 = (Spinner) findViewById(R.id.sp_view_568);
        spCategoria                 = (Spinner) findViewById(R.id.sp_categoria_568);
        spMarcas                    = (Spinner) findViewById(R.id.sp_marca_568);
        sp_condpagto_568            = (Spinner) findViewById(R.id.sp_condpagto_568);
        txt_cliente_568             = (TextView) findViewById(R.id.txt_cliente_568);
        sp_tabpreco_568             = (Spinner) findViewById(R.id.sp_tabpreco_568);
        lbl_fardos_previstos_568    = (TextView) findViewById(R.id.lbl_fardos_previstos_568);
        txt_fardos_previstos_568    = (TextView) findViewById(R.id.txt_fardos_previstos_568);



    }

    private void cabec_popula(){


        txt_cliente_568.setText(pedido.getClienteEntrega().getRAZAO().trim()+" Frete Arroz "+format_02.format(pedido.getFRETEARROZ())+" frete Feijao "+pedido.getFRETEFEIJAO());


        int index = -1;

        int i = 0;

        List<String[]> opcoes = new ArrayList<>();

        for (CondPagto op : pedido.getLsCondPagto()) {

            opcoes.add(new String[]{op.getCODIGO(), op.getDESCRICAO()});

            if (op.getCODIGO().equals(pedido.getCabec().getCOND())) {

                index = i;

            }

            i++;

        }

        if (index == -1) {

            opcoes.add(new String[]{"01", " "});

            index = 0;

        }

        sp_condpagto_568.setEnabled(false);

        condadapter = new defaultAdapter(PrePedidoActivity.this, R.layout.choice_default_row, opcoes, "Cond Pagto", pedido.getCabec().isValidoByName("COND"));

        sp_condpagto_568.setAdapter(condadapter);

        sp_condpagto_568.setSelection(index);

        sp_tabpreco_568.setEnabled(false);

        opcoes = new ArrayList<>();

        opcoes.add(new String[]{pedido.getCabec().getTABPRECO(), pedido.getCabec().get_TabPreco()});

        tabprecoadapter = new defaultAdapter(PrePedidoActivity.this, R.layout.choice_default_row, opcoes, "Tab.Preço", pedido.getCabec().isValidoByName("TABPRECO"));

        sp_tabpreco_568.setAdapter(tabprecoadapter);

        sp_tabpreco_568.setSelection(0);

        txt_fardos_previstos_568.setText(format_02.format(pedido.getCabec().getFDSPREVISTO()));


    }

    private void cabec_onClick() {

        List<String[]> opcoes;


        int index = -1;

        int i = 0;

        opcoes = new ArrayList<String[]>();

        for (CondPagto op : pedido.getLsCondPagto()) {

            opcoes.add(new String[]{op.getCODIGO(), op.getCODIGO() + "-" + op.getDESCRICAO()});

            if (op.getCODIGO().equals(pedido.getCabec().getCOND())) {


                index = i;


            }

            i++;

        }

        if (index == -1) {

            opcoes.add(new String[]{"01", " "});

            index = 0;

        }

        sp_condpagto_568.setEnabled(true);

        condadapter = new defaultAdapter(PrePedidoActivity.this, R.layout.choice_default_row, opcoes, "Cond Pagto", pedido.getCabec().isValidoByName("COND"));

        sp_condpagto_568.setAdapter(condadapter);

        sp_condpagto_568.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                condadapter.setEscolha(position);

                if (position < pedido.getLsCondPagto().size()) {

                    pedido.setCondpagto(pedido.getLsCondPagto().get(position));

                    pedido.getCabec().setCOND(pedido.getLsCondPagto().get(position).getCODIGO());

                    pedido.getCabec().set_Cond(pedido.getLsCondPagto().get(position).getDESCRICAO());

                }

                if (!condadapter.isInicializacao()) {

                    if (pedido.getLsDetalhe().size() > 0) {

                            AtualizaTPThread atPedido = new AtualizaTPThread(mHandlerLoad);

                            atPedido.start();
                    }

                } else {

                    condadapter.setIsInicializacao(false);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        condadapter.setIsInicializacao(true);

        sp_condpagto_568.setSelection(index);


        index = -1;

        i = 0;

        opcoes = new ArrayList<String[]>();

        for (TabPrecoCabec op : pedido.getLsTabPrecoCabec()) {

            opcoes.add(new String[]{op.getCODIGO(), op.getCODIGO() + "-" + op.getDESCRICAO()});

            if (op.getCODIGO().equals(pedido.getCabec().getTABPRECO())) {


                index = i;


            }

            i++;

        }

        if (index == -1) {

            opcoes.add(new String[]{"01", " "});

            index = 0;

        }

        sp_tabpreco_568.setEnabled(true);

        tabprecoadapter = new defaultAdapter(PrePedidoActivity.this, R.layout.choice_default_row, opcoes, "Tab.Preço", pedido.getCabec().isValidoByName("TABPRECO"));

        sp_tabpreco_568.setAdapter(tabprecoadapter);

        sp_tabpreco_568.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()


        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {

                int indice = -1;

                //VALIDA A TROCA ANTES DE EXECUTAR

                if (!tabprecoadapter.isInicializacao()) {
                    try {

                        String tabela = tabprecoadapter.getOpcao(position);

                        pedido.validaTrocaDeTabela(tabela);

                    } catch (Exception e) {

                        toast(e.getMessage());

                        tabprecoadapter.setIsInicializacao(true);

                        sp_tabpreco_568.setSelection(pedido.getIndiceByCodigo(pedido.getCabec().getTABPRECO()));

                        return;

                    }
                }

                tabprecoadapter.setEscolha(position);

                Object lixo = sp_tabpreco_568.getSelectedItem();

                indice = pedido.getIndiceByCodigo(((String[]) lixo)[0]);

                if (indice != -1) {

                    pedido.getCabec().setTABPRECO(((String[]) lixo)[0]);

                    if (!tabprecoadapter.isInicializacao()) {

                        try {

                            pedido.atualizaTabela();

                            if (pedido.getLsDetalhe().size() > 0){

//                                PedidoV10Activity.AtualizaTPThread atp = new PedidoV10Activity.AtualizaTPThread(mHandlerAtualizaPedido);
//
//                                atp.start();

                            }


                        } catch (ExceptionItemProduto exceptionItemProduto) {

                            toast(exceptionItemProduto.getMessage());

                        } catch (Exception e) {

                            toast(e.getMessage());
                        }
                    } else {

                        tabprecoadapter.setIsInicializacao(false);
                    }
                } else {

                    toast("Tabela Não Encontrada: " + ((String[]) lixo)[0]);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        tabprecoadapter.setIsInicializacao(true);

        sp_tabpreco_568.setSelection(index);

        lbl_fardos_previstos_568.setOnClickListener(new Click());

    }


    private void toast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }


    private void produto_filtro(){

        final List<String[]> opcoes = new ArrayList<>();

        opcoes.add(new String[]{"01", "TODOS"});

        pedido.loadMarcasECategorias("000","000");

        viewadapter = new  defaultAdapter(PrePedidoActivity.this, R.layout.choice_default_row, opcoes,"",true);

        sp_view_568.setAdapter(viewadapter);

        sp_view_568.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                viewadapter.setEscolha(position);

                Object lixo = sp_view_568.getSelectedItem();

                spCategoria.setSelection(0);

                spMarcas.setSelection(0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        sp_view_568.setSelection(0);

        categoriaadapter = new defaultAdapter(PrePedidoActivity.this, R.layout.choice_default_row,pedido.getLsCategoria(),"Categoria: ",true);

        spCategoria.setAdapter(categoriaadapter);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaadapter.setEscolha(position);
                Object lixo = spCategoria.getSelectedItem();
                adapter.setFilter(((String[]) lixo)[0], adapter.Filtro_marca);
                pedido.loadMarcasECategorias("XXX",((String[]) lixo)[0]);

                //AJUSTA MARCAS SÓ DA CATEGORIA
                marcaadapter   = new defaultAdapter(PrePedidoActivity.this, R.layout.choice_default_row, pedido.getLsMarca(),"Marca: ",true);
                spMarcas.setAdapter(marcaadapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spCategoria.setSelection(0);

        marcaadapter   = new defaultAdapter(PrePedidoActivity.this, R.layout.choice_default_row, pedido.getLsMarca(),"Marca: ",true);

        spMarcas.setAdapter(marcaadapter);

        spMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                marcaadapter.setEscolha(position);
                Object lixo = spMarcas.getSelectedItem();
                adapter.setFilter(adapter.Filtro_grupo,((String[]) lixo)[0]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spMarcas.setSelection(0);


    }

    private Handler mHandlerLoad = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            if (msg.getData().getString("erro").equals("---")) {

                dialog = ProgressDialog.show(PrePedidoActivity.this, "RECALCULANDO PEDIDO", "Processando !!", false, true);
                dialog.setCancelable(false);
                dialog.show();
            }


            if ((msg.getData().getString("erro").equals("FECT"))) {

                if ((dialog != null)) {

                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }
                }

                adapter = new Adapter(getBaseContext(), lista);

                lv.setAdapter(adapter);

                adapter.notifyDataSetChanged();

                pedido.Validar();


                produto_filtro();

            }


            if (msg.getData().getString("erro").equals("PRONTO")) {

                try {

                    lista = new ArrayList<>();

                    produto_filtro();

                    lista.add("CABEC");

                    lista.addAll(pedido.getLsDetalhe());

                    adapter = new Adapter(getBaseContext(), lista);

                    lv.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    toast(e.getMessage());
                }

            }

            if (msg.getData().getString("erro").equals("MENSAGEM")) {

                try {

                    adapter.setMensagem(msg.getData().getString("msgerro"));

                } catch (Exception e) {
                    toast(e.getMessage());
                }

            }

            if (msg.getData().getString("erro").equals("ERRO")) {

                try {

                    lista = new ArrayList<>();

                    lista.add("CABEC");

                    lista.add(new NoData(msg.getData().getString("msgerro")));

                    adapter = new Adapter(getBaseContext(), lista);

                    lv.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {

                    toast(e.getMessage());
                }

            }

        }

    };

    public void ClickGetDados(final String Titulo, final String FieldName, int maxlenght){

        final PedidoCabMb obj  = pedido.getCabec();

        final Dialog dialog    = new Dialog(PrePedidoActivity.this);

        dialog.setContentView(R.layout.gettexttopadrao);

        dialog.setTitle("Favor Digitar O Informação");

        final TextView titulo   = (TextView) dialog.findViewById(R.id.txt_570_texto1);

        final TextView mensagem = (TextView) dialog.findViewById(R.id.txt_570_error);

        final TextView tvCONTADOR = (TextView) dialog.findViewById(R.id.lbl_570_contador);

        titulo.setText(Titulo);

        final EditText campo = (EditText) dialog.findViewById(R.id.edCampo_570);

        if ("FDSPREVISTO#QTDENTREGA".contains(FieldName)){

            if (FieldName.equals("FDSPREVISTO")){

                campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                try {


                    campo.setText(String.valueOf((Float) obj.getFieldByName(FieldName)));


                } catch (Exception e){

                    campo.setText("");

                }
            }

            if (FieldName.equals("QTDENTREGA")){

                campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                try {

                    campo.setText(String.valueOf((Integer) obj.getFieldByName(FieldName)));


                } catch (Exception e){

                    campo.setText("");

                }}


        } else {


            campo.setText((String)obj.getFieldByName(FieldName));

        }


        if (maxlenght > 0) {

            campo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxlenght)});

            mensagem.setText("Tamanho Máximo: "+Integer.toString(maxlenght));
        }

        campo.setSelection(0,campo.getText().toString().length());

        campo.addTextChangedListener(new TextWatcher() {

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


        final Button confirmar    = (Button) dialog.findViewById(R.id.btn_570_ok);
        final Button cancelar     = (Button) dialog.findViewById(R.id.btn_570_can);

        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try

                {


                    if ("FDSPREVISTO#QTDENTREGA".contains(FieldName)) {


                        if (FieldName.equals("FDSPREVISTO")) {

                            try {

                                obj.setFieldByName(FieldName, Float.valueOf(campo.getText().toString()));

                                pedido.setClienteEntrega(obj.getCODIGOENT(), obj.getLOJAENT());

                                AtualizaTPThread atPedido = new AtualizaTPThread(mHandlerLoad);

                                atPedido.start();

                            } catch (Exception e) {

                                obj.setFieldByName(FieldName, 0f);
                            }

                        }

                        if (FieldName.equals("QTDENTREGA")) {

                            Integer qtd = 1;

                            try {

                                try {

                                    qtd = Integer.valueOf(campo.getText().toString());

                                    if (qtd <= 0 ){

                                        qtd = 1;

                                    }

                                } catch (Exception e){

                                    qtd = 1;

                                }

                                obj.setFieldByName(FieldName, qtd);

                                pedido.setClienteEntrega(obj.getCODIGOENT(), obj.getLOJAENT());

//                                if (pedido.getLsDetalhe().size() > 0) {
//
//                                    AtualizaTPThread atp = new AtualizaTPThread(mHandlerAtualizaPedido);
//
//                                    atp.start();
//
//                                }

                            } catch (Exception e) {

                                obj.setFieldByName(FieldName, 01);
                            }

                        }
                    }

                    else {

                        obj.setFieldByName(FieldName, campo.getText().toString());

                    }


                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                }


                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

                pedido.Validar();

                cabec_popula();
                cabec_onClick();

            }

        });

        dialog.show();

    }



    //Inner Class

    private class AtualizaTPThread extends Thread {

        private Handler mHandler;

        private Bundle params = new Bundle();

        public AtualizaTPThread(Handler handler) {

            super();

            mHandler = handler;

        }

        @Override

        public void run() {

            try {

                params.putString("erro"   , "---");

                params.putString("msgerro", "");

                sendmsg(params);

                pedido.loadItensNewOrder();

                List<Float> values = pedido.getFAIXAS();

                FreteDAO fretedao = new FreteDAO();

                Float FreteArroz  = 0f;

                Float FreteFeijao = 0f;

                for (Float value : values) {

                    Float qtd = 0f;

                    try {

                        qtd = value / pedido.getCabec().getQTDENTREGA();

                    } catch (Exception e){

                        qtd = value;
                    }

                    fretedao.open();

                    Frete freteArroz = fretedao.getFretArroz(qtd,pedido.getClienteEntrega().getREDE(),pedido.getClienteEntrega().getESTADO(),pedido.getClienteEntrega().getCODCIDADE());

                    fretedao.close();

                    if (freteArroz == null){

                        freteArroz = new Frete();

                        freteArroz.setFRETE(3.50f);

                    }

                    Frete freteFeijao = fretedao.getFreteFeijao(qtd,pedido.getClienteEntrega().getREDE(),pedido.getClienteEntrega().getESTADO(),pedido.getClienteEntrega().getCODCIDADE());

                    if (freteFeijao == null){

                        freteFeijao = new Frete();

                        freteFeijao.setFRETE(3.50f);

                    }

                    FreteArroz   = freteArroz.getFRETE();

                    FreteFeijao  = freteFeijao.getFRETE();

                    for(int i =0 ; i < pedido.getLsDetalhe().size() ; i++) {


                        pedido.FormandoPreco_V2(PedidoBusinessV10.FORMANDOPRECO_FRETE, i, value,FreteArroz,FreteFeijao);


                    }

                }




                lista = new ArrayList<Object>();

                lista.add("CABEC");

                lista.addAll(pedido.getLsDetalhe());

                params.putString("erro"   , "FECT");

                params.putString("msgerro", "");

                sendmsg(params);



            } catch (Exception e){

                params.putString("erro"   , "FECT");

                params.putString("msgerro", e.getMessage());

                sendmsg(params);



            }

        }


        public void sendmsg(Bundle value) {

            if (value != null) {
                Message msgObj = mHandler.obtainMessage();
                msgObj.setData(value);
                mHandler.sendMessage(msgObj);
            }

        }

    }

    private class LOADThread extends Thread {

        private Handler mHandler;

        private Bundle params = new Bundle();

        public LOADThread(Handler handler) {

            super();

            mHandler = handler;

        }

        @Override

        public void run() {

            try {


                params.putString("erro"   , "MENSAGEM");

                params.putString("msgerro", "Carregando Os Produtos...");

                sendmsg(params);

                pedido.loadItensNewOrder();

                params.putString("erro"   , "MENSAGEM");

                params.putString("msgerro", "Calculando Os Fretes...");

                sendmsg(params);

                List<Float> values = pedido.getFAIXAS();

                FreteDAO fretedao = new FreteDAO();

                Float FreteArroz  = 0f;

                Float FreteFeijao = 0f;

                for (Float value : values) {

                    Float qtd = 0f;

                    try {

                        qtd = value / pedido.getCabec().getQTDENTREGA();

                    } catch (Exception e){

                        qtd = value;
                    }

                    fretedao.open();

                    Frete freteArroz = fretedao.getFretArroz(qtd,pedido.getClienteEntrega().getREDE(),pedido.getClienteEntrega().getESTADO(),pedido.getClienteEntrega().getCODCIDADE());

                    fretedao.close();

                    if (freteArroz == null){

                        freteArroz = new Frete();

                        freteArroz.setFRETE(3.50f);

                    }

                    Frete freteFeijao = fretedao.getFreteFeijao(qtd,pedido.getClienteEntrega().getREDE(),pedido.getClienteEntrega().getESTADO(),pedido.getClienteEntrega().getCODCIDADE());

                    if (freteFeijao == null){

                        freteFeijao = new Frete();

                        freteFeijao.setFRETE(3.50f);

                    }

                    FreteArroz   = freteArroz.getFRETE();

                    FreteFeijao  = freteFeijao.getFRETE();

                    for(int i =0 ; i < pedido.getLsDetalhe().size() ; i++) {


                        pedido.FormandoPreco_V2(PedidoBusinessV10.FORMANDOPRECO_FRETE, i, value,FreteArroz,FreteFeijao);


                    }

                }


                params.putString("erro"   , "PRONTO");

                params.putString("msgerro", "");



            } catch (Exception e){

                params.putString("erro"   , "ERRO");

                params.putString("msgerro", e.getMessage());


            }

            sendmsg(params);



        }


        public void sendmsg(Bundle value) {

            if (value != null) {
                Message msgObj = mHandler.obtainMessage();
                msgObj.setData(value);
                mHandler.sendMessage(msgObj);
            }

        }

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

            String obj = lista.get(position)[1];

            LayoutInflater inflater =  getLayoutInflater();

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
    }

    private class Adapter extends BaseAdapter {


        private List<Object> lsObjetos;
        private List<Object> lsProdutos;
        private Context context;

        private String Filtro_grupo = "000";
        private String Filtro_marca = "000";
        private String SubFiltro    = "01" ;

        final int ITEM_VIEW_CABEC               = 0;
        final int ITEM_VIEW_PRODUTO             = 1;
        final int ITEM_VIEW_NO_DATA             = 2;
        final int ITEM_VIEW_NO_DATA_PROGRESS    = 3;
        final int ITEM_VIEW_COUNT               = 4;

        private LayoutInflater inflater;

        private String mensagem;



        public Adapter(Context context, List<Object> pObjects) {

            this.lsProdutos = pObjects;

            this.lsObjetos = filtro();

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void setFilter(String grupo,String marca){

            this.Filtro_grupo = grupo;

            this.Filtro_marca = marca;

            this.lsObjetos = filtro();

            notifyDataSetChanged();


        }

        private List<Object> filtro(){

            List<Object> retorno = new ArrayList<>();

            retorno.add("CABEC");

            if (Filtro_grupo.equals("000") && Filtro_marca.equals("000")){

                for(Object obj : lsProdutos){

                    if (obj instanceof NoData || obj instanceof NoDataProgress){

                        retorno.add(obj);

                        continue;

                    }


                    if (obj instanceof PedidoDetMB_fast){

                        if (SubFiltro.equals("01")) {

                            retorno.add(obj);
                        }
                        if (SubFiltro.equals("02") && !((PedidoDetMB_fast) obj).getNRO().isEmpty() && ((PedidoDetMB_fast) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }
                        if (SubFiltro.equals("03") && !((PedidoDetMB_fast) obj).getNRO().isEmpty() && !((PedidoDetMB_fast) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }
                        if (SubFiltro.equals("05") && ((PedidoDetMB_fast) obj).get_Mix()){

                            retorno.add(obj);
                        }

                    }

                }

                return retorno;

            }

            for(Object obj : lsProdutos){

                if (obj instanceof NoData || obj instanceof NoDataProgress){

                    retorno.add(obj);

                    continue;

                }


                if (obj instanceof PedidoDetMB_fast){

                    if ((!Filtro_grupo.equals("000") || (!Filtro_marca.equals("000")) )){

                        if ( ( Filtro_grupo.equals("000") || ((PedidoDetMB_fast) obj).get_CodGrupo().equals(Filtro_grupo) ) && ( Filtro_marca.equals("000") || ((PedidoDetMB_fast) obj).get_CodMarca().equals(Filtro_marca) ) ) {

                            if (SubFiltro.equals("01")){

                                retorno.add(obj);
                            }
                            if (SubFiltro.equals("02") && !((PedidoDetMB_fast) obj).getNRO().isEmpty() && ((PedidoDetMB_fast) obj).getSTATUS().equals("3")){

                                retorno.add(obj);

                            }
                            if (SubFiltro.equals("03") && !((PedidoDetMB_fast) obj).getNRO().isEmpty() && !((PedidoDetMB_fast) obj).getSTATUS().equals("3")){

                                retorno.add(obj);

                            }

                        }




                    } else {

                        if (SubFiltro.equals("01")) {

                            retorno.add(obj);
                        }
                        if (SubFiltro.equals("02") && ((PedidoDetMB_fast) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }
                        if (SubFiltro.equals("03") && !((PedidoDetMB_fast) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }

                    }
                }

            }

            return retorno;

        }

        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof PedidoDetMB_fast) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Produtos: " + String.valueOf(qtd);

            return retorno;
        }


        public void setMensagem(String mensa){

            this.mensagem = mensa;

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

            if (lsObjetos.get(position) instanceof PedidoDetMB_fast) {

                retorno = ITEM_VIEW_PRODUTO;

            }


            if (lsObjetos.get(position) instanceof NoData) {

                retorno = ITEM_VIEW_NO_DATA;

            }

            if (lsObjetos.get(position) instanceof NoDataProgress) {

                retorno = ITEM_VIEW_NO_DATA_PROGRESS;

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


                        case ITEM_VIEW_PRODUTO:

                            convertView = inflater.inflate(R.layout.tabela_produto_row, null);

                            break;


                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA_PROGRESS:

                            convertView = inflater.inflate(R.layout.progress_data_row, null);

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_PRODUTO: {

                        final PedidoDetMB_fast obj = (PedidoDetMB_fast) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.tabela_preco_05);

                        if((pos % 2)==0) {//par

                            view.setBackgroundColor(Color.parseColor("#BC8F8F"));

                        }
                        else {//impar

                            view.setBackgroundColor(Color.parseColor("#FFDEAD"));

                        }

                        TextView lbl_produto_05 = (TextView) convertView.findViewById(R.id.lbl_produto_05);
                        TextView lbl_preco_05   = (TextView) convertView.findViewById(R.id.lbl_preco_05);


                        View     ll_faixa_00  = (View)     convertView.findViewById(R.id.ll_faixa_00);
                        TextView lbl_faixa_00 = (TextView) convertView.findViewById(R.id.lbl_faixa_00);
                        TextView txt_faixa_00 = (TextView) convertView.findViewById(R.id.txt_faixa_00);

                        View     ll_faixa_01  = (View)     convertView.findViewById(R.id.ll_faixa_01);
                        TextView lbl_faixa_01 = (TextView) convertView.findViewById(R.id.lbl_faixa_01);
                        TextView txt_faixa_01 = (TextView) convertView.findViewById(R.id.txt_faixa_01);

                        View     ll_faixa_02  = (View)     convertView.findViewById(R.id.ll_faixa_02);
                        TextView lbl_faixa_02 = (TextView) convertView.findViewById(R.id.lbl_faixa_02);
                        TextView txt_faixa_02 = (TextView) convertView.findViewById(R.id.txt_faixa_02);

                        View     ll_faixa_03  = (View)     convertView.findViewById(R.id.ll_faixa_03);
                        TextView lbl_faixa_03 = (TextView) convertView.findViewById(R.id.lbl_faixa_03);
                        TextView txt_faixa_03 = (TextView) convertView.findViewById(R.id.txt_faixa_03);

                        View     ll_faixa_04  = (View)     convertView.findViewById(R.id.ll_faixa_04);
                        TextView lbl_faixa_04 = (TextView) convertView.findViewById(R.id.lbl_faixa_04);
                        TextView txt_faixa_04 = (TextView) convertView.findViewById(R.id.txt_faixa_04);

                        View     ll_faixa_05  = (View)     convertView.findViewById(R.id.ll_faixa_05);
                        TextView lbl_faixa_05 = (TextView) convertView.findViewById(R.id.lbl_faixa_05);
                        TextView txt_faixa_05 = (TextView) convertView.findViewById(R.id.txt_faixa_05);


                        lbl_produto_05.setText(obj.getPRODUTO()+"-"+obj.get_Produto());
                        lbl_preco_05.setText("Preço: "+format_02.format(obj.getPRCVEN()));

                        ll_faixa_00.setVisibility(View.INVISIBLE);
                        ll_faixa_01.setVisibility(View.INVISIBLE);
                        ll_faixa_02.setVisibility(View.INVISIBLE);
                        ll_faixa_03.setVisibility(View.INVISIBLE);
                        ll_faixa_04.setVisibility(View.INVISIBLE);
                        ll_faixa_05.setVisibility(View.INVISIBLE);

                        int i = 0;

                        for (Map.Entry<Float,Float> faixa : obj.getLsFreste().entrySet()) {

                            switch (i){

                                case 0:
                                    ll_faixa_00.setVisibility(View.VISIBLE);
                                    lbl_faixa_00.setText((faixa.getKey().compareTo(0f) == 0) ? "Cliente Retira" : "Até "+String.valueOf(faixa.getKey())+" FDS");
                                    txt_faixa_00.setText(format_02.format(faixa.getValue()));
                                    break;
                                case 1:
                                    ll_faixa_01.setVisibility(View.VISIBLE);
                                    lbl_faixa_01.setText("Até "+String.valueOf(faixa.getKey())+" FDS");
                                    txt_faixa_01.setText(format_02.format(faixa.getValue()));
                                    break;
                                case 2:
                                    ll_faixa_02.setVisibility(View.VISIBLE);
                                    lbl_faixa_02.setText("Até "+String.valueOf(faixa.getKey())+" FDS");
                                    txt_faixa_02.setText(format_02.format(faixa.getValue()));
                                    break;
                                case 3:
                                    ll_faixa_03.setVisibility(View.VISIBLE);
                                    lbl_faixa_03.setText("Até "+faixa.getKey()+" FDS");
                                    txt_faixa_03.setText(format_02.format(faixa.getValue()));
                                    break;
                                case 4:
                                    ll_faixa_04.setVisibility(View.VISIBLE);
                                    lbl_faixa_04.setText("Até "+faixa.getKey()+" FDS");
                                    txt_faixa_04.setText(format_02.format(faixa.getValue()));
                                    break;
                                case 5:
                                    ll_faixa_05.setVisibility(View.VISIBLE);
                                    lbl_faixa_05.setText("Até "+faixa.getKey()+" FDS");
                                    txt_faixa_05.setText(format_02.format(faixa.getValue()));
                                    break;
                            }

                            i++;

                        }


                        break;

                    }


                    case ITEM_VIEW_NO_DATA: {

                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                        tvTexto.setText(obj.getMensagem());

                        break;

                    }

                    case ITEM_VIEW_NO_DATA_PROGRESS: {

                        final NoDataProgress obj = (NoDataProgress) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.progress_data_row_texto);

                        tvTexto.setText(this.mensagem);

                        break;

                    }

                    default:

                        break;

                }

            } catch (Exception e) {

                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                Log.i("SAV",exceptionAsString);

                toast("Erro No Adapdador =>" + e.getMessage());

            }

            return convertView;

        }



    }

    private class Click implements View.OnClickListener{

        private PedidoCabMb obj;


        public Click() {

            this.obj = pedido.getCabec();

        }

        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.lbl_fardos_previstos_568:
                {

                    ClickGetDados("FARDOS PREVISTOS","FDSPREVISTO",0);

                    break;
                }


                default:

                    break;
            }


        }
    }

}
