package com.boilerplate.model;

public class CustomerDTO {
	

	private Long id;

	private String fullName;

	private String accountNo;

	private Long parentId;

	private String firstName;

	private String middleName;

	private String lastName;

	private String city;

	private Long cityId;

	private String address;

	private String mobileNo;

	private String landline;

	private String email;

	private String username;

	private String password;

	private String rePassword;

	private String operation;

	private String documentNo;

	private String document;

	private String dob;

	private String gender;

	private String occupation;

	private String fatherName;

	private String motherName;

	private String grandFatherName;

	private String issuedFrom;

	private String issuedDate;

	// address

	private String presentCityId;

	private long presentStateId;

	private String permanentCityId;

	private long permanentStateId;

	private String presentCityName;

	private String presentStateName;

	private String presentWardNo;

	private String permanentStateName;

	private String permanentWardNo;

	private String permanentCityName;

	private String presentStreet;

	private String permanentStreet;


	private boolean profileVerification;

	private boolean profileVerificationRequest;

	private String profileVerificationRemark;


	public boolean isProfileVerification() {
		return profileVerification;
	}

	public void setProfileVerification(boolean profileVerification) {
		this.profileVerification = profileVerification;
	}

	public boolean isProfileVerificationRequest() {
		return profileVerificationRequest;
	}

	public void setProfileVerificationRequest(boolean profileVerificationRequest) {
		this.profileVerificationRequest = profileVerificationRequest;
	}

	public String getProfileVerificationRemark() {
		return profileVerificationRemark;
	}

	public void setProfileVerificationRemark(String profileVerificationRemark) {
		this.profileVerificationRemark = profileVerificationRemark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}


	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}


	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getGrandFatherName() {
		return grandFatherName;
	}

	public void setGrandFatherName(String grandFatherName) {
		this.grandFatherName = grandFatherName;
	}

	public String getIssuedFrom() {
		return issuedFrom;
	}

	public void setIssuedFrom(String issuedFrom) {
		this.issuedFrom = issuedFrom;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getPresentCityName() {
		return presentCityName;
	}

	public void setPresentCityName(String presentCityName) {
		this.presentCityName = presentCityName;
	}

	public String getPresentStateName() {
		return presentStateName;
	}

	public void setPresentStateName(String presentStateName) {
		this.presentStateName = presentStateName;
	}

	public String getPresentWardNo() {
		return presentWardNo;
	}

	public void setPresentWardNo(String presentWardNo) {
		this.presentWardNo = presentWardNo;
	}

	public String getPermanentStateName() {
		return permanentStateName;
	}

	public void setPermanentStateName(String permanentStateName) {
		this.permanentStateName = permanentStateName;
	}

	public String getPermanentWardNo() {
		return permanentWardNo;
	}

	public void setPermanentWardNo(String permanentWardNo) {
		this.permanentWardNo = permanentWardNo;
	}

	public String getPermanentCityName() {
		return permanentCityName;
	}

	public void setPermanentCityName(String permanentCityName) {
		this.permanentCityName = permanentCityName;
	}

	public long getPresentStateId() {
		return presentStateId;
	}

	public void setPresentStateId(long presentStateId) {
		this.presentStateId = presentStateId;
	}

	public String getPresentCityId() {
		return presentCityId;
	}

	public void setPresentCityId(String presentCityId) {
		this.presentCityId = presentCityId;
	}

	public String getPermanentCityId() {
		return permanentCityId;
	}

	public void setPermanentCityId(String permanentCityId) {
		this.permanentCityId = permanentCityId;
	}

	public long getPermanentStateId() {
		return permanentStateId;
	}

	public void setPermanentStateId(long permanentStateId) {
		this.permanentStateId = permanentStateId;
	}

	public String getPresentStreet() {
		return presentStreet;
	}

	public void setPresentStreet(String presentStreet) {
		this.presentStreet = presentStreet;
	}

	public String getPermanentStreet() {
		return permanentStreet;
	}

	public void setPermanentStreet(String permanentStreet) {
		this.permanentStreet = permanentStreet;
	}

}
