package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.TabPrecoDet;


public class TabPrecoDetDAO  extends DAO2 implements IDao2<TabPrecoDet> {



	private final String TAG = "TABPRECODETDAO";

	private String whereClausePrimary = " codigo = ? ";

	public TabPrecoDetDAO() throws Exception {
		super("TABPRECODET","dbuser");
	}


	@Override
	public TabPrecoDet insert(TabPrecoDet obj) {
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

			regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[]{values[0]});

		} catch (Exception e) {

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return regs;

	}

	@Override
	public Boolean Update(TabPrecoDet obj) {
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
	public TabPrecoDet cursorToObj(Cursor cursor) {

		TabPrecoDet tabprecodet = null;
		try {
			tabprecodet = new TabPrecoDet(
					cursor.getString(0),
					cursor.getString(1),
					cursor.getFloat(2),
					cursor.getFloat(3),
					cursor.getFloat(4),
					cursor.getString(5),
					cursor.getFloat(6),
					cursor.getFloat(7),
					cursor.getFloat(8),
					cursor.getFloat(9),
					cursor.getFloat(10),
					cursor.getFloat(11)

					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return tabprecodet;
	}

	@Override
	public List<TabPrecoDet> getAll() {
		Cursor cursor = null;

		List<TabPrecoDet> result = new ArrayList<>();

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
	public TabPrecoDet seek(String[] values) {

		TabPrecoDet obj = null;

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
