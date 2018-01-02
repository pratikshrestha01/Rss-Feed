package com.boilerplate.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boilerplate.entity.User;
import com.boilerplate.repositories.UserRepository;
import com.boilerplate.util.BugMail;

@Controller
public class AuthFailureHandler {
	
	

	
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/authenticationFailure")
	public String authenticationFailure(ModelMap modelMap, HttpServletRequest request) {
		try {
			
			HttpServletRequest curRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();
			
			String username =(String) curRequest.getSession().getAttribute("TMKauthUserName");
			String prevalid = (String) curRequest.getSession().getAttribute("TMKauthValidity");
			
			
			curRequest.getSession().setAttribute("TMKauthUserName", "");
			curRequest.getSession().setAttribute("TMKauthValidity", "");
			
			/*if(prevalid!=null){
				if(prevalid.equals("true")){*/
					if(username!=null){
						User u=userRepository.findByUsername(username);
						if(u==null){
							return "redirect:/main?errormessage=authentication.login.failed";
						}else{
							
							try{
								
							long diff = new Date().getTime() - u.getLastAccess().getTime();

							if (diff > 120000 && u.getAccessTotal() >= 3) {
								
								u.setAccessTotal(0);
								userRepository.save(u);
								
							} 
							}catch(Exception e){
								BugMail.Bugmail(e);
								e.printStackTrace();
							}
							if(u.getAccessTotal()>=3){
								return "redirect:/main?errormessage=temprory.blocked";
							}else{
								u.setAccessTotal((u.getAccessTotal()+1));
								u.setLastAccess(new Date());
								userRepository.save(u);
							}
							
							
						}
					}
				/*}else{
					
				}
			}*/
			
			
			
			
			
		} catch (Exception e) {
			BugMail.Bugmail(e);
			e.printStackTrace();
			return "redirect:/main?errormessage=authentication.login.failed";
		}
		return "redirect:/main?errormessage=authentication.login.failed";
	}
	

}
