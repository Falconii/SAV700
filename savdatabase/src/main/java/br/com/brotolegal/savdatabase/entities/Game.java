package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 04/07/2016.
 */
public class Game extends ObjRegister {


    protected String U10_CODIGO;
    protected String U10_DESCRI;
    protected String U10_STATUS;
    protected String U10_DTINI;
    protected String U10_DTFIM;
    protected String U10_ULTCAL;
    protected String U10_DTCAL;
    protected String U10_HRCAL;
    protected int    U10_QTDPRE;


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Game";


    public Game(){

        super(_OBJETO,"GAME");

        loadColunas();

        InicializaFields();
    }


    public Game(String u10_CODIGO, String u10_DESCRI, String u10_STATUS, String u10_DTINI, String u10_DTFIM, String u10_ULTCAL, String u10_DTCAL, String u10_HRCAL, int u10_QTDPRE) {

        super(_OBJETO,"GAME");

        loadColunas();

        InicializaFields();

        U10_CODIGO = u10_CODIGO;
        U10_DESCRI = u10_DESCRI;
        U10_STATUS = u10_STATUS;
        U10_DTINI = u10_DTINI;
        U10_DTFIM = u10_DTFIM;
        U10_ULTCAL = u10_ULTCAL;
        U10_DTCAL = u10_DTCAL;
        U10_HRCAL = u10_HRCAL;
        U10_QTDPRE = u10_QTDPRE;
    }

    public String getU10_CODIGO() {
        return U10_CODIGO;
    }

    public void setU10_CODIGO(String u10_CODIGO) {
        U10_CODIGO = u10_CODIGO;
    }

    public String getU10_DESCRI() {
        return U10_DESCRI;
    }

    public void setU10_DESCRI(String u10_DESCRI) {
        U10_DESCRI = u10_DESCRI;
    }

    public String getU10_STATUS() {
        return U10_STATUS;
    }

    public void setU10_STATUS(String u10_STATUS) {
        U10_STATUS = u10_STATUS;
    }

    public String getU10_DTINI() {
        return U10_DTINI;
    }

    public void setU10_DTINI(String u10_DTINI) {
        U10_DTINI = u10_DTINI;
    }

    public String getU10_DTFIM() {
        return U10_DTFIM;
    }

    public void setU10_DTFIM(String u10_DTFIM) {
        U10_DTFIM = u10_DTFIM;
    }

    public String getU10_ULTCAL() {
        return U10_ULTCAL;
    }

    public void setU10_ULTCAL(String u10_ULTCAL) {
        U10_ULTCAL = u10_ULTCAL;
    }

    public String getU10_DTCAL() {
        return U10_DTCAL;
    }

    public void setU10_DTCAL(String u10_DTCAL) {
        U10_DTCAL = u10_DTCAL;
    }

    public String getU10_HRCAL() {
        return U10_HRCAL;
    }

    public void setU10_HRCAL(String u10_HRCAL) {
        U10_HRCAL = u10_HRCAL;
    }

    public int getU10_QTDPRE() {
        return U10_QTDPRE;
    }

    public void setU10_QTDPRE(int u10_QTDPRE) {
        U10_QTDPRE = u10_QTDPRE;
    }

    @Override
    public void loadColunas() {
        _colunas = new ArrayList<String>();
        _colunas.add("U10_CODIGO");
        _colunas.add("U10_DESCRI");
        _colunas.add("U10_STATUS");
        _colunas.add("U10_DTINI");
        _colunas.add("U10_DTFIM");
        _colunas.add("U10_ULTCAL");
        _colunas.add("U10_DTCAL");
        _colunas.add("U10_HRCAL");
        _colunas.add("U10_QTDPRE");


    }
}
