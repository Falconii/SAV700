package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.sav700.Adaptadores.defaultAdapter001;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.ContaCorrenteDAO;
import br.com.brotolegal.savdatabase.dao.PedDetTvsDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.ContaCorrente;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;

public class CCActivity extends AppCompatActivity {

    private Boolean OrdemRefresh = true;

    private Toolbar toolbar;

    private ListView lv;

    private List<Object> lsLista;

    Spinner spSituacoes;

    Spinner spRede;

    Map<String,String> mpRedes     = new TreeMap<String, String >();

    List<String[]> lsPeriodos          = new ArrayList<>();

    List<String[]> lsRedes             = new ArrayList<>();

    defaultAdapter periodoadapter;

    defaultAdapter redeadapter;

    Adapter adapter;

    defaultAdapter001 periodoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc);


        try {
            toolbar = (Toolbar) findViewById(R.id.tb_cc_451);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Controle Conta Corrente");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.inflateMenu(R.menu.menu_cc);

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

//                CodCliente = params.getString("CODCLIENTE","");
//
//                LojCliente = params.getString("LOJCLIENTE","");
//
//                PEDIDO = params.getString("PEDIDO","");
//
//                FILIAL = params.getString("FILIAL","");


            }

            lv = (ListView) findViewById(R.id.lvCC_451);

//            spFiliais = (Spinner) findViewById(R.id.sp_cidade_334);
//
//            spSituacoes = (Spinner) findViewById(R.id.sp_rede_334);
//
//            spOrdem = (Spinner) findViewById(R.id.sp_ordem_334);
//
//            edPesquisa = (EditText) findViewById(R.id.edpesquisa_334);
//
//            lsOrdens = new ArrayList<>();
//
//            lsOrdens.add(new String[]{"01", "Nº Pedido Protheus"});
//            lsOrdens.add(new String[]{"02", "Razão Social"});
//            lsOrdens.add(new String[]{"03", "Código Cliente"});
//            lsOrdens.add(new String[]{"04", "CNPJ"});
//            lsOrdens.add(new String[]{"05", "Nº Pedido Tablete"});
//
//
//            ordensadapter = new defaultAdapter(CCActivity.this, R.layout.choice_default_row, lsOrdens, "");
//
//            spOrdem.setAdapter(ordensadapter);
//
//            spOrdem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                    ordensadapter.setEscolha(position);
//
//                    Object lixo = spOrdem.getSelectedItem();
//
//                    if (lixo != null){
//
//                        if (adapter != null){
//
//                            adapter.setOrdem(((String[]) lixo)[0]);
//
//                        }
//
//                    }
//
//                    edPesquisa.setText("");
//
//                    switch (position) {
//                        case 0:  //numerico
//
//                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);
//
//                            break;
//
//                        case 1:  //texto maiusculo
//
//                            edPesquisa.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
//
//                            break;
//
//                        case 2:  //numerico
//
//                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);
//
//                            break;
//
//                        case 3:  //numerico
//
//                            edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);
//
//                            break;
//
//                    }
//
//                    if (!(lsLista == null)) {
//
//                        //loadPedidos();
//
//                    }
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
//            spOrdem.setSelection(0);
//
//
             loadCC();

        } catch (Exception e){

            showToast(e.getMessage());

            finish();

        }

    }

    private void loadCC(){

        try {

            lsLista = new ArrayList<>();

////            lsSituacoes        = new ArrayList<>();
////
////            lsFiliais          = new ArrayList<>();
////
////            lsOrdens           = new ArrayList<>();
////
////            lsFiliais.add(new String[] {""  ,"TODAS"});
////            lsFiliais.add(new String[] {"02","PORTO"});
////            lsFiliais.add(new String[] {"07","MATRIZ"});
////
////            lsSituacoes.add(new String[] {""  ,"TODAS"});

            lsLista.add("Pedidos");

            //Object lixo      = spOrdem.getSelectedItem();

            ContaCorrenteDAO dao = new ContaCorrenteDAO();

            dao.open();

            lsLista.addAll(dao.getAll());

            dao.close();

            if (lsLista.size() == 1){

                lsLista.add(new NoData("Nenhm Documento Encontrado !!!"));

            } else {


            }

            adapter = new Adapter(getBaseContext(),lsLista);

            //adapter.setOrdem(((String[]) lixo)[0]);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        } catch (Exception e){

            showToast(e.getMessage());

        }

    }

    private void showToast(String mensagem){

        Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();

    }

    private class Adapter extends BaseAdapter {


        private String _TIPODOC     = "A";

        private DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private DecimalFormat format_03 = new DecimalFormat(",##0.000");
        private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
        private DecimalFormat format_05 = new DecimalFormat(",##0.00000");

        private List<Object> lsObjetos;

        private Context context;

        final int ITEM_VIEW_CABEC    = 0;
        final int ITEM_VIEW_NOTA     = 1;
        final int ITEM_VIEW_ACORDO   = 2;
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

            Float total = 0f;

            for (Object obj : lsObjetos) {

                if (obj instanceof ContaCorrente) {

                    if (((ContaCorrente) obj).getTIPO().equals("PEDIDO")){

                        total += ((ContaCorrente) obj).getARRECADADO();

                    } else {

                        total -= ((ContaCorrente) obj).getARRECADADO();


                    }

                }

            }

            retorno = "Saldo Do Periodo: " + format_02.format(total);

            return retorno;
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

            if (_TIPODOC.equals("A")) {

                return new ArrayList(lsLista);

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsLista.size(); x++) {

                    if (lsLista.get(x) instanceof ContaCorrente) {

                        if (    (_TIPODOC.equals(((ContaCorrente) lsLista.get(x)).getTIPO()))) {

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

////            if (edPesquisa.getText().toString().trim().isEmpty()) {
////
////                return new ArrayList(lsLista);
////
////            } else {
////
////                result = new ArrayList<>();
////
////                for (int x = 0; x < lsLista.size(); x++) {
////
////                    if (lsLista.get(x) instanceof PedCabTvs) {
////
////
////                        if (_Ordem.equals("01")) {//nro do pedido
////
////                            if (((PedCabTvs) lsLista.get(x)).getPEDIDO().contains(edPesquisa.getText().toString().trim())) {
////
////                                result.add(lsLista.get(x));
////
////                            }
////
////
////                        }
////                        if (_Ordem.equals("02")) {//razao
////
////                            if (((PedCabTvs) lsLista.get(x)).getRAZAO().contains(edPesquisa.getText().toString().trim())) {
////
////                                result.add(lsLista.get(x));
////
////                            }
////
////                        }
////
////                        if (_Ordem.equals("03")) {//codigo cliente
////
////                            if (((PedCabTvs) lsLista.get(x)).getCLIENTE().contains(edPesquisa.getText().toString().trim())) {
////
////                                result.add(lsLista.get(x));
////
////                            }
////                        }
////                        if (_Ordem.equals("04")) {//cnpj
////
////                            if (((PedCabTvs) lsLista.get(x)).get_CNPJ().contains(edPesquisa.getText().toString().trim())) {
////
////                                result.add(lsLista.get(x));
////
////                            }
////
////                        }
////
////                        if (_Ordem.equals("05")){//nro pedido tablete
////
////                            if (((PedCabTvs) lsLista.get(x)).getPEDIDOMOBILE().contains(edPesquisa.getText().toString().trim())){
////
////                                result.add(lsLista.get(x));
////
////                            }
////
////                        }
////
////
////
////                    } else {
////
////                        result.add(lsLista.get(x));
////
////                    }
////
////                }
//
//            }

            if (result.size() == 1){

                result.add(new NoData("Nenhum Cliente Para O Filtro !!!"));

            }
            return result;
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

            if (lsObjetos.get(position) instanceof ContaCorrente) {

                if (((ContaCorrente) lsObjetos.get(position)).getTIPO().equals("PEDIDO")){

                    retorno = ITEM_VIEW_NOTA;

                } else {

                    retorno = ITEM_VIEW_ACORDO;
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


                        case ITEM_VIEW_NOTA:

                            convertView = inflater.inflate(R.layout.cc_nota_row, null);

                            break;


                        case ITEM_VIEW_ACORDO:

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

                    case ITEM_VIEW_NOTA: {

                        final ContaCorrente obj = (ContaCorrente) lsObjetos.get(pos);

                        TextView txt_mensagem_499 = (TextView) convertView.findViewById(R.id.txt_mensagem_499);

                        txt_mensagem_499.setText(App.aaaammddToddmmaa(obj.getEMISSAO()));

                        TextView txt_nro_pedido_499 = (TextView) convertView.findViewById(R.id.txt_nro_pedido_499);

                        txt_nro_pedido_499.setText("PEDIDO: "+obj.getFILPED()+"-"+obj.getPEDIDO());

                        TextView txt_nro_nota_499 = (TextView) convertView.findViewById(R.id.txt_nro_nota_499);

                        txt_nro_nota_499.setText("NOTA FISCAL: "+obj.getFILIAL()+"-"+obj.getNOTAFISCAL());

                        TextView txt_cliente_499 = (TextView) convertView.findViewById(R.id.txt_cliente_499);

                        txt_cliente_499.setText(obj.getCODCLI()+"-"+obj.getFILIAL()+" "+obj.getNOMECLI());

                        TextView txt_produto_499 = (TextView) convertView.findViewById(R.id.txt_produto_499);

                        txt_produto_499.setText("PRODUTO: "+obj.getCODPROD()+" "+obj.getNOMEPROD());

                        TextView txt_doc_499 = (TextView) convertView.findViewById(R.id.txt_doc_499);

                        txt_doc_499.setText(obj.getCODPROMOCAO());

                        TextView txt_pl_499 = (TextView) convertView.findViewById(R.id.txt_pl_499);

                        txt_pl_499.setText(format_02.format(obj.getPRCMIN()));

                        TextView txt_pp_499 = (TextView) convertView.findViewById(R.id.txt_pp_499);

                        txt_pp_499.setText(format_02.format(obj.getPRCVEN()));

                        TextView txt_pua_499 = (TextView) convertView.findViewById(R.id.txt_pua_499);

                        txt_pua_499.setText(format_02.format(obj.getPRCVEN() - obj.getPRCMIN()));

                        TextView txt_qtd_499 = (TextView) convertView.findViewById(R.id.txt_qtd_499);

                        txt_qtd_499.setText(String.valueOf(obj.getQTDVEN()));

                        TextView txt_vlrarrec_499 = (TextView) convertView.findViewById(R.id.txt_vlrarrec_499);

                        txt_vlrarrec_499.setText(format_02.format(obj.getARRECADADO()));

                        break;

                    }

                    case ITEM_VIEW_ACORDO: {

                        final ContaCorrente obj = (ContaCorrente) lsObjetos.get(pos);


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
