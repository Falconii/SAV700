package br.com.brotolegal.savdatabase.entities;

import android.database.Cursor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.dao.FileTable;
import br.com.brotolegal.savdatabase.dao.HelpFiltro;
import br.com.brotolegal.savdatabase.dao.HelpParam;
import br.com.brotolegal.savdatabase.database.ObjRegister;


public class Produto extends ObjRegister {

	protected String CODIGO;
	protected String GRUPO;
	protected String MARCA;
	protected String DESCRICAO;
	protected String ATIVO;
	protected String DUN;
	protected String EAN;
	protected Float PESOLIQUI;
	protected Float PESOBRUTO;
	protected Float CONVERSAO;
	protected String UM;
	protected Float MULTVENDA;
	protected String TIPOPEDIDO;
	protected String ORIGEM;
	protected Integer UNIDADE;


	protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.Produto";
	
	
	public Produto(){
		
		 super(_OBJETO,"PRODUTO");
		 
		 loadColunas();
		 
		 InicializaFields();
		 
	}


	public Produto(String CODIGO, String GRUPO, String MARCA, String DESCRICAO, String ATIVO, String DUN, String EAN, Float PESOLIQUI, Float PESOBRUTO, Float CONVERSAO, String UM, Float MULTVENDA, String TIPOPEDIDO, String ORIGEM, Integer UNIDADE) {

		super(_OBJETO,"PRODUTO");

		loadColunas();

		InicializaFields();

		this.CODIGO = CODIGO;
		this.GRUPO = GRUPO;
		this.MARCA = MARCA;
		this.DESCRICAO = DESCRICAO;
		this.ATIVO = ATIVO;
		this.DUN = DUN;
		this.EAN = EAN;
		this.PESOLIQUI = PESOLIQUI;
		this.PESOBRUTO = PESOBRUTO;
		this.CONVERSAO = CONVERSAO;
		this.UM = UM;
		this.MULTVENDA = MULTVENDA;
		this.TIPOPEDIDO = TIPOPEDIDO;
		this.ORIGEM = ORIGEM;
		this.UNIDADE = UNIDADE;
	}


	public String getCODIGO() {
		return CODIGO;
	}

	public void setCODIGO(String CODIGO) {
		this.CODIGO = CODIGO;
	}

	public String getGRUPO() {
		return GRUPO;
	}

	public void setGRUPO(String GRUPO) {
		this.GRUPO = GRUPO;
	}

	public String getMARCA() {
		return MARCA;
	}

	public void setMARCA(String MARCA) {
		this.MARCA = MARCA;
	}

	public String getDESCRICAO() {
		return DESCRICAO;
	}

	public void setDESCRICAO(String DESCRICAO) {
		this.DESCRICAO = DESCRICAO;
	}

	public String getATIVO() {
		return ATIVO;
	}

	public void setATIVO(String ATIVO) {
		this.ATIVO = ATIVO;
	}

	public String getDUN() {
		return DUN;
	}

	public void setDUN(String DUN) {
		this.DUN = DUN;
	}

	public String getEAN() {
		return EAN;
	}

	public void setEAN(String EAN) {
		this.EAN = EAN;
	}

	public Float getPESOLIQUI() {
		return PESOLIQUI;
	}

	public void setPESOLIQUI(Float PESOLIQUI) {
		this.PESOLIQUI = PESOLIQUI;
	}

	public Float getPESOBRUTO() {
		return PESOBRUTO;
	}

	public void setPESOBRUTO(Float PESOBRUTO) {
		this.PESOBRUTO = PESOBRUTO;
	}

	public Float getCONVERSAO() {
		return CONVERSAO;
	}

	public void setCONVERSAO(Float CONVERSAO) {
		this.CONVERSAO = CONVERSAO;
	}

	public String getUM() {
		return UM;
	}

	public void setUM(String UM) {
		this.UM = UM;
	}

	public Float getMULTVENDA() {
		return MULTVENDA;
	}

	public void setMULTVENDA(Float MULTVENDA) {
		this.MULTVENDA = MULTVENDA;
	}

	public String getTIPOPEDIDO() {
		return TIPOPEDIDO;
	}

	public void setTIPOPEDIDO(String TIPOPEDIDO) {
		this.TIPOPEDIDO = TIPOPEDIDO;
	}

	public String getORIGEM() {
		return ORIGEM;
	}

	public void setORIGEM(String ORIGEM) {
		this.ORIGEM = ORIGEM;
	}

	public Integer getUNIDADE() {
		return UNIDADE;
	}

	public void setUNIDADE(Integer UNIDADE) {
		this.UNIDADE = UNIDADE;
	}

	@Override
	public void loadHelp() {
		
		List<HelpParam> help;
		List<HelpFiltro> filtro;
		
		help = new ArrayList<HelpParam>();
		help.add(new HelpParam("DESCRIÇÃO" ,
				                "select produto.codigo as codigo,produto.descricao as descricao,produto.um as um,produto.grupo as grupo,produto.marca as marca,grupo.descricao as grudescricao,marca.descricao as mardescricao " +
				                "from produto " +
				                "inner join grupo on grupo.codigo = produto.grupo "	+
				                "inner join marca on marca.codigo = produto.marca " ,
				                "where produto.descricao like ''%{0}%'' "           ,
				                "order by produto.descricao",
				                "DESCRICAO",
				                new String[] {"CODIGO"},
								"",
								new String[]{}));
		help.add(new HelpParam("CODIGO" ,
				                "select produto.codigo as codigo,produto.descricao as descricao,produto.um as um,produto.grupo as grupo,produto.marca as marca,grupo.descricao as grudescricao,marca.descricao as mardescricao " +
				                "from produto " +
				                "inner join grupo on grupo.codigo = produto.grupo "	+
				                "inner join marca on marca.codigo = produto.marca " ,
				                "where produto.codigo like ''{0}%'' "    			  , 
				                "order by produto.codigo",
				                "CODIGO",
				                 new String[] {"CODIGO"},
				                 "",
								 new String[]{}));

		filtro    = new ArrayList<HelpFiltro>();
		//filtro.add(new HelpFiltro("GRUPO", new String[] {"GRUPO","CADASTRO DOS GRUPOS","N"} , "CODIGO", HelpInformation.HelpGrupo,new String[] {"produto.grupo"},new String[] {""}));
		//filtro.add(new HelpFiltro("MARCA", new String[] {"MARCA","CADASTRO DE MARCAS" ,"N"} , "CODIGO", HelpInformation.HelpMarca,new String[] {"produto.marca"},new String[] {""}));

		_fileTable.add(new FileTable("PRODUTO", help, filtro, null));

		
		help = new ArrayList<HelpParam>();
		help.add(new HelpParam("DESCRIÇÃO" ,
				                "select produto.codigo as codigo,produto.descricao as descricao,produto.um as um,produto.grupo as grupo,produto.marca as marca,grupo.descricao as grudescricao,marca.descricao as mardescricao,tabprecodet.prcven as preco " +
				                "from produto " +
				                "inner join tabprecodet on tabprecodet.produto = produto.codigo "	+		
				                "inner join grupo on grupo.codigo = produto.grupo "	+
				                "inner join marca on marca.codigo = produto.marca " ,
				                "where produto.descricao like ''%{0}%'' "           ,
				                "order by produto.descricao",
				                "DESCRICAO",
				                new String[] {"CODIGO"},
				                " tabprecodet.codigo = ''{0}'' and produto.tipopedido like ''%@{1}%'' ",
								new String[]{}));
		help.add(new HelpParam("CODIGO" ,
				                "select produto.codigo as codigo,produto.descricao as descricao,produto.um as um,produto.grupo as grupo,produto.marca as marca,grupo.descricao as grudescricao,marca.descricao as mardescricao,tabprecodet.prcven as preco " +
				                "from produto " +
				                "inner join tabprecodet on tabprecodet.produto = produto.codigo "	+		
				                "inner join grupo on grupo.codigo = produto.grupo "	+
				                "inner join marca on marca.codigo = produto.marca " ,
				                "where produto.codigo like ''{0}%'' "    			  , 
				                "order by produto.codigo",
				                "CODIGO",
				                 new String[] {"CODIGO"},
    			                " tabprecodet.codigo = ''{0}'' and produto.tipopedido like ''%@{1}%'' ",
								 new String[]{}));

		filtro    = new ArrayList<HelpFiltro>();
		//filtro.add(new HelpFiltro("GRUPO", new String[] {"GRUPO","CADASTRO DOS GRUPOS","N"} , "CODIGO", HelpInformation.HelpGrupo,new String[] {"produto.grupo"},new String[] {""}));
		//filtro.add(new HelpFiltro("MARCA", new String[] {"MARCA","CADASTRO DE MARCAS" ,"N"} , "CODIGO", HelpInformation.HelpMarca,new String[] {"produto.marca"},new String[] {""}));

		_fileTable.add(new FileTable("PRODUTOPRECO", help, filtro, null));

		loadTableHelp("PRODUTO");
		
	};
	
	@Override
	public String[] getHelpLinhas(Cursor cursor) {
		
		DecimalFormat format_04 = new DecimalFormat(",##0.0000");
		
		String[] retorno = {"", //linha1
							"", //linha2
							"", //letra
							"", //texto1
							""}; //texto2
		
		
		String linha1    = "";
		String linha2    = "";
		String letra     = "";
		String texto1    = "";
		String texto2    = "";
		
		try
		{
		
			linha1 = cursor.getString(cursor.getColumnIndex("codigo"))+"-"+cursor.getString(cursor.getColumnIndex("descricao"));
	
			linha2 = "Unid: "+cursor.getString(cursor.getColumnIndex("um"))+" Marca: "+cursor.getString(cursor.getColumnIndex("mardescricao"))+" Grupo: " + cursor.getString(cursor.getColumnIndex("grudescricao"));
			
			letra  = cursor.getString(cursor.getColumnIndex("grudescricao")).substring(0, 1);

		}catch(Exception e) {

			linha1 = e.getMessage();
			
		}
		
		
		retorno[0] = linha1;
		
		retorno[1] = linha2;
		
		retorno[2] = letra;
		
		retorno[3] = texto1;
		
		retorno[4] = texto2;
		
		return retorno;

		
	};

	@Override
	public void loadColunas() {
		
		_colunas = new ArrayList<String>();

		_colunas.add("CODIGO");
		_colunas.add("GRUPO");
		_colunas.add("MARCA");
		_colunas.add("DESCRICAO");
		_colunas.add("ATIVO");
		_colunas.add("DUN");
		_colunas.add("EAN");
		_colunas.add("PESOLIQUI");
		_colunas.add("PESOBRUTO");
		_colunas.add("CONVERSAO");
		_colunas.add("UM");
		_colunas.add("MULTVENDA");
		_colunas.add("TIPOPEDIDO");
		_colunas.add("ORIGEM");
		_colunas.add("UNIDADE");


	}

}
