package br.com.brotolegal.savdatabase.dao;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class HelpDAO  {
	private SQLiteDatabase database;
	private String         arquivo;
	private ObjRegister registro;

	public HelpDAO(String arquivo){
		
		this.arquivo = arquivo;
		
		this.registro = App.dbuser.getObjeto(arquivo);
		
	}

	public void open() throws SQLException {
		database = App.dbuser.getWritableDatabase();
	}

	public void close() {
		App.dbuser.close();
	}
	
	public SQLiteDatabase getDataBase(){
		
		return database;
		
	}

	
	
	public void LoadTableFile(String file) throws Exception {

		try

		{
			
            registro.loadTableHelp(file);			

		}

		catch(Exception e){

			throw new Exception(e.getMessage());

		}
		
	}
	public List<String> getOrdemAll() throws Exception

	{

		List<String> retorno = new ArrayList<String>(); 
		
		try

		{
			
            retorno = registro.getOrdem();			

		}

		catch(Exception e){

			throw new Exception(e.getMessage());

		}
		
		
		return retorno;

	}


	public String getSelect(String ordem,Object[] values, Object[] aliasValues) throws Exception
	{

		String  where       = "";
		String  select      = ""; 
		String  filtro      = "";
		String  aliasFiltro = "";
		String  and         = "";

		try

		{


			if (registro.getHelpFiltro().size() != 0 ){


				int contador = 0;
				
				for (int x=0;x <  registro.getHelpFiltro().size();x++) {

					if (!registro.getHelpFiltro().get(x).getKeyValues()[0].isEmpty()){

						if (contador > 0){
	
							filtro += " and  ";
	
						}	
	
						filtro += " ( " + registro.getHelpFiltro().get(x).getFields()[0] + " =  '" + registro.getHelpFiltro().get(x).getKeyValues()[0] + "' ) ";
						
						contador++;
						
					}
				}
			}
			
			HelpParam param = registro.getHelp(ordem);

			
			if (aliasValues.length != 0){
				
				
				aliasFiltro = MessageFormat.format(param.getAliasWhere(), aliasValues) ;
				
			}

			
			if (values.length != 0) {

				where = MessageFormat.format(param.getWhere(), values) ;

			}	
			
			and = " and ";

			if (where.isEmpty() && (!filtro.isEmpty() || !aliasFiltro.isEmpty())) {
				
				where = " where ";
				
				and   = "";  
				
			} 
				

			if (!filtro.isEmpty()){
				
				where  += and + filtro;
				
				
				if (!aliasFiltro.isEmpty()){
					
					and   = " and ";
					
					
				}
				
			} 
			
				
			if (!aliasFiltro.isEmpty()){
				
			   where += and  + aliasFiltro;
			   
			}

			
			select = param.getSelect() + where + param.getOrdeby();


		}

		catch(Exception e){

			throw new Exception(e.getMessage());

		}


		return select;

	}

	public List<HelpFiltro> getHelpFiltro(){
		
		return registro.getHelpFiltro();
		
	}
	
	public HelpDefault getHelpDefault(Cursor cursor, String ordem) throws Exception

	{

		HelpDefault helpdefault = new HelpDefault(false,"","",new String[] {},new String[] {},"","","",""); 

		List<String[]> keys = new ArrayList<String[]>();
		

		try

		{

			HelpParam param = registro.getHelp(ordem);
			
			String[] linhas = registro.getHelpLinhas(cursor);
			
			helpdefault.setMensagem1(linhas[0]);
			
			helpdefault.setMensagem2(linhas[1]);
			
			helpdefault.setLetra(linhas[2]);
			
			helpdefault.setTexto1(linhas[3]);
			
			helpdefault.setTexto2(linhas[4]);
			
			
			helpdefault.setTextopesquisa(cursor.getString(cursor.getColumnIndex(param.getFieldtextopesquisa().toLowerCase())));

			for (String key : param.getFieldkey()){
				
			    keys.add(new String[] {key,cursor.getString(cursor.getColumnIndex(key.toLowerCase()))});	
				
			}
			
			if (keys.size() > 0){
				
				String afields[]  = new String[keys.size()];
				String avalues[]  = new String[keys.size()];
				
				
				int i = 0;
				
				for (String[] k : keys){
					
					afields[i] = k[0];
					avalues[i] = k[1];

					i++;
					
				}
				
				helpdefault.setFieldkey(afields);
				helpdefault.setValuekey(avalues);
				
				
			}

		}

		catch(Exception e){

			throw new Exception(e.getMessage());

		}
		
		
		return helpdefault;

	}

	
}
