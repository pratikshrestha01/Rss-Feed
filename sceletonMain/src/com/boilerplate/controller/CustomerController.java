package com.boilerplate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boilerplate.api.ICityApi;
import com.boilerplate.api.ICustomerApi;
import com.boilerplate.api.IUserApi;
import com.boilerplate.model.CityDTO;
import com.boilerplate.model.CustomerDTO;
import com.boilerplate.model.RestResponseDTO;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.UserType;
import com.boilerplate.model.error.CustomerError;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.Authorities;
import com.boilerplate.util.BugMail;
import com.boilerplate.validation.CustomerValidation;

@Controller
public class CustomerController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private ICityApi cityApi;

	@Autowired
	private CustomerValidation customerValidation;


	@Autowired
	private IUserApi userApi;


	private MessageSource messageSource;

	/*
	 * public CustomerController(ICustomerApi customerApi, CustomerValidation
	 * customerValidation, ICountryApi countryApi) { this.customerApi =
	 * customerApi; this.customerValidation = customerValidation;
	 * this.countryApi = countryApi; }
	 */

	@ModelAttribute("customer")
	public CustomerDTO getUser() {
		return new CustomerDTO();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/messages")
	public String getRegistrationSuccess(ModelMap modelMap,
			@RequestParam(value = "message", required = true) String message,

	HttpServletRequest request) {
		logger.debug("in messages==>" + messageSource.getMessage(message, null, Locale.ROOT));
		modelMap.put("message", message == null ? "" : messageSource.getMessage(message, null, Locale.ROOT));
		return "Message/" + "messages";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editCustomer")
	public ResponseEntity<RestResponseDTO> editCustomer(ModelMap modelMap,
			@RequestParam(value = "customerId", required = true) String customerId, HttpServletRequest request) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					CustomerDTO customerDto = customerApi.getCustomerById(Long.parseLong(customerId));
					restResponseDTO.setDetail(customerDto);
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

	@RequestMapping(method = RequestMethod.POST, value = "/editCustomer")
	public ResponseEntity<RestResponseDTO> editCustomer(@ModelAttribute("customer") CustomerDTO customerDTO,
			BindingResult bindingResult, ModelMap modelMap, HttpServletRequest request,
			RedirectAttributes redirectAttributes, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			System.out.println("edit Customers has errors: " + bindingResult.getAllErrors());
		}

		logger.debug("in post edit Customer==>");
		customerDTO.setOperation("edit");
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.SUPER_ZONE) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.REGIONAL_ZONE) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.ZONE) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.POINT) && authority.contains(Authorities.AUTHENTICATED)) {

				try {
					CustomerError customerError = new CustomerError();
					customerError = customerValidation.customerValidation(customerDTO);
					if (customerError.isValid()) {
						customerDTO = customerApi.editCustomer(customerDTO);
						restResponseDTO = getResponseDto(ResponseStatus.SUCCESS,
								"Customer has been successfully edited", customerDTO);
					} else {
						logger.debug("error occured while adding==>");
						restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error Occured",
								customerError);

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

	@RequestMapping(method = RequestMethod.GET, value = "/addCustomer")
	public String addCustomer(@RequestParam(value = "pointId", required = false) Long pointId, ModelMap modelMap,
			Model model, HttpServletRequest request) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.SUPER_ZONE) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.REGIONAL_ZONE)
								&& authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.ZONE) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.POINT) && authority.contains(Authorities.AUTHENTICATED)) {

					List<CustomerDTO> customerList = new ArrayList<CustomerDTO>();
					List<CityDTO> cityList = new ArrayList<CityDTO>();
					customerList = customerApi.getAllCustomer();

					if (pointId == null || pointId == 0) {
						customerList = customerApi.getAllCustomer();
					} else {
						customerList = customerApi.getAllCustomerBYPointId(pointId);
						modelMap.put("mySubException", true);
					}
					for (CustomerDTO customer : customerList) {
						UserDTO user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.CUSTOMER);
						customer.setUsername(user.getUsername());
					}
					cityList = cityApi.getAllCity();

				
					modelMap.put("myException",
							(AuthenticationUtil.getCurrentUser().getUserType() == UserType.POINT) ? "yes" : "");
					modelMap.put("myParentException",
							(AuthenticationUtil.getCurrentUser().getUserType() == UserType.ADMIN) ? "yes" : "");
					modelMap.put("cityList", cityList);
					modelMap.put("customerList", customerList);
					if (authority.contains(Authorities.POINT) && authority.contains(Authorities.AUTHENTICATED)) {
						return "Customer/" + "PointCustomer";
					}
					return "Customer/" + "addCustomer";

				}
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/viewcustomerstatement")
	public String viewCustomerTransaction(@RequestParam long id, ModelMap modelMap, Model model,
			HttpServletRequest request) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.POINT) && authority.contains(Authorities.AUTHENTICATED)) {

					

					return "Customer/" + "viewCustomerStatement";

				}
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			return "redirect:/";
		}
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/changeCustomerProfile")
	public String changeCustomerProfile(@RequestParam long id, @RequestParam long customerId,
			HttpServletRequest request) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

					try {
						customerApi.changeProfile(id, customerId);
						return "Success";
					} catch (Exception e) {
						BugMail.Bugmail(e);
						e.printStackTrace();
						return "failure";
					}

				}
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			return "failure";
		}
		return "failure";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addCustomer")
	public ResponseEntity<RestResponseDTO> addCustomer(@ModelAttribute("customer") CustomerDTO customerDTO,
			BindingResult bindingResult, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			System.out.println("Add Customers has errors: " + bindingResult.getAllErrors());
		}

		logger.debug("in post addCustomer==>");
		customerDTO.setOperation("add");
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			System.out.println("testing for superagent " + authority);
			if (authority.contains(Authorities.POINT) && authority.contains(Authorities.AUTHENTICATED)) {

				try {
					CustomerError customerError = new CustomerError();
					customerError = customerValidation.customerValidation(customerDTO);
					if (customerError.isValid()) {
						customerDTO = customerApi.saveCustomer(customerDTO, request);
						restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "Customer has been successfully saved",
								customerDTO);

					} else {
						logger.debug("error occured while adding==>");
						restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error Occured",
								customerError);

					}
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

	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail) {
		RestResponseDTO restResponseDto = new RestResponseDTO();

		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);

		return restResponseDto;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listCustomer")
	public String listCustomer(ModelMap modelMap, HttpServletRequest request, Model model,
			HttpServletResponse response) {
		List<CustomerDTO> dtoList = new ArrayList<CustomerDTO>();
		dtoList = customerApi.getAllCustomer();
		modelMap.put("customerList", dtoList);
		String param = (String) model.asMap().get("message");
		logger.debug("param:" + param);
		if (param != null && !param.trim().equals("")) {
			modelMap.put("message", param == null ? "" : messageSource.getMessage(param, null, Locale.ROOT));
		}
		return "/Customer/listCustomer";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteCustomer")
	public String deleteCustomer(ModelMap modelMap, Model model,
			@RequestParam(value = "customerId", required = true) String customerId, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				customerApi.deleteCustomer(Long.parseLong(customerId));
				redirectAttributes.addFlashAttribute("message", "customer.delete.successfull");
				return "redirect:/listCustomer";
			}
			return "/";
		}
		return "/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/Static/{staticPage}")
	public String getStatic(ModelMap map, @PathVariable("staticPage") String staticPage) {
		return "Static/" + staticPage;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/Static/{staticPage}")
	public String getStaticPost(ModelMap map, @PathVariable("staticPage") String staticPage) {
		return "Static/" + staticPage;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	@RequestMapping(method = RequestMethod.POST, value = "/postCustomerRequestData")
	public ResponseEntity<RestResponseDTO> selfUpdateCustomer(@ModelAttribute CustomerDTO customerDto) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {

			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();

				if (authority.contains(Authorities.CUSTOMER) && authority.contains(Authorities.AUTHENTICATED)) {
					try {
						CustomerError customerError = new CustomerError();
						customerError = customerValidation.customerKYCValidation(customerDto);
						if (customerError.isValid()) {
							String operation = "KycForm";
							Long associatedId = AuthenticationUtil.getCurrentUser().getAssociatedId();
							customerDto.setProfileVerificationRequest(true);
							customerDto.setProfileVerification(false);
							customerApi.updateKYCCustomer(customerDto, associatedId, operation);

							restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "Success", customerDto);
							System.out.println("Running Smoothly");
						} else {
							restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error", customerError);
							System.out.println("Validation failed");
						}

					} catch (Exception e) {
						BugMail.Bugmail(e);
						restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Failed", new Object());
						return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
					}

				} else {
					restResponseDTO = getResponseDto(ResponseStatus.UNAUTHORIZED_USER, "Failed", new Object());
					return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.UNAUTHORIZED);
				}

			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			restResponseDTO = getResponseDto(ResponseStatus.FAILURE, "Failed", new Object());
			return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/getCustomerKYCDetail")
	public ResponseEntity<RestResponseDTO> getCustomerKYCDetail(ModelMap modelMap,
			@RequestParam(value = "customerId", required = true) long customerId, HttpServletRequest request) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					CustomerDTO customerDto = customerApi.getCustomerDetailsById(customerId);
					restResponseDTO.setDetail(customerDto);
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

	
	@RequestMapping(method = RequestMethod.POST, value = "/customerApproved")
	public ResponseEntity<RestResponseDTO> customerApproved(ModelMap modelMap,
			@RequestParam(value = "customerId", required = true) long customerId,
			@RequestParam(value = "remark", required = true) String profileVerificationRemark,
			HttpServletRequest request) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					CustomerDTO customerDto = customerApi.getCustomerDetailsById(customerId);
					CustomerError customerError = new CustomerError();
					customerError = customerValidation.validateCustomer(customerDto);
					if (customerError.isValid()) {
						customerDto.setProfileVerification(true);
						customerDto.setProfileVerificationRemark(profileVerificationRemark);
						customerDto.setProfileVerificationRequest(false);
						String operation = "approved";
						customerApi.updateKYCCustomer(customerDto, customerId, operation);
						restResponseDTO.setDetail(customerDto);
						restResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
					} else {
						restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error", customerError);
						System.out.println("Validation failed");
						return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.BAD_REQUEST);
					}
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

	@RequestMapping(method = RequestMethod.POST, value = "/sendForRevision")
	public ResponseEntity<RestResponseDTO> sendForRevsion(ModelMap modelMap,
			@RequestParam(value = "customerId", required = true) long customerId,
			@RequestParam(value = "remark", required = true) String profileVerificationRemark) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					CustomerDTO customerDto = this.customerApi.getCustomerDetailsById(customerId);
					customerDto.setProfileVerificationRemark(profileVerificationRemark);
					customerDto.setProfileVerificationRequest(false);
					customerDto.setProfileVerification(false);
					try {
						CustomerError customerError = new CustomerError();
						customerError = customerValidation.sendForRevisionValidation(customerDto);
						if (customerError.isValid()) {

							String operation = "sendForRevision";
							customerApi.updateKYCCustomer(customerDto, customerId, operation);

							restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "Success", customerDto);
							System.out.println("Running Smoothly");
							return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
						} else {
							restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error", customerError);
							System.out.println("Validation failed");
							return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.BAD_REQUEST);
						}

					} catch (Exception e) {
						BugMail.Bugmail(e);
						restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Failed", new Object());
						return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					restResponseDTO.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
					return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.UNAUTHORIZED);

				}
			} else {
				restResponseDTO.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
				return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			restResponseDTO.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
