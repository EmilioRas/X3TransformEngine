package socket.ssl;

public interface ByteHTTPManage {

	public void setRequest(byte[] request);
	
	public void setRequestHeader(byte[] request);
	
	public void setOnlyBody(byte[] onlybody);
	
	public byte[] getRequest();
	
	public byte[] getRequestHeader();
	
	public byte[] getOnlyBody();
	
	public void setResponse(byte[] response);
	
	public void setResponseHeader(byte[] responseheader);
	
	public byte[] getResponse();
	
	public byte[] getResponseHeader();
}
