package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.TabPrecoCabec;
import br.com.brotolegal.savdatabase.regrasdenegocio.TabPreco;


public class TabPrecoCabecDAO  extends DAO2 implements IDao2<TabPrecoCabec> {



	private final String TAG = "TABPRECOCABECDAO";

	private String whereClausePrimary = " codigo = ? ";

	private String whereClauseCarga = " codigo = ? or flagfaixa = '1' ";

	public TabPrecoCabecDAO() throws Exception {
		super("TABPRECOCABEC","dbuser");
	}


	@Override
	public TabPrecoCabec insert(TabPrecoCabec obj) {
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
	public Boolean Update(TabPrecoCabec obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getCODIGO()});

		} catch (Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;
	}

	@Override
	public TabPrecoCabec cursorToObj(Cursor cursor) {

		TabPrecoCabec tabprecocabec = null;
		try {
			tabprecocabec = new TabPrecoCabec(
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
					cursor.getString(12),
					cursor.getString(13),
					cursor.getString(14)
					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return tabprecocabec;

	}

	@Override
	public List<TabPrecoCabec> getAll() {
		Cursor cursor = null;

		List<TabPrecoCabec> result = new ArrayList<>();

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
	public TabPrecoCabec seek(String[] values) {
		TabPrecoCabec obj = null;

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


	public List<TabPreco> seekTabelaByPedido(String Codigo, String pedido) {

		List<TabPreco> lstabpreco = new ArrayList<TabPreco>();

		try {

			String select =  "select "               +
					"tabprecocabec.codigo, "         +
					"tabprecocabec.descricao, "      +
					"tabprecocabec.flagfaixa, "      +
					"tabprecocabec.flaghabdesc, "    +
					"tabprecocabec.flaghabmotivo, "  +
					"tabprecocabec.flagcontrato, "   +
					"tabprecocabec.flagcc, "         +
					"tabprecocabec.flagtaxafinanc, " +
					"tabprecocabec.flagdesccanal, "  +
					"tabprecocabec.flagdesclogist, " +
					"tabprecocabec.faixade, "        +
					"tabprecocabec.faixaate, "       +
					"tabprecocabec.tipocontrato, "   +
					"tabprecocabec.tipofrete, "      +
					"tabprecocabec.tipoprazo, "      +
					"tabprecodet.PRODUTO,"           +
					"tabprecodet.PRCVEN,"            +
					"tabprecodet.DESCONTOMAIS,"      +
					"tabprecodet.ACRESCIMOMAIS, "    +
					"produto.DESCRICAO,  "           +
					"produto.UM,         "           +
					"produto.GRUPO,      "           +
					"produto.MARCA,      "           +
					"produto.ORIGEM,     "           +
					"produto.CONVERSAO,  "           +
					"grupo.DESCRICAO,    "           +
					"marca.DESCRICAO,    "           +
					"pedidodetmb.ITEM,   "           +
					"tabprecodet.FATOR,  "           +
					"tabprecodet.PRCBASE, "          +
					"tabprecodet.POLITICABASE, "     +
					"tabprecodet.CUSTOOPER, "        +
					"tabprecodet.BDI, "              +
					"tabprecodet.PERCONTRATO, "      +
					"tabprecodet.PERPRAZO "
					+ "from       tabprecocabec "
					+ "inner join tabprecodet on tabprecodet.codigo = tabprecocabec.codigo "
					+ "inner join produto     on produto.codigo     = tabprecodet.produto  "
					+ "inner join grupo       on grupo.codigo       = produto.grupo "
					+ "inner join marca       on marca.codigo       = produto.marca "
					+ "inner join pedidodetmb on pedidodetmb.nro    = '"+pedido+"' and  pedidodetmb.produto = tabprecodet.produto "
					+ "where tabprecocabec.codigo = '"+ Codigo + "' "
					+ "order by tabprecocabec.codigo,produto.grupo,produto.descricao ";

			Cursor cursor;

			cursor = getDataBase().rawQuery(select , null);

			if (!cursor.moveToFirst()){

				lstabpreco = new ArrayList<TabPreco>();

			} else {

				while (!(cursor.isAfterLast())) {

					TabPreco tabpreco = new TabPreco(
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
							cursor.getString(12),
							cursor.getString(13),
							cursor.getString(14),
							cursor.getString(15),
							cursor.getFloat(16),
							cursor.getFloat(17),
							cursor.getFloat(18),
							cursor.getString(19),
							cursor.getString(20),
							cursor.getString(21),
							cursor.getString(22),
							cursor.getString(23),
							cursor.getFloat(24),
							cursor.getString(25),
							cursor.getString(26),
							cursor.getString(27),
							cursor.getString(28),
							cursor.getFloat(29),
							cursor.getFloat(30),
							cursor.getFloat(31),
							cursor.getFloat(32),
							cursor.getFloat(33),
							cursor.getFloat(34)


							);

					lstabpreco.add(tabpreco);

					cursor.moveToNext();
				}
			}
		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return lstabpreco;

	}


	public List<PedidoDetMb> seekDetalheByPedido(String Codigo, String pedido) {

		List<PedidoDetMb> lsDet = new ArrayList<>();

		DecimalFormat format_02 = new DecimalFormat(",##0.00");

		try {

			String select =  "select pedidodetmb.*, " +
					"produto.DESCRICAO     , "+
					"produto.GRUPO , "+
					"produto.MARCA , "+
					"produto.CONVERSAO , "+
					"acordo.CODIGO , "+
					"acordo.SALDO    "
					+ "from       tabprecocabec "
					+ "inner join tabprecodet on tabprecodet.codigo = tabprecocabec.codigo "
					+ "inner join produto     on produto.codigo     = tabprecodet.produto  "
					+ "inner join grupo       on grupo.codigo       = produto.grupo "
					+ "inner join marca       on marca.codigo       = produto.marca "
					+ "inner join pedidodetmb on pedidodetmb.nro    = '"+pedido+"' and  pedidodetmb.produto = tabprecodet.produto "
					+ "left  join acordo      on acordo.codigo      = pedidodetmb.acordo "
					+ "where tabprecocabec.codigo = '"+ Codigo + "' "
					+ "order by produto.descricao ";

			Cursor cursor;

			cursor = getDataBase().rawQuery(select , null);

			if (!cursor.moveToFirst()){

				lsDet = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					PedidoDetMb det = new PedidoDetMb(
							cursor.getString(0),
							cursor.getString(1),
							cursor.getString(2),
							cursor.getString(3),
							cursor.getString(4),
							cursor.getString(5),
							cursor.getString(6),
							cursor.getFloat(7),
							cursor.getString(8),
							cursor.getFloat(9),
							cursor.getFloat(10),
							cursor.getFloat(11),
							cursor.getString(12),
							cursor.getFloat(13),
							cursor.getString(14),
							cursor.getFloat(15),
							cursor.getFloat(16),
							cursor.getFloat(17),
							cursor.getFloat(18),
							cursor.getString(19),
							cursor.getString(20),
							cursor.getString(21),
							cursor.getString(22),
							cursor.getString(23),
							cursor.getString(24),
							cursor.getFloat(25),
							cursor.getFloat(26),
							cursor.getFloat(27),
							cursor.getFloat(28),
							cursor.getFloat(29),
							cursor.getFloat(30),
							cursor.getFloat(31),
							cursor.getFloat(32),
							cursor.getString(33),
							cursor.getString(34),
							cursor.getString(35),
							cursor.getString(36),
							cursor.getString(37),
							cursor.getString(38),
							cursor.getString(39),
							cursor.getString(40),
							cursor.getString(41),
							cursor.getFloat(42),
							cursor.getString(43),
							cursor.getFloat(44),
							cursor.getFloat(45),
							cursor.getString(46),
							cursor.getString(47),
							cursor.getString(48),
							cursor.getString(49),
							cursor.getInt(50),
							cursor.getString(51),
							cursor.getFloat(52),
							cursor.getFloat(53),
							cursor.getFloat(54),
							cursor.getFloat(55),
							cursor.getFloat(56),
							cursor.getFloat(57)


							);

					det.set_Produto(cursor.getString(52));
					det.set_Grupo(cursor.getString(53));
					det.set_Marca(cursor.getString(54));
					det.setCONVERSAO(cursor.getFloat(55));

					if (cursor.getString(56) == null){

						det.set_Acordo("");

					} else {

						det.set_Acordo("Cód.: " + cursor.getString(56) + " Saldo: " + format_02.format(cursor.getFloat(57)));
					}


					lsDet.add(det);

					cursor.moveToNext();
				}
			}
		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return lsDet;

	}


	public List<TabPreco> seekTabela(String Codigo, String Tipo) {

		List<TabPreco> lstabpreco = new ArrayList<TabPreco>();

		try {

			String select =
					"select "                       +
							"tabprecocabec.codigo, "         +
							"tabprecocabec.descricao, "      +
							"tabprecocabec.flagfaixa, "      +
							"tabprecocabec.flaghabdesc, "    +
							"tabprecocabec.flaghabmotivo, "  +
							"tabprecocabec.flagcontrato, "   +
							"tabprecocabec.flagcc, "         +
							"tabprecocabec.flagtaxafinanc, " +
							"tabprecocabec.flagdesccanal, "  +
							"tabprecocabec.flagdesclogist, " +
							"tabprecocabec.faixade, "        +
							"tabprecocabec.faixaate, "       +
							"tabprecocabec.tipocontrato, "   +
							"tabprecocabec.tipofrete, "      +
							"tabprecocabec.tipoprazo, "      +
							"tabprecodet.PRODUTO,"           +
							"tabprecodet.PRCVEN,"            +
							"tabprecodet.DESCONTOMAIS,"      +
							"tabprecodet.ACRESCIMOMAIS, "    +
							"produto.DESCRICAO,  "           +
							"produto.UM,         "           +
							"produto.GRUPO,      "           +
							"produto.MARCA,      "           +
							"produto.ORIGEM,     "           +
							"produto.CONVERSAO,  "           +
							"grupo.DESCRICAO,    "           +
							"marca.DESCRICAO,    "           +
							"' ' as ITEM,   "           +
							"tabprecodet.FATOR,  "           +
							"tabprecodet.PRCBASE, "          +
							"tabprecodet.POLITICABASE, "     +
							"tabprecodet.CUSTOOPER, "     +
							"tabprecodet.BDI, "     +
							"tabprecodet.PERCONTRATO, "     +
							"tabprecodet.PERPRAZO "
							+ "from       tabprecocabec "
							+ "inner join tabprecodet on tabprecodet.codigo = tabprecocabec.codigo "
							+ "inner join produto     on produto.codigo     = tabprecodet.produto and produto.tipopedido like '%"+Tipo+"%' "
							+ "inner join grupo       on grupo.codigo       = produto.grupo "
							+ "inner join marca       on marca.codigo       = produto.marca "
							+ "where tabprecocabec.codigo =  '" + Codigo + "' "
							+ "order by produto.descricao";

			Cursor cursor;

			cursor = getDataBase().rawQuery(select , null);
			if (!cursor.moveToFirst()){

				lstabpreco = new ArrayList<TabPreco>();

			} else {

				while (!(cursor.isAfterLast())) {

					TabPreco tabpreco = new TabPreco(
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
							cursor.getString(12),
							cursor.getString(13),
							cursor.getString(14),
							cursor.getString(15),
							cursor.getFloat(16),
							cursor.getFloat(17),
							cursor.getFloat(18),
							cursor.getString(19),
							cursor.getString(20),
							cursor.getString(21),
							cursor.getString(22),
							cursor.getString(23),
							cursor.getFloat(24),
							cursor.getString(25),
							cursor.getString(26),
							cursor.getString(27),
							cursor.getString(28),
							cursor.getFloat(29),
							cursor.getFloat(30),
							cursor.getFloat(31),
							cursor.getFloat(32),
							cursor.getFloat(33),
							cursor.getFloat(34)


							);

					lstabpreco.add(tabpreco);

					cursor.moveToNext();
				}
			}
		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return lstabpreco;

	}


	public List<TabPrecoCabec> seekTabelaCarga(String Codigo) {

		Cursor cursor = null;

		List<TabPrecoCabec> result = new ArrayList<>();

		try {


			String select      =  "select * "
					+ "from  tabprecocabec "
					+ "where ( (tabprecocabec.codigo =  '"+ Codigo + "') ";
			if (Codigo.compareTo("500") >= 0) select += " or (tabprecocabec.flagfaixa = '1') ";
			select +=  " ) ";
			select +=  "order by tabprecocabec.codigo ";

			cursor = getDataBase().rawQuery(select , null);

			if (!cursor.moveToFirst()){

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursorToObj(cursor));

					cursor.moveToNext();

				}
			}


		}catch (Exception e) {

			new Exception(e.getMessage());

		}

		return result;
	}

}
