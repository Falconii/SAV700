package br.com.brotolegal.savdatabase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.entities.Politica;
import br.com.brotolegal.savdatabase.regrasdenegocio.ParamPolitica;


public class PoliticaDAO  extends DAO2 implements IDao2<Politica> {


	private final String TAG = "POLITICA";

	private String whereClausePrimary = " codigo = ? ";


	public PoliticaDAO() throws Exception {
		super("POLITICA","dbuser");
	}

	@Override
	public Politica insert(Politica obj) {
		Cursor cursor = null;

		ContentValues values = getContentValues(obj) ;

		try

		{

			long insertId = getDataBase().insert(obj.getFileName(), null, values);

			if (insertId != -1){

				cursor = getDataBase().query(obj.getFileName(), obj.getColumn(),whereClausePrimary,new String[] {obj.getCODIGO()},null, null, null);

				cursor.moveToFirst();

				obj =  cursorToObj(cursor);


			} else {

				obj = null;

			}

		}

		catch(Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return obj;

	}

	@Override
	public Integer Delete(String[] values) {

		int regs = 0;

		try {

			regs = getDataBase().delete(getRegistro().getFileName(), whereClausePrimary, new String[] {values[0]});

		} catch (Exception e) {

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return regs;


	}

	@Override
	public Boolean Update(Politica obj) {
		int retorno = 0;

		try {

			ContentValues values = getContentValues(obj);

			retorno = getDataBase().update(getRegistro().getFileName(), values, whereClausePrimary, new String[] {obj.getCODIGO()});

		} catch (Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}


		return retorno == 0 ? false : true;

	}



	@Override
	public Politica cursorToObj(Cursor cursor) {

		Politica obj = null;

		try {
			obj = new Politica(
					cursor.getString(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4),
					cursor.getString(5),
					cursor.getString(6),
					cursor.getFloat(7),
					cursor.getFloat(8),
					cursor.getString(9),
					cursor.getString(10),
					cursor.getString(11),
					cursor.getString(12),
					cursor.getString(13),
					cursor.getFloat(14)

					);

		} catch (Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}

		return obj;

	}

	@Override
	public List<Politica> getAll() {
		Cursor cursor = null;

		List<Politica> result = new ArrayList<>();

		try {

			cursor = getDataBase().rawQuery("select * from " + getRegistro().getFileName(), null);

			if (!cursor.moveToFirst()){

				result = new ArrayList<>();

			} else {

				while (!(cursor.isAfterLast())) {

					result.add(cursorToObj(cursor));

					cursor.moveToNext();

				}
			}


		}catch (Exception e) {

			Log.i(TAG, e.getMessage());


			new Exception(e.getMessage());

		}

		return result;

	}

	@Override
	public Politica seek(String[] values) {

		Politica obj = null;

		try {

			Cursor cursor = getDataBase().query(getRegistro().getFileName(), getAllColumns(),whereClausePrimary, new String[] {values[0]},null, null, null);

			if (cursor.moveToFirst())
			{

				obj =  cursorToObj(cursor);

			} else {

				obj = null;

			}
		} catch (Exception e){

			Log.i(TAG, e.getMessage());

			new Exception(e.getMessage());

		}


		return obj;

	}

	public Politica getPolitica(ParamPolitica param){

		String cCliente	= param.getCCLIENTE();
		String cLoja 	= param.getCLOJA();
		String cCanal 	= param.getCCANAL();
		String cMarca 	= param.getCMARCA();
		String cRegiao 	= param.getCREGIAO();
		String cGrupo 	= param.getCGRUPO();
		String cRede 	= param.getCREDE();
		String cProduto = param.getCPRODUTO();
		String cVende 	= param.getCVENDE();
		String cSuper 	= param.getCSUPER();
		String cGeren 	= param.getCGEREN();
		Float nQtd 		= param.getNQTD();
		Float nQtdGrupo = param.getNQTDGRUPO();
		Float nQtdCateg = param.getNQTDCATEG();

		Politica Retorno = new Politica();

		String cQuery = "";

		cQuery = "SELECT *  ";
		cQuery += "FROM POLITICA ";
		cQuery += "WHERE ((CODIGO = '" + cCliente + "' AND loja = '" + cLoja + "' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (CODIGO = '" + cCliente + "' AND loja = '" + cLoja + "' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (CODIGO = '" + cCliente + "' AND loja = '" + cLoja + "' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '" + cRegiao.trim() + "' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '" + cRegiao.trim() + "' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '" + cRegiao.trim() + "' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND rtrim(ltrim(regiao)) = '' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND rtrim(ltrim(regiao)) = '' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND rtrim(ltrim(regiao)) = '' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (rtrim(ltrim(canal))  = '' AND rtrim(ltrim(regiao)) = '" + cRegiao.trim() + "' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (rtrim(ltrim(canal))  = '' AND rtrim(ltrim(regiao)) = '" + cRegiao.trim() + "' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (rtrim(ltrim(canal))  = '' AND rtrim(ltrim(regiao)) = '" + cRegiao.trim() + "' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (rede   = '"+cRede+"' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (rede   = '"+cRede+"' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (rede   = '"+cRede+"' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (geren  = '"+cGeren+"' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (geren  = '"+cGeren+"' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (geren  = '"+cGeren+"' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (super  = '"+cSuper+"' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (super  = '"+cSuper+"' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (super  = '"+cSuper+"' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (vend   = '"+cVende+"' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (vend   = '"+cVende+"' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (vend   = '"+cVende+"' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "      ) ";
		cQuery += "AND TIPOPOL = '1' "; //Somente as políticas comerciais.
		cQuery += "ORDER BY CODIGO DESC, canal DESC, regiao DESC, produto DESC,marca DESC,grupo DESC, vlrmin DESC  ";

		Log.i("SQL",cQuery);

		Log.i("SQL",cQuery);

		try {


			Cursor cursor = getDataBase().rawQuery(cQuery, null);

			if (!cursor.moveToFirst()){



			} else {

//				while (!(cursor.isAfterLast())) {
//
//					Retorno = cursorToObj(cursor);
//
//					cursor.moveToNext();
//
//				}

				Retorno = cursorToObj(cursor);
			}


		} catch (Exception e){

			new Exception(e.getMessage());

		}


		return Retorno;

	}

	public Politica getDNA(ParamPolitica param){

		String cCliente	= param.getCCLIENTE();
		String cLoja 	= param.getCLOJA();
		String cCanal 	= param.getCCANAL();
		String cMarca 	= param.getCMARCA();
		String cRegiao 	= param.getCREGIAO();
		String cGrupo 	= param.getCGRUPO();
		String cRede 	= param.getCREDE();
		String cProduto = param.getCPRODUTO();
		String cVende 	= param.getCVENDE();
		String cSuper 	= param.getCSUPER();
		String cGeren 	= param.getCGEREN();
		Float nQtd 		= param.getNQTD();
		Float nQtdGrupo = param.getNQTDGRUPO();
		Float nQtdCateg = param.getNQTDCATEG();

		Politica Retorno = new Politica();

		String cQuery = "";

		cQuery = "SELECT *  ";
		cQuery += "FROM POLITICA ";
		cQuery += "WHERE ((CODIGO = '" + cCliente + "' AND loja = '" + cLoja + "' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (CODIGO = '" + cCliente + "' AND loja = '" + cLoja + "' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (CODIGO = '" + cCliente + "' AND loja = '" + cLoja + "' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '" + cRegiao + "' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '" + cRegiao + "' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '" + cRegiao + "' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '      ' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '      ' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '" + cCanal + "' AND regiao = '      ' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '      ' AND regiao = '" + cRegiao + "' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (canal  = '      ' AND regiao = '" + cRegiao + "' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (canal  = '      ' AND regiao = '" + cRegiao + "' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (rede   = '"+cRede+"' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (rede   = '"+cRede+"' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (rede   = '"+cRede+"' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (geren  = '"+cGeren+"' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (geren  = '"+cGeren+"' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (geren  = '"+cGeren+"' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (super  = '"+cSuper+"' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (super  = '"+cSuper+"' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (super  = '"+cSuper+"' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (vend   = '"+cVende+"' AND produto = '" + cProduto + "' AND vlrmin <= " + String.valueOf(nQtd) + ") ";
		cQuery += "OR     (vend   = '"+cVende+"' AND grupo = '" + cGrupo + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "OR     (vend   = '"+cVende+"' AND marca = '" + cMarca + "' AND vlrmin <= " + String.valueOf(nQtdGrupo) + ") ";
		cQuery += "      ) ";
		cQuery += "AND TIPOPOL = '2' "; //Somente DNA
		cQuery += "ORDER BY CODIGO DESC, canal DESC, regiao DESC, produto DESC, marca DESC, grupo DESC, vlrmin DESC LIMIT 1";

		Log.i("SQL",cQuery);

		try {

			Cursor cursor = getDataBase().rawQuery(cQuery, null);

			if (!cursor.moveToFirst()){


			} else {
				while (!(cursor.isAfterLast())) {

					Retorno = cursorToObj(cursor);

					cursor.moveToNext();

				}
			}


		} catch (Exception e){

			new Exception(e.getMessage());

		}


		return Retorno;

	}

}
