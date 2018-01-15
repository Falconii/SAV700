package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 15/03/2017.
 */

public class NotaFiscalCab extends ObjRegister {

    protected String FILIAL;
    protected String SERIE;
    protected String NOTAFISCAL;
    protected String TIPODOC;
    protected String CODCLI;
    protected String CODLOJA;
    protected String NOMECLI;
    protected String DTEMISSAO;
    protected String DTENTREGA;
    protected String OBSERVACAO;
    protected Float TOTALNF;
    protected String NUMPEDIDO;
    protected String CHAVE;
    protected String PEDIDOCLIENTE;
    protected String CODTRANSP;
    protected String NOMTRANSP;
    protected String TELTRANSP;
    protected String DTCANHOTO;
    protected String ROMANEIO;
    protected String CONDICAO;

    protected String _CNPJ;
    protected String _IE;
    protected String _CODCIDADE;
    protected String _CIDADE;
    protected String _CODREDE;
    protected String _REDE;
    protected String _DDD;
    protected String _TELEFONE;



    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.NotaFiscalCab";

    protected Boolean View_nota;




    public NotaFiscalCab(){

        super(_OBJETO,"NOTAFISCALCAB");

        loadColunas();

        InicializaFields();

        _CNPJ        = "";
        _IE          = "";
        _CODCIDADE   = "";
        _CIDADE      = "";
        _CODREDE     = "";
        _REDE        = "";
        _TELEFONE    = "";

        View_nota = false;

    }

    public NotaFiscalCab(String FILIAL, String SERIE, String NOTAFISCAL, String TIPODOC, String CODCLI, String CODLOJA, String NOMECLI, String DTEMISSAO, String DTENTREGA, String OBSERVACAO, Float TOTALNF, String NUMPEDIDO, String CHAVE, String PEDIDOCLIENTE, String CODTRANSP, String NOMTRANSP, String TELTRANSP, String DTCANHOTO, String ROMANEIO, String CONDICAO) {

        super(_OBJETO,"NOTAFISCALCAB");

        loadColunas();

        InicializaFields();

        this.FILIAL = FILIAL;
        this.SERIE = SERIE;
        this.NOTAFISCAL = NOTAFISCAL;
        this.TIPODOC = TIPODOC;
        this.CODCLI = CODCLI;
        this.CODLOJA = CODLOJA;
        this.NOMECLI = NOMECLI;
        this.DTEMISSAO = DTEMISSAO;
        this.DTENTREGA = DTENTREGA;
        this.OBSERVACAO = OBSERVACAO;
        this.TOTALNF = TOTALNF;
        this.NUMPEDIDO = NUMPEDIDO;
        this.CHAVE = CHAVE;
        this.PEDIDOCLIENTE = PEDIDOCLIENTE;
        this.CODTRANSP = CODTRANSP;
        this.NOMTRANSP = NOMTRANSP;
        this.TELTRANSP = TELTRANSP;
        this.DTCANHOTO = DTCANHOTO;
        this.ROMANEIO = ROMANEIO;
        this.CONDICAO = CONDICAO;


        _CNPJ        = "";
        _IE          = "";
        _CODCIDADE   = "";
        _CIDADE      = "";
        _CODREDE     = "";
        _REDE        = "";
        _TELEFONE    = "";

        View_nota = false;

    }

    public String getFILIAL() {
        return FILIAL;
    }

    public void setFILIAL(String FILIAL) {
        this.FILIAL = FILIAL;
    }

    public String getSERIE() {
        return SERIE;
    }

    public void setSERIE(String SERIE) {
        this.SERIE = SERIE;
    }

    public String getNOTAFISCAL() {
        return NOTAFISCAL;
    }

    public void setNOTAFISCAL(String NOTAFISCAL) {
        this.NOTAFISCAL = NOTAFISCAL;
    }

    public String getTIPODOC() {
        return TIPODOC;
    }

    public void setTIPODOC(String TIPODOC) {
        this.TIPODOC = TIPODOC;
    }

    public String getCODCLI() {
        return CODCLI;
    }

    public void setCODCLI(String CODCLI) {
        this.CODCLI = CODCLI;
    }

    public String getCODLOJA() {
        return CODLOJA;
    }

    public void setCODLOJA(String CODLOJA) {
        this.CODLOJA = CODLOJA;
    }

    public String getNOMECLI() {
        return NOMECLI;
    }

    public void setNOMECLI(String NOMECLI) {
        this.NOMECLI = NOMECLI;
    }

    public String getDTEMISSAO() {
        return DTEMISSAO;
    }

    public void setDTEMISSAO(String DTEMISSAO) {
        this.DTEMISSAO = DTEMISSAO;
    }

    public String getDTENTREGA() {
        return DTENTREGA;
    }

    public void setDTENTREGA(String DTENTREGA) {
        this.DTENTREGA = DTENTREGA;
    }

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public Float getTOTALNF() {
        return TOTALNF;
    }

    public void setTOTALNF(Float TOTALNF) {
        this.TOTALNF = TOTALNF;
    }

    public String getNUMPEDIDO() {
        return NUMPEDIDO;
    }

    public void setNUMPEDIDO(String NUMPEDIDO) {
        this.NUMPEDIDO = NUMPEDIDO;
    }

    public String getCHAVE() {
        return CHAVE;
    }

    public void setCHAVE(String CHAVE) {
        this.CHAVE = CHAVE;
    }

    public String getPEDIDOCLIENTE() {
        return PEDIDOCLIENTE;
    }

    public void setPEDIDOCLIENTE(String PEDIDOCLIENTE) {
        this.PEDIDOCLIENTE = PEDIDOCLIENTE;
    }

    public String getCODTRANSP() {
        return CODTRANSP;
    }

    public void setCODTRANSP(String CODTRANSP) {
        this.CODTRANSP = CODTRANSP;
    }

    public String getNOMTRANSP() {
        return NOMTRANSP;
    }

    public void setNOMTRANSP(String NOMTRANSP) {
        this.NOMTRANSP = NOMTRANSP;
    }

    public String getTELTRANSP() {
        return TELTRANSP;
    }

    public void setTELTRANSP(String TELTRANSP) {
        this.TELTRANSP = TELTRANSP;
    }

    public String getDTCANHOTO() {
        return DTCANHOTO;
    }

    public void setDTCANHOTO(String DTCANHOTO) {
        this.DTCANHOTO = DTCANHOTO;
    }

    public String getROMANEIO() {
        return ROMANEIO;
    }

    public void setROMANEIO(String ROMANEIO) {
        this.ROMANEIO = ROMANEIO;
    }

    public String getCONDICAO() {
        return CONDICAO;
    }

    public void setCONDICAO(String CONDICAO) {
        this.CONDICAO = CONDICAO;
    }

    public Boolean getView_nota() {
        return View_nota;
    }

    public void setView_nota(Boolean view_nota) {
        View_nota = view_nota;
    }


    public void complemento(String _CNPJ, String _IE, String _CODCIDADE, String _CIDADE, String _CODREDE, String _REDE, String _DDD, String _TELEFONE) {
        this._CNPJ = _CNPJ;
        this._IE = _IE;
        this._CODCIDADE = _CODCIDADE;
        this._CIDADE = _CIDADE;
        this._CODREDE = _CODREDE;
        this._REDE = _REDE;
        this._DDD = _DDD;
        this._TELEFONE = _TELEFONE;
    }

    public String get_CNPJ() {
        return _CNPJ;
    }

    public String get_IE() {
        return _IE;
    }

    public String get_CODCIDADE() {
        return _CODCIDADE;
    }

    public String get_CIDADE() {
        return _CIDADE;
    }

    public String get_CODREDE() {
        return _CODREDE;
    }

    public String get_REDE() {
        return _REDE;
    }

    public String get_DDD() {
        return _DDD;
    }

    public String get_TELEFONE() {
        return _TELEFONE;
    }

    public String get_Tipo(){

        String retorno = "";

        if (this.TIPODOC == null){

            return retorno;

        }

        switch (this.TIPODOC.charAt(0)){

            case 'V':
                        retorno = "VENDAS";
                        break;
            case 'D':
                        retorno = "DEVOLUÇÃO";
                        break;
            default:
                        retorno = "";
        }

        return retorno;

    }
    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("FILIAL");
        _colunas.add("SERIE");
        _colunas.add("NOTAFISCAL");
        _colunas.add("TIPODOC");
        _colunas.add("CODCLI");
        _colunas.add("CODLOJA");
        _colunas.add("NOMECLI");
        _colunas.add("DTEMISSAO");
        _colunas.add("DTENTREGA");
        _colunas.add("OBSERVACAO");
        _colunas.add("TOTALNF");
        _colunas.add("NUMPEDIDO");
        _colunas.add("CHAVE");
        _colunas.add("PEDIDOCLIENTE");
        _colunas.add("CODTRANSP");
        _colunas.add("NOMTRANSP");
        _colunas.add("TELTRANSP");
        _colunas.add("DTCANHOTO");
        _colunas.add("ROMANEIO");
        _colunas.add("CONDICAO");



    }
}
