package br.com.brotolegal.savdatabase.util;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Falconi on 29/05/2017.
 */

public class Filtro_Categoria {
    
    private Boolean ativo;
    private String  descricao;
    private Map<String,String>  lsCategorias;
    private Map<String,Boolean> lsEscolhidos;


    public Filtro_Categoria(Boolean ativo, String descricao) {
        this.ativo = ativo;
        this.descricao = descricao;
        this.lsCategorias =  new TreeMap<String, String >();
        this.lsEscolhidos = new TreeMap<String, Boolean>();


        loadCategorias();
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getStatusByCategoria(String value) {

        Boolean status;

        try {

            status = lsEscolhidos.get(value);

        } catch (Exception e){

            status = false;
        };

        return lsEscolhidos.get(value);

    }

    public void setStatusByCategoria(String key,Boolean value) {

        Boolean status;

        try {

            lsEscolhidos.put(key,value);

        } catch (Exception e){

            //
        };

    }

    private void loadCategorias(){
        
        lsCategorias.put("3.01","FEIJÃO");
        lsCategorias.put("3.02","ARROZ");
        lsCategorias.put("3.20","AZEITE");
        lsCategorias.put("3.21","JERKED BEEF");
        lsCategorias.put("3.22","PESCADO");


        lsEscolhidos.put("3.01",false);
        lsEscolhidos.put("3.02",false);
        lsEscolhidos.put("3.20",false);
        lsEscolhidos.put("3.21",false);
        lsEscolhidos.put("3.22",false);

    }

    public Map<String, Boolean> getLsEscolhidos() {
        return lsEscolhidos;
    }
}
