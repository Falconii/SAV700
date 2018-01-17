package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.dao.CotaDAO;

/**
 * Created by Falconi on 19/10/2016.
 */

public class PedidoDetMB_fast {

    protected String NRO;
    protected String ITEM;
    protected String CPROTHEUS;
    protected String CPROTHEUSB;
    protected String STATUS;
    protected String PRODUTO;
    protected String UM;
    protected Float QTD;
    protected String ORIGEM;
    protected Float PRCVEN;
    protected Float DESCON;
    protected Float DESCVER;
    protected String CODVERBA;
    protected Float DESCVER2;
    protected String CODVERBA2;
    protected Float TOTAL;
    protected Float BONIQTD;
    protected Float BONIPREC;
    protected Float BONITOTAL;
    protected String PEDDISTFIL;
    protected String PEDDIST;
    protected String PEDDISTITEM;
    protected String PEDDISTFIL2;
    protected String PEDDIST2;
    protected String PEDDISTITEM2;
    protected Float PRECOFORMACAO;
    protected Float DESCCONTRATO;
    protected Float PRECOTABELA;
    protected Float ACRESCIMOMAIS;
    protected Float DESCONTOMAIS;
    protected Float DESCONTOPOL;
    protected Float DNADESCONTO;
    protected Float DNAVALOR;
    protected String USATAXAFIN;
    protected String TAXAFIN;
    protected String MENSAGEM;
    protected String ACORDO;
    protected String ACORDO2;
    protected String SIMULADOR;
    protected String SIMULADOR2;
    protected String PEDCLI;
    protected String PEDCLI2;
    protected Float FRETE;
    protected String FATOR;
    protected Float IMPOSTO;
    protected Float CONVERSAO;
    protected String LOTE;
    protected String EMPACOTAMENTO;
    protected String VENCIMENTO;
    protected String OBS;
    protected Integer UNIDADE;
    protected String COTA;
    protected Float VLRCAMPANHA;
    protected Float VLRDESCARGA2;
    protected Float POLITICABASE;
    protected Float CUSTOOPER;
    protected Float BDI;






    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.PedidoDetMb";

    protected String  _Status;
    protected String  _Produto;
    protected String  _Verba;
    protected String  _Verba2;
    protected String  _Acordo;
    protected String  _Acordo2;
    protected String  _Simulador;
    protected String  _Simulador2;
    protected String  _View;
    protected String  _CodGrupo;
    protected String  _Grupo;
    protected String  _CodMarca;
    protected String  _Marca;
    protected Float   _UltimoPreco;
    protected Float   _PesoBruto;
    protected Float   _PesoLiquido;
    protected String  _MotDev;
    protected Boolean _Mix;
    protected Float   _Meta;
    protected Float   _Carteira;
    protected Float   _Realizado;
    protected String  _UsaPolitica;
    protected String  _PedidoMae;


    protected Map<String,String> lsStatus = new TreeMap<String, String >();
    protected Map<Float,Float> lsFreste = new TreeMap<Float, Float >();
    protected List<Boolean> _isValid;
    protected int   _maxcol = 57;
    protected String _tipo  = "";


    public PedidoDetMB_fast() {
        init();
    }

    public PedidoDetMB_fast(
                            String NRO,
                            String ITEM,
                            String CPROTHEUS,
                            String CPROTHEUSB,
                            String STATUS,
                            String PRODUTO,
                            String UM,
                            Float QTD,
                            String ORIGEM,
                            Float PRCVEN,
                            Float DESCON,
                            Float DESCVER,
                            String CODVERBA,
                            Float DESCVER2,
                            String CODVERBA2,
                            Float TOTAL,
                            Float BONIQTD,
                            Float BONIPREC,
                            Float BONITOTAL,
                            String PEDDISTFIL,
                            String PEDDIST,
                            String PEDDISTITEM,
                            String PEDDISTFIL2,
                            String PEDDIST2,
                            String PEDDISTITEM2,
                            Float PRECOFORMACAO,
                            Float DESCCONTRATO,
                            Float PRECOTABELA,
                            Float ACRESCIMOMAIS,
                            Float DESCONTOMAIS,
                            Float DESCONTOPOL,
                            Float DNADESCONTO,
                            Float DNAVALOR,
                            String USATAXAFIN,
                            String TAXAFIN,
                            String MENSAGEM,
                            String ACORDO,
                            String ACORDO2,
                            String SIMULADOR,
                            String SIMULADOR2,
                            String PEDCLI,
                            String PEDCLI2,
                            Float FRETE,
                            String FATOR,
                            Float IMPOSTO,
                            Float CONVERSAO,
                            String LOTE,
                            String EMPACOTAMENTO,
                            String VENCIMENTO,
                            String OBS,
                            Integer UNIDADE,
                            String COTA,
                            Float VLRCAMPANHA,
                            Float VLRDESCARGA2,
                            Float POLITICABASE,
                            Float CUSTOOPER,
                            Float BDI,
                            String _Produto,
                            String _Verba,
                            String _Verba2,
                            String _Acordo,
                            String _Acordo2,
                            String _Simulador,
                            String _Simulador2,
                            String _CodGrupo,
                            String _Grupo,
                            String _CodMarca,
                            String _Marca,
                            Float _UltimoPreco,
                            Float _PesoBruto,
                            Float _PesoLiquido,
                            String _MotDev,
                            Boolean _Mix,
                            Float _Meta,
                            Float _Carteira,
                            Float _Realizado,
                            String _UsaPolitica,
                            String _PedidoMae)
    {
        this.NRO = NRO;
        this.ITEM = ITEM;
        this.CPROTHEUS = CPROTHEUS;
        this.CPROTHEUSB = CPROTHEUSB;
        this.STATUS = STATUS;
        this.PRODUTO = PRODUTO;
        this.UM = UM;
        this.QTD = QTD;
        this.ORIGEM = ORIGEM;
        this.PRCVEN = PRCVEN;
        this.DESCON = DESCON;
        this.DESCVER = DESCVER;
        this.CODVERBA = CODVERBA;
        this.DESCVER2 = DESCVER2;
        this.CODVERBA2 = CODVERBA2;
        this.TOTAL = TOTAL;
        this.BONIQTD = BONIQTD;
        this.BONIPREC = BONIPREC;
        this.BONITOTAL = BONITOTAL;
        this.PEDDISTFIL = PEDDISTFIL;
        this.PEDDIST = PEDDIST;
        this.PEDDISTITEM = PEDDISTITEM;
        this.PEDDISTFIL2 = PEDDISTFIL2;
        this.PEDDIST2 = PEDDIST2;
        this.PEDDISTITEM2 = PEDDISTITEM2;
        this.PRECOFORMACAO = PRECOFORMACAO;
        this.DESCCONTRATO = DESCCONTRATO;
        this.PRECOTABELA = PRECOTABELA;
        this.ACRESCIMOMAIS = ACRESCIMOMAIS;
        this.DESCONTOMAIS = DESCONTOMAIS;
        this.DESCONTOPOL = DESCONTOPOL;
        this.DNADESCONTO = DNADESCONTO;
        this.DNAVALOR = DNAVALOR;
        this.USATAXAFIN = USATAXAFIN;
        this.TAXAFIN = TAXAFIN;
        this.MENSAGEM = MENSAGEM;
        this.ACORDO = ACORDO;
        this.ACORDO2 = ACORDO2;
        this.SIMULADOR = SIMULADOR;
        this.SIMULADOR2 = SIMULADOR2;
        this.PEDCLI = PEDCLI;
        this.PEDCLI2 = PEDCLI2;
        this.FRETE = FRETE;
        this.FATOR = FATOR;
        this.IMPOSTO = IMPOSTO;
        this.CONVERSAO = CONVERSAO;
        this.LOTE = LOTE;
        this.EMPACOTAMENTO = EMPACOTAMENTO;
        this.VENCIMENTO = VENCIMENTO;
        this.OBS = OBS;
        this.UNIDADE = UNIDADE;
        this.COTA    = COTA;
        this.VLRCAMPANHA = VLRCAMPANHA;
        this.VLRDESCARGA2 = VLRDESCARGA2;
        this.POLITICABASE = POLITICABASE;
        this.CUSTOOPER    = CUSTOOPER;
        this.BDI          = BDI;

        //
        this._Produto     = _Produto;
        this._Verba       = _Verba;
        this._Verba2      = _Verba2;
        this._Acordo      = _Acordo;
        this._Acordo2     = _Acordo2;
        this._Simulador   = _Simulador;
        this._Simulador2  = _Simulador2;
        this._CodGrupo    = _CodGrupo;
        this._Grupo       = _Grupo;
        this._CodMarca    = _CodMarca;
        this._Marca       = _Marca;
        this._UltimoPreco = _UltimoPreco;
        this._PesoBruto   = _PesoBruto;
        this._PesoLiquido = _PesoLiquido;
        this._MotDev      = _MotDev;
        this._Mix         = _Mix;
        this._Meta        = _Meta;
        this._Carteira    = _Carteira;
        this._Realizado   = _Realizado;
        this._UsaPolitica = _UsaPolitica;
        this._PedidoMae   = _PedidoMae;

        _ValidaOK();

    }


    public void atualizar(PedidoDetMB_fast obj) {

        this.NRO                = obj.getNRO();
        this.QTD                = obj.getQTD();
        this.CODVERBA           = obj.getCODVERBA();
        this.BONIQTD            = obj.getBONIQTD();
        this.BONIPREC           = obj.getBONIPREC();
        this.CODVERBA2          = obj.getCODVERBA2();
        this.PEDDISTFIL         = obj.getPEDDISTFIL();
        this.PEDDIST            = obj.getPEDDIST();
        this.PEDDISTITEM        = obj.getPEDDISTITEM();
        this.PEDDISTFIL2        = obj.getPEDDISTFIL2();
        this.PEDDIST2           = obj.getPEDDIST2();
        this.PEDDISTITEM2       = obj.getPEDDISTITEM2();
        this.ACORDO             = obj.getACORDO();
        this.ACORDO2            = obj.getACORDO2();
        this.SIMULADOR          = obj.getSIMULADOR();
        this.SIMULADOR2         = obj.getSIMULADOR2();
        this.PEDCLI             = obj.getPEDCLI();
        this.PEDCLI2            = obj.getPEDCLI2();
        this.LOTE               = obj.getLOTE();
        this.EMPACOTAMENTO      = obj.getEMPACOTAMENTO();
        this.VENCIMENTO         = obj.getVENCIMENTO();
        this.OBS                = obj.getOBS();
        this.UNIDADE            = obj.getUNIDADE();
        this.COTA               = obj.getCOTA();
        this.VLRCAMPANHA        = obj.getVLRCAMPANHA();
        this.VLRDESCARGA2       = obj.getVLRDESCARGA2();
        this.POLITICABASE       = obj.getPOLITICABASE();

        //Ajusta preço para sim.,ped dist e cota
        if (!obj.getSIMULADOR().trim().isEmpty() || !obj.getPEDDIST().trim().isEmpty() || !obj.getCOTA().trim().isEmpty()){

            this.setPRCVEN(obj.getPRCVEN());

            return ;
        }

        //
        this._Verba             = obj.get_Verba();
        this._Verba2            = obj.get_Verba2();
        this._Acordo            = obj.get_Acordo();
        this._Acordo2           = obj.get_Acordo2();
        this._Simulador         = obj.get_Simulador();
        this._Simulador2        = obj.get_Simulador2();
        this._UsaPolitica       = obj.get_UsaPolitica();
        this._PedidoMae         = obj.get_PedidoMae();


    }


    private void init(){


        _View  = "G";

        _Produto     = ""  ;
        _Verba       = ""  ;
        _Verba2      = ""  ;
        _Simulador   = ""  ;
        _Simulador2  = ""  ;
        _Acordo      = ""  ;
        _Acordo2     = ""  ;
        _Grupo       = ""  ;
        _Marca       = ""  ;
        _CodGrupo    = ""  ;
        _CodMarca    = ""  ;
        _UltimoPreco = 0f;
        _PesoBruto   = 0f;
        _PesoLiquido = 0f;
        _Mix         = false;
        _Meta        = 0f;
        _Carteira    = 0f;
        _Realizado   = 0f;
        _UsaPolitica = "N";
        _PedidoMae   = "";
        _tipo        = "";

        loadStatusDescricao();

        _ValidaOK();


    }

    private void loadStatusDescricao(){

        lsStatus.put("1" ,"Novo");
        lsStatus.put("2" ,"Incompleto");
        lsStatus.put("3" ,"Liberado");
        lsStatus.put("4" ,"Excluído");
        lsStatus.put("5" ,"Bloqueado");
        lsStatus.put("9" ,"Problema Protheus");
    }

    public String getNRO() {
        return NRO;
    }

    public void setNRO(String NRO) {
        this.NRO = NRO;
    }

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public String getCPROTHEUS() {
        return CPROTHEUS;
    }

    public void setCPROTHEUS(String CPROTHEUS) {
        this.CPROTHEUS = CPROTHEUS;
    }

    public String getCPROTHEUSB() {
        return CPROTHEUSB;
    }

    public void setCPROTHEUSB(String CPROTHEUSB) {
        this.CPROTHEUSB = CPROTHEUSB;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String sTATUS) {

            STATUS = sTATUS;

            try {

                _Status = lsStatus.get(Integer.valueOf(STATUS));

            } catch (Exception e){

                _Status = "Status Não Definido.";

            }

        }

    public String getPRODUTO() {
        return PRODUTO;
    }

    public void setPRODUTO(String PRODUTO) {
        this.PRODUTO = PRODUTO;
    }

    public String getUM() {
        return UM;
    }

    public void setUM(String UM) {
        this.UM = UM;
    }

    public Float getQTD() {
        return QTD;
    }

    public void setQTD(Float QTD) {
        this.QTD = QTD;
    }

    public String getORIGEM() {
        return ORIGEM;
    }

    public void setORIGEM(String ORIGEM) {
        this.ORIGEM = ORIGEM;
    }

    public Float getPRCVEN() {
        return PRCVEN;
    }

    public void setPRCVEN(Float PRCVEN) {
        this.PRCVEN = PRCVEN;
    }

    public Float getDESCON() {
        return DESCON;
    }

    public void setDESCON(Float DESCON) {
        this.DESCON = DESCON;
    }

    public Float getDESCVER() {
        return DESCVER;
    }

    public void setDESCVER(Float DESCVER) {
        this.DESCVER = DESCVER;
    }

    public String getCODVERBA() {
        return CODVERBA;
    }

    public void setCODVERBA(String CODVERBA) {
        this.CODVERBA = CODVERBA;
    }

    public Float getDESCVER2() {
        return DESCVER2;
    }

    public void setDESCVER2(Float DESCVER2) {
        this.DESCVER2 = DESCVER2;
    }

    public String getCODVERBA2() {
        return CODVERBA2;
    }

    public void setCODVERBA2(String CODVERBA2) {
        this.CODVERBA2 = CODVERBA2;
    }

    public Float getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(Float TOTAL) {
        this.TOTAL = TOTAL;
    }

    public Float getBONIQTD() {
        return BONIQTD;
    }

    public void setBONIQTD(Float BONIQTD) {
        this.BONIQTD = BONIQTD;
    }

    public Float getBONIPREC() {
        return BONIPREC;
    }

    public void setBONIPREC(Float BONIPREC) {
        this.BONIPREC = BONIPREC;
    }

    public Float getBONITOTAL() {
        return BONITOTAL;
    }

    public void setBONITOTAL(Float BONITOTAL) {
        this.BONITOTAL = BONITOTAL;
    }

    public String getPEDDIST() {
        return PEDDIST;
    }

    public void setPEDDIST(String PEDDIST) {
        this.PEDDIST = PEDDIST;
    }

    public String getPEDDISTITEM() {
        return PEDDISTITEM;
    }

    public void setPEDDISTITEM(String PEDDISTITEM) {
        this.PEDDISTITEM = PEDDISTITEM;
    }

    public Float getPRECOFORMACAO() {
        return PRECOFORMACAO;
    }

    public void setPRECOFORMACAO(Float PRECOFORMACAO) {
        this.PRECOFORMACAO = PRECOFORMACAO;
    }

    public Float getDESCCONTRATO() {
        return DESCCONTRATO;
    }

    public void setDESCCONTRATO(Float DESCCONTRATO) {
        this.DESCCONTRATO = DESCCONTRATO;
    }

    public Float getPRECOTABELA() {
        return PRECOTABELA;
    }

    public void setPRECOTABELA(Float PRECOTABELA) {
        this.PRECOTABELA = PRECOTABELA;
    }

    public Float getACRESCIMOMAIS() {
        return ACRESCIMOMAIS;
    }

    public void setACRESCIMOMAIS(Float ACRESCIMOMAIS) {
        this.ACRESCIMOMAIS = ACRESCIMOMAIS;
    }

    public Float getDESCONTOMAIS() {
        return DESCONTOMAIS;
    }

    public void setDESCONTOMAIS(Float DESCONTOMAIS) {
        this.DESCONTOMAIS = DESCONTOMAIS;
    }

    public Float getDESCONTOPOL() {
        return DESCONTOPOL;
    }

    public void setDESCONTOPOL(Float DESCONTOPOL) {
        this.DESCONTOPOL = DESCONTOPOL;
    }

    public Float getDNADESCONTO() {
        return DNADESCONTO;
    }

    public void setDNADESCONTO(Float DNADESCONTO) {
        this.DNADESCONTO = DNADESCONTO;
    }

    public Float getDNAVALOR() {
        return DNAVALOR;
    }

    public void setDNAVALOR(Float DNAVALOR) {
        this.DNAVALOR = DNAVALOR;
    }

    public String getUSATAXAFIN() {
        return USATAXAFIN;
    }

    public void setUSATAXAFIN(String USATAXAFIN) {
        this.USATAXAFIN = USATAXAFIN;
    }

    public String getTAXAFIN() {
        return TAXAFIN;
    }

    public void setTAXAFIN(String TAXAFIN) {
        this.TAXAFIN = TAXAFIN;
    }

    public String getMENSAGEM() {
        return MENSAGEM;
    }

    public void setMENSAGEM(String MENSAGEM) {
        this.MENSAGEM = MENSAGEM;
    }

    public String getACORDO() {
        return ACORDO;
    }

    public void setACORDO(String ACORDO) {
        this.ACORDO = ACORDO;
    }

    public String getACORDO2() {
        return ACORDO2;
    }

    public void setACORDO2(String ACORDO2) {
        this.ACORDO2 = ACORDO2;
    }

    public String getSIMULADOR() {
        return SIMULADOR;
    }

    public void setSIMULADOR(String SIMULADOR) {
        this.SIMULADOR = SIMULADOR;
    }

    public String getSIMULADOR2() {
        return SIMULADOR2;
    }

    public void setSIMULADOR2(String SIMULADOR2) {
        this.SIMULADOR2 = SIMULADOR2;
    }

    public Float getFRETE() {
        return FRETE;
    }

    public void setFRETE(Float FRETE) {
        this.FRETE = FRETE;
    }

    public String getFATOR() {
        return FATOR;
    }

    public void setFATOR(String FATOR) {
        this.FATOR = FATOR;
    }

    public Float getIMPOSTO() {
        return IMPOSTO;
    }

    public void setIMPOSTO(Float IMPOSTO) {
        this.IMPOSTO = IMPOSTO;
    }

    public Float getCONVERSAO() {
        return CONVERSAO;
    }

    public void setCONVERSAO(Float CONVERSAO) {
        this.CONVERSAO = CONVERSAO;
    }

    public String getLOTE() {
        return LOTE;
    }

    public void setLOTE(String LOTE) {
        this.LOTE = LOTE;
    }

    public String getEMPACOTAMENTO() {
        return EMPACOTAMENTO;
    }

    public void setEMPACOTAMENTO(String EMPACOTAMENTO) {
        this.EMPACOTAMENTO = EMPACOTAMENTO;
    }

    public String getVENCIMENTO() {
        return VENCIMENTO;
    }

    public void setVENCIMENTO(String VENCIMENTO) {
        this.VENCIMENTO = VENCIMENTO;
    }

    public String getOBS() {
        return OBS;
    }

    public void setOBS(String OBS) {
        this.OBS = OBS;
    }

    public Integer getUNIDADE() {
        return UNIDADE;
    }

    public void setUNIDADE(Integer UNIDADE) {
        this.UNIDADE = UNIDADE;
    }

    public String getPEDDIST2() {
        return PEDDIST2;
    }

    public void setPEDDIST2(String PEDDIST2) {
        this.PEDDIST2 = PEDDIST2;
    }

    public String getPEDDISTITEM2() {
        return PEDDISTITEM2;
    }

    public void setPEDDISTITEM2(String PEDDISTITEM2) {
        this.PEDDISTITEM2 = PEDDISTITEM2;
    }

    public String getPEDCLI() {
        return PEDCLI;
    }

    public void setPEDCLI(String PEDCLI) {
        this.PEDCLI = PEDCLI;
    }

    public String getPEDCLI2() {
        return PEDCLI2;
    }

    public void setPEDCLI2(String PEDCLI2) {
        this.PEDCLI2 = PEDCLI2;
    }

    public String get_Status() {
        return _Status;
    }

    public void set_Status(String _Status) {
        this._Status = _Status;
    }

    public String get_Produto() {
        return _Produto;
    }

    public void set_Produto(String _Produto) {
        this._Produto = _Produto;
    }

    public String get_Verba() {
        return _Verba;
    }

    public void set_Verba(String _Verba) {
        this._Verba = _Verba;
    }

    public String get_Verba2() {
        return _Verba2;
    }

    public void set_Verba2(String _Verba2) {
        this._Verba2 = _Verba2;
    }

    public String get_Acordo() {
        return _Acordo;
    }

    public void set_Acordo(String _Acordo) {
        this._Acordo = _Acordo;
    }

    public String get_View() {
        return _View;
    }

    public void set_View(String _View) {
        this._View = _View;
    }

    public String get_Grupo() {
        return _Grupo;
    }

    public void set_Grupo(String _Grupo) {
        this._Grupo = _Grupo;
    }

    public String get_Marca() {
        return _Marca;
    }

    public void set_Marca(String _Marca) {
        this._Marca = _Marca;
    }

    public String get_Simulador() {
        return _Simulador;
    }

    public void set_Simulador(String _Simulador) {
        this._Simulador = _Simulador;
    }

    public String get_Simulador2() {
        return _Simulador2;
    }

    public void set_Simulador2(String _Simulador2) {
        this._Simulador2 = _Simulador2;
    }

    public String get_Acordo2() {
        return _Acordo2;
    }

    public void set_Acordo2(String _Acordo2) {
        this._Acordo2 = _Acordo2;
    }

    public String getPEDDISTFIL() {
        return PEDDISTFIL;
    }

    public void setPEDDISTFIL(String PEDDISTFIL) {
        this.PEDDISTFIL = PEDDISTFIL;
    }

    public String getPEDDISTFIL2() {
        return PEDDISTFIL2;
    }

    public void setPEDDISTFIL2(String PEDDISTFIL2) {
        this.PEDDISTFIL2 = PEDDISTFIL2;
    }

    public Float get_UltimoPreco() {
        return _UltimoPreco;
    }

    public void set_UltimoPreco(Float _UltimoPreco) {
        this._UltimoPreco = _UltimoPreco;
    }

    public String get_UsaPolitica() {
        return _UsaPolitica;
    }

    public void set_UsaPolitica(String _UsaPolitica) {
        this._UsaPolitica = _UsaPolitica;
    }

    public Boolean get_isValid(int indice) {
        return _isValid.get(indice);
    }

    public void set_isValid(int indice, boolean value) {
        this._isValid.set(indice,value);
    }

    public List<Boolean> get_isValid() {
        return _isValid;
    }

    public void set_isValid(List<Boolean> _isValid) {
        this._isValid = _isValid;
    }

    public String getCOTA() {
        return COTA;
    }

    public void setCOTA(String COTA) {
        this.COTA = COTA;
    }

    public Float getVLRCAMPANHA() {
        return VLRCAMPANHA;
    }

    public void setVLRCAMPANHA(Float VLRCAMPANHA) {
        this.VLRCAMPANHA = VLRCAMPANHA;
    }

    public Float getVLRDESCARGA2() {
        return VLRDESCARGA2;
    }

    public void setVLRDESCARGA2(Float VLRDESCARGA2) {
        this.VLRDESCARGA2 = VLRDESCARGA2;
    }

    public String get_MotDev() {
        return _MotDev;
    }

    public void set_MotDev(String _MotDev) {
        this._MotDev = _MotDev;
    }

    public void _ValidaOK(){

        _isValid = new ArrayList<Boolean>();

        for(int x = 0 ; x < _maxcol ; x++){

            _isValid.add(true);

        }

    }

    public void setSTATUS(){

        String status = "3";

        for (boolean ok : _isValid){

            if (!ok){

                status = "2";

                break;

            }

        }

        setSTATUS(status);


    }

    public Float getPOLITICABASE() {
        return POLITICABASE;
    }

    public void setPOLITICABASE(Float POLITICABASE) {
        this.POLITICABASE = POLITICABASE;
    }

    public Float getCUSTOOPER() {return CUSTOOPER;}

    public void setCUSTOOPER(Float CUSTOOPER) {this.CUSTOOPER = CUSTOOPER;}

    public Float getBDI() {
        return BDI;
    }

    public void setBDI(Float BDI) {
        this.BDI = BDI;
    }

    public String get_CodGrupo() {
        return _CodGrupo;
    }

    public void set_CodGrupo(String _CodGrupo) {
        this._CodGrupo = _CodGrupo;
    }

    public String get_CodMarca() {
        return _CodMarca;
    }

    public void set_CodMarca(String _CodMarca) {
        this._CodMarca = _CodMarca;
    }

    public Float getAproveitamento(){

        Float retorno = 0f;

        try {

            if (this.getDESCVER().compareTo(0f) > 0 || !this.getSIMULADOR().trim().isEmpty()) {

                retorno  = 0f;

            } else {


                if (!this.getCOTA().trim().isEmpty()){

                    // Procurar A Cota

                    CotaDAO dao = new CotaDAO();

                    dao.open();

                    Cota detalhe = dao.seek(new String[]{this.getCOTA()});

                    dao.close();

                    if (detalhe == null){

                        detalhe = new Cota();

                        detalhe.set_PRECOFINAL(this.getPRECOFORMACAO());

                    } else {

                        detalhe.CalculoFinal(this.getDESCCONTRATO(),this.getTAXAFIN(), this.getCONVERSAO());
                    }

                    if (this.getPRCVEN().compareTo(detalhe.get_PRECOFINAL()) > 0)
                        retorno += this.getQTD() * (this.getPRCVEN() - detalhe.get_PRECOFINAL());



                } else {

                    retorno = this.getQTD() * (this.getPRECOFORMACAO() * ((this.getDESCONTOPOL() - this.getDESCON()) / 100));

                    if (this.getPRCVEN().compareTo(this.getPRECOFORMACAO()) > 0)
                        retorno += this.getQTD() * (this.getPRCVEN() - this.getPRECOFORMACAO());
                }
            }

        }catch (Exception e){

            retorno = 0f;

        }

        return retorno;
    }

    public Float get_PesoBruto() {
        return _PesoBruto;
    }

    public void set_PesoBruto(Float _PesoBruto) {
        this._PesoBruto = _PesoBruto;
    }

    public Float get_PesoLiquido() {
        return _PesoLiquido;
    }

    public void set_PesoLiquido(Float _PesoLiquido) {
        this._PesoLiquido = _PesoLiquido;
    }

    public Boolean get_Mix() {

        return _Mix;

    }

    public void set_Mix(Boolean _Mix) {
        this._Mix = _Mix;
    }

    public Float get_Meta() {
        return _Meta;
    }

    public void set_Meta(Float _Meta) {
        this._Meta = _Meta;
    }

    public Float get_Carteira() {
        return _Carteira;
    }

    public void set_Carteira(Float _Carteira) {
        this._Carteira = _Carteira;
    }

    public Float get_Realizado() {
        return _Realizado;
    }

    public void set_Realizado(Float _Realizado) {
        this._Realizado = _Realizado;
    }

    public String get_PedidoMae() {
        return _PedidoMae;
    }

    public void set_PedidoMae(String _PedidoMae) {
        this._PedidoMae = _PedidoMae;
    }

    public Float getLsFreste(String key) {

        Float retorno = 0f;

        try {

            retorno = this.lsFreste.get(key);


        } catch (Exception e){

            retorno = 0f;

        }

        return retorno;
    }

    public Map<Float, Float> getLsFreste() {
        return lsFreste;
    }

    public void setLsFreste(Map<Float, Float> lsFreste) {
        this.lsFreste = lsFreste;
    }

    public void setLsFreste(Float key, Float value) {

        this.lsFreste.put(key,value);

    }

    public String get_tipo() {
        return _tipo;
    }

    public void set_tipo(String _tipo) {
        this._tipo = _tipo;
    }
}
