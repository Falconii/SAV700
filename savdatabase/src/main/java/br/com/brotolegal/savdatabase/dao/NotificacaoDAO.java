package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Notificacao;

/**
 * Created by Falconi on 09/09/2016.
 */
public class NotificacaoDAO extends DAO2 implements IDao2<Notificacao> {

    private final String TAG = "NOTIFICACAODAO";

    private String whereClausePrimary = "  seq = ? ";

    public NotificacaoDAO() throws Exception {
        super("NOTIFICACAO","dbuser");
    }


    @Override
    public Notificacao insert(Notificacao obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {String.valueOf(obj.getSEQ())},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0]});

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return regs;
    }

    @Override
    public Boolean Update(Notificacao obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{String.valueOf(obj.getSEQ())});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;

    }

    @Override
    public Notificacao cursorToObj(Cursor cursor) {

        Notificacao obj = null;

        try {
            obj = new Notificacao(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                    );

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<Notificacao> getAll() {

        Cursor cursor = null;

        List<Notificacao> result;

        result = new ArrayList<Notificacao>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<Notificacao>();

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
    public Notificacao seek(String[] values) {

        Notificacao obj = null;

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

    public List<Notificacao> getAllByToday(String hoje,String codigo) {

        Cursor cursor = null;

        List<Notificacao> result;

        result = new ArrayList<Notificacao>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName()+ " where substr(hora,1,8) = '"+hoje+"' and codigo = '"+codigo+"' order by  HORA desc " , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<Notificacao>();

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
