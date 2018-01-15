package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Canal;
import br.com.brotolegal.savdatabase.entities.Simulador;

/**
 * Created by Falconi on 30/11/2016.
 */

public class SimuladorDAO extends DAO2 implements IDao2<Simulador> {

    private final String TAG = "SIMULADOR";

    private String whereClausePrimary = " codsimulador = ? and codcli = ? and lojacli = ? and codproduto = ? ";

    public SimuladorDAO() throws Exception {

        super("SIMULADOR","dbuser");

    }

    @Override
    public Simulador insert(Simulador obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getCODSIMULADOR(),obj.getCODCLI(), obj.getLOJACLI(), obj.getCODPRODUTO()},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), " codsimulador = ? ", new String[] {values[0],values[1],values[2],values[3]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Boolean Update(Simulador obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getCODSIMULADOR(),obj.getCODCLI(), obj.getLOJACLI(), obj.getCODPRODUTO()});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;

    }

    @Override
    public Simulador cursorToObj(Cursor cursor) {
        Simulador simulador = null;
        try {
            simulador = new Simulador(
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
                    cursor.getFloat(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getFloat(13),
                    cursor.getFloat(14)

                    );

        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return simulador;
    }

    @Override
    public List<Simulador> getAll() {
        Cursor cursor = null;

        List<Simulador> result = new ArrayList<>();

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



        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;
    }


    @Override
    public Simulador seek(String[] values) {
        Simulador obj = null;

        try {

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
}
