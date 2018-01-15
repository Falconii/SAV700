package br.com.brotolegal.savdatabase.entities;

import android.util.Log;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 25/11/2015.
 */
public class PreAcordo extends ObjRegister  implements KvmSerializable  {


    protected String CODMOBILE;
    protected String NUM;
    protected String DATA;
    protected String TIPO;
    protected String DATAINI;
    protected String DATAFIM;
    protected String DATAPAGTO;
    protected String CLIENTE;
    protected String LOJA;
    protected String CODVEND;
    protected String NOMVEND;
    protected String DESCRIC;
    protected String TIPOPAG;
    protected Float SLDINI;
    protected String OBS;
    protected String STATUS;
    protected String HISTLIB;
    protected String CODVERB;
    protected String MENSAGEM;
    protected String REDE;


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.PreAcordo";

    protected String _RAZAO;
    protected String _CNPJ;
    protected String _IE;
    protected String _VERBA;
    protected String _REDE;
    protected List<String[]> _formaspagto;



    public PreAcordo() {

        super(_OBJETO, "PREACORDO");

        loadColunas();

        InicializaFields();

        _RAZAO = "";
        _CNPJ  = "";
        _IE    = "";
        _VERBA = "";
        _REDE  = "";

        loadFormas();


    }

    public PreAcordo(String CODMOBILE, String NUM, String DATA, String TIPO, String DATAINI, String DATAFIM, String DATAPAGTO, String CLIENTE, String LOJA, String CODVEND, String NOMVEND, String DESCRIC, String TIPOPAG, Float SLDINI, String OBS, String STATUS, String HISTLIB, String CODVERB, String MENSAGEM, String REDE) {

        super(_OBJETO, "PREACORDO");

        loadColunas();

        InicializaFields();

        this.CODMOBILE  = CODMOBILE;
        this.NUM        = NUM;
        this.DATA       = DATA;
        this.TIPO       = TIPO;
        this.DATAINI    = DATAINI;
        this.DATAFIM    = DATAFIM;
        this.DATAPAGTO  = DATAPAGTO;
        this.CLIENTE    = CLIENTE;
        this.LOJA       = LOJA;
        this.CODVEND    = CODVEND;
        this.NOMVEND    = NOMVEND;
        this.DESCRIC    = DESCRIC;
        this.TIPOPAG    = TIPOPAG;
        this.SLDINI     = SLDINI;
        this.OBS        = OBS;
        this.STATUS     = STATUS;
        this.HISTLIB    = HISTLIB;
        this.CODVERB    = CODVERB;
        this.MENSAGEM   = MENSAGEM;
        this.REDE       = REDE;

        _RAZAO = "";
        _CNPJ  = "";
        _IE    = "";
        _VERBA = "";
        _REDE  = "";

        loadFormas();
    }

    public void  complemento(String _RAZAO, String _CNPJ, String _IE, String _VERBA, String _REDE) {

        this._RAZAO = _RAZAO;
        this._CNPJ  = _CNPJ;
        this._IE    = _IE;
        this._VERBA = _VERBA;
        this._REDE  = _REDE;
    }

    public String getCODMOBILE() {
        return CODMOBILE;
    }

    public void setCODMOBILE(String CODMOBILE) {
        this.CODMOBILE = CODMOBILE;
    }

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getDATAINI() {
        return DATAINI;
    }

    public void setDATAINI(String DATAINI) {
        this.DATAINI = DATAINI;
    }

    public String getDATAFIM() {
        return DATAFIM;
    }

    public void setDATAFIM(String DATAFIM) {
        this.DATAFIM = DATAFIM;
    }

    public String getDATAPAGTO() {
        return DATAPAGTO;
    }

    public void setDATAPAGTO(String DATAPAGTO) {
        this.DATAPAGTO = DATAPAGTO;
    }

    public String getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(String CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public String getLOJA() {
        return LOJA;
    }

    public void setLOJA(String LOJA) {
        this.LOJA = LOJA;
    }

    public String getCODVEND() {
        return CODVEND;
    }

    public void setCODVEND(String CODVEND) {
        this.CODVEND = CODVEND;
    }

    public String getNOMVEND() {
        return NOMVEND;
    }

    public void setNOMVEND(String NOMVEND) {
        this.NOMVEND = NOMVEND;
    }

    public String getDESCRIC() {
        return DESCRIC;
    }

    public void setDESCRIC(String DESCRIC) {
        this.DESCRIC = DESCRIC;
    }

    public String getTIPOPAG() {
        return TIPOPAG;
    }

    public void setTIPOPAG(String TIPOPAG) {
        this.TIPOPAG = TIPOPAG;
    }

    public Float getSLDINI() {
        return SLDINI;
    }

    public void setSLDINI(Float SLDINI) {
        this.SLDINI = SLDINI;
    }

    public String getOBS() {
        return OBS;
    }

    public void setOBS(String OBS) {
        this.OBS = OBS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getHISTLIB() {
        return HISTLIB;
    }

    public void setHISTLIB(String HISTLIB) {
        this.HISTLIB = HISTLIB;
    }

    public String getCODVERB() {
        return CODVERB;
    }

    public void setCODVERB(String CODVERB) {
        this.CODVERB = CODVERB;
    }

    public String get_RAZAO() {
        return _RAZAO;
    }

    public void set_RAZAO(String _RAZAO) {
        this._RAZAO = _RAZAO;
    }

    public String get_CNPJ() {
        return _CNPJ;
    }

    public void set_CNPJ(String _CNPJ) {
        this._CNPJ = _CNPJ;
    }

    public String get_IE() {
        return _IE;
    }

    public void set_IE(String _IE) {
        this._IE = _IE;
    }

    public String get_VERBA() {
        return _VERBA;
    }

    public void set_VERBA(String _VERBA) {
        this._VERBA = _VERBA;
    }

    public String getMENSAGEM() {
        return MENSAGEM;
    }

    public void setMENSAGEM(String MENSAGEM) {
        this.MENSAGEM = MENSAGEM;
    }

    public String get_REDE() {
        return _REDE;
    }

    public void set_REDE(String _REDE) {
        this._REDE = _REDE;
    }

    public List<String[]> get_formaspagto() {

        return _formaspagto;

    }

    public String get_FormaPagto(){

        String retorno = "";

        if (this.TIPOPAG == null || this.TIPOPAG.isEmpty()) return retorno;

        for(String[] st : _formaspagto){

            if (st[0].equals(this.getTIPOPAG())){

                return st[1];

            }

        }

        return retorno;


    }

    public String get_Status(){

        String retorno = "";

        if (this.STATUS == null || this.STATUS.isEmpty()) return retorno;

        switch (this.STATUS.charAt(0)){

            case '0':retorno = "Incompleto";break;
            case '1':retorno = "Pronto Para Transmitir";break;
            case '2':retorno = "GA";break;
            case '3':retorno = "GNV";break;
            case '4':retorno = "DIRETORIA";break;
            case '5':retorno = "APOIO";break;
            case '6':retorno = "APROVADO";break;
            case '7':retorno = "REPROVADO";break;
            case '8':retorno = "EXCLUÍDO";break;

            default:retorno = "";

        }

        return retorno;
    }

    public String getREDE() {
        return REDE;
    }

    public void setREDE(String REDE) {
        this.REDE = REDE;
    }

    public void setStatusByTOTVS(String value){

        if (value == null || value.length() == 0){

            //não altera o status

        }

        switch (value.charAt(0)){

            case 'S':this.STATUS = "2"; break;
            case 'G':this.STATUS = "3"; break;
            case 'D':this.STATUS = "4"; break;
            case 'A':this.STATUS = "5"; break;
            case 'L':this.STATUS = "6"; break;
            case 'B':this.STATUS = "7"; break;
            case 'X':this.STATUS = "8"; break;

        }


    }
    private void loadFormas(){

        _formaspagto = new ArrayList<>();

        _formaspagto.add(new String[] {"" ,"Defina A Forma De Pagamento Por Favor"});
        _formaspagto.add(new String[] {"S","ABATIMENTOS"});
        _formaspagto.add(new String[] {"A","AMBOS (DESCONTO/BONIFICAÇÃO)"});
        _formaspagto.add(new String[] {"Q","BOLETO BANCÁRIO"});
        _formaspagto.add(new String[] {"B","BONIFICACAO"});
        _formaspagto.add(new String[] {"D","DESCONTO NO PRODUTO"});
        _formaspagto.add(new String[] {"T","DEPÓSITO BANCÁRIO"});



    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("CODMOBILE");
        _colunas.add("NUM");
        _colunas.add("DATA");
        _colunas.add("TIPO");
        _colunas.add("DATAINI");
        _colunas.add("DATAFIM");
        _colunas.add("DATAPAGTO");
        _colunas.add("CLIENTE");
        _colunas.add("LOJA");
        _colunas.add("CODVEND");
        _colunas.add("NOMVEND");
        _colunas.add("DESCRIC");
        _colunas.add("TIPOPAG");
        _colunas.add("SLDINI");
        _colunas.add("OBS");
        _colunas.add("STATUS");
        _colunas.add("HISTLIB");
        _colunas.add("CODVERB");
        _colunas.add("MENSAGEM");
        _colunas.add("REDE");


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

                    break;

                };

            }

        } catch (Exception e){

            Log.i("ERRO", e.getMessage());

            retorno = false;
        }

        return retorno;
    }

    private Boolean ValidaCampo(int index)  {

        String SEM_VALIDACAO     = "#NUM#HISTLIB#TIPO#CODVEND#NOMVEND#HISTLIB#STATUS#OBS#LOJA";

        String VALIDACAO_NAONULO = "#CODMOBILE#NUM#DATA#DATAINI#DATAFIM#LOJA#TIPOPAG#CODVERB#DESCRIC";


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


        if (field.equals("CLIENTE")) {

            if (!REDE.equals(""))     return true;

            if (CLIENTE.equals(""))   return false;

            if (CLIENTE.length() > 6) return false;

        }

        if (field.equals("REDE")) {

            if (!CLIENTE.equals("")) return true;

            if (REDE.equals(""))     return false;

            if (REDE.length() > 6)   return false;

        }


        if (field.equals("SLDINI")) {


            if (SLDINI <= 0){

                return false;
            }

        }

        if (field.equals("CODVERB")) {

            if (CODVERB.equals("")) return false;

            if (CODVERB.length() > 6) return false;

        }


        if (field.equals("DATAINI")) {

            try {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",new Locale("pt", "BR"));

                sdf.setLenient(false);

                sdf.parse(this.DATAINI);

                Date  emissao  = sdf.parse(this.DATA);

                if (sdf.parse(this.DATAINI).compareTo(emissao) < 0){

                    return false;

                }

            } catch (java.text.ParseException e) {

                return false;

            }

        }

        if (field.equals("DATAFIM")) {

            try {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",new Locale("pt", "BR"));

                sdf.setLenient(false);

                sdf.parse(this.DATAFIM);

                Date  dtinicial  = sdf.parse(this.DATAINI);

                if (sdf.parse(this.DATAINI).compareTo(dtinicial) < 0){

                    return false;

                }


            } catch (java.text.ParseException e) {

                return false;

            }

        }


        if (field.equals("DATAPAGTO")) {

            if ((this.TIPOPAG.isEmpty()) || !this.TIPOPAG.equals("T")) {

                this.setDATAPAGTO("");

            } else {

                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                    sdf.setLenient(false);

                    sdf.parse(this.DATAPAGTO);

                    Date  emissao  = sdf.parse(this.DATA);

                    if (emissao.compareTo(sdf.parse(this.DATAPAGTO)) > 0){

                        return false;

                    }


                } catch (java.text.ParseException e) {

                    return false;

                }

            }

        }

        if (field.equals("TIPOPAG")) {

            if (TIPOPAG.equals("")) return false;

        }


        return true;

    }


    @Override
    public Object getProperty(int i) {
        String fieldName = "";

        if (i < 0 || i > getPropertyCount()) {

            return (null);

        } else {

            fieldName = _colunas.get(i);

        }

        return getFieldByName(fieldName).toString();

    }

    @Override
    public int getPropertyCount() {

        /* Não Usar _colunas.size() - 1 */

        return _colunas.size();
    }

    @Override
    public void setProperty(int index, Object o) {

        String fieldName = "";

        if (index < 0 || index > getPropertyCount()) {

            fieldName = null;

        } else {

            fieldName = _colunas.get(index);

        }

        if (fieldName != null) {

            setFieldByName(fieldName, o.toString());

        }


    }

    @Override
    public void getPropertyInfo(int index, Hashtable hashtable, PropertyInfo info) {

        info.type = PropertyInfo.STRING_CLASS;
        info.name = _colunas.get(index);

    }

}
