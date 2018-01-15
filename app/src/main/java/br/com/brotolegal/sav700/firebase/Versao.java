package br.com.brotolegal.sav700.firebase;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 02/10/2017.
 */

public class Versao extends ObjRegister implements KvmSerializable {


    protected String CVERSAO;
    protected String CDATA;

    protected static final String _OBJETO = "br.com.brotolegal.sav700.firebase.Versao";

    public Versao() {

        super(_OBJETO,"VERSAO");

        loadColunas();

        InicializaFields();

        getDados();
    }

    public String getCVERSAO() {
        return CVERSAO;
    }

    public void setCVERSAO(String CVERSAO) {
        this.CVERSAO = CVERSAO;
    }

    public String getCDATA() {
        return CDATA;
    }

    public void setCDATA(String CDATA) {
        this.CDATA = CDATA;
    }

    private void getDados(){

        this.CVERSAO = App.getCustomAppContext().getResources().getString(R.string.app_versao);

        this.CDATA     = App.getDataHora();

    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();
        _colunas.add("CVERSAO");
        _colunas.add("CDATA");


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
