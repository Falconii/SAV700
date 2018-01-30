package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.DocCliente;

/**
 * Created by Falconi on 30/01/2018.
 */

public class DocClienteDAO extends DAO2 implements IDao2<DocCliente> {

    private final String TAG = "DOCCLIENTE";

    private String whereClausePrimary = " codigo = ?  and controle = ? ";

    public DocClienteDAO() throws Exception {

        super("DOCCLIENTE","dbuser");

    }

    @Override
    public DocCliente insert(DocCliente obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getCODIGO(),obj.getCONTROLE().toString()},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0],values[1]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Boolean Update(DocCliente obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getCODIGO(),obj.getCONTROLE().toString()});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;

    }

    @Override
    public DocCliente cursorToObj(Cursor cursor) {
        DocCliente obj = null;
        try {
            obj = new DocCliente(

                    cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)


                    );

        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;
    }

    @Override
    public List<DocCliente> getAll() {
        Cursor cursor = null;

        List<DocCliente> result = new ArrayList<>();

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
    public DocCliente seek(String[] values) {
        DocCliente obj = null;

        try{

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1]},null, null, null);

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
}
