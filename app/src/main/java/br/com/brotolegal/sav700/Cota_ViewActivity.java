package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.CotaDAO;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.Cota;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.regrasdenegocio.ParamPolitica;
import br.com.brotolegal.savdatabase.regrasdenegocio.ParametroCota;
import br.com.brotolegal.savdatabase.regrasdenegocio.ResumoAgendamento;

public class Cota_ViewActivity extends AppCompatActivity {

    private ParametroCota parametros;

    private Toolbar toolbar;

    private  String cliente   = "";
    private  String loja      = "";
    private  String rede      = "";
    private  String canal     = "";
    private  String regiao    = "";
    private  String smarca    = "";
    private  String produto   = "";

    private  String ENTREGA           = "";
    private  Float  DESCONTROCONTRATO = 0f;
    private  String TAXAFINANCEIRA    = "0";
    private  Float  PERCPOL           = 0f;

    private  String RETCOTA           = "";
    private  Float  RETPRECO          = 0f;
    private  Float  CONVERSAO         = 0f;

    private Adapter adapter;

    private List<Object> lista;

    private ListView lv ;

    private int Result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cota__view);

        toolbar = (Toolbar) findViewById(R.id.tb_cota_487);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("COTAS DISPONÍVEIS PARA ESTE PRODUTO");
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.menu_negociacao);


        try {
            parametros = new ParametroCota();

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                cliente = params.getString("CLIENTE", "");

                loja = params.getString("LOJA"      , "");

                rede = params.getString("REDE"      , "");

                canal = params.getString("CANAL"    , "");

                regiao = params.getString("REGIAO"  , "");

                smarca = params.getString("SMARCA"  , "");

                produto = params.getString("PRODUTO", "");

                ENTREGA            = params.getString("ENTREGA", "");
                DESCONTROCONTRATO  = params.getFloat("DESCONTROCONTRATO",0f);
                TAXAFINANCEIRA     = params.getString("TAXAFINANCEIRA", "0");
                PERCPOL            = params.getFloat("PERCPOL",0f);
                CONVERSAO          = params.getFloat("CONVERSAO",0f);



            }

            lv = (ListView) findViewById(R.id.lvCota_487);

            loadCotas();

        } catch (Exception e){

             toast(e.getMessage());

             finish();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cota_viewl, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:

                Result = 0;

                finish();

                break;

            case R.id.ac_cota_cancelar: {

                Result = 0;

                finish();

                break;

            }

        }

         return super.onOptionsItemSelected(item);
    }

    @Override
    public  void finish(){

        lista = new ArrayList<Object>();

        Intent data = new Intent();

        data.putExtra("COTA",RETCOTA);

        data.putExtra("PRECO",RETPRECO);

        setResult(Result, data);

        super.finish();

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

        lista = new ArrayList<Object>();

    }

    private void toast(String mensagem){

        Toast.makeText(this,mensagem,Toast.LENGTH_LONG).show();

    }


    private void loadCotas(){

        try {

            lista = new ArrayList<>();

            lista.add("CABEC");

            CotaDAO dao = new CotaDAO();

            dao.open();

            parametros = new ParametroCota(cliente,loja,rede,canal,regiao,smarca,produto);

            lista.addAll(dao.getCota(parametros));

            dao.close();

            if (lista.size() == 1) {

                lista.add(new NoData("Nenhum Pedido Encontrado !!"));

            }

            for(Object obj :  lista){

                if (obj instanceof Cota){

                    ((Cota) obj).CalculoFinal(DESCONTROCONTRATO,TAXAFINANCEIRA,CONVERSAO);

                }

            }

            adapter = new Adapter(Cota_ViewActivity.this, lista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e){

            toast(e.getMessage());

        }


    }

    protected class OnDoubleClickListener implements View.OnClickListener {

        private boolean nonDoubleClick = true;
        private long firstClickTime = 0L;
        private final int DOUBLE_CLICK_TIMEOUT = 10000; //ViewConfiguration.getDoubleTapTimeout();
        private Cota obj;


        public OnDoubleClickListener(Cota obj){

            this.obj = obj;

        }

        @Override
        public void onClick(View view) {
            // @TODO check and catch the double click event
            synchronized(OnDoubleClickListener.this) {
                if(firstClickTime == 0) {
                    firstClickTime = SystemClock.elapsedRealtime();
                    nonDoubleClick = true;
                } else {
                    long deltaTime = SystemClock.elapsedRealtime() - firstClickTime;
                    firstClickTime = 0;
                    if(deltaTime < DOUBLE_CLICK_TIMEOUT) {
                        nonDoubleClick = false;
                        this.onItemDoubleClick();
                        return;
                    }
                }


                view.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if(nonDoubleClick) {
                            // @TODO add your logic for single click event
                        }
                    }

                }, DOUBLE_CLICK_TIMEOUT);
            }

        }

        public void onItemDoubleClick() {


            if (adapter.CompararDatas(App.aaaammddToddmmaaaa(obj.getDTENTINICIAL()),App.aaaammddToddmmaaaa(obj.getDTENTFINAL()),ENTREGA)) {

                Result    = 1;

                RETCOTA   = obj.getCODIGO();

                RETPRECO  = obj.get_PRECOFINAL();

                finish();

            } else {

                toast("Cota Inválida Para Esta Data Entrega!");

            }


        }


    }

    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        Context context;

        final int ITEM_VIEW_CABEC       = 0;
        final int ITEM_VIEW_DETALHE     = 1;
        final int ITEM_VIEW_NO_DATA     = 2;
        final int ITEM_VIEW_COUNT       = 3;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos = pObjects;
            this.context   = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Cota) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total De Itens: " + String.valueOf(qtd);

            return retorno;
        }

        public  void refresh(PedidoCabMb obj,int pos){

            this.lsObjetos.set(pos,obj);

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

            if (lsObjetos.get(position) instanceof Cota) {

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

                            convertView = inflater.inflate(R.layout.cota_row, null);

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

                        final Cota obj = (Cota) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.txt_view_017);

                        view.setOnClickListener( new OnDoubleClickListener(obj));

                        TextView txt_produto_017  =  (TextView) convertView.findViewById(R.id.txt_produto_017);

                        TextView txt_saldo_017    =  (TextView) convertView.findViewById(R.id.txt_saldo_017);

                        TextView txt_contrato_017 =  (TextView) convertView.findViewById(R.id.txt_contrato_017);

                        TextView txt_taxal_017    =  (TextView) convertView.findViewById(R.id.txt_taxal_017);

                        TextView txt_validade_017 =  (TextView)convertView.findViewById(R.id.txt_validade_017);

                        TextView txt_preco_017    =  (TextView)convertView.findViewById(R.id.txt_preco_017);

                        TextView txt_obs_017      =  (TextView)convertView.findViewById(R.id.txt_obs_017);

                        txt_produto_017.setText("COTA: "+obj.getCODIGO() + "-" + obj.getDESCRICAO().trim());

                        if (obj.getUTILCOTA().equals("S")){

                            txt_saldo_017.setText(format_02.format(obj.getSLDCOTA()));

                        } else {

                            txt_saldo_017.setText("Não Usado");

                        }

                        txt_contrato_017.setText(App.TabletSIMNAO(obj.getUTILCONTR()));

                        txt_taxal_017.setText(App.TabletSIMNAO(obj.getUTILTX()));

                        txt_validade_017.setText(App.aaaammddToddmmaaaa(obj.getDTENTINICIAL())+" ATÉ " + App.aaaammddToddmmaaaa(obj.getDTENTFINAL()) );

                        txt_preco_017.setText("COTA R$: " + format_02.format(obj.getPRECO()) + " D.C.: "+format_02.format(DESCONTROCONTRATO)+" TAXA FIN. : "+TAXAFINANCEIRA+" POLITICA+DNA: "+format_02.format(PERCPOL)+"% FINAL R$: " + format_02.format(obj.get_PRECOFINAL()));

                        txt_obs_017.setText((CompararDatas(App.aaaammddToddmmaaaa(obj.getDTENTINICIAL()),App.aaaammddToddmmaaaa(obj.getDTENTFINAL()),ENTREGA) ? "COTAÇÃO VÁLIDA !!" : "COTAÇÃO INVÁLIDA. PARA ESTA DATA DE ENTREGA!"));

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


        public Boolean CompararDatas(String DataInicial,String DataFinal, String Entrega){

            Boolean retorno = false;

            try {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date dtinicial = sdf.parse(DataInicial);
                Date dtfinal   = sdf.parse(DataFinal);
                Date dtentrega = sdf.parse(Entrega);

                if( (dtentrega.compareTo(dtinicial) >= 0) && (dtentrega.compareTo(dtfinal) <= 0) ){

                    retorno = true;

                } else {

                    retorno = false;

                }


            } catch (Exception e){

                retorno = false;

            }


            return retorno;
        }

        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }

    }

}
