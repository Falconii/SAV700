package br.com.brotolegal.savdatabase.eventbus;

/**
 * Created by Falconi on 19/09/2016.
 */
public class NotificationCarga {

    private String ERRO;
    private String MSGERRO;

    public NotificationCarga(String ERRO, String MSGERRO) {
        this.ERRO = ERRO;
        this.MSGERRO = MSGERRO;
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
}
