package br.com.brotolegal.savdatabase.regrasdenegocio;


import br.com.brotolegal.savdatabase.dao.HelpFiltro;

public class opcoesFiltro {
	private int nro;
	private HelpFiltro filtro;
	private int imageid;
	private int tipo;
    private String mensagem;	
	
	
	
	
	public opcoesFiltro(int nro, HelpFiltro filtro, int imageid) {
		super();
		this.nro      = nro;
		this.filtro   = filtro;
		this.imageid  = imageid;
		this.tipo     = 1;
		this.mensagem = "";
	}
	
	
	public int getNro() {
		return nro;
	}
	public void setNro(int nro) {
		this.nro = nro;
	}
	public HelpFiltro getFiltro() {
		return filtro;
	}
	public void setFiltro(HelpFiltro filtro) {
		this.filtro = filtro;
	}
	public int getImageid() {
		return imageid;
	}
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	
	
	

}
