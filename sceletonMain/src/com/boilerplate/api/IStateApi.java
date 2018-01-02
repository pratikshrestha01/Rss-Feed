package com.boilerplate.api;

import java.util.List;

import com.boilerplate.entity.State;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.StateDTO;

public interface IStateApi {

	StateDTO saveState(StateDTO dto);
	
	PagablePage getAllStatePagable(int currentpage);
	
	List<StateDTO> findState();

	List<StateDTO> getAllState();

	State getStateById(long stateId);
	
	StateDTO findStateById(Long stateId);

	StateDTO editState(StateDTO stateDTO);
	
	List<StateDTO> getStatesByCountryIso(String iso);
	
	StateDTO findByStateName(String name);
	
}
