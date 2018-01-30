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
    protected Integer CONTROLE;
    protected String DESCRICAO;
    protected String CAMINHO;
    protected String STATUS;
    protected String TIPO;




    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.DocCliente";


    public DocCliente(){

        super(_OBJETO,"DOCCLIENTE");

        loadColunas();

        InicializaFields();
    }

    public DocCliente(String CODIGO, Integer CONTROLE, String DESCRICAO, String CAMINHO, String STATUS, String TIPO) {

        super(_OBJETO,"DOCCLIENTE");

        loadColunas();

        InicializaFields();

        this.CODIGO = CODIGO;
        this.CONTROLE = CONTROLE;
        this.DESCRICAO = DESCRICAO;
        this.CAMINHO = CAMINHO;
        this.STATUS = STATUS;
        this.TIPO = TIPO;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public Integer getCONTROLE() {
        return CONTROLE;
    }

    public void setCONTROLE(Integer CONTROLE) {
        this.CONTROLE = CONTROLE;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public String getCAMINHO() {
        return CAMINHO;
    }

    public void setCAMINHO(String CAMINHO) {
        this.CAMINHO = CAMINHO;
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
        _colunas.add("CONTROLE");
        _colunas.add("DESCRICAO");
        _colunas.add("CAMINHO");
        _colunas.add("STATUS");
        _colunas.add("TIPO");

    }
}
