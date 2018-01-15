package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.ContaCorrente;

/**
 * Created by Falconi on 09/05/2017.
 */

public class ContaCorrenteDAO extends DAO2  implements IDao2<ContaCorrente>{


    private final String TAG = "CONTACORRENTE";

    private String whereClausePrimary = " filial = ? and serie = ? and notafiscal = ? and codprod = ? ";

    public ContaCorrenteDAO() throws Exception {

        super("CONTACORRENTE","dbuser");

    }


    @Override
    public ContaCorrente insert(ContaCorrente obj) {

        ContaCorrente registro = obj;

        Cursor cursor = null;

        ContentValues values = getContentValues(registro);

        try

        {
            String whereClause = " CODIGO = ? ";

            long insertId = getDataBase().insert(registro.getFileName(), null, values);

            if (insertId != -1) {

                cursor = getDataBase().query(registro.getFileName(), registro.getColumn(), whereClause, new String[]{registro.getFILIAL(),registro.getSERIE(),registro.getNOTAFISCAL(),registro.getCODPROD()}, null, null, null);

                cursor.moveToFirst();

                registro = cursorToObj(cursor);


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
    public Integer Delete(String[] values) {
        int regs = 0;

        try {

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0],values[1],values[2],values[3]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;


    }

    @Override
    public Boolean Update(ContaCorrente obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getFILIAL(),obj.getSERIE(),obj.getNOTAFISCAL(),obj.getCODPROD()});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;


    }

    @Override
    public ContaCorrente cursorToObj(Cursor cursor) {


        ContaCorrente obj = null;

        try {
            obj = new ContaCorrente(

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
                    cursor.getFloat(14),
                    cursor.getFloat(15),
                    cursor.getFloat(16),
                    cursor.getFloat(17),
                    cursor.getFloat(18),
                    cursor.getFloat(19)


                    );

        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<ContaCorrente> getAll() {


        Cursor cursor = null;

        List<ContaCorrente> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName()  , null);

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
    public ContaCorrente seek(String[] values) {

        ContaCorrente obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1],values[2],values[3]},null, null, null);

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
