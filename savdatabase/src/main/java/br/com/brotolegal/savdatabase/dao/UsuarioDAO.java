package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Usuario;

/**
 * Created by Falconi on 22/10/2015.
 */
public class UsuarioDAO extends DAO2 implements IDao2<Usuario> {


    private final String TAG = "USUARIODAO";

    private String whereClausePrimary = " cod = ? ";

    public UsuarioDAO() throws Exception {

        super("USUARIO");

    }

    @Override
    public Usuario insert(Usuario obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getCOD()},null, null, null);

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
    public Boolean Update(Usuario obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getCOD()});

        } catch (Exception e){

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
    public Usuario cursorToObj(Cursor cursor) {

        Usuario obj = null;

        try {
            obj = new Usuario(
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
                    cursor.getString(13)
                    );

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<Usuario> getAll() {

        Cursor cursor = null;

        List<Usuario> result = new ArrayList<>();

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
    public Usuario seek(String[] values) {

        Usuario obj = null;


        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0]},null, null, null);

            if (cursor.moveToFirst())
            {

                obj =  cursorToObj(cursor);

            } else {

                obj = null;

            }
        } catch (Exception e){

            Log.i(TAG, "Erro No Seek :"+e.getMessage());

            new Exception(e.getMessage());

        }


        return obj;

    }

    public Usuario getUserMaster(){

        Cursor cursor = null;

        Usuario result = null;

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where status = 'M' limit 1" , null);

            if (!cursor.moveToFirst()){

                result = null;

            } else {

                result = cursorToObj(cursor);

            }

        }catch (Exception e) {

            Log.i(TAG, "Erro Na GetMaster: "+e.toString());

            new Exception(e.getMessage());

        }

        return result;


    }
}
