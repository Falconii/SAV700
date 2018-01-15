package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class PedCabTvs extends ObjRegister {

	protected String FILIAL;
	protected String PEDIDO;
	protected String PEDIDOMOBILE;
	protected String CLIENTE;
	protected String LOJA;
	protected String RAZAO;
	protected String SITUACAO;
	protected Float TOTALPEDIDO;
	protected Float TOTALFATURADO;
	protected String EMISSAO;
	protected String ENTREGA;
	protected String ORIGEM;
	protected String TIPO;
	protected String CONDPAGTO;
	protected String CPDESCRICAO;
	protected String TIPOFRETE;
	protected String DESCRICAO;
	protected String FORMAPAGTO;
	protected String VEND;
	protected String PEDIDOCLIENTE;
	protected String OBSNOTA;
	protected String OBSPED;
	protected String NOTAFILIAL;
	protected String NOTAFISCAL;
	protected String NOTASERIE;
	protected String CODIGOENTREGA;
	protected String LOJAENTREGA;
	protected Float DESCFRETE;
	protected Float FDPREVISTOS;
	protected Integer QTDENTREGA;


	protected String _CNPJ;
	protected String _IE;
	protected String _CODCIDADE;
	protected String _CIDADE;
	protected String _CODREDE;
	protected String _REDE;
	protected String _DDD;
	protected String _TELEFONE;
	protected String _RAZAOENTREGA;
	protected String _CIDADEENTREGA;
	protected String _TELEENTREGA;



	protected Boolean View_pedido;


	
	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.PedCabTvs";
	
	
	public PedCabTvs(){
		
		 super(_OBJETO,"PEDCABTVS");

		 InicializaFields();

		 loadColunas();

		_CNPJ           = "";
		_IE             = "";
		_CODCIDADE      = "";
		_CIDADE         = "";
		_CODREDE        = "";
		_REDE           = "";
		_TELEFONE       = "";
		_RAZAOENTREGA   = "";
		_CIDADEENTREGA  = "";
		_TELEENTREGA    = "";

		View_pedido = false;
	}


	public PedCabTvs(String FILIAL, String PEDIDO, String PEDIDOMOBILE, String CLIENTE, String LOJA, String RAZAO, String SITUACAO,
					 Float TOTALPEDIDO, Float TOTALFATURADO, String EMISSAO, String ENTREGA, String ORIGEM, String TIPO, String CONDPAGTO,
					 String CPDESCRICAO, String TIPOFRETE, String DESCRICAO, String FORMAPAGTO, String VEND, String PEDIDOCLIENTE, String OBSNOTA,
					 String OBSPED, String NOTAFILIAL, String NOTAFISCAL, String NOTASERIE,
					 String CODIGOENTREGA,String LOJAENTREGA, Float DESCFRETE, Float FDPREVISTOS, Integer QTDENTREGA){

		super(_OBJETO,"PEDCABTVS");

		InicializaFields();

		loadColunas();

		this.FILIAL = FILIAL;
		this.PEDIDO = PEDIDO;
		this.PEDIDOMOBILE = PEDIDOMOBILE;
		this.CLIENTE = CLIENTE;
		this.LOJA = LOJA;
		this.RAZAO = RAZAO;
		this.SITUACAO = SITUACAO;
		this.TOTALPEDIDO = TOTALPEDIDO;
		this.TOTALFATURADO = TOTALFATURADO;
		this.EMISSAO = EMISSAO;
		this.ENTREGA = ENTREGA;
		this.ORIGEM = ORIGEM;
		this.TIPO = TIPO;
		this.CONDPAGTO = CONDPAGTO;
		this.CPDESCRICAO = CPDESCRICAO;
		this.TIPOFRETE = TIPOFRETE;
		this.DESCRICAO = DESCRICAO;
		this.FORMAPAGTO = FORMAPAGTO;
		this.VEND = VEND;
		this.PEDIDOCLIENTE = PEDIDOCLIENTE;
		this.OBSNOTA = OBSNOTA;
		this.OBSPED = OBSPED;
		this.NOTAFILIAL = NOTAFILIAL;
		this.NOTAFISCAL = NOTAFISCAL;
		this.NOTASERIE = NOTASERIE;
		this.CODIGOENTREGA = CODIGOENTREGA;
		this.LOJAENTREGA = LOJAENTREGA;
		this.DESCFRETE   = DESCFRETE;
		this.FDPREVISTOS = FDPREVISTOS;
		this.QTDENTREGA  = QTDENTREGA;



		_CNPJ        = "";
		_IE          = "";
		_CODCIDADE   = "";
		_CIDADE      = "";
		_CODREDE     = "";
		_REDE        = "";
		_TELEFONE    = "";
		_RAZAOENTREGA   = "";
		_CIDADEENTREGA  = "";
		_TELEENTREGA    = "";

		View_pedido = false;

	}

	public void Complemento(String _CNPJ,String _IE, String _CODCIDADE,String _CIDADE, String _CODREDE, String _REDE, String _DDD, String _TELEFONE, String _RAZAOENTREGA, String _CIDADEENTREGA, String _TELEENTREGA) {

		this._CNPJ      = _CNPJ;
		this._IE        = _IE;
		this._CODCIDADE = _CODCIDADE;
		this._CIDADE = _CIDADE;
		this._CODREDE = _CODREDE;
		this._REDE = _REDE;
		this._DDD = _DDD;
		this._TELEFONE = _TELEFONE;
		this._RAZAOENTREGA = _RAZAOENTREGA;
		this._CIDADEENTREGA = _CIDADEENTREGA;
		this._TELEENTREGA = _TELEENTREGA;
	}

	public String getFILIAL() {
		return FILIAL;
	}

	public void setFILIAL(String FILIAL) {
		this.FILIAL = FILIAL;
	}

	public String getPEDIDO() {
		return PEDIDO;
	}

	public void setPEDIDO(String PEDIDO) {
		this.PEDIDO = PEDIDO;
	}

	public String getPEDIDOMOBILE() {
		return PEDIDOMOBILE;
	}

	public void setPEDIDOMOBILE(String PEDIDOMOBILE) {
		this.PEDIDOMOBILE = PEDIDOMOBILE;
	}

	public String getCLIENTE() {
		return CLIENTE;
	}

	public void setCLIENTE(String CLIENTE) {
		this.CLIENTE = CLIENTE;
	}

	public String getLOJA() {
		return LOJA;
	}

	public void setLOJA(String LOJA) {
		this.LOJA = LOJA;
	}

	public String getRAZAO() {
		return RAZAO;
	}

	public void setRAZAO(String RAZAO) {
		this.RAZAO = RAZAO;
	}

	public String getSITUACAO() {
		return SITUACAO;
	}

	public void setSITUACAO(String SITUACAO) {
		this.SITUACAO = SITUACAO;
	}

	public Float getTOTALPEDIDO() {
		return TOTALPEDIDO;
	}

	public void setTOTALPEDIDO(Float TOTALPEDIDO) {
		this.TOTALPEDIDO = TOTALPEDIDO;
	}

	public Float getTOTALFATURADO() {
		return TOTALFATURADO;
	}

	public void setTOTALFATURADO(Float TOTALFATURADO) {
		this.TOTALFATURADO = TOTALFATURADO;
	}

	public String getEMISSAO() {
		return EMISSAO;
	}

	public void setEMISSAO(String EMISSAO) {
		this.EMISSAO = EMISSAO;
	}

	public String getENTREGA() {
		return ENTREGA;
	}

	public void setENTREGA(String ENTREGA) {
		this.ENTREGA = ENTREGA;
	}

	public String getORIGEM() {
		return ORIGEM;
	}

	public void setORIGEM(String ORIGEM) {
		this.ORIGEM = ORIGEM;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String TIPO) {
		this.TIPO = TIPO;
	}

	public String getCONDPAGTO() {
		return CONDPAGTO;
	}

	public void setCONDPAGTO(String CONDPAGTO) {
		this.CONDPAGTO = CONDPAGTO;
	}

	public String getCPDESCRICAO() {
		return CPDESCRICAO;
	}

	public void setCPDESCRICAO(String CPDESCRICAO) {
		this.CPDESCRICAO = CPDESCRICAO;
	}

	public String getTIPOFRETE() {
		return TIPOFRETE;
	}

	public void setTIPOFRETE(String TIPOFRETE) {
		this.TIPOFRETE = TIPOFRETE;
	}

	public String getDESCRICAO() {
		return DESCRICAO;
	}

	public void setDESCRICAO(String DESCRICAO) {
		this.DESCRICAO = DESCRICAO;
	}

	public String getFORMAPAGTO() {
		return FORMAPAGTO;
	}

	public void setFORMAPAGTO(String FORMAPAGTO) {
		this.FORMAPAGTO = FORMAPAGTO;
	}

	public String getVEND() {
		return VEND;
	}

	public void setVEND(String VEND) {
		this.VEND = VEND;
	}

	public String getPEDIDOCLIENTE() {
		return PEDIDOCLIENTE;
	}

	public void setPEDIDOCLIENTE(String PEDIDOCLIENTE) {
		this.PEDIDOCLIENTE = PEDIDOCLIENTE;
	}

	public String getOBSNOTA() {
		return OBSNOTA;
	}

	public void setOBSNOTA(String OBSNOTA) {
		this.OBSNOTA = OBSNOTA;
	}

	public String getOBSPED() {
		return OBSPED;
	}

	public void setOBSPED(String OBSPED) {
		this.OBSPED = OBSPED;
	}

	public String getNOTAFILIAL() {
		return NOTAFILIAL;
	}

	public void setNOTAFILIAL(String NOTAFILIAL) {
		this.NOTAFILIAL = NOTAFILIAL;
	}

	public String getNOTAFISCAL() {
		return NOTAFISCAL;
	}

	public void setNOTAFISCAL(String NOTAFISCAL) {
		this.NOTAFISCAL = NOTAFISCAL;
	}

	public String getNOTASERIE() {
		return NOTASERIE;
	}

	public void setNOTASERIE(String NOTASERIE) {
		this.NOTASERIE = NOTASERIE;
	}

	public Boolean getView_pedido() {
		return View_pedido;
	}

	public void setView_pedido(Boolean view_pedido) {
		View_pedido = view_pedido;
	}

	public String get_CNPJ() {
		return _CNPJ;
	}

	public void set_CNPJ(String _CNPJ) {
		this._CNPJ = _CNPJ;
	}

	public String get_IE() {
		return _IE;
	}

	public void set_IE(String _IE) {
		this._IE = _IE;
	}

	public String get_CODCIDADE() {
		return _CODCIDADE;
	}

	public void set_CODCIDADE(String _CODCIDADE) {
		this._CODCIDADE = _CODCIDADE;
	}

	public String get_CIDADE() {
		return _CIDADE;
	}

	public void set_CIDADE(String _CIDADE) {
		this._CIDADE = _CIDADE;
	}

	public String get_CODREDE() {
		return _CODREDE;
	}

	public void set_CODREDE(String _CODREDE) {
		this._CODREDE = _CODREDE;
	}

	public String get_REDE() {
		return _REDE;
	}

	public void set_REDE(String _REDE) {
		this._REDE = _REDE;
	}

	public String get_DDD() {
		return _DDD;
	}

	public void set_DDD(String _DDD) {
		this._DDD = _DDD;
	}

	public String get_TELEFONE() {
		return _TELEFONE;
	}

	public void set_TELEFONE(String _TELEFONE) {
		this._TELEFONE = _TELEFONE;
	}

	public String getCODIGOENTREGA() {
		return CODIGOENTREGA;
	}

	public void setCODIGOENTREGA(String CODIGOENTREGA) {
		this.CODIGOENTREGA = CODIGOENTREGA;
	}

	public String getLOJAENTREGA() {
		return LOJAENTREGA;
	}

	public void setLOJAENTREGA(String LOJAENTREGA) {
		this.LOJAENTREGA = LOJAENTREGA;
	}

	public Float getDESCFRETE() {
		return DESCFRETE;
	}

	public void setDESCFRETE(Float DESCFRETE) {
		this.DESCFRETE = DESCFRETE;
	}

	public String get_RAZAOENTREGA() {
		return _RAZAOENTREGA;
	}

	public void set_RAZAOENTREGA(String _RAZAOENTREGA) {
		this._RAZAOENTREGA = _RAZAOENTREGA;
	}

	public String get_CIDADEENTREGA() {
		return _CIDADEENTREGA;
	}

	public void set_CIDADEENTREGA(String _CIDADEENTREGA) {
		this._CIDADEENTREGA = _CIDADEENTREGA;
	}

	public String get_TELEENTREGA() {
		return _TELEENTREGA;
	}

	public void set_TELEENTREGA(String _TELEENTREGA) {
		this._TELEENTREGA = _TELEENTREGA;
	}

	public String get_TipoFrete(){

		String retorno = "";

		if (this.getTIPOFRETE() == null){

			return retorno;

		}

		switch (this.getTIPOFRETE().charAt(0)){

			case 'C': retorno = "CIF"; break;
			case 'F': retorno = "FOB - Cliente Retira" ; break;
			default: retorno = "";

		}

		return retorno;

	}

	public Float getFDPREVISTOS() {
		return FDPREVISTOS;
	}

	public void setFDPREVISTOS(Float FDPREVISTOS) {
		this.FDPREVISTOS = FDPREVISTOS;
	}

	public Integer getQTDENTREGA() {
		return QTDENTREGA;
	}

	public void setQTDENTREGA(Integer QTDENTREGA) {
		this.QTDENTREGA = QTDENTREGA;
	}


	@Override
	public void loadColunas() {

		_colunas = new ArrayList<String>();

		_colunas.add("FILIAL");
		_colunas.add("PEDIDO");
		_colunas.add("PEDIDOMOBILE");
		_colunas.add("CLIENTE");
		_colunas.add("LOJA");
		_colunas.add("RAZAO");
		_colunas.add("SITUACAO");
		_colunas.add("TOTALPEDIDO");
		_colunas.add("TOTALFATURADO");
		_colunas.add("EMISSAO");
		_colunas.add("ENTREGA");
		_colunas.add("ORIGEM");
		_colunas.add("TIPO");
		_colunas.add("CONDPAGTO");
		_colunas.add("CPDESCRICAO");
		_colunas.add("TIPOFRETE");
		_colunas.add("DESCRICAO");
		_colunas.add("FORMAPAGTO");
		_colunas.add("VEND");
		_colunas.add("PEDIDOCLIENTE");
		_colunas.add("OBSNOTA");
		_colunas.add("OBSPED");
		_colunas.add("NOTAFILIAL");
		_colunas.add("NOTAFISCAL");
		_colunas.add("NOTASERIE");
		_colunas.add("CODIGOENTREGA");
		_colunas.add("LOJAENTREGA");
		_colunas.add("DESCFRETE");
		_colunas.add("FDPREVISTOS");
		_colunas.add("QTDENTREGA");



	}

}
