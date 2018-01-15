package br.com.brotolegal.sav700.Campanha.Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falconi on 21/07/2017.
 */

public class PeriodoCampanha {

    private String mesInicial;
    private String anoInicial;


    private String mesFinal;
    private String anoFinal;

    public PeriodoCampanha(String mesInicial, String anoInicial, String mesFinal, String anoFinal) {
        this.mesInicial = mesInicial;
        this.anoInicial = anoInicial;
        this.mesFinal = mesFinal;
        this.anoFinal = anoFinal;
    }

    public String getMesInicial() {
        return mesInicial;
    }

    public void setMesInicial(String mesInicial) {
        this.mesInicial = mesInicial;
    }

    public String getAnoInicial() {
        return anoInicial;
    }

    public void setAnoInicial(String anoInicial) {
        this.anoInicial = anoInicial;
    }

    public String getMesFinal() {
        return mesFinal;
    }

    public void setMesFinal(String mesFinal) {
        this.mesFinal = mesFinal;
    }

    public String getAnoFinal() {
        return anoFinal;
    }

    public void setAnoFinal(String anoFinal) {
        this.anoFinal = anoFinal;
    }

    public String getPeriodoInicial(){

        return this.anoInicial+this.getMesInicial();
    }

    public String getPeriodoFinal(){

        return this.anoFinal+this.getMesFinal();
    }


}
