package com.boilerplate.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boilerplate.api.IAddressApi;
import com.boilerplate.api.ICustomerApi;
import com.boilerplate.api.IUserApi;
import com.boilerplate.entity.Address;
import com.boilerplate.entity.Customer;
import com.boilerplate.mail.SendMail;
import com.boilerplate.model.AddressDTO;
import com.boilerplate.model.CustomerDTO;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.UserType;
import com.boilerplate.model.enumconstant.AddressType;
import com.boilerplate.repositories.CityRepository;
import com.boilerplate.repositories.CustomerRepository;
import com.boilerplate.repositories.UserRepository;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.ConvertUtil;
import com.boilerplate.util.DateTimeUtil;

@Service
public class CustomerApi implements ICustomerApi {

	@Autowired
	private CustomerRepository customerRepository;


	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private IUserApi userApi;


	@Autowired
	private UserRepository userRepository;


	@Autowired
	private SendMail sendMail;
	
	@Autowired
	private IAddressApi addressApi;
	
	



	/*
	 * public CustomerApi(CustomerRepository
	 * customerRepository,CountryRepository countryRepository) {
	 * this.customerRepository = customerRepository;
	 * this.countryRepository=countryRepository; }
	 */
	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO, HttpServletRequest request)
			throws IOException, JSONException {
		Customer customer = new Customer();
		customer.setFirstName(customerDTO.getFirstName());
		customer.setMiddleName(customerDTO.getMiddleName());
		customer.setLastName(customerDTO.getLastName());
		customer.setMobileNo(customerDTO.getMobileNo());
		customer.setLandline(customerDTO.getLandline());
		customer.setEmail(customerDTO.getEmail());
		customer.setCity(cityRepository.findByCity(customerDTO.getCity()));
		customer.setAddress(customerDTO.getAddress());
	

		if (customerDTO.getMiddleName().trim().equals("") || customerDTO.getMiddleName() == null) {
			customer.setFullName(customerDTO.getFirstName() + " " + customerDTO.getLastName());
		} else {
			customer.setFullName(
					customerDTO.getFirstName() + " " + customerDTO.getMiddleName() + " " + customerDTO.getLastName());
		}

		

		customer.setParentId(AuthenticationUtil.getCurrentUser().getAssociatedId());
		customer.setParentType(AuthenticationUtil.getCurrentUser().getUserType());
		customer = customerRepository.save(customer);

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(customerDTO.getUsername());
		userDTO.setPassword(customerDTO.getPassword());
		userDTO.setUserType(UserType.CUSTOMER);
		userDTO.setObjectUserId(customer.getId());
		userDTO.setUserTemplateId(4L);
		userDTO.setEmailVerification(false);
		userDTO = userApi.saveUser(userDTO);

					return ConvertUtil.convertCustomer(customer);

	}

	@Override
	public void registerCustomer(long id) {
		Customer Object = customerRepository.findOne(id);
		Object.setRegistered(true);
		customerRepository.save(Object);

	}

	@Override
	public CustomerDTO editCustomer(CustomerDTO customerDto) throws IOException, JSONException {
		Customer customer = customerRepository.findById(customerDto.getId());

		customer = ConvertUtil.convertCustomerDTO(customer, customerDto);

		if (customerDto.getMiddleName().trim().equals("") || customerDto.getMiddleName() == null) {
			customer.setFullName(customerDto.getFirstName() + " " + customerDto.getLastName());
		} else {
			customer.setFullName(
					customerDto.getFirstName() + " " + customerDto.getMiddleName() + " " + customerDto.getLastName());
		}
		return ConvertUtil.convertCustomer(customerRepository.save(customer));

	}

	@Override
	public List<CustomerDTO> getAllCustomer() {
		List<Customer> copy = customerRepository.getAllCustomer();
		return ConvertUtil.ConvertCustomerToDTO(copy);

	}

	@Override
	public List<CustomerDTO> getAllCustomerBYPointId(long id) {
		List<Customer> copy = customerRepository.getCustomerByPointId(id);
		return ConvertUtil.ConvertCustomerToDTO(copy);

	}

	@Override
	public List<CustomerDTO> findCustomer() {
		List<Customer> customerList = new ArrayList<Customer>();
		customerList = customerRepository.findCustomer();
		return ConvertUtil.ConvertCustomerToDTO(customerList);
	}

	@Override
	public Customer getCustomerWithId(long customerId) {
		return customerRepository.findOne(customerId);
	}

	@Override
	public CustomerDTO getCustomerById(long customerId) {
		Customer customer = customerRepository.findOne(customerId);
		if (customer == null) {
			return null;
		} else {
			return ConvertUtil.convertCustomer(customer);
		}
	}

	@Override
	public void deleteCustomer(Long customerId) {
		customerRepository.delete(customerId);
	}

	@Override
	public List<CustomerDTO> getUnregisteredCustomer(String objectName) {
		List<Customer> copy = new ArrayList<Customer>();
		long associatedID = AuthenticationUtil.getCurrentUser().getAssociatedId();
		if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.ADMIN) {
			copy = ConvertUtil.convertIterableToList(customerRepository.getUnregisteredcustoemr(false, objectName));
		}

		return ConvertUtil.ConvertCustomerToDTO(copy);
	}

	@Override
	public List<CustomerDTO> getAllUnregisteredCustomer() {
		List<Customer> copy = ConvertUtil
				.convertIterableToList(customerRepository.getAllUnregisteredcustoemr(UserType.CUSTOMER));
		return ConvertUtil.ConvertCustomerToDTO(copy);
	}

	@Override
	public CustomerDTO getCustomerByMobileNo(String mobileNo) {
		Customer customer = customerRepository.getCustomerByMobileNo(mobileNo);
		if (customer != null) {
			return ConvertUtil.convertCustomer(customer);
		}
		return null;
	}

	

	@Override
	public CustomerDTO registerCustomer(CustomerDTO customerDTO, HttpServletRequest request) {
		Customer customer = new Customer();
		customer.setFirstName(customerDTO.getFirstName());
		customer.setMiddleName(customerDTO.getMiddleName());
		customer.setLastName(customerDTO.getLastName());
		customer.setMobileNo(customerDTO.getMobileNo());
		customer.setLandline(customerDTO.getLandline());
		customer.setEmail(customerDTO.getEmail());
		customer.setCity(cityRepository.findByCity(customerDTO.getCity()));
		customer.setAddress(customerDTO.getAddress());
		

		if (customerDTO.getMiddleName().trim().equals("") || customerDTO.getMiddleName() == null) {
			customer.setFullName(customerDTO.getFirstName() + " " + customerDTO.getLastName());
		} else {
			customer.setFullName(
					customerDTO.getFirstName() + " " + customerDTO.getMiddleName() + " " + customerDTO.getLastName());
		}

	

		customer.setParentId(userRepository.findByUsername("admin").getAssociatedId());
		customer.setParentType(UserType.ADMIN);

		customer = customerRepository.save(customer);

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(customerDTO.getUsername());
		userDTO.setPassword(customerDTO.getPassword());
		userDTO.setUserType(UserType.CUSTOMER);
		userDTO.setObjectUserId(customer.getId());
		userDTO.setUserTemplateId(4L);
		userDTO.setEmailVerification(false);
		userDTO = userApi.saveUser(userDTO);

		


		return ConvertUtil.convertCustomer(customer);
	}

	
	@Override
	public CustomerDTO updateKYCCustomer(CustomerDTO customerDto, Long associatedId, String operation) {
		Customer customer = this.customerRepository.findOne(associatedId);
		if (operation.equals("KycForm")) {
			customer.setFirstName(customerDto.getFirstName());
			customer.setMiddleName(customerDto.getMiddleName());
			customer.setLastName(customerDto.getLastName());
			// customer.setAddress(customerDto.getAddress());
			customer.setLandline(customerDto.getLandline());
			customer.setMobileNo(customerDto.getMobileNo());
			customer.setEmail(customerDto.getEmail());
			customer.setProfileVerificationRequest(customerDto.isProfileVerificationRequest());
			customer.setProfileVerification(customer.isProfileVerification());
			customer.setGender(customerDto.getGender());
			customer.setOccupation(customerDto.getOccupation());
			customer.setDob(DateTimeUtil.convertStringToDate(customerDto.getDob()));
			customer.setFatherName(customerDto.getFatherName());
			customer.setMotherName(customerDto.getMotherName());
			customer.setGrandFatherName(customerDto.getGrandFatherName());
			customer.setDocumentNo(customerDto.getDocumentNo());
			customer.setIssuedDate(DateTimeUtil.convertStringToDate(customerDto.getIssuedDate()));
			customer.setIssuedFrom(customerDto.getIssuedFrom());
			List<Address> addresslist = new ArrayList<Address>();
			addresslist.add(getPresentAddress(customerDto));
			addresslist.add(getPermanentAddress(customerDto));
			customer.setAddresses(addresslist);
		}
		if (operation.equals("sendForRevision")) {
			customer.setProfileVerificationRemark(customerDto.getProfileVerificationRemark());
			customer.setProfileVerification(customerDto.isProfileVerification());
			customer.setProfileVerificationRequest(customerDto.isProfileVerificationRequest());
		}
		if (operation.equals("approved")) {
			customer.setProfileVerificationRemark(customerDto.getProfileVerificationRemark());
			customer.setProfileVerification(customerDto.isProfileVerification());
			customer.setProfileVerificationRequest(customerDto.isProfileVerificationRequest());
		}
		customerRepository.save(customer);
		return ConvertUtil.convertCustomer(customer);
	}

	public Address getPresentAddress(CustomerDTO customerDto) {
		AddressDTO addressDto = new AddressDTO();
		addressDto.setStreet(customerDto.getPresentStreet());
		addressDto.setWardNo(customerDto.getPresentWardNo());
		addressDto.setAddressType(AddressType.PRESENT);
		addressDto.setCityId(Long.parseLong(customerDto.getPresentCityId()));
		return addressApi.addAddress(addressDto);
	}

	public Address getPermanentAddress(CustomerDTO customerDto) {
		AddressDTO addressDto = new AddressDTO();
		addressDto.setStreet(customerDto.getPermanentStreet());
		addressDto.setWardNo(customerDto.getPermanentWardNo());
		addressDto.setAddressType(AddressType.PERMANENT);
		addressDto.setCityId(Long.parseLong(customerDto.getPermanentCityId()));
		return addressApi.addAddress(addressDto);
	}

	@Override
	public CustomerDTO getCustomerDetailsById(long customerId) {
		Customer customer = customerRepository.findOne(customerId);
		if (customer == null) {
			return null;
		} else {
			CustomerDTO customerDto = ConvertUtil.convertCustomer(customer);
			for (Address a : customer.getAddresses()) {
				if (a.getAddressType().equals(AddressType.PRESENT)) {
					customerDto.setPresentCityId(a.getCity().getId().toString());
					customerDto.setPresentCityName(a.getCity().getName());
					customerDto.setPresentStateId(a.getCity().getState().getId());
					customerDto.setPresentStateName(a.getCity().getState().getName());
					customerDto.setPresentStreet(a.getStreet());
					customerDto.setPresentWardNo(a.getWardNo());
				} else if (a.getAddressType().equals(AddressType.PERMANENT)) {
					customerDto.setPermanentCityId(a.getCity().getId().toString());
					customerDto.setPermanentCityName(a.getCity().getName());
					customerDto.setPermanentStateId(a.getCity().getState().getId());
					customerDto.setPermanentStateName(a.getCity().getState().getName());
					customerDto.setPermanentStreet(a.getStreet());
					customerDto.setPermanentWardNo(a.getWardNo());
				}
			}
			return customerDto;
		}

	}

	@Override
	public void changeProfile(long id, long cid) {
		// TODO Auto-generated method stub
		
	}

}
