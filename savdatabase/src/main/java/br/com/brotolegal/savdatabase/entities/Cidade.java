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
public class Cidade extends ObjRegister {

    protected String CODIGO;
    protected String ESTADO;
    protected String CIDADE;
    protected String DDD;




    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Cidade";


    public Cidade(){

        super(_OBJETO,"CIDADE");

        loadColunas();

        InicializaFields();
    }

    public Cidade(String CODIGO, String ESTADO, String CIDADE, String DDD) {

        super(_OBJETO,"CIDADE");

        loadColunas();

        InicializaFields();

        this.CODIGO = CODIGO;
        this.ESTADO = ESTADO;
        this.CIDADE = CIDADE;
        this.DDD    = DDD;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getCIDADE() {
        return CIDADE;
    }

    public void setCIDADE(String CIDADE) {
        this.CIDADE = CIDADE;
    }

    public String getDDD() {
        return DDD;
    }

    public void setDDD(String DDD) {
        this.DDD = DDD;
    }


    @Override
    public void loadHelp() {

		/*
		 *
		 *  ALIAS CIDADE
		 *
		 *
		 */

        List<HelpParam> help     = new ArrayList<HelpParam>();

        help.add(new HelpParam("CIDADE" ,
                "select codigo,estado,cidade,ddd  from cidade  ",
                "where cidade like ''%{0}%'' "           ,
                "order by estado,cidade",
                "cidade",
                new String[] {"CODIGO","ESTADO","CIDADE"},
                "",   //aliaswhere
                new String[] {}));

        help.add(new HelpParam("CÓDIGO",
                "select codigo,estado,cidade,ddd  from cidade  ",
                "where codigo like ''%{0}%'' "           ,
                "order by codigo,cidade",
                "codigo",
                new String[] {"CODIGO","ESTADO","CIDADE"},
                "",   //aliaswhere
                new String[] {}));

        List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

        _fileTable.add(new FileTable("CIDADE", help, filtro, null));


        loadTableHelp("CIDADE");


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

            linha1 = cursor.getString(cursor.getColumnIndex("estado"))+" "+cursor.getString(cursor.getColumnIndex("cidade"));

            linha2 = "CÓDIGO: "+cursor.getString(cursor.getColumnIndex("codigo"))+" DDD: "+cursor.getString(cursor.getColumnIndex("ddd"));

            letra  = cursor.getString(cursor.getColumnIndex("cidade")).substring(0, 1);

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
        _colunas.add("CODIGO");
        _colunas.add("ESTADO");
        _colunas.add("CIDADE");
        _colunas.add("DDD");

    }
}
