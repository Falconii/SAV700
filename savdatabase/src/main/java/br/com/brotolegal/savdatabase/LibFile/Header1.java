package br.com.brotolegal.savdatabase.LibFile;

public class Header1 {

	private String DATA_HORA;
	private String ARQUIVO;
	private int    QTD_CAMPOS;
	
	


	public Header1(String[] values) {
	
		DATA_HORA    = values[1];
		
		ARQUIVO      = values[2];
		
		QTD_CAMPOS   = Integer.parseInt(values[3]);
		
		
	}

	
	public String getDATA_HORA() {
		return DATA_HORA;
	}


	public void setDATA_HORA(String dATA_HORA) {
		DATA_HORA = dATA_HORA;
	}


	public String getARQUIVO() {
		return ARQUIVO;
	}


	public void setARQUIVO(String aRQUIVO) {
		ARQUIVO = aRQUIVO;
	}


	public int getQTD_CAMPOS() {
		return QTD_CAMPOS;
	}


	public void setQTD_CAMPOS(int qTD_CAMPOS) {
		QTD_CAMPOS = qTD_CAMPOS;
	}
	
	
}
