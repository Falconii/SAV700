package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 14/09/2017.
 */

public class ChavePedDistribuicao {

    private String filial;
    private String pedido;
    private String item;
    private Float  prcven;


    public ChavePedDistribuicao(String filial, String pedido, String item, Float prcven) {
        this.filial = filial;
        this.pedido = pedido;
        this.item = item;
        this.prcven = prcven;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Float getPrcven() {
        return prcven;
    }

    public void setPrcven(Float prcven) {
        this.prcven = prcven;
    }
}
