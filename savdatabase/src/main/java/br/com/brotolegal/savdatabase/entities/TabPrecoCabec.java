package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class TabPrecoCabec extends ObjRegister {

	protected String CODIGO;
	protected String DESCRICAO;
	protected String FLAGFAIXA;
	protected String FLAGHABDESC;
	protected String FLAGHABMOTIVO;
	protected String FLAGCONTRATO;
	protected String FLAGCC;
	protected String FLAGTAXAFINANC;
	protected String FLAGDESCCANAL;
	protected String FLAGDESCLOGIST;
	protected Float FAIXADE;
	protected Float FAIXAATE;


	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.TabPrecoCabec";


	public TabPrecoCabec(){

		super(_OBJETO,"TABPRECOCABEC");

		loadColunas();

		InicializaFields();
	}

	public TabPrecoCabec(String CODIGO, String DESCRICAO, String FLAGFAIXA, String FLAGHABDESC, String FLAGHABMOTIVO, String FLAGCONTRATO, String FLAGCC, String FLAGTAXAFINANC, String FLAGDESCCANAL, String FLAGDESCLOGIST, Float FAIXADE, Float FAIXAATE) {
		super(_OBJETO,"TABPRECOCABEC");

		loadColunas();

		InicializaFields();

		this.CODIGO = CODIGO;
		this.DESCRICAO = DESCRICAO;
		this.FLAGFAIXA = FLAGFAIXA;
		this.FLAGHABDESC = FLAGHABDESC;
		this.FLAGHABMOTIVO = FLAGHABMOTIVO;
		this.FLAGCONTRATO = FLAGCONTRATO;
		this.FLAGCC = FLAGCC;
		this.FLAGTAXAFINANC = FLAGTAXAFINANC;
		this.FLAGDESCCANAL = FLAGDESCCANAL;
		this.FLAGDESCLOGIST = FLAGDESCLOGIST;
		this.FAIXADE = FAIXADE;
		this.FAIXAATE = FAIXAATE;
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

	@Override
	public void loadHelp() {

		/*
		 *
		 *  ALIAS TABPRECO
		 *
		 *
		 */

		List<HelpParam> help     = new ArrayList<HelpParam>();

		help.add(new HelpParam("DESCRIÇÃO",
				"select codigo,descricao  from tabprecocabec   ",
				"where codigo like ''{0}%'' "           ,
				"order by codigo",
				"codigo",
				new String[] {"CODIGO","DESCRICAO"},
				"",   //aliaswhere
				new String[] {}));



		help.add(new HelpParam(
				"CÓDIGO" ,
				"select codigo,descricao  from tabprecocabec   ",
				"where razao like ''%{0}%'' "           ,
				"order by descricao",
				"descricao",
				new String[] {"CODIGO","DESCRICAO"},
				"",   //aliaswhere
				new String[] {}));



		List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("TABPRECO",  help, filtro, null));


		loadTableHelp("TABPRECO");


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
		_colunas.add("FLAGFAIXA");
		_colunas.add("FLAGHABDESC");
		_colunas.add("FLAGHABMOTIVO");
		_colunas.add("FLAGCONTRATO");
		_colunas.add("FLAGCC");
		_colunas.add("FLAGTAXAFINANC");
		_colunas.add("FLAGDESCCANAL");
		_colunas.add("FLAGDESCLOGIST");
		_colunas.add("FAIXADE");
		_colunas.add("FAIXAATE");


	}



}
