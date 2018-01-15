package br.com.brotolegal.savdatabase.util;

/**
 * Created by Falconi on 29/05/2017.
 */

public class Filtro_Cliente {

    private Boolean ativo;
    private String  tipo;
    private String  codigo;
    private String  loja;
    private String  descricao;


    public Filtro_Cliente(Boolean ativo, String tipo, String codigo, String loja, String descricao) {

        this.ativo     = ativo;
        this.tipo      = tipo;
        this.codigo    = codigo;
        this.loja      = loja;
        this.descricao = descricao;

    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {

        if (!ativo){

             this.setCodigo("");
             this.setLoja("");
             this.setDescricao("");

        }

        this.ativo = ativo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
