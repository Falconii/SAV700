package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Frete;

/**
 * Created by Falconi on 07/10/2016.
 */
public class FreteDAO extends DAO2 implements IDao2<Frete> {

    private final String TAG = "FRETEODAO";

    private String whereClausePrimary = " filial = ? and  rede = ? and estado = ?   and  fdmin = ? and   fdmax = ? ";

    public FreteDAO() throws Exception {
        super("FRETE","dbuser");
    }


    @Override
    public Frete insert(Frete obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getFILIAL(),obj.getESTADO(),String.valueOf(obj.getFDMAX()),String.valueOf(obj.getFDMIN())},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0],values[1],values[2],values[3],values[4]});

        } catch (Exception e) {

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return regs;
    }

    @Override
    public Boolean Update(Frete obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getFILIAL(),obj.getREDE(),obj.getESTADO(),String.valueOf(obj.getFDMAX()),String.valueOf(obj.getFDMIN())});

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;

    }

    @Override
    public Frete cursorToObj(Cursor cursor) {

        Frete obj = null;

        try {
            obj = new Frete(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getFloat(4),
                    cursor.getFloat(5),
                    cursor.getFloat(6)

            );

        } catch (Exception e){

            Log.i(TAG, e.getMessage());

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<Frete> getAll() {
        Cursor cursor = null;

        List<Frete> result;

        result = new ArrayList<Frete>();

        try {

            cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName() , null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<Frete>();

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
    public Frete seek(String[] values) {
        Frete obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1],values[2],values[3],values[4]},null, null, null);

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

    public Frete getFretArroz(Float value,String rede,String estado,String codmun){

        Cursor cursor = null;

        Frete result = null;

        Frete maximo = null;

        Frete minimo  = null;

        String  sql  = "";


        if (!rede.trim().equals("000000")){


            try {

                rede = rede.substring(0,3)+"000";

                //BUSCA FRETE MAIS CARO - MENOR QUANTIDADE
                sql = " select * " +
                        " from " + getRegistro().getFileName() +
                        " where FRETE.filial = '02' and  FRETE.rede = '" + rede + "' and FRETE.estado = '"+ estado +"'  " +
                        " order by FRETE.fdmin asc limit 1";


                cursor = getDataBase().rawQuery(sql, null);

                if (!cursor.moveToFirst()) {

                    maximo = null;

                } else {

                    maximo = cursorToObj(cursor);

                }

                cursor.close();


                //BUSCA FRETE MAIS BARATO - MAIOR QUANTIDADE
                sql = " select * " +
                        " from " + getRegistro().getFileName() +
                        " where FRETE.filial = '02' and  FRETE.rede = '" + rede + "' and FRETE.estado = '"+ estado +"'  " +
                        " order by FRETE.fdmin desc limit 1";


                cursor = getDataBase().rawQuery(sql, null);

                if (!cursor.moveToFirst()) {

                    minimo = null;

                } else {

                    minimo = cursorToObj(cursor);

                }

                cursor.close();


                if (minimo == null || maximo == null) {

                    return result;

                }


                if ( (value.compareTo(maximo.getFDMIN()) < 0) || (value.compareTo(minimo.getFDMAX()) > 0) ) {

                    if (value.compareTo(maximo.getFDMIN()) < 0) {

                        result =  maximo;

                    }

                    if (value.compareTo(minimo.getFDMAX()) > 0) {

                        result = minimo;

                    }

                } else {


                    sql = " select * " +
                            " from " + getRegistro().getFileName() +
                            " where FRETE.filial = '02' and  FRETE.rede = '" + rede + "' and FRETE.estado = '" + estado + "' and (" + String.valueOf(value) + " >= FRETE.fdmin and  " + String.valueOf(value) + " <= FRETE.fdmax ) ";


                    cursor = getDataBase().rawQuery(sql, null);

                    if (!cursor.moveToFirst()) {

                        result = maximo;

                    } else {

                        result = cursorToObj(cursor);

                    }

                    cursor.close();

                }

                if (value.compareTo(minimo.getFDMAX()) > 0){

                    value = minimo.getFDMAX();

                }

                if (value.compareTo(result.getFDMIN()) < 0){

                    value = result.getFDMIN();

                }

                Float frete = 0f;

                Float perc = Arredondamento2((value / result.getFDMAX()) * 100);

                if (perc.compareTo(70.00f) < 0) {

                    perc = 70f;

                }

                frete = (result.getFRETE() * result.getFDMAX()) / (result.getFDMAX() * (perc / 100));

                frete = Arredondamento3(frete);

                frete = Arredondamento2(frete);

                result.setFRETE(frete);

            } catch (Exception e) {

                Log.i(TAG, e.getMessage());


                new Exception(e.getMessage());

            }



        } else {

            try {

                //BUSCA FRETE MAIS CARO - MENOR QUANTIDADE
                sql = " select * " +
                        " from " + getRegistro().getFileName() +
                        " where FRETE.filial = '02' and  FRETE.codmun = '" + codmun + "'  " +
                        " order by FRETE.fdmin asc limit 1";


                cursor = getDataBase().rawQuery(sql, null);

                if (!cursor.moveToFirst()) {

                    maximo = null;

                } else {

                    maximo = cursorToObj(cursor);

                }

                cursor.close();


                //BUSCA FRETE MAIS BARATO - MAIOR QUANTIDADE
                sql = " select * " +
                        " from " + getRegistro().getFileName() +
                        " where FRETE.filial = '02' and  FRETE.codmun = '" + codmun + "'  " +
                        " order by FRETE.fdmin desc limit 1";


                cursor = getDataBase().rawQuery(sql, null);

                if (!cursor.moveToFirst()) {

                    minimo = null;

                } else {

                    minimo = cursorToObj(cursor);

                }

                cursor.close();


                if (minimo == null || maximo == null) {

                    return result;

                }

                if ( (value.compareTo(maximo.getFDMIN()) < 0) || (value.compareTo(minimo.getFDMAX()) > 0) ) {

                    if (value.compareTo(maximo.getFDMIN()) < 0) {

                        result =  maximo;

                    }

                    if (value.compareTo(minimo.getFDMAX()) > 0) {

                        result = minimo;

                    }

                } else {


                    sql = " select * " +
                            " from " + getRegistro().getFileName() +
                            " where FRETE.filial = '02' and  FRETE.codmun = '" + codmun + "' and (" + String.valueOf(value) + " >= FRETE.fdmin and  " + String.valueOf(value) + " <= FRETE.fdmax ) ";



                    cursor = getDataBase().rawQuery(sql, null);

                    if (!cursor.moveToFirst()) {

                        result = maximo;

                    } else {

                        result = cursorToObj(cursor);

                    }

                    cursor.close();

                }

                if (value.compareTo(minimo.getFDMAX()) > 0){

                    value = minimo.getFDMAX();

                }

                if (value.compareTo(result.getFDMIN()) < 0){

                    value = result.getFDMIN();

                }
                Float frete = 0f;

                Float perc = Arredondamento2((value / result.getFDMAX()) * 100);

                if (perc.compareTo(70.00f) < 0) {

                    perc = 70f;

                }

                frete = (result.getFRETE() * result.getFDMAX()) / (result.getFDMAX() * (perc / 100));

                frete = Arredondamento3(frete);

                frete = Arredondamento2(frete);

                result.setFRETE(frete);


            } catch (Exception e) {

                Log.i(TAG, e.getMessage());


                new Exception(e.getMessage());

            }
        }
        return result;

    }

    public Frete getFreteFeijao(Float value,String rede,String estado,String codmun){

        Cursor cursor = null;

        Frete result = null;

        Frete maximo = null;

        Frete minimo  = null;

        String  sql  = "";


        if (!rede.trim().equals("000000")){


            try {

                rede = rede.substring(0,3)+"000";

                //BUSCA FRETE MAIS CARO - MENOR QUANTIDADE
                sql = " select * " +
                        " from " + getRegistro().getFileName() +
                        " where FRETE.filial = '07' and  FRETE.rede = '" + rede + "' and FRETE.estado = '"+ estado +"'  " +
                        " order by FRETE.fdmin asc limit 1";


                cursor = getDataBase().rawQuery(sql, null);

                if (!cursor.moveToFirst()) {

                    maximo = null;

                } else {

                    maximo = cursorToObj(cursor);

                }

                cursor.close();


                //BUSCA FRETE MAIS BARATO - MAIOR QUANTIDADE
                sql = " select * " +
                        " from " + getRegistro().getFileName() +
                        " where FRETE.filial = '07' and  FRETE.rede = '" + rede + "' and FRETE.estado = '"+ estado +"'  " +
                        " order by FRETE.fdmin desc limit 1";


                cursor = getDataBase().rawQuery(sql, null);

                if (!cursor.moveToFirst()) {

                    minimo = null;

                } else {

                    minimo = cursorToObj(cursor);

                }

                cursor.close();


                if (minimo == null || maximo == null) {

                    return result;

                }


                if ( (value.compareTo(maximo.getFDMIN()) < 0) || (value.compareTo(minimo.getFDMAX()) > 0) ) {

                    if (value.compareTo(maximo.getFDMIN()) < 0) {

                        result =  maximo;

                    }

                    if (value.compareTo(minimo.getFDMAX()) > 0) {

                        result = minimo;

                    }

                } else {


                    sql = " select * " +
                            " from " + getRegistro().getFileName() +
                            " where FRETE.filial = '07' and  FRETE.rede = '" + rede + "' and FRETE.estado = '" + estado + "' and (" + String.valueOf(value) + " >= FRETE.fdmin and  " + String.valueOf(value) + " <= FRETE.fdmax ) ";


                    cursor = getDataBase().rawQuery(sql, null);

                    if (!cursor.moveToFirst()) {

                        result = maximo;

                    } else {

                        result = cursorToObj(cursor);

                    }

                    cursor.close();

                }

                if (value.compareTo(minimo.getFDMAX()) > 0){

                    value = minimo.getFDMAX();

                }


                if (value.compareTo(result.getFDMIN()) < 0){

                    value = result.getFDMIN();

                }

                Float frete = 0f;

                if (result.getFDMAX().compareTo(150f) <= 0){

                    frete = result.getFRETE();

                } else {

                    Float perc = Arredondamento2((value / result.getFDMAX()) * 100);

                    if (perc.compareTo(70.00f) < 0) {

                        perc = 70f;

                    }

                    frete = (result.getFRETE() * result.getFDMAX()) / (result.getFDMAX() * (perc / 100));

                }

                frete = Arredondamento3(frete);

                frete = Arredondamento2(frete);

                result.setFRETE(frete);

            } catch (Exception e) {

                Log.i(TAG, e.getMessage());

                new Exception(e.getMessage());

            }



        } else {

            try {

                //BUSCA FRETE MAIS CARO - MENOR QUANTIDADE
                sql = " select * " +
                        " from " + getRegistro().getFileName() +
                        " where FRETE.filial = '07' and  FRETE.codmun = '" + codmun + "'  " +
                        " order by FRETE.fdmin asc limit 1";


                cursor = getDataBase().rawQuery(sql, null);

                if (!cursor.moveToFirst()) {

                    maximo = null;

                } else {

                    maximo = cursorToObj(cursor);

                }

                cursor.close();


                //BUSCA FRETE MAIS BARATO - MAIOR QUANTIDADE
                sql = " select * " +
                        " from " + getRegistro().getFileName() +
                        " where FRETE.filial = '07' and  FRETE.codmun = '" + codmun + "'  " +
                        " order by FRETE.fdmin desc limit 1";


                cursor = getDataBase().rawQuery(sql, null);

                if (!cursor.moveToFirst()) {

                    minimo = null;

                } else {

                    minimo = cursorToObj(cursor);

                }

                cursor.close();


                if (minimo == null || maximo == null) {

                    return result;

                }

                if ( (value.compareTo(maximo.getFDMIN()) < 0) || (value.compareTo(minimo.getFDMAX()) > 0) ) {

                    if (value.compareTo(maximo.getFDMIN()) < 0) {

                        result =  maximo;

                    }

                    if (value.compareTo(minimo.getFDMAX()) > 0) {

                        result = minimo;

                    }

                } else {


                    sql = " select * " +
                            " from " + getRegistro().getFileName() +
                            " where FRETE.filial = '07' and  FRETE.codmun = '" + codmun + "' and (" + String.valueOf(value) + " >= FRETE.fdmin and  " + String.valueOf(value) + " <= FRETE.fdmax ) ";



                    cursor = getDataBase().rawQuery(sql, null);

                    if (!cursor.moveToFirst()) {

                        result = maximo;

                    } else {

                        result = cursorToObj(cursor);

                    }

                    cursor.close();

                }

                if (value.compareTo(minimo.getFDMAX()) > 0){

                    value = minimo.getFDMAX();

                }


                if (value.compareTo(result.getFDMIN()) < 0){

                    value = result.getFDMIN();

                }

                Float frete = 0f;

                if (result.getFDMAX().compareTo(150f) <= 0){

                    frete = result.getFRETE();

                } else {

                    Float perc = Arredondamento2((value / result.getFDMAX()) * 100);

                    if (perc.compareTo(70.00f) < 0) {

                        perc = 70f;

                    }

                    frete = (result.getFRETE() * result.getFDMAX()) / (result.getFDMAX() * (perc / 100));

                }

                frete = Arredondamento3(frete);

                frete = Arredondamento2(frete);

                result.setFRETE(frete);


            } catch (Exception e) {

                Log.i(TAG, e.getMessage());


                new Exception(e.getMessage());

            }
        }
        return result;


    }


    private Float Arredondamento2(Float value ){

        Float retorno = 0f;

        BigDecimal tot2 = new BigDecimal(Float.toString(value));

        tot2 = tot2.setScale(2, BigDecimal.ROUND_HALF_UP);

        retorno = tot2.floatValue();

        return retorno;

    }


    private Float Arredondamento3(Float value ){

        Float retorno = 0f;

        BigDecimal tot2 = new BigDecimal(Float.toString(value));

        tot2 = tot2.setScale(3, BigDecimal.ROUND_HALF_UP);

        retorno = tot2.floatValue();

        return retorno;

    }

    public List<Float> getFaixas() {

        Cursor cursor = null;

        String sql    = "select fdmax from frete group by fdmax";
        List<Float> result;

        result = new ArrayList<>();

        try {

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                result.add(0f);

            } else {

                result.add(0f);

                while (!(cursor.isAfterLast())) {

                    result.add(cursor.getFloat(0));

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

