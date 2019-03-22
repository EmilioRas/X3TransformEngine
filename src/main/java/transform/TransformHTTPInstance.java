package transform;

import java.util.Map;


public class TransformHTTPInstance  extends X3Basic implements Runnable{

	public TransformHTTPInstance() {
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
		synchronized (this) {
			//End
			this.setResponse("HTTP/1.1 200 OK\r\n\r\nCiao !!! X3 Transform server".getBytes());
			System.out.println("Response... " );
			this.notify();
		}
	}
	
	

}
