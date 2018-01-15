package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Contrato extends ObjRegister {

	protected String CODIGO;
	protected String TIPO;
	protected String CATEGORIA;
	protected String MARCA;
	protected String PRODUTO;
	protected String CLIENTE;
	protected String LOJA;
	protected String REDE;
	protected Float  PECDESCBOL;
	protected Float  CBOL2;
	protected Float  PERCTOF;
	protected Float  PERCADIC;
	protected String DESCDESCOF;
	protected String DESCDESCCO;
	protected String SITUCAO;
	protected String DATA;
	protected Float  TAXAFIN;
	protected Float  PAGTOBOL;
	protected Float  PAGTOADI;
	protected String OBS;
	protected Float  PERCCD;

	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Contrato";
	protected String _DESCRICAO;
	protected String _REDE;


	public Contrato(){

		super(_OBJETO,"CONTRATO");

		loadColunas();

		InicializaFields();

		_DESCRICAO = "";

		_REDE = "" ;


	}

	public Contrato(String CODIGO, String TIPO, String CATEGORIA, String MARCA, String PRODUTO, String CLIENTE, String LOJA, String REDE, Float PECDESCBOL, Float CBOL2, Float PERCTOF, Float PERCADIC, String DESCDESCOF, String DESCDESCCO, String SITUCAO, String DATA, Float TAXAFIN, Float PAGTOBOL, Float PAGTOADI, String OBS, Float PERCCD) {

		super(_OBJETO,"CONTRATO");

		loadColunas();

		InicializaFields();

		_DESCRICAO = "";

		_REDE = "" ;


		this.CODIGO = CODIGO;
		this.TIPO = TIPO;
		this.CATEGORIA = CATEGORIA;
		this.MARCA = MARCA;
		this.PRODUTO = PRODUTO;
		this.CLIENTE = CLIENTE;
		this.LOJA = LOJA;
		this.REDE = REDE;
		this.PECDESCBOL = PECDESCBOL;
		this.CBOL2 = CBOL2;
		this.PERCTOF = PERCTOF;
		this.PERCADIC = PERCADIC;
		this.DESCDESCOF = DESCDESCOF;
		this.DESCDESCCO = DESCDESCCO;
		this.SITUCAO = SITUCAO;
		this.DATA = DATA;
		this.TAXAFIN = TAXAFIN;
		this.PAGTOBOL = PAGTOBOL;
		this.PAGTOADI = PAGTOADI;
		this.OBS = OBS;
		this.PERCCD = PERCCD;
	}

	public String getCODIGO() {
		return CODIGO;
	}

	public void setCODIGO(String CODIGO) {
		this.CODIGO = CODIGO;
	}

	public String getCLIENTE() {
		return CLIENTE;
	}

	public void setCLIENTE(String CLIENTE) {
		this.CLIENTE = CLIENTE;
	}

	public String getLOJA() {
		return LOJA;
	}

	public void setLOJA(String LOJA) {
		this.LOJA = LOJA;
	}

	public String getREDE() {
		return REDE;
	}

	public void setREDE(String REDE) {
		this.REDE = REDE;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String TIPO) {
		this.TIPO = TIPO;
	}

	public String getCATEGORIA() {
		return CATEGORIA;
	}

	public void setCATEGORIA(String CATEGORIA) {
		this.CATEGORIA = CATEGORIA;
	}

	public String getMARCA() {
		return MARCA;
	}

	public void setMARCA(String MARCA) {
		this.MARCA = MARCA;
	}

	public String getPRODUTO() {
		return PRODUTO;
	}

	public void setPRODUTO(String PRODUTO) {
		this.PRODUTO = PRODUTO;
	}

	public Float getPECDESCBOL() {
		return PECDESCBOL;
	}

	public void setPECDESCBOL(Float PECDESCBOL) {
		this.PECDESCBOL = PECDESCBOL;
	}

	public Float getCBOL2() {
		return CBOL2;
	}

	public void setCBOL2(Float CBOL2) {
		this.CBOL2 = CBOL2;
	}

	public Float getPERCTOF() {
		return PERCTOF;
	}

	public void setPERCTOF(Float PERCTOF) {
		this.PERCTOF = PERCTOF;
	}

	public Float getPERCADIC() {
		return PERCADIC;
	}

	public void setPERCADIC(Float PERCADIC) {
		this.PERCADIC = PERCADIC;
	}

	public String getDESCDESCOF() {
		return DESCDESCOF;
	}

	public void setDESCDESCOF(String DESCDESCOF) {
		this.DESCDESCOF = DESCDESCOF;
	}

	public String getDESCDESCCO() {
		return DESCDESCCO;
	}

	public void setDESCDESCCO(String DESCDESCCO) {
		this.DESCDESCCO = DESCDESCCO;
	}

	public String getSITUCAO() {
		return SITUCAO;
	}

	public void setSITUCAO(String SITUCAO) {
		this.SITUCAO = SITUCAO;
	}

	public String getDATA() {
		return DATA;
	}

	public void setDATA(String DATA) {
		this.DATA = DATA;
	}

	public Float getTAXAFIN() {
		return TAXAFIN;
	}

	public void setTAXAFIN(Float TAXAFIN) {
		this.TAXAFIN = TAXAFIN;
	}

	public Float getPAGTOBOL() {
		return PAGTOBOL;
	}

	public void setPAGTOBOL(Float PAGTOBOL) {
		this.PAGTOBOL = PAGTOBOL;
	}

	public Float getPAGTOADI() {
		return PAGTOADI;
	}

	public void setPAGTOADI(Float PAGTOADI) {
		this.PAGTOADI = PAGTOADI;
	}

	public String getOBS() {
		return OBS;
	}

	public void setOBS(String OBS) {
		this.OBS = OBS;
	}

	public String getChave(){

		String retorno = "";

		if (TIPO == null || TIPO.isEmpty()){

			return retorno;

		}
		switch (TIPO.charAt(0)) {

			case 'P':

				retorno = PRODUTO;

				break;

			case 'M':

				retorno = MARCA;

				break;

			case 'C':

				retorno = CATEGORIA;

				break;


			default:

				retorno = "";

				break;
		}

		return retorno;

	}


	public String get_STATUS(){

		String retorno = "";

		if (SITUCAO == null || SITUCAO.isEmpty()){

			return retorno;

		}


		switch (this.SITUCAO.charAt(0)){

			case 'A': retorno = "ATIVO"; break;
			case 'I': retorno = "INATIVO"; break;
			default: retorno = this.SITUCAO; break;

		}

		return retorno;

	}

	public String get_TIPO(){

		String retorno = "";

		if (TIPO == null || TIPO.isEmpty()){

			return retorno;

		}


		switch (this.TIPO.charAt(0)){

			case 'T': retorno = "TODOS"     ;     break;
			case 'C': retorno = "CATEGORIA" ;     break;
			case 'G': retorno = "MARCA"     ;     break;
			case 'P': retorno = "PRODUTO"   ;     break;
			default: retorno = this.TIPO    ;     break;

		}

		return retorno;

	}


	public String get_DESCRICAO() {
		return _DESCRICAO;
	}

	public void set_DESCRICAO(String _DESCRICAO) {
		this._DESCRICAO = _DESCRICAO;
	}

	public String get_REDE() {
		return _REDE;
	}

	public void set_REDE(String _REDE) {
		this._REDE = _REDE;
	}

	public Float getPERCCD() {
		return PERCCD;
	}

	public void setPERCCD(Float PERCCD) {
		this.PERCCD = PERCCD;
	}

	@Override
	public void loadColunas() {

		_colunas = new ArrayList<String>();
		_colunas.add("CODIGO");
		_colunas.add("TIPO");
		_colunas.add("CATEGORIA");
		_colunas.add("MARCA");
		_colunas.add("PRODUTO");
		_colunas.add("CLIENTE");
		_colunas.add("LOJA");
		_colunas.add("REDE");
		_colunas.add("PECDESCBOL");
		_colunas.add("CBOL2");
		_colunas.add("PERCTOF");
		_colunas.add("PERCADIC");
		_colunas.add("DESCDESCOF");
		_colunas.add("DESCDESCCO");
		_colunas.add("SITUCAO");
		_colunas.add("DATA");
		_colunas.add("TAXAFIN");
		_colunas.add("PAGTOBOL");
		_colunas.add("PAGTOADI");
		_colunas.add("OBS");
		_colunas.add("PERCCD");

	}

}
