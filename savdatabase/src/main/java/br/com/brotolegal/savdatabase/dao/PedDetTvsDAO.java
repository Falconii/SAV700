package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.PedDetTvs;
import br.com.brotolegal.savdatabase.regrasdenegocio.ChavePedDistribuicao;

public class PedDetTvsDAO  extends DAO2 implements IDao2<PedDetTvs> {

	private final String TAG = "PEDDETTVS";

	private String whereClausePrimary = " filial = ? and pedido = ? and item = ? ";

	private String whereClausePedido  = " filial = ? and pedido = ?  ";

	public PedDetTvsDAO() throws Exception {

		super("PEDDETTVS","dbuser");

	}

	@Override
	public PedDetTvs insert(PedDetTvs obj) {
		Cursor cursor = null;

		ContentValues values = getContentValues(obj);

		try

		{

			long insertId = getDataBase().insert(obj.getFileName(), null, values);

			if (insertId != -1) {

				cursor = getDataBase().query(obj.getFileName(), obj.getColumn(), whereClausePrimary, new String[]{obj.getFILIAL(),obj.getPEDIDO(),obj.getITEM()}, null, null, null);

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

			regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[]{values[0],values[1],values[2]});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return regs;
	}

	@Override
	public Boolean Update(PedDetTvs obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getFILIAL(),obj.getPEDIDO(),obj.getITEM()});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;

	}

	@Override
	public PedDetTvs cursorToObj(Cursor cursor) {

		PedDetTvs peddettvs = null;
		try {
			peddettvs = new PedDetTvs(

					cursor.getString(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4),
					cursor.getString(5),
					cursor.getFloat(6),
					cursor.getFloat(7),
					cursor.getFloat(8),
					cursor.getFloat(9),
					cursor.getString(10),
					cursor.getString(11),
					cursor.getFloat(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getString(16),
					cursor.getString(17)


					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return peddettvs;

	}

	@Override
	public List<PedDetTvs> getAll() {
		Cursor cursor = null;

		List<PedDetTvs> result = new ArrayList<>();

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
	public PedDetTvs seek(String[] values) {
		PedDetTvs obj = null;

		try{

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1],values[2]},null, null, null);

			if (cursor.moveToFirst())
			{

				obj =  cursorToObj(cursor);

			} else {

				obj = null;
			}
		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return obj;
	}


	public ChavePedDistribuicao seekByMobile(String[] values) {

        String sql    = "";

		ChavePedDistribuicao obj = null;

		try{

			Cursor cursor = null;

				sql =        "select peddettvs.filial,pedcabtvs.pedidomobile,peddettvs.item,peddettvs.prcven from peddettvs "+
						     "inner join pedcabtvs on pedcabtvs.filial = peddettvs.filial and pedcabtvs.pedido = peddettvs.pedido ";

				//where
				sql += " where ( pedcabtvs.pedidomobile = '"+values[1]+"' and peddettvs.item = '"+values[2]+"' )";

				//order by
				sql += " order by pedcabtvs.pedidomobile ";

				cursor = getDataBase().rawQuery(sql, null);

				if (!cursor.moveToFirst()) {

					obj = null;

				} else {

					while (!(cursor.isAfterLast())) {

						obj = new ChavePedDistribuicao(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3));

						cursor.moveToNext();

					}
				}


			} catch (Exception e) {

				Log.i(TAG, e.getMessage());

				new Exception(e.getMessage());

			}

			return obj;

	}

	public List<PedDetTvs> seekByPedido(String[] values) {

		Cursor cursor = null;

		List<PedDetTvs> result = new ArrayList<>();

		try {

			cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePedido, new String[] {values[0],values[1]},null, null, null);

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


}
