package br.com.brotolegal.savdatabase.LibFile;

public class Dicionario {

	private String CAMPO;
	private String TIPO;
	private String TAM;
	private String CD;
	
	
	public Dicionario(String cAMPO, String tIPO, String tAM, String cD) {
		
		CAMPO = cAMPO;
		TIPO = tIPO;
		TAM = tAM;
		CD = cD;
		
	}


	public Dicionario(String[] values) {
		
		CAMPO = values[1];
		TIPO  = values[2];
		TAM   = "";
		CD    = "";
		
	}

	public String getCAMPO() {
		return CAMPO;
	}


	public void setCAMPO(String cAMPO) {
		CAMPO = cAMPO;
	}


	public String getTIPO() {
		return TIPO;
	}


	public void setTIPO(String tIPO) {
		TIPO = tIPO;
	}


	public String getTAM() {
		return TAM;
	}


	public void setTAM(String tAM) {
		TAM = tAM;
	}


	public String getCD() {
		return CD;
	}


	public void setCD(String cD) {
		CD = cD;
	}
	
	
}
