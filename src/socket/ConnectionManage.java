package socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


import transform.TransformGenerator;

public class ConnectionManage {
	
	private static final int localport = 8080;
	
	private static final int proxytimeout = 5000;
	
	private static final String serveraddress = "127.0.0.1";
	
	private static final int serverhttpbklog = 1;
	
	private static String logfolder = "."+File.separator+"logs";
	

	public static int getLocalport() {
		return localport;
	}

	public static int getProxytimeout() {
		return proxytimeout;
	}

	public static String getServeraddress() {
		return serveraddress;
	}
	
	public static int getServerhttpbklog() {
		return serverhttpbklog;
	}
	
	public static String getLogfolder() {
		return logfolder;
	}

	public static void setLogfolder(String logfolder) {
		ConnectionManage.logfolder = logfolder;
	}

	protected static Calendar time;
	
	protected ServerSocket local;
	
	protected static int connect_num;
	
	protected static int connect_passnum;
	
	protected static int connect_httpnum;
	
	protected static int max_concurrent_socket = 12;
	
	protected static int concurrent_socket;
	
	
	public ServerSocket getLocal() {
		return local;
	}

	public void setLocal(ServerSocket local) {
		this.local = local;
	}

	
	/**
	 * local is a reference to ServerSocket witch
	 * able to serve the .dat file to the browser in a specific format
	 * It does listen on 8081 local port
	 * @throws IOException
	 */
	public ConnectionManage() throws IOException{
		this.local = new ServerSocket(ConnectionManage.getLocalport());
	}
	
	public ConnectionManage(int port) throws IOException{
		this.local = new ServerSocket(port);
	}
	
	public ConnectionManage(int port,int backlog,InetAddress inet) throws IOException{
		this.local = new ServerSocket(port,backlog,inet);
	}
	
	public ConnectionManage(int port,int backlog,InetAddress inet, boolean serverHttp) throws IOException, Exception{
		this.local = new ServerSocket(port,backlog,inet);
		if (serverHttp) {
			System.out.println("Ghost manager start going to...");
		}
	}
	
	private static void createLogs(String logFolder) throws Exception{
		if (logFolder != null && !logFolder.isEmpty()) {
			ConnectionManage.out_x3_log = new File(logFolder,"out_x3_log.log");
			ConnectionManage.error_x3_log = new File(logFolder,"err_x3_log.log");
		} else {
			ConnectionManage.out_x3_log = new File(ConnectionManage.getLogfolder(),"out_x3_log.log");
			ConnectionManage.error_x3_log = new File(ConnectionManage.getLogfolder(),"err_x3_log.log");
		}
		
		OutputStream sout = new FileOutputStream(ConnectionManage.out_x3_log);
		OutputStream serr = new FileOutputStream(ConnectionManage.error_x3_log);
	}
	
	private static File error_x3_log;
	
	private static File out_x3_log;
	
	
	
	public static File getError_x3_log() {
		return ConnectionManage.error_x3_log;
	}

	public static File getOut_x3_log() {
		return ConnectionManage.out_x3_log;
	}

	public static void main(String[] args) {
		
		ConnectionManage mytest = null;
		try {
			ConnectionManage.time = GregorianCalendar.getInstance(Locale.ITALY);
			if (args.length == 0){
				System.out.println("To start this test you should insert 6 parameters : ");
				System.out.println("1. Max test time (millis)");
				System.out.println("2. Media type: ie.: \"text/html\" or \"application/pdf\" for example");
				System.out.println("3. chuncked (int)");
				System.out.println("4. port (int)");
				System.out.println("5. charset");
				System.out.println("6. inet");
				System.out.println("7. if http");
				System.out.println("8. log folder");
				System.exit(0);
			}
			int port = ConnectionManage.getLocalport();
			if (args[0] != null && !args[0].isEmpty() &&
					args[1] != null && !args[1].isEmpty() &&
						args[2] != null && !args[2].isEmpty()){
				if (args[3] != null && !args[3].isEmpty()){
					
					try {
						port = Integer.parseInt(args[3]);
					} catch (NumberFormatException nfe) {
						port = 8081;
						System.out.println("An error occurred in port input parsing");
					}
					
				}
				
				boolean passToServerHttpFlag = false;
				
				
				if (args.length == 6 && args[5] != null && !args[5].isEmpty()) {
					mytest = new ConnectionManage(port,1,TransformGenerator.getInet(args[5], false));
				} else if (args.length == 7 && args[5] != null && !args[5].isEmpty() && 
						args[6] != null && !args[6].isEmpty()){
					try {
						passToServerHttpFlag = Boolean.parseBoolean(args[6]);
						mytest = new ConnectionManage(port,1,TransformGenerator.getInet(args[5], false),passToServerHttpFlag);	
					} catch (Exception e) {
						System.out.println("Check pass and try again... ");
						if (args[5] != null && !args[5].isEmpty()) {
							mytest = new ConnectionManage(port,1,TransformGenerator.getInet(args[5], false));
						} else {
							mytest = new ConnectionManage(port);
						}
					}
				} else {	
					mytest = new ConnectionManage(port);
				}
				
				if (args[4] != null && !args[4].isEmpty()) {
				
					System.out.println("Address socket : " + mytest.getLocal().getInetAddress() + " | port :" +  mytest.getLocal().getLocalPort());
					int maxtime = Integer.parseInt(args[0]);
					long startTime = ConnectionManage.time.getTime().getTime();
					System.out.println("Startime : " + startTime);
					if (args[1] != null && !args[1].isEmpty()){
						
						System.out.println("Your connection are going to run for " + maxtime + " milliseconds:");
						
						Date now = GregorianCalendar.getInstance(Locale.ITALY).getTime();
						
						ProxyPassServer.setContentType(args[1]);
						ProxyPassServer.setCharset(args[4]);
												
						ProxyPassServer.setChunked(Integer.parseInt(args[2]));
						ProxyPassServer proxypass = new ProxyPassServer();
						
						if (args.length == 8) {
							ConnectionManage.createLogs(args[7]);
						} else {
							ConnectionManage.createLogs(ConnectionManage.logfolder);
						}
						
												
						while (maxtime == 0 || (ConnectionManage.time.getTime().getTime() + maxtime) > now.getTime()){
							now = GregorianCalendar.getInstance(Locale.ITALY).getTime();
							System.out.println("Starting connection... "+mytest.getLocal().getInetAddress()+", port: "+mytest.getLocal().getLocalPort());
							
							synchronized (proxypass) {
								proxypass.setProxy(mytest.getLocal().accept());
								//--->
								proxypass.setPassFlag(false);
								//--->
								proxypass.setHttpServer(passToServerHttpFlag);
								//--->
								ConnectionManage.concurrent_socket++;
								System.out.println("Socket Concurrent Numbers :" + ConnectionManage.concurrent_socket);
								Thread p = new Thread((Runnable)proxypass);
								p.start();
								
								if (ConnectionManage.concurrent_socket >= ConnectionManage.max_concurrent_socket) {
									System.out.println("Max socket number... , waiting...");
									proxypass.wait();
								}
								proxypass.wait();
							}
						}	
					} else {
						System.err.println("Sorry, you must insert what ConnectionManage you would to work now, ie.: \"text/html\" or \"application/pdf\" for example"); 
					}
				} else {
					System.err.println("Sorry, you must insert what port ConnectionManage you would listen now"); 
				}
			} else {
				System.err.println("Sorry, you must insert ConnectionManage max time number (millis) in start command in first argument"); 
			}
		} catch (IOException e){
			System.err.println("IO Error in starting ConnectionManage, exit...");
			e.printStackTrace();
			System.exit(1);
		} catch (NumberFormatException e){
			System.err.println("Sorry, you must insert ConnectionManage max time number () in start command, like a number");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error in starting ConnectionManage, exit...");
			e.printStackTrace();
			System.exit(1);
		} finally {
			System.exit(0);
		}
	}
}
