package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 21/06/2017.
 */

public class MetaCategoria {

    private String DATA;
    private String CLIENTE;
    private String LOJA;
    private String CATEGORIA;
    private String NOMECLIENTE;
    private String DESCCATEGORIA;
    private Float  OBJETIVO;
    private Float  CARTEIRA;
    private Float  REAL;

    public MetaCategoria(String DATA, String CLIENTE, String LOJA, String CATEGORIA, String NOMECLIENTE, String DESCCATEGORIA, Float OBJETIVO, Float CARTEIRA, Float REAL) {
        this.DATA = DATA;
        this.CLIENTE = CLIENTE;
        this.LOJA = LOJA;
        this.CATEGORIA = CATEGORIA;
        this.NOMECLIENTE = NOMECLIENTE;
        this.DESCCATEGORIA = DESCCATEGORIA;
        this.OBJETIVO = OBJETIVO;
        this.CARTEIRA = CARTEIRA;
        this.REAL = REAL;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(String CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public String getLOJA() {
        return LOJA;
    }

    public void setLOJA(String LOJA) {
        this.LOJA = LOJA;
    }

    public String getCATEGORIA() {
        return CATEGORIA;
    }

    public void setCATEGORIA(String CATEGORIA) {
        this.CATEGORIA = CATEGORIA;
    }

    public String getNOMECLIENTE() {
        return NOMECLIENTE;
    }

    public void setNOMECLIENTE(String NOMECLIENTE) {
        this.NOMECLIENTE = NOMECLIENTE;
    }

    public String getDESCCATEGORIA() {
        return DESCCATEGORIA;
    }

    public void setDESCCATEGORIA(String DESCCATEGORIA) {
        this.DESCCATEGORIA = DESCCATEGORIA;
    }

    public Float getOBJETIVO() {
        return OBJETIVO;
    }

    public void setOBJETIVO(Float OBJETIVO) {
        this.OBJETIVO = OBJETIVO;
    }

    public Float getCARTEIRA() {
        return CARTEIRA;
    }

    public void setCARTEIRA(Float CARTEIRA) {
        this.CARTEIRA = CARTEIRA;
    }

    public Float getREAL() {
        return REAL;
    }

    public void setREAL(Float REAL) {
        this.REAL = REAL;
    }
}
