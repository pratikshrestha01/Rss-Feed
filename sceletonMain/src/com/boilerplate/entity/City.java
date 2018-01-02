package com.boilerplate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.boilerplate.model.Status;

@Entity
@Table(name="city")
public class City extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private State state;
	
	@Column(nullable = false)
	private Status status;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
