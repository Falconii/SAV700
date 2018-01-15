package br.com.brotolegal.savdatabase.database;

/**
 * Created by Falconi on 22/10/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Dispositivo;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionDicionario;


public class DBAp extends SQLiteOpenHelper {

    private static final String TAG =           "DBAp";

    /* Data Base */
    private static final int           DATABASE_VERSION = 4;
    private static final String        DATABASE_NAME    = "sistema";

    private static SQLiteDatabase   db;
    private static DBAp instance;
    private static Context          context;


    List<Tabela> lsTabelas = new ArrayList<Tabela>();
    List<ObjRegister> lsTables = new ArrayList<ObjRegister>();



    public DBAp(Context context) {


        super(context, App.PathDB + "/" +  DATABASE_NAME, null, DATABASE_VERSION);

        DBAp.context =  context;

        lsTabelas.add(new Tabela(new int[] {0}	    , "", new Config()));
        lsTabelas.add(new Tabela(new int[] {0}	    , "", new Dispositivo()));
        lsTabelas.add(new Tabela(new int[] {0}	    , "", new Usuario()));
        lsTabelas.add(new Tabela(null	            , "", new Status()));

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.beginTransaction();

        try {

            for (Tabela tabela : lsTabelas) {

                Log.e(TAG, tabela.getCREATESTRING());

                db.execSQL(tabela.getCREATESTRING());

            }

            db.setTransactionSuccessful();

        } catch (SQLException sqle) {

            Log.e(TAG, sqle.getMessage());

        } finally {

            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "";

        int    bak = 100;
        //FAZ BACKUP
        db.beginTransaction();

        try {

            copyFileToOldVersion(db,"CONFIG",100);

            copyFileToOldVersion(db,"DISPOSITIVO",100);

            copyFileToOldVersion(db,"USUARIO"  ,100);

            copyFileToOldVersion(db,"STATUS" ,100);

            db.setTransactionSuccessful();

        } catch (SQLException sqle) {

            Log.e(TAG, sqle.getMessage());

        } catch (Exception e) {

            Log.e(TAG, e.getMessage());

        } finally {

            db.endTransaction();
        }


        //PROCESSAMENTO
        db.beginTransaction();

        try {


            for (Tabela tabela : lsTabelas) {

                Log.e(TAG, "DROP TABLE IF EXISTS " + tabela.getNOME());

                db.execSQL("DROP TABLE IF EXISTS " + tabela.getNOME());

            }

            db.setTransactionSuccessful();

        } catch (SQLException sqle) {

            Log.e(TAG, sqle.getMessage());

        } catch (Exception e) {

            Log.e(TAG, e.getMessage());

        } finally {

            db.endTransaction();
        }


        onCreate(db);

        //FAZ VOLTA BACKUP
        db.beginTransaction();

        try {

            copyOldVersionToNewVersion(db,"CONFIG"     ,100);

            copyOldVersionToNewVersion(db,"DISPOSITIVO",100);

            copyOldVersionToNewVersion(db,"USUARIO"    ,100);

            copyOldVersionToNewVersion(db,"STATUS"     ,100);

            db.setTransactionSuccessful();

        } catch (SQLException sqle) {

            Log.e(TAG, sqle.getMessage());

        } catch (Exception e) {

            Log.e(TAG, e.getMessage());

        } finally {

            db.endTransaction();
        }

    }

    private void copyFileToOldVersion(SQLiteDatabase db,String tabela,int oldVersion) throws Exception {

        String oldTabela = tabela+"_"+String.valueOf(oldVersion);

        String sql = "";

        try {

            sql = "DROP TABLE IF EXISTS " + oldTabela;

            db.execSQL(sql);

            sql = "create table "+oldTabela+" as select * from "+tabela;

            db.execSQL(sql);

            Log.i(TAG,"Copiado Arquivo "+tabela);

        } catch (SQLException sqle) {

            Log.e(TAG, sqle.getMessage());

            throw   new Exception(sqle.getMessage());

        }

    }

    private void copyOldVersionToNewVersion(SQLiteDatabase db,String tabela,int oldVersion) throws Exception {

        String oldTabela = tabela+"_"+String.valueOf(oldVersion);

        String insertString = getInsertFromTable(db,oldTabela);

        String sql = "";

        try {

            sql = "insert into "+tabela+" ("+insertString+") select "+insertString+"  from "+oldTabela;

            db.execSQL(sql);

            Log.i(TAG,"Restaurado Arquivo "+oldTabela);

        } catch (SQLException sqle) {

            Log.e(TAG, sqle.getMessage());

            throw new Exception(sqle.getMessage());

        }
    }

    private String getInsertFromTable(SQLiteDatabase db,String tabela){

        String retorno = "";

        String sql = "select * from "+tabela+" LIMIT 0";

        Cursor rs = db.rawQuery(sql, null);

        rs.moveToFirst();

        for(int x = 0; x < rs.getColumnCount(); x++){

            retorno += rs.getColumnName(x);

            if ( x != rs.getColumnCount()-1) retorno += ", ";

        }

        return retorno;

    }

    public String[]  getColunas(String arquivo) throws Exception{

        String[] retorno = null;

        try {

            for (Tabela tab : lsTabelas){

                if (tab.getNOME().equals(arquivo)){

                    retorno = tab.getOBJ().getColumn();

                }


            }


        } catch (Exception e) {

            throw new ExceptionDicionario(e.getMessage());

        }

        return retorno ;
    }


    @Override
    public synchronized void close() {
        if (instance != null)
            db.close();
    }

    public static synchronized DBAp getInstance(Context context) {
        if (instance == null) {

            instance = new DBAp(context);

            db = instance.getWritableDatabase();
        }

        return instance;
    }


    public ObjRegister getObjeto(String arquivo){

        ObjRegister obj = null;

        for (Tabela tab : lsTabelas){

            if (tab.getNOME().equalsIgnoreCase(arquivo)){

                obj = tab.getOBJ();

                break;

            }

        }

        return obj;

    }



    //inner class

    private class Tabela {

        private  String       NOME;
        private  String       CREATESTRING;
        private ObjRegister OBJ;

        public Tabela(int[] chaves, String complemento,ObjRegister obj){

            NOME          = obj._FILENAME;

            OBJ           = obj;

            CREATESTRING  = OBJ.getCreateString(chaves, complemento);

        }

        public String getNOME() {
            return NOME;
        }

        public void setNOME(String nOME) {
            NOME = nOME;
        }

        public String getCREATESTRING() {

            return CREATESTRING;

        }

        public ObjRegister getOBJ(){

            return OBJ;

        }

    }


}
