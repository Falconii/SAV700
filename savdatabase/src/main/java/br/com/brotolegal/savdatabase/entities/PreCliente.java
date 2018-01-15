package br.com.brotolegal.savdatabase.entities;

import android.content.Context;
import android.util.Log;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.CanalDAO;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.regrasdenegocio.TabPreco;
import br.com.brotolegal.savdatabase.regrasdenegocio.ValidaCNPJ;

/**
 * Created by Falconi on 25/11/2015.
 */
public class PreCliente extends ObjRegister implements KvmSerializable {

    protected String ID;
    protected String ERRO;
    protected String MSGERRO;
    protected String OPERACAO;
    protected String STATUS;
    protected String CODIGO;
    protected String RAZAO;
    protected String FANTASIA;
    protected String PESSOA;
    protected String CNPJ;
    protected String RG;
    protected String IE;
    protected String IM;
    protected String LOGRADOURO;
    protected String ENDERECO;
    protected String NRO;
    protected String COMPLEMENTO;
    protected String BAIRRO;
    protected String CODCIDADE;
    protected String CIDADE;
    protected String ESTADO;
    protected String CEP;
    protected String DDD;
    protected String TELEFONE;
    protected String CELULAR;
    protected String HOMEPAGE;
    protected String EMAILNFE;
    protected String EMAIL;
    protected String FUNDACAO;
    protected String CANAL;
    protected String REDE;
    protected String POLITICA;
    protected String TABPRECO;
    protected String CONDPAGTO;
    protected String BOLETO;
    protected String TAXA;
    protected String OPSIMPLES;
    protected String ISENTOST;
    protected Float LIMITE;
    protected String ICMS;
    protected String CLIENTEENTREGA;
    protected String RESTRECEB;
    protected String HORARECEB;
    protected String AGENDAMENTO;
    protected Float VLRDESCARG;
    protected String UNIDDESCARG;
    protected String PERFILVEIC;
    protected String PERFILCARG;
    protected String PALETIZACAO;
    protected String RESSALVACOM;
    protected String RESSALVALOG;
    protected String FORMAPAGTO;
    protected String MISTO;
    protected String CADASTRO;
    protected String DEPTO01;
    protected String DEPTO02;
    protected String DEPTO03;
    protected String DEPTO04;
    protected String DEPTO05;
    protected String DEPTO06;
    protected String CIENTE;
    protected String LATITUDE;
    protected String LONGITUDE;
    protected String SUFRAMA;


    /* Descriçõoes de canais, tabela de preço */
    private String CANALDESCRI;
    private String TABPRECODESCRI;
    private String POLITICADESCRI;
    private String REDEDESCRI;
    private String CONDPAGTODESCRI;
    private String STATUSDESCRI;
    private List<Palete> PALETES;
    private List<Perfil> PERFIS;
    private List<String> UNIDADES;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.PreCliente";

    public PreCliente() {

        super(_OBJETO, "PRECLIENTE");

        loadColunas();

        loadUnidades();

        InicializaFields();

        setSTATUS("0")      ;

        CODIGO              = "";
        ICMS                = "NAO";
        CLIENTEENTREGA      = "2";
        RESTRECEB           = "TRUE|FALSE|FALSE|FALSE|FALSE|FALSE|TRUE|";
        HORARECEB           = "00:00";
        FORMAPAGTO          = "1-DINHEIRO";
        MISTO               = "SIM";
        UNIDDESCARG         = "1 - FD";
        AGENDAMENTO         = "NÃO";
        PERFILCARG          = "1-PADRÃO";
        CIENTE              = "NÃO";

        PERFIS              = new ArrayList<Perfil>();

        PALETES             = new ArrayList<Palete>();


        setPALETIZACAO("");

        setPERFILVEIC("");

        CANALDESCRI       = "";
        TABPRECODESCRI    = "";
        POLITICADESCRI    = "";
        REDEDESCRI        = "";
        CONDPAGTODESCRI   = "";


    }



    public PreCliente(String oPERACAO, String iD) {
        super(_OBJETO, "PRECLIENTE");

        loadColunas();

        loadUnidades();

        InicializaFields();

        PERFIS  = new ArrayList<Perfil>();

        PALETES = new ArrayList<Palete>();

        ID                  = iD;
        CODIGO              = "";
        OPERACAO            = oPERACAO;
        setSTATUS("0")      ;
        ICMS                = "NAO";
        CLIENTEENTREGA      = "2";
        RESTRECEB           = "TRUE|FALSE|FALSE|FALSE|FALSE|FALSE|TRUE|";
        HORARECEB           = "00:00";
        FORMAPAGTO          = "1-DINHEIRO";
        MISTO               = "SIM";
        UNIDDESCARG         = "1 - FD";
        AGENDAMENTO         = "NAO";
        PERFILCARG          = "1-PADRÃO";
        CIENTE              = "NÃO";

        setPALETIZACAO("");

        setPERFILVEIC("");

        CANALDESCRI       = "";
        TABPRECODESCRI    = "";
        POLITICADESCRI    = "";
        REDEDESCRI        = "";
        CONDPAGTODESCRI   = "";

    }


    public PreCliente(String ID, String ERRO, String MSGERRO, String OPERACAO, String STATUS, String CODIGO, String RAZAO, String FANTASIA, String PESSOA, String CNPJ, String RG, String IE, String IM, String LOGRADOURO, String ENDERECO, String NRO, String COMPLEMENTO, String BAIRRO, String CODCIDADE, String CIDADE, String ESTADO, String CEP, String DDD, String TELEFONE, String CELULAR, String HOMEPAGE,
                      String EMAILNFE, String EMAIL, String FUNDACAO, String CANAL, String REDE, String POLITICA, String TABPRECO, String CONDPAGTO, String BOLETO, String TAXA, String OPSIMPLES, String ISENTOST, Float LIMITE, String ICMS, String CLIENTEENTREGA, String RESTRECEB,
                      String HORARECEB, String AGENDAMENTO, Float VLRDESCARG, String UNIDDESCARG, String PERFILVEIC, String PERFILCARG, String PALETIZACAO, String RESSALVACOM, String RESSALVALOG, String FORMAPAGTO, String MISTO, String CADASTRO, String DEPTO01, String DEPTO02, String DEPTO03, String DEPTO04, String DEPTO05, String DEPTO06, String CIENTE, String LATITUDE, String LONGITUDE, String SUFRAMA){


        super(_OBJETO, "PRECLIENTE");

        loadColunas();

        loadUnidades();

        InicializaFields();

        this.ID = ID;
        this.ERRO = ERRO;
        this.MSGERRO = MSGERRO;
        this.OPERACAO = OPERACAO;
        this.STATUS = STATUS;
        this.CODIGO = CODIGO;
        this.RAZAO = RAZAO;
        this.FANTASIA = FANTASIA;
        this.PESSOA = PESSOA;
        this.CNPJ = CNPJ;
        this.RG = RG;
        this.IE = IE;
        this.IM = IM;
        this.LOGRADOURO = LOGRADOURO;
        this.ENDERECO = ENDERECO;
        this.NRO = NRO;
        this.COMPLEMENTO = COMPLEMENTO;
        this.BAIRRO = BAIRRO;
        this.CODCIDADE  = CODCIDADE;
        this.CIDADE     = CIDADE;
        this.ESTADO     = ESTADO;
        this.CEP        = CEP;
        this.DDD        = DDD;
        this.TELEFONE   = TELEFONE;
        this.CELULAR    = CELULAR;
        this.HOMEPAGE   = HOMEPAGE;
        this.EMAILNFE   = EMAILNFE;
        this.EMAIL      = EMAIL;
        this.FUNDACAO   = FUNDACAO;
        this.CANAL      = CANAL;
        this.REDE       = REDE;
        this.POLITICA   = POLITICA;
        this.TABPRECO   = TABPRECO;
        this.CONDPAGTO  = CONDPAGTO;
        this.BOLETO     = BOLETO;
        this.TAXA       = TAXA;
        this.OPSIMPLES  = OPSIMPLES;
        this.ISENTOST   = ISENTOST;
        this.LIMITE     = LIMITE;
        this.ICMS           = ICMS;
        this.CLIENTEENTREGA = CLIENTEENTREGA;
        this.RESTRECEB      = RESTRECEB;
        this.HORARECEB      = HORARECEB;
        this.AGENDAMENTO    = AGENDAMENTO;
        this.VLRDESCARG     = VLRDESCARG;
        this.UNIDDESCARG    = UNIDDESCARG;
        this.PERFILVEIC     = PERFILVEIC;
        this.PERFILCARG     = PERFILCARG;
        this.PALETIZACAO    = PALETIZACAO;
        this.RESSALVACOM    = RESSALVACOM;
        this.RESSALVALOG    = RESSALVALOG;
        this.FORMAPAGTO     = FORMAPAGTO;
        this.MISTO          = MISTO;
        this.CADASTRO       = CADASTRO;
        this.DEPTO01        = DEPTO01;
        this.DEPTO02        = DEPTO02;
        this.DEPTO03        = DEPTO03;
        this.DEPTO04        = DEPTO04;
        this.DEPTO05        = DEPTO05;
        this.DEPTO06        = DEPTO06;
        this.CIENTE         = CIENTE;
        this.LATITUDE       = LATITUDE;
        this.LONGITUDE      = LONGITUDE;
        this.SUFRAMA        = SUFRAMA;


        PERFIS              = new ArrayList<Perfil>();

        PALETES             = new ArrayList<Palete>();

        setPALETIZACAO(PALETIZACAO);

        setPERFILVEIC(PERFILVEIC);

        CANALDESCRI       = "";
        TABPRECODESCRI    = "";
        POLITICADESCRI    = "";
        REDEDESCRI        = "";
        CONDPAGTODESCRI   = "";



    }


    public String getERRO() {
        return ERRO;
    }

    public void setERRO(String eRRO) {
        ERRO = eRRO;
    }

    public String getMSGERRO() {
        return MSGERRO;
    }

    public void setMSGERRO(String mSGERRO) {
        MSGERRO = mSGERRO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getOPERACAO() {
        return OPERACAO;
    }

    public void setOPERACAO(String oPERACAO) {
        OPERACAO = oPERACAO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String cODIGO) {
        CODIGO = cODIGO;
    }

    public String getRAZAO() {
        return RAZAO;
    }

    public void setRAZAO(String rAZAO) {
        RAZAO = rAZAO;
    }

    public String getFANTASIA() {
        return FANTASIA;
    }

    public void setFANTASIA(String fANTASIA) {
        FANTASIA = fANTASIA;
    }

    public String getPESSOA() {
        return PESSOA;
    }

    public void setPESSOA(String pESSOA) {
        PESSOA = pESSOA;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String cNPJ) {
        CNPJ = cNPJ;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String rG) {
        RG = rG;
    }

    public String getIE() {
        return IE;
    }

    public void setIE(String iE) {
        IE = iE;
    }

    public String getIM() {
        return IM;
    }

    public void setIM(String iM) {
        IM = iM;
    }

    public String getLOGRADOURO() {
        return LOGRADOURO;
    }

    public void setLOGRADOURO(String lOGRADOURO) {
        LOGRADOURO = lOGRADOURO;
    }

    public String getENDERECO() {
        return ENDERECO;
    }

    public void setENDERECO(String eNDERECO) {
        ENDERECO = eNDERECO;
    }

    public String getNRO() {
        return NRO;
    }

    public void setNRO(String nRO) {
        NRO = nRO;
    }

    public String getCOMPLEMENTO() {
        return COMPLEMENTO;
    }

    public void setCOMPLEMENTO(String cOMPLEMENTO) {
        COMPLEMENTO = cOMPLEMENTO;
    }

    public String getBAIRRO() {
        return BAIRRO;
    }

    public void setBAIRRO(String bAIRRO) {
        BAIRRO = bAIRRO;
    }

    public String getCODCIDADE() {
        return CODCIDADE;
    }

    public void setCODCIDADE(String cODCIDADE) {
        CODCIDADE = cODCIDADE;
    }

    public String getCIDADE() {
        return CIDADE;
    }

    public void setCIDADE(String cIDADE) {
        CIDADE = cIDADE;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String eSTADO) {
        ESTADO = eSTADO;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String cEP) {
        CEP = cEP;
    }

    public String getDDD() {
        return DDD;
    }

    public void setDDD(String dDD) {
        DDD = dDD;
    }

    public String getTELEFONE() {
        return TELEFONE;
    }

    public void setTELEFONE(String tELEFONE) {
        TELEFONE = tELEFONE;
    }

    public String getCELULAR() {
        return CELULAR;
    }

    public void setCELULAR(String cELULAR) {
        CELULAR = cELULAR;
    }

    public String getHOMEPAGE() {
        return HOMEPAGE;
    }

    public void setHOMEPAGE(String hOMEPAGE) {
        HOMEPAGE = hOMEPAGE;
    }

    public String getEMAILNFE() {
        return EMAILNFE;
    }

    public void setEMAILNFE(String eMAILNFE) {
        EMAILNFE = eMAILNFE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }

    public String getFUNDACAO() {
        return FUNDACAO;
    }

    public void setFUNDACAO(String fUNDACAO) {
        FUNDACAO = fUNDACAO;
    }

    public String getCANAL() {
        return CANAL;
    }

    public void setCANAL(String cANAL) {
        CANAL = cANAL;
    }

    public String getREDE() {
        return REDE;
    }

    public void setREDE(String rEDE) {
        REDE = rEDE;
    }

    public String getPOLITICA() {
        return POLITICA;
    }

    public void setPOLITICA(String pOLITICA) {
        POLITICA = pOLITICA;
    }

    public String getTABPRECO() {
        return TABPRECO;
    }

    public void setTABPRECO(String tABPRECO) {
        TABPRECO = tABPRECO;
    }

    public String getCONDPAGTO() {
        return CONDPAGTO;
    }

    public void setCONDPAGTO(String cONDPAGTO) {
        CONDPAGTO = cONDPAGTO;
    }

    public String getBOLETO() {
        return BOLETO;
    }

    public void setBOLETO(String bOLETO) {
        BOLETO = bOLETO;
    }

    public String getTAXA() {
        return TAXA;
    }

    public void setTAXA(String tAXA) {
        TAXA = tAXA;
    }

    public String getOPSIMPLES() {
        return OPSIMPLES;
    }

    public void setOPSIMPLES(String oPSIMPLES) {
        OPSIMPLES = oPSIMPLES;
    }

    public String getISENTOST() {
        return ISENTOST;
    }

    public void setISENTOST(String iSENTOST) {
        ISENTOST = iSENTOST;
    }

    public Float getLIMITE() {
        return LIMITE;
    }

    public void setLIMITE(Float lIMITE) {
        LIMITE = lIMITE;
    }

    public String getICMS() {
        return ICMS;
    }

    public void setICMS(String iCMS) {
        ICMS = iCMS;
    }

    public String getCLIENTEENTREGA() {
        return CLIENTEENTREGA;
    }

    public void setCLIENTEENTREGA(String cLIENTEENTREGA) {
        CLIENTEENTREGA = cLIENTEENTREGA;
    }

    public String getRESTRECEB() {
        return RESTRECEB;
    }

    public void setRESTRECEB(String RESTRECEB) {
        this.RESTRECEB = RESTRECEB;
    }

    public String getHORARECEB() {
        return HORARECEB;
    }

    public void setHORARECEB(String HORARECEB) {
        this.HORARECEB = HORARECEB;
    }

    public String getAGENDAMENTO() {
        return AGENDAMENTO;
    }

    public void setAGENDAMENTO(String AGENDAMENTO) {
        this.AGENDAMENTO = AGENDAMENTO;
    }

    public Float getVLRDESCARG() {
        return VLRDESCARG;
    }

    public void setVLRDESCARG(Float VLRDESCARG) {
        this.VLRDESCARG = VLRDESCARG;
    }

    public String getUNIDDESCARG() {
        return UNIDDESCARG;
    }

    public void setUNIDDESCARG(String UNIDDESCARG) {
        this.UNIDDESCARG = UNIDDESCARG;
    }

    public String getPERFILCARG() {
        return PERFILCARG;
    }

    public void setPERFILCARG(String PERFILCARG) {
        this.PERFILCARG = PERFILCARG;
    }

    public String getPERFILVEIC() {
        String basico = "";
        basico =  "VAN|VAN|FALSE|&";
        basico += "VUC|VUC|FALSE|&";
        basico += "3/4|3/4|FALSE|&";
        basico += "TOC|TOCO|FALSE|&";
        basico += "TRU|TRUCK|FALSE|&";
        basico += "CAR|CARRETA|FALSE|&";
        basico += "BAU|BAU|FALSE|&";
        if (this.PERFILVEIC.trim().isEmpty()){

            this.PERFILVEIC = basico;

        }

        return this.PERFILVEIC;
    }

    public void setPERFILVEIC(String pERFILVEIC) {

        String basico = "";
        basico =  "VAN|VAN|FALSE|&";
        basico += "VUC|VUC|FALSE|&";
        basico += "3/4|3/4|FALSE|&";
        basico += "TOC|TOCO|FALSE|&";
        basico += "TRU|TRUCK|FALSE|&";
        basico += "CAR|CARRETA|FALSE|&";
        basico += "BAU|BAU|FALSE|&";


        if (pERFILVEIC.trim().isEmpty()){

            this.PERFILVEIC = basico;

        } else {

            this.PERFILVEIC = pERFILVEIC;

        }

    }


    public void upDatePERFILVEIC(Perfil perfil){
        //atualiza PERFILVEIC

        this.PERFILVEIC = "";

        for (Perfil p : PERFIS){

            if (p.getCodigo().equals(perfil.getCodigo())){

                p.setProcessar(perfil.getProcessar());

            }

            this.PERFILVEIC += p.getCodigo()+"|"+p.getTexto1()+"|"+ Boolean.toString(p.getProcessar())+"|&";

        }

    }

    public List<Perfil> getPERFIS() {

        String[] reg = this.getPERFILVEIC().split("\\&");

        this.PERFIS = new ArrayList<Perfil>();

        for (String r:reg) {

            String vetor[] = r.split("\\|");

            this.PERFIS.add( new Perfil(vetor[0],vetor[1],"",vetor[2].toUpperCase().equals("TRUE") ? true : false ));

        }

        return this.PERFIS;
    }




    public void setPERFIS(Perfil perfil) {

        Boolean lAchou = false;

        for (Perfil p : this.PERFIS) {

            if(p.getCodigo().equals(perfil.getCodigo())){

                p.setProcessar(perfil.getProcessar());

                lAchou = true;

                break;

            }

        }

        if (!lAchou){

            this.PERFIS.add(new Perfil(perfil.getCodigo(), perfil.getTexto1(),perfil.getTexto2(),perfil.getProcessar()));

        }

    }


    public String getRESSALVACOM() {
        return RESSALVACOM;
    }

    public void setRESSALVACOM(String RESSALVACOM) {
        this.RESSALVACOM = RESSALVACOM;
    }

    public String getRESSALVALOG() {
        return RESSALVALOG;
    }

    public void setRESSALVALOG(String RESSALVALOG) {
        this.RESSALVALOG = RESSALVALOG;
    }

    public String getFORMAPAGTO() {
        return FORMAPAGTO;
    }

    public void setFORMAPAGTO(String FORMAPAGTO) {
        this.FORMAPAGTO = FORMAPAGTO;
    }

    public String getMISTO() {
        return MISTO;
    }

    public void setMISTO(String MISTO) {
        this.MISTO = MISTO;
    }


    public String getCADASTRO() {
        return CADASTRO;
    }

    public void setCADASTRO(String CADASTRO) {
        this.CADASTRO = CADASTRO;
    }

    public String getDEPTO01() {
        return DEPTO01;
    }

    public void setDEPTO01(String DEPTO01) {
        this.DEPTO01 = DEPTO01;
    }

    public String getDEPTO02() {
        return DEPTO02;
    }

    public void setDEPTO02(String DEPTO02) {
        this.DEPTO02 = DEPTO02;
    }

    public String getDEPTO03() {
        return DEPTO03;
    }

    public void setDEPTO03(String DEPTO03) {
        this.DEPTO03 = DEPTO03;
    }

    public String getDEPTO04() {
        return DEPTO04;
    }

    public void setDEPTO04(String DEPTO04) {
        this.DEPTO04 = DEPTO04;
    }

    public String getDEPTO05() {
        return DEPTO05;
    }

    public void setDEPTO05(String DEPTO05) {
        this.DEPTO05 = DEPTO05;
    }

    public String getDEPTO06() {
        return DEPTO06;
    }

    public void setDEPTO06(String DEPTO06) {
        this.DEPTO06 = DEPTO06;
    }

    public String getCIENTE() {
        return CIENTE;
    }

    public void setCIENTE(String CIENTE) {
        this.CIENTE = CIENTE;
    }

    public String getPALETIZACAO() {
        String basico = "";
        basico =  "001|FEIJÃO 30X1|"+getUnidPadrao("001")+"|FD|&";
        basico += "002|FEIJÃO 15X2|"+getUnidPadrao("002")+"|FD|&";
        basico += "003|ARROZ 6X5 E 30X1|"+getUnidPadrao("003")+"|FD|&";
        basico += "004|ARROZ 15X2|"+getUnidPadrao("004")+"|FD|&";
        basico += "005|ATUM|"+getUnidPadrao("005")+"|CX|&";
        basico += "006|AZEITE 500 ML|"+getUnidPadrao("006")+"|CX|&";
        basico += "007|JERKED 30X1 60X500 6X5|"+getUnidPadrao("007")+"|CX|&";
        basico += "008|JERKED 20X500|"+getUnidPadrao("008")+"|CX|&";


        if (this.PALETIZACAO.trim().isEmpty()){

            this.PALETIZACAO = basico;

        }

        return PALETIZACAO;
    }

    public void setPALETIZACAO(String pALETIZACAO) {
        String basico = "";
        basico =  "001|FEIJÃO 30X1|"+getUnidPadrao("001")+"|FD|&";
        basico += "002|FEIJÃO 15X2|"+getUnidPadrao("002")+"|FD|&";
        basico += "003|ARROZ 6X5 E 30X1|"+getUnidPadrao("003")+"|FD|&";
        basico += "004|ARROZ 15X2|"+getUnidPadrao("004")+"|FD|&";
        basico += "005|ATUM|"+getUnidPadrao("005")+"|CX|&";
        basico += "006|AZEITE 500 ML|"+getUnidPadrao("006")+"|CX|&";
        basico += "007|JERKED 30X1 60X500 6X5|"+getUnidPadrao("007")+"|CX|&";
        basico += "008|JERKED 20X500|"+getUnidPadrao("008")+"|CX|&";

        if (pALETIZACAO.trim().isEmpty()){

            this.PALETIZACAO = basico;

        } else {

            this.PALETIZACAO = pALETIZACAO;

        }


    }



    public void upDatePALETIZACAO(Palete palete){
       //atualiza PALETES

        this.PALETIZACAO = "";

        for (Palete p : PALETES){

            if (p.getGrupo().equals(palete.getGrupo())){

                p.setUnidade(palete.getUnidade());

            }

            this.PALETIZACAO =  this.PALETIZACAO + p.getGrupo()+"|"+p.getDescricao()+"|"+p.getUnidade()+"|"+p.getUnid()+"|&";

        }

    }

    public List<Palete> getPALETES() {
        String[] reg = this.getPALETIZACAO().split("\\&");

        this.PALETES = new ArrayList<Palete>();

        for (String r:reg) {

            String vetor[] = r.split("\\|");

            this.PALETES.add( new Palete(vetor[0],vetor[1],vetor[2],vetor[3]));

        }

        return this.PALETES;
    }


    public void setPALETES(Palete palete) {

        Boolean lAchou = false;

        for (Palete p : this.PALETES) {

            if(p.getGrupo().equals(palete.getGrupo())){

                p.setUnidade(palete.getUnidade());

                lAchou = true;

                break;

            }

        }

        if (!lAchou){

            this.PALETES.add(new Palete(palete.getGrupo(),palete.getDescricao(),palete.getUnidade(),palete.getUnid()));

        }

    }


    public String getCANALDESCRI(Context context) {

        CANALDESCRI = SEEKCanal().getDESCRICAO();

        return CANALDESCRI;
    }




    public String getTABPRECODESCRI(Context context) {

        TABPRECODESCRI = SEEKtabpreco().getDESCRICAO();

        return TABPRECODESCRI;
    }




    public String getPOLITICADESCRI(Context context) {

        POLITICADESCRI = "";

        return POLITICADESCRI;
    }


    public String getREDEDESCRI(Context context) {

        REDEDESCRI = SEEKRede().getDESCRICAO();

        return REDEDESCRI;
    }


    public String getCONDPAGTODESCRI(Context context) {

        CONDPAGTODESCRI = SEEKCondPagto().getDESCRICAO();

        return CONDPAGTODESCRI;
    }

    public String getSTATUSDESCRI() {

        String retorno = "";

        switch (STATUS.charAt(0)) {
            case '0':
                retorno = "MEMORIA";
                break;
            case '2':
                retorno = "COM ERRO !!";
                break;
            case '4':
                retorno = "PRONTO PARA ENVIAR";
                break;
            case '5':
                retorno = "GA";
                break;
            case '6':
                retorno = "APOIO";
                break;
            case '7':
                retorno = "LOGISTICA";
                break;
            case '8':
                retorno = "FINANCEIRO";
                break;
            case 'A':
                retorno = "FISCAL";
                break;
            case 'C':
                retorno = "APROVADO";
                break;
            case 'E':
                retorno = "REPROVADO";
                break;
            case 'F':
                retorno = "DUPLICIDADE";
                break;
            case 'G':
                retorno = "EXCLUIDO";
                break;
            default:
                retorno = "SEM STATUS";
                break;
        }


        STATUSDESCRI = retorno;

        return STATUSDESCRI;
    }


    private Canal SEEKCanal(){

        Canal canal = null;

        try {

            CanalDAO dao = new CanalDAO();

            dao.open();

            canal = dao.seek(new String[] {this.CANAL});

            dao.close();

        } catch (Exception e){


            canal = null;

        }

        return canal;

    }

    private TabPreco SEEKtabpreco(){

            return null;

    }

    private Politica SEEKPolitica(){


//        DBAdapterPolitica datasourcepolitica;
//
//        datasourcepolitica = new DBAdapterPolitica(context);
//
//        datasourcepolitica.open();
//
//        Politica politica = datasourcepolitica.seek(POLITICA);
//
//        datasourcepolitica.close();
//
//        return politica;

        return null;

    }

    private Rede SEEKRede(){


//        DBAdapterRede datasourcerede;
//
//        datasourcerede = new DBAdapterRede(context);
//
//        datasourcerede.open();
//
//        Rede rede = datasourcerede.getRede(REDE);
//
//        datasourcerede.close();
//
//        return rede;

        return null;

    }


    private CondPagto SEEKCondPagto(){


//        DBAdapterCondPagto datasourcecondpagto;
//
//        datasourcecondpagto = new DBAdapterCondPagto(context);
//
//        datasourcecondpagto.open();
//
//        CondPagto condpagto = datasourcecondpagto.seek(CONDPAGTO);
//
//        datasourcecondpagto.close();
//
//        return condpagto;

        return null;

    }


    public void setRestricao(int indice, Boolean checked){

        String[] dias = this.RESTRECEB.split("\\|");

        dias[indice] = Boolean.toString(checked);

        this.RESTRECEB = dias[0]+"|"+dias[1]+"|"+dias[2]+"|"+dias[3]+"|"+dias[4]+"|"+dias[5]+"|"+dias[6]+"|";

    }


    public Boolean getRestricao(int indice){

        String[] dias = this.RESTRECEB.split("\\|");

        return Boolean.valueOf(dias[indice]);

    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("ID");
        _colunas.add("ERRO");
        _colunas.add("MSGERRO");
        _colunas.add("OPERACAO");
        _colunas.add("STATUS");
        _colunas.add("CODIGO");
        _colunas.add("RAZAO");
        _colunas.add("FANTASIA");
        _colunas.add("PESSOA");
        _colunas.add("CNPJ");
        _colunas.add("RG");
        _colunas.add("IE");
        _colunas.add("IM");
        _colunas.add("LOGRADOURO");
        _colunas.add("ENDERECO");
        _colunas.add("NRO");
        _colunas.add("COMPLEMENTO");
        _colunas.add("BAIRRO");
        _colunas.add("CODCIDADE");
        _colunas.add("CIDADE");
        _colunas.add("ESTADO");
        _colunas.add("CEP");
        _colunas.add("DDD");
        _colunas.add("TELEFONE");
        _colunas.add("CELULAR");
        _colunas.add("HOMEPAGE");
        _colunas.add("EMAILNFE");
        _colunas.add("EMAIL");
        _colunas.add("FUNDACAO");
        _colunas.add("CANAL");
        _colunas.add("REDE");
        _colunas.add("POLITICA");
        _colunas.add("TABPRECO");
        _colunas.add("CONDPAGTO");
        _colunas.add("BOLETO");
        _colunas.add("TAXA");
        _colunas.add("OPSIMPLES");
        _colunas.add("ISENTOST");
        _colunas.add("LIMITE");
        _colunas.add("ICMS");
        _colunas.add("CLIENTEENTREGA");
        _colunas.add("RESTRECEB");
        _colunas.add("HORARECEB");
        _colunas.add("AGENDAMENTO");
        _colunas.add("VLRDESCARG");
        _colunas.add("UNIDDESCARG");
        _colunas.add("PERFILVEIC");
        _colunas.add("PERFILCARG");
        _colunas.add("PALETIZACAO");
        _colunas.add("RESSALVACOM");
        _colunas.add("RESSALVALOG");
        _colunas.add("FORMAPAGTO");
        _colunas.add("MISTO");
        _colunas.add("CADASTRO");
        _colunas.add("DEPTO01");
        _colunas.add("DEPTO02");
        _colunas.add("DEPTO03");
        _colunas.add("DEPTO04");
        _colunas.add("DEPTO05");
        _colunas.add("DEPTO06");
        _colunas.add("CIENTE");
        _colunas.add("LATITUDE");
        _colunas.add("LONGITUDE");
        _colunas.add("SUFRAMA");

    }


    public Boolean Validadador(Context context,String campo){

        int indice;

        indice = getIndiceByNameColunas(campo);

        if (indice == -1) return true;

        return  ValidaCampo(context,indice);
    }


    public Boolean ValidaAll(Context context){

        boolean retorno;

        int indice;

        retorno = true;

        try {
            for(indice=0;indice <= _colunas.size()-1;indice++){

                if (!ValidaCampo(context,indice)){

                    retorno = false;

                };

            }

        } catch (Exception e){

            Log.i("ERRO", e.getMessage());

            retorno = false;
        }

        return retorno;
    }

    private Boolean ValidaCampo(Context context,int index)  {

        String SEM_VALIDACAO     = "#ERRO#MSGERRO#ID#OPERACAO#STATUS#CODIGO#RG#IM#COMPLEMENTO#CELULAR#HOMEPAGE#EMAIL";

        String VALIDACAO_NAONULO = "#PESSOA#LOGRADOURO#ENDERECO#NRO#BAIRRO#CODCIDADE#CIDADE#ESTADO#CEP#DDD#TELEFONE#EMAILNFE#CLIENTEENTREGA#BOLETO#OPSIMPLES#ISENTOST#ICMS";


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

        if (field.equals("RAZAO")) {

            RAZAO.replaceAll("\\n|\\r", " ");

            if (RAZAO.equals("")) return false;

            if (RAZAO.length() > 50) return false;

        }


        	/* FANTASIA */
        if (field.equals("FANTASIA")) {
            FANTASIA.replaceAll("\\n|\\r", " ");

            if (FANTASIA.trim().equals("")) return false;

            if (FANTASIA.trim().length() > 20) return false;
        }


        if (field.equals("CNPJ")) {

            if (CNPJ.equals("")) return false;

            return ValidaCNPJ.isCNPJ(CNPJ.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", ""));

        }

            /* IE */
        if (field.equals("IE")) {

            if (IE.equals("")) return false;

            return true; //ValidaIE.isIE(IE.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", ""), ESTADO);

        }

        	/* FUNDACAO */

        if (field.equals("IE")) {

            if (FUNDACAO.equals("")) return false;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            sdf.setLenient(false);

            try {

                Date data = sdf.parse(FUNDACAO);

                return true;

            } catch (java.text.ParseException e) {

                return false;

            }

        }


        	/* CANAL */
        if (field.equals("CANAL")) {

            if (CANAL.equals("")) return false;

            if (SEEKCanal().getCODIGO().equals("")) return false;
            else return true;

        }

        	/* REDE */
        if (field.equals("REDE")) {

            if (REDE.equals("")) return false;

            if (SEEKRede().getCODIGO().equals("")) return false;
            else return true;

        }

        	/* POLITICA*/

        if (field.equals("POLITICA")) {
            if (POLITICA.equals("")) return false;

            if (SEEKPolitica().getCODIGO().equals("")) return false;
            else return true;

        }

        	/*  TABPRECO */

        if (field.equals("TABPRECO")) {


            if (TABPRECO.equals("")) return false;

            if (SEEKtabpreco().getCODIGO().equals("")) return false;
            else return true;

        }

        if (field.equals("CONDPAGTO")) {

            if (CONDPAGTO.equals("")) return false;

            if (SEEKCondPagto().getCODIGO().equals("")) return false;
            else return true;

        }

        if (field.equals("FUNDACAO")) {

            if (FUNDACAO.equals("")) return false;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            sdf.setLenient(false);

            try {

                Date data = sdf.parse(FUNDACAO);

                return true;

            } catch (java.text.ParseException e) {

                return false;

            }

        }
        if (field.equals("LIMITE")) {

            if (LIMITE == 0) return false;

        }


        if (field.equals("HORARECEB")) {

            int hora = 0;
            int minu = 0;

            if (HORARECEB.equals("00:00")) return true;

            try {

                hora = Integer.valueOf(HORARECEB.substring(0, 2));

                minu = Integer.valueOf(HORARECEB.substring(3, 5));

                if (hora < 0 || hora > 23){

                    return false;
                }

                if (minu < 0 || minu > 59){

                    return false;
                }

                return true;

            } catch (Exception e){


                return false;

            }

        }


        if (field.equals("CIENTE")) {

            if (!CIENTE.equals("SIM")) return false;

        }

        return true;

    }



    public void loadUnidades(){

        UNIDADES = new ArrayList<String>();

        UNIDADES.add(" 5 X 5 =  25"); // 0 - 007
        UNIDADES.add(" 6 X 5 =  30"); // 1 - 002 - 004
        UNIDADES.add(" 7 X 5 =  35"); // 2 - 001 - 003 -
        UNIDADES.add("10 X 7 =  70"); // 3 - 005
        UNIDADES.add("15 X 5 =  75"); // 4 - 008
        UNIDADES.add("17 X 3 =  51");
        UNIDADES.add("17 / 6 = 102"); // 6 - 006

    }

    public List<String> getUNIDADES(){

        return this.UNIDADES;

    }


    public String getUnidPadrao(String grupo){


        if (grupo.equals("007")){

            return UNIDADES.get(0);

        }

        if ((grupo.equals("002")) || (grupo.equals("004"))){

            return UNIDADES.get(1);

        }


        if ((grupo.equals("001")) || (grupo.equals("003"))){

            return UNIDADES.get(2);

        }


        if (grupo.equals("005")){

            return UNIDADES.get(3);

        }


        if (grupo.equals("008")){

            return UNIDADES.get(4);

        }


        if (grupo.equals("006")){

            return UNIDADES.get(6);

        }


        return "";
    }


    public String getTempo(String Inicio, String Fim){

        /* Todo */


        return "";
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
