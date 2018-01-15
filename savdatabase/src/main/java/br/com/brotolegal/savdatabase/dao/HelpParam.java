package br.com.brotolegal.savdatabase.dao;

public class HelpParam {
	
	private String   ordem;
	private String   select;
	private String   where;
	private String   ordeby;
	private String   fieldtextopesquisa;
	private String[] fieldkey;
	private String   aliasWhere;
	private String[] aliasValues;
	
	
	public HelpParam(String ordem, String select, String where, String ordeby,
			String fieldtextopesquisa, String[] fieldkey, String aliasWhere,
			String[] aliasValues) {

		this.ordem = ordem;
		this.select = select;
		this.where = where;
		this.ordeby = ordeby;
		this.fieldtextopesquisa = fieldtextopesquisa;
		this.fieldkey = fieldkey;
		this.aliasWhere = aliasWhere;
		this.aliasValues = aliasValues;
	}


	public String getOrdem() {
		return ordem;
	}


	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}


	public String getSelect() {
		return select;
	}


	public void setSelect(String select) {
		this.select = select;
	}


	public String getWhere() {
		return where;
	}


	public void setWhere(String where) {
		this.where = where;
	}


	public String getOrdeby() {
		return ordeby;
	}


	public void setOrdeby(String ordeby) {
		this.ordeby = ordeby;
	}


	public String getFieldtextopesquisa() {
		return fieldtextopesquisa;
	}


	public void setFieldtextopesquisa(String fieldtextopesquisa) {
		this.fieldtextopesquisa = fieldtextopesquisa;
	}


	public String[] getFieldkey() {
		return fieldkey;
	}


	public void setFieldkey(String[] fieldkey) {
		this.fieldkey = fieldkey;
	}


	public String getAliasWhere() {
		return aliasWhere;
	}


	public void setAliasWhere(String aliasWhere) {
		this.aliasWhere = aliasWhere;
	}


	public String[] getAliasValues() {
		return aliasValues;
	}


	public void setAliasValues(String[] aliasValues) {
		this.aliasValues = aliasValues;
	}
	
	

}
