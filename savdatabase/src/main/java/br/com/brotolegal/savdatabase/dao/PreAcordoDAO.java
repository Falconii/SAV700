package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.Prospeccao;

/**
 * Created by Falconi on 29/09/2016.
 */
public class PreAcordoDAO extends DAO2 implements IDao2<PreAcordo> {


    private final String TAG = "PREACORDO";

    private String whereClausePrimary = " CODMOBILE  = ? ";

    public PreAcordoDAO() throws Exception {

        super("PREACORDO","dbuser");

    }

    @Override
    public PreAcordo insert(PreAcordo obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {String.valueOf(obj.getCODMOBILE())},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary,new String[] {values[0]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;


    }

    @Override
    public Boolean Update(PreAcordo obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {String.valueOf(obj.getCODMOBILE())});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;
    }

    @Override
    public PreAcordo cursorToObj(Cursor cursor) {
        PreAcordo obj = null;
        try {
            obj = new PreAcordo(

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
                    cursor.getFloat(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19)


                    );

            if (cursor.getColumnCount() != 21){

                obj.complemento(

                        cursor.getString(20),
                        cursor.getString(21),
                        cursor.getString(22),
                        cursor.getString(23),
                        cursor.getString(24)


                        );

            }

        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<PreAcordo> getAll() {
        Cursor cursor = null;

        List<PreAcordo> result = new ArrayList<>();

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
    public PreAcordo seek(String[] values) {
        PreAcordo obj = null;

        Cursor cursor;

        try {

            String select = "select preacordo.*,    "
                    + "ifnull(cliente.razao,''),    "
                    + "ifnull(cliente.cnpj,'000'),  "
                    + "ifnull(cliente.ie,''),       "
                    + "ifnull(verba.descricao,''),  "
                    + "ifnull(rede.descricao,'')    "
                    + "from  preacordo "
                    + "left join cliente   on preacordo.cliente  =  cliente.codigo and  preacordo.loja = cliente.loja "
                    + "left join verba     on preacordo.codverb  =  verba.codigo "
                    + "left join rede      on preacordo.rede     =  rede.codigo "
                    + "where preacordo.codmobile = '"+values[0]+"' ";

            cursor = getDataBase().rawQuery(select, null);

            if (!cursor.moveToFirst()){

                obj = null;

            } else {

                 obj = cursorToObj(cursor);
            }

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return obj;
    }



    public List<PreAcordo> getAllWithComplemento() {
        Cursor cursor = null;

        List<PreAcordo> result = new ArrayList<>();

        try {

            String select = "select preacordo.*,"
                    + "ifnull(cliente.razao,''),   "
                    + "ifnull(cliente.cnpj,'000'),   "
                    + "ifnull(cliente.ie,''),   "
                    + "ifnull(verba.descricao,''),   "
                    + "ifnull(rede.descricao,'')   "
                    + "from  preacordo "
                    + "left join cliente   on preacordo.cliente  =  cliente.codigo and  preacordo.loja = cliente.loja "
                    + "left join verba     on preacordo.codverb  =  verba.codigo "
                    + "left join rede      on preacordo.rede     =  rede.codigo "
                    + "order by preacordo.codmobile desc ";

            cursor = getDataBase().rawQuery(select, null);

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

    public PreAcordo getOneWithComplemento(String preacordo) {
        Cursor cursor = null;

        PreAcordo result = new PreAcordo();

        try {

            String select = "select preacordo.*,"
                    + "ifnull(cliente.razao,''),   "
                    + "ifnull(cliente.cnpj,'000'),   "
                    + "ifnull(cliente.ie,''),   "
                    + "ifnull(verba.descricao,''),   "
                    + "ifnull(rede.descricao,'')   "
                    + "from  preacordo "
                    + "left join cliente   on preacordo.cliente  =  cliente.codigo and  preacordo.loja = cliente.loja "
                    + "left join verba     on preacordo.codverb  =  verba.codigo "
                    + "left join rede      on preacordo.rede     =  rede.codigo ";
                    if (!preacordo.trim().isEmpty()){
                        select += "where preacordo.codmobile = '"+preacordo+"'";
                    }
            select +=  "order by preacordo.codmobile desc ";

            cursor = getDataBase().rawQuery(select, null);

            if (!cursor.moveToFirst()){

                result = null;

            } else {

                while (!(cursor.isAfterLast())) {

                    result = (cursorToObj(cursor));

                    break;

                }
            }


        }catch (Exception e) {

              new Exception(e.getMessage());

        }

        return result;

    }

    public List<PreAcordo> getAllToSend(String status) {
        Cursor cursor = null;

        List<PreAcordo> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName()+" where "+ getRegistro().getFileName().trim() +".status = '"+status+"' " , null);

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

    public List<PreAcordo> getAllToRefresh() {
        Cursor cursor = null;

        List<PreAcordo> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName()+" where "+ getRegistro().getFileName().trim() +".status in ('2','3','4','5') " , null);

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


    public  String getInsertFromTable(String tabela){

        String retorno = "";

        String sql = "select * from "+tabela+" LIMIT 0";

        Cursor  rs = getDataBase().rawQuery(sql, null);

        rs.moveToFirst();

        for(int x = 0; x < rs.getColumnCount(); x++){

            retorno += rs.getColumnName(x);

            if ( x != rs.getColumnCount()-1) retorno += ", ";

        }

        return retorno;

    }

}
