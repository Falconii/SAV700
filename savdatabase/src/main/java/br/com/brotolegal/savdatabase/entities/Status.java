package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 13/07/2016.
 */
public class Status extends ObjRegister {

    protected String APPBLOCK;
    protected String PEDIDO;
    protected String PEDDATA;
    protected String PEDHORA;
    protected String CARGA;
    protected String CARDATA;
    protected String CARHORA;
    protected String ULTATUAL;
    protected String LOGADO;
    protected String USERLOG;
    protected String HORALOG;
    protected String CALCULO;



    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Status";


    public Status() {

        super(_OBJETO, "STATUS");

        loadColunas();

        InicializaFields();
    }

    public Status(String APPBLOCK, String PEDIDO, String PEDDATA, String PEDHORA, String CARGA, String CARDATA, String CARHORA, String ULTATUAL, String LOGADO, String USERLOG, String HORALOG, String CALCULO) {

        super(_OBJETO, "STATUS");

        loadColunas();

        InicializaFields();

        this.APPBLOCK = APPBLOCK;
        this.PEDIDO = PEDIDO;
        this.PEDDATA = PEDDATA;
        this.PEDHORA = PEDHORA;
        this.CARGA = CARGA;
        this.CARDATA = CARDATA;
        this.CARHORA = CARHORA;
        this.ULTATUAL = ULTATUAL;
        this.LOGADO = LOGADO;
        this.USERLOG = USERLOG;
        this.HORALOG = HORALOG;
        this.CALCULO = CALCULO;
    }

    public String getAPPBLOCK() {
        return APPBLOCK;
    }

    public void setAPPBLOCK(String APPBLOCK) {
        this.APPBLOCK = APPBLOCK;
    }

    public String getPEDIDO() {
        return PEDIDO;
    }

    public void setPEDIDO(String PEDIDO) {
        this.PEDIDO = PEDIDO;
    }

    public String getPEDDATA() {
        return PEDDATA;
    }

    public void setPEDDATA(String PEDDATA) {
        this.PEDDATA = PEDDATA;
    }

    public String getPEDHORA() {
        return PEDHORA;
    }

    public void setPEDHORA(String PEDHORA) {
        this.PEDHORA = PEDHORA;
    }

    public String getCARGA() {
        return CARGA;
    }

    public void setCARGA(String CARGA) {
        this.CARGA = CARGA;
    }

    public String getCARDATA() {
        return CARDATA;
    }

    public void setCARDATA(String CARDATA) {
        this.CARDATA = CARDATA;
    }

    public String getCARHORA() {
        return CARHORA;
    }

    public void setCARHORA(String CARHORA) {
        this.CARHORA = CARHORA;
    }

    public String getULTATUAL() {
        return ULTATUAL;
    }

    public void setULTATUAL(String ULTATUAL) {
        this.ULTATUAL = ULTATUAL;
    }

    public String getLOGADO() {
        return LOGADO;
    }

    public void setLOGADO(String LOGADO) {
        this.LOGADO = LOGADO;
    }

    public String getUSERLOG() {
        return USERLOG;
    }

    public void setUSERLOG(String USERLOG) {
        this.USERLOG = USERLOG;
    }

    public String getHORALOG() {
        return HORALOG;
    }

    public void setHORALOG(String HORALOG) {
        this.HORALOG = HORALOG;
    }

    public String getCALCULO() {
        return CALCULO;
    }

    public void setCALCULO(String CALCULO) {
        this.CALCULO = CALCULO;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("APPBLOCK");
        _colunas.add("PEDIDO");
        _colunas.add("PEDDATA");
        _colunas.add("PEDHORA");
        _colunas.add("CARGA");
        _colunas.add("CARDATA");
        _colunas.add("CARHORA");
        _colunas.add("ULTATUAL");
        _colunas.add("LOGADO");
        _colunas.add("USERLOG");
        _colunas.add("HORALOG");
        _colunas.add("CALCULO");


    }

    public String getCargaStatus() {

        String retorno = "";

        if (this.getCARGA().equals("N")) {

            retorno = "CARGA Liberada !!";

        } else {

            retorno = "CARGA Bloqueada !!";

        }

        return retorno;
    }


    public String getPedidoStatus() {

        String retorno = "";

        if (this.getPEDIDO().equals("N")) {

            retorno = "PEDIDO Liberado !!";

        } else {

            retorno = "PEDIDO Bloqueado !!";

        }

        return retorno;
    }
}