package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 15/03/2017.
 */

public class NotaFiscalDet extends ObjRegister {

    protected String FILIAL;
    protected String SERIE;
    protected String NOTAFISCAL;
    protected String TIPODOC;
    protected String CODCLI;
    protected String CODLOJA;
    protected String ITEM;
    protected String CODPRODUTO;
    protected String DESCRICAOPRODUTO;
    protected Float QUANTIDADE;
    protected Float PRECOUNITARIO;
    protected Float TOTAL;
    protected Float DESCONTO;
    protected Float DESCONTOVERBA;
    protected String CODIGOVERBA;
    protected String DESCRICAOVERBA;
    protected String CODIGOACORDO;
    protected String PDFILIAL;
    protected String PDNUMERO;
    protected String SIMULADOR;
    protected String CFOP;


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.NotaFiscalDet";


    public NotaFiscalDet(){

        super(_OBJETO,"NOTAFISCALDET");

        loadColunas();

        InicializaFields();

    }

    public NotaFiscalDet(String FILIAL, String SERIE, String NOTAFISCAL, String TIPODOC, String CODCLI, String CODLOJA, String ITEM, String CODPRODUTO, String DESCRICAOPRODUTO, Float QUANTIDADE, Float PRECOUNITARIO, Float TOTAL, Float DESCONTO, Float DESCONTOVERBA, String CODIGOVERBA, String DESCRICAOVERBA, String CODIGOACORDO, String PDFILIAL, String PDNUMERO, String SIMULADOR,String CFOP) {

        super(_OBJETO,"NOTAFISCALDET");

        loadColunas();

        InicializaFields();

        this.FILIAL = FILIAL;
        this.SERIE = SERIE;
        this.NOTAFISCAL = NOTAFISCAL;
        this.TIPODOC = TIPODOC;
        this.CODCLI = CODCLI;
        this.CODLOJA = CODLOJA;
        this.ITEM = ITEM;
        this.CODPRODUTO = CODPRODUTO;
        this.DESCRICAOPRODUTO = DESCRICAOPRODUTO;
        this.QUANTIDADE = QUANTIDADE;
        this.PRECOUNITARIO = PRECOUNITARIO;
        this.TOTAL = TOTAL;
        this.DESCONTO = DESCONTO;
        this.DESCONTOVERBA = DESCONTOVERBA;
        this.CODIGOVERBA = CODIGOVERBA;
        this.DESCRICAOVERBA = DESCRICAOVERBA;
        this.CODIGOACORDO = CODIGOACORDO;
        this.PDFILIAL = PDFILIAL;
        this.PDNUMERO = PDNUMERO;
        this.SIMULADOR = SIMULADOR;
        this.CFOP      = CFOP;
    }


    public String getFILIAL() {
        return FILIAL;
    }

    public void setFILIAL(String FILIAL) {
        this.FILIAL = FILIAL;
    }

    public String getSERIE() {
        return SERIE;
    }

    public void setSERIE(String SERIE) {
        this.SERIE = SERIE;
    }

    public String getNOTAFISCAL() {
        return NOTAFISCAL;
    }

    public void setNOTAFISCAL(String NOTAFISCAL) {
        this.NOTAFISCAL = NOTAFISCAL;
    }

    public String getTIPODOC() {
        return TIPODOC;
    }

    public void setTIPODOC(String TIPODOC) {
        this.TIPODOC = TIPODOC;
    }

    public String getCODCLI() {
        return CODCLI;
    }

    public void setCODCLI(String CODCLI) {
        this.CODCLI = CODCLI;
    }

    public String getCODLOJA() {
        return CODLOJA;
    }

    public void setCODLOJA(String CODLOJA) {
        this.CODLOJA = CODLOJA;
    }

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public String getCODPRODUTO() {
        return CODPRODUTO;
    }

    public void setCODPRODUTO(String CODPRODUTO) {
        this.CODPRODUTO = CODPRODUTO;
    }

    public String getDESCRICAOPRODUTO() {
        return DESCRICAOPRODUTO;
    }

    public void setDESCRICAOPRODUTO(String DESCRICAOPRODUTO) {
        this.DESCRICAOPRODUTO = DESCRICAOPRODUTO;
    }

    public Float getQUANTIDADE() {
        return QUANTIDADE;
    }

    public void setQUANTIDADE(Float QUANTIDADE) {
        this.QUANTIDADE = QUANTIDADE;
    }

    public Float getPRECOUNITARIO() {
        return PRECOUNITARIO;
    }

    public void setPRECOUNITARIO(Float PRECOUNITARIO) {
        this.PRECOUNITARIO = PRECOUNITARIO;
    }

    public Float getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(Float TOTAL) {
        this.TOTAL = TOTAL;
    }

    public Float getDESCONTO() {
        return DESCONTO;
    }

    public void setDESCONTO(Float DESCONTO) {
        this.DESCONTO = DESCONTO;
    }

    public Float getDESCONTOVERBA() {
        return DESCONTOVERBA;
    }

    public void setDESCONTOVERBA(Float DESCONTOVERBA) {
        this.DESCONTOVERBA = DESCONTOVERBA;
    }

    public String getCODIGOVERBA() {
        return CODIGOVERBA;
    }

    public void setCODIGOVERBA(String CODIGOVERBA) {
        this.CODIGOVERBA = CODIGOVERBA;
    }

    public String getDESCRICAOVERBA() {
        return DESCRICAOVERBA;
    }

    public void setDESCRICAOVERBA(String DESCRICAOVERBA) {
        this.DESCRICAOVERBA = DESCRICAOVERBA;
    }

    public String getCODIGOACORDO() {
        return CODIGOACORDO;
    }

    public void setCODIGOACORDO(String CODIGOACORDO) {
        this.CODIGOACORDO = CODIGOACORDO;
    }

    public String getPDFILIAL() {
        return PDFILIAL;
    }

    public void setPDFILIAL(String PDFILIAL) {
        this.PDFILIAL = PDFILIAL;
    }

    public String getPDNUMERO() {
        return PDNUMERO;
    }

    public void setPDNUMERO(String PDNUMERO) {
        this.PDNUMERO = PDNUMERO;
    }

    public String getSIMULADOR() {
        return SIMULADOR;
    }

    public void setSIMULADOR(String SIMULADOR) {
        this.SIMULADOR = SIMULADOR;
    }

    public String getCFOP() {
        return CFOP;
    }

    public void setCFOP(String CFOP) {
        this.CFOP = CFOP;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("FILIAL");
        _colunas.add("SERIE");
        _colunas.add("NOTAFISCAL");
        _colunas.add("TIPODOC");
        _colunas.add("CODCLI");
        _colunas.add("CODLOJA");
        _colunas.add("ITEM");
        _colunas.add("CODPRODUTO");
        _colunas.add("DESCRICAOPRODUTO");
        _colunas.add("QUANTIDADE");
        _colunas.add("PRECOUNITARIO");
        _colunas.add("TOTAL");
        _colunas.add("DESCONTO");
        _colunas.add("DESCONTOVERBA");
        _colunas.add("CODIGOVERBA");
        _colunas.add("DESCRICAOVERBA");
        _colunas.add("CODIGOACORDO");
        _colunas.add("PDFILIAL");
        _colunas.add("PDNUMERO");
        _colunas.add("SIMULADOR");
        _colunas.add("CFOP");


    }
}
