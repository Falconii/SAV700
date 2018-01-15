package br.com.brotolegal.savdatabase.util;

/**
 * Created by Falconi on 03/10/2016.
 */
public class Logradouro {

    public String CODIGO;
    public String ABREVIATURA;
    public String LOGRADOURO;


    public Logradouro(String cODIGO, String aBREVIATURA, String lOGRADOURO) {
        super();
        CODIGO      = cODIGO;
        ABREVIATURA = aBREVIATURA;
        LOGRADOURO  = lOGRADOURO;
    }


    public String getCODIGO() {
        return CODIGO;
    }


    public void setCODIGO(String cODIGO) {
        CODIGO = cODIGO;
    }


    public String getABREVIATURA() {
        return ABREVIATURA;
    }


    public void setABREVIATURA(String aBREVIATURA) {
        ABREVIATURA = aBREVIATURA;
    }


    public String getLOGRADOURO() {
        return LOGRADOURO;
    }


    public void setLOGRADOURO(String lOGRADOURO) {
        LOGRADOURO = lOGRADOURO;
    }

    public String getAbreDescri(){

        return ABREVIATURA+"-"+LOGRADOURO;

    }

}


