package br.com.brotolegal.savdatabase.internet;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by Falconi on 02/03/2016.
 */
public abstract class HandleSoap {

    protected SoapObject result;

    public HandleSoap() {

        this.result = null;

    }

    public void setResult(SoapObject result) {

        this.result = result;

    }

    public abstract void processa() throws Exception;

    public abstract void processaArray() throws Exception;
}
