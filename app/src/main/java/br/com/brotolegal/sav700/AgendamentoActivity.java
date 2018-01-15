package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.NoData;

public class AgendamentoActivity extends AppCompatActivity {

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

    String CODIGO = "";

    String LOJA   = "";

    Cliente_fast cliente;


    ImageButton cadastro;

    ImageButton processo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        Intent i = getIntent();

        if (i != null) {

            Bundle params = i.getExtras();

            CODIGO    = params.getString("CODIGO");
            LOJA      = params.getString("LOJA");

            try {

                ClienteDAO dao = new ClienteDAO();

                dao.open();

                cliente = dao.seek_fast(CODIGO,LOJA,"");

                dao.close();

                if (cliente == null){

                    toast("Cliente Não Encontrado !!");

                    finish();

                }



            } catch (Exception e){


                toast(e.getMessage());

                finish();

            }

        } else {

            CODIGO    = "";
            LOJA      = "";

            finish();

        }

        lv = (ListView) findViewById(R.id.lvAgendasPlanejamento);

        populaCliente();

        ImageButton bt_cadastro    = (ImageButton) findViewById(R.id.bt_cadastro_404);

        bt_cadastro.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               Intent intent=new Intent(getBaseContext(),ClienteViewAtivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                               Bundle params = new Bundle();
                                               params.putString("CODIGO"  , CODIGO);
                                               params.putString("LOJA"    , LOJA);
                                               intent.putExtras(params);
                                               startActivity(intent);

                                           }
                                       }
        );

        ImageButton bt_processo = (ImageButton) findViewById(R.id.bt_processo_404);

        bt_processo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /* botao de agendamento */

                Date hoje = new Date();

                SimpleDateFormat fhoje = new SimpleDateFormat("dd/MM/yyyy",new Locale("pt", "BR"));

                fhoje.setLenient(false);



                final Dialog dialog = new Dialog(AgendamentoActivity.this);

                dialog.setContentView(R.layout.dlgagendamento);

                //define o título do Dialog

                dialog.setTitle("Processamento De Novos Agendamentos!");

                //Carrega as Possiveis Data

                final EditText data  = (EditText) dialog.findViewById(R.id.edit_data_121);

                data.setRawInputType(InputType.TYPE_CLASS_PHONE);

                data.addTextChangedListener(Mask.insert("##/##/####", data));

                data.setHint("dd/mm/yyyy");

                data.setText(fhoje.format(hoje));

                final EditText hora      = (EditText) dialog.findViewById(R.id.edit_hora_121);

                hora.setText(cliente.getROTHORA().trim());

                hora.setRawInputType(InputType.TYPE_CLASS_PHONE);

                hora.addTextChangedListener(Mask.insert("##:##", hora));

                hora.setHint("HH:MM");

                hora.setText(cliente.getROTHORA().trim());

                try {

                    hora.setSelection(hora.getText().toString().length());

                }

                catch (Exception e) {


                }

                final Spinner spPeriodo = (Spinner) dialog.findViewById(R.id.edit_periodo_121);

                int index = 0;

                for(String[] op : cliente.getlsPeriodo()){

                    if (op[0].equals(cliente.getROTPERI())){

                        break;

                    }

                    index++;
                }

                if (index > cliente.getlsPeriodo().size()-1){

                    index =  cliente.getlsPeriodo().size()-1;

                }


                periodoadapter = new defaultAdapter(getBaseContext(), R.layout.choice_default_row, cliente.getlsPeriodo(),"Periodo:");

                spPeriodo.setAdapter(periodoadapter);

                spPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        periodoadapter.setEscolha(position);

                        Object lixo = spPeriodo.getSelectedItem();

                        cliente.setROTPERI(((String[]) lixo)[0]);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

                spPeriodo.setSelection(index);

                final EditText observacao = (EditText) dialog.findViewById(R.id.edit_observacao_121);

                observacao.setText(cliente.getROTOBSVI().trim());


                final Button confirmar    = (Button) dialog.findViewById(R.id.bt_confirma_121);
                final Button cancelar     = (Button) dialog.findViewById(R.id.bt_cancela_121);

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


                            Toast.makeText(getBaseContext(),"Horário Inválido !!",Toast.LENGTH_LONG).show();

                            return;

                        }


                        try {

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",new Locale("pt", "BR"));

                            sdf.setLenient(false);

                            sdf.parse(data.getText().toString());

                        } catch (java.text.ParseException e) {


                            Toast.makeText(getBaseContext(),"Data Inválido !!",Toast.LENGTH_LONG).show();


                            return ;

                        }


                        try {

                            String lixo =   data.getText().toString().replaceAll("[/]", "");
                            lixo        =   lixo.substring(4, 8) +
                                    lixo.substring(2, 4) +
                                    lixo.substring(0, 2);

                            cliente.setROTDTBA(lixo);

                            cliente.setROTHORA(hora.getText().toString());

                            cliente.setROTOBSVI(observacao.getText().toString());

                            ClienteDAO dao = new ClienteDAO();

                            dao.open();

                            dao.updateRota(cliente.getCODIGO(),cliente.getLOJA(),cliente.getROTDTBA(),cliente.getROTHORA(),cliente.getROTPERI(),cliente.getROTOBSVI());

                            dao.close();

                            populaCliente();

                            AgendamentoDAO ageDAO = new AgendamentoDAO();

                            ageDAO.open();

                            //ageDAO.processa_ag(cliente.getCODIGO(),cliente.getLOJA(), App.aaaammddToddmmaaaa(cliente.getROTDTBA()),"31/12/2017",cliente.getROTHORA(),cliente.getROTPERI(),"","","");

                            ageDAO.close();

                            loadAgendas();


                        } catch (Exception e){


                            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();


                        }



                        if ((dialog != null)){

                            if (dialog.isShowing()){

                                View view = dialog.getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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




        loadAgendas();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        lsLista = new ArrayList<Object>();

    }


    private void populaCliente(){


        TextView txt_database_404  = (TextView) findViewById(R.id.txt_database_404);

        TextView txt_periodo_404   = (TextView) findViewById(R.id.txt_periodo_404);

        TextView txt_codigo_404 = (TextView) findViewById(R.id.txt_codigo_404);

        txt_database_404.setText("Data Base : "+ App.aaaammddToddmmaaaa(cliente.getROTDTBA())+" Horário: "+cliente.getROTHORA()+"\n"+cliente.getROTOBSVI());

        txt_periodo_404.setText("Periodicidade:\n"+cliente.get_PERIODICIDADE());

        txt_codigo_404.setText("Código Protheus: " + cliente.getCODIGO()+"-"+cliente.getLOJA());

        TextView txt_situacao_404 = (TextView) findViewById(R.id.txt_situacao_404);

        txt_situacao_404.setText("Situação Do Cliente: "+cliente.getSITUACAO());

        if (!cliente.getSITUACAO().trim().equals("ATIVO")){

            txt_situacao_404.setTextColor(getResources().getColor(R.color.red));

        } else {

            txt_situacao_404.setTextColor(getResources().getColor(R.color.green));

        }


        TextView txt_cliente_404 = (TextView) findViewById(R.id.txt_cliente_404);

        txt_cliente_404.setText("Cliente: "+cliente.getRAZAO());

        TextView txt_cnpj_404 = (TextView) findViewById(R.id.txt_cnpj_404);

        txt_cnpj_404.setText("CNPJ/CPF: "+ App.cnpj_cpf(cliente.getCNPJ()));

        TextView txt_IE_404   = (TextView) findViewById(R.id.txt_ie_404);

        txt_IE_404.setText("I.E.: "+cliente.getIE());

        TextView txt_cidade_404   = (TextView) findViewById(R.id.txt_cidade_404);

        txt_cidade_404.setText("Cidade: "+cliente.getCIDADE());

        TextView txt_telefone_404   = (TextView) findViewById(R.id.txt_telefone_404);

        txt_telefone_404.setText("Tel.: "+"("+cliente.getDDD()+")"+cliente.getTELEFONE());

        TextView txt_desc_rede_404 = (TextView) findViewById(R.id.txt_desc_rede_404);

        txt_desc_rede_404.setText("Rede: "+cliente.getREDE()+"-"+cliente.get_REDE());



    }

    private void toast(String mensagem){


        Toast.makeText(getBaseContext(),mensagem,Toast.LENGTH_LONG).show();


    }


    private void loadAgendas(){

        try {


            lsLista = new ArrayList<>();

            lsLista.add("CABEC");

            AgendamentoDAO dao = new AgendamentoDAO();

            dao.open();

            lsLista.addAll(dao.getAllByCliente(CODIGO,LOJA));

            dao.close();

            if (lsLista.size() == 1){


                lsLista.add(new NoData("Nehum AgendamentoController Encontrado !"));



            }

            adapter = new Adapter(getBaseContext(),lsLista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e){


            toast(e.getLocalizedMessage());

        }

    }




    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");

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

            this.lsObjetos = pObjects;

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        public void setCidade(String filtro){

            _Cidade = filtro;

        }

        public void setRede(String filtro){

            _Rede = filtro;

        }



        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Agendamento) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Agendamentos: " + String.valueOf(qtd);

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

            if (lsObjetos.get(position) instanceof Agendamento) {

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

                            convertView = inflater.inflate(R.layout.agenda_planejamento_row, null);

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

                        final Agendamento obj = (Agendamento) lsObjetos.get(pos);


                        TextView  txt_data_402      = (TextView) convertView.findViewById(R.id.txt_data_402);

                        txt_data_402.setText(App.aaaammddToddmmaaaa(obj.getDATA()));

                        TextView  txt_hora_402      = (TextView) convertView.findViewById(R.id.txt_hora_402);

                        txt_hora_402.setText("Horário:"+obj.getHORA());

                        TextView  txt_situacao_402  = (TextView) convertView.findViewById(R.id.txt_situacao_402);

                        txt_situacao_402.setText("Situação:"+obj.getSITUACAO());

                        TextView  txt_pedido_402    = (TextView) convertView.findViewById(R.id.txt_pedido_402);

                        txt_pedido_402.setText("Pedido: "+obj.getMOBILE());

                        TextView  txt_motivo_402    = (TextView) convertView.findViewById(R.id.txt_motivo_402);

                        //txt_motivo_402.setText("Motivo: "+obj.getMOTIVO());

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

