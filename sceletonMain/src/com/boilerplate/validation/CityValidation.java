package com.boilerplate.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boilerplate.api.ICityApi;
import com.boilerplate.controller.CityController;
import com.boilerplate.model.CityDTO;
import com.boilerplate.model.OperationType;
import com.boilerplate.model.error.CityError;


@Component
public class CityValidation {
	private Logger logger=LoggerFactory.getLogger(CityController.class );
	
	@Autowired
	private ICityApi cityApi;
	
	CityError cityError;

	public CityError cityValidation(CityDTO cityDTO){
		
		cityError =new CityError();
		boolean valid=true;
		
		valid = valid && checkName(cityDTO);
		valid = valid && checkState(cityDTO);
		
		cityError.setValid(valid);
		return cityError;
	}
	
	private boolean checkName(CityDTO cityDTO){
		
		if(cityDTO.getName()==null){
			logger.debug("Name null");
			cityError.setName("Name Required");
			return false;
		}else{
			if(cityDTO.getName().trim().equals("")){
				logger.debug("Name null");
				cityError.setName("Name Required");
				return false;
			}else{
				if(cityDTO.getOperation()==OperationType.edit){
					CityDTO city = cityApi.findCityById(cityDTO.getId());
					if(!(city.getName().equals(cityDTO.getName()))){
						CityDTO name = cityApi.findByCity(cityDTO.getName());
						if(name != null){
							logger.debug("city already exists");
							cityError.setName("City Already Exists");
							return false;
						}
					}
				}else{
					CityDTO city = cityApi.findByCity(cityDTO.getName());
					if(city != null){
						logger.debug("city already exists");
						cityError.setName("City Already Exists");
						return false;
					}
				}
				
			}
			
		}

		return true;
	}
	
	private boolean checkState(CityDTO cityDTO){
		if(cityDTO.getState()==null){
			logger.debug("state null");
			cityError.setState("State Required");
			return false;
		}else{
			if(cityDTO.getState().trim().equals("")){
				logger.debug("state null");
				cityError.setState("State Required");
				return false;
			}
		}
		return true;
	}

}
