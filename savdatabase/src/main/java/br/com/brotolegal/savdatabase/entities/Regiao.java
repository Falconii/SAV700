package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Regiao extends ObjRegister {
	
	protected String CODIGO;
	protected String DESCRICAO;
	
	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Regiao";
	
	
	public Regiao(){
		
		 super(_OBJETO,"REGIAO");
		 
		 loadColunas();
	}
	


	public Regiao(String cODIGO, String dESCRICAO) {

		super(_OBJETO,"REGIAO");
		
		CODIGO = cODIGO;
		DESCRICAO = dESCRICAO;
		
		loadColunas();
		 
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


	@Override
	public void loadColunas() {
		
		_colunas = new ArrayList<String>();
		_colunas.add("CODIGO");
		_colunas.add("DESCRICAO");

	}



}
