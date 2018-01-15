package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Motivo;

/**
 * Created by Falconi on 07/10/2016.
 */
public class MotivoDAO extends DAO2 implements IDao2<Motivo> {

    private final String TAG = "MOTIVODAO";

    private String whereClausePrimary = " tipo = ? and codigo = ? ";

    public MotivoDAO() throws Exception {
        super("MOTIVO","dbuser");
    }


    @Override
    public Motivo insert(Motivo obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getTIPO(),obj.getCODIGO()},null, null, null);

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
    public Boolean Update(Motivo obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getTIPO(),obj.getCODIGO()});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return retorno == 0 ? false : true;

    }

    @Override
    public Motivo cursorToObj(Cursor cursor) {
        Motivo obj = null;

        try {
            obj = new Motivo(

                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)



                    );
        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<Motivo> getAll() {
        Cursor cursor = null;

        ArrayList<Motivo> result = new ArrayList<>();

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
    public Motivo seek(String[] values) {
        Motivo obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1]},null, null, null);

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

}

