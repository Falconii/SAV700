package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Cota;
import br.com.brotolegal.savdatabase.entities.FreteMedio;

/**
 * Created by Falconi on 07/06/2017.
 */

public class FreteMedioDAO extends DAO2 implements IDao2<FreteMedio>{

    private final String TAG = "FRETEMEDIODAO";

    private String whereClausePrimary = " tabela  = ? and estado  = ? and fdmin  = ? and fdmax  = ? ";

    public FreteMedioDAO() throws Exception {

        super("FRETEMEDIO","dbuser");

    }


    @Override
    public FreteMedio insert(FreteMedio obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {
            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getTABELA(),obj.getTABELA(),String.format("%02d",obj.getFDMIN()),String.format("%02d",obj.getFDMAX())},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0],values[1],values[2],values[3]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;


    }

    @Override
    public Boolean Update(FreteMedio obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getTABELA(),obj.getTABELA(),String.format("%02d",obj.getFDMIN()),String.format("%02d",obj.getFDMAX())});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;

    }

    @Override
    public FreteMedio cursorToObj(Cursor cursor) {

        FreteMedio obj = null;

        try {



            obj = new FreteMedio(

                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getFloat(2),
                    cursor.getFloat(3),
                    cursor.getFloat(4)
            );


        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;
    }

    @Override
    public List<FreteMedio> getAll() {
        Cursor cursor = null;

        List<FreteMedio> result = new ArrayList<>();

        try {

            String sql = "";

            sql = "select * from " + getRegistro().getFileName() + " order by  tabela, estado, fdmin, fdmax  ";

            cursor = getDataBase().rawQuery(sql, null);

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
    public FreteMedio seek(String[] values) {
        FreteMedio obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {obj.getTABELA(),obj.getTABELA(),String.format("%02d",obj.getFDMIN()),String.format("%02d",obj.getFDMAX())},null, null, null);

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


    public FreteMedio getFretemedio(String tabela, String estado, Float qtd) {

        Cursor cursor = null;

        FreteMedio result = null;

        try {

            String sqlMinMax = "Select count(*),min(fdmin),max(fdmax) from fretemedio where tabela = '"+tabela+"'  and estado = '"+estado+"' ";

            cursor = getDataBase().rawQuery(sqlMinMax, null);

            if (!cursor.moveToFirst()){

                return null;

            } else {

                if (!(cursor.isAfterLast())) {

                    if (qtd.compareTo(cursor.getFloat(1)) < 0){

                        qtd = cursor.getFloat(1);

                    }

                    if (qtd.compareTo(cursor.getFloat(2)) > 0){

                        qtd = cursor.getFloat(2);

                    }

                }
            }

            cursor.close();

            String sql = "select * from " + getRegistro().getFileName() + " where tabela = '"+tabela+"'  and estado = '"+estado+"' and ( "+qtd.toString()+" >= fdmin and "+qtd.toString()+" <= fdmax)  order by  tabela, estado, fdmin, fdmax  ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                result = null;

            } else {

                if (!(cursor.isAfterLast())) {

                  result = cursorToObj(cursor);

                }
            }

        }catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;
    }

}

