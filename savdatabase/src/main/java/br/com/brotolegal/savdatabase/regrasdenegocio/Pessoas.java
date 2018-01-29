package br.com.brotolegal.savdatabase.regrasdenegocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falconi on 29/01/2018.
 */
public class Pessoas {

    private Pessoa pessoa;
    private List<Pessoa> lsPessoas;


    public Pessoas() {

        load();

    }

    public List<Pessoa> getLsPessoas() {

        return lsPessoas;

    }

    public int getIndice(String value) {

        int indice = 0;

        int retorno = -1;

        String chave;

        for (Pessoa pessoa : lsPessoas) {

            chave = pessoa.getDESCRICAO();

            if (chave.equals(value)) {

                retorno = indice;

            }

            indice++;

        }


        return retorno;


    }

    public int getIndiceLetra(String value) {

        int indice = 0;

        int retorno = -1;

        String chave;

        for (Pessoa pessoa : lsPessoas) {

            chave = pessoa.getCODIGO();

            if (chave.equals(value)) {

                retorno = indice;

            }

            indice++;

        }


        return retorno;


    }


    public String getPessoa(int ind) {

        String retorno = "";

        try {

            Pessoa pes = lsPessoas.get(ind);

            retorno = pes.getDESCRICAO();

        } catch (Exception e) {


            retorno = "";


        }

        return retorno;


    }


    private void load()

    {

        lsPessoas = new ArrayList<Pessoa>();

        lsPessoas.add(new Pessoa("F", "CONSUMIDOR FINAL"));
        lsPessoas.add(new Pessoa("L", "PRODUTO RURAL"));
        lsPessoas.add(new Pessoa("R", "REVENDEDOR"));
        lsPessoas.add(new Pessoa("S", "SOLIDARIO"));
        lsPessoas.add(new Pessoa("X", "EXPORTAÇÃO"));

    }

}