package br.com.brotolegal.savdatabase.eventbus;

/**
 * Created by Falconi on 19/09/2016.
 */
public class NotificationPedidoGeral {

    private String ERRO;
    private String MSGERRO;
    private String CLIENTE;
    private String Loja;

    public NotificationPedidoGeral(String ERRO, String MSGERRO, String CLIENTE, String loja) {
        this.ERRO = ERRO;
        this.MSGERRO = MSGERRO;
        this.CLIENTE = CLIENTE;
        Loja = loja;
    }

    public String getERRO() {
        return ERRO;
    }

    public void setERRO(String ERRO) {
        this.ERRO = ERRO;
    }

    public String getMSGERRO() {
        return MSGERRO;
    }

    public void setMSGERRO(String MSGERRO) {
        this.MSGERRO = MSGERRO;
    }

    public String getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(String CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public String getLoja() {
        return Loja;
    }

    public void setLoja(String loja) {
        Loja = loja;
    }
}
