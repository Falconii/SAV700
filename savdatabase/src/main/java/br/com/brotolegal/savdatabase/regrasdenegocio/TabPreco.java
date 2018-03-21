package br.com.brotolegal.savdatabase.regrasdenegocio;

import java.math.BigDecimal;

public class TabPreco {

/*
 *   Cabecalho
 * 	
 */
	private String CODIGO;
	private String DESCRICAO;
	private String FLAGFAIXA;
	private String FLAGHABDESC;
	private String FLAGHABMOTIVO;
	private String FLAGCONTRATO;
	private String FLAGCC;
	private String FLAGTAXAFINANC;
	private String FLAGDESCCANAL;
	private String FLAGDESCLOGIST;
	private Float  FAIXADE;
	private Float  FAIXAATE;
	private String  TIPOCONTRATO;
	private String  TIPOFRETE;
	private String  TIPOPRAZO;
	
/*
 * 
 *  Detalhe
 * 
 */
	private String PRODUTO;
	private Float  PRCVEN;
	private Float  DESCONTOMAIS;
	private Float  ACRESCIMOMAIS;
	private String DESCRICAOPRODUTO;
	private String UNID;
	private String GRUPO;
	private String MARCA;
	private String ORIGEM;
	private Float  CONVERSAO;
	private String DESCRICAOGRUPO;
	private String DESCRICAOMARCA;
	private String NROITEM;
	private String FATOR;
	private Float  PRCBASE;
	private Float  POLITICABASE;
	private Float  CUSTOOPER;
	private Float  BDI;
	private Float  PERCONTRATO;
	protected Float PERPRAZO;




	public TabPreco(String CODIGO, String DESCRICAO, String FLAGFAIXA, String FLAGHABDESC, String FLAGHABMOTIVO,
					String FLAGCONTRATO, String FLAGCC, String FLAGTAXAFINANC, String FLAGDESCCANAL, String FLAGDESCLOGIST,
					Float FAIXADE, Float FAIXAATE,String  TIPOCONTRATO,String  TIPOFRETE, String TIPOPRAZO,String PRODUTO, Float PRCVEN, Float DESCONTOMAIS,
					Float ACRESCIMOMAIS, String DESCRICAOPRODUTO, String UNID, String GRUPO, String MARCA, String ORIGEM, Float CONVERSAO,
					String DESCRICAOGRUPO, String DESCRICAOMARCA, String NROITEM, String FATOR, Float PRCBASE, Float POLITICABASE,
					Float CUSTOOPER, Float BDI,Float  PERCONTRATO, Float PERPRAZO
	) {
		this.CODIGO 			= CODIGO;
		this.DESCRICAO 			= DESCRICAO;
		this.FLAGFAIXA 			= FLAGFAIXA;
		this.FLAGHABDESC 		= FLAGHABDESC;
		this.FLAGHABMOTIVO    	= FLAGHABMOTIVO;
		this.FLAGCONTRATO     	= FLAGCONTRATO;
		this.FLAGCC           	= FLAGCC;
		this.FLAGTAXAFINANC   	= FLAGTAXAFINANC;
		this.FLAGDESCCANAL    	= FLAGDESCCANAL;
		this.FLAGDESCLOGIST   	= FLAGDESCLOGIST;
		this.FAIXADE          	= FAIXADE;
		this.FAIXAATE         	= FAIXAATE;
		this.TIPOCONTRATO       = TIPOCONTRATO;
		this.TIPOFRETE          = TIPOFRETE;
		this.TIPOPRAZO          = TIPOPRAZO;
		this.PRODUTO          	= PRODUTO;
		this.PRCVEN           	= PRCVEN;
		this.DESCONTOMAIS     	= DESCONTOMAIS;
		this.ACRESCIMOMAIS    	= ACRESCIMOMAIS;
		this.DESCRICAOPRODUTO 	= DESCRICAOPRODUTO;
		this.UNID 				= UNID;
		this.GRUPO 				= GRUPO;
		this.MARCA 				= MARCA;
		this.ORIGEM 			= ORIGEM;
		this.CONVERSAO 			= CONVERSAO;
		this.DESCRICAOGRUPO 	= DESCRICAOGRUPO;
		this.DESCRICAOMARCA 	= DESCRICAOMARCA;
		this.NROITEM 			= NROITEM;
		this.FATOR 				= FATOR;
		this.PRCBASE 			= PRCBASE;
		this.POLITICABASE 		= POLITICABASE;
		this.CUSTOOPER 			= CUSTOOPER;
		this.BDI 				= BDI;
		this.PERCONTRATO        = PERCONTRATO;
		this.PERPRAZO           = PERPRAZO;
	}


	public String getCODIGO() {
		return CODIGO;
	}

	public void setCODIGO(String CODIGO) {
		this.CODIGO = CODIGO;
	}

	public String getDESCRICAO() {
		return DESCRICAO;
	}

	public void setDESCRICAO(String DESCRICAO) {
		this.DESCRICAO = DESCRICAO;
	}

	public String getFLAGFAIXA() {
		return FLAGFAIXA;
	}

	public void setFLAGFAIXA(String FLAGFAIXA) {
		this.FLAGFAIXA = FLAGFAIXA;
	}

	public String getFLAGHABDESC() {
		return FLAGHABDESC;
	}

	public void setFLAGHABDESC(String FLAGHABDESC) {
		this.FLAGHABDESC = FLAGHABDESC;
	}

	public String getFLAGHABMOTIVO() {
		return FLAGHABMOTIVO;
	}

	public void setFLAGHABMOTIVO(String FLAGHABMOTIVO) {
		this.FLAGHABMOTIVO = FLAGHABMOTIVO;
	}

	public String getFLAGCONTRATO() {
		return FLAGCONTRATO;
	}

	public void setFLAGCONTRATO(String FLAGCONTRATO) {
		this.FLAGCONTRATO = FLAGCONTRATO;
	}

	public String getFLAGCC() {
		return FLAGCC;
	}

	public void setFLAGCC(String FLAGCC) {
		this.FLAGCC = FLAGCC;
	}

	public String getFLAGTAXAFINANC() {
		return FLAGTAXAFINANC;
	}

	public void setFLAGTAXAFINANC(String FLAGTAXAFINANC) {
		this.FLAGTAXAFINANC = FLAGTAXAFINANC;
	}

	public String getFLAGDESCCANAL() {
		return FLAGDESCCANAL;
	}

	public void setFLAGDESCCANAL(String FLAGDESCCANAL) {
		this.FLAGDESCCANAL = FLAGDESCCANAL;
	}

	public String getFLAGDESCLOGIST() {
		return FLAGDESCLOGIST;
	}

	public void setFLAGDESCLOGIST(String FLAGDESCLOGIST) {
		this.FLAGDESCLOGIST = FLAGDESCLOGIST;
	}

	public Float getFAIXADE() {
		return FAIXADE;
	}

	public void setFAIXADE(Float FAIXADE) {
		this.FAIXADE = FAIXADE;
	}

	public Float getFAIXAATE() {
		return FAIXAATE;
	}

	public void setFAIXAATE(Float FAIXAATE) {
		this.FAIXAATE = FAIXAATE;
	}

	public String getTIPOCONTRATO() {
		return TIPOCONTRATO;
	}

	public void setTIPOCONTRATO(String TIPOCONTRATO) {
		this.TIPOCONTRATO = TIPOCONTRATO;
	}

	public String getTIPOPRAZO() {
		return TIPOPRAZO;
	}

	public void setTIPOPRAZO(String TIPOPRAZO) {
		this.TIPOPRAZO = TIPOPRAZO;
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

	public String getDESCRICAOPRODUTO() {
		return DESCRICAOPRODUTO;
	}

	public void setDESCRICAOPRODUTO(String DESCRICAOPRODUTO) {
		this.DESCRICAOPRODUTO = DESCRICAOPRODUTO;
	}

	public String getUNID() {
		return UNID;
	}

	public void setUNID(String UNID) {
		this.UNID = UNID;
	}

	public String getGRUPO() {
		return GRUPO;
	}

	public void setGRUPO(String GRUPO) {
		this.GRUPO = GRUPO;
	}

	public String getMARCA() {
		return MARCA;
	}

	public void setMARCA(String MARCA) {
		this.MARCA = MARCA;
	}

	public String getORIGEM() {
		return ORIGEM;
	}

	public void setORIGEM(String ORIGEM) {
		this.ORIGEM = ORIGEM;
	}

	public Float getCONVERSAO() {
		return CONVERSAO;
	}

	public void setCONVERSAO(Float CONVERSAO) {
		this.CONVERSAO = CONVERSAO;
	}

	public String getDESCRICAOGRUPO() {
		return DESCRICAOGRUPO;
	}

	public void setDESCRICAOGRUPO(String DESCRICAOGRUPO) {
		this.DESCRICAOGRUPO = DESCRICAOGRUPO;
	}

	public String getDESCRICAOMARCA() {
		return DESCRICAOMARCA;
	}

	public void setDESCRICAOMARCA(String DESCRICAOMARCA) {
		this.DESCRICAOMARCA = DESCRICAOMARCA;
	}

	public String getNROITEM() {
		return NROITEM;
	}

	public void setNROITEM(String NROITEM) {
		this.NROITEM = NROITEM;
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

	public Double getFATORNUM(){

		BigDecimal FATOR;

		Double fator = (Double.parseDouble(this.getFATOR().trim()+"000000000"));

		FATOR = new BigDecimal(fator);

		FATOR = FATOR.setScale(9, BigDecimal.ROUND_HALF_UP);

		return FATOR.doubleValue();

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

	public Float getPERCONTRATO() {
		return PERCONTRATO;
	}

	public void setPERCONTRATO(Float PERCONTRATO) {
		this.PERCONTRATO = PERCONTRATO;
	}

	public String getTIPOFRETE() {return TIPOFRETE;	}

	public void setTIPOFRETE(String TIPOFRETE) {this.TIPOFRETE = TIPOFRETE;}

	public Float getPERPRAZO() {
		return PERPRAZO;
	}

	public void setPERPRAZO(Float PERPRAZO) {
		this.PERPRAZO = PERPRAZO;
	}
}



