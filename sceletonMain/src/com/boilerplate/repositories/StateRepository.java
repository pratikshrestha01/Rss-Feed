package com.boilerplate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.boilerplate.entity.Country;
import com.boilerplate.entity.State;

public interface StateRepository extends CrudRepository<State, Long>, JpaSpecificationExecutor<State> {
	
	@Query("select s from State s order by s.id desc")
	List<State> findState();
	
	@Query("select s from State s where s.name=?1")
	State findByState(String name);
	
	@Query("select s from State s where s.country.isoThree=?1")
	List<State> findStatesByCountryIso(String iso);
	
	@Query("select s.name from State s where s.name=?1")
	String findStateName(String name);

}
