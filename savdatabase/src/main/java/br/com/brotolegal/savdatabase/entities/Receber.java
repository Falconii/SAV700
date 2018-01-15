package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Receber extends ObjRegister {

	protected String FILIAL;
	protected String PREFIXO;
	protected String NUM;
	protected String PARCELA;
	protected String TIPO;
	protected String CLIENTE;
	protected String LOJA;
	protected String RAZAO;
	protected String REDE;
	protected String EMISSAO;
	protected Float VALOR;
	protected String VENCTO;
	protected String PAGTO;
	protected Float SALDO;
	protected Float DESCCONTRATO;
	protected String BANCO;
	protected String AGENCIA;
	protected String NROBANCARIO;
	protected String LINHADIGITAVEL;
	protected Integer ATRASO;


	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Receber";


	public Receber(){

		super(_OBJETO,"RECEBER");

		loadColunas();

		InicializaFields();
	}

	public Receber(String FILIAL, String PREFIXO, String NUM, String PARCELA, String TIPO, String CLIENTE, String LOJA, String RAZAO, String REDE, String EMISSAO, Float VALOR, String VENCTO, String PAGTO, Float SALDO, Float DESCCONTRATO, String BANCO, String AGENCIA, String NROBANCARIO, String LINHADIGITAVEL, Integer ATRASO) {

		super(_OBJETO,"RECEBER");

		loadColunas();

		InicializaFields();

		this.FILIAL = FILIAL;
		this.PREFIXO = PREFIXO;
		this.NUM = NUM;
		this.PARCELA = PARCELA;
		this.TIPO = TIPO;
		this.CLIENTE = CLIENTE;
		this.LOJA = LOJA;
		this.RAZAO = RAZAO;
		this.REDE = REDE;
		this.EMISSAO = EMISSAO;
		this.VALOR = VALOR;
		this.VENCTO = VENCTO;
		this.PAGTO = PAGTO;
		this.SALDO = SALDO;
		this.DESCCONTRATO = DESCCONTRATO;
		this.BANCO = BANCO;
		this.AGENCIA = AGENCIA;
		this.NROBANCARIO = NROBANCARIO;
		this.LINHADIGITAVEL = LINHADIGITAVEL;
		this.ATRASO         = ATRASO;
	}


	public String getFILIAL() {
		return FILIAL;
	}

	public void setFILIAL(String FILIAL) {
		this.FILIAL = FILIAL;
	}

	public String getPREFIXO() {
		return PREFIXO;
	}

	public void setPREFIXO(String PREFIXO) {
		this.PREFIXO = PREFIXO;
	}

	public String getNUM() {
		return NUM;
	}

	public void setNUM(String NUM) {
		this.NUM = NUM;
	}

	public String getPARCELA() {
		return PARCELA;
	}

	public void setPARCELA(String PARCELA) {
		this.PARCELA = PARCELA;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String TIPO) {
		this.TIPO = TIPO;
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

	public String getREDE() {
		return REDE;
	}

	public void setREDE(String REDE) {
		this.REDE = REDE;
	}

	public String getEMISSAO() {
		return EMISSAO;
	}

	public void setEMISSAO(String EMISSAO) {
		this.EMISSAO = EMISSAO;
	}

	public Float getVALOR() {
		return VALOR;
	}

	public void setVALOR(Float VALOR) {
		this.VALOR = VALOR;
	}

	public String getVENCTO() {
		return VENCTO;
	}

	public void setVENCTO(String VENCTO) {
		this.VENCTO = VENCTO;
	}

	public String getPAGTO() {
		return PAGTO;
	}

	public void setPAGTO(String PAGTO) {
		this.PAGTO = PAGTO;
	}

	public Float getSALDO() {
		return SALDO;
	}

	public void setSALDO(Float SALDO) {
		this.SALDO = SALDO;
	}

	public Float getDESCCONTRATO() {
		return DESCCONTRATO;
	}

	public void setDESCCONTRATO(Float DESCCONTRATO) {
		this.DESCCONTRATO = DESCCONTRATO;
	}

	public String getBANCO() {
		return BANCO;
	}

	public void setBANCO(String BANCO) {
		this.BANCO = BANCO;
	}

	public String getAGENCIA() {
		return AGENCIA;
	}

	public void setAGENCIA(String AGENCIA) {
		this.AGENCIA = AGENCIA;
	}

	public String getNROBANCARIO() {
		return NROBANCARIO;
	}

	public void setNROBANCARIO(String NROBANCARIO) {
		this.NROBANCARIO = NROBANCARIO;
	}

	public String getLINHADIGITAVEL() {
		return LINHADIGITAVEL;
	}

	public void setLINHADIGITAVEL(String LINHADIGITAVEL) {
		this.LINHADIGITAVEL = LINHADIGITAVEL;
	}

	public Integer getATRASO() {
		return ATRASO;
	}

	public void setATRASO(Integer ATRASO) {
		this.ATRASO = ATRASO;
	}

	@Override
	public void loadColunas() {

		_colunas = new ArrayList<String>();

		_colunas.add("FILIAL");
		_colunas.add("PREFIXO");
		_colunas.add("NUM");
		_colunas.add("PARCELA");
		_colunas.add("TIPO");
		_colunas.add("CLIENTE");
		_colunas.add("LOJA");
		_colunas.add("RAZAO");
		_colunas.add("REDE");
		_colunas.add("EMISSAO");
		_colunas.add("VALOR");
		_colunas.add("VENCTO");
		_colunas.add("PAGTO");
		_colunas.add("SALDO");
		_colunas.add("DESCCONTRATO");
		_colunas.add("BANCO");
		_colunas.add("AGENCIA");
		_colunas.add("NROBANCARIO");
		_colunas.add("LINHADIGITAVEL");
		_colunas.add("ATRASO");


	}

}
