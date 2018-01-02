package com.boilerplate.api;

import com.boilerplate.entity.Address;
import com.boilerplate.model.AddressDTO;

public interface IAddressApi {

	Address addAddress(AddressDTO addressDto);
	
	Address editAddress(AddressDTO addressDto);
	
}
