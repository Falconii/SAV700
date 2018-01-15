package br.com.brotolegal.savdatabase.regrasdenegocio;

public class ExceptionSavePedido extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7944826251499128429L;
	
	private final static String mensa = "Falha Na Gravação Do Pedido !!!";

	public ExceptionSavePedido(){
		
		super(mensa);
		
	}


	public ExceptionSavePedido(String mensage){
		
		super(mensa + " " + mensage);
		
	}

}
