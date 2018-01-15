package br.com.brotolegal.savdatabase.entities;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 01/08/2016.
 */
public class SALESORDERMB extends ObjRegister implements KvmSerializable {


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.SALESORDERMB";

    protected String NRO;
    protected String STATUS;
    protected String CMENSAGEM;
    protected String CPROTHEUS;

    public SALESORDERMB()  {

        super(_OBJETO,"SALESORDERMB");

        loadColunas();

        InicializaFields();
    }

    public SALESORDERMB(String NRO, String STATUS, String CMENSAGEM, String CPROTHEUS) {

        super(_OBJETO,"SALESORDERMB");

        loadColunas();

        InicializaFields();

        this.NRO = NRO;
        this.STATUS = STATUS;
        this.CMENSAGEM = CMENSAGEM;
        this.CPROTHEUS = CPROTHEUS;
    }


    public String getNRO() {
        return NRO;
    }

    public void setNRO(String NRO) {
        this.NRO = NRO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCMENSAGEM() {
        return CMENSAGEM;
    }

    public void setCMENSAGEM(String CMENSAGEM) {
        this.CMENSAGEM = CMENSAGEM;
    }

    public String getCPROTHEUS() {
        return CPROTHEUS;
    }

    public void setCPROTHEUS(String CPROTHEUS) {
        this.CPROTHEUS = CPROTHEUS;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("NRO");
        _colunas.add("STATUS");
        _colunas.add("CMENSAGEM");
        _colunas.add("CPROTHEUS");

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
