package br.com.brotolegal.savdatabase.LibFile;

/**
 * Created by Falconi on 12/07/2016.
 */

public class ExceptionNoFile extends Exception {

    private final static String mensa = "Arquivo Ainda Não Está Pronto.";
    /**
     *
     */
    private static final long serialVersionUID = -2456723853444743500L;

    public ExceptionNoFile(){

        super(mensa);


    }


    public ExceptionNoFile(String mensage){

        super(mensa + " " + mensage);


    }

}
