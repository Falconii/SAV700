package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 04/07/2016.
 */
public class PontosGame extends ObjRegister {

    protected String U12_TPMOV;
    protected String U12_CODU10;
    protected String U12_CODU11;
    protected String U12_DATA;
    protected String U12_CODPT;
    protected String U12_DESPT;
    protected Integer U12_PONTO;
    protected String U12_OBS;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.PontosGame";


    public PontosGame(){

        super(_OBJETO,"PONTOSGAME");

        loadColunas();

        InicializaFields();
    }


    public PontosGame(String u12_TPMOV, String u12_CODU10, String u12_CODU11, String u12_DATA, String u12_CODPT, String u12_DESPT, Integer u12_PONTO, String u12_OBS) {

        super(_OBJETO,"PONTOSGAME");

        loadColunas();

        InicializaFields();

        U12_TPMOV = u12_TPMOV;
        U12_CODU10 = u12_CODU10;
        U12_CODU11 = u12_CODU11;
        U12_DATA = u12_DATA;
        U12_CODPT = u12_CODPT;
        U12_DESPT = u12_DESPT;
        U12_PONTO = u12_PONTO;
        U12_OBS = u12_OBS;
    }

    public String getU12_TPMOV() {
        return U12_TPMOV;
    }

    public void setU12_TPMOV(String u12_TPMOV) {
        U12_TPMOV = u12_TPMOV;
    }

    public String getU12_CODU10() {
        return U12_CODU10;
    }

    public void setU12_CODU10(String u12_CODU10) {
        U12_CODU10 = u12_CODU10;
    }

    public String getU12_CODU11() {
        return U12_CODU11;
    }

    public void setU12_CODU11(String u12_CODU11) {
        U12_CODU11 = u12_CODU11;
    }

    public String getU12_DATA() {
        return U12_DATA;
    }

    public void setU12_DATA(String u12_DATA) {
        U12_DATA = u12_DATA;
    }

    public String getU12_CODPT() {
        return U12_CODPT;
    }

    public void setU12_CODPT(String u12_CODPT) {
        U12_CODPT = u12_CODPT;
    }

    public String getU12_DESPT() {
        return U12_DESPT;
    }

    public void setU12_DESPT(String u12_DESPT) {
        U12_DESPT = u12_DESPT;
    }

    public Integer getU12_PONTO() {
        return U12_PONTO;
    }

    public void setU12_PONTO(Integer u12_PONTO) {
        U12_PONTO = u12_PONTO;
    }

    public String getU12_OBS() {
        return U12_OBS;
    }

    public void setU12_OBS(String u12_OBS) {
        U12_OBS = u12_OBS;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("U12_TPMOV");
        _colunas.add("U12_CODU10");
        _colunas.add("U12_CODU11");
        _colunas.add("U12_DATA");
        _colunas.add("U12_CODPT");
        _colunas.add("U12_DESPT");
        _colunas.add("U12_PONTO");
        _colunas.add("U12_OBS");



    }
}
