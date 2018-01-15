package br.com.brotolegal.savdatabase.entities;

import android.util.Log;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.dao.CotaDAO;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class PedidoDetMb extends ObjRegister implements KvmSerializable {

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

	protected String _Status;
	protected String _Produto;
	protected String _Verba;
	protected String _Verba2;
	protected String _Acordo;
	protected String _Acordo2;
	protected String _Simulador;
	protected String _Simulador2;
	protected String _View;
	protected String _Grupo;
	protected String _Marca;
	protected Float _UltimoPreco;
	protected Float  _PesoBruto;
	protected Float  _PesoLiquido;
	protected String  _MotDev;
	protected Boolean _Mix;
	protected Float _Meta;
	protected Float _Carteira;
	protected Float _Realizado;
	protected String _UsaPolitica;



	protected Map<String,String> lsStatus = new TreeMap<String, String >();


	public PedidoDetMb(){

		super(_OBJETO,"PEDIDODETMB");

		loadColunas();

		InicializaFields();

		Novo();
	}

	public PedidoDetMb(String NRO, String ITEM, String CPROTHEUS, String CPROTHEUSB, String STATUS, String PRODUTO, String UM, Float QTD, String ORIGEM, Float PRCVEN, Float DESCON, Float DESCVER, String CODVERBA, Float DESCVER2, String CODVERBA2, Float TOTAL, Float BONIQTD, Float BONIPREC, Float BONITOTAL, String PEDDISTFIL, String PEDDIST, String PEDDISTITEM, String PEDDISTFIL2, String PEDDIST2, String PEDDISTITEM2, Float PRECOFORMACAO, Float DESCCONTRATO, Float PRECOTABELA, Float ACRESCIMOMAIS, Float DESCONTOMAIS, Float DESCONTOPOL, Float DNADESCONTO, Float DNAVALOR, String USATAXAFIN, String TAXAFIN, String MENSAGEM, String ACORDO, String ACORDO2, String SIMULADOR, String SIMULADOR2, String PEDCLI, String PEDCLI2, Float FRETE, String FATOR, Float IMPOSTO, Float CONVERSAO, String LOTE, String EMPACOTAMENTO, String VENCIMENTO, String OBS, Integer UNIDADE, String COTA, Float VLRCAMPANHA, Float VLRDESCARGA2, Float POLITICABASE, Float CUSTOOPER, Float BDI) {

		super(_OBJETO,"PEDIDODETMB");

		loadColunas();

		InicializaFields();
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


		_View  = "G";

		_Produto      = ""  ;
		_Verba        = ""  ;
		_Verba2       = ""  ;
		_Acordo       = ""  ;
		_Acordo2      = ""  ;
		_Simulador    = ""  ;
		_Simulador2   = ""  ;
		_Grupo        = ""  ;
		_Marca        = ""  ;
		_UltimoPreco  = 0f;
		_PesoBruto    = 0f;
		_PesoLiquido  = 0f;
		_Mix          = false;
		_Meta         = 0f;
		_Carteira     = 0f;
		_Realizado    = 0f;
		_UsaPolitica  = "N";

		loadStatusDescricao();
	}

	public void ImportFast(PedidoDetMB_fast obj){

		InicializaFields();

		this.NRO           = obj.getNRO();
		this.ITEM          = obj.getITEM();
		this.CPROTHEUS     = obj.getCPROTHEUS();
		this.CPROTHEUSB    = obj.getCPROTHEUSB();
		this.STATUS        = obj.getSTATUS();
		this.PRODUTO       = obj.getPRODUTO();
		this.UM            = obj.getUM();
		this.QTD           = obj.getQTD();
		this.ORIGEM        = obj.getORIGEM();
		this.PRCVEN        = obj.getPRCVEN();
		this.DESCON        = obj.getDESCON();
		this.DESCVER       = obj.getDESCVER();
		this.CODVERBA 	   = obj.getCODVERBA();
		this.DESCVER2 	   = obj.getDESCVER2();
		this.CODVERBA2     = obj.getCODVERBA2();
		this.TOTAL         = obj.getTOTAL();
		this.BONIQTD       = obj.getBONIQTD();
		this.BONIPREC      = obj.getBONIPREC();
		this.BONITOTAL     = obj.getBONITOTAL();
		this.PEDDISTFIL    = obj.getPEDDISTFIL();
		this.PEDDIST       = obj.getPEDDIST();
		this.PEDDISTITEM   = obj.getPEDDISTITEM();
		this.PEDDISTFIL2   = obj.getPEDDISTFIL2();
		this.PEDDIST2      = obj.getPEDDIST2();
		this.PEDDISTITEM2  = obj.getPEDDISTITEM2();
		this.PRECOFORMACAO = obj.getPRECOFORMACAO();
		this.DESCCONTRATO  = obj.getDESCCONTRATO();
		this.PRECOTABELA   = obj.getPRECOTABELA();
		this.ACRESCIMOMAIS = obj.getACRESCIMOMAIS();
		this.DESCONTOMAIS  = obj.getDESCONTOMAIS();
		this.DESCONTOPOL   = obj.getDESCONTOPOL();
		this.DNADESCONTO   = obj.getDNADESCONTO();
		this.DNAVALOR      = obj.getDNAVALOR();
		this.USATAXAFIN    = obj.getUSATAXAFIN();
		this.TAXAFIN       = obj.getTAXAFIN();
		this.MENSAGEM      = obj.getMENSAGEM();
		this.ACORDO        = obj.getACORDO();
		this.ACORDO2       = obj.getACORDO2();
		this.SIMULADOR     = obj.getSIMULADOR();
		this.SIMULADOR2    = obj.getSIMULADOR2();
		this.PEDCLI        = obj.getPEDCLI();
		this.PEDCLI2       = obj.getPEDCLI2();
		this.FRETE 		   = obj.getFRETE();
		this.FATOR         = obj.getFATOR();
		this.IMPOSTO       = obj.getIMPOSTO();
		this.CONVERSAO     = obj.getCONVERSAO();
		this.LOTE          = obj.getLOTE();
		this.EMPACOTAMENTO = obj.getEMPACOTAMENTO();
		this.VENCIMENTO    = obj.getVENCIMENTO();
		this.OBS           = obj.getOBS();
		this.UNIDADE       = obj.getUNIDADE();
		this.COTA          = obj.getCOTA();
		this.VLRCAMPANHA   = obj.getVLRCAMPANHA();
		this.VLRDESCARGA2  = obj.getVLRDESCARGA2();
		this.POLITICABASE  = obj.getPOLITICABASE();
		this.CUSTOOPER     = obj.getCUSTOOPER();
		this.BDI           = obj.getBDI();
		this._isValid      = obj.get_isValid();

		_View       = "G";

		_Produto    =  obj.get_Produto();
		_Verba      =  obj.get_Verba();
		_Verba2     =  obj.get_Verba2();
		_Acordo     =  obj.get_Acordo();
		_Acordo2    =  obj.get_Acordo2();
		_Simulador  =  obj.get_Simulador();
		_Simulador2 =  obj.get_Simulador2();
		_Grupo      =  obj.get_CodGrupo();
		_Marca      =  obj.get_CodMarca();
		_UltimoPreco = obj.get_UltimoPreco();
		_PesoBruto   = obj.get_PesoBruto();
		_PesoLiquido = obj.get_PesoLiquido();
		_MotDev      = obj.get_MotDev();
		_Mix         = obj.get_Mix();
		_Meta        = obj.get_Meta();
		_Carteira    = obj.get_Carteira();
		_Realizado   = obj.get_Realizado();
		_UsaPolitica = obj.get_UsaPolitica();


		loadStatusDescricao();


	}


	public PedidoDetMB_fast ToFast(){

		PedidoDetMB_fast obj = new PedidoDetMB_fast();

		obj.setNRO(this.getNRO());
		obj.setITEM(this.getITEM());
		obj.setCPROTHEUS(this.getCPROTHEUS());
		obj.setCPROTHEUSB(this.getCPROTHEUSB());
		obj.setSTATUS(this.getSTATUS());
		obj.setPRODUTO(this.getPRODUTO());
		obj.setUM(this.getUM());
		obj.setQTD(this.getQTD());
		obj.setORIGEM(this.getORIGEM());
		obj.setPRCVEN(this.getPRCVEN());
		obj.setDESCON(this.getDESCON());
		obj.setDESCVER(this.getDESCVER());
		obj.setCODVERBA(this.getCODVERBA());
		obj.setDESCVER2(this.getDESCVER2());
		obj.setCODVERBA2(this.getCODVERBA2());
		obj.setTOTAL(this.getTOTAL());
		obj.setBONIQTD(this.getBONIQTD());
		obj.setBONIPREC(this.getBONIPREC());
		obj.setBONITOTAL(this.getBONITOTAL());
		obj.setPEDDISTFIL(this.getPEDDISTFIL());
		obj.setPEDDIST(this.getPEDDIST());
		obj.setPEDDISTITEM(this.getPEDDISTITEM());
		obj.setPEDDISTFIL2(this.getPEDDISTFIL2());
		obj.setPEDDIST2(this.getPEDDIST2());
		obj.setPEDDISTITEM2(this.getPEDDISTITEM2());
		obj.setPRECOFORMACAO(this.getPRECOFORMACAO());
		obj.setDESCCONTRATO(this.getDESCCONTRATO());
		obj.setPRECOTABELA(this.getPRECOTABELA());
		obj.setACRESCIMOMAIS(this.getACRESCIMOMAIS());
		obj.setDESCONTOMAIS(this.getDESCONTOMAIS());
		obj.setDESCONTOPOL(this.getDESCONTOPOL());
		obj.setDNADESCONTO(this.getDNADESCONTO());
		obj.setDNAVALOR(this.getDNAVALOR());
		obj.setUSATAXAFIN(this.getUSATAXAFIN());
		obj.setTAXAFIN(this.getTAXAFIN());
		obj.setMENSAGEM(this.getMENSAGEM());
		obj.setACORDO(this.getACORDO());
		obj.setACORDO2(this.getACORDO2());
		obj.setSIMULADOR(this.getSIMULADOR());
		obj.setSIMULADOR2(this.getSIMULADOR2());
		obj.setPEDCLI(this.PEDCLI);
		obj.setPEDCLI2(this.PEDCLI2);
		obj.setFRETE(this.getFRETE());
		obj.setFATOR(this.getFATOR());
		obj.setIMPOSTO(this.getIMPOSTO());
		obj.setCONVERSAO(this.getCONVERSAO());
		obj.setLOTE(this.getLOTE());
		obj.setEMPACOTAMENTO(this.getEMPACOTAMENTO());
		obj.setVENCIMENTO(this.getVENCIMENTO());
		obj.setOBS(this.getOBS());
		obj.setUNIDADE(this.getUNIDADE());
		obj.setCOTA(this.COTA);
		obj.setUNIDADE(this.UNIDADE);
		obj.setCOTA(this.COTA);
		obj.setVLRCAMPANHA(this.VLRCAMPANHA);
		obj.setVLRDESCARGA2(this.VLRDESCARGA2);
		obj.setPOLITICABASE(this.POLITICABASE);
		obj.setCUSTOOPER(this.CUSTOOPER);
		obj.setBDI(this.BDI);

		obj.set_isValid(this.get_isValid());
		obj.set_View(_View);
		obj.set_Produto(_Produto);
		obj.set_Verba(_Verba);
		obj.set_Verba2(_Verba2);
		obj.set_Acordo(_Acordo);
		obj.set_Acordo2(_Acordo2);
		obj.set_Simulador(_Simulador);
		obj.set_Simulador2(_Simulador2);
		obj.set_CodGrupo(_Grupo);
		obj.set_CodMarca(_Marca);
		obj.set_UltimoPreco(_UltimoPreco);
		obj.set_PesoBruto(_PesoBruto);
		obj.set_PesoLiquido(_PesoLiquido);
		obj.set_MotDev(_MotDev);
		obj.set_Mix(_Mix);
		obj.set_Meta(_Meta);
		obj.set_Carteira(_Carteira);
		obj.set_Realizado(_Realizado);
		obj.set_UsaPolitica(_UsaPolitica);


		return obj;
	}

	private void Novo(){


		InicializaFields();

		STATUS       = "1" ;
		MENSAGEM     = ""  ;
		_View        = "G" ;
		_Produto     = ""  ;
		_Verba       = ""  ;
		_Verba2      = ""  ;
		_Acordo      = ""  ;
		_Acordo2     = ""  ;
		_Simulador   = ""  ;
		_Simulador2  = ""  ;
		_Grupo       = ""  ;
		_Marca       = ""  ;
		_UltimoPreco = 0f;
		_PesoBruto   = 0f;
		_PesoLiquido = 0f;
		_MotDev      = "";
		_Mix         = false;
		_Meta        = 0f;
		_Carteira    = 0f;
		_Realizado   = 0f;
		_UsaPolitica = "N";

		loadStatusDescricao();

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

	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS;
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

	public String getSIMULADOR() {
		return SIMULADOR;
	}

	public void setSIMULADOR(String SIMULADOR) {
		this.SIMULADOR = SIMULADOR;
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

	public String get_Produto() {
		return _Produto;
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

	public void set_Produto(String _Produto) {
		this._Produto = _Produto;
	}

	public String getACORDO2() {
		return ACORDO2;
	}

	public void setACORDO2(String ACORDO2) {
		this.ACORDO2 = ACORDO2;
	}

	public String getSIMULADOR2() {
		return SIMULADOR2;
	}

	public void setSIMULADOR2(String SIMULADOR2) {
		this.SIMULADOR2 = SIMULADOR2;
	}

	public String get_Acordo2() {
		return _Acordo2;
	}

	public void set_Acordo2(String _Acordo2) {
		this._Acordo2 = _Acordo2;
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

	public Integer getUNIDADE() {
		return UNIDADE;
	}

	public void setUNIDADE(Integer UNIDADE) {
		this.UNIDADE = UNIDADE;
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

	public Float getAproveitamento(){

		Float retorno = 0f;

		try {

			if (this.getDESCVER().compareTo(0f) > 0 || !this.getSIMULADOR().trim().isEmpty()) {

				retorno  = 0f;

			} else {

				if (!this.getCOTA().trim().isEmpty()) {

					// Procurar A Cota

					CotaDAO dao = new CotaDAO();

					dao.open();

					Cota detalhe = dao.seek(new String[]{this.getCOTA()});

					dao.close();

					if (detalhe == null) {

						detalhe = new Cota();

						detalhe.set_PRECOFINAL(this.getPRECOFORMACAO());

					} else {

						detalhe.CalculoFinal(this.getDESCCONTRATO(), this.getTAXAFIN(),this.getCONVERSAO());
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

	public Map<String, String> getLsStatus() {
		return lsStatus;
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

	public void setLsStatus(Map<String, String> lsStatus) {
		this.lsStatus = lsStatus;
	}

	public String  get_Status(){

		String retorno = "";

		try {

			retorno = lsStatus.get(STATUS);

		} catch (Exception e) {

			retorno = "Status Não Definido !!!";

		}

		return retorno;
	}

	protected void loadStatusDescricao(){

		lsStatus.put("1" ,"Novo");
		lsStatus.put("2" ,"Incompleto");
		lsStatus.put("3" ,"Liberado");
		lsStatus.put("4" ,"Excluído");
		lsStatus.put("5" ,"Bloqueado");
		lsStatus.put("9" ,"Problema Protheus");
	}

	public void _ValidaOK(){

		_isValid = new ArrayList<Boolean>();

		for(int x = 0 ; x<_colunas.size() ; x++){

			_isValid.add(true);


		}

	}

	public void set_isValid(int indice, boolean value) {
		this._isValid.set(indice,value);
	}

	public List<Boolean> get_isValid() {
		return _isValid;
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

	public void set_Status(String _Status) {
		this._Status = _Status;
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

	public String get_UsaPolitica() {
		return _UsaPolitica;
	}

	public void set_UsaPolitica(String _UsaPolitica) {
		this._UsaPolitica = _UsaPolitica;
	}

	public Float getPOLITICABASE() {
		return POLITICABASE;
	}

	public void setPOLITICABASE(Float POLITICABASE) {
		this.POLITICABASE = POLITICABASE;
	}

	@Override
	public void loadColunas() {

		_colunas = new ArrayList<String>();
		_colunas.add("NRO");
		_colunas.add("ITEM");
		_colunas.add("CPROTHEUS");
		_colunas.add("CPROTHEUSB");
		_colunas.add("STATUS");
		_colunas.add("PRODUTO");
		_colunas.add("UM");
		_colunas.add("QTD");
		_colunas.add("ORIGEM");
		_colunas.add("PRCVEN");
		_colunas.add("DESCON");
		_colunas.add("DESCVER");
		_colunas.add("CODVERBA");
		_colunas.add("DESCVER2");
		_colunas.add("CODVERBA2");
		_colunas.add("TOTAL");
		_colunas.add("BONIQTD");
		_colunas.add("BONIPREC");
		_colunas.add("BONITOTAL");
		_colunas.add("PEDDISTFIL");
		_colunas.add("PEDDIST");
		_colunas.add("PEDDISTITEM");
		_colunas.add("PEDDISTFIL2");
		_colunas.add("PEDDIST2");
		_colunas.add("PEDDISTITEM2");
		_colunas.add("PRECOFORMACAO");
		_colunas.add("DESCCONTRATO");
		_colunas.add("PRECOTABELA");
		_colunas.add("ACRESCIMOMAIS");
		_colunas.add("DESCONTOMAIS");
		_colunas.add("DESCONTOPOL");
		_colunas.add("DNADESCONTO");
		_colunas.add("DNAVALOR");
		_colunas.add("USATAXAFIN");
		_colunas.add("TAXAFIN");
		_colunas.add("MENSAGEM");
		_colunas.add("ACORDO");
		_colunas.add("ACORDO2");
		_colunas.add("SIMULADOR");
		_colunas.add("SIMULADOR2");
		_colunas.add("PEDCLI");
		_colunas.add("PEDCLI2");
		_colunas.add("FRETE");
		_colunas.add("FATOR");
		_colunas.add("IMPOSTO");
		_colunas.add("CONVERSAO");
		_colunas.add("LOTE");
		_colunas.add("EMPACOTAMENTO");
		_colunas.add("VENCIMENTO");
		_colunas.add("OBS");
		_colunas.add("UNIDADE");
		_colunas.add("COTA");
		_colunas.add("VLRCAMPANHA");
		_colunas.add("VLRDESCARGA2");
		_colunas.add("POLITICABASE");
		_colunas.add("CUSTOOPER");
		_colunas.add("BDI");


		_ValidaOK();

	}

	@Override
	public Object getProperty(int index) {

		Object retorno = null;

		try{

			String fieldname ;

			fieldname = _colunas.get(index);

			if (getTypeByName(fieldname).equals(ObjRegister._string)){


				retorno = (String)  getFieldByName(fieldname);

			}
			if (getTypeByName(fieldname).equals(ObjRegister._float)){

				retorno = String.valueOf((Float) getFieldByName(fieldname));

			}
			if (getTypeByName(fieldname).equals(ObjRegister._integer)){

				retorno = String.valueOf((Integer)  getFieldByName(fieldname));

			}
			if (getTypeByName(fieldname).equals(ObjRegister._long)){

				retorno = String.valueOf((Long)  getFieldByName(fieldname));

			}


		} catch (Exception e){

			Log.i("KSOAP", e.getMessage());

		}

		return retorno;

	}

	@Override
	public int getPropertyCount() {

		return _colunas.size();



	}

	@Override
	public void getPropertyInfo(int  index, Hashtable arg1, PropertyInfo info) {

		String fieldname ;

		fieldname = _colunas.get(index);

		info.name = fieldname;

		info.type = PropertyInfo.STRING_CLASS;

	}

	@Override
	public void setProperty(int index, Object value) {


		try {
			String fieldname ;

			fieldname = _colunas.get(index);

			if (getTypeByName(fieldname).equals(ObjRegister._string)){

				setFieldByName(fieldname, (String) value);

			}
			if (getTypeByName(fieldname).equals(ObjRegister._float)){

				setFieldByName(fieldname, (Float) value);

			}
			if (getTypeByName(fieldname).equals(ObjRegister._integer)){

				setFieldByName(fieldname, (Integer) value);

			}
			if (getTypeByName(fieldname).equals(ObjRegister._long)){

				setFieldByName(fieldname, (Long) value);

			}
		} catch (Exception e){

			Log.i("KSOAP",e.getMessage());

		}

	}

}
