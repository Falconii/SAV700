package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb_fast;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.Pedidos;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionDeletePedido;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionSavePedido;
import br.com.brotolegal.savdatabase.util.Rel_Visao;
import br.com.brotolegal.savdatabase.util.VisaoRel01;


public class PedidoCabMbDAO  extends DAO2 implements IDao2<PedidoCabMb> {

	private final String TAG = "PEDIDOCABMB";

	private String whereClausePrimary = " nro = ? ";

	public PedidoCabMbDAO() throws Exception {

		super("PEDIDOCABMB","dbuser");

	}

	@Override
	public PedidoCabMb insert(PedidoCabMb obj) {
		Cursor cursor = null;

		ContentValues values = getContentValues(obj);

		try

		{

			long insertId = getDataBase().insert(obj.getFileName(), null, values);

			if (insertId != -1) {

				cursor = getDataBase().query(obj.getFileName(), obj.getColumn(), whereClausePrimary, new String[]{obj.getNRO()}, null, null, null);

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

			regs = getDataBase().delete(getRegistro().getFileName(), " codigo = ? ", new String[]{values[0]});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return regs;
	}

	@Override
	public Boolean Update(PedidoCabMb obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getNRO()});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;

	}

	@Override
	public PedidoCabMb cursorToObj(Cursor cursor) {

		PedidoCabMb pedidocabmb = null;
		try {
			pedidocabmb = new PedidoCabMb(
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
					cursor.getFloat(24),
					cursor.getFloat(25),
					cursor.getFloat(26),
					cursor.getString(27),
					cursor.getString(28),
					cursor.getFloat(29),
					cursor.getFloat(30),
					cursor.getFloat(31),
					cursor.getFloat(32),
					cursor.getString(33),
					cursor.getFloat(34),
					cursor.getString(35),
					cursor.getFloat(36),
					cursor.getFloat(37),
					cursor.getFloat(38),
					cursor.getString(39),
					( ( cursor.getString(40) ==  null)  ?  "" : cursor.getString(40) ),
					( ( cursor.getString(41) ==  null)  ?  "" : cursor.getString(41) ),
					cursor.getFloat(42),
					cursor.getInt(43)

					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return pedidocabmb;
	}

	public PedidoCabMb_fast cursorToObjFast(Cursor cursor) {

		PedidoCabMb_fast pedidocabmb = null;
		try {
			pedidocabmb = new PedidoCabMb_fast(
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
					cursor.getFloat(24),
					cursor.getFloat(25),
					cursor.getFloat(26),
					cursor.getString(27),
					cursor.getString(28),
					cursor.getFloat(29),
					cursor.getFloat(30),
					cursor.getFloat(31),
					cursor.getFloat(32),
					cursor.getString(33),
					cursor.getFloat(34),
					cursor.getString(35),
					cursor.getFloat(36),
					cursor.getFloat(37),
					cursor.getFloat(38),
					cursor.getString(39),
					cursor.getString(40),
					cursor.getString(41),
					cursor.getFloat(42),
					cursor.getInt(43)

					);
			pedidocabmb.Complemento(
					cursor.getString(44),
					cursor.getString(45),
					cursor.getString(46),
					cursor.getString(47),
					cursor.getString(48),
					cursor.getString(49),
					cursor.getString(50),
					cursor.getString(51),
					cursor.getString(52),
					cursor.getString(53),
					cursor.getString(54),
					cursor.getString(55),
					cursor.getString(56)
					);



		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return pedidocabmb;
	}

	@Override
	public List<PedidoCabMb> getAll() {
		Cursor cursor = null;

		List<PedidoCabMb> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery("select pedidocabmb.*,cliente.razaopa,cliente.cnpj,cliente.ie,tabprecocabec.descricao,condpagto.descricao,entrega.razaopa "+
					" from " + getRegistro().getFileName() +
					" left join CLIENTE on cliente.codigo = pedidocabmb.codigofat and cliente.loja =  pedidocabmb.lojafat "+
					" left join TABPRECOCABEC on pedidocabmb.tabpreco = tabprecocabec.codigo  "+
					" left join CONDPAGTO on pedidocabmb.cond    = condpagto.codigo "+
					" left join CLIENTE ENTREGA on entrega.codigo = pedidocabmb.codigoent and entrega.loja = pedidocabmb.lojaent ", null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedidoCabMb obj = cursorToObj(cursor);

					obj.set_ClienteFatRazao(cursor.getString(38));
					obj.set_ClienteFatCnpj(cursor.getString(39));
					obj.set_ClienteFatIE(cursor.getString(40));
					obj.set_TabPreco(cursor.getString(41));
					obj.set_Cond(cursor.getString(42));
					if (obj.getCODIGOENT().isEmpty()){

						obj.set_ClienteEntRazao("");

					} else {

						obj.set_ClienteEntRazao(cursor.getString(43));
					}

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

	@Override
	public PedidoCabMb seek(String[] values) {

		PedidoCabMb obj = null;

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

	public void SavePedidoFast(PedidoCabMb pedido, List<PedidoDetMB_fast> detalhes) throws ExceptionSavePedido {

		SQLiteStatement stmtc;
		SQLiteStatement stmti;
		SQLiteStatement stmtd;

		String sql = "";

		PedidoDetMb detail = null;

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

			sql = "delete from pedidodetmb  where nro = '"+pedido.getFieldByName("NRO")+"'";

			stmtd = getDataBase().compileStatement(sql);

			stmtd.execute();

			detail = new PedidoDetMb();

			//saLva detalhes
			for (PedidoDetMB_fast ped : detalhes) {

				if (ped.getNRO().isEmpty()) continue;

				detail.ImportFast(ped);

				sql = detail.getInsertString();

				stmti = getDataBase().compileStatement(sql);

				String detalhe[] = detail.getArrayStringValues();

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

	public void SavePedido(PedidoCabMb pedido, List<PedidoDetMb> detalhes) throws ExceptionSavePedido {

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

			sql = "delete from " + detalhes.get(0).getFileName() + "  where nro = '"+pedido.getFieldByName("NRO")+"'";

			stmtd = getDataBase().compileStatement(sql);

			stmtd.execute();


			//saLva detalhes
			for (PedidoDetMb ped : detalhes) {

				if (ped.getNRO().isEmpty()) continue;

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

	public void DeletePedido(String nro, List<String> Pedidos) throws ExceptionDeletePedido {
		SQLiteStatement stmto;
		SQLiteStatement stmtc;
		SQLiteStatement stmtd;

		String sql = "";

		String NewPedido = "";

		for(String ped : Pedidos){

			if (!ped.equals(nro)){

				NewPedido = ped;

				break;

			}

		}

		try {


			getDataBase().beginTransaction();

			//libera agendamento

			if (NewPedido.isEmpty()) {

				sql = "update agendamento        " +
						"set   mobile          = '', " +
						"      motivonvenda    = '', " +
						"      motivonvisita   = '', " +
						"      situacao        = '', " +
						"      dtdigit         = '', " +
                        "      hrdigit         = ''  " +
						"where  mobile         = '"+nro+"'";
			} else {

				sql = "update agendamento       " +
						"set    mobile          = '"+NewPedido+"',  " +
						"       motivonvenda    = '', " +
						"       motivonvisita   = '' " +
						"where  mobile          = '"+nro + "'";


			}

			stmto = getDataBase().compileStatement(sql);

			stmto.execute();

			stmto.clearBindings();

			sql =  "delete from pedidocabmb where nro = '"+nro+"'";

			stmtc = getDataBase().compileStatement(sql);

			stmtc.execute();

			stmtc.clearBindings();



			//apaga detalhes

			sql = "delete from pedidodetmb  where nro = '"+nro+"'";

			stmtd = getDataBase().compileStatement(sql);

			stmtd.execute();


			//grava no banco
			getDataBase().setTransactionSuccessful();

		} catch (Exception e){

			throw new ExceptionDeletePedido(e.getMessage());
		}

		finally {

			try {

				getDataBase().endTransaction();

			} catch (Exception e){

				throw  new ExceptionDeletePedido(e.getMessage());

			}

		}

	}

	public List<PedidoCabMb> getPedidosATRansmitir(){

		Cursor cursor = null;

		List<PedidoCabMb> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery("select pedidocabmb.*,cliente.razaopa,cliente.cnpj,cliente.ie,tabprecocabec.descricao,condpagto.descricao,entrega.razaopa "+
					" from " + getRegistro().getFileName() +
					" left join CLIENTE on cliente.codigo = pedidocabmb.codigofat and cliente.loja =  pedidocabmb.lojafat "+
					" left join TABPRECOCABEC on pedidocabmb.tabpreco = tabprecocabec.codigo  "+
					" left join CONDPAGTO on pedidocabmb.cond    = condpagto.codigo "+
					" left join CLIENTE ENTREGA on entrega.codigo = pedidocabmb.codigoent and entrega.loja = pedidocabmb.lojaent where pedidocabmb.status = '3'  ", null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedidoCabMb obj = cursorToObj(cursor);

					obj.set_ClienteFatRazao(cursor.getString(37));
					obj.set_ClienteFatCnpj(cursor.getString(38));
					obj.set_ClienteFatIE(cursor.getString(39));
					obj.set_TabPreco(cursor.getString(40));
					obj.set_Cond(cursor.getString(41));
					if (obj.getCODIGOENT().isEmpty()){

						obj.set_ClienteEntRazao("");

					} else {

						obj.set_ClienteEntRazao(cursor.getString(42));
					}


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

	public List<PedidoCabMb> getPedidosByStatus(String status){

		Cursor cursor = null;

		List<PedidoCabMb> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery("select pedidocabmb.*,cliente.razaopa,cliente.cnpj,cliente.ie,tabprecocabec.descricao,condpagto.descricao,entrega.razaopa "+
					" from " + getRegistro().getFileName() +
					" left join CLIENTE on cliente.codigo = pedidocabmb.codigofat and cliente.loja =  pedidocabmb.lojafat "+
					" left join TABPRECOCABEC on pedidocabmb.tabpreco = tabprecocabec.codigo  "+
					" left join CONDPAGTO on pedidocabmb.cond    = condpagto.codigo "+
					" left join CLIENTE ENTREGA on entrega.codigo = pedidocabmb.codigoent and entrega.loja = pedidocabmb.lojaent where pedidocabmb.status = '"+status+"'  ", null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedidoCabMb obj = cursorToObj(cursor);

					obj.set_ClienteFatRazao(cursor.getString(38));
					obj.set_ClienteFatCnpj(cursor.getString(39));
					obj.set_ClienteFatIE(cursor.getString(40));
					obj.set_TabPreco(cursor.getString(41));
					obj.set_Cond(cursor.getString(42));
					if (obj.getCODIGOENT().isEmpty()){

						obj.set_ClienteEntRazao("");

					} else {

						obj.set_ClienteEntRazao(cursor.getString(43));
					}

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

	public List<PedidoCabMb> getPedidosByCliente(String codigo, String loja){

		Cursor cursor = null;

		List<PedidoCabMb> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery(
					"       select "+
							"ifnull(pedidocabmb.nro,''),  "+
							"ifnull(pedidocabmb.cprotheus,''),  "+
							"ifnull(pedidocabmb.cprotheusb,''),  "+
							"ifnull(pedidocabmb.status,''),  "+
							"ifnull(pedidocabmb.emissao,''),  "+
							"ifnull(pedidocabmb.entrega,''),  "+
							"ifnull(pedidocabmb.tipo,''),  "+
							"ifnull(pedidocabmb.dttrans,''),  "+
							"ifnull(pedidocabmb.hotrans,''),  "+
							"ifnull(pedidocabmb.codigofat,''),  "+
							"ifnull(pedidocabmb.lojafat,''),  "+
							"ifnull(pedidocabmb.codigoent,''),  "+
							"ifnull(pedidocabmb.lojaent,''),  "+
							"ifnull(pedidocabmb.cond,''),  "+
							"ifnull(pedidocabmb.tabpreco,''),  "+
							"ifnull(pedidocabmb.prepedido,''),  "+
							"ifnull(pedidocabmb.preplanilha,''),  "+
							"ifnull(pedidocabmb.proposta,''),  "+
							"ifnull(pedidocabmb.contrato,''),  "+
							"ifnull(pedidocabmb.obsped,''),  "+
							"ifnull(pedidocabmb.obsnf,''),  "+
							"ifnull(pedidocabmb.agprotocolo,''),  "+
							"ifnull(pedidocabmb.agdata,''),  "+
							"ifnull(pedidocabmb.aghora,''),  "+
							"ifnull(pedidocabmb.totalpedido,0),  "+
							"ifnull(pedidocabmb.totaldesconto,0),  "+
							"ifnull(pedidocabmb.totalverba,0),  "+
							"ifnull(pedidocabmb.vend,''),  "+
							"ifnull(pedidocabmb.pedcliente,''),  "+
							"ifnull(pedidocabmb.qtdbinificada,0),  "+
							"ifnull(pedidocabmb.vlrbonificado,0),  "+
							"ifnull(pedidocabmb.pesobruto,0),  "+
							"ifnull(pedidocabmb.pesoliquido,0),  "+
							"ifnull(pedidocabmb.retira,''),  "+
							"ifnull(pedidocabmb.descret,0),  "+
							"ifnull(pedidocabmb.mensagem,''),  "+
							"ifnull(pedidocabmb.fdsprevisto,0),  "+
							"ifnull(pedidocabmb.fdsreais,0),  "+
							"ifnull(pedidocabmb.aproveitamento,0),  "+
							"ifnull(pedidocabmb.pedcli2,''),  "+
							"ifnull(pedidocabmb.ccopiapedido,''),  "+
							"ifnull(pedidocabmb.cemailcopiapedido,''),  "+
							"ifnull(pedidocabmb.saldoaproveitamento,''),  "+
							"ifnull(pedidocabmb.qtdentrega,0),  "+
							"cliente.razaopa,"+					//44
							"cliente.cnpj, "  +					//45
							"cliente.ie, "    +					//46
							"tabprecocabec.descricao, "+		//47
							"condpagto.descricao, "+			//48
							"entrega.razaopa " +				//49
							" from " + getRegistro().getFileName() +
							" left join CLIENTE on cliente.codigo = pedidocabmb.codigofat and cliente.loja =  pedidocabmb.lojafat "+
							" left join TABPRECOCABEC on pedidocabmb.tabpreco = tabprecocabec.codigo  "+
							" left join CONDPAGTO on pedidocabmb.cond     = condpagto.codigo "+
							" left join CLIENTE ENTREGA on entrega.codigo = pedidocabmb.codigoent and entrega.loja = pedidocabmb.lojaent "+
							" where pedidocabmb.status in ('0','1','2','3','4','6','98','99') and (PEDIDOCABMB.CODIGOFAT = '"+codigo+"' AND PEDIDOCABMB.LOJAFAT = '"+loja+"' ) ", null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedidoCabMb obj = cursorToObj(cursor);

					obj.set_ClienteFatRazao(cursor.getString(44));
					obj.set_ClienteFatCnpj(cursor.getString(45));
					obj.set_ClienteFatIE(cursor.getString(46));
					obj.set_TabPreco(cursor.getString(47));
					obj.set_Cond(cursor.getString(48));
					if (obj.getCODIGOENT().isEmpty()){

						obj.set_ClienteEntRazao("");

					} else {

						obj.set_ClienteEntRazao(cursor.getString(49));
					}

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

	public List<String> getPedidosByClienteAndDate(String codigo, String loja, String Data){

		Cursor cursor = null;

		List<String> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery("select pedidocabmb.nro from pedidocabmb "+
					" where pedidocabmb.status in ('0','1','2','3','4') and (PEDIDOCABMB.CODIGOFAT = '"+codigo+"' AND PEDIDOCABMB.LOJAFAT = '"+loja+"' ) AND PEDIDOCABMB.EMISSAO = '"+Data+"' ", null);

			if (!cursor.moveToFirst()) {

				result.add("");

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursor.getString(0));

					cursor.moveToNext();

				}
			}


		} catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;



	}

	public List<PedidoCabMb> getPedidosBySincronizacao(){

		Cursor cursor = null;

		List<PedidoCabMb> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery(
					"       select "+
							"ifnull(pedidocabmb.nro,''),  "+
							"ifnull(pedidocabmb.cprotheus,''),  "+
							"ifnull(pedidocabmb.cprotheusb,''),  "+
							"ifnull(pedidocabmb.status,''),  "+
							"ifnull(pedidocabmb.emissao,''),  "+
							"ifnull(pedidocabmb.entrega,''),  "+
							"ifnull(pedidocabmb.tipo,''),  "+
							"ifnull(pedidocabmb.dttrans,''),  "+
							"ifnull(pedidocabmb.hotrans,''),  "+
							"ifnull(pedidocabmb.codigofat,''),  "+
							"ifnull(pedidocabmb.lojafat,''),  "+
							"ifnull(pedidocabmb.codigoent,''),  "+
							"ifnull(pedidocabmb.lojaent,''),  "+
							"ifnull(pedidocabmb.cond,''),  "+
							"ifnull(pedidocabmb.tabpreco,''),  "+
							"ifnull(pedidocabmb.prepedido,''),  "+
							"ifnull(pedidocabmb.preplanilha,''),  "+
							"ifnull(pedidocabmb.proposta,''),  "+
							"ifnull(pedidocabmb.contrato,''),  "+
							"ifnull(pedidocabmb.obsped,''),  "+
							"ifnull(pedidocabmb.obsnf,''),  "+
							"ifnull(pedidocabmb.agprotocolo,''),  "+
							"ifnull(pedidocabmb.agdata,''),  "+
							"ifnull(pedidocabmb.aghora,''),  "+
							"ifnull(pedidocabmb.totalpedido,0),  "+
							"ifnull(pedidocabmb.totaldesconto,0),  "+
							"ifnull(pedidocabmb.totalverba,0),  "+
							"ifnull(pedidocabmb.vend,''),  "+
							"ifnull(pedidocabmb.pedcliente,''),  "+
							"ifnull(pedidocabmb.qtdbinificada,0),  "+
							"ifnull(pedidocabmb.vlrbonificado,0),  "+
							"ifnull(pedidocabmb.pesobruto,0),  "+
							"ifnull(pedidocabmb.pesoliquido,0),  "+
							"ifnull(pedidocabmb.retira,''),  "+
							"ifnull(pedidocabmb.descret,0),  "+
							"ifnull(pedidocabmb.mensagem,''),  "+
							"ifnull(pedidocabmb.fdsprevisto,0),  "+
							"ifnull(pedidocabmb.fdsreais,0),  "+
							"ifnull(pedidocabmb.aproveitamento,0),  "+
							"ifnull(pedidocabmb.pedcli2,''),  "+
							"ifnull(pedidocabmb.ccopiapedido,''),  "+
							"ifnull(pedidocabmb.cemailcopiapedido,''),  "+
							"ifnull(pedidocabmb.saldoaproveitamento,0),  "+
							"ifnull(pedidocabmb.qtdentrega,0),  "+
							"cliente.razaopa,"+					//44
							"cliente.cnpj, "  +					//45
							"cliente.ie, "    +					//46
							"tabprecocabec.descricao, "+		//47
							"condpagto.descricao, "+			//48
							"entrega.razaopa " +				//49
							" from " + getRegistro().getFileName() +
							" left join CLIENTE on cliente.codigo = pedidocabmb.codigofat and cliente.loja =  pedidocabmb.lojafat "+
							" left join TABPRECOCABEC on pedidocabmb.tabpreco = tabprecocabec.codigo  "+
							" left join CONDPAGTO on pedidocabmb.cond     = condpagto.codigo "+
							" left join CLIENTE ENTREGA on entrega.codigo = pedidocabmb.codigoent and entrega.loja = pedidocabmb.lojaent "+
							" where pedidocabmb.status in ('0','1','2','3','4','6','98','99') ", null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedidoCabMb obj = cursorToObj(cursor);

					obj.set_ClienteFatRazao(cursor.getString(44));
					obj.set_ClienteFatCnpj(cursor.getString(45));
					obj.set_ClienteFatIE(cursor.getString(46));
					obj.set_TabPreco(cursor.getString(47));
					obj.set_Cond(cursor.getString(48));
					if (obj.getCODIGOENT().isEmpty()){

						obj.set_ClienteEntRazao("");

					} else {

						obj.set_ClienteEntRazao(cursor.getString(49));
					}

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

	public List<PedidoCabMb> getPedidosPosSincronizacao(){

		Cursor cursor = null;

		List<PedidoCabMb> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery(
					" select "+
							"ifnull(pedidocabmb.nro,''),  "+
							"ifnull(pedidocabmb.cprotheus,''),  "+
							"ifnull(pedidocabmb.cprotheusb,''),  "+
							"ifnull(pedidocabmb.status,''),  "+
							"ifnull(pedidocabmb.emissao,''),  "+
							"ifnull(pedidocabmb.entrega,''),  "+
							"ifnull(pedidocabmb.tipo,''),  "+
							"ifnull(pedidocabmb.dttrans,''),  "+
							"ifnull(pedidocabmb.hotrans,''),  "+
							"ifnull(pedidocabmb.codigofat,''),  "+
							"ifnull(pedidocabmb.lojafat,''),  "+
							"ifnull(pedidocabmb.codigoent,''),  "+
							"ifnull(pedidocabmb.lojaent,''),  "+
							"ifnull(pedidocabmb.cond,''),  "+
							"ifnull(pedidocabmb.tabpreco,''),  "+
							"ifnull(pedidocabmb.prepedido,''),  "+
							"ifnull(pedidocabmb.preplanilha,''),  "+
							"ifnull(pedidocabmb.proposta,''),  "+
							"ifnull(pedidocabmb.contrato,''),  "+
							"ifnull(pedidocabmb.obsped,''),  "+
							"ifnull(pedidocabmb.obsnf,''),  "+
							"ifnull(pedidocabmb.agprotocolo,''),  "+
							"ifnull(pedidocabmb.agdata,''),  "+
							"ifnull(pedidocabmb.aghora,''),  "+
							"ifnull(pedidocabmb.totalpedido,0),  "+
							"ifnull(pedidocabmb.totaldesconto,0),  "+
							"ifnull(pedidocabmb.totalverba,0),  "+
							"ifnull(pedidocabmb.vend,''),  "+
							"ifnull(pedidocabmb.pedcliente,''),  "+
							"ifnull(pedidocabmb.qtdbinificada,0),  "+
							"ifnull(pedidocabmb.vlrbonificado,0),  "+
							"ifnull(pedidocabmb.pesobruto,0),  "+
							"ifnull(pedidocabmb.pesoliquido,0),  "+
							"ifnull(pedidocabmb.retira,''),  "+
							"ifnull(pedidocabmb.descret,0),  "+
							"ifnull(pedidocabmb.mensagem,''),  "+
							"ifnull(pedidocabmb.fdsprevisto,0),  "+
							"ifnull(pedidocabmb.fdsreais,0),  "+
							"ifnull(pedidocabmb.aproveitamento,0),  "+
							"ifnull(pedidocabmb.pedcli2,''),  "+
							"ifnull(pedidocabmb.ccopiapedido,''),  "+
							"ifnull(pedidocabmb.cemailcopiapedido,''),  "+
							"ifnull(pedidocabmb.saldoaproveitamento,0),  "+
							"ifnull(pedidocabmb.qtdentrega,0)   "+
							"cliente.razaopa,"+					//43
							"cliente.cnpj, "  +					//44
							"cliente.ie, "    +					//45
							"tabprecocabec.descricao, "+		//46
							"condpagto.descricao, "+			//47
							"entrega.razaopa " +				//48
							" from " + getRegistro().getFileName() +
							" left join CLIENTE on cliente.codigo = pedidocabmb.codigofat and cliente.loja =  pedidocabmb.lojafat "+
							" left join TABPRECOCABEC on pedidocabmb.tabpreco = tabprecocabec.codigo  "+
							" left join CONDPAGTO on pedidocabmb.cond     = condpagto.codigo "+
							" left join CLIENTE ENTREGA on entrega.codigo = pedidocabmb.codigoent and entrega.loja = pedidocabmb.lojaent "+
							" where pedidocabmb.status in ('5','6','98','99') order by  pedidocabmb.nro DESC ", null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedidoCabMb obj = cursorToObj(cursor);

					obj.set_ClienteFatRazao(cursor.getString(44));
					obj.set_ClienteFatCnpj(cursor.getString(45));
					obj.set_ClienteFatIE(cursor.getString(46));
					obj.set_TabPreco(cursor.getString(47));
					obj.set_Cond(cursor.getString(48));
					if (obj.getCODIGOENT().isEmpty()){

						obj.set_ClienteEntRazao("");

					} else {

						obj.set_ClienteEntRazao(cursor.getString(49));
					}

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

	public List<Pedidos> getPedidosToSend(String status){

		Cursor cursor = null;

		List<Pedidos> result = new ArrayList<>();

		String        sql    = 	" select pedidocabmb.*,pedidodetmb.* " +
				" from " + getRegistro().getFileName() +
				" inner join pedidodetmb on pedidodetmb.nro = pedidocabmb.nro "+
				" where pedidocabmb.status = '"+status+"'  ";

		String  nro 		= "";

		try {

			cursor = getDataBase().rawQuery(sql,null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					if (!cursor.getString(0).equals(nro)) {

						PedidoCabMb obj = cursorToObj(cursor);

						nro = obj.getNRO();

						result.add(new Pedidos(obj));

					}

					result.get(result.size()-1).getLsDetalhe().add(cursorToPedidoDetMB(cursor));

					cursor.moveToNext();

				}
			}

		} catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;



	}

	public List<Pedidos> getPedidosToSendByCliente(String codigo, String loja, String status){

		Cursor cursor = null;

		List<Pedidos> result = new ArrayList<>();

		String        sql    = 	" select pedidocabmb.*,pedidodetmb.* " +
				" from " + getRegistro().getFileName() +
				" inner join pedidodetmb on pedidodetmb.nro = pedidocabmb.nro "+
				" where pedidocabmb.codigofat = '"+codigo+"' and  pedidocabmb.lojafat = '"+loja+"' and pedidocabmb.status = '"+status+"'  ";

		String  nro 		= "";

		try {

			cursor = getDataBase().rawQuery(sql,null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					if (!cursor.getString(0).equals(nro)) {

						PedidoCabMb obj = cursorToObj(cursor);

						nro = obj.getNRO();

						result.add(new Pedidos(obj));

					}

					result.get(result.size()-1).getLsDetalhe().add(cursorToPedidoDetMB(cursor));

					cursor.moveToNext();
				}
			}

		} catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;



	}

	public List<Pedidos> getPedidosToSendByPedidoAutomatico(String pedido,String status){

		Log.i(TAG, "NRO "+pedido);

		Cursor cursor = null;

		List<Pedidos> result = new ArrayList<>();

		String      sql    = 	" select pedidocabmb.*,pedidodetmb.* " +
				" from " + getRegistro().getFileName() +
				" inner join pedidodetmb on pedidodetmb.nro = pedidocabmb.nro "+
				" where pedidocabmb.nro = '"+pedido+"' and  pedidocabmb.status = '"+status+"'  ";

		String  nro 		= "";

		try {

			cursor = getDataBase().rawQuery(sql,null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					Log.i(TAG, "Encontrou !!!");

					if (!cursor.getString(0).equals(nro)) {

						PedidoCabMb obj = cursorToObj(cursor);

						nro = obj.getNRO();

						result.add(new Pedidos(obj));

					}

					result.get(result.size()-1).getLsDetalhe().add(cursorToPedidoDetMB(cursor));

					cursor.moveToNext();
				}
			}

		} catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;



	}

	public PedidoDetMb cursorToPedidoDetMB(Cursor cursor) {

		PedidoDetMb obj = null;

		try {
			obj = new PedidoDetMb(
					cursor.getString(44),
					cursor.getString(45),
					cursor.getString(46),
					cursor.getString(47),
					cursor.getString(48),
					cursor.getString(49),
					cursor.getString(50),
					cursor.getFloat(51),
					cursor.getString(52),
					cursor.getFloat(53),
					cursor.getFloat(54),
					cursor.getFloat(55),
					cursor.getString(56),
					cursor.getFloat(57),
					cursor.getString(58),
					cursor.getFloat(59),
					cursor.getFloat(60),
					cursor.getFloat(61),
					cursor.getFloat(62),
					cursor.getString(63),
					cursor.getString(64),
					cursor.getString(65),
					cursor.getString(66),
					cursor.getString(67),
					cursor.getString(68),
					cursor.getFloat(69),
					cursor.getFloat(70),
					cursor.getFloat(71),
					cursor.getFloat(72),
					cursor.getFloat(73),
					cursor.getFloat(74),
					cursor.getFloat(75),
					cursor.getFloat(76),
					cursor.getString(77),
					cursor.getString(78),
					cursor.getString(79),
					cursor.getString(80),
					cursor.getString(81),
					cursor.getString(82),
					cursor.getString(83),
					cursor.getString(84),
					cursor.getString(85),
					cursor.getFloat(86),
					cursor.getString(87),
					cursor.getFloat(88),
					cursor.getFloat(89),
					cursor.getString(90),
					cursor.getString(91),
					cursor.getString(92),
					cursor.getString(93),
					cursor.getInt(94),
					cursor.getString(95),
					cursor.getFloat(96),
					cursor.getFloat(97),
					cursor.getFloat(98),
					cursor.getFloat(99),
					cursor.getFloat(100),
					cursor.getFloat(101)


					);

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return obj;
	}

	public void Caduca( String data) throws Exception

	{

		try

		{

			String sql = " update  pedidocabmb set status = '98' " +
					" where pedidocabmb.status in ('1','2','3') and pedidocabmb.emissao <> '"+data+"' " ;


			Log.i(TAG,sql);

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

	public List<PedidoCabMb_fast> getAllFast(String ordem) {
		Cursor cursor = null;

		List<PedidoCabMb_fast> result = new ArrayList<>();

		try {

			String sql = "select " +
					"ifnull(pedidocabmb.nro,''),  "+
					"ifnull(pedidocabmb.cprotheus,''),  "+
					"ifnull(pedidocabmb.cprotheusb,''),  "+
					"ifnull(pedidocabmb.status,''),  "+
					"ifnull(pedidocabmb.emissao,''),  "+
					"ifnull(pedidocabmb.entrega,''),  "+
					"ifnull(pedidocabmb.tipo,''),  "+
					"ifnull(pedidocabmb.dttrans,''),  "+
					"ifnull(pedidocabmb.hotrans,''),  "+
					"ifnull(pedidocabmb.codigofat,''),  "+
					"ifnull(pedidocabmb.lojafat,''),  "+
					"ifnull(pedidocabmb.codigoent,''),  "+
					"ifnull(pedidocabmb.lojaent,''),  "+
					"ifnull(pedidocabmb.cond,''),  "+
					"ifnull(pedidocabmb.tabpreco,''),  "+
					"ifnull(pedidocabmb.prepedido,''),  "+
					"ifnull(pedidocabmb.preplanilha,''),  "+
					"ifnull(pedidocabmb.proposta,''),  "+
					"ifnull(pedidocabmb.contrato,''),  "+
					"ifnull(pedidocabmb.obsped,''),  "+
					"ifnull(pedidocabmb.obsnf,''),  "+
					"ifnull(pedidocabmb.agprotocolo,''),  "+
					"ifnull(pedidocabmb.agdata,''),  "+
					"ifnull(pedidocabmb.aghora,''),  "+
					"ifnull(pedidocabmb.totalpedido,0),  "+
					"ifnull(pedidocabmb.totaldesconto,0),  "+
					"ifnull(pedidocabmb.totalverba,0),  "+
					"ifnull(pedidocabmb.vend,''),  "+
					"ifnull(pedidocabmb.pedcliente,''),  "+
					"ifnull(pedidocabmb.qtdbinificada,0),  "+
					"ifnull(pedidocabmb.vlrbonificado,0),  "+
					"ifnull(pedidocabmb.pesobruto,0),  "+
					"ifnull(pedidocabmb.pesoliquido,0),  "+
					"ifnull(pedidocabmb.retira,''),  "+
					"ifnull(pedidocabmb.descret,0),  "+
					"ifnull(pedidocabmb.mensagem,''),  "+
					"ifnull(pedidocabmb.fdsprevisto,0),  "+
					"ifnull(pedidocabmb.fdsreais,0),  "+
					"ifnull(pedidocabmb.aproveitamento,0),  "+
					"ifnull(pedidocabmb.pedcli2,''),  "+
					"ifnull(pedidocabmb.ccopiapedido,''),  "+
					"ifnull(pedidocabmb.cemailcopiapedido,''),  "+
					"ifnull(pedidocabmb.saldoaproveitamento,0),  "+
					"ifnull(pedidocabmb.qtdentrega,0),  "+
					"ifnull(cliente.razaopa,'')," +
					"ifnull(cliente.cnpj,'')," +
					"ifnull(cliente.ie,'')," +
					"ifnull(cliente.cidade,'')," +
					"ifnull(cliente.telefone,'')," +
					"ifnull(entrega.razaopa,''), "+
					"ifnull(cliente.fantasia,''), "+
					"ifnull(entrega.cidade,'')," +
					"'(' || ifnull(entrega.ddd,'') || ')' || ifnull(entrega.telefone,'')," +
					"ifnull(rede.codigo,''), "+
					"ifnull(tabprecocabec.descricao,''), " +
					"ifnull(rede.descricao,''), "+
					"ifnull(condpagto.descricao,'') "+
					" from " + getRegistro().getFileName() +
					" left join CLIENTE on cliente.codigo = pedidocabmb.codigofat and cliente.loja =  pedidocabmb.lojafat "+
					" left join TABPRECOCABEC on pedidocabmb.tabpreco = tabprecocabec.codigo  "+
					" left join CONDPAGTO on pedidocabmb.cond    = condpagto.codigo "+
					" left join CLIENTE ENTREGA on entrega.codigo = pedidocabmb.codigoent and entrega.loja = pedidocabmb.lojaent "+
					" left join REDE    on rede.codigo = cliente.rede "+
					" where pedidocabmb.status = '5' "+
					" order by "+ordem ;

			cursor = getDataBase().rawQuery(sql, null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedidoCabMb_fast obj = cursorToObjFast(cursor);

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


	public List<VisaoRel01> getPedidosToRel01(){

		Cursor cursor = null;

		List<VisaoRel01> result = new ArrayList<>();

		String
				sql    = 	" select " +
				"ifnull(pedidocabmb.emissao,''),  "+
				"ifnull(pedidocabmb.codigofat,''),  "+
				"ifnull(pedidocabmb.lojafat,''),  "+
				"ifnull(cliente.rede,'')," +
				"ifnull(produto.grupo,'')," +
				"ifnull(produto.marca,'')," +
				"ifnull(pedidodetmb.produto,'')," +
				"ifnull((pedidodetmb.qtd + pedidodetmb.boniqtd) ,0)," +
				"ifnull(pedidodetmb.total,0)," +
				"ifnull(pedidodetmb.prcven,0)," +
				"ifnull(pedidodetmb.precoformacao,0)," +
				"ifnull(pedidodetmb.descon,0)," +
				"ifnull(pedidocabmb.nro,''),  "+
				"ifnull(pedidocabmb.tipo,''),  "+
				"ifnull(pedidocabmb.cprotheus,''),  "+
				"ifnull(cliente.razaopa,'')," +
				"ifnull(rede.descricao,''), "+
				"ifnull(grupo.descricao,''), "+
				"ifnull(marca.descricao,''), "+
				"ifnull(produto.descricao,''), "+
				"ifnull(produto.conversao,0) "+
				" from " + getRegistro().getFileName() +
				" inner join PEDIDODETMB on pedidodetmb.nro = pedidocabmb.nro "+
				" left join  CLIENTE on cliente.codigo = pedidocabmb.codigofat and cliente.loja =  pedidocabmb.lojafat "+
				" left join  PRODUTO on produto.codigo = pedidodetmb.produto "+
				" left join  REDE    on rede.codigo    = cliente.rede  "+
				" left join  GRUPO   on grupo.codigo   = produto.grupo "+
				" left join  MARCA   on marca.codigo   = produto.marca "+
				" where ( pedidocabmb.tipo in ('001','003','010','011') and pedidocabmb.status = '5') ";
		if (!App.manager_filtro_01.getWhere().isEmpty()) sql += " and "+App.manager_filtro_01.getWhere();
		sql += " order by "+ App.manager_filtro_01.getStringOrderBy();

		try {

			cursor = getDataBase().rawQuery(sql,null);

			if (!cursor.moveToFirst()) {

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					VisaoRel01 obj = new VisaoRel01("DT");

					obj.setDATA(cursor.getString(0));
					obj.setCLIENTE(cursor.getString(1));
					obj.setLOJA(cursor.getString(2));
					obj.setREDE(cursor.getString(3));
					obj.setCATEGORIA(cursor.getString(4));
					obj.setMARCA(cursor.getString(5));
					obj.setPRODUTO(cursor.getString(6));
					obj.setQTD(cursor.getFloat(7));
					obj.setVALOR(cursor.getFloat(8));
					obj.setPRCVEN(cursor.getFloat(9));
					obj.setPRCBASE(cursor.getFloat(10));
					obj.setDESCONTO(cursor.getFloat(11));
					obj.setMOBILENRO(cursor.getString(12));
					obj.setMOBILETIPO(cursor.getString(13));
					obj.setPEDIDOPROTHEUSNRO(cursor.getString(14));
					obj.setCLIENTERAZAO(cursor.getString(15));
					obj.setREDEDESCRI(cursor.getString(16));
					obj.setCATEGORIADESCRI(cursor.getString(17));
					obj.setMARCADESCRICAO(cursor.getString(18));
					obj.setPRODUTODESCRICAO(cursor.getString(19));
					obj.setQTDFDS(cursor.getFloat(7) * cursor.getFloat(20));


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


}
