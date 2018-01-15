package br.com.brotolegal.savdatabase.util;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Falconi on 30/05/2017.
 */

public class Rel_Visao {

    private String Opcao;
    private List<Visoes> lsVisoes;

    public Rel_Visao() {

        Opcao = "BP";

        lsVisoes = new ArrayList<>();

    }

    public String getOpcao() {
        return Opcao;
    }

    public void setOpcao(String opcao) {
        Opcao = opcao;
    }

    public void addVisao(String codigo, String descricao){

        lsVisoes.add(new Visoes(codigo,descricao));

    }
    public List<String[]> getVisoes(){

        List<String[]> retorno = new ArrayList<>();

        for(Visoes vi : this.lsVisoes){


            retorno.add(new String[]  {vi.getCodigo(),vi.getDescricao()});


        }

        return retorno;

    }

    public int getIndice(){

        int retorno = 0;

        int indice  = 0;

        for(Visoes vi : lsVisoes){

            if (vi.getCodigo().equals(this.Opcao)){

                retorno = indice;

                break;

            }

            indice++;
        }

        return retorno ;
    }

    private class Visoes

    {

        private String codigo;
        private String descricao;


        public Visoes(String codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
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
    }
}
