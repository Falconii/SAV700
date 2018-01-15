package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 06/06/2017.
 */

public class MotivosTrocaDev extends ObjRegister {

    protected String TIPO;
    protected String CODIGO;
    protected String DESCRICAO;




    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.MotivosTrocaDev";


    public MotivosTrocaDev(){

        super(_OBJETO,"MOTIVOSTROCADEV");

        loadColunas();

        InicializaFields();
    }

    public MotivosTrocaDev(String TIPO, String CODIGO, String DESCRICAO) {

        super(_OBJETO,"MOTIVOSTROCADEV");

        loadColunas();

        InicializaFields();

        this.TIPO      = TIPO;
        this.CODIGO    = CODIGO;
        this.DESCRICAO = DESCRICAO;
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
    public void loadHelp() {

        List<HelpParam> help     = new ArrayList<HelpParam>();

        help.add(new HelpParam("DESCRIÇÃO",
                "select tipo,codigo,descricao from MOTIVOSTROCADEV  ",
                "where descricao like ''%{0}%'' ",
                "order by descricao ",
                "descricao",
                new String[] {"TIPO","CODIGO"},
                "( tipo = ''{0}''  ) ",   //aliaswhere
                new String[] {}));

        help.add(new HelpParam("CÓDIGO",
                "select tipo,codigo,descricao from MOTIVOSTROCADEV ",
                "where codigo like ''%{0}%'' ",
                "order by codigo ",
                "codigo",
                new String[] {"TIPO","CODIGO"},
                "( tipo = ''{0}''  ) ",   //aliaswhere
                new String[] {}));
        List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

        _fileTable.add(new FileTable("MOTIVOSDEV", help, filtro, null));

        loadTableHelp("MOTIVOSDEV");


    };



    @Override
    public String[] getHelpLinhas(Cursor cursor) {

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

            linha1 = "Código: "+cursor.getString(cursor.getColumnIndex("codigo"))+" Descrição: "+cursor.getString(cursor.getColumnIndex("descricao"));

            linha2 = "";

            letra  = cursor.getString(cursor.getColumnIndex("descricao")).substring(0, 1);

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
        _colunas.add("TIPO");
        _colunas.add("CODIGO");
        _colunas.add("DESCRICAO");


    }
}
