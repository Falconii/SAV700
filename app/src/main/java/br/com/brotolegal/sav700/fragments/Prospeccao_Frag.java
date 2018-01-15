package br.com.brotolegal.sav700.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import br.com.brotolegal.sav700.ProspeccaoActivity;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ProspeccaoDAO;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Prospeccao;


public class Prospeccao_Frag extends Fragment {

    ListView lv;

    FloatingActionButton fab;

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

        View rootView = inflater.inflate(R.layout.fragment_prospeccao, container, false);

        spCidade =   (Spinner) rootView.findViewById(R.id.sp_cidade_334);

        spRede   =   (Spinner) rootView.findViewById(R.id.sp_rede_334);

        spOrdem  =   (Spinner)  rootView.findViewById(R.id.sp_ordem_334);

        lv=(ListView) rootView.findViewById(R.id.lvClientesProspeccao);

        fab = (FloatingActionButton) rootView.findViewById(R.id.plus_prospeccao);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POS = -5;
                Intent intent = new Intent(getContext(), ProspeccaoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle params = new Bundle();
                params.putString("ID"  , "");
                intent.putExtras(params);
                getContext().startActivity(intent);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Prospecção de Clientes");

        loadProspeccao();


    }

    @Override
    public void onResume() {

        try {

            if (POS == -5){


                loadProspeccao();


            }

            if (POS >= 0) {

                ProspeccaoDAO dao = new ProspeccaoDAO();

                dao.open();

                Prospeccao propecto = dao.seek(new String[] {((Prospeccao)lsLista.get(POS)).getID()});

                dao.close();

                if (propecto != null) {

                    lsLista.set(POS, propecto);

                    adapter.setCliente(propecto);

                } else {

                    loadProspeccao();
                }
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }


        POS = -1;

        super.onResume();
    }

    private void loadProspeccao(){

        try {

            lsLista    = new ArrayList<Object>();

            lsCidades  = new ArrayList<>();

            lsRedes    = new ArrayList<>();

            lsOrdens   = new ArrayList<>();

            lsLista.add("Prospecções");

            ProspeccaoDAO dao = new ProspeccaoDAO();

            dao.open();

            lsLista.addAll(dao.getAll());

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

                    if (obj instanceof Prospeccao){

                        try {

                            mpCidades.put(((Prospeccao) obj).getCODCIDADE(), ((Prospeccao) obj).getCIDADE());

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

        public void setCliente(Prospeccao propeccto){

            int x = 0;

            for(Object obj : lsOriginal){

                if (obj instanceof Prospeccao){

                    if (((Prospeccao) obj).getID().equals(propeccto.getID())){

                        lsOriginal.set(x,propeccto);

                        break;

                    }

                }

                x++;

            }

            x = 0;

            for(Object obj : lsObjetos){

                if (obj instanceof Prospeccao){

                    if (((Prospeccao) obj).getID().equals(propeccto.getID())){

                        lsObjetos.set(x,propeccto);

                        break;

                    }

                }

                x++;

            }


            notifyDataSetChanged();

        }

        private List<Object> filtro() {

            List<Object> result = this.lsOriginal;

            return result;
        }





        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Prospeccao) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Clientes Prospectados: " + String.valueOf(qtd);

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

            if (lsObjetos.get(position) instanceof Prospeccao) {

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

                            convertView = inflater.inflate(R.layout.cliente_prospeccao_row, null);

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

                        final Prospeccao obj = (Prospeccao) lsObjetos.get(pos);

                        Drawable img = getResources().getDrawable(R.drawable.error5);

                        img.setBounds(0, 0, 30, 30);

                        ImageButton bt_agendamento  = (ImageButton) convertView.findViewById(R.id.bt_agendamento_407);

                        TextView txt_id_407         = (TextView) convertView.findViewById(R.id.txt_id_407);

                        TextView txt_data_407       = (TextView) convertView.findViewById(R.id.txt_data_407);

                        TextView txt_situacao_407   = (TextView) convertView.findViewById(R.id.txt_situacao_407);

                        TextView txt_razao_407      = (TextView) convertView.findViewById(R.id.txt_razao_407);

                        TextView txt_endereco_407   = (TextView) convertView.findViewById(R.id.txt_endereco_407);

                        TextView txt_bairro_407     = (TextView) convertView.findViewById(R.id.txt_bairro_407);

                        TextView txt_cidade_407     = (TextView) convertView.findViewById(R.id.txt_cidade_407);

                        TextView txt_telefone_407   = (TextView) convertView.findViewById(R.id.txt_telefone_407);

                        TextView txt_cnpj_407       = (TextView) convertView.findViewById(R.id.txt_cnpj_407);

                        TextView txt_ie_407         = (TextView) convertView.findViewById(R.id.txt_ie_407);

                        TextView txt_obs_407        = (TextView) convertView.findViewById(R.id.txt_obs_407);

                        TextView txt_contato_407    = (TextView) convertView.findViewById(R.id.txt_contato_407);

                        TextView txt_email_407      = (TextView) convertView.findViewById(R.id.txt_email_407);


                        if (obj.getSTATUS().equals("0")){

                            txt_situacao_407.setCompoundDrawables(img, null, null, null);


                        } else {

                            txt_situacao_407.setCompoundDrawables(null, null, null, null);

                        }


                        txt_id_407.setText("ID: "+obj.getID());

                        txt_data_407.setText("DATA: "+ App.aaaammddToddmmaaaa(obj.getDATA()));

                        txt_situacao_407.setText("SIT.: "+obj.get_STATUS());

                        txt_razao_407.setText(obj.getRAZAO());

                        txt_endereco_407.setText(obj.getENDERECO().trim()+" "+obj.getNRO());

                        txt_bairro_407.setText(  obj.getBAIRRO());

                        txt_cidade_407.setText(  "Código: "+obj.getCODCIDADE()+"-"+obj.getCIDADE().trim()+","+obj.getESTADO());

                        txt_telefone_407.setText("("+obj.getDDD()+") "+obj.getTELEFONE());

                        txt_cnpj_407.setText(    App.cnpj_cpf(obj.getCNPJ()));

                        txt_ie_407.setText(      obj.getIE());

                        txt_obs_407.setText(     obj.getOBS());

                        txt_contato_407.setText( obj.getCONTATO());

                        txt_email_407.setText(   obj.getEMAIL());

                        bt_agendamento.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View v) {

                                                                  if (obj.getSTATUS().compareTo("1") <= 0){

                                                                      POS = pos;

                                                                  } else {

                                                                      POS = -1;

                                                                  }

                                                                  Intent intent=new Intent(context,ProspeccaoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                  Bundle params = new Bundle();
                                                                  params.putString("ID"  , obj.getID());
                                                                  intent.putExtras(params);
                                                                  context.startActivity(intent);

                                                              }
                                                          }
                        );


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
