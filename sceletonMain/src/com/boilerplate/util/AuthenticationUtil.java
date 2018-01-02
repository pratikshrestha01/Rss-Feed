package com.boilerplate.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.boilerplate.entity.User;
import com.boilerplate.session.UserDetailsWrapper;


public class AuthenticationUtil {

	public static final User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetailsWrapper) {
			return ((UserDetailsWrapper) principal).getUser();
		}
		return null;
	}
	
	public static void main(String[] args){
		
		String test = "test";
		String encodedStr = "";
	}
}
