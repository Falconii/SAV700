package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.List;

import br.com.brotolegal.savdatabase.LibFile.Dicionario;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionDicionario;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionInsertFast;


public class DAODados {
    private String TAG      = "DAODados";
    private SQLiteDatabase  database;
    private String[]        allColumns;
    private ObjRegister registro;

    public DAODados(String table) throws ExceptionDicionario,Exception {

        this.allColumns = App.dbuser.getColunas(table);
        this.registro   = App.dbuser.getObjeto(table);

    }



    public ObjRegister getRegistro() {
        return registro;
    }

    public void open() throws SQLException {

        database = App.dbuser.getWritableDatabase();

    }

    public void close() {
        App.dbuser.close();
    }


    public SQLiteDatabase getDataBase(){

        return database;

    }


    public void validaDicionario(String arquivo,List<Dicionario> lsDicionario) throws ExceptionDicionario, Exception {


        String[] colunas = App.dbuser.getColunas(arquivo);

        if (colunas.length != lsDicionario.size()){

            throw new ExceptionDicionario("Arquivo: "+arquivo+" Tamanho DB = "+String.valueOf(colunas.length)+ " Difere Do Tamanho Do Dicionario: "+String.valueOf(lsDicionario.size()));

        }

    }

    protected String[] getAllColumns(){

        return allColumns;

    }


    protected ContentValues getContentValues(ObjRegister obj){

        ContentValues values = new ContentValues();

        try {


            for (int i = 0; i < allColumns.length; i++) {

                String type = obj.getTypeByName(allColumns[i]);

                if (type.equals(ObjRegister._string)) {

                    values.put(allColumns[i], (String) obj.getFieldByName(allColumns[i]));

                }

                if (type.equals(ObjRegister._float)) {


                    values.put(allColumns[i], (Float) obj.getFieldByName(allColumns[i]));

                }

                if (type.equals(ObjRegister._long)) {


                    values.put(allColumns[i], (Long) obj.getFieldByName(allColumns[i]));

                }

                if (type.equals(ObjRegister._integer)) {


                    values.put(allColumns[i], (Integer) obj.getFieldByName(allColumns[i]));

                }

            }
        } catch (Exception e){


            Log.i(TAG, obj.getFileName() + "-" + e.getMessage());


        }

        return values;

    }

    public Boolean InsetFast( List<String[]> dados) throws ExceptionInsertFast

    {
        String insert   = registro.getInsertString();

        Boolean retorno = false;

        try

        {

            String sql = insert;

            try {

                getDataBase().beginTransactionNonExclusive();

                getDataBase().delete(registro.getFileName(),null,null);

                SQLiteStatement stmt = getDataBase().compileStatement(sql);

                for (String[] dado : dados) {

                    for( int y = 0; y < dado.length ; y ++){

                        stmt.bindString(y+1, dado[y]);

                    }

                    stmt.execute();

                    stmt.clearBindings();

                }

                getDataBase().setTransactionSuccessful();

                retorno = true;

            } catch (SQLiteException e){

                throw new ExceptionInsertFast(e.getMessage());

            } catch (Exception e){

                throw new ExceptionInsertFast(e.getMessage());

            }
            finally {

                getDataBase().endTransaction();

            }

        }

        catch(Exception e){

            throw new ExceptionInsertFast(e.getMessage());

        }

        return retorno;

    }

    public void InsetFast( List<String[]> dados, String insert, Boolean deleteAll) throws Exception

    {

        Boolean retorno = false;

        try

        {

            String sql = insert;

            try {

                getDataBase().beginTransactionNonExclusive();

                if (deleteAll) getDataBase().delete(registro.getFileName(),null,null);

                SQLiteStatement stmt = getDataBase().compileStatement(sql);

                for (String[] dado : dados) {

                    for (int y = 1; y < dado.length; y++) {

                        stmt.bindString(y, dado[y]);

                    }

                    stmt.execute();

                    stmt.clearBindings();

                }

                getDataBase().setTransactionSuccessful();

                retorno = true;

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
