package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 09/09/2016.
 */
public class Notificacao extends ObjRegister {


    protected Integer  SEQ;
    protected String   CODIGO;
    protected String   DESCRICAO;
    protected String   HORA;
    protected String   STATUS;
    protected String   MENSAGEM;
    protected String   DOC;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Notificacao";

    public Notificacao(){

        super(_OBJETO,"NOTIFICACAO");

        loadColunas();

        InicializaFields();
    }

    public Notificacao(Integer SEQ, String CODIGO, String DESCRICAO, String HORA, String STATUS, String MENSAGEM, String DOC) {

        super(_OBJETO,"NOTIFICACAO");

        loadColunas();

        InicializaFields();


        this.SEQ = SEQ;
        this.CODIGO = CODIGO;
        this.DESCRICAO = DESCRICAO;
        this.HORA = HORA;
        this.STATUS = STATUS;
        this.MENSAGEM = MENSAGEM;
        this.DOC      = DOC;
    }

    public Integer getSEQ() {
        return SEQ;
    }

    public void setSEQ(Integer SEQ) {
        this.SEQ = SEQ;
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

    public String getHORA() {
        return HORA;
    }

    public void setHORA(String HORA) {
        this.HORA = HORA;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getMENSAGEM() {
        return MENSAGEM;
    }

    public void setMENSAGEM(String MENSAGEM) {
        this.MENSAGEM = MENSAGEM;
    }

    public String get_Data(){

        return  this.HORA.substring(0,9);

    }

    public String get_Hora(){

        return  this.HORA.substring(9);

    }

    public String get_Pedido(){

        int i = this.DESCRICAO.indexOf("!");

        if (i>=0) {

            return  this.DESCRICAO.substring(0,i+1);

        } else {

            return "";

        }

    }

    public String getDOC() {
        return DOC;
    }

    public void setDOC(String DOC) {
        this.DOC = DOC;
    }

    public String get_Cliente(){

        int i = this.DESCRICAO.indexOf("!");

        if (i>=0) {

            return  this.DESCRICAO.substring(i+2);

        } else {

            return "";

        }

    }


    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();
        _colunas.add("SEQ");
        _colunas.add("CODIGO");
        _colunas.add("DESCRICAO");
        _colunas.add("HORA");
        _colunas.add("STATUS");
        _colunas.add("MENSAGEM");
        _colunas.add("DOC");

    }
}
