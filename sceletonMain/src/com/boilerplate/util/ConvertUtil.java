package com.boilerplate.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boilerplate.api.ICityApi;
import com.boilerplate.entity.Address;
import com.boilerplate.entity.Admin;
import com.boilerplate.entity.City;
import com.boilerplate.entity.Country;
import com.boilerplate.entity.Customer;

import com.boilerplate.entity.RssItem;
import com.boilerplate.entity.State;
import com.boilerplate.entity.User;
import com.boilerplate.entity.UserSession;
import com.boilerplate.entity.WebService;
import com.boilerplate.model.AddressDTO;
import com.boilerplate.model.AdminDTO;
import com.boilerplate.model.CityDTO;
import com.boilerplate.model.CountryDTO;
import com.boilerplate.model.CustomerDTO;
import com.boilerplate.model.NbaRssItemDto;
import com.boilerplate.model.RpmRssItemDto;
import com.boilerplate.model.StateDTO;
import com.boilerplate.model.Status;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.UserSessionDTO;
import com.boilerplate.model.WebServiceDTO;
import com.dto.rssItem.RssItemDto;

@Component
public class ConvertUtil {

	@Autowired
	private ICityApi cityApi;

	private ConvertUtil() {

	}

	public static List<AdminDTO> convertAdminToDTO(List<Admin> admin) {
		List<AdminDTO> adminDTO = new ArrayList<AdminDTO>();
		for (Admin a : admin) {
			adminDTO.add(convertAdmin(a));
		}
		return adminDTO;
	}

	public static List<RssItemDto> convertRssItemToDTO(List<RssItem> items) {
		List<RssItemDto> itemsdto = new ArrayList<RssItemDto>();
		for (RssItem i : items) {
			itemsdto.add(convertRss(i));
		}
		return itemsdto;
	}

	public static AdminDTO convertAdmin(Admin admin) {
		AdminDTO adminDto = new AdminDTO();
		try {
			if (admin != null) {
				adminDto.setId(admin.getId());
				adminDto.setAddress(admin.getAddress());
				if (admin.getCity() != null) {
					adminDto.setCity(admin.getCity().getName());
					adminDto.setCityId(admin.getCity().getId());
				}
				adminDto.setFullName(admin.getFullName());
				adminDto.setFirstName(admin.getFirstName());
				adminDto.setLastName(admin.getLastName());
				adminDto.setMobileNo(admin.getMobileNo());
				adminDto.setRegistered(admin.isRegistered());

				/* adminDto.setTimeZone(admin.getTimeZone()); */

			}
		} catch (Exception e) {
			BugMail.Bugmail(e);
			e.printStackTrace();
		}
		return adminDto;
	}

	public static RssItemDto convertRss(RssItem item) {
		RssItemDto dto = new RssItemDto();
		try {
			dto.setCatagory(item.getCatagory());
			dto.setSource(item.getSource());
			dto.setTitle(item.getTitle());
			dto.setDescription(item.getDescription());
			dto.setId(item.getId());
			dto.setImageUrl(item.getImageUrl());
			if (item.getUrl() == null || item.getUrl().trim().equals("")) {
				dto.setUrl("noImg");
			} else {
				dto.setUrl(item.getUrl());
			}
			dto.setUrl(item.getUrl());

		} catch (Exception e) {
			BugMail.Bugmail(e);
			e.printStackTrace();
		}
		return dto;
	}

	public static String CreateHash(String key, String password, String hash) throws ClientException {
		try {
			byte[] decodedKey = (key).getBytes();
			SecretKeySpec keySpec = new SecretKeySpec(decodedKey, hash);
			Mac mac = Mac.getInstance(hash);
			mac.init(keySpec);
			byte[] dataBytes = password.getBytes("UTF-8");
			byte[] signatureBytes = mac.doFinal(dataBytes);
			String encoded = new String(Base64.encodeBase64(signatureBytes), "UTF-8");
			System.out.println("Prepared Encoded Signature :" + encoded);
			return encoded;
		} catch (Exception e) {
			BugMail.Bugmail(e);
			throw new ClientException("Service Down !!!");
		}
	}

	public static List<UserSessionDTO> convertSessionList(List<UserSession> userSession) {
		List<UserSessionDTO> dtoList = new ArrayList<UserSessionDTO>();
		for (UserSession session : userSession) {
			dtoList.add(convertSession(session));
		}
		return dtoList;
	}

	public static UserSessionDTO convertSession(UserSession session) {
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
		UserSessionDTO dto = new UserSessionDTO();
		dto.setUsername(session.getUser().getUsername());
		dto.setId(session.getId());
		dto.setSessionId(session.getSessionId());
		dto.setUserId(session.getUser().getId());
		dto.setUserType(session.getUser().getUserType());
		dto.setLastRequest(time.format(session.getLastRequest()));
		return dto;
	}

	public static Customer convertCustomerDTO(CustomerDTO customerDTO) {
		Customer customer = new Customer();

		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setMiddleName(customerDTO.getMiddleName());
		return customer;
	}

	public static Customer convertCustomerDTO(Customer customer, CustomerDTO customerDto) {

		customer.setFirstName(customerDto.getFirstName());
		customer.setMiddleName(customerDto.getMiddleName());
		customer.setLastName(customerDto.getLastName());
		customer.setMobileNo(customerDto.getMobileNo());
		customer.setFullName(
				customerDto.getFirstName() + " " + customerDto.getMiddleName() + " " + customerDto.getLastName());

		return customer;
	}

	public static List<CustomerDTO> ConvertCustomerToDTO(List<Customer> customer) {
		List<CustomerDTO> customerDTO = new ArrayList<CustomerDTO>();
		for (Customer cust : customer) {
			customerDTO.add(convertCustomer(cust));
		}
		return customerDTO;
	}

	public static CustomerDTO convertCustomer(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setAccountNo(customer.getAccountNo());
		dto.setFirstName(customer.getFirstName());
		dto.setFullName(customer.getFullName());
		dto.setLastName(customer.getLastName());
		dto.setMiddleName(customer.getMiddleName());
		dto.setLandline(customer.getLandline());
		dto.setMobileNo(customer.getMobileNo());
		dto.setEmail(customer.getEmail());
		dto.setParentId(customer.getParentId());
		dto.setGender(customer.getGender());
		dto.setOccupation(customer.getOccupation());
		dto.setDob(DateTimeUtil.convertDateToString(customer.getDob()));
		dto.setFatherName(customer.getFatherName());
		dto.setMotherName(customer.getMotherName());
		dto.setGrandFatherName(customer.getGrandFatherName());

		dto.setDocumentNo(customer.getDocumentNo());
		dto.setIssuedFrom(customer.getIssuedFrom());
		dto.setIssuedDate(DateTimeUtil.convertDateToString(customer.getIssuedDate()));

		dto.setProfileVerification(customer.isProfileVerification());
		dto.setProfileVerificationRequest(customer.isProfileVerificationRequest());
		dto.setProfileVerificationRemark(customer.getProfileVerificationRemark());

		if (customer.getCity() != null) {
			dto.setCity(customer.getCity().getName());
		}

		return dto;
	}

	public static List<UserDTO> ConvertUserToDTO(List<User> user) {
		List<UserDTO> userDTO = new ArrayList<UserDTO>();
		for (User u : user) {
			userDTO.add(convertUser(u));
		}
		return userDTO;
	}

	public static UserDTO convertUser(User user) {

		if (user == null) {
			return null;
		}
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		if (user.getMobileNo() != null) {
			dto.setMobileNo(user.getMobileNo());
		}

		dto.setAuthority(user.getAuthority());
		dto.setUserType(user.getUserType());
		dto.setStatus(user.getStatus());
		/* dto.setTimeZone(user.getTimeZone()); */
		dto.setSecret_key(user.getSecret_key());

		dto.setSimCommissionAccountNo(user.getSimCommissionAccountNo());
		dto.setSimVirtualCommissionAccountNo(user.getSimVirtualCommissionAccountNo());

		return dto;
	}

	public static <E> List<E> convertIterableToList(Iterable<E> iter) {
		List<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

	public static Country convertCountryDTO(CountryDTO countryDTO) {
		Country country = new Country();

		country.setName(countryDTO.getName());
		country.setIsoTwo(countryDTO.getIsoTwo());
		country.setIsoThree(countryDTO.getIsoThree());
		country.setDialCode(countryDTO.getDialCode());
		country.setStatus(Status.Active);

		return country;
	}

	public static Country convertCountryDTO(CountryDTO countryDTO, Country country) {

		country.setName(countryDTO.getName());
		country.setIsoTwo(countryDTO.getIsoTwo());
		country.setIsoThree(countryDTO.getIsoThree());
		country.setDialCode(countryDTO.getDialCode());
		if (countryDTO.getStatus() != null) {
			country.setStatus(Status.valueOf(countryDTO.getStatus()));
		}

		return country;
	}

	public static List<Country> convertToCountryListFromDtoList(List<CountryDTO> countryDtoList) {
		List<Country> countryList = new ArrayList<Country>();
		for (CountryDTO countryDto : countryDtoList) {
			countryList.add(convertCountryDTO(countryDto));
		}
		return countryList;
	}

	public static List<CountryDTO> convertCountrytoCountryDtoList(List<Country> country) {
		List<CountryDTO> countryDTO = new ArrayList<CountryDTO>();
		for (Country countryList : country) {
			countryDTO.add(convertToCountryDto(countryList));
		}
		return countryDTO;
	}

	public static CountryDTO convertToCountryDto(Country country) {
		CountryDTO countryDTO = new CountryDTO();

		countryDTO.setId(country.getId());
		countryDTO.setName(country.getName());
		countryDTO.setIsoTwo(country.getIsoTwo());
		countryDTO.setIsoThree(country.getIsoThree());
		countryDTO.setDialCode(country.getDialCode());
		countryDTO.setStatus(country.getStatus().getValue());

		return countryDTO;
	}

	public static List<StateDTO> convertStateDtoToState(List<State> state) {
		List<StateDTO> stateDTO = new ArrayList<StateDTO>();
		for (State stateList : state) {
			stateDTO.add(convertState(stateList));
		}
		return stateDTO;
	}

	public static StateDTO convertState(State state) {
		StateDTO stateDTO = new StateDTO();
		stateDTO.setId(state.getId());
		stateDTO.setName(state.getName());
		stateDTO.setCountry(state.getCountry().getName());
		stateDTO.setStatus(state.getStatus());
		return stateDTO;
	}

	public static StateDTO convertStateDto(State state) {
		StateDTO stateDTO = new StateDTO();
		stateDTO.setId(state.getId());
		stateDTO.setName(state.getName());
		stateDTO.setCountry(state.getCountry().getName());
		stateDTO.setStatus(Status.Active);
		return stateDTO;
	}

	public static State convertDtoToState(StateDTO stateDto, State state) {
		state.setName(stateDto.getName());
		state.setStatus(stateDto.getStatus());

		return state;
	}

	public static State convertDtoToState(StateDTO stateDto) {
		State state = new State();
		state.setName(stateDto.getName());
		state.setStatus(Status.Active);
		return state;
	}

	public static City convertDtoToCity(CityDTO cityDTO, City city) {
		city.setName(cityDTO.getName());
		city.setStatus(cityDTO.getStatus());
		return city;

	}

	public static City convertDtoToCity(CityDTO cityDTO) {

		City city = new City();
		city.setName(cityDTO.getName());
		city.setStatus(Status.Active);

		return city;

	}

	public static List<CityDTO> convertCityDtoToCity(List<City> city) {
		List<CityDTO> cityDTO = new ArrayList<CityDTO>();
		for (City cityList : city) {
			cityDTO.add(convertCity(cityList));
		}
		return cityDTO;
	}

	public static CityDTO convertCity(City city) {
		if (city == null) {
			return null;
		}
		CityDTO cityDTO = new CityDTO();
		cityDTO.setId(city.getId());
		cityDTO.setName(city.getName());
		cityDTO.setState(city.getState().getName());
		cityDTO.setStatus(city.getStatus());
		return cityDTO;
	}

	public static ArrayList<String> convertDtoToCountryNameArray(List<CountryDTO> countryDtoList) {
		ArrayList<String> countryArrayList = new ArrayList<String>();
		for (CountryDTO countryDto : countryDtoList) {
			countryArrayList.add(countryDto.getName());
		}

		return countryArrayList;
	}

	public static User convertDtoToUser(UserDTO userDto, User user) {
		user.setUserType(userDto.getUserType());
		user.setStatus(userDto.getStatus());
		/* user.setTimeZone(userDto.getTimeZone()); */

		return user;

	}

	public Address convertAddressDTO(AddressDTO dto) {
		Address entity = new Address();
		entity.setAddressType(dto.getAddressType());
		entity.setCity(cityApi.getCityById(dto.getCityId()));
		entity.setStreet(dto.getStreet());
		entity.setWardNo(dto.getWardNo());
		return entity;

	}

	public AddressDTO convertAddress(Address entity) {

		if (entity == null) {
			return null;
		}
		AddressDTO dto = new AddressDTO();
		dto.setId(entity.getId());
		dto.setAddressType(entity.getAddressType());
		dto.setStreet(entity.getStreet());
		dto.setWardNo(entity.getWardNo());
		if (entity.getCity() != null) {
			dto.setCityId(entity.getCity().getId());
			dto.setCity(entity.getCity().getName());
		}

		return dto;

	}

	public static List<WebServiceDTO> ConvertWebServiceList(List<WebService> serviceList) {
		List<WebServiceDTO> dtoList = new ArrayList<WebServiceDTO>();
		for (WebService service : serviceList) {
			dtoList.add(convertWebService(service));
		}
		return dtoList;
	}

	public static WebServiceDTO convertWebService(WebService service) {
		WebServiceDTO dto = new WebServiceDTO();
		dto.setAccess_key(service.getAccess_key());
		dto.setApi_password(service.getApi_password());
		dto.setApi_user(service.getApi_user());
		dto.setStatus(service.getStatus());
		return dto;
	}

	public String convertCommaSeperatedStringFromStringList(List<String> stringList) {

		String commaSeperatedString = "";
		for (String string : stringList) {
			commaSeperatedString += string;
		}

		return commaSeperatedString;
	}

	public List<String> splitCommaSeperatedStringToStringList(String string) {

		List<String> stringList = new ArrayList<>();
		String[] stringArray = string.split("");
		for (String character : stringArray) {
			stringList.add(character.trim());
		}

		return stringList;
	}

}
