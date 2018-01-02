package com.boilerplate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.boilerplate.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	
	@Query("select c from City c order by c.name")
	List<City> findCity();
	
	@Query("select c from City c order by c.id desc")
	List<City> getAllCity();
	
	@Query("select c from City c where c.name=?1")
	City findByCity(String name);
	
	@Query("select c from City c where c.state.id=?1")
	List<City> findCityByState(long state);
	
	@Query("select c.name from City c where c.name=?1")
	String findCityName(String name);
	
}
