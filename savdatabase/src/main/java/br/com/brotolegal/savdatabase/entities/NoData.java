package br.com.brotolegal.savdatabase.entities;

/**
 * Created by Falconi on 26/11/2015.
 */
public class NoData {

    private String mensagem;


    public NoData(String mensagem) {

        this.mensagem = mensagem;

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
