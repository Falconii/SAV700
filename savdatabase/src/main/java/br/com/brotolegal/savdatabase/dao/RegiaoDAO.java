package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Regiao;

public class RegiaoDAO  extends DAO2 implements IDao2<Regiao> {

	private final String TAG = "REGIAODAO";

	private String whereClausePrimary = " codigo = ? ";

	public RegiaoDAO() throws Exception {
		super("REGIAO","dbuser");
	}


	@Override
	public Regiao insert(Regiao obj) {
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
	public Boolean Update(Regiao obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getCODIGO()});

		} catch (Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;

	}

	@Override
	public Regiao cursorToObj(Cursor cursor) {
		Regiao obj = null;

		try {
			obj = new Regiao(
					cursor.getString(0),
					cursor.getString(1)
			);

		} catch (Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return obj;
	}

	@Override
	public List<Regiao> getAll() {
		Cursor cursor = null;

		List<Regiao> result = new ArrayList<>();

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
	public Regiao seek(String[] values) {
		Regiao obj = null;

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
}
