package br.com.brotolegal.savdatabase.dao;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.entities.Base01;
import br.com.brotolegal.savdatabase.util.VisaoRel01;

/**
 * Created by Falconi on 07/06/2017.
 */

public class Base01DAO extends DAO2 implements  IDao2<Base01> {


    private final String TAG = "BASE01";

    private String whereClausePrimary = "";

    public Base01DAO() throws Exception {

        super("BASE01","dbuser");

    }

    @Override
    public Base01 insert(Base01 obj) {
        return null;
    }

    @Override
    public Integer Delete(String[] values) {
        return null;
    }

    @Override
    public Boolean Update(Base01 obj) {
        return null;
    }

    @Override
    public Base01 cursorToObj(Cursor cursor) {

        Base01 obj = null;

        try {

            obj = new Base01(

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
                    cursor.getFloat(11),
                    cursor.getFloat(12),
                    cursor.getFloat(13),
                    cursor.getFloat(14),
                    cursor.getFloat(15),
                    cursor.getFloat(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20),
                    cursor.getString(21),
                    cursor.getString(22),
                    cursor.getString(23),
                    cursor.getString(24),
                    cursor.getString(25),
                    cursor.getFloat(26),
                    cursor.getString(27),
                    cursor.getString(28),
                    cursor.getString(29)



                    );

        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<Base01> getAll() {
        return null;
    }

    @Override
    public Base01 seek(String[] values) {
        return null;
    }


    public List<VisaoRel01> getPedidosToRel01(){

        Cursor cursor = null;

        List<VisaoRel01> result = new ArrayList<>();

        String
                sql    = 	" select * "+
                " from " + getRegistro().getFileName() +
                " where  ";
        if (!App.manager_filtro_01.getWhere().isEmpty()) sql += App.manager_filtro_01.getWhere();
        sql += " order by "+ App.manager_filtro_01.getStringOrderBy();

        Log.i("SAV",sql);

        try {

            cursor = getDataBase().rawQuery(sql,null);

            if (!cursor.moveToFirst()) {

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    VisaoRel01 obj = new VisaoRel01("DT");

                    obj.setDATA(App.aaaammddToddmmaaaa(cursor.getString(3)));
                    obj.setCLIENTE(cursor.getString(4));
                    obj.setLOJA(cursor.getString(5));
                    obj.setREDE(cursor.getString(6));
                    obj.setCATEGORIA(cursor.getString(7));
                    obj.setMARCA(cursor.getString(8));
                    obj.setPRODUTO(cursor.getString(9));
                    obj.setQTD(cursor.getFloat(10));
                    obj.setVALOR(cursor.getFloat(11));
                    obj.setPRCVEN(cursor.getFloat(12));
                    obj.setPRCBASE(cursor.getFloat(13));
                    obj.setDESCONTO(cursor.getFloat(14));
                    obj.setMOBILENRO(cursor.getString(17));
                    obj.setMOBILETIPO(cursor.getString(18));
                    obj.setPEDIDOPROTHEUSNRO(cursor.getString(19));
                    obj.setCLIENTERAZAO(cursor.getString(21));
                    obj.setREDEDESCRI(cursor.getString(22));
                    obj.setCATEGORIADESCRI(cursor.getString(23));
                    obj.setMARCADESCRICAO(cursor.getString(24));
                    obj.setPRODUTODESCRICAO(cursor.getString(25));
                    obj.setQTDFDS(cursor.getFloat(26));
                    obj.setNFPROTHEUSFIL(cursor.getString(27));
                    obj.setNFPROTHEUSNRO(cursor.getString(28));
                    obj.setNFPROTHEUSSERIE(cursor.getString(29));


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

}

