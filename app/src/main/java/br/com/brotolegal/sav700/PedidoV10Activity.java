package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.com.brotolegal.sav700.help20.Help20Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.CotaDAO;
import br.com.brotolegal.savdatabase.dao.MetaDAO;
import br.com.brotolegal.savdatabase.dao.PedDetTvsDAO;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.dao.SimuladorDAO;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.entities.CondPagto;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Cota;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.NoDataProgress;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.Simulador;
import br.com.brotolegal.savdatabase.entities.TabPrecoCabec;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionItemProduto;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionLoadPedido;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionSavePedido;
import br.com.brotolegal.savdatabase.regrasdenegocio.MetaCategoria;
import br.com.brotolegal.savdatabase.regrasdenegocio.PedidoBusinessV10;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PLAY_SERVICES_RESOLUTION_REQUEST;

public class PedidoV10Activity extends AppCompatActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static PedidoBusinessV10 pedido;
    private String tipoPedidoSemFardos = "005#006#007";

    static {

        try {

            pedido = new PedidoBusinessV10();

        } catch (Exception e) {

            pedido = null;

            Log.i("STATIC", e.getMessage());
        }


    }

    private static final long ONE_MIN                 = 1000 * 60;
    private static final long TWO_MIN                 = ONE_MIN * 2;
    private static final long FIVE_MIN                = ONE_MIN * 5;
    private static final long POLLING_FREQ            = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ     = 1000 * 5;
    private static final float MIN_ACCURACY           = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;

    private LocationRequest mLocationRequest;

    private Location mBestReading;

    private GoogleApiClient mGoogleApiClient;

    private Dialog dialog;

    private int mPositionClicked;

    private Bundle savebundle;

    private Toolbar toolbar;

    private ListView lv;

    private View ll_lanca_pedido_cabec;

    private View ll_lanca_pedido_detalhe;

    private View linha_troca_005;

    private View linha_dados_005;

    private View inc_pedidomb_filtro;

    private Spinner sp_view_088;

    private Spinner spCategoria;

    private Spinner spMarcas;

    private defaultAdapter tipoadapter;

    private defaultAdapter entregaadapter;

    private defaultAdapter retiraadapter;

    private defaultAdapter condadapter;

    private defaultAdapter tabprecoadapter;

    private List<Object> lista = new ArrayList<>();

    private List<Object> lsMetaCategoria = new ArrayList<>();

    private String HelpCliente = "";

    private String PosEdicao = "";

    private defaultAdapter categoriaadapter;

    private defaultAdapter marcaadapter;

    private defaultAdapter viewadapter;

    private Adapter adapter;

    private AdapterMetas adapterMetas;

    private String Importados = "001021,003004,008600,001022,002011";

    private DecimalFormat format_02 = new DecimalFormat(",##0.00");
    private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
    private DecimalFormat format_05 = new DecimalFormat(",##0.00000");


    /*
    *
    *  icones cabecalho
    *
    */

    private ImageView   item_max_min_001;
    private ImageView   item_trash_001;
    private ImageView   item_trash_005;
    private ImageButton item_details_001;
    private ImageView   item_volta_cabec_005;
    private TextView    txt_upload_001;
    private ImageView   bt_email_001;
    private TextView    txt_email_001;
    private ImageView   im_email_check_001;

    /*
    * Defines os campos cabecalho
    *
    */

    private TextView lbl_fardos_previstos_001;
    private TextView lbl_qtd_entrega_001;
    private TextView lbl_cliente_001;
    private TextView lbl_pedidocliente_001;
    private Spinner sp_tipopedido_001;
    private TextView lbl_clienteentrega_001;
    private Spinner sp_entrega_001;
    private Spinner sp_condpagto_001;
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
    private Spinner sp_tabpreco_001;
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
    private TextView txt_qtd_entrega_001;
    private TextView txt_peso_liquido_001;
    private TextView txt_peso_bruto_001;
    private ListView lv_metas;
    //detalhe

    private TextView lbl_total_pedido_005;
    private TextView lbl_total_fd_previstos_005;
    private TextView lbl_total_fd_realizados_005;
    private TextView lbl_total_aproveitamento_pol_005;
    private TextView lbl_total_saldo_aproveitamento_pol_005;
    private TextView lbl_peso_liquido_005;
    private TextView lbl_peso_bruto_005;

    private TextView txt_id_005;
    private TextView txt_item_005;
    private TextView txt_status_005;
    private TextView txt_erro_005;
    private TextView txt_produto_005;
    private TextView lbl_qtd_005;
    private TextView lbl_preco_005;
    private TextView lbl_desconto_005;
    private TextView lbl_total_005;
    private TextView lbl_verba_005;
    private TextView lbl_acordo_005;
    private TextView lbl_simulador_005;
    private TextView lbl_peddistr_005;
    private TextView txt_qtd_005;
    private TextView txt_preco_005;
    private TextView txt_preco_unidade_venda_005;
    private TextView txt_ultimo_preco_venda_005;
    private TextView txt_desconto_005;
    private TextView txt_total_005;
    private TextView txt_verba_desconto_005;
    private TextView txt_verba_descricao_005;
    private TextView txt_acordo_005;
    private TextView txt_simulador_005;
    private TextView txt_peddistr_005;
    private TextView lbl_cota_005;
    private TextView txt_pedcli_005;
    private TextView lbl_qtdbonif_005;
    private TextView lbl_precobonif_005;
    private TextView txt_preco_unidade_bonif_005;
    private TextView lbl_descontoBoni_005;
    private TextView lbl_totalbonif_005;
    private TextView lbl_verba2_desconto_005;
    private TextView lbl_verba2_005;
    private TextView lbl_acordo2_005;
    private TextView lbl_simulador2_005;
    private TextView lbl_peddistr2_005;
    private TextView lbl_pedcli2_005;
    private TextView txt_qtdbonif_005;
    private TextView txt_precobonif_005;
    private TextView txt_descontoBoni_005;
    private TextView txt_totalbonif_005;
    private TextView txt_verba2_desconto_005;
    private TextView txt_verba2_descricao_005;
    private TextView txt_acordo2_005;
    private TextView txt_simulador2_005;
    private TextView txt_peddistr2_005;
    private TextView txt_pedcli2_005;

    private TextView lbl_qtd_troca_005;
    private TextView lbl_preco_troca_005;
    private TextView lbl_lote_005;
    private TextView lbl_dtempacotamento_005;
    private TextView lbl_vencimento_005;
    private TextView lbl_motivo_005;
    private TextView lbl_obstroca_005;
    private TextView txt_qtd_troca_005;
    private TextView txt_preco_troca_005;
    private TextView txt_total_troca_005;
    private TextView txt_lote_005;
    private TextView txt_dtempacotamento_005;
    private TextView txt_vencimento_005;
    private TextView txt_motivo_005;
    private TextView txt_obstroca_005;
    private TextView txt_cota_005;
    private TextView txt_politica_perc_005;


    private Config config;

    private String operacao    = "";

    private String nropedido   = "";

    private String CODIGO      = "";

    private String LOJA        = "";

    private String IDAGE       = "";

    private String NEGOCIACAO  = "";

    private String LOG = "LANCAPEDIDO";

    private boolean editable = true;

    private String view = "CABEC";
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private defaultAdapter opcoesAdapter;

    //private final LocationManager manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pedido_v10);

        savebundle = savedInstanceState;

        try {


            adicionaHeaderNavigation();

            adicionaLeftNavigation();

            toolbar = (Toolbar) findViewById(R.id.tb_lanca_pedidov10);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Lançamentos De Pedidos");
            toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);

            toolbar.inflateMenu(R.menu.menu_lanca_pedidov2);


            try {

                Intent i = getIntent();

                if (i != null) {

                    Bundle params = i.getExtras();

                    operacao = params.getString("OPERACAO");

                    nropedido = params.getString("NROPEDIDO");

                    CODIGO = params.getString("CODIGO", "");

                    LOJA = params.getString("LOJA", "");

                    IDAGE = params.getString("IDAGE", "");

                    NEGOCIACAO = params.getString("NEGOCIACAO", "S");

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

        } catch (Exception e) {


            toast(e.getMessage());

        }


        Init();


        //metas

        try {

            lsMetaCategoria.add("CABEÇALHO");

            MetaDAO dao = new MetaDAO();

            dao.open();

            lsMetaCategoria.addAll(dao.getMetaByClienteCateroria(CODIGO,LOJA));

            adapterMetas = new AdapterMetas(PedidoV10Activity.this,lsMetaCategoria);

            lv_metas.setAdapter(adapterMetas);

            adapterMetas.notifyDataSetChanged();


        } catch (Exception e){

            toast(e.getMessage());


        }

        if (App.user.get_GPS().trim().equals("1")) {


            try {

                if (!checkPlayServices()) {

                    finish();
                }

                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();


                mLocationRequest = LocationRequest.create();
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                mLocationRequest.setInterval(POLLING_FREQ);
                mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                        .addLocationRequest(mLocationRequest);

                // set builder to always true (Shows the dialog after never operation too)
                builder.setAlwaysShow(true);

                // Then check whether current location settings are satisfied:
                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:

                                //Localização será pega em outro lugar

                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                // Location settings are not satisfied. But could be fixed by showing the user
                                // a dialog.
                                try {
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    status.startResolutionForResult(PedidoV10Activity.this,HelpInformation.REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                // Location settings are not satisfied. However, we have no way to fix the
                                // settings so we won't show the dialog.
                                toast("SETTINGS_CHANGE_UNAVAILABLE");
                                break;
                        }
                    }
                });


            } catch (Exception e) {


                toast(e.getMessage());

            }
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_lanca_pedidov2, menu);

        MenuItem iEnviar = menu.findItem(R.id.lanca_pedido_menuV2_enviar);
        MenuItem iGravar = menu.findItem(R.id.lanca_pedido_menuV2_ok);
        MenuItem iCancelar = menu.findItem(R.id.lanca_pedido_menuV2_cancela);

        if ("01,02,03".contains(pedido.getCabec().getSTATUS().trim())) {

            iGravar.setVisible(true);

        } else {

            iGravar.setVisible(false);

        }

        if ("03".contains(pedido.getCabec().getSTATUS().trim())) {

            iEnviar.setVisible(true);

        } else {

            iEnviar.setVisible(false);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.lanca_pedido_menuV2_enviar: {

                try {

                    final String nropedido = pedido.getCabec().getNRO();

                    if (Float.compare(pedido.getCabec().getVLRBONIFICADO() + pedido.getCabec().getTOTALPEDIDO(), 0) == 0) {

                        throw new Exception("Pedido Com Valor Zerado !\nDefina Pelo Menos Um Item.");

                    }

                    if (!verificaConexao()){

                        throw new Exception("Sem Rede Disponivel..");

                    }

                    String latitude  = "";

                    String longitude = "";

                    try {

                        latitude = Double.toString(mBestReading.getLatitude());

                    } catch (Exception e){

                        latitude = "";

                    }

                    try {

                        longitude = Double.toString(mBestReading.getLongitude());

                    } catch (Exception e){

                        longitude = "";

                    }

                    pedido.save(latitude,longitude);



                    try {

                        PedidoCabMbDAO dao = new PedidoCabMbDAO();

                        dao.open();

                        pedido.getCabec().setSTATUS("0");

                        if (dao.Update(pedido.getCabec())) {

                            Intent it = new Intent("PEDIDOBACKGROUND");

                            Bundle params = new Bundle();

                            params.putString("PEDIDO", nropedido);

                            params.putString("ROTINA", "PEDIDO");

                            it.putExtras(params);

                            startService(it);

                        } else {

                            toast("Falha Ao Mudar STATUS Do Pedido P/ AUTOMATICO !");

                        }

                        dao.close();


                    } catch (Exception e) {

                        toast("Falha Para Ativar Sincronização Automática");

                    }

                    finish();

                } catch (ExceptionSavePedido exceptionSavePedido) {

                    toast(exceptionSavePedido.getMessage());

                } catch (Exception e) {

                    toast(e.getMessage());

                }

                break;

            }


            case R.id.lanca_pedido_menuV2_tabela: {

                try {

                    Intent intent = new Intent(getApplicationContext(), PrePedidoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle params = new Bundle();
                    params.putString("CODIGO"         , pedido.getCabec().getCODIGOFAT());
                    params.putString("LOJA"           , pedido.getCabec().getLOJAFAT());
                    params.putFloat("FDSPREVISTO"     , pedido.getCabec().getFDSPREVISTO());
                    intent.putExtras(params);
                    startActivity(intent);


                } catch (Exception e) {

                    toast(e.getMessage());

                }

                break;
            }


            case R.id.lanca_pedido_menuV2_ok: {

                try {

                    final String nropedido = pedido.getCabec().getNRO();

                    if (Float.compare(pedido.getCabec().getVLRBONIFICADO() + pedido.getCabec().getTOTALPEDIDO(), 0) == 0) {

                        throw new Exception("Pedido Com Valor Zerado !\nDefina Pelo Menos Um Item.");

                    }


                    if (operacao.equals("NOVO")){


                        String latitude  = "";

                        String longitude = "";

                        try {

                            latitude = Double.toString(mBestReading.getLatitude());

                        } catch (Exception e){

                            latitude = "";

                        }

                        try {

                            longitude = Double.toString(mBestReading.getLongitude());

                        } catch (Exception e){

                            longitude = "";

                        }

                        pedido.save(latitude,longitude);

                    } else {

                        pedido.save("","");

                    }



                    finish();


                } catch (ExceptionSavePedido exceptionSavePedido) {

                    toast(exceptionSavePedido.getMessage());

                } catch (Exception e) {

                    toast(e.getMessage());

                }

                break;
            }

            case R.id.lanca_pedido_menuV2_cancela: {


                if (!"1,2,3".contains(pedido.getCabec().getSTATUS())){

                    finish();

                    break;

                }

                try {

                    final Dialog dialog = new Dialog(PedidoV10Activity.this);

                    dialog.setContentView(R.layout.dlglibped);

                    dialog.setTitle("CANCELAMENTO");

                    final Button confirmar = (Button) dialog.findViewById(R.id.btn_040_ok);
                    final Button cancelar = (Button) dialog.findViewById(R.id.btn_040_can);
                    final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);
                    final TextView tvtexto2 = (TextView) dialog.findViewById(R.id.txt_040_texto2);

                    tvtexto1.setText("DESEJA REALMENTE CANCELAR ?");
                    tvtexto2.setText(operacao.equals("NOVO") ? "INCLUSÃO DO PEDIDO" : "ALTERAÇÃO DO PEDIDO");

                    cancelar.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });

                    confirmar.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            finish();

                            dialog.dismiss();


                        }

                    });


                    dialog.show();


                } catch (Exception e) {

                    Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();

                }


                break;
            }


            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void finish() {

        lista = new ArrayList<>();

        lsMetaCategoria = new ArrayList<>();

        pedido.Close();

        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Boolean refresh = false;

        if (resultCode == 1 && requestCode == HelpInformation.HelpCliente) {

            String codigo = null;

            String loja = null;

            try {

                if (data.hasExtra("CODIGO")) {

                    codigo = data.getExtras().getString("CODIGO");

                }
                if (data.hasExtra("LOJA")) {

                    loja = data.getExtras().getString("LOJA");

                }

                if (HelpCliente.equals("CLIENTE"))

                    pedido.setCliente(codigo, loja);

                else {

                    pedido.setClienteEntrega(codigo, loja);

                }


                cabec_refresh();

            } catch (IOException e) {

                toast(e.getMessage());

            } catch (ExceptionItemProduto e) {

                toast(e.getMessage());

            } catch (Exception e) {

                toast(e.getMessage());
            }

        }


        if (resultCode == 1 && requestCode == HelpInformation.HelpVerba) {

            if (data.hasExtra("CODIGO")) {

                String codigo = data.getExtras().getString("CODIGO");

                try {

                    pedido.setItemVerba(codigo);

                    pedido.validarItemEdicao();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                    adapter.refresh(pedido.getEdicao());

                    cabec_refresh();

                    cabec_onClick();

                    detalhe_popula();

                    refresh = true;

                } catch (ExceptionItemProduto e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV", e.getMessage());

                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV", e.getMessage());
                }

            }

        }


        if (resultCode == 1 && requestCode == HelpInformation.MotivosTrocaDev) {

            String tipo = "";

            String codigo = "";



            if (data.hasExtra("TIPO")) {

                tipo = data.getExtras().getString("TIPO");

            }

            if (data.hasExtra("CODIGO")) {

                codigo = data.getExtras().getString("CODIGO");

            }

            try {

                pedido.setItemMotivo(tipo,codigo);

                pedido.validarItemEdicao();

                pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                pedido.recalculo();

                pedido.Validar();

                pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                adapter.refresh(pedido.getEdicao());

                cabec_refresh();

                cabec_onClick();

                detalhe_popula();

                refresh = true;

            } catch (ExceptionItemProduto e) {

                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("SAV", e.getMessage());

            } catch (Exception e) {

                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("SAV", e.getMessage());
            }


        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpVerbaBonif) {

            if (data.hasExtra("CODIGO")) {

                String codigo = data.getExtras().getString("CODIGO");
                String tipo   = data.getExtras().getString("TIPO");

                try {

                    if (tipo.equals("P")){

                        if (pedido.getCabec().getAPROVEITAMENTO().compareTo(pedido.getEdicao().getBONITOTAL()) < 0){

                            toast("Saldo Do Aproveitamento De Politica Insuficiente Para Este Produto!");

                            return;

                        }

                    }

                    pedido.setItemVerba2(codigo);

                    pedido.validarItemEdicao();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                    adapter.refresh(pedido.getEdicao());

                    cabec_refresh();

                    cabec_onClick();

                    detalhe_popula();

                    refresh = true;

                } catch (ExceptionItemProduto e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV", e.getMessage());

                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV", e.getMessage());
                }

            }

        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpAcordo) {

            if (data.hasExtra("CODIGO")) {

                String codigo = data.getExtras().getString("CODIGO");

                try {

                    pedido.setItemAcordo(codigo);

                    pedido.validarItemEdicao();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                    adapter.refresh(pedido.getEdicao());

                    cabec_refresh();

                    cabec_onClick();

                    detalhe_popula();

                    refresh = true;

                } catch (ExceptionItemProduto e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV", e.getMessage());

                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV", e.getMessage());
                }

            }

        }
        if (resultCode == 1 && requestCode == HelpInformation.HelpAcordoBonif) {

            if (data.hasExtra("CODIGO")) {

                String codigo = data.getExtras().getString("CODIGO");

                try {

                    pedido.setItemAcordo2(codigo);

                    pedido.validarItemEdicao();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                    adapter.refresh(pedido.getEdicao());

                    cabec_refresh();

                    cabec_onClick();

                    detalhe_popula();

                    refresh = true;

                } catch (ExceptionItemProduto e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV", e.getMessage());

                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    Log.i("SAV", e.getMessage());
                }

            }


        }
        if (resultCode == 1 && requestCode == HelpInformation.HelpPedDistr) {

            String filial = "";

            String ped = "";

            String item = "";


            try {

                if (data.hasExtra("FILIAL")) {

                    filial = data.getExtras().getString("FILIAL");

                }
                if (data.hasExtra("PEDIDOMOBILE")) {

                    ped = data.getExtras().getString("PEDIDOMOBILE");

                }
                if (data.hasExtra("ITEM")) {

                    item = data.getExtras().getString("ITEM");

                    pedido.setItemPedDistr(filial, ped, item);

                    pedido.validarItemEdicao();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                    adapter.refresh(pedido.getEdicao());

                    cabec_refresh();

                    cabec_onClick();

                    detalhe_popula();

                    refresh = true;

                }


            } catch (ExceptionItemProduto exceptionItemProduto) {
                toast(exceptionItemProduto.getMessage());
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


                    pedido.setItemSimulador(codigo, cliente, loja, produto);

                    pedido.validarItemEdicao();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                    adapter.refresh(pedido.getEdicao());

                    cabec_refresh();

                    cabec_onClick();

                    detalhe_popula();

                    refresh = true;

                }


            } catch (ExceptionItemProduto exceptionItemProduto) {
                toast(exceptionItemProduto.getMessage());
            }

        }


        if (resultCode == 1 && requestCode == HelpInformation.HelpPedDistrBonif) {

            String filial = "";

            String ped = "";

            String item = "";


            try {

                if (data.hasExtra("FILIAL")) {

                    filial = data.getExtras().getString("FILIAL");

                }
                if (data.hasExtra("PEDIDOMOBILE")) {

                    ped = data.getExtras().getString("PEDIDOMOBILE");

                }
                if (data.hasExtra("ITEM")) {

                    item = data.getExtras().getString("ITEM");

                    pedido.setItemPedDistr2(filial, ped, item);

                    pedido.validarItemEdicao();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                    adapter.refresh(pedido.getEdicao());

                    cabec_refresh();

                    cabec_onClick();

                    detalhe_popula();

                    refresh = true;

                }


            } catch (ExceptionItemProduto exceptionItemProduto) {
                toast(exceptionItemProduto.getMessage());
            }

        }


        if (resultCode == 1 && requestCode == HelpInformation.HelpCota) {

            String cota = "";

            Float  preco = 0f;


            try {

                if (data.hasExtra("COTA")) {

                    cota = data.getExtras().getString("COTA");

                }
                if (data.hasExtra("PRECO")) {

                    preco = data.getExtras().getFloat("PRECO");

                }

                pedido.getEdicao().setCOTA(cota);

                pedido.getEdicao().setPRCVEN(preco);

                pedido.validarItemEdicao();

                pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                pedido.recalculo();

                pedido.Validar();

                pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                adapter.refresh(pedido.getEdicao());

                cabec_refresh();

                cabec_onClick();

                detalhe_popula();

                refresh = true;


            } catch (Exception e) {

                toast(e.getMessage());

            }

        }


        if (resultCode == 0 && requestCode == HelpInformation.REQUEST_CHECK_SETTINGS) {

            finish();


        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        lista = new ArrayList<Object>();

    }

    @Override
    public void onBackPressed() {

        toast("Use Os Botões Gravar Ou Cancelar !");

        //super.onBackPressed();

    }

    private String getNewID() {

        String retorno = "";

        Calendar c = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        retorno = App.user.getCOD() + format.format(c.getTime());

        return retorno;

    }

    private void toast(String mensagem) {

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();


    }


    private  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;

    }

    private void Init() {

        try {

            ll_lanca_pedido_cabec = (View) findViewById(R.id.ll_lanca_pedido_cabec);

            ll_lanca_pedido_detalhe = (View) findViewById(R.id.ll_lanca_pedido_detalhe);

            linha_troca_005 = (View) findViewById(R.id.linha_troca_005);

            linha_dados_005 = (View) findViewById(R.id.linha_dados_005);

            cabec_init();

            detalhe_init();

            loadPedido();

            setViews(true, false);

        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void setViews(boolean cabec, boolean detalhe) {

        if (cabec) {

            ll_lanca_pedido_cabec.setVisibility(View.VISIBLE);

            cabec_popula();

            if (condadapter != null) condadapter.setIsInicializacao(true);

            if (tipoadapter != null) tipoadapter.setIsInicializacao(true);

            cabec_onClick();

        } else {

            ll_lanca_pedido_cabec.setVisibility(View.GONE);

        }

        if (detalhe) {


            if (pedido.getCabec().getTIPO().equals("005") || pedido.getCabec().getTIPO().equals("006")) {

                linha_dados_005.setVisibility(View.GONE);
                linha_troca_005.setVisibility(View.VISIBLE);

            } else {

                linha_dados_005.setVisibility(View.VISIBLE);
                linha_troca_005.setVisibility(View.GONE);

            }

            ll_lanca_pedido_detalhe.setVisibility(View.VISIBLE);


            if (lista.size() == 0) {

                try {

                    LOADThread loadItens = new LOADThread(mHandlerLoad);

                    loadItens.start();

                    produto_filtro();

                    lista.add("CABEC");

                    lista.add(new NoDataProgress("Carregando os produtos..."));

                    adapter = new Adapter(getBaseContext(), lista);

                    lv.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                    PosEdicao = "";

                } catch (Exception e) {
                    toast(e.getMessage());
                }
            } else {

                pedido.setEdicao(new PedidoDetMb());

                adapter = new Adapter(getBaseContext(), lista);

                lv.setAdapter(adapter);

                //Ajusta Visão Dos Itens
                Object lixo = sp_view_088.getSelectedItem();

                if ( ((String[]) lixo)[0].equals("04")   ){

                    adapter.setViewPedido(true);

                } else {

                    adapter.setViewPedido(false);

                }

                adapter.setSubFiltro(((String[]) lixo)[0]);

                //Categoria
                Object categoria = spCategoria.getSelectedItem();


                //Marca
                Object marca  = spMarcas.getSelectedItem();


                adapter.setFilter(((String[]) categoria)[0],((String[]) marca)[0]);

                pedido.setEdicao(new PedidoDetMb());
                PosEdicao          = "";
                detalhe_popula();

                adapter.refreshwithfiltro();

                adapter.notifyDataSetChanged();

                PosEdicao = "";

            }

            detalhe_popula();

            detalhe_onClick();

        } else {

            ll_lanca_pedido_detalhe.setVisibility(View.GONE);
        }

    }

    private void loadPedido() {

        try {

            //pedido = new PedidoBusinessV10();

            pedido.Novo();

            if (operacao.equals("NOVO")) {

                if (IDAGE.trim().isEmpty()) {

                    pedido.setAgendamento(App.getNewIDAgendamento());

                } else {


                    pedido.setAgendamento(IDAGE);

                }

                if (NEGOCIACAO.equals("S")){

                    pedido.getCabec().setTIPO("012");
                    ;

                } else {

                    pedido.getCabec().setTIPO("001");

                }


                pedido.getCabec().loadTipoDescricao();

                pedido.getCabec().setNRO(getNewID());

                if (!CODIGO.isEmpty()) {

                    pedido.setCliente(CODIGO, LOJA);

                    pedido.Validar();

                }

                lista = new ArrayList<>();

            } else {

                if (IDAGE.trim().isEmpty()) {

                    pedido.setAgendamento(App.getNewIDAgendamento());

                } else {


                    pedido.setAgendamento(IDAGE);

                }

                pedido.loadOnlyCabec(nropedido);

                LOADThread loadItens = new LOADThread(mHandlerLoad);

                loadItens.start();


            }

        } catch (IOException e) {
            toast(e.getMessage());
        } catch (ExceptionLoadPedido exceptionLoadPedido) {
            toast(exceptionLoadPedido.getMessage());
        } catch (ExceptionItemProduto exceptionItemProduto) {
            toast(exceptionItemProduto.getMessage());
        } catch (Exception e) {
            toast(e.getMessage());
        }
    }

    private void cabec_init() {

          /*
          *
          *  icones
          *
          */

        item_max_min_001 = (ImageView) findViewById(R.id.item_max_min_001);
        item_trash_001 = (ImageView) findViewById(R.id.item_trash_001);
        item_details_001 = (ImageButton) findViewById(R.id.item_details_001);
        txt_upload_001 = (TextView) findViewById(R.id.txt_upload_001);
        bt_email_001 = (ImageView) findViewById(R.id.bt_email_001);
        txt_email_001 = (TextView) findViewById(R.id.txt_email_001);
        im_email_check_001 = (ImageView) findViewById(R.id.im_email_check_001);

        //item_trash_001.setOnClickListener(new LancaPedidoActivity.Click());
        //item_max_min_001.setOnClickListener(new LancaPedidoActivity.Click());

         /*
          *
          * Defines os campos
          *
          */

        lbl_fardos_previstos_001 = (TextView) findViewById(R.id.lbl_fardos_previstos_001);
        lbl_qtd_entrega_001 = (TextView) findViewById(R.id.lbl_qtd_entrega_001);
        lbl_cliente_001 = (TextView) findViewById(R.id.lbl_cliente_001);
        lbl_pedidocliente_001 = (TextView) findViewById(R.id.lbl_pedidocliente_001);
        sp_tipopedido_001 = (Spinner) findViewById(R.id.sp_tipopedido_001);
        lbl_clienteentrega_001 = (TextView) findViewById(R.id.lbl_clienteentrega_001);
        sp_entrega_001 = (Spinner) findViewById(R.id.sp_entrega_001);
        sp_condpagto_001 = (Spinner) findViewById(R.id.sp_condpagto_001);
        lbl_retira_001 = (TextView) findViewById(R.id.lbl_retira_001);
        lbl_obsped_001 = (TextView) findViewById(R.id.lbl_obsped_001);
        lbl_obsnf_001 = (TextView) findViewById(R.id.lbl_obsnf_001);
        txt_id_001 = (TextView) findViewById(R.id.txt_id_001);
        txt_protheus_001 = (TextView) findViewById(R.id.txt_protheus_001);
        txt_status_001 = (TextView) findViewById(R.id.txt_status_001);
        txt_erro_001 = (TextView) findViewById(R.id.txt_erro_001);
        txt_cliente_001 = (TextView) findViewById(R.id.txt_cliente_001);
        txt_pedidocliente_001 = (TextView) findViewById(R.id.txt_pedidocliente_001);
        txt_clienteentrega_001 = (TextView) findViewById(R.id.txt_clienteentrega_001);
        txt_emissao_001 = (TextView) findViewById(R.id.txt_emissao_001);
        sp_tabpreco_001 = (Spinner) findViewById(R.id.sp_tabpreco_001);
        txt_retira_001 = (TextView) findViewById(R.id.txt_retira_001);
        txt_obsped_001 = (TextView) findViewById(R.id.txt_obsped_001);
        txt_obsnf_001 = (TextView) findViewById(R.id.txt_obsnf_001);
        txt_cnpj_001 = (TextView) findViewById(R.id.txt_cnpj_001);
        txt_ie_001 = (TextView) findViewById(R.id.txt_ie_001);
        txt_qtdbonif_001 = (TextView) findViewById(R.id.txt_qtdbonif_001);
        txt_totalbonif_001 = (TextView) findViewById(R.id.txt_totalbonif_001);
        txt_totalpedido_001 = (TextView) findViewById(R.id.txt_totalpedido_001);
        txt_totaldesc_001 = (TextView) findViewById(R.id.txt_totaldesc_001);
        txt_totaldescverba_001 = (TextView) findViewById(R.id.txt_totaldescverba_001);
        txt_fardos_previstos_001 = (TextView) findViewById(R.id.txt_fardos_previstos_001);
        txt_fardos_realizados_001 = (TextView) findViewById(R.id.txt_fardos_realizados_001);
        txt_qtd_entrega_001       = (TextView) findViewById(R.id.txt_qtd_entrega_001);
        txt_peso_liquido_001      = (TextView) findViewById(R.id.txt_peso_liquido_001);
        txt_peso_bruto_001        = (TextView) findViewById(R.id.txt_peso_bruto_001);
        lv_metas                  = (ListView)  findViewById(R.id.lvMetas_001);


    }

    private void detalhe_init() {

        inc_pedidomb_filtro = (View) findViewById(R.id.inc_pedidomb_filtro);

        sp_view_088 = (Spinner) findViewById(R.id.sp_view_088);

        spCategoria = (Spinner) findViewById(R.id.sp_categoria_088);

        spMarcas = (Spinner) findViewById(R.id.sp_marca_088);


        lbl_total_pedido_005                   = (TextView) findViewById(R.id.lbl_total_pedido_005);
        ;
        lbl_total_fd_previstos_005             = (TextView) findViewById(R.id.lbl_total_fd_previstos_005);
        ;
        lbl_total_fd_realizados_005            = (TextView) findViewById(R.id.lbl_total_fd_realizados_005);
        ;
        lbl_total_aproveitamento_pol_005       = (TextView) findViewById(R.id.lbl_total_aproveitamento_pol_005);

        lbl_total_saldo_aproveitamento_pol_005 = (TextView) findViewById(R.id.lbl_total_saldo_aproveitamento_pol_005);

        lbl_peso_liquido_005                   = (TextView) findViewById(R.id.lbl_peso_liquido_005);

        lbl_peso_bruto_005                     = (TextView) findViewById(R.id.lbl_peso_bruto_005);

        txt_cota_005          = (TextView)  findViewById(R.id.txt_cota_005);
        txt_politica_perc_005 = (TextView)  findViewById(R.id.txt_politica_perc_005);
        item_trash_005        = (ImageView) findViewById(R.id.item_trash_005);
        item_volta_cabec_005  = (ImageView) findViewById(R.id.item_volta_cabec_005);

        txt_id_005            = (TextView) findViewById(R.id.txt_id_005);
        txt_item_005          = (TextView) findViewById(R.id.txt_item_005);
        txt_status_005        = (TextView) findViewById(R.id.txt_status_005);
        txt_erro_005          = (TextView) findViewById(R.id.txt_erro_005);
        txt_produto_005       = (TextView) findViewById(R.id.txt_produto_005);

        lbl_qtd_005           = (TextView) findViewById(R.id.lbl_qtd_005);
        lbl_preco_005         = (TextView) findViewById(R.id.lbl_preco_005);
        lbl_desconto_005      = (TextView) findViewById(R.id.lbl_desconto_005);
        lbl_total_005         = (TextView) findViewById(R.id.lbl_total_005);
        lbl_verba_005         = (TextView) findViewById(R.id.lbl_verba_005);
        lbl_acordo_005        = (TextView) findViewById(R.id.lbl_acordo_005);
        lbl_simulador_005     = (TextView) findViewById(R.id.lbl_simulador_005);
        lbl_peddistr_005      = (TextView) findViewById(R.id.lbl_peddistr_005);
        lbl_cota_005          = (TextView) findViewById(R.id.lbl_cota_005);

        txt_qtd_005                  = (TextView) findViewById(R.id.txt_qtd_005);
        txt_preco_005                = (TextView) findViewById(R.id.txt_preco_005);
        txt_preco_unidade_venda_005  = (TextView) findViewById(R.id.txt_preco_unidade_venda_005);
        txt_ultimo_preco_venda_005   = (TextView) findViewById(R.id.txt_ultimo_preco_venda_005);
        txt_desconto_005             = (TextView) findViewById(R.id.txt_desconto_005);
        txt_total_005                = (TextView) findViewById(R.id.txt_total_005);
        txt_verba_desconto_005       = (TextView) findViewById(R.id.txt_verba_desconto_005);
        txt_verba_descricao_005      = (TextView) findViewById(R.id.txt_verba_descricao_005);
        txt_acordo_005               = (TextView) findViewById(R.id.txt_acordo_005);
        txt_simulador_005            = (TextView) findViewById(R.id.txt_simulador_005);
        txt_peddistr_005             = (TextView) findViewById(R.id.txt_peddistr_005);


        lbl_qtdbonif_005 = (TextView) findViewById(R.id.lbl_qtdbonif_005);
        lbl_precobonif_005 = (TextView) findViewById(R.id.lbl_precobonif_005);
        txt_preco_unidade_bonif_005 = (TextView) findViewById(R.id.txt_preco_unidade_bonif_005);
        lbl_descontoBoni_005 = (TextView) findViewById(R.id.lbl_descontoBoni_005);
        lbl_totalbonif_005 = (TextView) findViewById(R.id.lbl_totalbonif_005);
        lbl_verba2_desconto_005 = (TextView) findViewById(R.id.lbl_verba2_desconto_005);
        lbl_verba2_005 = (TextView) findViewById(R.id.lbl_verba2_005);
        lbl_acordo2_005 = (TextView) findViewById(R.id.lbl_acordo2_005);
        lbl_simulador2_005 = (TextView) findViewById(R.id.lbl_simulador2_005);
        lbl_peddistr2_005 = (TextView) findViewById(R.id.lbl_peddistr2_005);
        lbl_pedcli2_005 = (TextView) findViewById(R.id.lbl_pedcli2_005);

        txt_qtdbonif_005 = (TextView) findViewById(R.id.txt_qtdbonif_005);
        txt_precobonif_005 = (TextView) findViewById(R.id.txt_precobonif_005);
        txt_preco_unidade_bonif_005 = (TextView) findViewById(R.id.txt_preco_unidade_bonif_005);
        txt_descontoBoni_005 = (TextView) findViewById(R.id.txt_descontoBoni_005);
        txt_totalbonif_005 = (TextView) findViewById(R.id.txt_totalbonif_005);
        txt_verba2_desconto_005 = (TextView) findViewById(R.id.txt_verba2_desconto_005);
        txt_verba2_descricao_005 = (TextView) findViewById(R.id.txt_verba2_descricao_005);
        txt_acordo2_005 = (TextView) findViewById(R.id.txt_acordo2_005);
        txt_simulador2_005 = (TextView) findViewById(R.id.txt_simulador2_005);
        txt_peddistr2_005 = (TextView) findViewById(R.id.txt_peddistr2_005);
        txt_pedcli2_005 = (TextView) findViewById(R.id.txt_pedcli2_005);


        //troca ou devolução
        lbl_qtd_troca_005           =  (TextView) findViewById(R.id.lbl_qtd_troca_005);
        lbl_preco_troca_005         = (TextView) findViewById(R.id.lbl_preco_troca_005);
        lbl_lote_005                = (TextView) findViewById(R.id.lbl_lote_005);
        lbl_dtempacotamento_005     = (TextView) findViewById(R.id.lbl_dtempacotamento_005);
        lbl_vencimento_005          = (TextView) findViewById(R.id.lbl_vencimento_005);
        lbl_obstroca_005            = (TextView) findViewById(R.id.lbl_obstroca_005);
        lbl_motivo_005              = (TextView) findViewById(R.id.lbl_motivo_005);
        txt_qtd_troca_005           = (TextView) findViewById(R.id.txt_qtd_troca_005);
        txt_preco_troca_005         = (TextView) findViewById(R.id.txt_preco_troca_005);
        txt_total_troca_005         = (TextView) findViewById(R.id.txt_total_troca_005);
        txt_lote_005                = (TextView) findViewById(R.id.txt_lote_005);
        txt_dtempacotamento_005     = (TextView) findViewById(R.id.txt_dtempacotamento_005);
        txt_motivo_005              = (TextView) findViewById(R.id.txt_motivo_005);
        txt_vencimento_005          = (TextView) findViewById(R.id.txt_vencimento_005);
        txt_obstroca_005            = (TextView) findViewById(R.id.txt_obstroca_005);


        txt_erro_005 = (TextView) findViewById(R.id.txt_erro_005);

        lv = (ListView) findViewById(R.id.lvpedidov10_100);


    }

    private void cabec_popula() {

        List<String[]> opcoes;

         /*
          *
          * Validacao
          *
          *
          */


        if ("0#1#2#3".contains(pedido.getCabec().getSTATUS())) {

            bt_email_001.setVisibility(View.VISIBLE);

            bt_email_001.setOnClickListener(new ClickEmail(PedidoV10Activity.this));

            if (pedido.getCabec().getCCOPIAPEDIDO().equals("S")) {

                im_email_check_001.setVisibility(View.VISIBLE);

                txt_email_001.setText(pedido.getCabec().getCEMAILCOPIAPEDIDO());

            } else {

                im_email_check_001.setVisibility(View.INVISIBLE);

                txt_email_001.setText("");

            }

        } else {


            bt_email_001.setVisibility(View.INVISIBLE);

            im_email_check_001.setVisibility(View.INVISIBLE);

            txt_email_001.setText("");

        }

        invalidateOptionsMenu();
         /*
          *
          *  Atribui Valores
          *
          *
          */
        txt_upload_001.setText(pedido.getCabec().getDTTRANS() + " " + pedido.getCabec().getHOTRANS());
        txt_id_001.setText(pedido.getCabec().getNRO());
        txt_protheus_001.setText(pedido.getCabec().getCPROTHEUS());
        txt_status_001.setText(pedido.getCabec().get_Status());
        txt_erro_001.setText(pedido.getCabec().getMENSAGEM());
        txt_cliente_001.setText(pedido.getCabec().get_ClienteFatRazao());
        txt_cnpj_001.setText(pedido.getCabec().get_ClienteFatCnpj());
        txt_ie_001.setText(pedido.getCabec().get_ClienteFatIE());
        txt_pedidocliente_001.setText(pedido.getCabec().getPEDCLIENTE());

        opcoes = pedido.getCabec().getlsTipos();

        sp_tipopedido_001.setEnabled(false);

        tipoadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes, "Tipo Ped.", pedido.getCabec().isValidoByName("TIPO"));

        sp_tipopedido_001.setAdapter(tipoadapter);

        try {

            sp_tipopedido_001.setSelection(pedido.getCabec().getlsTiposIndex());

        } catch (Exception e) {

            sp_tipopedido_001.setSelection(0);

        }

        opcoes = new ArrayList<String[]>();

        opcoes.add(new String[]{"1ª", pedido.getCabec().getENTREGA()});

        sp_entrega_001.setEnabled(false);

        entregaadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes, "Entrega", pedido.getCabec().isValidoByName("ENTREGA"));

        sp_entrega_001.setAdapter(entregaadapter);

        sp_entrega_001.setSelection(0);

        txt_retira_001.setText(pedido.getCabec().get_Retira());

        txt_emissao_001.setText(pedido.getCabec().getEMISSAO());

        txt_clienteentrega_001.setText(pedido.getCabec().get_ClienteEntRazao());

        int index = -1;

        int i = 0;

        opcoes = new ArrayList<>();

        for (CondPagto op : pedido.getLsCondPagtoByFiltro()) {

            opcoes.add(new String[]{op.getCODIGO(), op.getDESCRICAO()});

            if (op.getCODIGO().equals(pedido.getCabec().getCOND())) {

                index = i;

            }

            i++;

        }

        if (index == -1) {

            opcoes.add(new String[]{"01", " "});

            index = 0;

        }

        sp_condpagto_001.setEnabled(false);

        condadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes, "Cond Pagto", pedido.getCabec().isValidoByName("COND"));

        sp_condpagto_001.setAdapter(condadapter);

        sp_condpagto_001.setSelection(index);

        sp_tabpreco_001.setEnabled(false);

        opcoes = new ArrayList<>();

        opcoes.add(new String[]{pedido.getCabec().getTABPRECO(), pedido.getCabec().get_TabPreco()});

        tabprecoadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes, "Tab.Preço", pedido.getCabec().isValidoByName("TABPRECO"));

        sp_tabpreco_001.setAdapter(tabprecoadapter);

        sp_tabpreco_001.setSelection(0);

        txt_obsped_001.setText(pedido.getCabec().getOBSPED());
        txt_obsnf_001.setText(pedido.getCabec().getOBSNF());
        txt_qtdbonif_001.setText(format_02.format(pedido.getCabec().getQTDBINIFICADA()));
        txt_totalbonif_001.setText(format_02.format(pedido.getCabec().getVLRBONIFICADO()));
        txt_totalpedido_001.setText(format_02.format(pedido.getCabec().getTOTALPEDIDO()));
        txt_totaldesc_001.setText(format_02.format(pedido.getCabec().getTOTALDESCONTO()));
        txt_totaldescverba_001.setText(format_02.format(pedido.getCabec().getTOTALVERBA()));
        txt_fardos_previstos_001.setText(format_02.format(pedido.getCabec().getFDSPREVISTO()));
        txt_fardos_realizados_001.setText(format_02.format(pedido.getCabec().getFDSREAIS()));
        txt_qtd_entrega_001.setText(String.valueOf(pedido.getCabec().getQTDENTREGA()));
        txt_peso_liquido_001.setText(format_04.format(pedido.getCabec().getPESOLIQUIDO())+"Kg");
        txt_peso_bruto_001.setText(format_04.format(pedido.getCabec().getPESOBRUTO())+"Kg");
        int indice = pedido.getIndiceByCodigo(pedido.getCabec().getTABPRECO());

        if (indice != -1) {


            if (!pedido.getLsTabPrecoCabec().get(indice).getFLAGFAIXA().equals("1")) {

                txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


            } else {

                if (pedido.getCabec().getFDSPREVISTO().compareTo(pedido.getCabec().getFDSREAIS()) <= 0) {

                    txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

                } else {

                    txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }


        }


    }

    private void detalhe_popula() {

        if (pedido == null) {

            return;

        }

         /*
          *
          *  Validacao
          *
          */


        if (pedido != null) {

            invalidateOptionsMenu();

            if (pedido.getEdicao().getNRO().isEmpty()) {

                item_trash_005.setVisibility(View.INVISIBLE);

            } else {

                item_trash_005.setVisibility(View.VISIBLE);

            }

            lbl_total_pedido_005.setText("TOTAL\n" + format_02.format(pedido.getCabec().getTOTALPEDIDO()));
            lbl_total_fd_previstos_005.setText("PREVISTOS\n" + format_02.format(pedido.getCabec().getFDSPREVISTO()));
            lbl_total_fd_realizados_005.setText("REALIZADOS\n" + format_02.format(pedido.getCabec().getFDSREAIS()));
            lbl_total_aproveitamento_pol_005.setText("APROV. POL.\n" + format_02.format(pedido.getCabec().getAPROVEITAMENTO()));
            lbl_total_saldo_aproveitamento_pol_005.setText("SALDO POL.\n" + format_02.format(pedido.getCabec().getSALDOAPROVEITAMENTO()));
            lbl_peso_liquido_005.setText("Peso Liq.\n"+format_04.format(pedido.getCabec().getPESOLIQUIDO())+"Kg");
            lbl_peso_bruto_005.setText("Peso Bruto\n"+format_04.format(pedido.getCabec().getPESOBRUTO())+"kg");
            if (!pedido.getEdicao().isValidoByName("PRODUTO")) {

                txt_produto_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_produto_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            //venda
            if (!pedido.getEdicao().isValidoByName("QTD")) {

                txt_qtd_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_qtd_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("PRCVEN")) {

                txt_preco_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_preco_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("DESCON")) {

                txt_desconto_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_desconto_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("CODVERBA")) {

                txt_verba_descricao_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_verba_descricao_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }


            if (!pedido.getEdicao().isValidoByName("ACORDO")) {

                txt_acordo_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_acordo_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }


            //bonificação
            if (!pedido.getEdicao().isValidoByName("BONIQTD")) {

                txt_qtdbonif_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_qtdbonif_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("CODVERBA2")) {

                txt_verba2_descricao_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_verba2_descricao_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("ACORDO2")) {

                txt_acordo2_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_acordo2_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }


            txt_id_005.setText(pedido.getEdicao().getNRO());
            txt_item_005.setText(pedido.getEdicao().getITEM());
            txt_status_005.setText(pedido.getEdicao().getSTATUS() + "-" + pedido.getEdicao().get_Status());
            txt_erro_005.setText(pedido.getEdicao().getMENSAGEM());

            //tirar
            //txt_erro_005.setText("PF "+String.valueOf(pedido.getEdicao().getPRECOFORMACAO()) +" Frete: "+String.valueOf(pedido.getEdicao().getFRETE())+" Imposto: "+String.valueOf(pedido.getEdicao().getIMPOSTO()) + " Camp: "+String.valueOf(pedido.getEdicao().getVLRCAMPANHA())  );

            txt_produto_005.setText(pedido.getEdicao().getPRODUTO() + " " + pedido.getEdicao().get_Produto());

            txt_qtd_005.setText(format_02.format(pedido.getEdicao().getQTD()));
            txt_preco_005.setText(format_02.format(pedido.getEdicao().getPRCVEN()));
            if (pedido.getEdicao().getUNIDADE() == 0) {

                txt_preco_unidade_venda_005.setText("");

            } else {

                txt_preco_unidade_venda_005.setText("Unid.: " + format_02.format(pedido.getEdicao().getPRCVEN() / pedido.getEdicao().getUNIDADE()));

            }
            if (pedido.getEdicao().get_UltimoPreco() == 0) {

                txt_ultimo_preco_venda_005.setText("");

            } else {

                txt_ultimo_preco_venda_005.setText("Últ. Preço: " + format_02.format(pedido.getEdicao().get_UltimoPreco()));

            }
            txt_desconto_005.setText(format_05.format(pedido.getEdicao().getDESCON()));
            txt_total_005.setText(format_02.format(pedido.getEdicao().getTOTAL()));
            txt_verba_desconto_005.setText(format_02.format(pedido.getEdicao().getDESCVER()));
            txt_verba_descricao_005.setText(pedido.getEdicao().get_Verba().trim());
            txt_acordo_005.setText(pedido.getEdicao().get_Acordo());
            txt_simulador_005.setText(pedido.getEdicao().getSIMULADOR());
            txt_peddistr_005.setText(pedido.getEdicao().getPEDDIST());

            Float pol = (pedido.getEdicao().getDESCONTOPOL() - pedido.getEdicao().getDESCON());

            String texto = "";


            if (pol.compareTo(0f) > 0) {

                texto = "Aprov. Pol. R$ "+format_02.format(pedido.getEdicao().getAproveitamento())+"\n Pol+DNA " + format_02.format(pol)+"%" ;

            }

            txt_cota_005.setText(pedido.getEdicao().getCOTA());

            txt_politica_perc_005.setText(texto);

            txt_qtdbonif_005.setText(format_02.format(pedido.getEdicao().getBONIQTD()));
            txt_precobonif_005.setText(format_02.format(pedido.getEdicao().getBONIPREC()));
            if (pedido.getEdicao().getUNIDADE() == 0) {

                txt_preco_unidade_bonif_005.setText("");


            } else {

                txt_preco_unidade_bonif_005.setText("Unid.: " + format_02.format(pedido.getEdicao().getBONIPREC() / pedido.getEdicao().getUNIDADE()));

            }

            txt_descontoBoni_005.setText(format_05.format(0f));
            txt_totalbonif_005.setText(format_02.format(pedido.getEdicao().getBONITOTAL()));
            txt_verba2_desconto_005.setText(format_02.format(pedido.getEdicao().getDESCVER2()));
            txt_verba2_descricao_005.setText(pedido.getEdicao().get_Verba2().trim());
            txt_acordo2_005.setText(pedido.getEdicao().get_Acordo2());
            txt_peddistr2_005.setText(pedido.getEdicao().getPEDDIST2());

            if (txt_pedcli2_005.getVisibility() == View.VISIBLE) {
                lbl_pedcli2_005.setText("");
                txt_pedcli2_005.setText("DNA: "+format_02.format(pedido.getEdicao().getDNAVALOR())+" D.C: "+format_02.format(pedido.getEdicao().getDESCCONTRATO())+" TX : "+pedido.getEdicao().getTAXAFIN());
            }
            //trocas


            if (!pedido.getEdicao().isValidoByName("QTD")) {

                txt_qtd_troca_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_qtd_troca_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("PRCVEN")) {

                txt_preco_troca_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_preco_troca_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("LOTE")) {

                txt_lote_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_lote_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }


            if (!pedido.getEdicao().isValidoByName("EMPACOTAMENTO")) {

                txt_dtempacotamento_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_dtempacotamento_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("VENCIMENTO")) {

                txt_vencimento_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_vencimento_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }


            if (!pedido.getEdicao().isValidoByName("VENCIMENTO")) {

                txt_vencimento_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_vencimento_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (!pedido.getEdicao().isValidoByName("CODVERBA")) {

                txt_motivo_005.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

            } else {

                txt_motivo_005.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            txt_qtd_troca_005.setText(format_02.format(pedido.getEdicao().getQTD()));
            txt_preco_troca_005.setText(format_02.format(pedido.getEdicao().getPRCVEN()));
            txt_total_troca_005.setText(format_02.format(pedido.getEdicao().getTOTAL()));
            txt_lote_005.setText(pedido.getEdicao().getLOTE());
            txt_dtempacotamento_005.setText(pedido.getEdicao().getEMPACOTAMENTO());
            txt_vencimento_005.setText(pedido.getEdicao().getVENCIMENTO());

            if ( "005#006".contains(pedido.getCabec().getTIPO()) ) {

                txt_motivo_005.setText(pedido.getEdicao().get_MotDev());
            }
            else {

                txt_motivo_005.setText(pedido.getEdicao().get_Verba());

            }

            txt_obstroca_005.setText(pedido.getEdicao().getOBS());

            if ((pedido.getCabec().getSTATUS().equals("1") ||
                    pedido.getCabec().getSTATUS().equals("2") ||
                    pedido.getCabec().getSTATUS().equals("3"))) {

                detalhe_onClick();

            }
        }
    }

    private void detalhe_onClick() {

        /*
          *
          *  Listener dos botões
          *
          *
          */


        item_volta_cabec_005.setOnClickListener(new ClickDet());

        if (!editable) return;

        item_trash_005.setOnClickListener(new ClickDet());

        if ((pedido.getCliente().getCODIGO() != null) && (!pedido.getCliente().getCODIGO().equals(""))) {


            if ((pedido.getCabec().getTIPO().equals("005") || pedido.getCabec().getTIPO().equals("006"))) {

                lbl_qtd_troca_005.setOnClickListener(new ClickDet());
                lbl_preco_troca_005.setOnClickListener(new ClickDet());
                lbl_lote_005.setOnClickListener(new ClickDet());
                lbl_dtempacotamento_005.setOnClickListener(new ClickDet());
                lbl_vencimento_005.setOnClickListener(new ClickDet());
                lbl_motivo_005.setOnClickListener(new ClickDet());
                lbl_obstroca_005.setOnClickListener(new ClickDet());


            } else {

                if ((pedido.getCabec().getTIPO().equals("003")) || (pedido.getCabec().getTIPO().equals("011"))) {
                    //Bonificação
                    lbl_qtdbonif_005.setOnClickListener(new ClickDet());
                    lbl_precobonif_005.setOnClickListener(new ClickDet());
                    lbl_desconto_005.setOnClickListener(new ClickDet());
                    lbl_verba2_005.setOnLongClickListener(new ClickDetClear("CODVERBA2"));
                    lbl_verba2_005.setOnClickListener(new ClickDet());
                    lbl_acordo2_005.setOnLongClickListener(new ClickDetClear("ACORDO2"));
                    lbl_acordo2_005.setOnClickListener(new ClickDet());
                    lbl_peddistr2_005.setOnLongClickListener(new ClickDetClear("PEDDIST2"));
                    lbl_peddistr2_005.setOnClickListener(new ClickDet());
                    //lbl_pedcli2_005.setOnLongClickListener(new ClickDetClear(""));
                    //lbl_pedcli2_005.setOnClickListener(new ClickDet());
                } else {
                    //venda
                    lbl_qtd_005.setOnClickListener(new ClickDet());
                    lbl_preco_005.setOnClickListener(new ClickDet());
                    lbl_desconto_005.setOnClickListener(new ClickDet());
                    lbl_verba_005.setOnLongClickListener(new ClickDetClear("CODVERBA"));
                    lbl_verba_005.setOnClickListener(new ClickDet());
                    lbl_acordo_005.setOnLongClickListener(new ClickDetClear("ACORDO"));
                    lbl_acordo_005.setOnClickListener(new ClickDet());
                    lbl_simulador_005.setOnLongClickListener(new ClickDetClear("SIMULADOR"));
                    lbl_simulador_005.setOnClickListener(new ClickDet());
                    lbl_peddistr_005.setOnLongClickListener(new ClickDetClear("PEDDIST"));
                    lbl_peddistr_005.setOnClickListener(new ClickDet());
                    lbl_cota_005.setOnLongClickListener(new ClickDetClear("COTA"));
                    lbl_cota_005.setOnClickListener(new ClickDet());

                    //bonificação
                    lbl_qtdbonif_005.setOnClickListener(new ClickDet());
                    lbl_precobonif_005.setOnClickListener(new ClickDet());
                    lbl_descontoBoni_005.setOnClickListener(new ClickDet());
                    lbl_verba2_005.setOnLongClickListener(new ClickDetClear("CODVERBA2"));
                    lbl_verba2_005.setOnClickListener(new ClickDet());
                    lbl_acordo2_005.setOnLongClickListener(new ClickDetClear("ACORDO2"));
                    lbl_acordo2_005.setOnClickListener(new ClickDet());
                    lbl_peddistr2_005.setOnLongClickListener(new ClickDetClear("PEDDISTR2"));
                    lbl_peddistr2_005.setOnClickListener(new ClickDet());
                    //lbl_pedcli2_005.setOnLongClickListener(new ClickDetClear(""));
                    //lbl_pedcli2_005.setOnClickListener(new ClickDet());
                }
            }
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Get first reading. Get additional location updates if necessary
        if (checkPlayServices()) {
            // Get best last location measurement meeting criteria
            mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);

            if (null == mBestReading
                    || mBestReading.getAccuracy() > MIN_LAST_READ_ACCURACY
                    || mBestReading.getTime() < System.currentTimeMillis() - TWO_MIN) {

                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;

                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

                // Schedule a runnable to unregister location listeners
                Executors.newScheduledThreadPool(1).schedule(new Runnable() {

                    @Override
                    public void run() {
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, PedidoV10Activity.this);
                    }

                }, ONE_MIN, TimeUnit.MILLISECONDS);
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            toast("GPS Desligado...Favor Religá-lo!");
        } else if (i == CAUSE_NETWORK_LOST) {
            toast("Sem Rede. Favor Verificar!");
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        toast("onConnectionFailed");

    }

    @Override
    public void onLocationChanged(Location location) {
        // Determine whether new location is better than current best
        // estimate
        if (null == mBestReading || location.getAccuracy() < mBestReading.getAccuracy()) {

            mBestReading = location;

            if (mBestReading.getAccuracy() < MIN_ACCURACY) {

                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

            }
        }
    }

    private Location bestLastKnownLocation(float minAccuracy, long minTime) {
        Location bestResult = null;
        float bestAccuracy = Float.MAX_VALUE;
        long bestTime = Long.MIN_VALUE;
        // Get the best most recent location currently available
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return bestResult;

        }

        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null) {
            float accuracy = mCurrentLocation.getAccuracy();
            long time = mCurrentLocation.getTime();

            if (accuracy < bestAccuracy) {
                bestResult = mCurrentLocation;
                bestAccuracy = accuracy;
                bestTime = time;
            }
        }

        // Return best reading or null
        if (bestAccuracy > minAccuracy || bestTime < minTime) {
            return null;
        }
        else {

            return bestResult;

        }
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();

        int result = googleAPI.isGooglePlayServicesAvailable(this);

        if(result != ConnectionResult.SUCCESS) {

            if(googleAPI.isUserResolvableError(result)) {

                googleAPI.getErrorDialog(this, result,PLAY_SERVICES_RESOLUTION_REQUEST).show();

            }

            return false;
        }

        return true;
    }

    private class ClickDet implements View.OnClickListener{


        public void onClick(View v) {

			/*
			 *
			 *  Rejeita Click
			 *
			 *
			 */

            if  (v.getId() == R.id.item_volta_cabec_005) {

                view = "CABEC";

                invalidateOptionsMenu();

                setViews(true, false);

                return ;

            }

            if (pedido.getEdicao() == null ){

                return;

            }


            if (pedido.getEdicao().getITEM().trim().isEmpty()){

                return;


            }
            switch (v.getId()) {

                case R.id.item_trash_005:{

                    try {

                        final Dialog dialog = new Dialog(PedidoV10Activity.this);

                        dialog.setContentView(R.layout.dlglibped);

                        dialog.setTitle("EXCLUSÃO DE ITEM");

                        final Button confirmar  = (Button) dialog.findViewById(R.id.btn_040_ok);
                        final Button cancelar   = (Button) dialog.findViewById(R.id.btn_040_can);
                        final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);
                        final TextView tvtexto2 = (TextView) dialog.findViewById(R.id.txt_040_texto2);

                        tvtexto1.setText("CONFIRMA A EXCLUSÃO DO ITEM:");
                        tvtexto2.setText(pedido.getEdicao().getNRO()+"-"+pedido.getEdicao().get_Produto());

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

                                    pedido.getEdicao().setNRO("");

                                    pedido.getEdicao().setQTD(0f);

                                    pedido.getEdicao().setDESCON(0f);

                                    pedido.getEdicao().setPRCVEN(pedido.getEdicao().getPRECOFORMACAO());

                                    pedido.getEdicao().setTOTAL(0f);

                                    pedido.getEdicao().setBONIQTD(0f);

                                    pedido.getEdicao().setBONIPREC(pedido.getEdicao().getPRECOFORMACAO());

                                    pedido.getEdicao().setBONITOTAL(0f);

                                    pedido.getEdicao().setCODVERBA("");

                                    pedido.getEdicao().set_Verba("");

                                    pedido.getEdicao().setDESCVER(0f);

                                    pedido.getEdicao().set_Acordo("");

                                    pedido.getEdicao().setACORDO("");

                                    pedido.getEdicao().setACORDO2("");

                                    pedido.getEdicao().set_Acordo2("");

                                    pedido.getEdicao().setSIMULADOR("");

                                    pedido.getEdicao().set_Simulador("");

                                    pedido.getEdicao().set_Simulador2("");

                                    pedido.getEdicao().setPEDDISTFIL("");

                                    pedido.getEdicao().setPEDDIST("");

                                    pedido.getEdicao().setPEDDISTITEM("");

                                    pedido.getEdicao().setPEDDISTFIL2("");

                                    pedido.getEdicao().setPEDDIST2("");

                                    pedido.getEdicao().setPEDDISTITEM2("");

                                    pedido.getEdicao().setPEDCLI("");

                                    pedido.getEdicao().setPEDCLI2("");

                                    pedido.validarItemEdicao();

                                    pedido.getEdicao()._ValidaOK();

                                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                                    pedido.recalculo();

                                    pedido.Validar();

                                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                                    cabec_refresh();

                                    cabec_onClick();

                                    detalhe_popula();

                                    adapter.refresh(pedido.getEdicao());


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


                case R.id.lbl_qtd_005:{

                    //Amarra qtd com tipo


                    if (pedido.getCabec().getTIPO().equals("003") || pedido.getCabec().getTIPO().equals("011") || pedido.getCabec().getTIPO().equals("013")  ){

                        //nao ativo get

                    } else {

                        ClickGetDados("Qual A Qtd Do Item ?", "QTD", 11, 0);
                    }
                    break;
                }


                case R.id.lbl_qtd_troca_005:{

                    ClickGetDados("Qual A Qtd Do Item ?", "QTD", 11, 0);

                    break;
                }


                case R.id.lbl_lote_005:{

                    ClickGetDados("Lote ?","LOTE",0,10);

                    break;
                }

                case R.id.lbl_dtempacotamento_005:{

                    ClickGetDados("Data Empacotamento ?","EMPACOTAMENTO",9,0);

                    break;
                }

                case R.id.lbl_vencimento_005:{

                    ClickGetDados("Vencimento ?","VENCIMENTO",9,0);

                    break;
                }

                case R.id.lbl_motivo_005:{

                    Intent i = new Intent(getBaseContext(), Help20Activity.class);
                    Bundle params = new Bundle();
                    params.putString("ARQUIVO", "MOTIVOSTROCADEV");
                    params.putString("TITULO", "CADASTRO DE MOTIVOS DE TROCA E DEVOLUÇÃO");
                    params.putString("MULTICHOICE", "N");
                    params.putString("ALIAS", "MOTIVOSDEV");
                    params.putString("ALIASVALUES", pedido.getCabec().getTIPO().equals("005") ? "01" : "02");
                    i.putExtras(params);
                    startActivityForResult(i, HelpInformation.MotivosTrocaDev );

                    break;


                }

                case R.id.lbl_obstroca_005:{

                    ClickGetDados("OBSERVAÇÃO ?","OBS",0,100);

                    break;
                }



                case R.id.lbl_preco_005:{

                    if (pedido.getCabec().getTIPO().equals("003") || pedido.getCabec().getTIPO().equals("011") || pedido.getCabec().getTIPO().equals("013")) {

                        //

                    } else {


                        if (pedido.getEdicao().getQTD().compareTo(0f) == 0){

                            toast("Favor Informar A Qtd Primeiro !!");

                        } else {

                            ClickGetDadosPreco("Qual O Preço Unit. ?", "PRCVEN");

                        }



                    }

                    break;
                }


                case R.id.lbl_preco_troca_005:{

                    ClickGetDadosPreco("Qual O Preço Unit. ?", "PRCVEN");

                    break;
                }

                case R.id.lbl_precobonif_005:{


                    if ("001#003#010#011#013".contains(pedido.getCabec().getTIPO())) {

                        if (pedido.getEdicao().getPEDDIST2().trim().isEmpty()) {

                            if (pedido.getEdicao().get_UsaPolitica().equals("N")) {

                                if (pedido.getEdicao().getBONIQTD().compareTo(0f) == 0) {

                                    toast("Favor Informar A Qtd De Bonificação Primeiro !!");

                                } else {

                                    ClickGetDadosPreco("Qual O Preço Unit. ?", "BONIPREC");

                                }
                            }

                        }
                    }

                    break;
                }


                case R.id.lbl_desconto_005:{


                    if (pedido.getCabec().getTIPO().equals("003") || pedido.getCabec().getTIPO().equals("011") || pedido.getCabec().getTIPO().equals("013")) {

                        //nao ativo get

                    } else {

                        if (pedido.getEdicao().getQTD().compareTo(0f) == 0){

                            toast("Favor Informar A Qtd Primeiro !!");

                        } else {

                            if (pedido.getEdicao().getSIMULADOR().trim().isEmpty() &&
                                    pedido.getEdicao().getACORDO().trim().isEmpty() &&
                                    pedido.getEdicao().getPEDDIST().trim().isEmpty() &&
                                    pedido.getEdicao().getCOTA().trim().isEmpty()) {

                                ClickGetDados("Qual Perc. Desconto ?", "DESCON", 6, 0);

                            }
                        }
                    }
                    break;

                }



                case R.id.lbl_qtdbonif_005:{


                    if ("001#003#010#011#013".contains(pedido.getCabec().getTIPO())){

                        ClickGetDados("Qual A Qtd Do Item ?","BONIQTD",11,0);

                    }


                    break;
                }


                case R.id.lbl_verba_005:{

                    if ( pedido.getEdicao().getSIMULADOR().trim().isEmpty() && pedido.getEdicao().getACORDO().trim().equals("") && (pedido.getEdicao().getDESCVER() > 0 ) ){

                        Intent i = new Intent(getBaseContext(),Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO"     , "VERBA");
                        params.putString("TITULO"      , "CADASTRO DE VERBA");
                        params.putString("MULTICHOICE" , "N");
                        params.putString("ALIAS"       , "VERBA");
                        params.putString("ALIASVALUES" , "N");
                        i.putExtras(params);
                        startActivityForResult(i, HelpInformation.HelpVerba );

                    }
                    break;
                }

                case R.id.lbl_verba2_005:{

                    if (  pedido.getEdicao().getPEDDIST2().equals("") && pedido.getEdicao().getACORDO2().trim().equals("") &&  pedido.getEdicao().getBONIQTD().compareTo(0f) > 0) {

                        Intent i = new Intent(getBaseContext(), Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO", "VERBA");
                        params.putString("TITULO", "CADASTRO DE VERBA");
                        params.putString("MULTICHOICE", "N");
                        params.putString("ALIAS", "VERBA");
                        params.putString("ALIASVALUES", "N");
                        i.putExtras(params);
                        startActivityForResult(i, HelpInformation.HelpVerbaBonif);
                    }

                    break;
                }
                case R.id.lbl_acordo_005:{

                    if (pedido.getEdicao().getSIMULADOR().trim().isEmpty() &&
                            pedido.getEdicao().getPEDDIST().trim().isEmpty() ) {

                        if ((pedido.getEdicao().getSIMULADOR().trim().isEmpty()) && (pedido.getEdicao().get_Verba().trim().equals("")) && (pedido.getEdicao().getDESCVER() > 0)) {
                            Intent i = new Intent(getBaseContext(), Help20Activity.class);
                            Bundle params = new Bundle();
                            params.putString("ARQUIVO", "ACORDO");
                            params.putString("TITULO", "CADASTRO DE ACORDOS");
                            params.putString("MULTICHOICE", "N");
                            params.putString("ALIAS", "ACORDOCLIENTE");
                            params.putString("ALIASVALUES", pedido.getCabec().getCODIGOFAT() + "|" + pedido.getCabec().getLOJAFAT() + "|B|"+pedido.getCliente().getREDE().replaceAll("000000", "XXXXXX")+"|");
                            i.putExtras(params);
                            startActivityForResult(i, HelpInformation.HelpAcordo);
                        }
                    }
                    break;
                }


                case R.id.lbl_acordo2_005:{

                    if ( pedido.getEdicao().getPEDDIST2().equals("") && (pedido.getEdicao().get_Verba2().trim().equals("")) && (pedido.getEdicao().getBONIQTD().compareTo(0f) > 0 ) ){

                        Intent i = new Intent(getBaseContext(),Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO"     , "ACORDO");
                        params.putString("TITULO"      , "CADASTRO DE ACORDOS");
                        params.putString("MULTICHOICE" , "N");
                        params.putString("ALIAS"       , "ACORDOCLIENTEBONIF");
                        params.putString("ALIASVALUES" , pedido.getCabec().getCODIGOFAT()+"|"+pedido.getCabec().getLOJAFAT()+"|B|"+pedido.getCliente().getREDE().replaceAll("000000", "XXXXXX")+"|");
                        i.putExtras(params);
                        startActivityForResult(i, HelpInformation.HelpAcordoBonif );
                    }
                    break;
                }


                case R.id.lbl_simulador_005:
                {

                    if ( pedido.getEdicao().getACORDO().trim().isEmpty()    &&
                            pedido.getEdicao().getPEDDIST().trim().isEmpty() && pedido.getEdicao().getCOTA().trim().isEmpty() ) {

                        Intent i = new Intent(getBaseContext(), Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO", "SIMULADOR");
                        params.putString("TITULO", "SIMULADORES DISPONÍVEIS");
                        params.putString("MULTICHOICE", "N");
                        params.putString("ALIAS", "CODSIMULADOR");
                        params.putString("ALIASVALUES", pedido.getEdicao().getPRODUTO() + "|" + pedido.getCabec().getCODIGOENT() + "|" + pedido.getCabec().getLOJAENT() + "|" + ( pedido.getClienteEntrega().getREDE().equals("000000") ? "XXXXXX" : pedido.getClienteEntrega().getREDE() ) );
                        i.putExtras(params);
                        startActivityForResult(i, HelpInformation.HelpSimulador);

                    }
                    break;
                }


                case R.id.lbl_peddistr_005:{

                    if (pedido.getEdicao().getSIMULADOR().trim().isEmpty() &&
                            pedido.getEdicao().getACORDO().trim().isEmpty() && pedido.getEdicao().getCOTA().trim().isEmpty()) {

                        Intent i = new Intent(getBaseContext(), Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO", "PEDDETTVS");
                        params.putString("TITULO", "PEDIDO DE DISTRIBUIÇÃO");
                        params.putString("MULTICHOICE", "N");
                        params.putString("ALIAS", "PEDIDODISTR");
                        params.putString("ALIASVALUES", pedido.getCabec().getCODIGOFAT() + "|" + pedido.getCabec().getLOJAFAT() + "|" + pedido.getEdicao().getPRODUTO()+"|"+pedido.getCliente().getREDE().replaceAll("000000", "XXXXXX")+"|");
                        i.putExtras(params);
                        startActivityForResult(i, HelpInformation.HelpPedDistr);

                    }

                    break;

                }


                case R.id.lbl_cota_005:{

                    if (pedido.getEdicao().getSIMULADOR().trim().isEmpty() &&
                            pedido.getEdicao().getACORDO().trim().isEmpty() && pedido.getEdicao().getPEDDIST().trim().isEmpty()) {

                        Intent i = new Intent(getBaseContext(), Cota_ViewActivity.class);
                        Bundle params = new Bundle();

                        params.putString("CLIENTE" , pedido.getCabec().getCODIGOFAT());
                        params.putString("LOJA"    , pedido.getCabec().getLOJAFAT());
                        params.putString("REDE"    , pedido.getCliente().getREDE());
                        params.putString("CANAL"   , pedido.getCliente().getCANAL());
                        params.putString("REGIAO"  , pedido.getCliente().getREGIAO());
                        params.putString("SMARCA"  , pedido.getEdicao().get_Marca());
                        params.putString("PRODUTO" , pedido.getEdicao().getPRODUTO());

                        params.putString("ENTREGA"         , pedido.getCabec().getENTREGA());
                        params.putFloat("DESCONTROCONTRATO",(Float)  pedido.getEdicao().getDESCCONTRATO());
                        params.putString("TAXAFINANCEIRA"  ,(String) pedido.getEdicao().getTAXAFIN());
                        params.putFloat("PERCPOL"          ,(Float)  pedido.getEdicao().getDESCONTOPOL());
                        params.putFloat("CONVERSAO"        ,(Float)  pedido.getEdicao().getCONVERSAO());
                        i.putExtras(params);
                        startActivityForResult(i,HelpInformation.HelpCota);

                    }

                    break;

                }

                case R.id.lbl_peddistr2_005:{

                    if (    pedido.getEdicao().getPEDDIST2().trim().isEmpty() && pedido.getEdicao().getSIMULADOR2().trim().isEmpty() &&
                            pedido.getEdicao().getACORDO2().trim().isEmpty()) {

                        Intent i = new Intent(getBaseContext(), Help20Activity.class);
                        Bundle params = new Bundle();
                        params.putString("ARQUIVO", "PEDDETTVS");
                        params.putString("TITULO", "PEDIDO DE DISTRIBUIÇÃO");
                        params.putString("MULTICHOICE", "N");
                        params.putString("ALIAS", "PEDIDODISTRBON");
                        params.putString("ALIASVALUES", pedido.getCabec().getCODIGOFAT() + "|" + pedido.getCabec().getLOJAFAT() + "|" + pedido.getEdicao().getPRODUTO()+"|"+pedido.getCliente().getREDE().replaceAll("000000", "XXXXXX")+"|");
                        i.putExtras(params);
                        startActivityForResult(i, HelpInformation.HelpPedDistrBonif);

                    }

                    break;

                }



                default:

                    break;
            }


        }
    }

    public void ClickGetDados(final String Titulo, final String FieldName, int maxlenght){

        final PedidoCabMb obj  = pedido.getCabec();

        final Dialog dialog    = new Dialog(PedidoV10Activity.this);

        dialog.setContentView(R.layout.gettexttopadrao);

        dialog.setTitle("Favor Digitar O Informação");

        final TextView titulo   = (TextView) dialog.findViewById(R.id.txt_570_texto1);

        final TextView mensagem = (TextView) dialog.findViewById(R.id.txt_570_error);

        final TextView tvCONTADOR = (TextView) dialog.findViewById(R.id.lbl_570_contador);

        titulo.setText(Titulo);

        final EditText campo = (EditText) dialog.findViewById(R.id.edCampo_570);

        if ("FDSPREVISTO#QTDENTREGA".contains(FieldName)){

            if (FieldName.equals("FDSPREVISTO")){

                campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                try {


                    campo.setText(String.valueOf((Float) obj.getFieldByName(FieldName)));


                } catch (Exception e){

                    campo.setText("");

                }
            }

            if (FieldName.equals("QTDENTREGA")){

                campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                try {

                    campo.setText(String.valueOf((Integer) obj.getFieldByName(FieldName)));


                } catch (Exception e){

                    campo.setText("");

                }}


        } else {


            campo.setText((String)obj.getFieldByName(FieldName));

        }


        if (maxlenght > 0) {

            campo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxlenght)});

            mensagem.setText("Tamanho Máximo: "+Integer.toString(maxlenght));
        }

        campo.setSelection(0,campo.getText().toString().length());

        campo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    Integer com = s.toString().trim().length();

                    tvCONTADOR.setText(String.valueOf(com));


                } catch (Exception e) {

                    Log.i("SAV", e.getMessage());

                }

            }
        });


        final Button confirmar    = (Button) dialog.findViewById(R.id.btn_570_ok);
        final Button cancelar     = (Button) dialog.findViewById(R.id.btn_570_can);

        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try

                {


                    if ("FDSPREVISTO#QTDENTREGA".contains(FieldName)) {


                        if (FieldName.equals("FDSPREVISTO")) {

                            try {

                                obj.setFieldByName(FieldName, Float.valueOf(campo.getText().toString()));

                                pedido.setClienteEntrega(obj.getCODIGOENT(), obj.getLOJAENT());

                                if (pedido.TemItens()) {

                                    perguntaOpcoesRecalculo();
                                }

                            } catch (Exception e) {

                                obj.setFieldByName(FieldName, 0f);
                            }

                        }

                        if (FieldName.equals("QTDENTREGA")) {

                            Integer qtd = 1;

                            try {

                                try {

                                    qtd = Integer.valueOf(campo.getText().toString());

                                    if (qtd <= 0 ){

                                        qtd = 1;

                                    }

                                } catch (Exception e){

                                    qtd = 1;

                                }

                                obj.setFieldByName(FieldName, qtd);

                                pedido.setClienteEntrega(obj.getCODIGOENT(), obj.getLOJAENT());

                                if (pedido.TemItens()) {

                                    perguntaOpcoesRecalculo();

                                }

                            } catch (Exception e) {

                                obj.setFieldByName(FieldName, 01);
                            }

                        }
                    }

                    else {

                        obj.setFieldByName(FieldName, campo.getText().toString());

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

                detalhe_popula();

                detalhe_onClick();

            }

        });

        dialog.show();

    }

    public void ClickGetDados(final String Titulo, final String FieldName, int tipo,int maxlenght){

        final Dialog dialog = new Dialog(PedidoV10Activity.this);

        dialog.setContentView(R.layout.gettexttopadrao);

        dialog.setTitle("Favor Digitar O Informação");

        final TextView titulo = (TextView) dialog.findViewById(R.id.txt_570_texto1);

        final TextView mensagem = (TextView) dialog.findViewById(R.id.txt_570_error);

        final TextView tvCONTADOR = (TextView) dialog.findViewById(R.id.lbl_570_contador);

        titulo.setText(Titulo);

        final EditText campo = (EditText) dialog.findViewById(R.id.edCampo_570);


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

            case 11://inteiro

                campo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                campo.addTextChangedListener(Mask.insert("######", campo));

                break;

            default:

                campo.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                break;
        }


        if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._string)){

            campo.setText((String)pedido.getEdicao().getFieldByName(FieldName));

        }

        if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._float)){

            Float value = (Float) pedido.getEdicao().getFieldByName(FieldName);

            campo.setText(format_02.format(value));

            if (FieldName.equals("DESCON")){

                campo.setText(format_05.format(value));

            }


        }

        if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._long)){


            Long value = (Long) pedido.getEdicao().getFieldByName(FieldName);

            campo.setText(format_02.format(value));


        }

        if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._integer)){

            Integer value = (Integer) pedido.getEdicao().getFieldByName(FieldName);

            campo.setText(format_02.format(value));


        }

        campo.setSelection(0,campo.getText().toString().length());

        campo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    Integer com = s.toString().trim().length();

                    tvCONTADOR.setText(String.valueOf(com));


                } catch (Exception e) {

                    Log.i("SAV", e.getMessage());

                }

            }
        });

        if (maxlenght > 0) {

            campo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxlenght)});

            mensagem.setText("Tamanho Máximo: "+Integer.toString(maxlenght));
        }

        campo.setSelection(0,campo.getText().toString().length());

        final Button confirmar    = (Button) dialog.findViewById(R.id.btn_570_ok);
        final Button cancelar     = (Button) dialog.findViewById(R.id.btn_570_can);

        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try

                {

                    if ("#EMPACOTAMENTO#VENCIMENTO".contains(FieldName)) {

                        try {

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                            sdf.setLenient(false);

                            sdf.parse(campo.getText().toString());

                        } catch (java.text.ParseException e) {


                            mensagem.setText("Data Inválida !!");


                            return;

                        }
                    }


                    if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._string)){

                        pedido.getEdicao().setFieldByName(FieldName,campo.getText().toString());

                    }

                    if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._float)){

                        Float value = 0f;

                        try
                        {
                            value = Float.valueOf(campo.getText().toString().replaceAll(",", "."));

                        } catch (Exception e){

                            value = 0f;

                        }

                        if (FieldName.equals("BONIQTD")){

                            if ( pedido.getEdicao().getBONIPREC().compareTo(0f)==0) {

                                pedido.getEdicao().setBONIPREC(pedido.getEdicao().getPRECOFORMACAO());

                            }

                        }

                        if (FieldName.equals("DESCON")){


                            pedido.getEdicao().setPRCVEN(pedido.getEdicao().getPRECOFORMACAO());

                        }

                        pedido.getEdicao().setFieldByName(FieldName, value);

                        if (FieldName.equals("DESCON")){

                            pedido.validarItemEdicao();

                        }


                    }

                    if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._long)){

                        Long value = 0l;

                        try
                        {
                            value = Long.valueOf(campo.getText().toString());

                        } catch (Exception e){

                            value = 0l;

                        }

                        pedido.getEdicao().setFieldByName(FieldName, value);

                    }

                    if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._integer)){

                        Integer value = 0;

                        try
                        {
                            value = Integer.valueOf(campo.getText().toString());

                        } catch (Exception e){

                            value = 0;

                        }

                        pedido.getEdicao().setFieldByName(FieldName, value);

                    }


                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                }


                if (FieldName.equals("QTD") || FieldName.equals("BONIQTD")){

                    pedido.getEdicao().setNRO(pedido.getCabec().getNRO());

                }


                pedido.validarItemEdicao();

                pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1, pedido.getEdicao().ToFast());

                pedido.recalculo();

                pedido.Validar();

                pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                cabec_refresh();

                cabec_onClick();

                detalhe_popula();

                adapter.refresh(pedido.getEdicao());


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

        final Dialog dialog = new Dialog(PedidoV10Activity.this);

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


                if (FieldName.equals("BONIPREC")){

                    pedido.getEdicao().setFieldByName(FieldName, value);


                } else {

                    pedido.getEdicao().setDESCON(0f);

                    pedido.ajustaPrcVenByPreco(pedido.getEdicao(), value);

                    pedido.getEdicao().setFieldByName(FieldName, value);

                }
                dialog.dismiss();

                pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM())-1,pedido.getEdicao().ToFast());

                pedido.validarItemEdicao();

                pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1,pedido.getEdicao().ToFast() );

                pedido.recalculo();

                pedido.Validar();

                pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                cabec_refresh();

                cabec_onClick();

                detalhe_popula();

                adapter.refresh(pedido.getEdicao());


            }
        });


        campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        if (pedido.getEdicao().getPEDDIST().trim().isEmpty() && pedido.getEdicao().getSIMULADOR().trim().isEmpty() && pedido.getEdicao().getCOTA().trim().isEmpty()){

            preco.setText(format_04.format(pedido.getEdicao().getPRECOFORMACAO()));

        } else {

            if (!pedido.getEdicao().getSIMULADOR().trim().isEmpty()) {

                try {

                    SimuladorDAO simuladordao = new SimuladorDAO();

                    simuladordao.open();

                    Simulador simulador = simuladordao.seek(new String[]{pedido.getEdicao().getSIMULADOR(), pedido.getCabec().getCODIGOENT(), pedido.getCabec().getLOJAENT(), pedido.getEdicao().getPRODUTO()});

                    simuladordao.close();

                    if (simulador != null) {

                        preco.setText(format_04.format(simulador.getPRECOAPROVADO()));

                    } else {

                        preco.setText(format_04.format(0f));

                    }

                } catch (Exception e){


                    preco.setText(format_04.format(0f));

                }

            }

            if (!pedido.getEdicao().getPEDDIST().trim().isEmpty()) {

                try {

                    // Procurar O Pedido Distribuição

                    PedDetTvsDAO dao = new PedDetTvsDAO();

                    dao.open();

                    PedDetTvs detalhe = dao.seek(new String[]{pedido.getEdicao().getPEDDISTFIL(), pedido.getEdicao().getPEDDIST(), pedido.getEdicao().getPEDDISTITEM()});

                    dao.close();

                    if (detalhe != null) {

                        preco.setText(format_04.format(detalhe.getPRCVEN()));


                    } else {

                        preco.setText(format_04.format(0f));

                    }

                } catch (Exception e) {

                    preco.setText(format_04.format(0f));

                }

            }


            if (!pedido.getEdicao().getCOTA().trim().equals("")){

                try {


                    // Procurar A Cota

                    CotaDAO dao = new CotaDAO();

                    dao.open();

                    Cota detalhe = dao.seek(new String[]{pedido.getEdicao().getCOTA()});

                    dao.close();

                    if (detalhe != null){

                        detalhe.CalculoFinal(pedido.getEdicao().getDESCCONTRATO(),pedido.getEdicao().getTAXAFIN(),pedido.getEdicao().getCONVERSAO());

                        preco.setText(format_04.format(detalhe.get_PRECOFINAL()));

                    } else {

                        preco.setText(format_04.format(0f));

                    }


                } catch (Exception e) {

                    preco.setText(format_04.format(0f));

                }

            }



        }


        if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._float)){

            Float value = (Float) pedido.getEdicao().getFieldByName(FieldName);

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

                    if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._float)) {

                        if (FieldName.equals("BONIPREC")) {

                            Float precoMaximo = pedido.getEdicao().getPRECOFORMACAO() * (1 + (pedido.getEdicao().getACRESCIMOMAIS() / 100));

                            Float descMaximo = pedido.getEdicao().getDESCONTOMAIS() + pedido.getEdicao().getDESCONTOPOL();

                            BigDecimal precven;

                            BigDecimal desc;

                            precven = new BigDecimal(pedido.getEdicao().getPRECOFORMACAO());

                            desc = new BigDecimal(descMaximo);

                            Double preco = precven.doubleValue() - (precven.doubleValue() * (desc.doubleValue() / 100));

                            precven = new BigDecimal(preco);

                            precven = precven.setScale(2, BigDecimal.ROUND_HALF_UP);

                            precven.doubleValue();

                            Float precoMinimo = precven.floatValue();

                            Float value = 0f;

                            try {
                                value = Float.valueOf(campo.getText().toString().replaceAll(",", "."));

                            } catch (Exception e) {

                                value = 0f;

                            }

                            if (((Float.compare(value, precoMaximo)) > 0) || (Float.compare(value, precoMinimo)) < 0) {

                                if (Float.compare(value, precoMaximo) > 0)

                                    Toast.makeText(getBaseContext(), "Preço Não Pode Ser Maior Que: " + format_02.format(precoMaximo), Toast.LENGTH_LONG).show();


                                if (Float.compare(value, precoMinimo) < 0)

                                    Toast.makeText(getBaseContext(), "Preço Não Pode Ser Menor Que: " + format_02.format(precoMinimo), Toast.LENGTH_LONG).show();

                            } else {

                                pedido.getEdicao().setFieldByName(FieldName, value);


                            }

                        } else {

                            Float precoMaximo = pedido.getEdicao().getPRECOFORMACAO() * (1 + (pedido.getEdicao().getACRESCIMOMAIS() / 100));

                            Float precoMinimo = pedido.getEdicao().getPRECOFORMACAO() - ( pedido.getEdicao().getPRECOFORMACAO() * ( (pedido.getEdicao().getDESCONTOPOL() + pedido.getEdicao().getDESCONTOMAIS())/100));

                            Float value = 0f;

                            try {
                                value = Float.valueOf(campo.getText().toString().replaceAll(",", "."));

                            } catch (Exception e) {

                                value = 0f;

                            }

                            if ((Float.compare(value, precoMaximo)) > 0 || (Float.compare(value, precoMinimo)) < 0 ) {

                                if ((Float.compare(value, precoMaximo)) > 0 ) {

                                    Toast.makeText(getBaseContext(), "Preço Não Pode Ser Maior Que: " + format_02.format(precoMaximo), Toast.LENGTH_LONG).show();

                                }


                                if ( (Float.compare(value, precoMinimo)) < 0  ) {

                                    Toast.makeText(getBaseContext(), "Preço Não Pode Ser Menor Que: " + format_02.format(precoMinimo), Toast.LENGTH_LONG).show();

                                }
                            }

                            else {

                                pedido.getEdicao().setDESCON(0f);

                                pedido.ajustaPrcVenByPreco(pedido.getEdicao(), value);

                                pedido.getEdicao().setFieldByName(FieldName, value);

                            }
                        }
                    }
                    pedido.validarItemEdicao();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1,pedido.getEdicao().ToFast() );

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));


                } catch  (Exception e){

                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1,pedido.getEdicao().ToFast() );

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                }
                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

                cabec_refresh();

                cabec_onClick();

                detalhe_popula();

                adapter.refresh(pedido.getEdicao());


            }

        });

        dialog.show();

    }

    public void ClickGetDadosPrecoBonif(final String Titulo, final String FieldName){

        final Dialog dialog = new Dialog(PedidoV10Activity.this);

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


                pedido.getEdicao().setFieldByName(FieldName, value);

                dialog.dismiss();

                pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM())-1,pedido.getEdicao().ToFast());

                pedido.validarItemEdicao();

                pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1,pedido.getEdicao().ToFast() );

                pedido.recalculo();

                pedido.Validar();

                pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                cabec_refresh();

                cabec_onClick();

                detalhe_popula();

                adapter.refresh(pedido.getEdicao());


            }
        });


        campo.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._float)){



            Float value = (Float) pedido.getEdicao().getFieldByName(FieldName);

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

                    if (pedido.getEdicao().getTypeByName(FieldName).equals(ObjRegister._float)){

                        Float precoMaximo = pedido.getEdicao().getPRECOFORMACAO() * ( 1 + (pedido.getEdicao().getACRESCIMOMAIS()/100) );

                        Float value = 0f;

                        try {
                            value = Float.valueOf(campo.getText().toString().replaceAll(",", "."));

                        } catch (Exception e){

                            value = 0f;

                        }

                        if ( (Float.compare(value, precoMaximo)) > 0 ){


                            Toast.makeText(getBaseContext(), "Preço Não Pode Ser Maior Que: "+format_02.format(precoMaximo),Toast.LENGTH_LONG).show();


                        } else {

                            pedido.getEdicao().setFieldByName(FieldName, value);


                        }

                        pedido.validarItemEdicao();

                        pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1,pedido.getEdicao().ToFast() );

                        pedido.recalculo();

                        pedido.Validar();

                        pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                    }
                } catch  (Exception e){

                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                    pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1,pedido.getEdicao().ToFast() );

                    pedido.recalculo();

                    pedido.Validar();

                    pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

                }
                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

                cabec_refresh();

                cabec_onClick();

                detalhe_popula();

                adapter.refresh(pedido.getEdicao());


            }

        });

        dialog.show();

    }

    private class ClickDetClear implements View.OnLongClickListener{

        private String      field;

        public ClickDetClear(String field) {

            this.field = field;

        }




        @Override
        public boolean onLongClick(View v) {


            pedido.getEdicao().setFieldByName(field, "");

            if (field.equals("CODVERBA")){

                pedido.getEdicao().setACORDO("");
                pedido.getEdicao().setCODVERBA("");
                pedido.getEdicao().set_Verba("");
            }

            if (field.equals("CODVERBA2")){

                pedido.getEdicao().setACORDO2("");
                pedido.getEdicao().setCODVERBA2("");
                pedido.getEdicao().set_Verba2("");

            }

            if (field.equals("ACORDO")){

                pedido.getEdicao().setCODVERBA("");
                pedido.getEdicao().set_Verba("");
                pedido.getEdicao().set_Acordo("");

            }

            if (field.equals("ACORDO2")){

                pedido.getEdicao().setCODVERBA2("");
                pedido.getEdicao().set_Verba2("");
                pedido.getEdicao().set_Acordo2("");

            }


            if (field.equals("SIMULADOR")){

                pedido.getEdicao().setSIMULADOR("");
                pedido.getEdicao().setPRCVEN(pedido.getEdicao().getPRECOFORMACAO());

            }

            if (field.equals("PEDDIST")){

                pedido.getEdicao().setDESCON(0f);
                pedido.getEdicao().setPRCVEN(pedido.getEdicao().getPRECOFORMACAO());
                pedido.getEdicao().setPEDDISTFIL("");
                pedido.getEdicao().setPEDDIST("");
                pedido.getEdicao().setPEDDISTITEM("");
                pedido.getEdicao().setCODVERBA("");
                pedido.getEdicao().set_Verba("");
                pedido.getEdicao().set_Acordo("");

            }

            if (field.equals("COTA")){

                pedido.getEdicao().setDESCON(0f);
                pedido.getEdicao().setPRCVEN(pedido.getEdicao().getPRECOFORMACAO());
                pedido.getEdicao().setPEDDISTFIL("");
                pedido.getEdicao().setPEDDIST("");
                pedido.getEdicao().setPEDDISTITEM("");
                pedido.getEdicao().setCODVERBA("");
                pedido.getEdicao().set_Verba("");
                pedido.getEdicao().set_Acordo("");
                pedido.getEdicao().setCOTA("");

            }

            if (field.equals("PEDDISTR2")){
                pedido.getEdicao().setBONIPREC(pedido.getEdicao().getPRCVEN());
                pedido.getEdicao().setPEDDISTFIL2("");
                pedido.getEdicao().setPEDDIST2("");
                pedido.getEdicao().setPEDDISTITEM2("");
                pedido.getEdicao().setCODVERBA2("");
                pedido.getEdicao().set_Verba2("");
                pedido.getEdicao().set_UsaPolitica("N");
                pedido.getEdicao().set_Acordo2("");
            }


            pedido.validarItemEdicao();

            pedido.getLsDetalhe().set(Integer.valueOf(pedido.getEdicao().getITEM()) - 1,pedido.getEdicao().ToFast() );

            pedido.recalculo();

            pedido.Validar();

            pedido.getEdicao().ImportFast(pedido.getLsDetalhe().get(Integer.valueOf(pedido.getEdicao().getITEM()) - 1));

            detalhe_popula();

            adapter.refresh(pedido.getEdicao());



            return true;
        }

    }

    private void cabec_refresh() {

        List<String[]> opcoes;

         /*
          *
          * Validacao
          *
          *
          */

        if (!pedido.getCabec().isValidoByName("CODIGOFAT")) {

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
        txt_upload_001.setText(pedido.getCabec().getDTTRANS() + " " + pedido.getCabec().getHOTRANS());
        txt_id_001.setText(pedido.getCabec().getNRO());
        txt_protheus_001.setText(pedido.getCabec().getCPROTHEUS());
        txt_status_001.setText(pedido.getCabec().get_Status());
        txt_erro_001.setText(pedido.getCabec().getMENSAGEM());
        txt_cliente_001.setText(pedido.getCabec().get_ClienteFatRazao());
        txt_cnpj_001.setText(pedido.getCabec().get_ClienteFatCnpj());
        txt_ie_001.setText(pedido.getCabec().get_ClienteFatIE());
        txt_pedidocliente_001.setText(pedido.getCabec().getPEDCLIENTE());

        opcoes = pedido.getCabec().getlsTipos();

        sp_tipopedido_001.setEnabled(false);

        tipoadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes, "Tipo Ped.", pedido.getCabec().isValidoByName("TIPO"));

        sp_tipopedido_001.setAdapter(tipoadapter);

        try {

            sp_tipopedido_001.setSelection(pedido.getCabec().getlsTiposIndex());

        } catch (Exception e) {

            sp_tipopedido_001.setSelection(0);

        }


        txt_retira_001.setText(pedido.getCabec().get_Retira());

        txt_emissao_001.setText(pedido.getCabec().getEMISSAO());

        txt_clienteentrega_001.setText(pedido.getCabec().get_ClienteEntRazao());


        txt_obsped_001.setText(pedido.getCabec().getOBSPED());
        txt_obsnf_001.setText(pedido.getCabec().getOBSNF());
        txt_qtdbonif_001.setText(format_02.format(pedido.getCabec().getQTDBINIFICADA()));
        txt_totalbonif_001.setText(format_02.format(pedido.getCabec().getVLRBONIFICADO()));
        txt_totalpedido_001.setText(format_02.format(pedido.getCabec().getTOTALPEDIDO()));
        txt_totaldesc_001.setText(format_02.format(pedido.getCabec().getTOTALDESCONTO()));
        txt_totaldescverba_001.setText(format_02.format(pedido.getCabec().getTOTALVERBA()));
        txt_fardos_previstos_001.setText(format_02.format(pedido.getCabec().getFDSPREVISTO()));
        txt_fardos_realizados_001.setText(format_02.format(pedido.getCabec().getFDSREAIS()));

        int indice = pedido.getIndiceByCodigo(pedido.getCabec().getTABPRECO());

        if (indice != -1) {


            if (!pedido.getLsTabPrecoCabec().get(indice).getFLAGFAIXA().equals("1")) {

                txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


            } else {

                if (pedido.getCabec().getFDSPREVISTO().compareTo(pedido.getCabec().getFDSREAIS()) <= 0) {

                    txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

                } else {

                    txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

        }

        txt_qtd_entrega_001.setText(String.valueOf(pedido.getCabec().getQTDENTREGA()));
        txt_peso_liquido_001.setText(format_04.format(pedido.getCabec().getPESOLIQUIDO())+"Kg");
        txt_peso_bruto_001.setText(format_04.format(pedido.getCabec().getPESOBRUTO())+"Kg");
    }

    private void cabec_onClick() {

        List<String[]> opcoes;


         /*
          *
          *  Listener dos botões
          *
          *
          */

        item_details_001.setOnClickListener(new Click());

        if (editable){

            lbl_cliente_001.setOnClickListener(new Click());
            lbl_cliente_001.setOnLongClickListener(new ClickCabecClear("CODIGOFAT"));

            if (!pedido.getCliente().getCODIGO().equals("")){

                int index = 0;

                Integer prazo;

                try {

                    prazo      = Integer.parseInt(pedido.getCliente().getDIASPEDPROG().trim());

                }
                catch (Exception e){

                    prazo = 7;

                }

                SimpleDateFormat format_chave = new SimpleDateFormat("dd/MM/yyyy",     new Locale("pt", "BR"));

                SimpleDateFormat format_full  = new SimpleDateFormat("dd/MM/yyyy EEE", new Locale("pt", "BR"));

                opcoes  = new ArrayList<String[]>();

                Date emissao = new Date();

                final Calendar c = Calendar.getInstance();

                c.setTime(emissao);

                for ( int x = 0 ; x < prazo ; x = ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) ? x : ++x) {

                    if (x==0){

                        try {

                            emissao = format_chave.parse(pedido.getCabec().getEMISSAO());

                        } catch (ParseException e) {

                            emissao = new Date();

                        }

                        c.setTime(emissao);

                    } else {


                        c.setTime(emissao);

                        c.add(Calendar.DATE, + 1);


                    }

                    emissao = c.getTime();

                    if (pedido.getCabec().getENTREGA().equals(format_chave.format(emissao))){

                        index = x;

                    }

                    if ( (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ){

                        c.setTime(emissao);

                        c.add(Calendar.DATE, + 1);

                        emissao = c.getTime();

                        if (x==0) x++;

                        continue;

                    }

                    opcoes.add(new String[]{String.valueOf(x + 1) + "ª", format_full.format(emissao)});

                }

                sp_entrega_001.setEnabled(true);

                entregaadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes,"Entrega",pedido.getCabec().isValidoByName("ENTREGA"));

                sp_entrega_001.setAdapter(entregaadapter);

                sp_entrega_001.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        entregaadapter.setEscolha(position);

                        Object lixo = sp_entrega_001.getSelectedItem();

                        pedido.getCabec().setENTREGA(((String[]) lixo)[1].substring(0,10));

                        pedido.getCabec().setENTREGA(((String[]) lixo)[1].substring(0,10));
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

                if (pedido.getCabec().getTOTALPEDIDO().equals(0f)){

                    sp_tipopedido_001.setEnabled(true);

                } else {

                    sp_tipopedido_001.setEnabled(false);

                }

                tipoadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.conexoes_opcoes, pedido.getCabec().getlsTipos(),"Tipo Ped.",pedido.getCabec().isValidoByName("TIPO"));

                sp_tipopedido_001.setAdapter(tipoadapter);

                sp_tipopedido_001.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (!tipoadapter.isInicializacao) {

                            int index = 0;

                            tipoadapter.setEscolha(position);

                            Object lixo = sp_tipopedido_001.getSelectedItem();

                            pedido.getCabec().setTIPO(((String[]) lixo)[0]);

                            pedido.SetTipoPedido(((String[]) lixo)[0]);

                            List<String[]> opcoes = new ArrayList<String[]>();

                            int x = 0;

                            for (CondPagto op : pedido.getLsCondPagtoByFiltro()) {

                                if (op.getCODIGO().equals(pedido.getCliente().getCONDPAGTO())) {

                                    index = x;

                                }

                                opcoes.add(new String[]{op.getCODIGO(), op.getCODIGO() + "-" + op.getDESCRICAO()});

                                x++;

                            }

                            condadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes, "Cond Pagto", pedido.getCabec().isValidoByName("COND"));

                            sp_condpagto_001.setAdapter(condadapter);

                            condadapter.setIsInicializacao(true);

                            sp_condpagto_001.setSelection(index);

                            pedido.getCabec().setCOND(opcoes.get(index)[0]);

                            pedido.getCabec().set_Cond(opcoes.get(index)[0] + "-" + opcoes.get(index)[1]);

                        } else {

                            tipoadapter.isInicializacao = false;

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

                try {

                    sp_tipopedido_001.setSelection(pedido.getCabec().getlsTiposIndex());

                } catch (Exception e){

                    sp_tipopedido_001.setSelection(0);
                }


                index = -1;

                int i     = 0;

                opcoes = new ArrayList<String[]>();

                for (CondPagto op : pedido.getLsCondPagtoByFiltro() ){

                    opcoes.add(new String[] {op.getCODIGO(),op.getCODIGO()+"-"+op.getDESCRICAO()});

                    if (op.getCODIGO().equals(pedido.getCabec().getCOND())){

                        index = i;

                    }

                    i++;

                }

                if (index==-1){

                    opcoes.add(new String[] {"01"," "});

                    index = 0;

                }

                sp_condpagto_001.setEnabled(true);

                condadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes,"Cond Pagto",pedido.getCabec().isValidoByName("COND"));

                sp_condpagto_001.setAdapter(condadapter);

                sp_condpagto_001.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        condadapter.setEscolha(position);

                        Object lixo = sp_condpagto_001.getSelectedItem();

                        pedido.setCondpagto(pedido.seekcp(((String[]) lixo)[0]));

                        pedido.getCabec().setCOND(((String[]) lixo)[0]);

                        pedido.getCabec().set_Cond(((String[]) lixo)[1]);

                        if (!condadapter.isInicializacao()) {

                            if (pedido.TemItens()){

                                if ( (!pedido.getCondpagto().getCODIGO().equals("033")) &&  !("005#006#007".contains(pedido.getCabec().getTIPO()))) {

                                    perguntaOpcoesRecalculo() ;

                                }
                            }

                        } else {

                            condadapter.setIsInicializacao(false);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

                sp_condpagto_001.setSelection(index);

                condadapter.setIsInicializacao(true);

                sp_condpagto_001.setEnabled(true);

                index = -1;

                i     = 0;

                opcoes = new ArrayList<String[]>();

                for (TabPrecoCabec op : pedido.getLsTabPrecoCabec() ){

                    opcoes.add(new String[] {op.getCODIGO(),op.getCODIGO()+"-"+op.getDESCRICAO()});

                    if (op.getCODIGO().equals(pedido.getCabec().getTABPRECO())){


                        index = i;


                    }

                    i++;

                }

                if (index==-1){

                    opcoes.add(new String[] {"01"," "});

                    index = 0;

                }

                sp_tabpreco_001.setEnabled(true);

                tabprecoadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes,"Tab.Preço",pedido.getCabec().isValidoByName("TABPRECO"));

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

                            pedido.getCabec().setTABPRECO(((String[]) lixo)[0]);

                            if (pedido.getLsTabPrecoCabec().get(indice).getFLAGFAIXA().equals("2")) {

                                pedido.getCabec().setFDSPREVISTO(0f);

                                txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                            } else {

                                if (pedido.getLsTabPrecoCabec().get(indice).getFLAGFAIXA().equals("1"))
                                {
                                    pedido.getCabec().setFDSPREVISTO(pedido.getLsTabPrecoCabec().get(indice).getFAIXAATE());

                                    if (pedido.getCabec().getFDSPREVISTO().compareTo(pedido.getCabec().getFDSREAIS()) <= 0) {

                                        txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(R.drawable.erro_20_vermelho, 0, 0, 0);

                                    } else {

                                        txt_fardos_realizados_001.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    }
                                }
                            }


                            txt_fardos_previstos_001.setText(format_02.format(pedido.getCabec().getFDSPREVISTO()));


                            if (!tabprecoadapter.isInicializacao()) {

                                try {

                                    pedido.atualizaTabela();

                                    if (pedido.TemItens()){

                                        perguntaOpcoesRecalculo();

                                    }


                                } catch (ExceptionItemProduto exceptionItemProduto) {

                                    toast(exceptionItemProduto.getMessage());

                                } catch (Exception e) {

                                    toast(e.getMessage());
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
                lbl_qtd_entrega_001.setOnClickListener(new Click());
            }
        }

    }

    public void ClickGetDadosRetira(final String Titulo, final String FieldName){


        final Dialog dialog = new Dialog(PedidoV10Activity.this);

        dialog.setContentView(R.layout.getdescret);

        dialog.setTitle("Define O Valor Do Campo Cliente Retira.");

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

                pedido.getCabec().setRETIRA("2");

                pedido.getCabec().setDESCRET(value);

                if ((dialog != null)){

                    if (dialog.isShowing()){

                        dialog.dismiss();

                    }

                }

                if ( (pedido.TemItens()) && !("005#006#007".contains(pedido.getCabec().getTIPO())) ){

                    perguntaOpcoesRecalculo();

                }

                cabec_refresh();

                cabec_onClick();


            }
        });

        bt_retira_sim_119.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try

                {

                    if (pedido.getCabec().getTypeByName(FieldName).equals(ObjRegister._float)){

                        Float value = 0f;

                        pedido.getCabec().setFieldByName(FieldName, value);

                        pedido.getCabec().setRETIRA("1");

                        pedido.getCabec().setDESCRET(value);

                        if ((dialog != null)){

                            if (dialog.isShowing()){

                                dialog.dismiss();

                            }

                        }

                        if ( (pedido.TemItens()) && !("005#006#007".contains(pedido.getCabec().getTIPO())) ) {

                            perguntaOpcoesRecalculo();

                        }

                        cabec_refresh();

                        cabec_onClick();


                    }
                } catch  (Exception e){

                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                    pedido.recalculo();

                    pedido.Validar();



                }


                cabec_refresh();

                cabec_onClick();

            }

        });

        dialog.show();

    }

    private void perguntaOpcoesRecalculo() {

        ArrayList<String[]> opcoes = new ArrayList<>();

        opcoes.add(new String[] {"001", "MANTÉM DESCONTO"});
        opcoes.add(new String[] {"002", "MANTÉM PREÇO DIGITADO"});
        opcoes.add(new String[] {"003", "MANTÉM PREÇO BASE DA TABELA"});


        final Dialog dialog = new Dialog(PedidoV10Activity.this);

        dialog.setContentView(R.layout.getopcaorecalculo);

        dialog.setTitle("Recalculando Pedido !!!");

        final EditText observacao = (EditText) dialog.findViewById(R.id.edit_observacao_117);

        final Spinner spOpcoes = (Spinner) dialog.findViewById(R.id.edit_opcoes_120);

        final Button confirmar = (Button) dialog.findViewById(R.id.bt_confirma_120);

        opcoesAdapter  = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes, "Opções:",true);

        spOpcoes.setAdapter(opcoesAdapter);

        spOpcoes.setSelection(0);


        confirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)

            {

                Object lixo = spOpcoes.getSelectedItem();

                int opcao = Integer.parseInt((((String[]) lixo)[0]));

                AtualizaPedidoThread pedido = new AtualizaPedidoThread(mHandlerAtualizaPedido,opcao);

                pedido.start();

                dialog.dismiss();

                return;

            }

        });

        dialog.show();

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
            opcoes.add(new String[]{"05", "MIX"});
        }

        viewadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, opcoes,"",true);

        sp_view_088.setAdapter(viewadapter);

        sp_view_088.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                viewadapter.setEscolha(position);

                Object lixo = sp_view_088.getSelectedItem();

                spCategoria.setSelection(0);

                spMarcas.setSelection(0);

                if ( ((String[]) lixo)[0].equals("04")   ){

                    adapter.setViewPedido(true);

                } else {

                    adapter.setViewPedido(false);

                }


                adapter.setSubFiltro(((String[]) lixo)[0]);

                adapter.refreshwithfiltro();

                if (inc_pedidomb_filtro.getVisibility() == View.VISIBLE){

                    if (adapter.isViewPedido()){

                        spCategoria.setVisibility(View.GONE);

                        spMarcas.setVisibility(View.GONE);

                    } else {

                        spCategoria.setVisibility(View.VISIBLE);

                        spMarcas.setVisibility(View.VISIBLE);

                    }
                }

                pedido.setEdicao(new PedidoDetMb());

                PosEdicao          = "";

                detalhe_popula();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        sp_view_088.setSelection(0);

        categoriaadapter = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row,pedido.getLsCategoria(),"Categoria: ",true);

        spCategoria.setAdapter(categoriaadapter);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoriaadapter.setEscolha(position);
                Object lixo = spCategoria.getSelectedItem();
                adapter.setFilter(((String[]) lixo)[0], adapter.Filtro_marca);
                pedido.setEdicao(new PedidoDetMb());
                PosEdicao = "";
                detalhe_popula();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spCategoria.setSelection(0);

        marcaadapter   = new defaultAdapter(PedidoV10Activity.this, R.layout.choice_default_row, pedido.getLsMarca(),"Marca: ",true);

        spMarcas.setAdapter(marcaadapter);

        spMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                marcaadapter.setEscolha(position);
                Object lixo = spMarcas.getSelectedItem();
                adapter.setFilter(adapter.Filtro_grupo,((String[]) lixo)[0]);
                pedido.setEdicao(new PedidoDetMb());
                PosEdicao          = "";
                detalhe_popula();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spMarcas.setSelection(0);


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

                    break;
                }
                case R.id.item_max_min_002:{

                    String op = obj.get_View().equals("G") ? "P" : "G";

                    break;
                }

                case R.id.item_trash_001:{

                    break;
                }

                case R.id.item_details_001:{

                    try {


                        if (!tipoPedidoSemFardos.contains(pedido.getCabec().getTIPO())) {

                            if  ( (pedido.getCabec().getQTDENTREGA() == 1) && ("012#013".contains(pedido.getCabec().getTIPO()))){

                                try {

                                    final Dialog dialog = new Dialog(PedidoV10Activity.this);

                                    dialog.setContentView(R.layout.dlglibped);

                                    dialog.setTitle("QTD ENTREGA");

                                    final Button confirmar = (Button) dialog.findViewById(R.id.btn_040_ok);
                                    final Button cancelar = (Button) dialog.findViewById(R.id.btn_040_can);
                                    final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_040_texto1);
                                    final TextView tvtexto2 = (TextView) dialog.findViewById(R.id.txt_040_texto2);

                                    confirmar.setText("Sim. Vou Manter 1 Entrega");
                                    cancelar.setText("Ops! Vou Corrigir !");

                                    tvtexto1.setText("Pedido Realmente Só Possui Uma Entrega ?");
                                    tvtexto2.setText("Depois De Transmitido Não Será Mais Possível Alterar OK !!");

                                    cancelar.setOnClickListener(new View.OnClickListener() {

                                        public void onClick(View v) {

                                            dialog.dismiss();

                                        }
                                    });

                                    confirmar.setOnClickListener(new View.OnClickListener() {

                                        public void onClick(View v) {

                                            dialog.dismiss();

                                            if ((pedido.getCabec().getFDSPREVISTO().compareTo(0f) == 0) && (pedido.getCabec().getTABPRECO().compareTo("500") >= 0)) {

                                                toast("Favor Informar FARDOS PREVISTOS !");


                                            } else {

                                                view = "ITENS";

                                                invalidateOptionsMenu();

                                                setViews(false, true);

                                            }


                                        }

                                    });


                                    dialog.show();


                                } catch (Exception e) {

                                    toast(e.getMessage());

                                }

                            } else {

                                if ((pedido.getCabec().getFDSPREVISTO().compareTo(0f) == 0) && (pedido.getCabec().getTABPRECO().compareTo("500") >= 0)) {

                                    toast("Favor Informar FARDOS PREVISTOS !");


                                } else {

                                    view = "ITENS";

                                    invalidateOptionsMenu();

                                    setViews(false, true);

                                }


                            }

                        } else {

                            view = "ITENS";

                            invalidateOptionsMenu();

                            setViews(false, true);


                        }
                    } catch (Exception e){

                        toast(e.getMessage());

                    }
                    break;
                }

                case R.id.lbl_emissao_001:
                {

                    ClickGetDados("DATA DA EMISSÃO","EMISSAO",0);

                    break;
                }


                case R.id.lbl_pedidocliente_001:
                {

                    ClickGetDados("Qual O Pedido Do Cliente ?", "PEDCLIENTE",20);

                    break;
                }

                case R.id.lbl_cliente_001:
                {

                    if (((lista.size() == 0))) {
                        HelpCliente = "CLIENTE";
                        Intent i = new Intent(PedidoV10Activity.this, Help20Activity.class);
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
                    Intent i = new Intent(PedidoV10Activity.this,Help20Activity.class);
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

                    ClickGetDadosRetira("Desconto CLIENTE RETIRA", "DESCRET");

                    break;
                }


                case R.id.lbl_obsped_001:
                {

                    ClickGetDados("Observação Do Pedido","OBSPED",150);


                    break;
                }

                case R.id.lbl_obsnf_001:
                {

                    ClickGetDados("Observação Da NF","OBSNF",150);

                    break;
                }


                case R.id.lbl_fardos_previstos_001:
                {

                    ClickGetDados("FARDOS PREVISTOS","FDSPREVISTO",0);

                    break;
                }


                case R.id.lbl_qtd_entrega_001:
                {

                    if ("010#011#012#13".contains(pedido.getCabec().getTIPO())) {

                        ClickGetDados("QTD DE ENTREGAS", "QTDENTREGA", 0);

                    }

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

            pedido.recalculo();

            pedido.Validar();

            cabec_refresh();

            cabec_onClick();

            return true;
        }

    }

    private class defaultAdapter extends ArrayAdapter {

        private int escolha = 0;

        private List<String[]> lista;

        private String label;

        private boolean valido;

        private boolean isInicializacao = true;

        private defaultAdapter(Context context, int textViewResourceId, List<String[]> objects,String label, boolean valido) {

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

            String obj = lista.get(position)[1];

            LayoutInflater inflater =  getLayoutInflater();

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
        private List<Object> lsProdutos;
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

            this.lsProdutos = pObjects;

            this.lsObjetos = filtro();

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {


                if (obj instanceof PedidoDetMB_fast) {

                    qtd = qtd + 1;

                }

            }


            retorno = "Total de Produtos: " + String.valueOf(qtd);

            return retorno;
        }

        private void refresh(PedidoDetMb obj){

            int pos = -1;

            int x   = 0;

            for (Object ob : lsProdutos) {

                if (ob instanceof PedidoDetMB_fast) {

                    if (((PedidoDetMB_fast) ob).getITEM().equals(obj.getITEM())){

                        pos = x;

                    } else {

                        ((PedidoDetMB_fast) ob).setSTATUS(pedido.getLsDetalhe().get(Integer.parseInt(((PedidoDetMB_fast) ob).getITEM())-1).getSTATUS());
                        ((PedidoDetMB_fast) ob).set_isValid(pedido.getLsDetalhe().get(Integer.parseInt(((PedidoDetMB_fast) ob).getITEM())-1).get_isValid());

                    }
                }

                x++;

            }

            if (pos >= 0){

                this.lsProdutos.set(pos,obj.ToFast()) ;

            }

            lsObjetos = filtro();

            notifyDataSetChanged();

        }

        private void refreshwithfiltro(){

            lsObjetos = filtro();

            notifyDataSetChanged();

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

        private List<Object> filtro(){

            List<Object> retorno = new ArrayList<>();

            retorno.add("CABEC");

            if (viewPedido){

                Filtro_grupo = "000";

                Filtro_marca = "000";

                for(Object obj : lsProdutos){

                    if (obj instanceof NoData || obj instanceof NoDataProgress){

                        retorno.add(obj);

                        continue;

                    }


                    if (obj instanceof PedidoDetMB_fast) {

                        if (!((PedidoDetMB_fast) obj).getNRO().isEmpty()) {

                            retorno.add(obj);

                        }

                    }
                }

                return retorno;

            }

            if (Filtro_grupo.equals("000") && Filtro_marca.equals("000")){

                for(Object obj : lsProdutos){

                    if (obj instanceof NoData || obj instanceof NoDataProgress){

                        retorno.add(obj);

                        continue;

                    }


                    if (obj instanceof PedidoDetMB_fast){

                        if (SubFiltro.equals("01")) {

                            retorno.add(obj);
                        }
                        if (SubFiltro.equals("02") && !((PedidoDetMB_fast) obj).getNRO().isEmpty() && ((PedidoDetMB_fast) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }
                        if (SubFiltro.equals("03") && !((PedidoDetMB_fast) obj).getNRO().isEmpty() && !((PedidoDetMB_fast) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }
                        if (SubFiltro.equals("05") && ((PedidoDetMB_fast) obj).get_Mix()){

                            retorno.add(obj);
                        }

                    }

                }

                return retorno;

            }

            for(Object obj : lsProdutos){

                if (obj instanceof NoData || obj instanceof NoDataProgress){

                    retorno.add(obj);

                    continue;

                }


                if (obj instanceof PedidoDetMB_fast){

                    if ((!Filtro_grupo.equals("000") || (!Filtro_marca.equals("000")) )){

                        if ( ( Filtro_grupo.equals("000") || ((PedidoDetMB_fast) obj).get_CodGrupo().equals(Filtro_grupo) ) && ( Filtro_marca.equals("000") || ((PedidoDetMB_fast) obj).get_CodMarca().equals(Filtro_marca) ) ) {

                            if (SubFiltro.equals("01")){

                                retorno.add(obj);
                            }
                            if (SubFiltro.equals("02") && !((PedidoDetMB_fast) obj).getNRO().isEmpty() && ((PedidoDetMB_fast) obj).getSTATUS().equals("3")){

                                retorno.add(obj);

                            }
                            if (SubFiltro.equals("03") && !((PedidoDetMB_fast) obj).getNRO().isEmpty() && !((PedidoDetMB_fast) obj).getSTATUS().equals("3")){

                                retorno.add(obj);

                            }

                        }




                    } else {

                        if (SubFiltro.equals("01")) {

                            retorno.add(obj);
                        }
                        if (SubFiltro.equals("02") && ((PedidoDetMB_fast) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }
                        if (SubFiltro.equals("03") && !((PedidoDetMB_fast) obj).getSTATUS().equals("3")) {

                            retorno.add(obj);

                        }

                    }
                }

            }

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

            if (lsObjetos.get(position) instanceof PedidoDetMB_fast) {


                retorno = ITEM_VIEW_ITEM;

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

                            convertView = inflater.inflate(R.layout.pedidomb_det_itemv10_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA_PROGRESS:

                            convertView = inflater.inflate(R.layout.progress_data_row, null);


                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_ITEM: {

                        final PedidoDetMB_fast obj = (PedidoDetMB_fast) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.pedidomb_det_itemV10_row);

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                pedido.getEdicao().ImportFast(obj);

                                PosEdicao = obj.getITEM();

                                if ("1,2,3".contains(pedido.getCabec().get_Status())) pedido.validarItemEdicao();

                                detalhe_popula();

                                notifyDataSetChanged();


                            }
                        });

                        ImageView img_bola_007        = (ImageView) convertView.findViewById(R.id.img_bola_007);
                        ImageView img_dollar_07        = (ImageView) convertView.findViewById(R.id.img_dollar_07);
                        TextView lbl_produto_007      = (TextView) convertView.findViewById(R.id.lbl_produto_007);
                        TextView lbl_importado_007    = (TextView) convertView.findViewById(R.id.lbl_importado_007);
                        TextView lbl_descricao_007    = (TextView) convertView.findViewById(R.id.lbl_descricao_007);
                        TextView lbl_ultimo_preco_007 = (TextView) convertView.findViewById(R.id.lbl_ultimo_preco_007);
                        TextView lbl_preco_venda_007  = (TextView) convertView.findViewById(R.id.lbl_preco_venda_007);
                        TextView txt_meta_007         = (TextView) convertView.findViewById(R.id.txt_meta_007);
                        TextView txt_carteira_007     = (TextView) convertView.findViewById(R.id.txt_carteira_007);
                        TextView txt_realizado_007    = (TextView) convertView.findViewById(R.id.txt_realizado_007);
                        TextView txt_realizado_carteira_007 = (TextView) convertView.findViewById(R.id.txt_realizado_carteira_007);
                        TextView txt_falta_007              = (TextView) convertView.findViewById(R.id.txt_falta_007);

                        if (PosEdicao.equals(obj.getITEM())) {

                            view.setBackgroundResource(R.color.grey);

                            img_bola_007.setImageResource(R.drawable.ic_action_flag_amarela);

                        } else {

                            if (obj.getNRO().isEmpty()) {

                                view.setBackgroundResource(R.drawable.fundo);

                                img_bola_007.setVisibility(View.INVISIBLE);

                            } else {

                                img_bola_007.setVisibility(View.VISIBLE);

                                if (obj.getSTATUS().equals("3")) {

                                    img_bola_007.setImageResource(R.drawable.bola_verde);

                                } else {

                                    img_bola_007.setImageResource(R.drawable.bola_vermelha);
                                }

                                view.setBackgroundResource(R.color.md_yellow_400);
                            }
                        }

                        if ( obj.getORIGEM().trim().equals('1')){

                            img_dollar_07.setVisibility(View.VISIBLE);
                            lbl_importado_007.setVisibility(View.VISIBLE);

                        } else {

                            img_dollar_07.setVisibility(View.INVISIBLE);
                            lbl_importado_007.setVisibility(View.INVISIBLE);

                        }

                        lbl_produto_007.setText(obj.getPRODUTO().trim());
                        lbl_descricao_007.setText(obj.get_Produto().trim());
                        lbl_ultimo_preco_007.setText("Último Preço: "+format_02.format(obj.get_UltimoPreco()));
                        lbl_preco_venda_007.setText( "Preço Tabela: "+format_02.format(obj.getPRECOFORMACAO()));

                        txt_meta_007.setText(format_02.format(obj.get_Meta()));
                        txt_carteira_007.setText(format_02.format(obj.get_Carteira()));
                        txt_realizado_007.setText(format_02.format(obj.get_Realizado()));
                        txt_realizado_carteira_007.setText(format_02.format(obj.get_Carteira()+obj.get_Realizado()));
                        txt_falta_007.setText(format_02.format(obj.get_Meta() - ( obj.get_Carteira()+obj.get_Realizado())));


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

    private class AdapterMetas extends BaseAdapter {

        DecimalFormat format_02     = new DecimalFormat(",##0.00");


        final int ITEM_VIEW_CABEC            = 0;
        final int ITEM_VIEW_CATEGORIA        = 1;
        final int ITEM_VIEW_NO_DATA          = 2;
        final int ITEM_VIEW_NO_DATA_PROGRESS = 3;
        final int ITEM_VIEW_COUNT            = 4;

        List<Object> lsObjetos;

        Context context;


        private LayoutInflater inflater;

        public AdapterMetas(Context context, List<Object> pObjects) {

            this.lsObjetos = pObjects;

            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {


                if (obj instanceof MetaCategoria) {

                    qtd = qtd + 1;

                }

            }


            retorno = "Total de Categorias: " + String.valueOf(qtd);

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

            if (lsObjetos.get(position) instanceof MetaCategoria) {


                retorno = ITEM_VIEW_CATEGORIA;

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


                        case ITEM_VIEW_CATEGORIA:

                            convertView = inflater.inflate(R.layout.meta_categoria_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                        case ITEM_VIEW_NO_DATA_PROGRESS:

                            convertView = inflater.inflate(R.layout.progress_data_row, null);

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_CATEGORIA: {

                        final MetaCategoria obj = (MetaCategoria) lsObjetos.get(pos);

                        TextView txt_categoria_025 = (TextView) convertView.findViewById(R.id.txt_categoria_025);

                        TextView txt_meta_025 = (TextView) convertView.findViewById(R.id.txt_meta_025);
                        TextView txt_carteira_025 = (TextView) convertView.findViewById(R.id.txt_carteira_025);
                        TextView txt_realizado_025 = (TextView) convertView.findViewById(R.id.txt_realizado_025);
                        TextView txt_cart_real_025 = (TextView) convertView.findViewById(R.id.txt_cart_real_025);
                        TextView txt_atingido_025 = (TextView) convertView.findViewById(R.id.txt_atingido_025);

                        txt_categoria_025.setText(obj.getCATEGORIA()+"-"+obj.getDESCCATEGORIA());

                        txt_meta_025.setText(format_02.format(obj.getOBJETIVO()));

                        txt_carteira_025.setText(format_02.format(obj.getCARTEIRA()));

                        txt_realizado_025.setText(format_02.format(obj.getREAL()));

                        txt_cart_real_025.setText(format_02.format(obj.getREAL()+obj.getCARTEIRA()));

                        if (obj.getOBJETIVO().compareTo(0f) == 0){

                            txt_atingido_025.setText("");

                        } else {


                            txt_atingido_025.setText(format_02.format(((obj.getREAL()+obj.getCARTEIRA())/obj.getOBJETIVO()) * 100)+"%");


                        }

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

    private class ClickEmail implements View.OnClickListener {

        private Context context = null;

        public ClickEmail(Context context) {

            this.context = context;

        }

        @Override
        public void onClick(View v) {

            final Dialog dialog = new Dialog(context);

            dialog.setContentView(R.layout.dlemail);

            dialog.setTitle("ENVIAR PEDIDO POR E-MAIL");

            final TextView titulo   = (TextView) dialog.findViewById(R.id.titulo_121);

            final CheckBox cb_envia_email_121 = (CheckBox) dialog.findViewById(R.id.cb_enviar_email_121);

            final EditText endereco = (EditText) dialog.findViewById(R.id.email_121);

            titulo.setText("E-MAIL Será Enviado Na Sincronização.");

            if (pedido.getCabec().getCCOPIAPEDIDO().equals("S")) {

                cb_envia_email_121.setChecked(true);

            }
            else {

                cb_envia_email_121.setChecked(false);

            }
            if(pedido.getCabec().getCEMAILCOPIAPEDIDO().trim().isEmpty()){

                pedido.getCabec().setCEMAILCOPIAPEDIDO(pedido.getCliente().getEMAILTROCA());

            }

            endereco.setText(pedido.getCabec().getCEMAILCOPIAPEDIDO());

            final Button confirmar = (Button) dialog.findViewById(R.id.bt_confirma_121);

            final Button cancelar = (Button) dialog.findViewById(R.id.bt_cancela_121);

            cancelar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            confirmar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    if (endereco.getText().toString().trim().isEmpty()) {

                        toast("Digite Um E-Mail Por Favor !");

                        return;

                    }

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            View view = dialog.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }

                            dialog.dismiss();

                        }

                    }

                    if (cb_envia_email_121.isChecked()) {

                        pedido.getCabec().setCCOPIAPEDIDO("S");

                    } else {

                        pedido.getCabec().setCCOPIAPEDIDO("N");

                    }

                    pedido.getCabec().setCEMAILCOPIAPEDIDO(endereco.getText().toString());

                    if (pedido.getCabec().getCCOPIAPEDIDO().equals("S")){

                        im_email_check_001.setVisibility(View.VISIBLE);

                        txt_email_001.setText(endereco.getText());

                    } else {

                        im_email_check_001.setVisibility(View.INVISIBLE);

                        txt_email_001.setText("");

                    }

                }

            });

            dialog.show();

        }

    }

    private class AtualizaPedidoThread extends Thread {

        private Handler mHandler;

        private Bundle params = new Bundle();

        private int    opcaoRecalculo;

        public AtualizaPedidoThread(Handler handler, int opcaoRecalculo) {

            super();

            mHandler = handler;

            this.opcaoRecalculo = opcaoRecalculo;

        }

        @Override

        public void run() {

            try {

                params.putString("CERRO"   , "---");

                params.putString("CMSGERRO", "");

                sendmsg(params);

                ArrayList<PedidoDetMB_fast> old = new ArrayList<PedidoDetMB_fast>();

                old = pedido.ItensUsados();

                pedido.loadItensNewOrder();

                pedido.AdicionaProdutoAnterior(old,opcaoRecalculo);

                pedido.recalculo();

                pedido.Validar();

                lista = new ArrayList<Object>();

                lista.add("CABEC");

                lista.addAll(pedido.getLsDetalhe());

                params.putString("CERRO"   , "FECT");

                params.putString("CMSGERRO", "");

                sendmsg(params);

            } catch (Exception e){

                params.putString("CERRO"   , "FECT");

                params.putString("CMSGERRO", e.getMessage());

                sendmsg(params);

            }

        }


        public void sendmsg(Bundle value) {

            if (value != null) {
                Message msgObj = mHandler.obtainMessage();
                msgObj.setData(value);
                mHandler.sendMessage(msgObj);
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

                if (operacao.equals("NOVO")) {

                    pedido.loadItensNewOrder();

                } else {

                    pedido.load(nropedido,false);

                }

                params.putString("erro"   , "PRONTO");

                params.putString("msgerro", "");



            } catch (Exception e){

                params.putString("erro"   , "ERRO");

                params.putString("msgerro", e.getMessage());


            }


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

    private Handler mHandlerAtualizaPedido = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                toast("NAO CONTEM AS CHAVES..");

                return;

            }

            if (msg.getData().getString("CERRO").equals("---")) {

                dialog = ProgressDialog.show(PedidoV10Activity.this, "RECALCULANDO PEDIDO", "Processando !!", false, true);
                dialog.setCancelable(false);
                dialog.show();
            }


            if ((msg.getData().getString("CERRO").equals("FECT"))) {

                if ((dialog != null)) {

                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }
                }

                adapter = new Adapter(getBaseContext(), lista);

                lv.setAdapter(adapter);

                PosEdicao = "";

                adapter.notifyDataSetChanged();

                pedido.recalculo();

                pedido.Validar();

                txt_status_001.setText(pedido.getCabec().get_Status());
                txt_totalpedido_001.setText(format_02.format(pedido.getCabec().getTOTALPEDIDO()));
                txt_totaldesc_001.setText(format_02.format(pedido.getCabec().getTOTALDESCONTO()));
                txt_totaldescverba_001.setText(format_02.format(pedido.getCabec().getTOTALVERBA()));
                txt_fardos_previstos_001.setText(format_02.format(pedido.getCabec().getFDSPREVISTO()));
                txt_fardos_realizados_001.setText(format_02.format(pedido.getCabec().getFDSREAIS()));

            }


            if ((msg.getData().getString("CERRO").equals("FECC"))) {

                if ((dialog != null)) {

                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }
                }

                try {

                    adapter = new Adapter(getBaseContext(), lista);

                    lv.setAdapter(adapter);

                    PosEdicao = "";

                    adapter.notifyDataSetChanged();

                    //lv.setOnItemClickListener(new ListClickHandler());

                    txt_status_001.setText(pedido.getCabec().get_Status());
                    txt_totalpedido_001.setText(format_02.format(pedido.getCabec().getTOTALPEDIDO()));
                    txt_totaldesc_001.setText(format_02.format(pedido.getCabec().getTOTALDESCONTO()));
                    txt_totaldescverba_001.setText(format_02.format(pedido.getCabec().getTOTALVERBA()));
                    txt_fardos_previstos_001.setText(format_02.format(pedido.getCabec().getFDSPREVISTO()));
                    txt_fardos_realizados_001.setText(format_02.format(pedido.getCabec().getFDSREAIS()));


                } catch (Exception e) {

                    toast(e.getMessage());

                }


            }


        }

    };

    private Handler mHandlerLoad = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            if (msg.getData().getString("erro").equals("PRONTO")) {


                try {


                    //detalhe

                    if (operacao.equals("NOVO")) {

                        lista = new ArrayList<>();

                        produto_filtro();

                        lista.add("CABEC");

                        lista.addAll(pedido.getLsDetalhe());

                        adapter = new Adapter(getBaseContext(), lista);

                        lv.setAdapter(adapter);

                        adapter.notifyDataSetChanged();

                        //lv.setOnItemClickListener(new ListClickHandler());

                    } else {

                        lista = new ArrayList<>();

                        produto_filtro();

                        lista.add("CABEC");

                        lista.addAll(pedido.getLsDetalhe());

                        adapter = new Adapter(getBaseContext(), lista);

                        lv.setAdapter(adapter);

                        adapter.notifyDataSetChanged();

                        //lv.setOnItemClickListener(new ListClickHandler());

                    }

                } catch (Exception e) {
                    toast(e.getMessage());
                }

            }


            if (msg.getData().getString("erro").equals("ERRO")) {

                try {

                    lista = new ArrayList<>();

                    produto_filtro();

                    lista.add("CABEC");

                    lista.add(new NoData(msg.getData().getString("msgerro")));

                    adapter = new Adapter(getBaseContext(), lista);

                    lv.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                    //lv.setOnItemClickListener(new ListClickHandler());

                } catch (Exception e) {

                    toast(e.getMessage());
                }

            }


            detalhe_popula();

            detalhe_onClick();

        }

    };



    private void adicionaLeftNavigation(){

        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savebundle)
                .withSelectedItem(0)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {


                        mPositionClicked = i;

                        String opcao = (String) iDrawerItem.getTag();

                        navigationDrawerLeft.getAdapter().notifyDataSetChanged();

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

                        if (opcao.equals("pedido")) {

                            Intent intent = new Intent(getApplicationContext(), PedidosProtheusGeralActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODCLIENTE",pedido.getCabec().getCODIGOFAT());
                            params.putString("LOJCLIENTE",pedido.getCabec().getLOJAFAT());
                            intent.putExtras(params);
                            startActivity(intent);

                        }


                        if (opcao.equals("nf")) {

                            Intent intent = new Intent(getApplicationContext(),ConsultaNFTotvsActivity.class);
                            Bundle params = new Bundle();
                            params.putString("CODCLIENTE", pedido.getCabec().getCODIGOFAT());
                            params.putString("LOJCLIENTE", pedido.getCabec().getLOJAFAT());
                            intent.putExtras(params);
                            startActivity(intent);

                        }



                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {

                        Toast.makeText(PedidoV10Activity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();

                        return false;
                    }
                })
                .build();



        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Dados Cadastrais").withTextColor(getResources().getColor(R.color.md_red_200)).withTag("menucadastral"));
        navigationDrawerLeft.addItem(new SecondaryDrawerItem().withName("Cadastro Do Cliente").withIcon(getResources().getDrawable(R.drawable.cliente_30)).withTag("cliente"));
        navigationDrawerLeft.addItem(new SecondaryDrawerItem().withName("Contrato Do Cliente").withIcon(getResources().getDrawable(R.drawable.contrato_30)).withTag("contrato"));
        navigationDrawerLeft.addItem(new SecondaryDrawerItem().withName("Financeiro").withIcon(getResources().getDrawable(R.drawable.financeiro_30)).withTag("financeiro"));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Dados Do Protheus").withTextColor(getResources().getColor(R.color.md_red_200)).withTag("menuprotheus"));
        navigationDrawerLeft.addItem(new SecondaryDrawerItem().withName("Pedidos").withIcon(getResources().getDrawable(R.drawable.pedido_30)).withTag("pedido"));
        navigationDrawerLeft.addItem(new SecondaryDrawerItem().withName("Notas Fiscais").withIcon(getResources().getDrawable(R.drawable.financeiro_30)).withTag("nf"));

    }

    private void adicionaHeaderNavigation(){

        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savebundle)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(R.drawable.sav_logo_pedido)
                .build();

    }


}
