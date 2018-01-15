package br.com.brotolegal.sav700.Campanha.Entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Falconi on 21/07/2017.
 */

public class ParametrosCampanha {

    private String codigo;
    private String descricao;
    private int    selecionado;

    private List<PeriodoCampanha> lsPeriodos;

    public ParametrosCampanha(String codigo, String descricao) {

        this.codigo      = codigo;
        this.descricao   = descricao;
        this.selecionado = -1;
        this.lsPeriodos  = new ArrayList<>();

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(int selecionado) {
        this.selecionado = selecionado;
    }

    public List<PeriodoCampanha> getLsPeriodos() {
        return lsPeriodos;
    }

    public void setLsPeriodos(List<PeriodoCampanha> lsPeriodos) {
        this.lsPeriodos = lsPeriodos;
    }

    public void addPeriodo(PeriodoCampanha periodo){

        this.lsPeriodos.add(periodo);

    }

    public PeriodoCampanha getPeriodo(int index){

        PeriodoCampanha retorno = null;

        if (index >= 0 && index < lsPeriodos.size()) retorno = lsPeriodos.get(index);

        return retorno;

    }

    public List<String[]> getOpcoes(){

        List<String[]> retorno = new ArrayList<>();

        for(int x = 0; x < lsPeriodos.size();x++) {

            retorno.add(new String[]{String.valueOf(x), getPeriodoExtenso(x)});

        }
        return retorno;



    }

    public PeriodoCampanha getPeriodoAtivo(){

        PeriodoCampanha periodo = null;

        if (selecionado >= 0){

            periodo = lsPeriodos.get(this.selecionado);

        }

        return periodo;
    }

    public String getPeriodoAtivoExtenso(){

        String retorno = getPeriodoExtenso(selecionado);

        return retorno;

    }

    public String getPeriodoExtenso(int index){

        String retorno = "";

        if (index >= 0 && index < lsPeriodos.size()){

            retorno = extenso(index);

        }

        return retorno;
    }

    private String extenso(int index){

        String retorno = "";
        String meses   = "";

        try {

            DateFormat df;
            df             = new SimpleDateFormat("dd/MM/yyyy");
            Date DataBase;
            DateFormat df2 = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
            DateFormat df3 = new SimpleDateFormat("yyyy", new Locale("pt", "BR"));

            String data1   = "01" + "/" + lsPeriodos.get(index).getMesInicial() + "/" + lsPeriodos.get(index).getAnoInicial();
            DataBase       = df.parse(data1);
            meses         += df2.format(DataBase);


            String data2   = "01" + "/" + lsPeriodos.get(index).getMesFinal() + "/" + lsPeriodos.get(index).getAnoFinal();
            DataBase       = df.parse(data2);
            meses         += "/"+df2.format(DataBase);

            retorno        = String.valueOf(index+1)+"º Bimestre - "+meses.toUpperCase( new Locale("pt", "BR"))+" "+df3.format(DataBase);

        } catch (Exception e){

            retorno = "ERRO !!!";

        }

        return retorno;
    }
}
