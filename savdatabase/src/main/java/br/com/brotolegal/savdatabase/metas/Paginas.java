package br.com.brotolegal.savdatabase.metas;

import java.util.ArrayList;
import java.util.List;

public class Paginas {
	
	private List<Object> lsLista;
	private  int atual;
	
	
	public Paginas() {
	
		atual = -1;
		
		lsLista = new ArrayList<Object>();
		
	}
	
	public void add(Anterior ant){
		
		lsLista.add(ant);
		
		atual = lsLista.size()-1;
		
	}
	
	
	public void remove(int pag){
		
		if ((pag > 0) && (pag < lsLista.size() ) ) {
			
			lsLista.remove(pag);
			
		}
		
		
	}
	
	
	public  Anterior getAtual(){
		
		Anterior retorno = null;
		
		if (lsLista.isEmpty()) {
			
			return retorno;
			
		}
		
		 
	   retorno = (Anterior) lsLista.get(lsLista.size()-1);
			 
		
		return retorno;
		
		
	}
	
	
	public void goBack(){
		
		String erro;
		
		if ((lsLista.size()) > 1 )  {
			
			try {
			
			lsLista.remove(lsLista.size() - 1);
			
			atual = lsLista.size() - 1;
			
			}
			
			catch (UnsupportedOperationException E ) {
				
				
					erro = E.getMessage();
					
			       }
			catch (IndexOutOfBoundsException E) {
				
				   erro = E.getMessage();
				
			}
			
		}
		
	}
	

	public void clear(){
		
		if (lsLista.isEmpty() || lsLista.size() == 1){
			
			return;
			
		}
		
		while  ( (lsLista.size() >  1) ) {
			
			lsLista.remove(lsLista.size()-1);
			
		}
		
		atual = 0;
		
	}


}
