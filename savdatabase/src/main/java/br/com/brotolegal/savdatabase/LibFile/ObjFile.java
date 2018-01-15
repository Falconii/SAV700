package br.com.brotolegal.savdatabase.LibFile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.dao.DAO;
import br.com.brotolegal.savdatabase.dao.DAODados;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionDicionario;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionInsertFast;

public class ObjFile {
	
	private String              path;
	private String              file;
	private List<Dicionario> 	dicionario;
	private Header0 header0;
	private Trailer0 trailer0;
	private Header1 header1;
	private Trailer1 trailer1;
	private List<String[]>      dados;
	private List<Header1>       listfiles;
	private int					numfile;
	private DAODados 			dao;
	
	public ObjFile(String path, String file) {

		this.path = path;
		this.file = file;
		
	}
	
	public List<Header1> getlistfiles(){
		
		return listfiles;
	
	}
	
	public List<String[]> getDados(){
		
		return dados;
		
	}
	
	public List<Dicionario> getDicionario(){
		
		return dicionario;
		
	}
	

	public void loadListFile()  throws Exception{

		try {
			
			File myFile = new File(path+file);
			
			FileInputStream fIn = new FileInputStream(myFile);
			
			BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
			
			String aDataRow = "";
			
			listfiles       =  new ArrayList<Header1>();
			
			
			while ((aDataRow = myReader.readLine()) != null) {

				aDataRow.replaceAll("\\n|\\r", " ");

				if (aDataRow.trim().length() == 0){

					continue;

				}

				switch (aDataRow.charAt(1)) {

				case '1':

					{
						String texto = (String) aDataRow.subSequence(1, aDataRow.length()-1);
						
						Header1 header = new Header1(texto.split("\\|"));
						
						listfiles.add(header);
					}	

					break;

				default:
					
					break;
					
				}
				
			}
			
			myReader.close();
			
		} catch (Exception e) {

			
			throw new Exception(e.getMessage());
			
		}
		
		

		
	}


	public boolean existFile(String values){
		
		boolean retorno = false;

		try {

			if (listfiles.size() == 0){
				
				return retorno;
			}
			
			for(Header1 hd  : listfiles) {
				
				if (hd.getARQUIVO().equals(values)) {
					
					retorno = true;
					
					break;
					
				}
				
			}
			
		} catch (Exception e) {

			
			return retorno = false;
			
		}
		
		
		   return retorno;
		
	}

	
	
	public void loadFileByName(String nome) {
		
		
	}
	
	public void loadFileByIndice(String indice) {
		
		
	}
	
	public boolean loadFile(String nome){
		
		Boolean Arquivo;
		
		try {
			
			File myFile = new File(path+file);
			
			FileInputStream fIn = new FileInputStream(myFile);
			
			BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
			
			String aDataRow = "";

			Boolean fim     = false;
			
			dicionario =  new ArrayList<Dicionario>();
			
			dados      =  new ArrayList<String[]>();
			
			Arquivo    =  false;
			
			while (((aDataRow = myReader.readLine()) != null) || !fim) {

				aDataRow.replaceAll("\\n|\\r", " ");

				if (aDataRow.trim().length() == 0){

					continue;

				}
				
				if (aDataRow.charAt(1) == '1') {  //filtro arquivo
					
					String fields[] = aDataRow.split("\\|");
					
					if (fields[3].equals(nome)) {
						
						Arquivo = true;
						
					}
					else {
						
						Arquivo = false;
					}
					
					
				}
				
				if ((aDataRow.charAt(1) == '1' || 
				     aDataRow.charAt(1) == '2' || 
				     aDataRow.charAt(1) == '3' || 
				     aDataRow.charAt(1) == '4') && !Arquivo ) {
					
					 continue;
					
				}
				
				switch (aDataRow.charAt(1)) {
				case '0':
					
					{
					
						String texto = (String) aDataRow.subSequence(1, aDataRow.length()-1);
						
						header0 = new Header0(texto.split("\\|"));
					
					}
					
					break;

				case '1':

					{
						String texto = (String) aDataRow.subSequence(1, aDataRow.length()-1);
						
						header1 = new Header1(texto.split("\\|"));
					}	
					break;

				case '2':
					{
						String texto = (String) aDataRow.subSequence(1, aDataRow.length()-1);
						
						dicionario.add(new Dicionario(texto.split("\\|")));
					}	
					break;
					
				case '3':
					{
						String texto = (String) aDataRow.subSequence(1, aDataRow.length()-1);
						
						dados.add(texto.split("\\|"));
						
					}	
					
					break;

				case '4':

					{
						String texto = (String) aDataRow.subSequence(1, aDataRow.length()-1);
						
						String[] hd = texto.split("\\|");
						
						trailer1 = new Trailer1(Integer.parseInt((hd[1])));
						
						fim = true;
						
					}	
					
					break;

				case '9':  //Nao usado Por enquanto

					{
						String texto = (String) aDataRow.subSequence(1, aDataRow.length()-1);
						
						String[] hd = texto.split("\\|");
						
						trailer0 = new Trailer0(Integer.parseInt((hd[1])));
						
					}	

					break;

				default:
					
					break;
					
				}
				
			}
			
			myReader.close();
			
		
	    } catch (Exception e) {


	    	StringWriter sw = new StringWriter();

	    	PrintWriter pw  = new PrintWriter(sw);

	    	e.printStackTrace(pw);

	    	Log.i("SAV",e.getMessage());
			
			return false;
			
		}
		
	    return true;
		
	}

	
	public void close(){
		
	   this.dicionario =  new ArrayList<Dicionario>();
		
	   this.dados      =  new ArrayList<String[]>();

	}
	
	public void saveToBD(Context context, String cuser, Boolean deleteAll) throws ExceptionInsertFast, Exception {
		
		 if (this.header1 == null){
			 
			 new Exception("Header do Arquivo Vazio !!!!");
		 }

		this.dao = new DAODados(header1.getARQUIVO());
		
		dao.open();
		try {

			dao.validaDicionario(header1.getARQUIVO(), getDicionario());

		} catch (Exception e){

			throw new Exception(e.getMessage());

		}
			
		try {

			dao.InsetFast(getDados(),getStringInsert(),deleteAll);

		} catch (Exception e){

			throw new Exception(e.getMessage());

		}

		
		dao.close();
			
	}
		
	public String getStringInsert(){
		String retorno = "INSERT OR REPLACE INTO "+header1.getARQUIVO()+"(";
		String virgula = "";
		Dicionario dicio;
		
		if (dicionario.size() == 0){
			
			return "";
			
		}
		
		
		for(int x = 0; x < dicionario.size() ; x++){
			
			dicio = dicionario.get(x);
			
			if (x > 0){
				
				virgula = "," ;
				
			}
			
			retorno +=  virgula + dicio.getCAMPO();
			
		}
		
		retorno += ") VALUES ( ";
		
		virgula = "";
		
		for (int x=0 ; x<dicionario.size() ; x++) {

			if (x > 0){
				
				virgula = "," ;
				
			}

			retorno += virgula + " ? ";
			
		}
		
		retorno += ")";
		
		return retorno;
		
	}
}

