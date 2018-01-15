package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionSavePedido;


public class PedCabTvsDAO  extends DAO2 implements IDao2<PedCabTvs> {

	private final String TAG = "PEDCABTVS";

	private String whereClausePrimary = " filial = ? and pedido = ? ";

	public PedCabTvsDAO() throws Exception {

		super("PEDCABTVS","dbuser");

	}


	@Override
	public PedCabTvs insert(PedCabTvs obj) {
		Cursor cursor = null;

		ContentValues values = getContentValues(obj);

		try

		{

			long insertId = getDataBase().insert(obj.getFileName(), null, values);

			if (insertId != -1) {

				cursor = getDataBase().query(obj.getFileName(), obj.getColumn(), whereClausePrimary, new String[]{obj.getFILIAL(),obj.getPEDIDO()}, null, null, null);

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

			regs = getDataBase().delete(getRegistro().getFileName(),whereClausePrimary, new String[]{values[0],values[1]});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return regs;
	}

	@Override
	public Boolean Update(PedCabTvs obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getFILIAL(),obj.getPEDIDO()});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;

	}

	@Override
	public PedCabTvs cursorToObj(Cursor cursor) {

		PedCabTvs pedcabtvs = null;
		try {
			pedcabtvs = new PedCabTvs(
					cursor.getString(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4),
					cursor.getString(5),
					cursor.getString(6),
					cursor.getFloat(7),
					cursor.getFloat(8),
					cursor.getString(9),
					cursor.getString(10),
					cursor.getString(11),
					cursor.getString(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getString(16),
					cursor.getString(17),
					cursor.getString(18),
					cursor.getString(19),
					cursor.getString(20),
					cursor.getString(21),
					cursor.getString(22),
					cursor.getString(23),
					cursor.getString(24),
					cursor.getString(25),
					cursor.getString(26),
					cursor.getFloat(27),
					cursor.getFloat(28),
					cursor.getInt(29)

					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return pedcabtvs;

	}

	@Override
	public List<PedCabTvs> getAll() {
		Cursor cursor = null;

		List<PedCabTvs> result = new ArrayList<>();

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
	public PedCabTvs seek(String[] values) {
		PedCabTvs obj = null;

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

	/*

	0 - Codigo do Cliente
	1 - Loja
	2 - Ordenação
	3 - Filial
	4 - Pedido
	5 - Acordo

	 */


	public List<PedCabTvs> getAllWithFiltro(String[] values) {

		Cursor cursor = null;

		List<PedCabTvs> result = new ArrayList<>();

		String sql = "";

		try {

			//select
			sql = getSqlPadrao01();

			//where
			if (!values[0].isEmpty()) {
				sql += " where pedcabtvs.cliente = '"+values[0]+"' and pedcabtvs.loja = '"+values[1]+"'  ";
			}
			if (!values[3].isEmpty()) {
				sql += " where pedcabtvs.filial = '"+values[3]+"' and pedcabtvs.pedido = '"+values[4]+"'  ";
			}
			//order by
			if (values[2].equals("C")) {  //crescente

				sql += " order by pedcabtvs.emissao ASC ";

			} else {

				sql += " order by pedcabtvs.emissao DESC ";
			}

			cursor = getDataBase().rawQuery(sql, null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedCabTvs obj = cursorToObj(cursor);

					obj.Complemento(
							cursor.getString(28),
							cursor.getString(29),
							cursor.getString(30),
							cursor.getString(31),
							cursor.getString(32),
							cursor.getString(33),
							cursor.getString(34),
							cursor.getString(35),
							cursor.getString(36),
							cursor.getString(37),
							cursor.getString(38)

					);

					result.add(obj);

					cursor.moveToNext();

				}
			}


		} catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;

	}

	/*
	   0-nro do acordo
	   1-ordernacao

	 */

	public List<PedCabTvs> getPedidoByAcordo(String [] values) {

		Cursor cursor = null;

		List<PedCabTvs> result = new ArrayList<>();

		String sql = "";

		try {

			//select
			sql =  getSqlPadrao01();

			//order by
			if (values[1].equals("C")) {  //crescente

				sql += " order by pedcabtvs.emissao ASC ";

			} else {

				sql += " order by pedcabtvs.emissao DESC ";
			}

			cursor = getDataBase().rawQuery(sql, null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedCabTvs obj = cursorToObj(cursor);

					obj.Complemento(
							cursor.getString(28),
							cursor.getString(29),
							cursor.getString(30),
							cursor.getString(31),
							cursor.getString(32),
							cursor.getString(33),
							cursor.getString(34),
							cursor.getString(35),
							cursor.getString(36),
							cursor.getString(37),
							cursor.getString(38)

					);

					result.add(obj);

					cursor.moveToNext();

				}
			}


		} catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;

	}


	public List<PedCabTvs> getAllNaoFaturados(String[] values) {

		Cursor cursor = null;

		List<PedCabTvs> result = new ArrayList<>();

		String sql = "";

		try {

			//select
			sql = getSqlPadrao01();

			//where
			if (!values[0].isEmpty()) {
				sql += " where pedcabtvs.cliente = '"+values[0]+"' and pedcabtvs.loja = '"+values[1]+"' and  ";
				sql += "       pedcabtvs.situacao in ('PED DIST - BLOQUEADO VERBA','PED DIST - BLOQUEADO CREDITO','PED DIST - BLOQUEADO ESTOQUE','BLOQUEADO VERBA','BLOQUEADO CREDITO', 'BLOQUEADO ESTOQUE','PENDENTE')";
			} else {
				sql += " where  trim(pedcabtvs.situacao) in ('PED DIST - BLOQUEADO VERBA','PED DIST - BLOQUEADO CREDITO','PED DIST - BLOQUEADO ESTOQUE','BLOQUEADO VERBA','BLOQUEADO CREDITO', 'BLOQUEADO ESTOQUE','PENDENTE')";
			}
			//order by
			if (values[2].equals("C")) {  //crescente

				sql += " order by pedcabtvs.entrega,pedcabtvs.pedido ASC ";

			} else {

				sql += " order by pedcabtvs.entrega,pedcabtvs.pedido DESC ";
			}

			cursor = getDataBase().rawQuery(sql, null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedCabTvs obj = cursorToObj(cursor);

					obj.Complemento(
							cursor.getString(28),
							cursor.getString(29),
							cursor.getString(30),
							cursor.getString(31),
							cursor.getString(32),
							cursor.getString(33),
							cursor.getString(34),
							cursor.getString(35),
							cursor.getString(36),
							cursor.getString(37),
							cursor.getString(38)

					);

					result.add(obj);

					cursor.moveToNext();

				}
			}


		} catch (Exception e) {

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return result;

	}


	public List<PedCabTvs> getAllPedidosComposicaoCarga() {

		Cursor cursor = null;

		List<PedCabTvs> result = new ArrayList<>();

		String sql = "";

		try {

			//select
			sql =  getSqlPadrao01();

			//where
			sql += " where pedcabtvs.tipo in ('001','012','013')";

			//order by
			sql += " order by pedcabtvs.entrega,pedcabtvs.pedido DESC ";

			cursor = getDataBase().rawQuery(sql, null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedCabTvs obj = cursorToObj(cursor);

					obj.Complemento(
							cursor.getString(28),
							cursor.getString(29),
							cursor.getString(30),
							cursor.getString(31),
							cursor.getString(32),
							cursor.getString(33),
							cursor.getString(34),
							cursor.getString(35),
							cursor.getString(36),
							cursor.getString(37),
							cursor.getString(38)

					);

					result.add(obj);

					cursor.moveToNext();

				}
			}


		} catch (Exception e) {

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return result;

	}

	private String getSqlPadrao01(){

		String sql = "";

		sql =  " select pedcabtvs.*, " +
				"        ifnull(cliente.cnpj      , ''),   " +
				"        ifnull(cliente.ie        , ''),   " +
				"        ifnull(cliente.codcidade , ''),   " +
				"        ifnull(cliente.cidade    , ''),   " +
				"        ifnull(cliente.rede      , ''),   " +
				"        ifnull(rede.descricao    , ''),   " +
				"        ifnull(cliente.ddd       , ''),   " +
				"        ifnull(cliente.telefone  , ''),   " +
				"        ifnull(clientee.razao    , ''),   " +
				"        ifnull(clientee.cidade   , ''),   " +
				"        '(' || ifnull(clientee.ddd ,'') || ') ' || ifnull(clientee.telefone ,'')  as tel       " +
				" from pedcabtvs " +
				" inner join cliente            on pedcabtvs.cliente        =  cliente.codigo and pedcabtvs.loja = cliente.loja "+
				" inner join cliente clientee   on pedcabtvs.codigoentrega  =  clientee.codigo and pedcabtvs.lojaentrega = clientee.loja "+
				" inner join rede               on cliente.rede             =  rede.codigo  ";

		return sql;

	}

	public void SavePedido(PedCabTvs pedido, List<PedDetTvs> detalhes) throws ExceptionSavePedido {

		SQLiteStatement stmtc;
		SQLiteStatement stmti;
		SQLiteStatement stmtd;

		String sql = "";

		try {

			sql =  pedido.getInsertString();

			getDataBase().beginTransaction();

			stmtc = getDataBase().compileStatement(sql);

			//Save Cabecalho
			String cabec[] = pedido.getArrayStringValues();

			for(int x = 0; x <  cabec.length ; x++){

				stmtc.bindString(x+1, cabec[x]);

			}

			stmtc.execute();

			stmtc.clearBindings();

			//apaga detalhes

			sql = "delete from " + detalhes.get(0).getFileName() + "  where filial = '"+pedido.getFILIAL()+"' and pedido = '"+pedido.getPEDIDO()+"'";

			stmtd = getDataBase().compileStatement(sql);

			stmtd.execute();


			//saLva detalhes
			for (PedDetTvs ped : detalhes) {

				sql = ped.getInsertString();

				stmti = getDataBase().compileStatement(sql);

				String detalhe[] = ped.getArrayStringValues();

				for(int x = 0; x < detalhe.length ; x++){

					stmti.bindString(x+1, detalhe[x]);

				}

				stmti.execute();

				stmti.clearBindings();

			}

			//grava no banco
			getDataBase().setTransactionSuccessful();

		} catch (Exception e){

			throw new ExceptionSavePedido(e.getMessage());
		}

		finally {

			try {

				getDataBase().endTransaction();

			} catch (Exception e){

				throw  new ExceptionSavePedido(e.getMessage());

			}

		}
	}

}

