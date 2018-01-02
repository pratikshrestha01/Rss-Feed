package com.boilerplate.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.boilerplate.api.ICustomerApi;
import com.boilerplate.api.IOAuthClientApi;
import com.boilerplate.entity.OAuthClient;
import com.boilerplate.model.RestResponseDTO;
import com.boilerplate.model.mobile.ResponseDTO;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.util.BugMail;
import com.boilerplate.util.RandGenerator;

@Controller
@RequestMapping(value = "/oauth")
public class OAuthClientController {

	private Logger logger = LoggerFactory.getLogger(OAuthClientController.class);

	@Autowired
	private IOAuthClientApi oauthClientApi;
	



	@Autowired
	ICustomerApi customerApi;


	/*
	 * @RequestMapping(value="/registerClient",method=RequestMethod.GET) public
	 * String getClientDetailsForm(HttpServletRequest request){
	 * if(AuthenticationUtil.getCurrentUser()!=null){ String authority =
	 * AuthenticationUtil.getCurrentUser().getAuthority();
	 * if(authority.contains(Authorities.ADMINISTRATOR) &&
	 * authority.contains(Authorities.AUTHENTICATED)){ CsrfToken token =
	 * (CsrfToken) request.getAttribute(CsrfToken.class.getName());
	 * System.out.println(token.getParameterName()+" param name");
	 * System.out.println(token.getToken()+" token name");
	 * System.out.println(token.getHeaderName()+" header name");
	 * 
	 * return "OAuth/registerClient"; }else{ logger.debug("Unauthorized User");
	 * return "redirect:/"; } }else{ logger.debug("Unauthenticated User");
	 * return "redirect:/"; } }
	 */

	

	@RequestMapping(value = "/registerClient", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getClientDetailsForm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();

		OAuthClient oauthClient = oauthClientApi.registerClient(RandGenerator.getRanCode(15));
		System.out.println(oauthClient.getClient_id() + " id");
		System.out.println(oauthClient.getClient_secret() + " id");
		restResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
		restResponseDTO.setClientId(oauthClient.getClient_id());
		restResponseDTO.setClientSecret(oauthClient.getClient_secret());
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}
	


	@RequestMapping(method = RequestMethod.POST, value = "/clientregister", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<ResponseDTO> registerOauthClient(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO result = new ResponseDTO();
		try {

			String redirectUri = request.getParameter("redirect_uri");
			OAuthClient oauthClient = oauthClientApi.registerClient(redirectUri);

			if (oauthClient != null) {
				result.setStatus(ResponseStatus.SUCCESS);
				result.setMessage("Client Successfully Registered");
				result.setDetails(oauthClient);
			} else {
				result.setStatus(ResponseStatus.BAD_REQUEST);
				result.setMessage("Client Not Registered");
			}
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		} catch (Exception e) {
			BugMail.Bugmail(e);
			result.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			result.setMessage("Please contact your Administrator.");
			e.printStackTrace();
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		}
	}
	
	

}
