package transform;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public class Resolver implements URIResolver{

	private boolean certificated;
	
	
	
	public boolean isCertificated() {
		return certificated;
	}

	private String hostname;
	
	public Resolver(String host,String filename,String protocol){
		this.hostname = host;
		this.filename = filename;
		this.protocol = protocol;
		this.isOnURL = true;
		this.base = protocol.concat("://").concat(host).concat("/").concat(filename);
	}
	
	@Override
	public Source resolve(String obj, String base) throws TransformerException {
		Source s = null;
		String myHref = null;
		String myBase = null;
		if (obj != null && !obj.isEmpty() && base != null && !base.isEmpty()){
			myHref = obj;
			myBase = base;
		} else if (this.isOnURL){
			myHref = this.base;
		} else {
			myHref = this.base;
			myBase = this.root;
		}
		try {
			if ((myBase.startsWith("http") | myBase.startsWith("https")) && this.isOnURL){
				if (myBase.startsWith("https")){
					this.certificated = true;
				}
				s = new StreamSource(myHref);
			} else if (!this.isOnURL && myHref.startsWith(File.separatorChar+"")){
				s = new StreamSource(myHref);
			} else if (!myHref.startsWith(File.separatorChar+"")){
				s = new StreamSource(".".concat(File.separatorChar+"").concat(myHref));
			}
				
		} catch (Exception ex){
			throw new TransformerException(ex);
		}
		return s;
	}
	
	private String root;
	
	
	
	public void setRoot(String root) {
		this.root = root;
	}

	private String base;
	
	private boolean isOnURL;
	
	private String protocol;
	
	
	public Resolver(String root,String filename){
		this.root = root;
		this.filename = filename;
		this.isOnURL = false;
		this.base = root.concat(filename);
	}
	
	private String filename;
	
	public Resolver(String filename){
		this.filename = filename;
		this.isOnURL = false;
		this.base = root.concat(filename);
	}
}
