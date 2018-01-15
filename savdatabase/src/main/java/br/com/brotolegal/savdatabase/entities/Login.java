package br.com.brotolegal.savdatabase.entities;

/**
 * Created by Falconi on 20/10/2015.
 */
public class Login {

    private String codigo;
    private String nome;

    public Login() {
    }


    public Login(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public void setLogin(Login login){

        this.codigo = login.codigo;
        this.nome   = login.nome;

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
}
