package br.com.brotolegal.sav700.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Falconi on 13/06/2018.
 */

public class MyToast {


    private Context context;
    private int    tempo;
    private String  mensagem;


    public MyToast(Context context,  String mensagem, int tempo) {
        this.context = context;
        this.tempo = tempo;
        this.mensagem = mensagem;
    }

    public void show(){

        Toast toast = Toast.makeText(context,mensagem,Toast.LENGTH_LONG);

        toast.setDuration(tempo*1000);

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        toast.show();

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
