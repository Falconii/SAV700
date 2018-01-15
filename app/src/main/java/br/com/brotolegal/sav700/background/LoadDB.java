package br.com.brotolegal.sav700.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falconi on 11/07/2017.
 */

public class LoadDB extends AsyncTask<Void,String,List<Object>> {

    private Context context;
    private ProgressDialog progress;
    private IRefreshScreen refreshScreen;
    private ProgressBar progressBar;

    public LoadDB(Context context,IRefreshScreen iRefreshScreen,ProgressBar progressBar){

        this.context = context;
        this.refreshScreen = iRefreshScreen;
        this.progressBar   = progressBar;

    }

    @Override
    protected void onPreExecute() {

        if (progressBar == null) {

            progress = new ProgressDialog(context);
            progress.setMessage("Carregando Dados....");
            progress.show();

        } else {

            progressBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected List<Object> doInBackground(Void... params) {

        List<Object> retorno = new ArrayList<>();

        try {

            retorno = refreshScreen.Loading();

            if (retorno == null){

                retorno = new ArrayList<>();

            }

        } catch (Exception e){

            retorno = new ArrayList<>();

        }

        return retorno;
    }


    @Override
    protected void onProgressUpdate(String... value) {

    }

    @Override
    protected void onPostExecute(List<Object> params) {

        if (progress != null) {

            refreshScreen.refresh((ArrayList) params);

        } else {

            refreshScreen.refreshOver((ArrayList) params);

        }

        if (progress != null) {

            progress.dismiss();

        }

    }
}
