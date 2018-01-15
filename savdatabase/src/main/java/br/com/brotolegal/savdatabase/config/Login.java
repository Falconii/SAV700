package br.com.brotolegal.savdatabase.config;

public class Login {


     private String erro;
	 private String msgerror;
	 private String codigo;
	 private String nome;
	 private String password;
	 private String nreduz;
	 private int    nivel;
	 private String classi;
	 private float  saldo;
	 private String redes;
	 
	 public Login(){
		 
	 }

	public Login(String erro, String msgerror, String codigo, String nome,String password,
			String nreduz, int  nivel, String classi, float saldo, String redes) {

		this.erro = erro;
		this.msgerror = msgerror;
		this.codigo = codigo;
		this.nome = nome;
		this.password = password;
		this.nreduz = nreduz;
		this.nivel = nivel;
		this.classi = classi;
		this.saldo = saldo;
		this.redes  = redes;
	}

	
	public void setLogin(Login log){
		
		this.erro     = log.getErro();
		this.msgerror = log.getMsgerror();
		this.codigo   = log.getCodigo();
		this.nome     = log.getNome();
		this.password = log.getPassword();
		this.nreduz   = log.getNreduz();
		this.nivel    = log.getNivel();
		this.classi   = log.getClassi();
		this.saldo    = log.getSaldo();
		this.redes    = log.getRedes(); 
		
	}
	
	
	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMsgerror() {
		return msgerror;
	}

	public void setMsgerror(String msgerror) {
		this.msgerror = msgerror;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNreduz() {
		return nreduz;
	}

	public void setNreduz(String nreduz) {
		this.nreduz = nreduz;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getClassi() {
		return classi;
	}

	public void setClassi(String classi) {
		this.classi = classi;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	 
	
	 public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRedes() {
		return redes;
	}

	public void setRedes(String redes) {
		this.redes = redes;
	}


	 
}
