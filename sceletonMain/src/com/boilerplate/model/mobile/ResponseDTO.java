package com.boilerplate.model.mobile;

import java.util.HashMap;

public class ResponseDTO{

	private String status;
	private String code;
	private String message;
	private Object details;
	
	private HashMap<String, String> response;
	
	public ResponseDTO(){
		
	}
	
	public ResponseDTO(String status,String code,String message,Object details){
		this.status= status;
		this.code = code;
		this.message = message;
		this.details = details;
	}
	public ResponseDTO(String status,String code,String message){
		this.status= status;
		this.code = code;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status.getKey();
		code = status.getValue();
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public HashMap<String, String> getResponse() {
		return response;
	}

	public void setResponse(HashMap<String, String> response) {
		this.response = response;
	}

}
