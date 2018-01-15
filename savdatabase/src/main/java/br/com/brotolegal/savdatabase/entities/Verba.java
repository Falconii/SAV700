package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Verba extends ObjRegister {

	protected String CODIGO;
	protected String DESCRICAO;
	protected String NIVEL;
	protected String TIPO;
	protected String TPACORDO;


	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Verba";


	public Verba(){

		super(_OBJETO,"VERBA");

		loadColunas();

		InicializaFields();
	}


	public Verba(String CODIGO, String DESCRICAO, String NIVEL, String TIPO, String TPACORDO) {

		super(_OBJETO,"VERBA");

		loadColunas();

		InicializaFields();

		this.CODIGO = CODIGO;
		this.DESCRICAO = DESCRICAO;
		this.NIVEL = NIVEL;
		this.TIPO = TIPO;
		this.TPACORDO = TPACORDO;
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

	public String getNIVEL() {
		return NIVEL;
	}

	public void setNIVEL(String NIVEL) {
		this.NIVEL = NIVEL;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String TIPO) {
		this.TIPO = TIPO;
	}

	public String getTPACORDO() {
		return TPACORDO;
	}

	public void setTPACORDO(String TPACORDO) {
		this.TPACORDO = TPACORDO;
	}


	public String get_Nivel(){

		String retorno = "";

		switch (this.NIVEL) {

			case "1":
				retorno = "VENDEDOR";
				break;
			case "2":
				retorno = "GA";
				break;
			case "3":
				retorno = "GNV";
				break;
			default:
				retorno = "DIRETORIA";
		}

		return retorno;

	}


	public String get_Nivel(String nivel){

		String retorno = "";

		switch (nivel) {

			case "1":
				retorno = "VENDEDOR";
				break;
			case "2":
				retorno = "GA";
				break;
			case "3":
				retorno = "GNV";
				break;
			default:
				retorno = "DIRETORIA";
		}

		return retorno;

	}


	public String get_Tipo(String tipo){

		String retorno = "";

		switch (tipo) {

			case "N":
				retorno = "NORMAL";
				break;
			case "X":
				retorno = "TIPO X";
				break;
			case "A":
				retorno = "ACORDO";
				break;
			case "P":
				retorno = "POLÍTICA";
				break;
			default:
				retorno = "NÃO DEFINIDO";
		}

		return retorno;

	}



	@Override
	public void loadHelp() {

		List<HelpParam> help     = new ArrayList<HelpParam>();
		help.add(new HelpParam("DESCRIÇÃO" ,
				"SELECT CODIGO,DESCRICAO,TIPO,NIVEL FROM VERBA WHERE  TIPO <> 'A' ",
				" AND DESCRICAO LIKE ''%{0}%'' "           ,
				"ORDER BY DESCRICAO",
				"DESCRICAO",
				new String[] {"CODIGO","TIPO"},
				"",
				new String[] {}));

		help.add(new HelpParam("CODIGO"    ,
				"SELECT CODIGO,DESCRICAO,TIPO,NIVEL FROM VERBA WHERE  TIPO <> 'A' ",
				" AND CODIGO LIKE ''{0}%'' "    			  ,
				"ORDER BY CODIGO",
				"CODIGO",
				new String[] {"CODIGO","TIPO"},
				"",   //aliaswhere
				new String[] {}));

		List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("VERBA", help, filtro, null));

		loadTableHelp("VERBA");


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

		linha2 = "Nivel: "+this.get_Nivel(cursor.getString(cursor.getColumnIndex("nivel")))+" Tipo: "+this.get_Tipo(cursor.getString(cursor.getColumnIndex("tipo")));

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
		_colunas.add("NIVEL");
		_colunas.add("TIPO");
		_colunas.add("TPACORDO");

		}

		}
