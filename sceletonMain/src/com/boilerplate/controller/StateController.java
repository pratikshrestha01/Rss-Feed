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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boilerplate.api.ICountryApi;
import com.boilerplate.api.IStateApi;
import com.boilerplate.model.CountryDTO;
import com.boilerplate.model.OperationType;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.RestResponseDTO;
import com.boilerplate.model.StateDTO;
import com.boilerplate.model.error.StateError;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.Authorities;
import com.boilerplate.util.BugMail;
import com.boilerplate.validation.StateValidation;

@Controller
public class StateController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(StateController.class);

	@Autowired
	private IStateApi stateApi;
	@Autowired
	private StateValidation stateValidation;
	@Autowired
	private ICountryApi countryApi;
	
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET, value = "/addState")
	public String addState(@RequestParam(value = "pageNo", required = false) Integer page,ModelMap modelMap, HttpServletRequest request) {
		try{
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					
					if(page == null){
						page = 0;
					}
					
					PagablePage mypage=stateApi.getAllStatePagable(page);
					
					modelMap.put("stateList", mypage.getObject());
					modelMap.put("lastpage", mypage.getLastpage());
					modelMap.put("currentpage", mypage.getCurrentPage());
					modelMap.put("pagelist", mypage.getPageList());

					
					List<CountryDTO> countryList = new ArrayList<CountryDTO>();
					countryList = countryApi.findCountry();
					modelMap.put("countryList", countryList);
					//List<StateDTO> stateList = new ArrayList<StateDTO>();
					//stateList = stateApi.findState();
					//modelMap.put("stateList", stateList);
					return "State/" + "addState";
				}
			}
		}catch(Exception e){
			BugMail.Bugmail(e);
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addState")
	public ResponseEntity<RestResponseDTO> addState(ModelMap modelMap, @ModelAttribute("state") StateDTO stateDTO, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponseDTO= new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

					try {
						StateError stateError = new StateError();
						stateDTO.setOperation(OperationType.add);
						stateError = stateValidation.stateValidation(stateDTO);
						logger.debug("valid" + stateError.isValid());
						if (stateError.isValid()) {
							stateDTO = stateApi.saveState(stateDTO);
							restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "State has been successfully saved", stateDTO);
							
						} else {
							logger.debug("error while adding");
							restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error Occured", stateError);
							
						}

					} catch (Exception e) {
						BugMail.Bugmail(e);
						logger.debug("exception" + e);
						restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Request Operator", new Object());
					
					}

			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listState")
	public String listState(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, Model model) {

		List<StateDTO> stateDTOList = new ArrayList<StateDTO>();
		stateDTOList = stateApi.getAllState();
		modelMap.put("stateList", stateDTOList);
		logger.debug("agentList" + stateDTOList);
		String param = (String) model.asMap().get("message");
		logger.debug("param:" + param);

		if (param != null && !param.trim().equals("")) {
			modelMap.put("message", param == null ? "" : messageSource.getMessage(param, null, Locale.ROOT));
		}
		return "/State/listState";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editState")
	public ResponseEntity<RestResponseDTO> editState(ModelMap modelMap, @RequestParam(value = "stateId", required = true) Long stateId,
			HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try{
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					StateDTO stateDto = stateApi.findStateById(stateId);
					restResponseDTO = getResponseDto(ResponseStatus.SUCCESS,"State Found", stateDto);
				}else{
					restResponseDTO = getResponseDto(ResponseStatus.UNAUTHORIZED_USER, "Unauthorized User", new Object());
				}
			}else{
				restResponseDTO = getResponseDto(ResponseStatus.UNAUTHORIZED_USER, "Unauthorized User", new Object());
			}
		}catch(Exception e){
			BugMail.Bugmail(e);
			logger.debug("exception" + e);
			restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Request Operator", new Object());
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editState")
	public ResponseEntity<RestResponseDTO> editState(ModelMap modelMap, @ModelAttribute("state") StateDTO stateDTO, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		logger.debug("at edit state");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				
					try {
						StateError stateError = new StateError();
						stateDTO.setOperation(OperationType.edit);
						stateError = stateValidation.stateValidation(stateDTO);
						if (stateError.isValid()) {
							logger.debug("error valid=" + stateError.isValid());
							stateDTO = stateApi.editState(stateDTO);
							restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "City has been successfully edited", stateDTO);
						} else {
							logger.debug("error while editing");
							restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED, "Error Occured", stateError);
						}
					} catch (Exception e) {
						
						BugMail.Bugmail(e);
						logger.debug("exception" + e);
						restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Request Operator",e);
					}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail){
		RestResponseDTO restResponseDto = new RestResponseDTO();
		
		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);
		
		return restResponseDto;
	}

}
