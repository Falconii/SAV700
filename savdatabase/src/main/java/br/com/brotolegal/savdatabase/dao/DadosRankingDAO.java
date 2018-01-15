package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.DadosRanking;

/**
 * Created by Falconi on 05/07/2016.
 */
public class DadosRankingDAO extends DAO2 implements IDao2<DadosRanking> {

    private final String TAG = "DADOSRANKINGDAO";

    private String whereClausePrimary = " tmp_campanha = ? and  tmp_codigo = ? ";

    public DadosRankingDAO() throws Exception {
        super("DADOSRANKING","dbuser");
    }

    @Override
    public DadosRanking insert(DadosRanking obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getTMP_CAMPANHA(),obj.getTMP_PARTICIPANTE()},null, null, null);

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
    public Boolean Update(DadosRanking obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getTMP_CAMPANHA(), obj.getTMP_PARTICIPANTE()});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;


    }

    @Override
    public DadosRanking cursorToObj(Cursor cursor) {

        DadosRanking obj = null;

        try {
            obj = new DadosRanking(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            );

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<DadosRanking> getAll() {
        Cursor cursor = null;

        List<DadosRanking> result;

        result = new ArrayList<DadosRanking>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<DadosRanking>();

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
    public DadosRanking seek(String[] values) {

        DadosRanking obj = null;

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

    public List<DadosRanking> getRankingByCampanha(String campanha){

        Cursor cursor = null;

        List<DadosRanking> result;

        result = new ArrayList<DadosRanking>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where tmp_campanha = '"+campanha+"' order by tmp_posicao ", null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<DadosRanking>();

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

}
