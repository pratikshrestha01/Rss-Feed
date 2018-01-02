package com.boilerplate.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boilerplate.api.ICityApi;
import com.boilerplate.controller.CityController;
import com.boilerplate.entity.City;
import com.boilerplate.entity.Country;
import com.boilerplate.entity.State;
import com.boilerplate.model.CityDTO;
import com.boilerplate.model.PagablePage;
import com.boilerplate.repositories.CityRepository;
import com.boilerplate.repositories.StateRepository;
import com.boilerplate.util.ConvertUtil;
import com.boilerplate.util.PageInfo;

@Service
@Transactional
public class CityApi implements ICityApi {

	private Logger logger=LoggerFactory.getLogger(CityController.class );
	
	@Autowired
	private CityRepository cityRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private StateRepository stateRepository;
	/*public CityApi(CityRepository cityRepository,StateRepository stateRepository){
		this.cityRepository=cityRepository;
		this.stateRepository=stateRepository;
		}
	*/
	
	
	@Override
	public PagablePage getAllCityPagable(int currentpage) {
			
			PagablePage pageble = new PagablePage();
			long totalList = cityRepository.count();
			int totalpage =(int) Math.ceil(totalList/PageInfo.pageList);
			if(currentpage>totalpage || currentpage==0){
				currentpage=1;
			}
			
			int starting = ((currentpage*(int)PageInfo.pageList)-(int)PageInfo.pageList);
			entityManager=entityManager.getEntityManagerFactory().createEntityManager();
			Session session = entityManager.unwrap(Session.class);
		    Query selectQuery = session.createQuery("select a from City a order by a.id desc");
	    selectQuery.setFirstResult(starting);
	    selectQuery.setMaxResults((int)PageInfo.pageList);
	    List<City> city = selectQuery.list();
	    System.out.println(city.size());
	    List<Integer> pagesnumbers= PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
	    pageble.setCurrentPage(currentpage);
	    pageble.setObject(ConvertUtil.convertCityDtoToCity(city));
	    pageble.setPageList(pagesnumbers);
	    pageble.setLastpage(totalpage);
	    
	    
	    return pageble;
	    
	}

	
	@Override
	public CityDTO saveCity(CityDTO cityDTO){

		State state=stateRepository.findByState(cityDTO.getState());
		City city = ConvertUtil.convertDtoToCity(cityDTO);
		city.setState(state);
		city = cityRepository.save(city);
		
		return ConvertUtil.convertCity(city) ;
		
	}
	
	@Override
	public List<CityDTO> findCity() {
		List<City> cityList = new ArrayList<City>();
		cityList = cityRepository.findCity();
		return ConvertUtil.convertCityDtoToCity(cityList);
	}
	
	@Override
	public List<CityDTO> getAllCity() {
		List<City> cityList=cityRepository.getAllCity();
		return ConvertUtil.convertCityDtoToCity(cityList);
	}
	
	@Override
	public City getCityById(long cityId) {
		return cityRepository.findOne(cityId);
	}
	
	@Override
	public List<CityDTO> getCityByState(long state) {
		List<City> cityList=ConvertUtil.convertIterableToList(cityRepository.findCityByState(state));
		return ConvertUtil.convertCityDtoToCity(cityList);
	}

	@Override
	public CityDTO editCity(CityDTO cityDTO) {
		
		City city = cityRepository.findOne(cityDTO.getId());
		State state=stateRepository.findByState(cityDTO.getState());
		city = ConvertUtil.convertDtoToCity(cityDTO, city);
		city.setState(state);
		city = cityRepository.save(city);
		
		return ConvertUtil.convertCity(city);
	}

	@Override
	public CityDTO findByCity(String name) {
		City city = cityRepository.findByCity(name);
		if(city!=null){
			return ConvertUtil.convertCity(city);
		}else{
			return null;
		}
	}

	@Override
	public City findByCityName(String name) {
		City city = cityRepository.findByCity(name);
		return city;
	}

	@Override
	public CityDTO findCityById(Long cityId) {
		City city = cityRepository.findOne(cityId);
		return ConvertUtil.convertCity(city);
	}

}
