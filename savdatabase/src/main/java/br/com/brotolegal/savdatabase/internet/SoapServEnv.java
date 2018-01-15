package br.com.brotolegal.savdatabase.internet;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class SoapServEnv extends SoapSerializationEnvelope 
  {    
	public SoapServEnv(int version) {
		super(version);      
		// TODO Auto-generated constructor stub   
		}    
	@Override   
	public void write(XmlSerializer writer)      
			throws IOException   {
		          writer.setPrefix("i", xsi);        
		          writer.setPrefix("d", xsd);        
		          writer.setPrefix("c", enc);        
		          writer.setPrefix("soapenv", env);        
		          writer.startTag(env, "Envelope");        
		          writer.startTag(env, "Header");        
		          writeHeader(writer);        
		          writer.endTag(env, "Header");        
		          writer.startTag(env, "Body");        
		          writeBody(writer);        
		          writer.endTag(env, "Body");        
		          writer.endTag(env, "Envelope");   
		    }
	}
	
  

