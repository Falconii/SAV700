package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Verba;


public class VerbaDAO  extends DAO2 implements IDao2<Verba> {

	private final String TAG = "VERBADAO";

	private String whereClausePrimary = " codigo = ? ";

	public VerbaDAO() throws Exception {

		super("VERBA","dbuser");

	}

	@Override
	public Verba insert(Verba obj) {

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

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return obj;


	}

	@Override
	public Integer Delete(String[] values) {

		int regs = 0;

		try {

			regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0]});

		} catch (Exception e) {

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return regs;


	}

	@Override
	public Boolean Update(Verba obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getCODIGO()});

		} catch (Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;

	}

	@Override
	public Verba cursorToObj(Cursor cursor) {
		Verba verba = null;
		try {
			verba = new Verba(

					cursor.getString(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4)

					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return verba;

	}

	@Override
	public List<Verba> getAll() {

		Cursor cursor = null;

		List<Verba> result = new ArrayList<>();

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
	public Verba seek(String[] values) {

		Verba obj = null;

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


	public List<Verba> getAllByTipo(String tipo) {

		Cursor cursor = null;

		List<Verba> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName()+ " where tipo = '"+tipo+"' order by descricao " , null);

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
}
