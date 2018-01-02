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

import com.boilerplate.api.ICityApi;
import com.boilerplate.api.IStateApi;
import com.boilerplate.model.CityDTO;
import com.boilerplate.model.OperationType;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.RestResponseDTO;
import com.boilerplate.model.StateDTO;
import com.boilerplate.model.error.CityError;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.Authorities;
import com.boilerplate.util.BugMail;
import com.boilerplate.validation.CityValidation;

@Controller
public class CityController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private ICityApi cityApi;
	@Autowired
	private CityValidation cityValidation;
	@Autowired
	private IStateApi stateApi;
	
	private MessageSource messageSource;

	/*public CityController(ICityApi cityApi, CityValidation cityValidation, IStateApi stateApi) {
		this.cityApi = cityApi;
		this.cityValidation = cityValidation;
		this.stateApi = stateApi;
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "/addCity")
	public String addCity(@RequestParam(value = "pageNo", required = false) Integer page,ModelMap modelMap, HttpServletRequest request) {
		try{
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					
					
					if (page == null) {
						page = 0;
					}

					PagablePage mypage=cityApi.getAllCityPagable(page);
					
					modelMap.put("cityList", mypage.getObject());
					modelMap.put("lastpage", mypage.getLastpage());
					modelMap.put("currentpage", mypage.getCurrentPage());
					modelMap.put("pagelist", mypage.getPageList());

					
					List<StateDTO> stateList = new ArrayList<StateDTO>();
					stateList = stateApi.findState();
					modelMap.put("stateList", stateList);
					//List<CityDTO> cityDTOList = new ArrayList<CityDTO>();
					//cityDTOList = cityApi.getAllCity();
					//modelMap.put("cityList", cityDTOList);
					return "City/" + "addCity";

				}
			}
		}catch(Exception e){
			BugMail.Bugmail(e);
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addCity")
	public ResponseEntity<RestResponseDTO> addCity(ModelMap modelMap, @ModelAttribute("city") CityDTO cityDTO, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				
					try {
						CityError cityError = new CityError();
						cityDTO.setOperation(OperationType.add);
						cityError = cityValidation.cityValidation(cityDTO);
						logger.debug("valid" + cityError.isValid());
						if (cityError.isValid()) {
							cityDTO = cityApi.saveCity(cityDTO);
							restResponseDTO = getResponseDto(ResponseStatus.SUCCESS,"City has been successfully saved", cityDTO);
							
						} else {
							logger.debug("error while adding");
							restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED,"Error Occured", cityError);
						}

					} catch (Exception e) {
						BugMail.Bugmail(e);
						logger.debug("exception" + e);
						restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR,"Request Operator", new Object());
						
					}

			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listCity")
	public String listCity(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, Model model) {

		List<CityDTO> cityDTOList = new ArrayList<CityDTO>();
		cityDTOList = cityApi.getAllCity();
		modelMap.put("cityList", cityDTOList);
		logger.debug("citylist" + cityDTOList);
		String param = (String) model.asMap().get("message");
		logger.debug("param:" + param);

		if (param != null && !param.trim().equals("")) {
			modelMap.put("message", param == null ? "" : messageSource.getMessage(param, null, Locale.ROOT));
		}
		return "/City/listCity";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editCity")
	public ResponseEntity<RestResponseDTO> editCity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "cityId", required = true) Long cityId) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try{
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					CityDTO cityDto = cityApi.findCityById(cityId);
					restResponseDTO = getResponseDto(ResponseStatus.SUCCESS,"City Found", cityDto);
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

	@RequestMapping(method = RequestMethod.POST, value = "/editCity")
	public ResponseEntity<RestResponseDTO> editCity(ModelMap modelMap, @ModelAttribute("city") CityDTO cityDTO, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		logger.debug("at edit city");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					try {
						CityError cityError = new CityError();
						cityDTO.setOperation(OperationType.edit);
						cityError = cityValidation.cityValidation(cityDTO);
						if (cityError.isValid()) {
							logger.debug("error valid=" + cityError.isValid());
							cityDTO = cityApi.editCity(cityDTO);
							restResponseDTO = getResponseDto(ResponseStatus.SUCCESS,"City has been successfully edited", cityDTO);
						} else {
							logger.debug("error while editing");
							restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED,"Error Occured", cityError);
						}
					} catch (Exception e) {
						BugMail.Bugmail(e);
						logger.debug("exception" + e);
						restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR,"Request Operator", new Object());
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAllCities")
	public ResponseEntity<RestResponseDTO> getAllCities(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					try {
						List<CityDTO> cityDTOList = new ArrayList<CityDTO>();
						cityDTOList = cityApi.getAllCity();
						restResponseDTO = getResponseDto(ResponseStatus.SUCCESS,"get cities", cityDTOList);
					} catch (Exception e) {
						BugMail.Bugmail(e);
						logger.debug("exception" + e);
						e.printStackTrace();
						restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR,"Request Operator", new Object());
					}
			}

		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

}
