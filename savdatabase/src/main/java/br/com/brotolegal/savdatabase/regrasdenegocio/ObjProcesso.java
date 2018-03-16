package br.com.brotolegal.savdatabase.regrasdenegocio;

/**
 * Created by Falconi on 16/03/2018.
 */
public class ObjProcesso{

    private String Titulo;
    private String Estagio;
    private String Obs;

    public ObjProcesso(String titulo, String estagio, String obs) {
        Titulo = titulo;
        Estagio = estagio;
        Obs = obs;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getEstagio() {
        return Estagio;
    }

    public void setEstagio(String estagio) {
        Estagio = estagio;
    }

    public String getObs() {
        return Obs;
    }

    public void setObs(String obs) {
        Obs = obs;
    }
}

