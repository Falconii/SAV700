package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 29/01/2018.
 */

public class Pessoa {


    private String CODIGO;
    private String DESCRICAO;


    public Pessoa(String cODIGO, String dESCRICAO) {
        super();
        CODIGO = cODIGO;
        DESCRICAO = dESCRICAO;
    }


    public String getCODIGO() {
        return CODIGO;
    }


    public void setCODIGO(String cODIGO) {
        CODIGO = cODIGO;
    }


    public String getDESCRICAO() {
        return CODIGO+"-"+DESCRICAO;
    }


    public void setDESCRICAO(String dESCRICAO) {
        DESCRICAO = dESCRICAO;
    }


}
