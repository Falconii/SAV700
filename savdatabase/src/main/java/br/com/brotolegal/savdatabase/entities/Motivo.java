package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 07/10/2016.
 */
public class Motivo extends ObjRegister {

    protected String TIPO;
    protected String CODIGO;
    protected String DESCRICAO;


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Motivo";


    public Motivo(){

        super(_OBJETO,"MOTIVO");

        loadColunas();

        InicializaFields();
    }

    public Motivo(String TIPO,String CODIGO, String DESCRICAO) {

        super(_OBJETO,"MOTIVO");

        loadColunas();

        InicializaFields();

        this.CODIGO    = CODIGO;
        this.DESCRICAO = DESCRICAO;
        this.TIPO      = TIPO;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }



    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();
        _colunas.add("TIPO");
        _colunas.add("CODIGO");
        _colunas.add("DESCRICAO");




    }
}
