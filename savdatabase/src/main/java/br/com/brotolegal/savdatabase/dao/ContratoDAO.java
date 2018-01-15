package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Contrato;

public class ContratoDAO  extends DAO2 implements IDao2<Contrato> {

	private final String TAG = "CONTRATO";

	private String whereClausePrimary = " codigo = ? ";

	public ContratoDAO() throws Exception {

		super("CONTRATO","dbuser");

	}

	@Override
	public Contrato insert(Contrato obj) {

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
	public Boolean Update(Contrato obj) {
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
	public Contrato cursorToObj(Cursor cursor) {
		Contrato contrato = null;
		try {
			contrato = new Contrato(
					cursor.getString(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4),
					cursor.getString(5),
					cursor.getString(6),
					cursor.getString(7),
					cursor.getFloat(8),
					cursor.getFloat(9),
					cursor.getFloat(10),
					cursor.getFloat(11),
					cursor.getString(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getFloat(16),
					cursor.getFloat(17),
					cursor.getFloat(18),
					cursor.getString(19),
					cursor.getFloat(20)


					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return contrato;
	}

	public Contrato cursorToObj2(Cursor cursor) {
		Contrato contrato = null;
		try {
			contrato = new Contrato(
					cursor.getString(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4),
					cursor.getString(5),
					cursor.getString(6),
					cursor.getString(7),
					cursor.getFloat(8),
					cursor.getFloat(9),
					cursor.getFloat(10),
					cursor.getFloat(11),
					cursor.getString(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getFloat(16),
					cursor.getFloat(17),
					cursor.getFloat(18),
					cursor.getString(19),
					cursor.getFloat(20)

			);



			if (contrato.getTIPO().equals("T")) {

				contrato.set_DESCRICAO("TODOS");

			}
			if (contrato.getTIPO().equals("C")) {

				if (cursor.getString(20) == null){

					contrato.set_DESCRICAO(contrato.getCATEGORIA());

				} else {

					contrato.set_DESCRICAO(cursor.getString(20));

				}

			}
			if (contrato.getTIPO().equals("G")) {

				if (cursor.getString(21) == null) {

					contrato.set_DESCRICAO(contrato.getMARCA());

				} else {

					contrato.set_DESCRICAO(cursor.getString(21));

				}
			}

			if (contrato.getTIPO().equals("P")) {

				if (cursor.getString(22) == null) {


					contrato.set_DESCRICAO(contrato.getPRODUTO());

				} else {

					contrato.set_DESCRICAO(cursor.getString(22));

				}

			}




			if (cursor.getString(23) == null) {

				contrato.set_REDE(contrato.getREDE());

			} else {

				contrato.set_REDE(contrato.getREDE() + "-"+ cursor.getString(23));

			}


		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return contrato;
	}
	@Override
	public List<Contrato> getAll() {
		Cursor cursor = null;

		List<Contrato> result = new ArrayList<>();

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
	public Contrato seek(String[] values) {
		Contrato obj = null;

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


	public List<Contrato> getSeekFull(String Codigo){

		List<Contrato> lsContratos = new ArrayList<Contrato>();

		try {

			String whereClause = " CODIGO = ? ";

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClause, new String[] {Codigo},null, null, null);

			if (!cursor.moveToFirst()){

				lsContratos = new ArrayList<Contrato>();

			} else {
				while (!(cursor.isAfterLast())) {

					lsContratos.add(cursorToObj(cursor));

					cursor.moveToNext();

				}
			}


		} catch (Exception e){

			new Exception(e.getMessage());

		}


		return lsContratos;
	}

	public List<Contrato> getSeekFull2(String Codigo){

		List<Contrato> lsContratos = new ArrayList<Contrato>();

		try {

			String sql = "select CONTRATO.*,GRUPO.descricao,MARCA.descricao,PRODUTO.descricao,REDE.descricao "+
					" from " + getRegistro().getFileName() +
					" left join GRUPO   on GRUPO.codigo   = CONTRATO.categoria  "+
					" left join MARCA   on MARCA.codigo   = CONTRATO.marca  "+
					" left join PRODUTO on PRODUTO.codigo = CONTRATO.produto  "+
					" left join REDE    on REDE.codigo    = CONTRATO.rede  "+
					" where CONTRATO.codigo = '"+Codigo+"' ORDER BY CONTRATO.categoria,CONTRATO.marca,CONTRATO.produto";


			Cursor cursor = getDataBase().rawQuery(sql,null);

			if (!cursor.moveToFirst()){

				lsContratos = new ArrayList<Contrato>();

			} else {
				while (!(cursor.isAfterLast())) {

					lsContratos.add(cursorToObj2(cursor));

					cursor.moveToNext();

				}
			}


		} catch (Exception e){

			new Exception(e.getMessage());

		}


		return lsContratos;
	}

}
