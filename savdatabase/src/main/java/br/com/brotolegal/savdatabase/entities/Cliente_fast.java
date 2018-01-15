package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;
import java.util.List;

public class Cliente_fast {

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


	protected String _CANAL;
	protected String _REDE;
	protected String _TABELA;
	protected String _COND;
	protected String _POLITICA;
	protected String _VENDEDOR;
	protected String _GA;
	protected String _GNV;


	protected String _FILAGE;
	protected String _IDAGE;
	protected String _DATA;
	protected String _HORA;
	protected String _MOBILE;
	protected String _TIPOAGE;
	protected String _SITAGE;
	protected String _MOTIVONVENDA;
	protected String _MOTIVONVISITA;
	protected Float  _DIVIDAEMATRASO;

	private Integer _yellow;
	private Integer _red;

	private List<Palete> PALETES;
	private List<Perfil> PERFIS;
	private List<String> UNIDADES;

	private Boolean      _Flag;


	public Cliente_fast(String _FILAGE, String _IDAGE, String _DATA, String _HORA, String _MOBILE, String _TIPOAGE, String _SITAGE, String _MOTIVONVENDA, String _MOTIVONVISITA, float _DIVIDAEMATRASO) {
		this._FILAGE = _FILAGE;
		this._IDAGE = _IDAGE;
		this._DATA = _DATA;
		this._HORA = _HORA;
		this._MOBILE = _MOBILE;
		this._TIPOAGE = _TIPOAGE;
		this._SITAGE = _SITAGE;
		this._MOTIVONVENDA = _MOTIVONVENDA;
		this._MOTIVONVISITA = _MOTIVONVISITA;
		this._DIVIDAEMATRASO = _DIVIDAEMATRASO;
		_Flag     = false;
	}

	public Cliente_fast(String CODIGO, String LOJA, String RAZAOPA, String RAZAO, String FANTASIA, String CNPJ, String IE, String ENDERECO, String ESTADO, String CODCIDADE,
						String CIDADE, String BAIRRO, String DDD, String TELEFONE, String CEP, String SITUACAO, String CADASTRO, Float LIMITE, String RISCO, String TIPOFRETE,
						String CANAL, String TABELA, String TABELA2, String ULTVIS, String ULTCOM, String CODIGOFORMAPAGTO, String CONDPAGTO, String OBS, String DIASPEDPROG,
						String FLAGALTERACP, String TAXAFIN, String PEDMINVLR, String PEDMINPES, String REDE, String CONTRATO, String VEND, String GA, String GNV, String EMAILTROCA,
						String EMAILNFE, String EMAIL, String SITE, String REGIAO, String PESSOA, String FUNDACAO, String BOLETO, String SIMPLES, String ISENTOST, String ICMS,
						String CLIENTEENTREGA, String AGENDADATA, String AGENDAHORA, String AGENDAPERIODO, String AGENDAOBS, Float DESCCLIENTE, String RESTRENTREGA, String RESTRHORA,
						String AGENDAR, Float VLRDESCARGA, String UNIDDESCARGA, String PAGDESCARGA, String PERFVEIC, String PERFCARGA, String PALETIZACAO, String CARGAMISTA, String VENCLC,
						String SITCOM, String SITPRO, String ROTDTBA, String ROTHORA, String ROTPERI, String ROTOBSVI, String LATITUDE, String LONGITUDE, String PARTICIPANTE, Float VLRDESCARGA2, String TIPOCON,
						Integer _yellow, Integer _red, String _canal, String _rede, String _tabela, String _politica, String _cond,
						String _FILAGE, String _IDAGE, String _TIPOAGE,String _DATA, String _HORA, String _MOBILE,  String _SITAGE, String _MOTIVONVENDA, String _MOTIVONVISITA, Float _DIVIDAEMATRASO) {

		this.CODIGO = CODIGO;
		this.LOJA = LOJA;
		this.RAZAOPA = RAZAOPA;
		this.RAZAO = RAZAO;
		this.FANTASIA = FANTASIA;
		this.CNPJ = CNPJ;
		this.IE = IE;
		this.ENDERECO = ENDERECO;
		this.ESTADO = ESTADO;
		this.CODCIDADE = CODCIDADE;
		this.CIDADE = CIDADE;
		this.BAIRRO = BAIRRO;
		this.DDD = DDD;
		this.TELEFONE = TELEFONE;
		this.CEP = CEP;
		this.SITUACAO = SITUACAO;
		this.CADASTRO = CADASTRO;
		this.LIMITE = LIMITE;
		this.RISCO = RISCO;
		this.TIPOFRETE = TIPOFRETE;
		this.CANAL = CANAL;
		this.TABELA = TABELA;
		this.TABELA2 = TABELA2;
		this.ULTVIS = ULTVIS;
		this.ULTCOM = ULTCOM;
		this.CODIGOFORMAPAGTO = CODIGOFORMAPAGTO;
		this.CONDPAGTO = CONDPAGTO;
		this.OBS = OBS;
		this.DIASPEDPROG = DIASPEDPROG;
		this.FLAGALTERACP = FLAGALTERACP;
		this.TAXAFIN = TAXAFIN;
		this.PEDMINVLR = PEDMINVLR;
		this.PEDMINPES = PEDMINPES;
		this.REDE = REDE;
		this.CONTRATO = CONTRATO;
		this.VEND = VEND;
		this.GA = GA;
		this.GNV = GNV;
		this.EMAILTROCA = EMAILTROCA;
		this.EMAILNFE = EMAILNFE;
		this.EMAIL = EMAIL;
		this.SITE = SITE;
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
		this.AGENDAOBS = AGENDAOBS;
		this.DESCCLIENTE = DESCCLIENTE;
		this.RESTRENTREGA = RESTRENTREGA;
		this.RESTRHORA = RESTRHORA;
		this.AGENDAR = AGENDAR;
		this.VLRDESCARGA = VLRDESCARGA;
		this.UNIDDESCARGA = UNIDDESCARGA;
		this.PAGDESCARGA = PAGDESCARGA;
		this.PERFVEIC = PERFVEIC;
		this.PERFCARGA = PERFCARGA;
		this.PALETIZACAO = PALETIZACAO;
		this.CARGAMISTA = CARGAMISTA;
		this.VENCLC = VENCLC;
		this.SITCOM = SITCOM;
		this.SITPRO = SITPRO;
		this.ROTDTBA = ROTDTBA;
		this.ROTHORA = ROTHORA;
		this.ROTPERI = ROTPERI;
		this.ROTOBSVI = ROTOBSVI;
		this.LATITUDE = LATITUDE;
		this.LONGITUDE = LONGITUDE;
		this.PARTICIPANTE =  PARTICIPANTE;
		this.VLRDESCARGA2 =  VLRDESCARGA2;
		this.TIPOCON      =  TIPOCON;

		_CANAL  = _canal;
		_REDE   = _rede;
		_TABELA = _tabela;
		_POLITICA = _politica;
		_COND = _cond;
		_VENDEDOR = "";
		_GA       = "";
		_GNV      = "";
		_Flag     = false;

		this._FILAGE        = _FILAGE;
		this._IDAGE         = _IDAGE;
		this._DATA          = _DATA;
		this._HORA          = _HORA;
		this._MOBILE        = _MOBILE;
		this._TIPOAGE       = _TIPOAGE;
		this._DATA          = _DATA;
		this._HORA          = _HORA;
		this._MOBILE        = _MOBILE;
		this._SITAGE        = _SITAGE;
		this._MOTIVONVENDA  = _MOTIVONVENDA;
		this._MOTIVONVISITA = _MOTIVONVISITA;
		this._DIVIDAEMATRASO = _DIVIDAEMATRASO;

		this._yellow = _yellow;
		this._red    = _red;

		this.PERFIS  = new ArrayList<>();
		this.PALETES = new ArrayList<>();

		loadUnidades();

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

	public String getTABELA2() {
		return TABELA2;
	}

	public void setTABELA2(String TABELA2) {
		this.TABELA2 = TABELA2;
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

	public String get_POLITICA() {
		return _POLITICA;
	}

	public void set_POLITICA(String _POLITICA) {
		this._POLITICA = _POLITICA;
	}

	public void set_COND(String _COND) {
		this._COND = _COND;
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

	public Integer get_yellow() {
		return _yellow;
	}

	public void set_yellow(Integer _yellow) {
		this._yellow = _yellow;
	}

	public Integer get_red() {
		return _red;
	}

	public void set_red(Integer _red) {
		this._red = _red;
	}

	public Boolean get_Flag() {
		return _Flag;
	}

	public void set_Flag(Boolean _Flag) {
		this._Flag = _Flag;
	}

	public String get_FILAGE() {
		return _FILAGE;
	}

	public void set_FILAGE(String _FILAGE) {
		this._FILAGE = _FILAGE;
	}

	public String get_IDAGE() {
		return _IDAGE;
	}

	public void set_IDAGE(String _IDAGE) {
		this._IDAGE = _IDAGE;
	}

	public String get_DATA() {
		return _DATA;
	}

	public void set_DATA(String _DATA) {
		this._DATA = _DATA;
	}

	public String get_HORA() {
		return _HORA;
	}

	public void set_HORA(String _HORA) {
		this._HORA = _HORA;
	}

	public String get_MOBILE() {
		return _MOBILE;
	}

	public void set_MOBILE(String _MOBILE) {
		this._MOBILE = _MOBILE;
	}

	public String get_TIPOAGE() {
		return _TIPOAGE;
	}

	public void set_TIPOAGE(String _TIPOAGE) {
		this._TIPOAGE = _TIPOAGE;
	}

	public String get_SITAGE() {
		return _SITAGE;
	}

	public void set_SITAGE(String _SITAGE) {
		this._SITAGE = _SITAGE;
	}

	public String get_MOTIVONVENDA() {
		return _MOTIVONVENDA;
	}

	public void set_MOTIVONVENDA(String _MOTIVONVENDA) {
		this._MOTIVONVENDA = _MOTIVONVENDA;
	}

	public String get_MOTIVONVISITA() {
		return _MOTIVONVISITA;
	}

	public void set_MOTIVONVISITA(String _MOTIVONVISITA) {
		this._MOTIVONVISITA = _MOTIVONVISITA;
	}

	public Float get_DIVIDAEMATRASO() {
		return _DIVIDAEMATRASO;
	}

	public void set_DIVIDAEMATRASO(Float _DIVIDAEMATRASO) {
		this._DIVIDAEMATRASO = _DIVIDAEMATRASO;
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

	public String get_Tipo(){

		String retorno = this.PESSOA;

		if (retorno == null){

			return "";

		}

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

	public String get_PerfilCarga(){

		String retorno = "";

		switch (this.PERFCARGA){

			case "1":{

				retorno = "1-PADRAO";

				break;
			}
			case "2":{

				retorno = "2-STRECH";

				break;
			}
			case "3":{

				retorno = "3-STRECH (CARGA BATIDA 12:00)";

				break;
			}
			case "4":{

				retorno = "4-STRECH+ETIQUETA";

				break;
			}
			case "5":{

				retorno = "5-STRECH+ENVELOPE";

				break;
			}
			case "6":{

				retorno = "6-STRECH+ETIQUETA";

				break;
			}
			case "7":{

				retorno = "7-CHAPA DE MERCADO";

				break;
			}

			default:{

				retorno = this.PERFCARGA;

				break;
			}


		}

		return  retorno;

	}

	public String get_Unidade(){

		String retorno = "";

		switch (this.UNIDDESCARGA){

			case "1":{

				retorno = "1-FARDO";

				break;
			}
			case "2":{

				retorno = "2-TONELADA";

				break;
			}
			case "3":{

				retorno = "3-CHAPA";

				break;
			}
			case "4":{

				retorno = "4-PALETE";

				break;
			}

			default:{

				retorno = this.UNIDDESCARGA;

				break;
			}


		}

		return  retorno;

	}

	public String get_FormaPagto(){

		String retorno = "";

		switch (this.getPAGDESCARGA()){

			case "1":{

				retorno = "1-DINHEIRO";

				break;
			}
			case "2":{

				retorno = "2-BOLETO";

				break;
			}

			default:

				retorno = this.PAGDESCARGA;

				break;
		}

		return  retorno;

	}

	public String getClienteSituacao(){

		String retorno = "";

		if (this.SITUACAO !=  null && this.SITUACAO.equals("")) {

			return retorno;

		}

		switch (this.SITUACAO.charAt(0)){

			case 'I':retorno = "comercialmente INATIVO"; break;

			case 'A':retorno = "comercialmente ATIVO" ; break;

			default:retorno  = "";
		}

		return retorno;

	}

	public Boolean getRestricao(int indice){

		String[] dias = this.RESTRENTREGA.split("\\&");

		if (dias[indice].equals("X")){

			return Boolean.valueOf(false);


		} else {


			return Boolean.valueOf(true);

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

	public String getPERFILVEIC() {
		String basico = "";
		basico =  "VAN|VAN|FALSE|&";
		basico += "VUC|VUC|FALSE|&";
		basico += "3/4|3/4|FALSE|&";
		basico += "TOC|TOCO|FALSE|&";
		basico += "TRU|TRUCK|FALSE|&";
		basico += "CAR|CARRETA|FALSE|&";
		basico += "BAU|BAU|FALSE|&";
		if (this.PERFVEIC.trim().isEmpty()){

			this.PERFVEIC = basico;

		}

		return this.PERFVEIC;
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

			this.PERFVEIC = basico;

		} else {

			this.PERFVEIC = pERFILVEIC;

		}

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

	public String get_PERIODICIDADE() {

		String retorno = "";

		switch (this.getROTPERI()) {


			case "1":

				retorno = "1-Cada 1 Semana";

				break;

			case "2":

				retorno = "2-Cada 2 Semana";

				break;

			case "3":

				retorno = "3-Cada 3 Semana";

				break;

			case "4":

				retorno = "4-Cada 4 Semana";

				break;

			case "M":

				retorno = "M-Mensalmente";

				break;

			case "A":

				retorno = "A-Anualmente";

				break;


			case "N":

				retorno = "N-NENHUMA";

				break;

			default: {

				retorno = this.ROTPERI;

				break;
			}
		}

		return retorno;

	}

	public List<String[]> getlsPeriodo(){

		List<String[]> retorno = new ArrayList<>();

		retorno.add(new String[] {"1","CADA 1 SEMANA"});
		retorno.add(new String[] {"2","CADA 2 SEMANA"});
		retorno.add(new String[] {"3","CADA 3 SEMANA"});
		retorno.add(new String[] {"4","CADA 4 SEMANA"});
		retorno.add(new String[] {"M","MENSALMENTE"});
		retorno.add(new String[] {"A","ANUALMENTE"});
		retorno.add(new String[] {"N","NENHUM"}
		);

		return retorno;


	}


}