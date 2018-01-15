package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;


public class ClienteDAO  extends DAO2 implements IDao2<Cliente> {

	private final String TAG = "CLIENTEDAO";

	private String whereClausePrimary = " codigo = ? and loja = ?";

	public ClienteDAO() throws Exception {
		super("CLIENTE","dbuser");
	}

	@Override
	public Cliente insert(Cliente obj) {

		Cursor cursor = null;

		ContentValues values = getContentValues(obj) ;

		try

		{

			long insertId = getDataBase().insert(obj.getFileName(), null, values);

			if (insertId != -1){

				cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getCODIGO(),obj.getLOJA()},null, null, null);

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

			regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0],values[1]});

		} catch (Exception e) {

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return regs;

	}

	@Override
	public Boolean Update(Cliente obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getCODIGO(), obj.getLOJA()});

		} catch (Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return retorno == 0 ? false : true;

	}

	@Override
	public Cliente cursorToObj(Cursor cursor) {
		Cliente cli = null;

		try {
			cli = new Cliente(

					getNoNULL(cursor.getString(0)),
					getNoNULL(cursor.getString(1)),
					getNoNULL(cursor.getString(2)),
					getNoNULL(cursor.getString(3)),
					getNoNULL(cursor.getString(4)),
					getNoNULL(cursor.getString(5)),
					getNoNULL(cursor.getString(6)),
					getNoNULL(cursor.getString(7)),
					getNoNULL(cursor.getString(8)),
					getNoNULL(cursor.getString(9)),
					getNoNULL(cursor.getString(10)),
					getNoNULL(cursor.getString(11)),
					getNoNULL(cursor.getString(12)),
					getNoNULL(cursor.getString(13)),
					getNoNULL(cursor.getString(14)),
					getNoNULL(cursor.getString(15)),
					getNoNULL(cursor.getString(16)),
					getNoNULLFloat(cursor.getFloat(17)),
					getNoNULL(cursor.getString(18)),
					getNoNULL(cursor.getString(19)),
					getNoNULL(cursor.getString(20)),
					getNoNULL(cursor.getString(21)),
					getNoNULL(cursor.getString(22)),
					getNoNULL(cursor.getString(23)),
					getNoNULL(cursor.getString(24)),
					getNoNULL(cursor.getString(25)),
					getNoNULL(cursor.getString(26)),
					getNoNULL(cursor.getString(27)),
					getNoNULL(cursor.getString(28)),
					getNoNULL(cursor.getString(29)),
					getNoNULL(cursor.getString(30)),
					getNoNULL(cursor.getString(31)),
					getNoNULL(cursor.getString(32)),
					getNoNULL(cursor.getString(33)),
					getNoNULL(cursor.getString(34)),
					getNoNULL(cursor.getString(35)),
					getNoNULL(cursor.getString(36)),
					getNoNULL(cursor.getString(37)),
					getNoNULL(cursor.getString(38)),
					getNoNULL(cursor.getString(39)),
					getNoNULL(cursor.getString(40)),
					getNoNULL(cursor.getString(41)),
					getNoNULL(cursor.getString(42)),
					getNoNULL(cursor.getString(43)),
					getNoNULL(cursor.getString(44)),
					getNoNULL(cursor.getString(45)),
					getNoNULL(cursor.getString(46)),
					getNoNULL(cursor.getString(47)),
					getNoNULL(cursor.getString(48)),
					getNoNULL(cursor.getString(49)),
					getNoNULL(cursor.getString(50)),
					getNoNULL(cursor.getString(51)),
					getNoNULL(cursor.getString(52)),
					getNoNULL(cursor.getString(53)),
					getNoNULLFloat(cursor.getFloat(54)),
					getNoNULL(cursor.getString(55)),
					getNoNULL(cursor.getString(56)),
					getNoNULL(cursor.getString(57)),
					getNoNULLFloat(cursor.getFloat(58)),
					getNoNULL(cursor.getString(59)),
					getNoNULL(cursor.getString(60)),
					getNoNULL(cursor.getString(61)),
					getNoNULL(cursor.getString(62)),
					getNoNULL(cursor.getString(63)),
					getNoNULL(cursor.getString(64)),
					getNoNULL(cursor.getString(65)),
					getNoNULL(cursor.getString(66)),
					getNoNULL(cursor.getString(67)),
					getNoNULL(cursor.getString(68)),
					getNoNULL(cursor.getString(69)),
					getNoNULL(cursor.getString(70)),
					getNoNULL(cursor.getString(71)),
					getNoNULL(cursor.getString(72)),
					getNoNULL(cursor.getString(73)),
					getNoNULL(cursor.getString(74)),
					getNoNULLFloat(cursor.getFloat(75)),
					getNoNULL(cursor.getString(76))




					);
		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return cli;

	}

	public Cliente_fast cursorToObj_fast(Cursor cursor) {

		Cliente_fast cli = null;

		try {
			cli = new Cliente_fast(

					getNoNULL(cursor.getString(0)),
					getNoNULL(cursor.getString(1)),
					getNoNULL(cursor.getString(2)),
					getNoNULL(cursor.getString(3)),
					getNoNULL(cursor.getString(4)),
					getNoNULL(cursor.getString(5)),
					getNoNULL(cursor.getString(6)),
					getNoNULL(cursor.getString(7)),
					getNoNULL(cursor.getString(8)),
					getNoNULL(cursor.getString(9)),
					getNoNULL(cursor.getString(10)),
					getNoNULL(cursor.getString(11)),
					getNoNULL(cursor.getString(12)),
					getNoNULL(cursor.getString(13)),
					getNoNULL(cursor.getString(14)),
					getNoNULL(cursor.getString(15)),
					getNoNULL(cursor.getString(16)),
					getNoNULLFloat(cursor.getFloat(17)),
					getNoNULL(cursor.getString(18)),
					getNoNULL(cursor.getString(19)),
					getNoNULL(cursor.getString(20)),
					getNoNULL(cursor.getString(21)),
					getNoNULL(cursor.getString(22)),
					getNoNULL(cursor.getString(23)),
					getNoNULL(cursor.getString(24)),
					getNoNULL(cursor.getString(25)),
					getNoNULL(cursor.getString(26)),
					getNoNULL(cursor.getString(27)),
					getNoNULL(cursor.getString(28)),
					getNoNULL(cursor.getString(29)),
					getNoNULL(cursor.getString(30)),
					getNoNULL(cursor.getString(31)),
					getNoNULL(cursor.getString(32)),
					getNoNULL(cursor.getString(33)),
					getNoNULL(cursor.getString(34)),
					getNoNULL(cursor.getString(35)),
					getNoNULL(cursor.getString(36)),
					getNoNULL(cursor.getString(37)),
					getNoNULL(cursor.getString(38)),
					getNoNULL(cursor.getString(39)),
					getNoNULL(cursor.getString(40)),
					getNoNULL(cursor.getString(41)),
					getNoNULL(cursor.getString(42)),
					getNoNULL(cursor.getString(43)),
					getNoNULL(cursor.getString(44)),
					getNoNULL(cursor.getString(45)),
					getNoNULL(cursor.getString(46)),
					getNoNULL(cursor.getString(47)),
					getNoNULL(cursor.getString(48)),
					getNoNULL(cursor.getString(49)),
					getNoNULL(cursor.getString(50)),
					getNoNULL(cursor.getString(51)),
					getNoNULL(cursor.getString(52)),
					getNoNULL(cursor.getString(53)),
					getNoNULLFloat(cursor.getFloat(54)),
					getNoNULL(cursor.getString(55)),
					getNoNULL(cursor.getString(56)),
					getNoNULL(cursor.getString(57)),
					getNoNULLFloat(cursor.getFloat(58)),
					getNoNULL(cursor.getString(59)),
					getNoNULL(cursor.getString(60)),
					getNoNULL(cursor.getString(61)),
					getNoNULL(cursor.getString(62)),
					getNoNULL(cursor.getString(63)),
					getNoNULL(cursor.getString(64)),
					getNoNULL(cursor.getString(65)),
					getNoNULL(cursor.getString(66)),
					getNoNULL(cursor.getString(67)),
					getNoNULL(cursor.getString(68)),
					getNoNULL(cursor.getString(69)),
					getNoNULL(cursor.getString(70)),
					getNoNULL(cursor.getString(71)),
					getNoNULL(cursor.getString(72)),
					getNoNULL(cursor.getString(73)),
					getNoNULL(cursor.getString(74)),
					getNoNULLFloat(cursor.getFloat(75)),
					getNoNULL(cursor.getString(76)),


					cursor.getInt(77),
					cursor.getInt(78),
					cursor.getString(79),
					cursor.getString(80),
					cursor.getString(81),
					cursor.getString(82),
					cursor.getString(83),
					cursor.getString(84),
					cursor.getString(85),
					cursor.getString(86),
					cursor.getString(87),
					cursor.getString(88),
					cursor.getString(89),
					cursor.getString(90),
					cursor.getString(91),
					cursor.getString(92),
					getNoNULLFloat(cursor.getFloat(93))




					);
		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return cli;

	}

	@Override
	public List<Cliente> getAll() {
		Cursor cursor = null;

		ArrayList<Cliente> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName()  , null);

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

	public List<Cliente_fast> getAll_fast(String ordem,String data) {
		Cursor cursor = null;

		String open = "";

		ArrayList<Cliente_fast> result = new ArrayList<>();

		if (data.equals("OPEN")){

			open = data;

			data = App.getHoje();

		}

		try {

			String sql = "";

			if (data.trim().isEmpty()) {
				sql = "select CLIENTE.*," +
						"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('0','1','2','3','4') ) AS AMARELO, " +
						"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('6','7','99') )        AS VERMELHO, " +
						"canal.descricao, " +
						"rede.descricao, " +
						"TABPRECOCABEC.descricao, " +
						"regiao.descricao, " +
						"condpagto.descricao,  " +
						"ifnull(agendamento.filial,'')        as _FILAGE,    " +
						"ifnull(agendamento.id,'')            as _IDAGE,     " +
						"ifnull(agendamento.tipo,'')          as _TIPOAGE,   " +
						"ifnull(agendamento.data,'')          as _DATA,      " +
						"ifnull(agendamento.hora,'')          as _HORA,      " +
						"ifnull(agendamento.mobile,'')        as _MOBILE,    " +
						"ifnull(agendamento.situacao,'')      as _SITAGE,  " +
						"ifnull(agendamento.motivonvenda,'')  as _MOTIVONVENDA,  " +
						"ifnull(agendamento.motivonvisita,'') as _MOTIVONVISITA, " +
						"(select sum(receber.saldo) from receber where receber.cliente = cliente.codigo and receber.loja = cliente.loja and receber.saldo > 0 and receber.vencto  < '20170719') as divida "+
						" from " + getRegistro().getFileName() +
						" left join CANAL             on CANAL.codigo = CLIENTE.canal  " +
						" left join REDE              on rede.codigo = CLIENTE.rede  " +
						" left join TABPRECOCABEC     on TABPRECOCABEC.codigo = CLIENTE.tabela  " +
						" left join REGIAO            on REGIAO.codigo = CLIENTE.regiao  " +
						" left join CONDPAGTO         on CONDPAGTO.codigo = CLIENTE.condpagto  "+
						" left join AGENDAMENTO       on AGENDAMENTO.cliente = CLIENTE.codigo and  AGENDAMENTO.loja = CLIENTE.LOJA and AGENDAMENTO.data = '" + App.getHoje() + "'  ";
			} else {
				sql = "select CLIENTE.*," +
						"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('0','1','2','3','4') ) AS AMARELO, " +
						"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('6','7','99') )        AS VERMELHO, " +
						"canal.descricao, " +
						"rede.descricao, " +
						"TABPRECOCABEC.descricao, " +
						"regiao.descricao, " +
						"condpagto.descricao,  " +
						"agendamento.filial   as _FILAGE,  " +
						"agendamento.id       as _IDAGE,   " +
						"agendamento.tipo     as _TIPOAGE, " +
						"agendamento.data   as _DATA,      " +
						"agendamento.hora   as _HORA,      " +
						"agendamento.mobile as _MOBILE,    " +
						"agendamento.situacao as _SITAGE,  " +
						"agendamento.motivonvenda  as _MOTIVONVENDA,  " +
						"agendamento.motivonvisita as _MOTIVONVISITA, " +
						"(select sum(receber.saldo) from receber where receber.cliente = cliente.codigo and receber.loja = cliente.loja and receber.saldo > 0 and receber.vencto  < '20170719') as divida "+
						" from " + getRegistro().getFileName() +
						" left join CANAL             on CANAL.codigo = CLIENTE.canal  " +
						" left join REDE              on rede.codigo = CLIENTE.rede  " +
						" left join TABPRECOCABEC     on TABPRECOCABEC.codigo = CLIENTE.tabela  " +
						" left join REGIAO            on REGIAO.codigo = CLIENTE.regiao  " +
						" left join CONDPAGTO         on CONDPAGTO.codigo = CLIENTE.condpagto  " ;

				if (open.equals("OPEN")){

					sql += " inner join AGENDAMENTO   on AGENDAMENTO.cliente = CLIENTE.codigo and  AGENDAMENTO.loja = CLIENTE.LOJA and AGENDAMENTO.data < '" + data + "' and AGENDAMENTO.situacao <> 'E'  ";

				} else {

					sql += " inner join AGENDAMENTO   on AGENDAMENTO.cliente = CLIENTE.codigo and  AGENDAMENTO.loja = CLIENTE.LOJA and AGENDAMENTO.data = '" + data + "'  ";

				}

			}

			if (ordem.trim().isEmpty()) {

				sql += " order by  cliente.razao ";

			}
			else {

				sql += 	" order by  "+ordem;

			}

			cursor = getDataBase().rawQuery(sql, null);

			if (!cursor.moveToFirst()){

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursorToObj_fast(cursor));

					cursor.moveToNext();

				}
			}


		}catch (Exception e) {

			new Exception(e.getMessage());

		}

		return result;

	}


	public List<Cliente_fast> getAll_fastByOnlyFlags(String ordem) {
		Cursor cursor = null;

		ArrayList<Cliente_fast> result = new ArrayList<>();

		try {

			String sql = "select CLIENTE.*,"+
					"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('0','1','2','3','4') ) AS AMARELO, " +
					"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('6','7','99') )        AS VERMELHO, " +
					"canal.descricao, "      +
					"rede.descricao, "       +
					"TABPRECOCABEC.descricao, "+
					"regiao.descricao, "     +
					"condpagto.descricao,  "  +
					"' ' as _FILAGE,  " +
					"' ' as _IDAGE,   " +
					"' ' as _TIPOAGE, " +
					"' ' as _DATA,    " +
					"' ' as _HORA,    " +
					"' ' as _MOBILE,  " +
					"' ' as _SITAGE,  " +
					"' ' as _MOTIVONVENDA,  "+
					"' ' as _MOTIVONVISITA, "+
					"(select sum(receber.saldo) from receber where receber.cliente = cliente.codigo and receber.loja = cliente.loja and receber.saldo > 0 and receber.vencto  < '20170719') as divida "+
					" from " + getRegistro().getFileName() +
					" left join CANAL             on CANAL.codigo = CLIENTE.canal  " +
					" left join REDE              on rede.codigo = CLIENTE.rede  " +
					" left join TABPRECOCABEC     on TABPRECOCABEC.codigo = CLIENTE.tabela  "+
					" left join REGIAO            on REGIAO.codigo = CLIENTE.regiao  "+
					" left join CONDPAGTO         on CONDPAGTO.codigo = CLIENTE.condpagto  ";

			if (ordem.trim().isEmpty()) {

				sql += " order by  cliente.razao ";

			}
			else {

				sql += 	" order by  "+ordem;

			}

			cursor = getDataBase().rawQuery(sql, null);

			if (!cursor.moveToFirst()){

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					Cliente_fast fast = cursorToObj_fast(cursor);

					if (fast.get_yellow() > 0 || fast.get_red() > 0){

						result.add(fast);

					}

					cursor.moveToNext();

				}
			}


		}catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;

	}

	public Cliente_fast seek_fast(String Codigo, String Loja, String Data) {

		Cursor cursor       = null;

		Cliente_fast result = null;

		try {

			String sql = "";

			if (Data.trim().isEmpty()) {

				sql = "select CLIENTE.*," +
						"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('0','1','2','3','4') ) AS AMARELO, " +
						"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('6','7','99') )        AS VERMELHO, " +
						"canal.descricao, " +
						"rede.descricao, " +
						"TABPRECOCABEC.descricao, " +
						"regiao.descricao, " +
						"condpagto.descricao,  " +
						"' ' as _FILAGE,  " +
						"' ' as _IDAGE,   " +
						"' ' as _TIPOAGE, " +
						"' ' as _DATA,    " +
						"' ' as _HORA,    " +
						"' ' as _MOBILE,  " +
						"' ' as _SITAGE,  " +
						"' ' as _MOTIVONVENDA,  "+
						"' ' as _MOTIVONVISITA, "+
						"(select sum(receber.saldo) from receber where receber.cliente = cliente.codigo and receber.loja = cliente.loja and receber.saldo > 0 and receber.vencto  < '20170719') as divida "+
						" from " + getRegistro().getFileName() +
						" left join CANAL             on CANAL.codigo = CLIENTE.canal  " +
						" left join REDE              on rede.codigo = CLIENTE.rede  " +
						" left join TABPRECOCABEC     on TABPRECOCABEC.codigo = CLIENTE.tabela  " +
						" left join REGIAO            on REGIAO.codigo = CLIENTE.regiao  " +
						" left join CONDPAGTO         on CONDPAGTO.codigo = CLIENTE.condpagto  "+
						" where CLIENTE.codigo = '"+Codigo+"' and CLIENTE.loja = '"+Loja+"' ";
			} else {

				sql = "select CLIENTE.*," +
						"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('0','1','2','3','4') ) AS AMARELO, " +
						"(SELECT COUNT(*) FROM PEDIDOCABMB WHERE (PEDIDOCABMB.CODIGOFAT = CLIENTE.CODIGO AND PEDIDOCABMB.LOJAFAT = CLIENTE.LOJA) AND PEDIDOCABMB.STATUS IN ('6','7','99') )        AS VERMELHO, " +
						"canal.descricao, " +
						"rede.descricao, " +
						"TABPRECOCABEC.descricao, " +
						"regiao.descricao, " +
						"condpagto.descricao,  " +
						"ifnull(agendamento.filial,'')        as _FILAGE,    " +
						"ifnull(agendamento.id,'')            as _IDAGE,     " +
						"ifnull(agendamento.tipo,'')          as _TIPOAGE,   " +
						"ifnull(agendamento.data,'')          as _DATA,      " +
						"ifnull(agendamento.hora,'')          as _HORA,      " +
						"ifnull(agendamento.mobile,'')        as _MOBILE,    " +
						"ifnull(agendamento.situacao,'')      as _SITAGE,  " +
						"ifnull(agendamento.motivonvenda,'')  as _MOTIVONVENDA,  " +
						"ifnull(agendamento.motivonvisita,'') as _MOTIVONVISITA, " +
						"(select sum(receber.saldo) from receber where receber.cliente = cliente.codigo and receber.loja = cliente.loja and receber.saldo > 0 and receber.vencto  < '20170719') as divida "+
						" from " + getRegistro().getFileName() +
						" left join CANAL             on CANAL.codigo = CLIENTE.canal  " +
						" left join REDE              on rede.codigo = CLIENTE.rede  " +
						" left join TABPRECOCABEC     on TABPRECOCABEC.codigo = CLIENTE.tabela  " +
						" left join REGIAO            on REGIAO.codigo = CLIENTE.regiao  " +
						" left join CONDPAGTO         on CONDPAGTO.codigo = CLIENTE.condpagto  " +
						" left join AGENDAMENTO   on AGENDAMENTO.cliente = CLIENTE.codigo and  AGENDAMENTO.loja = CLIENTE.LOJA and AGENDAMENTO.data = '" + Data + "'  "+
						" where CLIENTE.codigo = '"+Codigo+"' and CLIENTE.loja = '"+Loja+"' ";
			}


			cursor = getDataBase().rawQuery(sql, null);


			if (!cursor.moveToFirst()) {

				result = null;

			} else {



				result = cursorToObj_fast(cursor);


			}


		}catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;

	}


	@Override
	public Cliente seek(String[] values) {

		Cliente obj = null;

		try {

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0],values[1]},null, null, null);

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



	public void updateRota(String Codigo, String Loja, String ROTDTBA,String ROTHORA, String ROTPERI, String ROTOBSVI) throws Exception{

		Cursor cursor = null;

		SQLiteStatement stmtd;

		try {

			String sql = "update  CLIENTE  set ROTDTBA = '"+ROTDTBA+"', ROTHORA = '"+ROTHORA+"', ROTPERI = '"+ROTPERI+"', ROTOBSVI = '"+ROTOBSVI+"' "+
					" where codigo = '"+Codigo+"' and loja = '"+Loja+"' ";

			stmtd = getDataBase().compileStatement(sql);
			stmtd.execute();

		}catch (Exception e) {

			new Exception(e.getMessage());

		}

	}


	private String getNoNULL(String value) {

		if (value == null){

			value = "";

		}

		return value;
	}

	private Float getNoNULLFloat(Float value) {

		if (value == null){

			value = 0f;

		}

		return value;
	}

}
