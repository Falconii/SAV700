package br.com.brotolegal.savdatabase.entities;


import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Dispositivo extends ObjRegister {

    protected String COD;
    protected String VERSAO;
    protected String BUILD;
    protected String IMEI;
    protected String CHAPA;
    protected String MODELO;
    protected String MARCA;
    protected String STATUS;
    protected String FABRICANTE;
    protected String TOKEN;


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Dispositivo";


    public Dispositivo()  {

        super(_OBJETO,"DISPOSITIVO");

        loadColunas();

        InicializaFields();
    }

    public Dispositivo(String COD, String VERSAO, String BUILD, String IMEI, String CHAPA, String MODELO, String MARCA, String STATUS, String FABRICANTE, String TOKEN) {

        super(_OBJETO,"DISPOSITIVO");

        loadColunas();

        InicializaFields();

        this.COD = COD;
        this.VERSAO = VERSAO;
        this.BUILD = BUILD;
        this.IMEI = IMEI;
        this.CHAPA = CHAPA;
        this.MODELO = MODELO;
        this.MARCA = MARCA;
        this.STATUS = STATUS;
        this.FABRICANTE = FABRICANTE;
        if (TOKEN != null) this.TOKEN = TOKEN;
    }

    public String getCOD() {
        return COD;
    }

    public void setCOD(String COD) {
        this.COD = COD;
    }

    public String getVERSAO() {
        return VERSAO;
    }

    public void setVERSAO(String VERSAO) {
        this.VERSAO = VERSAO;
    }

    public String getBUILD() {
        return BUILD;
    }

    public void setBUILD(String BUILD) {
        this.BUILD = BUILD;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getCHAPA() {
        return CHAPA;
    }

    public void setCHAPA(String CHAPA) {
        this.CHAPA = CHAPA;
    }

    public String getMODELO() {
        return MODELO;
    }

    public void setMODELO(String MODELO) {
        this.MODELO = MODELO;
    }

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getFABRICANTE() {
        return FABRICANTE;
    }

    public void setFABRICANTE(String FABRICANTE) {
        this.FABRICANTE = FABRICANTE;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }


    public String get_TOKEN(){

        String retorno = "";

        if (this.TOKEN == null || this.TOKEN.isEmpty()){

            retorno = "NÃO CADASTRADO.";

        } else {

            retorno = "ATIVO.";

        }

        return retorno;

    }
    @Override
    public String toString() {
        return  "Dispositivo : \n" +
                "CODiGO      = " + COD    + "\n" +
                "VERSÃO      = " + VERSAO + "\n" +
                "BUILD       = " + BUILD  + "\n" +
                "IMEI        = " + IMEI   + "\n" +
                "CHAPA       = " + CHAPA  + "\n" +
                "MODELO      = " + MODELO + "\n" +
                "MARCA       = " + MARCA  + "\n" +
                "STATUS      = " + STATUS + "\n" +
                "FABRICANTE  = " + FABRICANTE + "\n" ;

    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("COD");
        _colunas.add("VERSAO");
        _colunas.add("BUILD");
        _colunas.add("IMEI");
        _colunas.add("CHAPA");
        _colunas.add("MODELO");
        _colunas.add("MARCA");
        _colunas.add("STATUS");
        _colunas.add("FABRICANTE");
        _colunas.add("TOKEN");


    }

}
