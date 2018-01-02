package com.boilerplate.api;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONException;

import com.boilerplate.entity.User;
import com.boilerplate.model.Status;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.UserType;
import com.boilerplate.util.ClientException;

public interface IUserApi {

	UserDTO saveUser(UserDTO userDTO);

	List<UserDTO> getAllUser();

	User getUserWithId(long userId);
	
	User getUserByUsername(String name);

	List<UserDTO> findUser();

	List<UserDTO> findAllUserExceptAdmin();

	List<UserDTO> findAllUserExceptDefaultAdmin();

	UserDTO editUser(UserDTO userDTO) throws IOException, JSONException;

	void deleteUser(Long parseLong);

	String generateSecretKey(String clientId, String accessKey) throws ClientException;

	User findByAccessAndSecretKey(String accessKey, String secretKey);

	void changePassword(long userId,String newPassword) throws IOException , JSONException;
	
	UserDTO getUserByUserName(String userName);
	
	Object userDetector(String name, UserType userType);
	
	Object userFinder(String name, UserType userType);
	
	Object getUnregisteredUser (UserType userType);
	
	String getVcodeWithEmailAddress(String emailAdrress);
	
	void resetPassword(String vcode, String password);
	
	UserDTO getUserWithVcode(String vcode);
	
	UserDTO getUserWithEmail(String email);
	
	//test//
	List<UserDTO> userDetectorTest(String name, UserType userType);
	
	void createUser(String userName,UserType userType,String authorities,String password,Status status, TimeZone timeZone,long associatedId);
	
	UserDTO saveUser(String userName,UserType userType,String authorities,String password,Status status, TimeZone timeZone,long associatedId);
	
	UserDTO findUserByAssociatedIdAndUserType(long associatedId, UserType userType);
	
	UserDTO getUserById(long userId);

	public List<UserDTO> getAllUserByUserType(UserType userType);

	
	void favouriteService(long id, String like);

	UserDTO getUserByName(String name);

	void changeMyPassword(String password);

	UserDTO editAssociatedUsers(UserDTO userDto);

	UserDTO getUserByMobileNo(String mobileNo);

	UserDTO editMobileNo(String mobileNo, long userId);
	
	void update(Long userId , String vcode);

	void emailVerificationFlip(Long userId);

}
