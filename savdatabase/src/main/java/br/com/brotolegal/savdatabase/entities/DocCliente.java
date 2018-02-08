package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 04/07/2016.
 */
public class DocCliente extends ObjRegister {

    protected String CODIGO;
    protected String PATCH;
    protected String DESCRICAO;
    protected String NOME;
    protected String STATUS;
    protected String TIPO;




    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.DocCliente";


    public DocCliente(){

        super(_OBJETO,"DOCCLIENTE");

        loadColunas();

        InicializaFields();
    }

    public DocCliente(String CODIGO, String PATCH, String DESCRICAO, String NOME, String STATUS, String TIPO) {

        super(_OBJETO,"DOCCLIENTE");

        loadColunas();

        InicializaFields();

        this.CODIGO = CODIGO;
        this.PATCH = PATCH;
        this.DESCRICAO = DESCRICAO;
        this.NOME = NOME;
        this.STATUS = STATUS;
        this.TIPO = TIPO;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getPATCH() {
        return PATCH;
    }

    public void setPATCH(String PATCH) {
        this.PATCH = PATCH;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("CODIGO");
        _colunas.add("PATCH");
        _colunas.add("DESCRICAO");
        _colunas.add("NOME");
        _colunas.add("STATUS");
        _colunas.add("TIPO");


    }
}
