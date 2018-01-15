package br.com.brotolegal.sav700;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class Notificacao01Activity extends AppCompatActivity {

    private String from;
    private String Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao01);

        Intent i = getIntent();

        if (i != null) {

            Bundle params = i.getExtras();

            from = params.getString("FROM");

            Message = params.getString("MESSAGE");

        }


        TextView tx_from  = (TextView) findViewById(R.id.tx_from);

        TextView tx_messa = (TextView) findViewById(R.id.tx_message);

        tx_from.setText(from);

        tx_messa.setText(Message);

        Log.i("SAPO",FirebaseInstanceId.getInstance().getToken());


    }
}
