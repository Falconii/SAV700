package br.com.brotolegal.savdatabase.entities;

import android.util.Log;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.regrasdenegocio.ValidaCNPJ;

/**
 * Created by Falconi on 29/09/2016.
 */
public class Prospeccao extends ObjRegister {


    protected String ID;
    protected String PRECLIENTE;
    protected String PROTHEUS;
    protected String STATUS;
    protected String DATA;
    protected String RAZAO;
    protected String FANTASIA;
    protected String CNPJ;
    protected String IE;
    protected String LOGRADOURO;
    protected String ENDERECO;
    protected String NRO;
    protected String COMPLEMENTO;
    protected String ESTADO;
    protected String CODCIDADE;
    protected String CIDADE;
    protected String BAIRRO;
    protected String DDD;
    protected String TELEFONE;
    protected String CEP;
    protected String CONTATO;
    protected String EMAIL;
    protected String OBS;
    protected String VEND;



    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Prospeccao";

    public Prospeccao() {

        super(_OBJETO,"PROSPECCAO");

        loadColunas();

        InicializaFields();
    }

    public Prospeccao(String ID, String PRECLIENTE, String PROTHEUS, String STATUS, String DATA, String RAZAO, String FANTASIA, String CNPJ, String IE, String LOGRADOURO, String ENDERECO, String NRO, String COMPLEMENTO, String ESTADO, String CODCIDADE, String CIDADE, String BAIRRO, String DDD, String TELEFONE, String CEP, String CONTATO, String EMAIL, String OBS, String VEND) {

        super(_OBJETO,"PROSPECCAO");

        loadColunas();

        InicializaFields();

        this.ID = ID;
        this.PRECLIENTE = PRECLIENTE;
        this.PROTHEUS = PROTHEUS;
        this.STATUS = STATUS;
        this.DATA = DATA;
        this.RAZAO = RAZAO;
        this.FANTASIA = FANTASIA;
        this.CNPJ = CNPJ;
        this.IE = IE;
        this.LOGRADOURO = LOGRADOURO;
        this.ENDERECO = ENDERECO;
        this.NRO = NRO;
        this.COMPLEMENTO = COMPLEMENTO;
        this.ESTADO = ESTADO;
        this.CODCIDADE = CODCIDADE;
        this.CIDADE = CIDADE;
        this.BAIRRO = BAIRRO;
        this.DDD = DDD;
        this.TELEFONE = TELEFONE;
        this.CEP = CEP;
        this.CONTATO = CONTATO;
        this.EMAIL = EMAIL;
        this.OBS = OBS;
        this.VEND = VEND;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPRECLIENTE() {
        return PRECLIENTE;
    }

    public void setPRECLIENTE(String PRECLIENTE) {
        this.PRECLIENTE = PRECLIENTE;
    }

    public String getPROTHEUS() {
        return PROTHEUS;
    }

    public void setPROTHEUS(String PROTHEUS) {
        this.PROTHEUS = PROTHEUS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getRAZAO() {
        return RAZAO;
    }

    public void setRAZAO(String RAZAO) {
        this.RAZAO = RAZAO;
    }

    public String getFANTASIA() {
        return FANTASIA;
    }

    public void setFANTASIA(String FANTASIA) {
        this.FANTASIA = FANTASIA;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getIE() {
        return IE;
    }

    public void setIE(String IE) {
        this.IE = IE;
    }

    public String getLOGRADOURO() {
        return LOGRADOURO;
    }

    public void setLOGRADOURO(String LOGRADOURO) {
        this.LOGRADOURO = LOGRADOURO;
    }

    public String getENDERECO() {
        return ENDERECO;
    }

    public void setENDERECO(String ENDERECO) {
        this.ENDERECO = ENDERECO;
    }

    public String getNRO() {
        return NRO;
    }

    public void setNRO(String NRO) {
        this.NRO = NRO;
    }

    public String getCOMPLEMENTO() {
        return COMPLEMENTO;
    }

    public void setCOMPLEMENTO(String COMPLEMENTO) {
        this.COMPLEMENTO = COMPLEMENTO;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getCODCIDADE() {
        return CODCIDADE;
    }

    public void setCODCIDADE(String CODCIDADE) {
        this.CODCIDADE = CODCIDADE;
    }

    public String getCIDADE() {
        return CIDADE;
    }

    public void setCIDADE(String CIDADE) {
        this.CIDADE = CIDADE;
    }

    public String getBAIRRO() {
        return BAIRRO;
    }

    public void setBAIRRO(String BAIRRO) {
        this.BAIRRO = BAIRRO;
    }

    public String getDDD() {
        return DDD;
    }

    public void setDDD(String DDD) {
        this.DDD = DDD;
    }

    public String getTELEFONE() {
        return TELEFONE;
    }

    public void setTELEFONE(String TELEFONE) {
        this.TELEFONE = TELEFONE;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getCONTATO() {
        return CONTATO;
    }

    public void setCONTATO(String CONTATO) {
        this.CONTATO = CONTATO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getOBS() {
        return OBS;
    }

    public void setOBS(String OBS) {
        this.OBS = OBS;
    }

    public String getVEND() {
        return VEND;
    }

    public void setVEND(String VEND) {
        this.VEND = VEND;
    }

    public String get_STATUS() {

        String retorno = "";

        switch (this.STATUS) {

            case "0": {

                retorno = "INCOMPLETO";

                break;

            }
            case "1": {

                retorno = "SINCRONIZAÇÃO";

                break;

            }
            case "2": {

                retorno = "SINCRONIZADO";

                break;

            }

            default: {

                break;

            }


        }

    return retorno;


    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();
        _colunas.add("ID");
        _colunas.add("PRECLIENTE");
        _colunas.add("PROTHEUS");
        _colunas.add("STATUS");
        _colunas.add("DATA");
        _colunas.add("RAZAO");
        _colunas.add("FANTASIA");
        _colunas.add("CNPJ");
        _colunas.add("IE");
        _colunas.add("LOGRADOURO");
        _colunas.add("ENDERECO");
        _colunas.add("NRO");
        _colunas.add("COMPLEMENTO");
        _colunas.add("ESTADO");
        _colunas.add("CODCIDADE");
        _colunas.add("CIDADE");
        _colunas.add("BAIRRO");
        _colunas.add("DDD");
        _colunas.add("TELEFONE");
        _colunas.add("CEP");
        _colunas.add("CONTATO");
        _colunas.add("EMAIL");
        _colunas.add("OBS");
        _colunas.add("VEND");


    }


    public Boolean Validadador(String campo){

        int indice;

        indice = getIndiceByNameColunas(campo);

        if (indice == -1) return true;

        return  ValidaCampo(indice);
    }


    public Boolean ValidaAll(){

        boolean retorno;

        int indice;

        retorno = true;

        try {
            for(indice=0;indice <= _colunas.size()-1;indice++){

                if (!ValidaCampo(indice)){

                    retorno = false;

                };

            }

        } catch (Exception e){

            Log.i("ERRO", e.getMessage());

            retorno = false;
        }

        return retorno;
    }

    private Boolean  ValidaCampo(int index)  {

        String SEM_VALIDACAO     = "#ID#STATUS#PRECLIENTE#PROTHEUS#IM#COMPLEMENTO#CONTATO#OBS#EMAIL";

        String VALIDACAO_NAONULO = "#LOGRADOURO#ENDERECO#NRO#BAIRRO#CODCIDADE#CIDADE#ESTADO#CEP#DDD#TELEFONE#EMAIL#OBS#CONTATO";


        if (index > _colunas.size()-1){

            return false;
        }

        String field =  _colunas.get(index);


        /* CAMPOS SEM VALIDACAO */
        if (SEM_VALIDACAO.contains('#'+field)) {

            return true;

        }


        if (VALIDACAO_NAONULO.contains('#'+field)) {

            if (((String) getFieldByName(field)).trim().isEmpty()) {

                return false;

            }
        }

        if (field.equals("RAZAO")) {

            RAZAO.replaceAll("\\n|\\r", " ");

            if (RAZAO.equals("")) return false;

            if (RAZAO.length() > 40) return false;

        }


        	/* FANTASIA */
        if (field.equals("FANTASIA")) {
            FANTASIA.replaceAll("\\n|\\r", " ");

            if (FANTASIA.trim().equals("")) return false;

            if (FANTASIA.trim().length() > 20) return false;
        }


        if (field.equals("CNPJ")) {

            if (CNPJ.equals("")) return true;

            return ValidaCNPJ.isCNPJ(CNPJ.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", ""));

        }

            /* IE */
        if (field.equals("IE")) {

            if (IE.equals("")) return true;

            return true; //ValidaIE.isIE(IE.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", ""), ESTADO);

        }

        return true;

    }


}
