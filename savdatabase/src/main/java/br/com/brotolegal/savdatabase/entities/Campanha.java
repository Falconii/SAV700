package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 24/07/2017.
 */

public class Campanha extends ObjRegister {

    protected String DATA;
    protected String GNV;
    protected String GA;
    protected String VEND;
    protected String CAMPANHA;
    protected String PARTICIPANTE;
    protected String CATEGORIA;
    protected String MARCA;
    protected Float OBJETIVO;
    protected Float CARTEIRA;
    protected Float REAL;
    protected Float PREMIO;
    protected String NOMEGNV;
    protected String NOMEGA;
    protected String NOMEVEND;
    protected String DESCCAMPANHA;
    protected String NOMEPARTICIPANTE;
    protected String DESCCATEGORIA;
    protected String DESCMARCA;



    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Campanha";


    public Campanha() {

        super(_OBJETO, "CAMPANHA");

        loadColunas();

        InicializaFields();
    }


    public Campanha(String DATA, String GNV, String GA, String VEND, String CAMPANHA, String PARTICIPANTE, String CATEGORIA, String MARCA, Float OBJETIVO, Float CARTEIRA, Float REAL, Float PREMIO, String NOMEGNV, String NOMEGA, String NOMEVEND, String DESCCAMPANHA, String NOMEPARTICIPANTE, String DESCCATEGORIA, String DESCMARCA) {

        super(_OBJETO, "CAMPANHA");

        loadColunas();

        InicializaFields();

        this.DATA = DATA;
        this.GNV = GNV;
        this.GA = GA;
        this.VEND = VEND;
        this.CAMPANHA = CAMPANHA;
        this.PARTICIPANTE = PARTICIPANTE;
        this.CATEGORIA = CATEGORIA;
        this.MARCA = MARCA;
        this.OBJETIVO = OBJETIVO;
        this.CARTEIRA = CARTEIRA;
        this.REAL = REAL;
        this.PREMIO = PREMIO;
        this.NOMEGNV = NOMEGNV;
        this.NOMEGA = NOMEGA;
        this.NOMEVEND = NOMEVEND;
        this.DESCCAMPANHA = DESCCAMPANHA;
        this.NOMEPARTICIPANTE = NOMEPARTICIPANTE;
        this.DESCCATEGORIA = DESCCATEGORIA;
        this.DESCMARCA = DESCMARCA;

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

    public String getCAMPANHA() {
        return CAMPANHA;
    }

    public void setCAMPANHA(String CAMPANHA) {
        this.CAMPANHA = CAMPANHA;
    }

    public String getPARTICIPANTE() {
        return PARTICIPANTE;
    }

    public void setPARTICIPANTE(String PARTICIPANTE) {
        this.PARTICIPANTE = PARTICIPANTE;
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

    public Float getPREMIO() {
        return PREMIO;
    }

    public void setPREMIO(Float PREMIO) {
        this.PREMIO = PREMIO;
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

    public String getDESCCAMPANHA() {
        return DESCCAMPANHA;
    }

    public void setDESCCAMPANHA(String DESCCAMPANHA) {
        this.DESCCAMPANHA = DESCCAMPANHA;
    }

    public String getNOMEPARTICIPANTE() {
        return NOMEPARTICIPANTE;
    }

    public void setNOMEPARTICIPANTE(String NOMEPARTICIPANTE) {
        this.NOMEPARTICIPANTE = NOMEPARTICIPANTE;
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



    @Override
    public void loadColunas() {

        _colunas = new ArrayList<>();

        _colunas.add("DATA");
        _colunas.add("GNV");
        _colunas.add("GA");
        _colunas.add("VEND");
        _colunas.add("CAMPANHA");
        _colunas.add("PARTICIPANTE");
        _colunas.add("CATEGORIA");
        _colunas.add("MARCA");
        _colunas.add("OBJETIVO");
        _colunas.add("CARTEIRA");
        _colunas.add("REAL");
        _colunas.add("PREMIO");
        _colunas.add("NOMEGNV");
        _colunas.add("NOMEGA");
        _colunas.add("NOMEVEND");
        _colunas.add("DESCCAMPANHA");
        _colunas.add("NOMEPARTICIPANTE");
        _colunas.add("DESCCATEGORIA");
        _colunas.add("DESCMARCA");



    }
}
