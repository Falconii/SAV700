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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.brotolegal.sav700.ClienteViewAtivity;
import br.com.brotolegal.sav700.ContratoViewActivity;
import br.com.brotolegal.sav700.LancaPedidoActivity;
import br.com.brotolegal.sav700.PedidosActivity;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.sav700.Receber_View_Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.entities.AGENDADATA;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.util.AgendamentoController;


public class Pedidos_Planejamento_Frag extends Fragment {

    ListView lv;

    Spinner spMesAno;

    Spinner spData;

    List<Object> lsLista;

    List<String[]> lsMesAno         = new ArrayList<>();

    List<String[]> lsData           = new ArrayList<>();

    defaultAdapter MesAnoadapter;

    defaultAdapter Dataadapter;

    Adapter adapter;

    int  POS = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pedido_planejamento, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Pedidos Do Planejamento");

        spMesAno =   (Spinner) rootView.findViewById(R.id.sp_mes_ano_337);

        spData   =   (Spinner) rootView.findViewById(R.id.sp_dia_337);

        lv=(ListView) rootView.findViewById(R.id.lvClientesPlanejamento);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Filtro();


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

    private void Filtro(){

        try {

            lsLista    = new ArrayList<Object>();

            lsMesAno   = new ArrayList<>();

            lsData      = new ArrayList<>();

            //Monta Lista Dos Meses 1 ano

            AgendamentoController agendamento = new AgendamentoController();

            Calendar c = Calendar.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String hoje = format.format(c.getTime());

            hoje = "01" + hoje.substring(2);

            List<AgendamentoController.Agenda> meses = agendamento.Agendar("m", format.parse(hoje), null);

            for(int x=0; x < meses.size() ; x++){

                lsMesAno.add(new String[] {String.valueOf(x+1),meses.get(x).getMesAno()});

            }

            MesAnoadapter = new defaultAdapter(getContext(), R.layout.choice_default_row, lsMesAno,"MES:");

            spMesAno.setAdapter(MesAnoadapter);

            spMesAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    DateFormat sdf  = new SimpleDateFormat("dd/MMMM/yyyy");

                    DateFormat sdf2  = new SimpleDateFormat("yyyyMMdd");

                    MesAnoadapter.setEscolha(position);

                    Object lixo = spMesAno.getSelectedItem();

                    try {

                        Date hoje = sdf.parse("01/"+ ((String[]) lixo)[1]);

                        String Database = sdf2.format(hoje);

                        Database = Database.substring(0,6);

                        lsData      = new ArrayList<>();

                        AgendamentoDAO daoAGE = new AgendamentoDAO();

                        daoAGE.open();

                        List<AGENDADATA> lsDatas = new ArrayList<AGENDADATA>();//daoAGE.getAllByDia(Database);

                        daoAGE.close();

                        if (lsDatas.size() == 0){

                            lsData.add(new String[]{"", "NENHUM AGENDAMENTO ENCONTRADO."});


                        } else {

                            for (AGENDADATA ag : lsDatas) {

                                lsData.add(new String[]{String.valueOf(ag.getContador()), App.aaaammddToddmmaaaa(ag.getData())});

                            }
                        }
                    } catch (Exception e){

                        lsData.add(new String[] {" ","NEHUMA AGENDA ENCONTRADA."});


                    }
                    Dataadapter = new defaultAdapter(getContext(), R.layout.choice_default_row, lsData,"Data:");

                    spData.setAdapter(Dataadapter);

                    spData.setSelection(0);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spMesAno.setSelection(0);


            spData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    lsData = new ArrayList<String[]>();

                    DateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");

                    DateFormat sdf2  = new SimpleDateFormat("yyyyMMdd");

                    Dataadapter.setEscolha(position);

                    Object lixo = spData.getSelectedItem();

                    try {

                        Date hoje = sdf.parse(((String[]) lixo)[1]);

                        String Database = sdf2.format(hoje);

                        LoadClientes(Database);

                    } catch (Exception e){

                        lsData.add(new String[] {" ","NENHUMA AGENDA ENCONTRADA."});


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });


        } catch (Exception e) {

            Toast.makeText(getContext(), "Erro Na Carga: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }


    private void LoadClientes(String DataBase){

        try {

            lsLista    = new ArrayList<Object>();

            lsLista.add("Clientes");

            ClienteDAO dao = new ClienteDAO();

            dao.open();

            //lsLista.addAll(dao.getAll_fastByRota(DataBase));

            dao.close();

            if (lsData.size() == 1){


                lsLista.add(new NoData("Cliente Cliente Agendado Para Esta Data."));

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

            this.lsObjetos  = pObjects;

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }



        public void setCliente(Cliente_fast cliente){

            int x = 0;


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


                        ImageButton bt_cadastro   = (ImageButton) convertView.findViewById(R.id.bt_file_408);

                        ImageButton bt_contrato   = (ImageButton) convertView.findViewById(R.id.bt_contrato_400);

                        ImageButton bt_financeiro = (ImageButton) convertView.findViewById(R.id.bt_financeiro_400);

                        ImageButton bt_pedidos    = (ImageButton) convertView.findViewById(R.id.bt_pedidos_400);

                        ImageButton bt_nf         = (ImageButton) convertView.findViewById(R.id.bt_nf_400);

                        ImageButton bt_agenda         = (ImageButton) convertView.findViewById(R.id.bt_agenda_400);

                        ImageButton bt_alteracao = (ImageButton) convertView.findViewById(R.id.bt_alteracao_400);

                        ImageButton bt_salles_lista = (ImageButton) convertView.findViewById(R.id.bt_salles_lista);


                        TextView lbl_hora     = (TextView) convertView.findViewById(R.id.lbl_hora_400);


                        lbl_hora.setText(obj.getROTHORA());

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


                        bt_alteracao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

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

            String col01 = lista.get(position)[0];
            String col02 = lista.get(position)[1];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_01_row, parent, false);

            TextView tvcol01 = (TextView) layout.findViewById(R.id.txt_col01_898);

            TextView tvcol02 = (TextView) layout.findViewById(R.id.txt_col02_898);

            tvcol01.setTextSize(14f);

            tvcol02.setTextSize(14f);


            tvcol01.setText(col01);

            tvcol02.setText(col02);

            tvcol01.setTextColor(Color.RED);

            tvcol02.setTextColor(Color.RED);

            ImageView img = (ImageView) layout.findViewById(R.id.im_flag_898);

            if (position == escolha) {

                img.setVisibility(View.VISIBLE);

                tvcol01.setTextColor(Color.BLACK);

                tvcol02.setTextColor(Color.BLACK);

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
