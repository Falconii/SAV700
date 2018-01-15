package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;


public class TabPrecoDet extends ObjRegister {

	protected String CODIGO;
	protected String PRODUTO;
	protected Float PRCVEN;
	protected Float DESCONTOMAIS;
	protected Float ACRESCIMOMAIS;
	protected String FATOR;
	protected Float PRCBASE;
	protected Float POLITICABASE;
	protected Float CUSTOOPER;
	protected Float BDI;
	protected Float PRECOANTERIOR;




	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.TabPrecoDet";
	
	
	public TabPrecoDet(){
		
		 super(_OBJETO,"TABPRECODET");
		 
		 loadColunas();

		 InicializaFields();
	}


	public TabPrecoDet(String CODIGO, String PRODUTO, Float PRCVEN, Float DESCONTOMAIS, Float ACRESCIMOMAIS, String FATOR, Float PRCBASE, Float POLITICABASE,Float CUSTOOPER,Float BDI, Float PRECOANTERIOR ) {

		super(_OBJETO,"TABPRECODET");

		loadColunas();

		InicializaFields();

		this.CODIGO        = CODIGO;
		this.PRODUTO       = PRODUTO;
		this.PRCVEN        = PRCVEN;
		this.DESCONTOMAIS  = DESCONTOMAIS;
		this.ACRESCIMOMAIS = ACRESCIMOMAIS;
		this.FATOR         = FATOR;
		this.PRCBASE       = PRCBASE;
		this.POLITICABASE  = POLITICABASE;
		this.CUSTOOPER     = CUSTOOPER;
		this.BDI           = BDI;
		this.PRECOANTERIOR = PRECOANTERIOR;

	}

	public String getCODIGO() {
		return CODIGO;
	}




	public void setCODIGO(String cODIGO) {
		CODIGO = cODIGO;
	}




	public String getPRODUTO() {
		return PRODUTO;
	}




	public void setPRODUTO(String pRODUTO) {
		PRODUTO = pRODUTO;
	}




	public Float getPRCVEN() {
		return PRCVEN;
	}




	public void setPRCVEN(Float pRCVEN) {
		PRCVEN = pRCVEN;
	}




	public Float getDESCONTOMAIS() {
		return DESCONTOMAIS;
	}




	public void setDESCONTOMAIS(Float dESCONTOMAIS) {
		DESCONTOMAIS = dESCONTOMAIS;
	}




	public Float getACRESCIMOMAIS() {
		return ACRESCIMOMAIS;
	}




	public void setACRESCIMOMAIS(Float aCRESCIMOMAIS) {
		ACRESCIMOMAIS = aCRESCIMOMAIS;
	}

	public String getFATOR() {
		return FATOR;
	}

	public void setFATOR(String FATOR) {
		this.FATOR = FATOR;
	}

	public Float getPRCBASE() {
		return PRCBASE;
	}

	public void setPRCBASE(Float PRCBASE) {
		this.PRCBASE = PRCBASE;
	}

	public Float getPOLITICABASE() {
		return POLITICABASE;
	}

	public void setPOLITICABASE(Float POLITICABASE) {
		this.POLITICABASE = POLITICABASE;
	}

	public Float getCUSTOOPER() {
		return CUSTOOPER;
	}

	public void setCUSTOOPER(Float CUSTOOPER) {
		this.CUSTOOPER = CUSTOOPER;
	}

	public Float getBDI() {
		return BDI;
	}

	public void setBDI(Float BDI) {
		this.BDI = BDI;
	}

	@Override
	public void loadColunas() {
		
		_colunas = new ArrayList<String>();
		_colunas.add("CODIGO");
		_colunas.add("PRODUTO");
		_colunas.add("PRCVEN");
		_colunas.add("DESCONTOMAIS");
		_colunas.add("ACRESCIMOMAIS");
		_colunas.add("FATOR");
		_colunas.add("PRCBASE");
		_colunas.add("POLITICABASE");
		_colunas.add("CUSTOOPER");
		_colunas.add("BDI");
		_colunas.add("PRECOANTERIOR");

	}

	

}
