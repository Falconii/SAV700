package br.com.brotolegal.savdatabase.regrasdenegocio;

public class ExceptionNoConexao extends Exception {

	private static final long serialVersionUID = 2102152300815597390L;
	private final static String mensa = "Falha Leitura Do Pedido !!!";

	public ExceptionNoConexao(){

		super(mensa);

	}


	public ExceptionNoConexao(String mensage){
		
		super(mensa + " " + mensage);
		
	}

}
