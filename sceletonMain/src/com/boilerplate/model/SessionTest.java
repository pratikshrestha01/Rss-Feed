package com.boilerplate.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.boilerplate.entity.Country;


@Component
@Scope("session")
public class SessionTest {

	String mysession;
	
	boolean checker;
	
	

	public boolean isChecker() {
		return checker;
	}

	public void setChecker(boolean checker) {
		this.checker = checker;
	}

	public String getMysession() {
		return mysession;
	}

	public void setMysession(String mysession) {
		this.mysession = mysession;
	}
	
	
	
	
}
