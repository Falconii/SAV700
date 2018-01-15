package br.com.brotolegal.savdatabase.entities;


import java.text.DecimalFormat;
import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Config extends ObjRegister {

    protected String CODIGO;
    protected String DESCRICAO;
    protected String FTPIP;
    protected String FTPUSER;
    protected String FTPPASS;
    protected String FTPPORTA;
    protected String IP;
    protected String NAMESPACE;
    protected String URL;
    protected String USERATIVO;
    protected String PERIODO;
    protected String STATUS;
    protected String PORTA;


    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Config";

    /* Valores default para inicializar registro 000 */

    private static String[] conexoes         = {"WIFI BROTO","CONEXÃO PADRÃO","CONEXÃO ALTERNATIVA 1","CONEXÃO ALTERNATIVA 2"};

    private static String[] enderecos        = {"192.168.0.18","189.109.60.186","201.77.183.249","189.112.99.82"};

    private static String ftpuser            = "mobile";

    private static String ftppass            = "brotomobile";

    private static String url                = "/ws/WSMOBILE.APW";

    private static String namespace          = "/ws/WSMOBILE.PRW";

    private static String porta              = "9876";


    public Config()  {

        super(_OBJETO,"CONFIG");

        loadColunas();

        InicializaFields();

    }

    public Config(String CODIGO, String DESCRICAO, String FTPIP, String FTPUSER, String FTPPASS, String FTPPORTA, String IP, String NAMESPACE, String URL, String USERATIVO, String PERIODO, String STATUS, String PORTA) {
        super(_OBJETO,"CONFIG");

        loadColunas();

        InicializaFields();

        this.CODIGO    = CODIGO;
        this.DESCRICAO = DESCRICAO;
        this.FTPIP     = FTPIP;
        this.FTPUSER   = FTPUSER;
        this.FTPPASS   = FTPPASS;
        this.FTPPORTA  = FTPPORTA;
        this.IP        = IP;
        this.NAMESPACE = NAMESPACE;
        this.URL       = URL;
        this.USERATIVO = USERATIVO;
        this.PERIODO   = PERIODO;
        this.STATUS    = STATUS;
        this.PORTA     = PORTA;
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

    public String getFTPIP() {
        return FTPIP;
    }

    public void setFTPIP(String FTPIP) {
        this.FTPIP = FTPIP;
    }

    public String getFTPUSER() {
        return FTPUSER;
    }

    public void setFTPUSER(String FTPUSER) {
        this.FTPUSER = FTPUSER;
    }

    public String getFTPPASS() {
        return FTPPASS;
    }

    public void setFTPPASS(String FTPPASS) {
        this.FTPPASS = FTPPASS;
    }

    public String getFTPPORTA() {
        return FTPPORTA;
    }

    public void setFTPPORTA(String FTPPORTA) {
        this.FTPPORTA = FTPPORTA;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getNAMESPACE() {
        return NAMESPACE;
    }

    public void setNAMESPACE(String NAMESPACE) {
        this.NAMESPACE = NAMESPACE;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUSERATIVO() {
        return USERATIVO;
    }

    public void setUSERATIVO(String USERATIVO) {
        this.USERATIVO = USERATIVO;
    }

    public String getPERIODO() {
        return PERIODO;
    }

    public void setPERIODO(String PERIODO) {
        this.PERIODO = PERIODO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getPORTA() {
        return PORTA;
    }

    public void setPORTA(String PORTA) {
        this.PORTA = PORTA;
    }

    public String getUrlFull(){

        return "http://"+this.IP+":"+getPORTA()+this.URL;

    }

    public String getNSFull(){

        return "http://"+this.IP+":"+getPORTA()+this.NAMESPACE;

    }

    public static String[] getConexoes() {
        return conexoes;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();
        _colunas.add("CODIGO");
        _colunas.add("DESCRICAO");
        _colunas.add("FTPIP");
        _colunas.add("FTPUSER");
        _colunas.add("FTPPASS");
        _colunas.add("FTPPORTA");
        _colunas.add("IP");
        _colunas.add("NAMESPACE");
        _colunas.add("URL");
        _colunas.add("USERATIVO");
        _colunas.add("PERIODO");
        _colunas.add("STATUS");
        _colunas.add("PORTA");

    }


    public static  String[] getDefault(int x){

        DecimalFormat format_00 = new DecimalFormat("000");

        String[] result  = {format_00.format(x+1),conexoes[x],enderecos[x],ftpuser,ftppass,"21",enderecos[x],namespace,url,"000000","05/2016","A",porta};

        return result;



    }

}
