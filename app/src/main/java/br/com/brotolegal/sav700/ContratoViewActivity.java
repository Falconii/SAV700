package br.com.brotolegal.sav700;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ContratoDAO;
import br.com.brotolegal.savdatabase.entities.Contrato;
import br.com.brotolegal.savdatabase.entities.NoData;

public class ContratoViewActivity extends AppCompatActivity {

    private String CONTRATO;
    private String CODIGO;
    private String RAZAO;

    private Adapter adapter;

    private DecimalFormat format_02 = new DecimalFormat(",##0.00");

    private List<Object> lsContrato = new ArrayList<Object>();

    private Contrato contrato = new Contrato();

    Toolbar toolbar;

    TextView txt_nro_008     ;
    TextView txt_data_008    ;
    TextView txt_status_008  ;
    TextView txt_tipo_008    ;
    TextView txt_rede_008    ;
    TextView txt_cliente_008 ;
    TextView txt_desc_fin_loja_008 ;
    TextView txt_desc_fin_cd_008;

    ListView lvContrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contrato_view);

        toolbar = (Toolbar) findViewById(R.id.tb_contrato);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle(getResources().getString(R.string.app_versao));
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent i = getIntent();

        if (i != null) {

            Bundle params = i.getExtras();

            CODIGO    = params.getString("CODIGO");
            RAZAO     = params.getString("RAZAO");
            CONTRATO  = params.getString("CONTRATO");

        } else {

            CONTRATO  = "";

        }

        txt_nro_008        = (TextView) findViewById(R.id.txt_nro_008);
        txt_data_008       = (TextView) findViewById(R.id.txt_data_008);
        txt_status_008     = (TextView) findViewById(R.id.txt_status_008);
        txt_tipo_008       = (TextView) findViewById(R.id.txt_tipo_008);
        txt_rede_008       = (TextView) findViewById(R.id.txt_rede_008);
        txt_cliente_008    = (TextView) findViewById(R.id.txt_cliente_008);
        txt_desc_fin_loja_008    = (TextView) findViewById(R.id.txt_desc_fin_loja_008);
        txt_desc_fin_cd_008       = (TextView) findViewById(R.id.txt_desc_fin_cd_008);

        lvContrato         = (ListView) findViewById(R.id.lvcontrato);

        try{

            loadContrato();

        } catch (Exception e){

            Toast.makeText(getBaseContext(),"Falha Na Carga Dos Contratos/n"+e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;


            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        lsContrato = new ArrayList<Object>();

    }

    private void loadContrato() throws Exception {

        lsContrato.add("CABEC");

        ContratoDAO dao = new ContratoDAO();

        dao.open();

        lsContrato.addAll(dao.getSeekFull2(CONTRATO));

        dao.close();

        if (lsContrato.size() == 1){

            lsContrato.add(new NoData("Não Encontrado Contrato Para Este Cliente!"));

            contrato = new Contrato();

        } else {

            contrato = (Contrato) lsContrato.get(1);

        }

        txt_nro_008.setText(contrato.getCODIGO());
        txt_data_008.setText(App.aaaammddToddmmaaaa(contrato.getDATA()));
        txt_status_008.setText(contrato.get_STATUS());
        txt_tipo_008.setText(contrato.get_TIPO());
        txt_rede_008.setText(contrato.get_REDE());
        txt_cliente_008.setText(RAZAO);

        if (contrato.getTIPO().equals("T")){

            txt_desc_fin_loja_008.setText(format_02.format(contrato.getPECDESCBOL())+"%");
            txt_desc_fin_cd_008.setText(format_02.format(contrato.getCBOL2())+"%");


        } else {

            txt_desc_fin_loja_008.setText("");
            txt_desc_fin_cd_008.setText("");


        }

        adapter = new Adapter(getBaseContext(),lsContrato);

        lvContrato.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }


    //INNER CLASS

    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        Context context;

        final int ITEM_VIEW_CABEC   = 0;
        final int ITEM_VIEW_DETALHE = 1;
        final int ITEM_VIEW_NO_DATA = 2;
        final int ITEM_VIEW_COUNT   = 3;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos = pObjects;
            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Contrato) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Itens: " + String.valueOf(qtd);

            return retorno;
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


        @Override
        public int getItemViewType(int position) {

            int retorno = -1;


            if (lsObjetos.get(position) instanceof String) {

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsObjetos.get(position) instanceof Contrato) {

                retorno = ITEM_VIEW_DETALHE;

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

                            convertView = inflater.inflate(R.layout.contrato_view_cabec_row, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.contrato_view_det_row, null);

                            break;


                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        break;
                    }

                    case ITEM_VIEW_DETALHE: {

                        final Contrato obj = (Contrato) lsObjetos.get(pos);

                        TextView txt_descricao_017     = (TextView) convertView.findViewById(R.id.txt_descricao_017);
                        TextView txt_perloja_017       = (TextView) convertView.findViewById(R.id.txt_perloja_017);
                        TextView txt_perccd_017        = (TextView) convertView.findViewById(R.id.txt_perccd_017);

                        txt_descricao_017.setText(obj.get_DESCRICAO());

                        txt_perloja_017.setText(format_02.format(obj.getPECDESCBOL())+"%");
                        txt_perccd_017.setText(format_02.format(obj.getCBOL2())+"%");

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

                toast("Erro No Adapdador =>" + e.getMessage());

            }

            return convertView;

        }


        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }

    }

}
