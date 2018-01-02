package com.boilerplate.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.boilerplate.model.Status;
import com.boilerplate.model.enumconstant.AddressType;

@Entity
@Table(name = "address")
public class Address extends AbstractEntity<Long>{

	@ManyToOne
	private City city;
	
	private String street;
	
	private String wardNo;
	
	private AddressType addressType;
	
	private Status status;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getWardNo() {
		return wardNo;
	}

	public void setWardNo(String wardNo) {
		this.wardNo = wardNo;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
}
