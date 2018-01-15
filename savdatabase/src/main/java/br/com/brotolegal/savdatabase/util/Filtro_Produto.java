package br.com.brotolegal.savdatabase.util;

/**
 * Created by Falconi on 29/05/2017.
 */

public class Filtro_Produto {

    private Boolean ativo;
    private String  descricao;
    private String  codigoInicial;
    private String  descriInicial;
    private String  codigofinal;
    private String  descrifinal;

    public Filtro_Produto(Boolean ativo, String descricao, String codigoInicial, String descriInicial, String codigofinal, String descrifinal) {
        this.ativo = ativo;
        this.descricao = descricao;
        this.codigoInicial = codigoInicial;
        this.descriInicial = descriInicial;
        this.codigofinal = codigofinal;
        this.descrifinal = descrifinal;
    }


    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoInicial() {
        return codigoInicial;
    }

    public void setCodigoInicial(String codigoInicial) {
        this.codigoInicial = codigoInicial;
    }

    public String getDescriInicial() {
        return descriInicial;
    }

    public void setDescriInicial(String descriInicial) {
        this.descriInicial = descriInicial;
    }

    public String getCodigofinal() {
        return codigofinal;
    }

    public void setCodigofinal(String codigofinal) {
        this.codigofinal = codigofinal;
    }

    public String getDescrifinal() {
        return descrifinal;
    }

    public void setDescrifinal(String descrifinal) {
        this.descrifinal = descrifinal;
    }
}
