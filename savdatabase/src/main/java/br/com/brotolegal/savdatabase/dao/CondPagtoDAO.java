package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.CondPagto;


public class CondPagtoDAO  extends DAO2 implements IDao2<CondPagto> {


	private final String TAG = "CONDPAGTO";

	private String whereClausePrimary = " codigo = ? ";

	public CondPagtoDAO() throws Exception {

		super("CONDPAGTO","dbuser");

	}

	@Override
	public CondPagto insert(CondPagto obj) {

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
	public Integer Delete(String[] values) {
		int regs = 0;

		try {

			regs = getDataBase().delete(getRegistro().getFileName(), " codigo = ? ", new String[] {values[0]});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return regs;

	}

	@Override
	public Boolean Update(CondPagto obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getCODIGO()});

		} catch (Exception e){

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;

	}

	@Override
	public CondPagto cursorToObj(Cursor cursor) {

		CondPagto condpagto = null;
		try {
			condpagto = new CondPagto(

					cursor.getString(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getInt(3),
					cursor.getString(4),
					cursor.getString(5)


					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return condpagto;

	}

	@Override
	public List<CondPagto> getAll() {
		Cursor cursor = null;

		List<CondPagto> result = new ArrayList<>();

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
	public CondPagto seek(String[] values) {
		CondPagto obj = null;

		try{

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0]},null, null, null);

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

	public List<CondPagto> seekMedia(int media) {

		List<CondPagto> lsCondPagto = new ArrayList<CondPagto>();

		try {

			String whereClause = "  media <= ? ";

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClause, new String[] {String.valueOf(media)},null, null, null);

			if (!cursor.moveToFirst()){

				lsCondPagto = new ArrayList<CondPagto>();

			} else {

				while (!(cursor.isAfterLast())) {

					lsCondPagto.add(cursorToObj(cursor));

					cursor.moveToNext();
				}
			}


		} catch (Exception e){

			new Exception(e.getMessage());

		}


		return lsCondPagto;
	}

}

