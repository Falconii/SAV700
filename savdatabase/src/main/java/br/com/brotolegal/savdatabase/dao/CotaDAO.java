package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import br.com.brotolegal.savdatabase.entities.Cota;
import br.com.brotolegal.savdatabase.regrasdenegocio.ParametroCota;

/**
 * Created by Falconi on 24/05/2016.
 */
public class CotaDAO extends DAO2 implements IDao2<Cota> {


    private final String TAG = "COTA";

    private String whereClausePrimary =  " codigo = ? ";



    private int    pageSize = 50;


    public CotaDAO() throws Exception {

        super("COTA","dbuser");

    }



    @Override
    public Cota insert(Cota obj) {

        Cursor cursor = null;

        ContentValues values = getContentValues(obj) ;

        try

        {
            long insertId = getDataBase().insert(obj.getFileName(), null, values);

            if (insertId != -1){

                cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getCODIGO()},null, null, null);

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
    public Integer Delete(String[] values)  {

        int regs = 0;

        try {

            regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0]});

        } catch (Exception e) {

            new Exception(e.getMessage());

        }

        return regs;


    }

    @Override
    public Boolean Update(Cota obj) {
        int retorno = 0;

        try {

            ContentValues values = getContentValues(obj);

            retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getCODIGO(),obj.getCODCLI(),obj.getLOJA(),obj.getREDE(),obj.getCANAL(),obj.getREGIAO(),obj.getSMARCA(),obj.getPRODUTO()});

        } catch (Exception e){

            new Exception(e.getMessage());

        }


        return retorno == 0 ? false : true;

    }

    @Override
    public Cota cursorToObj(Cursor cursor) {

        Cota obj = null;

        try {



            obj = new Cota(
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
                    cursor.getFloat(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getFloat(16),
                    cursor.getFloat(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20)




                    );

            if (cursor.getColumnCount() > 19 ) {

                obj.virtuais(

                        cursor.getString(21),
                        cursor.getString(22),
                        cursor.getString(23),
                        cursor.getString(24),
                        cursor.getString(25)
                );

            }

        } catch (Exception e){

            new Exception(e.getMessage());

        }

        return obj;

    }

    @Override
    public List<Cota> getAll() {

        Cursor cursor = null;

        List<Cota> result = new ArrayList<>();

        try {

            String sql = "";

            sql = "select * from " + getRegistro().getFileName() + " order by codigo ";

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
    public Cota seek(String[] values) {

        Cota obj = null;

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


    public List<Cota> getCota(ParametroCota param) {


        Cursor cursor = null;

        List<Cota> result = new ArrayList<>();

        try {

            String sql = "";

            sql =   "select "+
                    "ifnull(cota.codigo,'') , "+
                    "ifnull(cota.geren,'') , "+
                    "ifnull(cota.super,'') , "+
                    "ifnull(cota.vend,'') , "+
                    "ifnull(cota.codcli,'') , "+
                    "ifnull(cota.loja,'') , "+
                    "ifnull(cota.rede,'') , "+
                    "ifnull(cota.canal,'') , "+
                    "ifnull(cota.regiao,'') , "+
                    "ifnull(cota.smarca,'') , "+
                    "ifnull(cota.produto,'') , "+
                    "ifnull(cota.preco,'') , "+
                    "ifnull(cota.utilcontr,'') , "+
                    "ifnull(cota.utiltx,'') , "+
                    "ifnull(cota.utildna,'') , "+
                    "ifnull(cota.utilcota,'') , "+
                    "ifnull(cota.qtdcota,'') , "+
                    "ifnull(cota.sldcota,'') , "+
                    "ifnull(cota.dtentinicial,'') , "+
                    "ifnull(cota.dtentfinal,'') , "+
                    "ifnull(cota.descricao,'') , "+
                    "ifnull(cliente.razaopa, '') ,  "+
                    " '' as _REDE,   " +
                    " '' as _CANAL,  " +
                    " '' as _SMARCA, " +
                    "ifnull(produto.descricao,'')   "+
                    " from cota "+
                    " left join cliente on cliente.codigo = cota.codcli  and cliente.loja = cota.loja "+
                    " left join produto on produto.codigo = cota.produto  ";
 //                   " where "+
 //                   " trim(cota.codcli)  = 'X' and trim(cota.loja)  = 'Y'  )";
//                    "         ( ( trim(cota.codcli)  = '' and trim(cota.loja)  = ''  ) or (cota.codcli = '"+param.getCliente()+"' and cota.loja  = '"+param.getLoja()+"' ) ) and " +
//                    "         ( ( trim(cota.rede)    = '' )  or (cota.rede     = '"+param.getRede()   +"' ) )     and " +
//                    "         ( ( trim(cota.canal)   = '' )  or (cota.canal    = '"+param.getCanal()  +"' ) )     and " +
//                    "         ( ( trim(cota.regiao)  = '' )  or (cota.regiao   = '"+param.getRegiao() +"' ) )     and " +
//                    "         ( ( trim(cota.smarca)  = '' )  or (cota.smarca   = '"+param.getSmarca() +"' ) )     and " +
//                    "         ( ( trim(cota.produto) = '' )  or (cota.produto  = '"+param.getProduto()+"' ) )       ";

            Log.i("COTA",sql);
            cursor = getDataBase().rawQuery(sql, null);

            if (!cursor.moveToFirst()){

                result = new ArrayList<>();

            } else {

                while (!(cursor.isAfterLast())) {


                    if ( ( (cursor.getString(4).trim().isEmpty() && cursor.getString(5).trim().isEmpty()) || (cursor.getString(4).equals(param.getCliente()) && cursor.getString(5).equals(param.getLoja())))  &&
                            (   (cursor.getString(6).trim().isEmpty() )  || (cursor.getString(6).equals(param.getRede()) )) &&
                            (   (cursor.getString(7).trim().isEmpty() )  || (cursor.getString(7).equals(param.getCanal()) )) &&
                            (   (cursor.getString(8).trim().isEmpty() )  || (cursor.getString(8).equals(param.getRegiao()) )) &&
                            (   (cursor.getString(9).trim().isEmpty() )  || (cursor.getString(9).equals(param.getSmarca()) )) &&
                            (   (cursor.getString(10).trim().isEmpty() ) || (cursor.getString(10).equals(param.getProduto()) ))

                     ) {

                        result.add(cursorToObj(cursor));

                    };

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
