package br.com.brotolegal.savdatabase.entities;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 09/05/2017.
 */

public class ContaCorrente extends ObjRegister {

    protected String TIPO;
    protected String FILIAL;
    protected String SERIE;
    protected String NOTAFISCAL;
    protected String FILPED;
    protected String PEDIDO;
    protected String EMISSAO;
    protected String CODCLI;
    protected String LOJA;
    protected String NOMECLI;
    protected String REDE;
    protected String CODPROD;
    protected String NOMEPROD;
    protected String CODPROMOCAO;
    protected Float QTDVEN;
    protected Float PRCVEN;
    protected Float PRCMIN;
    protected Float TOTPRCVEN;
    protected Float TOTPRCMIN;
    protected Float ARRECADADO;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.ContaCorrente";


    public ContaCorrente() {

        super(_OBJETO,"CONTACORRENTE");

        loadColunas();

        InicializaFields();


    }

    public ContaCorrente(String TIPO, String FILIAL, String SERIE, String NOTAFISCAL, String FILPED, String PEDIDO, String EMISSAO, String CODCLI, String LOJA, String NOMECLI, String REDE, String CODPROD, String NOMEPROD, String CODPROMOCAO, Float QTDVEN, Float PRCVEN, Float PRCMIN, Float TOTPRCVEN, Float TOTPRCMIN, Float ARRECADADO) {

        super(_OBJETO,"CONTACORRENTE");

        loadColunas();

        InicializaFields();

        this.TIPO = TIPO;
        this.FILIAL = FILIAL;
        this.SERIE = SERIE;
        this.NOTAFISCAL = NOTAFISCAL;
        this.FILPED = FILPED;
        this.PEDIDO = PEDIDO;
        this.EMISSAO = EMISSAO;
        this.CODCLI = CODCLI;
        this.LOJA = LOJA;
        this.NOMECLI = NOMECLI;
        this.REDE = REDE;
        this.CODPROD = CODPROD;
        this.NOMEPROD = NOMEPROD;
        this.CODPROMOCAO = CODPROMOCAO;
        this.QTDVEN = QTDVEN;
        this.PRCVEN = PRCVEN;
        this.PRCMIN = PRCMIN;
        this.TOTPRCVEN = TOTPRCVEN;
        this.TOTPRCMIN = TOTPRCMIN;
        this.ARRECADADO = ARRECADADO;
    }


    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
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

    public String getFILPED() {
        return FILPED;
    }

    public void setFILPED(String FILPED) {
        this.FILPED = FILPED;
    }

    public String getPEDIDO() {
        return PEDIDO;
    }

    public void setPEDIDO(String PEDIDO) {
        this.PEDIDO = PEDIDO;
    }

    public String getEMISSAO() {
        return EMISSAO;
    }

    public void setEMISSAO(String EMISSAO) {
        this.EMISSAO = EMISSAO;
    }

    public String getCODCLI() {
        return CODCLI;
    }

    public void setCODCLI(String CODCLI) {
        this.CODCLI = CODCLI;
    }

    public String getLOJA() {
        return LOJA;
    }

    public void setLOJA(String LOJA) {
        this.LOJA = LOJA;
    }

    public String getNOMECLI() {
        return NOMECLI;
    }

    public void setNOMECLI(String NOMECLI) {
        this.NOMECLI = NOMECLI;
    }

    public String getREDE() {
        return REDE;
    }

    public void setREDE(String REDE) {
        this.REDE = REDE;
    }

    public String getCODPROD() {
        return CODPROD;
    }

    public void setCODPROD(String CODPROD) {
        this.CODPROD = CODPROD;
    }

    public String getNOMEPROD() {
        return NOMEPROD;
    }

    public void setNOMEPROD(String NOMEPROD) {
        this.NOMEPROD = NOMEPROD;
    }

    public String getCODPROMOCAO() {
        return CODPROMOCAO;
    }

    public void setCODPROMOCAO(String CODPROMOCAO) {
        this.CODPROMOCAO = CODPROMOCAO;
    }

    public Float getQTDVEN() {
        return QTDVEN;
    }

    public void setQTDVEN(Float QTDVEN) {
        this.QTDVEN = QTDVEN;
    }

    public Float getPRCVEN() {
        return PRCVEN;
    }

    public void setPRCVEN(Float PRCVEN) {
        this.PRCVEN = PRCVEN;
    }

    public Float getPRCMIN() {
        return PRCMIN;
    }

    public void setPRCMIN(Float PRCMIN) {
        this.PRCMIN = PRCMIN;
    }

    public Float getTOTPRCVEN() {
        return TOTPRCVEN;
    }

    public void setTOTPRCVEN(Float TOTPRCVEN) {
        this.TOTPRCVEN = TOTPRCVEN;
    }

    public Float getTOTPRCMIN() {
        return TOTPRCMIN;
    }

    public void setTOTPRCMIN(Float TOTPRCMIN) {
        this.TOTPRCMIN = TOTPRCMIN;
    }

    public Float getARRECADADO() {
        return ARRECADADO;
    }

    public void setARRECADADO(Float ARRECADADO) {
        this.ARRECADADO = ARRECADADO;
    }

    @Override
    public void loadColunas() {

        _colunas.add("TIPO");
        _colunas.add("FILIAL");
        _colunas.add("SERIE");
        _colunas.add("NOTAFISCAL");
        _colunas.add("FILPED");
        _colunas.add("PEDIDO");
        _colunas.add("EMISSAO");
        _colunas.add("CODCLI");
        _colunas.add("LOJA");
        _colunas.add("NOMECLI");
        _colunas.add("REDE");
        _colunas.add("CODPROD");
        _colunas.add("NOMEPROD");
        _colunas.add("CODPROMOCAO");
        _colunas.add("QTDVEN");
        _colunas.add("PRCVEN");
        _colunas.add("PRCMIN");
        _colunas.add("TOTPRCVEN");
        _colunas.add("TOTPRCMIN");
        _colunas.add("ARRECADADO");


    }
}
