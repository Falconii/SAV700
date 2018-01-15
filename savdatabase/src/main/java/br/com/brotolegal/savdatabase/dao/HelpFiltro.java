package br.com.brotolegal.savdatabase.dao;

public class HelpFiltro {
	
	private String    filtro;
	private String[]  opcoes;
	private String    where;
	private int       id;
	private String[]  fields;
	private String[]  keyValues;
	
	public HelpFiltro(String filtro, String[] opcoes, String where, int id, String[] fields, String[] keyValues) {

		this.filtro = filtro;
		
		this.opcoes = opcoes;
		
		this.where = where;
		
		this.id    = id; 
		
		this.fields = fields;
		
		this.keyValues = keyValues;
		
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public String[] getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(String[] opcoes) {
		this.opcoes = opcoes;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String[] getKeyValues() {
		return keyValues;
	}

	public void setKeyValues(String[] keyValues) {
		this.keyValues = keyValues;
	}


    public void setOneField(int index,String value){
    	
    	if (index < fields.length){
    		
    		fields[index] = value;
    		
    	}
    	
    }
	
    public String getOneField(int index){
    	
    	String retorno = "";
    	
    	if (index < fields.length){
    		
    		retorno = fields[index];
    		
    	}
    	
    	return retorno;
    	
    }

    public void setOneKeyValue(int index,String value){
    	
    	if (index < keyValues.length){
    		
    		keyValues[index] = value;
    		
    	}
    	
    }
	
    public String getOneKeyValue(int index){
    	
    	String retorno = "";
    	
    	if (index < keyValues.length){
    		
    		retorno = keyValues[index];
    		
    	}
    	
    	return retorno;
    	
    }
   
    
    
}
