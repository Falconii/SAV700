package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 06/04/2018.
 */

public class AgeByData {

    private String  data;
    private String  situacao;
    private Integer contador;


    public AgeByData(String data, String situacao, Integer contador) {
        this.data = data;
        this.situacao = situacao;
        this.contador = contador;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }
}
