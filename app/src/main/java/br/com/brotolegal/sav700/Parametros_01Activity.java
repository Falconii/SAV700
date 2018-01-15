package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.sav700.help20.Help20Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.MarcaDAO;
import br.com.brotolegal.savdatabase.dao.ProdutoDAO;
import br.com.brotolegal.savdatabase.dao.RedeDAO;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.Marca;
import br.com.brotolegal.savdatabase.entities.Produto;
import br.com.brotolegal.savdatabase.entities.Rede;
import br.com.brotolegal.savdatabase.util.Filtro_Categoria;
import br.com.brotolegal.savdatabase.util.Filtro_Cliente;
import br.com.brotolegal.savdatabase.util.Filtro_Data;
import br.com.brotolegal.savdatabase.util.Filtro_Marca;
import br.com.brotolegal.savdatabase.util.Filtro_Produto;
import br.com.brotolegal.savdatabase.util.ManagerPreferencias;
import br.com.brotolegal.savdatabase.util.Rel_Topicos;
import br.com.brotolegal.savdatabase.util.Rel_Visao;

public class Parametros_01Activity extends AppCompatActivity {

    private Toolbar  toolbar;
    private ListView lv;
    private String   BASE = "";


    private List<Object>     lsLista;

    private defaultAdapter   marcasadapter;

    private defaultAdapter   visaoadapter;

    private Adapter          adapter;

    private String           HELP = "INICIAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros_01);

        toolbar = (Toolbar) findViewById(R.id.tb_filtros_503);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Filtros");

        toolbar.setLogo(R.mipmap.ic_launcher);

        lv = (ListView) findViewById(R.id.lv_filtros_503);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.menu_filtros);

        try {

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                BASE = params.getString("BASE","");

            }

            List<Marca> lsMarcas = new ArrayList<>();

            try {

                MarcaDAO dao = new MarcaDAO();

                dao.open();

                lsMarcas = dao.getAll();

                dao.close();

            }catch (Exception e){

                lsMarcas = new ArrayList<>();

            }

            lsLista    = new ArrayList<>();

            lsLista.add("CABEC");

            App.manager_filtro_01.getFiltro_marca().loadMarcas(lsMarcas);

            lsLista.add(App.manager_filtro_01.getFiltro_data());

            lsLista.add(App.manager_filtro_01.getFiltro_cliente());

            lsLista.add(App.manager_filtro_01.getFiltro_categoria());

            lsLista.add(App.manager_filtro_01.getFiltro_marca());

            lsLista.add(App.manager_filtro_01.getFiltro_produto());

            lsLista.add(App.manager_filtro_01.getRel_visao());

            lsLista.add(App.manager_filtro_01.getRel_topicos());

            adapter = new Adapter(Parametros_01Activity.this,lsLista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();



        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_filtros, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.ac_filtros_ok:

                Intent intent = new Intent(Parametros_01Activity.this,Relatorio_01Activity.class);
                startActivity(intent);


                break;

            case R.id.ac_filtros_cancelar:

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

        ManagerPreferencias savepref = new ManagerPreferencias(Parametros_01Activity.this);

        savepref.savePreferenciasRel_01();

        super.finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == 1 && requestCode == HelpInformation.HelpCliente) {

            Filtro_Cliente obj = adapter.getFiltroCliente();

            String codigo      = null;

            String loja        = null;

            try {

                if (data.hasExtra("CODIGO")) {

                    codigo = data.getExtras().getString("CODIGO");

                }
                if (data.hasExtra("LOJA")) {

                    loja = data.getExtras().getString("LOJA");

                }

                ClienteDAO dao = new ClienteDAO();

                dao.open();

                Cliente cliente = dao.seek(new String[] {codigo,loja});

                dao.close();

                obj.setTipo("C");

                if (cliente != null){

                    obj.setCodigo(cliente.getCODIGO());

                    obj.setLoja(cliente.getLOJA());

                    obj.setDescricao(cliente.getRAZAOPA());

                    obj.setAtivo(true);

                } else {

                    obj.setAtivo(false);

                }

            } catch (Exception e) {

                toast(e.getMessage());
            }

        }

        if (resultCode == 1 && requestCode == HelpInformation.HelpRede) {

            Filtro_Cliente obj = adapter.getFiltroCliente();

            String codigo = null;

            try {

                if (data.hasExtra("CODIGO")) {

                    codigo = data.getExtras().getString("CODIGO");

                }

                RedeDAO dao = new RedeDAO();

                dao.open();

                Rede rede = dao.seek(new String[] {codigo});

                dao.close();

                obj.setTipo("R");

                if (rede != null) {

                    obj.setCodigo(rede.getCODIGO());

                    obj.setDescricao(rede.getDESCRICAO());

                    obj.setAtivo(true);

                } else {

                    obj.setAtivo(false);
                }

            } catch (Exception e) {

                toast(e.getMessage());
            }

        }


        if (resultCode == 1 && requestCode == HelpInformation.HelpProduto) {

            Filtro_Produto obj = adapter.getFiltroProduto();

            String codigo      = null;

            try {

                if (data.hasExtra("CODIGO")) {

                    codigo = data.getExtras().getString("CODIGO");

                }

                ProdutoDAO dao = new ProdutoDAO();

                dao.open();

                Produto produto = dao.seek(new String[] {codigo});

                dao.close();

                if (HELP.equals("INICIAL")){

                    if (produto != null){

                        obj.setCodigoInicial(codigo);
                        obj.setDescriInicial(produto.getDESCRICAO());

                    }else {


                        obj.setCodigoInicial("");
                        obj.setCodigoInicial("");


                    }

                } else {

                    if (produto != null){

                        obj.setCodigofinal(codigo);
                        obj.setDescrifinal(produto.getDESCRICAO());


                    }else {

                        obj.setCodigofinal("");
                        obj.setDescrifinal("");


                    }

                }


            } catch (Exception e) {

                toast(e.getMessage());
            }

        }


    }

    private  void toast(String msg) {

        Toast.makeText(Parametros_01Activity.this, msg, Toast.LENGTH_SHORT).show();

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
        final int ITEM_VIEW_VISAO      = 6;
        final int ITEM_VIEW_TOPICO     = 7;
        final int ITEM_VIEW_COUNT      = 8;

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



        public void setFiltroData(Filtro_Data obj){

            App.manager_filtro_01.getFiltro_data().setAtivo(true);

            App.manager_filtro_01.getFiltro_data().setDatainicial(obj.getDatainicial());

            App.manager_filtro_01.getFiltro_data().setDatafinal(obj.getDatafinal());


            for(int x = 0; x <= lsObjetos.size(); x++){

                if (lsObjetos.get(x) instanceof  Filtro_Data) {

                    lsObjetos.set(x, lsObjetos.get(x));

                    break;
                }

            }

            notifyDataSetChanged();
        }

        public void setFiltroCliente(Filtro_Cliente obj){


            for(int x = 0; x <= lsObjetos.size(); x++){

                if (lsObjetos.get(x) instanceof  Filtro_Cliente) {

                    lsObjetos.set(x, lsObjetos.get(x));

                    break;
                }

            }

            notifyDataSetChanged();
        }

        public Filtro_Cliente getFiltroCliente(){


            for(int x = 0; x <= lsObjetos.size(); x++){

                if (lsObjetos.get(x) instanceof  Filtro_Cliente) {

                    return (Filtro_Cliente) lsObjetos.get(x);

                }

            }

            return null;
        }

        public Filtro_Produto getFiltroProduto(){

            for(int x = 0; x <= lsObjetos.size(); x++){

                if (lsObjetos.get(x) instanceof  Filtro_Produto) {

                    return (Filtro_Produto) lsObjetos.get(x);

                }

            }

            return null;
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

            if (lsObjetos.get(position) instanceof Filtro_Data) {

                retorno = ITEM_VIEW_DATA;

            }


            if (lsObjetos.get(position) instanceof Filtro_Cliente) {

                retorno = ITEM_VIEW_CLIENTE;

            }

            if (lsObjetos.get(position) instanceof Filtro_Categoria) {

                retorno = ITEM_VIEW_CATEGORIA;

            }

            if (lsObjetos.get(position) instanceof Filtro_Marca) {

                retorno = ITEM_VIEW_MARCA;

            }

            if (lsObjetos.get(position) instanceof Filtro_Produto) {

                retorno = ITEM_VIEW_PRODUTO;

            }

            if (lsObjetos.get(position) instanceof Rel_Visao) {

                retorno = ITEM_VIEW_VISAO;

            }

            if (lsObjetos.get(position) instanceof Rel_Topicos) {

                retorno = ITEM_VIEW_TOPICO;

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


                        case ITEM_VIEW_DATA:

                            convertView = inflater.inflate(R.layout.parametros_data_row, null);

                            break;


                        case ITEM_VIEW_CLIENTE:

                            convertView = inflater.inflate(R.layout.parametros_cliente_row, null);

                            break;

                        case ITEM_VIEW_CATEGORIA:

                            convertView = inflater.inflate(R.layout.parametros_categoria_row, null);

                            break;

                        case ITEM_VIEW_MARCA:

                            convertView = inflater.inflate(R.layout.parametros_marcas_row, null);

                            break;

                        case ITEM_VIEW_PRODUTO:

                            convertView = inflater.inflate(R.layout.parametros_produto_row, null);

                            break;

                        case ITEM_VIEW_VISAO:

                            convertView = inflater.inflate(R.layout.parametros_visao_row, null);

                            break;

                        case ITEM_VIEW_TOPICO:

                            convertView = inflater.inflate(R.layout.parametros_topicos_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_DATA: {

                        final Filtro_Data obj = (Filtro_Data) lsObjetos.get(pos);

                        TextView lbl_dtinicial_720 = (TextView) convertView.findViewById(R.id.lbl_dtinicial_720);

                        TextView lbl_dtfinal_720   = (TextView) convertView.findViewById(R.id.lbl_dtfinal_720);

                        TextView txt_dtinicial_720 = (TextView) convertView.findViewById(R.id.txt_dtinicial_720);

                        TextView txt_dtfinal_720   = (TextView) convertView.findViewById(R.id.txt_dtfinal_720);

                        CheckBox cb_filtro_720     = (CheckBox) convertView.findViewById(R.id.cb_filtro_720);

                        cb_filtro_720.setChecked(obj.getAtivo());

                        cb_filtro_720.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setAtivo(((CheckBox) v).isChecked());

                                if (((CheckBox) v).isChecked()){

                                    obj.setDatainicial(App.getInicialMesddmmyyyy());
                                    obj.setDatafinal(App.getFinalMesddmmyyyy());

                                } else {

                                    obj.setDatainicial("");
                                    obj.setDatafinal("");
                                }


                                adapter.notifyDataSetChanged();

                            }
                        });

                        txt_dtinicial_720.setText(obj.getDatainicial());

                        txt_dtfinal_720.setText(obj.getDatafinal());

                        lbl_dtinicial_720.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                final Dialog dialog = new Dialog(v.getContext());

                                dialog.setContentView(R.layout.gettexttopadrao);

                                dialog.setTitle("");

                                final Button confirmar = (Button) dialog.findViewById(R.id.btn_570_ok);
                                final Button cancelar = (Button) dialog.findViewById(R.id.btn_570_can);
                                final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_570_texto1);
                                final EditText edCampo = (EditText) dialog.findViewById(R.id.edCampo_570);
                                final TextView tvCONTADOR = (TextView) dialog.findViewById(R.id.lbl_570_contador);
                                final TextView tvMensagem = (TextView) dialog.findViewById(R.id.txt_570_error);

                                tvMensagem.setText("");

                                edCampo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

                                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                                edCampo.addTextChangedListener(Mask.insert("##/##/####", edCampo));

                                edCampo.setText(obj.getDatainicial());

                                try {

                                    edCampo.setSelection(0,edCampo.getText().toString().length());

                                    edCampo.addTextChangedListener(new TextWatcher() {

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }

                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            try {

                                                Integer com = s.toString().trim().length();

                                                tvCONTADOR.setText(String.valueOf(com));


                                            } catch (Exception e) {

                                                Log.i("SAV", e.getMessage());

                                            }

                                        }
                                    });


                                } catch (Exception e) {


                                }


                                tvtexto1.setText("Digite A Data Inicial:");

                                confirmar.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {

                                        try {

                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                                            sdf.setLenient(false);

                                            sdf.parse(edCampo.getText().toString());


                                        } catch (java.text.ParseException e) {


                                            tvMensagem.setText("Data Inválida !!");


                                            return;

                                        }


                                        dialog.dismiss();

                                        obj.setDatainicial(edCampo.getText().toString());

                                        obj.setAtivo(true);

                                        adapter.notifyDataSetChanged();

                                    }


                                });

                                cancelar.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }

                                });


                                dialog.show();



                            }
                        });

                        lbl_dtfinal_720.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                final Dialog dialog = new Dialog(v.getContext());

                                dialog.setContentView(R.layout.gettexttopadrao);

                                dialog.setTitle("Digite Data Final: ");

                                final Button confirmar = (Button) dialog.findViewById(R.id.btn_570_ok);
                                final Button cancelar = (Button) dialog.findViewById(R.id.btn_570_can);
                                final TextView tvtexto1 = (TextView) dialog.findViewById(R.id.txt_570_texto1);
                                final EditText edCampo = (EditText) dialog.findViewById(R.id.edCampo_570);
                                final TextView tvCONTADOR = (TextView) dialog.findViewById(R.id.lbl_570_contador);
                                final TextView tvMensagem = (TextView) dialog.findViewById(R.id.txt_570_error);

                                tvMensagem.setText("");

                                edCampo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

                                edCampo.setRawInputType(InputType.TYPE_CLASS_PHONE);

                                edCampo.addTextChangedListener(Mask.insert("##/##/####", edCampo));

                                edCampo.setText(obj.getDatafinal());

                                try {

                                    edCampo.setSelection(0,edCampo.getText().toString().length());

                                    edCampo.addTextChangedListener(new TextWatcher() {

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }

                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            try {

                                                Integer com = s.toString().trim().length();

                                                tvCONTADOR.setText(String.valueOf(com));


                                            } catch (Exception e) {

                                                Log.i("SAV", e.getMessage());

                                            }

                                        }
                                    });


                                } catch (Exception e) {


                                }


                                tvtexto1.setText("");

                                confirmar.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {

                                        try {

                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                                            sdf.setLenient(false);

                                            sdf.parse(edCampo.getText().toString());

                                            Date emissao  = sdf.parse(obj.getDatainicial());

                                            if (sdf.parse(edCampo.getText().toString()).compareTo(emissao) < 0){

                                                tvMensagem.setText("Data Deverá Ser Posterior A Data Inicial ");

                                                return;

                                            }


                                        } catch (java.text.ParseException e) {


                                            tvMensagem.setText("Data Inválida !!");


                                            return;

                                        }


                                        dialog.dismiss();

                                        obj.setDatafinal(edCampo.getText().toString());

                                        obj.setAtivo(true);

                                        adapter.notifyDataSetChanged();

                                    }


                                });

                                cancelar.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }

                                });


                                dialog.show();

                            }
                        });




                        break;

                    }

                    case ITEM_VIEW_CLIENTE: {

                        final Filtro_Cliente obj = (Filtro_Cliente) lsObjetos.get(pos);

                        final CheckBox cb_cliente_721   =  (CheckBox) convertView.findViewById(R.id.cb_filtro_721);

                        final Switch swClienteRede_721  =  (Switch) convertView.findViewById(R.id.swClienteRede_721);

                        final TextView lbl_codcli_721   =  (TextView) convertView.findViewById(R.id.lbl_codcli_721);

                        final TextView txt_codcli_721   =  (TextView) convertView.findViewById(R.id.txt_codcli_721);

                        final TextView txt_razao_721    =  (TextView) convertView.findViewById(R.id.txt_razao_721);


                        cb_cliente_721.setChecked(obj.getAtivo());

                        if (obj.getTipo().equals("R")) swClienteRede_721.setChecked(true);
                        else swClienteRede_721.setChecked(false);

                        txt_codcli_721.setText(obj.getCodigo());

                        txt_razao_721.setText(obj.getDescricao());

                        swClienteRede_721.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                if (isChecked) {

                                    lbl_codcli_721.setText("Cód. REDE");

                                    obj.setTipo("R");

                                } else {

                                    lbl_codcli_721.setText("Cód. CLIENTE");

                                    obj.setTipo("C");

                                }

                                obj.setAtivo(false);

                                adapter.notifyDataSetChanged();
                            }
                        });


                        lbl_codcli_721.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if ((!swClienteRede_721.isChecked())){
                                    Intent i = new Intent(context, Help20Activity.class);
                                    Bundle params = new Bundle();
                                    params.putString("ARQUIVO", "CLIENTE");
                                    params.putString("TITULO", "CADASTRO DE CLIENTES");
                                    params.putString("MULTICHOICE", "N");
                                    params.putString("ALIAS", "CLIENTE");
                                    params.putString("ALIASVALUES", "");
                                    i.putExtras(params);
                                    startActivityForResult(i, HelpInformation.HelpCliente);
                                } else {
                                    Intent i = new Intent(context, Help20Activity.class);
                                    Bundle params = new Bundle();
                                    params.putString("ARQUIVO", "REDE");
                                    params.putString("TITULO", "CADASTRO DE REDES");
                                    params.putString("MULTICHOICE", "N");
                                    params.putString("ALIAS", "REDE");
                                    params.putString("ALIASVALUES", "");
                                    i.putExtras(params);
                                    startActivityForResult(i, HelpInformation.HelpRede);
                                }


                            }
                        });


                        cb_cliente_721.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setAtivo(((CheckBox) v).isChecked());

                                adapter.notifyDataSetChanged();
                            }
                        });
                        break;

                    }

                    case ITEM_VIEW_CATEGORIA: {

                        final CheckBox cb_filtro_722   = (CheckBox) convertView.findViewById(R.id.cb_filtro_722);

                        final Filtro_Categoria obj = (Filtro_Categoria) lsObjetos.get(pos);

                        final CheckBox rb_categorias_arroz_722 = (CheckBox) convertView.findViewById(R.id.rb_categorias_arroz_722);

                        final CheckBox rb_categorias_feijao_722 = (CheckBox) convertView.findViewById(R.id.rb_categorias_feijao_722);

                        final CheckBox rb_categorias_jb_722 = (CheckBox) convertView.findViewById(R.id.rb_categorias_jb_722);

                        final CheckBox rb_categorias_azeite_722 = (CheckBox) convertView.findViewById(R.id.rb_categorias_azeite_722);

                        final CheckBox rb_categorias_pescado_722 = (CheckBox) convertView.findViewById(R.id.rb_categorias_pescado_722);


                        rb_categorias_feijao_722.setChecked(obj.getStatusByCategoria("3.01"));

                        rb_categorias_arroz_722.setChecked(obj.getStatusByCategoria("3.02"));

                        rb_categorias_azeite_722.setChecked(obj.getStatusByCategoria("3.20"));

                        rb_categorias_jb_722.setChecked(obj.getStatusByCategoria("3.21"));

                        rb_categorias_pescado_722.setChecked(obj.getStatusByCategoria("3.22"));

                        cb_filtro_722.setChecked(obj.getAtivo());

                        cb_filtro_722.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setAtivo(((CheckBox) v).isChecked());

                            }
                        });



                        rb_categorias_feijao_722.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusByCategoria("3.01",((CheckBox) v).isChecked());

                            }
                        });

                        rb_categorias_arroz_722.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusByCategoria("3.02",((CheckBox) v).isChecked());



                            }
                        });

                        rb_categorias_azeite_722.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusByCategoria("3.20",((CheckBox) v).isChecked());

                            }
                        });

                        rb_categorias_jb_722.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusByCategoria("3.21",((CheckBox) v).isChecked());
                            }
                        });

                        rb_categorias_pescado_722.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusByCategoria("3.22",((CheckBox) v).isChecked());

                            }
                        });

                        break;

                    }


                    case ITEM_VIEW_MARCA: {

                        final Filtro_Marca obj = (Filtro_Marca) lsObjetos.get(pos);

                        final CheckBox cb_filtro_723   = (CheckBox) convertView.findViewById(R.id.cb_filtro_723);
                        final Spinner sp_marcas_723    = (Spinner) convertView.findViewById(R.id.sp_marcas_723);

                        cb_filtro_723.setChecked(obj.isAtivo());

                        sp_marcas_723.setEnabled(true);

                        marcasadapter = new defaultAdapter(context, R.layout.choice_default_row, obj.getlsMarcas(), "Marcas", true);

                        sp_marcas_723.setAdapter(marcasadapter);

                        sp_marcas_723.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                        {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (! (marcasadapter.isInicializacao())) {

                                    marcasadapter.setEscolha(position);

                                    Object lixo = sp_marcas_723.getSelectedItem();

                                    obj.setCodigo(((String[]) lixo)[0]);

                                    obj.setDescricao(((String[]) lixo)[1]);

                                    adapter.notifyDataSetChanged();
                                }

                                marcasadapter.setIsInicializacao(false);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

                        marcasadapter.setIsInicializacao(true);

                        sp_marcas_723.setSelection(obj.getIndice());

                        cb_filtro_723.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setAtivo(((CheckBox)v).isChecked());

                            }
                        });

                        break;

                    }

                    case ITEM_VIEW_PRODUTO: {

                        final Filtro_Produto obj = (Filtro_Produto) lsObjetos.get(pos);

                        final CheckBox cb_filtro_724   = (CheckBox) convertView.findViewById(R.id.cb_filtro_724);

                        final TextView lbl_produto_inicial_724 = (TextView) convertView.findViewById(R.id.lbl_produto_inicial_724);

                        final TextView lbl_produto_final_724   = (TextView) convertView.findViewById(R.id.lbl_produto_final_724);

                        cb_filtro_724.setChecked(obj.getAtivo());

                        cb_filtro_724.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setAtivo(((CheckBox)v).isChecked());

                            }
                        });

                        TextView txt_produto_inicial_724 = (TextView) convertView.findViewById(R.id.txt_produto_inicial_724);

                        TextView txt_produto_final_724   = (TextView) convertView.findViewById(R.id.txt_produto_final_724);

                        txt_produto_inicial_724.setText(obj.getCodigoInicial().trim()+"-"+obj.getDescriInicial());

                        txt_produto_final_724.setText(obj.getCodigofinal().trim()+"-"+obj.getDescrifinal());



                        lbl_produto_inicial_724.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                HELP = "INICIAL";

                                Intent i = new Intent(context, Help20Activity.class);
                                Bundle params = new Bundle();
                                params.putString("ARQUIVO", "PRODUTO");
                                params.putString("TITULO", "CADASTRO DE PRODUTOS");
                                params.putString("MULTICHOICE", "N");
                                params.putString("ALIAS", "PRODUTO");
                                params.putString("ALIASVALUES", "");
                                i.putExtras(params);
                                startActivityForResult(i, HelpInformation.HelpProduto);


                            }
                        });

                        lbl_produto_final_724.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                HELP = "FINAL";

                                Intent i = new Intent(context, Help20Activity.class);
                                Bundle params = new Bundle();
                                params.putString("ARQUIVO", "PRODUTO");
                                params.putString("TITULO", "CADASTRO DE PRODUTOS");
                                params.putString("MULTICHOICE", "N");
                                params.putString("ALIAS", "PRODUTO");
                                params.putString("ALIASVALUES", "");
                                i.putExtras(params);
                                startActivityForResult(i, HelpInformation.HelpProduto);


                            }
                        });


                        break;

                    }

                    case ITEM_VIEW_VISAO: {

                        final Rel_Visao obj = (Rel_Visao) lsObjetos.get(pos);

                        final Spinner sp_visao_726 = (Spinner) convertView.findViewById(R.id.sp_visao_726);

                        sp_visao_726.setEnabled(true);

                        visaoadapter = new defaultAdapter(context, R.layout.choice_default_row, obj.getVisoes(), "Visões", true);

                        sp_visao_726.setAdapter(visaoadapter);

                        sp_visao_726.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                        {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (! (visaoadapter.isInicializacao())) {

                                    visaoadapter.setEscolha(position);

                                    Object lixo = sp_visao_726.getSelectedItem();

                                    obj.setOpcao(((String[]) lixo)[0]);

                                    adapter.notifyDataSetChanged();
                                }

                                visaoadapter.setIsInicializacao(false);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

                        visaoadapter.setIsInicializacao(true);

                        sp_visao_726.setSelection(obj.getIndice());

                        break;

                    }

                    case ITEM_VIEW_TOPICO: {

                        final Rel_Topicos obj       = (Rel_Topicos) lsObjetos.get(pos);

                        CheckBox rb_data_725        = (CheckBox) convertView.findViewById(R.id.rb_data_725);

                        CheckBox rb_cliente_725     = (CheckBox) convertView.findViewById(R.id.rb_cliente_725);

                        CheckBox rb_categoria_725   = (CheckBox) convertView.findViewById(R.id.rb_categoria_725);

                        CheckBox rb_marca_725       = (CheckBox) convertView.findViewById(R.id.rb_marca_725);

                        CheckBox rb_produto_725     = (CheckBox) convertView.findViewById(R.id.rb_produto_725);


                        rb_data_725.setChecked(obj.getStatusOpcao("DT"));
                        rb_cliente_725.setChecked(obj.getStatusOpcao("CL"));
                        rb_categoria_725.setChecked(obj.getStatusOpcao("CT"));
                        rb_marca_725.setChecked(obj.getStatusOpcao("MC"));
                        rb_produto_725.setChecked(obj.getStatusOpcao("PR"));

                        rb_data_725.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusOpcao("DT",((CheckBox) v).isChecked());
                            }
                        });

                        rb_cliente_725.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusOpcao("CL",((CheckBox) v).isChecked());
                            }
                        });

                        rb_categoria_725.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusOpcao("CT",((CheckBox) v).isChecked());
                            }
                        });

                        rb_marca_725.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusOpcao("MC",((CheckBox) v).isChecked());
                            }
                        });

                        rb_produto_725.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                obj.setStatusOpcao("PR",((CheckBox) v).isChecked());
                            }
                        });

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

    private class defaultAdapter extends ArrayAdapter {

        private int escolha = 0;

        private List<String[]> lista;

        private String label;

        private boolean valido;

        private boolean isInicializacao = true;

        private defaultAdapter(Context context, int textViewResourceId, List<String[]> objects,String label, boolean valido) {

            super(context, textViewResourceId, objects);

            this.lista = objects;

            this.label = label;

            this.valido = valido;
        }

        public String getOpcao(int pos){


            if ( (pos < this.lista.size() )){


                return lista.get(pos)[0];

            }

            return "";

        }

        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }

        public View getOpcoesView(int position, View convertView, ViewGroup parent) {

            String col01 = lista.get(position)[0];
            String col02 = lista.get(position)[1];

            LayoutInflater inflater =  getLayoutInflater();

            View layout = inflater.inflate(R.layout.choice_01_row, parent, false);

            TextView tvcol01 = (TextView) layout.findViewById(R.id.txt_col01_898);

            TextView tvcol02 = (TextView) layout.findViewById(R.id.txt_col02_898);

            tvcol01.setTextSize(14f);

            tvcol02.setTextSize(14f);


            tvcol01.setText(col01);

            tvcol02.setText(col02);

            tvcol01.setTextColor(Color.RED);

            tvcol02.setTextColor(Color.RED);

            ImageView img = (ImageView) layout.findViewById(R.id.im_flag_898);

            if (position == escolha) {

                img.setVisibility(View.VISIBLE);

                tvcol01.setTextColor(Color.BLACK);

                tvcol02.setTextColor(Color.BLACK);

            }

            return layout;
        }

        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            String obj = lista.get(position)[1];

            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

            TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

            tvlabel.setText(this.label);

            if (this.label.isEmpty()){

                tvlabel.setVisibility(View.GONE);

            }

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

            tvOpcao.setText(obj);

            ImageView img = (ImageView) layout.findViewById(R.id.img_22);

            if (valido){

                img.setVisibility(View.GONE);

            }   else
            {
                img.setImageResource(R.drawable.erro_20_vermelho);
            }

            if (position == escolha) {

                tvOpcao.setTextColor(getResources().getColor(R.color.dark_blue));

                tvOpcao.setGravity(Gravity.CENTER);

            }

            return layout;
        }

        // Mostra as Opções
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            return getOpcoesView(position, convertView, parent);

        }

        // Mostra o item selecionado
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getEscolhaView(position, convertView, parent);

        }

        public boolean isInicializacao() {
            return isInicializacao;
        }

        public void setIsInicializacao(boolean isInicializacao) {
            this.isInicializacao = isInicializacao;
        }

        public void setValido(boolean valido) {
            this.valido = valido;
        }
    }
}
