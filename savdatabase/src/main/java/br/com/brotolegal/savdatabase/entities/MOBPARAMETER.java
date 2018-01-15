package br.com.brotolegal.savdatabase.entities;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 23/11/2015.
 */
public  class MOBPARAMETER extends ObjRegister implements KvmSerializable {

    protected String U51_FILIAL  ;
    protected String U51_COD      ;
    protected String U51_REGID    ;
    protected String U51_VERSAO   ;
    protected String U51_BUILD    ;
    protected String U51_IMEI     ;
    protected String U51_CHAPA    ;
    protected String U51_STATUS   ;
    protected String U51_FABRI    ;
    protected String U51_FABID    ;
    protected String U51_MODELO   ;
    protected String U51_MARCA    ;
    protected String U51_SDID     ;
    protected String U50_COD      ;
    protected String U50_NOME     ;
    protected String U50_SENHA    ;
    protected String U50_CODVEN   ;
    protected String U50_NIVEL    ;
    protected String U50_CLASS    ;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.MOBPARAMETER";


    public MOBPARAMETER()  {

        super(_OBJETO,"MOBPARAMETER");

        loadColunas();

        InicializaFields();
    }

    public MOBPARAMETER(String u51_FILIAL, String u51_COD, String u51_REGID, String u51_VERSAO, String u51_BUILD, String u51_IMEI, String u51_CHAPA, String u51_STATUS, String u51_FABRI, String u51_FABID, String u51_MODELO, String u51_MARCA, String u51_SDID, String u50_COD, String u50_NOME, String u50_SENHA, String u50_CODVEN, String u50_NIVEL, String u50_CLASS) {

        super(_OBJETO,"MOBPARAMETER");

        U51_FILIAL = u51_FILIAL;
        U51_COD = u51_COD;
        U51_REGID = u51_REGID;
        U51_VERSAO = u51_VERSAO;
        U51_BUILD = u51_BUILD;
        U51_IMEI = u51_IMEI;
        U51_CHAPA = u51_CHAPA;
        U51_STATUS = u51_STATUS;
        U51_FABRI = u51_FABRI;
        U51_FABID = u51_FABID;
        U51_MODELO = u51_MODELO;
        U51_MARCA = u51_MARCA;
        U51_SDID  = u51_SDID;
        U50_COD = u50_COD;
        U50_NOME = u50_NOME;
        U50_SENHA = u50_SENHA;
        U50_CODVEN = u50_CODVEN;
        U50_NIVEL = u50_NIVEL;
        U50_CLASS = u50_CLASS;

        loadColunas();
    }

    public String getU51_FILIAL() {
        return U51_FILIAL;
    }

    public void setU51_FILIAL(String u51_FILIAL) {
        U51_FILIAL = u51_FILIAL;
    }

    public String getU51_COD() {
        return U51_COD;
    }

    public void setU51_COD(String u51_COD) {
        U51_COD = u51_COD;
    }

    public String getU51_REGID() {
        return U51_REGID;
    }

    public void setU51_REGID(String u51_REGID) {
        U51_REGID = u51_REGID;
    }

    public String getU51_VERSAO() {
        return U51_VERSAO;
    }

    public void setU51_VERSAO(String u51_VERSAO) {
        U51_VERSAO = u51_VERSAO;
    }

    public String getU51_BUILD() {
        return U51_BUILD;
    }

    public void setU51_BUILD(String u51_BUILD) {
        U51_BUILD = u51_BUILD;
    }

    public String getU51_IMEI() {
        return U51_IMEI;
    }

    public void setU51_IMEI(String u51_IMEI) {
        U51_IMEI = u51_IMEI;
    }

    public String getU51_CHAPA() {
        return U51_CHAPA;
    }

    public void setU51_CHAPA(String u51_CHAPA) {
        U51_CHAPA = u51_CHAPA;
    }

    public String getU51_STATUS() {
        return U51_STATUS;
    }

    public void setU51_STATUS(String u51_STATUS) {
        U51_STATUS = u51_STATUS;
    }

    public String getU51_FABRI() {
        return U51_FABRI;
    }

    public void setU51_FABRI(String u51_FABRI) {
        U51_FABRI = u51_FABRI;
    }

    public String getU51_FABID() {
        return U51_FABID;
    }

    public void setU51_FABID(String u51_FABID) {
        U51_FABID = u51_FABID;
    }

    public String getU51_MODELO() {
        return U51_MODELO;
    }

    public void setU51_MODELO(String u51_MODELO) {
        U51_MODELO = u51_MODELO;
    }

    public String getU51_SDID() {
        return U51_SDID;
    }

    public void setU51_SDID(String u51_SDID) {
        U51_SDID = u51_SDID;
    }

    public String getU51_MARCA() {
        return U51_MARCA;
    }

    public void setU51_MARCA(String u51_MARCA) {
        U51_MARCA = u51_MARCA;
    }

    public String getU50_COD() {
        return U50_COD;
    }

    public void setU50_COD(String u50_COD) {
        U50_COD = u50_COD;
    }

    public String getU50_NOME() {
        return U50_NOME;
    }

    public void setU50_NOME(String u50_NOME) {
        U50_NOME = u50_NOME;
    }

    public String getU50_SENHA() {
        return U50_SENHA;
    }

    public void setU50_SENHA(String u50_SENHA) {
        U50_SENHA = u50_SENHA;
    }

    public String getU50_CODVEN() {
        return U50_CODVEN;
    }

    public void setU50_CODVEN(String u50_CODVEN) {
        U50_CODVEN = u50_CODVEN;
    }

    public String getU50_NIVEL() {
        return U50_NIVEL;
    }

    public void setU50_NIVEL(String u50_NIVEL) {
        U50_NIVEL = u50_NIVEL;
    }

    public String getU50_CLASS() {
        return U50_CLASS;
    }

    public void setU50_CLASS(String u50_CLASS) {
        U50_CLASS = u50_CLASS;
    }

    @Override
    public void loadColunas() {


        _colunas = new ArrayList<String>();


        _colunas.add("U51_FILIAL");
        _colunas.add("U51_COD");
        _colunas.add("U51_REGID");
        _colunas.add("U51_VERSAO");
        _colunas.add("U51_BUILD");
        _colunas.add("U51_IMEI");
        _colunas.add("U51_CHAPA");
        _colunas.add("U51_STATUS");
        _colunas.add("U51_FABRI");
        _colunas.add("U51_FABID");
        _colunas.add("U51_MODELO");
        _colunas.add("U51_MARCA");
        _colunas.add("U51_SDID");
        _colunas.add("U50_COD");
        _colunas.add("U50_NOME");
        _colunas.add("U50_SENHA");
        _colunas.add("U50_CODVEN");
        _colunas.add("U50_NIVEL");
        _colunas.add("U50_CLASS");

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
