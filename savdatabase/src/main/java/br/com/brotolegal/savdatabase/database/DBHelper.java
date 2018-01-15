package br.com.brotolegal.savdatabase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Acordo;
import br.com.brotolegal.savdatabase.entities.Canal;
import br.com.brotolegal.savdatabase.entities.CondPagto;
import br.com.brotolegal.savdatabase.entities.Contrato;
import br.com.brotolegal.savdatabase.entities.Grupo;
import br.com.brotolegal.savdatabase.entities.Marca;
import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.Produto;
import br.com.brotolegal.savdatabase.entities.Rede;
import br.com.brotolegal.savdatabase.entities.TabPrecoCabec;
import br.com.brotolegal.savdatabase.entities.TabPrecoDet;
import br.com.brotolegal.savdatabase.entities.Verba;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionDicionario;


public class DBHelper extends SQLiteOpenHelper{
	
	
	  /* Data Base */
	  private static final int           DATABASE_VERSION = 0;
      private static final String        DATABASE_NAME    = "brotolegal";
      private static       String        DATABASE_PATH    = Environment.getExternalStorageDirectory().getPath();
      										//super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + DATABASE_NAME, null, DATABASE_VERSION);
      
      List<Tabela> lsTabelas = new ArrayList<Tabela>();
      List<ObjRegister> lsTables = new ArrayList<ObjRegister>();
	  

	
  	public DBHelper(Context context,String user) {

  		   super(context, DATABASE_PATH + "/SAV/" + user + "/" + DATABASE_NAME, null, DATABASE_VERSION);

		   lsTabelas.add(new Tabela(new int[] {0}	, "", new Rede()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new Canal()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new TabPrecoCabec()));
		   lsTabelas.add(new Tabela(new int[] {0,1}	, "", new TabPrecoDet()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new CondPagto()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new Produto()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new Grupo()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new Marca()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new PedCabTvs()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new PedDetTvs()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new Contrato()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new PedidoCabMb()));
		   lsTabelas.add(new Tabela(new int[] {0,1}	, "", new PedidoDetMb()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new Verba()));
		   lsTabelas.add(new Tabela(new int[] {0}	, "", new Acordo()));

  	
  	}
  	

    @Override
	public void onCreate(SQLiteDatabase db) {

    	
    	try {
	    	for (Tabela tabela : lsTabelas) {
	    		
	    		db.execSQL(tabela.getCREATESTRING());
				
			}
	    	
    	} catch ( Exception e) {
			
    		Log.i("SAV", e.getMessage());
    		
		}
	
    }
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "";
		
    	try {
	    	for (Tabela tabela : lsTabelas) {
	    		
	    		sql = "DROP TABLE IF EXISTS " + tabela.getNOME();
	    		
	    		db.execSQL(sql);
				
			}
	    	
    	} catch ( Exception e) {
			
    		Log.i("SAV", e.getMessage());
    		
		}

		onCreate(db);
		
	}

	public String[]  getColunas(String arquivo) throws Exception{
		
		String[] retorno = null;
		
		try {
			
			for (Tabela tab : lsTabelas){
				
				if (tab.getNOME().equals(arquivo)){
				
					retorno = tab.getOBJ().getColumn();
					
				}
				
				
			}
			
			
		} catch (Exception e) {
		
		    throw new ExceptionDicionario(e.getMessage());
		
		}
		
	    return retorno ;
	}
	    

	public ObjRegister getObjeto(String arquivo){
		
		ObjRegister obj = null;
		
		for (Tabela tab : lsTabelas){
			
			if (tab.getNOME().equalsIgnoreCase(arquivo)){
				
				obj = tab.getOBJ();
				
				break;
				
			}
			
		}
		
		return obj;
		
	}
	
	
	//inner class
	
	private class Tabela {
		
		private  String       NOME;
		private  String       CREATESTRING;
		private ObjRegister OBJ;
		
		public Tabela(int[] chaves, String complemento,ObjRegister obj){
			
			NOME          = obj._FILENAME;
			
			OBJ           = obj;
			
			CREATESTRING  = OBJ.getCreateString(chaves, complemento);
			
		}

		public String getNOME() {
			return NOME;
		}

		public void setNOME(String nOME) {
			NOME = nOME;
		}

		public String getCREATESTRING() {
			
			return CREATESTRING;
					
		}

		public ObjRegister getOBJ(){
			
			return OBJ;
			
		}
		
	}
	

}
