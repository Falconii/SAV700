package br.com.brotolegal.savdatabase.entities;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 04/07/2016.
 */
public class Task extends ObjRegister implements KvmSerializable {

    protected String CODIGO;
    protected String DESCRI;


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Task";


    public Task(){

        super(_OBJETO,"TASK");

        loadColunas();

        InicializaFields();
    }

    public Task(String CODIGO, String DESCRI) {
        super(_OBJETO,"TASK");

        loadColunas();

        InicializaFields();

        this.CODIGO = CODIGO;
        this.DESCRI = DESCRI;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getDESCRI() {
        return DESCRI;
    }

    public void setDESCRI(String DESCRI) {
        this.DESCRI = DESCRI;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();
        _colunas.add("CODIGO");
        _colunas.add("DESCRI");


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
