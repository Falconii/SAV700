package br.com.brotolegal.sav700.background;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.sav700.SAVActivity;
import br.com.brotolegal.sav700.fragments.DashBoard_Frag;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.eventbus.NotificationConexao;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.SoapServEnv;

/**
 * Created by Falconi on 12/04/2017.
 */

public class ProcessosBackGround extends Service {

    String TAG = "BACKGROUND";

    ArrayList<Worker> workers = new ArrayList<>();


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        Log.i(TAG,"Serviço criado !!");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String pedido = "";

        String rotina = "";

        Log.i(TAG,"Serviço Iniciado !!");

        if ( intent != null ) {

            Bundle params = intent.getExtras();

            pedido        = params.getString("PEDIDO","");

            rotina        = params.getString("ROTINA","");

        }


        Worker worker = new Worker(startId,pedido,rotina);

        worker.start();

        workers.add(worker);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG,"Serviço Finalizado !!");

        for(int x = 0; x < workers.size(); x++){

            workers.get(x).ativo = false;

        }
    }

    class Worker extends Thread {

        int count = 0;
        int startId = 0;
        boolean ativo = false;
        String pedido = "";
        String rotina = "";


        public Worker(int startId,String pedido,String rotina){

            this.startId = startId;
            this.pedido  = pedido;
            this.rotina  = rotina;

        }

        public void run(){

            try{

                if (rotina.equals("PEDIDO")){

                    Log.i(TAG,"Enviando Pedido "+pedido);

                    transmitir();

                }

                if (rotina.equals("CONEXAO")){

                    Log.i(TAG,"Verificando as conexões..");

                    VerificaConexaoAtiva();

                }
            }catch (Exception e){

                Log.i(TAG,e.getMessage());

            }

            stopSelf(startId);


        }

        private void transmitir() throws  Exception {

            AccessWebInfo acessoWeb = new AccessWebInfo(null, getBaseContext(), App.user, "PUTSALESORDERMB", "PUTSALESORDERMB", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_ATUALIZACARGABACKGROUND , null, null,startId);

            acessoWeb.setPEDIDO(pedido);

            acessoWeb.start();
        }

        private  void VerificaConexaoAtiva() throws Exception{

            String SOAP_ACTION = "GETSTATUS";

            String METHOD_NAME = "GETSTATUS";

            Boolean lAchou = false;

            List<Config> lsLista = new ArrayList<>();

            ConfigDAO dao  = new ConfigDAO();

            Config   padrao = new Config();

            //BUSCA conexoes gravadas
            try {

                dao.open();

                padrao  = dao.seek(new String[] {"000"});

                if (padrao != null) lsLista.add(padrao);

                for(Config conf : dao.getConexoes()) {

                    if (conf.getDESCRICAO().equals(padrao.getDESCRICAO())) continue;

                    lsLista.add(conf);

                }

                dao.close();

                if (lsLista.size() == 0) {

                    NotificationConexao notificationConexao = new NotificationConexao("001","SEM CONFIGURAÇÃO","F",null);

                    EventBus.getDefault().post(notificationConexao);

                    return;

                }

                if (!verificaConexao()) {

                    NotificationConexao notificationConexao = new NotificationConexao("001","SEM INTERNET","R",null);

                    EventBus.getDefault().post(notificationConexao);

                    return;

                }

                NotificationConexao notificationConexao;

                for (Config conf : lsLista) {

                    try {


                        Log.i(TAG,"Validando "+conf.getDESCRICAO());

                        notificationConexao = new NotificationConexao("001","VERIFICANDO","V",conf);

                        EventBus.getDefault().post(notificationConexao);

                        SoapObject request = new SoapObject(conf.getNSFull(), METHOD_NAME);

                        SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);

                        request.addProperty("CCODUSER", App.user.getCOD().trim());

                        request.addProperty("CPASSUSER", App.user.getSENHA().trim());

                        envelope.implicitTypes = true;

                        envelope.setAddAdornments(false);

                        envelope.dotNet = true;

                        envelope.setOutputSoapObject(request);

                        HttpTransportSE androidHttpTransport = new HttpTransportSE(conf.getUrlFull());

                        androidHttpTransport.debug = true;

                        androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

                        androidHttpTransport.call(SOAP_ACTION, envelope);

                        SoapObject spRetorno = (SoapObject) envelope.getResponse();

                        String cerro = spRetorno.getPropertyAsString("CERRO");

                        String cmsgerro = spRetorno.getPropertyAsString("CMSGERRO");

                        if (cerro.equals("000")) {

                            conf.setCODIGO("000");

                            dao.open();

                            dao.Update(conf);

                            dao.close();

                            notificationConexao = new NotificationConexao("000","SEM ERROS","C",conf);

                            EventBus.getDefault().post(notificationConexao);

                            lAchou = true;

                            break;

                        } else {

                        }

                    } catch (Exception e){

                        Log.i(TAG,e.getMessage());

                    }
                }

                if (!lAchou){

                    notificationConexao = new NotificationConexao("001","SEM CONEXAO ATIVA","D",null);

                    EventBus.getDefault().post(notificationConexao);

                }
            }
            catch(Exception e){

                Log.i(TAG,e.getMessage());

            }

        }

        public  boolean verificaConexao() {
            boolean conectado;
            ConnectivityManager conectivtyManager = (ConnectivityManager) getBaseContext().getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
            if (    conectivtyManager.getActiveNetworkInfo() != null
                    && conectivtyManager.getActiveNetworkInfo().isAvailable()
                    && conectivtyManager.getActiveNetworkInfo().isConnected()) {
                conectado = true;
            } else {
                conectado = false;
            }
            return conectado;
        }


    }
}
