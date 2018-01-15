package br.com.brotolegal.savdatabase.md5;

import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import br.com.brotolegal.savdatabase.app.App;

public class CheckMD5 {

	private static char[] hexDigits = "0123456789abcdef".toCharArray();

	public static String md5(InputStream is) throws IOException {
		String md5 = "";
		try {
			byte[] bytes = new byte[4096];
			int read = 0;
			MessageDigest digest = MessageDigest.getInstance("MD5");
			while ((read = is.read(bytes)) != -1) {
				digest.update(bytes, 0, read);
			}
			byte[] messageDigest = digest.digest();
			StringBuilder sb = new StringBuilder(32);
			for (byte b : messageDigest) {
				sb.append(hexDigits[(b >> 4) & 0x0f]);
				sb.append(hexDigits[b & 0x0f]);
			}
			md5 = sb.toString();
		} catch (Exception e) {
			Log.i("SAV",e.getMessage());
		}
		return md5;
  }
	

	public Boolean checkFile(String md5Original,String filename) throws Exception{

		String md5Origin  = md5Original;//original file's md5 checksum
		String filePath   = App.BasePath + "/" + App.AppPath + "/" + App.user.getCOD() + "/" + filename;

		Boolean retorno = false;

		try {

			FileInputStream fis = new FileInputStream(filePath);
			
			String md5Checksum = md5(fis);
			
			if (md5Checksum.equals(md5Origin)) {
				
				retorno = true;
				
			}
		} catch (IOException e){
			
			throw new Exception(e.getMessage());
			
		} catch (Exception e) {

			throw new Exception(e.getMessage());
		}

		return retorno;
	}
}