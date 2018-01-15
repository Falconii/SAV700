package br.com.brotolegal.savdatabase.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.entities.Marca;

/**
 * Created by Falconi on 29/05/2017.
 */

public class Filtro_Marca {

    private boolean ativo;
    private String  codigo;
    private String descricao;
    private Map<String,String> lsmarca;

    public Filtro_Marca(boolean ativo, String codigo, String descricao, List<Marca> marcas) {

        this.ativo = ativo;
        this.codigo = codigo;
        this.descricao = descricao;

        this.lsmarca = new TreeMap<String, String >();

        loadMarcas(marcas);
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void loadMarcas(List<Marca> marcas) {

        for (Marca marca : marcas){

            lsmarca.put(marca.getCODIGO(),marca.getDESCRICAO());
        }
    }

    public List<String[]> getlsMarcas(){

        List<String[]> retorno = new ArrayList<>();

        for(Map.Entry<String, String> values : lsmarca.entrySet()){


            retorno.add(new String[] {values.getKey(),values.getValue()});

        }

        return retorno;
    }

    public int getIndice(){

        int retorno = -1;

        int x = 0;

        String opcional = "";

        for(Map.Entry<String, String> values : lsmarca.entrySet()){

            if (opcional.isEmpty()){

                opcional = values.getKey();

            }

            if (values.getKey().equals(this.codigo)){

                retorno = x;

                break;

            }

            x++;

        }

        if (retorno == -1){

            retorno = 0;

            this.codigo = opcional;

        }

        return retorno;

    }
}
