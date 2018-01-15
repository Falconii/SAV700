package br.com.brotolegal.savdatabase.wsentities;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 07/07/2016.
 */
public class TASK extends ObjRegister implements KvmSerializable  {

    protected String  CERRO;
    protected String  CMSGERRO;
    protected String  CCODIGO;
    protected String  CDESCRICAO;
    protected String  CMODULO;
    protected String  CNOMEARQ;


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.wsentities.TASK";


    public TASK()  {

        super(_OBJETO,"TASK");

        loadColunas();

        InicializaFields();
    }

    public TASK(String CERRO, String CMSGERRO, String CCODIGO, String CDESCRICAO, String CMODULO, String CNOMEARQ) {
        super(_OBJETO,"TASK");

        loadColunas();

        InicializaFields();

        this.CERRO = CERRO;
        this.CMSGERRO = CMSGERRO;
        this.CCODIGO = CCODIGO;
        this.CDESCRICAO = CDESCRICAO;
        this.CMODULO = CMODULO;
        this.CNOMEARQ = CNOMEARQ;
    }

    public String getCERRO() {
        return CERRO;
    }

    public void setCERRO(String CERRO) {
        this.CERRO = CERRO;
    }

    public String getCMSGERRO() {
        return CMSGERRO;
    }

    public void setCMSGERRO(String CMSGERRO) {
        this.CMSGERRO = CMSGERRO;
    }

    public String getCCODIGO() {
        return CCODIGO;
    }

    public void setCCODIGO(String CCODIGO) {
        this.CCODIGO = CCODIGO;
    }

    public String getCDESCRICAO() {
        return CDESCRICAO;
    }

    public void setCDESCRICAO(String CDESCRICAO) {
        this.CDESCRICAO = CDESCRICAO;
    }

    public String getCMODULO() {
        return CMODULO;
    }

    public void setCMODULO(String CMODULO) {
        this.CMODULO = CMODULO;
    }

    public String getCNOMEARQ() {
        return CNOMEARQ;
    }

    public void setCNOMEARQ(String CNOMEARQ) {
        this.CNOMEARQ = CNOMEARQ;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("CERRO");
        _colunas.add("CMSGERRO");
        _colunas.add("CCODIGO");
        _colunas.add("CDESCRICAO");
        _colunas.add("CMODULO");
        _colunas.add("CNOMEARQ");

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
