package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Campanha;
import br.com.brotolegal.savdatabase.entities.Campanha_fast;
import br.com.brotolegal.savdatabase.entities.Canal;

/**
 * Created by Falconi on 24/07/2017.
 */

public class CampanhaDAO extends DAO2 implements  IDao2<Campanha> {


    private final String TAG = "CAMPANHA";

    private String whereClausePrimary = " data = ? and gnv = ? and ga = ? and vend = ? and campanha = ? participante = ? and categoria = ? and marca = ? ";


    public CampanhaDAO() throws Exception {

        super("CAMPANHA","dbuser");

    }

    @Override
    public Campanha insert(Campanha obj) {
        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {

            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getDATA(),obj.getGNV(),obj.getGA(),obj.getVEND(),obj.getPARTICIPANTE(),obj.getCATEGORIA(),obj.getMARCA()},null, null, null);

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

            regs = getDataBase().delete(getRegistro().getFileName(), " whereClausePrimary ", new String[] {values[0],values[1],values[2],values[3],values[4],values[5],values[6],values[7]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;

    }

    @Override
    public Boolean Update(Campanha obj) {

        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getDATA(),obj.getGNV(),obj.getGA(),obj.getVEND(),obj.getPARTICIPANTE(),obj.getCATEGORIA(),obj.getMARCA()});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;


    }

    @Override
    public Campanha cursorToObj(Cursor cursor) {
        Campanha obj = null;
        try {
            obj = new Campanha(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getFloat(8),
                    cursor.getFloat(9),
                    cursor.getFloat(10),
                    cursor.getFloat(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18)
            );

        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;
    }

    @Override
    public List<Campanha> getAll() {
        Cursor cursor = null;

        List<Campanha> result = new ArrayList<>();

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
    public Campanha seek(String[] values) {
        Campanha obj = null;

        try {

            Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1],values[2],values[3],values[4],values[5],values[6],values[7]},null, null, null);

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

    public List<Campanha_fast> getCampanhas(String Ano,String[] Periodo){

        Cursor cursor;

        List<Campanha_fast> retorno = new ArrayList<>();

        String sql = "";

        try {

            sql = "select campanha,desccampanha,sum(objetivo),sum(carteira),sum(real),sum(premio) "+
                    "from campanha "+
                    "where  data in ('"+Periodo[0]+"','"+Periodo[1]+"') "+
                    "group by campanha,desccampanha ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    retorno.add(new Campanha_fast(

                            "",
                            "",
                            "",
                            "",
                            cursor.getString(0),
                            "",
                            "",
                            "",
                            cursor.getFloat(2),
                            cursor.getFloat(3),
                            cursor.getFloat(4),
                            cursor.getFloat(5),
                            "",
                            "",
                            "",
                            cursor.getString(1),
                            "",
                            "",
                            "",
                            "C"



                    ));

                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;

    }

    public List<Campanha_fast> getCampanhaCategoria(String Campanha, String[] Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();



        try {


            sql = "select campanha,desccampanha,categoria,desccategoria,sum(objetivo),sum(carteira),sum(real) "+
                    "from campanha ";

            sql += "where campanha = '" + Campanha +  "' and data in ('" + Periodo[0] + "','" + Periodo[1] + "') ";

            sql += "group by campanha,desccampanha,categoria,desccategoria ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    retorno.add(new Campanha_fast(

                            "",
                            "",
                            "",
                            "",
                            cursor.getString(0),
                            "",
                            cursor.getString(2),
                            "",
                            cursor.getFloat(4),
                            cursor.getFloat(5),
                            cursor.getFloat(6),
                            0f,
                            "",
                            "",
                            "",
                            cursor.getString(1),
                            "",
                            cursor.getString(3),
                            "",
                            "T"



                    ));

                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;

    }

    public List<Campanha_fast> getCampanhaCategoriaMarca(String Campanha, String Categoria, String[] Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();



        try {


            sql = "select campanha,desccampanha,categoria,desccategoria,marca,descmarca,sum(objetivo),sum(carteira),sum(real) "+
                    "from campanha ";

            sql += "where campanha = '" + Campanha + "' and  categoria = '"+Categoria+"' and data in ('" + Periodo[0] + "','" + Periodo[1] + "') ";

            sql += "group by campanha,desccampanha,categoria,desccategoria,marca,descmarca ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    if (!cursor.getString(4).trim().isEmpty()) {
                        retorno.add(new Campanha_fast(

                                "",
                                "",
                                "",
                                "",
                                cursor.getString(0),
                                "",
                                cursor.getString(2),
                                cursor.getString(4),
                                cursor.getFloat(6),
                                cursor.getFloat(7),
                                cursor.getFloat(8),
                                0f,
                                "",
                                "",
                                "",
                                "",
                                cursor.getString(1),
                                cursor.getString(3),
                                cursor.getString(5),
                                "M"


                        ));
                    }
                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;

    }

    public List<Campanha_fast> getCampanhaParticipante(String Campanha, String Participante, String[] Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();



        try {


            sql = "select campanha,desccampanha,participante,nomeparticipante,sum(objetivo),sum(carteira),sum(real),sum(premio) "+
                    "from campanha ";

            if (Participante.trim().isEmpty()){

                sql += "where campanha = '" + Campanha + "' and data in ('" + Periodo[0] + "','" + Periodo[1] + "') ";

            } else {

                sql += "where campanha = '" + Campanha + "' and  participante = '" + Participante + "' and data in ('" + Periodo[0] + "','" + Periodo[1] + "') ";

            }
            sql += "group by campanha,desccampanha,participante,nomeparticipante ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    if (!cursor.getString(4).trim().isEmpty()) {
                        retorno.add(new Campanha_fast(

                                "",
                                "",
                                "",
                                "",
                                cursor.getString(0),
                                cursor.getString(2),
                                "",
                                "",
                                cursor.getFloat(4),
                                cursor.getFloat(5),
                                cursor.getFloat(6),
                                cursor.getFloat(7),
                                "",
                                "",
                                "",
                                cursor.getString(1),
                                cursor.getString(3),
                                "",
                                "",
                                "L"


                        ));
                    }
                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;
    }

    public List<Campanha_fast> getCampanhaParticipanteCategoria(String Campanha, String Participante,String[] Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();



        try {


            sql = "select campanha,desccampanha,participante,nomeparticipante,categoria,desccategoria,sum(objetivo),sum(carteira),sum(real),sum(premio) "+
                    "from campanha ";
            sql += "where campanha = '" + Campanha + "' and  participante = '" + Participante + "' and data in ('" + Periodo[0] + "','" + Periodo[1] + "') ";
            sql += "group by campanha,desccampanha,participante,nomeparticipante,categoria,desccategoria ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    retorno.add(new Campanha_fast(
                            "",
                            "",
                            "",
                            "",
                            cursor.getString(0),
                            cursor.getString(2),
                            cursor.getString(4),
                            "",
                            cursor.getFloat(6),
                            cursor.getFloat(7),
                            cursor.getFloat(8),
                            cursor.getFloat(9),
                            "",
                            "",
                            "",
                            cursor.getString(1),
                            cursor.getString(3),
                            cursor.getString(5),
                            "",
                            "T"


                    ));

                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;
    }

    public List<Campanha_fast> getCampanhaParticipanteCategoriaMes(String Campanha, String Participante,String Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();



        try {


            sql = "select campanha,desccampanha,participante,nomeparticipante,categoria,desccategoria,data,sum(objetivo),sum(carteira),sum(real),sum(premio) "+
                    "from campanha ";
            sql += "where campanha = '" + Campanha + "' and  participante = '" + Participante + "' and data = '"+Periodo+"' ";
            sql += "group by campanha,desccampanha,participante,nomeparticipante,categoria,desccategoria,data ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    retorno.add(new Campanha_fast(
                            cursor.getString(6),
                            "",
                            "",
                            "",
                            cursor.getString(0),
                            cursor.getString(2),
                            cursor.getString(4),
                            "",
                            cursor.getFloat(7),
                            cursor.getFloat(8),
                            cursor.getFloat(9),
                            cursor.getFloat(10),
                            "",
                            "",
                            "",
                            cursor.getString(1),
                            cursor.getString(3),
                            cursor.getString(5),
                            "",
                            "T"


                    ));

                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;
    }

    public List<Campanha_fast> getCampanhaParticipanteCategoriaMarca(String Campanha, String Participante,String Categoria,String[] Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();



        try {


            sql = "select campanha,desccampanha,participante,nomeparticipante,categoria,desccategoria,marca,descmarca,sum(objetivo),sum(carteira),sum(real),sum(premio) "+
                    "from campanha ";

            sql += "where campanha = '" + Campanha + "' and  participante = '" + Participante + "' and categoria = '"+Categoria+"' and data in ('" + Periodo[0] + "','" + Periodo[1] + "') ";

            sql += "group by campanha,desccampanha,participante,nomeparticipante,categoria,desccategoria,marca,descmarca ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    if (!cursor.getString(6).trim().isEmpty()) {
                        retorno.add(new Campanha_fast(

                                "",
                                "",
                                "",
                                "",
                                cursor.getString(0),
                                cursor.getString(2),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getFloat(8),
                                cursor.getFloat(9),
                                cursor.getFloat(10),
                                cursor.getFloat(11),
                                "",
                                "",
                                "",
                                cursor.getString(1),
                                cursor.getString(3),
                                cursor.getString(5),
                                cursor.getString(7),
                                "M"


                        ));
                    }
                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;
    }

    public List<Campanha_fast> getCampanhaParticipanteCategoriaMarcaMes(String Campanha, String Participante,String Categoria,String Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();



        try {


            sql = "select campanha,desccampanha,participante,nomeparticipante,categoria,desccategoria,marca,descmarca,data,sum(objetivo),sum(carteira),sum(real),sum(premio) "+
                    "from campanha ";

            sql += "where campanha = '" + Campanha + "' and  participante = '" + Participante + "' and categoria = '"+Categoria+"' and data = '" + Periodo + "' ";

            sql += "group by campanha,desccampanha,participante,nomeparticipante,categoria,desccategoria,marca,descmarca,data ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {

                    if (!cursor.getString(6).trim().isEmpty()) {
                        retorno.add(new Campanha_fast(

                                cursor.getString(8),
                                "",
                                "",
                                "",
                                cursor.getString(0),
                                cursor.getString(2),
                                cursor.getString(4),
                                cursor.getString(6),
                                cursor.getFloat(9),
                                cursor.getFloat(10),
                                cursor.getFloat(11),
                                cursor.getFloat(12),
                                "",
                                "",
                                "",
                                cursor.getString(1),
                                cursor.getString(3),
                                cursor.getString(5),
                                cursor.getString(7),
                                "M"


                        ));
                    }
                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;
    }

    public List<Campanha_fast> getCampanhaParticipanteBimestre(String Campanha, String Participante, String[] Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();

        try {


            sql = "select campanha,desccampanha,participante,nomeparticipante,sum(objetivo),sum(carteira),sum(real),sum(premio) "+
                    "from campanha ";
            sql += "where campanha = '" + Campanha + "' and  participante = '" + Participante + "' and data in ('" + Periodo[0] + "','" + Periodo[1] + "') ";


            sql += "group by campanha,desccampanha,participante,nomeparticipante ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {


                    retorno.add(new Campanha_fast(

                            "",
                            "",
                            "",
                            "",
                            cursor.getString(0),
                            cursor.getString(2),
                            "",
                            "",
                            cursor.getFloat(4),
                            cursor.getFloat(5),
                            cursor.getFloat(6),
                            cursor.getFloat(7),
                            "",
                            "",
                            "",
                            cursor.getString(1),
                            cursor.getString(3),
                            "",
                            "",
                            "B"


                    ));

                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;
    }

    public List<Campanha_fast> getCampanhaParticipanteMeses(String Campanha, String Participante, String[] Periodo){

        Cursor cursor;

        String sql = "";

        List<Campanha_fast> retorno = new ArrayList<>();

        try {


            sql = "select campanha,desccampanha,participante,nomeparticipante,data,sum(objetivo),sum(carteira),sum(real),sum(premio) "+
                    "from campanha ";
            sql += "where campanha = '" + Campanha + "' and  participante = '" + Participante + "' and data in ('" + Periodo[0] + "','" + Periodo[1] + "') ";


            sql += "group by campanha,desccampanha,participante,nomeparticipante,data ";

            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                retorno  = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {


                    retorno.add(new Campanha_fast(

                            cursor.getString(4),
                            "",
                            "",
                            "",
                            cursor.getString(0),
                            cursor.getString(2),
                            "",
                            "",
                            cursor.getFloat(5),
                            cursor.getFloat(6),
                            cursor.getFloat(7),
                            cursor.getFloat(8),
                            "",
                            "",
                            "",
                            cursor.getString(1),
                            cursor.getString(3),
                            "",
                            "",
                            "E"


                    ));

                    cursor.moveToNext();

                }
            }

        } catch (Exception e){

            retorno = new ArrayList<>();
        }


        return retorno;
    }

    public void deleteMesAtual(){

        try

        {
            //Prepara as datas
            Date tempo;

            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));

            tempo = cal.getTime();
            String mesAtual = format.format(tempo);


            String sql = "delete * from campanha where data =  '"+mesAtual+"' ";

            try {

                getDataBase().beginTransactionNonExclusive();

                SQLiteStatement stmt = getDataBase().compileStatement(sql);

                stmt.execute();

                getDataBase().setTransactionSuccessful();

            }

            catch (SQLiteException e){

                throw new Exception(e.getMessage());

            }
            catch (Exception e){

                throw new Exception(e.getMessage());
            }
            finally {

                getDataBase().endTransaction();

            }

        }

        catch(Exception e){

            new Exception(e.getMessage());

        }

    }

}
