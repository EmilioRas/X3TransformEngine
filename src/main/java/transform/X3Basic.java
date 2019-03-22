package main.java.transform;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class X3Basic {

	public String paramFromMap(Map<String,String> xmlParam){
		if (!(xmlParam==null)) {
			StringBuffer xmlQuery = new StringBuffer();
			xmlQuery.append("?");
			Set<String> xmlSet = xmlParam.keySet();
			Iterator<String> i = xmlSet.iterator();
			boolean init = true;
			while(i.hasNext()){
				if (!init)
					xmlQuery.append("&");
				String name = i.next();
				xmlQuery.append(name+"=");
				xmlQuery.append(xmlParam.get(name));
				init = false;
			}
			return xmlQuery.toString();
		}
		return null;
	}
}
