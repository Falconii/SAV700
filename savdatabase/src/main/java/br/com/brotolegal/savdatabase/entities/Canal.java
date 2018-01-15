package br.com.brotolegal.savdatabase.entities;


import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Canal extends ObjRegister {
	
	protected String CODIGO;
	protected String DESCRICAO;
	protected String TABPRECO;
	protected String TAXAFIN;
	protected String REGIAO;
	
	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Canal";
	
	
	public Canal(){
		
		 super(_OBJETO,"CANAL");
		 
		 loadColunas();

		 InicializaFields();
	}
	


	

	public Canal(String cODIGO, String dESCRICAO,String tABPRECO, String tAXAFIN, String rEGIAO) {
		
		super(_OBJETO,"CANAL");

		loadColunas();

		InicializaFields();

		CODIGO = cODIGO;
		DESCRICAO = dESCRICAO;
		TABPRECO = tABPRECO;
		TAXAFIN = tAXAFIN;
		REGIAO = rEGIAO;

		
	}





	public String getCODIGO() {
		return CODIGO;
	}





	public void setCODIGO(String cODIGO) {
		CODIGO = cODIGO;
	}





	public String getDESCRICAO() {
		return DESCRICAO;
	}





	public void setDESCRICAO(String dESCRICAO) {
		DESCRICAO = dESCRICAO;
	}





	public String getTABPRECO() {
		return TABPRECO;
	}





	public void setTABPRECO(String tABPRECO) {
		TABPRECO = tABPRECO;
	}





	public String getTAXAFIN() {
		return TAXAFIN;
	}





	public void setTAXAFIN(String tAXAFIN) {
		TAXAFIN = tAXAFIN;
	}





	public String getREGIAO() {
		return REGIAO;
	}





	public void setREGIAO(String rEGIAO) {
		REGIAO = rEGIAO;
	}





	@Override
	public void loadColunas() {
		
		_colunas = new ArrayList<String>();
		_colunas.add("CODIGO");
		_colunas.add("DESCRICAO");
		_colunas.add("TABPRECO");
		_colunas.add("TAXAFIN");
		_colunas.add("REGIAO");

	}

}
