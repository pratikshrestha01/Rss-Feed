package com.boilerplate.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.boilerplate.entity.Customer;
import com.boilerplate.model.CustomerDTO;

public interface ICustomerApi {

	public CustomerDTO saveCustomer(CustomerDTO customerDTO, HttpServletRequest request) throws IOException, JSONException;

	public List<CustomerDTO> getAllCustomer();

	public Customer getCustomerWithId(long customerId);

	public List<CustomerDTO> findCustomer();

	public void deleteCustomer(Long parseLong);

	public CustomerDTO editCustomer(CustomerDTO customerDto) throws IOException , JSONException;
	
	public List<CustomerDTO> getAllUnregisteredCustomer();
	
	public List<CustomerDTO> getUnregisteredCustomer(String name);

	public CustomerDTO getCustomerById(long customerId);
	
	public CustomerDTO getCustomerDetailsById(long customerId);

	public void registerCustomer(long id);

	public CustomerDTO getCustomerByMobileNo(String mobileNo);

	public List<CustomerDTO> getAllCustomerBYPointId(long id);

	void changeProfile(long id, long cid);

	public CustomerDTO registerCustomer(CustomerDTO customerDTO, HttpServletRequest request);
	
	
	public CustomerDTO updateKYCCustomer(CustomerDTO customerDto,Long associatedId,String operation);
}
