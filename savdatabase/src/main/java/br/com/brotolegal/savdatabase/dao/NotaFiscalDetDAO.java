package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.entities.NotaFiscalCab;
import br.com.brotolegal.savdatabase.entities.NotaFiscalDet;

/**
 * Created by Falconi on 15/03/2017.
 */

public class NotaFiscalDetDAO extends DAO2 implements IDao2<NotaFiscalDet> {


    private final String TAG = "NOTAFISCALDET";

    private String whereClausePrimary = " filial = ? and serie = ? and notafiscal = ? and item = ? ";

    private String whereClauseNota = " filial = ? and serie = ? and notafiscal = ? ";


    public NotaFiscalDetDAO() throws Exception {

        super("NOTAFISCALDET","dbuser");

    }


    @Override
    public NotaFiscalDet insert(NotaFiscalDet obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getFILIAL(), obj.getSERIE(),obj.getNOTAFISCAL()},null, null, null);

                cursor.moveToFirst();

                obj =  (NotaFiscalDet) cursorToObj(cursor);


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

            regs = getDataBase().delete(getRegistro().getFileName(),whereClausePrimary, new String[] {values[0],values[1],values[2]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;


    }

    @Override
    public Boolean Update(NotaFiscalDet obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getFILIAL(), obj.getSERIE(),obj.getNOTAFISCAL()});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return retorno == 0 ? false : true;
    }

    @Override
    public NotaFiscalDet cursorToObj(Cursor cursor) {

        NotaFiscalDet obj = null;

        try {

            obj = new NotaFiscalDet(

                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getFloat(9),
                    cursor.getFloat(10),
                    cursor.getFloat(11),
                    cursor.getFloat(12),
                    cursor.getFloat(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20)


            );
        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;
    }

    @Override
    public List<NotaFiscalDet> getAll() {
        Cursor cursor = null;

        List<NotaFiscalDet> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select *  from " + getRegistro().getFileName(),null) ;

            if (!cursor.moveToFirst()) {

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    NotaFiscalDet obj = cursorToObj(cursor);

                    result.add(obj);

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
    public NotaFiscalDet seek(String[] values) {

        NotaFiscalDet obj = null;

        try{

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1],values[2],values[3]},null, null, null);

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

    public List<NotaFiscalDet> seekByNota(String[] values) {

        Cursor cursor = null;

        List<NotaFiscalDet> result = new ArrayList<>();

        try{

            cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClauseNota, new String[] {values[0],values[1],values[2]},null, null, null);

            if (!cursor.moveToFirst()) {

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    NotaFiscalDet obj = cursorToObj(cursor);

                    result.add(obj);

                    cursor.moveToNext();

                }
            }


        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return result;


    }

}
