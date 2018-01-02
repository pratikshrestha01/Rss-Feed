package com.boilerplate.api;

import java.util.List;

import com.boilerplate.entity.City;
import com.boilerplate.model.CityDTO;
import com.boilerplate.model.PagablePage;

public interface ICityApi {

	CityDTO saveCity(CityDTO cityDTO);
	
	PagablePage getAllCityPagable(int currentpage) ;
	
	List<CityDTO> findCity();

	List<CityDTO> getAllCity();

	City getCityById(long cityId);

	CityDTO findCityById(Long cityId);
	
	CityDTO editCity(CityDTO cityDTO);
	
	List<CityDTO> getCityByState(long state);
	
	CityDTO findByCity(String name);
	
	City findByCityName(String name);
	
}
