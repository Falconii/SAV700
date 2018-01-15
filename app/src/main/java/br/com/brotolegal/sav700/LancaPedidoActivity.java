package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import org.ksoap2.serialization.SoapObject;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.sav700.help20.Help20Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.config.Parametros;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.entities.CondPagto;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.NoDataProgress;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.TabPrecoCabec;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionItemProduto;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionSavePedido;
import br.com.brotolegal.savdatabase.regrasdenegocio.PedidoBusiness;

public class LancaPedidoActivity extends AppCompatActivity {

    private int Result  = -1;

    private Toolbar         toolbar;

    private ListView        lv;

    private Spinner         spCategoria;

    private Spinner         spMarcas;

    private PedidoBusiness  pedido;

    private String HelpCliente      = "";

    private DecimalFormat format_02 = new DecimalFormat(",##0.00");

    private DecimalFormat format_04 = new DecimalFormat(",##0.0000");

    private DecimalFormat format_05 = new DecimalFormat(",##0.00000");

    private Parametros param        = new Parametros();

    private Dialog dialog;

    private boolean editable = true;

    private Adapter adapter;

    private defaultAdapter tipoadapter;

    private defaultAdapter entregaadapter;

    private defaultAdapter retiraadapter;

    private defaultAdapter condadapter;

    private defaultAdapter tabprecoadapter;

    private defaultAdapter categoriaadapter;

    private defaultAdapter marcaadapter;

    private defaultAdapter viewadapter;

    private List<Object> lsProdutos = new ArrayList<>();

    private LOADThread loadthread;

    private LOADFirst loadfirst;

    private Drawer.Result navigationDrawerRight;

    private PedidoDetMb item_edicao;

    private LinearLayout filtros;

    private View inc_pedidomb_det_row;

    private View inc_pedidomb_filtro;

    private Spinner sp_view_088;



    /*
    *
    *  icones cabecalho
    *
    */

    private ImageView item_max_min_001;
    private ImageView item_trash_001;
    private ImageView item_details_001;
    private TextView  txt_upload_001;
    private ImageView img_flag_001;


     /*
    *
    * Defines os campos cabecalho
    *
    */

    private TextView lbl_fardos_previstos_001;
    private TextView lbl_cliente_001;
    private TextView lbl_pedidocliente_001;
    private Spinner  sp_tipopedido_001;
    private TextView lbl_clienteentrega_001;
    private Spinner  sp_entrega_001;
    private Spinner  sp_condpagto_001;
    private TextView lbl_retira_001;
    private TextView lbl_obsped_001;
    private TextView lbl_obsnf_001;
    private TextView txt_id_001;
    private TextView txt_protheus_001;
    private TextView txt_status_001;
    private TextView txt_erro_001;
    private TextView txt_cliente_001;
    private TextView txt_pedidocliente_001;
    private TextView txt_clienteentrega_001;
    private TextView txt_emissao_001;
    private Spinner  sp_tabpreco_001;

    private TextView txt_retira_001;
    private TextView txt_obsped_001;
    private TextView txt_obsnf_001;
    private TextView txt_cnpj_001;
    private TextView txt_ie_001;
    private TextView txt_qtdbonif_001;
    private TextView txt_totalbonif_001;
    private TextView txt_totalpedido_001;
    private TextView txt_totaldesc_001;
    private TextView txt_totaldescverba_001;
    private TextView txt_fardos_previstos_001;
    private TextView txt_fardos_realizados_001;


    //detalhe

    ImageView item_max_min_005;
    ImageView item_trash_005;

    /*
     *
     * Defines os campos
     *
     */

    LinearLayout ll_venda;
    LinearLayout ll_troca;
    TextView lbl_produto_005;
    TextView lbl_qtd_005;
    TextView lbl_preco_005;
    TextView lbl_desconto_005;
    TextView lbl_verba_005;
    TextView lbl_verba2_005;
    TextView lbl_qtdbonif_005;
    TextView lbl_acordo_005;
    TextView lbl_simulador_005;
    TextView txt_verba_descricao;
    TextView txt_verba2_descricao;
    TextView txt_verba_desconto;
    TextView txt_verba2_desconto;
    TextView txt_id_005;
    TextView txt_item_005;
    TextView txt_status_005;
    TextView txt_erro_005;
    TextView txt_produto_005;
    TextView txt_qtd_005;
    TextView txt_preco_005;
    TextView txt_desconto_005;
    TextView txt_total_005;
    TextView txt_qtdbonif_005;
    TextView txt_precobonif_005;
    TextView txt_totalbonif_005;
    TextView txt_acordo_005;
    TextView txt_simulador_005;
    TextView txt_precotab_005;
    TextView txt_txfin_005;
    TextView txt_txfinper_005;
    TextView txt_contrato_005;
    TextView txt_contratoperc_005;
    TextView txt_politica_005;
    TextView txt_politicaperc_005;
    TextView txt_dna_005;
    TextView txt_dnavlr_005;


    callbackTransmissao callbacktrasmissao;

    private Config config;

    private String operacao  = "";

    private String nropedido = "";

    private String CODIGO    = "";

    private String LOJA      = "";

    private String LOG       = "LANCAPEDIDO";






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lanca_pedido);


        try {

            toolbar = (Toolbar) findViewById(R.id.tb_lanca_pedido);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Lançamentos De Pedidos");
            toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);

            toolbar.inflateMenu(R.menu.menu_lanca_pedido);


            try {

                Intent i = getIntent();

                if (i != null) {

                    Bundle params = i.getExtras();

                    operacao = params.getString("OPERACAO");

                    nropedido = params.getString("NROPEDIDO");

                    CODIGO = params.getString("CODIGO", "");

                    LOJA = params.getString("LOJA", "");

                }

                if (!(operacao.equals("NOVO")) && !(operacao.equals("ALTERACAO"))) {


                    editable = false;


                }


                ConfigDAO dao = new ConfigDAO();

                dao.open();

                config = dao.seek(new String[]{"000"});

                if (config == null) {

                    Toast.makeText(getApplicationContext(), "Não encontrei Arquivo De Configuração !", Toast.LENGTH_SHORT).show();

                    finish();

                }

                dao.close();

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                finish();

            }


            // NAVIGATIOn DRAWER
            // END - RIGHT
            navigationDrawerRight = new Drawer()
                    .withActivity(this)
                    //.withToolbar(mToolbar)
                    .addDrawerItems(
                            new SecondaryDrawerItem().withName("Cadastro Do Cliente").withIcon(getResources().getDrawable(R.drawable.car_selected_1)).withTag("cliente"),
                            new SecondaryDrawerItem().withName("Contrato Do Cliente").withIcon(getResources().getDrawable(R.drawable.car_selected_1)).withTag("contrato"),
                            new SecondaryDrawerItem().withName("Financeiro").withIcon(getResources().getDrawable(R.drawable.car_selected_2)).withTag("financeiro"),
                            new SecondaryDrawerItem().withName("Pedidos").withIcon(getResources().getDrawable(R.drawable.car_selected_3)).withTag("pedido"),
                            new SecondaryDrawerItem().withName("Notas Fiscais").withIcon(getResources().getDrawable(R.drawable.car_4)).withTag("nf")
                    )
                    .withDisplayBelowToolbar(true)
                    .withActionBarDrawerToggleAnimated(true)
                    .withDrawerGravity(Gravity.END)
                    .withSavedInstance(savedInstanceState)
                    .withSelectedItem(-1)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {


                            String opcao = (String) iDrawerItem.getTag();

                            navigationDrawerRight.getAdapter().notifyDataSetChanged();

                            //selecao

                            if (opcao.equals("cliente")) {

                                Intent intent = new Intent(getApplicationContext(), ClienteViewAtivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();
                                params.putString("CODIGO"  , pedido.getCabec().getCODIGOFAT());
                                params.putString("LOJA"    , pedido.getCabec().getLOJAFAT());
                                intent.putExtras(params);
                                startActivity(intent);

                            }

                            if (opcao.equals("contrato")) {

                                Intent intent = new Intent(getApplicationContext(), ContratoViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();

                                params.putString("CODIGO"  ,pedido.getCabec().getCODIGOFAT()+"-"+pedido.getCabec().getLOJAFAT());
                                params.putString("RAZAO"   ,pedido.getCabec().get_ClienteFatRazao());
                                params.putString("CONTRATO",pedido.getCabec().getCONTRATO());

                                intent.putExtras(params);
                                startActivity(intent);

                            }

                            if (opcao.equals("financeiro")) {

                                Intent intent = new Intent(getApplicationContext(), Receber_View_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle params = new Bundle();

                                params.putString("CODIGO"  ,pedido.getCabec().getCODIGOFAT());
                                params.putString("LOJA"     ,pedido.getCabec().getLOJAFAT());

                                intent.putExtras(params);
                                startActivity(intent);

                            }

                        }
                    })
                    .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                            Toast.makeText(LancaPedidoActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                    .build();



            //toolbar.inflateMenu(R.menu.menu_);

            lv = (ListView) findViewById(R.id.lvpedido_100);

            inc_pedidomb_det_row = (View) findViewById(R.id.inc_pedidomb_det_row);

            inc_pedidomb_filtro = (View) findViewById(R.id.inc_pedidomb_filtro);

            sp_view_088 = (Spinner) findViewById(R.id.sp_view_088);

            spCategoria = (Spinner) findViewById(R.id.sp_categoria_088);

            spMarcas    = (Spinner) findViewById(R.id.sp_marca_088);

            inc_pedidomb_det_row.setVisibility(View.GONE);

            inc_pedidomb_filtro.setVisibility(View.GONE);

            lv.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    v.getParent().requestDisallowInterceptTouchEvent(true);



                    return false;
                }


            });

            if (operacao.equals("NOVO")){

                inc_pedidomb_det_row.setVisibility(View.GONE);
                inc_pedidomb_filtro.setVisibility(View.GONE);
                lv.setVisibility(View.GONE);

            }

            if (operacao.equals("ALTERACAO")){

                inc_pedidomb_det_row.setVisibility(View.GONE);
                inc_pedidomb_filtro.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);

            }


            if (operacao.equals("VIEW")){

                inc_pedidomb_det_row.setVisibility(View.GONE);
                inc_pedidomb_filtro.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);


            }


            detalhe_init();

            LOADFirst first = new LOADFirst(mHandlerFirst);

            first.start();


        } catch (Exception e){

            toast("On Create: "+e.getMessage());
        }
    }


    protected class OnDoubleClickListener implements View.OnClickListener {

        private boolean nonDoubleClick = true;
        private long firstClickTime = 0L;
        private final int DOUBLE_CLICK_TIMEOUT = 10000; //ViewConfiguration.getDoubleTapTimeout();


        public OnDoubleClickListener(){



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

            Log.i(LOG,"DUPLO CLICK");

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lanca_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // action with ID action_refresh was selected
            case android.R.id.home:

                finish();

                break;

            case R.id.lanca_pedido_menu_ok: {

                try {

                    if (pedido.getCabec().getTOTALPEDIDO().compareTo(0f) == 0){

                        throw new Exception("Pedido Sem Produtos !!!");

                    }

                    pedido.save();

                    toast("Pedido Salvo.");

                    finish();

                }
                catch (ExceptionSavePedido e){

                    toast(e.getMessage());

                } catch (Exception e){

                    toast(e.getMessage());

                }

                break;
            }

            case R.id.lanca_pedido_menu_cancela: {

                finish();

                break;
            }

            case R.id.lanca_pedido_menu_duplica: {

                for(int x = 0; x < 20 ; x++){

                    PedidoBusiness ped = pedido;

                    ped.getCabec().setNRO(getNewID());

                    ped.ajustaNro();

                    try {

                        ped.save();

                    } catch (ExceptionSavePedido e) {

                        toast(e.getMessage());

                        break;

                    }

                }

                break;
            }

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {

        Result  = 1;

        Intent data = new Intent();

        data.putExtra("CODIGO",CODIGO);

        data.putExtra("LOJA",LOJA);

        setResult(Result, data);

        super.finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Boolean refresh = false;

        if (resultCode == 1 && requestCode == HelpInformation.HelpCliente) {

            String codigo = null;

            String loja   = null;

            try {

                if (data.hasExtra("CODIGO")) {

                    codigo = data.getExtras().getString("CODIGO");

                }
                if (data.hasExtra("LOJA")) {

                    loja = data.getExtras().getString("LOJA");

                }

                if (HelpCliente.equals("CLIENTE"))

                    pedido.setCliente(codigo       , loja);

                else {

                    pedido.setClienteEntrega(codigo, loja);

                }


            } catch (IOException e) {

                toast(e.getMessage());

            } catch (ExceptionItemProduto e) {

                toast(e.getMessage());

            } catch (Exception e) {

                toast(e.getMessage());
            }

        }



        if (resultCode == 1 && requestCode == HelpInformation.HelpProduto) {

//            if (data.hasExtra("CODIGO")) {
//
//                String codigo = data.getExtras().getString("CODIGO");
//
//                try {
//
//                    //adapter.setProduto(codigo);
//
//                    refresh = true;
//
//                } catch (Exception e) {
//
//                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//
//                    refresh = false;
//
//                }
//
//            }

        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpVerba) {

            if (data.hasExtra("CODIGO")) {

                String codigo = data.getExtras().getString("CODIGO");

                try {

                    pedido.setItemVerba(item_edicao.getITEM(),codigo);

                    item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

                    refresh = true;

                } catch (ExceptionItemProduto e) {

                    Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV",e.getMessage());

                } catch (Exception e) {

                    Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV",e.getMessage());
                }

            }

        }


        if (resultCode == 1 && requestCode == HelpInformation.HelpVerbaBonif) {

            if (data.hasExtra("CODIGO")) {

                String codigo = data.getExtras().getString("CODIGO");

                try {

                    pedido.setItemVerba2(item_edicao.getITEM(),codigo);

                    item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

                    refresh = true;

                } catch (ExceptionItemProduto e) {

                    Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV",e.getMessage());

                } catch (Exception e) {

                    Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV",e.getMessage());
                }

            }

        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpAcordo) {

            if (data.hasExtra("CODIGO")) {

                String codigo = data.getExtras().getString("CODIGO");

                try {

                    pedido.setItemAcordo(item_edicao.getITEM(),codigo);

                    item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

                    refresh = true;

                }  catch (ExceptionItemProduto e) {

                    Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV",e.getMessage());

                } catch (Exception e) {

                    Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV",e.getMessage());
                }

            }

        }


        if (resultCode == 1 && requestCode == HelpInformation.HelpSimulador) {

            String codigo = "";

            String cliente = "";

            String loja = "";

            String produto = "";


            try {

                if (data.hasExtra("CODSIMULADOR")) {

                    codigo = data.getExtras().getString("CODSIMULADOR");

                }
                if (data.hasExtra("CODCLI")) {

                    cliente = data.getExtras().getString("CODCLI");

                }
                if (data.hasExtra("LOJACLI")) {

                    loja = data.getExtras().getString("LOJACLI");

                }
                if (data.hasExtra("CODPRODUTO")) {

                    produto = data.getExtras().getString("CODPRODUTO");

                }

                pedido.setItemSimulador(item_edicao.getITEM(),codigo,cliente,loja,produto);

                item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

                refresh = true;


            } catch (Exception e) {
                toast(e.getMessage());
            }

        }


        if (refresh){

            try {


                pedido.validarItem(item_edicao);

                item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

                adapter.refresh();

                detalhe_refresh();


            } catch (Exception e) {

                Log.i("SAV", "Retorno do Filtro: " + e.getMessage());

            }
        }

        pedido.Validar();

        cabec_refresh();

        cabec_onClick();


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        lsProdutos = new ArrayList<Object>();

    }

    private String getNewID()

    {

        String retorno = "";

        Calendar c = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        retorno = App.user.getCOD() + format.format(c.getTime());

        return retorno;

    }


    private void loadItens() throws Exception {

        pedido.LoadDetalhe_V2();

        lsProdutos = new ArrayList<>();

        for(PedidoDetMb det : pedido.getLsDetalhe()){

            lsProdutos.add(det);

            if (det instanceof PedidoDetMb){

                Log.i(LOG, " Item: "+(((PedidoDetMb)det).getITEM()+"Produto.: "+(((PedidoDetMb)det).getPRODUTO()+ " " + (((PedidoDetMb)det).get_Grupo()))));

            }

        }

    }

    private void toast(String msg){

        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();


    };

    private void cabec_init() {


        final PedidoCabMb obj = pedido.getCabec();

         /*
          *
          *  icones
          *
          */

        item_max_min_001 = (ImageView) findViewById(R.id.item_max_min_001);
        item_trash_001   = (ImageView) findViewById(R.id.item_trash_001);
        item_details_001 = (ImageView) findViewById(R.id.item_details_001);
        txt_upload_001   = (TextView)  findViewById(R.id.txt_upload_001);
        img_flag_001     = (ImageView) findViewById(R.id.bt_email_001);

        item_trash_001.setOnClickListener(new Click());
        item_max_min_001.setOnClickListener(new Click());

         /*
          *
          * Defines os campos
          *
          */

        lbl_fardos_previstos_001 = (TextView) findViewById(R.id.lbl_fardos_previstos_001);
        lbl_cliente_001         = (TextView) findViewById(R.id.lbl_cliente_001);
        lbl_pedidocliente_001   = (TextView) findViewById(R.id.lbl_pedidocliente_001);
        sp_tipopedido_001       = (Spinner) findViewById(R.id.sp_tipopedido_001);
        lbl_clienteentrega_001  = (TextView) findViewById(R.id.lbl_clienteentrega_001);
        sp_entrega_001          = (Spinner) findViewById(R.id.sp_entrega_001);
        sp_condpagto_001        = (Spinner) findViewById(R.id.sp_condpagto_001);
        lbl_retira_001          = (TextView) findViewById(R.id.lbl_retira_001);
        lbl_obsped_001          = (TextView) findViewById(R.id.lbl_obsped_001);
        lbl_obsnf_001           = (TextView) findViewById(R.id.lbl_obsnf_001);
        txt_id_001              = (TextView) findViewById(R.id.txt_id_001);
        txt_protheus_001        = (TextView) findViewById(R.id.txt_protheus_001);
        txt_status_001          = (TextView) findViewById(R.id.txt_status_001);
        txt_erro_001            = (TextView) findViewById(R.id.txt_erro_001);
        txt_cliente_001         = (TextView) findViewById(R.id.txt_cliente_001);
        txt_pedidocliente_001   = (TextView) findViewById(R.id.txt_pedidocliente_001);
        txt_clienteentrega_001  = (TextView) findViewById(R.id.txt_clienteentrega_001);
        txt_emissao_001         = (TextView) findViewById(R.id.txt_emissao_001);
        sp_tabpreco_001         = (Spinner) findViewById(R.id.sp_tabpreco_001);
        txt_retira_001          = (TextView) findViewById(R.id.txt_retira_001);
        txt_obsped_001          = (TextView) findViewById(R.id.txt_obsped_001);
        txt_obsnf_001           = (TextView) findViewById(R.id.txt_obsnf_001);
        txt_cnpj_001            = (TextView) findViewById(R.id.txt_cnpj_001);
        txt_ie_001              = (TextView) findViewById(R.id.txt_ie_001);
        txt_qtdbonif_001        = (TextView) findViewById(R.id.txt_qtdbonif_001);
        txt_totalbonif_001      = (TextView) findViewById(R.id.txt_totalbonif_001);
        txt_totalpedido_001     = (TextView) findViewById(R.id.txt_totalpedido_001);
        txt_totaldesc_001       = (TextView) findViewById(R.id.txt_totaldesc_001);
        txt_totaldescverba_001  = (TextView) findViewById(R.id.txt_totaldescverba_001);
        txt_fardos_previstos_001 = (TextView) findViewById(R.id.txt_fardos_previstos_001);
        txt_fardos_realizados_001     = (TextView) findViewById(R.id.txt_fardos_realizados_001);
    }

    private void cabec_refresh() {

        final PedidoCabMb obj = pedido.getCabec();

        List<String[]> opcoes;

         /*
          *
          * Validacao
          *
          *
          */


        if ((!operacao.equals("NOVO"))) {

            item_details_001.setVisibility(View.INVISIBLE);

        }

        if ((obj.getSTATUS().equals("")) || (obj.getSTATUS().equals("1")) || (obj.getSTATUS().equals("2"))) {

            img_flag_001.setImageResource(R.drawable.ic_action_flag_azul);

        }
        if ((obj.getSTATUS().equals("3")) || (obj.getSTATUS().equals("4"))) {

            img_flag_001.setImageResource(R.drawable.ic_action_flag_amarela);

        }
        if ((obj.getSTATUS().equals("5"))) {

            img_flag_001.setImageResource(R.drawable.ic_action_flag_verde);

        }
        if ((obj.getSTATUS().equals("6"))) {

            img_flag_001.setImageResource(R.drawable.ic_action_flag_vermelha);

        }

        if (!obj.isValidoByName("CODIGOFAT")) {

            txt_cliente_001.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_cliente_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

         /*
          *
          *  Atribui Valores
          *
          *
          */
        txt_upload_001.setText(obj.getDTTRANS() + " " + obj.getHOTRANS());
        txt_id_001.setText(obj.getNRO());
        txt_protheus_001.setText(obj.getCPROTHEUS());
        txt_status_001.setText(obj.get_Status());
        txt_erro_001.setText(obj.getMENSAGEM());
        txt_cliente_001.setText(obj.get_ClienteFatRazao());
        txt_cnpj_001.setText(obj.get_ClienteFatCnpj());
        txt_ie_001.setText(obj.get_ClienteFatIE());
        txt_pedidocliente_001.setText(obj.getPEDCLIENTE());

        opcoes = obj.getlsTipos();

        sp_tipopedido_001.setEnabled(false);

        tipoadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, opcoes, "Tipo Ped.", obj.isValidoByName("TIPO"));

        sp_tipopedido_001.setAdapter(tipoadapter);

        try {

            sp_tipopedido_001.setSelection(obj.getlsTiposIndex());

        } catch (Exception e) {

            sp_tipopedido_001.setSelection(0);

        }

        opcoes = new ArrayList<String[]>();

        opcoes.add(new String[]{"1ª", obj.getENTREGA()});

        sp_entrega_001.setEnabled(false);

        entregaadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, opcoes, "Entrega", obj.isValidoByName("ENTREGA"));

        sp_entrega_001.setAdapter(entregaadapter);

        sp_entrega_001.setSelection(0);

        txt_retira_001.setText(obj.get_Retira());

        txt_emissao_001.setText(obj.getEMISSAO());

        txt_clienteentrega_001.setText(obj.get_ClienteEntRazao());

        int index = -1;

        int i = 0;

        opcoes = new ArrayList<>();

        for (CondPagto op : pedido.getLsCondPagto()) {

            opcoes.add(new String[]{op.getCODIGO(), op.getCODIGO() + "-" + op.getDESCRICAO()});

            if (op.getCODIGO().equals(obj.getCOND())) {

                index = i;

            }

            i++;

        }

        if (index == -1) {

            opcoes.add(new String[]{"01", " "});

            index = 0;

        }

        sp_condpagto_001.setEnabled(false);

        condadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, opcoes, "Cond Pagto", obj.isValidoByName("COND"));

        sp_condpagto_001.setAdapter(condadapter);

        sp_condpagto_001.setSelection(index);

        sp_tabpreco_001.setEnabled(false);

        opcoes = new ArrayList<>();

        opcoes.add(new String[]{pedido.getCabec().getTABPRECO(), pedido.getCabec().get_TabPreco()});

        tabprecoadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, opcoes, "Tab.Preço", obj.isValidoByName("TABPRECO"));

        sp_tabpreco_001.setAdapter(tabprecoadapter);

        sp_tabpreco_001.setSelection(0);


        txt_obsped_001.setText(obj.getOBSPED());
        txt_obsnf_001.setText(obj.getOBSNF());
        txt_qtdbonif_001.setText(format_02.format(obj.getQTDBINIFICADA()));
        txt_totalbonif_001.setText(format_02.format(obj.getVLRBONIFICADO()));
        txt_totalpedido_001.setText(format_02.format(obj.getTOTALPEDIDO()));
        txt_totaldesc_001.setText(format_02.format(obj.getTOTALDESCONTO()));
        txt_totaldescverba_001.setText(format_02.format(obj.getTOTALVERBA()));
        txt_fardos_previstos_001.setText(format_02.format(obj.getFDSPREVISTO()));
        txt_fardos_realizados_001.setText(format_02.format(obj.getFDSREAIS()));

        int indice = pedido.getIndiceByCodigo(obj.getTABPRECO());

        if (indice != -1) {


            if (!pedido.getLsTabPrecoCabec().get(indice).getFLAGFAIXA().equals("1")) {

                txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


            } else {

                if (obj.getFDSPREVISTO().compareTo(obj.getFDSREAIS()) > 0) {

                    txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

                } else {

                    txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }


        }

    }



    private void cabec_onClick() {

        final PedidoCabMb obj = pedido.getCabec();

        List<String[]> opcoes;


         /*
          *
          *  Listener dos botões
          *
          *
          */



        if (editable){

            item_details_001.setOnClickListener(new Click());

            lbl_cliente_001.setOnClickListener(new Click());
            lbl_cliente_001.setOnLongClickListener(new ClickCabecClear("CODIGOFAT"));

            if (!pedido.getCliente().getCODIGO().equals("")){

                int index = 0;

                int prazo;

                try {

                    prazo = Integer.parseInt(pedido.getCliente().getDIASPEDPROG());

                }
                catch (Exception e){

                    prazo = 7;

                }

                SimpleDateFormat format_chave = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                SimpleDateFormat format_full  = new SimpleDateFormat("dd/MM/yyyy EEE", new Locale("pt", "BR"));

                opcoes  = new ArrayList<String[]>();

                Date emissao = null;

                Calendar c = Calendar.getInstance();

                for ( int x = 0 ; x < prazo ; x++){

                    if (x==0){

                        try {

                            emissao = format_chave.parse(obj.getEMISSAO());

                        } catch (ParseException e) {

                            emissao = new Date();

                        }

                        c.setTime(emissao);

                    } else {


                        c.setTime(emissao);

                        c.add(Calendar.DATE, + 1);


                    }

                    emissao = c.getTime();

                    if (obj.getENTREGA().equals(format_chave.format(emissao))){

                        index = x;

                    }

                    opcoes.add(new String[] { String.valueOf(x+1)+"ª" , format_full.format(emissao) });

                }

                sp_entrega_001.setEnabled(true);

                entregaadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, opcoes,"Entrega",obj.isValidoByName("ENTREGA"));

                sp_entrega_001.setAdapter(entregaadapter);

                sp_entrega_001.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        entregaadapter.setEscolha(position);

                        Object lixo = sp_entrega_001.getSelectedItem();

                        obj.setENTREGA(((String[]) lixo)[1].substring(0,10));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

                sp_entrega_001.setSelection(index);

                lbl_clienteentrega_001.setOnClickListener(new Click());
                lbl_clienteentrega_001.setOnLongClickListener(new ClickCabecClear("CODIGOENT"));

                lbl_pedidocliente_001.setOnClickListener(new Click());
                lbl_pedidocliente_001.setOnLongClickListener(new ClickCabecClear("PEDCLIENTE"));

                if (lsProdutos.size() == 0){

                    sp_tipopedido_001.setEnabled(true);

                } else {

                    sp_tipopedido_001.setEnabled(false);

                }

                tipoadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.conexoes_opcoes, obj.getlsTipos(),"Tipo Ped.",obj.isValidoByName("TIPO"));

                sp_tipopedido_001.setAdapter(tipoadapter);

                sp_tipopedido_001.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        tipoadapter.setEscolha(position);

                        Object lixo = sp_tipopedido_001.getSelectedItem();

                        obj.setTIPO(((String[]) lixo)[0]);

                        pedido.SetTipoPedido(((String[]) lixo)[0]);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

                try {

                    sp_tipopedido_001.setSelection(obj.getlsTiposIndex());

                } catch (Exception e){

                    sp_tipopedido_001.setSelection(0);
                }


                index = -1;

                int i     = 0;

                opcoes = new ArrayList<String[]>();

                for (CondPagto op : pedido.getLsCondPagto() ){

                    opcoes.add(new String[] {op.getCODIGO(),op.getCODIGO()+"-"+op.getDESCRICAO()});

                    if (op.getCODIGO().equals(obj.getCOND())){


                        index = i;


                    }

                    i++;

                }

                if (index==-1){

                    opcoes.add(new String[] {"01"," "});

                    index = 0;

                }

                sp_condpagto_001.setEnabled(true);

                condadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, opcoes,"Cond Pagto",obj.isValidoByName("COND"));

                sp_condpagto_001.setAdapter(condadapter);

                sp_condpagto_001.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        condadapter.setEscolha(position);

                        if (position < pedido.getLsCondPagto().size()) {

                            pedido.setCondpagto(pedido.getLsCondPagto().get(position));

                            obj.setCOND(pedido.getLsCondPagto().get(position).getCODIGO());

                            obj.set_Cond(pedido.getLsCondPagto().get(position).getDESCRICAO());

                        }

                        if (!condadapter.isInicializacao()) {
                            try {

                                pedido.trocaBasePreco();

                                pedido.recalculo();

                                if (lsProdutos.size() > 0) {

                                    adapter.refresh();

                                    detalhe_refresh();

                                    txt_totalpedido_001.setText(format_02.format(obj.getTOTALPEDIDO()));
                                    txt_totaldesc_001.setText(format_02.format(obj.getTOTALDESCONTO()));
                                    txt_totaldescverba_001.setText(format_02.format(obj.getTOTALVERBA()));
                                    txt_fardos_previstos_001.setText(format_02.format(obj.getFDSPREVISTO()));
                                    txt_fardos_realizados_001.setText(format_02.format(obj.getFDSREAIS()));

                                }

                            } catch (ExceptionItemProduto exceptionItemProduto) {

                                toast(exceptionItemProduto.getMessage());

                            }
                        } else {

                            condadapter.setIsInicializacao(false);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

                condadapter.setIsInicializacao(true);

                sp_condpagto_001.setSelection(index);


                index = -1;

                i     = 0;

                opcoes = new ArrayList<String[]>();

                for (TabPrecoCabec op : pedido.getLsTabPrecoCabec() ){

                    opcoes.add(new String[] {op.getCODIGO(),op.getCODIGO()+"-"+op.getDESCRICAO()});

                    if (op.getCODIGO().equals(obj.getTABPRECO())){


                        index = i;


                    }

                    i++;

                }

                if (index==-1){

                    opcoes.add(new String[] {"01"," "});

                    index = 0;

                }

                sp_tabpreco_001.setEnabled(true);

                tabprecoadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, opcoes,"Tab.Preço",obj.isValidoByName("TABPRECO"));

                sp_tabpreco_001.setAdapter(tabprecoadapter);

                sp_tabpreco_001.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {

                        int indice = -1;

                        //VALIDA A TROCA ANTES DE EXECUTAR


                        if (!tabprecoadapter.isInicializacao()) {
                            try {

                                String tabela = tabprecoadapter.getOpcao(position);

                                pedido.validaTrocaDeTabela(tabela);

                            } catch (Exception e) {

                                toast(e.getMessage());

                                tabprecoadapter.setIsInicializacao(true);

                                sp_tabpreco_001.setSelection(pedido.getIndiceByCodigo(pedido.getCabec().getTABPRECO()));

                                return;

                            }
                        }
                        tabprecoadapter.setEscolha(position);

                        Object lixo = sp_tabpreco_001.getSelectedItem();

                        indice = pedido.getIndiceByCodigo(((String[]) lixo)[0]);

                        if (indice != -1) {

                            obj.setTABPRECO(((String[]) lixo)[0]);

                            if (pedido.getLsTabPrecoCabec().get(indice).getFLAGFAIXA().equals("2")) {

                                obj.setFDSPREVISTO(0f);

                                txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                            } else {

                                if (pedido.getLsTabPrecoCabec().get(indice).getFLAGFAIXA().equals("1"))
                                {
                                    obj.setFDSPREVISTO(pedido.getLsTabPrecoCabec().get(indice).getFAIXAATE());

                                    if (obj.getFDSPREVISTO().compareTo(obj.getFDSREAIS()) > 0) {

                                        txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

                                    } else {

                                        txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    }
                                }
                            }


                            txt_fardos_previstos_001.setText(format_02.format(obj.getFDSPREVISTO()));


                            if (!tabprecoadapter.isInicializacao()) {

                                try {

                                    pedido.atualizaTabela();

                                    pedido.trocaBasePreco();

                                    pedido.recalculo();

                                    pedido.Validar();

                                    if (lsProdutos.size() > 0) {

                                        adapter.refresh();

                                        detalhe_refresh();

                                        txt_totalpedido_001.setText(format_02.format(obj.getTOTALPEDIDO()));
                                        txt_totaldesc_001.setText(format_02.format(obj.getTOTALDESCONTO()));
                                        txt_totaldescverba_001.setText(format_02.format(obj.getTOTALVERBA()));
                                        txt_fardos_previstos_001.setText(format_02.format(obj.getFDSPREVISTO()));
                                        txt_fardos_realizados_001.setText(format_02.format(obj.getFDSREAIS()));

                                    }

                                } catch (ExceptionItemProduto exceptionItemProduto) {

                                    toast(exceptionItemProduto.getMessage());

                                }
                            } else {

                                tabprecoadapter.setIsInicializacao(false);
                            }
                        } else {

                            toast("Tabela Não Encontrada: " + ((String[]) lixo)[0]);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

                tabprecoadapter.setIsInicializacao(true);

                sp_tabpreco_001.setSelection(index);

                lbl_retira_001.setOnClickListener(new Click());

                lbl_obsped_001.setOnClickListener(new Click());
                lbl_obsped_001.setOnLongClickListener(new ClickCabecClear("OBSPED"));

                lbl_obsnf_001.setOnClickListener(new Click());
                lbl_obsnf_001.setOnLongClickListener(new ClickCabecClear("OBSNF"));

                lbl_fardos_previstos_001.setOnClickListener(new Click());
                lbl_fardos_previstos_001.setOnLongClickListener(new ClickCabecClear("FDSPREVISTO"));
            }
        }




    }

    private void setLSProdutos(){

        try {

            if (!(item_edicao == null)) {

                lsProdutos.set(Integer.valueOf(item_edicao.getITEM()) - 1, item_edicao);

                adapter.refresh();

            }

        } catch (Exception e){

            //

        }

    }

    private void produto_filtro(){

        final List<String[]> opcoes = new ArrayList<>();

        if ( operacao.equals("VIEW") ) {
            opcoes.add(new String[]{"04", "PEDIDO"});
        } else {
            opcoes.add(new String[]{"01", "TODOS"});
            opcoes.add(new String[]{"02", "OK"});
            opcoes.add(new String[]{"03", "PROBLEMA"});
            opcoes.add(new String[]{"04", "PEDIDO"});
        }

        viewadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, opcoes,"",true);

        sp_view_088.setAdapter(viewadapter);

        sp_view_088.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                viewadapter.setEscolha(position);

                Object lixo = sp_view_088.getSelectedItem();

                if ( ((String[]) lixo)[0].equals("04")   ){

                    adapter.setViewPedido(true);

                } else {

                    adapter.setViewPedido(false);

                }

                adapter.setSubFiltro(((String[]) lixo)[0]);

                adapter.refresh();

                if (inc_pedidomb_filtro.getVisibility() == View.VISIBLE){

                    if (adapter.isViewPedido()){

                        inc_pedidomb_det_row.setVisibility(View.GONE);

                        spCategoria.setVisibility(View.GONE);

                        spMarcas.setVisibility(View.GONE);

                    } else {

                        inc_pedidomb_det_row.setVisibility(View.VISIBLE);

                        spCategoria.setVisibility(View.VISIBLE);

                        spMarcas.setVisibility(View.VISIBLE);

                    }
                }

                item_edicao = new PedidoDetMb();

                detalhe_refresh();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        sp_view_088.setSelection(0);

        categoriaadapter = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, pedido.getLsCategoria(),"Categoria: ",true);

        spCategoria.setAdapter(categoriaadapter);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoriaadapter.setEscolha(position);
                Object lixo = spCategoria.getSelectedItem();
                adapter.setFilter(((String[]) lixo)[0], adapter.Filtro_marca);

                item_edicao = new PedidoDetMb();

                detalhe_refresh();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        spCategoria.setSelection(0);


        marcaadapter   = new defaultAdapter(LancaPedidoActivity.this, R.layout.choice_default_row, pedido.getLsMarca(),"Marca: ",true);

        spMarcas.setAdapter(marcaadapter);

        spMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                marcaadapter.setEscolha(position);
                Object lixo = spMarcas.getSelectedItem();
                adapter.setFilter(adapter.Filtro_grupo,((String[]) lixo)[0]);
                item_edicao = new PedidoDetMb();
                detalhe_refresh();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



        spMarcas.setSelection(0);


    }

    private void detalhe_init() {



         /*
          *
          *  icones
          *
          */

        item_max_min_005 = (ImageView) findViewById(R.id.item_max_min_005);
        item_trash_005 = (ImageView) findViewById(R.id.item_trash_005);

         /*
          *
          * Defines os campos
          *
          */

        ll_venda = (LinearLayout) findViewById(R.id.parte_venda_005);
        ll_troca = (LinearLayout) findViewById(R.id.parte_troca_005);



        lbl_produto_005 = (TextView) findViewById(R.id.lbl_produto_005);
        lbl_qtd_005 = (TextView) findViewById(R.id.lbl_qtd_005);
        lbl_preco_005 = (TextView) findViewById(R.id.lbl_preco_005);
        lbl_desconto_005 = (TextView) findViewById(R.id.lbl_desconto_005);
        lbl_verba_005    = (TextView) findViewById(R.id.lbl_verba_005);
        lbl_verba2_005   = (TextView) findViewById(R.id.lbl_verba2_005);
        lbl_qtdbonif_005 = (TextView) findViewById(R.id.lbl_qtdbonif_005);
        lbl_acordo_005     = (TextView) findViewById(R.id.lbl_acordo_005);
        lbl_simulador_005  = (TextView)  findViewById(R.id.lbl_simulador_005);
        txt_verba_descricao = (TextView) findViewById(R.id.txt_verba_descricao_005);
        txt_verba_desconto = (TextView) findViewById(R.id.txt_verba_desconto_005);
        txt_verba2_descricao = (TextView) findViewById(R.id.txt_verba2_descricao_005);
        txt_verba2_desconto  = (TextView) findViewById(R.id.txt_verba2_desconto_005);
        txt_id_005 = (TextView) findViewById(R.id.txt_id_005);
        txt_item_005 = (TextView) findViewById(R.id.txt_item_005);
        txt_status_005 = (TextView) findViewById(R.id.txt_status_005);
        txt_erro_005 = (TextView) findViewById(R.id.txt_erro_005);
        txt_produto_005 = (TextView) findViewById(R.id.txt_produto_005);
        txt_qtd_005 = (TextView) findViewById(R.id.txt_qtd_005);
        txt_preco_005 = (TextView) findViewById(R.id.txt_preco_005);
        txt_desconto_005 = (TextView) findViewById(R.id.txt_desconto_005);
        txt_total_005 = (TextView) findViewById(R.id.txt_total_005);
        txt_qtdbonif_005 = (TextView) findViewById(R.id.txt_qtdbonif_005);
        txt_precobonif_005 = (TextView) findViewById(R.id.txt_precobonif_005);
        txt_totalbonif_005 = (TextView) findViewById(R.id.txt_totalbonif_005);
        txt_acordo_005 = (TextView) findViewById(R.id.txt_acordo_005);
        txt_simulador_005 = (TextView) findViewById(R.id.txt_simulador_005);
        txt_precotab_005 = (TextView) findViewById(R.id.txt_precotab_005);
        txt_txfin_005 = (TextView) findViewById(R.id.txt_txfin_005);
        txt_txfinper_005 = (TextView) findViewById(R.id.txt_txfinper_005);
        txt_contrato_005 = (TextView) findViewById(R.id.txt_contrato_005);
        txt_contratoperc_005 = (TextView) findViewById(R.id.txt_contratoperc_005);
        txt_politica_005 = (TextView) findViewById(R.id.txt_politica_005);
        txt_politicaperc_005 = (TextView) findViewById(R.id.txt_politicaperc_005);
        txt_dna_005 = (TextView) findViewById(R.id.txt_dna_005);
        txt_dnavlr_005 = (TextView) findViewById(R.id.txt_dnavlr_005);
    }

    private void detalhe_refresh(){

         /*
          *
          *  Validacao
          *
          */


        if (pedido != null) {

            if (pedido.getCabec().getTIPO().equals("005")) {

                ll_venda.setVisibility(View.GONE);
                ll_troca.setVisibility(View.VISIBLE);

            } else {

                ll_venda.setVisibility(View.VISIBLE);
                ll_troca.setVisibility(View.GONE);

            }
        }

        if ((item_edicao == null) || (inc_pedidomb_det_row.getVisibility() != View.VISIBLE) ){

            return;

        }

        if (item_edicao.getNRO().isEmpty()){

            item_trash_005.setVisibility(View.INVISIBLE);

        } else {

            item_trash_005.setVisibility(View.VISIBLE);


        }

        if (!item_edicao.isValidoByName("PRODUTO")){

            txt_produto_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_produto_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }


        if (!item_edicao.isValidoByName("QTD")){

            txt_qtd_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_qtd_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (!item_edicao.isValidoByName("PRCVEN")){

            txt_preco_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_preco_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (!item_edicao.isValidoByName("DESCON")){

            txt_desconto_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_desconto_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (!item_edicao.isValidoByName("BONIQTD")){

            txt_qtdbonif_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_qtdbonif_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }


        if (!item_edicao.isValidoByName("CODVERBA")){

            txt_verba_descricao.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_verba_descricao.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }


        if (!item_edicao.isValidoByName("CODVERBA2")){

            txt_verba2_descricao.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_verba2_descricao.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }


        if (!item_edicao.isValidoByName("ACORDO")){

            txt_acordo_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

        } else {

            txt_acordo_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }


        /*
          *
          *  Atribui Valores
          *
          *
          */

        txt_id_005.setText(item_edicao.getNRO());
        txt_item_005.setText(item_edicao.getITEM());
        txt_status_005.setText(item_edicao.get_Status());
        txt_erro_005.setText(item_edicao.getMENSAGEM());
        txt_produto_005.setText("Cód.: "+item_edicao.getPRODUTO()+"-"+item_edicao.get_Produto());
        txt_qtd_005.setText(format_02.format(item_edicao.getQTD()));
        txt_preco_005.setText(format_02.format(item_edicao.getPRCVEN()));
        txt_desconto_005.setText(format_05.format(item_edicao.getDESCON()));
        txt_total_005.setText(format_02.format(item_edicao.getTOTAL()));
        txt_qtdbonif_005.setText(format_02.format(item_edicao.getBONIQTD()));
        txt_precobonif_005.setText(format_02.format(item_edicao.getBONIPREC()));
        txt_totalbonif_005.setText(format_02.format(item_edicao.getBONITOTAL()));
        txt_verba_descricao.setText(item_edicao.get_Verba());
        txt_verba_desconto.setText(format_02.format(item_edicao.getDESCVER()));
        txt_verba2_descricao.setText(item_edicao.get_Verba2());
        txt_verba2_desconto.setText(format_02.format(item_edicao.getDESCVER2()));
        txt_acordo_005.setText(item_edicao.get_Acordo());
        txt_simulador_005.setText(item_edicao.getSIMULADOR());
        if (pedido.getCliente() == null) {

            txt_precotab_005.setText("");

        }else {

            if (item_edicao.getNRO().isEmpty()){

                txt_precotab_005.setText("");

            } else {

                txt_precotab_005.setText(format_02.format(item_edicao.getPRECOTABELA())   +
                        " D. Máx: " + format_02.format(item_edicao.getDESCONTOMAIS()) +
                        " A. Máx.: " + format_02.format(item_edicao.getACRESCIMOMAIS())  +
                        " PF: "  + format_02.format(item_edicao.getPRECOFORMACAO())  +
                        " D.C: "  + format_02.format(pedido.getCliente().getDESCCLIENTE())+
                        " I: "+format_02.format(item_edicao.getIMPOSTO())+
                        " F: "+item_edicao.getFATOR());
            }

        }
        txt_txfin_005.setText(item_edicao.getUSATAXAFIN());
        txt_txfinper_005.setText(item_edicao.getTAXAFIN());
        if (pedido.getCabec() == null) {


            txt_contrato_005.setText("");


        } else {


            txt_contrato_005.setText(pedido.getCabec().getCONTRATO());


        }

        txt_contratoperc_005.setText(format_02.format(item_edicao.getDESCCONTRATO()));
        txt_politica_005.setText("");
        txt_politicaperc_005.setText(format_02.format(item_edicao.getDESCONTOPOL()));
        txt_dna_005.setText("");
        txt_dnavlr_005.setText(format_02.format(item_edicao.getDNAVALOR()));

    }

    private void detalhe_onClick(){

        /*
          *
          *  Listener dos botões
          *
          *
          */

        if (item_edicao == null || (inc_pedidomb_det_row.getVisibility() != View.VISIBLE)){

            return;

        }

        if (editable){

            if ((pedido.getCliente().getCODIGO() != null) && (!pedido.getCliente().getCODIGO().equals(""))){

                item_trash_005.setOnClickListener(new ClickDet());
                lbl_qtd_005.setOnClickListener(new ClickDet());
                lbl_preco_005.setOnClickListener(new ClickDet());
                lbl_desconto_005.setOnClickListener(new ClickDet());
                lbl_qtdbonif_005.setOnClickListener(new ClickDet());
                lbl_verba_005.setOnLongClickListener(new ClickDetClear( "CODVERBA"));
                lbl_verba_005.setOnClickListener(new ClickDet());
                lbl_verba2_005.setOnLongClickListener(new ClickDetClear( "CODVERBA2"));
                lbl_verba2_005.setOnClickListener(new ClickDet());
                lbl_acordo_005.setOnLongClickListener(new ClickDetClear("ACORDO"));
                lbl_acordo_005.setOnClickListener(new ClickDet());
                lbl_simulador_005.setOnLongClickListener(new ClickDetClear("SIMULADOR"));
                lbl_simulador_005.setOnClickListener(new ClickDet());

            }

        }

    }

    private class clickedicao  implements View.OnClickListener{

        private PedidoDetMb obj;


        public clickedicao(PedidoDetMb obj) {
            this.obj = obj;
        }

        @Override
        public void onClick(View v) {

            try {


                if (obj.getNRO().isEmpty()) {

                    try {

                        pedido.setItemProduto(obj.getITEM(),obj.getPRODUTO());

                        item_edicao = pedido.getItemBySelf(obj.getITEM());


                    } catch (ExceptionItemProduto e) {

                        e.printStackTrace();
                    }

                } else {


                    item_edicao = pedido.getItemBySelf(obj.getITEM());

                }

                pedido.Validar();

                adapter.refresh();


            } catch (Exception e) {

                e.printStackTrace();

            }

            detalhe_init();

            detalhe_refresh();

            detalhe_onClick();


        }
    }

    private class ClickDefault implements View.OnClickListener{

        private PedidoDetMb obj;

        public ClickDefault(PedidoDetMb obj) {

            this.obj = obj;

        }

        public void onClick(View v) {

            try {

                pedido.setItemProduto(obj.getITEM(), obj.getPRODUTO());

            } catch (ExceptionItemProduto exceptionItemProduto) {

                exceptionItemProduto.printStackTrace();

            }

            detalhe_init();

            detalhe_refresh();

            detalhe_onClick();

        }
    }

    private class Click implements View.OnClickListener{

        private PedidoCabMb obj;

        public Click() {

            this.obj = pedido.getCabec();

        }

        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.item_max_min_001:{

                    String op = obj.get_View().equals("G") ? "P" : "G";

                    //adapter.setMinMax(op);

                    break;
                }
                case R.id.item_max_min_002:{

                    String op = obj.get_View().equals("G") ? "P" : "G";

                    //adapter.setMinMax(op);

                    break;
                }

                case R.id.item_trash_001:{



                    break;
                }

                case R.id.item_details_001:{

                    try{

                        {
                            if (!pedido.getCabec().getCODIGOFAT().isEmpty()){
//                                &&
//                                !(pedido.getCabec().getFDSPREVISTO().compareTo(0f) == 0)) {


                                if (pedido != null) {

                                    if (pedido.getCabec().getTIPO().equals("005")) {

                                        ll_venda.setVisibility(View.GONE);
                                        ll_troca.setVisibility(View.VISIBLE);

                                    } else {

                                        ll_venda.setVisibility(View.VISIBLE);
                                        ll_troca.setVisibility(View.GONE);

                                    }
                                }

                                inc_pedidomb_det_row.setVisibility(View.VISIBLE);

                                inc_pedidomb_filtro.setVisibility(View.VISIBLE);



                                loadthread = new LOADThread(mHandlerLoad);

                                loadthread.start();

                                item_details_001.setVisibility(View.INVISIBLE);

                            }  else {

                                if (pedido.getCabec().getCODIGOFAT().isEmpty()){

                                    throw  new Exception("Favor Digitar O Código Do Cliente.");

                                }

                                //if ((pedido.getCabec().getFDSPREVISTO().compareTo(0f) == 0f)){

                                //    throw  new Exception("Favor Digitar A Quantidade De Fardos Previstos.");

                                //}

                            }
                        }

                    } catch (Exception e){


                        toast(e.getMessage());

                    }

                    break;
                }

                case R.id.lbl_emissao_001:
                {

                    ClickGetDados("DATA DA EMISSÃO","EMISSAO");

                    break;
                }


                case R.id.lbl_pedidocliente_001:
                {

                    ClickGetDados("Qual O Pedido Do Cliente ?", "PEDCLIENTE");

                    break;
                }

                case R.id.lbl_cliente_001:
                {

                    if (((lsProdutos.size() == 0))) {
                        HelpCliente = "CLIENTE";
                        Intent i = new Intent(LancaPedidoActivity.this, Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO", "CLIENTE");
                        params.putString("TITULO", "CADASTRO DE CLIENTES");
                        params.putString("MULTICHOICE", "N");
                        params.putString("ALIAS", "CLIENTE");
                        params.putString("ALIASVALUES", "");
                        i.putExtras(params);
                        startActivityForResult(i, HelpInformation.HelpCliente);
                    }
                    break;
                }
                case R.id.lbl_clienteentrega_001:
                {
                    if (obj.get_ClienteCodRede().equals("000000")){

                        toast("Cliente Não Pertence A Nenhuma REDE !");

                        break;

                    }

                    HelpCliente = "CLIENTEENTREGA";
                    Intent i = new Intent(LancaPedidoActivity.this,Help20Activity.class);
                    Bundle params = new Bundle();
                    params.putString("ARQUIVO"     , "CLIENTE");
                    params.putString("TITULO"      , "CADASTRO DE CLIENTES ENTREGA");
                    params.putString("MULTICHOICE" , "N");
                    params.putString("ALIAS"       , "CLIENTEENTREGA");
                    params.putString("ALIASVALUES" , obj.get_ClienteCodRede());
                    i.putExtras(params);
                    startActivityForResult(i, HelpInformation.HelpCliente);

                    break;
                }


                case R.id.lbl_retira_001:
                {

                    if (((lsProdutos.size() == 0)) && (  (obj.getTIPO().equals("001")) || (obj.getTIPO().equals("003")) ) ) {

                        ClickGetDadosRetira("Desconto CLIENTE RETIRA", "DESCRET");

                    }

                    break;
                }


                case R.id.lbl_obsped_001:
                {

                    ClickGetDados("Observação Do Pedido","OBSPED");


                    break;
                }

                case R.id.lbl_obsnf_001:
                {

                    ClickGetDados("Observação Da NF","OBSNF");

                    break;
                }


                case R.id.lbl_fardos_previstos_001:
                {

                    ClickGetDados("FARDOS PREVISTOS","FDSPREVISTO");

                    break;
                }

                default:

                    break;
            }


        }
    }

    private class ClickCabecClear implements View.OnLongClickListener{

        private String      field;

        public ClickCabecClear(String field) {

            this.field = field;

        }


        @Override
        public boolean onLongClick(View v) {

            pedido.getCabec().setFieldByName(field, "");

            if (field.equals("CODIGOFAT")){

                pedido.getCabec().set_ClienteFatRazao("");

                pedido.getCabec().set_ClienteFatCnpj("");

                pedido.getCabec().set_ClienteFatIE("");

                pedido.getCabec().set_ClienteEntRazao("");

                pedido.getCabec().set_ClienteCodRede("");

                pedido.getCabec().setCOND("");

                pedido.getCabec().set_Cond("");

                pedido.setLsCondPagto(new ArrayList<CondPagto>());

                pedido.getCabec().setTABPRECO("");

                pedido.getCabec().set_TabPreco("");

                pedido.getCabec().setCODIGOENT("");

            }

            if (field.equals("CODIGOENT")){

                pedido.getCabec().set_ClienteEntRazao("");
            }

            pedido.Validar();

            cabec_refresh();

            cabec_onClick();

            return true;
        }

    }


    private class ClickDetClear implements View.OnLongClickListener{

        private String      field;

        public ClickDetClear(String field) {

            this.field = field;

        }




        @Override
        public boolean onLongClick(View v) {


            item_edicao.setFieldByName(field, "");

            if (field.equals("CODVERBA")){

                if (item_edicao.getACORDO().trim().equals("")){
                    item_edicao.setCODVERBA("");
                    item_edicao.set_Verba("");
                }
            }

            if (field.equals("CODVERBA2")){

                if (item_edicao.getACORDO().trim().equals("")){
                    item_edicao.setCODVERBA2("");
                    item_edicao.set_Verba2("");
                }
            }

            if (field.equals("ACORDO")){

                item_edicao.setCODVERBA("");
                item_edicao.set_Verba("");
                item_edicao.setCODVERBA2("");
                item_edicao.set_Verba2("");
                item_edicao.set_Acordo("");

            }



            pedido.getLsDetalhe().set(Integer.valueOf(item_edicao.getITEM())-1,item_edicao);

            pedido.recalculo();

            pedido.Validar();


            try {

                item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

            } catch (ExceptionItemProduto exceptionItemProduto1) {

                exceptionItemProduto1.printStackTrace();

            }


            detalhe_refresh();

            cabec_refresh();


            return true;
        }

    }

    private class ClickDet implements View.OnClickListener{



        public void onClick(View v) {




			/*
			 *
			 *  Rejeita Click
			 *
			 *
			 */

            if (item_edicao.getPRODUTO().equals("")) { //item sem produto

                if ((v.getId() == R.id.lbl_qtd_005) || (v.getId() == R.id.lbl_preco_005) || (v.getId() == R.id.lbl_desconto_005) || (v.getId() == R.id.lbl_qtdbonif_005)) {

                    return;

                }
            }

            if (item_edicao.getQTD().compareTo(0f) == 0){

                if ((v.getId() == R.id.lbl_preco_005) || (v.getId() == R.id.lbl_desconto_005) ) {

                    return;

                }

            }

            if ( !item_edicao.getACORDO().trim().equals("") || (item_edicao.getDESCVER() == 0  && item_edicao.getBONIQTD() == 0))  {

                if ( (v.getId() == R.id.lbl_verba_005) ){

                    return ;
                }

            }

            if ( (item_edicao.getBONIQTD() == 0))  {

                if ( (v.getId() == R.id.lbl_verba2_005) ){

                    return ;
                }

            }

            if ( !item_edicao.getCODVERBA().trim().equals("") || (item_edicao.getDESCVER() == 0  && item_edicao.getBONIQTD() == 0) ){

                if ( (v.getId() == R.id.lbl_acordo_005) ) {

                    return ;
                }

            }

            switch (v.getId()) {


                case R.id.item_trash_005:{

                    try {

                        final Dialog dialog = new Dialog(LancaPedidoActivity.this);

                        dialog.setContentView(R.layout.dlglibped);

                        dialog.setTitle("EXCLUSÃO DE ITEM");

                        final Button confirmar = (Button) dialog.findViewById(R.id.btn_040_ok);
                        final Button cancelar = (Button) dialog.findViewById(R.id.btn_040_can);
                        final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);
                        final TextView tvtexto2 = (TextView) dialog.findViewById(R.id.txt_040_texto2);

                        tvtexto1.setText("CONFIRMA A EXCLUSÃO DO ITEM:");
                        tvtexto2.setText(item_edicao.getNRO()+"-"+item_edicao.get_Produto());

                        cancelar.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {

                                //finaliza o dialog
                                dialog.dismiss();

                            }
                        });

                        confirmar.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {

                                try

                                {


                                    item_edicao.setNRO("");

                                    item_edicao.setQTD(0f);

                                    item_edicao.setBONIQTD(0f);

                                    pedido.getLsDetalhe().set(Integer.valueOf(item_edicao.getITEM()) - 1, item_edicao);


                                    try {

                                        item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

                                    } catch (ExceptionItemProduto exceptionItemProduto) {

                                        //
                                    }

                                    item_edicao = new PedidoDetMb();

                                    pedido.recalculo();

                                    pedido.Validar();

                                    cabec_refresh();

                                    cabec_onClick();

                                    detalhe_refresh();

                                    adapter.refresh();


                                } catch (Exception e) {

                                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                                }


                                //finaliza o dialog
                                dialog.dismiss();


                            }

                        });


                        dialog.show();


                    } catch (Exception e) {

                        toast(e.getMessage());

                    }



                    break;
                }
                case R.id.lbl_produto_005:
                {
//                    Intent i = new Intent(getBaseContext(),Help20Activity.class);
//                    Bundle params = new Bundle();
//                    params.putString("ARQUIVO"     , "PRODUTO");
//                    params.putString("TITULO"      , "CADASTRO DE PRODUTOS");
//                    params.putString("MULTICHOICE" , "N");
//                    params.putString("ALIAS"       , "PRODUTOPRECO");
//                    params.putString("ALIASVALUES" , adapter.pedido.getCabec().getTABPRECO().trim()+"|00"+adapter.pedido.getCabec().getTIPO().trim());
//                    i.putExtras(params);
//                    startActivityForResult(i,HelpInformation.HelpProduto );
//
//                    break;
                }

                case R.id.lbl_qtd_005:{

                    //Amarra qtd com tipo


                    if (pedido.getCabec().getTIPO().equals("003") || pedido.getCabec().getTIPO().equals("011")){

                        //nao ativo get

                    } else {

                        ClickGetDados("Qual A Qtd Do Item ?", "QTD", 6);
                    }
                    break;
                }

                case R.id.lbl_preco_005:{

                    if (item_edicao.getSIMULADOR().trim().isEmpty()) ClickGetDadosPreco("Qual O Preço Unit. ?","PRCVEN");

                    break;
                }

                case R.id.lbl_desconto_005:{

                    if (item_edicao.getSIMULADOR().trim().isEmpty()) ClickGetDados("Qual Perc. Desconto ?","DESCON",6);

                    break;
                }


                case R.id.lbl_qtdbonif_005:{


                    //Amarra qtd com tipo



                    if (pedido.getCabec().getTIPO().equals("005") || pedido.getCabec().getTIPO().equals("006") || pedido.getCabec().getTIPO().equals("007")){

                        // nao ativa

                    } else {

                        ClickGetDados("Qual A Qtd Do Item ?","BONIQTD",6);

                    }


                    break;
                }


                case R.id.lbl_verba_005:{

                    if (item_edicao.getSIMULADOR().trim().isEmpty()) {
                        if (item_edicao.getACORDO().trim().equals("") && (item_edicao.getDESCVER() > 0 || item_edicao.getBONIQTD() > 0)) {

                            Intent i = new Intent(getBaseContext(), Help20Activity.class);
                            Bundle params = new Bundle();
                            params.putString("ARQUIVO", "VERBA");
                            params.putString("TITULO", "CADASTRO DE VERBA");
                            params.putString("MULTICHOICE", "N");
                            params.putString("ALIAS", "VERBA");
                            params.putString("ALIASVALUES", "");
                            i.putExtras(params);
                            startActivityForResult(i, HelpInformation.HelpVerba);

                        }
                    }
                    break;
                }
                case R.id.lbl_verba2_005:{


                    if (item_edicao.getSIMULADOR().trim().isEmpty()) {
                        Intent i = new Intent(getBaseContext(), Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO", "VERBA");
                        params.putString("TITULO", "CADASTRO DE VERBA");
                        params.putString("MULTICHOICE", "N");
                        params.putString("ALIAS", "VERBA");
                        params.putString("ALIASVALUES", "");
                        i.putExtras(params);
                        startActivityForResult(i, HelpInformation.HelpVerbaBonif);
                    }

                    break;
                }
                case R.id.lbl_acordo_005:{
                    if (item_edicao.getSIMULADOR().trim().isEmpty()) {
                        if (item_edicao.get_Verba().trim().equals("") && (item_edicao.getDESCVER() > 0 || item_edicao.getBONIQTD() > 0)) {
                            Intent i = new Intent(getBaseContext(), Help20Activity.class);
                            Bundle params = new Bundle();
                            params.putString("ARQUIVO", "ACORDO");
                            params.putString("TITULO", "CADASTRO DE ACORDOS");
                            params.putString("MULTICHOICE", "N");
                            params.putString("ALIAS", "ACORDOCLIENTE");
                            params.putString("ALIASVALUES", pedido.getCabec().getCODIGOFAT() + "|" + pedido.getCabec().getLOJAFAT());
                            i.putExtras(params);
                            startActivityForResult(i, HelpInformation.HelpAcordo);
                        }
                    }
                    break;
                }

                case R.id.lbl_simulador_005:{
                    {
                        Intent i = new Intent(getBaseContext(),Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO"     , "SIMULADOR");
                        params.putString("TITULO"      , "SIMULADORES DISPONÍVEIS");
                        params.putString("MULTICHOICE" , "N");
                        params.putString("ALIAS"       , "CODSIMULADOR");
                        params.putString("ALIASVALUES" , item_edicao.getPRODUTO()+"|"+pedido.getCabec().getCODIGOFAT()+"|"+pedido.getCabec().getLOJAFAT());
                        i.putExtras(params);
                        startActivityForResult(i,HelpInformation.HelpSimulador );

                        break;
                    }

                }


                default:

                    break;
            }


        }
    }

    public void ClickGetDados(final String Titulo, final String FieldName){

        final PedidoCabMb obj  = pedido.getCabec();

        final Dialog dialog = new Dialog(LancaPedidoActivity.this);

        dialog.setContentView(R.layout.getdados);

        dialog.setTitle("Favor Digitar O Informação");

        final TextView titulo = (TextView) dialog.findViewById(R.id.txt_campo_116);

        titulo.setText(Titulo);

        final EditText campo = (EditText) dialog.findViewById(R.id.edit_campo_116);

        if (FieldName.equals("FDSPREVISTO")){

            campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            try {


                campo.setText(String.valueOf((Float) obj.getFieldByName(FieldName)));


            } catch (Exception e){

                campo.setText("");

            }

        } else {


            campo.setText((String)obj.getFieldByName(FieldName));

        }



        final Button confirmar    = (Button) dialog.findViewById(R.id.bt_confirma_116);
        final Button cancelar     = (Button) dialog.findViewById(R.id.bt_cancela_116);

        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try

                {

                    if (FieldName.equals("FDSPREVISTO")) {

                        try {

                            obj.setFieldByName(FieldName, Float.valueOf(campo.getText().toString()));

                            pedido.setClienteEntrega(obj.getCODIGOENT(),obj.getLOJAENT());

                        } catch (Exception e){

                            obj.setFieldByName(FieldName,0f);
                        }
                    } else {

                        obj.setFieldByName(FieldName, campo.getText().toString());

                    }

                    setLSProdutos();

                    if (lv.getVisibility() == View.VISIBLE) {

                        adapter.refresh();

                    }


                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                }


                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

                pedido.Validar();

                cabec_refresh();

                cabec_onClick();

                detalhe_refresh();

                detalhe_onClick();

            }

        });

        dialog.show();

    }

    public void ClickGetDados(final String Titulo, final String FieldName, int tipo){

        final Dialog dialog = new Dialog(LancaPedidoActivity.this);

        dialog.setContentView(R.layout.getdados);

        dialog.setTitle("Favor Digitar O Informação");

        final TextView titulo = (TextView) dialog.findViewById(R.id.txt_campo_116);

        titulo.setText(Titulo);

        final EditText campo = (EditText) dialog.findViewById(R.id.edit_campo_116);


        switch (tipo) {

            case 0:  //texto maiusculo

                campo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                break;

            case 1:  //numerico

                campo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                break;

            case 2: //data

                campo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                campo.addTextChangedListener(Mask.insert("##/##/####", campo));

                break;

            case 3: //telefone

                campo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                break;

            case 4: //url

                campo.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

                break;

            case 5: //email

                campo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                break;

            case 6: //FLOAT

                campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                break;

            case 7: //cnpj

                campo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                campo.addTextChangedListener(Mask.insert("##.###.###/####-##", campo));

                break;

            case 8: //CEP

                campo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                campo.addTextChangedListener(Mask.insert("#####-###", campo));

                break;

            case 9: //DATA

                campo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                campo.addTextChangedListener(Mask.insert("##/##/####", campo));

                campo.setHint("dd/mm/aaaa");

                break;

            case 10:

                campo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                campo.addTextChangedListener(Mask.insert("###.###.###.####", campo));

                break;

            default:

                campo.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                break;
        }


        if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._string)){

            campo.setText((String)item_edicao.getFieldByName(FieldName));

        }

        if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._float)){

            Float value = (Float) item_edicao.getFieldByName(FieldName);


            campo.setText(format_02.format(value));


            if (FieldName.equals("DESCON")){

                campo.setText(format_05.format(value));

            }


        }

        if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._long)){


            Long value = (Long) item_edicao.getFieldByName(FieldName);

            campo.setText(format_02.format(value));


        }

        if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._integer)){

            Integer value = (Integer) item_edicao.getFieldByName(FieldName);

            campo.setText(format_02.format(value));


        }


        final Button confirmar    = (Button) dialog.findViewById(R.id.bt_confirma_116);
        final Button cancelar     = (Button) dialog.findViewById(R.id.bt_cancela_116);

        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try

                {

                    if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._string)){

                        campo.setText((String)item_edicao.getFieldByName(FieldName));

                    }

                    if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._float)){

                        Float value = 0f;

                        try
                        {
                            value = Float.valueOf(campo.getText().toString().replaceAll(",", "."));

                        } catch (Exception e){

                            value = 0f;

                        }


                        if (FieldName.equals("DESCON")){


                            item_edicao.setPRCVEN(item_edicao.getPRECOFORMACAO());

                        }

                        item_edicao.setFieldByName(FieldName, value);

                        if (FieldName.equals("DESCON")){

                            pedido.validarItem(item_edicao);

                        }


                    }

                    if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._long)){

                        Long value = 0l;

                        try
                        {
                            value = Long.valueOf(campo.getText().toString());

                        } catch (Exception e){

                            value = 0l;

                        }

                        item_edicao.setFieldByName(FieldName, value);

                    }

                    if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._integer)){

                        Integer value = 0;

                        try
                        {
                            value = Integer.valueOf(campo.getText().toString());

                        } catch (Exception e){

                            value = 0;

                        }

                        item_edicao.setFieldByName(FieldName, value);

                    }


                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                }


                if (FieldName.equals("QTD") || FieldName.equals("BONIQTD")){

                    item_edicao.setNRO(pedido.getCabec().getNRO());

                }

                pedido.getLsDetalhe().set(Integer.valueOf(item_edicao.getITEM()) - 1, item_edicao);

                try {

                    item_edicao = pedido.getItemBySelf(item_edicao.getITEM());


                } catch (ExceptionItemProduto exceptionItemProduto) {

                    //
                }

                pedido.recalculo();

                pedido.Validar();

                cabec_refresh();

                cabec_onClick();

                detalhe_refresh();

                adapter.refresh();


                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

            }

        });

        dialog.show();

    }

    public void ClickGetDadosPreco(final String Titulo, final String FieldName){

        final Dialog dialog = new Dialog(LancaPedidoActivity.this);

        dialog.setContentView(R.layout.getprcven);

        dialog.setTitle("Favor Digitar O Informação");

        final TextView titulo = (TextView) dialog.findViewById(R.id.txt_campo_118);

        titulo.setText(Titulo);

        final EditText campo = (EditText) dialog.findViewById(R.id.edit_campo_118);

        final TextView preco = (TextView) dialog.findViewById(R.id.txt_preco_118);

        preco.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Float value = 0f;

                try {

                    value = Float.valueOf(preco.getText().toString().replaceAll(",", "."));

                } catch (Exception e) {

                    value = 0f;

                }

                item_edicao.setDESCON(0f);

                pedido.ajustaPrcVenByPreco(item_edicao, value);

                item_edicao.setFieldByName(FieldName, value);

                dialog.dismiss();

                pedido.getLsDetalhe().set(Integer.valueOf(item_edicao.getITEM())-1,item_edicao);

                pedido.recalculo();

                pedido.Validar();

                try {

                    item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

                } catch (ExceptionItemProduto exceptionItemProduto) {

                    //
                }

                cabec_refresh();

                cabec_onClick();

                detalhe_refresh();

                adapter.refresh();



            }
        });


        campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        preco.setText(format_04.format(item_edicao.getPRECOFORMACAO()));



        if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._float)){



            Float value = (Float) item_edicao.getFieldByName(FieldName);

            campo.setText(format_04.format(value));

        }



        final Button confirmar    = (Button) dialog.findViewById(R.id.bt_confirma_118);
        final Button cancelar     = (Button) dialog.findViewById(R.id.bt_cancela_118);

        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try

                {

                    if (item_edicao.getTypeByName(FieldName).equals(ObjRegister._float)){
                        Float precoMaximo = item_edicao.getPRECOFORMACAO() * ( 1 + (item_edicao.getACRESCIMOMAIS()/100) );
                        Float value = 0f;

                        try {
                            value = Float.valueOf(campo.getText().toString().replaceAll(",", "."));

                        } catch (Exception e){

                            value = 0f;

                        }

                        if ( (Float.compare(value, precoMaximo)) > 0 ){


                            Toast.makeText(getBaseContext(), "Preço Não Pode Ser Maior Que: "+format_02.format(precoMaximo),Toast.LENGTH_LONG).show();


                        } else {

                            item_edicao.setDESCON(0f);

                            pedido.ajustaPrcVenByPreco(item_edicao, value);

                            item_edicao.setFieldByName(FieldName, value);


                        }
                        pedido.validarItem(item_edicao);

                        pedido.getLsDetalhe().set(Integer.valueOf(item_edicao.getITEM()) - 1, item_edicao);

                        pedido.recalculo();

                        pedido.Validar();

                        try {

                            item_edicao = pedido.getItemBySelf(item_edicao.getITEM());

                        } catch (ExceptionItemProduto exceptionItemProduto) {

                            //
                        }


                    }
                } catch  (ExceptionItemProduto e){

                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                    pedido.recalculo();

                    pedido.Validar();



                }
                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

                cabec_refresh();

                cabec_onClick();

                detalhe_refresh();

                adapter.refresh();


            }

        });

        dialog.show();

    }


    public void ClickGetDadosRetira(final String Titulo, final String FieldName){

        final Dialog dialog = new Dialog(LancaPedidoActivity.this);

        dialog.setContentView(R.layout.getdescret);

        dialog.setTitle("Favor Digitar O Informação");

        final Button bt_retira_cancela_119    = (Button) dialog.findViewById(R.id.bt_retira_cancela_119);
        final Button bt_retira_sim_119    = (Button) dialog.findViewById(R.id.bt_retira_sim_119);
        final Button bt_retira_nao_119     = (Button) dialog.findViewById(R.id.bt_retira_nao_119);


        bt_retira_cancela_119.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

            }
        });
        bt_retira_nao_119.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Float value = 0f;


            }
        });

        bt_retira_sim_119.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

            }

        });

        dialog.show();

    }

    private Handler mHandlerLoad=new Handler(){

        @Override
        public void handleMessage(Message msg){


            if (msg.getData().getString("erro").equals("---")) {

                lv.setVisibility(View.VISIBLE);

                lsProdutos = new ArrayList<>();

                lsProdutos.add("CABEC");

                lsProdutos.add(new NoDataProgress("Processando..."));

                adapter = new Adapter(LancaPedidoActivity.this, lsProdutos);

                lv.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }

            if (msg.getData().getString("erro").equals("PRONTO")){

                try {

                    if (lsProdutos.size() == 0){

                        toast("NENHUM PRODUTO ENCONTRADO !!!");

                    }

                    if (operacao.equals("NOVO")) {

                        adapter = new Adapter(LancaPedidoActivity.this, lsProdutos);

                        lv.setAdapter(adapter);

                        adapter.notifyDataSetChanged();

                        produto_filtro();

                    }

                    if (operacao.equals("ALTERACAO") ){

                        inc_pedidomb_det_row.setVisibility(View.VISIBLE);

                        inc_pedidomb_filtro.setVisibility(View.VISIBLE);

                        adapter = new Adapter(LancaPedidoActivity.this, lsProdutos);

                        lv.setAdapter(adapter);

                        adapter.notifyDataSetChanged();

                        produto_filtro();


                    }

                    if (operacao.equals("VIEW") ) {

                        inc_pedidomb_det_row.setVisibility(View.GONE);

                        inc_pedidomb_filtro.setVisibility(View.VISIBLE);

                        adapter = new Adapter(LancaPedidoActivity.this, lsProdutos);

                        lv.setAdapter(adapter);

                        produto_filtro();

                    }

                    if (lsProdutos.size() != 0){

                        sp_tipopedido_001.setEnabled(false);

                    }


                } catch ( Exception e){

                    toast(e.getMessage());

                }
            }

        }

    };

    private Handler mHandlerFirst=new Handler(){

        @Override
        public void handleMessage(Message msg){

            if (msg.getData().getString("erro").equals("PRONTO")){

                cabec_init();

                cabec_refresh();

                cabec_onClick();

                if (!operacao.equals("NOVO")) {

                    loadthread = new LOADThread(mHandlerLoad);

                    loadthread.start();
                }

            }

        }

    };




    private Handler mHandlerTrasmissao=new Handler(){

        @Override
        public void handleMessage(Message msg){


            if (msg.getData().getString("erro").equals("---")) {


                toast("Começou !!");

            }

            if (msg.getData().getString("erro").equals("PRONTO")){

                toast("Acabou");

            }


        }




    };


    private void transmitir(){

        callbacktrasmissao = new callbackTransmissao();

        AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerTrasmissao, getBaseContext(), App.user, "PUTSALESORDERMB", "PUTSALESORDERMB", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_CUSTOM , config, null,-1);

        acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

        acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

        SoapObject soapPedido = new SoapObject("", "PEDIDO");

        soapPedido.addProperty("PVCABEC", pedido.getCabec());

        SoapObject soapPVITENS  = new SoapObject("", "PVITENS");

        for (PedidoDetMb item : pedido.getLsDetalhe()){

            if (!item.getNRO().isEmpty()) {

                soapPVITENS.addProperty("WSPEDIDODETMB", item);

            }

        }

        soapPedido.addSoapObject(soapPVITENS);

        acessoWeb.addSoapObject(soapPedido);

        acessoWeb.addObjeto("PedCabMb", new PedidoCabMb().getClass());

        acessoWeb.addObjeto("PedDetMb",new PedidoDetMb().getClass());

        acessoWeb.start();

    }

    //INNER CLASS
    private class callbackTransmissao extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            String cerro       = result.getPropertyAsString("CERRO");
            String cmsgerro    = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){


            } else {

            }

        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

        }
    }

    private class LOADThread extends Thread {

        private Handler mHandler;

        private Bundle params = new Bundle();

        public LOADThread(Handler handler) {

            super();

            mHandler = handler;

        }

        @Override

        public void run() {




            try {

                params.putString("erro"   , "---");
                params.putString("msgerro", "");
                sendmsg(params);

                loadItens();

            } catch (Exception e) {

                //

            }

            params.putString("erro"   , "PRONTO");
            params.putString("msgerro", "");
            sendmsg(params);

        }


        public void sendmsg(Bundle value) {

            if (value != null) {
                Message msgObj = mHandler.obtainMessage();
                msgObj.setData(value);
                mHandler.sendMessage(msgObj);
            }

        }





    }

    private class LOADFirst extends Thread {

        private Handler mHandler;

        private Bundle params = new Bundle();

        public LOADFirst(Handler handler) {

            super();

            mHandler = handler;

        }

        @Override

        public void run() {

            try {

                if (operacao.equals("NOVO")){

                    pedido = new PedidoBusiness();

                    pedido.Novo();

                    pedido.getCabec().setNRO(getNewID());

                    if (!CODIGO.isEmpty()){

                        pedido.setCliente(CODIGO,LOJA);

                        pedido.Validar();

                    }

                } else {

                    pedido = new PedidoBusiness();

                    pedido.load(nropedido);

                }


            } catch (Exception e){



            }

            params.putString("erro"   , "PRONTO");

            params.putString("msgerro", "");

            sendmsg(params);


        }


        public void sendmsg(Bundle value) {

            if (value != null) {
                Message msgObj = mHandler.obtainMessage();
                msgObj.setData(value);
                mHandler.sendMessage(msgObj);
            }

        }





    }

    private class defaultAdapter extends ArrayAdapter {

        private int escolha = 0;

        private List<String[]> lista;

        private String label;

        private boolean valido;

        private boolean isInicializacao = true;

        public defaultAdapter(Context context, int textViewResourceId, List<String[]> objects,String label, boolean valido) {

            super(context, textViewResourceId, objects);

            this.lista = objects;

            this.label = label;

            this.valido = valido;
        }


        public String getOpcao(int pos){


            if ( (pos < this.lista.size() )){


                return lista.get(pos)[0];

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

            LayoutInflater inflater =  getLayoutInflater();

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

            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            tvlabel.setText(this.label);

            if (this.label.isEmpty()){

                tvlabel.setVisibility(View.GONE);

            }

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setText(obj);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            if (valido){

                img.setVisibility(View.GONE);

            }   else
            {
                img.setImageResource(R.drawable.erro_20_vermelho);
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

        DecimalFormat format_02     = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        private Context context;
        private String Filtro_grupo = "000";
        private String Filtro_marca = "000";
        private String SubFiltro    = "01" ;
        private boolean viewPedido  = false;


        final int ITEM_VIEW_CABEC            = 0;
        final int ITEM_VIEW_ITEM             = 1;
        final int ITEM_VIEW_VENDA            = 2;
        final int ITEM_VIEW_NO_DATA          = 3;
        final int ITEM_VIEW_NO_DATA_PROGRESS = 4;
        final int ITEM_VIEW_COUNT            = 5;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos = pObjects;

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void setViewPedido(boolean value){

            viewPedido = value;

        }

        public String getSubFiltro() {
            return SubFiltro;
        }

        public void setSubFiltro(String subFiltro) {
            SubFiltro = subFiltro;
        }

        public boolean isViewPedido() {
            return viewPedido;
        }

        public void setFilter(String grupo,String marca){

            this.Filtro_grupo = grupo;

            this.Filtro_marca = marca;

            this.lsObjetos = filtro();

            notifyDataSetChanged();


        }

        public void refresh(){

            this.lsObjetos = filtro();

            notifyDataSetChanged();

        }

        private List<Object> filtro(){

            List<Object> retorno = new ArrayList<>();

            retorno.add("CABEC");

            if (viewPedido){

                Filtro_grupo = "000";

                Filtro_marca = "000";

                for(Object obj : lsProdutos){

                    if (obj instanceof PedidoDetMb) {

                        if (!((PedidoDetMb) obj).getNRO().isEmpty()) {

                            retorno.add(obj);

                        }

                    }
                }

                return retorno;

            }

            if (Filtro_grupo.equals("000") && Filtro_marca.equals("000")){

                for(Object obj : lsProdutos){

                    if (obj instanceof PedidoDetMb){

                        if (SubFiltro.equals("01")) {

                            retorno.add(obj);
                        }
                        if (SubFiltro.equals("02") && !((PedidoDetMb) obj).getNRO().isEmpty() && ((PedidoDetMb) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }
                        if (SubFiltro.equals("03") && !((PedidoDetMb) obj).getNRO().isEmpty() && !((PedidoDetMb) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }

                    }

                }

                return retorno;

            }

            for(Object obj : lsProdutos){

                if (obj instanceof PedidoDetMb){

                    if ((!Filtro_grupo.equals("000") || (!Filtro_marca.equals("000")) )){

                        if ( ( Filtro_grupo.equals("000") || ((PedidoDetMb) obj).get_Grupo().equals(Filtro_grupo) ) && ( Filtro_marca.equals("000") || ((PedidoDetMb) obj).get_Marca().equals(Filtro_marca) ) ) {

                            if (SubFiltro.equals("01")){

                                retorno.add(obj);
                            }
                            if (SubFiltro.equals("02") && !((PedidoDetMb) obj).getNRO().isEmpty() && ((PedidoDetMb) obj).getSTATUS().equals("3")){

                                retorno.add(obj);

                            }
                            if (SubFiltro.equals("03") && !((PedidoDetMb) obj).getNRO().isEmpty() && !((PedidoDetMb) obj).getSTATUS().equals("3")){

                                retorno.add(obj);

                            }

                        }

                    }


                } else {

                    if (SubFiltro.equals("01")){

                        retorno.add(obj);
                    }
                    if (SubFiltro.equals("02") && ((PedidoDetMb) obj).getSTATUS().equals("3")){

                        retorno.add(obj);

                    }
                    if (SubFiltro.equals("03") && !((PedidoDetMb) obj).getSTATUS().equals("3")){

                        retorno.add(obj);

                    }



                }

            }

            return retorno;

        }




        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {


                if (obj instanceof PedidoDetMb) {

                    qtd = qtd + 1;

                }

            }


            retorno = "Total de Produtos: " + String.valueOf(qtd);

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

            if (lsObjetos.get(position) instanceof PedidoDetMb) {

                if ( !isViewPedido() ) {

                    retorno = ITEM_VIEW_ITEM;

                } else {

                    retorno = ITEM_VIEW_VENDA;

                }

            }

            if (lsObjetos.get(position) instanceof NoData) {

                retorno = ITEM_VIEW_NO_DATA;

            }

            if (lsObjetos.get(position) instanceof NoDataProgress) {

                retorno = ITEM_VIEW_NO_DATA_PROGRESS;

            }

            return retorno;


        }

        @Override
        public boolean isEnabled(int position) {
            boolean retorno = false;
            return retorno;
        }

        public void deleteitem(int position) {

            this.lsObjetos.remove(position);
            notifyDataSetChanged();

            return;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            try {

                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_ITEM:

                            convertView = inflater.inflate(R.layout.pedidomb_det_item_row, null);

                            break;

                        case ITEM_VIEW_VENDA:

                            convertView = inflater.inflate(R.layout.pedidomb_det__p_2_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;
                        case ITEM_VIEW_NO_DATA_PROGRESS:

                            convertView = inflater.inflate(R.layout.progress_data_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_ITEM: {

                        final PedidoDetMb obj = (PedidoDetMb) lsObjetos.get(pos);

                        View view                   = (View)     convertView.findViewById(R.id.pedidomb_det_item_row);
                        TextView lbl_produto_007    = (TextView) convertView.findViewById(R.id.lbl_produto_007);
                        TextView lbl_descricao_007  = (TextView) convertView.findViewById(R.id.lbl_descricao_007);
                        TextView lbl_unid_007       = (TextView) convertView.findViewById(R.id.lbl_unid_007);
                        ImageView img_bola_007      = (ImageView) convertView.findViewById(R.id.img_bola_007);

                        lbl_produto_007.setText(obj.getPRODUTO());
                        lbl_descricao_007.setText(obj.get_Produto());
                        lbl_unid_007.setText(obj.getUM());


                        if ((item_edicao != null) && (obj.getITEM().equals(item_edicao.getITEM()))) {

                            view.setBackgroundResource(R.color.grey);

                        } else {

                            if (obj.getNRO().isEmpty()) {

                                view.setBackgroundResource(R.drawable.fundo);

                            } else {

                                view.setBackgroundResource(R.color.md_yellow_400);
                            }
                        }
                        if ((item_edicao != null) && (obj.getITEM().equals(item_edicao.getITEM()))){

                            img_bola_007.setImageResource(R.drawable.ic_action_flag_amarela);

                        } else {
                            if (obj.getSTATUS().equals("3")) {

                                img_bola_007.setImageResource(R.drawable.bola_verde);

                            } else {

                                img_bola_007.setImageResource(R.drawable.bola_vermelha);
                            }
                        }
                        lbl_descricao_007.setOnClickListener(new clickedicao(obj));
                        lbl_unid_007.setOnClickListener(new clickedicao(obj));
                        img_bola_007.setOnClickListener(new clickedicao(obj));

                        break;
                    }

                    case ITEM_VIEW_VENDA: {

                        final PedidoDetMb obj = (PedidoDetMb) lsObjetos.get(pos);

                        TextView txt_produto_006                = (TextView) convertView.findViewById(R.id.txt_produto_006);
                        TextView txt_status_006                 = (TextView) convertView.findViewById(R.id.txt_status_006);

                        TextView txt_mensagem_006               = (TextView) convertView.findViewById(R.id.txt_mensagem_006);

                        TextView txt_qtd_006                    = (TextView) convertView.findViewById(R.id.txt_qtd_006);
                        TextView txt_prcven_006                 = (TextView) convertView.findViewById(R.id.txt_prcven_006);
                        TextView txt_desconto_006               = (TextView) convertView.findViewById(R.id.txt_desconto_006);
                        TextView txt_desconto_verba_006         = (TextView) convertView.findViewById(R.id.txt_desconto_verba_006);
                        TextView txt_acordo_006                 = (TextView) convertView.findViewById(R.id.txt_acordo_006);
                        TextView txt_total_006                  = (TextView) convertView.findViewById(R.id.txt_total_006);


                        TextView txt_qtd_boni_006               = (TextView) convertView.findViewById(R.id.txt_qtd_boni_006);
                        TextView txt_prcven_boni_006            = (TextView) convertView.findViewById(R.id.txt_prcven_boni_006);
                        TextView txt_total_boni_006             = (TextView) convertView.findViewById(R.id.txt_total_boni_006);

                        TextView txt_descricao_verba_006        = (TextView) convertView.findViewById(R.id.txt_descricao_verba_006);
                        TextView txt_descricao_acordo_006       = (TextView) convertView.findViewById(R.id.txt_descricao_acordo_006);

                        txt_produto_006.setText(obj.getPRODUTO().trim()+"-"+obj.get_Produto().trim());
                        txt_status_006.setText(obj.get_Status());

                        if (obj.getSTATUS().equals("3")){

                            txt_status_006.setTextColor(Color.GREEN);

                        } else {

                            txt_status_006.setTextColor(Color.RED);

                        }

                        txt_mensagem_006.setText(obj.getMENSAGEM());

                        txt_qtd_006.setText(format_02.format(obj.getQTD()));
                        txt_prcven_006.setText(format_04.format(obj.getPRCVEN()));
                        txt_desconto_006.setText(format_05.format(obj.getDESCON()));
                        txt_desconto_verba_006.setText(format_05.format(obj.getDESCVER()));

                        txt_total_006.setText(format_02.format(obj.getTOTAL()));

                        txt_qtd_boni_006.setText(format_02.format(obj.getBONIQTD()));
                        txt_prcven_boni_006.setText(format_04.format(obj.getBONIPREC()));
                        txt_total_boni_006.setText(format_02.format(obj.getBONITOTAL()));
                        txt_acordo_006.setText(obj.get_Acordo());
                        txt_descricao_verba_006.setText(obj.get_Verba());
                        txt_descricao_acordo_006.setText(obj.get_Acordo());




                        break;
                    }

                    case ITEM_VIEW_NO_DATA: {

                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                        tvTexto.setText(obj.getMensagem());

                        break;

                    }

                    case ITEM_VIEW_NO_DATA_PROGRESS: {

                        final NoDataProgress obj = (NoDataProgress) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.progress_data_row_texto);

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



