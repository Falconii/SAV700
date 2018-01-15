package br.com.brotolegal.savdatabase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.brotolegal.savdatabase.app.App;

/**
 * Created by Falconi on 29/05/2017.
 */

public class Filtro_Data {

    private Boolean ativo;
    private String  titulo;
    private String  datainicial;
    private String  datafinal;

    public Filtro_Data() {

        ativo       = false;
        titulo      = "Filtro De Datas.";
        datainicial = "";
        datafinal   = "";

    }

    public Filtro_Data(Boolean ativo, String titulo, String datainicial, String datafinal) {
        this.ativo = ativo;
        this.titulo = titulo;
        this.datainicial = datainicial;
        this.datafinal = datafinal;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {

        this.ativo = ativo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(String datainicial) {
        this.datainicial = datainicial;
    }

    public String getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(String datafinal) {
        this.datafinal = datafinal;
    }
}
