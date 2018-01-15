package br.com.brotolegal.savdatabase.metas;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class RelMetas extends OData{
	
	  protected String ERRO;
      protected String MSGERRO;
	  protected String CATEGORIACOD;
	  protected String CATEGORIADES;
	  protected String CDTRECMET;
	  protected String CLASSRET;
	  protected String CODRET;
	  protected String NOMERET;
      protected Float ATINGIDO;
      protected Float CARTEIRAS;
      protected Float OBJETIVO;
      protected Float PERREAL;
      protected Float TOTALREAL;
      protected Float PERCCART;
      protected Float TOTALCAR;
      protected Float PERIDEAL;
      protected String VISIVEL;
      protected String MOVIMENTO;


  	public RelMetas(){
  		
		super("br.com.brotolegal.savdatabase.metas.RelMetas");
		
    }

	
	public RelMetas(String eRRO,
			        String mSGERRO,
			        String cATEGORIACOD,
			        String cATEGORIADES,
			        String cDTRECMET,
			        String cLASSRET,
			        String cODRET,
			        String nOMERET,
			        float aTINGIDO, 
			        float cARTEIRAS, 
			        float oBJETIVO,
			        float pERIDEAL,
			        String mOVIMENTO
            ) {
		super("br.com.brotolegal.savdatabase.metas.RelMetas");
		ERRO         = eRRO;
		MSGERRO      =  mSGERRO;
		CATEGORIACOD = cATEGORIACOD;
		CATEGORIADES = cATEGORIADES;
		CDTRECMET    = cDTRECMET;
		CLASSRET     = cLASSRET;
		CODRET       = cODRET;
		NOMERET      = nOMERET;
		ATINGIDO     = aTINGIDO;
		CARTEIRAS    = cARTEIRAS;
		OBJETIVO     = oBJETIVO;
		PERIDEAL     = pERIDEAL;
		
		if ( OBJETIVO > 0){
			
			PERREAL      = (ATINGIDO/OBJETIVO) * 100;
		}
		
		else{
			
			PERREAL       = 0f;
			
		}

		
		TOTALCAR     = this.ATINGIDO+this.CARTEIRAS; 

		
		if (this.OBJETIVO > 0){
			
			PERCCART = (TOTALCAR/OBJETIVO) * 100;
		}
		
		else{
			
			PERCCART = 0f;
			
		}

		VISIVEL = "N";
		
		MOVIMENTO = mOVIMENTO;
		
	}



	public String getMOVIMENTO() {
		return MOVIMENTO;
	}


	public void setMOVIMENTO(String mOVIMENTO) {
		MOVIMENTO = mOVIMENTO;
	}


	public Float getPERIDEAL() {
		return PERIDEAL;
	}


	public void setPERIDEAL(Float pERIDEAL) {
		PERIDEAL = pERIDEAL;
	}


	public String getVISIVEL() {
		return VISIVEL;
	}


	public void setVISIVEL(String vISIVEL) {
		VISIVEL = vISIVEL;
	}


	public String getERRO() {
		return ERRO;
	}


	public void setERRO(String eRRO) {
		ERRO = eRRO;
	}

	public String getMSGERRO() {
		return MSGERRO;
	}


	public void setMSGERRO(String mSGERRO) {
		MSGERRO = mSGERRO;
	}


	public String getCATEGORIACOD() {
		return CATEGORIACOD;
	}


	public void setCATEGORIACOD(String cATEGORIACOD) {
		CATEGORIACOD = cATEGORIACOD;
	}


	public String getCATEGORIADES() {
		return CATEGORIADES;
	}


	public void setCATEGORIADES(String cATEGORIADES) {
		CATEGORIADES = cATEGORIADES;
	}


	public String getCLASSRET() {
		return CLASSRET;
	}


	public void setCLASSRET(String cLASSRET) {
		CLASSRET = cLASSRET;
	}

	public String getCODRET() {
		return CODRET;
	}

	public void setCODRET(String cODRET) {
		CODRET = cODRET;
	}

	public String getNOMERET() {
		return NOMERET;
	}

	public void setNOMERET(String nOMERET) {
		NOMERET = nOMERET;
	}

	public float getATINGIDO() {
		return ATINGIDO;
	}

	public void setATINGIDO(float aTINGIDO) {
		ATINGIDO = aTINGIDO;
	}

	public float getCARTEIRAS() {
		return CARTEIRAS;
	}


	public void setCARTEIRAS(float cARTEIRAS) {
		CARTEIRAS = cARTEIRAS;
	}






	public float getOBJETIVO() {
		return OBJETIVO;
	}






	public void setOBJETIVO(float oBJETIVO) {
		OBJETIVO = oBJETIVO;
	}






	public float getPERREAL() {
		
		float retorno = 0;
		
		if (this.OBJETIVO > 0){
			
			retorno = (this.ATINGIDO/this.OBJETIVO) * 100;
		}
		
		else{
			
			retorno = 0;
			
		}
		
		return retorno;
	}






	public float getTOTALREAL() {
		return TOTALREAL;
	}






	public float getPERCCART() {
		return PERCCART;
	}






	public float getTOTALCAR() {
		return TOTALCAR;
	}






	public String getCDTRECMET() {
		return CDTRECMET;
	}


	public void setCDTRECMET(String cDTRECMET) {
		CDTRECMET = cDTRECMET;
	}


	public String getValueFormatedByname(String name) {

		DecimalFormat format = new DecimalFormat(",##0.00");

		final String _string = "java.lang.String";
		final String _float  = "java.lang.Float";
		final String _long   = "java.lang.Long";

		String retorno = " ";

		String type = getTypeByName(name);

		if (getIndiceByName(name) != -1){

			try {  

				Class cls = Class.forName("br.com.brotolegal.savdatabase.metas.RelMetas");

				Field fieldlist[] = cls.getDeclaredFields();

				for (int i = 0; i < fieldlist.length; i++) {  

					Field fld = fieldlist[i];

					String fieldname = fld.getName();


					if (fieldname.equals(name)){

						try {

							if (type.equals(_string)){

								retorno = (String) fld.get(this);


							}

							if (type.equals(_float)){

								retorno = format.format((Float) fld.get(this));

							}

							if (type.equals(_long)){

								retorno = format.format((Long) fld.get(this));

							}

						}

						catch (IllegalAccessException x) {

							retorno = "";

						}

					}

				}  
			}  
			catch (Throwable e) {

				retorno = "";  

			}
		}


		return retorno;

	}
/*
 * 
 *  0 = verde
 *  1 = amarelo
 *  2 = vermelho
 *  3 = branco
 */
	public int semaforo(String tipo){
		
		int retorno = 0;
		
		BigDecimal REAL;
		
		Double min = (PERIDEAL - (PERIDEAL * (0.10)));
				
		BigDecimal IDEAL = new BigDecimal(PERIDEAL);
		
		IDEAL = IDEAL.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		if (tipo.equals("R")){
			
			REAL = new BigDecimal(PERREAL);
			
		} else {
			
			REAL = new BigDecimal(PERCCART);
			
		}
		
		REAL = REAL.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal MINIMO = new BigDecimal(min);
		
		MINIMO = MINIMO.setScale(2, BigDecimal.ROUND_HALF_UP);

		retorno = REAL.compareTo(IDEAL);
		
		if ((retorno == 1) || (retorno == 0)) {
			
			retorno = 0 ; //verde
			
		} else {
			
			int menorIdeal  = REAL.compareTo(IDEAL);
			int maiorMinimo = REAL.compareTo(MINIMO);
			int menorMinimo = REAL.compareTo(MINIMO);
			
			if ( menorIdeal == -1){
				
				menorIdeal = 1;
				
			} else {
				
				menorIdeal = 0;
				
			}
			
			if ( (maiorMinimo == 1) || (maiorMinimo == 0)) {
				
				maiorMinimo = 1;
				
			} else {
				
				maiorMinimo = 0;
				
			}
			
			if ( menorMinimo == -1){
				
				menorMinimo = 1;
				
			} else {
				
				menorMinimo = 0;
				
			}
			
			if ( menorMinimo == 1 ) {
				
				retorno = 2;
				
			} else {
				
				
				retorno = 1;
				
			}
			
		}
       
		
		return retorno;
	}

}
