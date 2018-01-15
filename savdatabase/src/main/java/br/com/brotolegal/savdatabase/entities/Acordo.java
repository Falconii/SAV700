package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Acordo extends ObjRegister {

	protected String CODIGO;
	protected String DATA;
	protected String DATAINICIAL;
	protected String DATAFINAL;
	protected String DATAPAGTO;
	protected String TIPOPAGAMENTO;
	protected String CLIENTE;
	protected String LOJA;
	protected String RAZAO;
	protected String CNPJ;
	protected String IE;
	protected String CODVERBA;
	protected String DESVERBA;
	protected String TIPO;
	protected String DESCTIPO;
	protected String STATUS;
	protected String DESCRICAO;
	protected String OBS;
	protected String CODMOBILE;
	protected String CHAVFIN;
	protected String HISTLIB;
	protected Float SALDO;
	protected Float VALOR;
	protected String REDE;
	protected String DESCREDE;

	
	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Acordo";
	
	
	public Acordo(){
		
		 super(_OBJETO,"ACORDO");
		 
		 loadColunas();
		 
		 InicializaFields();
	}

	public Acordo(String CODIGO, String DATA, String DATAINICIAL, String DATAFINAL, String DATAPAGTO, String TIPOPAGAMENTO, String CLIENTE, String LOJA, String RAZAO, String CNPJ, String IE, String CODVERBA, String DESVERBA, String TIPO, String DESCTIPO, String STATUS, String DESCRICAO, String OBS, String CODMOBILE, String CHAVFIN, String HISTLIB, Float SALDO, Float VALOR, String REDE, String DESCREDE) {


		super(_OBJETO,"ACORDO");

		loadColunas();

		InicializaFields();


		this.CODIGO = CODIGO;
		this.DATA = DATA;
		this.DATAINICIAL = DATAINICIAL;
		this.DATAFINAL = DATAFINAL;
		this.DATAPAGTO = DATAPAGTO;
		this.TIPOPAGAMENTO = TIPOPAGAMENTO;
		setCLIENTE(CLIENTE);
		this.LOJA = LOJA;
		this.RAZAO = RAZAO;
		this.CNPJ = CNPJ;
		this.IE = IE;
		this.CODVERBA = CODVERBA;
		this.DESVERBA = DESVERBA;
		this.TIPO = TIPO;
		this.DESCTIPO = DESCTIPO;
		this.STATUS = STATUS;
		this.DESCRICAO = DESCRICAO;
		this.OBS = OBS;
		this.CODMOBILE = CODMOBILE;
		this.CHAVFIN = CHAVFIN;
		this.HISTLIB = HISTLIB;
		this.SALDO = SALDO;
		this.VALOR = VALOR;
		this.setREDE(REDE);
		this.DESCREDE = DESCREDE;
	}

	public String getCODIGO() {
		return CODIGO;
	}

	public void setCODIGO(String CODIGO) {
		this.CODIGO = CODIGO;
	}

	public String getDATA() {
		return DATA;
	}

	public void setDATA(String DATA) {
		this.DATA = DATA;
	}

	public String getDATAINICIAL() {
		return DATAINICIAL;
	}

	public void setDATAINICIAL(String DATAINICIAL) {
		this.DATAINICIAL = DATAINICIAL;
	}

	public String getDATAFINAL() {
		return DATAFINAL;
	}

	public void setDATAFINAL(String DATAFINAL) {
		this.DATAFINAL = DATAFINAL;
	}

	public String getDATAPAGTO() {
		return DATAPAGTO;
	}

	public void setDATAPAGTO(String DATAPAGTO) {
		this.DATAPAGTO = DATAPAGTO;
	}

	public String getTIPOPAGAMENTO() {
		return TIPOPAGAMENTO;
	}

	public void setTIPOPAGAMENTO(String TIPOPAGAMENTO) {
		this.TIPOPAGAMENTO = TIPOPAGAMENTO;
	}

	public String getCLIENTE() {
		return CLIENTE;
	}

	public void setCLIENTE(String CLIENTE) {

		if (CLIENTE == null){

			CLIENTE = "";

		}

		this.CLIENTE = CLIENTE;
	}

	public String getLOJA() {
		return LOJA;
	}

	public void setLOJA(String LOJA) {

		if (LOJA == null){

			LOJA = "";

		}

		this.LOJA = LOJA;
	}

	public String getRAZAO() {
		return RAZAO;
	}

	public void setRAZAO(String RAZAO) {
		this.RAZAO = RAZAO;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String CNPJ) {
		this.CNPJ = CNPJ;
	}

	public String getIE() {
		return IE;
	}

	public void setIE(String IE) {
		this.IE = IE;
	}

	public String getCODVERBA() {
		return CODVERBA;
	}

	public void setCODVERBA(String CODVERBA) {
		this.CODVERBA = CODVERBA;
	}

	public String getDESVERBA() {
		return DESVERBA;
	}

	public void setDESVERBA(String DESVERBA) {
		this.DESVERBA = DESVERBA;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String TIPO) {
		this.TIPO = TIPO;
	}

	public String getDESCTIPO() {
		return DESCTIPO;
	}

	public void setDESCTIPO(String DESCTIPO) {
		this.DESCTIPO = DESCTIPO;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS;
	}

	public String getDESCRICAO() {
		return DESCRICAO;
	}

	public void setDESCRICAO(String DESCRICAO) {
		this.DESCRICAO = DESCRICAO;
	}

	public String getOBS() {
		return OBS;
	}

	public void setOBS(String OBS) {
		this.OBS = OBS;
	}

	public String getCODMOBILE() {
		return CODMOBILE;
	}

	public void setCODMOBILE(String CODMOBILE) {
		this.CODMOBILE = CODMOBILE;
	}

	public String getCHAVFIN() {
		return CHAVFIN;
	}

	public void setCHAVFIN(String CHAVFIN) {
		this.CHAVFIN = CHAVFIN;
	}

	public String getHISTLIB() {
		return HISTLIB;
	}

	public void setHISTLIB(String HISTLIB) {
		this.HISTLIB = HISTLIB;
	}

	public Float getSALDO() {
		return SALDO;
	}

	public void setSALDO(Float SALDO) {
		this.SALDO = SALDO;
	}

	public Float getVALOR() {
		return VALOR;
	}

	public void setVALOR(Float VALOR) {
		this.VALOR = VALOR;
	}

	public String getREDE() {
		return REDE;
	}

	public void setREDE(String REDE) {

		if (REDE ==null){

			REDE = "";

		}

		this.REDE = REDE;
	}

	public String getDESCREDE() {
		return DESCREDE;
	}

	public void setDESCREDE(String DESCREDE) {
		this.DESCREDE = DESCREDE;
	}

	@Override
	public void loadHelp() {

		
		List<HelpParam> help;
		List<HelpFiltro> filtro;
		/*
		 * 
		 *  ACORDO VENDA
		 * 		
		 */
		help       = new ArrayList<HelpParam>();
		help.add(new HelpParam("Nº ACORDO" ,
						"select acordo.codigo,acordo.cliente,acordo.loja,acordo.desverba,cliente.razao as razao,acordo.saldo from acordo "+
						"left join cliente on cliente.codigo = acordo.cliente and cliente.loja = acordo.loja ", 
						"where ( acordo.saldo > 0 and (acordo.status = 'S' or trim(acordo.status) = '') and (acordo.tipopagamento = 'A' or acordo.tipopagamento = 'D' or acordo.tipopagamento = 'B' ) ) and acordo.codigo like ''%{0}%'' ",
						"order by acordo.codigo",
						"CODIGO",
						new String[] {"CODIGO"},
						"",
						new String[]{}));
		filtro    = new ArrayList<HelpFiltro>();
		
		_fileTable.add(new FileTable("ACORDO", help, filtro, null));
		

		/*
		 * 
		 *  ACORDOCLIENTE
		 * 		
		 */

		help     = new ArrayList<HelpParam>();
		help.add(new HelpParam("Nº ACORDO" ,
						"select acordo.codigo,acordo.cliente,acordo.loja,acordo.desverba,cliente.razao as razao,acordo.saldo,acordo.tipopagamento,acordo.rede as rede, rede.descricao as descrede from acordo "+
						"left join cliente on cliente.codigo = acordo.cliente and cliente.loja = acordo.loja "+
				        "left join rede    on rede.codigo    = acordo.rede ",
						"where ( acordo.saldo > 0 and (acordo.status = 'S' or trim(acordo.status) = '') and (acordo.tipopagamento = 'A' or acordo.tipopagamento = 'D' or acordo.tipopagamento = 'B' ) ) and  acordo.codigo like ''%{0}%'' " ,
						"order by acordo.codigo",
						"CODIGO",
						new String[] {"CODIGO"},
						"( (acordo.cliente = ''{0}'' and acordo.loja = ''{1}'') or (acordo.rede = ''{3}'' ) ) and acordo.tipopagamento <> ''{2}'' ",
						new String[]{}));
         filtro    = new ArrayList<HelpFiltro>();
		
		_fileTable.add(new FileTable("ACORDOCLIENTE", help, filtro, null));
		
		
		loadTableHelp("ACORDOCLIENTE");

		/*
		 *
		 *  ACORDOCLIENTEBONIF
		 *
		 */

		help     = new ArrayList<HelpParam>();
		help.add(new HelpParam("Nº ACORDO" ,
				"select acordo.codigo,acordo.cliente,acordo.loja,acordo.desverba,cliente.razao as razao,acordo.saldo,acordo.tipopagamento,acordo.rede as rede,rede.descricao as descrede from acordo "+
						"left join cliente on cliente.codigo = acordo.cliente and cliente.loja = acordo.loja "+
						"left join rede    on rede.codigo    = acordo.rede ",
				"where ( acordo.saldo > 0 and (acordo.status = 'S' or trim(acordo.status) = '') and (acordo.tipopagamento = 'A' or acordo.tipopagamento = 'D' or acordo.tipopagamento = 'B' ) ) and acordo.codigo like ''%{0}%'' " ,
				"order by acordo.codigo",
				"CODIGO",
				new String[] {"CODIGO"},
				" ( (acordo.cliente = ''{0}'' and acordo.loja = ''{1}'') or (acordo.rede = ''{3}'' ) ) and acordo.tipopagamento = ''{2}'' ",
				new String[]{}));
		filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("ACORDOCLIENTEBONIF", help, filtro, null));


		loadTableHelp("ACORDOCLIENTEBONIF");


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

			if ( cursor.getString(cursor.getColumnIndex("cliente")).trim().isEmpty() ) {

				linha1 = "Código: " + cursor.getString(cursor.getColumnIndex("codigo")) + " Rede: " + cursor.getString(cursor.getColumnIndex("rede")) + "-" + cursor.getString(cursor.getColumnIndex("descrede"));

				letra  = cursor.getString(cursor.getColumnIndex("rede")).substring(0, 1);

			} else {

				linha1 = "Código: "+cursor.getString(cursor.getColumnIndex("codigo"))+" Cliente: "+cursor.getString(cursor.getColumnIndex("cliente"))+"-"+cursor.getString(cursor.getColumnIndex("loja"))+" "+cursor.getString(cursor.getColumnIndex("razao"));

				letra  = cursor.getString(cursor.getColumnIndex("cliente")).substring(0, 1);

			}

	
			linha2 = cursor.getString(cursor.getColumnIndex("desverba"))+" Saldo: "+format_02.format(cursor.getFloat(cursor.getColumnIndex("saldo")))+" Tipo Pagto: "+cursor.getString(cursor.getColumnIndex("tipopagamento"));
			



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


	public String get_STATUS(){

		String retorno = "";

		if (this.getSTATUS() == null || this.getSTATUS().length() == 0){

			return "";

		}

		switch (this.getSTATUS().charAt(0)){

			case 'B': retorno = "BLOQUEADO"; break;
			case 'N': retorno = "NÃO LIBERADO"; break;
			case 'S': retorno = "LIBERADO"; break;
		}

		return retorno;

	}

	public String get_FormaPagto(){

		String retorno = "";

		if (this.getTIPOPAGAMENTO() == null || this.getTIPOPAGAMENTO().length() == 0){

			return "";

		}

		switch (this.getTIPOPAGAMENTO().charAt(0)){

			case 'S': retorno = "ABATIMENTOS"; break;
			case 'A': retorno = "AMBOS (DESONCONTO/BONIFICAÇÃO)"; break;
			case 'Q': retorno = "BOLETO BANCÁRIO"; break;
			case 'B': retorno = "BONIFICAÇÃO"; break;
			case 'D': retorno = "DESCONTO NO PRODUTO"; break;
			case 'T': retorno = "DEPÓSITO BANCÁRIO"; break;

		}

		return retorno;

	}
	
	@Override
	public void loadColunas() {
		
		_colunas = new ArrayList<String>();

		_colunas.add("CODIGO");
		_colunas.add("DATA");
		_colunas.add("DATAINICIAL");
		_colunas.add("DATAFINAL");
		_colunas.add("DATAPAGTO");
		_colunas.add("TIPOPAGAMENTO");
		_colunas.add("CLIENTE");
		_colunas.add("LOJA");
		_colunas.add("RAZAO");
		_colunas.add("CNPJ");
		_colunas.add("IE");
		_colunas.add("CODVERBA");
		_colunas.add("DESVERBA");
		_colunas.add("TIPO");
		_colunas.add("DESCTIPO");
		_colunas.add("STATUS");
		_colunas.add("DESCRICAO");
		_colunas.add("OBS");
		_colunas.add("CODMOBILE");
		_colunas.add("CHAVFIN");
		_colunas.add("HISTLIB");
		_colunas.add("SALDO");
		_colunas.add("VALOR");
		_colunas.add("REDE");
		_colunas.add("DESCREDE");


	}

}
