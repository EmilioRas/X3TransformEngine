package transform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class TransformPass extends X3Basic implements Runnable{

	public TransformPass() {
	}
	
	private boolean connectMethod;
	
	private String verb;
	
	private String URLForConnection;
	
	private int port;
	
	private String protocol;
	
	private byte[] request;
	
	private byte[] response;
	
	public boolean isConnectMethod() {
		return connectMethod;
	}

	public void setConnectMethod(boolean connectMethod) {
		this.connectMethod = connectMethod;
	}

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

	public byte[] getRequest() {
		return request;
	}

	public void setRequest(byte[] request) {
		this.request = request;
	}
	
	public byte[] getResponse() {
		return response;
	}

	public void setResponse(byte[] response) {
		this.response = response;
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
	
	public String getURLForConnection() {
		return URLForConnection;
	}

	public void setURLForConnection(String uRLForConnection) {
		URLForConnection = uRLForConnection;
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
	
	private boolean output;
	
	public boolean isOutput() {
		return output;
	}

	public void setOutput(boolean output) {
		this.output = output;
	}
	
	private boolean checkPath;
	
	public boolean isCheckPath() {
		return checkPath;
	}

	public void setCheckPath(boolean checkPath) {
		this.checkPath = checkPath;
	}

	@Override
	public void run() {
		OutputStream out = null;
		InputStream in = null;
		HttpURLConnection http = null;
		HttpsURLConnection https = null;
		URLConnection connection = null;
		try{
			System.out.println(". pass ...");
			URL url = null;
			boolean httpsFlag = false;
			synchronized (this) {
				
				boolean legelMethods = (this.getVerb().equals("GET") ||
						this.getVerb().equals("POST") ||
						this.getVerb().equals("HEAD") || 
						this.getVerb().equals("OPTIONS") || 
						this.getVerb().equals("PUT") ||
						this.getVerb().equals("DELETE") ||
						this.getVerb().equals("TRACE") ? true : false );
				
				if (legelMethods) {
					System.out.println("Pass URL connection : " + this.getURLForConnection());
					
					url = (this.isCheckPath() ? 
						new URL(this.getURLForConnection()) : 
							new URL(this.getProtocol()+ "://" + this.getURLForConnection()));
					
					if (this.getProtocol().equalsIgnoreCase("https")) {
						https = (HttpsURLConnection) url.openConnection();
						httpsFlag = true;
						https.setDoOutput(this.isOutput());
					} else if (this.getProtocol().equalsIgnoreCase("http")){
						http = (HttpURLConnection) url.openConnection();
						httpsFlag = false;
						http.setDoOutput(this.isOutput());
					}
					
					System.out.println("Pass connection type : " + (httpsFlag ? "https" : "http") + " , method :" + this.getVerb());
					
					int len = 0;
					byte[] buffer = new byte[8];
					
					
					
					if (httpsFlag) {
						
						if (this.getHeaderFields() != null) {
							for (String hf : this.getHeaderFields().keySet()) {
								https.setRequestProperty(hf, this.getHeaderFields().get(hf)); 
							}
						}
						https.connect();
						if (this.isOutput()) {
							out = https.getOutputStream();
						}
					} else {
						
						if (this.getHeaderFields() != null) {
							for (String hf : this.getHeaderFields().keySet()) {
								http.setRequestProperty(hf, this.getHeaderFields().get(hf)); 
							}
						}
						http.connect();
						if (this.isOutput()) {
							out = http.getOutputStream();
						}
					}
					
					System.out.println("Connect...");
					
					if (this.isOutput() && this.getRequest() != null) {
						out.write("\r\n".getBytes());
						out.write(this.getRequest());
						out.flush();
						out.close();
						System.out.println("Write body request...");
					}
					
					
					
					if (httpsFlag) {
						in = https.getInputStream();
					} else {
						in = http.getInputStream();
					}
					
					System.out.println("Close comunication pass output...");
					StringBuffer response = new StringBuffer();
					while ((len = in.read(buffer)) != -1) {
						 response.append(new String(buffer,0,len));
					}
					
					
					this.setResponse(response.toString().getBytes());
					System.out.println("Response... " + (httpsFlag ? https.getResponseCode() : http.getResponseCode()));
					in.close();
					
				} else {
					System.out.println("Pass URL only open urlconnection : " + this.getURLForConnection());
					
					if (this.isConnectMethod()) {
						url = new URL(this.getURLForConnection());
						
						connection = url.openConnection();
						connection.setDoOutput(true);
						
						System.out.println("Pass connection type : " + (httpsFlag ? "https" : "http") + " , method :" + this.getVerb());
						
						out = connection.getOutputStream();
						
						out.write("\r\n".getBytes());
						out.write(this.getRequest());
						out.flush();
						out.close();
						System.out.println("Write request url connection...");
						
						int len = 0;
						byte[] buffer = new byte[8];
						
						in = connection.getInputStream();
						
						System.out.println("Close comunication pass output url...");
						StringBuffer response = new StringBuffer();
						while ((len = in.read(buffer)) != -1) {
							 response.append(new String(buffer,0,len));
						}
						
						
						this.setResponse(response.toString().getBytes());
						System.out.println("Response... " );
						in.close();
					} else {
						System.out.println("Cannot manage pass for this URL : " + this.getURLForConnection());
					}
					
				}
				this.notify();
			}
		}catch (Exception e){
			System.err.println("Error in TransformPass");
			e.printStackTrace();
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
			if (http != null) {
				http.disconnect();
			}
			
			if (https != null) {
				https.disconnect();
			}
			
		}
	}
	

}
