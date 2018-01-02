package com.boilerplate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.boilerplate.model.Status;
import com.boilerplate.model.UserType;

@Entity
@Table(name = "Users")
public class User extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String accountNo;

	private String mobileNo;

	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(unique = true, nullable = true)
	private String usernameStartup;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private UserType userType;

	@Column(nullable = false)
	private String authority;

	@Column(nullable = false)
	private Status status;

	@Column(nullable = true)
	private String timeZone;

	@Column
	private String secret_key;
	
	private String vcode;
	
	private Date expire;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private WebService web_service;



	@OneToMany(mappedBy = "user")
	private List<Customer> customerList;


	private long associatedId;

	
	private boolean emailVerification;
	
	private Date lastAccess;
	
	private int accessTotal;
	
	private String simCommissionAccountNo;
	
	private String simVirtualCommissionAccountNo;
	
	
	

//	@ManyToMany
//	@JoinTable(name="user_group",
//	   uniqueConstraints=@UniqueConstraint(columnNames={"user_id","group_id"}),
//	   joinColumns=@JoinColumn(name="user_id",referencedColumnName="id"),
//	   inverseJoinColumns=@JoinColumn(name="group_id",referencedColumnName="id")
//	)
//	private Set<Group> groupList;
	
	/*@ManyToMany(targetEntity = Group.class)
	@JoinTable(name = "user_group",
			   uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "group_id"}),
			   joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			   inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id")
			)
	private Set<Group> groupList;*/
	
	

	public String getUsernameStartup() {
		return usernameStartup;
	}

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







	public void setUsernameStartup(String usernameStartup) {
		this.usernameStartup = usernameStartup;
	}







	public WebService getWeb_service() {
		return web_service;
	}

	public void setWeb_service(WebService web_service) {
		this.web_service = web_service;
	}

	public Date getLastAccess() {
		return lastAccess;
	}







	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}







	public int getAccessTotal() {
		return accessTotal;
	}







	public void setAccessTotal(int accessTotal) {
		this.accessTotal = accessTotal;
	}




	@Column(unique = true, nullable = true)
	private String email;
	
	
	
	public String getEmail() {
		return email;
	}







	public void setEmail(String email) {
		this.email = email;
	}







	public boolean isEmailVerification() {
		return emailVerification;
	}







	public void setEmailVerification(boolean emailVerification) {
		this.emailVerification = emailVerification;
	}




	private long myparent;

	// @ManyToMany
	// @JoinTable(name="user_group",
	// uniqueConstraints=@UniqueConstraint(columnNames={"user_id","group_id"}),
	// joinColumns=@JoinColumn(name="user_id",referencedColumnName="id"),
	// inverseJoinColumns=@JoinColumn(name="group_id",referencedColumnName="id")
	// )
	// private Set<Group> groupList;

	/*
	 * @ManyToMany(targetEntity = Group.class)
	 * 
	 * @JoinTable(name = "user_group",
	 * uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "group_id"}),
	 * joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	 * inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName
	 * = "id") ) private Set<Group> groupList;
	 */



	public long getAssociatedId() {
		return associatedId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getMyparent() {
		return myparent;
	}

	public void setMyparent(long myparent) {
		this.myparent = myparent;
	}

	public void setAssociatedId(long associatedId) {
		this.associatedId = associatedId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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



	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getVcode() {
		return vcode;
	}







	public void setVcode(String vcode) {
		this.vcode = vcode;
	}







	public Date getExpire() {
		return expire;
	}







	public void setExpire(Date expire) {
		this.expire = expire;
	}

	/*
	 * public Set<Group> getGroupList() { return groupList; }
	 * 
	 * public void setGroupList(Set<Group> groupList) { this.groupList =
	 * groupList; }
	 */

}
