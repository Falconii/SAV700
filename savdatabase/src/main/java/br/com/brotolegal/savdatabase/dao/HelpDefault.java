package br.com.brotolegal.savdatabase.dao;

public class HelpDefault {
	
	private boolean check;
	private String mensagem1;
	private String mensagem2;
	private String[] fieldkey;
	private String[] valuekey;
	private String   textopesquisa;
	private String   letra;
	private String   texto1;
	private String   texto2;
	
	
	public HelpDefault(boolean check, String mensagem1, String mensagem2,
			String[] fieldkey, String[] valuekey, String textopesquisa,
			String letra, String texto1, String texto2) {
		super();
		this.check = check;
		this.mensagem1 = mensagem1;
		this.mensagem2 = mensagem2;
		this.fieldkey = fieldkey;
		this.valuekey = valuekey;
		this.textopesquisa = textopesquisa;
		this.letra = letra;
		this.texto1 = texto1;
		this.texto2 = texto2;
	}


	public boolean isCheck() {
		return check;
	}


	public void setCheck(boolean check) {
		this.check = check;
	}


	public String getMensagem1() {
		return mensagem1;
	}


	public void setMensagem1(String mensagem1) {
		this.mensagem1 = mensagem1;
	}


	public String getMensagem2() {
		return mensagem2;
	}


	public void setMensagem2(String mensagem2) {
		this.mensagem2 = mensagem2;
	}


	public String[] getFieldkey() {
		return fieldkey;
	}


	public void setFieldkey(String[] fieldkey) {
		this.fieldkey = fieldkey;
	}


	public String[] getValuekey() {
		return valuekey;
	}


	public void setValuekey(String[] valuekey) {
		this.valuekey = valuekey;
	}


	public String getTextopesquisa() {
		return textopesquisa;
	}


	public void setTextopesquisa(String textopesquisa) {
		this.textopesquisa = textopesquisa;
	}


	public String getLetra() {
		return letra;
	}


	public void setLetra(String letra) {
		this.letra = letra;
	}


	public String getTexto1() {
		return texto1;
	}


	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}


	public String getTexto2() {
		return texto2;
	}


	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}
	
		

}
