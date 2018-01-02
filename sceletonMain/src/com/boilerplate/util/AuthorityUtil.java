package com.boilerplate.util;

public class AuthorityUtil {
	
	public static boolean isAdminOrSuperZoOrRegZoOrZo(String authority){
		if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
				|| (authority.contains(Authorities.SUPER_ZONE) && authority.contains(Authorities.AUTHENTICATED))
				|| (authority.contains(Authorities.REGIONAL_ZONE) && authority.contains(Authorities.AUTHENTICATED))
			|| (authority.contains(Authorities.ZONE) && authority.contains(Authorities.AUTHENTICATED))) {
			return true;
		}
		else{
			return false;
		}
	
	}
	
	public static boolean isAll(String authority){
		if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
				|| (authority.contains(Authorities.SUPER_ZONE) && authority.contains(Authorities.AUTHENTICATED))
				|| (authority.contains(Authorities.REGIONAL_ZONE) && authority.contains(Authorities.AUTHENTICATED))
			|| (authority.contains(Authorities.ZONE) && authority.contains(Authorities.AUTHENTICATED))
			|| (authority.contains(Authorities.POINT) && authority.contains(Authorities.AUTHENTICATED))
			|| (authority.contains(Authorities.CUSTOMER) && authority.contains(Authorities.AUTHENTICATED))
			|| (authority.contains(Authorities.MERCHANT) && authority.contains(Authorities.AUTHENTICATED))) {
			return true;
		}
		else{
			return false;
		}
	
	}
	
	
	public static boolean isPointOrCustomer(String authority){
		if ((authority.contains(Authorities.POINT) && authority.contains(Authorities.AUTHENTICATED))
			|| (authority.contains(Authorities.CUSTOMER) && authority.contains(Authorities.AUTHENTICATED))) {
			return true;
		}
		else{
			return false;
		}
	
	}
	
	/*public static boolean isCustomer(String authority){
		if (authority.contains(Authorities.CUSTOMER) && authority.contains(Authorities.AUTHENTICATED)
				|| (authority.contains(Authorities.BENEFICIARY_CUSTOMER) && authority.contains(Authorities.AUTHENTICATED))
				|| (authority.contains(Authorities.SENDER_CUSTOMER) && authority.contains(Authorities.AUTHENTICATED))){
			return true;
		}
		else{
			return false;
		}
	
	}*/

}
