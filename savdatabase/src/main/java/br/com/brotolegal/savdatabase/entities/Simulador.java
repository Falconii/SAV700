package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;

import static android.R.attr.format;

/**
 * Created by Falconi on 04/07/2016.
 */
public class Simulador extends ObjRegister {

    protected String CODSIMULADOR;
    protected String CODCLI;
    protected String LOJACLI;
    protected String REDE;
    protected String CONDPAG;
    protected String EMISSAO;
    protected String DATAINICIAL;
    protected String DATAFINAL;
    protected String STATUS;
    protected String CODTABELA;
    protected Float  QTDPREVISTA;
    protected String ITEM;
    protected String CODPRODUTO;
    protected Float  SALDO;
    protected Float  PRECOAPROVADO;




    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Simulador";


    public Simulador(){

        super(_OBJETO,"SIMULADOR");

        loadColunas();

        InicializaFields();
    }

    public Simulador(String CODSIMULADOR, String CODCLI, String LOJACLI, String REDE, String CONDPAG, String EMISSAO, String DATAINICIAL, String DATAFINAL, String STATUS, String CODTABELA, Float QTDPREVISTA, String ITEM, String CODPRODUTO, Float SALDO, Float PRECOAPROVADO) {
        super(_OBJETO,"SIMULADOR");

        loadColunas();

        InicializaFields();
        this.CODSIMULADOR = CODSIMULADOR;
        this.CODCLI = CODCLI;
        this.LOJACLI = LOJACLI;
        this.REDE    = REDE;
        this.CONDPAG = CONDPAG;
        this.EMISSAO = EMISSAO;
        this.DATAINICIAL = DATAINICIAL;
        this.DATAFINAL = DATAFINAL;
        this.STATUS = STATUS;
        this.CODTABELA = CODTABELA;
        this.QTDPREVISTA = QTDPREVISTA;
        this.ITEM = ITEM;
        this.CODPRODUTO = CODPRODUTO;
        this.SALDO = SALDO;
        this.PRECOAPROVADO = PRECOAPROVADO;
    }



    public String getCODSIMULADOR() {
        return CODSIMULADOR;
    }

    public void setCODSIMULADOR(String CODSIMULADOR) {
        this.CODSIMULADOR = CODSIMULADOR;
    }

    public String getCODCLI() {
        return CODCLI;
    }

    public void setCODCLI(String CODCLI) {
        this.CODCLI = CODCLI;
    }

    public String getLOJACLI() {
        return LOJACLI;
    }

    public void setLOJACLI(String LOJACLI) {
        this.LOJACLI = LOJACLI;
    }

    public String getCONDPAG() {
        return CONDPAG;
    }

    public void setCONDPAG(String CONDPAG) {
        this.CONDPAG = CONDPAG;
    }

    public String getEMISSAO() {
        return EMISSAO;
    }

    public void setEMISSAO(String EMISSAO) {
        this.EMISSAO = EMISSAO;
    }

    public String getDATAINICIAL() {
        return DATAINICIAL;
    }

    public void setDATAINICIAL(String DATAINICIAL) {
        this.DATAINICIAL = DATAINICIAL;
    }

    public String getDATAFINAL() {
        return DATAFINAL;
    }

    public void setDATAFINAL(String DATAFINAL) {
        this.DATAFINAL = DATAFINAL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCODTABELA() {
        return CODTABELA;
    }

    public void setCODTABELA(String CODTABELA) {
        this.CODTABELA = CODTABELA;
    }

    public Float getQTDPREVISTA() {
        return QTDPREVISTA;
    }

    public void setQTDPREVISTA(Float QTDPREVISTA) {
        this.QTDPREVISTA = QTDPREVISTA;
    }

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public String getCODPRODUTO() {
        return CODPRODUTO;
    }

    public void setCODPRODUTO(String CODPRODUTO) {
        this.CODPRODUTO = CODPRODUTO;
    }

    public Float getSALDO() {
        return SALDO;
    }

    public void setSALDO(Float SALDO) {
        this.SALDO = SALDO;
    }

    public Float getPRECOAPROVADO() {
        return PRECOAPROVADO;
    }

    public void setPRECOAPROVADO(Float PRECOAPROVADO) {
        this.PRECOAPROVADO = PRECOAPROVADO;
    }

    public String getREDE() {
        return REDE;
    }

    public void setREDE(String REDE) {
        this.REDE = REDE;
    }

    @Override
    public void loadHelp() {

		/*
		 *
		 *  ALIAS SIMULADOR
		 *
		 *
		 */

        List<HelpParam> help     = new ArrayList<HelpParam>();

        help.add(new HelpParam("CODIGO" ,
                "select "+
                        "simulador.codsimulador    as codsimulador, "  +
                        "simulador.codcli          as codcli,  "       +
                        "simulador.lojacli         as lojacli ,"       +
                        "simulador.rede            as redecli    ,"       +
                        "simulador.item            as item, "          +
                        "cliente.razao             as razao, "         +
                        "simulador.emissao         as emissao, "       +
                        "simulador.datainicial     as datainicial, "   +
                        "simulador.datafinal       as datafinal, "     +
                        "simulador.status          as status, "        +
                        "simulador.codtabela       as codtabela ,  "   +
                        "simulador.qtdprevista     as qtdprevista, "   +
                        "simulador.item            as item, "          +
                        "simulador.codproduto      as codproduto, "    +
                        "simulador.saldo           as saldo, "         +
                        "simulador.precoaprovado   as precoaprovado, " +
                        "produto.descricao         as descricao "      +
                        "from simulador  " +
                        "inner join produto on produto.codigo = simulador.codproduto  " +
                        "inner join cliente on cliente.codigo = simulador.codcli and cliente.loja = lojacli ",
                "where simulador.codsimulador like ''%{0}%'' "           ,
                "order by simulador.codsimulador",
                "codsimulador",
                new String[] {"CODSIMULADOR","CODCLI","LOJACLI","CODPRODUTO"},
                " codproduto = ''{0}'' and ( (codcli = ''{1}'' and lojacli = ''{2}'') or ( redecli = ''{3}'' )  )",   //aliaswhere
                new String[] {}));


        List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

        _fileTable.add(new FileTable("SIMULADOR", help, filtro, null));

        loadTableHelp("SIMULADOR");


    };

    @Override
    public String[] getHelpLinhas(Cursor cursor) {

        DecimalFormat format_02 = new DecimalFormat(",##0.00");

        String[] retorno = {"", //linha1
                "", //linha2
                "", //letra
                "", //texto1
                ""}; //texto2


        String linha1    = "";
        String linha2    = "";
        String letra     = "";
        String texto1    = "";
        String texto2    = "";

        try
        {

            linha1 = "Produto: "+cursor.getString(cursor.getColumnIndex("codproduto"))+"-"+cursor.getString(cursor.getColumnIndex("descricao"));

            linha2 = cursor.getString(cursor.getColumnIndex("codcli"))+"-"+cursor.getString(cursor.getColumnIndex("lojacli"))+" "+cursor.getString(cursor.getColumnIndex("razao"));

            texto1 = "SALDO: "+format_02.format(cursor.getFloat(cursor.getColumnIndex("saldo")))+" Validade: "+ App.aaaammddToddmmaaaa(cursor.getString(cursor.getColumnIndex("datafinal")));

            texto1 = "PREÇO: "+format_02.format(cursor.getFloat(cursor.getColumnIndex("precoaprovado")))+" Validade: "+ App.aaaammddToddmmaaaa(cursor.getString(cursor.getColumnIndex("datafinal")));

            letra  = cursor.getString(cursor.getColumnIndex("razao")).substring(0, 1);

        }catch(Exception e) {

            linha1 = e.getMessage();

        }

        retorno[0] = linha1;

        retorno[1] = linha2;

        retorno[2] = letra;

        retorno[3] = texto1;

        retorno[4] = texto2;

        return retorno;


    };

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();
        _colunas.add("CODSIMULADOR");
        _colunas.add("CODCLI");
        _colunas.add("LOJACLI");
        _colunas.add("REDE");
        _colunas.add("CONDPAG");
        _colunas.add("EMISSAO");
        _colunas.add("DATAINICIAL");
        _colunas.add("DATAFINAL");
        _colunas.add("STATUS");
        _colunas.add("CODTABELA");
        _colunas.add("QTDPREVISTA");
        _colunas.add("ITEM");
        _colunas.add("CODPRODUTO");
        _colunas.add("SALDO");
        _colunas.add("PRECOAPROVADO");

    }
}
