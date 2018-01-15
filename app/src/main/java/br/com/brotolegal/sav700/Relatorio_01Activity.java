package br.com.brotolegal.sav700;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.sav700.help20.Help20Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.dao.Base01DAO;
import br.com.brotolegal.savdatabase.dao.MarcaDAO;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.entities.Marca;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.util.Filtro_Categoria;
import br.com.brotolegal.savdatabase.util.Filtro_Cliente;
import br.com.brotolegal.savdatabase.util.Filtro_Data;
import br.com.brotolegal.savdatabase.util.Filtro_Marca;
import br.com.brotolegal.savdatabase.util.Filtro_Produto;
import br.com.brotolegal.savdatabase.util.Rel_Topicos;
import br.com.brotolegal.savdatabase.util.Rel_Visao;
import br.com.brotolegal.savdatabase.util.VisaoRel01;

public class Relatorio_01Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lv;
    private List<Object> lsLista;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_01);


        toolbar = (Toolbar) findViewById(R.id.tb_relatorio_01_511);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Relatório");

        toolbar.setLogo(R.mipmap.ic_launcher);

        lv = (ListView) findViewById(R.id.lv_relatorio_01_511);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.menu_relatorio_01);

        try {


            loadRelatorio();



        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

    }


    private void loadRelatorio() throws Exception {

        String Topicos = App.manager_filtro_01.getStringTopicos();

        String[] TopArray  = Topicos.split("\\|");

        List<String[]> chaves = new ArrayList<>();

        for(int x =  0; x < TopArray.length ; x++){

                chaves.add(new String[] {TopArray[x],getIndiceVisao(TopArray[x])});

        }

        lsLista    = new ArrayList<>();

        List<VisaoRel01> lsRegistros = new ArrayList<>();

        if (App.manager_filtro_01.getRel_visao().getOpcao().equals("BT")) {

            PedidoCabMbDAO dao = new PedidoCabMbDAO();

            dao.open();

            lsRegistros = dao.getPedidosToRel01();

            dao.close();

        } else {

            Base01DAO dao = new Base01DAO();

            dao.open();

            lsRegistros = dao.getPedidosToRel01();

            dao.close();

        }

        for(VisaoRel01 rel : lsRegistros){

            //totalizador geral
            Integer i = getChave("CABEC");

            if ( i == null){

                lsLista.add(new VisaoRel01("CABEC"));

                i = 0;

            }

            ((VisaoRel01) lsLista.get(i)).setTOTALQTD(((VisaoRel01) lsLista.get(i)).getTOTALQTD() + rel.getQTDFDS());
            ((VisaoRel01) lsLista.get(i)).setTOTALVLR(((VisaoRel01) lsLista.get(i)).getTOTALVLR() + rel.getVALOR());

            String chvant = "";

            List<String[]> localchaves = new ArrayList<>();

            for(String[] chave : chaves){

                localchaves.add(chave);

                String chv =  chvant+chave[0]+rel.getChave(Integer.parseInt(chave[1]));

                chvant = chv;

                i = getChaveByVisao(chv,localchaves,chave[0]);

                if (i == null){

                    lsLista.add(new VisaoRel01(chave[0]));

                    i = lsLista.size()-1;

                    ((VisaoRel01) lsLista.get(i)).setVISAO(chave[0]);

                    ((VisaoRel01) lsLista.get(i)).setDATA(rel.getDATA());

                    ((VisaoRel01) lsLista.get(i)).setCLIENTE(rel.getCLIENTE());

                    ((VisaoRel01) lsLista.get(i)).setLOJA(rel.getLOJA());

                    ((VisaoRel01) lsLista.get(i)).setCLIENTERAZAO(rel.getCLIENTERAZAO());

                    ((VisaoRel01) lsLista.get(i)).setREDE(rel.getREDE());

                    ((VisaoRel01) lsLista.get(i)).setREDEDESCRI(rel.getREDEDESCRI());

                    ((VisaoRel01) lsLista.get(i)).setCATEGORIA(rel.getCATEGORIA());

                    ((VisaoRel01) lsLista.get(i)).setCATEGORIADESCRI(rel.getCATEGORIADESCRI());

                    ((VisaoRel01) lsLista.get(i)).setMARCA(rel.getMARCA());

                    ((VisaoRel01) lsLista.get(i)).setMARCADESCRICAO(rel.getMARCADESCRICAO());

                    ((VisaoRel01) lsLista.get(i)).setPRODUTO(rel.getPRODUTO());

                    ((VisaoRel01) lsLista.get(i)).setPRODUTODESCRICAO(rel.getPRODUTODESCRICAO());


                }

                ((VisaoRel01) lsLista.get(i)).setTOTALQTD(((VisaoRel01) lsLista.get(i)).getTOTALQTD() + rel.getQTDFDS());
                ((VisaoRel01) lsLista.get(i)).setTOTALVLR(((VisaoRel01) lsLista.get(i)).getTOTALVLR() + rel.getVALOR());
            }

        };

        if (lsLista.size() == 0) {

            lsLista.add(new NoData("Nehum Dado Foi Encontrado !"));

        }

        adapter = new Adapter(Relatorio_01Activity.this,lsLista);

        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    private Integer getChaveByVisao(String chave,List<String[]> chaves,String visao){

        Integer retorno = null;

        int x = 0;

        for(Object obj : lsLista){

            if ( (obj instanceof VisaoRel01) && ((VisaoRel01) obj).getVISAO().equals(visao)){

                String chvant = "";

                for(String[] cha : chaves) {

                    String chv = chvant + cha[0] + ((VisaoRel01) obj).getChave(Integer.parseInt(cha[1]));

                    chvant = chv;
                }

                if (chave.equals(chvant)) {

                    retorno = x;

                    break;

                }

            }

            x++;
        }


        return retorno;


    }

    private Integer getChave(String chave){

        Integer retorno = null;

        int x = 0;

        for(Object obj : lsLista){


            if ( (obj instanceof VisaoRel01) && ((VisaoRel01) obj).getVISAO().equals(chave)){

                retorno = x;

                break;

            }

            x++;
        }


        return retorno;


    }

    private String getIndiceVisao(String visao){

        String  retorno = "0";

        if (visao.equals("DT")) retorno = "0";

        if (visao.equals("CL")) retorno = "1";

        if (visao.equals("CT")) retorno = "2";

        if (visao.equals("MC")) retorno = "3";

        if (visao.equals("PR")) retorno = "4";


        return  retorno;

    }

    private String getCorVisao(String visao){

        String  retorno = "#FFFACD";

        if (visao.equals("DT")) retorno = "#FFDAB9";

        if (visao.equals("CL")) retorno = "#FFE4E1";

        if (visao.equals("CT")) retorno = "#FFF0F5";

        if (visao.equals("MC")) retorno = "#FAF0E6";

        if (visao.equals("PR")) retorno = "#FDF5E6";


        return  retorno;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_relatorio_01, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.ac_relatorio_01_cancelar:

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {

        lsLista            = new ArrayList<Object>();

        super.finish();

    }

    private class Adapter extends BaseAdapter {

        private DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private DecimalFormat format_03 = new DecimalFormat(",##0.000");
        private DecimalFormat format_04 = new DecimalFormat(",##0.0000");
        private DecimalFormat format_05 = new DecimalFormat(",##0.00000");

        private List<Object> lsObjetos;

        private Context context;

        final int ITEM_VIEW_CABEC      = 0;
        final int ITEM_VIEW_DATA       = 1;
        final int ITEM_VIEW_CLIENTE    = 2;
        final int ITEM_VIEW_CATEGORIA  = 3;
        final int ITEM_VIEW_MARCA      = 4;
        final int ITEM_VIEW_PRODUTO    = 5;
        final int ITEM_VIEW_NODATA     = 6;
        final int ITEM_VIEW_COUNT      = 7;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos  = pObjects;

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private String Cabec() {

            String retorno = "FILTROS DO RELATÓRIO";


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


            if (lsObjetos.get(position) instanceof VisaoRel01) {

                if (((VisaoRel01) lsObjetos.get(position)).getVISAO().equals("CABEC")) retorno = ITEM_VIEW_CABEC;

                if (((VisaoRel01) lsObjetos.get(position)).getVISAO().equals("DT")) retorno = ITEM_VIEW_DATA;

                if (((VisaoRel01) lsObjetos.get(position)).getVISAO().equals("CL")) retorno = ITEM_VIEW_CLIENTE;

                if (((VisaoRel01) lsObjetos.get(position)).getVISAO().equals("CT")) retorno = ITEM_VIEW_CATEGORIA;

                if (((VisaoRel01) lsObjetos.get(position)).getVISAO().equals("MC")) retorno = ITEM_VIEW_MARCA;

                if (((VisaoRel01) lsObjetos.get(position)).getVISAO().equals("PR")) retorno = ITEM_VIEW_PRODUTO;

            }

            if (lsObjetos.get(position) instanceof NoData) {

                retorno = ITEM_VIEW_NODATA;

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

                            convertView = inflater.inflate(R.layout.relatorio_visao_dt, null);

                            break;


                        case ITEM_VIEW_DATA:

                            convertView = inflater.inflate(R.layout.relatorio_visao_dt, null);

                            break;


                        case ITEM_VIEW_CLIENTE:

                            convertView = inflater.inflate(R.layout.relatorio_visao_dt, null);

                            break;

                        case ITEM_VIEW_CATEGORIA:

                            convertView = inflater.inflate(R.layout.relatorio_visao_dt, null);

                            break;

                        case ITEM_VIEW_MARCA:

                            convertView = inflater.inflate(R.layout.relatorio_visao_dt, null);

                            break;

                        case ITEM_VIEW_PRODUTO:

                            convertView = inflater.inflate(R.layout.relatorio_visao_dt, null);

                            break;

                        case ITEM_VIEW_NODATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        final VisaoRel01 obj = (VisaoRel01) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.view_VDT);

                        view.setBackgroundColor(Color.parseColor("#F08080"));

                        TextView lbl_titulo_VDT = (TextView) convertView.findViewById(R.id.lbl_titulo_VDT);

                        TextView txt_nome_VDT   = (TextView) convertView.findViewById(R.id.txt_nome_VDT);

                        TextView txt_qtd_VDT    = (TextView) convertView.findViewById(R.id.txt_qtd_VDT);

                        TextView txt_vlr_VDT    = (TextView) convertView.findViewById(R.id.txt_vlr_VDT);

                        lbl_titulo_VDT.setText("RESUMO FINAL");

                        txt_nome_VDT.setText("Base Das Informações Pedidos Do Tablet.");

                        txt_qtd_VDT.setText("QTD: "+format_03.format(obj.getTOTALQTD())+" FDS.");

                        txt_vlr_VDT.setText("R$ "+format_02.format(obj.getTOTALVLR()));


                        break;
                    }

                    case ITEM_VIEW_DATA: {

                        final VisaoRel01 obj = (VisaoRel01) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.view_VDT);

                        view.setBackgroundColor(Color.parseColor(getCorVisao("DT")));

                        TextView lbl_titulo_VDT = (TextView) convertView.findViewById(R.id.lbl_titulo_VDT);

                        TextView txt_nome_VDT   = (TextView) convertView.findViewById(R.id.txt_nome_VDT);

                        TextView txt_qtd_VDT    = (TextView) convertView.findViewById(R.id.txt_qtd_VDT);

                        TextView txt_vlr_VDT    = (TextView) convertView.findViewById(R.id.txt_vlr_VDT);

                        lbl_titulo_VDT.setText("DATA: "+obj.getDATA());

                        txt_nome_VDT.setText("");

                        txt_qtd_VDT.setText("QTD: "+format_03.format(obj.getTOTALQTD())+" FDS.");

                        txt_vlr_VDT.setText("R$ "+format_02.format(obj.getTOTALVLR()));

                        break;

                    }

                    case ITEM_VIEW_CLIENTE: {

                        final VisaoRel01 obj = (VisaoRel01) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.view_VDT);

                        view.setBackgroundColor(Color.parseColor(getCorVisao("CL")));

                        TextView lbl_titulo_VDT = (TextView) convertView.findViewById(R.id.lbl_titulo_VDT);

                        TextView txt_nome_VDT   = (TextView) convertView.findViewById(R.id.txt_nome_VDT);

                        TextView txt_qtd_VDT    = (TextView) convertView.findViewById(R.id.txt_qtd_VDT);

                        TextView txt_vlr_VDT    = (TextView) convertView.findViewById(R.id.txt_vlr_VDT);

                        lbl_titulo_VDT.setText("CLIENTE: "+obj.getCLIENTE()+"-"+obj.getLOJA()+" "+obj.getCLIENTERAZAO());

                        txt_nome_VDT.setText("REDE: "+obj.getREDE()+"-"+obj.getREDEDESCRI());

                        txt_qtd_VDT.setText("QTD: "+format_03.format(obj.getTOTALQTD())+" FDS.");

                        txt_vlr_VDT.setText("R$ "+format_02.format(obj.getTOTALVLR()));


                        break;

                    }

                    case ITEM_VIEW_CATEGORIA: {

                        final VisaoRel01 obj = (VisaoRel01) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.view_VDT);

                        view.setBackgroundColor(Color.parseColor(getCorVisao("CT")));

                        TextView lbl_titulo_VDT = (TextView) convertView.findViewById(R.id.lbl_titulo_VDT);

                        TextView txt_nome_VDT   = (TextView) convertView.findViewById(R.id.txt_nome_VDT);

                        TextView txt_qtd_VDT    = (TextView) convertView.findViewById(R.id.txt_qtd_VDT);

                        TextView txt_vlr_VDT    = (TextView) convertView.findViewById(R.id.txt_vlr_VDT);

                        lbl_titulo_VDT.setText("CATEGORIA: "+obj.getCATEGORIA()+"-"+obj.getCATEGORIADESCRI());

                        txt_nome_VDT.setText("");

                        txt_qtd_VDT.setText("QTD: "+format_03.format(obj.getTOTALQTD())+" FDS.");

                        txt_vlr_VDT.setText("R$ "+format_02.format(obj.getTOTALVLR()));

                        break;

                    }


                    case ITEM_VIEW_MARCA: {

                        final VisaoRel01 obj = (VisaoRel01) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.view_VDT);

                        view.setBackgroundColor(Color.parseColor(getCorVisao("MC")));

                        TextView lbl_titulo_VDT = (TextView) convertView.findViewById(R.id.lbl_titulo_VDT);

                        TextView txt_nome_VDT   = (TextView) convertView.findViewById(R.id.txt_nome_VDT);

                        TextView txt_qtd_VDT    = (TextView) convertView.findViewById(R.id.txt_qtd_VDT);

                        TextView txt_vlr_VDT    = (TextView) convertView.findViewById(R.id.txt_vlr_VDT);

                        lbl_titulo_VDT.setText("MARCA: "+obj.getMARCA()+"-"+obj.getMARCADESCRICAO());

                        txt_nome_VDT.setText("");

                        txt_qtd_VDT.setText("QTD: "+format_03.format(obj.getTOTALQTD())+" FDS.");

                        txt_vlr_VDT.setText("R$ "+format_02.format(obj.getTOTALVLR()));


                        break;

                    }

                    case ITEM_VIEW_PRODUTO: {

                        final VisaoRel01 obj = (VisaoRel01) lsObjetos.get(pos);

                        View view = (View) convertView.findViewById(R.id.view_VDT);

                        view.setBackgroundColor(Color.parseColor(getCorVisao("PR")));

                        TextView lbl_titulo_VDT = (TextView) convertView.findViewById(R.id.lbl_titulo_VDT);

                        TextView txt_nome_VDT   = (TextView) convertView.findViewById(R.id.txt_nome_VDT);

                        TextView txt_qtd_VDT    = (TextView) convertView.findViewById(R.id.txt_qtd_VDT);

                        TextView txt_vlr_VDT    = (TextView) convertView.findViewById(R.id.txt_vlr_VDT);

                        lbl_titulo_VDT.setText(obj.getPRODUTO().trim()+"-"+obj.getPRODUTODESCRICAO());

                        txt_nome_VDT.setText("CATEGORIA: "+obj.getCATEGORIA()+"-"+obj.getCATEGORIADESCRI()+" GRUPO: "+obj.getMARCA()+"-"+obj.getMARCADESCRICAO());

                        txt_qtd_VDT.setText("QTD: "+format_03.format(obj.getTOTALQTD())+" FDS.");

                        txt_vlr_VDT.setText("R$ "+format_02.format(obj.getTOTALVLR()));

                        break;

                    }

                    case ITEM_VIEW_NODATA:


                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                        tvTexto.setText(obj.getMensagem());

                        break;


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
