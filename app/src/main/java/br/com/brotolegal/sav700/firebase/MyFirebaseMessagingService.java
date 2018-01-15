package br.com.brotolegal.sav700.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;
import org.ksoap2.serialization.PropertyInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import br.com.brotolegal.sav700.Notificacao01Activity;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.sav700.util.AudioPlayer;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.AcordoDAO;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.NotificacaoDAO;
import br.com.brotolegal.savdatabase.dao.OcorrenciaDAO;
import br.com.brotolegal.savdatabase.dao.PreAcordoDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.UsuarioDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Notificacao;
import br.com.brotolegal.savdatabase.entities.Ocorrencia;
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;
import br.com.brotolegal.savdatabase.eventbus.NotificationCarga;
import br.com.brotolegal.savdatabase.eventbus.NotificationPreAcordo;
import br.com.brotolegal.savdatabase.eventbus.NotificationSincronizacao;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.wsentities.TASK;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private Config config;
    private String rotina      = "";
    private String MV_ZBLECRG  = "";
    private String MV_ZBLEPED  = "";
    private String MV_ZDTECRG  = "";
    private String MV_ZDTEPED  = "";
    private String MV_ZHRECRG  = "";
    private String MV_ZHREPED  = "";
    private String codacao     = "";
    private String titulo      = "";
    private String mensagem    = "";
    private String acordo      = "";
    private String flag        = "";
    private String TOTTABLE    = "";
    private String QTD         = "";
    private String PERCENTUAL  = "";
    private String CODTASK     = "";
    private String CFIL        = "";
    private String CDOC        = "";
    private String CSERIE      = "";
    private String CNUMPED     = "";
    private String CRAZAO      = "";
    private String CHORA       = "";
    private String CACORDO     = "";
    private String CACORDOP    = "";
    private String CHISTO      = "";
    private String CALCADA     = "";
    private String CSTATUS     = "";
    private String CMENSAGEM   = "";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        try {

            App.setDBAP(getBaseContext());

        } catch (Exception e) {

            Log.i(TAG,e.getMessage());

        }


        if (App.dbap == null){

            Log.i(TAG,"Sem DBAP");

        }


        if (App.dbuser == null){


            try {

                UsuarioDAO dao = new UsuarioDAO();

                dao.open();

                Usuario user = dao.getUserMaster();

                if (!(user == null)){

                    App.user = user;

                    App.setDataBaseUser();

                    dao.close();

                } else {

                    dao.close();

                    return;

                }


            } catch (Exception e){


                Log.i(TAG,e.getMessage());

                return;

            }


        }




        for(Map.Entry<String,String> obj : remoteMessage.getData().entrySet()){

            if (obj.getKey().equals("titulo")){

                titulo = obj.getValue();

            }

            if (obj.getKey().equals("mensagem")){

                mensagem = obj.getValue();

            }

            if (obj.getKey().equals("rotina")){

                rotina = obj.getValue();

            }

            if (obj.getKey().equals("codacao")){

                codacao = obj.getValue();

            }

            if (obj.getKey().equals("acordo")){

                acordo = obj.getValue();

            }

            if (obj.getKey().equals("flag")){

                flag = obj.getValue();

            }

            if (obj.getKey().equals("MV_ZBLECRG")){

                MV_ZBLECRG = obj.getValue();

            }

            if (obj.getKey().equals("MV_ZBLEPED")){

                MV_ZBLEPED = obj.getValue();

            }


            if (obj.getKey().equals("MV_ZDTECRG")){


                MV_ZDTECRG = obj.getValue();

            }
            if (obj.getKey().equals("MV_ZDTEPED")){


                MV_ZDTEPED = obj.getValue();

            }
            if (obj.getKey().equals("MV_ZHRECRG")){

                MV_ZHRECRG = obj.getValue();

            }
            if (obj.getKey().equals("MV_ZHREPED")){

                MV_ZHREPED = obj.getValue();

            }

            if (obj.getKey().equals("TOTTABLE")){

                TOTTABLE = obj.getValue();

            }

            if (obj.getKey().equals("QTD")){

                TOTTABLE = obj.getValue();

            }

            if (obj.getKey().equals("PERCENTUAL")){

                TOTTABLE = obj.getValue();

            }

            if (obj.getKey().equals("CODTASK")){

                CODTASK = obj.getValue();

            }

            if (obj.getKey().equals("CFIL")){

                CFIL = obj.getValue();

            }

            if (obj.getKey().equals("CNUMPED")) {


                CNUMPED = obj.getValue();

            }

            if (obj.getKey().equals("CDOC")) {


                CDOC = obj.getValue();

            }


            if (obj.getKey().equals("CSERIE")) {


                CSERIE = obj.getValue();

            }



            if (obj.getKey().equals("CRAZAO")) {


                CRAZAO = obj.getValue();

            }

            if (obj.getKey().equals("CHORA")) {

                CHORA = obj.getValue();

            }


            if (obj.getKey().equals("CACORDO")) {

                CACORDO = obj.getValue();

            }

            if (obj.getKey().equals("CACORDOP")) {

                CACORDOP = obj.getValue();

            }

            if (obj.getKey().equals("CHISTO")) {

                CHISTO = obj.getValue();

            }

            if (obj.getKey().equals("CALCADA")) {

                CALCADA = obj.getValue();

            }


            if (obj.getKey().equals("CSTATUS")) {

                CSTATUS = obj.getValue();

            }


            if (obj.getKey().equals("CMENSAGEM")) {

                CMENSAGEM = obj.getValue();

            }

        }


        try {

            ConfigDAO dao = new ConfigDAO();

            dao.open();

            config = dao.seek(new String[]{"000"});

            dao.close();

        } catch (Exception e){

            Log.i(TAG,e.getMessage());

        }

        /*

        ROTINA
        000 - Carga Inicial
        001 - Executa Uma Tarefa
        002 - Atualiza Os Pedidos
        003 - Informa Versão
        004 - Informa QTD de pedidos Não Transmitidos
        005 - Acordo Liberado Acordo
        006 - Andamento da Carga
        007 - Faturamento De Pedido
        999 - Adiciona as cargas

         */

        if (rotina.isEmpty()){

            return;

        }



        //Adiciona  cargas
        if (rotina.equals("000")){

            sendNotification("BROTO LEGAL ALIMENTOS","Arquivo Gravado No Servidor...Inicio Do DownLoad..." ,"");

            try {

                Put_Ver_Task(rotina,codacao);

            } catch (Exception e) {

                Log.i(TAG,e.getMessage());

            }

            return ;

        }

        // Executa uma tarefa
        if (rotina.equals("001")){

            try {

                Put_Ver_Task(rotina,codacao);

            } catch (Exception e) {

                Log.i(TAG,e.getMessage());

            }

            return ;

        }


        //Atualiza Pedidos
        if (rotina.equals("002")){

            try {

                refresh_pedido();

            } catch (Exception e) {

                Log.i(TAG,e.getMessage());

            }

            return;
        }


        //Solicitação de Versao
        if (rotina.equals("003")){

            try {

                getVersao();

            } catch (Exception e) {

                Log.i(TAG,e.getMessage());

            }

            return;
        }


        //Acordo Liberado Acordo
        if (rotina.equals("005")){

            try {

                PreAcordoDAO dao = new PreAcordoDAO();

                dao.open();

                PreAcordo acordo = dao.seek(new String[] {CACORDO});

                if ( acordo != null){

                    if (CSTATUS.trim().equals("N")){

                        acordo.setSTATUS("7");


                    } else {


                        if (!CALCADA.trim().isEmpty()) {

                            acordo.setStatusByTOTVS(CALCADA);

                        } else {

                            acordo.setSTATUS("6");

                        }

                    }

                }
                acordo.setHISTLIB(CHISTO);

                dao.Update(acordo);

                dao.close();

                Notificacao notificacao = new Notificacao(null,rotina,"ACORDO : "+CACORDOP+ "! MUDOU STATUS PARA "+acordo.get_Status(),CHORA,"",CMENSAGEM,CACORDO);

                NotificacaoDAO daoNOTIFICATION = new NotificacaoDAO();

                daoNOTIFICATION.open();

                daoNOTIFICATION.insert(notificacao);

                daoNOTIFICATION.close();

                AudioPlayer player = new AudioPlayer("faturoupedido.mp3",getBaseContext());

                player.playAudio();

                sendNotification(titulo,"ACORDO : "+CACORDOP+ " MUDOU STATUS PARA "+acordo.get_Status() ,"");

                try {

                    if (acordo.getSTATUS().equals("6")) {


                        AccessWebInfo acessoWeb3 = new AccessWebInfo(null, getBaseContext(), App.user, "GETACORDOS", "GETACORDOS", AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO, AccessWebInfo.PROCESSO_ATUALIZA_ACORDO_PROTHEUS, config, null, -1);

                        acessoWeb3.setCODIGO(acordo.getNUM());

                        acessoWeb3.start();

                    }
                } catch (Exception e) {

                    Log.i(TAG,e.getMessage());
                }

                EventBus.getDefault().post(new NotificationPreAcordo("","",CACORDO,""));

                EventBus.getDefault().post(new NotificationSincronizacao("",""));

            } catch (Exception e) {

                Log.i(TAG,e.getMessage());

            }

            return;


        }

        //006 - Andamento da Carga
        if (rotina.equals("006")){

            try {

                OcorrenciaDAO dao = new OcorrenciaDAO();

                dao.open();

                Ocorrencia ocorrencia = dao.seekByCodigo(new String[]{codacao});

                dao.close();

                if (ocorrencia != null) {

                    if (ocorrencia.getSTATUS().equals("1") && ocorrencia.getCODTAREFA().equals(CODTASK)) {

                        sendNotification("Broto Legal", mensagem, "");

                        atualizaOcorrencia(codacao, mensagem, "1", null,null);

                    }
                }

                EventBus.getDefault().post(new NotificationCarga("000", ""));

                EventBus.getDefault().post(new NotificationSincronizacao("",""));

            } catch (Exception e) {

                Log.i(TAG,e.getMessage());

            }

            return;

        }


        //007 - Faturamento De Pedido
        if (rotina.equals("007")){

            try {

                Notificacao notificacao = new Notificacao(null,rotina,"Pedido: "+CFIL+"-"+CNUMPED+" FATURADO! "+CRAZAO,CHORA,"","NOTA FISCAL: "+CDOC+"-"+CSERIE,CFIL+"-"+CNUMPED);

                NotificacaoDAO daoNOTIFICATION = new NotificacaoDAO();

                daoNOTIFICATION.open();

                daoNOTIFICATION.insert(notificacao);

                daoNOTIFICATION.close();

                AudioPlayer player = new AudioPlayer("faturoupedido.mp3",getBaseContext());

                player.playAudio();

//                long[] pattern = {0, 250, 250, 250};
//
//                Vibrator vibrator  = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//
//                vibrator.vibrate(pattern, -1);

                EventBus.getDefault().post(new NotificationSincronizacao("",""));

            } catch (Exception e) {

                Log.i(TAG,e.getMessage());

            }

            return;

        }

        //Adiciona  cargas
        if (rotina.equals("999")){

            sendNotification(titulo,mensagem ,"");

            return ;

        }



    }

    //This method to generate push notification
    private void sendNotification(String titulo,String message,String messageBody) {

        Bundle params = new Bundle();
        params.putString("FROM"   ,titulo);
        params.putString("MESSAGE",messageBody);


        //MainActivity Intent Registration
        Intent intent = new Intent(this, Notificacao01Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(params);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //Take Notification Sound
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Generate the Notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Create Push Notification
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void refresh_pedido() throws  Exception {

        AccessWebInfo acessoWeb = new AccessWebInfo(null, getBaseContext(), App.user, "", "", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_BACKEND_PEDIDO, config, null,-1);

        acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

        acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

        acessoWeb.start();

    }

    private void getVersao() throws  Exception {

        Versao versao = new Versao();

        PropertyInfo pi = new PropertyInfo();
        pi.setName("Versao");
        pi.setValue(versao);
        pi.setType(versao.getClass());

        AccessWebInfo acessoWeb2 = new AccessWebInfo(null, getBaseContext(), App.user, "GETVERSAO", "GETVERSAO", AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO, AccessWebInfo.PROCESSO_FILE, config, null,-1);

        acessoWeb2.addInfo(pi);
        acessoWeb2.addParam("CCODUSER", App.user.getCOD());
        acessoWeb2.addParam("CPASSUSER", App.user.getSENHA());
        acessoWeb2.addObjeto("VERSAO", new Versao());

        acessoWeb2.start();

    }

    private void Put_Ver_Task(String rotina,String codigo) throws  Exception {

        OcorrenciaDAO dao = new OcorrenciaDAO();

        dao.open();

        Ocorrencia ocorrencia = dao.seekByCodigo(new String[]{codigo});

        dao.close();

        if (ocorrencia == null) {

            sendNotification("SAV - ERRO","OCORRÊNCIA NÃO ENCONTRADA "+codigo+" !","");

            dao.close();

            return;

        }

        if (rotina.equals("000")) {

            ocorrencia.setSTATUS("1");
            ocorrencia.setOBS("Solicitada Carga Automaticamente !!");
            ocorrencia.setARQUIVO("");

            dao.Update(ocorrencia);

        } else {

            if (ocorrencia.getSTATUS().equals("0")){

                dao.close();

                return ;

            }

        }


        dao.close();

        if (rotina.equals("000")) {

            TASK task = new TASK("", "", ocorrencia.getCODIGO(), ocorrencia.getDESCRICAO(), "", "");

            PropertyInfo pi = new PropertyInfo();
            pi.setName("TASK");
            pi.setValue(task);
            pi.setType(task.getClass());

            AccessWebInfo acessoWeb2 = new AccessWebInfo(null, getBaseContext(), App.user, "PUTTASKS", "PUTTASKS", AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO, AccessWebInfo.PROCESSO_FILE, config, null,-1);

            acessoWeb2.addInfo(pi);
            acessoWeb2.addParam("CCODUSER", App.user.getCOD());
            acessoWeb2.addParam("CPASSUSER", App.user.getSENHA());
            acessoWeb2.addObjeto("TASK", new TASK());

            acessoWeb2.start();


        } else {


            if (ocorrencia.getSTATUS().equals("1")) {

                TASK task = new TASK("", "", ocorrencia.getCODIGO(), ocorrencia.getDESCRICAO(), "COM", ocorrencia.getARQUIVO());

                PropertyInfo pi = new PropertyInfo();
                pi.setName("TASK");
                pi.setValue(task);
                pi.setType(task.getClass());

                AccessWebInfo acessoWeb2 = new AccessWebInfo(null, getBaseContext(), App.user, "PROCTASKS", "PROCTASKS", AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO, AccessWebInfo.PROCESSO_FILE, config, null, -1);

                acessoWeb2.addInfo(pi);
                acessoWeb2.addParam("CCODUSER", App.user.getCOD());
                acessoWeb2.addParam("CPASSUSER", App.user.getSENHA());
                acessoWeb2.addObjeto("TASK", new TASK());

                acessoWeb2.start();

            }
        }

    }

    private void refresh_ocorrencia() throws Exception {

        try {

            AccessWebInfo acessoWeb3 = new AccessWebInfo(null, getBaseContext(), App.user, "GETTASK", "GETTASK", AccessWebInfo.RETORNO_ARRAY_ESTRUTURADO, AccessWebInfo.PROCESSO_CUSTOM, config, null,-1);

            acessoWeb3.addParam("CCODUSER",  App.user.getCOD().trim());

            acessoWeb3.addParam("CPASSUSER", App.user.getSENHA().trim());

            acessoWeb3.addParam("CCODMODULE","COM");

            acessoWeb3.start();

        } catch (Exception e) {

            throw new Exception(e.getMessage());
        }

    }

    private void atualizaOcorrencia(String processo, String msg, String status, String filename, String codTask ) throws Exception {

        OcorrenciaDAO dao = new OcorrenciaDAO();

        dao.open();

        Ocorrencia ocorrencia = dao.seekByCodigo(new String[]{processo});

        if (ocorrencia != null) {

            ocorrencia.setSTATUS(status);

            if (filename != null) ocorrencia.setARQUIVO(filename);

            if (codTask != null) ocorrencia.setCODTAREFA(codTask);

            ocorrencia.setOBS(msg);

            dao.Update(ocorrencia);


        } else {

            dao.close();

            throw new Exception("Falha Na Atualização Da Ocorrência.\nOcorrência Não Encontrada.");
        }

        dao.close();

        if (processo.equals("000001") && (ocorrencia != null) && ocorrencia.getSTATUS().equals("0")){

            Calendar c = Calendar.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String atualizacao  = format.format(c.getTime());

            StatusDAO daoST = new StatusDAO();

            daoST.open();

            Status st = daoST.seek(null);

            if (st != null) {

                st.setULTATUAL(atualizacao);

                daoST.Update(st);

            } else {

                //Ignora atualização do status;
            }

            daoST.close();

        }

    }

}