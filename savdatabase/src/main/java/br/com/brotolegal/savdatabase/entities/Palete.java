package br.com.brotolegal.savdatabase.entities;

public class Palete{

    private String grupo;
    private String descricao;
    private String unidade;
    private String unid;

    public Palete(String grupo, String descricao, String unidade, String unid) {
        this.grupo = grupo;
        this.descricao = descricao;
        this.unidade = unidade;
        this.unid    = unid;
    }


    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }
}
