package br.com.brotolegal.sav700.VerificaWeb;

/**
 * Created by Falconi on 24/03/2017.
 */

public class Parametros {

    private  int figura;
    private  String linha01;
    private  String linha02;

    public Parametros(int figura, String linha01, String linha02) {
        this.figura  = figura;
        this.linha01 = linha01;
        this.linha02 = linha02;
    }

    public int getFigura() {

        return figura;
    }

    public void setFigura(int figura) {
        this.figura = figura;
    }

    public String getLinha01() {
        return linha01;
    }

    public void setLinha01(String linha01) {
        this.linha01 = linha01;
    }

    public String getLinha02() {
        return linha02;
    }

    public void setLinha02(String linha02) {
        this.linha02 = linha02;
    }


}


