package com.boilerplate.model.enumconstant;

import javax.servlet.http.HttpServletRequest;

public enum EmailURL {
	
Local("http://localhost:8080"), remote("http://192.168.1.254:8080");
	
	private final String value;

	private EmailURL(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}
	
	public static String getValue(HttpServletRequest request){
		
		/*
		String url = request.getServerName();
	          
		if(url.equals("localhost")){
			
			return Local.getValue();
			
		} else {
			
			return remote.getValue();
			
		}*/
		
		String uri = request.getScheme() + "://" +   // "http" + "://
	             request.getServerName() +       // "myhost"
	             ":" +                           // ":"
	             request.getServerPort() ;       // "8080"
		
		return uri;
	}

	public static EmailURL getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (EmailURL v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}


}
