package com.boilerplate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.boilerplate.model.UserType;

@Entity
@Table(name="customer")
public class Customer extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	private long parentId;
	
	
	@Column(unique=true)
	private String accountNo;
	
	@Column(nullable = true)
	private String firstName;
	
	@Column(nullable = true)
	private String middleName;
	
	@Column(nullable = true)
	private String lastName;
	
	@Column(nullable = true)
	private String fullName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private City city;
	
	private String address;
	
	private String mobileNo;
	
	private String landline;
	
	private String email;

	
	private String documentNo;
	
	private String passportPhotoDocument;
	
	private Date dob;
	
	private String gender;
	
	private String occupation;
	
	private String fatherName;
	
	private String motherName;
	
	private String grandFatherName;
	
	private String issuedFrom;
	
	private Date issuedDate;
	
	@Column(nullable = true)
	private UserType parentType;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(value=FetchMode.SUBSELECT)
	private List<Address> addresses;
	
	private boolean profileVerification;
	
	private boolean profileVerificationRequest;
	
	private String profileVerificationRemark;
	
	private boolean isRegistered;
	
	


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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public UserType getParentType() {
		return parentType;
	}

	public void setParentType(UserType parentType) {
		this.parentType = parentType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
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

	public String getPassportPhotoDocument() {
		return passportPhotoDocument;
	}

	public void setPassportPhotoDocument(String passportPhotoDocument) {
		this.passportPhotoDocument = passportPhotoDocument;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
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

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	
	

}
