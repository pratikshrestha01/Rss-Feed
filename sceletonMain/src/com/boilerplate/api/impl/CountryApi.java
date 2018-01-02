package com.boilerplate.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boilerplate.api.ICountryApi;
import com.boilerplate.entity.Country;
import com.boilerplate.model.CountryDTO;
import com.boilerplate.model.PagablePage;
import com.boilerplate.repositories.CountryRepository;
import com.boilerplate.util.ConvertUtil;
import com.boilerplate.util.PageInfo;

@Service
public class CountryApi implements ICountryApi{
	
	@Autowired
	private CountryRepository countryRepository;
	
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public PagablePage getAllCountryPagable(int currentpage) {
			
			PagablePage pageble = new PagablePage();
			long totalList = countryRepository.count();
			int totalpage =(int) Math.ceil(totalList/PageInfo.pageList);
			if(currentpage>totalpage || currentpage==0){
				currentpage=1;
			}
			
			int starting = ((currentpage*(int)PageInfo.pageList)-(int)PageInfo.pageList);
			entityManager=entityManager.getEntityManagerFactory().createEntityManager();
			Session session = entityManager.unwrap(Session.class);
		    Query selectQuery = session.createQuery("select a from Country a order by a.id desc");
	    selectQuery.setFirstResult(starting);
	    selectQuery.setMaxResults((int)PageInfo.pageList);
	    List<Country> country = selectQuery.list();
	    System.out.println(country.size());
	    List<Integer> pagesnumbers= PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
	    pageble.setCurrentPage(currentpage);
	    pageble.setObject(ConvertUtil.convertCountrytoCountryDtoList(country));
	    pageble.setPageList(pagesnumbers);
	    pageble.setLastpage(totalpage);
	    
	    
	    return pageble;
	    
	}


	
	@Override
	public CountryDTO saveCountry(CountryDTO countryDTO) {
		Country country=ConvertUtil.convertCountryDTO(countryDTO);
		country=countryRepository.save(country);
		return ConvertUtil.convertToCountryDto(country);
	}

	public CountryDTO editCountry(CountryDTO countryDto){
		Country country = countryRepository.findOne(countryDto.getId());
		country = ConvertUtil.convertCountryDTO(countryDto, country);
		countryRepository.save(country);
		return ConvertUtil.convertToCountryDto(country);
	}
	
	@Override
	public List<CountryDTO> getAllCountry() {
		List<Country> countryList = countryRepository.getAllCountry();
		return ConvertUtil.convertCountrytoCountryDtoList(countryList);
	}

	@Override
	@Deprecated
	public Country getCountryById(long countryId) {
		return countryRepository.findOne(countryId);
	}
	
	@Override
	public CountryDTO findCountryById(long countryId) {
		return ConvertUtil.convertToCountryDto(countryRepository.findOne(countryId));
	}
	
	@Override
	public List<CountryDTO> findCountry() {
		List<Country> countryList = new ArrayList<Country>();
		countryList = countryRepository.findCountry();
		return ConvertUtil.convertCountrytoCountryDtoList(countryList);
	}
	
	@Override
	public List<CountryDTO> findAllCountryList() {
		List<Country> countryList = new ArrayList<Country>();
		countryList = countryRepository.findCountry();
		return ConvertUtil.convertCountrytoCountryDtoList(countryList);
	}
	
	@Override
	public List<CountryDTO> findAllCountryExcept(List<Country> countryList) {
		List<Long> countryId = new ArrayList<>();
		if(countryList.size()<0){
		for(Country country:countryList){
			countryId.add(country.getId());
			
		}
		// TODO Auto-generated method stub 
		return ConvertUtil.convertCountrytoCountryDtoList(countryRepository.findAllCountryExcept(countryId));
		}else{
			return findCountry();
		}
		 
	}
	
	
	

	@Override
	public List<CountryDTO> findCountryListFromNameList(List<String> countryNameList) {
		List<Country> countryList = countryRepository.findCountryListFromNameList(countryNameList);
		return ConvertUtil.convertCountrytoCountryDtoList(countryList);
	}
	
	@Override
	public List<String> findCountryListExceptNameList(List<String> countryNameList){
		return countryRepository.findCountryListExceptNameList(countryNameList);
	}

	@Override
	public void deleteCountry(long countryId) {
		countryRepository.delete(countryId);
		
	}

	@Override
	public CountryDTO findByCountryName(String name) {
		Country country = countryRepository.findByCountry(name);
		return ConvertUtil.convertToCountryDto(country);
	}

	@Override
	public String findCountryIsoTwo(String isoTwo) {
		String countryIsoTwo =countryRepository.findCountryIsoTwo(isoTwo);
		return countryIsoTwo;
	}

	@Override
	public String findCountryIsoThreee(String isoThree) {
		String countryIsoThree =countryRepository.findCountryIsoThree(isoThree);
		return countryIsoThree;
	}

	@Override
	public String findCountryName(String name) {
		String countryName = countryRepository.findCountryName(name);
		return countryName;
	}

	@Override
	public String fingCountryDialCode(String dialCode) {
		String countryDialCode = countryRepository.findCountryDialCode(dialCode);
		return countryDialCode;
	}
}
