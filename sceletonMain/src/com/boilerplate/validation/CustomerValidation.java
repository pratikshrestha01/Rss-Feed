package com.boilerplate.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boilerplate.api.ICityApi;
import com.boilerplate.api.ICustomerApi;
import com.boilerplate.api.IUserApi;
import com.boilerplate.entity.Customer;
import com.boilerplate.entity.User;
import com.boilerplate.model.CustomerDTO;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.error.CustomerError;
import com.boilerplate.repositories.CustomerRepository;
import com.boilerplate.util.AuthenticationUtil;


@Component
public class CustomerValidation {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	
	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private ICityApi cityApi;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private CustomerRepository customerRepository;

	private boolean valid;

	public CustomerError customerValidation(CustomerDTO customerDTO) {
		CustomerError error = new CustomerError();
		valid = true;

		error.setFirstName(this.checkFirstName(customerDTO.getFirstName()));
		error.setLastName(this.checkLastName(customerDTO.getLastName()));
		error.setMobileNo(this.checkMobileNo(customerDTO.getMobileNo()));
		error.setCity(this.checkCity(customerDTO.getCity()));
		error.setAddress(this.checkAddress(customerDTO.getAddress()));
		error.setUsername(this.checkUsername(customerDTO.getUsername()));
		error.setEmail(this.checkEmail(customerDTO.getEmail()));
		
		error.setValid(valid);
		return error;

	}

	public CustomerError customerKYCValidation(CustomerDTO customerDTO) {
		CustomerError error = new CustomerError();
		valid = true;
		checkFormValidity(customerDTO);
		error.setFirstName(this.checkFirstName(customerDTO.getFirstName()));
		error.setLastName(this.checkLastName(customerDTO.getLastName()));
		error.setMobileNo(this.checkKYCMobileNo(customerDTO.getMobileNo()));
		// error.setAddress(this.checkAddress(customerDTO.getAddress()));
		error.setGender(this.checkGender(customerDTO.getGender()));
		error.setOccupation(this.checkOccupation(customerDTO.getOccupation()));
		error.setDob(this.checkDOB(customerDTO.getDob()));
		error.setFatherName(this.checkFatherName(customerDTO.getFatherName()));
		error.setMotherName(this.checkMotherName(customerDTO.getMotherName()));
		error.setGrandFatherName(this.checkGrandFatherName(customerDTO.getGrandFatherName()));
		error.setEmail(this.checkEmail(customerDTO.getEmail()));

		error.setDocumentNo(this.checkDocumentNo(customerDTO.getDocumentNo()));
		error.setIssuedFrom(this.checkIssuedFrom(customerDTO.getIssuedFrom()));
		error.setIssuedDate(this.checkIssuedDate(customerDTO.getIssuedDate()));
		error.setPresentCityId(this.checkPresentCityId(customerDTO.getPresentCityId()));
		error.setPresentStreet(this.checkPresentStreet(customerDTO.getPresentStreet()));
		error.setPresentWardNo(this.checkPresentWardNo(customerDTO.getPresentWardNo()));
		error.setPermanentCityId(this.checkPermanentCityId(customerDTO.getPermanentCityId()));
		error.setPermanentStreet(this.checkPermanentStreet(customerDTO.getPermanentStreet()));
		error.setPermanentWardNo(this.checkPermanentWardNo(customerDTO.getPermanentWardNo()));
		error.setValid(valid);
		return error;

	}

	private void checkFormValidity(CustomerDTO customerDTO) {
		
		User user = AuthenticationUtil.getCurrentUser();
		Customer customer=this.customerRepository.findOne(user.getAssociatedId());
		CustomerError error = new CustomerError();
		if (customer == null) {
			valid = false;
			error.setValid(valid);
		}
		if (customer.isProfileVerification()) {
			valid = false;
			error.setValid(valid);
		} else {
			valid = true;
			error.setValid(valid);
		}
	}

	public CustomerError sendForRevisionValidation(CustomerDTO customerDto) {
		CustomerError error = new CustomerError();
		checkValidity(customerDto);
		if (customerDto.getProfileVerificationRemark() == null
				|| customerDto.getProfileVerificationRemark().trim().equals("")) {
			error.setProfileVerificationRemark("Write remarks for customer review");
			error.setValid(false);
		} else {
			valid = true;
			error.setValid(valid);
		}

		return error;
	}

	public CustomerError validateCustomer(CustomerDTO customerDto) {
		return checkValidity(customerDto);
	}

	private String checkPermanentWardNo(String permanentWardNo) {
		if (permanentWardNo == null) {
			logger.debug("Permanent Ward Number Required==>");
			valid = false;
			return "Invalid Permanent Ward Number";
		} else if (permanentWardNo.trim().equals("")) {
			logger.debug("Permanent Ward Number Required==>");
			valid = false;
			return "Invalid Permanent Ward Number";
		}
		return null;
	}

	private String checkPermanentStreet(String permanentStreet) {
		if (permanentStreet == null) {
			logger.debug("Permanent Street Required==>");
			valid = false;
			return "Invalid Permanent Street";
		} else if (permanentStreet.trim().equals("")) {
			logger.debug("Permanent Street Required==>");
			valid = false;
			return "Invalid Permanent Street";
		}
		return null;
	}

	private String checkPermanentCityId(String permanentCityId) {
		if (permanentCityId == null) {
			logger.debug("Permanent City Required==>");
			valid = false;
			return "Invalid Permanent City";
		} else if (permanentCityId.trim().equals("")) {
			logger.debug("Present Street Required==>");
			valid = false;
			return "Invalid Permanent City";
		}
		return null;
	}

	private String checkPresentWardNo(String presentWardNo) {
		if (presentWardNo == null) {
			logger.debug("Present Ward Number Required==>");
			valid = false;
			return "Invalid Present Ward Number";
		} else if (presentWardNo.trim().equals("")) {
			logger.debug("Present Ward Number Required==>");
			valid = false;
			return "Invalid Present Ward Number";
		}
		return null;
	}

	private String checkPresentStreet(String presentStreet) {
		if (presentStreet == null) {
			logger.debug("Present Street Required==>");
			valid = false;
			return "Invalid Present Street";
		} else if (presentStreet.trim().equals("")) {
			logger.debug("Present Street Required==>");
			valid = false;
			return "Invalid Present Street";
		}
		return null;
	}

	private String checkPresentCityId(String presentCityId) {
		if (presentCityId == null) {
			logger.debug("Present City Required==>");
			valid = false;
			return "Invalid Present City";
		} else if (presentCityId.trim().equals("")) {
			logger.debug("Present City Required==>");
			valid = false;
			return "Invalid Present City";
		}
		return null;
	}

	private String checkIssuedDate(String issuedDate) {
		if (issuedDate == null) {
			logger.debug("Issued Date Required==>");
			valid = false;
			return "Invalid Issued Date";
		} else if (issuedDate.trim().equals("")) {
			logger.debug("Issued Date Required==>");
			valid = false;
			return "Invalid Issued Date";
		}
		return null;
	}

	private String checkIssuedFrom(String issuedFrom) {
		if (issuedFrom == null) {
			logger.debug("Issued From Required==>");
			valid = false;
			return "Invalid Issued From";
		} else if (issuedFrom.trim().equals("")) {
			logger.debug("Issued From Required==>");
			valid = false;
			return "Invalid Issued From";
		}
		return null;
	}

	private String checkDocumentNo(String documentNo) {
		if (documentNo == null) {
			logger.debug("Document Number Required==>");
			valid = false;
			return "Invalid Document Number";
		} else if (documentNo.trim().equals("")) {
			logger.debug("Document Number Required==>");
			valid = false;
			return "Invalid Document Number";
		}
		return null;
	}

	private String checkGrandFatherName(String grandFatherName) {
		if (grandFatherName == null) {
			logger.debug("Grand Father Name Required==>");
			valid = false;
			return "Invalid Grand Father Name";
		} else if (grandFatherName.trim().equals("")) {
			logger.debug("Grand Father Name Required==>");
			valid = false;
			return "Invalid Grand Father Name";
		}
		return null;
	}

	private String checkMotherName(String motherName) {
		if (motherName == null) {
			logger.debug("Mother Name Required==>");
			valid = false;
			return "Invalid Mother Name";
		} else if (motherName.trim().equals("")) {
			logger.debug("Mother Name Required==>");
			valid = false;
			return "Invalid Mother Name";
		}
		return null;
	}

	private String checkFatherName(String fatherName) {
		if (fatherName == null) {
			logger.debug("Father Name Required==>");
			valid = false;
			return "Invalid Father Name";
		} else if (fatherName.trim().equals("")) {
			logger.debug("Father Name Required==>");
			valid = false;
			return "Invalid Father Name";
		}
		return null;
	}

	private String checkDOB(String dob) {
		if (dob == null) {
			logger.debug("DOB Required==>");
			valid = false;
			return "Invalid DOB";
		} else if (dob.trim().equals("")) {
			logger.debug("DOB Required==>");
			valid = false;
			return "Invalid DOB";
		}
		return null;
	}

	private CustomerError checkValidity(CustomerDTO customerDto) {

		// User user = AuthenticationUtil.getCurrentUser();
		CustomerError error = new CustomerError();
		Customer customer = customerRepository.findOne(customerDto.getId());
		if (customer == null) {
			valid = false;
			error.setValid(valid);
		}
		if (customer.isProfileVerification()) {
			valid = false;
			error.setValid(valid);
		} else {
			valid = true;
			error.setValid(valid);
		}
		return error;

	}

	private String checkFirstName(String firstName) {

		if (firstName == null) {
			logger.debug("First Name Required==>");
			valid = false;
			return "Invalid First Name";
		} else if (firstName.trim().equals("")) {
			logger.debug("First Name Required==>");
			valid = false;
			return "Invalid First Name";
		}
		return null;
	}

	private String checkLastName(String lastName) {

		if (lastName == null) {
			logger.debug("Last Name Required==>");
			valid = false;
			return "Invalid Last Name";
		} else if (lastName.trim().equals("")) {
			logger.debug("Last Name Required==>");
			valid = false;
			return "Invalid Last Name";
		}
		return null;
	}

	private String checkAddress(String address) {

		if (address == null) {
			logger.debug("Address Required==>");
			valid = false;
			return "Invalid Address";
		} else if (address.trim().equals("")) {
			logger.debug("Address Required==>");
			valid = false;
			return "Invalid Address";
		}
		return null;
	}

	private String checkCity(String city) {
		if (city == null) {
			logger.debug("City Required==>");
			valid = false;
			return "Invalid City";
		} else if (city.trim().equals("")) {
			logger.debug("City Required==>");
			valid = false;
			return "Invalid City";
		}
		try {
			if (cityApi.findByCity(city) == null) {
				logger.debug("Invalid city==>");
				valid = false;
				return "Invalid City";
			}
		} catch (Exception e) {
			logger.debug("Invalid city name==>");
			valid = false;
			e.printStackTrace();
			return "Invalid City";
		}
		return null;
	}

	private String checkMobileNo(String mobileNo) {

		if (mobileNo == null) {
			logger.debug("Mobile Number null");
			valid = false;
			return "Invalid Mobile Number";
		} else if (mobileNo.trim().equals("")) {
			logger.debug("Mobile Number null");
			valid = false;
			return "Invalid Mobile Number";
		} else if (!(onlyContainsNumbers(mobileNo))) {
			logger.debug("Invalid Mobile Number");
			valid = false;
			return "Invalid Mobile Number";
		} else if (mobileNo.length() != 10) {
			logger.debug("Mobile Number Must Be Of 10-digit");
			valid = false;
			return "Mobile Number Must Be Of 10-digit";
		} else {
			CustomerDTO customer = customerApi.getCustomerByMobileNo(mobileNo);
			if (customer != null) {
				logger.debug("Mobile Number Already Exists");
				valid = false;
				return "Mobile Number Already Exists";
			}
		}

		return null;
	}

	private String checkKYCMobileNo(String mobileNo) {

		if (mobileNo == null) {
			logger.debug("Mobile Number null");
			valid = false;
			return "Invalid Mobile Number";
		} else if (mobileNo.trim().equals("")) {
			logger.debug("Mobile Number null");
			valid = false;
			return "Invalid Mobile Number";
		} else if (!(onlyContainsNumbers(mobileNo))) {
			logger.debug("Invalid Mobile Number");
			valid = false;
			return "Invalid Mobile Number";
		} else if (mobileNo.length() != 10) {
			logger.debug("Mobile Number Must Be Of 10-digit");
			valid = false;
			return "Mobile Number Must Be Of 10-digit";
		} else {
			CustomerDTO customer = customerApi.getCustomerByMobileNo(mobileNo);
			if (!(mobileNo.equals(customer.getMobileNo()))) {
				if (customer != null) {
					logger.debug("Mobile Number Already Exists");
					valid = false;
					return "Mobile Number Already Exists";
				}
			}
		}

		return null;
	}

	private String checkUsername(String username) {
		if (username == null) {
			logger.debug("Address Username==>");
			valid = false;
			return "Invalid Address";
		} else if (username.trim().equals("")) {
			logger.debug("Address Username==>");
			valid = false;
			return "Invalid Username";
		} else {
			UserDTO user = userApi.getUserByUserName(username);
			if (user != null) {
				logger.debug("Username Already Exists");
				valid = false;
				return "Username Already Exists";
			}
		}
		return null;
	}

	private String checkEmail(String email) {
		if (email == null) {
			logger.debug("Email Required==>");
			valid = false;
			return "Invalid Email";
		} else if (email.trim().equals("")) {
			logger.debug("Email Required==>");
			valid = false;
			return "Invalid Email";
		}
		return null;
	}



	private boolean onlyContainsNumbers(String string) {
		try {
			Long.parseLong(string);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	private String checkGender(String gender) {

		if (gender == null) {
			logger.debug("Gender Required==>");
			valid = false;
			return "Please choose your gender";
		} else if (gender.trim().equals("")) {
			logger.debug("Gender Required==>");
			valid = false;
			return "Invalid Gender";
		}
		return null;
	}

	private String checkOccupation(String occupation) {

		if (occupation == null) {
			logger.debug("Occupation Required==>");
			valid = false;
			return "Invalid Occupation";
		} else if (occupation.trim().equals("")) {
			logger.debug("Occupation Required==>");
			valid = false;
			return "Invalid Occupation";
		}
		return null;
	}

}
