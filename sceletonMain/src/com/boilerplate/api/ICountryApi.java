package com.boilerplate.api;

import java.util.List;

import com.boilerplate.entity.Country;
import com.boilerplate.model.CountryDTO;
import com.boilerplate.model.PagablePage;

public interface ICountryApi {

	CountryDTO saveCountry(CountryDTO countryDTO);
	
	 PagablePage getAllCountryPagable(int currentpage);

	List<CountryDTO> getAllCountry();

	Country getCountryById(long countryId);
	
	List<CountryDTO> findCountry();

	List<CountryDTO> findAllCountryExcept(List<Country> countryList);
	
	
	CountryDTO findCountryById(long countryId); 
	
	List<CountryDTO> findCountryListFromNameList(List<String> countryNameList);
	
	List<String> findCountryListExceptNameList(List<String> countryNameList);
	 
	CountryDTO editCountry(CountryDTO countryDto);
	
	void deleteCountry(long countryId);
	
	List<CountryDTO> findAllCountryList();
	
	CountryDTO findByCountryName(String name);
	
	String findCountryIsoTwo(String isoTwo);
	
	String findCountryIsoThreee(String isoThree);
	
	String findCountryName(String name);
	
	String fingCountryDialCode(String dialCode);
	
}
