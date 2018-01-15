package br.com.brotolegal.savdatabase.config;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class Parametros {
	
	List<String> namespace = new ArrayList<String>();
	List<String> url       = new ArrayList<String>();
	List<String> ip        = new ArrayList<String>();

	public Parametros(){
		
		String ipDNS;
		
		/* Usar esta string no browser
		 * http://189.109.60.186:9999/ws/WSBROTO.apw?WSDL
		 * http://201..77.183.249:9999/ws/WSBROTO.apw?WSDL
		 */
		
		//namespace.add("http://192.168.0.18:9999/ws/WSBROTO.PRW");
		//namespace.add("http://189.109.60.186:9999/ws/WSBROTO.PRW");
		//namespace.add("http://189.109.60.186:9999/ws/WSBROTO.PRW");

		//url.add("http://192.168.0.18:9999/ws/WSBROTO.APW");
		//url.add("http://189.109.60.186:9999/ws/WSBROTO.APW");
		//url.add("http://189.109.60.186:9999/ws/WSBROTO.APW");
		
		
		
		//201.77.183.249
		
		
		
		//ipDNS = Ip("web.brotolegal.com.br");
		
		ipDNS = "189.109.60.186";
		
		namespace.add("http://"+ipDNS+":9999/ws/WSBROTO.PRW");
		namespace.add("http://192.168.0.18:9999/ws/WSBROTO.PRW");
		namespace.add("http://201.77.183.249:9999/ws/WSBROTO.PRW");

		url.add("http://"+ipDNS+":9999/ws/WSBROTO.APW");
		url.add("http://192.168.0.18:9999/ws/WSBROTO.APW");
		url.add("http://201.77.183.249:9999/ws/WSBROTO.APW");
		
		

	}


	public String Ip(String hostname){
		
	    String ip;

	    try {
	    	
	         InetAddress ipaddress = InetAddress.getByName(hostname);
	      
	         ip = ipaddress.getHostAddress();
	         
	    } catch ( UnknownHostException e ) {
	    	
	         ip = "0.0.0.0";
	    	
	    }
	    
	    return ip;
	    
	}

	public List<String> getNamespace() {
		return namespace;
	}


	public List<String> getUrl() {
		return url;
	}


	public String getNameSpaceItem(int item){
		
		return namespace.get(item);
		
	}
	
	public String getUrlItem(int item){
		
		return url.get(item);
		
	}

	
	
}
