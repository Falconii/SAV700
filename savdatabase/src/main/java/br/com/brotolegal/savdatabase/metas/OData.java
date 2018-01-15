package br.com.brotolegal.savdatabase.metas;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Falconi
 */
public abstract class OData {
    
    private String OBJETO;
            
    public OData(String objeto){
        
        OBJETO = objeto;
        
    }
    
    
    public String[] getColumn(){
        
         List<String> retorno = new ArrayList<String>();
        
         try {  
                
                Class cls = Class.forName(OBJETO);

                Field fieldlist[] = cls.getDeclaredFields();

                for (int i = 0; i < fieldlist.length; i++) {  

                    Field fld = fieldlist[i];

                    String fieldname = fld.getName();

                    retorno.add(fieldname);

                    }
                }  
            
         catch (Throwable e) {
            
            System.out.println(e.getMessage());
            
         }
        
        return retorno.toArray( new String[0]);
        
        
    }
    
    public void setFieldByname(String name, Object value){
        
        final String _string = "java.lang.String";
        final String _float  = "java.lang.Float";
        final String _long   = "java.lang.Long";
        
        String type = getTypeByName(name);
        
        if (getIndiceByName(name) != -1){
         
            try {  
                
                Class cls = Class.forName(OBJETO);

                Field fieldlist[] = cls.getDeclaredFields();

                for (int i = 0; i < fieldlist.length; i++) {  

                    Field fld = fieldlist[i];

                    String fieldname = fld.getName();

                    if (fieldname.equals(name)){

                        try {

                            if (type.equals(_string)){

                                fld.set(this, (String)value) ;

                            }

                            if (type.equals(_float)){

                                fld.set(this,(Float) value);

                            }

                            if (type.equals(_long)){

                                fld.set(this,(Long) value);

                            }

                        }
                        catch (IllegalAccessException x) {
                            
                            x.printStackTrace();
                            
                        }

                    }
                }  
            }  
            catch (Throwable e) {
            
                 System.out.println(e.getMessage());
            
            }
        }


    }
    
    public Object getFieldByname(String name){
        
        final String _string = "java.lang.String";
        final String _float  = "java.lang.Float";
        final String _long   = "java.lang.Long";
        

        Object retorno = null;
        
        String type = getTypeByName(name);
        
        if (getIndiceByName(name) != -1){
         
            try {  
                
                Class cls = Class.forName(OBJETO);

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

                                retorno = (Float) fld.get(this);
                                
                            }

                            if (type.equals(_long)){

                                retorno = (Long) fld.get(this);

                            }

                        }
                        
                        catch (IllegalAccessException x) {
                            
                            x.printStackTrace();
                            
                        }

                    }
                }  
            }  
            catch (Throwable e) {
            
            System.out.println(e.getMessage());
            
            }
        }


        return retorno;
    }

    public int getIndiceByName(String nome){
        
        int retorno = -1;
        
        try {  
            Class cls = Class.forName(OBJETO);
        
            Field fieldlist[] = cls.getDeclaredFields();
            
            for (int i = 0; i < fieldlist.length; i++) {  
                
                Field fld = fieldlist[i];
                
                String fieldname = fld.getName();
                
                if (fieldname.equals(nome)){
                    
                    retorno = i;    

                }
            }  
        }  
        catch (Throwable e) {
            
            System.out.println(e.getMessage());
            
        } 
        
        return retorno;
        
        
    }
    
    public String getTypeByName(String nome){
        
        String retorno = "null";
        
        try {  
            
            Class cls = Class.forName(OBJETO);
        
            Field fieldlist[] = cls.getDeclaredFields();
            
            for (int i = 0; i < fieldlist.length; i++) {  
                
                Field fld = fieldlist[i];
                
                String fieldname = fld.getName();
                
                if (fieldname.equals(nome)){
                    
                    retorno = fld.getType().getName();    

                }
            }  
        }  
        catch (Throwable e) {
            
            System.out.println(e.getMessage());
            
        } 
        
        return retorno;
        
        
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

				Class cls = Class.forName(OBJETO);

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
    
    
}
