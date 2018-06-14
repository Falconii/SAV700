package br.com.brotolegal.sav700;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.ReceberDAO;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.Contrato;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.Receber;

public class Receber_View_Activity extends AppCompatActivity {

    DecimalFormat format_02 = new DecimalFormat(",##0.00");

    private Toolbar toolbar;
    private String CODIGO = "";
    private String LOJA   = "";
    private String FILIAL  = "";
    private String PREFIXO = "";
    private String NUM     = "";


    private ListView lvReceber;

    private List<Object> lsReceber;

    private Adapter adapter;

    private Spinner spFiltro ;

    private List<String[]>  lsFiltro ;

    private defaultAdapter filtroAdapter;

    private SomaTitulo somaTitulo;

    private int filtro = 0;

    private TextView txt_flag_verde_f010;

    private TextView txt_flag_amarelo_f010;

    private TextView txt_flag_vermelho_f010;

    private TextView txt_flag_preto_f010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber__view_);

        toolbar = (Toolbar) findViewById(R.id.tb_receber);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.inflateMenu(R.menu.menu_financeiro);


        Intent i = getIntent();

        if (i != null) {

            Bundle params = i.getExtras();

            CODIGO = params.getString("CODIGO","");
            LOJA   = params.getString("LOJA","");
            FILIAL = params.getString("FILIAL","");
            PREFIXO = params.getString("PREFIXO","");
            NUM     = params.getString("NUM","");

        }

        if (!NUM.isEmpty()){

            toolbar.setSubtitle("Duplicatas Referentes a Nota: "+FILIAL+" "+PREFIXO+" "+NUM);

        } else {

            try {

                ClienteDAO dao = new ClienteDAO();

                dao.open();

                Cliente cliente = dao.seek(new String[]{CODIGO, LOJA});

                if (cliente == null){

                    Toast.makeText(this, "Cliente Não Encontrado No Cadastro !", Toast.LENGTH_SHORT).show();

                    finish();

                }

                toolbar.setSubtitle("Cliente: "+cliente.getCODIGO()+"-"+cliente.getLOJA()+" "+cliente.getRAZAO());

            } catch (Exception e){

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                finish();

            }
        }

        txt_flag_verde_f010    = (TextView) findViewById(R.id.txt_flag_verde_f010);

        txt_flag_amarelo_f010  = (TextView) findViewById(R.id.txt_flag_amarelo_f010);

        txt_flag_vermelho_f010 = (TextView) findViewById(R.id.txt_flag_vermelho_f010);

        txt_flag_preto_f010    = (TextView) findViewById(R.id.txt_flag_preto_f010);

        lvReceber              = (ListView) findViewById(R.id.lvreceber);

        spFiltro               = (Spinner)  findViewById(R.id.sp_receber_f010);

        lsFiltro = new ArrayList<>();

        lsFiltro.add(new String[]{"0", "TODAS"});
        lsFiltro.add(new String[]{"1", "A VENCER"});
        lsFiltro.add(new String[]{"2", "EM ALERTA"});
        lsFiltro.add(new String[]{"3", "ATRASADAS"});
        lsFiltro.add(new String[]{"4", "EM CARTÓRIO"});

        filtroAdapter = new defaultAdapter(getBaseContext(), R.layout.choice_default_row, lsFiltro,"Filtro:");

        spFiltro.setAdapter(filtroAdapter);

        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                filtroAdapter.setEscolha(position);

                Object lixo = spFiltro.getSelectedItem();

                filtro = Integer.valueOf(((String[]) lixo)[0]);

                adapter.filtrar();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spFiltro.setSelection(0);

        somaTitulo = new SomaTitulo();

        try{

            loadReceber();

        } catch (Exception e){

            Toast.makeText(getBaseContext(),"Falha Na Carga Do Receber/n"+e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_financeiro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.menu_financeiro_cancelar:

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onDestroy() {

        super.onDestroy();

        lsReceber = new ArrayList<Object>();

    }

    private  void loadReceber() throws Exception {

        lsReceber = new ArrayList<>();

        lsReceber.add("CABEC");

        ReceberDAO receberDAO = new ReceberDAO();

        receberDAO.open();

        if (!NUM.isEmpty()) {
            lsReceber.addAll(receberDAO.getAllByDoc(FILIAL, PREFIXO, NUM));
        }
        else {
            lsReceber.addAll(receberDAO.getAllByCodigo(CODIGO, LOJA));
        }
        receberDAO.close();


        if (lsReceber.size() == 1){

            lsReceber.add(new NoData("Nenhum Título Encontrado !!"));

        } else {

            for(int x = 1; x<lsReceber.size(); x++){

               somaTitulo.soma(((Receber) lsReceber.get(x)).getATRASO(),((Receber) lsReceber.get(x)).getSALDO());

            }

        }


        txt_flag_verde_f010.setText(format_02.format(somaTitulo.getVerde()));

        txt_flag_amarelo_f010.setText(format_02.format(somaTitulo.getAmarelo()));

        txt_flag_vermelho_f010.setText(format_02.format(somaTitulo.getVermelho()));

        txt_flag_preto_f010.setText(format_02.format(somaTitulo.getPreto()));

        adapter = new Adapter(getBaseContext(),lsReceber);

        lvReceber.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }


    //INNER CLASS


    private class SomaTitulo {

        private Float verde;
        private Float amarelo;
        private Float vermelho;
        private Float preto;

        public SomaTitulo() {

            reset();


        }

        public void reset(){

            verde    = 0f;
            amarelo  = 0f;
            vermelho = 0f;
            preto    = 0f;

        }


        public void soma(Integer atraso, Float valor){

            if (atraso <= 0){

                verde += valor;

                return;


            }


            if (atraso > 0 && atraso <= 5){

                amarelo += valor;

                return;


            }



            if (atraso > 5){

                vermelho += valor;

                return;


            }



        }

        public Float getVerde() {
            return verde;
        }

        public void setVerde(Float verde) {
            this.verde = verde;
        }

        public Float getAmarelo() {
            return amarelo;
        }

        public void setAmarelo(Float amarelo) {
            this.amarelo = amarelo;
        }

        public Float getVermelho() {
            return vermelho;
        }

        public void setVermelho(Float vermelho) {
            this.vermelho = vermelho;
        }

        public Float getPreto() {
            return preto;
        }

        public void setPreto(Float preto) {
            this.preto = preto;
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

            String opcao =  lista.get(position)[0];

            String obj = lista.get(position)[1];

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            tvlabel.setText(this.label);

            if (this.label.isEmpty()){

                tvlabel.setVisibility(View.GONE);

            }

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setBackground(null);

            tvOpcao.setText(obj);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            switch (opcao.charAt(0)) {

                case '1':
                    img.setImageResource(R.drawable.ic_action_flag_verde);
                    break;
                case '2':
                    img.setImageResource(R.drawable.ic_action_flag_amarela);
                    break;
                case '3':
                    img.setImageResource(R.drawable.ic_action_flag_vermelha);
                    break;
                case '4':
                    img.setImageResource(R.drawable.ic_action_flag_preta);
                    break;
                default:
                    img.setVisibility(View.GONE);
                    break;

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
        Context context;

        final int ITEM_VIEW_CABEC   = 0;
        final int ITEM_VIEW_DETALHE = 1;
        final int ITEM_VIEW_NO_DATA = 2;
        final int ITEM_VIEW_COUNT   = 3;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            if (filtro == 0) {

                this.lsObjetos = pObjects;

            } else {

                this.lsObjetos = filtro();

            }

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private List<Object> filtro(){


            List<Object> retorno = new ArrayList<>();

            for(Object obj : lsReceber) {

                if (obj instanceof  String){


                    retorno.add(obj);

                    continue;

                }

                if (filtro == 1 &&  (((Receber) obj).getATRASO() <= 0)) { //verde

                    retorno.add(obj);

                    continue;

                }

                if (filtro == 2 &&  (  (((Receber) obj).getATRASO() > 0) &&  ((Receber) obj).getATRASO() <= 5 )) { //amarelo

                    retorno.add(obj);

                    continue;

                }

                if (filtro == 3  && (((Receber) obj).getATRASO() > 0)) { //vermelho

                    retorno.add(obj);

                    continue;

                }

                if (filtro == 4) { //preto


                }

            }



            if (retorno.size() == 1){

                retorno.add(new NoData("Nehum Título Encontrado Para Este Filtro"));

            }
            return retorno;

        }


        public void filtrar(){


            if (filtro == 0) {

                this.lsObjetos = lsReceber;

            } else {

                this.lsObjetos = filtro();

            }

            notifyDataSetChanged();

        }
        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Contrato) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Itens: " + String.valueOf(qtd);

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

            if (lsObjetos.get(position) instanceof Receber) {

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

                            convertView = inflater.inflate(R.layout.receber_row, null);

                            break;


                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {


                        break;
                    }

                    case ITEM_VIEW_DETALHE: {

                        final Receber obj = (Receber) lsObjetos.get(pos);

                        ImageView flag              = (ImageView) convertView.findViewById(R.id.flag_77);

                        TextView lbl_titulo_077     = (TextView) convertView.findViewById(R.id.lbl_titulo_077);

                        TextView txt_codigo_077     = (TextView) convertView.findViewById(R.id.txt_codigo_077);

                        TextView txt_cliente_077    = (TextView) convertView.findViewById(R.id.txt_cliente_077);

                        TextView txt_emissao_077    = (TextView) convertView.findViewById(R.id.txt_emissao_077);

                        TextView txt_vencimento_077 = (TextView) convertView.findViewById(R.id.txt_vencimento_077);

                        TextView txt_saldo_077      = (TextView) convertView.findViewById(R.id.txt_saldo_077);

                        TextView txt_atraso_077     = (TextView) convertView.findViewById(R.id.txt_atraso_077);

                        TextView txt_banco_077      = (TextView) convertView.findViewById(R.id.txt_banco_077);

                        TextView txt_ld_077         = (TextView) convertView.findViewById(R.id.txt_ld_077);



                        if (obj.getATRASO() <= 0){

                            flag.setImageResource(R.drawable.ic_action_flag_verde);


                        }


                        if (obj.getATRASO() >=  1 && obj.getATRASO() <= 5){

                            flag.setImageResource(R.drawable.ic_action_flag_amarela);


                        }



                        if (obj.getATRASO() > 5){

                            flag.setImageResource(R.drawable.ic_action_flag_vermelha);

                        }





                        lbl_titulo_077.setText(obj.getPREFIXO()+ " " + obj.getNUM() + "/" + obj.getPARCELA());

                        txt_codigo_077.setText(obj.getCLIENTE()+obj.getLOJA());

                        txt_cliente_077.setText(obj.getRAZAO());

                        txt_emissao_077.setText(App.aaaammddToddmmaaaa(obj.getEMISSAO()));

                        txt_vencimento_077.setText(App.aaaammddToddmmaaaa(obj.getVENCTO()));

                        txt_saldo_077.setText(format_02.format(obj.getSALDO()));

                        txt_atraso_077.setText(obj.getATRASO().toString());

                        txt_banco_077.setText(obj.getBANCO()+ " " + obj.getNROBANCARIO());

                        txt_ld_077.setText(obj.getLINHADIGITAVEL());


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
