package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 02/08/2017.
 */

public class ResumoAgendamento {

    private String  mensagem;
    private Integer qtd;

    public ResumoAgendamento(String mensagem, Integer qtd) {
        this.mensagem = mensagem;
        this.qtd = qtd;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
}
