package br.com.brotolegal.savdatabase.entities;


import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
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
	public void loadHelp() {

		List<HelpParam> help     = new ArrayList<HelpParam>();
		help.add(new HelpParam("DESCRIÇÃO" ,
				"SELECT CODIGO,DESCRICAO,TAXAFIN FROM CANAL ",
				"WHERE DESCRICAO LIKE ''%{0}%'' "            ,
				"ORDER BY DESCRICAO ",
				"DESCRICAO",
				new String[] {"CODIGO","DESCRICAO","TAXAFIN"},
				"",
				new String[] {}));

		help.add(new HelpParam("CODIGO",
				"SELECT CODIGO,DESCRICAO,TAXAFIN FROM CANAL ",
				"WHERE CODIGO LIKE ''{0}%'' "           ,
				"ORDER BY CODIGO ",
				"CODIGO",
				new String[] {"CODIGO","DESCRICAO","TAXAFIN"},
				"",
				new String[] {}));

		List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("CANAL", help, filtro, null));

		loadTableHelp("CANAL");


	};

	@Override
	public String[] getHelpLinhas(Cursor cursor) {

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

			linha1 = "Código: "+cursor.getString(cursor.getColumnIndex("codigo"))+" Descrição: "+cursor.getString(cursor.getColumnIndex("descricao"));

			linha2 = "";

			letra  = cursor.getString(cursor.getColumnIndex("descricao")).substring(0, 1);

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
		_colunas.add("CODIGO");
		_colunas.add("DESCRICAO");
		_colunas.add("TABPRECO");
		_colunas.add("TAXAFIN");
		_colunas.add("REGIAO");

	}

}
