package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 05/07/2016.
 */
public class Imposto extends ObjRegister {

    protected String ESTADO;
    protected String GRUPO;
    protected String ORIGEM;
    protected Float ALQICMS;
    protected Float ALIQPIS;
    protected Float ALIQCOFINS;
    protected Float TOTALIMPOSTO;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Imposto";


    public Imposto(){

        super(_OBJETO,"IMPOSTO");

        loadColunas();

        InicializaFields();
    }


    public Imposto(String ESTADO, String GRUPO, String ORIGEM, Float ALQICMS, Float ALIQPIS, Float ALIQCOFINS, Float TOTALIMPOSTO) {

        super(_OBJETO,"IMPOSTO");

        loadColunas();

        InicializaFields();

        this.ESTADO = ESTADO;
        this.GRUPO = GRUPO;
        this.ORIGEM = ORIGEM;
        this.ALQICMS = ALQICMS;
        this.ALIQPIS = ALIQPIS;
        this.ALIQCOFINS = ALIQCOFINS;
        this.TOTALIMPOSTO = TOTALIMPOSTO;
    }


    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getGRUPO() {
        return GRUPO;
    }

    public void setGRUPO(String GRUPO) {
        this.GRUPO = GRUPO;
    }

    public String getORIGEM() {
        return ORIGEM;
    }

    public void setORIGEM(String ORIGEM) {
        this.ORIGEM = ORIGEM;
    }

    public Float getALQICMS() {
        return ALQICMS;
    }

    public void setALQICMS(Float ALQICMS) {
        this.ALQICMS = ALQICMS;
    }

    public Float getALIQPIS() {
        return ALIQPIS;
    }

    public void setALIQPIS(Float ALIQPIS) {
        this.ALIQPIS = ALIQPIS;
    }

    public Float getALIQCOFINS() {
        return ALIQCOFINS;
    }

    public void setALIQCOFINS(Float ALIQCOFINS) {
        this.ALIQCOFINS = ALIQCOFINS;
    }

    public Float getTOTALIMPOSTO() {
        return TOTALIMPOSTO;
    }

    public void setTOTALIMPOSTO(Float TOTALIMPOSTO) {
        this.TOTALIMPOSTO = TOTALIMPOSTO;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("ESTADO");
        _colunas.add("GRUPO");
        _colunas.add("ORIGEM");
        _colunas.add("ALQICMS");
        _colunas.add("ALIQPIS");
        _colunas.add("ALIQCOFINS");
        _colunas.add("TOTALIMPOSTO");


    }
}
