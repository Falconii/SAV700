package br.com.brotolegal.sav700.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.DispositivoDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Dispositivo;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CUSTOM;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;


public class Dispositivo_frag extends Fragment {

    private Dispositivo dispositivo;
    private String      LOG = "DISPOSITIVO";
    private Dialog      dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dispositivo_frag, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Dispositivo");


        DispositivoDAO dao = null;

        try {

            dao = new DispositivoDAO();

            dao.open();

            dispositivo = dao.seek(new String[] {"000000"});

            dao.close();

        } catch (Exception e) {

            //
        }

        if (dispositivo == null){

            dispositivo = new Dispositivo();

        }

        TextView txtUsuario = (TextView) rootView.findViewById(R.id.txt_user_333);

        TextView txtAtivo   = (TextView) rootView.findViewById(R.id.txt_ativo_333);

        TextView txtlinha01 = (TextView) rootView.findViewById(R.id.txt_linha_01_333);

        TextView txtlinha02 = (TextView) rootView.findViewById(R.id.txt_linha_02_333);

        TextView txtlinha03 = (TextView) rootView.findViewById(R.id.txt_linha_03_333);

        TextView txtlinha04 = (TextView) rootView.findViewById(R.id.txt_linha_04_333);

        TextView txtlinha05 = (TextView) rootView.findViewById(R.id.txt_linha_05_333);

        TextView txtlinha06 = (TextView) rootView.findViewById(R.id.txt_linha_06_333);

        ImageButton bt_refresh_333 = (ImageButton) rootView.findViewById(R.id.bt_refresh_333);

        txtUsuario.setText(App.user.getNOME());

        txtAtivo.setText(dispositivo.getCHAPA());

        txtlinha01.setText("Fabrincante: "+dispositivo.getFABRICANTE());

        txtlinha02.setText("Modelo: "+dispositivo.getMODELO());

        txtlinha03.setText("S.O:  "+dispositivo.getVERSAO());

        txtlinha04.setText("API:  "+dispositivo.getBUILD());

        txtlinha05.setText("IMEI: "+dispositivo.getIMEI());

        txtlinha06.setText("TOKEN: "+dispositivo.get_TOKEN());

        bt_refresh_333.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Busca arquivo dispositivo
                try {

                    DispositivoDAO dao = new DispositivoDAO();

                    dao.open();

                    dispositivo = dao.seek(new String[]{"000000"});

                    if (dispositivo == null){

                        dispositivo = new Dispositivo();

                    }

                    dispositivo.setTOKEN(FirebaseInstanceId.getInstance().getToken());

                    dao.Update(dispositivo);

                    dao.close();

                    AccessWebInfo acessoWeb = new AccessWebInfo(mHandlerDispositivo, getContext(), App.user, "SETTOKEN", "SETTOKEN", RETORNO_TIPO_ESTUTURADO, PROCESSO_CUSTOM, null, null,-1);

                    acessoWeb.addParam("CCODUSER" , App.user.getCOD().trim());

                    acessoWeb.addParam("CPASSUSER", App.user.getSENHA().trim());

                    acessoWeb.addParam("CIMEI"    ,dispositivo.getIMEI() );

                    acessoWeb.addParam("CTOKEN"   ,dispositivo.getTOKEN());

                    acessoWeb.start();


                } catch (Exception e) {

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

        });


        return rootView;

    }



    @Override
    public void onAttach(Context context) {

        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private Handler mHandlerDispositivo = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(getContext(), "Dispositivo.Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
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


                if (msg.getData().getString("CERRO").equals("EXE")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    try {

                        //inclusao.processa();

                    } catch (Exception e) {

                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    processado = true;

                }


                if ((msg.getData().getString("CERRO").equals("FEC"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(getContext(), "Erro: "+msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            }
            catch (Exception E){

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(getContext(), "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };


}
