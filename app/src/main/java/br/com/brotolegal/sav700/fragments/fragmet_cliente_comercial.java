package br.com.brotolegal.sav700.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.brotolegal.sav700.R;


public class fragmet_cliente_comercial extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView tvID;
    private TextView tvCODIGO;
    private TextView tvCNPJ;
    private TextView tvIE;
    private TextView tvENTREGA;
    private TextView tvSTATUS;
    private TextView tvMENSAGEM;
    private TextView tvRAZAO;
    private TextView tvFANTASIA;
    private TextView tvPESSOA;
    private TextView tvLOGRADOURO;
    private TextView tvENDERECO;
    private TextView tvNUMERO;
    private TextView tvCOMPLEMENTO;
    private TextView tvBAIRRO;
    private TextView tvCODIGO_CIDADE;
    private TextView tvESTADO;
    private TextView tvCIDADE;
    private TextView tvCEP;
    private TextView tvDDD;
    private TextView tvTELEFONE;
    private TextView tvHOME;
    private TextView tvEMAILNFE;
    private TextView tvEMAIL;
    private TextView tvFUNDACAO;
    private TextView tvCANAL;
    private TextView tvREDE;
    private TextView tvREDEDESCRI;
    private TextView tvCANALDESCRI;
    private TextView tvTABPRECO;
    private TextView tvTABPRECODESCRI;
    private TextView tvPOLITICA;
    private TextView tvPOLITICADESCRI;
    private TextView tvCOND;
    private TextView tvCONDDESCRI;
    private TextView tvBoleto;
    private TextView tvSimplesOP;
    private TextView tvIsentoST;
    private TextView tvLIMITE;
    private TextView tvICMS;


    private OnFragmentInteractionListener mListener;

    public fragmet_cliente_comercial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmet_cliente_comercial.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmet_cliente_comercial newInstance(String param1, String param2) {
        fragmet_cliente_comercial fragment = new fragmet_cliente_comercial();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fragmet_cliente_comercial, container, false);



        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);



    }
    @Override
    public void onResume() {

        tvRAZAO.setText("MARCOS RENATO FALCONI");

        super.onResume();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
