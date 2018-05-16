package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.MotivoDAO;
import br.com.brotolegal.savdatabase.entities.AGENDADATA;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.Motivo;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.eventbus.NotificationPedido;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeAgendamentoAtrasado;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeTabelaPreco;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PLAY_SERVICES_RESOLUTION_REQUEST;

public class PedidosAgendaActivity extends AppCompatActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final long ONE_MIN = 1000 * 60;
    private static final long TWO_MIN = ONE_MIN * 2;
    private static final long FIVE_MIN = ONE_MIN * 5;
    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private static final float MIN_ACCURACY = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;

    private LocationRequest mLocationRequest;

    private Location mBestReading;

    private GoogleApiClient mGoogleApiClient;

    Boolean OrdemRefresh = true;

    Toolbar toolbar;

    ListView lv;

    Spinner spSituacoes;

    Spinner spAgendas;

    Spinner spOrdem;

    EditText edPesquisa;

    List<Object> lsLista;

    Map<String,String> mpCidades      = new TreeMap<String, String >();

    Map<String,String> mpAgenda       = new TreeMap<String, String >();

    List<String[]> lsSituacoes        = new ArrayList<>();

    List<String[]> lsAgendas          = new ArrayList<>();

    List<String[]> lsOrdens           = new ArrayList<>();

    List<Motivo>   lsMotivos          = new ArrayList<>();

    defaultAdapter situacoesadapter;

    defaultAdapter agendasadapter;

    defaultAdapter ordensadapter;

    defaultAdapter motivoadapter;

    Adapter adapter;

    Cliente_fast  POS = null;

    private String rotina    = "";

    private String nropedido = "";

    private String CODIGO    = "";

    private String LOJA      = "";

    private String DATAAGENDA = App.getHoje();

    private String LOG       = "AGENDA";

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_agenda);


        toolbar = (Toolbar) findViewById(R.id.tb_pedidos_500);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Pedidos Planejamento");
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.menu_pedido_agenda);

        try {

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                rotina = params.getString("ROTINA","");

            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }


        try {

            App.tabelaValida();

        } catch (ExceptionValidadeTabelaPreco e) {

            Toast(e.getMessage());

            finish();
        }
        catch (ExceptionValidadeAgendamentoAtrasado e) {

            Intent intent = new Intent(PedidosAgendaActivity.this,AgendamentosAtrasadosActivity.class);

            startActivityForResult(intent, HelpInformation.AgendamentosIrregulares);

        } catch (Exception e) {

            Toast(e.getMessage());

            finish();

        }

        try {

            MotivoDAO motivodao = new MotivoDAO();

            motivodao.open();

            lsMotivos = motivodao.getAll();

            motivodao.close();

        } catch (Exception e){

            Toast.makeText(this, "Falha Ao Carregar Os Motivos", Toast.LENGTH_SHORT).show();

            finish();

        }

        spSituacoes =   (Spinner) findViewById(R.id.sp_cidade_334);

        spAgendas   =   (Spinner) findViewById(R.id.sp_rede_334);

        spOrdem     =   (Spinner) findViewById(R.id.sp_ordem_334);

        edPesquisa = (EditText) findViewById(R.id.edpesquisa_334);

        edPesquisa.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        lv = (ListView) findViewById(R.id.lvClientesSales_500);

        lsOrdens   = new ArrayList<>();

        lsOrdens.add(new String[]{"01", "Razão Social"});
        lsOrdens.add(new String[]{"02", "Código"});
        lsOrdens.add(new String[]{"03", "CNPJ"});
        lsOrdens.add(new String[]{"04", "Nome Fantasia"});


        ordensadapter = new defaultAdapter(PedidosAgendaActivity.this, R.layout.choice_default_row, lsOrdens,"");

        spOrdem.setAdapter(ordensadapter);

        spOrdem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ordensadapter.setEscolha(position);

                edPesquisa.setText("");

                switch (position) {
                    case 0:  //texto maiusculo

                        edPesquisa.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                        break;

                    case 1:  //numerico

                        edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                        break;

                    case 2:  //numerico

                        edPesquisa.setRawInputType(InputType.TYPE_CLASS_PHONE);

                        break;

                }

                if (!(lsLista == null)){

                    LoadClientes();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spOrdem.setSelection(0);

        try {

            lsAgendas = new ArrayList<>();

            String hoje = App.getHoje();

            int index = -1;

            int proximo = -1;

            int cnt = 0;

            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

            agendamentoDAO.open();

            for (AGENDADATA ag : agendamentoDAO.getAllByData()) {

                lsAgendas.add(new String[]{ag.getData(), App.aaaammddToddmmaa(ag.getData()) + " " + "Total de Clientes: " + String.valueOf(ag.getContador())});

                if (hoje.equals(ag.getData())) {

                    index = cnt;
                }

                if (hoje.compareTo(ag.getData()) < 0) {

                    if (proximo == -1) proximo = cnt;
                }

                cnt++;

            }

            agendamentoDAO.close();

            agendasadapter = new defaultAdapter(PedidosAgendaActivity.this, R.layout.choice_default_row, lsAgendas, "Data:");

            spAgendas.setAdapter(agendasadapter);

            agendasadapter.setIsInicializacao(true);

            spAgendas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (!agendasadapter.isInicializacao()) {

                        OrdemRefresh = true;

                    }

                    edPesquisa.setText("");

                    agendasadapter.setEscolha(position);

                    Object lixo = spAgendas.getSelectedItem();

                    DATAAGENDA = (((String[]) lixo)[0]);

                    LoadClientes();

                    OrdemRefresh = false;

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });


            if (index == -1) {

                if (proximo != -1) {

                    index = proximo;

                } else {

                    index = 0;

                }

            }

            spAgendas.setSelection(index);


            LoadClientes();


            if (App.user.getGPS().trim().equals("1")) {

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
                                        status.startResolutionForResult(PedidosAgendaActivity.this, HelpInformation.REQUEST_CHECK_SETTINGS);
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

        }catch(Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedido_agenda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.ac_agenda_cancelar:

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {

        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    public void onResume() {

        try {

            if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);


            if (POS != null) {

                ClienteDAO dao = new ClienteDAO();

                dao.open();

                Cliente_fast cliente = dao.seek_fast(POS.getCODIGO(),POS.getLOJA(),DATAAGENDA);

                dao.close();

                if (cliente != null) {

                    adapter.setCliente(cliente);


                }
            }
        } catch (Exception e) {

            Toast.makeText(PedidosAgendaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


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

                        adapter.refresh2();

                    }

                } catch (Exception e) {

                    Log.i("SAV", e.getMessage());

                }

            }
        });


        POS = null;

        OrdemRefresh = false;

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        super.onResume();
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

        lsLista      = new ArrayList<Object>();

        lsSituacoes  = new ArrayList<>();

        lsAgendas    = new ArrayList<>();

        lsMotivos    = new ArrayList<>();

        super.finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Boolean refresh = false;

        if (resultCode == 1 && requestCode == HelpInformation.AgendamentosIrregulares) {

            //Agendamentos
            {
                try {

                    Boolean erroAgendamento = false;

                    ClienteDAO dao = new ClienteDAO();

                    dao.open();

                    if (dao.getAll_fast("AGENDAMENTO.data desc, AGENDAMENTO.hora ", "OPEN").size() > 0)

                        erroAgendamento = true;

                    dao.close();

                    if (erroAgendamento) {

                        finish();

                    }
                }catch (Exception e){

                    Toast(e.getMessage());

                    finish();

                }
            }

            LoadClientes();
        }


    }

    public void Toast(String msg) {

        Toast.makeText(PedidosAgendaActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    private void LoadClientes(){

        String ordem = "cliente.razao";

        try {

            lsLista    = new ArrayList<Object>();


            lsLista.add("Clientes");


            lsSituacoes  = new ArrayList<>();

            Object lixo = spOrdem.getSelectedItem();

            if (lixo == null){

                ordem = "cliente.razao";

            } else {

                if (((String[]) lixo)[0].equals("01")) {

                    ordem = "cliente.razao";

                }
                if (((String[]) lixo)[0].equals("02")) {

                    ordem = "cliente.codigo";

                }
                if (((String[]) lixo)[0].equals("03")) {

                    ordem = "cliente.cnpj";

                }
                if (((String[]) lixo)[0].equals("04")) {

                    ordem = "cliente.fantasia";

                }

            }
            ClienteDAO dao = new ClienteDAO();

            dao.open();

            lsLista.addAll(dao.getAll_fast("agendamento.data,agendamento.hora",DATAAGENDA));

            dao.close();

            mpCidades.put("","TODAS");

            mpAgenda.put("", "TODAS");

            if (lsLista.size() == 1) {

                lsLista.add(new NoData("Nenhum Cliente Encontrado !!"));

            } else {

                for(Object obj : lsLista){

                    if (obj instanceof Cliente_fast){

                        try {

                            mpCidades.put(((Cliente_fast) obj).getCODCIDADE(), ((Cliente_fast) obj).getCIDADE());

                        } catch (Exception e){

                            //

                        }

                    }

                }


                for(Map.Entry<String, String> values : mpCidades.entrySet()){


                    lsSituacoes.add(new String[] {values.getKey(),values.getValue()});

                }


            }

            situacoesadapter = new defaultAdapter(PedidosAgendaActivity.this, R.layout.choice_default_row, lsSituacoes,"Cidade");

            spSituacoes.setAdapter(situacoesadapter);

            spSituacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    OrdemRefresh = true;

                    edPesquisa.setText("");

                    situacoesadapter.setEscolha(position);

                    Object lixo = spSituacoes.getSelectedItem();

                    adapter.setCidade(((String[]) lixo)[0]);

                    adapter.refresh();

                    OrdemRefresh = false;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

            spSituacoes.setSelection(0);





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

                            spSituacoes.setSelection(0);

                            adapter.refresh2();

                        }

                    } catch (Exception e) {

                        Log.i("SAV", e.getMessage());

                    }

                }
            });


            adapter = new Adapter(PedidosAgendaActivity.this, lsLista);

            adapter.setOrdem(((String[]) lixo)[0]);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            OrdemRefresh = false;



        } catch (Exception e) {

            Toast.makeText(PedidosAgendaActivity.this, "Erro Na Carga: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    public void refresh(){

        LoadClientes();

    }

    private List<String[]> loadMotivos(String tipo){

        List<String[]> retorno = new ArrayList<>();

        retorno.clear();

        retorno.add(new String[] {"000","Escolha Um Motivo"});

        for(Motivo mot : lsMotivos){

            if (mot.getTIPO().trim().equals(tipo)){

                retorno.add(new String[] {mot.getCODIGO(),mot.getDESCRICAO()});

            }

        }

        return retorno;

    }

    private String[] getMotivos(String tipo,String codigo){

        String[] retorno = new String[] {"",""};

        if (tipo.trim().isEmpty()){


            return retorno;


        }

        for(Motivo mot : lsMotivos){

            if (mot.getTIPO().trim().equals(tipo.trim()) && mot.getCODIGO().trim().equals(codigo.trim())){

                retorno = new String[] {mot.getCODIGO(),mot.getDESCRICAO()};

                break;

            }

        }

        return retorno;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onReceiverNotification(NotificationPedido notificationPedido) {

        if (notificationPedido.getCLIENTE().isEmpty()){

            return;

        };

        try {

            ClienteDAO dao = new ClienteDAO();

            dao.open();

            Cliente_fast cliente = dao.seek_fast(notificationPedido.getCLIENTE(),notificationPedido.getLOJA(),DATAAGENDA);

            dao.close();

            if (cliente != null) {

                adapter.setCliente(cliente);


            }

        } catch (Exception e) {

            Toast.makeText(PedidosAgendaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, PedidosAgendaActivity.this);
                    }

                }, ONE_MIN, TimeUnit.MILLISECONDS);
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

    private void toast(String mensagem) {

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();


    }


    private Handler mHandlerTrasmissao = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if (!msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO")) {

                    toast("NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(PedidosAgendaActivity.this, msg.getData().getString("CMSGERRO"), "Acessando Servidores.Aguarde !!", false, true);
                    dialog.setCancelable(false);
                    dialog.show();

                    processado = true;
                }


                if (msg.getData().getString("CERRO").equals("MMM")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.setTitle(msg.getData().getString("CMSGERRO"));

                        }

                    }

                    processado = true;

                }


                if ((msg.getData().getString("CERRO").equals("000"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    if (!msg.getData().getString("CMSGERRO").isEmpty()) {

                        toast(msg.getData().getString("CMSGERRO"));

                    }


                    processado = true;
                }

                if ((msg.getData().getString("CERRO").equals("FEC"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    if (!msg.getData().getString("CMSGERRO").isEmpty()) {

                        toast(msg.getData().getString("CMSGERRO"));

                    }


                    processado = true;
                }


                if (!processado) {


                    toast("Erro:" + msg.getData().getString("CERRO") + " " + msg.getData().getString("CMSGERRO"));

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }
                    }


                }

            } catch (Exception E) {

                if ((dialog != null)) {

                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }
                }

                toast("Erro Handler: " + E.getMessage());

            }
        }


    };

    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");

        private List<Object> lsOriginal;

        private List<Object> lsObjetos;

        Context context;

        private String _Cidade = "";
        private String _Data   = "";
        private String _Ordem  = "";

        final int ITEM_VIEW_CABEC   = 0;
        final int ITEM_VIEW_DETALHE = 1;
        final int ITEM_VIEW_NO_DATA = 2;
        final int ITEM_VIEW_COUNT   = 3;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsOriginal = pObjects;

            this.lsObjetos  = filtro();

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void setCidade(String filtro){

            _Cidade = filtro;

        }

        public void setData(String filtro){

            _Data = filtro;

        }

        public void setOrdem(String filtro){

            _Ordem = filtro;

        }

        public void refresh(){


            this.lsObjetos  = filtro();

            notifyDataSetChanged();

        }

        public void refresh2(){

            this.lsObjetos  = filtro2();

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

            if (_Cidade.equals("") && _Data.equals("")) {

                return lsOriginal;

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsOriginal.size(); x++) {

                    if (lsOriginal.get(x) instanceof Cliente_fast) {

                        if (    (_Cidade.equals("") || (_Cidade.equals(((Cliente_fast) lsOriginal.get(x)).getCODCIDADE()))) ){

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


        private List<Object> filtro2() {

            List<Object> result = null;

            if (edPesquisa.getText().toString().trim().isEmpty()) {

                return lsOriginal;

            } else {

                result = new ArrayList<>();

                for (int x = 0; x < lsOriginal.size(); x++) {

                    if (lsOriginal.get(x) instanceof Cliente_fast) {


                        if (_Ordem.equals("01")){//razao

                            if (((Cliente_fast) lsOriginal.get(x)).getRAZAO().toUpperCase().contains(edPesquisa.getText().toString().trim())){

                                result.add(lsOriginal.get(x));

                            }


                        }
                        if (_Ordem.equals("02")){//codigo

                            if (((Cliente_fast) lsOriginal.get(x)).getCODIGO().contains(edPesquisa.getText().toString().trim())){

                                result.add(lsOriginal.get(x));

                            }

                        }
                        if (_Ordem.equals("03")){//cnpj

                            if (((Cliente_fast) lsOriginal.get(x)).getCNPJ().contains(edPesquisa.getText().toString().trim())){

                                result.add(lsOriginal.get(x));

                            }

                        }

                        if (_Ordem.equals("04")){//fantasia

                            if (((Cliente_fast) lsOriginal.get(x)).getFANTASIA().contains(edPesquisa.getText().toString().trim())){

                                result.add(lsOriginal.get(x));

                            }

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

                        ImageButton bt_cadastro   = (ImageButton) convertView.findViewById(R.id.bt_cadastro_400);

                        ImageButton bt_contrato   = (ImageButton) convertView.findViewById(R.id.bt_contrato_400);

                        ImageButton bt_financeiro = (ImageButton) convertView.findViewById(R.id.bt_financeiro_400);

                        ImageButton bt_pedidos    = (ImageButton) convertView.findViewById(R.id.bt_pedidos_400);

                        ImageButton bt_nf         = (ImageButton) convertView.findViewById(R.id.bt_nf_400);

                        ImageButton bt_agenda     = (ImageButton) convertView.findViewById(R.id.bt_agenda_400);

                        ImageButton bt_pedido     = (ImageButton) convertView.findViewById(R.id.bt_alteracao_400);

                        ImageButton bt_salles_lista = (ImageButton) convertView.findViewById(R.id.bt_salles_lista);

                        TextView  lbl_mensagem_400  = (TextView) convertView.findViewById(R.id.lbl_mensagem_400);

                        TextView lbl_hora_400       = (TextView) convertView.findViewById(R.id.lbl_hora_400);

                        if (obj.get_DATA().trim().isEmpty()){

                            lbl_hora_400.setText("");


                        } else {

                            lbl_hora_400.setText(App.aaaammddToddmmaa(obj.get_DATA())+"\nàs\n  "+obj.get_HORA());

                        }


                        if (App.TotvsSIMNAO(obj.getCLIENTEENTREGA()).equals("SIM")){

                            lbl_mensagem_400.setText("CLIENTE ENTREGA");

                        } else {

                            lbl_mensagem_400.setTextSize(12f);
                            lbl_mensagem_400.setGravity(Gravity.LEFT+Gravity.CENTER_VERTICAL+Gravity.CENTER_HORIZONTAL);
                            lbl_mensagem_400.setText("");

                            if (obj.get_IDAGE().trim().isEmpty()) {

                                lbl_mensagem_400.setText("");

                            } else {

                                if (obj.get_MOBILE().trim().isEmpty() && obj.get_MOTIVONVENDA().trim().isEmpty() && obj.get_MOTIVONVISITA().trim().isEmpty()){

                                    lbl_mensagem_400.setText("Tipo: "+App.TipoAgendamento(obj.get_TIPOAGE())   +" Situac.: "+App.SituacaoAgendamento(obj.get_SITAGE())+"\n");

                                } else {

                                    if (!obj.get_MOBILE().trim().isEmpty()) {

                                        lbl_mensagem_400.setText("Tipo: "+App.TipoAgendamento(obj.get_TIPOAGE())+" Situac.: "+App.SituacaoAgendamento(obj.get_SITAGE())+"\n\nPEDIDO: " + obj.get_MOBILE());

                                    } else {


                                        if (!obj.get_MOTIVONVENDA().trim().isEmpty()) {

                                            lbl_mensagem_400.setText("Tipo: "+App.TipoAgendamento(obj.get_TIPOAGE())+" Situac.: "+App.SituacaoAgendamento(obj.get_SITAGE())+"\n\nNão Venda: " + getMotivos("NAOVENDA", obj.get_MOTIVONVENDA())[1]);

                                        }


                                        if (!obj.get_MOTIVONVISITA().trim().isEmpty()) {

                                            lbl_mensagem_400.setText("Tipo: "+App.TipoAgendamento(obj.get_TIPOAGE())+" Situac.: "+App.SituacaoAgendamento(obj.get_SITAGE())+"\n\nNão Visita: " + getMotivos("NAOVISITA", obj.get_MOTIVONVISITA())[1]);

                                        }

                                    }
                                }


                            }
                        }

                        TextView txt_flag_red = (TextView) convertView.findViewById(R.id.txt_flag_red);

                        TextView txt_flag_yellow = (TextView) convertView.findViewById(R.id.txt_flag_yellow);

                        TextView txt_codigo_400 = (TextView) convertView.findViewById(R.id.txt_codigo_400);

                        txt_codigo_400.setText("Código Protheus: " + obj.getCODIGO()+ (obj.get_DIVIDAEMATRASO().compareTo(0f) == 0 ? "" : " DÍVIDA EM ATRASO: "+ format_02.format(obj.get_DIVIDAEMATRASO())));

                        TextView txt_situacao_400 = (TextView) convertView.findViewById(R.id.txt_situacao_400);

                        txt_situacao_400.setText("Situação Do Cliente: "+obj.getClienteSituacao());

                        if (!obj.getSITUACAO().trim().equals("ATIVO")){

                            txt_situacao_400.setTextColor(getResources().getColor(R.color.red));

                        } else {

                            txt_situacao_400.setTextColor(getResources().getColor(R.color.green));

                        }


                        TextView txt_cliente_400 = (TextView) convertView.findViewById(R.id.txt_cliente_400);

                        txt_cliente_400.setText("Razão Social: "+obj.getRAZAO());

                        TextView txt_cnpj_400 = (TextView) convertView.findViewById(R.id.txt_cnpj_400);

                        TextView txt_fantasia_400 = (TextView) convertView.findViewById(R.id.txt_fantasia_400);

                        txt_fantasia_400.setText("Fantasia: "+obj.getFANTASIA());

                        txt_cnpj_400.setText("CNPJ/CPF: "+obj.getCNPJ());

                        TextView txt_IE_400   = (TextView) convertView.findViewById(R.id.txt_ie_400);

                        txt_IE_400.setText("I.E.: "+obj.getIE());

                        TextView txt_cidade_400   = (TextView) convertView.findViewById(R.id.txt_cidade_400);

                        TextView txt_end_400   = (TextView) convertView.findViewById(R.id.txt_end_400);

                        txt_end_400.setText("End: "+obj.getENDERECO());

                        TextView txt_bair_400   = (TextView) convertView.findViewById(R.id.txt_bair_400);

                        txt_bair_400.setText("Bairro: "+obj.getBAIRRO());

                        txt_cidade_400.setText("Cidade: "+obj.getCIDADE());

                        TextView txt_telefone_400   = (TextView) convertView.findViewById(R.id.txt_telefone_400);

                        txt_telefone_400.setText("Tel.: ("+obj.getDDD()+")"+obj.getTELEFONE());

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

                        bt_pedidos.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {

                                                              Intent intent = new Intent(context,PedidosProtheusGeralActivity.class);
                                                              Bundle params = new Bundle();
                                                              params.putString("CODCLIENTE", obj.getCODIGO());
                                                              params.putString("LOJCLIENTE", obj.getLOJA());
                                                              intent.putExtras(params);
                                                              startActivity(intent);

                                                          }
                                                      }
                        );

                        bt_nf.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {

                                                         Intent intent = new Intent(context,ConsultaNFTotvsActivity.class);
                                                         Bundle params = new Bundle();
                                                         params.putString("CODCLIENTE", obj.getCODIGO());
                                                         params.putString("LOJCLIENTE", obj.getLOJA());
                                                         intent.putExtras(params);
                                                         startActivity(intent);

                                                     }
                                                 }
                        );


                        bt_agenda.setOnClickListener(new ClickAgenda(context,obj));



                        if (App.TotvsSIMNAO(obj.getCLIENTEENTREGA()).equals("SIM")){

                            bt_pedido.setVisibility(View.INVISIBLE);
                            bt_salles_lista.setVisibility(View.INVISIBLE);

                        } else {

                            bt_pedido.setVisibility(View.VISIBLE);
                            bt_salles_lista.setVisibility(View.VISIBLE);


                            bt_pedido.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {

                                        App.tabelaValida();

                                    } catch (ExceptionValidadeTabelaPreco e) {

                                        toast(e.getMessage());

                                        return;
                                    }
                                    catch (ExceptionValidadeAgendamentoAtrasado e) {

                                        Intent intent = new Intent(PedidosAgendaActivity.this,AgendamentosAtrasadosActivity.class);

                                        startActivity(intent);

                                        return;

                                    } catch (Exception e) {

                                        toast(e.getMessage());

                                        return;

                                    }

                                    POS = obj;

                                    Intent intent = new Intent(context, PedidoV10Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Bundle params = new Bundle();
                                    params.putString("CODIGO", obj.getCODIGO());
                                    params.putString("LOJA", obj.getLOJA());
                                    params.putString("OPERACAO", "NOVO");
                                    params.putString("NROPEDIDO", "");
                                    params.putString("IDAGE",obj.get_IDAGE());
                                    params.putString("NEGOCIACAO",(rotina.equals("CONSULTANEGOCIACAO") ? "S" : "N"));
                                    intent.putExtras(params);
                                    context.startActivity(intent);


                                }
                            });

                            bt_salles_lista.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    POS = obj;
                                    Intent intent = new Intent(context, PedidosActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Bundle params = new Bundle();
                                    params.putString("CODIGO", obj.getCODIGO());
                                    params.putString("LOJA", obj.getLOJA());
                                    params.putString("ROTINA", rotina);
                                    params.putString("IDAGE",obj.get_IDAGE());
                                    intent.putExtras(params);
                                    context.startActivity(intent);

                                }
                            });
                        }


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

    private class ClickAgenda implements  View.OnClickListener {

        private Agendamento agenda = null;

        private Context context = null;

        private Cliente_fast obj = null;


        public ClickAgenda(Context context,Cliente_fast obj) {

            this.context = context;
            this.obj     = obj;
        }

        @Override
        public void onClick(View v) {

            final List<String[]> motivos = new ArrayList<>();

            //busca agenda
            try {

                AgendamentoDAO dao = new AgendamentoDAO();

                dao.open();

                agenda = dao.seek(new String[] {obj.get_IDAGE()});

                dao.close();

                if (agenda == null){

                    throw new Exception("Agendamento Não Encontrado !");

                }

            } catch (Exception e){

                toast(e.getMessage());

                agenda = null;

            }

            if (agenda == null){

                return;

            }

            if (agenda.getSITUACAO().trim().equals("E")){

                toast("Agendamento Encerrado !");

                return;


            }

            if (!agenda.getMOBILE().trim().isEmpty()){

                toast("Agendamento Possui Venda "+agenda.getMOBILE());

                return;


            }

            final Dialog dialog = new Dialog(context);

            dialog.setContentView(R.layout.getmotivos);

            dialog.setTitle("JUSTIFICATIVAS");

            final Spinner spPeriodo = (Spinner) dialog.findViewById(R.id.edit_periodo_117);



            final RadioButton rb_visita = (RadioButton) dialog.findViewById(R.id.radio_nao_visita_117);

            RadioButton rb_venda  = (RadioButton) dialog.findViewById(R.id.radio_nao_venda_117);

            rb_visita.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    boolean checked = ((RadioButton) v).isChecked();

                    if (checked){

                        motivos.clear();

                        motivos.addAll(loadMotivos("NAOVISITA"));

                        motivoadapter = new defaultAdapter(context, R.layout.choice_default_row, motivos, "Motivo:");

                        spPeriodo.setAdapter(motivoadapter);

                        spPeriodo.setSelection(0);

                    }


                }
            });

            rb_venda.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    boolean checked = ((RadioButton) v).isChecked();

                    if (checked){

                        motivos.clear();

                        motivos.addAll(loadMotivos("NAOVENDA"));

                        motivoadapter = new defaultAdapter(context, R.layout.choice_default_row, motivos, "Motivo:");

                        spPeriodo.setAdapter(motivoadapter);

                        spPeriodo.setSelection(0);


                    }


                }
            });


            if (agenda.getMOTIVONVISITA().trim().isEmpty() && agenda.getMOTIVONVENDA().trim().isEmpty()){

                rb_visita.setChecked(true);
                rb_venda.setChecked(false);

                motivos.clear();

                motivos.addAll(loadMotivos("NAOVISITA"));

                motivoadapter = new defaultAdapter(context, R.layout.choice_default_row, motivos, "Motivo:");

                spPeriodo.setAdapter(motivoadapter);

                spPeriodo.setSelection(0);

            } else {

                if (!agenda.getMOTIVONVISITA().trim().isEmpty()){

                    rb_visita.setChecked(true);
                    rb_venda.setChecked(false);

                    motivos.clear();

                    motivos.addAll(loadMotivos("NAOVISITA"));

                    motivoadapter = new defaultAdapter(context, R.layout.choice_default_row, motivos, "Motivo:");

                    spPeriodo.setAdapter(motivoadapter);

                    int index = 0;

                    for(String[] op : motivos){

                        if(op[0].equals(agenda.getMOTIVONVISITA())){

                            break;

                        }

                        index++;

                    }

                    if (index > motivos.size() - 1) {

                        index = 0;

                    }

                    spPeriodo.setSelection(index);

                } else {

                    rb_venda.setChecked(true);
                    rb_visita.setChecked(false);

                    motivos.clear();

                    motivos.addAll(loadMotivos("NAOVENDA"));

                    motivoadapter = new defaultAdapter(context, R.layout.choice_default_row, motivos, "Motivo:");

                    spPeriodo.setAdapter(motivoadapter);

                    int index = 0;

                    for(String[] op : motivos){

                        if(op[0].equals(agenda.getMOTIVONVENDA())){

                            break;

                        }

                        index++;

                    }

                    if (index > motivos.size() - 1) {

                        index = 0;

                    }

                    spPeriodo.setSelection(index);


                }

            }

            final EditText observacao = (EditText) dialog.findViewById(R.id.edit_observacao_117);

            observacao.setText(agenda.getOBS().trim());


            final Button confirmar = (Button) dialog.findViewById(R.id.bt_confirma_117);
            final Button cancelar = (Button) dialog.findViewById(R.id.bt_cancela_117);

            cancelar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            confirmar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Object lixo = spPeriodo.getSelectedItem();

                    if (((String[]) lixo)[0].equals("000")) {

                        toast("Selecione Um Item Ou Cancele A Edição !");

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
                    try {

                        agenda.setOBS(observacao.getText().toString());

                        agenda.setSITUACAO("T");

                        agenda.setDTDIGIT(App.getHoje());

                        agenda.setHRDIGIT(App.getHoraHHMM());

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

                        agenda.setLATITUDE(latitude);

                        agenda.setLONGITUDE(longitude);

                        AgendamentoDAO dao = new AgendamentoDAO();

                        dao.open();

                        if (!dao.Update(agenda)){

                            toast("Falha Na Atualização Do Agendamento");

                        }

                        dao.close();

                        ClienteDAO daoCliente = new ClienteDAO();

                        daoCliente.open();

                        Cliente_fast cliente = daoCliente.seek_fast(obj.getCODIGO(),obj.getLOJA(),DATAAGENDA);

                        daoCliente.close();

                        if (cliente != null) {

                            adapter.setCliente(cliente);

                        }

                        try {

                            AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerTrasmissao, getBaseContext(), App.user, "PUTAGENDAMENTOS", "PUTAGENDAMENTOS", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_AGENDAMENTO_UNICO, null, null, -1);

                            acessoWeb.setCODIGO(agenda.getID());

                            acessoWeb.start();

                        } catch (Exception e){

                            Toast(e.getMessage());
                        }

                    } catch (Exception e) {

                        toast(e.getMessage());
                    }


                }

            });

            dialog.show();


            spPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Object lixo = spPeriodo.getSelectedItem();

                    if (!((String[]) lixo)[0].equals("000")) {

                        if (rb_visita.isChecked()) {

                            agenda.setMOTIVONVISITA(((String[]) lixo)[0]);
                            agenda.setMOTIVONVENDA("");

                        } else {

                            agenda.setMOTIVONVENDA(((String[]) lixo)[0]);
                            agenda.setMOTIVONVISITA("");

                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });



        }

        private  void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }
    }

}
