package main.java.transform;

public enum TransformHTTPStatus {
	//1xx
	HTTP_CONTINUE(100),
	HTTP_SWITCHING_PROTOCOLS(101),
	HTTP_PROCESSING(102),
	
	//2xx
	HTTP_OK(200);
	
	
	private int status;
	
	private TransformHTTPStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
}
