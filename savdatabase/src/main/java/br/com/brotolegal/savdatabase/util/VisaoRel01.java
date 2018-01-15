package br.com.brotolegal.savdatabase.util;

/**
 * Created by Falconi on 31/05/2017.
 */

public class VisaoRel01 {

    private String VISAO;
    private String NIVEL;
    private String NNIVEL;
    private String DATA;
    private String CLIENTE;
    private String LOJA;
    private String REDE;
    private String CATEGORIA;
    private String MARCA;
    private String PRODUTO;
    private Float  QTD;
    private Float  VALOR;
    private Float  PRCVEN;
    private Float  PRCBASE;
    private Float  DESCONTO;
    private Float  TOTALQTD;
    private Float  TOTALVLR;
    private String MOBILENRO;
    private String MOBILETIPO;
    private String PEDIDOPROTHEUSNRO;
    private String PEDIDOPROTHEUSFILIAL;
    private String CLIENTERAZAO;
    private String REDEDESCRI;
    private String CATEGORIADESCRI;
    private String MARCADESCRICAO;
    private String PRODUTODESCRICAO;
    private Float  QTDFDS;
    private String NFPROTHEUSFIL;
    private String NFPROTHEUSNRO;
    private String NFPROTHEUSSERIE;

    public VisaoRel01(String VISAO, String NIVEL, String NNIVEL, String DATA, String CLIENTE, String LOJA, String REDE,
                      String CATEGORIA, String MARCA, String PRODUTO, Float QTD, Float VALOR, Float PRCVEN, Float PRCBASE,
                      Float DESCONTO, Float TOTALQTD, Float TOTALVLR, String MOBILENRO, String MOBILETIPO, String PEDIDOPROTHEUSNRO,
                      String PEDIDOPROTHEUSFILIAL, String CLIENTERAZAO, String REDEDESCRI, String CATEGORIADESCRI, String MARCADESCRICAO,
                      String PRODUTODESCRICAO, Float QTDFDS, String NFPROTHEUSFIL, String NFPROTHEUSNRO, String NFPROTHEUSSERIE) {
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

    public VisaoRel01(String visao) {
        this.VISAO                  = visao;
        this.NIVEL                  = "";
        this.NNIVEL                 = "";
        this.DATA                   = "";
        this.CLIENTE                = "";
        this.LOJA                   = "";
        this.REDE                   = "";
        this.CATEGORIA              = "";
        this.MARCA                  = "";
        this.PRODUTO                = "";
        this.QTD                    = 0f;
        this.VALOR                  = 0f;
        this.PRCVEN                 = 0f;
        this.PRCBASE                = 0f;
        this.DESCONTO               = 0f;
        this.TOTALQTD               = 0f;
        this.TOTALVLR               = 0f;
        this.MOBILENRO              = "";
        this.MOBILETIPO             = "";
        this.PEDIDOPROTHEUSNRO      = "";
        this.PEDIDOPROTHEUSFILIAL   = "";
        this.CLIENTERAZAO           = "";
        this.REDEDESCRI             = "";
        this.CATEGORIADESCRI        = "";
        this.MARCADESCRICAO         = "";
        this.PRODUTODESCRICAO       = "";
        this.QTDFDS                 = 0f;
        this.NFPROTHEUSFIL          = "";
        this.NFPROTHEUSNRO          = "";
        this.NFPROTHEUSSERIE         = "";
    }

    public String getChave(int indice){

        switch (indice){

            case 0: return getDATA();

            case 1: return getCLIENTE() + getLOJA();

            case 2: return getCATEGORIA();

            case 3: return getMARCA();

            case 4: return getPRODUTO();

        }

        return "";

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
}
