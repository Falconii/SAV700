package br.com.brotolegal.savdatabase.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import br.com.brotolegal.savdatabase.app.App;

/**
 * Created by Falconi on 01/06/2017.
 */

public class Manager_Filtro_01 {

    private Filtro_Data filtro_data;
    private Filtro_Cliente filtro_cliente;
    private Filtro_Categoria filtro_categoria;
    private Filtro_Marca filtro_marca;
    private Filtro_Produto filtro_produto;
    private Rel_Topicos rel_topicos;
    private Rel_Visao rel_visao;


    public Manager_Filtro_01(Filtro_Data filtro_data, Filtro_Cliente filtro_cliente, Filtro_Categoria filtro_categoria, Filtro_Marca filtro_marca, Filtro_Produto filtro_produto, Rel_Topicos rel_topicos, Rel_Visao rel_visao) {
        this.filtro_data = filtro_data;
        this.filtro_cliente = filtro_cliente;
        this.filtro_categoria = filtro_categoria;
        this.filtro_marca = filtro_marca;
        this.filtro_produto = filtro_produto;
        this.rel_topicos = rel_topicos;
        this.rel_visao = rel_visao;
    }

    public Manager_Filtro_01() {


        this.filtro_data = null;
        this.filtro_cliente = null;
        this.filtro_categoria = null;
        this.filtro_marca = null;
        this.filtro_produto = null;
        this.rel_topicos = null;
        this.rel_visao = null;

    }

    public String getStringTopicos(){

        String retorno = "";

        if (rel_topicos != null){

            for(String[] topico : rel_topicos.getOpcoes()){

                if (rel_topicos.getStatusOpcao(topico[0])) {

                    if (!retorno.isEmpty()) retorno += "|";

                    retorno += topico[0];
                }

            }

        }

        return retorno;

    }


    public String getWhere(){

        String retorno = "";

        if (rel_visao.getOpcao().equals("BT")) {

            if (filtro_data.getAtivo()) {

                if (!retorno.isEmpty()) retorno += " and ";

                retorno += "( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2) between " +
                        "'" + App.ddmmaaaaatoaaaammdd(filtro_data.getDatainicial()) + "'  and " +
                        "'" + App.ddmmaaaaatoaaaammdd(filtro_data.getDatafinal()) + "' ) ";
            }

            if (filtro_cliente.getAtivo()) {

                if (!retorno.isEmpty()) retorno += " and ";

                if (filtro_cliente.getTipo().equals("C")) {

                    retorno += " ( pedidocabmb.codigofat = '" + filtro_cliente.getCodigo() + "' and pedidocabmb.lojafat = '" + filtro_cliente.getLoja() + "' ) ";


                } else {

                    retorno += " ( cliente.rede = '" + filtro_cliente.getCodigo() + "' ) ";

                }

            }

            if (filtro_categoria.getAtivo()) {

                String where = "";

                Boolean lAchou = false;

                for (Map.Entry<String, Boolean> cat : filtro_categoria.getLsEscolhidos().entrySet()) {

                    if (cat.getValue()) {

                        lAchou = true;

                        if (!where.isEmpty()) where += " or ";

                        if (cat.getKey().equals("3.20")) {

                            where += " produto.grupo in ('3.17','3.18','3.19','3.20') ";

                        } else {

                            where += " produto.grupo = '" + cat.getKey() + "' ";

                        }


                    }

                }

                if (lAchou) {

                    if (!retorno.isEmpty()) retorno += " and ";

                    retorno += "( " + where + " ) ";
                }
            }


            if (filtro_marca.isAtivo() && !filtro_marca.getCodigo().trim().isEmpty()) {

                if (!retorno.isEmpty()) retorno += " and ";

                retorno += " ( produto.marca = '" + filtro_marca.getCodigo() + "' ) ";
            }


            if (filtro_produto.getAtivo()) {


                if (!retorno.isEmpty()) retorno += " and ";

                retorno += "( pedidodetmb.produto between " +
                        "'" + filtro_produto.getCodigoInicial() + "'  and " +
                        "'" + filtro_produto.getCodigofinal() + "' ) ";
            }

        } else {

            if (filtro_data.getAtivo()) {

                if (!retorno.isEmpty()) retorno += " and ";

                retorno += "( base01.data between " +
                        "'" + App.ddmmaaaaatoaaaammdd(filtro_data.getDatainicial()) + "'  and " +
                        "'" + App.ddmmaaaaatoaaaammdd(filtro_data.getDatafinal()) + "' ) ";
            }

            if (filtro_cliente.getAtivo()) {

                if (!retorno.isEmpty()) retorno += " and ";

                if (filtro_cliente.getTipo().equals("C")) {

                    retorno += " ( base01.cliente = '" + filtro_cliente.getCodigo() + "' and base01.loja = '" + filtro_cliente.getLoja() + "' ) ";


                } else {

                    retorno += " ( base01.rede = '" + filtro_cliente.getCodigo() + "' ) ";

                }

            }

            if (filtro_categoria.getAtivo()) {

                String where = "";

                Boolean lAchou = false;

                for (Map.Entry<String, Boolean> cat : filtro_categoria.getLsEscolhidos().entrySet()) {

                    if (cat.getValue()) {

                        lAchou = true;

                        if (!where.isEmpty()) where += " or ";

                        if (cat.getKey().equals("3.20")) {

                            where += " base01.categoria in ('3.17','3.18','3.19','3.20') ";

                        } else {

                            where += "  base01.categoria = '" + cat.getKey() + "' ";

                        }


                    }

                }

                if (lAchou) {

                    if (!retorno.isEmpty()) retorno += " and ";

                    retorno += "( " + where + " ) ";
                }
            }


            if (filtro_marca.isAtivo() && !filtro_marca.getCodigo().trim().isEmpty()) {

                if (!retorno.isEmpty()) retorno += " and ";

                retorno += " ( base01.marca = '" + filtro_marca.getCodigo() + "' ) ";
            }


            if (filtro_produto.getAtivo()) {


                if (!retorno.isEmpty()) retorno += " and ";

                retorno += "( base01.produto between " +
                        "'" + filtro_produto.getCodigoInicial() + "'  and " +
                        "'" + filtro_produto.getCodigofinal() + "' ) ";
            }




        }
        return  retorno ;

    }

    public String getStringOrderBy(){

        String retorno = "";

        String topicos = getStringTopicos();

        if (!topicos.isEmpty()) {


            if (rel_visao.getOpcao().equals("BT")) {

                if (topicos.contains("DT")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2)";


                }

                if (topicos.contains("CL")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "pedidocabmb.codigofat.cliente,pedidocabmb.lojafat";


                }

                if (topicos.contains("CT")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "produto.grupo";


                }

                if (topicos.contains("MC")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "produto.marca";


                }

                if (topicos.contains("PR")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "pedidocabmb.produto";


                }

                if (retorno.isEmpty())
                    retorno = "( substr(pedidocabmb.emissao,7,4) || substr(pedidocabmb.emissao,4,2) || substr(pedidocabmb.emissao,1,2) )";

            } else {


                if (topicos.contains("DT")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "base01.data";


                }

                if (topicos.contains("CL")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "base01.cliente,base01.loja";


                }

                if (topicos.contains("CT")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "base01.categoria";


                }

                if (topicos.contains("MC")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "base01.marca";


                }

                if (topicos.contains("PR")) {

                    if (!retorno.isEmpty()) retorno += ",";

                    retorno += "base01.produto";


                }

                if (retorno.isEmpty())
                    retorno = "(base01.data)";
            }


        }

        return retorno;

    }

    public Filtro_Data getFiltro_data() {
        return filtro_data;
    }

    public void setFiltro_data(Filtro_Data filtro_data) {
        this.filtro_data = filtro_data;
    }

    public Filtro_Cliente getFiltro_cliente() {
        return filtro_cliente;
    }

    public void setFiltro_cliente(Filtro_Cliente filtro_cliente) {
        this.filtro_cliente = filtro_cliente;
    }

    public Filtro_Categoria getFiltro_categoria() {
        return filtro_categoria;
    }

    public void setFiltro_categoria(Filtro_Categoria filtro_categoria) {
        this.filtro_categoria = filtro_categoria;
    }

    public Filtro_Marca getFiltro_marca() {
        return filtro_marca;
    }

    public void setFiltro_marca(Filtro_Marca filtro_marca) {
        this.filtro_marca = filtro_marca;
    }

    public Filtro_Produto getFiltro_produto() {
        return filtro_produto;
    }

    public void setFiltro_produto(Filtro_Produto filtro_produto) {
        this.filtro_produto = filtro_produto;
    }

    public Rel_Topicos getRel_topicos() {
        return rel_topicos;
    }

    public void setRel_topicos(Rel_Topicos rel_topicos) {
        this.rel_topicos = rel_topicos;
    }

    public Rel_Visao getRel_visao() {
        return rel_visao;
    }

    public void setRel_visao(Rel_Visao rel_visao) {
        this.rel_visao = rel_visao;
    }
}
