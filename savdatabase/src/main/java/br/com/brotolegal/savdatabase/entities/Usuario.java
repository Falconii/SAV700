package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 26/10/2015.
 */

public class Usuario extends ObjRegister {
    protected String COD;
    protected String NOME;
    protected String SENHA;
    protected String EXPIRA;
    protected String CODPRO;
    protected String CODVEN;
    protected String NIVEL;
    protected String CLASS;
    protected String MODULO;
    protected String CODDIS;
    protected String STATUS;
    protected String CODSUP;
    protected String PROPRIETARIO;
    protected String GPS;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Usuario";

    public Usuario()  {

        super(_OBJETO,"USUARIO");

        loadColunas();

        InicializaFields();
    }

    public Usuario(String COD, String NOME, String SENHA, String EXPIRA, String CODPRO, String CODVEN, String NIVEL, String CLASS, String MODULO, String CODDIS, String STATUS, String CODSUP, String PROPRIETARIO, String GPS) {

        super(_OBJETO,"USUARIO");

        this.COD = COD;
        this.NOME = NOME;
        this.SENHA = SENHA;
        this.EXPIRA = EXPIRA;
        this.CODPRO = CODPRO;
        this.CODVEN = CODVEN;
        this.NIVEL = NIVEL;
        this.CLASS = CLASS;
        this.MODULO = MODULO;
        this.CODDIS = CODDIS;
        this.STATUS = STATUS;
        this.CODSUP = CODSUP;
        this.PROPRIETARIO = PROPRIETARIO;
        this.GPS          = GPS;


        loadColunas();

    }

    public String getCOD() {
        return COD;
    }

    public void setCOD(String COD) {
        this.COD = COD;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getSENHA() {
        return SENHA;
    }

    public void setSENHA(String SENHA) {
        this.SENHA = SENHA;
    }

    public String getEXPIRA() {
        return EXPIRA;
    }

    public void setEXPIRA(String EXPIRA) {
        this.EXPIRA = EXPIRA;
    }

    public String getCODPRO() {
        return CODPRO;
    }

    public void setCODPRO(String CODPRO) {
        this.CODPRO = CODPRO;
    }

    public String getCODVEN() {
        return CODVEN;
    }

    public void setCODVEN(String CODVEN) {
        this.CODVEN = CODVEN;
    }

    public String getNIVEL() {
        return NIVEL;
    }

    public void setNIVEL(String NIVEL) {
        this.NIVEL = NIVEL;
    }

    public String getCLASS() {
        return CLASS;
    }

    public void setCLASS(String CLASS) {
        this.CLASS = CLASS;
    }

    public String getMODULO() {
        return MODULO;
    }

    public void setMODULO(String MODULO) {
        this.MODULO = MODULO;
    }

    public String getCODDIS() {
        return CODDIS;
    }

    public void setCODDIS(String CODDIS) {
        this.CODDIS = CODDIS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCODSUP() {
        return CODSUP;
    }

    public void setCODSUP(String CODSUP) {
        this.CODSUP = CODSUP;
    }

    public String getPROPRIETARIO() {
        return PROPRIETARIO;
    }

    public void setPROPRIETARIO(String PROPRIETARIO) {
        this.PROPRIETARIO = PROPRIETARIO;
    }

    public String getGPS() {

        if (this.GPS.trim().isEmpty()){

            return "1";

        } else {

            return GPS;

        }
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }

    public String get_GPS(){

        String retorno = "SIM";

        if (this.GPS.trim().equals("1")){

            retorno = "SIM";
        }

        if (this.GPS.trim().equals("2")){


            retorno = "NÃO";

        }

        if (this.GPS.trim().equals("3")){

            retorno = "DEVO PERGUNTAR";

        }

        return retorno;

    }

    public  String get_CLASSE(){

        String classe = this.CLASS;

        try {


            switch (classe.charAt(0)){

                case 'V' :classe  = "VENDEDOR";break;
                case 'K' :classe  = "COORDENADOR DE CONTA";break;
                default:classe    = "-";

            }

        } catch (Exception e){

            classe = "";

        }

        return classe;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("COD");
        _colunas.add("NOME");
        _colunas.add("SENHA");
        _colunas.add("EXPIRA");
        _colunas.add("CODPRO");
        _colunas.add("CODVEN");
        _colunas.add("NIVEL");
        _colunas.add("CLASS");
        _colunas.add("MODULO");
        _colunas.add("CODDIS");
        _colunas.add("STATUS");
        _colunas.add("CODSUP");
        _colunas.add("PROPRIETARIO");
        _colunas.add("GPS");
    }
}
