package br.com.brotolegal.savdatabase.database;

/**
 * Created by Falconi on 22/10/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Acordo;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.Base01;
import br.com.brotolegal.savdatabase.entities.Campanha;
import br.com.brotolegal.savdatabase.entities.Canal;
import br.com.brotolegal.savdatabase.entities.Cidade;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.CondPagto;
import br.com.brotolegal.savdatabase.entities.ContaCorrente;
import br.com.brotolegal.savdatabase.entities.Contrato;
import br.com.brotolegal.savdatabase.entities.Cota;
import br.com.brotolegal.savdatabase.entities.DadosRanking;
import br.com.brotolegal.savdatabase.entities.Frete;
import br.com.brotolegal.savdatabase.entities.Game;
import br.com.brotolegal.savdatabase.entities.Grupo;
import br.com.brotolegal.savdatabase.entities.Imposto;
import br.com.brotolegal.savdatabase.entities.Marca;
import br.com.brotolegal.savdatabase.entities.Meta;
import br.com.brotolegal.savdatabase.entities.Motivo;
import br.com.brotolegal.savdatabase.entities.MotivosTrocaDev;
import br.com.brotolegal.savdatabase.entities.Negociacao;
import br.com.brotolegal.savdatabase.entities.NotaFiscalCab;
import br.com.brotolegal.savdatabase.entities.NotaFiscalDet;
import br.com.brotolegal.savdatabase.entities.Notificacao;
import br.com.brotolegal.savdatabase.entities.Ocorrencia;
import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.Politica;
import br.com.brotolegal.savdatabase.entities.PontosGame;
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.PreCliente;
import br.com.brotolegal.savdatabase.entities.Produto;
import br.com.brotolegal.savdatabase.entities.Prospeccao;
import br.com.brotolegal.savdatabase.entities.Receber;
import br.com.brotolegal.savdatabase.entities.Rede;
import br.com.brotolegal.savdatabase.entities.Regiao;
import br.com.brotolegal.savdatabase.entities.Simulador;
import br.com.brotolegal.savdatabase.entities.TabPrecoCabec;
import br.com.brotolegal.savdatabase.entities.TabPrecoDet;
import br.com.brotolegal.savdatabase.entities.Task;
import br.com.brotolegal.savdatabase.entities.Verba;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionDicionario;


public class DBUser extends SQLiteOpenHelper {

    private static final String TAG =  "DBUser";

    /* Data Base */
    private static final int           DATABASE_VERSION = 99;
    private static final String        DATABASE_NAME    = "dados";
    private static       String        DATABASE_PATH    = Environment.getExternalStorageDirectory().getPath();

    private static SQLiteDatabase   db;
    private static DBUser instance;
    private static Context          context;
    private static String           user;


    List<Tabela> lsTabelas     = new ArrayList<Tabela>();
    List<ObjRegister> lsTables = new ArrayList<ObjRegister>();


    public DBUser(Context context) {

        //super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + DATABASE_NAME, null, DATABASE_VERSION);

        super(context, DATABASE_PATH + "/SAV700/"+user+"/" + DATABASE_NAME, null, DATABASE_VERSION);

        DBUser.context =  context;

        //Cadastros
        lsTabelas.add(new Tabela(new int[]{0, 1}        , "", new Cliente()));
        lsTabelas.add(new Tabela(null                   , "", new Politica()));
        lsTabelas.add(new Tabela(new int[] {0}          , "", new Canal()));
        lsTabelas.add(new Tabela(new int[] {0}          , "", new CondPagto()));
        lsTabelas.add(new Tabela(new int[] {0}          , "", new TabPrecoCabec()));
        lsTabelas.add(new Tabela(new int[] {0,1}        , "", new TabPrecoDet()));
        lsTabelas.add(new Tabela(new int[] {0}          , "", new Marca()));
        lsTabelas.add(new Tabela(new int[] {0}          , "", new Grupo()));
        lsTabelas.add(new Tabela(new int[] {0}          , "", new Rede()));
        lsTabelas.add(new Tabela(new int[] {0}	        , "", new Regiao()));
        lsTabelas.add(new Tabela(new int[] {0}	        , "", new Cidade()));
        lsTabelas.add(new Tabela(new int[] {0,1,2}	    , "", new Imposto()));
        lsTabelas.add(new Tabela(new int[] {0,1,2,3,4}  , "", new Frete()));
        lsTabelas.add(new Tabela(new int[] {0,1}        , "", new Motivo()));
        lsTabelas.add(new Tabela(new int[] {0}      	, "", new Verba()));
        lsTabelas.add(new Tabela(new int[] {0,1,2,3,4}  , "", new Contrato()));
        lsTabelas.add(new Tabela(new int[] {0}	        , "", new Produto()));
        lsTabelas.add(new Tabela(new int[] {0}	        , "", new Acordo()));
        lsTabelas.add(new Tabela(new int[] {0,1,2,3,11} , "", new Simulador()));
        lsTabelas.add(new Tabela(new int[] {0}          , "", new PreAcordo()));
        lsTabelas.add(new Tabela(new int[] {0,1,2}      , "", new NotaFiscalCab()));
        lsTabelas.add(new Tabela(new int[] {0,1,2,6}    , "", new NotaFiscalDet()));
        lsTabelas.add(new Tabela(new int[] {0,1,2,3,4,5,6,7,8,9}    , "", new Meta()));
        lsTabelas.add(new Tabela(new int[] {0,}         , "", new Negociacao()));
        lsTabelas.add(new Tabela(new int[] {0,1,2,3,4,5,6,7}        , "", new Campanha()));
        lsTabelas.add(new Tabela(new int[] {0}          , "", new Cota()));
        //Gamefication
        lsTabelas.add(new Tabela(new int[] {0}	        , "", new Game()));
        lsTabelas.add(new Tabela(new int[] {1,2}        , "", new PontosGame()));
        lsTabelas.add(new Tabela(new int[] {0,1}	    , "", new DadosRanking()));

        //Pedidos
        lsTabelas.add(new Tabela(new int[] {0}	        , "", new PedidoCabMb()));
        lsTabelas.add(new Tabela(new int[] {0,1}	    , "", new PedidoDetMb()));
        lsTabelas.add(new Tabela(new int[] {0,1}        , "", new PedCabTvs()));
        lsTabelas.add(new Tabela(new int[] {0,1,2}	    , "", new PedDetTvs()));
        lsTabelas.add(new Tabela(new int[] {0,1,2,3,4}  , "", new Receber()));
        lsTabelas.add(new Tabela(new int[] {0,1}        , "", new Agendamento()));
        lsTabelas.add(new Tabela(null                   , "", new ContaCorrente()));
        lsTabelas.add(new Tabela(null                   , "", new Base01()));
        lsTabelas.add(new Tabela(new int[] {0,1}        , "", new MotivosTrocaDev()));

        //Pré-Cliente
        lsTabelas.add(new Tabela(new int[] {0}	        , "", new PreCliente()));

        //Prospeccao
        lsTabelas.add(new Tabela(new int[] {0}	        , "", new Prospeccao()));

        //Tarefas
        lsTabelas.add(new Tabela(new int[] {0}	    , "", new Notificacao()));
        lsTabelas.add(new Tabela(new int[] {0}	    , "", new Task()));
        lsTabelas.add(new Tabela(new int[] {0}	    , "", new Ocorrencia()));


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

                copyFileToOldVersion(db,"PEDIDOCABMB",100);

                copyFileToOldVersion(db,"PEDIDODETMB",100);

                copyFileToOldVersion(db,"PREACORDO"  ,100);

                copyFileToOldVersion(db,"AGENDAMENTO",100);

                //copyFileToOldVersion(db,"NEGOCIACAO" ,100);

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

                copyOldVersionToNewVersion(db,"PEDIDOCABMB",100);

                copyOldVersionToNewVersion(db,"PEDIDODETMB",100);

                copyOldVersionToNewVersion(db,"PREACORDO"  ,100);

                //copyOldVersionToNewVersion(db,"NEGOCIACAO" ,100);

                copyOldVersionToNewVersion(db,"AGENDAMENTO"  ,100);

                db.setTransactionSuccessful();

            } catch (SQLException sqle) {

                Log.e(TAG, sqle.getMessage());

            } catch (Exception e) {

                Log.e(TAG, e.getMessage());

            } finally {

                db.endTransaction();
            }

    }

    public String  getCreateString(String arquivo) throws Exception {

        String retorno = null;

        try {

            for (Tabela tab : lsTabelas){

                if (tab.getNOME().equals(arquivo)){

                    retorno = tab.getCREATESTRING();

                }

            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }

        return retorno ;
    }

    public String[]  getColunas(String arquivo) throws ExceptionDicionario,Exception{

        String[] retorno = null;

        try {

            for (Tabela tab : lsTabelas){

                if (tab.getNOME().equals(arquivo)){

                    retorno = tab.getOBJ().getColumn();

                }

            }

            if (retorno == null){

                throw new ExceptionDicionario("Tabela "+arquivo+" Não Existe No Tablete.");

            }


        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }

        return retorno ;
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

        Cursor  rs = db.rawQuery(sql, null);

        rs.moveToFirst();

        for(int x = 0; x < rs.getColumnCount(); x++){

            retorno += rs.getColumnName(x);

            if ( x != rs.getColumnCount()-1) retorno += ", ";

        }

        return retorno;

    }
    @Override
    public synchronized void close() {
        if (instance != null)
            db.close();
    }

    public static synchronized DBUser getInstance(Context context) {
        if (instance == null) {

            instance = new DBUser(context);

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

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {

        if (instance != null) db.close();

        DBUser.user = user;

        instance = null;


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
