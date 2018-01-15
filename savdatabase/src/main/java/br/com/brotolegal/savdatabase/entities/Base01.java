package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 07/06/2017.
 */

public class Base01 extends ObjRegister {

    protected String VISAO;
    protected String NIVEL;
    protected String NNIVEL;
    protected String DATA;
    protected String CLIENTE;
    protected String LOJA;
    protected String REDE;
    protected String CATEGORIA;
    protected String MARCA;
    protected String PRODUTO;
    protected Float QTD;
    protected Float VALOR;
    protected Float PRCVEN;
    protected Float PRCBASE;
    protected Float DESCONTO;
    protected Float TOTALQTD;
    protected Float TOTALVLR;
    protected String MOBILENRO;
    protected String MOBILETIPO;
    protected String PEDIDOPROTHEUSNRO;
    protected String PEDIDOPROTHEUSFILIAL;
    protected String CLIENTERAZAO;
    protected String REDEDESCRI;
    protected String CATEGORIADESCRI;
    protected String MARCADESCRICAO;
    protected String PRODUTODESCRICAO;
    protected Float QTDFDS;
    protected String NFPROTHEUSFIL;
    protected String NFPROTHEUSNRO;
    protected String NFPROTHEUSSERIE;





    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Base01";


    public Base01(){

        super(_OBJETO,"BASE01");

        loadColunas();

        InicializaFields();
    }


    public Base01(String VISAO, String NIVEL, String NNIVEL, String DATA, String CLIENTE, String LOJA, String REDE, String CATEGORIA, String MARCA,
                  String PRODUTO, Float QTD, Float VALOR, Float PRCVEN, Float PRCBASE, Float DESCONTO, Float TOTALQTD, Float TOTALVLR,
                  String MOBILENRO, String MOBILETIPO, String PEDIDOPROTHEUSNRO, String PEDIDOPROTHEUSFILIAL, String CLIENTERAZAO,
                  String REDEDESCRI, String CATEGORIADESCRI, String MARCADESCRICAO, String PRODUTODESCRICAO, Float QTDFDS,
                  String NFPROTHEUSFIL,String NFPROTHEUSNRO,String NFPROTHEUSSERIE) {

        super(_OBJETO,"BASE01");

        loadColunas();

        InicializaFields();

        this.VISAO = VISAO;
        this.NIVEL = NIVEL;
        this.NNIVEL = NNIVEL;
        this.DATA = DATA;
        this.CLIENTE = CLIENTE;
        this.LOJA = LOJA;
        this.REDE = REDE;
        this.CATEGORIA = CATEGORIA;
        this.MARCA = MARCA;
        this.PRODUTO = PRODUTO;
        this.QTD = QTD;
        this.VALOR = VALOR;
        this.PRCVEN = PRCVEN;
        this.PRCBASE = PRCBASE;
        this.DESCONTO = DESCONTO;
        this.TOTALQTD = TOTALQTD;
        this.TOTALVLR = TOTALVLR;
        this.MOBILENRO = MOBILENRO;
        this.MOBILETIPO = MOBILETIPO;
        this.PEDIDOPROTHEUSNRO = PEDIDOPROTHEUSNRO;
        this.PEDIDOPROTHEUSFILIAL = PEDIDOPROTHEUSFILIAL;
        this.CLIENTERAZAO = CLIENTERAZAO;
        this.REDEDESCRI = REDEDESCRI;
        this.CATEGORIADESCRI = CATEGORIADESCRI;
        this.MARCADESCRICAO = MARCADESCRICAO;
        this.PRODUTODESCRICAO = PRODUTODESCRICAO;
        this.QTDFDS = QTDFDS;
        this.NFPROTHEUSFIL = NFPROTHEUSFIL;
        this.NFPROTHEUSNRO = NFPROTHEUSNRO;
        this.NFPROTHEUSSERIE = NFPROTHEUSSERIE;
    }


    public String getVISAO() {
        return VISAO;
    }

    public void setVISAO(String VISAO) {
        this.VISAO = VISAO;
    }

    public String getNIVEL() {
        return NIVEL;
    }

    public void setNIVEL(String NIVEL) {
        this.NIVEL = NIVEL;
    }

    public String getNNIVEL() {
        return NNIVEL;
    }

    public void setNNIVEL(String NNIVEL) {
        this.NNIVEL = NNIVEL;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(String CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public String getLOJA() {
        return LOJA;
    }

    public void setLOJA(String LOJA) {
        this.LOJA = LOJA;
    }

    public String getREDE() {
        return REDE;
    }

    public void setREDE(String REDE) {
        this.REDE = REDE;
    }

    public String getCATEGORIA() {
        return CATEGORIA;
    }

    public void setCATEGORIA(String CATEGORIA) {
        this.CATEGORIA = CATEGORIA;
    }

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getPRODUTO() {
        return PRODUTO;
    }

    public void setPRODUTO(String PRODUTO) {
        this.PRODUTO = PRODUTO;
    }

    public Float getQTD() {
        return QTD;
    }

    public void setQTD(Float QTD) {
        this.QTD = QTD;
    }

    public Float getVALOR() {
        return VALOR;
    }

    public void setVALOR(Float VALOR) {
        this.VALOR = VALOR;
    }

    public Float getPRCVEN() {
        return PRCVEN;
    }

    public void setPRCVEN(Float PRCVEN) {
        this.PRCVEN = PRCVEN;
    }

    public Float getPRCBASE() {
        return PRCBASE;
    }

    public void setPRCBASE(Float PRCBASE) {
        this.PRCBASE = PRCBASE;
    }

    public Float getDESCONTO() {
        return DESCONTO;
    }

    public void setDESCONTO(Float DESCONTO) {
        this.DESCONTO = DESCONTO;
    }

    public Float getTOTALQTD() {
        return TOTALQTD;
    }

    public void setTOTALQTD(Float TOTALQTD) {
        this.TOTALQTD = TOTALQTD;
    }

    public Float getTOTALVLR() {
        return TOTALVLR;
    }

    public void setTOTALVLR(Float TOTALVLR) {
        this.TOTALVLR = TOTALVLR;
    }

    public String getMOBILENRO() {
        return MOBILENRO;
    }

    public void setMOBILENRO(String MOBILENRO) {
        this.MOBILENRO = MOBILENRO;
    }

    public String getMOBILETIPO() {
        return MOBILETIPO;
    }

    public void setMOBILETIPO(String MOBILETIPO) {
        this.MOBILETIPO = MOBILETIPO;
    }

    public String getPEDIDOPROTHEUSNRO() {
        return PEDIDOPROTHEUSNRO;
    }

    public void setPEDIDOPROTHEUSNRO(String PEDIDOPROTHEUSNRO) {
        this.PEDIDOPROTHEUSNRO = PEDIDOPROTHEUSNRO;
    }

    public String getPEDIDOPROTHEUSFILIAL() {
        return PEDIDOPROTHEUSFILIAL;
    }

    public void setPEDIDOPROTHEUSFILIAL(String PEDIDOPROTHEUSFILIAL) {
        this.PEDIDOPROTHEUSFILIAL = PEDIDOPROTHEUSFILIAL;
    }

    public String getCLIENTERAZAO() {
        return CLIENTERAZAO;
    }

    public void setCLIENTERAZAO(String CLIENTERAZAO) {
        this.CLIENTERAZAO = CLIENTERAZAO;
    }

    public String getREDEDESCRI() {
        return REDEDESCRI;
    }

    public void setREDEDESCRI(String REDEDESCRI) {
        this.REDEDESCRI = REDEDESCRI;
    }

    public String getCATEGORIADESCRI() {
        return CATEGORIADESCRI;
    }

    public void setCATEGORIADESCRI(String CATEGORIADESCRI) {
        this.CATEGORIADESCRI = CATEGORIADESCRI;
    }

    public String getMARCADESCRICAO() {
        return MARCADESCRICAO;
    }

    public void setMARCADESCRICAO(String MARCADESCRICAO) {
        this.MARCADESCRICAO = MARCADESCRICAO;
    }

    public String getPRODUTODESCRICAO() {
        return PRODUTODESCRICAO;
    }

    public void setPRODUTODESCRICAO(String PRODUTODESCRICAO) {
        this.PRODUTODESCRICAO = PRODUTODESCRICAO;
    }

    public Float getQTDFDS() {
        return QTDFDS;
    }

    public void setQTDFDS(Float QTDFDS) {
        this.QTDFDS = QTDFDS;
    }

    public String getNFPROTHEUSFIL() {
        return NFPROTHEUSFIL;
    }

    public void setNFPROTHEUSFIL(String NFPROTHEUSFIL) {
        this.NFPROTHEUSFIL = NFPROTHEUSFIL;
    }

    public String getNFPROTHEUSNRO() {
        return NFPROTHEUSNRO;
    }

    public void setNFPROTHEUSNRO(String NFPROTHEUSNRO) {
        this.NFPROTHEUSNRO = NFPROTHEUSNRO;
    }

    public String getNFPROTHEUSSERIE() {
        return NFPROTHEUSSERIE;
    }

    public void setNFPROTHEUSSERIE(String NFPROTHEUSSERIE) {
        this.NFPROTHEUSSERIE = NFPROTHEUSSERIE;
    }

    @Override
    public void loadColunas() {
        _colunas = new ArrayList<String>();

        _colunas.add("VISAO");
        _colunas.add("NIVEL");
        _colunas.add("NNIVEL");
        _colunas.add("DATA");
        _colunas.add("CLIENTE");
        _colunas.add("LOJA");
        _colunas.add("REDE");
        _colunas.add("CATEGORIA");
        _colunas.add("MARCA");
        _colunas.add("PRODUTO");
        _colunas.add("QTD");
        _colunas.add("VALOR");
        _colunas.add("PRCVEN");
        _colunas.add("PRCBASE");
        _colunas.add("DESCONTO");
        _colunas.add("TOTALQTD");
        _colunas.add("TOTALVLR");
        _colunas.add("MOBILENRO");
        _colunas.add("MOBILETIPO");
        _colunas.add("PEDIDOPROTHEUSNRO");
        _colunas.add("PEDIDOPROTHEUSFILIAL");
        _colunas.add("CLIENTERAZAO");
        _colunas.add("REDEDESCRI");
        _colunas.add("CATEGORIADESCRI");
        _colunas.add("MARCADESCRICAO");
        _colunas.add("PRODUTODESCRICAO");
        _colunas.add("QTDFDS");
        _colunas.add("NFPROTHEUSFIL");
        _colunas.add("NFPROTHEUSNRO");
        _colunas.add("NFPROTHEUSSERIE");


    }
}
