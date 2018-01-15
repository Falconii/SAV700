package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Imposto;

/**
 * Created by Falconi on 05/07/2016.
 */
public class ImpostoDAO extends DAO2 implements IDao2<Imposto> {

    private final String TAG = "IMPOSTODAO";

    private String whereClausePrimary = "  estado = ? and grupo = ? and origem = ? ";

    public ImpostoDAO() throws Exception {
        super("IMPOSTO","dbuser");
    }

    @Override
    public Imposto insert(Imposto obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getESTADO(),obj.getGRUPO(),obj.getORIGEM()},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0],values[1],values[2]});

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return regs;
    }

    @Override
    public Boolean Update(Imposto obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getESTADO(),obj.getGRUPO(),obj.getORIGEM()});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return retorno == 0 ? false : true;

    }

    @Override
    public Imposto cursorToObj(Cursor cursor) {

        Imposto obj = null;

        try {
            obj = new Imposto(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3),
                    cursor.getFloat(4),
                    cursor.getFloat(5),
                    cursor.getFloat(6)
                    );

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<Imposto> getAll() {
        Cursor cursor = null;

        List<Imposto> result;

        result = new ArrayList<Imposto>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<Imposto>();

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
    public Imposto seek(String[] values) {

        Imposto obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1],values[2]},null, null, null);

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
