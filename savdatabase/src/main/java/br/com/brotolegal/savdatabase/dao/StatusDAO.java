package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Status;

/**
 * Created by Falconi on 13/07/2016.
 */
public class StatusDAO extends DAO2 implements IDao2<Status> {

    private final String TAG = "STATUSDAO";

    private String whereClausePrimary = "  ";

    public StatusDAO() throws Exception {

        super("STATUS");

    }


    @Override
    public Status insert(Status obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj);

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId == -1) {

                obj = null;

            }

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;
    }

    @Override
    public Integer Delete(String[] values) {
        int regs = 0;

        try {

            DeleteAll();

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return regs;
    }

    @Override
    public Boolean Update(Status obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, null, null);

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return retorno == 0 ? false : true;

    }

    @Override
    public Status cursorToObj(Cursor cursor) {
        Status obj = null;

        try {
            obj = new Status(
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
                    cursor.getString(11)
                    );

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;
    }

    @Override
    public List<Status> getAll() {
        Cursor cursor = null;

        ArrayList<Status> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName(), null);

            if (!cursor.moveToFirst()) {

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    result.add(cursorToObj(cursor));

                    cursor.moveToNext();

                }
            }


        } catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;
    }

    @Override
    public Status seek(String[] values) {

        Cursor cursor = null;

        ArrayList<Status> result = new ArrayList<>();

        Status obj = null;


        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName(), null);

            if (!cursor.moveToFirst()) {

                obj = null;

            } else {

                while (!(cursor.isAfterLast())) {

                    obj = (cursorToObj(cursor));

                    break;

                }
            }


        } catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return obj;
    }
}
