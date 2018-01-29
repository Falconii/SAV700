package br.com.brotolegal.savdatabase.regrasdenegocio;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.brotolegal.savdatabase.entities.PreCliente;

/**
 * Created by Falconi on 29/01/2018.
 */

public class ValidadorPreCliente {

    PreCliente preCliente;

    public ValidadorPreCliente(PreCliente preCliente) {

        this.preCliente = preCliente;


    }

    public Boolean Validadador(Context context,String campo){

        int indice;

        indice = preCliente.getIndiceByNameColunas(campo);

        if (indice == -1) return true;

        return  ValidaCampo(context,indice);
    }

    public Boolean ValidaAll(Context context){

        boolean retorno;

        int indice;

        retorno = true;

        try {
            for(indice=0;indice <= preCliente.get_ColunasSize()-1;indice++){

                if (!ValidaCampo(context,indice)){

                    retorno = false;

                };

            }

        } catch (Exception e){

            Log.i("ERRO", e.getMessage());

            retorno = false;
        }

        return retorno;
    }

    private Boolean ValidaCampo(Context context,int index)  {

        String SEM_VALIDACAO     = "#ERRO#MSGERRO#ID#OPERACAO#STATUS#CODIGO#RG#IM#COMPLEMENTO#CELULAR#HOMEPAGE#EMAIL";

        String VALIDACAO_NAONULO = "#PESSOA#LOGRADOURO#ENDERECO#NRO#BAIRRO#CODCIDADE#CIDADE#ESTADO#CEP#DDD#TELEFONE#EMAILNFE#CLIENTEENTREGA#BOLETO#OPSIMPLES#ISENTOST#ICMS";


        if (index > preCliente.get_ColunasSize()-1){

            return false;
        }

        String field =  preCliente.get_colunasByIndice(index);


        /* CAMPOS SEM VALIDACAO */
        if (SEM_VALIDACAO.contains('#'+field)) {

            return true;

        }


        if (VALIDACAO_NAONULO.contains('#'+field)) {

            if (((String) preCliente.getFieldByName(field)).trim().isEmpty()) {

                return false;

            }
        }

        if (field.equals("RAZAO")) {

            preCliente.getRAZAO().replaceAll("\\n|\\r", " ");

            if (preCliente.getRAZAO().equals("")) return false;

            if (preCliente.getRAZAO().length() > 50) return false;

        }


        	/* FANTASIA */
        if (field.equals("FANTASIA")) {

            preCliente.getFANTASIA().replaceAll("\\n|\\r", " ");

            if (preCliente.getFANTASIA().trim().equals("")) return false;

            if (preCliente.getFANTASIA().trim().length() > 20) return false;
        }


        if (field.equals("CNPJ")) {

            if (preCliente.getCNPJ().equals("")) return false;

            return ValidaCNPJ.isCNPJ(preCliente.getCNPJ().replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", ""));

        }

            /* IE */
        if (field.equals("IE")) {

            if (preCliente.getIE().equals("")) return false;

            return true; //ValidaIE.isIE(IE.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", ""), ESTADO);

        }

        	/* FUNDACAO */

        if (field.equals("FUNDACAO")) {

            if (preCliente.getFUNDACAO().equals("")) return false;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            sdf.setLenient(false);

            try {

                Date data = sdf.parse(preCliente.getFUNDACAO());

                return true;

            } catch (java.text.ParseException e) {

                return false;

            }

        }


        	/* CANAL */
        if (field.equals("CANAL")) {

            if (preCliente.getCANAL().equals("")) return false;

        }

        	/* REDE */
        if (field.equals("REDE")) {

            if (preCliente.getREDE().equals("")) return false;

        }

        	/* POLITICA*/

        if (field.equals("POLITICA")) {
            if (preCliente.getPOLITICA().equals("")) return false;

        }

        	/*  TABPRECO */

        if (field.equals("TABPRECO")) {


            if (preCliente.getTABPRECO().equals("")) return false;


        }

        if (field.equals("CONDPAGTO")) {

            if (preCliente.getCONDPAGTO().equals("")) return false;

        }

        if (field.equals("FUNDACAO")) {

            if (preCliente.getFUNDACAO().equals("")) return false;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            sdf.setLenient(false);

            try {

                Date data = sdf.parse(preCliente.getFUNDACAO());

                return true;

            } catch (java.text.ParseException e) {

                return false;

            }

        }
        if (field.equals("LIMITE")) {

            if (preCliente.getLIMITE() == 0) return false;

        }


        if (field.equals("HORARECEB")) {

            int hora = 0;
            int minu = 0;

            if (preCliente.getHORARECEB().equals("00:00")) return true;

            try {

                hora = Integer.valueOf(preCliente.getHORARECEB().substring(0, 2));

                minu = Integer.valueOf(preCliente.getHORARECEB().substring(3, 5));

                if (hora < 0 || hora > 23){

                    return false;
                }

                if (minu < 0 || minu > 59){

                    return false;
                }

                return true;

            } catch (Exception e){


                return false;

            }

        }


        if (field.equals("CIENTE")) {

            if (!preCliente.getCIENTE().equals("SIM")) return false;

        }

        return true;

    }




}
