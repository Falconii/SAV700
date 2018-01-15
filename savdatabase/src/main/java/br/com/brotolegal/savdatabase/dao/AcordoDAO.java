package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Acordo;

/**
 * Created by Falconi on 24/05/2016.
 */
public class AcordoDAO extends DAO2 implements IDao2<Acordo> {


    private final String TAG = "ACORDO";

    private String whereClausePrimary = " codigo = ? ";

    private int    pageSize = 50;

    public AcordoDAO() throws Exception {

        super("ACORDO","dbuser");

    }

    @Override
    public Acordo insert(Acordo obj) {

        Acordo registro = (Acordo) obj;

        Cursor cursor = null;

        ContentValues values = getContentValues(registro) ;

        try

        {
            String whereClause = " CODIGO = ? ";

            long insertId = getDataBase().insert(registro.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(registro.getFileName(), registro.getColumn(),whereClause,new String[] {registro.getCODIGO()},null, null, null);

                cursor.moveToFirst();

                registro =  (Acordo) cursorToObj(cursor);


            } else {

                registro = null;

            }

        }

        catch(Exception e){

            Log.i("SAV", e.getMessage());

            new Exception(e.getMessage());

        }

        return registro;
    }


    @Override
    public Integer Delete(String[] values)  {

        int regs = 0;

        try {

            regs = getDataBase().delete(getRegistro().getFileName(), " codigo = ? ", new String[] {values[0]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;


    }

    @Override
    public Boolean Update(Acordo obj){

    int retorno = 0;

    try {

        ContentValues values = getContentValues(obj);

        String whereClause = " codigo = ? ";

        retorno = getDataBase().update(getRegistro().getFileName(), values, whereClause, new String[] {obj.getCODIGO()});

    } catch (Exception e){

        new Exception(e.getMessage());

    }


    return retorno == 0 ? false : true;

    }

    @Override
    public Acordo cursorToObj(Cursor cursor) {

        Acordo acordo = null;

        try {
            acordo = new Acordo(

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
                    cursor.getString(20),
                    cursor.getFloat(21),
                    cursor.getFloat(22),
                    cursor.getString(23),
                    cursor.getString(24)


                    );

        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return acordo;

    }

    @Override
    public List<Acordo> getAll() {


        Cursor cursor = null;

        List<Acordo> result = new ArrayList<>();

        try {

            String sql = "";

            sql = "select * from " + getRegistro().getFileName() + " order by acordo.codigo DESC ";

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

    public List<Acordo> getAllBypage(int page) {


        Cursor cursor = null;

        List<Acordo> result = new ArrayList<>();

        try {

            String sql = "";

            sql = "select * from " + getRegistro().getFileName() + " order by acordo.codigo DESC ";

            //Acrescenta pagina

            sql += " limit 50 offset "+String.valueOf(page*50);

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

    @Override
    public Acordo seek(String[] values) {

        Acordo obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0]},null, null, null);

            if (cursor.moveToFirst())
            {

                obj =  cursorToObj(cursor);

            } else {

                obj = null;

            }
        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }


        return obj;

    }

    public int getNumberPages(){

        int page = -1;

        Cursor cursor = null;

        String sql = "";

        try {

            //select
            sql =  " select count(*)  from acordo ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()) {

                page = -1;

            } else {

                Log.i("ADAPTADOR","Total de Registros: "+String.valueOf(cursor.getInt(0)));

                page = (int)Math.round((cursor.getInt(0)/50));

            }

        } catch (Exception e) {

            page = -1;
        }

        return page;


    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
