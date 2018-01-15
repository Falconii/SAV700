package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 26/10/2016.
 */

public class ExceptionValidadeAgendamentoAtrasado extends  Exception{


    private final static String mensa = "Existem Agendamentos Não Apontados !";

    public ExceptionValidadeAgendamentoAtrasado() {

        super(mensa);

    }


    public ExceptionValidadeAgendamentoAtrasado(String mensage){

        super(mensa + " " + mensage);

    }
}
