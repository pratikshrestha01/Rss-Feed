package com.boilerplate.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boilerplate.api.IAddressApi;
import com.boilerplate.entity.Address;
import com.boilerplate.model.AddressDTO;
import com.boilerplate.repositories.AddressRepository;
import com.boilerplate.repositories.CityRepository;
import com.boilerplate.util.ConvertUtil;

@Service
public class AddressApi implements IAddressApi{

	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ConvertUtil convertUtil;

	@Override
	public Address addAddress(AddressDTO addressDto) {
		Address address = convertUtil.convertAddressDTO(addressDto);
		address = addressRepository.save(address);
		return address;

	}

	@Override
	public Address editAddress(AddressDTO addressDto) {
		
		Address entity = addressRepository.findOne(addressDto.getId());
		entity.setCity(cityRepository.findOne(addressDto.getCityId()));
		entity.setStreet(addressDto.getStreet());
		entity.setWardNo(addressDto.getWardNo());
				
		entity = addressRepository.save(entity);
		return entity;
	}
}
