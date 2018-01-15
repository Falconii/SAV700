package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.MotivosTrocaDev;

/**
 * Created by Falconi on 06/06/2017.
 */

public class MotivosTrocaDEvDAO  extends DAO2 implements IDao2<MotivosTrocaDev> {

    private final String TAG = "MOTIVOTROCADEV";

    private String whereClausePrimary = " tipo = ? and codigo = ? ";

    public MotivosTrocaDEvDAO() throws Exception {

        super("MOTIVOSTROCADEV","dbuser");

    }


    @Override
    public MotivosTrocaDev insert(MotivosTrocaDev obj) {

        MotivosTrocaDev registro = (MotivosTrocaDev) obj;

        Cursor cursor = null;

        ContentValues values = getContentValues(registro) ;

        try

        {

            long insertId = getDataBase().insert(registro.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(registro.getFileName(), registro.getColumn(),whereClausePrimary,new String[] {registro.getCODIGO()},null, null, null);

                cursor.moveToFirst();

                registro =  (MotivosTrocaDev) cursorToObj(cursor);


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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0],values[1]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Boolean Update(MotivosTrocaDev obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getCODIGO(),obj.getCODIGO()});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;

    }

    @Override
    public MotivosTrocaDev cursorToObj(Cursor cursor) {

        MotivosTrocaDev obj = null;

        try {

            obj = new MotivosTrocaDev(

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
    public List<MotivosTrocaDev> getAll() {

        Cursor cursor = null;

        List<MotivosTrocaDev> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " order by tipo,codigo " , null);

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
    public MotivosTrocaDev seek(String[] values) {

        MotivosTrocaDev obj = null;

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
