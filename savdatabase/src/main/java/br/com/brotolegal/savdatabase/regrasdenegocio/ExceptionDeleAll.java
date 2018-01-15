package br.com.brotolegal.savdatabase.regrasdenegocio;

public class ExceptionDeleAll extends Exception {

	private final static String mensa = "Falha Na Rotinal DELETE ALL";
	/**
	 *
	 */
	private static final long serialVersionUID = -2456723853444743500L;

	public ExceptionDeleAll(){

		super(mensa);


	}


	public ExceptionDeleAll(String mensage){
		
		super(mensa + " " + mensage);
		
		
	}

}
