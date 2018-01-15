package br.com.brotolegal.savdatabase.regrasdenegocio;

public class ItemPedido {
	
	private String ITEM;
	private String PRODUTO;
	private String DESCRICAO;
	private Float QTD;
	private String UM;
	private Float PRCVEN;
	private Float DESCON;
	private Float DESCVERBA;
	private String CODVERBA;
	private String DESCRIVERBA;
	private Float TOTAL;
	private Float PESO;
	private Float PRECOFORMACAO;
	private Float DESCCONTRATO;
	private Float PRECOTABELA;
	private int    ID;
	private Float ACRESCIMOMAIS;
	private Float DESCONTOMAIS;
	private Float DESCONTOPOL;
	private String USATAXAFIN;
    private Float TAXAFIN;
	private String Status;
 	
	
	public ItemPedido() 
	   {
		Status = "0";}


	public ItemPedido(String iTEM, String pRODUTO, String dESCRICAO, Float qTD,
			String uM, Float pRCVEN, Float dESCON, Float dESCVERBA,
			String cODVERBA, String dESCRIVERBA, Float tOTAL, Float pESO,
			Float pRECOFORMACAO, Float dESCCONTRATO, Float pRECOTABELA, int iD,
			Float aCRESCIMOMAIS, Float dESCONTOMAIS, Float dESCONTOPOL,
			String uSATAXAFIN, Float tAXAFIN) {
		
		ITEM = iTEM;
		PRODUTO = pRODUTO;
		DESCRICAO = dESCRICAO;
		QTD = qTD;
		UM = uM;
		PRCVEN = pRCVEN;
		DESCON = dESCON;
		DESCVERBA = dESCVERBA;
		CODVERBA = cODVERBA;
		DESCRIVERBA = dESCRIVERBA;
		TOTAL = tOTAL;
		PESO = pESO;
		PRECOFORMACAO = pRECOFORMACAO;
		DESCCONTRATO = dESCCONTRATO;
		PRECOTABELA = pRECOTABELA;
		ID = iD;
		ACRESCIMOMAIS = aCRESCIMOMAIS;
		DESCONTOMAIS = dESCONTOMAIS;
		DESCONTOPOL = dESCONTOPOL;
		USATAXAFIN = uSATAXAFIN;
		TAXAFIN = tAXAFIN;
		Status = "0";
	}


	public String getITEM() {
		return ITEM;
	}


	public void setITEM(String iTEM) {
		ITEM = iTEM;
	}


	public String getPRODUTO() {
		return PRODUTO;
	}


	public void setPRODUTO(String pRODUTO) {
		PRODUTO = pRODUTO;
	}


	public String getDESCRICAO() {
		return DESCRICAO;
	}


	public void setDESCRICAO(String dESCRICAO) {
		DESCRICAO = dESCRICAO;
	}


	public Float getQTD() {
		return QTD;
	}


	public void setQTD(Float qTD) {
		QTD = qTD;
	}


	public String getUM() {
		return UM;
	}


	public void setUM(String uM) {
		UM = uM;
	}


	public Float getPRCVEN() {
		return PRCVEN;
	}


	public void setPRCVEN(Float pRCVEN) {
		PRCVEN = pRCVEN;
	}


	public Float getDESCON() {
		return DESCON;
	}


	public void setDESCON(Float dESCON) {
		DESCON = dESCON;
	}


	public Float getDESCVERBA() {
		return DESCVERBA;
	}


	public void setDESCVERBA(Float dESCVERBA) {
		DESCVERBA = dESCVERBA;
	}


	public String getCODVERBA() {
		return CODVERBA;
	}


	public void setCODVERBA(String cODVERBA) {
		CODVERBA = cODVERBA;
	}


	public String getDESCRIVERBA() {
		return DESCRIVERBA;
	}


	public void setDESCRIVERBA(String dESCRIVERBA) {
		DESCRIVERBA = dESCRIVERBA;
	}


	public Float getTOTAL() {
		return TOTAL;
	}


	public void setTOTAL(Float tOTAL) {
		TOTAL = tOTAL;
	}


	public Float getPESO() {
		return PESO;
	}


	public void setPESO(Float pESO) {
		PESO = pESO;
	}


	public Float getPRECOFORMACAO() {
		return PRECOFORMACAO;
	}


	public void setPRECOFORMACAO(Float pRECOFORMACAO) {
		PRECOFORMACAO = pRECOFORMACAO;
	}


	public Float getDESCCONTRATO() {
		return DESCCONTRATO;
	}


	public void setDESCCONTRATO(Float dESCCONTRATO) {
		DESCCONTRATO = dESCCONTRATO;
	}


	public Float getPRECOTABELA() {
		return PRECOTABELA;
	}


	public void setPRECOTABELA(Float pRECOTABELA) {
		PRECOTABELA = pRECOTABELA;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public Float getACRESCIMOMAIS() {
		return ACRESCIMOMAIS;
	}


	public void setACRESCIMOMAIS(Float aCRESCIMOMAIS) {
		ACRESCIMOMAIS = aCRESCIMOMAIS;
	}


	public Float getDESCONTOMAIS() {
		return DESCONTOMAIS;
	}


	public void setDESCONTOMAIS(Float dESCONTOMAIS) {
		DESCONTOMAIS = dESCONTOMAIS;
	}


	public Float getDESCONTOPOL() {
		return DESCONTOPOL;
	}


	public void setDESCONTOPOL(Float dESCONTOPOL) {
		DESCONTOPOL = dESCONTOPOL;
	}


	public String getUSATAXAFIN() {
		return USATAXAFIN;
	}


	public void setUSATAXAFIN(String uSATAXAFIN) {
		USATAXAFIN = uSATAXAFIN;
	}


	public Float getTAXAFIN() {
		return TAXAFIN;
	}


	public void setTAXAFIN(Float tAXAFIN) {
		TAXAFIN = tAXAFIN;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}



	
}
