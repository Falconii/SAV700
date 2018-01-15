package br.com.brotolegal.savdatabase.eventbus;

import br.com.brotolegal.savdatabase.entities.Config;

/**
 * Created by Falconi on 19/09/2016.
 */
public class NotificationConexao {

    private String ERRO;
    private String MSGERRO;
    private String STATUS;
    private Config CONFIG;

    public NotificationConexao(String ERRO, String MSGERRO, String STATUS, Config CONFIG) {
        this.ERRO = ERRO;
        this.MSGERRO = MSGERRO;
        this.STATUS = STATUS;
        this.CONFIG = CONFIG;
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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public Config getCONFIG() {
        return CONFIG;
    }

    public void setCONFIG(Config CONFIG) {
        this.CONFIG = CONFIG;
    }
}