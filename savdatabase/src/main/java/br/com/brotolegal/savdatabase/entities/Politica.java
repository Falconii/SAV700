package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

public class Politica extends ObjRegister {


	protected String CODIGO;
	protected String LOJA;
	protected String CANAL;
	protected String REGIAO;
	protected String PRODUTO;
	protected String GRUPO;
	protected String MARCA;
	protected Float VLRMIN;
	protected Float DESCONTO;
	protected String TIPOPOL;
	protected String VEND;
	protected String SUPER;
	protected String GEREN;
	protected String REDE;
	protected Float  DESCLIM;



	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Politica";
	
	
	public Politica(){
		
		 super(_OBJETO,"POLITICA");
		 
		 loadColunas();
		 
		 InicializaFields();
		 
	}
	
	
	




	public Politica( String cODIGO, String lOJA,
			String cANAL, String rEGIAO, String pRODUTO, String gRUPO,
			String mARCA, Float vLRMIN, Float dESCONTO, String tIPOPOL,
			String vEND, String sUPER, String gEREN, String rEDE, Float dESCLIM) {
		super(_OBJETO,"POLITICA");

		CODIGO = cODIGO;
		LOJA = lOJA;
		CANAL = cANAL;
		REGIAO = rEGIAO;
		PRODUTO = pRODUTO;
		GRUPO = gRUPO;
		MARCA = mARCA;
		VLRMIN = vLRMIN;
		DESCONTO = dESCONTO;
		TIPOPOL = tIPOPOL;
		VEND = vEND;
		SUPER = sUPER;
		GEREN = gEREN;
		REDE = rEDE;
		DESCLIM = dESCLIM;

		loadColunas();

	}


	public String getCODIGO() {
		return CODIGO;
	}







	public void setCODIGO(String cODIGO) {
		CODIGO = cODIGO;
	}







	public String getLOJA() {
		return LOJA;
	}







	public void setLOJA(String lOJA) {
		LOJA = lOJA;
	}







	public String getCANAL() {
		return CANAL;
	}







	public void setCANAL(String cANAL) {
		CANAL = cANAL;
	}







	public String getREGIAO() {
		return REGIAO;
	}







	public void setREGIAO(String rEGIAO) {
		REGIAO = rEGIAO;
	}







	public String getPRODUTO() {
		return PRODUTO;
	}







	public void setPRODUTO(String pRODUTO) {
		PRODUTO = pRODUTO;
	}







	public String getGRUPO() {
		return GRUPO;
	}







	public void setGRUPO(String gRUPO) {
		GRUPO = gRUPO;
	}







	public String getMARCA() {
		return MARCA;
	}







	public void setMARCA(String mARCA) {
		MARCA = mARCA;
	}







	public Float getVLRMIN() {
		return VLRMIN;
	}







	public void setVLRMIN(Float vLRMIN) {
		VLRMIN = vLRMIN;
	}







	public Float getDESCONTO() {
		return DESCONTO;
	}







	public void setDESCONTO(Float dESCONTO) {
		DESCONTO = dESCONTO;
	}







	public String getTIPOPOL() {
		return TIPOPOL;
	}







	public void setTIPOPOL(String tIPOPOL) {
		TIPOPOL = tIPOPOL;
	}







	public String getVEND() {
		return VEND;
	}







	public void setVEND(String vEND) {
		VEND = vEND;
	}







	public String getSUPER() {
		return SUPER;
	}







	public void setSUPER(String sUPER) {
		SUPER = sUPER;
	}







	public String getGEREN() {
		return GEREN;
	}







	public void setGEREN(String gEREN) {
		GEREN = gEREN;
	}







	public String getREDE() {
		return REDE;
	}







	public void setREDE(String rEDE) {
		REDE = rEDE;
	}


	public Float getDESCLIM() {
		return DESCLIM;
	}

	public void setDESCLIM(Float DESCLIM) {
		this.DESCLIM = DESCLIM;
	}

	@Override
	public void loadColunas() {
		
		_colunas = new ArrayList<String>();
		_colunas.add("CODIGO");
		_colunas.add("LOJA");
		_colunas.add("CANAL");
		_colunas.add("REGIAO");
		_colunas.add("PRODUTO");
		_colunas.add("GRUPO");
		_colunas.add("MARCA");
		_colunas.add("VLRMIN");
		_colunas.add("DESCONTO");
		_colunas.add("TIPOPOL");
		_colunas.add("VEND");
		_colunas.add("SUPER");
		_colunas.add("GEREN");
		_colunas.add("REDE");
		_colunas.add("DESCLIM");


	}

	
	
}
