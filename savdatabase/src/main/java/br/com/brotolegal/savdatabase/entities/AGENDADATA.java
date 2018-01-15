package br.com.brotolegal.savdatabase.entities;

/**
 * Created by Falconi on 06/10/2016.
 */
public class AGENDADATA {


    private String data;
    private Integer contador;


    public AGENDADATA(String data, Integer contador) {

        this.data = data;
        if (contador == null){

            contador = 0;

        }

        this.contador = contador;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }
}
