package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;
import br.com.brotolegal.savdatabase.metas.Anterior;
import br.com.brotolegal.savdatabase.metas.MetaVend;
import br.com.brotolegal.savdatabase.metas.Paginas;
import br.com.brotolegal.savdatabase.metas.RelMetas;

public class ShowMetasActivity extends AppCompatActivity {

    Toolbar toolbar;

    private List<Config> conexoes;

    private int    IndiceConexao = 0;


    private Config config;

    private ListView list;
    private List<Object> lsLista  = new ArrayList<Object>();
    private List<Object> lsSaldos = new ArrayList<Object>();
    private RelMetas obj;

    private Dialog dialog;
    private Adapter adapter;
    private String categorias = "'3.01','3.02','3.03','3.13','3.14','3.16','3.17','3.18','3.19','3.20','3.21','3.22'";
    private String movimento  = "H";
    private String codconsulta;
    private String claconsulta;
    private Paginas paginas = new Paginas();
    private callBack callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_metas);

        try {

            toolbar = (Toolbar) findViewById(R.id.tb_showmetas_567);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Metas");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.inflateMenu(R.menu.menu_show_metas);

            try{
                if( Build.VERSION.SDK_INT >= 9){
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
            }catch (Exception e)
            {
                showToast("Erro: " + e.getMessage());
            }

            try {

                ConfigDAO dao = new ConfigDAO();

                dao.open();

                config = dao.seek(new String[]{"000"});

                conexoes = dao.getConexoes();

                br.com.brotolegal.savdatabase.entities.Config padrao = dao.seekByDescricao( new String[]{config.getDESCRICAO()});

                if (padrao != null) {

                    IndiceConexao = 0;

                    for( int x = 0; x < conexoes.size() ; x++){

                        if (padrao.getDESCRICAO().equals(conexoes.get(x).getDESCRICAO())) {

                            IndiceConexao = x;

                            break;
                        }
                    }

                } else {

                    IndiceConexao = 0;

                }

                dao.close();

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                finish();

            }


            codconsulta = App.user.getCODVEN();
            claconsulta = App.user.getCLASS();


	        /* Pagina Inicial */

            paginas.add(new Anterior(movimento, categorias, codconsulta, claconsulta,App.user.getCLASS()));

            list       = (ListView) findViewById(R.id.lvRelMetas);

            atualizar();




        } catch (Exception e){


            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_metas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.ac_metas_cancelar:

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
        lsLista = new ArrayList<Object>();
        lsSaldos = new ArrayList<Object>();
    }

    private void atualizar() {

        String clasRet = "";

        try{

            callback = new callBack();

            lsLista = new ArrayList<Object>();

            lsSaldos = new ArrayList<Object>();

            AccessWebInfo acessoWeb = new AccessWebInfo(mHandler, getBaseContext(), App.user, "GETMETAS2", "GETMETAS2",AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO , AccessWebInfo.PROCESSO_CUSTOM, config, callback,-1);

            acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

            acessoWeb.addParam("CPASSUSER",App.user.getSENHA());

            acessoWeb.addParam("CCLASS"   , App.user.getCLASS().trim());


            if (movimento.equals("B")){

                paginas.goBack();

                Anterior anterior = paginas.getAtual();

                acessoWeb.addParam("CCODIGO"     ,anterior.getCODCONSULTA());
                acessoWeb.addParam("CCLASSPSQ"   ,anterior.getCLACONSULTA());
                acessoWeb.addParam("CGRUPOS"     ,anterior.getCATEGORIAS());
                acessoWeb.addParam("CCLASSRET"   ,anterior.getCLARETORNO());
                movimento = anterior.getMOVIMENTO();
                clasRet   = anterior.getCLARETORNO();

            } else {

                acessoWeb.addParam("CCODIGO"     , codconsulta);
                acessoWeb.addParam("CCLASSPSQ"   , claconsulta);
                acessoWeb.addParam("CGRUPOS"     , categorias  );

                if (movimento.equals("H")) {

                    clasRet = App.user.getCLASS();

                }

                if (movimento.equals("D")) {


                    clasRet = getDowCategoria(claconsulta);

                }

                if (movimento.equals("C")) {

                    clasRet = "C";

                }

                acessoWeb.addParam("CCLASSRET"   , clasRet);

            }

            acessoWeb.addParam("CDATAREF"    , "201705");

            acessoWeb.start();


        }catch (Exception e)

        {
            showToast("Erro: " + e.getMessage());
        }


    }

    private void refresh(){

        adapter = new Adapter(this, lsLista);

        list.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    private void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private String getDowCategoria(String classi){

        String retorno = classi;

        if (classi.equals("D")){

            retorno = "G";
        }

        if (classi.equals("G")){

            retorno = "S";
        }

        if (classi.equals("S")){

            retorno = "V";
        }

        if (classi.equals("R")){

            retorno = "V";

        }


        return retorno;

    }




    private Handler mHandler=new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {


                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(ShowMetasActivity.this, "Baixando A Tabela", "Em Processamento !!!!", false, true);
                    dialog.setCancelable(false);
                    dialog.show();

                }

                if (msg.getData().getString("CERRO").equals("000")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                }

                if (msg.getData().getString("CERRO").equals("010")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    showToast(msg.getData().getString("CMSGERRO"));

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    try {

                        callback.processaArray();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }

            } catch (Exception E) {

                Toast.makeText(ShowMetasActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }

        }

    };

    //Inner Class
    private  class Adapter extends BaseAdapter
    {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");

        private List<Object> lsObjetos;
        private List<Object> lsOfull;

        Context context;
        final int ITEM_VIEW_DETALHE        = 0;
        final int ITEM_VIEW_SEPARADOR      = 1;
        final int ITEM_VIEW_VENDEDOR       = 2;
        final int ITEM_VIEW_COUNT          = 4;


        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsOfull    = pObjects;
            this.context    = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            load();
            AtualizaVendPerc();

        }


        private void load(){

            lsObjetos = new ArrayList<Object>();

            for (Object obj : lsOfull) {


                if (obj instanceof String) {

                    lsObjetos.add(obj);

                }

                if (obj instanceof MetaVend) {

                    MetaVend ob = (MetaVend) obj;

                    lsObjetos.add(obj);

                }


                if (obj instanceof RelMetas) {

                    RelMetas ob = (RelMetas) obj;

                    lsObjetos.add(obj);

                }


            }


        }


        private String Cabec(){

            String retorno = "";


            for (Object obj : lsOfull) {


                if (obj instanceof RelMetas) {

                    RelMetas ob = (RelMetas) obj;

                    retorno = ob.getCDTRECMET();

                    break;

                }


            }


            retorno = "Ultima Atualização: "+ retorno;


            return retorno;
        }



        private void AtualizaVendPerc(){


            for (Object obj : lsObjetos) {


                if (obj instanceof MetaVend) {

                    MetaVend ob = (MetaVend) obj;

                    SomaMetas(ob);

                }


            }


        }


        private void SomaMetas(MetaVend ven){

            Float meta      = 0f;
            Float realizado = 0f;
            Float carteira  = 0f;

            BigDecimal INDICE;

            Float indice;


            for (Object obj : lsObjetos) {


                if (obj instanceof RelMetas ) {

                    RelMetas ob = (RelMetas) obj;

                    if (((RelMetas) obj).getMOVIMENTO().equals("H")){

                        if (((RelMetas) ob).getCODRET().equals(ven.getCODIGO())) {

                            meta = meta + ob.getOBJETIVO();

                            realizado = realizado + ob.getATINGIDO();

                            carteira  = carteira  + ob.getTOTALCAR();

                        }
                    }
                    else {

                        meta = meta + ob.getOBJETIVO();

                        realizado = realizado + ob.getATINGIDO();

                        carteira  = carteira  + ob.getTOTALCAR();

                    }
                }


            }


	    	/* real */

            if (meta > 0){

                indice = (realizado/meta) * 100;

            } else {

                indice = 0f;

            }

            INDICE = new BigDecimal(indice);

            INDICE = INDICE.setScale(2, BigDecimal.ROUND_HALF_UP);

            ven.setPERREAL(Float.parseFloat(INDICE.toString()));

	    	/* carteiras */

            if (meta > 0){

                indice = (carteira/meta) * 100;

            } else {

                indice = 0f;

            }

            INDICE = new BigDecimal(indice);

            INDICE = INDICE.setScale(2, BigDecimal.ROUND_HALF_UP);

            ven.setPERCART(Float.parseFloat(INDICE.toString()));

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

            if (lsObjetos.get(position) instanceof String){

                retorno = ITEM_VIEW_SEPARADOR;
            }


            if (lsObjetos.get(position) instanceof MetaVend){

                retorno = ITEM_VIEW_VENDEDOR;
            }

            if (lsObjetos.get(position) instanceof RelMetas){

                retorno = ITEM_VIEW_DETALHE;

            }

            return retorno;


        }


        @Override
        public boolean isEnabled(int position) {
            boolean retorno = false;
            //Caso o item clicado seja o Separador ou Vendedor, nesse caso
            //não retornamos nada
            //retorno = (getItemViewType(position) == ITEM_VIEW_ESTADO);
            return retorno;
        }

        public void deleteitem(int position) {

            this.lsObjetos.remove(position);
            //Atualizar a lista caso seja adicionado algum item
            notifyDataSetChanged();

            return;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            try {

                final int pos  = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case  ITEM_VIEW_SEPARADOR:

                            convertView = inflater.inflate(R.layout.meta_cab_row,null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.meta_det_row,null);

                            break;


                        case ITEM_VIEW_VENDEDOR:

                            convertView = inflater.inflate(R.layout.meta_vend_row,null);

                            break;


                    }

                }

                switch (type) {

                    case ITEM_VIEW_SEPARADOR:{

                        TextView tvMensagem = (TextView) convertView.findViewById(R.id.lblcabec_23);

                        tvMensagem.setText(Cabec());

                        ImageView botao = (ImageView) convertView.findViewById(R.id.meta_home);

                        botao.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {

                                movimento = "H";

                                categorias = "'3.01','3.02','3.03','3.13','3.14','3.16','3.17','3.18','3.19','3.20','3.21',3.22";

                                codconsulta = App.user.getCODVEN();

                                claconsulta = App.user.getCLASS();

                                paginas.clear();

                                atualizar();

                            }
                        });


                        ImageView back = (ImageView) convertView.findViewById(R.id.meta_back);

                        back.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {

                                movimento = "B";

                                atualizar();

                            }
                        });

                        break;}

                    case ITEM_VIEW_VENDEDOR:{

                        int semaforo;

                        final MetaVend obj = (MetaVend) lsObjetos.get(pos);

                        TextView tvMensagem = (TextView) convertView.findViewById(R.id.lblVendedor_22);

                        tvMensagem.setText(obj.getNOME());

                        TextView tvPorcentagem = (TextView) convertView.findViewById(R.id.lblPorcentagem_22);

                        tvPorcentagem.setText("Vendas: => "+obj.getValueFormatedByname("PERCART")+"% Ideal => "+obj.getValueFormatedByname("PERIDEAL")+"%");

                        ImageView bola = (ImageView) convertView.findViewById(R.id.bola_22);

                        semaforo = obj.semaforo("C");

                        if (semaforo == 0) {

                            bola.setImageResource(R.drawable.bola_verde);

                        }
                        if (semaforo == 1) {

                            bola.setImageResource(R.drawable.bola_amarela);
                        }
                        if (semaforo == 2) {

                            bola.setImageResource(R.drawable.bola_vermelha);

                        }
                        if (semaforo == 3) {

                            bola.setImageResource(R.drawable.bola_branca);

                        }

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.lblTexto2_22);

                        tvTexto.setText(obj.getTEXTO());


                        break;}

                    case ITEM_VIEW_DETALHE:{

                        int semaforo;

                        final RelMetas obj = (RelMetas) lsObjetos.get(pos);

                        ImageView botao = (ImageView) convertView.findViewById(R.id.meta_move_dow);

                        if (obj.getVISIVEL().equals("S")){

                            botao.setImageResource(R.drawable.seta_cima);

                        } else {

                            botao.setImageResource(R.drawable.seta_baixo);

                        }

                        botao.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {


                                if (obj.getVISIVEL().equals("S")){

                                    obj.setVISIVEL("N");

                                } else {

                                    obj.setVISIVEL("S");

                                }

                                notifyDataSetChanged();

                            }
                        });

                        TextView tvCategoria    = (TextView) convertView.findViewById(R.id.lblCategoria_20);
                        TextView tvTexto        = (TextView) convertView.findViewById(R.id.txttexto_20);
                        tvTexto.setText("");

                        if (obj.getMOVIMENTO().equals("C")){

                            tvCategoria.setText(obj.getNOMERET());

                            tvTexto.setText("Meta: "+obj.getValueFormatedByname("OBJETIVO"));

                        }

                        if (obj.getMOVIMENTO().equals("D")){

                            tvCategoria.setText(obj.getNOMERET());
                            tvTexto.setText(obj.getCATEGORIADES());
                        }

                        if (obj.getMOVIMENTO().equals("H")) {

                            tvCategoria.setText(obj.getCATEGORIADES());

                        }


                        tvCategoria.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {

                                if (!obj.getMOVIMENTO().equals("C")) {

                                    if (obj.getCLASSRET().equals("V")){

                                        movimento = "C";

                                    }	else {

                                        movimento = "D";
                                    }

                                    categorias = "'"+obj.getCATEGORIACOD()+"'";

                                    codconsulta = obj.getCODRET();

                                    claconsulta = obj.getCLASSRET();

                                    notifyDataSetChanged();

                                    atualizar();

                                }
                            }
                        });


                        TextView tvPorc    = (TextView) convertView.findViewById(R.id.lblPorcentagem_20);
                        tvPorc.setText("Vendas => "+obj.getValueFormatedByname("PERCCART")+"% Ideal => "+obj.getValueFormatedByname("PERIDEAL")+"%");
                        TextView tvMeta_01      = (TextView) convertView.findViewById(R.id.txtMeta_1_20);
                        tvMeta_01.setText(obj.getValueFormatedByname("OBJETIVO"));
                        TextView tvRealizado_01 = (TextView) convertView.findViewById(R.id.txtMetaRealizado_1_20);
                        tvRealizado_01.setText(obj.getValueFormatedByname("ATINGIDO"));
                        TextView tvPerc_01      = (TextView) convertView.findViewById(R.id.txtPercRealizado_1_20);
                        tvPerc_01.setText(obj.getValueFormatedByname("PERREAL")+"%");


                        ImageView bola = (ImageView) convertView.findViewById(R.id.bola_20);

                        semaforo = obj.semaforo("C");

                        if (semaforo == 0) {

                            tvPerc_01.setTextColor(getResources().getColor(R.color.green));
                            bola.setImageResource(R.drawable.bola_verde);

                        }
                        if (semaforo == 1) {

                            tvPerc_01.setTextColor(getResources().getColor(R.color.orange));
                            bola.setImageResource(R.drawable.bola_amarela);
                        }
                        if (semaforo == 2) {

                            tvPerc_01.setTextColor(getResources().getColor(R.color.red));
                            bola.setImageResource(R.drawable.bola_vermelha);

                        }
                        if (semaforo == 3) {

                            tvPerc_01.setTextColor(getResources().getColor(R.color.white));
                            bola.setImageResource(R.drawable.bola_branca);

                        }

                        TextView tvReal_02      = (TextView) convertView.findViewById(R.id.txtPercIdeal_1_20);
                        tvReal_02.setText(obj.getValueFormatedByname("PERIDEAL")+"%");
                        TextView tvMeta_02      = (TextView) convertView.findViewById(R.id.txtMeta_20_2);
                        tvMeta_02.setText(obj.getValueFormatedByname("OBJETIVO"));
                        TextView tvRealizado_02 = (TextView) convertView.findViewById(R.id.txtRealizado_20_2);
                        tvRealizado_02.setText(obj.getValueFormatedByname("ATINGIDO"));
                        TextView tvCarteira_02  = (TextView) convertView.findViewById(R.id.txtCarteira_20_2);
                        tvCarteira_02.setText(obj.getValueFormatedByname("CARTEIRAS"));
                        TextView tvTotal_02     = (TextView) convertView.findViewById(R.id.txtTotal_20_2);
                        tvTotal_02.setText(obj.getValueFormatedByname("TOTALCAR"));
                        TextView tvPerc_02      = (TextView) convertView.findViewById(R.id.txtPercCarteira_20_2);

                        semaforo = obj.semaforo("C");

                        if (semaforo == 0) tvPerc_02.setTextColor(getResources().getColor(R.color.green));
                        if (semaforo == 1) tvPerc_02.setTextColor(getResources().getColor(R.color.orange));
                        if (semaforo == 2) tvPerc_02.setTextColor(getResources().getColor(R.color.red));
                        if (semaforo == 3) tvPerc_02.setTextColor(getResources().getColor(R.color.white));

                        tvPerc_02.setText(obj.getValueFormatedByname("PERCCART")+"%");
                        TextView tvIdeal_02      = (TextView) convertView.findViewById(R.id.txtPercIdeal_20_2);
                        tvIdeal_02.setText(obj.getValueFormatedByname("PERIDEAL")+"%");



                        TableLayout tab  = (TableLayout) convertView.findViewById(R.id.tablemetas_20);

                        if (obj.getVISIVEL().equals("S")){

                            tab.setVisibility(View.VISIBLE);

                        } else {

                            tab.setVisibility(View.GONE);

                        }


                        break;
                    }

                    default:
                        break;
                }

            }

            catch (Exception e) {

                toast("Erro No Adapdador =>" + e.getMessage());

            }


            return convertView;

        }

        public void toast (String msg)    {

            Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();

        }

    }

    private class callBack extends HandleSoap {

        private Handler mHandler;
        private Bundle params = new Bundle();
        private SoapObject spRelMeta;
        private SoapObject request;
        private String codvend;
        private Boolean back = false;
        private String clasRet = "";


        public Boolean getBack() {
            return back;
        }

        public void setBack(Boolean back) {
            this.back = back;
        }

        public String getClasRet() {
            return clasRet;
        }

        public void setClasRet(String clasRet) {
            this.clasRet = clasRet;
        }

        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

            try {

                SoapObject spRelMetas = this.result;


				/* carregando na memoria */

                lsLista.add("Relatorios De Metas");


				/* registra pagina */

                if (!movimento.equals("H") && !back) {
                    paginas.add(new Anterior(movimento,categorias,codconsulta,claconsulta,clasRet));
                }

                spRelMeta = (SoapObject) spRelMetas.getProperty(0);

                if ( !(spRelMeta.getPropertyAsString("CERRO").equals("000")))
                {

                    showToast(spRelMeta.getPropertyAsString("CMSGERRO"));

                } else {


                    if (movimento.equals("C")){

                        Boolean lCategoria = false;

                        for(int x=0;x<spRelMetas.getPropertyCount();x++) {

                            spRelMeta = (SoapObject) spRelMetas.getProperty(x);

                            if (!lCategoria){

                                lsLista.add(new MetaVend(spRelMeta.getProperty("CCCATEG").toString(), spRelMeta.getProperty("CNCATEG").toString(), spRelMeta.getProperty("CCLASSRET").toString(), Float.parseFloat(spRelMeta.getProperty("NIDEAL").toString()),"Clientes TOP 20"));

                                lCategoria = true;

                            }

                            lsLista.add(new RelMetas(spRelMeta.getProperty("CERRO").toString(),
                                    spRelMeta.getProperty("CMSGERRO").toString(),
                                    spRelMeta.getProperty("CCCATEG").toString(),
                                    spRelMeta.getProperty("CNCATEG").toString(),
                                    spRelMeta.getProperty("CDTRECMET").toString(),
                                    spRelMeta.getProperty("CCLASSRET").toString(),
                                    spRelMeta.getProperty("CCNOME").toString(),
                                    spRelMeta.getProperty("CNNOME").toString(),
                                    Float.parseFloat(spRelMeta.getProperty("NATINGIDO").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NCARTEIRA").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NOBJETIVO").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NIDEAL").toString()),
                                    movimento));


                        }


                    }


                    if (movimento.equals("D")){

                        Boolean lCategoria = false;

                        for(int x=0;x<spRelMetas.getPropertyCount();x++) {

                            spRelMeta = (SoapObject) spRelMetas.getProperty(x);

                            if (!lCategoria){

                                lsLista.add(new MetaVend(spRelMeta.getProperty("CCCATEG").toString(), spRelMeta.getProperty("CNCATEG").toString(), spRelMeta.getProperty("CCLASSRET").toString(), Float.parseFloat(spRelMeta.getProperty("NIDEAL").toString()),"  "));

                                lCategoria = true;

                            }

                            lsLista.add(new RelMetas(spRelMeta.getProperty("CERRO").toString(),
                                    spRelMeta.getProperty("CMSGERRO").toString(),
                                    spRelMeta.getProperty("CCCATEG").toString(),
                                    spRelMeta.getProperty("CNCATEG").toString(),
                                    spRelMeta.getProperty("CDTRECMET").toString(),
                                    spRelMeta.getProperty("CCLASSRET").toString(),
                                    spRelMeta.getProperty("CCNOME").toString(),
                                    spRelMeta.getProperty("CNNOME").toString(),
                                    Float.parseFloat(spRelMeta.getProperty("NATINGIDO").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NCARTEIRA").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NOBJETIVO").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NIDEAL").toString()),
                                    movimento));


                        }


                    }

                    if (movimento.equals("H")) {

                        codvend = "";

                        for(int x=0;x<spRelMetas.getPropertyCount();x++) {

                            spRelMeta = (SoapObject) spRelMetas.getProperty(x);

                            if (!Exists(spRelMeta.getProperty("CCNOME").toString())){

                                lsLista.add(new MetaVend(spRelMeta.getProperty("CCNOME").toString(), spRelMeta.getProperty("CNNOME").toString(), spRelMeta.getProperty("CCLASSRET").toString(), Float.parseFloat(spRelMeta.getProperty("NIDEAL").toString()),"  "));

                            }

                            lsLista.add(new RelMetas(spRelMeta.getProperty("CERRO").toString(),
                                    spRelMeta.getProperty("CMSGERRO").toString(),
                                    spRelMeta.getProperty("CCCATEG").toString(),
                                    spRelMeta.getProperty("CNCATEG").toString(),
                                    spRelMeta.getProperty("CDTRECMET").toString(),
                                    spRelMeta.getProperty("CCLASSRET").toString(),
                                    spRelMeta.getProperty("CCNOME").toString(),
                                    spRelMeta.getProperty("CNNOME").toString(),
                                    Float.parseFloat(spRelMeta.getProperty("NATINGIDO").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NCARTEIRA").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NOBJETIVO").toString()),
                                    Float.parseFloat(spRelMeta.getProperty("NIDEAL").toString()),
                                    movimento));
                        }


                    }


                    adapter = new Adapter(ShowMetasActivity.this, lsLista );

                    list.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                }


            }catch (Exception e)
            {

                showToast(e.getMessage());

            }


        }


        private boolean Exists(String cod){

            Boolean retorno = false;


            for (Object obj : lsLista) {


                if (obj instanceof MetaVend) {

                    if (((MetaVend) obj).getCODIGO().equals(cod)){

                        retorno = true;

                        break;

                    }

                }

            }

            return retorno;

        }
    }




}
