package br.com.brotolegal.sav700;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenAtWorkingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_at_working);
    }

    public void ClickVoltar(View v){

        finish();


    }
}
