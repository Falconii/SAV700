package br.com.brotolegal.savdatabase.entities;

/**
 * Created by Falconi on 26/11/2015.
 */
public class NoDataProgress {

    private String mensagem;


    public NoDataProgress(String mensagem) {

        this.mensagem = mensagem;

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
