package br.com.brotolegal.sav700.background;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.UsuarioDAO;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;

/**
 * Created by Falconi on 20/09/2016.
 */
public class ReceiverCaduca extends BroadcastReceiver {

    private String TAG = "ReceiverCaduca";

    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            try {

                App.setDBAP(context);

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



            StatusDAO dao = new StatusDAO();

            dao.open();

            Status status = dao.seek(new String[] {" "} );

            dao.Update(status);

            dao.close();

            PedidoCabMbDAO daoCab = new PedidoCabMbDAO();

            daoCab.open();

            daoCab.Caduca(status.getULTATUAL());

            daoCab.close();

            sendNotification("","Procedimento Executado !!!!","");

        } catch (Exception e){

            Log.i("ReceiverCaduca",e.getMessage());

        }

    }

    private void sendNotification(String titulo,String message,String messageBody) {

        try {

            PendingIntent pendingIntent = PendingIntent.getActivity(App.getCustomAppContext(), 0, new Intent(), 0);

            //Take Notification Sound
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            //Generate the Notification
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(App.getCustomAppContext())
                    .setSmallIcon(br.com.brotolegal.savdatabase.R.drawable.notification_template_icon_bg)
                    .setContentTitle("BROTO LEGAL")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) App.getCustomAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(100, notificationBuilder.build());


        } catch (Exception e) {

            Log.i("ReceiverCaduca", e.getMessage());

        }
    }

}
