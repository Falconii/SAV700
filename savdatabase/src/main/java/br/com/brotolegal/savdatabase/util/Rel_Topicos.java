package br.com.brotolegal.savdatabase.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falconi on 30/05/2017.
 */

public class Rel_Topicos {

    private String descricao;
    private List<Opcoes> op;

    public Rel_Topicos(String descricao) {
        this.descricao = descricao;

        op = new ArrayList<>();


    }

    public  void addOpcoes(String Codigo,Boolean Ativo, String Descricao, String Nivel, String NextNivel){

        op.add(new Opcoes(Codigo,Ativo,Descricao,Nivel,NextNivel));

    }

    public List<String[]> getOpcoes(){

        List<String[]> retorno = new ArrayList<>();

        for(Opcoes op : this.op){

            retorno.add(new String[] {op.getCodigo(),op.getDescricao()});

        }

        return retorno;

    }

    public Boolean getStatusOpcao(String key){

        Boolean retorno = false;

        for(Opcoes op : this.op) {

            if (op.getCodigo().equals(key)) {

                retorno = op.getAtivo();

                break;

            }
        }

        return retorno;

    }

    public void setStatusOpcao(String key,Boolean value){

        Boolean retorno = false;

        for(Opcoes op : this.op) {

            if (op.getCodigo().equals(key)) {

                op.setAtivo(value);

                break;

            }
        }


    }

    private class Opcoes{

        private String codigo;
        private Boolean ativo;
        private String Descricao;
        private String Nivel;
        private String NextNivel;

        public Opcoes(String codigo, Boolean ativo, String descricao, String nivel, String nextNivel) {
            this.codigo = codigo;
            this.ativo = ativo;
            Descricao = descricao;
            Nivel = nivel;
            NextNivel = nextNivel;
        }



        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public Boolean getAtivo() {
            return ativo;
        }

        public void setAtivo(Boolean ativo) {
            this.ativo = ativo;
        }

        public String getDescricao() {
            return Descricao;
        }

        public void setDescricao(String descricao) {
            Descricao = descricao;
        }

        public String getNivel() {
            return Nivel;
        }

        public void setNivel(String nivel) {
            Nivel = nivel;
        }

        public String getNextNivel() {
            return NextNivel;
        }

        public void setNextNivel(String nextNivel) {
            NextNivel = nextNivel;
        }
    }


}
