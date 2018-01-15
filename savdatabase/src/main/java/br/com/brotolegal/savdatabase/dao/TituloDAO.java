package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Titulo;

public class TituloDAO extends DAO2 implements IDao2<Titulo> {

	private final String TAG = "TITULO";

	private String whereClausePrimary = "  FILIAL = ? AND PREFIXO = ? AND NUM = ? ";

	public TituloDAO() throws Exception {

		super("TITULO","dbuser");

	}

	@Override
	public Titulo insert(Titulo obj) {

		Cursor cursor = null;

		ContentValues values = getContentValues(obj);

		try

		{

			long insertId = getDataBase().insert(obj.getFileName(), null, values);

			if (insertId != -1) {

				cursor = getDataBase().query(obj.getFileName(), obj.getColumn(), whereClausePrimary, new String[]{obj.getFILIAL(),obj.getPREFIXO(),obj.getNUM()}, null, null, null);

				cursor.moveToFirst();

				obj = cursorToObj(cursor);


			} else {

				obj = null;

			}

		} catch (Exception e) {

			Log.i("SAV", e.getMessage());

			new Exception(e.getMessage());

		}

		return obj;

	}

	@Override
	public Integer Delete(String[] values) {
		int regs = 0;

		try {

			regs = getDataBase().delete(getRegistro().getFileName(), " codigo = ? ", new String[]{values[0],values[1]});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return regs;
	}

	@Override
	public Boolean Update(Titulo obj) {

		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getFILIAL(),obj.getPREFIXO(),obj.getNUM()});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;

	}

	@Override
	public Titulo cursorToObj(Cursor cursor) {

		Titulo obj = null;
		try {
			obj     = new Titulo(
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
					cursor.getString(11),
					cursor.getString(12),
					cursor.getFloat(13),
					cursor.getFloat(14),
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
	public List<Titulo> getAll() {
		Cursor cursor = null;

		List<Titulo> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName(), null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursorToObj(cursor));

					cursor.moveToNext();

				}
			}


		} catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;

	}

	@Override
	public Titulo seek(String[] values) {

		Titulo obj = null;

		try{

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1]},null, null, null);

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
}
