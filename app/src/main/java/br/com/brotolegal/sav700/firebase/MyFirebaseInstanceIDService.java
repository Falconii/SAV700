package br.com.brotolegal.sav700.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.brotolegal.sav700.DispositivoActivity;
import br.com.brotolegal.sav700.Notificacao01Activity;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.DispositivoDAO;
import br.com.brotolegal.savdatabase.dao.TaskDAO;
import br.com.brotolegal.savdatabase.entities.Dispositivo;
import br.com.brotolegal.savdatabase.entities.Task;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;

/**
 * Created by Falconi on 01/09/2016.
 */

//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        try {

            DispositivoDAO dao = new DispositivoDAO();

            dao.open();

            Dispositivo dispositivo = dao.seek(new String[] {"000000"});

            if (dispositivo != null){

                dispositivo.setTOKEN(refreshedToken);

                dao.Update(dispositivo);

                Log.i(TAG, "Token Novo: "+refreshedToken);

            } else {

                dispositivo = new Dispositivo();

                dispositivo.setCOD("000000");

                dispositivo.setTOKEN(refreshedToken);

                dao.insert(dispositivo);

                Log.i(TAG, "Token Novo: "+refreshedToken);

            }

            dao.close();

        } catch (Exception e) {

            Log.d(TAG, e.getMessage());

        }

        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
    private void sendRegistrationToServer(String token) {

        try {

            Log.d(TAG, "Token Registrado: " + token);

            DispositivoDAO dao = new DispositivoDAO();

            dao.open();

            Dispositivo dispositivo = dao.seek(new String[]{"000000"});

            if (dispositivo != null) {

                dispositivo.setTOKEN(token);

                dao.Update(dispositivo);

                Log.i(TAG, "Token Novo: " + token);

            } else {

                dispositivo = new Dispositivo();

                dispositivo.setCOD("000000");

                dispositivo.setTOKEN(token);

                dao.insert(dispositivo);

                Log.i(TAG, "Token Novo: " + token);

            }

            dao.close();

            dispositivo.setTOKEN(FirebaseInstanceId.getInstance().getToken());

            AccessWebInfo acessoWeb = new AccessWebInfo(null, getBaseContext(), App.user, "SETTOKEN", "SETTOKEN", RETORNO_TIPO_ESTUTURADO, PROCESSO_CUSTOM, null, null, -1);

            acessoWeb.addParam("CCODUSER", App.user.getCOD().trim());

            acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

            acessoWeb.addParam("CIMEI", dispositivo.getIMEI());

            acessoWeb.addParam("CTOKEN", dispositivo.getTOKEN());

            acessoWeb.start();

            sendNotification("TOKEN","ENVIANDO TOKEN PARA O SERVIDOR !","");

        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

        //This method to generate push notification
    private void sendNotification(String titulo,String message,String messageBody) {

        Bundle params = new Bundle();
        params.putString("FROM"   ,titulo);
        params.putString("MESSAGE",messageBody);


//        //MainActivity Intent Registration
//        Intent intent = new Intent(this, Notificacao01Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtras(params);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //Take Notification Sound
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Generate the Notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);


        NotificationManager notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Create Push Notification
        notificationManager.notify(0, notificationBuilder.build());
    }

}




