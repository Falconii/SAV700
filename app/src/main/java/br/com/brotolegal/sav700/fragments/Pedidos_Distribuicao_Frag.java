package br.com.brotolegal.sav700.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.NoData;


public class Pedidos_Distribuicao_Frag extends Fragment {

    ListView lv;

    List<Object> lsLista;

    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pedido_distribuicao, container, false);

        lv=(ListView) rootView.findViewById(R.id.lvClientesDistribuicao);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Pedidos De Distribuição");


        if (savedInstanceState == null){

            //LoadClientes();

        }


    }



    private void LoadClientes(){

        try {

            lsLista = new ArrayList<Object>();

            lsLista.add("Clientes");

            ClienteDAO dao = new ClienteDAO();

            dao.open();

            lsLista.addAll(dao.getAll_fast("",""));

            dao.close();

            if (lsLista.size() == 1) {

                lsLista.add(new NoData("Nenhum Cliente Encontrado !!"));

            }

            adapter = new Adapter(getContext(), lsLista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e) {

            Toast.makeText(getContext(), "Erro Na Carga: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        Context context;

        final int ITEM_VIEW_CABEC   = 0;
        final int ITEM_VIEW_DETALHE = 1;
        final int ITEM_VIEW_NO_DATA = 2;
        final int ITEM_VIEW_COUNT   = 3;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos = pObjects;
            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Cliente_fast) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Pedidos: " + String.valueOf(qtd);

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

                        txt_desc_rede_400.setText("Rede: "+obj.getREDE());

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



}
