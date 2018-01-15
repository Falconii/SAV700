package br.com.brotolegal.savdatabase.dao;

import java.util.ArrayList;
import java.util.List;

public class FileTable {
	
	private String ID;
	private List<HelpParam> parametros;
	private List<HelpFiltro> filtros;
	private List<String[]> FiltroAdicional = new ArrayList<String[]>();
	
	
	
	public FileTable(String iD, List<HelpParam> parametros, List<HelpFiltro> filtros, List<String[]> filtroAdicional) {

		ID 				= iD;
		this.parametros = parametros;
		this.filtros 	= filtros;
		FiltroAdicional = filtroAdicional;
		
	}



	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}



	public List<HelpParam> getParametros() {
		return parametros;
	}



	public void setParametros(List<HelpParam> parametros) {
		this.parametros = parametros;
	}



	public List<HelpFiltro> getFiltros() {
		return filtros;
	}



	public void setFiltros(List<HelpFiltro> filtros) {
		this.filtros = filtros;
	}



	public List<String[]> getFiltroAdicional() {
		return FiltroAdicional;
	}



	public void setFiltroAdicional(List<String[]> filtroAdicional) {
		FiltroAdicional = filtroAdicional;
	}
	
	
	

	
}
