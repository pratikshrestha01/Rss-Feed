package com.boilerplate.repositories;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.boilerplate.entity.Country;

public interface CountryRepository extends CrudRepository<Country, Long>, JpaSpecificationExecutor<Country> {

	@Query("select c from Country c order by c.name")
	List<Country> findCountry();
	
	@Query("select c from Country c where c.name=?1")
	Country findByCountry(String name);
	
	@Query("select c from Country c where c.isoThree=?1")
	Country findByCountryIsoTwo(String iso);
	
	@Query("select c from Country c where c.isoThree=?1")
	Country findByCountryIsoThree(String iso);
	
	@Query("select c from Country c order by c.name")
	List<Country> findAllCountryExcept(List<Long> countryId);
	
	@Query("select c from Country c order by c.id desc")
	List<Country> getAllCountry();
	
	@Query("select c.isoThree from Country c where c.name=?1")
	String findByCountryIso(String name);
	
	@Query("select c from Country c where c.name IN (?1)")
	List<Country> findCountryListFromNameList(List<String> countryNameList);
	
	@Query("select c.name from Country c where c.name NOT IN (?1)")
	List<String> findCountryListExceptNameList(List<String> countryNameList);
	
	@Query("select c.isoTwo from Country c where c.isoTwo=?1")
	String findCountryIsoTwo(String isoTwo);
	
	@Query("select c.isoThree from Country c where c.isoThree=?1")
	String findCountryIsoThree(String isoThree);
	
	@Query("select c.name from Country c where c.name=?1")
	String findCountryName(String name);
	
	@Query("select c from Country c where c.name=?1")
	Country findCountryNameDem(String name);
	
	@Query("select c.dialCode from Country c where c.dialCode=?1")
	String findCountryDialCode(String dialCode);
	
	@Query("select c, (select count(s) from State s) from Country c where c.name=?1")
	HashMap<Country, Integer> testcountry(String name);
	
	
	
}
