package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.PreCliente;

/**
 * Created by Falconi on 22/10/2015.
 */
public class PreClienteDAO extends DAO2 implements IDao2<PreCliente> {

    private final String TAG          = "PreClienteDAO";

    private String whereClausePrimary = " id = ? ";

    public PreClienteDAO() throws Exception {

        super("PRECLIENTE","dbuser");

    }

    @Override
    public PreCliente insert(PreCliente obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getID()},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[]{values[0]});

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Boolean Update(PreCliente obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getID()});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;

    }


    @Override
    public PreCliente cursorToObj(Cursor cursor) {

        PreCliente obj = null;

        try {
            obj = new PreCliente(
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
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20),
                    cursor.getString(21),
                    cursor.getString(22),
                    cursor.getString(23),
                    cursor.getString(24),
                    cursor.getString(25),
                    cursor.getString(26),
                    cursor.getString(27),
                    cursor.getString(28),
                    cursor.getString(29),
                    cursor.getString(30),
                    cursor.getString(31),
                    cursor.getString(32),
                    cursor.getString(33),
                    cursor.getString(34),
                    cursor.getString(35),
                    cursor.getString(36),
                    cursor.getString(37),
                    cursor.getFloat(38),
                    cursor.getString(39),
                    cursor.getString(40),
                    cursor.getString(41),
                    cursor.getString(42),
                    cursor.getString(43),
                    cursor.getFloat(44),
                    cursor.getString(45),
                    cursor.getString(46),
                    cursor.getString(47),
                    cursor.getString(48),
                    cursor.getString(49),
                    cursor.getString(50),
                    cursor.getString(51),
                    cursor.getString(52),
                    cursor.getString(53),
                    cursor.getString(54),
                    cursor.getString(55),
                    cursor.getString(56),
                    cursor.getString(57),
                    cursor.getString(58),
                    cursor.getString(59),
                    cursor.getString(60),
                    cursor.getString(61),
                    cursor.getString(62),
                    cursor.getString(63),
                    cursor.getString(64),
                    cursor.getString(65)
            );

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<PreCliente> getAll() {

        Cursor cursor = null;

        List<PreCliente> result;

        result = new ArrayList<PreCliente>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " order by codigo " , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<PreCliente>();

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
    public PreCliente seek(String[] values) {

        PreCliente obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0]},null, null, null);

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



    public List<PreCliente> getPreClienteAtualizar() throws Exception {

        List<PreCliente> lista = new ArrayList<PreCliente>();

        Cursor cursor;

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where (STATUS in ('5','6','7','8','A') ) ", null);

            cursor.moveToFirst();

            while (!(cursor.isAfterLast())) {

                lista.add( cursorToObj(cursor));

                cursor.moveToNext();
            }


        }catch (Exception e) {

            throw  new Exception("Erro: "+ e.getMessage());

        }

        return lista;

    }


    public List<PreCliente> getPreClienteNovo() throws Exception {

        List<PreCliente> lista = new ArrayList<PreCliente>();

        Cursor cursor;

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where (STATUS in ('4') ) ", null);

            cursor.moveToFirst();

            while (!(cursor.isAfterLast())) {

                lista.add( cursorToObj(cursor));

                cursor.moveToNext();
            }


        }catch (Exception e) {

            throw  new Exception("Erro: "+ e.getMessage());

        }

        return lista;

    }


    public PreCliente getPreCliente() {

        PreCliente obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),null, null,null, null, null);

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

    public List<PreCliente> getFilterStatus(String status) {

        Cursor cursor = null;

        List<PreCliente> result;

        result = new ArrayList<PreCliente>();

        try {


            if (status.isEmpty()) {

                cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " order by codigo ", null);

            }
            else {

                if (status.equals("*")) {

                    cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where status in ('5','6','7','8','A') order by codigo ", null);

                } else {

                    cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() + " where status = '" + status + "' order by codigo ", null);
                }
            }
            if (!cursor.moveToFirst()){

                result = new ArrayList<PreCliente>();

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
