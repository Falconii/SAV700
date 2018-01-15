package br.com.brotolegal.savdatabase.config;


public  class Config {
    private String id;
	private String coduser;
	private String endereco;
	private int    rede;
	private String password;
	private String classificacao;
	private String datainicial;
	private String datafinal;
	private String savestatus;
	private String periodoverba;
	private String flag_verba;
	private String flag_aprovacao;
	private String flag_codigo;
	private String flag_indice;
	private String extra;
	
	


	public Config(String id,String coduser, String endereco, int rede, String password,
			String classificacao, String datainicial, String datafinal,
			String savestatus, String periodoverba, String flag_verba, String flag_aprovacao,String flag_codigo,String flag_indice,String extra) {
		super();
		this.id             = id;
		this.coduser        = coduser;
		this.endereco       = endereco;
		this.rede           = rede;
		this.password       = password;
		this.classificacao  = classificacao;
		this.datainicial    = datainicial;
		this.datafinal      = datafinal;
		this.savestatus     = savestatus;
		this.periodoverba   = periodoverba;
		this.flag_verba     = flag_verba;
		this.flag_aprovacao = flag_aprovacao;
		this.flag_codigo    = flag_codigo;
		this.flag_indice    = flag_indice;
		this.extra          = extra;
	}


	public Config(String id) {
		super();
		this.id             = id;
		this.coduser        = "";
		this.endereco       = "";
		this.rede           = 1;
		this.password       = "";
		this.classificacao  = "";
		this.datainicial    = "20140401";
		this.datafinal      = "20140430";
		this.savestatus     = "S";
		this.periodoverba   = "201404";
		this.flag_verba     = "";
		this.flag_aprovacao = "";
		this.flag_codigo    = "";
		this.flag_indice    = "";
		this.extra          = "";
	}

	

	public String getFlag_indice() {
		return flag_indice;
	}


	public void setFlag_indice(String flag_indice) {
		this.flag_indice = flag_indice;
	}


	public String getExtra() {
		return extra;
	}


	public void setExtra(String extra) {
		this.extra = extra;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFlag_codigo() {
		return flag_codigo;
	}


	public void setFlag_codigo(String flag_codigo) {
		this.flag_codigo = flag_codigo;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public int getRede() {
		return rede;
	}


	public void setRede(int rede) {
		this.rede = rede;
	}


	public String getCoduser() {
		return coduser;
	}


	public void setCoduser(String coduser) {
		this.coduser = coduser;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getClassificacao() {
		return classificacao;
	}


	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}


	public String getDatainicial() {
		return datainicial;
	}


	public void setDatainicial(String datainicial) {
		this.datainicial = datainicial;
	}


	public String getDatafinal() {
		return datafinal;
	}


	public void setDatafinal(String datafinal) {
		this.datafinal = datafinal;
	}


	public String getSavestatus() {
		return savestatus;
	}


	public void setSavestatus(String savestatus) {
		this.savestatus = savestatus;
	}


	public String getPeriodoverba() {
		return periodoverba;
	}


	public void setPeriodoverba(String periodoverba) {
		this.periodoverba = periodoverba;
	}
	
	public String getFlag_verba() {
		return flag_verba;
	}


	public void setFlag_verba(String flag_verba) {
		this.flag_verba = flag_verba;
	}


	public String getFlag_aprovacao() {
		return flag_aprovacao;
	}


	public void setFlag_aprovacao(String flag_aprovacao) {
		this.flag_aprovacao = flag_aprovacao;
	}
	


	
}
