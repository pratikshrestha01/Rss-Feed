package com.boilerplate.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.boilerplate.model.Status;


@Entity
@Table(name="country")
public class Country extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String isoTwo;
	
	@Column(unique = true, nullable = false)
	private String isoThree;
	
	@Column(nullable = false)
	private String dialCode;
	
	@Column(nullable = false)
	private Status status;
	
	@OneToMany( cascade=CascadeType.ALL, mappedBy="country")
	private Set<State> state = new HashSet<State>();
	
	private int totalstate;
	
	
	
	

	
	public int getTotalstate() {
		return totalstate;
	}

	public void setTotalstate(int totalstate) {
		this.totalstate = totalstate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoTwo() {
		return isoTwo;
	}

	public void setIsoTwo(String isoTwo) {
		this.isoTwo = isoTwo;
	}

	public String getIsoThree() {
		return isoThree;
	}

	public void setIsoThree(String isoThree) {
		this.isoThree = isoThree;
	}

	public String getDialCode() {
		return dialCode;
	}

	public void setDialCode(String dialCode) {
		this.dialCode = dialCode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<State> getState() {
		return state;
	}

	public void setState(Set<State> state) {
		this.state = state;
	}



	
}
