package br.com.brotolegal.sav700.help20;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.dao.HelpDAO;
import br.com.brotolegal.savdatabase.dao.HelpDefault;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.regrasdenegocio.opcoesFiltro;

public class Help20Activity extends AppCompatActivity {

    private Toolbar toolbar;

    private final static int tela_row  = R.layout.help20_default_row;

    private Adapter  adapter;

    private ListView list;

    private List<Object> lsPesquisa = new ArrayList<Object>();

    private ActionMode mActionMode;

    private int Result  = -1;

    private EditText edPesquisa;

    private TextView tvTitulo;

    private ImageView emInfo;

    private HelpDAO helpdao;

    private String   arquivo = "";

    private String   titulo  = "";

    private String   select = "";

    private Spinner ordem;

    private String  ord;

    private HelpDefault escolha = null;

    private Boolean OrdemRefresh = false;

    protected Boolean MultiChoice = false;

    private List<opcoesFiltro> lsOpcoes;

    private GridView grid;

    private OpcaoAdapter opcaoadapter;

    private RelativeLayout loFiltro;

    private String alias;

    private String[] aliasvalues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help20_default);

        list = (ListView) findViewById(R.id.lvHelp_h20);

        loFiltro = (RelativeLayout) findViewById(R.id.help_filtro_h20);

        lsPesquisa = new ArrayList<Object>();

        lsOpcoes   = new ArrayList<opcoesFiltro>();

        try{

            toolbar = (Toolbar) findViewById(R.id.tbhelp20);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Pesquisa");
            toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            toolbar.inflateMenu(R.menu.help_menu);


            Intent i = getIntent();

            if (i != null){

                Bundle params = i.getExtras();

                arquivo       = params.getString("ARQUIVO");

                titulo        = params.getString("TITULO");

                MultiChoice   = (params.getString("MULTICHOICE").equals("S")) ? true : false;

                alias         = params.getString("ALIAS");

                String values = params.getString("ALIASVALUES");

                aliasvalues   = values.split("\\|");

            }


            edPesquisa = (EditText) findViewById(R.id.help_pesquisar_h20);

            edPesquisa.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,	int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {


                        if (!OrdemRefresh){

                            Atualizar();

                        }

                    } catch (Exception e) {

                        Log.i("SAV", e.getMessage());

                    }

                }
            });


            tvTitulo   = (TextView) findViewById(R.id.help_texto_h20);

            tvTitulo.setText(titulo);

            helpdao = new HelpDAO(arquivo);

            helpdao.open();

            ordem      = (Spinner) findViewById(R.id.help_ordem_h20);

            addOrdem();

            ordem.setOnItemSelectedListener(new OrdemOnItemSelectedListener());

            grid       = (GridView) findViewById(R.id.gd_Help_20);

            int x      = 1;

            helpdao.LoadTableFile(alias);

            if (!helpdao.getHelpFiltro().isEmpty()){

                loFiltro.setVisibility(View.VISIBLE);

                for ( HelpFiltro hf : helpdao.getHelpFiltro()){

                    lsOpcoes.add(new opcoesFiltro(x,hf,R.drawable.filter_20));

                    x++;

                }

                opcaoadapter = new OpcaoAdapter(this, lsOpcoes);

                grid.setAdapter(opcaoadapter);

                emInfo = (ImageView) findViewById(R.id.information_filtro_002_help_20);

                emInfo.setOnClickListener(new ClickInfo());

            } else {

                loFiltro.setVisibility(View.GONE);

            }

            adapter = new Adapter(getBaseContext(),lsPesquisa);



        }catch (Exception e)
        {
            showToast("Erro: " + e.getMessage());

            finish();
        }

    }

    private class OrdemOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {


            try {

                OrdemRefresh = true;

                ord =  parent.getItemAtPosition(position).toString();

                edPesquisa.setText("");

                Atualizar();

            } catch (Exception e){

                Log.i("SAV", e.getMessage());

            }
            finally {

                OrdemRefresh = false;

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {


        }
    }

    private void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(edPesquisa.getWindowToken(), 0);
        }
    }

    private void addOrdem(){

        List<String> list = null;

        try {

            list = helpdao.getOrdemAll();

        } catch (Exception e) {

            list = new ArrayList<String>();

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ordem.setAdapter(dataAdapter);

    }

    private void Atualizar() throws Exception{

        List<Object> obj   = new ArrayList<Object>();
        List<Object> alias = new ArrayList<Object>();

        lsPesquisa = new ArrayList<Object>();

        lsPesquisa.add("CABECALHO");

        Cursor cursor = null;

        try {


            if (edPesquisa.getText().toString().trim().length() != 0){

                obj.add(edPesquisa.getText().toString().trim());

            }

            if (aliasvalues.length != 0){

                for (String value : aliasvalues){

                    alias.add(value);

                }

            }

            select = helpdao.getSelect(ord,obj.toArray(),alias.toArray());

            Log.i("SAV", select);

            cursor = helpdao.getDataBase().rawQuery(select, null);

            if (cursor.moveToFirst()){

                while (!(cursor.isAfterLast())) {

                    lsPesquisa.add(helpdao.getHelpDefault(cursor,ord));

                    cursor.moveToNext();

                }
            } else {


                lsPesquisa.add(new NoData("Nenhum Informação Foi Encontrada!"));

            }

            adapter = new Adapter(getBaseContext(), lsPesquisa);

            list.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        }catch (Exception e) {

            throw new Exception(e.getMessage());

        }

        finally {

            try {

                cursor.close();

            }
            catch (Exception ignore) {}
        }

    }

    private void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finish() {

        if (Result != -1) {


            if (escolha != null){

                Intent data = new Intent();

                for (int i = 0 ; i < escolha.getFieldkey().length ; i++){

                    data.putExtra(escolha.getFieldkey()[i],escolha.getValuekey()[i]);

                    Log.i("SAV", escolha.getFieldkey()[i] + " =  "+ escolha.getValuekey()[i]);

                }

                setResult(Result, data);
            }
        }


        super.finish();

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        lsPesquisa= new ArrayList<Object>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:

                Result = -1;

                finish();

                break;


            case R.id.help_menu_ok:


                if (escolha == null){

                    if (lsPesquisa.size() == 3){

                        escolha = (HelpDefault) lsPesquisa.get(1);

                    } else {

                        showToast("Escolha Algo Por favor !!!!");

                        break;
                    }

                }

                Result = 1;

                finish();

                break;

            case R.id.help_menu_cancela:

                Result = -1;

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Boolean refresh = false;

        if (resultCode == 1) {

            for (int x = 0 ; x < lsOpcoes.size(); x++) {

                HelpFiltro hf = lsOpcoes.get(x).getFiltro();

                if (requestCode == hf.getId()) {

                    int y = 0;

                    for(String field : hf.getFields()) {

                        if (data.hasExtra(hf.getWhere())) {

                            String value = data.getExtras().getString(hf.getWhere());

                            lsOpcoes.get(x).setMensagem(hf.getFiltro() + " = " + value);

                            hf.setOneKeyValue(y, value);

                            y++;

                            refresh = true;

                        }

                    }

                }

            }


        }



        if (refresh){
            try {


                opcaoadapter = new OpcaoAdapter(this, lsOpcoes);

                grid.setAdapter(opcaoadapter);

                Atualizar();

            } catch (Exception e) {

                Log.i("SAV", "Retorno do Filtro: " + e.getMessage());

            }
        }
    }




    //inner class

    private  class Adapter extends BaseAdapter
    {

        private NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));

        private DecimalFormat format_02 = new DecimalFormat(",##0.00");

        private List<Object> lsPesquisa;

        Context context;
        final int ITEM_VIEW_CABEC          = 0;
        final int ITEM_VIEW_DETALHE        = 1;
        final int ITEM_VIEW_NODATA         = 3;
        final int ITEM_VIEW_COUNT          = 4;


        private LayoutInflater inflater;


        public Adapter(Context context, List<Object> pObjects) {

            this.lsPesquisa  = pObjects;

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }



        private String soNUmeros(String str) {

            if (str != null) {

                return str.replaceAll("[^0123456789]", "");   }

            else {

                return "";

            }

        }


        private String Cabec(){

            String retorno 	= "";

            retorno 		= "Resultado Da Pesquisa. "+String.valueOf(lsPesquisa.size()+" Itens Encontrados !!");

            return retorno;

        }

        public void addItem(final HelpDefault item) {

            this.lsPesquisa.add(item);

            notifyDataSetChanged();
        }


        private void setChecked(Boolean checked,int pos){

            ((HelpDefault) this.lsPesquisa.get(pos)).setCheck(checked);

            notifyDataSetChanged();

        }


        @Override
        public int getCount() {
            return lsPesquisa.size();
        }

        @Override
        public Object getItem(int position) {
            return lsPesquisa.get(position);
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

            if (lsPesquisa.get(position) instanceof String){

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsPesquisa.get(position) instanceof HelpDefault){

                retorno = ITEM_VIEW_DETALHE;

            }


            if (lsPesquisa.get(position) instanceof NoData){

                retorno = ITEM_VIEW_NODATA;

            }



            return retorno;


        }


        @Override
        public boolean isEnabled(int position) {
            boolean retorno = false;

            return retorno;
        }

        public void deleteitem(int position) {

            this.lsPesquisa.remove(position);
            //Atualizar a lista caso seja adicionado algum item
            notifyDataSetChanged();

            return;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                final int pos  = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.help20_default_row, null);

                            break;


                        case ITEM_VIEW_NODATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;



                    }
                }

                switch (type) {

                    case ITEM_VIEW_CABEC:{

                        TextView tvMensagem = (TextView) convertView.findViewById(R.id.separador);

                        tvMensagem.setText(Cabec());

                        break;}


                    case ITEM_VIEW_DETALHE:
                    {

                        final HelpDefault obj = (HelpDefault) lsPesquisa.get(pos);

                        TextView tvMensa1   = (TextView) convertView.findViewById(R.id.help_mensagem1_help20);

                        tvMensa1.setText(obj.getMensagem1());

                        TextView tvMensa2   = (TextView) convertView.findViewById(R.id.help_mensagem2_help20);

                        tvMensa2.setText(obj.getMensagem2());

                        TextView tvTexto1   = (TextView) convertView.findViewById(R.id.texto1_help20);

                        if (obj.getTexto1().isEmpty()){

                            tvTexto1.setVisibility(View.GONE);

                        } else {

                            tvTexto1.setVisibility(View.VISIBLE);

                            tvTexto1.setText(obj.getTexto1());

                        }

                        TextView tvTexto2   = (TextView) convertView.findViewById(R.id.texto2_help20);


                        if (obj.getTexto2().isEmpty()){

                            tvTexto2.setVisibility(View.GONE);

                        } else {

                            tvTexto2.setVisibility(View.VISIBLE);

                            tvTexto2.setText(obj.getTexto2());

                        }



                        TextView tvLetra   = (TextView) convertView.findViewById(R.id.letra_help20);

                        tvLetra.setText(obj.getLetra());

                        CheckBox cbEscolha    = (CheckBox) convertView.findViewById(R.id.help_cb_help20);

                        if (MultiChoice){

                            cbEscolha.setVisibility(View.VISIBLE);

                            cbEscolha.setChecked(obj.isCheck());

                            cbEscolha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                    Log.i("SAV","Posicao "+String.valueOf(pos));

                                    setChecked(isChecked,pos);



                                    if(isChecked) {

//				                	addSelected();
//
//				                    if (selected == 1) {
//
//				                    	mActionMode = HelpActivity.this.startActionMode(new ActionBarCallBack());
//
//				                    } else {
//
//				                    	CharSequence texto = (selected == 1) ? " Item Selecionado." : " Itens Selecionados.";
//
//				                    	mActionMode.setTitle(getSelectedFormated() + texto);
//
//				                    }
                                    }
                                    else {

//				                	minusSelected();
//
//				                    if (selected == 0){
//
//				                    	mActionMode.setTitle("Nenhum Item Selecionado !!");
//
//				                    } else {
                                        //
//				                    	CharSequence texto = (selected == 1) ? " Item Selecionado." : " Itens Selecionados.";
//
//				                    	mActionMode.setTitle(getSelectedFormated() + texto);
//
//				                    }

                                    }
                                }

                            });




                        } else {


                            cbEscolha.setVisibility(View.INVISIBLE);

                            convertView.setOnClickListener(new OnDoubleClickListener(obj));

                        }


                        break;
                    }

                    case ITEM_VIEW_NODATA: {

                        final NoData obj = (NoData)  lsPesquisa.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                        tvTexto.setText(obj.getMensagem());

                        break;

                    }
                    default:
                        break;
                }


            }

            catch (Exception e) {
                trace("Erro : " + e.getMessage());
            }
            return convertView;
        }

        public void toast (String msg)    {
            Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();
        }

        private void trace (String msg)     {}


    }

    private class LongClick implements View.OnLongClickListener {

        private HelpDefault obj;

        public LongClick(HelpDefault obj) {

            this.obj = obj;

        }

        @Override
        public boolean onLongClick(View v) {

            edPesquisa.setText(obj.getTextopesquisa());

            escolha = obj;

            Result = 1;

            finish();

            return true;
        }

    }


    private class ClickHelp implements View.OnClickListener {

        private HelpFiltro obj;

        public ClickHelp(HelpFiltro obj) {

            this.obj = obj;

        }

        @Override
        public void onClick(View v) {

            Intent i = new Intent(getBaseContext(),FiltroActivity.class);
            Bundle params = new Bundle();
            params.putString("ARQUIVO"     , obj.getOpcoes()[0]);
            params.putString("TITULO"      , obj.getOpcoes()[1]);
            params.putString("MULTICHOICE" , obj.getOpcoes()[2]);
            params.putString("ALIAS"       , obj.getOpcoes()[0]);
            params.putString("ALIASVALUES" , "");
            i.putExtras(params);
            startActivityForResult(i,obj.getId());

        }

    }


    private class ClickInfo implements View.OnClickListener {


        @Override
        public void onClick(View v) {

            for (opcoesFiltro op : lsOpcoes){

                if (op.getTipo() == 1){

                    op.setTipo(2);

                } else {

                    op.setTipo(1);

                }

                opcaoadapter = new OpcaoAdapter(Help20Activity.this, lsOpcoes);

                grid.setAdapter(opcaoadapter);



            }


        }


    }


    private class Click implements View.OnClickListener{

        private HelpDefault obj;

        public Click(HelpDefault obj) {

            this.obj = obj;

        }

        public void onClick(View v) {

            escolha = obj;

            edPesquisa.setText(obj.getTextopesquisa());

        }

    }


    protected class OnDoubleClickListener implements View.OnClickListener {

        private boolean nonDoubleClick = true;
        private long firstClickTime = 0L;
        private final int DOUBLE_CLICK_TIMEOUT = 10000; //ViewConfiguration.getDoubleTapTimeout();
        private HelpDefault obj;


        public OnDoubleClickListener(HelpDefault obj){

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

            edPesquisa.setText(obj.getTextopesquisa());

            escolha = obj;

            Result = 1;

            finish();

        }


    }


    public class OpcaoAdapter extends BaseAdapter  {
        private List<opcoesFiltro> op = new ArrayList<opcoesFiltro>();
        private LayoutInflater inflater;
        private Context context;

        final int ITEM_VIEW_FILTRO          = 0;
        final int ITEM_VIEW_INFO            = 1;
        final int ITEM_VIEW_COUNT           = 2;


        public OpcaoAdapter(Context context, List<opcoesFiltro> opcoes) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            op = opcoes;
        }


        @Override
        public int getCount() {
            return op.size();
        }

        @Override
        public Object getItem(int position) {
            return op.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {

            int retorno = -1;

            if ( op.get(position).getTipo() == 1 ){

                retorno = ITEM_VIEW_FILTRO;
            }

            if (op.get(position).getTipo()  == 2 ){

                retorno = ITEM_VIEW_INFO;

            }

            return retorno;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            try{

                final opcoesFiltro ops = (opcoesFiltro) getItem(position);

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case  ITEM_VIEW_FILTRO: {

                            convertView = inflater.inflate(R.layout.opcao_filtros,null);

                            break;

                        }

                        case ITEM_VIEW_INFO: {

                            convertView = inflater.inflate(R.layout.opcao_info,null);

                            break;

                        }

                    }
                }

                switch (type) {

                    case ITEM_VIEW_FILTRO:{

                        ImageButton filtro  = (ImageButton) convertView.findViewById(R.id.opcaoBotao_help_20);

                        filtro.setImageResource(ops.getImageid());

                        filtro.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                try {

                                    HelpFiltro hf = ops.getFiltro();

                                    Intent i = new Intent(getBaseContext(),Help20Activity.class);
                                    Bundle params = new Bundle();
                                    params.putString("ARQUIVO"     , hf.getOpcoes()[0]);
                                    params.putString("TITULO"      , hf.getOpcoes()[1]);
                                    params.putString("MULTICHOICE" , hf.getOpcoes()[2]);
                                    params.putString("ALIAS"       , hf.getOpcoes()[0]);
                                    params.putString("ALIASVALUES" , "");
                                    i.putExtras(params);
                                    startActivityForResult(i,hf.getId());


                                }catch (Exception e)
                                {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        ImageView cancelar = (ImageView) convertView.findViewById(R.id.CancelarBotao_help_20);


                        cancelar.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                try {

                                    //Cancela O Filtro

                                    HelpFiltro hf = ops.getFiltro();

                                    int y = 0;

                                    ops.setMensagem("");

                                    for(String field : hf.getFields()) {

                                        hf.setOneKeyValue(y, "");

                                        y++;
                                    }

                                    notifyDataSetChanged();

                                    Atualizar();

                                }catch (Exception e)
                                {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        TextView tvOpcao = (TextView) convertView.findViewById(R.id.lblOpcao_help_20);

                        TextView tvAtivo = (TextView) convertView.findViewById(R.id.lblAtivo_help_20);

                        tvAtivo.setText(ops.getMensagem());

                        tvOpcao.setText(ops.getFiltro().getFiltro());

                        break;

                    }
                    case ITEM_VIEW_INFO:{


                        break;

                    }

                    default:{


                        break;

                    }
                }
            }catch (Exception e) {

                Toast.makeText (context,e.getMessage(), Toast.LENGTH_SHORT).show ();

            }

            return convertView;
        }

    }

}