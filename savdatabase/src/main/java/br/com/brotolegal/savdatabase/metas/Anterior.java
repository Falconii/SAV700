package br.com.brotolegal.savdatabase.metas;

public class Anterior {

	
	 private String MOVIMENTO;
	 
	 private String CATEGORIAS;
	 
	 private String CODCONSULTA;
	    
     private String CLACONSULTA;
     
     private String CLARETORNO;

	public Anterior(String mOVIMENTO, String cATEGORIAS, String cODCONSULTA,
					String cLACONSULTA, String cLARETORNO) {
		super();
		MOVIMENTO = mOVIMENTO;
		CATEGORIAS = cATEGORIAS;
		CODCONSULTA = cODCONSULTA;
		CLACONSULTA = cLACONSULTA;
		CLARETORNO = cLARETORNO;
	}

	public String getMOVIMENTO() {
		return MOVIMENTO;
	}

	public void setMOVIMENTO(String mOVIMENTO) {
		MOVIMENTO = mOVIMENTO;
	}

	public String getCATEGORIAS() {
		return CATEGORIAS;
	}

	public void setCATEGORIAS(String cATEGORIAS) {
		CATEGORIAS = cATEGORIAS;
	}

	public String getCODCONSULTA() {
		return CODCONSULTA;
	}

	public void setCODCONSULTA(String cADCONSULTA) {
		CODCONSULTA = cADCONSULTA;
	}

	public String getCLACONSULTA() {
		return CLACONSULTA;
	}

	public void setCLACONSULTA(String cLACONSULTA) {
		CLACONSULTA = cLACONSULTA;
	}

	public String getCLARETORNO() {
		return CLARETORNO;
	}

	public void setCLARETORNO(String cLARETORNO) {
		CLARETORNO = cLARETORNO;
	}
     
     
}
