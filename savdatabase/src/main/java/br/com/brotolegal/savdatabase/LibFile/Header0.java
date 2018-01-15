package br.com.brotolegal.savdatabase.LibFile;

public class Header0 {
	
	private String DATA_HORA;
	private String LABEL;
	private String MD5;
	
	public Header0(String[] values) {
		
		DATA_HORA = values[1]; 
		LABEL	  = values[2];
		MD5       = values[3];
		
	}
	
	


}
