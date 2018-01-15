package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;

public class Cliente extends ObjRegister {

	protected String CODIGO;
	protected String LOJA;
	protected String RAZAOPA;
	protected String RAZAO;
	protected String FANTASIA;
	protected String CNPJ;
	protected String IE;
	protected String ENDERECO;
	protected String ESTADO;
	protected String CODCIDADE;
	protected String CIDADE;
	protected String BAIRRO;
	protected String DDD;
	protected String TELEFONE;
	protected String CEP;
	protected String SITUACAO;
	protected String CADASTRO;
	protected Float LIMITE;
	protected String RISCO;
	protected String TIPOFRETE;
	protected String CANAL;
	protected String TABELA;
	protected String TABELA2;
	protected String ULTVIS;
	protected String ULTCOM;
	protected String CODIGOFORMAPAGTO;
	protected String CONDPAGTO;
	protected String OBS;
	protected String DIASPEDPROG;
	protected String FLAGALTERACP;
	protected String TAXAFIN;
	protected String PEDMINVLR;
	protected String PEDMINPES;
	protected String REDE;
	protected String CONTRATO;
	protected String VEND;
	protected String GA;
	protected String GNV;
	protected String EMAILTROCA;
	protected String EMAILNFE;
	protected String EMAIL;
	protected String SITE;
	protected String REGIAO;
	protected String PESSOA;
	protected String FUNDACAO;
	protected String BOLETO;
	protected String SIMPLES;
	protected String ISENTOST;
	protected String ICMS;
	protected String CLIENTEENTREGA;
	protected String AGENDADATA;
	protected String AGENDAHORA;
	protected String AGENDAPERIODO;
	protected String AGENDAOBS;
	protected Float DESCCLIENTE;
	protected String RESTRENTREGA;
	protected String RESTRHORA;
	protected String AGENDAR;
	protected Float VLRDESCARGA;
	protected String UNIDDESCARGA;
	protected String PAGDESCARGA;
	protected String PERFVEIC;
	protected String PERFCARGA;
	protected String PALETIZACAO;
	protected String CARGAMISTA;
	protected String VENCLC;
	protected String SITCOM;
	protected String SITPRO;
	protected String ROTDTBA;
	protected String ROTHORA;
	protected String ROTPERI;
	protected String ROTOBSVI;
	protected String LATITUDE;
	protected String LONGITUDE;
	protected String PARTICIPANTE;
	protected Float  VLRDESCARGA2;
	protected String TIPOCON;






	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Cliente";

	protected String _CANAL;
	protected String _REDE;
	protected String _TABELA;
	protected String _POLITICA;
	protected String _COND;
	protected String _VENDEDOR;
	protected String _GA;
	protected String _GNV;



	public Cliente(){

		super(_OBJETO,"CLIENTE");

		loadColunas();

		InicializaFields();

		_CANAL     = "";
		_REDE      = "";
		_TABELA    = "";
		_POLITICA  = "";
		_COND      = "";
		_VENDEDOR  = "";
		_GA        = "";
		_GNV       = "";

	}

	public Cliente(String CODIGO, String LOJA, String RAZAOPA, String RAZAO, String FANTASIA, String CNPJ, String IE, String ENDERECO, String ESTADO, String CODCIDADE, String CIDADE, String BAIRRO, String DDD, String TELEFONE, String CEP, String SITUACAO, String CADASTRO, Float LIMITE, String RISCO, String TIPOFRETE, String CANAL, String TABELA, String TABELA2, String ULTVIS, String ULTCOM, String CODIGOFORMAPAGTO, String CONDPAGTO, String OBS, String DIASPEDPROG, String FLAGALTERACP, String TAXAFIN, String PEDMINVLR, String PEDMINPES, String REDE, String CONTRATO, String VEND, String GA, String GNV, String EMAILTROCA, String EMAILNFE, String EMAIL, String SITE, String REGIAO, String PESSOA, String FUNDACAO, String BOLETO, String SIMPLES, String ISENTOST, String ICMS, String CLIENTEENTREGA, String AGENDADATA, String AGENDAHORA, String AGENDAPERIODO, String AGENDAOBS, Float DESCCLIENTE, String RESTRENTREGA, String RESTRHORA, String AGENDAR, Float VLRDESCARGA, String UNIDDESCARGA, String PAGDESCARGA, String PERFVEIC, String PERFCARGA, String PALETIZACAO, String CARGAMISTA, String VENCLC, String SITCOM, String SITPRO, String ROTDTBA, String ROTHORA, String ROTPERI, String ROTOBSVI, String LATITUDE, String LONGITUDE, String PARTICIPANTE, Float VLRDESCARGA2,String TIPOCON) {

		super(_OBJETO,"CLIENTE");

		loadColunas();

		InicializaFields();

		this.CODIGO    = CODIGO;
		this.LOJA      = LOJA;
		this.RAZAOPA   = RAZAOPA;
		this.RAZAO     = RAZAO;
		this.FANTASIA  = FANTASIA;
		this.CNPJ      = CNPJ;
		this.IE        = IE;
		this.ENDERECO  = ENDERECO;
		this.ESTADO    = ESTADO;
		this.CODCIDADE = CODCIDADE;
		this.CIDADE    = CIDADE;
		this.BAIRRO    = BAIRRO;
		this.DDD 	   = DDD;
		this.TELEFONE  = TELEFONE;
		this.CEP       = CEP;
		this.SITUACAO  = SITUACAO;
		this.CADASTRO  = CADASTRO;
		this.LIMITE    = LIMITE;
		this.RISCO     = RISCO;
		this.TIPOFRETE = TIPOFRETE;
		this.CANAL     = CANAL;
		this.TABELA    = TABELA;
		this.TABELA2   = TABELA2;
		this.ULTVIS    = ULTVIS;
		this.ULTCOM    = ULTCOM;
		this.CODIGOFORMAPAGTO = CODIGOFORMAPAGTO;
		this.CONDPAGTO        = CONDPAGTO;
		this.OBS              = OBS;
		this.DIASPEDPROG      = DIASPEDPROG;
		this.FLAGALTERACP     = FLAGALTERACP;
		this.TAXAFIN          = TAXAFIN;
		this.PEDMINVLR        = PEDMINVLR;
		this.PEDMINPES        = PEDMINPES;
		this.REDE             = REDE;
		this.CONTRATO         = CONTRATO;
		this.VEND             = VEND;
		this.GA               = GA;
		this.GNV              = GNV;
		this.EMAILTROCA       = EMAILTROCA;
		this.EMAILNFE         = EMAILNFE;
		this.EMAIL            = EMAIL;
		this.SITE             = SITE;
		this.REGIAO = REGIAO;
		this.PESSOA = PESSOA;
		this.FUNDACAO = FUNDACAO;
		this.BOLETO = BOLETO;
		this.SIMPLES = SIMPLES;
		this.ISENTOST = ISENTOST;
		this.ICMS = ICMS;
		this.CLIENTEENTREGA = CLIENTEENTREGA;
		this.AGENDADATA = AGENDADATA;
		this.AGENDAHORA = AGENDAHORA;
		this.AGENDAPERIODO = AGENDAPERIODO;
		this.AGENDAOBS     = AGENDAOBS;
		this.DESCCLIENTE   = DESCCLIENTE;
		this.RESTRENTREGA  = RESTRENTREGA;
		this.RESTRHORA     = RESTRHORA;
		this.AGENDAR       = AGENDAR;
		this.VLRDESCARGA   = VLRDESCARGA;
		this.UNIDDESCARGA  = UNIDDESCARGA;
		this.PAGDESCARGA   = PAGDESCARGA;
		this.PERFVEIC      = PERFVEIC;
		this.PERFCARGA     = PERFCARGA;
		this.PALETIZACAO   = PALETIZACAO;
		this.CARGAMISTA    = CARGAMISTA;
		this.VENCLC        = VENCLC;
		this.SITCOM        = SITCOM;
		this.SITPRO        = SITPRO;
		this.ROTDTBA       = ROTDTBA;
		this.ROTHORA       = ROTHORA;
		this.ROTPERI       = ROTPERI;
		this.ROTOBSVI      = ROTOBSVI;
		this.LATITUDE      = LATITUDE;
		this.LONGITUDE     = LONGITUDE;
		this.PARTICIPANTE  =  PARTICIPANTE;
		this.VLRDESCARGA2  =  VLRDESCARGA2;
		this.TIPOCON       =  TIPOCON;

		_CANAL     = "";
		_REDE      = "";
		_TABELA    = "";
		_POLITICA  = "";
		_COND      = "";
		_VENDEDOR  = "";
		_GA        = "";
		_GNV       = "";
	}

	public String getCODIGO() {
		return CODIGO;
	}

	public void setCODIGO(String CODIGO) {
		this.CODIGO = CODIGO;
	}

	public String getLOJA() {
		return LOJA;
	}

	public void setLOJA(String LOJA) {
		this.LOJA = LOJA;
	}

	public String getRAZAOPA() {
		return RAZAOPA;
	}

	public void setRAZAOPA(String RAZAOPA) {
		this.RAZAOPA = RAZAOPA;
	}

	public String getRAZAO() {
		return RAZAO;
	}

	public void setRAZAO(String RAZAO) {
		this.RAZAO = RAZAO;
	}

	public String getFANTASIA() {
		return FANTASIA;
	}

	public void setFANTASIA(String FANTASIA) {
		this.FANTASIA = FANTASIA;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String CNPJ) {
		this.CNPJ = CNPJ;
	}

	public String getIE() {
		return IE;
	}

	public void setIE(String IE) {
		this.IE = IE;
	}

	public String getENDERECO() {
		return ENDERECO;
	}

	public void setENDERECO(String ENDERECO) {
		this.ENDERECO = ENDERECO;
	}

	public String getESTADO() {
		return ESTADO;
	}

	public void setESTADO(String ESTADO) {
		this.ESTADO = ESTADO;
	}

	public String getCODCIDADE() {
		return CODCIDADE;
	}

	public void setCODCIDADE(String CODCIDADE) {
		this.CODCIDADE = CODCIDADE;
	}

	public String getCIDADE() {
		return CIDADE;
	}

	public void setCIDADE(String CIDADE) {
		this.CIDADE = CIDADE;
	}

	public String getBAIRRO() {
		return BAIRRO;
	}

	public void setBAIRRO(String BAIRRO) {
		this.BAIRRO = BAIRRO;
	}

	public String getDDD() {
		return DDD;
	}

	public void setDDD(String DDD) {
		this.DDD = DDD;
	}

	public String getTELEFONE() {
		return TELEFONE;
	}

	public void setTELEFONE(String TELEFONE) {
		this.TELEFONE = TELEFONE;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String CEP) {
		this.CEP = CEP;
	}

	public String getSITUACAO() {
		return SITUACAO;
	}

	public void setSITUACAO(String SITUACAO) {
		this.SITUACAO = SITUACAO;
	}

	public String getCADASTRO() {
		return CADASTRO;
	}

	public void setCADASTRO(String CADASTRO) {
		this.CADASTRO = CADASTRO;
	}

	public Float getLIMITE() {
		return LIMITE;
	}

	public void setLIMITE(Float LIMITE) {
		this.LIMITE = LIMITE;
	}

	public String getRISCO() {
		return RISCO;
	}

	public void setRISCO(String RISCO) {
		this.RISCO = RISCO;
	}

	public String getTIPOFRETE() {
		return TIPOFRETE;
	}

	public void setTIPOFRETE(String TIPOFRETE) {
		this.TIPOFRETE = TIPOFRETE;
	}

	public String getCANAL() {
		return CANAL;
	}

	public void setCANAL(String CANAL) {
		this.CANAL = CANAL;
	}

	public String getTABELA() {
		return TABELA;
	}

	public void setTABELA(String TABELA) {
		this.TABELA = TABELA;
	}

	public String getULTVIS() {
		return ULTVIS;
	}

	public void setULTVIS(String ULTVIS) {
		this.ULTVIS = ULTVIS;
	}

	public String getULTCOM() {
		return ULTCOM;
	}

	public void setULTCOM(String ULTCOM) {
		this.ULTCOM = ULTCOM;
	}

	public String getCODIGOFORMAPAGTO() {
		return CODIGOFORMAPAGTO;
	}

	public void setCODIGOFORMAPAGTO(String CODIGOFORMAPAGTO) {
		this.CODIGOFORMAPAGTO = CODIGOFORMAPAGTO;
	}

	public String getCONDPAGTO() {
		return CONDPAGTO;
	}

	public void setCONDPAGTO(String CONDPAGTO) {
		this.CONDPAGTO = CONDPAGTO;
	}

	public String getOBS() {
		return OBS;
	}

	public void setOBS(String OBS) {
		this.OBS = OBS;
	}

	public String getDIASPEDPROG() {
		return DIASPEDPROG;
	}

	public void setDIASPEDPROG(String DIASPEDPROG) {
		this.DIASPEDPROG = DIASPEDPROG;
	}

	public String getFLAGALTERACP() {
		return FLAGALTERACP;
	}

	public void setFLAGALTERACP(String FLAGALTERACP) {
		this.FLAGALTERACP = FLAGALTERACP;
	}

	public String getTAXAFIN() {
		return TAXAFIN;
	}

	public void setTAXAFIN(String TAXAFIN) {
		this.TAXAFIN = TAXAFIN;
	}

	public String getPEDMINVLR() {
		return PEDMINVLR;
	}

	public void setPEDMINVLR(String PEDMINVLR) {
		this.PEDMINVLR = PEDMINVLR;
	}

	public String getPEDMINPES() {
		return PEDMINPES;
	}

	public void setPEDMINPES(String PEDMINPES) {
		this.PEDMINPES = PEDMINPES;
	}

	public String getREDE() {
		return REDE;
	}

	public void setREDE(String REDE) {
		this.REDE = REDE;
	}

	public String getCONTRATO() {
		return CONTRATO;
	}

	public void setCONTRATO(String CONTRATO) {
		this.CONTRATO = CONTRATO;
	}

	public String getVEND() {
		return VEND;
	}

	public void setVEND(String VEND) {
		this.VEND = VEND;
	}

	public String getGA() {
		return GA;
	}

	public void setGA(String GA) {
		this.GA = GA;
	}

	public String getGNV() {
		return GNV;
	}

	public void setGNV(String GNV) {
		this.GNV = GNV;
	}

	public String getEMAILTROCA() {
		return EMAILTROCA;
	}

	public void setEMAILTROCA(String EMAILTROCA) {
		this.EMAILTROCA = EMAILTROCA;
	}

	public String getEMAILNFE() {
		return EMAILNFE;
	}

	public void setEMAILNFE(String EMAILNFE) {
		this.EMAILNFE = EMAILNFE;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String EMAIL) {
		this.EMAIL = EMAIL;
	}

	public String getSITE() {
		return SITE;
	}

	public void setSITE(String SITE) {
		this.SITE = SITE;
	}

	public String getREGIAO() {
		return REGIAO;
	}

	public void setREGIAO(String REGIAO) {
		this.REGIAO = REGIAO;
	}

	public String getPESSOA() {
		return PESSOA;
	}

	public void setPESSOA(String PESSOA) {
		this.PESSOA = PESSOA;
	}

	public String getFUNDACAO() {
		return FUNDACAO;
	}

	public void setFUNDACAO(String FUNDACAO) {
		this.FUNDACAO = FUNDACAO;
	}

	public String getBOLETO() {
		return BOLETO;
	}

	public void setBOLETO(String BOLETO) {
		this.BOLETO = BOLETO;
	}

	public String getSIMPLES() {
		return SIMPLES;
	}

	public void setSIMPLES(String SIMPLES) {
		this.SIMPLES = SIMPLES;
	}

	public String getISENTOST() {
		return ISENTOST;
	}

	public void setISENTOST(String ISENTOST) {
		this.ISENTOST = ISENTOST;
	}

	public String getICMS() {
		return ICMS;
	}

	public void setICMS(String ICMS) {
		this.ICMS = ICMS;
	}

	public String getCLIENTEENTREGA() {
		return CLIENTEENTREGA;
	}

	public void setCLIENTEENTREGA(String CLIENTEENTREGA) {
		this.CLIENTEENTREGA = CLIENTEENTREGA;
	}

	public String getAGENDADATA() {
		return AGENDADATA;
	}

	public void setAGENDADATA(String AGENDADATA) {
		this.AGENDADATA = AGENDADATA;
	}

	public String getAGENDAHORA() {
		return AGENDAHORA;
	}

	public void setAGENDAHORA(String AGENDAHORA) {
		this.AGENDAHORA = AGENDAHORA;
	}

	public String getAGENDAPERIODO() {
		return AGENDAPERIODO;
	}

	public void setAGENDAPERIODO(String AGENDAPERIODO) {
		this.AGENDAPERIODO = AGENDAPERIODO;
	}

	public String getAGENDAOBS() {
		return AGENDAOBS;
	}

	public void setAGENDAOBS(String AGENDAOBS) {
		this.AGENDAOBS = AGENDAOBS;
	}

	public Float getDESCCLIENTE() {
		return DESCCLIENTE;
	}

	public void setDESCCLIENTE(Float DESCCLIENTE) {
		this.DESCCLIENTE = DESCCLIENTE;
	}

	public String getRESTRENTREGA() {
		return RESTRENTREGA;
	}

	public void setRESTRENTREGA(String RESTRENTREGA) {
		this.RESTRENTREGA = RESTRENTREGA;
	}

	public String getRESTRHORA() {
		return RESTRHORA;
	}

	public void setRESTRHORA(String RESTRHORA) {
		this.RESTRHORA = RESTRHORA;
	}

	public String getAGENDAR() {
		return AGENDAR;
	}

	public void setAGENDAR(String AGENDAR) {
		this.AGENDAR = AGENDAR;
	}

	public Float getVLRDESCARGA() {
		return VLRDESCARGA;
	}

	public void setVLRDESCARGA(Float VLRDESCARGA) {
		this.VLRDESCARGA = VLRDESCARGA;
	}

	public String getUNIDDESCARGA() {
		return UNIDDESCARGA;
	}

	public void setUNIDDESCARGA(String UNIDDESCARGA) {
		this.UNIDDESCARGA = UNIDDESCARGA;
	}

	public String getPAGDESCARGA() {
		return PAGDESCARGA;
	}

	public void setPAGDESCARGA(String PAGDESCARGA) {
		this.PAGDESCARGA = PAGDESCARGA;
	}

	public String getPERFVEIC() {
		return PERFVEIC;
	}

	public void setPERFVEIC(String PERFVEIC) {
		this.PERFVEIC = PERFVEIC;
	}

	public String getPERFCARGA() {
		return PERFCARGA;
	}

	public void setPERFCARGA(String PERFCARGA) {
		this.PERFCARGA = PERFCARGA;
	}

	public String getPALETIZACAO() {
		return PALETIZACAO;
	}

	public void setPALETIZACAO(String PALETIZACAO) {
		this.PALETIZACAO = PALETIZACAO;
	}

	public String getCARGAMISTA() {
		return CARGAMISTA;
	}

	public void setCARGAMISTA(String CARGAMISTA) {
		this.CARGAMISTA = CARGAMISTA;
	}

	public String getVENCLC() {
		return VENCLC;
	}

	public void setVENCLC(String VENCLC) {
		this.VENCLC = VENCLC;
	}

	public String getSITCOM() {
		return SITCOM;
	}

	public void setSITCOM(String SITCOM) {
		this.SITCOM = SITCOM;
	}

	public String getSITPRO() {
		return SITPRO;
	}

	public void setSITPRO(String SITPRO) {
		this.SITPRO = SITPRO;
	}

	public String getROTDTBA() {
		return ROTDTBA;
	}

	public void setROTDTBA(String ROTDTBA) {
		this.ROTDTBA = ROTDTBA;
	}

	public String getROTHORA() {
		return ROTHORA;
	}

	public void setROTHORA(String ROTHORA) {
		this.ROTHORA = ROTHORA;
	}

	public String getROTPERI() {
		return ROTPERI;
	}

	public void setROTPERI(String ROTPERI) {
		this.ROTPERI = ROTPERI;
	}

	public String getROTOBSVI() {
		return ROTOBSVI;
	}

	public void setROTOBSVI(String ROTOBSVI) {
		this.ROTOBSVI = ROTOBSVI;
	}

	public String getLATITUDE() {
		return LATITUDE;
	}

	public void setLATITUDE(String LATITUDE) {
		this.LATITUDE = LATITUDE;
	}

	public String getLONGITUDE() {
		return LONGITUDE;
	}

	public void setLONGITUDE(String LONGITUDE) {
		this.LONGITUDE = LONGITUDE;
	}

	public String get_CANAL() {
		return _CANAL;
	}

	public void set_CANAL(String _CANAL) {
		this._CANAL = _CANAL;
	}

	public String get_REDE() {
		return _REDE;
	}

	public void set_REDE(String _REDE) {
		this._REDE = _REDE;
	}

	public String get_TABELA() {
		return _TABELA;
	}

	public void set_TABELA(String _TABELA) {
		this._TABELA = _TABELA;
	}

	public String get_COND() {
		return _COND;
	}

	public void set_COND(String _COND) {
		this._COND = _COND;
	}

	public String get_POLITICA() {
		return _POLITICA;
	}

	public void set_POLITICA(String _POLITICA) {
		this._POLITICA = _POLITICA;
	}

	public String get_VENDEDOR() {
		return _VENDEDOR;
	}

	public void set_VENDEDOR(String _VENDEDOR) {
		this._VENDEDOR = _VENDEDOR;
	}

	public String get_GA() {
		return _GA;
	}

	public void set_GA(String _GA) {
		this._GA = _GA;
	}

	public String get_GNV() {
		return _GNV;
	}

	public void set_GNV(String _GNV) {
		this._GNV = _GNV;
	}

	public String get_Tipo(){

		String retorno = this.PESSOA;

		switch (this.PESSOA){

			case "F":{

				retorno = "CONSUMIDOR FINAL";

				break;

			}
			case "L":{

				retorno = "PRODUTOR RURAL";

				break;

			}
			case "R":{

				retorno = "REVENDEDOR";

				break;

			}
			case "S":{

				retorno = "SOLIDÁRIO";

				break;

			}
			case "X":{

				retorno = "EXPORTAÇÃO";

				break;

			}
		}

		return  retorno;

	}

	public String getPARTICIPANTE() {
		return PARTICIPANTE;
	}

	public void setPARTICIPANTE(String PARTICIPANTE) {
		this.PARTICIPANTE = PARTICIPANTE;
	}

	public Float getVLRDESCARGA2() {
		return VLRDESCARGA2;
	}

	public void setVLRDESCARGA2(Float VLRDESCARGA2) {
		this.VLRDESCARGA2 = VLRDESCARGA2;
	}

	public String getTIPOCON() {
		return TIPOCON;
	}

	public void setTIPOCON(String TIPOCON) {
		this.TIPOCON = TIPOCON;
	}

	@Override
	public void loadHelp() {

		/*
		 *
		 *  ALIAS CLIENTE
		 *
		 *
		 */

		List<HelpParam> help     = new ArrayList<HelpParam>();

		help.add(new HelpParam("RAZÃO" ,
				"select codigo,loja,razaopa,cnpj,ie,bairro,cidade,estado,ddd,telefone,clienteentrega,rede  from cliente  ",
				"where razao like ''%{0}%'' "           ,
				"order by razaopa",
				"razaopa",
				new String[] {"CODIGO","LOJA"},
				" clienteentrega <> '1'  ",   //aliaswhere
				new String[] {}));

		help.add(new HelpParam("CÓDIGO",
				"select codigo,loja,razaopa,cnpj,ie,bairro,cidade,estado,ddd,telefone,clienteentrega,rede  from cliente   ",
				"where codigo like ''{0}%'' "           ,
				"order by codigo,razaopa",
				"codigo",
				new String[] {"CODIGO","LOJA"},
				" clienteentrega <> '1'  ",   //aliaswhere
				new String[] {}));

		help.add(new HelpParam("CNPJ"  ,
				"select codigo,loja,razaopa,cnpj,ie,bairro,cidade,estado,ddd,telefone,clienteentrega,rede  from cliente  ",
				" where cnpj  like ''{0}%'' "   ,
				"order by cnpj",
				"cnpj",
				new String[] {"CODIGO","LOJA"},
				" clienteentrega <> '1'  ",   //aliaswhere
				new String[] {}));


		List<HelpFiltro> filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("CLIENTE", help, filtro, null));

		/*
		 *
		 *  ALIAS CLIENTEENTREGA
		 *
		 *
		 */


		help     = new ArrayList<HelpParam>();

		help.add(new HelpParam("RAZÃO" ,
				"select codigo,loja,razaopa,cnpj,ie,bairro,cidade,estado,ddd,telefone,clienteentrega,rede from cliente ",
				"where razao like ''%{0}%'' "           ,
				"order by razaopa",
				"razaopa",
				new String[] {"CODIGO","LOJA"},
				" rede = ''{0}'' ",   //aliaswhere
				new String[] {}));

		help.add(new HelpParam("CÓDIGO",
				"select codigo,loja,razaopa,cnpj,ie,bairro,cidade,estado,ddd,telefone,clienteentrega,rede from cliente  ",
				"where codigo like ''{0}%'' "           ,
				"order by codigo,razaopa",
				"codigo",
				new String[] {"CODIGO","LOJA"},
				" rede = ''{0}'' ",   //aliaswhere
				new String[] {}));

		help.add(new HelpParam("CNPJ"  ,
				"select codigo,loja,razaopa,cnpj,ie,bairro,cidade,estado,ddd,telefone,clienteentrega,rede from cliente  ",
				"where cnpj  like ''{0}%'' "   ,
				"order by cnpj",
				"cnpj",
				new String[] {"CODIGO","LOJA"},
				" rede = ''{0}'' ",   //aliaswhere
				new String[] {}));

		filtro    = new ArrayList<HelpFiltro>();

		_fileTable.add(new FileTable("CLIENTEENTREGA", help, filtro, null));



		loadTableHelp("CLIENTE");


	};

	@Override
	public String[] getHelpLinhas(Cursor cursor) {

		String[] retorno = {"", //linha1
				"", //linha2
				"", //letra
				"", //texto1
				""}; //texto2


		String linha1    = "";
		String linha2    = "";
		String letra     = "";
		String texto1    = "";
		String texto2    = "";

		try
		{

			linha1 = cursor.getString(cursor.getColumnIndex("codigo"))+" "+cursor.getString(cursor.getColumnIndex("razaopa"));

			linha2 = "CNPJ/CPF: "+format("##.###.###/####-##",cursor.getString(cursor.getColumnIndex("cnpj")))+" Insc. Estadual: "+cursor.getString(cursor.getColumnIndex("ie"))+" Bairro: "+cursor.getString(cursor.getColumnIndex("bairro"));

			texto1 = "CID: "+cursor.getString(cursor.getColumnIndex("cidade"))+","+cursor.getString(cursor.getColumnIndex("estado"))+" Fone: "+cursor.getString(cursor.getColumnIndex("telefone"));

			letra  = cursor.getString(cursor.getColumnIndex("razaopa")).substring(0, 1);

		}catch(Exception e) {

			linha1 = e.getMessage();

		}

		retorno[0] = linha1;

		retorno[1] = linha2;

		retorno[2] = letra;

		retorno[3] = texto1;

		retorno[4] = texto2;

		return retorno;


	};

	@Override
	public void loadColunas() {

		_colunas = new ArrayList<String>();

		_colunas.add("CODIGO");
		_colunas.add("LOJA");
		_colunas.add("RAZAOPA");
		_colunas.add("RAZAO");
		_colunas.add("FANTASIA");
		_colunas.add("CNPJ");
		_colunas.add("IE");
		_colunas.add("ENDERECO");
		_colunas.add("ESTADO");
		_colunas.add("CODCIDADE");
		_colunas.add("CIDADE");
		_colunas.add("BAIRRO");
		_colunas.add("DDD");
		_colunas.add("TELEFONE");
		_colunas.add("CEP");
		_colunas.add("SITUACAO");
		_colunas.add("CADASTRO");
		_colunas.add("LIMITE");
		_colunas.add("RISCO");
		_colunas.add("TIPOFRETE");
		_colunas.add("CANAL");
		_colunas.add("TABELA");
		_colunas.add("TABELA2");
		_colunas.add("ULTVIS");
		_colunas.add("ULTCOM");
		_colunas.add("CODIGOFORMAPAGTO");
		_colunas.add("CONDPAGTO");
		_colunas.add("OBS");
		_colunas.add("DIASPEDPROG");
		_colunas.add("FLAGALTERACP");
		_colunas.add("TAXAFIN");
		_colunas.add("PEDMINVLR");
		_colunas.add("PEDMINPES");
		_colunas.add("REDE");
		_colunas.add("CONTRATO");
		_colunas.add("VEND");
		_colunas.add("GA");
		_colunas.add("GNV");
		_colunas.add("EMAILTROCA");
		_colunas.add("EMAILNFE");
		_colunas.add("EMAIL");
		_colunas.add("SITE");
		_colunas.add("REGIAO");
		_colunas.add("PESSOA");
		_colunas.add("FUNDACAO");
		_colunas.add("BOLETO");
		_colunas.add("SIMPLES");
		_colunas.add("ISENTOST");
		_colunas.add("ICMS");
		_colunas.add("CLIENTEENTREGA");
		_colunas.add("AGENDADATA");
		_colunas.add("AGENDAHORA");
		_colunas.add("AGENDAPERIODO");
		_colunas.add("AGENDAOBS");
		_colunas.add("DESCCLIENTE");
		_colunas.add("RESTRENTREGA");
		_colunas.add("RESTRHORA");
		_colunas.add("AGENDAR");
		_colunas.add("VLRDESCARGA");
		_colunas.add("UNIDDESCARGA");
		_colunas.add("PAGDESCARGA");
		_colunas.add("PERFVEIC");
		_colunas.add("PERFCARGA");
		_colunas.add("PALETIZACAO");
		_colunas.add("CARGAMISTA");
		_colunas.add("VENCLC");
		_colunas.add("SITCOM");
		_colunas.add("SITPRO");
		_colunas.add("ROTDTBA");
		_colunas.add("ROTHORA");
		_colunas.add("ROTPERI");
		_colunas.add("ROTOBSVI");
		_colunas.add("LATITUDE");
		_colunas.add("LONGITUDE");
		_colunas.add("PARTICIPANTE");
		_colunas.add("VLRDESCARGA2");
		_colunas.add("TIPOCON");

	}

}
