package com.boilerplate.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boilerplate.api.IStateApi;
import com.boilerplate.controller.StateController;
import com.boilerplate.model.OperationType;
import com.boilerplate.model.StateDTO;
import com.boilerplate.model.error.StateError;


@Component
public class StateValidation {
	
	private Logger logger=LoggerFactory.getLogger(StateController.class );
	
	@Autowired
	private IStateApi stateApi;
	
	StateError stateError;

	/*public StateValidation(IStateApi stateApi){
		this.stateApi=stateApi;
	}*/
	public StateError stateValidation(StateDTO stateDTO){	
		stateError =new StateError();
		boolean valid=true;
		
		valid = valid && checkName(stateDTO);
		valid = valid && checkCountry(stateDTO);

		stateError.setValid(valid);
		return stateError;
	}
	
	private boolean checkName(StateDTO stateDTO){
		
		if(stateDTO.getName()==null){
			logger.debug("Name null");
			stateError.setName("Name Required");
			return false;
		}else{
			if(stateDTO.getName().trim().equals("")){
				logger.debug("Name null");
				stateError.setName("Name Required");
				return false;
			}else{
				if(stateDTO.getOperation()== OperationType.edit){
					StateDTO state = stateApi.findStateById(stateDTO.getId());
					if(!(state.getName().equals(stateDTO.getName()))){
						StateDTO name = stateApi.findByStateName(stateDTO.getName());
						if(name != null){
							logger.debug("state already exists");
							stateError.setName("State Already Exists");
							return false;
						}
					}
				}else{
					StateDTO state = stateApi.findByStateName(stateDTO.getName());
					if(state != null){
						logger.debug("state already exists");
						stateError.setName("State Already Exists");
						return false;
					}
				}
			}
		}

		return true;
	}
	
	private boolean checkCountry(StateDTO stateDTO){
		if(stateDTO.getCountry()==null){
			logger.debug("agent address null");
			stateError.setCountry("Address Required");
			return false;
		}else {
			if(stateDTO.getCountry().trim().equals("")){
				logger.debug("Country null");
				stateError.setCountry("Country Required");
				return false;
			}
		}
		return true;
	}
}
