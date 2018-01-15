package br.com.brotolegal.savdatabase.metas;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MetaVend extends OData{
	
	  protected String CODIGO;
	  protected String NOME;
	  protected String CLASS;
	  protected Float PERIDEAL;
	  protected Float PERREAL;
	  protected Float PERCART;
	  protected String TEXTO;


	public MetaVend(){
  		
		super("br.com.brotolegal.savdatabase.metas.MetaVend");
		
    }


	public MetaVend(String cODIGO, String nOME, String cLASS, Float pERIDEAL, String tEXTO) {
		super("br.com.brotolegal.savdatabase.metas.MetaVend");
		CODIGO = cODIGO;
		NOME = nOME;
		CLASS = cLASS;
		PERIDEAL  = pERIDEAL;
		PERREAL   = 0f;
		PERCART   = 0f;		
		TEXTO     = tEXTO;
	}




	public String getTEXTO() {
		return TEXTO;
	}


	public void setTEXTO(String tEXTO) {
		TEXTO = tEXTO;
	}


	public String getCODIGO() {
		return CODIGO;
	}


	public void setCODIGO(String cODIGO) {
		CODIGO = cODIGO;
	}


	public String getNOME() {
		return NOME;
	}


	public void setNOME(String nOME) {
		NOME = nOME;
	}


	public String getCLASS() {
		return CLASS;
	}


	public void setCLASS(String cLASS) {
		CLASS = cLASS;
	}


	public Float getPERIDEAL() {
		return PERIDEAL;
	}


	public void setPERIDEAL(Float pERIDEAL) {
		PERIDEAL = pERIDEAL;
	}


	public Float getPERREAL() {
		return PERREAL;
	}


	public void setPERREAL(Float pERREAL) {
		PERREAL = pERREAL;
	}


	public Float getPERCART() {
		return PERCART;
	}


	public void setPERCART(Float pERCART) {
		PERCART = pERCART;
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

				Class cls = Class.forName("br.com.brotolegal.savdatabase.metas.MetaVend");

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

	public int semaforo(String tipo){
		
		int retorno = 0;
		
		BigDecimal REAL;
		
		Double min = (PERIDEAL - (PERIDEAL * (0.10)));
				
		BigDecimal IDEAL = new BigDecimal(PERIDEAL);
		
		IDEAL = IDEAL.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		if (tipo.equals("R")){
			
			REAL = new BigDecimal(PERREAL);
			
		} else {
			
			REAL = new BigDecimal(PERCART);
			
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
