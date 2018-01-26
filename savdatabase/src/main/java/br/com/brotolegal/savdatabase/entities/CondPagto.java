package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;

public class CondPagto extends ObjRegister {

	protected String CODIGO;
	protected String DESCRICAO;
	protected String FORMA;
	protected Integer MEDIA;
	protected String JUROS;
	protected String JUROS2;

	
	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.CondPagto";
	
	
	public CondPagto(){
		
		 super(_OBJETO,"CONDPAGTO");
		 
		 loadColunas();
		 
		 InicializaFields();
	}
	




	public CondPagto(String cODIGO,
			String dESCRICAO, String fORMA, Integer mEDIA, String jUROS,  String jUROS2) {

		super(_OBJETO,"CONDPAGTO");

		loadColunas();

		InicializaFields();

		CODIGO = cODIGO;
		DESCRICAO = dESCRICAO;
		FORMA  = fORMA;
		MEDIA  = mEDIA;
		JUROS  = jUROS;
		JUROS2 = jUROS2;
		
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





	public String getFORMA() {
		return FORMA;
	}





	public void setFORMA(String fORMA) {
		FORMA = fORMA;
	}





	public Integer getMEDIA() {
		return MEDIA;
	}





	public void setMEDIA(Integer mEDIA) {
		MEDIA = mEDIA;
	}





	public String getJUROS() {
		return JUROS;
	}





	public void setJUROS(String jUROS) {
		JUROS = jUROS;
	}


   public Double getJuros(){
	   
		BigDecimal JUROS;
		
		Double juros = (Double.parseDouble(this.getJUROS()));
				
		JUROS = new BigDecimal(juros);
		
		JUROS = JUROS.setScale(9, BigDecimal.ROUND_HALF_UP);

	    return JUROS.doubleValue();
	   
   }

	public String getJUROS2(){


		return this.JUROS2;

	}

	public Double getJuros2() {

		BigDecimal JUROS;

		Double juros = (Double.parseDouble(this.getJUROS2()));

		JUROS = new BigDecimal(juros);

		JUROS = JUROS.setScale(9, BigDecimal.ROUND_HALF_UP);

		return JUROS.doubleValue();
	}

	public void setJUROS2(String JUROS2) {
		this.JUROS2 = JUROS2;
	}

	@Override
	public void loadHelp() {

		List<HelpParam> help     = new ArrayList<HelpParam>();
		help.add(new HelpParam("DESCRIÇÃO" ,
				"SELECT CODIGO,DESCRICAO FROM CONDPAGTO ",
				"WHERE DESCRICAO LIKE ''%{0}%'' "            ,
				"ORDER BY DESCRICAO ",
				"DESCRICAO",
				new String[] {"CODIGO","DESCRICAO"},
				"",
				new String[] {}));
		
		help.add(new HelpParam("CODIGO",
				"SELECT CODIGO,DESCRICAO FROM CONDPAGTO ", 
				"WHERE CODIGO LIKE ''{0}%'' "           , 
				"ORDER BY CODIGO ",
				"CODIGO",
				new String[] {"CODIGO","DESCRICAO"},
				"",
				new String[] {}));

		List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();
		
		_fileTable.add(new FileTable("CONDPAGTO", help, filtro, null));
		
		loadTableHelp("CONDPAGTO");


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
		_colunas.add("FORMA");
		_colunas.add("MEDIA");
		_colunas.add("JUROS");
		_colunas.add("JUROS2");
		
	}


}
