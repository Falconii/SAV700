package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 07/10/2016.
 */
public class Frete extends ObjRegister {

    protected String FILIAL;
    protected String REDE;
    protected String ESTADO;
    protected String CODMUN;
    protected Float FDMIN;
    protected Float FDMAX;
    protected Float FRETE;



    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Frete";


    public Frete(){

        super(_OBJETO,"FRETE");

        loadColunas();

        InicializaFields();
    }

    public Frete(String FILIAL, String REDE, String ESTADO, String CODMUN, Float FDMIN, Float FDMAX, Float FRETE) {

        super(_OBJETO,"FRETE");

        loadColunas();

        InicializaFields();

        this.FILIAL = FILIAL;
        this.REDE   = REDE;
        this.ESTADO = ESTADO;
        this.CODMUN = CODMUN;
        this.FDMIN  = FDMIN;
        this.FDMAX  = FDMAX;
        this.FRETE  = FRETE;
    }


    public String getFILIAL() {
        return FILIAL;
    }

    public void setFILIAL(String FILIAL) {
        this.FILIAL = FILIAL;
    }

    public String getREDE() {
        return REDE;
    }

    public void setREDE(String REDE) {
        this.REDE = REDE;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getCODMUN() {
        return CODMUN;
    }

    public void setCODMUN(String CODMUN) {
        this.CODMUN = CODMUN;
    }

    public Float getFDMIN() {
        return FDMIN;
    }

    public void setFDMIN(Float FDMIN) {
        this.FDMIN = FDMIN;
    }

    public Float getFDMAX() {
        return FDMAX;
    }

    public void setFDMAX(Float FDMAX) {
        this.FDMAX = FDMAX;
    }

    public Float getFRETE() {
        return FRETE;
    }

    public void setFRETE(Float FRETE) {
        this.FRETE = FRETE;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("FILIAL");
        _colunas.add("REDE");
        _colunas.add("ESTADO");
        _colunas.add("CODMUN");
        _colunas.add("FDMIN");
        _colunas.add("FDMAX");
        _colunas.add("FRETE");



    }
}
