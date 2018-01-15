package br.com.brotolegal.savdatabase.entities;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 04/07/2016.
 */
public class Ocorrencia extends ObjRegister implements KvmSerializable {

    protected String SEQUENCIA;
    protected String CODIGO;
    protected String DESCRICAO;
    protected String DATASOL;
    protected String HORASOL;
    protected String DATAEXE;
    protected String HORAEXE;
    protected String ARQUIVO;
    protected String STATUS; //0-> FINALIZADA //1->Aguardano rtorno //2->FINALIZADA COM SUCESSO
    protected String OBS;
    protected String CODTAREFA;



    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Ocorrencia";

    public Ocorrencia()  {

        super(_OBJETO,"OCORRENCIA");

        loadColunas();

        InicializaFields();

    }

    public Ocorrencia(String SEQUENCIA, String CODIGO, String DESCRICAO, String DATASOL, String HORASOL, String DATAEXE, String HORAEXE, String ARQUIVO, String STATUS, String OBS,String CODTAREFA) {

        super(_OBJETO,"OCORRENCIA");

        loadColunas();

        InicializaFields();

        this.SEQUENCIA = SEQUENCIA;
        this.CODIGO    = CODIGO;
        this.DESCRICAO = DESCRICAO;
        this.DATASOL   = DATASOL;
        this.HORASOL   = HORASOL;
        this.DATAEXE   = DATAEXE;
        this.HORAEXE   = HORAEXE;
        this.ARQUIVO   = ARQUIVO;
        this.STATUS    = STATUS;
        this.OBS       = OBS;
        this.CODTAREFA = CODTAREFA;
    }

    public String getSEQUENCIA() {
        return SEQUENCIA;
    }

    public void setSEQUENCIA(String SEQUENCIA) {
        this.SEQUENCIA = SEQUENCIA;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public String getDATASOL() {
        return DATASOL;
    }

    public void setDATASOL(String DATASOL) {
        this.DATASOL = DATASOL;
    }

    public String getHORASOL() {
        return HORASOL;
    }

    public void setHORASOL(String HORASOL) {
        this.HORASOL = HORASOL;
    }

    public String getDATAEXE() {
        return DATAEXE;
    }

    public void setDATAEXE(String DATAEXE) {
        this.DATAEXE = DATAEXE;
    }

    public String getHORAEXE() {
        return HORAEXE;
    }

    public void setHORAEXE(String HORAEXE) {
        this.HORAEXE = HORAEXE;
    }

    public String getARQUIVO() {
        return ARQUIVO;
    }

    public void setARQUIVO(String ARQUIVO) {
        this.ARQUIVO = ARQUIVO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getOBS() {
        return OBS;
    }

    public void setOBS(String OBS) {
        this.OBS = OBS;
    }

    public String getStatusDescri(){

        String retorno = "";

        Integer op     = 0;

        try{

            op = Integer.valueOf(this.STATUS);

        }catch (Exception e){

            op = 0;

        }

        switch (op){

            case 0:
                retorno = "TAREFA FINALIZADA";
                break;
            case 1:
                retorno = "TAREFA COM PENDÊNCIA";
                break;
            case 2:
                retorno = "TAREFA FINALIZADA COM PROBLEMAS";
                break;
            case 3:
                retorno = "TAREFA PROCESSANDO ARQUIVO";
                break;
            case 4:
                retorno = "TAREFA TERMINADA COM PROBLEMA";
                break;
             default:
                retorno = "TAREFA FINALIZADA";
                break;
        }
        return retorno;

    }

    public String getCODTAREFA() {
        return CODTAREFA;
    }

    public void setCODTAREFA(String CODTAREFA) {
        this.CODTAREFA = CODTAREFA;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("SEQUENCIA");
        _colunas.add("CODIGO");
        _colunas.add("DESCRICAO");
        _colunas.add("DATASOL");
        _colunas.add("HORASOL");
        _colunas.add("DATAEXE");
        _colunas.add("HORAEXE");
        _colunas.add("ARQUIVO");
        _colunas.add("STATUS");
        _colunas.add("OBS");
        _colunas.add("CODTAREFA");

    }

    @Override
    public Object getProperty(int i) {

        String fieldName = "";

        if (i < 0 || i > _colunas.size() - 1) {

            return (null);

        } else {

            fieldName = _colunas.get(i);

        }

        return getFieldByName(fieldName);

    }


    @Override
    public int getPropertyCount() {
        return _colunas.size();
    }


    @Override
    public void setProperty(int index, Object o) {

        String fieldName = "";

        if (index < 0 || index > _colunas.size() - 1) {

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
