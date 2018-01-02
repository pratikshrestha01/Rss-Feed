package com.boilerplate.api;

import java.util.List;

import com.boilerplate.model.WebServiceDTO;
import com.boilerplate.util.ClientException;

public interface IWebServiceApi {

	void createUser(WebServiceDTO dto) throws ClientException;

	List<WebServiceDTO> getAlluser();

	WebServiceDTO findByUserAndkey(String clientId, String clientKey);

	WebServiceDTO findByAccessKey(String accessKey);

}
