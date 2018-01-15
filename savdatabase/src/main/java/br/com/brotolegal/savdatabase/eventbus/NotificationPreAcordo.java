package br.com.brotolegal.savdatabase.eventbus;

public class NotificationPreAcordo {

    private String ERRO;
    private String MSGERRO;
    private String ACORDO;
    private String STATUS;

    public NotificationPreAcordo(String ERRO, String MSGERRO, String ACORDO, String STATUS) {
        this.ERRO = ERRO;
        this.MSGERRO = MSGERRO;
        this.ACORDO = ACORDO;
        this.STATUS = STATUS;
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

    public String getACORDO() {
        return ACORDO;
    }

    public void setACORDO(String ACORDO) {
        this.ACORDO = ACORDO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
