package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.dao.PedidoDetMbDAO;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;
import br.com.brotolegal.savdatabase.regrasdenegocio.ErroPedidos;

public class showErrosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String  PEDIDO       = "";
    private List<Object> lsLista = null;
    private Adapter      adapter;
    private ListView     lv;
    protected Map<String,String[]> lsAcoes  = new TreeMap<String, String[] >();
    DecimalFormat format_02 = new DecimalFormat(",##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_erros);

        try {

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                PEDIDO = params.getString("PEDIDO");

            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }


        toolbar = (Toolbar) findViewById(R.id.tb_showerros);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("ERROS DO PEDIDO: "+PEDIDO);
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.menu_pedido_geral);

        lv = (ListView) findViewById(R.id.lv_erros);

        LoadAcoes();

        LoadErros();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_show_erros_pedido, menu);

        return true ;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_consulta_erros_voltar:

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public  void finish(){

        lsLista = new ArrayList<Object>();

        super.finish();

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

        lsLista = new ArrayList<Object>();

    }



    private void LoadErros(){

        lsLista = new ArrayList<>();

        lsLista.add("CABEC");

        String cliente = "";

        String loja    = "";

        try {

            {


                //Cabeçalho

                PedidoCabMbDAO dao = new PedidoCabMbDAO();

                dao.open();

                PedidoCabMb ped = dao.seek(new String[]{PEDIDO});

                dao.close();

                if (!(ped == null)) {

                    ErroPedidos erro = new ErroPedidos();

                    if (ped.getMENSAGEM().trim().equals("Erro Nos Produtos !!!")){

                        erro.setErrorCode("999");

                        erro.setMensagem(ped.getMENSAGEM().toUpperCase());

                    } else {

                        erro.setErrorCode(ped.getMENSAGEM().substring(0,3));

                        erro.setMensagem(ped.getMENSAGEM().substring(4).toUpperCase());

                    }

                    erro.setLocal("C");

                    erro.setStatus(ped.get_Status());

                    lsLista.add(erro);

                    cliente = ped.getCODIGOFAT();

                    loja    = ped.getLOJAFAT();

                } else {

                    throw new Exception("Pedido Não Encontrado !");

                }

            }
            //detalhe
            {
                PedidoDetMbDAO dao = new PedidoDetMbDAO();

                dao.open();

                List<PedidoDetMB_fast> itens = dao.getDetalheFast(new String[] {PEDIDO,cliente,loja});

                dao.close();

                for(PedidoDetMB_fast item : itens){

                    if (item.getSTATUS().equals("9")){

                        ErroPedidos erro = new ErroPedidos();
                        erro.setItem(item.getITEM());
                        erro.setLocal("D");
                        erro.setErrorCode(item.getMENSAGEM().substring(0,3));
                        erro.setMensagem(item.getMENSAGEM().substring(4).toUpperCase()+"\n"+
                                        "QTD: "+ format_02.format(item.getQTD())+
                                        " P.VENDA: "+format_02.format(item.getPRCVEN())+
                                        " TOTAL: "+format_02.format(item.getTOTAL()));
                        erro.setCodprod(item.getPRODUTO());
                        erro.setDescprod(item.get_Produto());
                        erro.setQtd(item.getQTD());
                        erro.setPrecven(item.getPRCVEN());
                        erro.setTotal(item.getTOTAL());

                        lsLista.add(erro);

                    }

                }
            }
        } catch (Exception e){

            lsLista.clear();
            lsLista.add("CABEC");
            lsLista.add( new NoData(e.getMessage()));

        }


        adapter = new Adapter(this,lsLista);

        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    private void LoadAcoes(){

        lsAcoes.clear();

        lsAcoes.put("007", new String[] { "Verifique com a TI. Cliente pode ter sido excluido.","T"});
        lsAcoes.put("009", new String[] { "Verifique com o apoio. ","A"});
        lsAcoes.put("010", new String[] { "Altere o cliente entrega do pedido.","V"});
        lsAcoes.put("011", new String[] { "Cliente ainda não foi liberado para venda","V"});
        lsAcoes.put("012", new String[] { "Corrija a data de entrega.","V"});
        lsAcoes.put("013", new String[] { "Corrija a data de entrega.","V"});
        lsAcoes.put("031", new String[] { "Altere o pedido ou exclua-o","V"});
        lsAcoes.put("032", new String[] { "Altere o pedido ou exclua-o","V"});
        lsAcoes.put("033", new String[] { "Altere o pedido ou exclua-o","V"});
        lsAcoes.put("014", new String[] { "Apague o produto com problema","V"});
        lsAcoes.put("015", new String[] { "Apague o produto com problema","V"});
        lsAcoes.put("016", new String[] { "Verifique com o apoio. ","A"});
        lsAcoes.put("017", new String[] { "Verifique com o apoio. ","A"});
        lsAcoes.put("018", new String[] { "Atualize a tabela de preços, recicle e transmita o pedido novamente","V"});
        lsAcoes.put("019", new String[] { "Altere o acordo ou exclua o pedido.","V"});
        lsAcoes.put("020", new String[] { "Informe a quantidade.","V"});
        lsAcoes.put("021", new String[] { "Altere o acordo ou deixe sem acordo o produto","V"});
        lsAcoes.put("022", new String[] { "Corrija a quantidade de bonificação","V"});
        lsAcoes.put("023", new String[] { "Informe o motivo","V"});
        lsAcoes.put("024", new String[] { "Faça uma carga nova do simulador, recicle e vincule o simulador novamente","V"});
        lsAcoes.put("025", new String[] { "Corrija o preço de venda do produto ou exclua o item do pedido.","V"});

        lsAcoes.put("999", new String[] { "Veja Os Erros Dos Produtos Abaixo.","V"});

    }
    private void toast(String mensagem){

        Toast.makeText(showErrosActivity.this, mensagem, Toast.LENGTH_LONG).show();

    }

    //INNER CLASS


    private class Adapter extends BaseAdapter {


        private List<Object> lsObjetos;
        Context context;

        final int ITEM_VIEW_CABEC       = 0;
        final int ITEM_VIEW_HEADER       = 1;
        final int ITEM_VIEW_DETAIL       = 2;
        final int ITEM_VIEW_NO_DATA     = 3;
        final int ITEM_VIEW_COUNT       = 4;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos = pObjects;
            this.context = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        private String Cabec() {

            String retorno = "";

            retorno = "Olhe Os Erros Com Atenção.";

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

            if (lsObjetos.get(position) instanceof ErroPedidos) {

                if ( ((ErroPedidos) lsObjetos.get(position)).getLocal().equals("C")){

                    retorno = ITEM_VIEW_HEADER;

                } else {

                    retorno = ITEM_VIEW_DETAIL;

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


                        case ITEM_VIEW_HEADER:

                            convertView = inflater.inflate(R.layout.erros_row, null);

                            break;

                        case ITEM_VIEW_DETAIL:

                            convertView = inflater.inflate(R.layout.erros_row, null);

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

                    case ITEM_VIEW_HEADER: {

                        final ErroPedidos obj = (ErroPedidos) lsObjetos.get(pos);

                        TextView linha01 = (TextView) convertView.findViewById(R.id.tvLinha_01_20);

                        TextView linha02 = (TextView) convertView.findViewById(R.id.tvLinha_02_20);

                        TextView linha03 = (TextView) convertView.findViewById(R.id.tvLinha_03_20);

                        ImageView img_responsavel_20 = (ImageView) convertView.findViewById(R.id.img_responsavel_20);

                        linha01.setText("CABEÇALHO DO PEDIDO - SIT.: "+obj.getStatus());

                        linha02.setText(obj.getMensagem());

                        linha03.setText(lsAcoes.get(obj.getErrorCode())[0].toUpperCase());

                        switch (lsAcoes.get(obj.getErrorCode())[1].charAt(0)){

                            case 'V':

                                img_responsavel_20.setImageResource(R.drawable.vendedor);

                                break;

                            case 'A':

                                img_responsavel_20.setImageResource(R.drawable.apoio);

                                break;


                            case 'T':

                                img_responsavel_20.setImageResource(R.drawable.ti);

                                break;

                        }

                        break;
                    }

                    case ITEM_VIEW_DETAIL: {

                        final ErroPedidos obj = (ErroPedidos) lsObjetos.get(pos);

                        TextView linha01 = (TextView) convertView.findViewById(R.id.tvLinha_01_20);

                        TextView linha02 = (TextView) convertView.findViewById(R.id.tvLinha_02_20);

                        TextView linha03 = (TextView) convertView.findViewById(R.id.tvLinha_03_20);

                        ImageView img_responsavel_20 = (ImageView) convertView.findViewById(R.id.img_responsavel_20);

                        linha01.setText("ITEM: "+obj.getItem()+"-"+obj.getDescprod());

                        linha02.setText(obj.getErrorCode()+"-"+obj.getMensagem());

                        linha03.setText(lsAcoes.get(obj.getErrorCode())[0].toUpperCase());

                        switch (lsAcoes.get(obj.getErrorCode())[1].charAt(0)){

                            case 'V':

                                img_responsavel_20.setImageResource(R.drawable.vendedor);

                                break;

                            case 'A':

                                img_responsavel_20.setImageResource(R.drawable.apoio);

                                break;


                            case 'T':

                                img_responsavel_20.setImageResource(R.drawable.ti);

                                break;

                        }




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

                toast(e.getMessage());

            }

            return convertView;

        }


    }
}
