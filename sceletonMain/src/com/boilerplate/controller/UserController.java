package com.boilerplate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boilerplate.api.ICityApi;
import com.boilerplate.api.ICustomerApi;
import com.boilerplate.api.IUserApi;
import com.boilerplate.entity.User;
import com.boilerplate.mail.SendMail;
import com.boilerplate.model.CustomerDTO;
import com.boilerplate.model.RestResponseDTO;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.UserType;
import com.boilerplate.model.enumconstant.EmailURL;
import com.boilerplate.model.error.CustomerError;
import com.boilerplate.model.error.UserError;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.repositories.UserRepository;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.Authorities;
import com.boilerplate.util.BugMail;
import com.boilerplate.util.EnumGetter;
import com.boilerplate.validation.CustomerValidation;
import com.boilerplate.validation.UserValidation;

@Controller
public class UserController implements MessageSourceAware {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserApi userApi;


	@Autowired
	private UserValidation userValidation;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ICustomerApi customerApi;


	@Autowired
	private UserRepository urr;



	@Autowired
	private SendMail sendMail;

	@Autowired
	private ICityApi cityApi;

	@Autowired
	private CustomerValidation customerValidation;

	/*
	 * public UserController(IUserApi userApi, UserValidation userValidation,
	 * PasswordEncoder passwordEncoder) { this.userApi = userApi;
	 * this.userValidation = userValidation; this.passwordEncoder =
	 * passwordEncoder;
	 * 
	 * }
	 */

	@ModelAttribute("user")
	public UserDTO getUser() {
		return new UserDTO();
	}

	@RequestMapping(value = "/registeruser", method = RequestMethod.GET)
	public String registerUser(ModelMap modelMap) {
		modelMap.put("cityList", cityApi.getAllCity());
		return "User/registerUser";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String registerUser(CustomerDTO customerDTO, ModelMap modelMap, HttpServletRequest request) {
		try {
			CustomerError customerError = new CustomerError();
			customerError = customerValidation.customerValidation(customerDTO);
			if (customerError.isValid()) {
				customerDTO = customerApi.registerCustomer(customerDTO, request);
				modelMap.put("registrationStatus", true);
			} else {
				logger.debug("error occured while adding==>");
				modelMap.put("customer", customerDTO);
				modelMap.put("error", customerError);
				modelMap.put("cityList", cityApi.getAllCity());
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			logger.debug("exception" + e);
			e.printStackTrace();
		}
		return "User/registerUser";
	}

	@RequestMapping(value = "/getCsrf", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getCsrf(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();

		try {
			CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			System.out.println(token.getParameterName() + " param name");
			System.out.println(token.getToken() + " token name");
			System.out.println(token.getHeaderName() + " header name");
			restResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
			restResponseDTO.setMessage("csrfSuccess");
			restResponseDTO.setCsrfName(token.getParameterName());
			restResponseDTO.setCsrfValue(token.getToken());
		} catch (Exception e) {
			BugMail.Bugmail(e);
			e.printStackTrace();
			restResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
			restResponseDTO.setMessage("csrfSuccess");
			restResponseDTO.setCsrfName("No Csrf");
			restResponseDTO.setCsrfValue("No Csrf");
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/editUser")
	public ResponseEntity<RestResponseDTO> editUser(ModelMap modelMap,
			@RequestParam(value = "userId", required = true) String userId, HttpServletRequest request) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					UserDTO userDto = userApi.getUserById(Long.parseLong(userId));
					restResponseDTO.setDetail(userDto);
					restResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
				} else {
					restResponseDTO.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
				}
			} else {
				restResponseDTO.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			restResponseDTO.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editUser")
	public ResponseEntity<RestResponseDTO> editUser(@ModelAttribute("user") UserDTO userDTO,
			BindingResult bindingResult, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			System.out.println("post edit user errors:" + bindingResult.getAllErrors());
		}
		logger.debug("in post edit User==>");
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		userDTO.setOperation("edit");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.SUPER_ZONE)
					|| authority.contains(Authorities.REGIONAL_ZONE) || authority.contains(Authorities.ZONE)
					|| authority.contains(Authorities.POINT)) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					UserError userError = new UserError();
					userError = userValidation.userValidation(userDTO);
					if (userError.isValid()) {
						userDTO = userApi.editUser(userDTO);
						restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "User has been successfully edited",
								userDTO);
					} else {
						logger.debug("error while adding");
						restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error Occured", userError);
					}
				} catch (Exception e) {
					BugMail.Bugmail(e);
					logger.debug("exception" + e);
					restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Request Operator",
							new Object());
				}
			}
		}

		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/changeUserPassword")
	public String changeUserPassword(ModelMap modelMap, @RequestParam(value = "userId", required = true) String userId,
			HttpServletRequest request) {
		try {
			User user = userApi.getUserWithId(Long.parseLong(userId));
			modelMap.put("user", user);
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					if (user == null) {
						logger.debug("userDTO null==>" + user);
						return "redirect:/Static/500";
					} else {

						modelMap.put("user", user);
						return "User/changeUserPassword";
					}
				}
				return "/";

			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			return "redirect:/";
		}
		return "/";
	}

	

	
	@RequestMapping(method = RequestMethod.POST, value = "/changeUserPassword")
	public String changeUserPassword(@ModelAttribute("user") UserDTO userDTO, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

		logger.debug("in post editUser==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (userDTO == null) {
					logger.debug("userDTO null==>" + userDTO);
					return "redirect:/Static/500";
				} else {
					try {
						UserError error = new UserError();
						logger.debug("userDTO getusername==>" + userDTO.getUsername());
						error = userValidation.userValidation(userDTO);
						logger.debug("valid==>" + error.isValid());
						if (error.isValid()) {
							logger.debug("userDTO.getId()==>" + userDTO.getId());

							userApi.changePassword(userDTO.getId(), userDTO.getPassword());
							String successMessage = messageSource.getMessage("user.password.sucessfull",
									new Object[] { userDTO.getUsername() }, Locale.ROOT);
							redirectAttributes.addFlashAttribute("message", successMessage);

							return "redirect:/listUser";

						} else {
							logger.debug("error occured while adding==>");
							modelMap.put("error", error);
							modelMap.put("user", userDTO);
							return "User/changeUserPassword";
						}
					} catch (Exception e) {
						BugMail.Bugmail(e);
						logger.debug("e==>" + e);
						System.out.println("Exception:" + e.getMessage());
						return "redirect:/Static/500";
					}
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addUser")
	public String addUser(ModelMap modelMap, HttpServletRequest request) {
		try {

			System.out.println("auth " + AuthenticationUtil.getCurrentUser().getAuthority());
			logger.debug("in get addUser==>");
			User user = AuthenticationUtil.getCurrentUser();

			String[] ids = TimeZone.getAvailableIDs();
						List<CustomerDTO> customerList = customerApi.findCustomer();
			List<UserDTO> userList = userApi.findUser();
			List<String> timeZoneList = new ArrayList<>();
			for (String id : ids) {
				timeZoneList.add(id);
			}

			/*
			 * List<UserTemplateDTO> userTemplate =
			 * userTemplateApi.getAllUserTemplate();
			 */
			UserType[] userType = UserType.values();
			List<String> userTypes = new ArrayList<String>();

			userTypes = EnumGetter.getUsetTypeEnum();
			modelMap.put("userList", userList);
			modelMap.put("userTypes", userTypes);
			/* modelMap.put("userTemplate", userTemplate); */
			modelMap.put("timeZoneList", timeZoneList);
			
			modelMap.put("customerList", customerList);
			return "User/" + "addUser";
		} catch (Exception e) {
			BugMail.Bugmail(e);
			e.printStackTrace();
			return "redirect:/";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addUser")
	public ResponseEntity<RestResponseDTO> addUser(@ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,
			ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			System.out.println("post add user errors:" + bindingResult.getAllErrors());
		}
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		userDTO.setOperation("add");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.SUPER_ZONE)
					|| authority.contains(Authorities.REGIONAL_ZONE) || authority.contains(Authorities.ZONE)
					|| authority.contains(Authorities.POINT)) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					UserError userError = new UserError();
					userError = userValidation.userValidation(userDTO);
					if (userError.isValid()) {
						userDTO = userApi.saveUser(userDTO);
						restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "User has been successfully saved",
								userDTO);
					} else {
						logger.debug("error while adding");
						restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error Occured", userError);
					}
				} catch (Exception e) {
					BugMail.Bugmail(e);
					logger.debug("exception" + e);
					restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Request Operator",
							new Object());
				}
			}
		}

		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/changeMyPassword")
	public ResponseEntity<RestResponseDTO> changeMyPassword(@RequestParam String oldpass, @RequestParam String newpass,
			@RequestParam String repass, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, RedirectAttributes redirectAttributes) {

		RestResponseDTO restResponseDTO = new RestResponseDTO();

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.SUPER_ZONE)
					|| authority.contains(Authorities.REGIONAL_ZONE) || authority.contains(Authorities.ZONE)
					|| authority.contains(Authorities.POINT) || authority.contains(Authorities.CUSTOMER)
					|| authority.contains(Authorities.MERCHANT)) && authority.contains(Authorities.AUTHENTICATED)) {
				try {

					
				} catch (Exception e) {
					BugMail.Bugmail(e);
					logger.debug("exception" + e);
					e.printStackTrace();
					restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Request Operator",
							new Object());
				}
			}
		}

		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listUser")
	public String listUser(ModelMap modelMap, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				List<UserDTO> dtoList = new ArrayList<UserDTO>();
				dtoList = userApi.findAllUserExceptDefaultAdmin();
				modelMap.put("userList", dtoList);
				String param = (String) model.asMap().get("message");
				logger.debug("param:" + param);
				if (param != null && !param.trim().equals("")) {
					modelMap.put("message", param == null ? "" : messageSource.getMessage(param, null, Locale.ROOT));
				}
				logger.debug("userList:" + dtoList);

			}
			return "/User/listUser";
		}
		return "/";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteUser")
	public String deleteUser(ModelMap modelMap, Model model,
			@RequestParam(value = "userId", required = true) String userId, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				userApi.deleteUser(Long.parseLong(userId));
				redirectAttributes.addFlashAttribute("message", "user.delete.successfull");
				return "redirect:/listUser";
			}
			return "/";
		}
		return "/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getUserObject")
	public ResponseEntity<RestResponseDTO> getUserObject(ModelMap modelMap, HttpServletRequest request,
			@RequestParam(value = "firecounter", required = true) int firecounter,
			@RequestParam(value = "usertype", required = true) UserType usertype,
			@RequestParam(value = "objectName", required = true) String objectName) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.SUPER_ZONE)
					|| authority.contains(Authorities.REGIONAL_ZONE) || authority.contains(Authorities.ZONE)
					|| authority.contains(Authorities.POINT)) && authority.contains(Authorities.AUTHENTICATED)) {

				Object object = userApi.userDetector(objectName, usertype);
				restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, Integer.toString(firecounter), object);

			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getUser")
	public ResponseEntity<RestResponseDTO> getUser(ModelMap modelMap, HttpServletRequest request,
			@RequestParam(value = "firecounter", required = true) int firecounter,
			@RequestParam(value = "usertype", required = true) UserType usertype,
			@RequestParam(value = "objectName", required = true) String objectName) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.SUPER_ZONE) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.REGIONAL_ZONE) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.ZONE) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.POINT) && authority.contains(Authorities.AUTHENTICATED)) {

				Object object = userApi.userFinder(objectName, usertype);
				restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, Integer.toString(firecounter), object);

			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}

	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail) {
		RestResponseDTO restResponseDto = new RestResponseDTO();

		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);

		return restResponseDto;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/forgetpassword")
	public ResponseEntity<RestResponseDTO> forgetpassword(ModelMap modelMap,
			@RequestParam(value = "email", required = true) String email, HttpServletRequest request) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {

			if (email == null | "".equals(email.trim())) {

				restResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
				restResponseDTO.setMessage("email address is required");
				return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
			}

		/*	EmailDetailError error = emailValidation.emailValidation(email);*/

			/*if (!error.isValid()) {

				restResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
				restResponseDTO.setMessage(error.getError());
				return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
			}*/

			UserDTO userDTO = userApi.getUserWithEmail(email);

			if (userDTO == null) {
				restResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
				restResponseDTO.setMessage("this email is not registered");
				return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
			}

			String vcode = getRanCode(10);

			userApi.update(userDTO.getId(), vcode);

			/*
			 * String url = request.getScheme() + "://" + // "http" + "://
			 * request.getServerName() + // "myhost" ":" + // ":"
			 * request.getServerPort();
			 */

			String msg = "To reset the password click the link" + "\n<a href='" + EmailURL.getValue(request)
					+ "/resetpassword?vcode=" + vcode + "'>ResetPassword</a>";

			// String msg = "To reset the password click the link" + "\n<a
			// href='http://192.168.1.254:8080/resetpassword?vcode=" + vcode +
			// "'>ResetPassword</a>";

			sendMail.sendMail(email, msg);

			restResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
			restResponseDTO.setMessage("reset password link sent to your email check it out");

		} catch (Exception e) {
			BugMail.Bugmail(e);
			restResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
			restResponseDTO.setMessage("unable to reset password");
			return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
		}

		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resetpassword")
	public String resetPassword(@RequestParam("vcode") String vcode, HttpServletRequest request, ModelMap map,
			RedirectAttributes redirectAttributes) {

		if (vcode == null | "".equals(vcode.trim())) {
			redirectAttributes.addFlashAttribute("message", "invalid verification code");
			return "redirect:/";
		}

		if (vcode.length() != 10) {
			redirectAttributes.addFlashAttribute("message", "invalid verification code");
			return "redirect:/";
		}

		UserDTO userDTO = userApi.getUserWithVcode(vcode);

		if (userDTO == null) {

			redirectAttributes.addFlashAttribute("message", "invalid verification code");

			return "redirect:/";
		}

		map.put("vcode", vcode);

		return "User/changeUserPassword";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/resetpassword")
	public String resetpassword(ModelMap modelMap, @RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "repassword", required = true) String repassword,
			@RequestParam(value = "vcode", required = true) String vcode, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		try {

			if (vcode == null | "".equals(vcode.trim())) {
				redirectAttributes.addFlashAttribute("message", "verification code is required");
				return "redirect:/resetpassword?vcode=" + vcode;
			}

			if (vcode.length() != 10) {
				redirectAttributes.addFlashAttribute("message", "invalid verification code");
				return "redirect:/resetpassword?vcode=" + vcode;
			}

			UserDTO userDTO = userApi.getUserWithVcode(vcode);
			if (userDTO == null) {
				redirectAttributes.addFlashAttribute("message", "invalid verification code");
				return "redirect:/resetpassword?vcode=" + vcode;
			}

			// check password criteria
		/*	String error = userValidation.passwordValidation(password, repassword);
			if (error != null) {
				redirectAttributes.addFlashAttribute("message", error);
				return "redirect:/resetpassword?vcode=" + vcode;
			}*/

			userApi.resetPassword(vcode, password);

			redirectAttributes.addFlashAttribute("message", "password successfully changed please login");

		} catch (Exception e) {
			BugMail.Bugmail(e);
			redirectAttributes.addFlashAttribute("message", "unable to reset your password");

			e.printStackTrace();
			return "redirect:/";
		}

		return "redirect:/";
	}

	public static String getRanCode(int length) {

		String mystr = "abcdefghijklmnopqrstuvwxyz";
		String[] array = mystr.split("");
		String restr = "";
		for (int i = 0; i < length; i++) {
			int nm = (int) (Math.random() * mystr.length());
			restr += array[nm];
		}

		return restr;

	}

}
