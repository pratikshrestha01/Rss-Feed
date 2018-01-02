package com.boilerplate.model;

import java.util.HashMap;

import com.boilerplate.model.mobile.ResponseStatus;

public class RestResponseDTO {
	
	private String Status;
	
	private ResponseStatus responseStatus;
	
	private String message;
	
	private String refresh;
	
	private Object detail;
	
	private HashMap<String, String> response;
	
	long id;
	
	private String clientId;
	
	private String clientSecret;
	
	private String csrfName;
	
	private String csrfValue;
	
	

	

	public String getCsrfName() {
		return csrfName;
	}

	public void setCsrfName(String csrfName) {
		this.csrfName = csrfName;
	}

	public String getCsrfValue() {
		return csrfValue;
	}

	public void setCsrfValue(String csrfValue) {
		this.csrfValue = csrfValue;
	}


	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	public HashMap<String, String> getResponse() {
		return response;
	}

	public void setResponse(HashMap<String, String> response) {
		this.response = response;
	}

}
