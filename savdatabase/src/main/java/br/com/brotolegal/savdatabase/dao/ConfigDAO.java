package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Config;

/**
 * Created by Falconi on 22/10/2015.
 */
public class ConfigDAO extends DAO2 implements IDao2<Config> {


    private final String TAG = "CONFIGDAO";

    private String whereClausePrimary = " codigo = ? ";

    private String whereClauseByDescricao = " descricao = ?  and codigo <> 0";

    public ConfigDAO() throws Exception {

        super("CONFIG");

    }

    @Override
    public Config insert(Config obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj);

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1) {

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(), whereClausePrimary, new String[]{obj.getCODIGO()}, null, null, null);

                cursor.moveToFirst();

                obj = cursorToObj(cursor);


            } else {

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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[]{values[0], values[1]});

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Boolean Update(Config obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getCODIGO()});

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return retorno == 0 ? false : true;

    }

    @Override
    public Integer DeleteAll() {
        int regs = 0;

        try {

            regs = getDataBase().delete(getRegistro().getFileName(), null, null);

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Config cursorToObj(Cursor cursor) {

        Config obj = null;

        try {
            obj = new Config(
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
                    cursor.getString(12)
                    );

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<Config> getAll() {

        Cursor cursor = null;

        ArrayList<Config> result = new ArrayList<>();

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
    public Config seek(String[] values) {

        Config obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(), whereClausePrimary, new String[]{values[0]}, null, null, null);

            if (cursor.moveToFirst()) {

                obj = cursorToObj(cursor);

            } else {

                obj = null;

            }
        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }


        return obj;

    }



    public Config seekByDescricao(String[] values) {

        Config obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(), whereClauseByDescricao, new String[]{values[0]}, null, null, null);

            if (cursor.moveToFirst()) {

                obj = cursorToObj(cursor);

            } else {

                obj = null;

            }
        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }


        return obj;

    }




    public List<Config> getConexoes() {

        Cursor cursor = null;

        ArrayList<Config> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where codigo <> '000' order by codigo ", null);

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
}
