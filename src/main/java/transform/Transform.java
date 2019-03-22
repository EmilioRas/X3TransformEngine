package main.java.transform;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;




public interface Transform {
	public void doXMLProcessingRes(Socket socket, 
			 String XML, String XSL, Map<String,String> xmlParam,
			OutputStream out, X3TransformWriter xml, Class<?> type) throws IOException;
	public void doXMLProcessing(Socket socket,String XSL, Map<String,String> xmlParam,
			 OutputStream out,X3TransformWriter xml, Class<?> type) throws IOException;
//	public void doXRabProcessing(String XRab, Map<String,String> xmlParam,
//			OutputStream out,X3TransformWriter xml, Class<?> type) throws IOException;
	public void setRoot(String root);
	
	public void processingXslXmlStreams(String XML, String XSL,Socket socket,String contentType) throws IOException;
}
