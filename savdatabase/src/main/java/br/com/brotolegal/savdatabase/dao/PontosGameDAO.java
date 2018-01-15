package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.PontosGame;

/**
 * Created by Falconi on 05/07/2016.
 */
public class PontosGameDAO extends DAO2 implements IDao2<PontosGame> {

    private final String TAG = "PONTOSGAME";

    private String whereClausePrimary = " u12_codu10 = ? and u12_codu11 = ? ";

    public PontosGameDAO() throws Exception {
        super("PONTOSGAME","dbuser");
    }


    @Override
    public PontosGame insert(PontosGame obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getU12_CODU10(),obj.getU12_CODU11()},null, null, null);

                cursor.moveToFirst();

                obj =  cursorToObj(cursor);


            } else {

                obj = null;

            }

        }

        catch(Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;


    }

    @Override
    public Integer Delete(String[] values) {
        int regs = 0;

        try {

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0],values[1]});

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Boolean Update(PontosGame obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getU12_CODU10(), obj.getU12_CODU11()});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return retorno == 0 ? false : true;

    }

    @Override
    public PontosGame cursorToObj(Cursor cursor) {
        PontosGame obj = null;

        try {
            obj = new PontosGame(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7)

            );

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;
    }

    @Override
    public List<PontosGame> getAll() {
        Cursor cursor = null;

        List<PontosGame> result;

        result = new ArrayList<PontosGame>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<PontosGame>();

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
    public PontosGame seek(String[] values) {
        PontosGame obj = null;

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

    public List<PontosGame> getExtratoByCodigo(String campanha, String codigo){

        Cursor cursor = null;

        List<PontosGame> result;

        result = new ArrayList<PontosGame>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where u12_codu10 = '"+campanha+"' and u12_codu11 = '"+codigo+"' " , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<PontosGame>();

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
}
