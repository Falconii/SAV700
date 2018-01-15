package br.com.brotolegal.savdatabase.entities;

/**
 * Created by Falconi on 30/11/2015.
 */
public class Perfil {

    private String codigo;
    private String texto1;
    private String texto2;
    private Boolean processar;


    public Perfil(String codigo, String texto1, String texto2, Boolean processar) {
        this.codigo = codigo;
        this.texto1 = texto1;
        this.texto2 = texto2;
        this.processar = processar;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTexto1() {
        return texto1;
    }

    public void setTexto1(String texto1) {
        this.texto1 = texto1;
    }

    public String getTexto2() {
        return texto2;
    }

    public void setTexto2(String texto2) {
        this.texto2 = texto2;
    }

    public Boolean getProcessar() {
        return processar;
    }

    public void setProcessar(Boolean processar) {
        this.processar = processar;
    }
}
