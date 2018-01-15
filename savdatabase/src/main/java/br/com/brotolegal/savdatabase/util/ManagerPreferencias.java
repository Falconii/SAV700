package br.com.brotolegal.savdatabase.util;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.brotolegal.savdatabase.app.App;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Falconi on 02/06/2017.
 */

public class ManagerPreferencias {

    private Context context;

    public ManagerPreferencias(Context context) {

        this.context = context;

    }

    public void savePreferenciasRel_01(){


        SharedPreferences prefs = context.getSharedPreferences("ParamentrosRel_01", MODE_PRIVATE);

        //filtro data
        Boolean dtAtivo       = App.manager_filtro_01.getFiltro_data().getAtivo();
        String  dtDataInicial = App.manager_filtro_01.getFiltro_data().getDatainicial();
        String  dtDataFinal   = App.manager_filtro_01.getFiltro_data().getDatafinal();


        //filtro cliente
        Boolean clAtivo       = App.manager_filtro_01.getFiltro_cliente().getAtivo();
        String  clTipo        = App.manager_filtro_01.getFiltro_cliente().getTipo();
        String  clCodigo      = App.manager_filtro_01.getFiltro_cliente().getCodigo();
        String  clLoja        = App.manager_filtro_01.getFiltro_cliente().getLoja();

        //categoria
        Boolean ctAtivo        = App.manager_filtro_01.getFiltro_categoria().getAtivo();
        Boolean  ct301         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.01");
        Boolean  ct302         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.02");
        Boolean  ct320         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.20");
        Boolean  ct321         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.21");
        Boolean  ct322         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.22");

        //marca
        Boolean mcAtivo        = App.manager_filtro_01.getFiltro_marca().isAtivo();
        String  mcCodigo       = App.manager_filtro_01.getFiltro_marca().getCodigo();

        //produto
        Boolean prProduto             = App.manager_filtro_01.getFiltro_produto().getAtivo();
        String  prCodigoInicial       = App.manager_filtro_01.getFiltro_produto().getCodigoInicial();
        String  prCodigoFinal         = App.manager_filtro_01.getFiltro_produto().getCodigofinal();


        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();

        editor.putBoolean("dtAtivo"     , dtAtivo);
        editor.putString("dtDataInicial", dtDataInicial);
        editor.putString("dtDataFinal"  , dtDataFinal);

        editor.putBoolean("clAtivo"     , dtAtivo);
        editor.putString("clTipo"       , clTipo);
        editor.putString("clCodigo"     , clCodigo);
        editor.putString("clLoja"       , clLoja);

        editor.putBoolean("ctAtivo"     , dtAtivo);
        editor.putBoolean("ct301"       , ct301);
        editor.putBoolean("ct302"       , ct302);
        editor.putBoolean("ct320"       , ct320);
        editor.putBoolean("ct321"       , ct321);
        editor.putBoolean("ct322"       , ct322);

        editor.putBoolean("mcAtivo"     , mcAtivo);
        editor.putString("mcCodigo"     , mcCodigo);

        editor.putBoolean("prProduto"      ,prProduto);
        editor.putString("prCodigoInicial" ,prCodigoInicial);
        editor.putString("prCodigoFinal"   ,prCodigoFinal);

        editor.commit();


    }


    public void loadPreferenciasRel_01(){


        SharedPreferences prefs = context.getSharedPreferences("ParamentrosRel_01", MODE_PRIVATE);

        //filtro data
        Boolean dtAtivo        = App.manager_filtro_01.getFiltro_data().getAtivo();
        String  dtDataInicial  = App.manager_filtro_01.getFiltro_data().getDatainicial();
        String  dtDataFinal    = App.manager_filtro_01.getFiltro_data().getDatafinal();


        //filtro cliente
        Boolean clAtivo        = App.manager_filtro_01.getFiltro_cliente().getAtivo();
        String  clTipo         = App.manager_filtro_01.getFiltro_cliente().getTipo();
        String  clCodigo       = App.manager_filtro_01.getFiltro_cliente().getCodigo();
        String  clLoja         = App.manager_filtro_01.getFiltro_cliente().getLoja();

        //categoria
        Boolean ctAtivo        = App.manager_filtro_01.getFiltro_categoria().getAtivo();
        Boolean  ct301         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.01");
        Boolean  ct302         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.02");
        Boolean  ct320         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.20");
        Boolean  ct321         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.21");
        Boolean  ct322         = App.manager_filtro_01.getFiltro_categoria().getStatusByCategoria("3.22");

        //marca
        Boolean mcAtivo        = App.manager_filtro_01.getFiltro_marca().isAtivo();
        String  mcCodigo       = App.manager_filtro_01.getFiltro_marca().getCodigo();

        //produto
        Boolean prProduto             = App.manager_filtro_01.getFiltro_produto().getAtivo();
        String  prCodigoInicial       = App.manager_filtro_01.getFiltro_produto().getCodigoInicial();
        String  prCodigoFinal         = App.manager_filtro_01.getFiltro_produto().getCodigofinal();

        App.manager_filtro_01.getFiltro_data().setAtivo(prefs.getBoolean("dtAtivo"     , dtAtivo));
        App.manager_filtro_01.getFiltro_data().setDatainicial(prefs.getString("dtDataInicial", dtDataInicial));
        App.manager_filtro_01.getFiltro_data().setDatafinal(prefs.getString("dtDataFinal"  , dtDataFinal));

        App.manager_filtro_01.getFiltro_cliente().setAtivo(prefs.getBoolean("clAtivo"     , dtAtivo));
        App.manager_filtro_01.getFiltro_cliente().setTipo(prefs.getString("clTipo"       , clTipo));
        App.manager_filtro_01.getFiltro_cliente().setCodigo(prefs.getString("clCodigo"     , clCodigo));
        App.manager_filtro_01.getFiltro_cliente().setLoja(prefs.getString("clLoja"       , clLoja));

        App.manager_filtro_01.getFiltro_categoria().setAtivo(prefs.getBoolean("ctAtivo"     , dtAtivo));
        App.manager_filtro_01.getFiltro_categoria().setStatusByCategoria("3.01",prefs.getBoolean("ct301"       , ct301));
        App.manager_filtro_01.getFiltro_categoria().setStatusByCategoria("3.02",prefs.getBoolean("ct302"       , ct302));
        App.manager_filtro_01.getFiltro_categoria().setStatusByCategoria("3.20",prefs.getBoolean("ct320"       , ct320));
        App.manager_filtro_01.getFiltro_categoria().setStatusByCategoria("3.21",prefs.getBoolean("ct321"       , ct321));
        App.manager_filtro_01.getFiltro_categoria().setStatusByCategoria("3.22",prefs.getBoolean("ct322"       , ct322));

        App.manager_filtro_01.getFiltro_marca().setAtivo(prefs.getBoolean("mcAtivo"     , mcAtivo));
        App.manager_filtro_01.getFiltro_marca().setCodigo(prefs.getString("mcCodigo"     , mcCodigo));

        App.manager_filtro_01.getFiltro_produto().setAtivo(prefs.getBoolean("prProduto"      ,prProduto));
        App.manager_filtro_01.getFiltro_produto().setCodigoInicial(prefs.getString("prCodigoInicial" ,prCodigoInicial));
        App.manager_filtro_01.getFiltro_produto().setCodigofinal(prefs.getString("prCodigoFinal"   ,prCodigoFinal));


    }
}
