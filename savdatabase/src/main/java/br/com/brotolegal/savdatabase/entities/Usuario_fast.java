package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 26/10/2015.
 */

public class Usuario_fast {
    private String COD;
    private String NOME;
    private String SENHA;
    private String EXPIRA;
    private String CODPRO;
    private String CODVEN;
    private String NIVEL;
    private String CLASS;
    private String MODULO;
    private String CODDIS;
    private String STATUS;
    private String CODSUP;
    private String PROPRIETARIO;
    private String GPS;

    public Usuario_fast() {
    }

    public Usuario_fast(String COD, String NOME, String SENHA, String EXPIRA, String CODPRO, String CODVEN, String NIVEL, String CLASS, String MODULO, String CODDIS, String STATUS, String CODSUP, String PROPRIETARIO, String GPS) {
    
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
        this.GPS = GPS;
    
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
        return GPS;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }
    
    
    public void importacao(Usuario user){

        this.COD            = user.getCOD();
        this.NOME           = user.getNOME();
        this.SENHA          = user.getSENHA();
        this.EXPIRA         = user.getEXPIRA();
        this.CODPRO         = user.getCODPRO();
        this.CODVEN         = user.getCODVEN();
        this.NIVEL          = user.getNIVEL();
        this.CLASS          = user.getCLASS();
        this.MODULO         = user.getMODULO();
        this.CODDIS         = user.getCODDIS();
        this.STATUS         = user.getSTATUS();
        this.CODSUP         = user.getCODSUP();
        this.PROPRIETARIO   = user.getPROPRIETARIO();
        this.GPS            = user.getGPS();
        
        
    }
    
}