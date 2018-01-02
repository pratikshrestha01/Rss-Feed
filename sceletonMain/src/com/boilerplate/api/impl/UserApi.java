package com.boilerplate.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boilerplate.api.IAdminApi;
import com.boilerplate.api.ICustomerApi;
import com.boilerplate.api.IUserApi;
import com.boilerplate.entity.Admin;
import com.boilerplate.entity.Customer;
import com.boilerplate.entity.User;
import com.boilerplate.model.Status;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.UserType;
import com.boilerplate.repositories.AdminRepository;
import com.boilerplate.repositories.CustomerRepository;
import com.boilerplate.repositories.UserRepository;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.Authorities;
import com.boilerplate.util.ClientException;
import com.boilerplate.util.ConvertUtil;

@Service
public class UserApi implements IUserApi {

	@Autowired
	private UserRepository userRepository;



	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ICustomerApi CustomerApi;

	

	@Autowired
	private IAdminApi adminApi;
	
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	

	
	
	/*
	 * public UserApi(UserRepository userRepository, WebServiceRepository
	 * webServiceRepository, PasswordEncoder passwordEncoder) {
	 * this.userRepository = userRepository; this.webServiceRepository =
	 * webServiceRepository; this.passwordEncoder = passwordEncoder; }
	 */

	@Override
	public UserDTO saveUser(UserDTO userDTO) {
		String accountno = "";
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setUserType(userDTO.getUserType());
		user.setStatus(Status.Active);
		user.setAssociatedId(userDTO.getObjectUserId());
		/* user.setTimeZone(userDTO.getTimeZone()); */
				
		// user.setUserTemplate(userTemplateRepository.findByUserTemplate(userDTO.getUserTemplate()));

		if (userDTO.getUserType() == UserType.ADMIN) {
			Admin admin = adminRepository.findOne(user.getAssociatedId());
			adminApi.registerAdmin(userDTO.getObjectUserId());
			user.setAuthority(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED);
			user.setMobileNo(admin.getMobileNo());

		} else if (userDTO.getUserType() == UserType.CUSTOMER) {
			Customer customer = customerRepository.findOne(userDTO.getObjectUserId());
			CustomerApi.registerCustomer(userDTO.getObjectUserId());
			user.setAuthority(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED);
			user.setMobileNo(customer.getMobileNo());
			accountno = customer.getAccountNo();
			user.setAccountNo(accountno);

		}
		
			user = userRepository.save(user);

		return ConvertUtil.convertUser(user);
	}
	


	@Override
	public void changeMyPassword(String password) {
		User user = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);

	}

	@Override
	public UserDTO editUser(UserDTO userDto) throws IOException, JSONException {
		User user = userRepository.findOne(userDto.getId());
		/* user.setTimeZone(userDto.getTimeZone()); */
		user.setUsername(userDto.getUsername());

		return ConvertUtil.convertUser(userRepository.save(user));
	}


	public void changePassword(long userId, String newPassword) throws IOException, JSONException {
		User user = userRepository.findOne(userId);
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Override
	public List<UserDTO> getAllUser() {
		List<User> copy = ConvertUtil.convertIterableToList(userRepository.findAll());
		return ConvertUtil.ConvertUserToDTO(copy);
	}

	@Override
	public User getUserWithId(long userId) {
		return userRepository.findOne(userId);

	}
	
	@Override
	public User getUserByUsername(String name) {
		return userRepository.findByUsername(name);

	}

	@Override
	public List<UserDTO> findAllUserExceptAdmin() {
		List<User> copy = ConvertUtil.convertIterableToList(userRepository.findAllUserExceptAdmin());
		return ConvertUtil.ConvertUserToDTO(copy);

	}

	public List<UserDTO> findUser() {
		List<User> userList = new ArrayList<User>();

		User requester = AuthenticationUtil.getCurrentUser();

		
		return ConvertUtil.ConvertUserToDTO(userList);
	}

	@Override
	public List<UserDTO> findAllUserExceptDefaultAdmin() {
		List<String> str = new ArrayList<>();
		str.add("admin");
		str.add("sysadmin");
		str.add(AuthenticationUtil.getCurrentUser().getUsername());
		List<User> copy = ConvertUtil.convertIterableToList(userRepository.findAllUserExceptDefaultAdmin(str));
		return ConvertUtil.ConvertUserToDTO(copy);
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.delete(userId);
	}



	@Override
	public User findByAccessAndSecretKey(String accessKey, String secretKey) {
		User u = userRepository.findByAccessAndSecretKey(accessKey, secretKey);
		return u;
	}

	@Override
	public UserDTO getUserByUserName(String userName) {
		User user = userRepository.findByUsername(userName);
		if (user != null) {
			return ConvertUtil.convertUser(user);
		} else {
			return null;
		}
	}

	@Override
	public void createUser(String userName, UserType userType, String authorities, String password, Status status,
			TimeZone timeZone, long associatedId) {
		User user = userRepository.findByUsername(userName);

		if (user == null) {
			user = new User();
			user.setUsername(userName);
			user.setUserType(userType);
			user.setAuthority(authorities);
			user.setPassword(passwordEncoder.encode(password));
			user.setCreated(new Date());
			user.setStatus(status);
			/* user.setTimeZone(timeZone.getID()); */
			user.setAssociatedId(associatedId);
			;
			userRepository.save(user);
		}
	}

	@Override
	public UserDTO saveUser(String userName, UserType userType, String authorities, String password, Status status,
			TimeZone timeZone, long associatedId) {
		User user = userRepository.findByUsername(userName);

		if (user == null) {
			user = new User();
			user.setUsername(userName);
			user.setUserType(userType);
			user.setAuthority(authorities);
			user.setPassword(passwordEncoder.encode(password));
			user.setCreated(new Date());
			user.setStatus(status);
			/* user.setTimeZone(timeZone.getID()); */
			user.setAssociatedId(associatedId);
			;
			user = userRepository.save(user);
		}

		return ConvertUtil.convertUser(user);
	}

	
	@Override
	public List<UserDTO> userDetectorTest(String name, UserType userType) {
		List<User> user = userRepository.userDetectorTest(name, userType);
		return ConvertUtil.ConvertUserToDTO(user);
	}

	
	

	@Override
	public UserDTO findUserByAssociatedIdAndUserType(long associatedId, UserType userType) {
		User user = userRepository.findUserByAssociatedIdAndUserType(associatedId, userType);
		return ConvertUtil.convertUser(user);
	}

	@Override
	public UserDTO getUserById(long userId) {
		return ConvertUtil.convertUser(userRepository.findOne(userId));
	}

	@Override
	public List<UserDTO> getAllUserByUserType(UserType userType) {
		List<User> user = userRepository.getAllUserByUserType(userType);
		return ConvertUtil.ConvertUserToDTO(user);
	}

	@Override
	public UserDTO getUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public UserDTO getUserByMobileNo(String mobileNo) {
		User user = userRepository.findByUserMobile(mobileNo);
		if(user!=null){
			return ConvertUtil.convertUser(user);
		}
		return null;
	}

	@Override
	public UserDTO editMobileNo(String mobileNo, long userId) {
		User user = userRepository.findOne(userId);
		user.setMobileNo(mobileNo);
		user = userRepository.save(user);
		return ConvertUtil.convertUser(user);
	}

	@Override
	public String getVcodeWithEmailAddress(String emailAddress) {
		// TODO Auto-generated method stub
		return null; /*userRepository.getVcode(emailAddress);*/
	}

	@Override
	public void resetPassword(String vcode , String password) {
		
		User user = null; // userRepository.getUserWithVcode(vcode, new Date());
		
		user.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(user);
		
	}

	@Override
	public UserDTO getUserWithVcode(String vcode) {
		// TODO Auto-generated method stub
		return null; //ConvertUtil.convertUser(userRepository.getUserWithVcode(vcode, new Date()));
	}

	@Override
	public UserDTO getUserWithEmail(String email) {
		// TODO Auto-generated method stub
		User user = null; // userRepository.getUserWithEmail(email);
		return ConvertUtil.convertUser(user);
	}

	@Override
	public void update(Long userId, String vcode) {
		

		User user = userRepository.findOne(userId);
		
		user.setVcode(vcode);
		
		 Date oldDate = new Date(); // oldDate == current time
		 Date newDate = new Date(oldDate.getTime() + TimeUnit.HOURS.toMillis(2)); // Adds 2 hours
		
		user.setExpire(newDate);
		
		userRepository.save(user);
		
	}
	
	
	@Override
	public void emailVerificationFlip(Long userId) {
		User user= userRepository.findOne(userId);
		user.setEmailVerification(true);
		userRepository.save(user);
	}



	@Override
	public String generateSecretKey(String clientId, String accessKey) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object userDetector(String name, UserType userType) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object userFinder(String name, UserType userType) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object getUnregisteredUser(UserType userType) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void favouriteService(long id, String like) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public UserDTO editAssociatedUsers(UserDTO userDto) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
