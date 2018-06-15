package br.com.brotolegal.savdatabase.entities;

import android.util.Log;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 27/09/2016.
 */
public class Agendamento extends ObjRegister implements KvmSerializable {

    protected String FILIAL;
    protected String ID;
    protected String CLIENTE;
    protected String LOJA;
    protected String DATA;
    protected String HORA;
    protected String MOBILE;
    protected String VEND;
    protected String PEDFIL;
    protected String PEDIDO;
    protected String MOTIVONVENDA;
    protected String MOTIVONVISITA;
    protected String FORMAVENDA;
    protected String ORIGEMVENDA;
    protected String SITUACAO;
    protected String TIPO;
    protected String OBS;
    protected String LATITUDE;
    protected String LONGITUDE;
    protected String DTDIGIT;
    protected String HRDIGIT;

    protected String _RAZAO;
    protected String _DESCRICAOMOTIVONAOVENDA;
    protected String _DESCRICAOMOTIVONAOVISITA;
    protected String _TIPO;





    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Agendamento";

    public Agendamento() {

        super(_OBJETO,"AGENDAMENTO");

        loadColunas();

        InicializaFields();

        this._RAZAO                    = "";
        this._DESCRICAOMOTIVONAOVENDA  = "";
        this._DESCRICAOMOTIVONAOVISITA = "";
        this._TIPO                     = "";

    }

    public Agendamento(String FILIAL, String ID, String CLIENTE, String LOJA, String DATA, String HORA, String MOBILE, String VEND, String PEDFIL, String PEDIDO, String MOTIVONVENDA, String MOTIVONVISITA, String FORMAVENDA, String ORIGEMVENDA, String SITUACAO, String TIPO, String OBS, String LATITUDE, String LONGITUDE, String DTDIGIT, String HRDIGIT) {

        super(_OBJETO,"AGENDAMENTO");

        loadColunas();

        InicializaFields();

        this.FILIAL = FILIAL;
        this.ID = ID;
        this.CLIENTE = CLIENTE;
        this.LOJA = LOJA;
        this.DATA = DATA;
        this.HORA = HORA;
        this.MOBILE = MOBILE;
        this.VEND = VEND;
        this.PEDFIL = PEDFIL;
        this.PEDIDO = PEDIDO;
        this.MOTIVONVENDA = MOTIVONVENDA;
        this.MOTIVONVISITA = MOTIVONVISITA;
        this.FORMAVENDA = FORMAVENDA;
        this.ORIGEMVENDA = ORIGEMVENDA;
        this.SITUACAO = SITUACAO;
        this.TIPO = TIPO;
        this.OBS = OBS;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.DTDIGIT = DTDIGIT;
        this.HRDIGIT = HRDIGIT;

        this._RAZAO                    = "";
        this._DESCRICAOMOTIVONAOVENDA  = "";
        this._DESCRICAOMOTIVONAOVISITA = "";
        this._TIPO                     = "";

    }

    public void Virtuais(String _RAZAO, String _DESCRICAOMOTIVONAOVENDA, String _DESCRICAOMOTIVONAOVISITA) {

        this._RAZAO                    = _RAZAO;
        this._DESCRICAOMOTIVONAOVENDA  = _DESCRICAOMOTIVONAOVENDA;
        this._DESCRICAOMOTIVONAOVISITA = _DESCRICAOMOTIVONAOVISITA;

    }

    public String getFILIAL() {
        return FILIAL;
    }

    public void setFILIAL(String FILIAL) {
        this.FILIAL = FILIAL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getHORA() {
        return HORA;
    }

    public void setHORA(String HORA) {
        this.HORA = HORA;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getVEND() {
        return VEND;
    }

    public void setVEND(String VEND) {
        this.VEND = VEND;
    }

    public String getPEDFIL() {
        return PEDFIL;
    }

    public void setPEDFIL(String PEDFIL) {
        this.PEDFIL = PEDFIL;
    }

    public String getPEDIDO() {
        return PEDIDO;
    }

    public void setPEDIDO(String PEDIDO) {
        this.PEDIDO = PEDIDO;
    }

    public String getMOTIVONVENDA() {
        return MOTIVONVENDA;
    }

    public void setMOTIVONVENDA(String MOTIVONVENDA) {
        this.MOTIVONVENDA = MOTIVONVENDA;
    }

    public String getMOTIVONVISITA() {
        return MOTIVONVISITA;
    }

    public void setMOTIVONVISITA(String MOTIVONVISITA) {
        this.MOTIVONVISITA = MOTIVONVISITA;
    }

    public String getFORMAVENDA() {
        return FORMAVENDA;
    }

    public void setFORMAVENDA(String FORMAVENDA) {
        this.FORMAVENDA = FORMAVENDA;
    }

    public String getORIGEMVENDA() {
        return ORIGEMVENDA;
    }

    public void setORIGEMVENDA(String ORIGEMVENDA) {
        this.ORIGEMVENDA = ORIGEMVENDA;
    }

    public String getSITUACAO() {
        return SITUACAO;
    }

    public void setSITUACAO(String SITUACAO) {
        this.SITUACAO = SITUACAO;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getOBS() {
        return OBS;
    }

    public void setOBS(String OBS) {
        this.OBS = OBS;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getDTDIGIT() {
        return DTDIGIT;
    }

    public void setDTDIGIT(String DTDIGIT) {
        this.DTDIGIT = DTDIGIT;
    }

    public String getHRDIGIT() {
        return HRDIGIT;
    }

    public void setHRDIGIT(String HRDIGIT) {
        this.HRDIGIT = HRDIGIT;
    }

    public String get_Situacao() {

        if (this.getSITUACAO() == null || this.getSITUACAO().length() == 0){

            return "";
        }

        String retorno = "";

        switch (this.getSITUACAO().charAt(0)){

            case 'T':retorno =  "A TRANSMITIR";break;

            case 'E':retorno =  "ENCERRADA"   ;break;

            default:retorno  =  "PENDENTE"    ;break;

        }

        return retorno;

    }

    public String get_RAZAO() {
        return _RAZAO;
    }

    public void set_RAZAO(String _RAZAO) {
        this._RAZAO = _RAZAO;
    }

    public String get_DESCRICAOMOTIVONAOVENDA() {
        return _DESCRICAOMOTIVONAOVENDA;
    }

    public void set_DESCRICAOMOTIVONAOVENDA(String _DESCRICAOMOTIVONAOVENDA) {
        this._DESCRICAOMOTIVONAOVENDA = _DESCRICAOMOTIVONAOVENDA;
    }

    public String get_DESCRICAOMOTIVONAOVISITA() {
        return _DESCRICAOMOTIVONAOVISITA;
    }

    public void set_DESCRICAOMOTIVONAOVISITA(String _DESCRICAOMOTIVONAOVISITA) {
        this._DESCRICAOMOTIVONAOVISITA = _DESCRICAOMOTIVONAOVISITA;
    }

    public String get_TIPO() {

        String retorno = "AVULSO";

        if (this.TIPO == null){

            return retorno;

        }


        if (this.TIPO.trim().equals("R")){

            retorno = "ROTEIRO";

        } else {

            retorno = "AVULSO";

        }

        return  retorno;

    }

    public String _motivo(){

        String retorno = "";


        if (!this._DESCRICAOMOTIVONAOVENDA.trim().isEmpty()){

            retorno = "NÃO VENDA-"+this.MOTIVONVENDA+"-"+this._DESCRICAOMOTIVONAOVENDA;

        }

        if (!this._DESCRICAOMOTIVONAOVISITA.trim().isEmpty()) {

            retorno = "NÃO VISITA-"+this.MOTIVONVISITA+"-"+this._DESCRICAOMOTIVONAOVISITA;

        }


        return retorno;

    }



    @Override
    public void loadColunas() {


        _colunas = new ArrayList<String>();
        _colunas.add("FILIAL");
        _colunas.add("ID");
        _colunas.add("CLIENTE");
        _colunas.add("LOJA");
        _colunas.add("DATA");
        _colunas.add("HORA");
        _colunas.add("MOBILE");
        _colunas.add("VEND");
        _colunas.add("PEDFIL");
        _colunas.add("PEDIDO");
        _colunas.add("MOTIVONVENDA");
        _colunas.add("MOTIVONVISITA");
        _colunas.add("FORMAVENDA");
        _colunas.add("ORIGEMVENDA");
        _colunas.add("SITUACAO");
        _colunas.add("TIPO");
        _colunas.add("OBS");
        _colunas.add("LATITUDE");
        _colunas.add("LONGITUDE");
        _colunas.add("DTDIGIT");
        _colunas.add("HRDIGIT");




    }
    /*
     *
     * (non-Javadoc)
     * @see org.ksoap2.serialization.KvmSerializable#getProperty(int)
     *
     *
     */
    @Override
    public Object getProperty(int index) {

        Object retorno = null;

        try{

            String fieldname ;

            fieldname = _colunas.get(index);

            if (getTypeByName(fieldname).equals(ObjRegister._string)){


                retorno = (String)  getFieldByName(fieldname);

            }
            if (getTypeByName(fieldname).equals(ObjRegister._float)){

                retorno = String.valueOf((Float) getFieldByName(fieldname));

            }
            if (getTypeByName(fieldname).equals(ObjRegister._integer)){

                retorno = String.valueOf((Integer)  getFieldByName(fieldname));

            }
            if (getTypeByName(fieldname).equals(ObjRegister._long)){

                retorno = String.valueOf((Long)  getFieldByName(fieldname));

            }


        } catch (Exception e){

            Log.i("KSOAP", e.getMessage());

        }

        return retorno;

    }

    @Override
    public int getPropertyCount() {

        return _colunas.size();



    }


    @Override
    public void getPropertyInfo(int  index, Hashtable arg1, PropertyInfo info) {

        String fieldname ;

        fieldname = _colunas.get(index);

        info.name = fieldname;

        info.type = PropertyInfo.STRING_CLASS;

    }


    @Override
    public void setProperty(int index, Object value) {


        try {
            String fieldname ;

            fieldname = _colunas.get(index);

            if (getTypeByName(fieldname).equals(ObjRegister._string)){

                setFieldByName(fieldname, (String) value);

            }
            if (getTypeByName(fieldname).equals(ObjRegister._float)){

                setFieldByName(fieldname, (Float) value);

            }
            if (getTypeByName(fieldname).equals(ObjRegister._integer)){

                setFieldByName(fieldname, (Integer) value);

            }
            if (getTypeByName(fieldname).equals(ObjRegister._long)){

                setFieldByName(fieldname, (Long) value);

            }
        } catch (Exception e){

            Log.i("KSOAP",e.getMessage());

        }

    }
}
