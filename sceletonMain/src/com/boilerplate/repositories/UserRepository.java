package com.boilerplate.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.boilerplate.entity.User;
import com.boilerplate.model.UserType;

public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	@Query("select u from User u where u.username=?1")
	User findByUsername(String username);
	
	@Query("select u from User u where u.usernameStartup=?1")
	User startupname(String username);
	
	
	User findByUserType(UserType u);
	
	@Query("select u from User u where u.mobileNo=?1")
	User findByUserMobile(String mobile);
	
	@Query("select u from User u where u.email=?1")
	User findByUserEmail(String email);
	
	@Query("select u from User u where u.userType <>'Admin'")
	List<User> findAllUserExceptAdmin();
	
	@Query("select u from User u where NOT u.username IN (?1)")
	List<User> findAllUserExceptDefaultAdmin(List<String> str);
	
	@Query("select u from User u order by u.username")
	List<User> findUser();
	
	@Query("select u from User u order by u.id desc")
	List<User> findUserByDesc();

	@Query("select u from User u where u.web_service.access_key=?1 and u.secret_key=?2")
	User findByAccessAndSecretKey(String accessKey, String secretKey);
	
	
	
	@Query("select u from User u where u.associatedId=?1")
	User findByAssociatedId(long associatedId);
	
	@Query("select u.username from User u where u.associatedId=?1")
	String findUserNameByAssociatedId(long associatedId);
	
	@Query("select u.username from User u where u.associatedId=?1 and u.authority=?2")
	String findUserNameByAssociatedIdAndAuthority(long associatedId,String authority);
	
	@Query("select u from User u where u.associatedId=?1 and u.authority=?2")
	User findUserNameByAssociatedIdAndAuthorityUser(long associatedId,String authority);
	
	@Query("select u from User u where u.id=?1 and u.userType=?2")
	User findUserNameByIdAndUserType(long id,UserType userType);
	
	@Query("select u from User u where u.userType=?2 and u.username like CONCAT(?1, '%') order by username desc 10")
	List<User> userDetectorTest(String name, UserType userType);
	
	@Query("select u from User u where u.associatedId=?1 and u.userType=?2")
	User findUserByAssociatedIdAndUserType(long associatedId, UserType userType);
	
	@Query("select u from User u where u.associatedId=?1 and u.authority=?2")
	User findUserByAssociatedIdAndAuthority(long associatedId, String authority);

	@Query("select u from User u where u.userType=?1")
	List<User> getAllUserByUserType(UserType userType);
	
	
	
}
