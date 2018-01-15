package br.com.brotolegal.savdatabase.database;

import android.content.Context;

public class getDBHelper {
	
	public static DBHelper dbhelper;
	
	
	public getDBHelper(Context context, String cUser){
		
		dbhelper = new DBHelper(context,cUser);
		
	}

}
