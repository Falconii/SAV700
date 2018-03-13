package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;

public class FreteMedio extends ObjRegister {

    protected String TABELA;
    protected String ESTADO;
    protected Float  FDMIN;
    protected Float  FDMAX;
    protected Float  VALOR;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.FreteMedio";


    public FreteMedio(){

        super(_OBJETO,"FRETEMEDIO");

        loadColunas();

        InicializaFields();
    }

    public FreteMedio(String TABELA, String ESTADO, Float FDMIN, Float FDMAX, Float VALOR) {

        super(_OBJETO,"FRETEMEDIO");

        loadColunas();

        InicializaFields();

        this.TABELA = TABELA;
        this.ESTADO = ESTADO;
        this.FDMIN = FDMIN;
        this.FDMAX = FDMAX;
        this.VALOR = VALOR;
    }

    public String getTABELA() {
        return TABELA;
    }

    public void setTABELA(String TABELA) {
        this.TABELA = TABELA;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
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

    public Float getVALOR() {
        return VALOR;
    }

    public void setVALOR(Float VALOR) {
        this.VALOR = VALOR;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();
        _colunas.add("TABELA");
        _colunas.add("ESTADO");
        _colunas.add("FDMIN");
        _colunas.add("FDMAX");
        _colunas.add("VALOR");


    }
}
