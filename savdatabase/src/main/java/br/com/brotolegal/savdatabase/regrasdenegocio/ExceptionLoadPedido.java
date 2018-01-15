package br.com.brotolegal.savdatabase.regrasdenegocio;

public class ExceptionLoadPedido extends Exception {

	private static final long serialVersionUID = 2102152300815597390L;
	private final static String mensa = "Falha Leitura Do Pedido !!!";

	public ExceptionLoadPedido(){
		
		super(mensa);
		
	}


	public ExceptionLoadPedido(String mensage){
		
		super(mensa + " " + mensage);
		
	}

}
