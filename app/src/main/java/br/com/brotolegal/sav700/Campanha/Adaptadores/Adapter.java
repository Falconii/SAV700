package br.com.brotolegal.sav700.Campanha.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.sav700.CampanhaBimestreViewActivity;
import br.com.brotolegal.sav700.CampanhaClienteViewActivity;
import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.dao.CampanhaDAO;
import br.com.brotolegal.savdatabase.entities.Campanha_fast;
import br.com.brotolegal.savdatabase.entities.Negociacao;
import br.com.brotolegal.savdatabase.entities.NoData;

public class Adapter extends BaseAdapter {


    private String _Alcada     = "";
    private String _Verba      = "";
    private String _Ordem      = "";

    private DecimalFormat format_02 = new DecimalFormat(",##0.00");
    private DecimalFormat format_03 = new DecimalFormat(",##0.000");
    private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
    private DecimalFormat format_05 = new DecimalFormat(",##0.00000");

    private List<Object> lsObjetos;

    private Context context;

    final int ITEM_VIEW_CABEC           = 0;
    final int ITEM_VIEW_CAMPANHA        = 1;
    final int ITEM_VIEW_CLIENTE         = 2;
    final int ITEM_VIEW_CATEGORIA       = 3;
    final int ITEM_VIEW_MARCA           = 4;
    final int ITEM_VIEW_BIMESTRE        = 5;
    final int ITEM_VIEW_MENSAL          = 6;
    final int ITEM_VIEW_NO_DATA         = 7;
    final int ITEM_VIEW_COUNT           = 8;


    private LayoutInflater inflater;

    private String PeriodoInicial;

    private String PeriodoFinal;

    private String Periodo;

    public void setPeriodoInicial(String periodoInicial) {
        PeriodoInicial = periodoInicial;
    }

    public void setPeriodoFinal(String periodoFinal) {
        PeriodoFinal = periodoFinal;
    }

    public Adapter(Context context, String PeriodoInicial, String PeriodoFinal, List<Object> pObjects) {

        this.lsObjetos  = pObjects;

        this.context    = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.PeriodoInicial = PeriodoInicial;

        this.PeriodoFinal   = PeriodoFinal;

        this.Periodo        = "";

    }

    private String Cabec() {

        String retorno = "";

        int qtd = 0;

        for (Object obj : lsObjetos) {

            if (obj instanceof Campanha_fast) {

                qtd = qtd + 1;

            }

        }

        retorno = "Total de Registros: " + String.valueOf(qtd);

        return retorno;
    }

    private void insereDetalhe(int pos) {

        Campanha_fast cab = ((Campanha_fast) lsObjetos.get(pos));

        List<Campanha_fast> detalhe = new ArrayList<>();

        try {

            CampanhaDAO dao = new CampanhaDAO();

            dao.open();

            detalhe = dao.getCampanhaCategoria(cab.getCAMPANHA(),new String[] {PeriodoInicial,PeriodoFinal});

            if (detalhe.size() == 0) {

                toast("Não Encontrei As Categorias Desta Camapnha !");

                return ;

            }

            int posicao = pos+1;

            if (!(cab.get_ViewDetalhe())) {

                for(Campanha_fast categoria : detalhe){

                   lsObjetos.add(posicao,categoria);

                    posicao++;

                    List<Campanha_fast> marcas = dao.getCampanhaCategoriaMarca(cab.getCAMPANHA(),categoria.getCATEGORIA(),new String[] {PeriodoInicial,PeriodoFinal});

                    lsObjetos.addAll(posicao,marcas);

                    posicao += marcas.size();

                }

            } else {

                for(int x = 0; x < lsObjetos.size(); x++){

                    if (lsObjetos.get(x) instanceof Campanha_fast) {

                        if (    ( ((Campanha_fast) lsObjetos.get(x)).get_TIPO().equals("T") && ((Campanha_fast) lsObjetos.get(x)).getCAMPANHA().equals(cab.getCAMPANHA()) )  ||
                                ( ((Campanha_fast) lsObjetos.get(x)).get_TIPO().equals("M") && ((Campanha_fast) lsObjetos.get(x)).getCAMPANHA().equals(cab.getCAMPANHA()) ) ) {
                            lsObjetos.remove(x);

                            x--;

                        }

                    }
                }
            }

            cab.set_ViewDetalhe(!cab.get_ViewDetalhe());

            notifyDataSetChanged();

        } catch (Exception e){

            toast(e.getMessage());

        }

    }

    private void insereParticipanteDetalhe(int pos) {

        Campanha_fast cab = ((Campanha_fast) lsObjetos.get(pos));

        List<Campanha_fast> detalhe = new ArrayList<>();

        try {

            CampanhaDAO dao = new CampanhaDAO();

            dao.open();

            detalhe = dao.getCampanhaParticipanteCategoria(cab.getCAMPANHA(),cab.getPARTICIPANTE(),new String[] {PeriodoInicial,PeriodoFinal});

            if (detalhe.size() == 0) {

                toast("Não Encontrei As Categorias Deste Participante !");

                return ;

            }

            int posicao = pos+1;

            if (!(cab.get_ViewDetalhe())) {

                for(Campanha_fast categoria : detalhe){

                    lsObjetos.add(posicao,categoria);

                    posicao++;

                    List<Campanha_fast> marcas = dao.getCampanhaParticipanteCategoriaMarca(cab.getCAMPANHA(),cab.getPARTICIPANTE(),categoria.getCATEGORIA(),new String[] {PeriodoInicial,PeriodoFinal});

                    lsObjetos.addAll(posicao,marcas);

                    posicao += marcas.size();

                }

            } else {

                for(int x = 0; x < lsObjetos.size(); x++){

                    if (lsObjetos.get(x) instanceof Campanha_fast) {

                        if (    ( ((Campanha_fast) lsObjetos.get(x)).get_TIPO().equals("T") && ((Campanha_fast) lsObjetos.get(x)).getPARTICIPANTE().equals(cab.getPARTICIPANTE()) )  ||
                                ( ((Campanha_fast) lsObjetos.get(x)).get_TIPO().equals("M") && ((Campanha_fast) lsObjetos.get(x)).getPARTICIPANTE().equals(cab.getPARTICIPANTE()) ) ) {
                            lsObjetos.remove(x);

                            x--;

                        }

                    }
                }
            }

            dao.close();

            cab.set_ViewDetalhe(!cab.get_ViewDetalhe());

            notifyDataSetChanged();

        } catch (Exception e){

            toast(e.getMessage());

        }

    }

    private void insereBimestreMeses(int pos) {

        Campanha_fast cab = ((Campanha_fast) lsObjetos.get(pos));

        List<Campanha_fast> detalhe = new ArrayList<>();

        try {

            CampanhaDAO dao = new CampanhaDAO();

            dao.open();

            detalhe = dao.getCampanhaParticipanteCategoriaMes(cab.getCAMPANHA(),cab.getPARTICIPANTE(),cab.getDATA());

            if (detalhe.size() == 0) {

                toast("Não Encontrei As Categorias Deste Bimestre !");

                return ;

            }

            int posicao = pos+1;

            if (!(cab.get_ViewDetalhe())) {

                for(Campanha_fast categoria : detalhe){

                    lsObjetos.add(posicao,categoria);

                    posicao++;

                    List<Campanha_fast> marcas = dao.getCampanhaParticipanteCategoriaMarcaMes(cab.getCAMPANHA(),cab.getPARTICIPANTE(),categoria.getCATEGORIA(),cab.getDATA());

                    lsObjetos.addAll(posicao,marcas);

                    posicao += marcas.size();

                }

            } else {

                for(int x = 0; x < lsObjetos.size(); x++){

                    if (lsObjetos.get(x) instanceof Campanha_fast) {

                        if (    ( ((Campanha_fast) lsObjetos.get(x)).get_TIPO().equals("T") && ((Campanha_fast) lsObjetos.get(x)).getPARTICIPANTE().equals(cab.getPARTICIPANTE()) )  ||
                                ( ((Campanha_fast) lsObjetos.get(x)).get_TIPO().equals("M") && ((Campanha_fast) lsObjetos.get(x)).getPARTICIPANTE().equals(cab.getPARTICIPANTE()) ) ) {
                            lsObjetos.remove(x);

                            x--;

                        }

                    }
                }
            }

            dao.close();

            cab.set_ViewDetalhe(!cab.get_ViewDetalhe());

            notifyDataSetChanged();

        } catch (Exception e){

            toast(e.getMessage());

        }

    }

    @Override
    public int getCount() {
        return lsObjetos.size();
    }

    @Override
    public Object getItem(int position) {
        return lsObjetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_COUNT;
    }

    public void setPeriodo(String periodo) {
        Periodo = periodo;
    }

    @Override
    public int getItemViewType(int position) {

        int retorno = -1;


        if (lsObjetos.get(position) instanceof String) {

            retorno = ITEM_VIEW_CABEC;
        }



        if (lsObjetos.get(position) instanceof Campanha_fast) {

            switch (((Campanha_fast) lsObjetos.get(position)).get_TIPO().charAt(0)){

                case 'C':retorno = ITEM_VIEW_CAMPANHA;break;
                case 'L':retorno = ITEM_VIEW_CLIENTE;break;
                case 'T':retorno = ITEM_VIEW_CATEGORIA ;break;
                case 'M':retorno = ITEM_VIEW_MARCA ;break;
                case 'B':retorno = ITEM_VIEW_BIMESTRE; break;
                case 'E':retorno = ITEM_VIEW_MENSAL; break;
                default:retorno  = ITEM_VIEW_CAMPANHA;

            }

        }

        if (lsObjetos.get(position) instanceof NoData) {

            retorno = ITEM_VIEW_NO_DATA;

        }

        return retorno;


    }

    @Override
    public boolean isEnabled(int position) {

        boolean retorno = false;

        return retorno;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {

            final int pos = position;

            final int type = getItemViewType(position);

            if (convertView == null) {

                switch (type) {

                    case ITEM_VIEW_CABEC:

                        convertView = inflater.inflate(R.layout.defaultdivider, null);

                        break;


                    case ITEM_VIEW_CAMPANHA:

                        convertView = inflater.inflate(R.layout.meta_fechamento_05, null);

                        break;


                    case ITEM_VIEW_CLIENTE:

                        convertView = inflater.inflate(R.layout.meta_cliente, null);

                        break;

                    case ITEM_VIEW_CATEGORIA:

                        convertView = inflater.inflate(R.layout.meta_categoria_05, null);

                        break;

                    case ITEM_VIEW_MARCA:

                        convertView = inflater.inflate(R.layout.meta_marca_05, null);

                        break;


                    case ITEM_VIEW_BIMESTRE:

                        convertView = inflater.inflate(R.layout.meta_bimestral_05, null);

                        break;

                    case ITEM_VIEW_MENSAL:

                        convertView = inflater.inflate(R.layout.meta_mensal_05, null);

                        break;


                    case ITEM_VIEW_NO_DATA:

                        convertView = inflater.inflate(R.layout.no_data_row, null);

                        break;

                }

            }

            switch (type) {

                case ITEM_VIEW_CABEC: {

                    TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                    tvCabec.setText(Cabec());

                    break;
                }

                case ITEM_VIEW_CAMPANHA: {

                    final Campanha_fast obj = (Campanha_fast) lsObjetos.get(pos);

                    Float total = (obj.getREAL()+obj.getCARTEIRA());

                    View view = convertView.findViewById(R.id.meta_fechamento_05);

                    Button buttonnext_05            = (Button) convertView.findViewById(R.id.buttonnext_05);
                    ImageButton drilldown_05        = (ImageButton) convertView.findViewById(R.id.drilldown_05);

                    TextView  lbl_titulo_05         = (TextView) convertView.findViewById(R.id.lbl_titulo_05);
                    TextView  txt_Meta2_05          = (TextView) convertView.findViewById(R.id.txt_Meta2_05);
                    TextView  txt_Realizado2_05     = (TextView) convertView.findViewById(R.id.lbl_Realizado2_05);
                    TextView  txt_Carteira2_05      = (TextView) convertView.findViewById(R.id.txt_Carteira2_05);
                    TextView  txt_Total2_05         = (TextView) convertView.findViewById(R.id.txt_Total2_05);
                    TextView  txt_perc2_05          = (TextView) convertView.findViewById(R.id.txt_perc2_05);
                    TextView  txt_premio2_05         = (TextView) convertView.findViewById(R.id.txt_premio2_05);


                    //Preenchimento
                    lbl_titulo_05.setText("CAMPANHA: "+obj.getCAMPANHA().trim()+"-"+obj.getDESCCAMPANHA().trim());

                    txt_Meta2_05.setText(format_02.format(obj.getOBJETIVO()));
                    txt_Realizado2_05.setText(format_02.format(obj.getREAL()));
                    txt_Carteira2_05.setText(format_02.format(obj.getCARTEIRA()));
                    txt_Total2_05.setText(format_02.format(total));
                    if (obj.getOBJETIVO().compareTo(0f) <= 0) {

                        txt_perc2_05.setText(format_02.format(0f));

                    } else {

                        txt_perc2_05.setText(format_02.format((total/obj.getOBJETIVO())*100));
                    }

                    txt_premio2_05.setText(format_02.format(obj.getPREMIO()));

                    buttonnext_05.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent=new Intent(context, CampanhaClienteViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle params = new Bundle();
                            params.putString("PARTICIPANTE"  , "");
                            params.putString("CODCAMPANHA"   , obj.getCAMPANHA());
                            params.putString("NOMECAMPANHA"  , obj.getNOMEPARTICIPANTE());
                            intent.putExtras(params);
                            context.startActivity(intent);

                        }
                    });

                    drilldown_05.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            insereDetalhe(pos);


                        }
                    });


                    break;

                }

                case ITEM_VIEW_CLIENTE: {

                    final Campanha_fast obj = (Campanha_fast) lsObjetos.get(pos);

                    Float total = (obj.getREAL()+obj.getCARTEIRA());


                    Button buttonnext_05            = (Button) convertView.findViewById(R.id.buttonnext_05);
                    ImageButton drilldown_05        = (ImageButton) convertView.findViewById(R.id.drilldown_05);

                    View view = convertView.findViewById(R.id.meta_cliente_05);
                    TextView  lbl_titulo_05 = (TextView) convertView.findViewById(R.id.lbl_titulo_05);
                    TextView  txt_Meta2_05          = (TextView) convertView.findViewById(R.id.txt_Meta2_05);
                    TextView  txt_Realizado2_05     = (TextView) convertView.findViewById(R.id.lbl_Realizado2_05);
                    TextView  txt_Carteira2_05      = (TextView) convertView.findViewById(R.id.txt_Carteira2_05);
                    TextView  txt_Total2_05         = (TextView) convertView.findViewById(R.id.txt_total2_05);
                    TextView  txt_perc2_05          = (TextView) convertView.findViewById(R.id.txt_perc2_05);
                    TextView  txt_premio_05         = (TextView) convertView.findViewById(R.id.txt_premio_05);



                    //Preenchimento
                    lbl_titulo_05.setText("PARTIPANTE: "+obj.getPARTICIPANTE().trim()+"-"+obj.getNOMEPARTICIPANTE().trim());

                    txt_Meta2_05.setText(format_02.format(obj.getOBJETIVO()));
                    txt_Realizado2_05.setText(format_02.format(obj.getREAL()));
                    txt_Carteira2_05.setText(format_02.format(obj.getCARTEIRA()));
                    txt_Total2_05.setText(format_02.format(total));
                    if (obj.getOBJETIVO().compareTo(0f) <= 0) {

                        txt_perc2_05.setText(format_02.format(0f));

                    } else {

                        txt_perc2_05.setText(format_02.format((total/obj.getOBJETIVO())*100));
                    }
                    txt_premio_05.setText(format_02.format(obj.getPREMIO()));


                    buttonnext_05.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent=new Intent(context, CampanhaBimestreViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle params = new Bundle();
                            params.putString("PARTICIPANTE"  , obj.getPARTICIPANTE());
                            params.putString("CODCAMPANHA"   , obj.getCAMPANHA());
                            params.putString("NOMECAMPANHA"  , obj.getDESCCAMPANHA());
                            intent.putExtras(params);
                            context.startActivity(intent);

                        }
                    });

                    drilldown_05.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            insereParticipanteDetalhe(pos);


                        }
                    });




                    break;

                }

                case ITEM_VIEW_CATEGORIA: {

                    final Campanha_fast obj = (Campanha_fast) lsObjetos.get(pos);

                    Float total = (obj.getREAL()+obj.getCARTEIRA());

                    View view = convertView.findViewById(R.id.meta_categoria_05);
                    TextView  lbl_titulo_05 = (TextView) convertView.findViewById(R.id.lbl_titulo_05);
                    TextView  txt_Meta2_05          = (TextView) convertView.findViewById(R.id.txt_Meta2_05);
                    TextView  txt_Realizado2_05     = (TextView) convertView.findViewById(R.id.lbl_Realizado2_05);
                    TextView  txt_Carteira2_05      = (TextView) convertView.findViewById(R.id.txt_Carteira2_05);
                    TextView  txt_Total2_05         = (TextView) convertView.findViewById(R.id.txt_Total2_05);
                    TextView  txt_perc2_05          = (TextView) convertView.findViewById(R.id.txt_perc2_05);
                    TextView  txt_premio2_05         = (TextView) convertView.findViewById(R.id.txt_premio2_05);


                    //Preenchimento
                    lbl_titulo_05.setText("CATEGORIA: "+obj.getCATEGORIA().trim()+"-"+obj.getDESCCATEGORIA().trim());

                    txt_Meta2_05.setText(format_02.format(obj.getOBJETIVO()));
                    txt_Realizado2_05.setText(format_02.format(obj.getREAL()));
                    txt_Carteira2_05.setText(format_02.format(obj.getCARTEIRA()));
                    txt_Total2_05.setText(format_02.format(total));
                    if (obj.getOBJETIVO().compareTo(0f) <= 0) {

                        txt_perc2_05.setText(format_02.format(0f));

                    } else {

                        txt_perc2_05.setText(format_02.format((total/obj.getOBJETIVO())*100));
                    }
                    txt_premio2_05.setText(format_02.format(obj.getPREMIO()));

                    break;

                }

                case ITEM_VIEW_MARCA: {

                    final Campanha_fast obj = (Campanha_fast) lsObjetos.get(pos);


                    Float total = (obj.getREAL()+obj.getCARTEIRA());

                    View view = convertView.findViewById(R.id.meta_marca_05);
                    TextView  lbl_titulo_05 = (TextView) convertView.findViewById(R.id.lbl_titulo_05);
                    TextView  txt_Meta2_05          = (TextView) convertView.findViewById(R.id.txt_Meta2_05);
                    TextView  txt_Realizado2_05     = (TextView) convertView.findViewById(R.id.lbl_Realizado2_05);
                    TextView  txt_Carteira2_05      = (TextView) convertView.findViewById(R.id.txt_Carteira2_05);
                    TextView  txt_Total2_05         = (TextView) convertView.findViewById(R.id.txt_Total2_05);
                    TextView  txt_perc2_05          = (TextView) convertView.findViewById(R.id.txt_perc2_05);
                    TextView  txt_premio2_05         = (TextView) convertView.findViewById(R.id.txt_premio2_05);


                    //Preenchimento
                    lbl_titulo_05.setText("MARCA: "+obj.getMARCA().trim()+"-"+obj.getDESCMARCA().trim());

                    txt_Meta2_05.setText(format_02.format(obj.getOBJETIVO()));
                    txt_Realizado2_05.setText(format_02.format(obj.getREAL()));
                    txt_Carteira2_05.setText(format_02.format(obj.getCARTEIRA()));
                    txt_Total2_05.setText(format_02.format(total));
                    if (obj.getOBJETIVO().compareTo(0f) <= 0) {

                        txt_perc2_05.setText(format_02.format(0f));

                    } else {

                        txt_perc2_05.setText(format_02.format((total/obj.getOBJETIVO())*100));
                    }
                    txt_premio2_05.setText(format_02.format(obj.getPREMIO()));

                    break;

                }


                case ITEM_VIEW_BIMESTRE: {

                    final Campanha_fast obj = (Campanha_fast) lsObjetos.get(pos);

                    Float total = (obj.getREAL()+obj.getCARTEIRA());

                    View view = convertView.findViewById(R.id.meta_bimestral_05);
                    TextView  lbl_campanha_05 = (TextView) convertView.findViewById(R.id.lbl_campanha_05);
                    TextView  lbl_periodo_05  = (TextView) convertView.findViewById(R.id.lbl_periodo_05);

                    TextView  txt_Meta2_05          = (TextView) convertView.findViewById(R.id.txt_Meta2_05);
                    TextView  txt_Realizado2_05     = (TextView) convertView.findViewById(R.id.txt_Realizado2_05);
                    TextView  txt_Carteira2_05      = (TextView) convertView.findViewById(R.id.txt_Carteira2_05);
                    TextView  txt_Total2_05         = (TextView) convertView.findViewById(R.id.txt_total2_05);
                    TextView  txt_perc2_05          = (TextView) convertView.findViewById(R.id.txt_perc2_05);
                    TextView  txt_premio2_05        = (TextView) convertView.findViewById(R.id.txt_premio2_05);


                    //Preenchimento
                    lbl_campanha_05.setText("CAMPANHA: "+obj.getCAMPANHA().trim()+"-"+obj.getDESCCAMPANHA().trim()+" PARTIC.: "+obj.getNOMEPARTICIPANTE().trim());
                    lbl_periodo_05.setText("PERÍODO: "+this.Periodo);
                    txt_Meta2_05.setText(format_02.format(obj.getOBJETIVO()));
                    txt_Realizado2_05.setText(format_02.format(obj.getREAL()));
                    txt_Carteira2_05.setText(format_02.format(obj.getCARTEIRA()));
                    txt_Total2_05.setText(format_02.format(total));
                    if (obj.getOBJETIVO().compareTo(0f) <= 0) {

                        txt_perc2_05.setText(format_02.format(0f));

                    } else {

                        txt_perc2_05.setText(format_02.format((total/obj.getOBJETIVO())*100));
                    }

                    txt_premio2_05.setText(format_02.format(obj.getPREMIO()));

                    break;

                }

                case ITEM_VIEW_MENSAL: {

                    final Campanha_fast obj = (Campanha_fast) lsObjetos.get(pos);

                    ImageButton drilldown_05        = (ImageButton) convertView.findViewById(R.id.drilldown_05);

                    Float total = (obj.getREAL()+obj.getCARTEIRA());

                    View view = convertView.findViewById(R.id.meta_mensal_05);
                    TextView  lbl_titulo_05 = (TextView) convertView.findViewById(R.id.lbl_titulo_05);
                    TextView  txt_Meta2_05          = (TextView) convertView.findViewById(R.id.txt_Meta2_05);
                    TextView  txt_Realizado2_05     = (TextView) convertView.findViewById(R.id.txt_Realizado2_05);
                    TextView  txt_Carteira2_05      = (TextView) convertView.findViewById(R.id.txt_Carteira2_05);
                    TextView  txt_Total2_05         = (TextView) convertView.findViewById(R.id.txt_total2_05);
                    TextView  txt_perc2_05          = (TextView) convertView.findViewById(R.id.txt_perc2_05);
                    TextView  txt_premio2_05        = (TextView) convertView.findViewById(R.id.txt_premio2_05);


                    //Preenchimento
                    lbl_titulo_05.setText("MÊS: "+mesExtenso(obj.getDATA()));

                    txt_Meta2_05.setText(format_02.format(obj.getOBJETIVO()));
                    txt_Realizado2_05.setText(format_02.format(obj.getREAL()));
                    txt_Carteira2_05.setText(format_02.format(obj.getCARTEIRA()));
                    txt_Total2_05.setText(format_02.format(total));
                    if (obj.getOBJETIVO().compareTo(0f) <= 0) {

                        txt_perc2_05.setText(format_02.format(0f));

                    } else {

                        txt_perc2_05.setText(format_02.format((total/obj.getOBJETIVO())*100));
                    }
                    txt_premio2_05.setText(format_02.format(obj.getPREMIO()));

                    drilldown_05.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            insereBimestreMeses(pos);


                        }
                    });

                    break;

                }


                case ITEM_VIEW_NO_DATA: {

                    final NoData obj = (NoData) lsObjetos.get(pos);

                    TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                    tvTexto.setText(obj.getMensagem());

                    break;

                }


                default:
                    break;
            }

        } catch (Exception e) {

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            Log.i("SAV",exceptionAsString);

            toast("Erro No Adapdador =>" + e.getMessage());

        }

        return convertView;

    }


    private String mesExtenso(String data){

        String retorno = "";

        try {

            DateFormat df;
            df             = new SimpleDateFormat("dd/MM/yyyy");
            Date DataBase;
            DateFormat df2 = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));

            String data1   = "01" + "/" +   data.substring(4,6) + "/" + data.substring(0,4);
            DataBase       = df.parse(data1);
            String mes     = df2.format(DataBase);

            retorno        = mes.toUpperCase( new Locale("pt", "BR"))+" DE "+data.substring(0,4);

        } catch (Exception e){

            retorno = "ERRO !!!";

        }

        return retorno;

    }
    public void toast(String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }






}