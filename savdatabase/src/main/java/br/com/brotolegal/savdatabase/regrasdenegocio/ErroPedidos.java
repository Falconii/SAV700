package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 29/11/2017.
 */

public class ErroPedidos {

    private String errorCode;
    private String local;
    private String mensagem;
    private String item;
    private String codprod;
    private String descprod;
    private String unid;
    private Float  qtd;
    private Float  precven;
    private Float  total;
    private String status;



    public ErroPedidos() {

        this.errorCode  = "";
        this.local      = "";
        this.mensagem   = "";
        this.item       = "";
        this.codprod    = "";
        this.descprod   = "";
        this.unid       = "";
        this.qtd        = 0f;
        this.precven    = 0f;
        this.total      = 0f;
        this.status     = "";

    }

    public ErroPedidos(String errorCode, String local, String mensagem, String item, String codprod, String descprod, String unid, Float qtd, Float precven, Float total, String status) {
        this.errorCode = errorCode;
        this.local = local;
        this.mensagem = mensagem;
        this.item = item;
        this.codprod = codprod;
        this.descprod = descprod;
        this.unid = unid;
        this.qtd = qtd;
        this.precven = precven;
        this.total = total;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCodprod() {
        return codprod;
    }

    public void setCodprod(String codprod) {
        this.codprod = codprod;
    }

    public String getDescprod() {
        return descprod;
    }

    public void setDescprod(String descprod) {
        this.descprod = descprod;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public Float getQtd() {
        return qtd;
    }

    public void setQtd(Float qtd) {
        this.qtd = qtd;
    }

    public Float getPrecven() {
        return precven;
    }

    public void setPrecven(Float precven) {
        this.precven = precven;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
