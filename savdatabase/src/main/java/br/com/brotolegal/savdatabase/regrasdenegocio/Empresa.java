package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 17/10/2017.
 */

public class Empresa {

    private String CODIGO;
    private String PEDDATA;
    private String PEDHORA;
    private String CARDATA;
    private String CARHORA;

    public Empresa() {}

    public Empresa(String CODIGO, String PEDDATA, String PEDHORA, String CARDATA, String CARHORA) {
        this.CODIGO = CODIGO;
        this.PEDDATA = PEDDATA;
        this.PEDHORA = PEDHORA;
        this.CARDATA = CARDATA;
        this.CARHORA = CARHORA;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getPEDDATA() {
        return PEDDATA;
    }

    public void setPEDDATA(String PEDDATA) {
        this.PEDDATA = PEDDATA;
    }

    public String getPEDHORA() {
        return PEDHORA;
    }

    public void setPEDHORA(String PEDHORA) {
        this.PEDHORA = PEDHORA;
    }

    public String getCARDATA() {
        return CARDATA;
    }

    public void setCARDATA(String CARDATA) {
        this.CARDATA = CARDATA;
    }

    public String getCARHORA() {
        return CARHORA;
    }

    public void setCARHORA(String CARHORA) {
        this.CARHORA = CARHORA;
    }
}
