package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 26/10/2016.
 */

public class ExceptionValidadeTabelaPreco extends  Exception{


    private final static String mensa = "Tabela de Preço Não Foi Atualizada.";

    public ExceptionValidadeTabelaPreco() {

        super(mensa);

    }


    public ExceptionValidadeTabelaPreco(String mensage){

        super(mensa + " " + mensage);

    }
}
