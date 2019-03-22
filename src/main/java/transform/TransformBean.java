package main.java.transform;


//import it.er.xrab.ResolverRab;
//import it.er.xrab.XRabProcExec;
//import it.er.xrab.XRabResolver;
//import it.er.xrab.XRabTransformer;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class TransformBean extends X3Basic implements Transform{

	private String root;
	
	private String webContentPath;
	
	private Map<String,String> siteWebContentPathMap = new HashMap<String, String>();
	
	

//	private static Logger log = LogManager.getLogger(TransformBean.class);
	
	public String getWebContentPath() {
		return webContentPath;
	}

	public void setWebContentPath(String webContentPath) {
		this.webContentPath = webContentPath;
	}

	
	
	public void close(){
//		log.info("Destroy transfromSty bean");
	}
	
	public void start(){
//		log.info("Init transfromSty bean");
	}
	@Override
	public void doXMLProcessingRes(Socket socket, String XML, String XSL, Map<String,String> xmlParam,
			OutputStream out, X3TransformWriter xml, Class<?> type) throws IOException{
		
		String xmlQuery = this.paramFromMap(xmlParam);
		if (!(xmlQuery==null))
			if (!xmlQuery.toString().equals("?"))
				XML += xmlQuery;
		InputStream xslt = null;
		Resolver r = null;
		InputStream xmlSource = null;
		try {
			
			xslt = new FileInputStream(webContentPath+XSL);
				r = new Resolver(webContentPath);
				r.setRoot(root);
			
				xmlSource = new ByteArrayInputStream(xml.writeTo(xml, type).toByteArray());
				
				TransformerFactory factory = TransformerFactory.newInstance();
				factory.setURIResolver(r);
				Transformer transformer = factory.newTransformer(new StreamSource(xslt));
				transformer.transform(new StreamSource(xmlSource), new StreamResult(out));
				out.flush();
		} catch (Exception e){
			//out.write(res.getBufferSize());
//			log.error("Error in transform", e);
		} finally {
			if (xslt != null)
				xslt.close();
			if (xmlSource != null)
				xmlSource.close();
		}
	}

	

	@Override
	public void doXMLProcessing(Socket socket, String XSL, Map<String, String> xmlParam,
			 OutputStream out,X3TransformWriter xml, Class<?> type) throws IOException {
		/*
		String xmlQuery = this.paramFromMap(xmlParam);
		if (!(xmlQuery==null))
			if (!xmlQuery.toString().equals("?"))
				XML += xmlQuery;
		*/
		InputStream xslt = null;
		Resolver r = null;
		InputStream xmlSource = null;
		try {
			String doctype = "<!DOCTYPE html>\n";
			out.write(doctype.getBytes());
			xslt = new FileInputStream(webContentPath+XSL);
			r = new Resolver(webContentPath);
			r.setRoot(root);
			
			
			xmlSource = new ByteArrayInputStream(xml.writeTo(xml, type).toByteArray());
			
//			TransformerFactory factory =  TransformerFactoryImpl.newInstance();       
//			factory.setURIResolver(r);
//			Templates template = factory.newTemplates(new StreamSource(xslt));
//			Transformer transformer = template.newTransformer();
//			transformer.transform(new StreamSource(xmlSource), new StreamResult(out));
			out.flush();
		} catch (Exception e){
//			log.error("Error in transform", e.getCause());
		} finally {
			xslt.close();
			xmlSource.close();
		}
	}

//	@Override
//	public void doXRabProcessing(String XRab, Map<String, String> xmlParam,
//			OutputStream out, X3TransformWriter xml, Class<?> type) throws IOException {
//		InputStream xrab = new FileInputStream(webContentPath+XRab);
//		InputStream xmlSource = new ByteArrayInputStream(xml.writeTo(xml, type).toByteArray());
//		try {
//			XRabResolver r = new ResolverRab(webContentPath);
//			
//			XRabTransformer xrabElab = it.er.xrab.XRab.newIstance(); 
//			xrabElab.setURIResolver(r);
//			XRabProcExec proc = it.er.xrab.XRab.newTrasform(xrabElab, new StreamSource(xrab));
//			proc.transform(new StreamSource(xmlSource), new StreamResult(out));
//			} catch (Exception e){
//			out.write(e.getMessage().getBytes());
//			log.error("Error in XRAB transform", e.getCause());
//		} finally {
//			xrab.close();
//			xmlSource.close();
//		}
//	}

	@Override
	public void setRoot(String root) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingXslXmlStreams(String XML, String XSL,Socket socket,String contentType) throws IOException {
		InputStream xslt = null;
		InputStream xml = null;
		Resolver r = null;
		OutputStream out = socket.getOutputStream();
		try {
			xslt = new ByteArrayInputStream(XSL.getBytes());
			xml = new ByteArrayInputStream(XML.getBytes());
			r = new Resolver(".");
			r.setRoot(root);
			out.write("HTTP/1.1 200 OK\r\n".getBytes());
			out.write(("Content-Type: "+contentType+"\r\n").getBytes());
			//out.write(("Content-Length:"+ stb.toString().getBytes().length+"\r\n").getBytes());
			out.write("\r\n".getBytes());
//			TransformerFactory factory =  TransformerFactoryImpl.newInstance();       
//			factory.setURIResolver(r);
//			Templates template = factory.newTemplates(new StreamSource(xslt));
//			Transformer transformer = template.newTransformer();
//			transformer.transform(new StreamSource(xml), new StreamResult(out));
			out.flush();
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				xslt.close();
				xml.close();
				out.close();
			} catch (IOException io){
				io.printStackTrace();
			}
		}
	}
	
	
}
