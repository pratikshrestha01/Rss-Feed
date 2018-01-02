package com.boilerplate.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.boilerplate.model.Status;


@Entity
@Table(name="state")
public class State extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Country country;
	
	@Column(nullable = false)
	private Status status;

	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL, mappedBy="state")
	private Set<City> city = new HashSet<City>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<City> getCity() {
		return city;
	}

	public void setCity(Set<City> city) {
		this.city = city;
	}
	
	
	

}
