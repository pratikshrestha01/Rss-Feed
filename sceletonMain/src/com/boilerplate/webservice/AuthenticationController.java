package com.boilerplate.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.boilerplate.api.IUserApi;
import com.boilerplate.api.IWebServiceApi;
import com.boilerplate.model.Status;
import com.boilerplate.model.WebServiceDTO;
import com.boilerplate.model.mobile.AuthenticationResponseDTO;
import com.boilerplate.model.mobile.AuthorizationResponseDTO;
import com.boilerplate.model.mobile.ResponseDTO;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.util.BugMail;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/Api")
public class AuthenticationController {

	@Autowired
	private IWebServiceApi webServiceApi;
	
	@Autowired
	private IUserApi userApi;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	/*public AuthenticationController(IWebServiceApi webServiceApi,
			IUserApi userApi, AuthenticationManager authenticationManager) {
		this.webServiceApi = webServiceApi;
		this.userApi = userApi;
		this.authenticationManager = authenticationManager;
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "/Authentication", produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<ResponseDTO> authentication(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDTO result = new ResponseDTO();
		try {
			String clientId = request.getHeader("api_id");
			String clientKey = request.getHeader("api_key");
			WebServiceDTO webService = webServiceApi.findByUserAndkey(clientId,
					clientKey);
			if (webService != null) {
				if (webService.getStatus().equals(Status.Active)) {
					AuthenticationResponseDTO key = new AuthenticationResponseDTO();
					key.setAccess_key(webService.getAccess_key());
					result.setStatus(ResponseStatus.SUCCESS);
					result.setMessage("Authentication Success");
					result.setDetails(key);
				} else {
					result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
					result.setMessage("Authentication Error");
					result.setDetails("Invalid Credentials");
				}
			} else {
				result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
				result.setMessage("Authentication Error");
				result.setDetails("Invalid Credentials");
			}
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		} catch (Exception e) {
			BugMail.Bugmail(e);
			result.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			result.setMessage("Authentication Error");
			result.setDetails("Internal Server Error");
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/Authorization", produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<ResponseDTO> authorization(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDTO result = new ResponseDTO();
		try {
			String clientId = request.getHeader("client_id");
			String clientKey = request.getHeader("client_key");
			String accessKey = request.getHeader("access_key");
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					clientId, clientKey);
			Authentication auth = authenticationManager.authenticate(token);
			if (auth.isAuthenticated()) {
				WebServiceDTO webService = webServiceApi
						.findByAccessKey(accessKey);
				if (webService != null) {
					if (webService.getStatus().equals(Status.Active)) {
						AuthorizationResponseDTO data=new AuthorizationResponseDTO();
						String secretkey=userApi.generateSecretKey(clientId,accessKey);
						data.setSecret_key(secretkey);
						result.setStatus(ResponseStatus.SUCCESS);
						result.setMessage("Authorization Success");
						result.setDetails(data);
					} else {
						result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
						result.setMessage("Authorization Error");
						result.setDetails("Invalid Credentials");
					}
				} else {
					result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
					result.setMessage("Authorization Error");
					result.setDetails("Invalid Credentials");
				}
			} else {
				result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
				result.setMessage("Authorization Error");
				result.setDetails("Invalid Credentials");
			}
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		} catch (Exception e) {
			BugMail.Bugmail(e);
			result.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			result.setMessage("Authorization Error");
			result.setDetails("Internal Server Error");
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		}

	}

}
