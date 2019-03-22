package socket;

import java.net.Socket;

import transform.TransformGenerator;
import  transform.TransformHTTPInstance;
import  transform.TransformPass;

public class ProxyPassServer implements Runnable{
	
	public ProxyPassServer(Socket proxy) {
		this.proxy = proxy;
	}
	
	public ProxyPassServer() {}

	private static String contentType;
	
	private static String charset;
	
	private boolean passFlag;
	
	private static int chunked;
	
	
	public static String getContentType() {
		return contentType;
	}

	public static void setContentType(String contentType) {
		ProxyPassServer.contentType = contentType;
	}

	public static String getCharset() {
		return charset;
	}

	public static void setCharset(String charset) {
		ProxyPassServer.charset = charset;
	}

	
	
	private Socket proxy;
	
	public Socket getProxy() {
		return proxy;
	}

	public void setProxy(Socket proxy) {
		this.proxy = proxy;
	}

	
	public static int getChunked() {
		return chunked;
	}

	public static void setChunked(int chunked) {
		ProxyPassServer.chunked = chunked;
	}
	
		
	private boolean httpServer;
	
	public boolean isPassFlag() {
		return passFlag;
	}

	public void setPassFlag(boolean passFlag) {
		this.passFlag = passFlag;
	}

	public boolean isHttpServer() {
		return httpServer;
	}

	public void setHttpServer(boolean httpServer) {
		this.httpServer = httpServer;
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				TransformGenerator tg = new TransformGenerator();
				synchronized (tg) {
					tg.setContentType(ProxyPassServer.getContentType());
					tg.setCharset(ProxyPassServer.getCharset());
					tg.setPassFlag(this.isPassFlag());
					tg.setHttpServer(this.isHttpServer());
					tg.setChunked(ProxyPassServer.getChunked());
					tg.setSocket( this.getProxy() );
					
					tg.setResponse(new byte[] {});
					
					
					Thread myFormat = new Thread((Runnable)tg);
					myFormat.start();
					tg.wait();
					
					
					ConnectionManage.connect_num++;
					
					if (tg.isPassFlag() && tg.isProtocolHttp()) {
						TransformPass ts = new TransformPass();
						ts.setContentType(ProxyPassServer.getContentType());
						ts.setCharset(ProxyPassServer.getCharset());
						ts.setRequest(tg.getRequest());
						ts.setPort(tg.getPort());
						ts.setProtocol(tg.getProtocol());
						ts.setURLForConnection(tg.getURL());
						ts.setOutput(tg.isOutput());
						ts.setVerb(tg.getVerb());
						ts.setHeaderFields(tg.getHeaderFields());
						ts.setConnectMethod(tg.isConnectMethod());
						ts.setCheckPath(tg.isCheckPath());
						
						if (tg.isPassFlag()) {
							synchronized (ts) {
								Thread myPassFormat = new Thread((Runnable)ts);
								myPassFormat.start();
								ts.wait();
								tg.setResponse(ts.getResponse());
								System.out.println("Num req passed: "+ConnectionManage.connect_passnum);
							}
							tg.notify();
							ConnectionManage.connect_passnum++;
							tg.wait();
						}
						System.out.println("Response...");
						System.out.println("Ending connection with pass... ");
					} else if (tg.isHttpServer() && tg.isProtocolHttp()) {
						/////////
						//ghost
						////////
						
						TransformHTTPInstance ts = new TransformHTTPInstance();
						ts.setContentType(ProxyPassServer.getContentType());
						ts.setCharset(ProxyPassServer.getCharset());
						ts.setRequest(tg.getRequest());
						ts.setPort(tg.getPort());
						ts.setProtocol(tg.getProtocol());
						ts.setURLForConnection(tg.getURL());
						ts.setOutput(tg.isOutput());
						ts.setVerb(tg.getVerb());
						ts.setHeaderFields(tg.getHeaderFields());
						ts.setConnectMethod(tg.isConnectMethod());
						ts.setCheckPath(tg.isCheckPath());
						
						if (tg.isHttpServer()) {
							synchronized (ts) {
								Thread myPassFormat = new Thread((Runnable)ts);
								myPassFormat.start();
								ts.wait();
								tg.setResponse(ts.getResponse());
								System.out.println("Num req in http passed: "+ConnectionManage.connect_httpnum);
							}
							tg.notify();
							ConnectionManage.connect_httpnum++;
							tg.wait();
						}
						System.out.println("Response...");
						System.out.println("Ending connection with http... ");
					} else if (!tg.isProtocolHttp()) {	
						System.out.println("Cannot manage the request. Not HTTP protocol... ");
						tg.setHttpServer(false);
						tg.notify();
					}
					
					this.notify();
				}
				System.out.println("Num req servite: "+ConnectionManage.connect_num);
				System.out.println("Ending connection... ");
			}
		} catch (Exception e) {
			System.err.println("Error in ProxyPass Server");
			e.printStackTrace();
		} finally {
			synchronized (this) {
				ConnectionManage.concurrent_socket--;
				if (ConnectionManage.concurrent_socket < ConnectionManage.max_concurrent_socket) {
					this.notify();
				}
			}
		}
	}

}
