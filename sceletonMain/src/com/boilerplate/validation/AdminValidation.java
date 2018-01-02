package com.boilerplate.validation;

import java.util.HashMap;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boilerplate.api.IUserApi;
import com.boilerplate.entity.Admin;
import com.boilerplate.entity.City;
import com.boilerplate.entity.User;
import com.boilerplate.model.AdminDTO;
import com.boilerplate.model.Hashes;
import com.boilerplate.model.OperationType;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.UserType;
import com.boilerplate.model.error.AdminError;
import com.boilerplate.repositories.AdminRepository;
import com.boilerplate.repositories.CityRepository;
import com.boilerplate.repositories.UserRepository;

@Service
public class AdminValidation {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;


	@Autowired
	private IUserApi userApi;
	
	

	boolean valid = true;
	
	
	public AdminError adminValidation(AdminDTO adminDTO) {
		valid = true;
		AdminError adminError = new AdminError();
		adminError.setAddress(this.addressValidation(adminDTO.getAddress()));
		adminError.setFirstName(this.firstNameValidation(adminDTO.getFirstName()));
		adminError.setLastName(this.lastNameValidation(adminDTO.getLastName()));
		adminError.setCity(this.cityValidation(adminDTO.getCityId()));
		adminError.setMobileNo(this.mobileNoValidation(adminDTO.getMobileNo()));
		
		if(adminDTO.getOperation().equals(OperationType.add)){
			adminError.setUsername(this.checkUserName(adminDTO.getUsername()));
			adminError.setPassword(this.checkPassword(adminDTO.getPassword()));
			adminError.setRepassword(this.checkRePassword(adminDTO.getRepassword(), adminDTO.getPassword()));
		}
		
		adminError.setUserTemplate(this.checkUserTemplate(adminDTO.getUserTemplateId()));
		/*
		 * adminError.setFullName(this.uniqueValidation(adminDTO.getFullName()))
		 * ;
		 * adminError.setFullName(this.identifireValidationValidation(adminDTO.
		 * getFullName()));
		 */
		adminError.setValid(valid);
		return adminError;
	}

	private String identifireValidationValidation(String object) {

		if (object == null) {
			valid = false;
			return "IDentifier is null";

		} else if (object.trim().equals("")) {
			valid = false;
			return "identifier cant be null";
		} else if (object.length() < 3 || object.length() > 20) {
			valid = false;
			return "Length must be less than 20 or greater than 3";
		} else {
			return "";
		}
	}

	private String firstNameValidation(String object) {

		if (object == null) {
			valid = false;
			return "First Name is null";

		} else if (object.trim().equals("")) {
			valid = false;
			return "First Name cant be null";
		} else if (object.length() < 3 || object.length() > 20) {
			valid = false;
			return "Length must be less than 20 or greater than 2";
		} else {
			return "";
		}
	}

	private String lastNameValidation(String object) {

		if (object == null) {
			valid = false;
			return "Last Name is null";
		} else if (object.trim().equals("")) {
			valid = false;
			return "Last Name cant be null";
		} else if (object.length() < 3 || object.length() > 20) {
			valid = false;
			return "Length must be less than 20 or greater than 2";
		} else {
			return "";
		}
	}

	private String addressValidation(String object) {

		if (object == null) {
			valid = false;
			return "Address  is null";
		} else if (object.trim().equals("")) {
			valid = false;
			return "Address  cant be null";
		} else if (object.length() < 3 || object.length() > 20) {
			valid = false;
			return "Length must be less than 20 or greater than 2";
		} else {
			return "";
		}
	}

	private String mobileNoValidation(String object) {

		if (object == null) {
			valid = false;
			return "Mobile number is null";
		} else if (object.trim().equals("")) {
			valid = false;
			return "Mobile number is blank";
		} else if (object.length() != 10) {
			valid = false;
			return "Length must 10 digit";
		} else {
			try {
				Long.parseLong(object);
				return "";
			} catch (Exception e) {
				valid = false;
				return "Mobile number is not valid";
			}
		}
	}

	private String uniqueValidation(String object) {

		if (object != null) {
			Admin admin = adminRepository.findAdminByFullName(object);
			if (admin != null) {
				valid = false;
				return "Admin with name " + object + " already Exist";
			} else {
				return "";
			}

		} else {
			return "";
		}

	}

	private String cityValidation(Long object) {

		if (object == null) {
			valid = false;
			return "First Name is null";
		} else if (object == 0) {
			valid = false;
			return "City cant be null";
		} else {
			City city = cityRepository.findOne(object);
			if (city == null) {
				valid = false;
				return "City Not Valid";
			} else {
				return "";
			}
		}
	}

	public Hashes transferByAdminValidation(String toUser, double amount) {

		boolean valid = true;
		Hashes hashes = new Hashes();
		HashMap<String, String> myHash = new HashMap<String, String>();

		User destinationUser = userRepository.findByUsername(toUser);

		if (destinationUser == null) {
			valid = false;
			myHash.put("availabality", "No user found");
		} else if (destinationUser.getUserType() == UserType.ADMIN
				|| destinationUser.getUserType() == UserType.MERCHANT) {
			valid = false;
			myHash.put("availabality", "User not Valid");
		}

		hashes.setValid(valid);
		hashes.setMyHash(myHash);

		return hashes;
	}

	private String checkUserName(String username) {

		/*if (username == null) {
			logger.debug("Username is null");
			valid = false;
			return "Username Required";
		} else if (username.trim().equals("")) {
			logger.debug("Username is null");
			valid = false;
			return "Username Required";
		} else {
			UserDTO user = userApi.getUserByUserName(username);
			if (user != null) {
				logger.debug("Username already exists");
				valid = false;
				return "Username Already Exists";
			}
		}*/
		
		if (username == null) {
			logger.debug("Email is null");
			valid = false;
			return "Email Required";
		} else if (username.trim().equals("")) {
			logger.debug("Email is blank");
			valid = false;
			return "Email Required";
		} else {
		 try {
		      InternetAddress emailAddr = new InternetAddress(username);
		      emailAddr.validate();
		      UserDTO user = userApi.getUserByUserName(username);
				if (user != null) {
					logger.debug("Username already exists");
					valid = false;
					return "Email Already Exists";
				}
				
				
				
		   } catch (AddressException ex) {
			   valid = false;
				return "Email Not Valid";
		   }
		}

		return "";
	}

	private String checkPassword(String password) {

		if (password == null) {
			logger.debug("Password is null");
			valid = false;
			return "Password Required";
		} else {
			if (password.trim().equals("")) {
				logger.debug("Password is null");
				valid = false;
				return "Password Required";
			}
		}

		return "";
	}

	private String checkRePassword(String repassword, String password) {
		if (!(repassword.equals(password))) {
			logger.debug("Password does not match");
			valid = false;
			return "Password Does Not Match";
		}

		return "";
	}

	private String checkUserTemplate(String userTemplateId) {

		if (userTemplateId == null) {
			logger.debug("User Template is null");
			valid = false;
			return "User Template Required";
		} else if (userTemplateId.trim().equals("")) {
			logger.debug("User Template is null");
			valid = false;
			return "User Template Required";
		} else {
			if (userTemplateId.equals("0")) {
				logger.debug("User Template is null");
				valid = false;
				return "User Template Required";
			}
		}

		return "";
	}

}
