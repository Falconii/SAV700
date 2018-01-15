package br.com.brotolegal.sav700.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.sav700.AgendamentoActivity;
import br.com.brotolegal.sav700.ClienteViewAtivity;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.NoData;


public class ALC_Frag extends Fragment {

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

    defaultAdapter periodoadapter;

    Adapter adapter;

    int  POS = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_planejamento, container, false);

        spCidade =   (Spinner) rootView.findViewById(R.id.sp_cidade_334);

        spRede   =   (Spinner) rootView.findViewById(R.id.sp_rede_334);

        spOrdem  =   (Spinner)  rootView.findViewById(R.id.sp_ordem_334);

        lv=(ListView) rootView.findViewById(R.id.lvClientesPlanejamento);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Planejamento De Rotas");

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

                            convertView = inflater.inflate(R.layout.cliat_planejamento_row, null);

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

                        ImageButton bt_cadastro    = (ImageButton) convertView.findViewById(R.id.bt_enviar_111);

                        ImageButton bt_alteracao = (ImageButton) convertView.findViewById(R.id.bt_alteracao_400);

                        ImageButton bt_agendamento = (ImageButton) convertView.findViewById(R.id.bt_agendamento_400);

                        TextView txt_database_400  = (TextView) convertView.findViewById(R.id.txt_database_400);

                        TextView txt_periodo_400   = (TextView) convertView.findViewById(R.id.txt_periodo_400);

                        TextView txt_codigo_400 = (TextView) convertView.findViewById(R.id.txt_codigo_400);

                        txt_database_400.setText("Data Base : "+ App.aaaammddToddmmaaaa(obj.getROTDTBA())+" Horário: "+obj.getROTHORA()+"\n"+obj.getROTOBSVI());

                        txt_periodo_400.setText("Periodicidade:\n"+obj.get_PERIODICIDADE());

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


                        if (obj.getROTPERI().trim().isEmpty() || obj.getROTPERI().equals("N")){


                            bt_agendamento.setVisibility(View.INVISIBLE);

                        } else {

                            bt_agendamento.setVisibility(View.VISIBLE);

                            bt_agendamento.setOnClickListener(new View.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(View v) {

                                                                      Intent intent=new Intent(context, AgendamentoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                      Bundle params = new Bundle();
                                                                      params.putString("CODIGO"  , obj.getCODIGO());
                                                                      params.putString("LOJA"    , obj.getLOJA());
                                                                      intent.putExtras(params);
                                                                      context.startActivity(intent);

                                                                  }
                                                              }
                            );
                        }
                        bt_alteracao.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


							    /* botao de agendamento */

                                final Dialog dialog = new Dialog(context);

                                dialog.setContentView(R.layout.getagendamento);

                                //define o título do Dialog

                                dialog.setTitle("PLANEJAMENTO!");

                                //Carrega as Possiveis Data

                                final EditText data  = (EditText) dialog.findViewById(R.id.edit_data_116);

                                data.setRawInputType(InputType.TYPE_CLASS_PHONE);

                                data.addTextChangedListener(Mask.insert("##/##/####", data));

                                data.setHint("dd/mm/yyyy");

                                if (obj.getROTDTBA().trim().isEmpty()){

                                    data.setText("");

                                } else {

                                    data.setText(App.aaaammddToddmmaaaa(obj.getROTDTBA()));

                                }

                                final EditText hora      = (EditText) dialog.findViewById(R.id.edit_hora_116);

                                hora.setText(obj.getROTHORA().trim());

                                hora.setRawInputType(InputType.TYPE_CLASS_PHONE);

                                hora.addTextChangedListener(Mask.insert("##:##", hora));

                                hora.setHint("HH:MM");

                                hora.setText(obj.getROTHORA().trim());

                                try {

                                    hora.setSelection(hora.getText().toString().length());

                                }

                                catch (Exception e) {


                                }

                                final Spinner spPeriodo = (Spinner) dialog.findViewById(R.id.edit_periodo_116);

                                int index = 0;

                                for(String[] op : obj.getlsPeriodo()){

                                    if (op[0].equals(obj.getROTPERI())){

                                        break;

                                    }

                                    index++;
                                }

                                if (index > obj.getlsPeriodo().size()-1){

                                    index =  obj.getlsPeriodo().size()-1;

                                }


                                periodoadapter = new defaultAdapter(getContext(), R.layout.choice_default_row, obj.getlsPeriodo(),"Periodo:");

                                spPeriodo.setAdapter(periodoadapter);

                                spPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        periodoadapter.setEscolha(position);

                                        Object lixo = spPeriodo.getSelectedItem();

                                        obj.setROTPERI(((String[]) lixo)[0]);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {


                                    }
                                });

                                spPeriodo.setSelection(index);

                                final EditText observacao = (EditText) dialog.findViewById(R.id.edit_observacao_116);

                                observacao.setText(obj.getROTOBSVI().trim());


                                final Button confirmar    = (Button) dialog.findViewById(R.id.bt_confirma_116);
                                final Button cancelar     = (Button) dialog.findViewById(R.id.bt_cancela_116);

                                cancelar.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {
                                        dialog.dismiss();

                                    }
                                });

                                confirmar.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {

                                        try{

                                            SimpleDateFormat horario = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"));

                                            horario.setLenient(false);

                                            horario.parse(hora.getText().toString());


                                        } catch (Exception e){


                                            Toast.makeText(context,"Horário Inválido !!",Toast.LENGTH_LONG).show();

                                            return;

                                        }


                                        try {

                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",new Locale("pt", "BR"));

                                            sdf.setLenient(false);

                                            sdf.parse(data.getText().toString());

                                        } catch (java.text.ParseException e) {


                                            Toast.makeText(context,"Data Inválido !!",Toast.LENGTH_LONG).show();


                                            return ;

                                        }


                                        try {

                                            String lixo =   data.getText().toString().replaceAll("[/]", "");
                                            lixo        =   lixo.substring(4, 8) +
                                                    lixo.substring(2, 4) +
                                                    lixo.substring(0, 2);

                                            obj.setROTDTBA(lixo);

                                            obj.setROTHORA(hora.getText().toString());

                                            obj.setROTOBSVI(observacao.getText().toString());

                                            ClienteDAO dao = new ClienteDAO();

                                            dao.open();

                                            dao.updateRota(obj.getCODIGO(),obj.getLOJA(),obj.getROTDTBA(),obj.getROTHORA(),obj.getROTPERI(),obj.getROTOBSVI());

                                            dao.close();

                                            adapter.setCliente(obj);


                                        } catch (Exception e){


                                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();


                                        }

//                                        try
//
//                                        {
//
//                                            obj.setCZZAGEPR(protocolo.getText().toString().replaceAll("\\n|\\r", " "));
//                                            obj.setCZZAGEDT(data.getSelectedItem().toString().substring(6, 10)+data.getSelectedItem().toString().substring(3, 5)+data.getSelectedItem().toString().substring(0, 2));
//                                            obj.setCZZAGEHO(hora.getText().toString());
//
//                                            notifyDataSetChanged();
//
//                                            try{
//
//                                                if ( !(verificaConexao())){
//
//                                                    showToast("Sem Concexão Com A Internet !!!");
//
//                                                    return;
//
//                                                }
//
//                                                if ((dialog != null)){
//
//                                                    if (dialog.isShowing()){
//
//                                                        dialog.dismiss();
//
//                                                    }
//
//                                                }

//						  					WAgenda=new WEBAgenda(mHandler,obj);
//
//						  					WAgenda.start();
//
//                                            }catch (Exception e)
//
//                                            {
//                                                //showToast("Erro: " + e.getMessage());
//                                            }
//
//
//
//                                        } catch (Exception e) {
//
//                                            //showToast(e.getMessage());
//
//                                        }


                                        if ((dialog != null)){

                                            if (dialog.isShowing()){

                                                View view = dialog.getCurrentFocus();
                                                if (view != null) {
                                                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                                }

                                                dialog.dismiss();

                                            }

                                        }


                                    }

                                });

                                dialog.show();

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
