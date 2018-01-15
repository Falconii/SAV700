package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;
import java.util.List;

public class Cliente_fb {

	private String CODIGO;
	private String LOJA;
	private String RAZAOPA;
	private String RAZAO;
	private String FANTASIA;
	private String CNPJ;
	private String IE;
	private String ENDERECO;
	private String ESTADO;
	private String CODCIDADE;
	private String CIDADE;
	private String BAIRRO;
	private String DDD;
	private String TELEFONE;
	private String CEP;
	private String SITUACAO;
	private String CADASTRO;
	private Float  LIMITE;
	private String RISCO;
	private String TIPOFRETE;
	private String CANAL;
	private String TABELA;
	private String TABELA2;
	private String ULTVIS;
	private String ULTCOM;
	private String CODIGOFORMAPAGTO;
	private String CONDPAGTO;
	private String OBS;
	private String DIASPEDPROG;
	private String FLAGALTERACP;
	private String TAXAFIN;
	private String PEDMINVLR;
	private String PEDMINPES;
	private String REDE;
	private String CONTRATO;
	private String VEND;
	private String GA;
	private String GNV;
	private String EMAILTROCA;
	private String EMAILNFE;
	private String EMAIL;
	private String SITE;
	private String REGIAO;
	private String PESSOA;
	private String FUNDACAO;
	private String BOLETO;
	private String SIMPLES;
	private String ISENTOST;
	private String ICMS;
	private String CLIENTEENTREGA;
	private String AGENDADATA;
	private String AGENDAHORA;
	private String AGENDAPERIODO;
	private String AGENDAOBS;
	private Float  DESCCLIENTE;
	private String RESTRENTREGA;
	private String RESTRHORA;
	private String AGENDAR;
	private Float  VLRDESCARGA;
	private String UNIDDESCARGA;
	private String PAGDESCARGA;
	private String PERFVEIC;
	private String PERFCARGA;
	private String PALETIZACAO;
	private String CARGAMISTA;
	private String VENCLC;
	private String SITCOM;
	private String SITPRO;
	private String ROTDTBA;
	private String ROTHORA;
	private String ROTPERI;
	private String ROTOBSVI;
	private String LATITUDE;
	private String LONGITUDE;
	private String PARTICIPANTE;
	private Float  VLRDESCARGA2;


	public Cliente_fb() {}

	public Cliente_fb(String CODIGO, String LOJA, String RAZAOPA, String RAZAO, String FANTASIA, String CNPJ, String IE, String ENDERECO, String ESTADO, String CODCIDADE, String CIDADE, String BAIRRO, String DDD, String TELEFONE, String CEP, String SITUACAO, String CADASTRO, Float LIMITE, String RISCO, String TIPOFRETE, String CANAL, String TABELA, String TABELA2, String ULTVIS, String ULTCOM, String CODIGOFORMAPAGTO, String CONDPAGTO, String OBS, String DIASPEDPROG, String FLAGALTERACP, String TAXAFIN, String PEDMINVLR, String PEDMINPES, String REDE, String CONTRATO, String VEND, String GA, String GNV, String EMAILTROCA, String EMAILNFE, String EMAIL, String SITE, String REGIAO, String PESSOA, String FUNDACAO, String BOLETO, String SIMPLES, String ISENTOST, String ICMS, String CLIENTEENTREGA, String AGENDADATA, String AGENDAHORA, String AGENDAPERIODO, String AGENDAOBS, Float DESCCLIENTE, String RESTRENTREGA, String RESTRHORA, String AGENDAR, Float VLRDESCARGA, String UNIDDESCARGA, String PAGDESCARGA, String PERFVEIC, String PERFCARGA, String PALETIZACAO, String CARGAMISTA, String VENCLC, String SITCOM, String SITPRO, String ROTDTBA, String ROTHORA, String ROTPERI, String ROTOBSVI, String LATITUDE, String LONGITUDE, String PARTICIPANTE, Float VLRDESCARGA2) {
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
		this.PARTICIPANTE = PARTICIPANTE;
		this.VLRDESCARGA2 = VLRDESCARGA2;
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
	
	public void importacao(Cliente_fast cliente){

		this.CODIGO = cliente.getCODIGO();
		this.LOJA = cliente.getLOJA();
		this.RAZAOPA = cliente.getRAZAOPA();
		this.RAZAO = cliente.getRAZAO();
		this.FANTASIA = cliente.getFANTASIA();
		this.CNPJ = cliente.getCNPJ();
		this.IE = cliente.getIE();
		this.ENDERECO = cliente.getENDERECO();
		this.ESTADO = cliente.getESTADO();
		this.CODCIDADE = cliente.getCODCIDADE();
		this.CIDADE = cliente.getCIDADE();
		this.BAIRRO = cliente.getBAIRRO();
		this.DDD = cliente.getDDD();
		this.TELEFONE = cliente.getTELEFONE();
		this.CEP = cliente.getCEP();
		this.SITUACAO = cliente.getSITUACAO();
		this.CADASTRO = cliente.getCADASTRO();
		this.LIMITE = cliente.getLIMITE();
		this.RISCO = cliente.getRISCO();
		this.TIPOFRETE = cliente.getTIPOFRETE();
		this.CANAL = cliente.getCANAL();
		this.TABELA = cliente.getTABELA();
		this.TABELA2 = cliente.getTABELA2();
		this.ULTVIS = cliente.getULTVIS();
		this.ULTCOM = cliente.getULTCOM();
		this.CODIGOFORMAPAGTO = cliente.getCODIGOFORMAPAGTO();
		this.CONDPAGTO = cliente.getCONDPAGTO();
		this.OBS = cliente.getOBS();
		this.DIASPEDPROG = cliente.getDIASPEDPROG();
		this.FLAGALTERACP = cliente.getFLAGALTERACP();
		this.TAXAFIN = cliente.getTAXAFIN();
		this.PEDMINVLR = cliente.getPEDMINVLR();
		this.PEDMINPES = cliente.getPEDMINPES();
		this.REDE = cliente.getREDE();
		this.CONTRATO = cliente.getCONTRATO();
		this.VEND = cliente.getVEND();
		this.GA = cliente.getGA();
		this.GNV = cliente.getGNV();
		this.EMAILTROCA = cliente.getEMAILTROCA();
		this.EMAILNFE = cliente.getEMAILNFE();
		this.EMAIL = cliente.getEMAIL();
		this.SITE = cliente.getSITE();
		this.REGIAO = cliente.getREGIAO();
		this.PESSOA = cliente.getPESSOA();
		this.FUNDACAO = cliente.getFUNDACAO();
		this.BOLETO = cliente.getBOLETO();
		this.SIMPLES = cliente.getSIMPLES();
		this.ISENTOST = cliente.getISENTOST();
		this.ICMS = cliente.getICMS();
		this.CLIENTEENTREGA = cliente.getCLIENTEENTREGA();
		this.AGENDADATA = cliente.getAGENDADATA();
		this.AGENDAHORA = cliente.getAGENDAHORA();
		this.AGENDAPERIODO = cliente.getAGENDAPERIODO();
		this.AGENDAOBS = cliente.getAGENDAOBS();
		this.DESCCLIENTE = cliente.getDESCCLIENTE();
		this.RESTRENTREGA = cliente.getRESTRENTREGA();
		this.RESTRHORA = cliente.getRESTRHORA();
		this.AGENDAR = cliente.getAGENDAR();
		this.VLRDESCARGA = cliente.getVLRDESCARGA();
		this.UNIDDESCARGA = cliente.getUNIDDESCARGA();
		this.PAGDESCARGA = cliente.getPAGDESCARGA();
		this.PERFVEIC = cliente.getPERFVEIC();
		this.PERFCARGA = cliente.getPERFCARGA();
		this.PALETIZACAO = cliente.getPALETIZACAO();
		this.CARGAMISTA = cliente.getCARGAMISTA();
		this.VENCLC = cliente.getVENCLC();
		this.SITCOM = cliente.getSITCOM();
		this.SITPRO = cliente.getSITPRO();
		this.ROTDTBA = cliente.getROTDTBA();
		this.ROTHORA = cliente.getROTHORA();
		this.ROTPERI = cliente.getROTPERI();
		this.ROTOBSVI = cliente.getROTOBSVI();
		this.LATITUDE = cliente.getLATITUDE();
		this.LONGITUDE = cliente.getLONGITUDE();
		this.PARTICIPANTE = cliente.getPARTICIPANTE();
		this.VLRDESCARGA2 = cliente.getVLRDESCARGA2();
	}
}