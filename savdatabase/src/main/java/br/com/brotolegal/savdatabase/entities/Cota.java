package br.com.brotolegal.savdatabase.entities;

import java.math.BigDecimal;
import java.util.ArrayList;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 04/08/2017.
 */

public class Cota extends ObjRegister {

    protected String CODIGO;
    protected String GEREN;
    protected String SUPER;
    protected String VEND;
    protected String CODCLI;
    protected String LOJA;
    protected String REDE;
    protected String CANAL;
    protected String REGIAO;
    protected String SMARCA;
    protected String PRODUTO;
    protected Float  PRECO;
    protected String UTILCONTR;
    protected String UTILTX;
    protected String UTILDNA;
    protected String UTILCOTA;
    protected Float  QTDCOTA;
    protected Float  SLDCOTA;
    protected String DTENTINICIAL;
    protected String DTENTFINAL;
    protected String DESCRICAO;



    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Cota";
    protected String _RAZAO;
    protected String _REDE;
    protected String _CANAL;
    protected String _SMARCA;
    protected String _PRODUTO;
    protected Float  _PRECOFINAL;


    public Cota(){

        super(_OBJETO,"COTA");

        loadColunas();

        InicializaFields();

        _RAZAO      =  "";
        _REDE       =  "";
        _CANAL      =  "";
        _SMARCA     =  "";
        _PRODUTO    =  "";
        _PRECOFINAL =  0F;

    }

    public Cota(String CODIGO, String GEREN, String SUPER, String VEND, String CODCLI, String LOJA, String REDE, String CANAL, String REGIAO, String SMARCA, String PRODUTO, Float PRECO, String UTILCONTR, String UTILTX, String UTILDNA, String UTILCOTA, Float QTDCOTA, Float SLDCOTA, String DTENTINICIAL, String DTENTFINAL, String DESCRICAO) {

        super(_OBJETO,"COTA");

        loadColunas();

        InicializaFields();


        this.CODIGO = CODIGO;
        this.GEREN = GEREN;
        this.SUPER = SUPER;
        this.VEND = VEND;
        this.CODCLI = CODCLI;
        this.LOJA = LOJA;
        this.REDE = REDE;
        this.CANAL = CANAL;
        this.REGIAO = REGIAO;
        this.SMARCA = SMARCA;
        this.PRODUTO = PRODUTO;
        this.PRECO = PRECO;
        this.UTILCONTR = UTILCONTR;
        this.UTILTX = UTILTX;
        this.UTILDNA = UTILDNA;
        this.UTILCOTA = UTILCOTA;
        this.QTDCOTA = QTDCOTA;
        this.SLDCOTA = SLDCOTA;
        this.DTENTINICIAL = DTENTINICIAL;
        this.DTENTFINAL = DTENTFINAL;
        this.DESCRICAO  = DESCRICAO;

        this._RAZAO      =  "";
        this._REDE       =  "";
        this._CANAL      =  "";
        this._SMARCA     =  "";
        this._PRODUTO    =  "";
        this._PRECOFINAL =  0F;
    }

    public void virtuais(String _RAZAO, String _REDE, String _CANAL, String _SMARCA, String _PRODUTO) {

        this._RAZAO = _RAZAO;
        this._REDE = _REDE;
        this._CANAL = _CANAL;
        this._SMARCA = _SMARCA;
        this._PRODUTO = _PRODUTO;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getGEREN() {
        return GEREN;
    }

    public void setGEREN(String GEREN) {
        this.GEREN = GEREN;
    }

    public String getSUPER() {
        return SUPER;
    }

    public void setSUPER(String SUPER) {
        this.SUPER = SUPER;
    }

    public String getVEND() {
        return VEND;
    }

    public void setVEND(String VEND) {
        this.VEND = VEND;
    }

    public String getCODCLI() {
        return CODCLI;
    }

    public void setCODCLI(String CODCLI) {
        this.CODCLI = CODCLI;
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

    public String getCANAL() {
        return CANAL;
    }

    public void setCANAL(String CANAL) {
        this.CANAL = CANAL;
    }

    public String getREGIAO() {
        return REGIAO;
    }

    public void setREGIAO(String REGIAO) {
        this.REGIAO = REGIAO;
    }

    public String getSMARCA() {
        return SMARCA;
    }

    public void setSMARCA(String SMARCA) {
        this.SMARCA = SMARCA;
    }

    public String getPRODUTO() {
        return PRODUTO;
    }

    public void setPRODUTO(String PRODUTO) {
        this.PRODUTO = PRODUTO;
    }

    public Float getPRECO() {
        return PRECO;
    }

    public void setPRECO(Float PRECO) {
        this.PRECO = PRECO;
    }

    public String getUTILCONTR() {
        return UTILCONTR;
    }

    public void setUTILCONTR(String UTILCONTR) {
        this.UTILCONTR = UTILCONTR;
    }

    public String getUTILTX() {
        return UTILTX;
    }

    public void setUTILTX(String UTILTX) {
        this.UTILTX = UTILTX;
    }

    public String getUTILDNA() {
        return UTILDNA;
    }

    public void setUTILDNA(String UTILDNA) {
        this.UTILDNA = UTILDNA;
    }

    public String getUTILCOTA() {
        return UTILCOTA;
    }

    public void setUTILCOTA(String UTILCOTA) {
        this.UTILCOTA = UTILCOTA;
    }

    public Float getQTDCOTA() {
        return QTDCOTA;
    }

    public void setQTDCOTA(Float QTDCOTA) {
        this.QTDCOTA = QTDCOTA;
    }

    public Float getSLDCOTA() {
        return SLDCOTA;
    }

    public void setSLDCOTA(Float SLDCOTA) {
        this.SLDCOTA = SLDCOTA;
    }

    public String getDTENTINICIAL() {
        return DTENTINICIAL;
    }

    public void setDTENTINICIAL(String DTENTINICIAL) {
        this.DTENTINICIAL = DTENTINICIAL;
    }

    public String getDTENTFINAL() {
        return DTENTFINAL;
    }

    public void setDTENTFINAL(String DTENTFINAL) {
        this.DTENTFINAL = DTENTFINAL;
    }

    public static String getObjeto() {
        return _OBJETO;
    }

    public String get_RAZAO() {
        return _RAZAO;
    }

    public void set_RAZAO(String _RAZAO) {
        this._RAZAO = _RAZAO;
    }

    public String get_REDE() {
        return _REDE;
    }

    public void set_REDE(String _REDE) {
        this._REDE = _REDE;
    }

    public String get_CANAL() {
        return _CANAL;
    }

    public void set_CANAL(String _CANAL) {
        this._CANAL = _CANAL;
    }

    public String get_SMARCA() {
        return _SMARCA;
    }

    public void set_SMARCA(String _SMARCA) {
        this._SMARCA = _SMARCA;
    }

    public String get_PRODUTO() {
        return _PRODUTO;
    }

    public void set_PRODUTO(String _PRODUTO) {
        this._PRODUTO = _PRODUTO;
    }

    public Float get_PRECOFINAL() {
        return _PRECOFINAL;
    }

    public void set_PRECOFINAL(Float _PRECOFINAL) {
        this._PRECOFINAL = _PRECOFINAL;
    }

    public void CalculoFinal(Float DESCONTROCONTRATO, String TAXAFINANCEIRA, Float CONVERSAO){

        Float preco = 0f;

        try {

            if (!this.getPRODUTO().trim().isEmpty()){

                preco = this.getPRECO();

            } else {

                preco = this.getPRECO() * CONVERSAO;

            }
            if (!(this.getUTILCONTR().equals("S"))) {

                DESCONTROCONTRATO = 0f;

            }

            //CALCULO TX FINANC E CONTRATO

            if ((this.getUTILTX().equals("S"))) {

                preco = ((preco * App.getJuros(TAXAFINANCEIRA).floatValue()) / ((1 - (DESCONTROCONTRATO / 100)) * (1 - (0 / 100))));

            } else {

                preco = ((preco * 1) / ((1 - (DESCONTROCONTRATO / 100)) * (1 - (0 / 100))));
            }

            //DESCONTO RETIRA SÓ NA TABELA VELHA
            //preco = preco - cabec.getDESCRET();

            BigDecimal tot = new BigDecimal(preco);

            tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

            preco = tot.floatValue();

        } catch (Exception e){

            preco = this.getPRECO();

        }

        this.set_PRECOFINAL(preco);

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

        _colunas.add("CODIGO");
        _colunas.add("GEREN");
        _colunas.add("SUPER");
        _colunas.add("VEND");
        _colunas.add("CODCLI");
        _colunas.add("LOJA");
        _colunas.add("REDE");
        _colunas.add("CANAL");
        _colunas.add("REGIAO");
        _colunas.add("SMARCA");
        _colunas.add("PRODUTO");
        _colunas.add("PRECO");
        _colunas.add("UTILCONTR");
        _colunas.add("UTILTX");
        _colunas.add("UTILDNA");
        _colunas.add("UTILCOTA");
        _colunas.add("QTDCOTA");
        _colunas.add("SLDCOTA");
        _colunas.add("DTENTINICIAL");
        _colunas.add("DTENTFINAL");
        _colunas.add("DESCRICAO");



    }
}
