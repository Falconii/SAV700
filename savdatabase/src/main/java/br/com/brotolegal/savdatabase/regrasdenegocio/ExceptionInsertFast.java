package br.com.brotolegal.savdatabase.regrasdenegocio;

public class ExceptionInsertFast extends Exception {

	private final static String mensa = "Inclusão De Registro Inválido !!!";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2456723853444743500L;
	
	public ExceptionInsertFast(){
		
		super(mensa);
		
		
	}

	
	public ExceptionInsertFast(String mensage){
		
		super(mensa + " " + mensage);
		
		
	}

}
