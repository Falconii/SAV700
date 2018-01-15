package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 29/09/2017.
 */

public class UsuarioTST {

    private String codigo;
    private String nome;
    private String email;


    public UsuarioTST() {}

    public UsuarioTST(String codigo, String nome, String email) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Nome: " + this.nome;
    }
}
