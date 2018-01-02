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

import com.boilerplate.api.IStateApi;
import com.boilerplate.controller.StateController;
import com.boilerplate.entity.Country;
import com.boilerplate.entity.State;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.StateDTO;
import com.boilerplate.repositories.CountryRepository;
import com.boilerplate.repositories.StateRepository;
import com.boilerplate.util.ConvertUtil;
import com.boilerplate.util.PageInfo;

@Service
@Transactional
public class StateApi implements IStateApi{
	
	private Logger logger=LoggerFactory.getLogger(StateController.class );
	
	@Autowired
	private  StateRepository stateRepository;
	@Autowired
	private  CountryRepository countryRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
//	public StateApi(StateRepository stateRepository,CountryRepository countryRepository){
//		this.stateRepository=stateRepository;
//		this.countryRepository=countryRepository;
//		}
	
	
	
	@Override
	public PagablePage getAllStatePagable(int currentpage) {
			
			PagablePage pageble = new PagablePage();
			long totalList = stateRepository.count();
			int totalpage =(int) Math.ceil(totalList/PageInfo.pageList);
			if(currentpage>totalpage || currentpage==0){
				currentpage=1;
			}
			
			int starting = ((currentpage*(int)PageInfo.pageList)-(int)PageInfo.pageList);
			entityManager=entityManager.getEntityManagerFactory().createEntityManager();
			Session session = entityManager.unwrap(Session.class);
		    Query selectQuery = session.createQuery("select a from State a order by a.id desc");
	    selectQuery.setFirstResult(starting);
	    selectQuery.setMaxResults((int)PageInfo.pageList);
	    List<State> state = selectQuery.list();
	    System.out.println(state.size());
	    List<Integer> pagesnumbers= PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
	    pageble.setCurrentPage(currentpage);
	    pageble.setObject(ConvertUtil.convertStateDtoToState(state));
	    pageble.setPageList(pagesnumbers);
	    pageble.setLastpage(totalpage);
	    
	    
	    return pageble;
	    
	}

	
	@Override
	public StateDTO saveState(StateDTO stateDTO){
		
		Country country=countryRepository.findByCountry(stateDTO.getCountry());
		State state=ConvertUtil.convertDtoToState(stateDTO);
		state.setCountry(country);
		state = stateRepository.save(state);
		logger.debug("State Saved");
		return ConvertUtil.convertState(state);
		
	}
	@Override
	public List<StateDTO> findState() {
		List<State> stateList = new ArrayList<State>();
		stateList = stateRepository.findState();
		return ConvertUtil.convertStateDtoToState(stateList);
	}
	
	@Override
	public List<StateDTO> getAllState() {
		List<State> stateList=ConvertUtil.convertIterableToList(stateRepository.findAll());
		return ConvertUtil.convertStateDtoToState(stateList);
	}
	@Override
	public State getStateById(long stateId) {
		return stateRepository.findOne(stateId);
	}
	
	@Override
	public List<StateDTO> getStatesByCountryIso(String iso) {
		List<State> stateList=ConvertUtil.convertIterableToList(stateRepository.findStatesByCountryIso(iso));
		return ConvertUtil.convertStateDtoToState(stateList);
	}

	@Override
	public StateDTO editState(StateDTO stateDTO) {
		
		State state=stateRepository.findOne(stateDTO.getId());
		Country country=countryRepository.findByCountry(stateDTO.getCountry());
		state=ConvertUtil.convertDtoToState(stateDTO, state);
		state.setCountry(country);
		state = stateRepository.save(state);
		
		return ConvertUtil.convertState(state);
	}
	@Override
	public StateDTO findByStateName(String name) {
		State state = stateRepository.findByState(name);
		if(state!=null){
			return ConvertUtil.convertState(state);
		}else{
			return null;
		}
		
	}
	@Override
	public StateDTO findStateById(Long stateId) {
		State state = stateRepository.findOne(stateId);
		return ConvertUtil.convertState(state);
	}
}
