package com.boilerplate.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boilerplate.api.ICountryApi;
import com.boilerplate.model.CountryDTO;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.RestResponseDTO;
import com.boilerplate.model.error.CountryError;
import com.boilerplate.model.mobile.ResponseStatus;
import com.boilerplate.repositories.CountryRepository;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.Authorities;
import com.boilerplate.util.BugMail;
import com.boilerplate.validation.CountryValidation;

@Controller
public class CountryController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	private ICountryApi countryApi;
	@Autowired
	private CountryValidation countryValidation;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CountryRepository countryRepository;

	/*
	 * public CountryController(ICountryApi countryApi, CountryValidation
	 * countryValidation, ICurrencyApi currencyApi) { this.countryApi =
	 * countryApi; this.countryValidation = countryValidation; this.currencyApi
	 * = currencyApi; }
	 */


	@RequestMapping(method = RequestMethod.GET, value = "/addCountry")
	public String addCountry(@RequestParam(value = "pageNo", required = false) Integer page,ModelMap modelmap, HttpServletRequest request) {
		try{
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

					if (page == null) {
						page = 0;
					}

					PagablePage mypage=countryApi.getAllCountryPagable(page);
					modelmap.put("countryList", mypage.getObject());
					modelmap.put("lastpage", mypage.getLastpage());
					modelmap.put("currentpage", mypage.getCurrentPage());
					modelmap.put("pagelist", mypage.getPageList());
					
					//List<CountryDTO> countryDTOList = new ArrayList<CountryDTO>();
					//countryDTOList = countryApi.getAllCountry();
					//modelmap.put("countryList", countryDTOList);
					return "Country/" + "addCountry";

				}
			}
		}
		catch(Exception e){
			BugMail.Bugmail(e);
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addCountry")

	public  ResponseEntity<RestResponseDTO> addCountry(ModelMap modelMap, @ModelAttribute("country") CountryDTO countryDTO,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
				RestResponseDTO restResponseDTO= new RestResponseDTO();
				countryDTO.setOperation("add");
			try {
				CountryError countryError = new CountryError();
				countryError = countryValidation.countryValidation(countryDTO);
				logger.debug("valid" + countryError.isValid());
				if (countryError.isValid()) {
					countryDTO =countryApi.saveCountry(countryDTO);
					restResponseDTO = getResponseDto(ResponseStatus.SUCCESS,"Country has been successfully saved", countryDTO);
					
				} else {
					logger.debug("error while adding");
					restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED,"Error Occured", countryError);
					
				}
			} catch (Exception e) {
				BugMail.Bugmail(e);
				logger.debug("exception" + e);
				e.printStackTrace();
				restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR,"Request Operator", new Object());
				

			}

		

		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listCountry")
	public String listCountry(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		List<CountryDTO> countryDTOList = new ArrayList<CountryDTO>();
		countryDTOList = countryApi.getAllCountry();
		modelMap.put("countryList", countryDTOList);
		logger.debug("agentList" + countryDTOList);
		return "/Country/listCountry";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editCountry")
	public ResponseEntity<RestResponseDTO> editCountry(ModelMap modelMap,
			@RequestParam(value = "countryId", required = true) String countryId, HttpServletRequest request,
			HttpServletResponse response) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					CountryDTO countryDTO = countryApi.findCountryById(Long.parseLong(countryId));
					restResponseDTO.setDetail(countryDTO);
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

	@RequestMapping(method = RequestMethod.POST, value = "/editCountry")
	public ResponseEntity<RestResponseDTO> editCountry(ModelMap modelMap, @ModelAttribute("country") CountryDTO countryDTO,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponseDTO= new RestResponseDTO();
		logger.debug("at edit country");
		
			try {
				
				CountryError countryError = new CountryError();
				countryDTO.setOperation("edit");
				countryError = countryValidation.countryValidation(countryDTO);
				if (countryError.isValid()) {
					logger.debug("error valid" + countryError.isValid());
					countryDTO = countryApi.editCountry(countryDTO);;
					restResponseDTO = getResponseDto(ResponseStatus.SUCCESS,"Country has been successfully edited", countryDTO);

				} else {
					logger.debug("error during edit");
					restResponseDTO = getResponseDto(ResponseStatus.VALIDATION_FAILED,"Error Occured", countryError);
				}
				
			} catch (Exception e) {
				BugMail.Bugmail(e);
				logger.debug("exception" + e);
				e.printStackTrace();
				restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR,"Request Operator", new Object());
				
			}
			
			return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteCountry")
	public String deleteCountry(@RequestParam(value = "countryId", required = true) String countryId,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				countryApi.deleteCountry(Long.parseLong(countryId));
				redirectAttributes.addFlashAttribute("message", "country.delete.successfull");
				return "redirect:/listAgent";
			}
			return "/";
		}
		return "/";
	}
	
	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail){
		RestResponseDTO restResponseDto = new RestResponseDTO();
		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);
		
		return restResponseDto;
	}

}
