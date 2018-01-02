package com.boilerplate.startup;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.boilerplate.entity.Admin;
import com.boilerplate.entity.User;
import com.boilerplate.model.Status;
import com.boilerplate.model.UserType;
import com.boilerplate.repositories.AdminRepository;
import com.boilerplate.repositories.UserRepository;
import com.boilerplate.util.Authorities;
import com.boilerplate.util.StringConstants;


@Component
public class FirstAdminCreator {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PendingFundTimer pendingFundTimer;

	@Autowired
	private CountryStateCity countryStateCity;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public static boolean singletone=false;
	@PostConstruct
	public void create() {

		

		System.out.println("roshan");
		
		if(singletone==false) {
			singletone=true;
			pendingFundTimer.Starter();
		}
		
		
		createUser(StringConstants.USER_ADMIN, UserType.ADMIN,
				Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED, "123456", Status.Active,
				"defaultUserAdmin");

	}

	private void createUser(String userName, UserType userType, String authorities, String password, Status status, String userTemplate) {
		User user = userRepository.startupname(userName);

		if (user == null) {

			Admin admin = new Admin();
			/*
			 * adminRepository.findAdminByFullName(userName); if (admin !=
			 * null){ return ; }else{ admin= }
			 */

			if (userType == userType.ADMIN) {
				admin = new Admin();

				admin.setFirstName("admin");
				admin.setSuperAdmin(true);
				admin.setMobileNo("0000000000");
				admin.setRegistered(true);
				admin.setFullName("admin");
				admin = adminRepository.save(admin);

			}
			user= new User();
			user.setUsername(userName);
			user.setUserType(userType);
			user.setAuthority(authorities);
			user.setPassword(passwordEncoder.encode(password));
			user.setCreated(new Date());
			user.setStatus(status);
			user.setUsernameStartup(userName);
			userRepository.save(user);

		}
		
	}

}
