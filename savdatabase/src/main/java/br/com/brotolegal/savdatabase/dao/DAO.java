package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import br.com.brotolegal.savdatabase.LibFile.Dicionario;
import br.com.brotolegal.savdatabase.database.DBHelper;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.database.getDBHelper;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionDicionario;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionInsertFast;


public class DAO {
    private ObjRegister registro;
	private SQLiteDatabase 	database;
	private DBHelper dbHelper;
	private String[]       	allColumns;

	public DAO(Context context, String cuser, ObjRegister registro){
		
		this.registro   = registro;
		this.dbHelper   = getDBHelper.dbhelper;
		this.allColumns = registro.getColumn(); 
		
	}
	
	public void setRegistro(ObjRegister registro) {
		this.registro = registro;
		this.allColumns = registro.getColumn(); 

	}



	public void setDbHelper(Context context,String cUser) {
		this.dbHelper   = new DBHelper(context,cUser);
	}
	



	public DAO(Context context, String cuser){
		
		this.registro   = registro;
		this.dbHelper   = new DBHelper(context,cuser);
		
	}


	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	
	public SQLiteDatabase getDataBase(){
		
		return database;
		
	}

	
	public void validaDicionario(String arquivo,List<Dicionario> lsDicionario) throws ExceptionDicionario, Exception {
		
			
		String[] colunas = dbHelper.getColunas(arquivo);
		
		if (colunas.length != lsDicionario.size()){
			
			throw new ExceptionDicionario("Arquivo: "+arquivo+" Tamanho DB = "+String.valueOf(colunas.length)+ " Difere Do Tamanho Do Dicionario: "+String.valueOf(lsDicionario.size()));
			
		}		
		
	}

    protected String[] getAllColumns(){
		
		return allColumns;
		
	}


    protected ObjRegister getRegistro(){
		
		return registro;
		
	}
   

	protected ContentValues getContentValues(ObjRegister obj){


			ContentValues values = new ContentValues();
			
			for(int i = 0;i < allColumns.length; i++){
				
				String type = registro.getTypeByName(allColumns[i]);
				
				if (type.equals(ObjRegister._string)){
					
					values.put(allColumns[i],(String) obj.getFieldByName(allColumns[i]));
					
				}

				if (type.equals(ObjRegister._float)){
					
					
					values.put(allColumns[i],(Float) obj.getFieldByName(allColumns[i]));
					
				}

				if (type.equals(ObjRegister._long)){
					
					
					values.put(allColumns[i],(Long) obj.getFieldByName(allColumns[i]));
					
				}

				if (type.equals(ObjRegister._integer)){
					
					
					values.put(allColumns[i],(Integer) obj.getFieldByName(allColumns[i]));
					
				}

			}
			
			return values;

		}

	public Boolean InsetFast( List<String[]> dados) throws ExceptionInsertFast

	{

		String insert   = getRegistro().getInsertString();

		Boolean retorno = false;

		try

		{

			String sql = insert;

			try {

				getDataBase().beginTransactionNonExclusive();

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

	public Boolean InsetFast( List<String[]> dados, String insert)

	{

		Boolean retorno = false;

		try

		{

			String sql = insert;

			try {


				getDataBase().beginTransactionNonExclusive();

				SQLiteStatement stmt = getDataBase().compileStatement(sql);

				for (String[] dado : dados) {

					for( int y = 1; y < dado.length ; y ++){

						stmt.bindString(y, dado[y]);

					}

					stmt.execute();

					stmt.clearBindings();

				}

				getDataBase().setTransactionSuccessful();

				retorno = true;

			} catch (SQLiteException e){

				new Exception(e.getMessage());

			}
			finally {

				getDataBase().endTransaction();

			}

		}

		catch(Exception e){

			new Exception(e.getMessage());

		}

		return retorno;

	}

	
}
