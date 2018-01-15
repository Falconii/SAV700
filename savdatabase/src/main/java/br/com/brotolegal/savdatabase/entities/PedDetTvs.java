package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class PedDetTvs extends ObjRegister {

	protected String FILIAL;
	protected String PEDIDO;
	protected String ITEM;
	protected String PRODUTO;
	protected String DESCRICAO;
	protected String UM;
	protected Float QTD;
	protected Float DESCONTO;
	protected Float PRCVEN;
	protected Float DESCVER;
	protected String CODVERBA;
	protected String DESCRICAOVERBA;
	protected Float TOTAL;
	protected String ACORDO;
	protected String PDFILIAL;
	protected String PDNUMERO;
	protected String SIMULADOR;
	protected String COTA;


	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.PedDetTvs";
	
	
	public PedDetTvs(){
		
		 super(_OBJETO,"PEDDETTVS");
		 
		 loadColunas();

		 InicializaFields();
	}

	public PedDetTvs(String FILIAL, String PEDIDO, String ITEM, String PRODUTO, String DESCRICAO, String UM, Float QTD, Float DESCONTO, Float PRCVEN, Float DESCVER, String CODVERBA, String DESCRICAOVERBA, Float TOTAL, String ACORDO, String PDFILIAL, String PDNUMERO, String SIMULADOR, String COTA) {

		super(_OBJETO,"PEDDETTVS");

		loadColunas();

		InicializaFields();

		this.FILIAL = FILIAL;
		this.PEDIDO = PEDIDO;
		this.ITEM = ITEM;
		this.PRODUTO = PRODUTO;
		this.DESCRICAO = DESCRICAO;
		this.UM = UM;
		this.QTD = QTD;
		this.DESCONTO = DESCONTO;
		this.PRCVEN = PRCVEN;
		this.DESCVER = DESCVER;
		this.CODVERBA = CODVERBA;
		this.DESCRICAOVERBA = DESCRICAOVERBA;
		this.TOTAL = TOTAL;
		this.ACORDO = ACORDO;
		this.PDFILIAL = PDFILIAL;
		this.PDNUMERO = PDNUMERO;
		this.SIMULADOR = SIMULADOR;
		this.COTA      = COTA;

	}

	public String getFILIAL() {
		return FILIAL;
	}

	public void setFILIAL(String FILIAL) {
		this.FILIAL = FILIAL;
	}

	public String getPEDIDO() {
		return PEDIDO;
	}

	public void setPEDIDO(String PEDIDO) {
		this.PEDIDO = PEDIDO;
	}

	public String getITEM() {
		return ITEM;
	}

	public void setITEM(String ITEM) {
		this.ITEM = ITEM;
	}

	public String getPRODUTO() {
		return PRODUTO;
	}

	public void setPRODUTO(String PRODUTO) {
		this.PRODUTO = PRODUTO;
	}

	public String getDESCRICAO() {
		return DESCRICAO;
	}

	public void setDESCRICAO(String DESCRICAO) {
		this.DESCRICAO = DESCRICAO;
	}

	public String getUM() {
		return UM;
	}

	public void setUM(String UM) {
		this.UM = UM;
	}

	public Float getQTD() {
		return QTD;
	}

	public void setQTD(Float QTD) {
		this.QTD = QTD;
	}

	public Float getDESCONTO() {
		return DESCONTO;
	}

	public void setDESCONTO(Float DESCONTO) {
		this.DESCONTO = DESCONTO;
	}

	public Float getPRCVEN() {
		return PRCVEN;
	}

	public void setPRCVEN(Float PRCVEN) {
		this.PRCVEN = PRCVEN;
	}

	public Float getDESCVER() {
		return DESCVER;
	}

	public void setDESCVER(Float DESCVER) {
		this.DESCVER = DESCVER;
	}

	public String getCODVERBA() {
		return CODVERBA;
	}

	public void setCODVERBA(String CODVERBA) {
		this.CODVERBA = CODVERBA;
	}

	public String getDESCRICAOVERBA() {
		return DESCRICAOVERBA;
	}

	public void setDESCRICAOVERBA(String DESCRICAOVERBA) {
		this.DESCRICAOVERBA = DESCRICAOVERBA;
	}

	public Float getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(Float TOTAL) {
		this.TOTAL = TOTAL;
	}

	public String getACORDO() {
		return ACORDO;
	}

	public void setACORDO(String ACORDO) {
		this.ACORDO = ACORDO;
	}

	public String getPDFILIAL() {
		return PDFILIAL;
	}

	public void setPDFILIAL(String PDFILIAL) {
		this.PDFILIAL = PDFILIAL;
	}

	public String getPDNUMERO() {
		return PDNUMERO;
	}

	public void setPDNUMERO(String PDNUMERO) {
		this.PDNUMERO = PDNUMERO;
	}

	public String getSIMULADOR() {
		return SIMULADOR;
	}

	public void setSIMULADOR(String SIMULADOR) {
		this.SIMULADOR = SIMULADOR;
	}

	public String getCOTA() {
		return COTA;
	}

	public void setCOTA(String COTA) {
		this.COTA = COTA;
	}

	@Override
	public void loadHelp() {

				/*
		 *
		 *  ALIAS PEDIDODISTRBON
		 *
		 *
		 */

		List<HelpParam> help     = new ArrayList<HelpParam>();

		help.add(new HelpParam("PEDIDO",
				"select peddettvs.filial as filial,peddettvs.pedido as pedido,peddettvs.produto as produto,peddettvs.item as item,"+
						"peddettvs.descricao as descricao,peddettvs.qtd as qtd,peddettvs.prcven as preco,pedcabtvs.cliente as cliente,pedcabtvs.loja as loja,"+
						"pedcabtvs.razao as razao, pedcabtvs.pedidomobile as pedidomobile  from peddettvs "+
						"inner join pedcabtvs on pedcabtvs.filial = peddettvs.filial and pedcabtvs.pedido = peddettvs.pedido "+
						"inner join cliente   on cliente.codigo =  pedcabtvs.cliente and cliente.loja = pedcabtvs.loja  ",
				"where peddettvs.pedido like ''%{0}%'' "           ,
				"order by peddettvs.pedido",
				"pedido",
				new String[] {"FILIAL","PEDIDOMOBILE","ITEM"},
				"((pedcabtvs.cliente = ''{0}'' and pedcabtvs.loja = ''{1}'') or (cliente.rede = ''{3}'') ) and peddettvs.produto = ''{2}'' and ( pedcabtvs.tipo  = ''011''  ) ",   //aliaswhere
				new String[] {}));

		List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("PEDIDODISTRBON", help, filtro, null));



		/*
		 *
		 *  ALIAS PEDIDODISTR
		 *
		 *
		 */

		help     = new ArrayList<HelpParam>();

		help.add(new HelpParam("PEDIDO",
				"select peddettvs.filial as filial,peddettvs.pedido as pedido,peddettvs.produto as produto,peddettvs.item as item,"+
			    "peddettvs.descricao as descricao,peddettvs.qtd as qtd,peddettvs.prcven as preco,pedcabtvs.cliente as cliente,pedcabtvs.loja as loja,"+
 			    "pedcabtvs.razao as razao,pedcabtvs.pedidomobile as pedidomobile from peddettvs "+
			    "inner join pedcabtvs on pedcabtvs.filial = peddettvs.filial and pedcabtvs.pedido = peddettvs.pedido "+
				"inner join cliente   on cliente.codigo =  pedcabtvs.cliente and cliente.loja = pedcabtvs.loja  ",
				"where peddettvs.pedido like ''%{0}%'' "           ,
				"order by peddettvs.pedido",
				"pedido",
				new String[] {"FILIAL","PEDIDOMOBILE","ITEM"},
				"((pedcabtvs.cliente = ''{0}'' and pedcabtvs.loja = ''{1}'') or (cliente.rede = ''{3}'') ) and peddettvs.produto = ''{2}'' and ( pedcabtvs.tipo  = ''010''  ) ",   //aliaswhere
				new String[] {}));

		filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("PEDIDODISTR", help, filtro, null));

		loadTableHelp("PEDIDODISTR");


	};

	@Override
	public String[] getHelpLinhas(Cursor cursor) {

		DecimalFormat format_02 = new DecimalFormat(",##0.00");

		String[] retorno = {"", //linha1
				"", //linha2
				"", //letra
				"", //texto1
				""}; //texto2


		String linha1    = "";
		String linha2    = "";
		String letra     = "";
		String texto1    = "";
		String texto2    = "";

		try
		{

			linha1 = "Pedido: "+ cursor.getString(cursor.getColumnIndex("pedido"))+" "+cursor.getString(cursor.getColumnIndex("razao"));

			linha2 = "Produto:"+cursor.getString(cursor.getColumnIndex("descricao"));

			texto1 = "Preço..:"+format_02.format(cursor.getFloat(cursor.getColumnIndex("preco")));

			letra  =  cursor.getString(cursor.getColumnIndex("razao")).substring(0, 1);

		}catch(Exception e) {

			linha1 = e.getMessage();

		}

		retorno[0] = linha1;

		retorno[1] = linha2;

		retorno[2] = letra;

		retorno[3] = texto1;

		retorno[4] = texto2;

		return retorno;


	};






	@Override
	public void loadColunas() {

		_colunas = new ArrayList<String>();

		_colunas.add("FILIAL");
		_colunas.add("PEDIDO");
		_colunas.add("ITEM");
		_colunas.add("PRODUTO");
		_colunas.add("DESCRICAO");
		_colunas.add("UM");
		_colunas.add("QTD");
		_colunas.add("DESCONTO");
		_colunas.add("PRCVEN");
		_colunas.add("DESCVER");
		_colunas.add("CODVERBA");
		_colunas.add("DESCRICAOVERBA");
		_colunas.add("TOTAL");
		_colunas.add("ACORDO");
		_colunas.add("PDFILIAL");
		_colunas.add("PDNUMERO");
		_colunas.add("SIMULADOR");
		_colunas.add("COTA");

	}

}
