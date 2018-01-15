package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Rede extends ObjRegister {
	
	protected String CODIGO;
	protected String DESCRICAO;
	protected String GRPREDE;
	protected String TIPO;
	protected String AGRUPA;
	
	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Rede";
	
	
	public Rede(){
		
		 super(_OBJETO,"REDE");
		 
		 loadColunas();
	}
	
	
	




	public Rede(String cODIGO, String dESCRICAO,
			String gRPREDE, String tIPO, String aGRUPA) {
	    
		super(_OBJETO,"REDE");

		CODIGO = cODIGO;
		DESCRICAO = dESCRICAO;
		GRPREDE = gRPREDE;
		TIPO = tIPO;
		AGRUPA = aGRUPA;
		
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







	public String getGRPREDE() {
		return GRPREDE;
	}







	public void setGRPREDE(String gRPREDE) {
		GRPREDE = gRPREDE;
	}







	public String getTIPO() {
		return TIPO;
	}







	public void setTIPO(String tIPO) {
		TIPO = tIPO;
	}







	public String getAGRUPA() {
		return AGRUPA;
	}







	public void setAGRUPA(String aGRUPA) {
		AGRUPA = aGRUPA;
	}




	@Override
	public void loadHelp() {



		/*
		 *
		 *  ALIAS REDE
		 *
		 *
		 */

		List<HelpParam> help     = new ArrayList<HelpParam>();

		help.add(new HelpParam("DESCRIÇÃO" ,
				"select codigo,descricao  from rede  ",
				"where descricao like ''%{0}%'' "           ,
				"order by descricao",
				"descricao",
				new String[] {"CODIGO"},
				"",   //aliaswhere
				new String[] {}));

		help.add(new HelpParam("CÓDIGO",
				"select codigo,descricao  from rede  ",
				"where codigo like ''%{0}%'' "           ,
				"order by codigo",
				"codigo",
				new String[] {"CODIGO"},
				"",   //aliaswhere
				new String[] {}));



		List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("REDE", help, filtro, null));

		loadTableHelp("REDE");

		/*
		 *
		 *  ALIAS REDE E CLIENTE E VENDEDOR
		 *
		 *
		 */

		help     = new ArrayList<HelpParam>();

		help.add(new HelpParam("DESCRIÇÃO" ,
				"select distinct rede.codigo as codigo,rede.descricao as descricao from rede inner join cliente on cliente.rede = rede.codigo ",
				"where rede.descricao like ''%{0}%'' "           ,
				"order by rede.descricao",
				"descricao",
				new String[] {"CODIGO"},
				"",   //aliaswhere
				new String[] {}));

		help.add(new HelpParam("CÓDIGO",
				"select distinct rede.codigo as codigo,rede.descricao as descricao from rede inner join cliente on cliente.rede = rede.codigo ",
				"where codigo like ''%{0}%'' "           ,
				"order by codigo",
				"codigo",
				new String[] {"CODIGO"},
				"",   //aliaswhere
				new String[] {}));



		filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("REDECLIENTE", help, filtro, null));

		loadTableHelp("REDECLIENTE");


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

			linha1 = cursor.getString(cursor.getColumnIndex("codigo"))+" "+cursor.getString(cursor.getColumnIndex("descricao"));

			linha2 = "";

			texto1 = "";

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
		_colunas.add("GRPREDE");
		_colunas.add("TIPO");
		_colunas.add("AGRUPA");

	}

	


}
