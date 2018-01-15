package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 15/06/2017.
 */

public class Meta extends ObjRegister {

    protected String DATA;
    protected String GNV;
    protected String GA;
    protected String VEND;
    protected String SEGMENTO;
    protected String CLIENTE;
    protected String LOJA;
    protected String CATEGORIA;
    protected String MARCA;
    protected String PRODUTO;
    protected Float OBJETIVO;
    protected Float CARTEIRA;
    protected Float REAL;
    protected String NOMEGNV;
    protected String NOMEGA;
    protected String NOMEVEND;
    protected String DESCSEGMENTO;
    protected String NOMECLIENTE;
    protected String DESCCATEGORIA;
    protected String DESCMARCA;
    protected String DESCPRODUTO;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Meta";

    public Meta(){

        super(_OBJETO,"META");

        loadColunas();

        InicializaFields();
    }

    public Meta(String DATA, String GNV, String GA, String VEND, String SEGMENTO, String CLIENTE, String LOJA, String CATEGORIA, String MARCA, String PRODUTO, Float OBJETIVO, Float CARTEIRA, Float REAL, String NOMEGNV, String NOMEGA, String NOMEVEND, String DESCSEGMENTO, String NOMECLIENTE, String DESCCATEGORIA, String DESCMARCA, String DESCPRODUTO) {

        super(_OBJETO,"META");

        loadColunas();

        InicializaFields();


        this.DATA = DATA;
        this.GNV = GNV;
        this.GA = GA;
        this.VEND = VEND;
        this.SEGMENTO = SEGMENTO;
        this.CLIENTE = CLIENTE;
        this.LOJA = LOJA;
        this.CATEGORIA = CATEGORIA;
        this.MARCA = MARCA;
        this.PRODUTO = PRODUTO;
        this.OBJETIVO = OBJETIVO;
        this.CARTEIRA = CARTEIRA;
        this.REAL = REAL;
        this.NOMEGNV = NOMEGNV;
        this.NOMEGA = NOMEGA;
        this.NOMEVEND = NOMEVEND;
        this.DESCSEGMENTO = DESCSEGMENTO;
        this.NOMECLIENTE = NOMECLIENTE;
        this.DESCCATEGORIA = DESCCATEGORIA;
        this.DESCMARCA = DESCMARCA;
        this.DESCPRODUTO = DESCPRODUTO;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getGNV() {
        return GNV;
    }

    public void setGNV(String GNV) {
        this.GNV = GNV;
    }

    public String getGA() {
        return GA;
    }

    public void setGA(String GA) {
        this.GA = GA;
    }

    public String getVEND() {
        return VEND;
    }

    public void setVEND(String VEND) {
        this.VEND = VEND;
    }

    public String getSEGMENTO() {
        return SEGMENTO;
    }

    public void setSEGMENTO(String SEGMENTO) {
        this.SEGMENTO = SEGMENTO;
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

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getPRODUTO() {
        return PRODUTO;
    }

    public void setPRODUTO(String PRODUTO) {
        this.PRODUTO = PRODUTO;
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

    public String getNOMEGNV() {
        return NOMEGNV;
    }

    public void setNOMEGNV(String NOMEGNV) {
        this.NOMEGNV = NOMEGNV;
    }

    public String getNOMEGA() {
        return NOMEGA;
    }

    public void setNOMEGA(String NOMEGA) {
        this.NOMEGA = NOMEGA;
    }

    public String getNOMEVEND() {
        return NOMEVEND;
    }

    public void setNOMEVEND(String NOMEVEND) {
        this.NOMEVEND = NOMEVEND;
    }

    public String getDESCSEGMENTO() {
        return DESCSEGMENTO;
    }

    public void setDESCSEGMENTO(String DESCSEGMENTO) {
        this.DESCSEGMENTO = DESCSEGMENTO;
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

    public String getDESCMARCA() {
        return DESCMARCA;
    }

    public void setDESCMARCA(String DESCMARCA) {
        this.DESCMARCA = DESCMARCA;
    }

    public String getDESCPRODUTO() {
        return DESCPRODUTO;
    }

    public void setDESCPRODUTO(String DESCPRODUTO) {
        this.DESCPRODUTO = DESCPRODUTO;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("DATA");
        _colunas.add("GNV");
        _colunas.add("GA");
        _colunas.add("VEND");
        _colunas.add("SEGMENTO");
        _colunas.add("CLIENTE");
        _colunas.add("LOJA");
        _colunas.add("CATEGORIA");
        _colunas.add("MARCA");
        _colunas.add("PRODUTO");
        _colunas.add("OBJETIVO");
        _colunas.add("CARTEIRA");
        _colunas.add("REAL");
        _colunas.add("NOMEGNV");
        _colunas.add("NOMEGA");
        _colunas.add("NOMEVEND");
        _colunas.add("DESCSEGMENTO");
        _colunas.add("NOMECLIENTE");
        _colunas.add("DESCCATEGORIA");
        _colunas.add("DESCMARCA");
        _colunas.add("DESCPRODUTO");


    }
}
