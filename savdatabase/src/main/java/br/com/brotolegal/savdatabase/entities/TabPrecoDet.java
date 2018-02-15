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
	protected Float PERCONTRATO;





	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.TabPrecoDet";



	public TabPrecoDet(){

		super(_OBJETO,"TABPRECODET");

		loadColunas();

		InicializaFields();
	}

	public TabPrecoDet(String CODIGO, String PRODUTO, Float PRCVEN, Float DESCONTOMAIS, Float ACRESCIMOMAIS, String FATOR, Float PRCBASE, Float POLITICABASE, Float CUSTOOPER, Float BDI, Float PRECOANTERIOR, Float PERCONTRATO) {

		super(_OBJETO,"TABPRECODET");

		loadColunas();

		InicializaFields();

		this.CODIGO = CODIGO;
		this.PRODUTO = PRODUTO;
		this.PRCVEN = PRCVEN;
		this.DESCONTOMAIS = DESCONTOMAIS;
		this.ACRESCIMOMAIS = ACRESCIMOMAIS;
		this.FATOR = FATOR;
		this.PRCBASE = PRCBASE;
		this.POLITICABASE = POLITICABASE;
		this.CUSTOOPER = CUSTOOPER;
		this.BDI = BDI;
		this.PRECOANTERIOR = PRECOANTERIOR;
		this.PERCONTRATO = PERCONTRATO;
	}

	public String getCODIGO() {
		return CODIGO;
	}

	public void setCODIGO(String CODIGO) {
		this.CODIGO = CODIGO;
	}

	public String getPRODUTO() {
		return PRODUTO;
	}

	public void setPRODUTO(String PRODUTO) {
		this.PRODUTO = PRODUTO;
	}

	public Float getPRCVEN() {
		return PRCVEN;
	}

	public void setPRCVEN(Float PRCVEN) {
		this.PRCVEN = PRCVEN;
	}

	public Float getDESCONTOMAIS() {
		return DESCONTOMAIS;
	}

	public void setDESCONTOMAIS(Float DESCONTOMAIS) {
		this.DESCONTOMAIS = DESCONTOMAIS;
	}

	public Float getACRESCIMOMAIS() {
		return ACRESCIMOMAIS;
	}

	public void setACRESCIMOMAIS(Float ACRESCIMOMAIS) {
		this.ACRESCIMOMAIS = ACRESCIMOMAIS;
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

	public Float getPRECOANTERIOR() {
		return PRECOANTERIOR;
	}

	public void setPRECOANTERIOR(Float PRECOANTERIOR) {
		this.PRECOANTERIOR = PRECOANTERIOR;
	}

	public Float getPERCONTRATO() {
		return PERCONTRATO;
	}

	public void setPERCONTRATO(Float PERCONTRATO) {
		this.PERCONTRATO = PERCONTRATO;
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
		_colunas.add("PERCONTRATO");


	}



}
