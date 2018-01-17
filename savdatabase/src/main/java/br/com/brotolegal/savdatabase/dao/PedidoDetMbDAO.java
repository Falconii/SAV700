package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;


public class PedidoDetMbDAO  extends DAO2 implements IDao2<PedidoDetMb> {

	private final String TAG = "PEDIDODETMB";

	private String whereClausePrimary = " nro = ? and item = ? ";

	private String whereClauseProduto = " nro = ? and produto = ? ";

	private String whereClausePedido  = " nro = ? ";


	public PedidoDetMbDAO() throws Exception {

		super("PEDIDODETMB","dbuser");

	}


	@Override
	public PedidoDetMb insert(PedidoDetMb obj) {
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

			regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[]{values[0],values[1]});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return regs;
	}

	@Override
	public Boolean Update(PedidoDetMb obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[]{obj.getNRO(), obj.getITEM()});

		} catch (Exception e) {

			new Exception(e.getMessage());

		}

		return retorno == 0 ? false : true;
	}

	@Override
	public PedidoDetMb cursorToObj(Cursor cursor) {

		PedidoDetMb pedidodetmb = null;
		try {
			pedidodetmb = new PedidoDetMb(
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
					cursor.getFloat(56)

					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return pedidodetmb;

	}

	@Override
	public List<PedidoDetMb> getAll() {
		Cursor cursor = null;

		List<PedidoDetMb> result = new ArrayList<>();

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
	public PedidoDetMb seek(String[] values) {
		PedidoDetMb obj = null;

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

	public List<PedidoDetMb> getAllByPedido(String[] values) {

		List<PedidoDetMb> result = new ArrayList<>();

		Cursor cursor = null;

		String whereClause = " NRO = ? ";

		try {

			cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClause, new String[] {values[0]},null, null, null);

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

		cursor.close();

		return result;
	}


	public List<PedidoDetMB_fast> getAllByPedidoFast(String[] values) {

		List<PedidoDetMB_fast> result = new ArrayList<>();

		Cursor cursor = null;


		try {


			String select = getPedidoFast(values)
					+ "from  pedidodetmb "
					+ "left join pedidocabmb                  on pedidocabmb.nro             = pedidodetmb.nro "
					+ "left join produto                      on pedidodetmb.produto         = produto.codigo "
					+ "left join verba                        on pedidodetmb.codverba        = verba.codigo   "
					+ "left join verba    verba2              on pedidodetmb.codverba2       = verba2.codigo  "
					+ "left join acordo                       on pedidodetmb.acordo          = acordo.codigo  "
					+ "left join acordo   acordo2             on pedidodetmb.acordo2         = acordo2.codigo "
					+ "left join simulador                    on pedidodetmb.simulador       = simulador.codsimulador "
					+ "                                       and pedidocabmb.codigofat      = simulador.codcli"
					+ "                                       and pedidocabmb.lojafat        = simulador.lojacli"
					+ "                                       and pedidodetmb.produto        = simulador.codproduto "
					+ "left join simulador  simulador2        on     pedidodetmb.simulador   = simulador2.codsimulador "
					+ "                                       and pedidocabmb.codigofat      = simulador2.codcli"
					+ "                                       and pedidocabmb.lojafat        = simulador2.lojacli"
					+ "                                       and pedidodetmb.produto        = simulador2.codproduto "
					+ "left join grupo             on grupo.codigo       = produto.grupo "
					+ "left join marca             on marca.codigo       = produto.marca "
					+ "left  join MotivosTrocaDev   on MotivosTrocaDev.codigo    = pedidodetmb.codverba "
					+ "left  join meta              on meta.data = '"+App.getHojeaaaamm()+"' and meta.cliente = '"+ values[1] +"' and meta.loja = '"+ values[2]+"' and meta.produto = produto.codigo "
					+ "where ( (pedidodetmb.nro =  '"+ values[0] + "') ) "
					+ "order by pedidodetmb.item ";

			cursor = getDataBase().rawQuery(select , null);

			if (!cursor.moveToFirst()){

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursorToObjFast(cursor));

					cursor.moveToNext();

				}
			}


		}catch (Exception e) {

			new Exception(e.getMessage());

		}

		cursor.close();

		return result;
	}

	/*
	  0->tabela de preço
	  1->nro do pedido
	  2->codigo do cliente
	  3->loja do cliente
	 */
	public List<PedidoDetMB_fast> getTabelaPedFast(String[] values) {

		List<PedidoDetMB_fast> result = new ArrayList<>();

		Cursor cursor = null;

		try {

			String select = getPedidoFast(values)
					+ "from  tabprecodet "
					+ "left join pedidodetmb               on pedidodetmb.nro = '"+values[0]+"' and pedidodetmb.produto  = tabprecodet.produto "
					+ "inner join produto                  on tabprecodet.produto        =  produto.codigo and produto.tipopedido like '%"+values[4]+"%'"
					+ "left join verba                     on pedidodetmb.codverba       =  verba.codigo   "
					+ "left join verba    verba2           on pedidodetmb.codverba2      =  verba2.codigo  "
					+ "left join acordo                    on pedidodetmb.acordo             =  acordo.codigo  "
					+ "left join acordo   acordo2          on pedidodetmb.acordo2            =  acordo2.codigo "
					+ "left join simulador                 on pedidodetmb.simulador          =  simulador.codsimulador "
					+ "                                       and '"+ values[1] + "'         = simulador.codcli"
					+ "                                       and '"+ values[2] + "'         = simulador.lojacli"
					+ "                                       and pedidodetmb.produto        = simulador.codproduto "
					+ "left join simulador  simulador2        on     pedidodetmb.simulador   = simulador2.codsimulador "
					+ "                                       and '"+ values[1] + "'         = simulador2.codcli"
					+ "                                       and '"+ values[2] + "'         = simulador2.lojacli"
					+ "                                       and pedidodetmb.produto        = simulador2.codproduto "
					+ "inner join grupo             on grupo.codigo       = produto.grupo "
					+ "inner join marca             on marca.codigo       = produto.marca "
					+ "left  join MotivosTrocaDev   on MotivosTrocaDev.codigo    = pedidodetmb.codverba "
					+ "left  join meta              on meta.data = '"+App.getHojeaaaamm()+"' and meta.cliente = '"+ values[1] +"' and meta.loja = '"+ values[2]+"' and meta.produto = produto.codigo "
					+ "where ( (tabprecodet.codigo =  '"+ values[3] +"' ) ) "
					//+ "where ( (tabprecodet.codigo =  '"+ values[3] +"')  and ( trim(tabprecodet.produto) = '124300201' ) ) "
					+ "order by produto.descricao ";


			cursor = getDataBase().rawQuery(select , null);

			Log.i("111",select);

			if (!cursor.moveToFirst()){

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursorToObjFast(cursor));

					cursor.moveToNext();

				}
			}


		}catch (Exception e) {

			new Exception(e.getMessage());

		}

		cursor.close();

		return result;
	}


	/*
       0->nro do pedido
    */

	public List<PedidoDetMB_fast> getDetalheFast(String[] values) {

		List<PedidoDetMB_fast> result = new ArrayList<>();

		Cursor cursor = null;


		try {

			String select = getPedidoFast2(values)
					+ "from  pedidodetmb "
					+ "inner join produto                  on produto.codigo             = pedidodetmb.produto "
					+ "left join verba                     on pedidodetmb.codverba       =  verba.codigo   "
					+ "left join verba    verba2           on pedidodetmb.codverba2      =  verba2.codigo  "
					+ "inner join grupo                    on grupo.codigo               = produto.grupo "
					+ "inner join marca                    on marca.codigo               = produto.marca "
					+ "left  join MotivosTrocaDev          on MotivosTrocaDev.codigo    = pedidodetmb.codverba "
					+ "left  join meta                     on meta.data = '"+App.getHojeaaaamm()+"' and meta.cliente = '"+ values[1] +"' and meta.loja = '"+ values[2]+"' and meta.produto = produto.codigo "
					+ "where ( pedidodetmb.nro = '"+values[0]+"' ) "
					+ "order by pedidodetmb.item ";

			cursor = getDataBase().rawQuery(select , null);

			if (!cursor.moveToFirst()){

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursorToObjFast(cursor));

					cursor.moveToNext();

				}
			}


		}catch (Exception e) {

			new Exception(e.getMessage());

		}

		cursor.close();

		return result;
	}

	public PedidoDetMb seekByProduto(String[] values) {
		PedidoDetMb obj = null;

		try{

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClauseProduto, new String[] {values[0],values[1]},null, null, null);

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

	public PedidoDetMB_fast cursorToObjFast(Cursor cursor) {

		String id;

		PedidoDetMB_fast pedidodetmb = null;

		if (cursor.getString(1).equals("000")){

			id = String.valueOf(cursor.getPosition()+1);

		} else {


			id = cursor.getString(1);

		}
		try {

			String descricao;


			pedidodetmb = new PedidoDetMB_fast(


					cursor.getString(0),
					id,
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
					cursor.getString(57),
					cursor.getString(58),
					cursor.getString(59),
					cursor.getString(60),
					cursor.getString(61),
					cursor.getString(62),
					cursor.getString(63),
					cursor.getString(64),
					cursor.getString(65),
					cursor.getString(66),
					cursor.getString(67),
					cursor.getFloat(68),
					cursor.getFloat(69),
					cursor.getFloat(70),
					cursor.getString(71),
					( cursor.getInt(72) == 0 ? false : true),
					cursor.getFloat(73),
					cursor.getFloat(74),
					cursor.getFloat(75),
					cursor.getString(76),
					cursor.getString(77)
					);

		} catch (Exception e){

			new Exception(e.getMessage());

		}

		return pedidodetmb;

	}

	private String getPedidoFast(String[] values){

		String sql = "";


        sql     = " select "
                + "ifnull(pedidodetmb.nro,''),   "
                + "ifnull(pedidodetmb.item,'000'),   "
                + "ifnull(pedidodetmb.cprotheus,''),   "
                + "ifnull(pedidodetmb.cprotheusb,''),   "
                + "ifnull(pedidodetmb.status,'2'),   "
                + "ifnull(pedidodetmb.produto,produto.codigo), "
                + "ifnull(pedidodetmb.um,produto.um), "
                + "ifnull(pedidodetmb.qtd,0),   "
                + "ifnull(pedidodetmb.origem,produto.origem),  "
                + "pedidodetmb.prcven,   "
                + "pedidodetmb.descon,   "
                + "ifnull(pedidodetmb.descver,0),   "
                + "ifnull(pedidodetmb.codverba,''),   "
                + "ifnull(pedidodetmb.descver2,0),   "
                + "ifnull(pedidodetmb.codverba2,''),   "
                + "ifnull(pedidodetmb.total,0),   "
                + "ifnull(pedidodetmb.boniqtd,0),   "
                + "ifnull(pedidodetmb.boniprec,0),   "
                + "ifnull(pedidodetmb.bonitotal,0),   "
                + "ifnull(pedidodetmb.peddistfil,''),   "
                + "ifnull(pedidodetmb.peddist,''),   "
                + "ifnull(pedidodetmb.peddistitem,''),   "
                + "ifnull(pedidodetmb.peddistfil2,''),   "
                + "ifnull(pedidodetmb.peddist2,''),   "
                + "ifnull(pedidodetmb.peddistitem2,''),   "
                + "pedidodetmb.precoformacao,   "
                + "pedidodetmb.desccontrato,   "
                + "pedidodetmb.precotabela,   "
                + "pedidodetmb.acrescimomais,   "
                + "pedidodetmb.descontomais,   "
                + "pedidodetmb.descontopol,   "
                + "pedidodetmb.dnadesconto,   "
                + "pedidodetmb.dnavalor,   "
                + "ifnull(pedidodetmb.usataxafin,''),   "
                + "ifnull(pedidodetmb.taxafin,''),   "
                + "pedidodetmb.mensagem,   "
                + "ifnull(pedidodetmb.acordo,''),      "
                + "ifnull(pedidodetmb.acordo2,''),    "
                + "ifnull(pedidodetmb.simulador,''),    "
                + "ifnull(pedidodetmb.simulador2,''),   "
                + "ifnull(pedidodetmb.pedcli,''),    "
                + "ifnull(pedidodetmb.pedcli2,''),   "
                + "pedidodetmb.frete,   "
                + "pedidodetmb.fator,   "
                + "pedidodetmb.imposto,   "
                + "ifnull(pedidodetmb.conversao,produto.conversao),   "
                + "ifnull(pedidodetmb.lote,''),    "
                + "ifnull(pedidodetmb.empacotamento,''), "
                + "ifnull(pedidodetmb.vencimento,''),    "
                + "ifnull(pedidodetmb.obs,''),   "
                + "ifnull(produto.unidade,1),   "
                + "ifnull(pedidodetmb.cota,''),  "
                + "ifnull(pedidodetmb.vlrcampanha,0),  "
                + "ifnull(pedidodetmb.vlrdescarga2,0),  "
				+ "ifnull(pedidodetmb.politicabase,tabprecodet.politicabase),  "
				+ "ifnull(pedidodetmb.custooper,tabprecodet.custooper),  "
				+ "ifnull(pedidodetmb.bdi,tabprecodet.bdi),  "
                + "ifnull(produto.descricao,''),"
                + "ifnull(verba.descricao,''),  "
                + "ifnull(verba2.descricao,''), "
                + "ifnull(acordo.desctipo,''),  "
                + "ifnull(acordo2.desctipo,''), "
                + "ifnull(simulador.precoaprovado,0),  "
                + "ifnull(simulador2.precoaprovado,0),  "
                + "grupo.codigo, "
                + "grupo.descricao,"
                + "marca.codigo,  "
                + "marca.descricao, "
                + "(select peddettvs.prcven from peddettvs "
                + "inner join pedcabtvs on peddettvs.filial = pedcabtvs.filial and pedcabtvs.pedido = peddettvs.pedido and pedcabtvs.cliente = '"+values[1] +"' and pedcabtvs.loja = '"+values[2] +"'  where  peddettvs.produto = tabprecodet.produto order by pedcabtvs.entrega,pedcabtvs.pedido ASC limit 1) as ultimoprcven, "
                + "ifnull(produto.pesobruto,0), "
                + "ifnull(produto.pesoliqui,0), "
                + "ifnull(MotivosTrocaDev.codigo,'') || '-' || ifnull(MotivosTrocaDev.descricao,''),  "
                + "0 as mix,  "
                + "ifnull(meta.objetivo,0),  "
                + "ifnull(meta.carteira,0),  "
                + "ifnull(meta.real,0),  "
                + "ifnull(verba2.tipo,'N'),  "
                + " ' ' as PEDIDOMAE   ";


                return sql;


	}

	private String getPedidoFast2(String[] values){

		String sql = "";


		sql     = " select "
				+ "ifnull(pedidodetmb.nro,''),   "
				+ "ifnull(pedidodetmb.item,'000'),   "
				+ "ifnull(pedidodetmb.cprotheus,''),   "
				+ "ifnull(pedidodetmb.cprotheusb,''),   "
				+ "ifnull(pedidodetmb.status,'2'),   "
				+ "ifnull(pedidodetmb.produto,produto.codigo), "
				+ "ifnull(pedidodetmb.um,produto.um), "
				+ "ifnull(pedidodetmb.qtd,0),   "
				+ "ifnull(pedidodetmb.origem,produto.origem),   "
				+ "pedidodetmb.prcven,   "
				+ "pedidodetmb.descon,   "
				+ "ifnull(pedidodetmb.descver,0),   "
				+ "ifnull(pedidodetmb.codverba,''),   "
				+ "ifnull(pedidodetmb.descver2,0),   "
				+ "ifnull(pedidodetmb.codverba2,''),   "
				+ "ifnull(pedidodetmb.total,0),   "
				+ "ifnull(pedidodetmb.boniqtd,0),   "
				+ "ifnull(pedidodetmb.boniprec,0),   "
				+ "ifnull(pedidodetmb.bonitotal,0),   "
				+ "ifnull(pedidodetmb.peddistfil,''),   "
				+ "ifnull(pedidodetmb.peddist,''),   "
				+ "ifnull(pedidodetmb.peddistitem,''),   "
				+ "ifnull(pedidodetmb.peddistfil2,''),   "
				+ "ifnull(pedidodetmb.peddist2,''),   "
				+ "ifnull(pedidodetmb.peddistitem2,''),   "
				+ "pedidodetmb.precoformacao,   "
				+ "pedidodetmb.desccontrato,   "
				+ "pedidodetmb.precotabela,   "
				+ "pedidodetmb.acrescimomais,   "
				+ "pedidodetmb.descontomais,   "
				+ "pedidodetmb.descontopol,   "
				+ "pedidodetmb.dnadesconto,   "
				+ "pedidodetmb.dnavalor,   "
				+ "pedidodetmb.usataxafin,   "
				+ "pedidodetmb.taxafin,   "
				+ "pedidodetmb.mensagem,   "
				+ "ifnull(pedidodetmb.acordo,''),      "
				+ "ifnull(pedidodetmb.acordo2,''),    "
				+ "ifnull(pedidodetmb.simulador,''),    "
				+ "ifnull(pedidodetmb.simulador2,''),   "
				+ "ifnull(pedidodetmb.pedcli,''),    "
				+ "ifnull(pedidodetmb.pedcli2,''),   "
				+ "pedidodetmb.frete,   "
				+ "pedidodetmb.fator,   "
				+ "pedidodetmb.imposto,   "
				+ "ifnull(pedidodetmb.conversao,produto.conversao),   "
				+ "ifnull(pedidodetmb.lote,''),    "
				+ "ifnull(pedidodetmb.empacotamento,''), "
				+ "ifnull(pedidodetmb.vencimento,''),    "
				+ "ifnull(pedidodetmb.obs,''),   "
				+ "ifnull(produto.unidade,1),   "
				+ "ifnull(pedidodetmb.cota,''),  "
				+ "ifnull(pedidodetmb.vlrcampanha,0),  "
				+ "ifnull(pedidodetmb.politicabase,0),  "
				+ "ifnull(pedidodetmb.vlrdescarga2,0),  "
				+ "ifnull(pedidodetmb.custooper,0),  "
				+ "ifnull(pedidodetmb.bdi,0),  "
				+ "ifnull(produto.descricao,''),"
				+ "ifnull(verba.descricao,''),  "
				+ "ifnull(verba2.descricao,''), "
				+ "' '   , " //ifnull(acordo.desctipo,''),  "
				+ "' '   , " //ifnull(acordo2.desctipo,''), "
				+ " 0 as simulador1,  "
				+ " 0 as simulador2,  "
				+ "grupo.codigo, "
				+ "grupo.descricao,"
				+ "marca.codigo,  "
				+ "marca.descricao, "
				+ "(select peddettvs.prcven from peddettvs "
				+ "inner join pedcabtvs on peddettvs.filial = pedcabtvs.filial and pedcabtvs.pedido = peddettvs.pedido and pedcabtvs.cliente = '"+values[1] +"' and pedcabtvs.loja = '"+values[2] +"'  where  peddettvs.produto = pedidodetmb.produto order by pedcabtvs.entrega,pedcabtvs.pedido ASC limit 1) as ultimoprcven, "
				+ "ifnull(produto.pesobruto,0), "
				+ "ifnull(produto.pesoliqui,0), "
                + "ifnull(MotivosTrocaDev.codigo,'') || '-' || ifnull(MotivosTrocaDev.descricao,''),  "
				+ "0 as mix,  "
				+ "ifnull(meta.objetivo,0),  "
				+ "ifnull(meta.carteira,0),  "
				+ "ifnull(meta.real,0),  "
				+ "ifnull(verba2.tipo,'N'),  "
				+ " ' ' as PEDIDOMAE   ";


		return sql;


	}

}
