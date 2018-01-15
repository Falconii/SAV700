package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.entities.Canal;
import br.com.brotolegal.savdatabase.entities.Marca;
import br.com.brotolegal.savdatabase.entities.Meta;
import br.com.brotolegal.savdatabase.entities.PontosGame;
import br.com.brotolegal.savdatabase.regrasdenegocio.MetaCategoria;


public class MetaDAO extends DAO2 implements IDao2<Meta> {

	private final String TAG = "META";

	private String whereClausePrimary = "data = ? and gnv = ? and ga = ? and vend = ? and  segmento = ? and cliente = ? and loja = ? and categoria = ? and marca = ? and produto = ?";

	public MetaDAO() throws Exception {

		super("META", "dbuser");

	}

	@Override
	public Meta insert(Meta obj) {

		Cursor cursor = null;

		ContentValues values = getContentValues(obj);

		try

		{

			long insertId = getDataBase().insert(obj.getFileName(), null, values);

			if (insertId != -1) {

				cursor = getDataBase().query(obj.getFileName(), obj.getColumn(), whereClausePrimary, new String[]{obj.getDATA(), obj.getGNV(), obj.getGA(), obj.getVEND(), obj.getSEGMENTO(), obj.getCLIENTE(), obj.getLOJA(), obj.getCATEGORIA(), obj.getMARCA(), obj.getPRODUTO()}, null, null, null);

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

			regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[]{values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9]});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return regs;
	}

	@Override
	public Boolean Update(Meta obj) {

		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getDATA(), obj.getGNV(), obj.getGA(), obj.getVEND(), obj.getSEGMENTO(), obj.getCLIENTE(), obj.getLOJA(), obj.getCATEGORIA(), obj.getMARCA(), obj.getPRODUTO()});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;


	}

	@Override
	public Meta cursorToObj(Cursor cursor) {

		Meta meta = null;

		try {

			meta = new Meta(
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
					cursor.getFloat(11),
					cursor.getFloat(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getString(16),
					cursor.getString(17),
					cursor.getString(18),
					cursor.getString(19),
					cursor.getString(20)

			);

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return meta;
	}

	@Override
	public List<Meta> getAll() {

		Cursor cursor = null;

		List<Meta> result = new ArrayList<>();

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
	public Meta seek(String[] values) {

		Meta obj = null;

		try {

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(), whereClausePrimary, new String[]{values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9]}, null, null, null);

			if (cursor.moveToFirst()) {

				obj = cursorToObj(cursor);

			} else {

				//retorno null
			}
		} catch (Exception e) {

			new Exception(e.getMessage());

		}


		return obj;
	}

	public List<MetaCategoria> getMetaByClienteCateroria(String Cliente, String Loja) {

		Cursor cursor;

		List<MetaCategoria> result = new ArrayList<>();

		try {

			String sql = "";

			sql = " select " +
					" meta.data,meta.cliente,meta.loja,meta.categoria," +
					" meta.nomecliente,meta.desccategoria," +
					" sum(meta.objetivo),sum(meta.carteira),sum(meta.real) from meta ";
			sql += " where meta.data = '" + App.getHojeaaaamm() + "' ";

			if (!Cliente.trim().isEmpty()) {

				sql += " and meta.cliente = '" + Cliente + "' and meta.loja = '" + Loja + "' ";

			}

			sql += " group by meta.data,meta.cliente,meta.loja,meta.categoria,meta.nomecliente,meta.desccategoria ";
			sql += " order by meta.data,meta.cliente,meta.loja,meta.categoria,meta.nomecliente,meta.desccategoria ";


			cursor = getDataBase().rawQuery(sql, null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursorToMetaCategoria(cursor));

					cursor.moveToNext();

				}
			}


		} catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;

	}


	public MetaCategoria cursorToMetaCategoria(Cursor cursor) {

		MetaCategoria obj = null;

		try {

			obj = new MetaCategoria(
					cursor.getString(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4),
					cursor.getString(5),
					cursor.getFloat(6),
					cursor.getFloat(7),
					cursor.getFloat(8)

			);

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return obj;

	}


	public void deleteMetaMes(){

		try

		{
			//Prepara as datas
			Date tempo;

			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));

			tempo = cal.getTime();
			String mesAtual = format.format(tempo);

			cal.add(Calendar.MONTH, -1);

			tempo = cal.getTime();
			String mesPassado = format.format(tempo);

			String sql = "delete * from meta where data = '"+mesPassado+"' or data = '"+mesAtual+"' ";

			try {

				getDataBase().beginTransactionNonExclusive();

				SQLiteStatement stmt = getDataBase().compileStatement(sql);

				stmt.execute();

				getDataBase().setTransactionSuccessful();

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
