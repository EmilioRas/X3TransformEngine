package  transform;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import socket.ConnectionManage;



public class TransformGenerator extends X3Basic implements Runnable{
	
	@GeneratorEngineStart
	public TransformGenerator(Socket socket) throws  InstantiationException, IllegalAccessException, ClassNotFoundException{
		this.transform = new TransformBean();
		XTransform[] xt = this.transform.getClass().getAnnotationsByType(XTransform.class);
		for (XTransform xt1:xt){
			if (xt1.transformClass() != null && xt1.transformPackage() != null &&
					!xt1.transformClass().isEmpty() && !xt1.transformPackage().isEmpty()){
				this.transform = (Transform) Class.forName(xt1.transformPackage()+"."+xt1.transformClass()).newInstance();
				break;
			}
		}
		GeneratorRoot[] anntRoot = this.transform.getClass().getAnnotationsByType(GeneratorRoot.class);
		for (GeneratorRoot xt1:anntRoot){
			if (xt1.root() != null &&
					!xt1.root().isEmpty()){
				this.root = xt1.root();
				break;
			}
		}
		this.socket = socket;
	}
	
	@GeneratorEngineStart
	public TransformGenerator() throws  InstantiationException, IllegalAccessException, ClassNotFoundException{
		this.transform = new TransformBean();
		XTransform[] xt = this.transform.getClass().getAnnotationsByType(XTransform.class);
		for (XTransform xt1:xt){
			if (xt1.transformClass() != null && xt1.transformPackage() != null &&
					!xt1.transformClass().isEmpty() && !xt1.transformPackage().isEmpty()){
				this.transform = (Transform) Class.forName(xt1.transformPackage()+"."+xt1.transformClass()).newInstance();
				break;
			}
		}
		GeneratorRoot[] anntRoot = this.transform.getClass().getAnnotationsByType(GeneratorRoot.class);
		for (GeneratorRoot xt1:anntRoot){
			if (xt1.root() != null &&
					!xt1.root().isEmpty()){
				this.root = xt1.root();
				break;
			}
		}
	}

	private int chunked = 0;
	
	public void setChunked(int chunked) {
		this.chunked = chunked;
	}
	
	

	public int getChunked() {
		return chunked;
	}

	@XTransform
	private Transform transform;
	
	@GeneratorRoot
	private String root;
	
	private Socket socket;
	

	public Socket getSocket() {
		return socket;
	}

	public void setRoot(String root) {
		this.root = root;
	}

//	private static Logger log = LogManager.getLogger(TransformGenerator.class);
	
	public void close(){
//		log.info("Destroy transfromStyGenerator bean");
		transform = null;
	}
	
	public void start(){
//		log.info("Init transfromStyGenerator bean");
	}
	
	public void setTransform(Transform transform){
		this.transform = transform;
	}
	
	public Transform getTransform(){
		return this.transform;
	}
	
	public void doXMLProcessing(Socket socket, String XML, String XSL, Map<String,String> xmlParam,
			OutputStream out, X3TransformWriter xml, Class<?> type) throws IOException{
		transform.setRoot(this.root);
		transform.doXMLProcessingRes(socket,XML,XSL,xmlParam,out,xml,type);
	}
	
	public void doXMLProcessing(Socket socket,String XSL, Map<String,String> xmlParam,
			OutputStream out,X3TransformWriter xml, Class<?> type) throws IOException{
		transform.setRoot(this.root);
		transform.doXMLProcessing(socket,XSL,xmlParam,out,xml,type);
	}
	
//	public void doXRabProcessing(String XRab, Map<String,String> xmlParam,
//			OutputStream out,X3TransformWriter xml, Class<?> type) throws IOException{
//		transform.doXRabProcessing(XRab,xmlParam,out,xml,type);
//	}
	
	public void processingXslXmlStreams(String XML, String XSL,Socket socket,String contentType) throws IOException{
		transform.processingXslXmlStreams(XML, XSL, socket, contentType);
	}
	
	private String xmlRequest = "";

	
	
	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}



	public void setXslRequest(String xslRequest) {
		this.xslRequest = xslRequest;
	}



	public String getXmlRequest() {
		return xmlRequest;
	}



	public String getXslRequest() {
		return xslRequest;
	}

	private String xslRequest = "";
	
	
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	private String contentType;
	
	private String charset;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	private byte[] request;
	
	public byte[] getRequest() {
		return request;
	}

	public void setRequest(byte[] request) {
		this.request = request;
	}
	
	private byte[] response;
	
	public byte[] getResponse() {
		return response;
	}

	public void setResponse(byte[] response) {
		this.response = response;
	}
	
	private boolean passFlag;
	
	

	public boolean isPassFlag() {
		return passFlag;
	}

	public void setPassFlag(boolean passFlag) {
		this.passFlag = passFlag;
	}
	
	private String URL;
	
	private int port;
	
	private String protocol;
	
	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	private boolean protocolHttp;
	
	

	public boolean isProtocolHttp() {
		return protocolHttp;
	}

	public void setProtocolHttp(boolean protocolHttp) {
		this.protocolHttp = protocolHttp;
	}
	
	private boolean output;
	
	public boolean isOutput() {
		return output;
	}

	public void setOutput(boolean output) {
		this.output = output;
	}
	
	private String verb;
	
	private Map<String,String> headerFields;
	
	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public Map<String, String> getHeaderFields() {
		return headerFields;
	}

	public void setHeaderFields(Map<String, String> headerFields) {
		this.headerFields = headerFields;
	}
	
	private boolean connectMethod;
	
	public boolean isConnectMethod() {
		return connectMethod;
	}

	public void setConnectMethod(boolean connectMethod) {
		this.connectMethod = connectMethod;
	}
	
	private boolean checkPath;
	
	public boolean isCheckPath() {
		return checkPath;
	}

	public void setCheckPath(boolean checkPath) {
		this.checkPath = checkPath;
	}
	
	private boolean httpServer;
	
	public boolean isHttpServer() {
		return httpServer;
	}

	public void setHttpServer(boolean httpServer) {
		this.httpServer = httpServer;
	}

	@Override
	public void run() {
		OutputStream out = null;
		InputStream in = null;
		String header = null;
		StringBuffer inStb = null;
		int c = 0;
		synchronized (this) {
			try{
				System.out.println(". request ...");
				InetAddress bindto = ((InetSocketAddress)this.getSocket().getRemoteSocketAddress()).getAddress();
				String[] remoteHostNameIpv4 = bindto.toString().indexOf("/") != -1 ? bindto.toString().split("\\/") : null;
				
				this.getSocket().setSoTimeout(ConnectionManage.getProxytimeout());
			
//				this.getSocket().setKeepAlive(true);
//				if (!this.isPassFlag()) {
//					this.getSocket().setSoTimeout(1000);
//				} else {
//					this.getSocket().setSoTimeout(1500);
//				}
				in = this.getSocket().getInputStream();
				out = this.getSocket().getOutputStream();
				int len = 0;
				byte[] buffer = new byte[8];
				

				inStb =  new StringBuffer();
				//header
				header = new String();
				c = 0;
				
				
				
				System.out.println("Who is ...??? "+ bindto);
				if (remoteHostNameIpv4 != null && remoteHostNameIpv4.length == 1) {
					System.out.println("\t " + remoteHostNameIpv4[0]);
				}
				if (remoteHostNameIpv4 != null && remoteHostNameIpv4.length > 1) {
					System.out.println("\t " + remoteHostNameIpv4[1]);
				}
				
				if (!this.isPassFlag()) {
					out.write("HTTP/1.1 200 OK".getBytes());
					out.write(("Content-Type: " + this.getContentType()+ (this.getCharset() != null
							 && !this.getCharset().equals("none") ? "; charset=" + this.getCharset() +"\r\n" : "\r\n")).getBytes());
					out.flush();
					
					System.out.println("Chunked :" + this.chunked);
					while (len != -1  || (this.getChunked() != 0 && this.getChunked() > c)){
						
						len = in.read(buffer);
						inStb.append(new String(buffer,0,len));
						c++;
						header = inStb.toString();
						
//							if (len > 0 && header.matches(".{0}")){
//								System.out.println("end");
//								break;
//							}
						
					}
					
					System.out.println("end read ... read in buffer "+c+" times");
					System.out.println("X3--->");
					c = 0;
//					if (header != null && inStb != null &&
//							header.startsWith("GET") || header.startsWith("POST") || header.startsWith("PUT") || header.startsWith("DELETE")){
//						
//						if (inStb.toString().indexOf("\r\n") != -1) {
//							System.out.println(header);
//							String inStbBody = inStb.toString().substring(inStb.toString().indexOf("\r\n")+1);
//							if (inStbBody.contains("xml=")){
//								this.setXmlRequest( inStbBody.substring(inStb.toString().indexOf("xml=")+1) );
//							} else if (inStbBody.contains("xsl=")) {
//								this.setXmlRequest( inStbBody.substring(inStb.toString().indexOf("xml=")+1) );
//							}
//							
//							if (!this.getXmlRequest().isEmpty() && !this.getXslRequest().isEmpty()){
//								this.processingXslXmlStreams(this.getXmlRequest(), this.getXslRequest(), this.getSocket(), "text/html");
//								this.setXmlRequest( "" );
//								this.setXslRequest( "" );
//							}
//						} else {
//							System.out.println("Generic Request...");
//							System.out.println(header);
//						}
//						
						//reverse
						//by Methods
					
				} else {
					
					while (len != -1){
						
						len = in.read(buffer);
						inStb.append(new String(buffer,0,len));
						c++;
						header = inStb.toString();
						
//							if (len > 0 && header.matches(".{0}")){
//								System.out.println("end");
//								break;
//							}
						
					}
				}	
			} catch (SocketTimeoutException e) {
				System.out.println("Timeout . read "+c+" bytes");
			} catch (Exception e){
				System.err.println("Error in TransformGenerator");
				e.printStackTrace();
			} finally {
				
				System.out.println("end read ... read in buffer "+c+" times");
				System.out.println("X3--->");
				c = 0;
				if (header != null && inStb != null &&
						header.startsWith("GET") || header.startsWith("POST") || header.startsWith("PUT") || header.startsWith("DELETE")){
					//reverse
					//by Methods
					System.out.println("Crud Request...");
					System.out.println(header);
				} else {
					System.out.println("Generic Request...");
					System.out.println(header);
				}
				
				///////
				boolean legelMethods = false;
				boolean protocolHTTP = false;
				
				if (header != null) {
					protocolHTTP = this.parseRequest(header, !this.isHttpServer()); 
					
					///////
					
					legelMethods = (this.getVerb().equals("GET") ||
							this.getVerb().equals("POST") ||
							this.getVerb().equals("HEAD") || 
							this.getVerb().equals("OPTIONS") || 
							this.getVerb().equals("PUT") ||
							this.getVerb().equals("DELETE") ||
							this.getVerb().equals("TRACE") ? true : false );
				}
				
				if (!legelMethods && !this.isConnectMethod()) {
					this.setPassFlag(false);
					System.out.println("no pass . not legal method for this connection");
				} else if(this.isConnectMethod() && header != null) {
					this.setRequest(header.getBytes());
				} else if (legelMethods && header != null && !header.isEmpty()) {
					
					String[] hb = header.split("\r\n\r\n");
					if (hb.length == 2) {
						if (!this.isOutput() && hb[1].isEmpty()) {
							System.out.println("cannot set body request . this is empty");
						} else {
							this.setRequest(hb[1].getBytes());
							System.out.println("Set body request! Length "+ hb[1].length());
						}
					} else if (hb.length > 2) {
						System.out.println("Set body request for pass issue");
					} else if (hb.length == 1) {
						System.out.println("cannot se et body request . this is not body");
					}
				} 
				
				if (legelMethods && this.isPassFlag() && this.getVerb() != null && 
						!this.getVerb().isEmpty() && this.getURL() != null &&
						!this.getURL().isEmpty()) {
					System.out.println("Going to the Pass ...");
					if (this.getHeaderFields() == null || this.getHeaderFields().isEmpty()) {
						System.out.println("Header map empty or null ...");
					}
				} else if (!legelMethods && this.isPassFlag()) {
					System.out.println("Missing parameter . cannot going to the Pass ...");
					this.setPassFlag(false);
				}
				
				if (legelMethods && this.isHttpServer() && this.getVerb() != null && 
						!this.getVerb().isEmpty() && this.getURL() != null &&
						!this.getURL().isEmpty()) {
					System.out.println("Going to the HTTP instance ...");
					if (this.getHeaderFields() == null || this.getHeaderFields().isEmpty()) {
						System.out.println("Header map empty or null ...");
					}
				} else if (!legelMethods && this.isHttpServer()) {
					System.out.println("Missing parameter . cannot going to the HTTP instance ...");
					this.setHttpServer(false);
				}
			
				this.setProtocolHttp(protocolHTTP);
				
				
				
				System.out.println("Remote URL : -" +this.getVerb() + "-");
				System.out.println("Remote URL : -" +this.getURL() + "-");
				System.out.println("Remote Port : -" +this.getPort() + "-");
				System.out.println("Remote Protocol : -" +this.getProtocol() + "-");
								
				this.notify();
				
				try {
					if (this.isHttpServer() || this.isPassFlag()) {
						this.wait();
					}
				} catch (InterruptedException e) {
					System.err.println("Il proxy non ha aspettato!!!");
					e.printStackTrace();
				}
			}
		}
		
		///////////
		
		try {
			if (this.isPassFlag()) {
				synchronized (this) {
					System.out.println("Get Response for proxy...");
					
					System.out.println("X3--->pass");
					
					System.out.println(new String(this.getResponse(),0,this.getResponse().length));
					
					out.write(this.getResponse());
					out.flush();
					this.notify();
				}
			}
			
			if (this.isHttpServer()) {
				synchronized (this) {
					System.out.println("Get Response for proxy...");
					
					System.out.println("X3--->http");
					
					System.out.println(new String(this.getResponse(),0,this.getResponse().length));
					
					out.write(this.getResponse());
					out.flush();
					this.notify();
				}
			}
		} catch (Exception e1) {
			System.err.println("CANNOT Get Response for proxy...");
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					System.err.println("Input pass stream error in close");
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					System.err.println("Output pass stream error in close");
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private boolean parseRequest(String header , boolean echo) {
		boolean protocolHTTP = false;
		if (header != null && !header.isEmpty()) {
			//set url port and protocol 
			String[] firstProtocoline = header.split("\r").length > 0 ? (header.split("\r")[0]).split("\\ ") : null;
			
			this.setCheckPath(true);
			
			if (firstProtocoline != null) {
				
				if (firstProtocoline.length >= 3 && firstProtocoline[2].indexOf("HTTP") != -1) {
					protocolHTTP = true;
					System.out.println("HTTP protocol...");
				}
				
				if (protocolHTTP) {
				
					String verb = firstProtocoline[0];
					String path = firstProtocoline[1];
					
					
					
					if (path.startsWith("https") || path.startsWith("http") || path.startsWith("www")) {
						this.setCheckPath(false);
						this.setURL(path);
					}
					
					this.setOutput(verb != null && !verb.equals("GET") &&
							!verb.equals("HEAD") && !verb.equals("CONNECT") ? true : false);
					this.setVerb(verb);
					this.setConnectMethod(verb.equals("CONNECT"));
					System.out.println("Verb :" + verb);
					String[] hostport = firstProtocoline[1].split("\\:");
					if (this.isCheckPath() && hostport != null && (hostport.length == 2 || hostport.length == 3)) {
						String protocol = hostport[0];
						
						System.out.println("Protocol :" + protocol);
						this.setProtocol(protocol);
						
						String host = hostport[1];
						int port = -1;
						try {
							port = Integer.parseInt(host);
							
							if (port == 443) {
								System.out.println("Protocol :" + "https");
								this.setProtocol("https");
								System.out.println("Port :" + port);
								this.setPort(port);
							} else {
								System.out.println("Port :" + port);
								this.setPort(port);
							}
						} catch (NumberFormatException e) {
							if (hostport.length == 3) {
								if (hostport[0].startsWith("https")) {
									this.setProtocol("https");
									System.out.println("Protocol :" + "https");
								} else if (hostport[0].startsWith("http")) {
									this.setProtocol("http");
									System.out.println("Protocol :" + "http");
								} 
								this.setURL(hostport[1]);
								System.out.println("URL :" + hostport[1]);	
								System.out.println("Port :" + hostport[2]);
								this.setPort( Integer.parseInt(hostport[2]) );
							} else if (hostport.length == 2) {
								this.setProtocol(hostport[0]);
								System.out.println("Protocol :" + hostport[0]);
								this.setURL(hostport[1]);
								System.out.println("URL :" + hostport[1]);
								System.out.println("Port not specified :" + 80);
								this.setPort(80);
							}
						}
					}
					
					if (this.isCheckPath() && hostport != null && (hostport.length == 1)) {
						this.setURL(hostport[0]);
						System.out.println("URL :" + hostport[0]);
						System.out.println("Port :" + "80");
						this.setPort(80);
						System.out.println("Protocol :" + "http");
						this.setProtocol("http");
					}
				}
			}
			
			String[] realHeader = header.split("\r");
			
			if (protocolHTTP && realHeader != null) {
				this.setHeaderFields(new HashMap<String,String>());
				for (String h : realHeader) {
					String[] kKeyValue = h.split("\\:");
					if (kKeyValue != null && kKeyValue.length >= 2) {
						
						String v0 = kKeyValue[0].replaceAll("\n", "");
						String v1 = kKeyValue[1].replaceAll("\n", "");
						
						this.getHeaderFields().put(v0, v1);
						
						System.out.println("Key Value Header :" + v0 + ":" + v1);
						if (checkPath && v0.equalsIgnoreCase("Host") && ((this.getURL() != null && 
								this.getURL().startsWith("/")) || this.getURL() == null)  && kKeyValue.length >= 3) {
							
							String url = this.getURL() != null ? this.getURL() : "";
							
							try {
								String v2 = kKeyValue[2].replaceAll("\n", "");
								int p = Integer.parseInt(v2);
								
								this.setURL(v1 + ":" + v2 + url);
								System.out.println("URL :" + url);
								System.out.println("Port :" + v2);
								this.setPort(p);
							} catch (NumberFormatException e) {
								if (url.startsWith("/")) {
									
									System.out.println("Port :" + "80");
									this.setPort(80);
									
									this.setURL(v1 + url);
									System.out.println("URL :" + this.getURL());
								}
							}
						} 
					}
				}
			}
		}
		return protocolHTTP;
	}
	
	
	/**
	 * 
	 * @param ip
	 * @param type at now olny false for ipv4
	 * @return
	 */
	public static InetAddress getInet(String ip, boolean type) throws Exception{
		if (!type && ip.split("\\.").length == 4){
			String[] ip4 = ip.split("\\.");
			try {
				long asLong = 0;
				byte[] bip4 = new byte[4];
				for (int i = 0; i < bip4.length; i++){
					asLong = (asLong << 8) | Integer.parseInt(ip4[i]);
					bip4[i] = (byte) asLong;
				}
				return InetAddress.getByAddress(bip4);
			} catch (Exception e){
				throw new Exception("Error in raw ipv4 address");
			}
		} else throw new Exception("Not input ipv4 address");
	}
}
