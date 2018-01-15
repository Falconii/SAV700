package br.com.brotolegal.savdatabase.database;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.config.MaskFormatter;
import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;

public abstract class ObjRegister {

	private String TAG = "OBJREGISTER";
	public static final String _string    = "java.lang.String";
	public static final String _float     = "java.lang.Float";
	public static final String _long      = "java.lang.Long";
	public static final String _integer   = "java.lang.Integer";

	protected String _OBJETO = "";
	protected String _FILENAME = "";
	protected List<String>      _colunas    = new ArrayList<String>();
	protected List<HelpParam>   _help       = new ArrayList<HelpParam>();
	protected List<HelpFiltro>  _filtro     = new ArrayList<HelpFiltro>();
	protected List<Boolean>     _isValid    = new ArrayList<Boolean>();
	protected List<FileTable>  _fileTable   = new ArrayList<FileTable>();

	public ObjRegister(String obj, String filename) {

		_OBJETO = obj;

		_FILENAME = filename;

		loadHelp();

	}

	public String getFileName(){

		return _FILENAME;

	}
	protected void setColunas(List<String> col) {

		this._colunas = col;

	}


	public String[] getColumn(){

		return _colunas.toArray( new String[0]);

	}

	public int getIndiceByName(String nome){

		int retorno = -1;

		try {
			Class cls = Class.forName(_OBJETO);

			Field fieldlist[] = cls.getDeclaredFields();

			for (int i = 0; i < fieldlist.length; i++) {

				Field fld = fieldlist[i];

				String fieldname = fld.getName().toUpperCase();

				if (fieldname.equals(nome.toUpperCase())){

					retorno = i;
				}
			}
		}
		catch (Throwable e) {

			retorno = -1;

		}

		return retorno;


	}


	public int getIndiceByNameColunas(String nome){

		int retorno = -1;

		try {

			for (int i = 0; i < _colunas.size(); i++) {

				if (_colunas.get(i).equals(nome.toUpperCase())){

					retorno = i;
				}
			}
		}
		catch (Throwable e) {

			retorno = -1;

		}

		return retorno;


	}

	public String getTypeByName(String nome){

		String retorno = "null";

		try {

			Class cls = Class.forName(_OBJETO);

			Field fieldlist[] = cls.getDeclaredFields();

			for (int i = 0; i < fieldlist.length; i++) {

				Field fld = fieldlist[i];

				String fieldname = fld.getName().toUpperCase();

				if (fieldname.equals(nome.toUpperCase())){

					retorno = fld.getType().getName();

				}

			}
		}

		catch (Throwable e) {

			retorno = "null";

		}

		return retorno;


	}

	public String getCreateString(int[] idKey, String complementoKey){

		int indice;

		String linha      = "";
		String primarykey = "";
		String retorno    = "create table "+_FILENAME+" (";

		try {

			Class cls = Class.forName(_OBJETO);

			Field fieldlist[] = cls.getDeclaredFields();

			/* monta chave primaria */

			if (idKey != null){

				primarykey = "primary key (";

				for (int i = 0; i < idKey.length; i++) {

					String fieldname = _colunas.get(idKey[i]).toLowerCase();

					if (i > 0){

						primarykey +=  ", ";
					}

					primarykey +=  fieldname;

					if (i == (idKey.length-1)){

						primarykey +=  ") ";

					}

				}


			} else {

				primarykey = "";

			}


			for (int i = 0; i < _colunas.size(); i++) {

				indice = getIndiceByName(_colunas.get(i));

				Field fld = fieldlist[indice];

				String fieldname = fld.getName();

				String type = fld.getType().getName();

				String tipo = " text ";

				String defaultValue = " default '' ";

				if (!(fieldname.charAt(0) == '_')) {

					linha  += fld.getName().toLowerCase() ;


					if (type.equals(_string)){

						tipo = " text ";

						defaultValue = " default '' ";

					}

					if (type.equals(_float)){

						tipo = " float ";

						defaultValue = " default 0 ";

					}

					if (type.equals(_long)){

						tipo = " long ";

						defaultValue = " default 0 ";

					}

					if (type.equals(_integer)){

						tipo = " integer ";

						defaultValue = " default 0 ";

					}

					//linha += tipo + " not null ";

					linha += tipo + defaultValue;

					if (primarykey.contains(fld.getName().toLowerCase())){

						linha += " not null ";

					}

					if (i == 0){

						linha += complementoKey;

					}
					if (i !=  (_colunas.size()-1)){

						linha += " , ";

					}

				}
			}

			//adiciona primary key

			if (!primarykey.isEmpty()){

				linha += ",";

				linha += primarykey;

			}

			//adiciona indices secundários

			if (!complementoKey.isEmpty()) {

				linha += ",";

				linha += complementoKey;


			}

			linha += " ) ";


		}

		catch (Throwable e) {

			linha = "";

		}

		retorno += linha;

		return retorno;
	}

	public String getInsertString(){


		String linha = "";

		String retorno = "insert or replace into "+_FILENAME+" (";

		try {

			//campos
			for (int i = 0; i < _colunas.size(); i++) {

				if (i > 0){

					linha += ",";

				}

				linha  += _colunas.get(i) ;

			}

			linha += " ) values( ";
			// ?
			for (int i = 0; i < _colunas.size(); i++) {

				if (i > 0){

					linha += ",";

				}

				linha  += " ? " ;

			}

			linha += " ) ";

		}

		catch (Throwable e) {

			linha = "";

		}

		retorno += linha;

		return retorno;
	}

	/* Define a sequencia dos campos que serao criados no arquivo */


	public  void setFieldByName(String name, Object value){

		String type = getTypeByName(name);

		if (getIndiceByName(name) != -1){

			try {

				Class cls = Class.forName(_OBJETO);

				Field fieldlist[] = cls.getDeclaredFields();

				for (int i = 0; i < fieldlist.length; i++) {

					Field fld = fieldlist[i];

					String fieldname = fld.getName();

					fld.setAccessible(true);

					if (fieldname.equals(name)){

						try {

							if (type.equals(_string)){

								fld.set(this, (String )value) ;

								break;

							}

							if (type.equals(_float)){

								fld.set(this,(Float) value);

								break;

							}

							if (type.equals(_long)){

								fld.set(this,(Long) value);

								break;

							}


							if (type.equals(_integer)){

								fld.set(this,(Integer) value);

								break;

							}

						}
						catch (IllegalAccessException e) {

							Log.i("SAV", e.getMessage());

						}

					}
				}
			}
			catch (Throwable e) {

				//silencia o erro  

			}

		}

	}


	public Object getFieldByName(String name){

		Object retorno = null;

		String type = getTypeByName(name);

		if (getIndiceByName(name) != -1){

			try {

				Class cls = Class.forName(_OBJETO);

				Field fieldlist[] = cls.getDeclaredFields();


				for (int i = 0; i < fieldlist.length; i++) {

					Field fld = fieldlist[i];

					fld.setAccessible(true);

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

							if (type.equals(_integer)){

								retorno = (Integer) fld.get(this);

							}

						}

						catch (IllegalAccessException x) {

							retorno = null;

						}

					}

				}
			}

			catch (Throwable e) {

				retorno = null;

			}
		}


		return retorno;
	}


	public String[] getHelpLinhas(Cursor cursor){

		String[] retorno = {"",""} ;

		return retorno;

	}


	protected void loadTableHelp(){

		_fileTable    = new ArrayList<FileTable>();

	}


	public void loadTableHelp(String table){

		for (FileTable ft : _fileTable){

			if (ft.getID().equals(table)){

				_help   = ft.getParametros();

				_filtro = ft.getFiltros();

				break;

			}
		}


	}



	public   void loadHelp(){

		_help    = new ArrayList<HelpParam>();

		_filtro  = new ArrayList<HelpFiltro>();


	}

	public HelpParam getHelp(String ordem){

		HelpParam helpparam = null;

		for( HelpParam param : _help){

			if (param.getOrdem().equals(ordem)){

				helpparam = param;

				break;

			}

		}

		return helpparam;

	}


	public  List<HelpFiltro> getHelpFiltro(){

		return _filtro;

	}

	public  List<String> getOrdem(){

		List<String> retorno = new ArrayList<>();

		for ( HelpParam param : _help){

			retorno.add(param.getOrdem());
		}

		return retorno;

	}


	protected void InicializaFields(){


		String type;

		for ( String name : _colunas){

			if (getIndiceByName(name) != -1){

				try {

					Class cls = Class.forName(_OBJETO);

					Field fieldlist[] = cls.getDeclaredFields();

					type = getTypeByName(name);

					for (int i = 0; i < fieldlist.length; i++) {

						Field fld = fieldlist[i];

						String fieldname = fld.getName();

						fld.setAccessible(true);

						if (fieldname.equals(name)){

							try {

								if (type.equals(_string)){

									fld.set(this, "") ;

									break;

								}

								if (type.equals(_float)){

									fld.set(this,0f);

									break;

								}

								if (type.equals(_long)){

									fld.set(this,0f);

									break;

								}


								if (type.equals(_integer)){

									fld.set(this,0);

									break;

								}

							}
							catch (IllegalAccessException e) {

								Log.i(TAG, e.getMessage());

							}

						}
					}
				}
				catch (Throwable e) {

					Log.i(TAG, e.getMessage());

				}

			}

		}


	}


	public String[] getArrayStringValues(){

		String[] retorno = new String[_colunas.size()];

		String type   = "";

		int    indice = 0;

		for (int x=0; x < retorno.length ; x++){

			retorno[x] = "";

		}


		indice = -1;

		for ( String name : _colunas){

			indice++;

			if (getIndiceByName(name) != -1){

				try {

					Class cls = Class.forName(_OBJETO);

					Field fieldlist[] = cls.getDeclaredFields();

					type = getTypeByName(name);

					for (int i = 0; i < fieldlist.length; i++) {

						Field fld = fieldlist[i];

						String fieldname = fld.getName();

						fld.setAccessible(true);

						if (fieldname.equals(name)){

							try {

								if (type.equals(_string)){

									if (!((String) getFieldByName(name) == null) )
									{
										retorno[indice] = (String) getFieldByName(name);
									}

									break;

								}

								if (type.equals(_float)){

									retorno[indice] = String.valueOf((Float) getFieldByName(name));

									break;

								}

								if (type.equals(_long)){

									retorno[indice] = String.valueOf((Long) getFieldByName(name));

									break;

								}


								if (type.equals(_integer)){

    								retorno[indice] = String.valueOf((Integer) getFieldByName(name));

				 					break;

								}

							}
							catch (Exception e) {

								Log.i(TAG, e.getMessage());

							}

						}

					}
				}
				catch (Throwable e) {

					Log.i(TAG, e.getMessage());

				}

			}

		}

		return retorno;
	}

	protected String format(String pattern, Object value) {

		MaskFormatter mask;
		try {
			mask = new MaskFormatter(pattern);
			return mask.valueToString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public Boolean isValidoByName(String field) {

		Boolean retorno = true;

		int     indice  = 0;

		for(String campo : _colunas){

			if (campo.equals(field)){

				retorno = _isValid.get(indice);

				break;

			}

			indice++;

		}

		return retorno;

	}


	public Boolean setValidoByName(String field,Boolean ok) {

		Boolean retorno = true;

		int     indice  = 0;

		for(String campo : _colunas){

			if (campo.equals(field)){

				_isValid.set(indice,ok);

				break;

			}

			indice++;

		}


		return retorno;

	}

	abstract public void loadColunas();





}
