package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 07/08/2017.
 */

public class ParametroCota {

    private String cliente      ;
    private String loja         ;
    private String rede         ;
    private String canal        ;
    private String regiao       ;
    private String smarca       ;
    private String produto      ;


    public ParametroCota() {

        this.cliente   = "";
        this.loja      = "";
        this.rede      = "";
        this.canal     = "";
        this.regiao    = "";
        this.smarca    = "";
        this.produto   = "";
        
    }

    public ParametroCota(String cliente, String loja, String rede, String canal, String regiao, String smarca, String produto) {
        this.cliente = cliente;
        this.loja = loja;
        this.rede = rede;
        this.canal = canal;
        this.regiao = regiao;
        this.smarca = smarca;
        this.produto = produto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getRede() {
        return rede;
    }

    public void setRede(String rede) {
        this.rede = rede;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getSmarca() {
        return smarca;
    }

    public void setSmarca(String smarca) {
        this.smarca = smarca;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }
}
