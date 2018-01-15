package br.com.brotolegal.sav700.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.sav700.ClienteViewAtivity;
import br.com.brotolegal.sav700.ContratoViewActivity;
import br.com.brotolegal.sav700.LancaPedidoActivity;
import br.com.brotolegal.sav700.PedidosActivity;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.sav700.Receber_View_Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeTabelaPreco;


public class Pedidos_Tablet_Frag extends Fragment {

    ListView lv;

    Spinner spCidade;

    Spinner spRede;

    Spinner spOrdem;

    List<Object> lsLista;

    Map<String,String> mpCidades    = new TreeMap<String, String >();

    Map<String,String> mpRedes      = new TreeMap<String, String >();

    List<String[]> lsCidades        = new ArrayList<>();

    List<String[]> lsRedes          = new ArrayList<>();

    List<String[]> lsOrdens         = new ArrayList<>();

    defaultAdapter cidadeadapter;

    defaultAdapter redeadapter;

    defaultAdapter ordensadapter;

    Adapter adapter;

    int  POS = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pedido_tablet, container, false);

        spCidade =   (Spinner) rootView.findViewById(R.id.sp_cidade_334);

        spRede   =   (Spinner) rootView.findViewById(R.id.sp_rede_334);

        spOrdem  =   (Spinner)  rootView.findViewById(R.id.sp_ordem_334);

        lv=(ListView) rootView.findViewById(R.id.lvClientesSales);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Pedidos Avulsos");

        LoadClientes();

    }

    @Override
    public void onResume() {



        try {


            if (POS != -1) {

                ClienteDAO dao = new ClienteDAO();

                dao.open();

                Cliente_fast cliente = dao.seek_fast(((Cliente_fast)lsLista.get(POS)).getCODIGO(),((Cliente_fast)lsLista.get(POS)).getLOJA(),"");

                dao.close();

                if (cliente != null) {

                    lsLista.set(POS, cliente);

                    adapter.setCliente(cliente);


                }
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }


        POS = -1;

        super.onResume();
    }

    private void LoadClientes(){

        try {

            lsLista    = new ArrayList<Object>();

            lsCidades  = new ArrayList<>();

            lsRedes    = new ArrayList<>();

            lsOrdens   = new ArrayList<>();

            lsLista.add("Clientes");

            ClienteDAO dao = new ClienteDAO();

            dao.open();

            lsLista.addAll(dao.getAll_fast("",""));

            dao.close();

            lsOrdens.add(new String[]{"01", "Razão Social"});
            lsOrdens.add(new String[]{"02", "Código"});
            lsOrdens.add(new String[]{"03", "Fantasia"});

            mpCidades.put("","TODAS");

            mpRedes.put("", "TODAS");

            if (lsLista.size() == 1) {

                lsLista.add(new NoData("Nenhum Cliente Encontrado !!"));

            } else {

                for(Object obj : lsLista){

                    if (obj instanceof Cliente_fast){

                        try {

                            mpCidades.put(((Cliente_fast) obj).getCODCIDADE(), ((Cliente_fast) obj).getCIDADE());

                            mpRedes.put(((Cliente_fast) obj).getREDE()       , ((Cliente_fast) obj).get_REDE());

                        } catch (Exception e){

                            //

                        }

                    }

                }


                for(Map.Entry<String, String> values : mpCidades.entrySet()){


                    lsCidades.add(new String[] {values.getKey(),values.getValue()});

                }


                for(Map.Entry<String, String> values : mpRedes.entrySet()){


                    lsRedes.add(new String[] {values.getKey(),values.getValue()});

                }
            }


            ordensadapter = new defaultAdapter(getContext(), R.layout.choice_default_row, lsOrdens,"");

            spOrdem.setAdapter(ordensadapter);

            spOrdem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ordensadapter.setEscolha(position);

                    Object lixo = spOrdem.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spOrdem.setSelection(0);

            cidadeadapter = new defaultAdapter(getContext(), R.layout.choice_default_row, lsCidades,"Cidade");

            spCidade.setAdapter(cidadeadapter);

            spCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    cidadeadapter.setEscolha(position);

                    Object lixo = spCidade.getSelectedItem();

                    adapter.setCidade(((String[]) lixo)[0]);

                    adapter.refresh();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spCidade.setSelection(0);

            redeadapter = new defaultAdapter(getContext(), R.layout.choice_default_row, lsRedes,"Rede");

            spRede.setAdapter(redeadapter);

            spRede.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    redeadapter.setEscolha(position);

                    Object lixo = spRede.getSelectedItem();

                    adapter.setRede(((String[]) lixo)[0]);

                    adapter.refresh();


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spRede.setSelection(0);

            adapter = new Adapter(getContext(), lsLista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e) {

            Toast.makeText(getContext(), "Erro Na Carga: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    public void refresh(){

        LoadClientes();

    }



    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsOriginal;

        private List<Object> lsObjetos;

        Context context;

        private String _Cidade = "";
        private String _Rede   = "";

        final int ITEM_VIEW_CABEC   = 0;
        final int ITEM_VIEW_DETALHE = 1;
        final int ITEM_VIEW_NO_DATA = 2;
        final int ITEM_VIEW_COUNT   = 3;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsOriginal = pObjects;

            this.lsObjetos  = filtro();

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        public void setCidade(String filtro){

            _Cidade = filtro;

        }

        public void setRede(String filtro){

            _Rede = filtro;

        }

        public void refresh(){


            this.lsObjetos  = filtro();

            notifyDataSetChanged();

        }

        public void setCliente(Cliente_fast cliente){

            int x = 0;

            for(Object obj : lsOriginal){

                if (obj instanceof Cliente_fast){

                    if (((Cliente_fast) obj).getCODIGO().equals(cliente.getCODIGO()) && ((Cliente_fast) obj).getLOJA().equals(cliente.getLOJA())){

                        lsOriginal.set(x,cliente);

                        break;

                    }

                }

                x++;

            }

            x = 0;

            for(Object obj : lsObjetos){

                if (obj instanceof Cliente_fast){

                    if (((Cliente_fast) obj).getCODIGO().equals(cliente.getCODIGO()) && ((Cliente_fast) obj).getLOJA().equals(cliente.getLOJA())){

                        lsObjetos.set(x,cliente);

                        break;

                    }

                }

                x++;

            }


            notifyDataSetChanged();

        }

        private List<Object> filtro() {

            List<Object> result = null;

            if (_Cidade.equals("") && _Rede.equals("")) {

                return lsOriginal;

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsOriginal.size(); x++) {

                    if (lsOriginal.get(x) instanceof Cliente_fast) {

                        if (    (_Cidade.equals("") || (_Cidade.equals(((Cliente_fast) lsOriginal.get(x)).getCODCIDADE()))) &&
                                (_Rede.equals("") || _Rede.equals(((Cliente_fast) lsOriginal.get(x)).getREDE())) ) {

                            result.add(lsOriginal.get(x));

                        }
                    } else {

                        result.add(lsOriginal.get(x));

                    }

                }

            }

            if (result.size() == 1){

                result.add(new NoData("Nenhum Cliente Para O Filtro !!!"));

            }
            return result;
        }





        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Cliente_fast) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Clientes: " + String.valueOf(qtd);

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

            if (lsObjetos.get(position) instanceof Cliente_fast) {

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

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.cliat_row, null);

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

                        final Cliente_fast obj = (Cliente_fast) lsObjetos.get(pos);


                        ImageButton bt_cadastro   = (ImageButton) convertView.findViewById(R.id.bt_enviar_111);

                        ImageButton bt_contrato   = (ImageButton) convertView.findViewById(R.id.bt_contrato_400);

                        ImageButton bt_financeiro = (ImageButton) convertView.findViewById(R.id.bt_financeiro_400);

                        ImageButton bt_pedidos    = (ImageButton) convertView.findViewById(R.id.bt_pedidos_400);

                        ImageButton bt_nf         = (ImageButton) convertView.findViewById(R.id.bt_nf_400);

                        ImageButton bt_agenda         = (ImageButton) convertView.findViewById(R.id.bt_agenda_400);

                        ImageButton bt_pedido = (ImageButton) convertView.findViewById(R.id.bt_alteracao_400);

                        ImageButton bt_salles_lista = (ImageButton) convertView.findViewById(R.id.bt_salles_lista);


                        TextView txt_flag_red = (TextView) convertView.findViewById(R.id.txt_flag_red);

                        TextView txt_flag_yellow = (TextView) convertView.findViewById(R.id.txt_flag_yellow);



                        TextView txt_codigo_400 = (TextView) convertView.findViewById(R.id.txt_codigo_400);

                        txt_codigo_400.setText("Código Protheus: " + obj.getCODIGO());

                        TextView txt_situacao_400 = (TextView) convertView.findViewById(R.id.txt_situacao_400);

                        txt_situacao_400.setText("Situação Do Cliente: "+obj.getSITUACAO());

                        if (!obj.getSITUACAO().trim().equals("ATIVO")){

                            txt_situacao_400.setTextColor(getResources().getColor(R.color.red));

                        } else {

                            txt_situacao_400.setTextColor(getResources().getColor(R.color.green));

                        }


                        TextView txt_cliente_400 = (TextView) convertView.findViewById(R.id.txt_cliente_400);

                        txt_cliente_400.setText("Cliente: "+obj.getCODIGO()+"-"+obj.getRAZAO());

                        TextView txt_cnpj_400 = (TextView) convertView.findViewById(R.id.txt_cnpj_400);

                        txt_cnpj_400.setText("CNPJ/CPF: "+obj.getCNPJ());

                        TextView txt_IE_400   = (TextView) convertView.findViewById(R.id.txt_ie_400);

                        txt_IE_400.setText("I.E.: "+obj.getIE());

                        TextView txt_cidade_400   = (TextView) convertView.findViewById(R.id.txt_cidade_400);

                        txt_cidade_400.setText("Cidade: "+obj.getCIDADE());

                        TextView txt_telefone_400   = (TextView) convertView.findViewById(R.id.txt_telefone_400);

                        txt_telefone_400.setText("Tel.: "+obj.getTELEFONE());

                        TextView txt_desc_rede_400 = (TextView) convertView.findViewById(R.id.txt_desc_rede_400);

                        txt_desc_rede_400.setText("Rede: "+obj.getREDE()+"-"+obj.get_REDE());

                        txt_flag_yellow.setText(String.valueOf(obj.get_yellow()));

                        txt_flag_red.setText(String.valueOf(obj.get_red()));

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



                        bt_contrato.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               Intent intent=new Intent(context,ContratoViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                               Bundle params = new Bundle();
                                                               params.putString("CODIGO"  , obj.getCODIGO());
                                                               params.putString("RAZAO"   , obj.getRAZAO());
                                                               params.putString("CONTRATO", obj.getCONTRATO());
                                                               intent.putExtras(params);
                                                               context.startActivity(intent);

                                                           }
                                                       }
                        );


                        bt_financeiro.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               Intent intent=new Intent(context,Receber_View_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                               Bundle params = new Bundle();
                                                               params.putString("CODIGO"  , obj.getCODIGO());
                                                               params.putString("LOJA"    , obj.getLOJA());
                                                               intent.putExtras(params);
                                                               context.startActivity(intent);

                                                           }
                                                       }
                        );


                        bt_pedido.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {

                                    App.tabelaValida();

                                } catch (ExceptionValidadeTabelaPreco e){

                                    toast(e.getMessage());

                                    return;

                                } catch (Exception e) {

                                    toast(e.getMessage());

                                    return;

                                }

                                POS = pos;
                                Intent intent = new Intent(context, LancaPedidoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();
                                params.putString("CODIGO", obj.getCODIGO());
                                params.putString("LOJA", obj.getLOJA());
                                params.putString("OPERACAO", "NOVO");
                                params.putString("NROPEDIDO", "");
                                intent.putExtras(params);
                                context.startActivity(intent);


                            }
                        });

                        bt_salles_lista.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                POS = pos;
                                Intent intent=new Intent(context,PedidosActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();
                                params.putString("CODIGO"  , obj.getCODIGO());
                                params.putString("LOJA", obj.getLOJA());
                                intent.putExtras(params);
                                context.startActivity(intent);

                            }
                        });
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
