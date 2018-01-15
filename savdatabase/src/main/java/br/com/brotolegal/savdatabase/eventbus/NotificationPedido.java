package br.com.brotolegal.savdatabase.eventbus;

/**
 * Created by Falconi on 19/09/2016.
 */
public class NotificationPedido {

    private String ERRO;
    private String MSGERRO;
    private String CLIENTE;
    private String LOJA;

    public NotificationPedido(String ERRO, String MSGERRO, String CLIENTE, String LOJA) {
        this.ERRO = ERRO;
        this.MSGERRO = MSGERRO;
        this.CLIENTE = CLIENTE;
        this.LOJA = LOJA;
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

    public String getLOJA() {
        return LOJA;
    }

    public void setLOJA(String LOJA) {
        this.LOJA = LOJA;
    }
}
