package com.boilerplate.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boilerplate.api.IUserApi;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.error.UserError;
import com.boilerplate.repositories.UserRepository;


@Component
public class UserValidation {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserApi userApi;

	@Autowired
	private UserRepository userRepository;


	private UserError userError;

	public UserError userValidation(UserDTO userDTO) {
		userError = new UserError();
		boolean valid = true;

		valid = valid && checkUserName(userDTO);
		valid = valid && checkTimeZone(userDTO);
		if (userDTO.getOperation().equals("add")) {
			valid = valid && checkPassword(userDTO);
			valid = valid && checkRePassword(userDTO);
			valid = valid && checkUserType(userDTO);
			valid = valid && checkObjectName(userDTO);
			valid = valid && checkUserTemplate(userDTO);
		}
		userError.setValid(valid);
		return userError;

	}

	private boolean checkUserName(UserDTO userDto) {

		if (userDto.getUsername() == null) {
			logger.debug("Username is null");
			userError.setUsername("Username Required");
			return false;
		} else if (userDto.getUsername().trim().equals("")) {
			logger.debug("Username is null");
			userError.setUsername("Username Required");
			return false;
		}

		if (userDto.getOperation().equals("add")) {
			UserDTO user = userApi.getUserByUserName(userDto.getUsername());
			if (user != null) {
				logger.debug("Username already exists");
				userError.setUsername("Username Already Exists");
				return false;
			}
		} else if (userDto.getOperation().equals("edit")) {
			UserDTO user = userApi.getUserById(userDto.getId());
			if (user != null) {
				if (!user.getUsername().equalsIgnoreCase(userDto.getUsername())) {
					user = userApi.getUserByUserName(userDto.getUsername());
					if (user != null) {
						logger.debug("Username already exists");
						userError.setUsername("Username Already Exists");
						return false;
					}
				}
			}

		}
		return true;
	}

	// Name validation
	private boolean checkName(UserDTO userDto) {

		if (userDto.getName() == null) {
			logger.debug("Username is null");
			userError.setUsername("Username Required");
			return false;
		} else if (userDto.getName().trim().equals("")) {
			logger.debug("name is null");
			userError.setUsername("Username Required");
			return false;
		}

		if (userDto.getOperation().equals("add")) {
			UserDTO user = userApi.getUserByUserName(userDto.getName());
			if (user != null) {
				logger.debug("Username already exists");
				userError.setUsername("Username Already Exists");
				return false;
			}
		} else if (userDto.getOperation().equals("edit")) {
			UserDTO user = userApi.getUserById(userDto.getId());
			if (user != null) {
				if (!user.getName().equalsIgnoreCase(userDto.getName())) {
					user = userApi.getUserByName(userDto.getName());
					if (user != null) {
						logger.debug("Username already exists");
						userError.setUsername("Username Already Exists");
						return false;
					}
				}
			}

		}
		return true;
	}

	private boolean checkPassword(UserDTO userDto) {

	

		if (userDto.getPassword() == null) {
			logger.debug("Password is null");
			userError.setPassword("Invalid Password");
			return false;
		} else if (userDto.getPassword().trim().equals("")) {
			logger.debug("Password is null");
			userError.setPassword("Invalid Password");
			return false;
		} 
		

		return true;
	}

	private boolean checkRePassword(UserDTO userDto) {

		if (!(userDto.getRepassword().equals(userDto.getPassword()))) {
			logger.debug("Password does not match");
			userError.setRepassword("Password Does Not Match");
			return false;
		}

		return true;
	}

	private boolean checkUserType(UserDTO userDto) {

		if (userDto.getUserType() == null) {
			logger.debug("UserType is null");
			userError.setUserType("UserType Required");
			return false;
		} else {
			if (userDto.getUserType().equals("")) {
				logger.debug("UserType is null");
				userError.setUserType("UserType Required");
				return false;
			}
		}

		return true;
	}

	private boolean checkObjectName(UserDTO userDto) {

		if (userDto.getObjectUserId() == null) {
			logger.debug("ObjectName is null");
			userError.setUserType("ObjectName Required");
			return false;
		} else {
			if (userDto.getObjectUserId() == 0) {
				logger.debug("ObjectName is null");
				userError.setUserType("ObjectName Required");
				return false;
			}
		}

		return true;
	}

	private boolean checkUserTemplate(UserDTO userDto) {

		if (userDto.getUserTemplateId() == null) {
			logger.debug("User Template is null");
			userError.setUserTemplate("User Template Required");
			return false;
		} else {
			if (userDto.getUserTemplateId() == 0) {
				logger.debug("User Template is null");
				userError.setUserTemplate("User Template Required");
				return false;
			}
		}

		return true;
	}

	private boolean checkTimeZone(UserDTO userDto) {

		/*
		 * if(userDto.getTimeZone()==null){ logger.debug("Timezone is null");
		 * userError.setTimeZone("Timezone Required"); return false; }else{
		 * if(userDto.getTimeZone().equals("")){
		 * logger.debug("UserType is null");
		 * userError.setTimeZone("Timezone Required"); return false; } }
		 */

		return true;

	}

	public String usernameValidation(String username) {

		if (username == null) {
			return "Username Required";
		} else if (username.trim().equals("")) {
			return "Username Required";
		}

		UserDTO user = userApi.getUserByUserName(username);
		if (user != null) {
			logger.debug("Username already exists");
			return "Username Already Exists";
		}

		return null;
	}

	
	

	private String checkAlphabatic(String password, String specialChar) {

		if (specialChar == null || specialChar.equals("")) {

			String mystr = "abcdefghijklmnopqrstuvwxyz";

			String[] s = password.split("");
			boolean valid = false;
			for (String ss : s) {
				if (mystr.contains(ss)) {
					valid = true;
					break;
				}
			}

			if (valid = false) {
				return "Password must contain a-z";
			}

		} else {
			Pattern lowerCase = Pattern.compile("[^a-z]");
			Pattern specialCharacter = Pattern.compile("[^" + specialChar + "]");
			Pattern pattern = Pattern.compile("[^a-z" + specialChar + "]");
			Matcher matcher = pattern.matcher(password);
			if (matcher.find()) {
				if (specialChar != null) {
					return "Password must only contain a-z and " + specialChar;
				} else {
					return "Password must only contain a-z";
				}
			} else {
				String[] characterArray = password.split("");
				boolean lowerCaseValid = false;
				boolean specialCharacterValid = false;
				for (String character : characterArray) {
					if (!(lowerCase.matcher(character).find())) {
						lowerCaseValid = true;
					}
					if (!(specialCharacter.matcher(character).find())) {
						specialCharacterValid = true;
					}
				}
				if (!lowerCaseValid || !specialCharacterValid) {
					if (specialChar != null) {
						return "Password must include lowercase letters and at least 1 special character i.e. "
								+ specialChar;
					} else {
						return "Password must only include lowercase letters";
					}
				}
			}
		}
		return null;
	}

	private String checkAlphaNumeric(String password, String specialChar) {

		if (specialChar == null || specialChar.equals("")) {

			String mystr = "abcdefghijklmnopqrstuvwxyz";
			String mystrone = "1234567890";

			String[] s = password.split("");
			boolean valid = false;

			boolean numvalid = false;

			for (String ss : s) {
				if (mystr.contains(ss)) {
					valid = true;
					break;
				}
			}

			for (String ss : s) {
				if (mystrone.contains(ss)) {
					numvalid = true;
					break;
				}
			}

			if (valid == false) {
				return "Password must contain a-z";
			} else if (numvalid == false) {
				return "Password must contain 0-9";
			}
		} else {
			Pattern lowerCase = Pattern.compile("[^a-z]");
			Pattern number = Pattern.compile("[^0-9]");
			Pattern specialCharacter = Pattern.compile("[^" + specialChar + "]");
			Pattern pattern = Pattern.compile("[^a-z0-9" + specialChar + "]");
			Matcher matcher = pattern.matcher(password);
			if (matcher.find()) {
				if (specialChar != null) {
					return "Password must only contain a-z, 0-9 and " + specialChar;
				} else {
					return "Password must only contain a-z and 0-9";
				}
			} else {
				String[] characterArray = password.split("");
				boolean lowerCaseValid = false;
				boolean numberValid = false;
				boolean specialCharacterValid = false;
				for (String character : characterArray) {
					if (!(lowerCase.matcher(character).find())) {
						lowerCaseValid = true;
					}
					if (!(specialCharacter.matcher(character).find())) {
						specialCharacterValid = true;
					}
					if (!(number.matcher(character).find())) {
						numberValid = true;
					}
				}
				if (!lowerCaseValid || !specialCharacterValid || !numberValid) {
					if (specialChar != null) {
						return "Password must include  lowercase letters, at least 1 number and special character i.e. "
								+ specialChar;
					} else {
						return "Password must include  lowercase letters and at least 1 number";
					}
				}
			}
		}
		return null;
	}

	private String checkCapsAlphabetic(String password, String specialChar) {

		if (specialChar == null || specialChar.equals("")) {

			String mystr = "abcdefghijklmnopqrstuvwxyz";
			String mystrone = "1234567890";
			String mystrtwo = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

			String[] s = password.split("");
			boolean valid = false;

			boolean numvalid = false;
			
			boolean capvalid = false;

			for (String ss : s) {
				if (mystr.contains(ss)) {
					valid = true;
					break;
				}
			}

			for (String ss : s) {
				if (mystrone.contains(ss)) {
					numvalid = true;
					break;
				}
			}
			
			for (String ss : s) {
				if (mystrtwo.contains(ss)) {
					capvalid = true;
					break;
				}
			}

			if (valid == false) {
				return "Password must contain a-z";
			} else if (numvalid == false) {
				return "Password must contain 0-9";
			}else if(capvalid==false){
				return "Password must contain A-Z";
			}
		} else {
			Pattern lowerCase = Pattern.compile("[^a-z]");
			Pattern upperCase = Pattern.compile("[^A-Z]");
			Pattern specialCharacter = Pattern.compile("[^" + specialChar + "]");
			Pattern pattern = Pattern.compile("[^a-zA-Z" + specialChar + "]");
			Matcher matcher = pattern.matcher(password);
			if (matcher.find()) {
				if (specialChar != null) {
					return "Password must only contain a-z, A-Z and " + specialChar;
				} else {
					return "Password must only contain a-z and A-Z";
				}
			} else {
				String[] characterArray = password.split("");
				boolean lowerCaseValid = false;
				boolean specialCharacterValid = false;
				boolean upperCaseValid = false;
				for (String character : characterArray) {
					if (!(lowerCase.matcher(character).find())) {
						lowerCaseValid = true;
					}
					if (!(specialCharacter.matcher(character).find())) {
						specialCharacterValid = true;
					}
					if (!(upperCase.matcher(character).find())) {
						upperCaseValid = true;
					}
				}
				if (!lowerCaseValid || !specialCharacterValid || !upperCaseValid) {
					if (specialChar != null) {
						return "Password must include both lower and uppercase letters and at least 1 special character i.e. "
								+ specialChar;
					} else {
						return "Password must include both lower and uppercase letters";
					}
				}
			}
		}
		return null;
	}

	private String checkCapsAlphaNumeric(String password, String specialChar) {
		Pattern lowerCase = Pattern.compile("[^a-z]");
		Pattern upperCase = Pattern.compile("[^A-Z]");
		Pattern number = Pattern.compile("[^0-9]");
		Pattern specialCharacter = Pattern.compile("[^" + specialChar + "]");
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9" + specialChar + "]");
		Matcher matcher = pattern.matcher(password);
		if (matcher.find()) {
			if (specialChar != null) {
				return "Password must only contain a-z, A-Z, 0-9 and " + specialChar;
			} else {
				return "Password must only contain a-z, A-Z and 0-9";
			}
		} else {

			String[] characterArray = password.split("");
			boolean lowerCaseValid = false;
			boolean numberValid = false;
			boolean specialCharacterValid = false;
			boolean upperCaseValid = false;
			for (String character : characterArray) {
				if (!(lowerCase.matcher(character).find())) {
					lowerCaseValid = true;
				}
				if (!(specialCharacter.matcher(character).find())) {
					specialCharacterValid = true;
				}
				if (!(number.matcher(character).find())) {
					numberValid = true;
				}
				if (!(upperCase.matcher(character).find())) {
					upperCaseValid = true;
				}
			}
			if (!lowerCaseValid || !specialCharacterValid || !numberValid || !upperCaseValid) {
				if (specialChar != null) {
					return "Password must include both lower and uppercase letters, at least 1 number and special character i.e. "
							+ specialChar;
				} else {
					return "Password must include both lower and uppercase letters and at least 1 number";
				}
			}
		}
		return null;
	}

	public String checkAddress(String address) {

		if (address == null) {
			logger.debug("Address null");
			return "Address Required";
		} else if (address.trim().equals("")) {
			logger.debug("Address null");
			return "Address Required";
		}

		return null;
	}

	public String checkMobileNo(String mobileNo) {

		if (mobileNo == null) {
			logger.debug("Mobile number null");
			return "Mobile number Required";
		} else if (mobileNo.trim().equals("")) {
			logger.debug("Mobile number null");
			return "Mobile number Required";
		} else {
			UserDTO user = userApi.getUserByMobileNo(mobileNo);
			if (user != null) {
				logger.debug("Mobile number already exists");
				return "Mobile number already exists";
			}
		}

		return null;
	}

}
