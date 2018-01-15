package br.com.brotolegal.savdatabase.regrasdenegocio;

public class ExceptionDicionario extends Exception {

	private final static String mensa = "Dicionario Inválido !!!";

	private static final long serialVersionUID = -2456723853444743500L;
	
	public ExceptionDicionario(){
		
		super(mensa);
		
		
	}

	
	public ExceptionDicionario(String mensage){
		
		super(mensa + " " + mensage);
		
		
	}

}
