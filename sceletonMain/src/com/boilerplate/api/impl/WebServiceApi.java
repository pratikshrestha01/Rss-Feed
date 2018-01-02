package com.boilerplate.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boilerplate.api.IWebServiceApi;
import com.boilerplate.entity.WebService;
import com.boilerplate.model.Status;
import com.boilerplate.model.WebServiceDTO;
import com.boilerplate.repositories.WebServiceRepository;
import com.boilerplate.util.BugMail;
import com.boilerplate.util.ClientException;
import com.boilerplate.util.ConvertUtil;

@Service
public class WebServiceApi implements IWebServiceApi{
	
	@Autowired
	private WebServiceRepository webServiceRepository;
	
	/*public WebServiceApi(WebServiceRepository webServiceRepository){
		this.webServiceRepository=webServiceRepository;
	}
*/
	@Override
	public void createUser(WebServiceDTO dto) throws ClientException {
		WebService user=webServiceRepository.findByName(dto.getApi_user());
		if(user==null){
			user=new WebService();
			user.setApi_user(dto.getApi_user());
			user.setApi_password(dto.getApi_password());
			user.setStatus(Status.Active);
			try {
				String access_key=ConvertUtil.CreateHash(dto.getApi_user(), dto.getApi_password(), "HmacMD5");
				user.setAccess_key(access_key);
				webServiceRepository.save(user);
				// Covert array of Hex bytes to a String
			} catch (Exception e) {
				BugMail.Bugmail(e);
				System.out.println("Exception:" + e.getMessage());
				throw new ClientException("Service Down !!!");
			}
		}
		
	}

	@Override
	public List<WebServiceDTO> getAlluser() {
		List<WebService> user=webServiceRepository.getAllUser();
		if(user!=null){
			return ConvertUtil.ConvertWebServiceList(user);
		}
		return null;
	}

	@Override
	public WebServiceDTO findByUserAndkey(String clientId, String clientKey) {
		WebService webservice=webServiceRepository.findByUserAndkey(clientId,clientKey);
		if(webservice!=null){
			return ConvertUtil.convertWebService(webservice);
		}
		return null;
	}

	@Override
	public WebServiceDTO findByAccessKey(String accessKey) {
		WebService webservice=webServiceRepository.findByAccessKey(accessKey);
		if(webservice!=null){
			return ConvertUtil.convertWebService(webservice);
		}
		return null;
	}
	

}
