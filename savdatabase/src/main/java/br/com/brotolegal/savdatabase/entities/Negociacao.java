package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 19/07/2017.
 */

public class Negociacao extends ObjRegister {

    protected String  CODIGO;
    protected String  STATUS;
    protected String  EMISSAO;
    protected String  CLIENTE;
    protected String  LOJA;
    protected String  REDE;
    protected String  TABPRECO;
    protected String  CONDPAGTO;
    protected Float   QTDFARDOS;
    protected Float   SLDFARDOS;
    protected Float   TOTAPROV;
    protected Float   SLDAPRV;
    protected Integer ENTREGAS;
    protected String  DTHOTRANS;



    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Negociacao";

    protected String _ClienteRazao;
    protected String _Rede;
    protected String _TabPreco;
    protected String _CondPagto;
    protected Integer _SaldoEtregas;

    public Negociacao() {

        super(_OBJETO,"NEGOCIACAO");

        loadColunas();

        InicializaFields();

    }

    public Negociacao(String CODIGO, String STATUS, String EMISSAO, String CLIENTE, String LOJA, String REDE, String TABPRECO, String CONDPAGTO, Float QTDFARDOS, Float SLDFARDOS, Float TOTAPROV, Float SLDAPRV, Integer ENTREGAS, String DTHOTRANS) {

        super(_OBJETO,"NEGOCIACAO");

        loadColunas();

        InicializaFields();

        this.CODIGO = CODIGO;
        this.STATUS = STATUS;
        this.EMISSAO = EMISSAO;
        this.CLIENTE = CLIENTE;
        this.LOJA = LOJA;
        this.REDE = REDE;
        this.TABPRECO = TABPRECO;
        this.CONDPAGTO = CONDPAGTO;
        this.QTDFARDOS = QTDFARDOS;
        this.SLDFARDOS = SLDFARDOS;
        this.TOTAPROV = TOTAPROV;
        this.SLDAPRV = SLDAPRV;
        this.ENTREGAS = ENTREGAS;
        this.DTHOTRANS = DTHOTRANS;

    }

    public void Complemento(String _ClienteRazao, String _Rede, String _TabPreco, String _CondPagto, Integer _SaldoEtregas) {

        this._ClienteRazao = _ClienteRazao;
        this._Rede         = _Rede;
        this._TabPreco     = _TabPreco;
        this._CondPagto    = _CondPagto;
        this._SaldoEtregas = _SaldoEtregas;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getEMISSAO() {
        return EMISSAO;
    }

    public void setEMISSAO(String EMISSAO) {
        this.EMISSAO = EMISSAO;
    }

    public String getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(String CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public String getLOJA() {
        return LOJA;
    }

    public void setLOJA(String LOJA) {
        this.LOJA = LOJA;
    }

    public String getREDE() {
        return REDE;
    }

    public void setREDE(String REDE) {
        this.REDE = REDE;
    }

    public String getTABPRECO() {
        return TABPRECO;
    }

    public void setTABPRECO(String TABPRECO) {
        this.TABPRECO = TABPRECO;
    }

    public String getCONDPAGTO() {
        return CONDPAGTO;
    }

    public void setCONDPAGTO(String CONDPAGTO) {
        this.CONDPAGTO = CONDPAGTO;
    }

    public Float getQTDFARDOS() {
        return QTDFARDOS;
    }

    public void setQTDFARDOS(Float QTDFARDOS) {
        this.QTDFARDOS = QTDFARDOS;
    }

    public Float getSLDFARDOS() {
        return SLDFARDOS;
    }

    public void setSLDFARDOS(Float SLDFARDOS) {
        this.SLDFARDOS = SLDFARDOS;
    }

    public Float getTOTAPROV() {
        return TOTAPROV;
    }

    public void setTOTAPROV(Float TOTAPROV) {
        this.TOTAPROV = TOTAPROV;
    }

    public Float getSLDAPRV() {
        return SLDAPRV;
    }

    public void setSLDAPRV(Float SLDAPRV) {
        this.SLDAPRV = SLDAPRV;
    }

    public Integer getENTREGAS() {
        return ENTREGAS;
    }

    public void setENTREGAS(Integer ENTREGAS) {
        this.ENTREGAS = ENTREGAS;
    }

    public String getDTHOTRANS() {
        return DTHOTRANS;
    }

    public void setDTHOTRANS(String DTHOTRANS) {
        this.DTHOTRANS = DTHOTRANS;
    }

    public String get_ClienteRazao() {
        return _ClienteRazao;
    }

    public void set_ClienteRazao(String _ClienteRazao) {
        this._ClienteRazao = _ClienteRazao;
    }

    public String get_Rede() {
        return _Rede;
    }

    public void set_Rede(String _Rede) {
        this._Rede = _Rede;
    }

    public String get_TabPreco() {
        return _TabPreco;
    }

    public void set_TabPreco(String _TabPreco) {
        this._TabPreco = _TabPreco;
    }

    public String get_CondPagto() {
        return _CondPagto;
    }

    public void set_CondPagto(String _CondPagto) {
        this._CondPagto = _CondPagto;
    }

    public Integer get_SaldoEtregas() {
        return _SaldoEtregas;
    }

    public void set_SaldoEtregas(Integer _SaldoEtregas) {
        this._SaldoEtregas = _SaldoEtregas;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<>();

        _colunas.add("CODIGO");
        _colunas.add("STATUS");
        _colunas.add("EMISSAO");
        _colunas.add("CLIENTE");
        _colunas.add("LOJA");
        _colunas.add("REDE");
        _colunas.add("TABPRECO");
        _colunas.add("CONDPAGTO");
        _colunas.add("QTDFARDOS");
        _colunas.add("SLDFARDOS");
        _colunas.add("TOTAPROV");
        _colunas.add("SLDAPRV");
        _colunas.add("ENTREGAS");
        _colunas.add("DTHOTRANS");


    }
}
