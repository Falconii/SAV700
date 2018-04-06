package br.com.brotolegal.sav700;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.regrasdenegocio.AgeByData;

public class ViewAgendamentosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Adapter adapter;
    private List<Object> lista;
    private ListView lv ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_agendamentos);

        toolbar = (Toolbar) findViewById(R.id.tb_calendar_777);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Calendário Dos Agendamentos");
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.menu_calendar);

        lv = (ListView) findViewById(R.id.lv_agendamentos_777);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view_777));

        cv.setAgendamentos(getApontamentos());

        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {

            }

            @Override
            public void onDayPress(Date date) {

                String data = "";

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

                data = format.format(date);

                loadAgendamentos(data);

                toast("Agendamentos Carregados");

            }
        });

        loadAgendamentos(App.getHoje());

    }

    private List<CalendarView.objCalendarAge> getApontamentos() {

        List<CalendarView.objCalendarAge> age = new ArrayList<>();

        String Data = "";

        Integer ct1,ct2;

        try {

            AgendamentoDAO dao = new AgendamentoDAO();

            dao.open();

            List<AgeByData> ages = dao.getAgeByData();

            dao.close();

            if (ages != null){

                 Data = ages.get(0).getData();

                 ct1 = 0;

                 ct2 = 0;

                 for(AgeByData obj : ages){

                     if (!Data.equals(obj.getData())){

                         age.add(new CalendarView.objCalendarAge(Data,ct1,ct2));

                         Data = obj.getData();

                         ct1 = 0;

                         ct2 = 0;

                     }

                     if (obj.getSituacao().equals("E")) ct1 += obj.getContador();
                     else ct2 += obj.getContador();

                 }

                age.add(new CalendarView.objCalendarAge(Data,ct1,ct2));
            }

        }catch (Exception e){

            //nao faz nada

        }

        return age;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_calendar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:

                 finish();

                break;

            case R.id.action_calendar_sincronizar: {

                finish();

                break;

            }

            case R.id.action_calendar_voltar: {

                finish();

                break;

            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public  void finish(){

        lista = new ArrayList<Object>();

        super.finish();

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

        lista = new ArrayList<Object>();

    }


    private void loadAgendamentos(String data){

        try {

            lista = new ArrayList<>();

            lista.add("CABEC");

            AgendamentoDAO dao = new AgendamentoDAO();

            dao.open();

            lista.addAll(dao.getAByData(data));

            dao.close();

            if (lista.size() == 1) {

                lista.add(new NoData("Nenhum Agendamento Encontrado !!"));

            }

            adapter = new Adapter(ViewAgendamentosActivity.this, lista,data);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e){

            toast(e.getMessage());

        }


    }

    private void toast(String mensagem){

        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();


    }

    private class Adapter extends BaseAdapter {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");
        private List<Object> lsObjetos;
        Context context;

        final int ITEM_VIEW_CABEC       = 0;
        final int ITEM_VIEW_DETALHE     = 1;
        final int ITEM_VIEW_NO_DATA     = 2;
        final int ITEM_VIEW_COUNT       = 3;

        private LayoutInflater inflater;

        private String Data;

        public Adapter(Context context, List<Object> pObjects, String Data) {

            this.lsObjetos = pObjects;
            this.context   = context;
            this.Data      = Data;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof Agendamento) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total De Agendamentos: " + String.valueOf(qtd) + " Data: " + App.aaaammddToddmmaa(Data);

            return retorno;
        }

        public  void refresh(PedidoCabMb obj, int pos){

            this.lsObjetos.set(pos,obj);

            notifyDataSetChanged();

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

            if (lsObjetos.get(position) instanceof Agendamento) {

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

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.agenda_planejamento2_row, null);

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

                    case ITEM_VIEW_DETALHE: {

                        final Agendamento obj = (Agendamento) lsObjetos.get(pos);

                        TextView txt_id_402            = (TextView) convertView.findViewById(R.id.txt_id_402);
                        TextView txt_data_402          = (TextView) convertView.findViewById(R.id.txt_data_402);
                        TextView txt_hora_402          = (TextView) convertView.findViewById(R.id.txt_hora_402);
                        TextView txt_tipo_402          = (TextView) convertView.findViewById(R.id.txt_tipo_402);
                        TextView txt_situacao_402      = (TextView) convertView.findViewById(R.id.txt_situacao_402);
                        TextView txt_cliente_402       = (TextView) convertView.findViewById(R.id.txt_cliente_402);
                        TextView txt_pedido_402        = (TextView) convertView.findViewById(R.id.txt_pedido_402);
                        TextView txt_motivo_402        = (TextView) convertView.findViewById(R.id.txt_motivo_402);
                        TextView txt_obs_402           = (TextView) convertView.findViewById(R.id.txt_obs_402);

                        txt_id_402.setText("ID: "+obj.getID());
                        txt_data_402.setText("DATA: "+App.aaaammddToddmmaa(obj.getDATA()));
                        txt_hora_402.setText("HORA: "+obj.getHORA());
                        txt_tipo_402.setText("TIPO: "+obj.get_TIPO());
                        txt_situacao_402.setText("SIT.: "+obj.get_Situacao());
                        txt_pedido_402.setText("PEDIDO: "+obj.getMOBILE());
                        txt_motivo_402.setText("MOTIVO: "+obj._motivo());
                        txt_cliente_402.setText("CLIENTE: "+obj.getCLIENTE()+"-"+obj.getLOJA()+" "+obj.get_RAZAO());
                        txt_obs_402.setText("OBS: "+obj.getOBS());

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


        public Boolean CompararDatas(String DataInicial,String DataFinal, String Entrega){

            Boolean retorno = false;

            try {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date dtinicial = sdf.parse(DataInicial);
                Date dtfinal   = sdf.parse(DataFinal);
                Date dtentrega = sdf.parse(Entrega);

                if( (dtentrega.compareTo(dtinicial) >= 0) && (dtentrega.compareTo(dtfinal) <= 0) ){

                    retorno = true;

                } else {

                    retorno = false;

                }


            } catch (Exception e){

                retorno = false;

            }


            return retorno;
        }

        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }

    }

}
