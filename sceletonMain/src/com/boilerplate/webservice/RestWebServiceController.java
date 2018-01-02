package com.boilerplate.webservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.boilerplate.api.ICustomerApi;
import com.boilerplate.api.IUserApi;
import com.boilerplate.model.mobile.ResponseDTO;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.session.SessionLoggingStrategy;
import com.boilerplate.session.UserDetailsWrapper;
import com.boilerplate.util.BugMail;
import com.boilerplate.validation.UserValidation;

@Controller
@RequestMapping("/rest/api")
public class RestWebServiceController {

	@Autowired
	private IUserApi userService;


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private SessionLoggingStrategy sessionLoggingStrategy;

	@Autowired
	ICustomerApi customerApi;

	@Autowired
	private IUserApi userApi;



	@Autowired
	private UserValidation userValidation;

	@RequestMapping(method = RequestMethod.POST, value = "/userlogin", produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<ResponseDTO> doLogin(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO result = new ResponseDTO();
		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");

			boolean auth = Authentication(username, password, request);
			if (auth) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				Object principal = authentication.getPrincipal();
				if (principal instanceof UserDetailsWrapper) {
					((UserDetailsWrapper) principal).getUser();
				}

				sessionLoggingStrategy.onAuthentication(authentication, request, response);

				result.setMessage("Login Successful");
				result.setStatus(ResponseStatus.SUCCESS);
				return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);

			} else {
				result.setStatus(ResponseStatus.FAILURE);
				result.setMessage("Username or Password Invalid");
				return new ResponseEntity<ResponseDTO>(result, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			BugMail.Bugmail(e);
			result.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			result.setMessage("Please contact your Administrator.");
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public boolean Authentication(String userName, String password, HttpServletRequest request)
			throws ServletException, IOException, Exception {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
			Authentication auth = authenticationManager.authenticate(token);

			SecurityContext securityContext = SecurityContextHolder.getContext();
			if (auth.isAuthenticated()) {
				securityContext.setAuthentication(auth);
				SecurityContextHolder.getContext().setAuthentication(auth);
				HttpSession session = request.getSession(true);
				session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
				return true;
			} else {
				SecurityContextHolder.getContext().setAuthentication(null);
				return false;
			}

		} catch (Exception e) {
			BugMail.Bugmail(e);
			SecurityContextHolder.getContext().setAuthentication(null);
			return false;
		}

	}

	
	/*
	 * @RequestMapping(method = RequestMethod.POST, value = "/rechargecard",
	 * produces = { MediaType.APPLICATION_JSON_VALUE })
	 * ResponseEntity<RestResponseDTO> getRechargePin(HttpServletRequest
	 * request, HttpServletResponse response) { RestResponseDTO result = new
	 * RestResponseDTO(); try {
	 * 
	 * User user =
	 * userService.getUserByUsername(AuthenticationUtil.getCurrentUser().
	 * getUsername());
	 * 
	 * if (user != null && user.getAuthority().contains(Authorities.POINT)) {
	 * HashMap<String, String> hashRequest = new HashMap<>(); String serviceId =
	 * request.getParameter("service_identifier"); String transactionId = "pin";
	 * String amount = request.getParameter("payment_amount");
	 * hashRequest.put("cardQuantity", request.getParameter("cardQuantity"));
	 * 
	 * Hashes hashes =
	 * merchantPaymentValidation.generalMerchantValidation(serviceId,
	 * transactionId, amount); if (hashes.isValid()) { HashMap<String, String>
	 * hashResponse = merchatPaymentService.merchantPayment(serviceId,
	 * transactionId, Double.parseDouble(amount),
	 * AuthenticationUtil.getCurrentUser().getId(),hashRequest);
	 * 
	 * if (hashResponse.get("status").equals("success")) {
	 * 
	 * List<PinResponse> pinResponseList = new ArrayList<>(); if
	 * (hashResponse.get("recharge") != null) { if
	 * (hashResponse.get("recharge").equals("true")) { if
	 * (hashResponse.get("transactionIdentifier") != null) { TransactionDto
	 * transactionDto = transactionService
	 * .findById(Long.parseLong(hashResponse.get("transactionIdentifier")));
	 * result.setDetail(transactionDto);
	 * 
	 * } result.setPinNo(true); if (hashResponse.get("size") != null) { int size
	 * = Integer.parseInt(hashResponse.get("size")); for (int i = 0; i < size;
	 * i++) { PinResponse pinResponse = new PinResponse();
	 * pinResponse.setPinNo(hashResponse.get("pinNumber" + i));
	 * pinResponse.setSerialNo(hashResponse.get("serialNumber" + i));
	 * pinResponse.setExpiryDate(hashResponse.get("expiryDate" + i));
	 * pinResponseList.add(pinResponse); } } else { PinResponse pinResponse =
	 * new PinResponse();
	 * 
	 * pinResponse.setPinNo(hashResponse.get("pinNumber"));
	 * pinResponse.setSerialNo(hashResponse.get("serialNumber")); if
	 * (hashResponse.get("expiryDate") != null) {
	 * pinResponse.setExpiryDate(hashResponse.get("expiryDate")); }
	 * pinResponseList.add(pinResponse); }
	 * result.setPinResponse(pinResponseList); } } else { if
	 * (hashResponse.get("transactionIdentifier") != null) { TransactionDto
	 * transactionDto = transactionService
	 * .findById(Long.parseLong(hashResponse.get("transactionIdentifier")));
	 * result.setDetail(transactionDto);
	 * 
	 * } } // responseDTO.setPinNo(hashResponse.get("pinNo"));
	 * result.setStatus(ResponseStatus.SUCCESS.getKey());
	 * result.setResponseStatus(ResponseStatus.SUCCESS);
	 * result.setMessage("Service Accomplish"); return new
	 * ResponseEntity<RestResponseDTO>(result, HttpStatus.OK); } else {
	 * result.setResponseStatus(ResponseStatus.BAD_REQUEST);
	 * result.setMessage("Service failed"); return new
	 * ResponseEntity<RestResponseDTO>(result, HttpStatus.OK); } } else {
	 * result.setResponseStatus(ResponseStatus.VALIDATION_FAILED);
	 * result.setMessage("Request Not Valid");
	 * result.setDetail(hashes.getMyHash()); return new
	 * ResponseEntity<RestResponseDTO>(result, HttpStatus.OK); } } else {
	 * result.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
	 * result.setMessage("You do not have Authorization."); return new
	 * ResponseEntity<RestResponseDTO>(result, HttpStatus.OK); }
	 * 
	 * } catch (Exception e) {
	 * result.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
	 * result.setMessage("Please contact your Administrator.");
	 * e.printStackTrace(); return new ResponseEntity<RestResponseDTO>(result,
	 * HttpStatus.OK); } }
	 */

		

}
