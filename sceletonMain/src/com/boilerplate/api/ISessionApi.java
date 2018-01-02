package com.boilerplate.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.boilerplate.entity.User;
import com.boilerplate.entity.UserSession;
import com.boilerplate.model.UserSessionDTO;
import com.boilerplate.session.UserDetailsWrapper;



public interface ISessionApi {
	
	void registerNewSession(String sessionId, UserDetailsWrapper principal);

	void removeSession(String tokenKey);

	UserSession getUserSession(String sessionId);

	void refreshSession(String sessionId);

	List<UserSession> getAllUserSession(long userId, boolean includeExpiredSessions);

	void expireSession(String sessionId);

	long countActiveSessions();

	Page<User> findOnlineUsers(Pageable page);

	Page<UserSession> findActiveSessions(Pageable page);

	void registerNewSession(String sessionId, User principal);

	List<UserSessionDTO> getAllActiveUser();

}
