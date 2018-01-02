package com.boilerplate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boilerplate.api.IAdminApi;
import com.boilerplate.api.ICityApi;
import com.boilerplate.entity.User;
import com.boilerplate.model.AdminDTO;
import com.boilerplate.model.CityDTO;
import com.boilerplate.model.Hashes;
import com.boilerplate.model.OperationType;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.RestResponseDTO;
import com.boilerplate.model.UserType;
import com.boilerplate.model.error.AdminError;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.repositories.UserRepository;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.Authorities;
import com.boilerplate.util.BugMail;
import com.boilerplate.util.ClientException;
import com.boilerplate.validation.AdminValidation;

@Controller
public class AdminController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private IAdminApi adminApi;

	@Autowired
	private ICityApi cityApi;

	private MessageSource messageSource;

	@Autowired
	private AdminValidation adminValidation;

	@Autowired
	private UserRepository userRepository;



	@ModelAttribute("admin")
	public AdminDTO getAdmin() {
		return new AdminDTO();
	}


	@RequestMapping(method = RequestMethod.GET, value = "/addAdmin")
	public String addAdmin(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap,
			HttpServletRequest request) {

		for (Thread t : Thread.getAllStackTraces().keySet()) {
			System.out.println(" from admin " + t.isAlive() + " thrade id " + t.getId() + " : " + t.getName()
					+ " My thrade : " + Thread.currentThread().getId());
		}
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					logger.debug("at addAdmin==>");

					logger.debug("This is testing message from logger==>");

					String[] ids = TimeZone.getAvailableIDs();
					List<String> timeZoneList = new ArrayList<>();
					if (page == null) {
						page = 0;
					}

					PagablePage mypage = adminApi.getAllAdminPagable(page);

					for (String id : ids) {
						timeZoneList.add(id);
					}

					List<AdminDTO> adminList =(List<AdminDTO>) mypage.getObject();

					for (AdminDTO dto : adminList) {
						User user = userRepository.findUserByAssociatedIdAndUserType(dto.getId(), UserType.ADMIN);
						try {
							if (user != null) {
								if (user.getUsername() != null) {
									dto.setUsername(user.getUsername());
								}
							}
						} catch (Exception e) {
							BugMail.Bugmail(e);
							e.printStackTrace();
						}

					}
					List<CityDTO> cityList = cityApi.getAllCity();
				

					
					modelMap.put("timeZoneList", timeZoneList);
					modelMap.put("cityList", cityList);
					modelMap.put("adminList", adminList);
					modelMap.put("lastpage", mypage.getLastpage());
					modelMap.put("currentpage", mypage.getCurrentPage());
					modelMap.put("pagelist", mypage.getPageList());

					return "Admin/" + "addAdmin";

				}
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			e.printStackTrace();

			return "redirect:/";
		}

		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addAdmin")
	public ResponseEntity<RestResponseDTO> addAdmin(@ModelAttribute("admin") AdminDTO adminDTO,
			HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					AdminError adminError = new AdminError();
					adminDTO.setOperation(OperationType.add);
					adminError = adminValidation.adminValidation(adminDTO);
					logger.debug("isValid" + adminError.isValid());
					if (adminError.isValid()) {
						adminDTO = adminApi.saveAdmin(adminDTO);
						restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "Admin has been successfully saved",
								adminDTO);

					} else {
						logger.debug("error while adding");
						restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error Occured", adminError);
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

	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail) {
		RestResponseDTO restResponseDto = new RestResponseDTO();

		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);

		return restResponseDto;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listAdmin")
	public String listUser(ModelMap modelMap, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				List<AdminDTO> dtoList = new ArrayList<AdminDTO>();

				dtoList = adminApi.findAllSuperAdmin();
				modelMap.put("adminList", dtoList);

				String param = (String) model.asMap().get("message");
				System.out.println("this is paran " + param);
				logger.debug("param:" + param);

				if (param != null && !param.trim().equals("")) {
					modelMap.put("message", param == null ? "" : messageSource.getMessage(param, null, Locale.ROOT));
				}
				logger.debug("userList:" + dtoList);

			}
			return "/Admin/listAdmin";
		}
		return "/";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/snapTest")
	public @ResponseBody String snapTest(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password) {

		if (username != null) {
			User user = userRepository.findByUsername(username);
			if (user != null) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

				if (encoder.matches(password, user.getPassword())) {
					return "true";
				} else {
					return "false";
				}
			} else {
				return "false";
			}

		} else {
			return "false";
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/adminChangePass")
	public ResponseEntity<RestResponseDTO> adminChangePass(@RequestParam(value = "id", required = false) long id,
			@RequestParam(value = "type", required = false) UserType type) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		restResponseDTO = getResponseDto(ResponseStatus.FAILURE, "failure", new Object());

		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					/* adminApi.adminChangePass(id, type); */
					restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "success", "");
				}
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			e.printStackTrace();
			restResponseDTO = getResponseDto(ResponseStatus.FAILURE, "failure", "");
		}

		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/editAdmin")
	public ResponseEntity<RestResponseDTO> editAdmin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "adminId", required = true) Long adminId) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					AdminDTO adminDTO = adminApi.getAdminForEdit(adminId);
					restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "Admin Found", adminDTO);
				} else {
					restResponseDTO = getResponseDto(ResponseStatus.UNAUTHORIZED_USER, "Unauthorized User",
							new Object());
				}
			} else {
				restResponseDTO = getResponseDto(ResponseStatus.UNAUTHORIZED_USER, "Unauthorized User", new Object());
			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			logger.debug("exception" + e);
			restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Request Operator", new Object());
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editAdmin")
	public ResponseEntity<RestResponseDTO> editUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("admin") AdminDTO adminDTO) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					AdminError adminError = new AdminError();
					adminDTO.setOperation(OperationType.edit);
					adminError = adminValidation.adminValidation(adminDTO);
					if (adminError.isValid()) {
						adminDTO = adminApi.editAdmin(adminDTO);
						restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "Admin has been successfully edited",
								adminDTO);

					} else {
						logger.debug("error while editing");
						restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error Occured", adminError);
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

	@RequestMapping(method = RequestMethod.POST, value = "/transferbyadmin")
	public ResponseEntity<Object> transferbyadmin(@RequestParam(value = "userId", required = true) String userid,
			@RequestParam(value = "amount", required = true) double amount, ModelMap modelMap,
			HttpServletRequest request) throws ClientException {
		RestResponseDTO responseDTO = new RestResponseDTO();
		Hashes hashes = new Hashes();
		try {

			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if ((authority.contains(Authorities.ADMINISTRATOR)) && authority.contains(Authorities.AUTHENTICATED)) {
					hashes = adminValidation.transferByAdminValidation(userid, amount);
					if (hashes.isValid()) {
						adminApi.transferByAdmin(userid, amount);
						responseDTO.setMessage("Balance Transfered Successfully");
						responseDTO.setStatus("success");
						return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
					} else {

						responseDTO.setMessage("failure");
						responseDTO.setStatus("validationError");
						responseDTO.setDetail(hashes.getMyHash());
						return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
					}
				}
			}

		} catch (Exception e) {
			BugMail.Bugmail(e);
			e.printStackTrace();
			responseDTO.setMessage("Error In Processing");
			responseDTO.setStatus("failure");
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		}

		responseDTO.setMessage("Error In Processing");
		responseDTO.setStatus("failure");
		return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);

	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
