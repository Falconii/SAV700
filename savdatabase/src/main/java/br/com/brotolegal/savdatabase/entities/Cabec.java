package br.com.brotolegal.savdatabase.entities;


import br.com.brotolegal.savdatabase.database.ObjRegister;

public class Cabec  extends ObjRegister {

	protected String NRO;
	protected String CPROTHEUS;
	protected String STATUS;
	protected Float TOTAL;
	
	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.PedidoCabMb";

	public Cabec(){

		super(_OBJETO,"CABEC");

		loadColunas();
		
		InicializaFields();

	}
	
	
	
	
	public Cabec(String nRO, String cPROTHEUS,
			String sTATUS, Float tOTAL) {

		super(_OBJETO,"CABEC");

		NRO = nRO;
		CPROTHEUS = cPROTHEUS;
		STATUS = sTATUS;
		TOTAL = tOTAL;

		loadColunas();

	}




	@Override
	public void loadColunas() {

		_colunas.add("NRO");
		_colunas.add("CPROTHEUS");
		_colunas.add("STATUS");
		_colunas.add("TOTAL");
		
	}


}
