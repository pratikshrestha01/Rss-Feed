package com.boilerplate.model;

import com.boilerplate.model.Status;
import com.boilerplate.model.UserType;


public class UserDTO  {
	
	private Long id;
	
	private String username;
	
	private String name;
	
	private String mobileNo;
	
	private String password;
	
	private String repassword;

	private UserType userType;

	private Status status;
	
	private String authority;
	
	private String operation;
	
	private String timeZone;

	private String secret_key;
	
	private String customer;
	
	private String superAgent;
	
	private String subAgent;
	
	private WebServiceDTO web_service;
	
	private String userTemplate; 
	
	private Long userTemplateId; 
	

	
	private String userObject;
	
	private Long objectUserId;
	
	private String address;
	
	private String email;
	
	private boolean emailVerification;
	
	private String simCommissionAccountNo;
	
	private String simVirtualCommissionAccountNo;
	
	
	
	public String getSimCommissionAccountNo() {
		return simCommissionAccountNo;
	}

	public void setSimCommissionAccountNo(String simCommissionAccountNo) {
		this.simCommissionAccountNo = simCommissionAccountNo;
	}

	public String getSimVirtualCommissionAccountNo() {
		return simVirtualCommissionAccountNo;
	}

	public void setSimVirtualCommissionAccountNo(String simVirtualCommissionAccountNo) {
		this.simVirtualCommissionAccountNo = simVirtualCommissionAccountNo;
	}

	public boolean isEmailVerification() {
		return emailVerification;
	}

	public void setEmailVerification(boolean emailVerification) {
		this.emailVerification = emailVerification;
	}

	public String getUserObject() {
		return userObject;
	}

	public void setUserObject(String userObject) {
		this.userObject = userObject;
	}

	public Long getObjectUserId() {
		return objectUserId;
	}

	public void setObjectUserId(Long objectUserId) {
		this.objectUserId = objectUserId;
	}

	public String getUserTemplate() {
		return userTemplate;
	}

	public void setUserTemplate(String userTemplate) {
		this.userTemplate = userTemplate;
	}

	

	

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSuperAgent() {
		return superAgent;
	}

	public void setSuperAgent(String superAgent) {
		this.superAgent = superAgent;
	}

	public String getSubAgent() {
		return subAgent;
	}

	public void setSubAgent(String subAgent) {
		this.subAgent = subAgent;
	}
	
/*
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}*/

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

	public WebServiceDTO getWeb_service() {
		return web_service;
	}

	public void setWeb_service(WebServiceDTO web_service) {
		this.web_service = web_service;
	}

	public Long getUserTemplateId() {
		return userTemplateId;
	}

	public void setUserTemplateId(Long userTemplateId) {
		this.userTemplateId = userTemplateId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
