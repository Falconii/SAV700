package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.NotaFiscalCab;
import br.com.brotolegal.savdatabase.entities.NotaFiscalCab_fast;

/**
 * Created by Falconi on 15/03/2017.
 */

public class NotaFiscalCabDAO extends DAO2 implements IDao2<NotaFiscalCab> {


    private final String TAG = "NOTAFISCALCAB";

    private String whereClausePrimary = " filial = ? and serie = ? and notafiscal = ? ";

    private int    pageSize = 2000;

    public NotaFiscalCabDAO() throws Exception {

        super("NOTAFISCALCAB","dbuser");

    }
    @Override
    public NotaFiscalCab insert(NotaFiscalCab obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getFILIAL(), obj.getSERIE(),obj.getNOTAFISCAL()},null, null, null);

                cursor.moveToFirst();

                obj =  (NotaFiscalCab) cursorToObj(cursor);


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

            regs = getDataBase().delete(getRegistro().getFileName(),whereClausePrimary, new String[] {values[0],values[1],values[2]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Boolean Update(NotaFiscalCab obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getFILIAL(), obj.getSERIE(),obj.getNOTAFISCAL()});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return retorno == 0 ? false : true;

    }

    @Override
    public NotaFiscalCab cursorToObj(Cursor cursor) {

        NotaFiscalCab obj = null;

        try {

            obj = new NotaFiscalCab(

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
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19)



            );
        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;

    }

    public NotaFiscalCab_fast cursorToObj_fast(Cursor cursor) {

        NotaFiscalCab_fast obj = null;

        try {

            obj = new NotaFiscalCab_fast(

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
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19)
            );

            obj.complemento(

                    cursor.getString(20),
                    cursor.getString(21),
                    cursor.getString(22),
                    cursor.getString(23),
                    cursor.getString(24),
                    cursor.getString(25),
                    cursor.getString(26),
                    cursor.getString(27)

            );



        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<NotaFiscalCab> getAll() {
        Cursor cursor = null;

        List<NotaFiscalCab> result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery("select *  from " + getRegistro().getFileName(),null) ;

            if (!cursor.moveToFirst()) {

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    NotaFiscalCab obj = cursorToObj(cursor);

                    result.add(obj);

                    cursor.moveToNext();

                }
            }


        } catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;


    }

    @Override
    public NotaFiscalCab seek(String[] values) {

        NotaFiscalCab obj = null;

        try{

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1],values[3]},null, null, null);

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

    public int  getAllWithFiltroGetPagina(String[] values) {

        int page = -1;

        Cursor cursor = null;

        String sql = "";

        try {

            //select
            sql =  " select count(*) " +
                    " from notafiscalcab " +
                    " inner join cliente   on notafiscalcab.codcli  =  cliente.codigo and notafiscalcab.codloja = cliente.loja "+
                    " inner join rede      on cliente.rede       =  rede.codigo  ";
            //where
            if (!values[0].isEmpty()) {
                sql += " where notafiscalcab.codcli = '"+values[0]+"' and notafiscalcab.codloja = '"+values[1]+"'  ";
            }
            //order by
            if (values[2].equals("C")) {  //crescente

                sql += " order by notafiscalcab.dtemissao ASC ";

            } else {

                sql += " order by notafiscalcab.dtemissao DESC ";
            }

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()) {

                page = -1;

            } else {

                page = (int)Math.round((cursor.getInt(0)/100));

            }


        } catch (Exception e) {


            page = -1;
        }

        return page;

    }

    /*
       0-Codigo do Cliente
       1-Loja do Cliente
       2-Ordenação D- Descrescente ou C-Crescente
       3-Pagina
     */
    public List<NotaFiscalCab_fast> getAllWithFiltro(String[] values) {

        Cursor cursor = null;

        List<NotaFiscalCab_fast> result = new ArrayList<>();

        String sql = "";

        try {

            //select
            sql =  " select notafiscalcab.*, " +
                    "        ifnull(cliente.cnpj     ,''),   " +
                    "        ifnull(cliente.ie       ,''),   " +
                    "        ifnull(cliente.codcidade,''),   " +
                    "        ifnull(cliente.cidade   ,''),   " +
                    "        ifnull(cliente.rede     ,''),   " +
                    "        ifnull(rede.descricao   ,''),   " +
                    "        ifnull(cliente.ddd      ,''),   " +
                    "        ifnull(cliente.telefone ,'')    " +
                    " from notafiscalcab " +
                    " inner join cliente   on notafiscalcab.codcli  =  cliente.codigo and notafiscalcab.codloja = cliente.loja "+
                    " inner join rede      on cliente.rede       =  rede.codigo  ";
            //where
            if (!values[0].isEmpty()) {
                sql += " where notafiscalcab.codcli = '"+values[0]+"' and notafiscalcab.codloja = '"+values[1]+"'  ";
            }
            //order by
            if (values[2].equals("C")) {  //crescente

                sql += " order by notafiscalcab.dtemissao ASC ";

            } else {

                sql += " order by notafiscalcab.dtemissao DESC ";
            }

            //Acrescenta pagina

            sql += " limit "+String.valueOf(pageSize)+" offset "+String.valueOf(Integer.valueOf(values[3])*pageSize);

            Log.i("ADAPTADOR",sql);

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()) {

                result = new ArrayList<>();

            } else {


                while (!(cursor.isAfterLast())) {


                    NotaFiscalCab_fast obj = cursorToObj_fast(cursor);



                    result.add(obj);

                    cursor.moveToNext();

                }
            }


        } catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;

    }

      /*
       0-Filial do Pedido
       1-Nro Do Pedido
       2-Ordenação D- Descrescente ou C-Crescente
       3-Pagina
     */

    public List<NotaFiscalCab_fast> getNotaByPedido(String[] values) {

        Cursor cursor = null;

        List<NotaFiscalCab_fast> result = new ArrayList<>();

        String sql = "";

        try {

            //select
            sql =  " select notafiscalcab.*, " +
                    "        ifnull(cliente.cnpj     , ''),   " +
                    "        ifnull(cliente.ie       , ''),   " +
                    "        ifnull(cliente.codcidade,''),   " +
                    "        ifnull(cliente.cidade   ,''),   " +
                    "        ifnull(cliente.rede     ,''),   " +
                    "        ifnull(rede.descricao   ,''),   " +
                    "        ifnull(cliente.ddd      ,''),   " +
                    "        ifnull(cliente.telefone ,'')    " +
                    " from notafiscalcab " +
                    " inner join cliente   on notafiscalcab.codcli  =  cliente.codigo and notafiscalcab.codloja = cliente.loja "+
                    " inner join rede      on cliente.rede       =  rede.codigo  ";
            //where
            sql += " where notafiscalcab.filial = '"+values[0]+"' and notafiscalcab.numpedido = '"+values[1]+"'  ";
            //order by
            if (values[2].equals("C")) {  //crescente

                sql += " order by notafiscalcab.dtemissao ASC ";

            } else {

                sql += " order by notafiscalcab.dtemissao DESC ";
            }


            //Acrescenta pagina

            sql += " limit "+String.valueOf(pageSize)+" offset "+String.valueOf(Integer.valueOf(values[3])*pageSize);


            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()) {

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    NotaFiscalCab_fast obj = cursorToObj_fast(cursor);

                    result.add(obj);

                    cursor.moveToNext();

                }
            }


        } catch (Exception e) {

            Log.i(TAG, e.getMessage());


            new Exception(e.getMessage());

        }

        return result;

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
