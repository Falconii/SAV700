package br.com.brotolegal.savdatabase.entities;

import android.util.Log;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class PedidoCabMb_fast  {

	protected String NRO;
	protected String CPROTHEUS;
	protected String CPROTHEUSB;
	protected String STATUS;
	protected String EMISSAO;
	protected String ENTREGA;
	protected String TIPO;
	protected String DTTRANS;
	protected String HOTRANS;
	protected String CODIGOFAT;
	protected String LOJAFAT;
	protected String CODIGOENT;
	protected String LOJAENT;
	protected String COND;
	protected String TABPRECO;
	protected String PREPEDIDO;
	protected String PREPLANILHA;
	protected String PROPOSTA;
	protected String CONTRATO;
	protected String OBSPED;
	protected String OBSNF;
	protected String AGPROTOCOLO;
	protected String AGDATA;
	protected String AGHORA;
	protected Float TOTALPEDIDO;
	protected Float TOTALDESCONTO;
	protected Float TOTALVERBA;
	protected String VEND;
	protected String PEDCLIENTE;
	protected Float QTDBINIFICADA;
	protected Float VLRBONIFICADO;
	protected Float PESOBRUTO;
	protected Float PESOLIQUIDO;
	protected String RETIRA;
	protected Float DESCRET;
	protected String MENSAGEM;
	protected Float FDSPREVISTO;
	protected Float FDSREAIS;
	protected Float APROVEITAMENTO;
	protected String PEDCLI2;
	protected String CCOPIAPEDIDO;
	protected String CEMAILCOPIAPEDIDO;
	protected Float SALDOAPROVEITAMENTO;
	protected Integer QTDENTREGA;






	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.PedidoCabMb";

	protected String _ClienteFatRazao;
	protected String _ClienteFatCnpj;
	protected String _ClienteFatIE;
	protected String _ClienteFatCidade;
	protected String _ClienteFatTelefone;
	protected String _ClienteEntRazao;
	protected String _ClienteFatFantasia;
	protected String _ClienteEntCidade;
	protected String _ClienteEntTelefone;
	protected String _ClienteCodRede;
	protected String _TabPreco;
	protected String _Rede;
	protected String _Cond;


	protected String  _Status;
	protected String  _Tipo;
	protected String  _Retira;
	protected Boolean _ViewPedido;

	protected Map<Integer,String> lsStatus = new TreeMap<Integer, String >();
	protected Map<String,String>  lsTipos  = new TreeMap<String, String >();

	public PedidoCabMb_fast(){

	}

	public PedidoCabMb_fast(String NRO, String CPROTHEUS, String CPROTHEUSB, String STATUS, String EMISSAO, String ENTREGA, String TIPO, String DTTRANS, String HOTRANS, String CODIGOFAT, String LOJAFAT, String CODIGOENT, String LOJAENT, String COND, String TABPRECO, String PREPEDIDO, String PREPLANILHA, String PROPOSTA, String CONTRATO, String OBSPED, String OBSNF, String AGPROTOCOLO, String AGDATA, String AGHORA, Float TOTALPEDIDO, Float TOTALDESCONTO, Float TOTALVERBA, String VEND, String PEDCLIENTE, Float QTDBINIFICADA, Float VLRBONIFICADO, Float PESOBRUTO, Float PESOLIQUIDO, String RETIRA, Float DESCRET, String MENSAGEM, Float FDSPREVISTO, Float FDSREAIS, Float APROVEITAMENTO, String PEDCLI2,String CCOPIAPEDIDO, String CEMAILCOPIAPEDIDO, Float SALDOAPROVEITAMENTO, Integer QTDENTREGA) {


		this._ClienteFatRazao    =  "";
		this._ClienteFatCnpj     =  "";
		this._ClienteFatIE       =  "";
		this._ClienteFatCidade   =  "";
		this._ClienteFatTelefone =  "";
		this._ClienteEntRazao    =  "";
		this._ClienteFatFantasia =  "";
		this._ClienteEntRazao    =  "";
        this._ClienteEntCidade   =  "";
		this._ClienteEntTelefone =  "";
		this._ClienteCodRede     =  "";
		this._TabPreco           =  "";
		this._Rede               =  "";
		this._Cond               =  "";
		_Retira                  =  "2";
		_ViewPedido              =  false;




		this.NRO = NRO;
		this.CPROTHEUS = CPROTHEUS;
		this.CPROTHEUSB = CPROTHEUSB;
		this.STATUS = STATUS;
		this.EMISSAO = EMISSAO;
		this.ENTREGA = ENTREGA;
		this.TIPO = TIPO;
		this.DTTRANS = DTTRANS;
		this.HOTRANS = HOTRANS;
		this.CODIGOFAT = CODIGOFAT;
		this.LOJAFAT = LOJAFAT;
		this.CODIGOENT = CODIGOENT;
		this.LOJAENT = LOJAENT;
		this.COND = COND;
		this.TABPRECO = TABPRECO;
		this.PREPEDIDO = PREPEDIDO;
		this.PREPLANILHA = PREPLANILHA;
		this.PROPOSTA = PROPOSTA;
		this.CONTRATO = CONTRATO;
		this.OBSPED = OBSPED;
		this.OBSNF = OBSNF;
		this.AGPROTOCOLO = AGPROTOCOLO;
		this.AGDATA = AGDATA;
		this.AGHORA = AGHORA;
		this.TOTALPEDIDO = TOTALPEDIDO;
		this.TOTALDESCONTO = TOTALDESCONTO;
		this.TOTALVERBA = TOTALVERBA;
		this.VEND = VEND;
		this.PEDCLIENTE = PEDCLIENTE;
		this.QTDBINIFICADA = QTDBINIFICADA;
		this.VLRBONIFICADO = VLRBONIFICADO;
		this.PESOBRUTO = PESOBRUTO;
		this.PESOLIQUIDO = PESOLIQUIDO;
		this.RETIRA = RETIRA;
		this.DESCRET = DESCRET;
		this.MENSAGEM = MENSAGEM;
		this.FDSPREVISTO = FDSPREVISTO;
		this.FDSREAIS = FDSREAIS;
		this.APROVEITAMENTO = APROVEITAMENTO;
		this.PEDCLI2 = PEDCLI2;
		this.CCOPIAPEDIDO = CCOPIAPEDIDO;
		this.CEMAILCOPIAPEDIDO = CEMAILCOPIAPEDIDO;
		this.SALDOAPROVEITAMENTO =  SALDOAPROVEITAMENTO;
		this.QTDENTREGA          = QTDENTREGA;


	}


	public void Complemento(String _ClienteFatRazao,
							String _ClienteFatCnpj,
							String _ClienteFatIE,
							String _ClienteFatCidade,
							String _ClienteFatTelefone,
							String _ClienteEntRazao,
							String _ClienteFatFantasia,
							String _ClienteEntCidade,
							String _ClienteEntTelefone,
							String _ClienteCodRede,
							String _TabPreco,
							String _Rede,
							String _Cond) {
		this._ClienteFatRazao = _ClienteFatRazao;
		this._ClienteFatCnpj = _ClienteFatCnpj;
		this._ClienteFatIE = _ClienteFatIE;
		this._ClienteFatCidade = _ClienteFatCidade;
		this._ClienteFatTelefone = _ClienteFatTelefone;
		this._ClienteEntRazao = _ClienteEntRazao;
		this._ClienteFatFantasia = _ClienteFatFantasia;
		this._ClienteEntCidade = _ClienteEntCidade;
		this._ClienteEntTelefone = _ClienteEntTelefone;
		this._ClienteCodRede = _ClienteCodRede;
		this._TabPreco = _TabPreco;
		this._Rede = _Rede;
		this._Cond = _Cond;
	}

	public String getNRO() {
		return NRO;
	}






	public void setNRO(String nRO) {
		NRO = nRO;
	}






	public String getCPROTHEUS() {
		return CPROTHEUS;
	}






	public void setCPROTHEUS(String pROTHEUS) {
		CPROTHEUS = pROTHEUS;
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






	public String getEMISSAO() {
		return EMISSAO;
	}






	public void setEMISSAO(String eMISSAO) {
		EMISSAO = eMISSAO;
	}






	public String getENTREGA() {
		return ENTREGA;
	}






	public void setENTREGA(String eNTREGA) {
		ENTREGA = eNTREGA;
	}






	public String getTIPO() {
		return TIPO;
	}






	public void setTIPO(String tIPO) {

		TIPO = tIPO;

		_Tipo = get_Tipo();


	}






	public String getDTTRANS() {
		return DTTRANS;
	}






	public void setDTTRANS(String dTTRANS) {
		DTTRANS = dTTRANS;
	}






	public String getHOTRANS() {
		return HOTRANS;
	}






	public void setHOTRANS(String hOTRANS) {
		HOTRANS = hOTRANS;
	}






	public String getCODIGOFAT() {
		return CODIGOFAT;
	}






	public void setCODIGOFAT(String cODIGOFAT) {
		CODIGOFAT = cODIGOFAT;
	}






	public String getLOJAFAT() {
		return LOJAFAT;
	}






	public void setLOJAFAT(String lOJAFAT) {
		LOJAFAT = lOJAFAT;
	}






	public String getCODIGOENT() {
		return CODIGOENT;
	}






	public void setCODIGOENT(String cODIGOENT) {
		CODIGOENT = cODIGOENT;
	}






	public String getLOJAENT() {
		return LOJAENT;
	}






	public void setLOJAENT(String lOJAENT) {
		LOJAENT = lOJAENT;
	}






	public String getCOND() {
		return COND;
	}






	public void setCOND(String cOND) {
		COND = cOND;
	}






	public String getTABPRECO() {
		return TABPRECO;
	}






	public void setTABPRECO(String tABPRECO) {
		TABPRECO = tABPRECO;
	}






	public String getPREPEDIDO() {
		return PREPEDIDO;
	}






	public void setPREPEDIDO(String pREPEDIDO) {
		PREPEDIDO = pREPEDIDO;
	}






	public String getPREPLANILHA() {
		return PREPLANILHA;
	}






	public void setPREPLANILHA(String pREPLANILHA) {
		PREPLANILHA = pREPLANILHA;
	}






	public String getPROPOSTA() {
		return PROPOSTA;
	}






	public void setPROPOSTA(String pROPOSTA) {
		PROPOSTA = pROPOSTA;
	}






	public String getCONTRATO() {
		return CONTRATO;
	}






	public void setCONTRATO(String cONTRATO) {
		CONTRATO = cONTRATO;
	}






	public String getOBSPED() {
		return OBSPED;
	}






	public void setOBSPED(String oBSPED) {
		OBSPED = oBSPED;
	}






	public String getOBSNF() {
		return OBSNF;
	}






	public void setOBSNF(String oBSNF) {
		OBSNF = oBSNF;
	}






	public String getAGPROTOCOLO() {
		return AGPROTOCOLO;
	}






	public void setAGPROTOCOLO(String aGPROTOCOLO) {
		AGPROTOCOLO = aGPROTOCOLO;
	}






	public String getAGDATA() {
		return AGDATA;
	}






	public void setAGDATA(String aGDATA) {
		AGDATA = aGDATA;
	}






	public String getAGHORA() {
		return AGHORA;
	}






	public void setAGHORA(String aGHORA) {
		AGHORA = aGHORA;
	}






	public Float getTOTALPEDIDO() {
		return TOTALPEDIDO;
	}






	public void setTOTALPEDIDO(Float tOTALPEDIDO) {
		TOTALPEDIDO = tOTALPEDIDO;
	}






	public Float getTOTALDESCONTO() {
		return TOTALDESCONTO;
	}






	public void setTOTALDESCONTO(Float tOTALDESCONTO) {
		TOTALDESCONTO = tOTALDESCONTO;
	}






	public Float getTOTALVERBA() {
		return TOTALVERBA;
	}






	public void setTOTALVERBA(Float tOTALVERBA) {
		TOTALVERBA = tOTALVERBA;
	}






	public String getVEND() {
		return VEND;
	}






	public void setVEND(String vEND) {
		VEND = vEND;
	}






	public String getPEDCLIENTE() {
		return PEDCLIENTE;
	}






	public void setPEDCLIENTE(String pEDCLIENTE) {
		PEDCLIENTE = pEDCLIENTE;
	}






	public Float getQTDBINIFICADA() {
		return QTDBINIFICADA;
	}






	public void setQTDBINIFICADA(Float qTDBINIFICADA) {
		QTDBINIFICADA = qTDBINIFICADA;
	}






	public Float getVLRBONIFICADO() {
		return VLRBONIFICADO;
	}






	public void setVLRBONIFICADO(Float vLRBONIFICADO) {
		VLRBONIFICADO = vLRBONIFICADO;
	}






	public Float getPESOBRUTO() {
		return PESOBRUTO;
	}






	public void setPESOBRUTO(Float pESOBRUTO) {
		PESOBRUTO = pESOBRUTO;
	}






	public Float getPESOLIQUIDO() {
		return PESOLIQUIDO;
	}






	public void setPESOLIQUIDO(Float pESOLIQUIDO) {
		PESOLIQUIDO = pESOLIQUIDO;
	}






	public String getRETIRA() {
		return RETIRA;
	}






	public void setRETIRA(String rETIRA) {
		RETIRA = rETIRA;

	}

	public Float getDESCRET() {
		return DESCRET;
	}

	public void setDESCRET(Float DESCRET) {
		this.DESCRET = DESCRET;
	}

	public String getMENSAGEM() {
		return MENSAGEM;
	}






	public void setMENSAGEM(String mENSAGEM) {
		MENSAGEM = mENSAGEM;
	}


	public Float getFDSPREVISTO() {
		return FDSPREVISTO;
	}

	public void setFDSPREVISTO(Float FDSPREVISTO) {
		this.FDSPREVISTO = FDSPREVISTO;
	}

	public Float getFDSREAIS() {
		return FDSREAIS;
	}

	public void setFDSREAIS(Float FDSREAIS) {
		this.FDSREAIS = FDSREAIS;
	}

	public Float getAPROVEITAMENTO() {
		return APROVEITAMENTO;
	}

	public void setAPROVEITAMENTO(Float APROVEITAMENTO) {
		this.APROVEITAMENTO = APROVEITAMENTO;
	}

	public String getPEDCLI2() {
		return PEDCLI2;
	}

	public void setPEDCLI2(String PEDCLI2) {
		this.PEDCLI2 = PEDCLI2;
	}

	public String getCCOPIAPEDIDO() {
		return CCOPIAPEDIDO;
	}

	public void setCCOPIAPEDIDO(String CCOPIAPEDIDO) {
		this.CCOPIAPEDIDO = CCOPIAPEDIDO;
	}

	public String getCEMAILCOPIAPEDIDO() {
		return CEMAILCOPIAPEDIDO;
	}

	public void setCEMAILCOPIAPEDIDO(String CEMAILCOPIAPEDIDO) {
		this.CEMAILCOPIAPEDIDO = CEMAILCOPIAPEDIDO;
	}

	public Float getSALDOAPROVEITAMENTO() {
		return SALDOAPROVEITAMENTO;
	}

	public void setSALDOAPROVEITAMENTO(Float SALDOAPROVEITAMENTO) {
		this.SALDOAPROVEITAMENTO = SALDOAPROVEITAMENTO;
	}

	public Integer getQTDENTREGA() {
		return QTDENTREGA;
	}

	public void setQTDENTREGA(Integer QTDENTREGA) {
		this.QTDENTREGA = QTDENTREGA;
	}

	private String getTipo(String Tipo){

		String retorno = "";

		List<String> tipos = new ArrayList<String>();

		for (String tipo : tipos){

			if (tipo.equals(Tipo)) {

				retorno = tipo;

				break;

			}

		}


		return retorno;


	}

	private String getStatus(String Statu){

		String retorno = "";

		List<String> Status = new ArrayList<String>();

		for (String statu : Status){

			if (statu.equals(Status)) {

				retorno = statu;

				break;

			}

		}


		return retorno;


	}


	public String get_ClienteFatRazao() {
		return _ClienteFatRazao;
	}












	public void set_ClienteFatRazao(String _ClienteFatRazao) {
		this._ClienteFatRazao = _ClienteFatRazao;
	}












	public String get_ClienteFatCnpj() {
		return _ClienteFatCnpj;
	}












	public void set_ClienteFatCnpj(String _ClienteFatCnpj) {
		this._ClienteFatCnpj = _ClienteFatCnpj;
	}












	public String get_ClienteFatIE() {
		return _ClienteFatIE;
	}












	public void set_ClienteFatIE(String _ClienteFatIE) {
		this._ClienteFatIE = _ClienteFatIE;
	}












	public String get_ClienteEntRazao() {
		return _ClienteEntRazao;
	}







	public void set_ClienteEntRazao(String _ClienteEntRazao) {
		this._ClienteEntRazao = _ClienteEntRazao;
	}






	public String get_ClienteCodRede() {
		return _ClienteCodRede;
	}




	public void set_ClienteCodRede(String _ClienteCodRede) {
		this._ClienteCodRede = _ClienteCodRede;
	}





	public String get_Cond() {
		return _Cond;
	}


	public void set_Cond(String _Cond) {
		this._Cond = _Cond;
	}


	public String get_TabPreco() {
		return _TabPreco;
	}


	public void set_TabPreco(String _TabPreco) {

		this._TabPreco = _TabPreco;
	}

	public String  get_Tipo(){

		String retorno = "";

		try {

			retorno = lsTipos.get(TIPO);

		} catch (Exception e) {

			retorno = "Tipo Não Definido !!!";

		}

		return retorno;
	}




	public String  get_Status(){

		String retorno = "";

		try {

			retorno = lsStatus.get(Integer.valueOf(STATUS));

		} catch (Exception e) {

			retorno = "Status Não Definido !!!";

		}

		return retorno;
	}



	public String get_Retira() {

		DecimalFormat format_02 = new DecimalFormat(",##0.00");

		_Retira = App.TotvsSIMNAO(RETIRA);

		if (this.DESCRET.compareTo(0f) > 0){


			_Retira += " " + format_02.format(this.DESCRET);

		}

		return _Retira;
	}


	public List<String[]> getlsTipos(){

		List<String[]> retorno = new ArrayList<>();

		for ( Entry<String, String> tipos: lsTipos.entrySet()){

			retorno.add(new String[] {tipos.getKey(),tipos.getValue()});

		}

		return retorno;

	}


	public int getlsTiposIndex(){

		int retorno = -1;

		int x = 0;

		for ( Entry<String, String> tipos: lsTipos.entrySet()){

			if (tipos.getKey().equals(TIPO)){

				retorno = x;

				break;

			}

			x++;
		}

		return retorno;

	}

	protected void loadStatusDescricao(){

		lsStatus.put(00 ,"AUTOMATICO");
		lsStatus.put(01 ,"Em Digitação");
		lsStatus.put(02 ,"Incomplento");
		lsStatus.put(03 ,"A transmitir");
		lsStatus.put(04 ,"Aguard. Finalização");
		lsStatus.put(05 ,"Trans.Com Sucesso");
		lsStatus.put(06 ,"Trans. Com Falha");
		lsStatus.put(98 ,"Fora Da Validade");
		lsStatus.put(99 ,"Erro De Processamento");

	}


	public void loadTipoDescricao(){

		lsTipos  = new TreeMap<String, String >();

		if ( (this.getTIPO().equals("012")) || (this.getTIPO().equals("013")) )  {

			lsTipos.put("012", "Comp. Carga Venda");
			lsTipos.put("013", "Comp. Carga Bonif");

		} else {

			lsTipos.put("001", "Venda");
			lsTipos.put("003", "Bonificação");
			lsTipos.put("005", "Troca");
			lsTipos.put("006", "Devolução");
			lsTipos.put("007", "Amostra");
			lsTipos.put("010", "Dist. Venda");
			lsTipos.put("011", "Dist. Bonif");
		}
	}


	public String get_ClienteFatFantasia() {
		return _ClienteFatFantasia;
	}

	public void set_ClienteFatFantasia(String _ClienteFatFantasia) {
		this._ClienteFatFantasia = _ClienteFatFantasia;
	}

	public String get_Rede() {
		return _Rede;
	}

	public void set_Rede(String _Rede) {
		this._Rede = _Rede;
	}

	public void set_Status(String _Status) {
		this._Status = _Status;
	}

	public void set_Tipo(String _Tipo) {
		this._Tipo = _Tipo;
	}

	public void set_Retira(String _Retira) {
		this._Retira = _Retira;
	}

	public Boolean get_ViewPedido() {
		return _ViewPedido;
	}

	public void set_ViewPedido(Boolean _ViewPedido) {
		this._ViewPedido = _ViewPedido;
	}

	public String get_ClienteFatCidade() {
		return _ClienteFatCidade;
	}

	public void set_ClienteFatCidade(String _ClienteFatCidade) {
		this._ClienteFatCidade = _ClienteFatCidade;
	}

	public String get_ClienteFatTelefone() {
		return _ClienteFatTelefone;
	}

	public void set_ClienteFatTelefone(String _ClienteFatTelefone) {
		this._ClienteFatTelefone = _ClienteFatTelefone;
	}

	public String get_ClienteEntCidade() {
		return _ClienteEntCidade;
	}

	public void set_ClienteEntCidade(String _ClienteEntCidade) {
		this._ClienteEntCidade = _ClienteEntCidade;
	}

	public String get_ClienteEntTelefone() {
		return _ClienteEntTelefone;
	}

	public void set_ClienteEntTelefone(String _ClienteEntTelefone) {
		this._ClienteEntTelefone = _ClienteEntTelefone;
	}
}
