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


public class PedidoCabMb extends ObjRegister implements KvmSerializable {

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
	protected String _ClienteEntRazao;
	protected String _ClienteCodRede;
	protected String _Status;
	protected String _Tipo;
	protected String _Cond;
	protected String _TabPreco;
	protected String _Retira;
	protected String _View;

	protected Map<Integer,String> lsStatus = new TreeMap<Integer, String >();
	protected Map<String,String>  lsTipos  = new TreeMap<String, String >();

	public PedidoCabMb(){

		super(_OBJETO,"PEDIDOCABMB");

		loadColunas();

		InicializaFields();

		loadTipoDescricao();

		loadStatusDescricao();

		novo();


	}

	public PedidoCabMb(String NRO, String CPROTHEUS, String CPROTHEUSB, String STATUS, String EMISSAO, String ENTREGA, String TIPO, String DTTRANS, String HOTRANS, String CODIGOFAT, String LOJAFAT, String CODIGOENT, String LOJAENT, String COND, String TABPRECO, String PREPEDIDO, String PREPLANILHA, String PROPOSTA, String CONTRATO, String OBSPED, String OBSNF, String AGPROTOCOLO, String AGDATA, String AGHORA, Float TOTALPEDIDO, Float TOTALDESCONTO, Float TOTALVERBA, String VEND, String PEDCLIENTE, Float QTDBINIFICADA, Float VLRBONIFICADO, Float PESOBRUTO, Float PESOLIQUIDO, String RETIRA, Float DESCRET, String MENSAGEM, Float FDSPREVISTO, Float FDSREAIS, Float APROVEITAMENTO, String PEDCLI2, String CCOPIAPEDIDO,String CEMAILCOPIAPEDIDO, float SALDOAPROVEITAMENTO, Integer QTDENTREGA) {

		super(_OBJETO, "PEDIDOCABMB");

		loadColunas();

		InicializaFields();

		loadTipoDescricao();

		loadStatusDescricao();

		_ClienteFatRazao  = "";

		_ClienteFatCnpj   = "";

		_ClienteFatIE     = "";

		_ClienteEntRazao  = "";

		_ClienteCodRede   = "";

		_Cond             = "";

		_TabPreco         = "";

		_Retira           = "2";




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
		this.SALDOAPROVEITAMENTO = SALDOAPROVEITAMENTO;
		this.QTDENTREGA          = QTDENTREGA;

		_View = "G";
	}


	private void novo(){

		Date now = new Date();

		InicializaFields();

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

		setSTATUS("1");

		setTIPO("1");

		setCPROTHEUS("");

		set_View("G");

		QTDENTREGA = 1;

		_ClienteFatRazao  = "";

		_ClienteFatCnpj   = "";

		_ClienteFatIE     = "";

		_ClienteEntRazao  = "";

		_ClienteCodRede   = "";

		_Cond             = "";

		_TabPreco         = "";

		_Retira           = "2";

		String emissao    = DATE_FORMAT.format(now);

		String entrega    = DATE_FORMAT.format(now);

		setEMISSAO(emissao);

		setENTREGA(entrega);

		loadTipoDescricao();

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

	public String get_View() {
		return _View;
	}

	public void set_View(String _View) {
		this._View = _View;
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


	public void loadTipoDescricao() {

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




	@Override
	public void loadColunas() {

		_colunas = new ArrayList<String>();
		_colunas.add("NRO");
		_colunas.add("CPROTHEUS");
		_colunas.add("CPROTHEUSB");
		_colunas.add("STATUS");
		_colunas.add("EMISSAO");
		_colunas.add("ENTREGA");
		_colunas.add("TIPO");
		_colunas.add("DTTRANS");
		_colunas.add("HOTRANS");
		_colunas.add("CODIGOFAT");
		_colunas.add("LOJAFAT");
		_colunas.add("CODIGOENT");
		_colunas.add("LOJAENT");
		_colunas.add("COND");
		_colunas.add("TABPRECO");
		_colunas.add("PREPEDIDO");
		_colunas.add("PREPLANILHA");
		_colunas.add("PROPOSTA");
		_colunas.add("CONTRATO");
		_colunas.add("OBSPED");
		_colunas.add("OBSNF");
		_colunas.add("AGPROTOCOLO");
		_colunas.add("AGDATA");
		_colunas.add("AGHORA");
		_colunas.add("TOTALPEDIDO");
		_colunas.add("TOTALDESCONTO");
		_colunas.add("TOTALVERBA");
		_colunas.add("VEND");
		_colunas.add("PEDCLIENTE");
		_colunas.add("QTDBINIFICADA");
		_colunas.add("VLRBONIFICADO");
		_colunas.add("PESOBRUTO");
		_colunas.add("PESOLIQUIDO");
		_colunas.add("RETIRA");
		_colunas.add("DESCRET");
		_colunas.add("MENSAGEM");
		_colunas.add("FDSPREVISTO");
		_colunas.add("FDSREAIS");
		_colunas.add("APROVEITAMENTO");
		_colunas.add("PEDCLI2");
		_colunas.add("CCOPIAPEDIDO");
		_colunas.add("CEMAILCOPIAPEDIDO");
		_colunas.add("SALDOAPROVEITAMENTO");
		_colunas.add("QTDENTREGA");



		_isValid = new ArrayList<Boolean>();
		for(int x = 0 ; x<_colunas.size() ; x++){

			_isValid.add(true);


		}

	}



	/*
	 * 
	 * (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getProperty(int)
	 * 
	 * 
	 */
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
