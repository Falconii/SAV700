package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.AGENDADATA;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.regrasdenegocio.AgeByData;
import br.com.brotolegal.savdatabase.regrasdenegocio.ResumoAgendamento;
import br.com.brotolegal.savdatabase.util.AgendamentoController;

/**
 * Created by Falconi on 27/09/2016.
 */
public class AgendamentoDAO extends DAO2 implements IDao2<Agendamento> {

    private final String TAG = "AGENDAMENTO";

    private String whereClausePrimary = " ID = ? ";

    public AgendamentoDAO() throws Exception {

        super("AGENDAMENTO","dbuser");

    }


    @Override
    public Agendamento insert(Agendamento obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getID()},null, null, null);

                cursor.moveToFirst();

                obj =  cursorToObj(cursor);


            } else {

                obj = null;

            }

        }

        catch(Exception e){

            Log.i("SAV", e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public Integer Delete(String[] values) {
        int regs = 0;

        try {

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary,new String[] {values[0]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;
    }

    @Override
    public Boolean Update(Agendamento obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getID()});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;
    }

    @Override
    public Agendamento cursorToObj(Cursor cursor) {
        Agendamento obj = null;
        try {
            obj = new Agendamento(

                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20)

            );

            //Verifica se tem complemento
            if (cursor.getColumnCount()> getRegistro().getColumn().length ){

                int coluna = getRegistro().getColumn().length;

                obj.set_RAZAO(cursor.getString(coluna++));
                obj.set_DESCRICAOMOTIVONAOVENDA(cursor.getString(coluna++));
                obj.set_DESCRICAOMOTIVONAOVISITA(cursor.getString(coluna++));

            }
        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;
    }

    @Override
    public List<Agendamento> getAll() {
        Cursor cursor = null;

        List<Agendamento> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    result.add(cursorToObj(cursor));

                    cursor.moveToNext();

                }
            }


        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;
    }

    @Override
    public Agendamento seek(String[] values) {
        Agendamento obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0]},null, null, null);

            if (cursor.moveToFirst())
            {

                obj =  cursorToObj(cursor);

            } else {

                //retorno null
            }
        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;
    }

    public List<Agendamento> getAllByCliente(String codigo, String loja){

        Cursor cursor = null;

        List<Agendamento> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where agendamento.cliente = '"+codigo+"' and agendamento.loja = '"+loja+"'", null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    result.add(cursorToObj(cursor));

                    cursor.moveToNext();

                }
            }


        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;

    }

    public List<Agendamento> getAllByByStatus(String codigo, String loja, String Status){

        Cursor cursor = null;

        List<Agendamento> result = new ArrayList<>();

        try {

            String sql = "";

            if (codigo.isEmpty()){

                sql = "select * from " + getRegistro().getFileName() + " where agendamento.situacao = '"+Status+"' ";


            } else {

                sql = "select * from " + getRegistro().getFileName() + " where agendamento.cliente = '"+codigo+"' and agendamento.loja = '"+loja+"' and agendamento.situacao = '"+Status+"' ";

            }

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    result.add(cursorToObj(cursor));

                    cursor.moveToNext();

                }
            }


        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;

    }

    public List<Agendamento> getAllAvulsoToSinc(){

        Cursor cursor = null;

        List<Agendamento> result = new ArrayList<>();

        try {

            String sql = "";

            sql =   " select agendamento.*,"+
                    " ifnull(cliente.razaopa,''),"+
                    " ifnull((select motivo.descricao from motivo where trim(motivo.tipo) = 'NAOVENDA'  and motivo.codigo = agendamento.motivonvenda limit 1),''),"+
                    " ifnull((select motivo.descricao from motivo where trim(motivo.tipo) = 'NAOVISITA' and motivo.codigo = agendamento.motivonvisita limit 1),'') "+
                    " from " + getRegistro().getFileName() +
                    " left join cliente on cliente.codigo = agendamento.cliente and cliente.loja = agendamento.loja  "+
                    " where agendamento.situacao = 'T' and trim(agendamento.mobile) = '' and trim(agendamento.mobile) = '' ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    Agendamento age = cursorToObj(cursor);

                    age.Virtuais(
                            cursor.getString(21),
                            cursor.getString(22),
                            cursor.getString(23)
                    );

                    result.add(age);

                    cursor.moveToNext();

                }
            }


        }catch (Exception e) {

            new Exception(e.getMessage());

        }

        return result;

    }

    public ResumoAgendamento getAllByByStatus(String Status){

        Cursor cursor = null;

        ResumoAgendamento resumoAgendamento = new ResumoAgendamento("",0);

        List<Agendamento> result = new ArrayList<>();

        try {

            String sql = "";

            sql = "select count(*) as soma from " + getRegistro().getFileName() + " where agendamento.situacao = '"+Status+"'  and trim(agendamento.mobile) = '' ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                resumoAgendamento.setMensagem("");
                resumoAgendamento.setQtd(0);

            } else {

                while (!(cursor.isAfterLast())) {

                    resumoAgendamento.setMensagem("");
                    resumoAgendamento.setQtd(cursor.getInt(0));

                    cursor.moveToNext();

                }
            }


        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return resumoAgendamento;

    }

    public List<AGENDADATA> getAllByData(){

        Cursor cursor = null;

        List<AGENDADATA> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select agendamento.data,count(*) AS total  from " + getRegistro().getFileName() +
                    " group by agendamento.data "+
                    " order by agendamento.data ", null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    result.add(new AGENDADATA(cursor.getString(0),cursor.getInt(1)));

                    cursor.moveToNext();

                }
            }


        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;

    }

    public List<Agendamento> getAByData(String Data){

        Cursor cursor = null;

        List<Agendamento> result = new ArrayList<>();

        String sql = "";

        try {

            sql = "select agendamento.*, cliente.razaopa ,ifnull (nven.descricao,'') ,ifnull (nvis.descricao,'') "+
                     "from agendamento " +
                     "left join cliente on cliente.codigo = agendamento.cliente and cliente.loja = agendamento.loja " +
                     "left join motivo nven on nven.tipo = 'NAONVENDA'  and agendamento.motivonvenda =  nven.codigo " +
                     "left join motivo nvis on nvis.tipo = 'NAONVISITA'  and agendamento.motivonvisita =  nvis.codigo " +
                     "where data = '"+Data+"' order by agendamento.hora";


            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    result.add(cursorToObj(cursor));

                    cursor.moveToNext();

                }
            }


        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;

    }

    public List<AgeByData> getAgeByData(){

        Cursor cursor = null;

        List<AgeByData> result = new ArrayList<>();

        String sql = "";

        try {

            sql = "select data,situacao,count(*) from agendamento group by data,situacao order by data,situacao";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    result.add(new AgeByData(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getInt(2)));

                    cursor.moveToNext();

                }
            }


        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;

    }

}
