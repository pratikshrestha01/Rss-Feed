package com.boilerplate.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.boilerplate.model.UserType;

public class EnumGetter {

	public static List<String> getUsetTypeEnum() {

		List<String> myenumlist = new ArrayList<String>();
		if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.ADMIN) {
			myenumlist = getUserTypeForAdmin();
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.SUPER_ZONE) {
			myenumlist = getUserTypeForSuperZone();
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.REGIONAL_ZONE) {
			myenumlist = getUserTypeForRegionalZone();
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.ZONE) {
			myenumlist = getUserTypeForZone();
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.POINT) {
			myenumlist = getUserTypeForPoint();
		}

		return myenumlist;
	}

	public static List<String> getUserTypeForAdmin() {

		UserType[] list = UserType.values();
		List<String> allTypes = new ArrayList<String>();
		for (int i = 0; i < list.length; i++) {

			allTypes.add(list[i].name());

		}

		return allTypes;
	}

	public static List<String> getUserTypeForSuperZone() {

		UserType[] list = UserType.values();
		List<String> allTypes = new ArrayList<String>();
		for (int i = 0; i < list.length; i++) {
			if (list[i] == UserType.REGIONAL_ZONE || list[i] == UserType.ZONE || list[i] == UserType.POINT
					|| list[i] == UserType.CUSTOMER) {
				allTypes.add(list[i].name());
			}

		}

		return allTypes;
	}


	public static List<String> getUserTypeForRegionalZone() {

		UserType[] list = UserType.values();
		List<String> allTypes = new ArrayList<String>();
		for (int i = 0; i < list.length; i++) {
			if (list[i] == UserType.ZONE || list[i] == UserType.POINT || list[i] == UserType.CUSTOMER) {
				allTypes.add(list[i].name());
			}

		}

		return allTypes;
	}

	public static List<String> getUserTypeForZone() {

		UserType[] list = UserType.values();
		List<String> allTypes = new ArrayList<String>();
		for (int i = 0; i < list.length; i++) {
			if (list[i] == UserType.POINT || list[i] == UserType.CUSTOMER) {
				allTypes.add(list[i].name());
			}

		}

		return allTypes;
	}

	public static List<String> getUserTypeForPoint() {

		UserType[] list = UserType.values();
		List<String> allTypes = new ArrayList<String>();
		for (int i = 0; i < list.length; i++) {
			if (list[i] == UserType.CUSTOMER) {
				allTypes.add(list[i].name());
			}

		}

		return allTypes;
	}




	public static List<String> getEnumForTemplate() {
		UserType[] Type = UserType.values();
		List<String> allTypes = new ArrayList<String>();
		for (int i = 0; i < Type.length; i++) {
			if (Type[i] == UserType.ADMIN) {
				allTypes.add(Type[i].name());
			} else if (Type[i] == UserType.SUPER_ZONE) {
				allTypes.add(Type[i].name());
			} else if (Type[i] == UserType.REGIONAL_ZONE) {
				allTypes.add(Type[i].name());
			} else if (Type[i] == UserType.ZONE) {
				allTypes.add(Type[i].name());
			} else if (Type[i] == UserType.POINT) {
				allTypes.add(Type[i].name());
			} else if (Type[i] == UserType.CUSTOMER) {
				allTypes.add(Type[i].name());
			} else if (Type[i] == UserType.MERCHANT) {
				allTypes.add(Type[i].name());
			}

		}

		return allTypes;

	}

	public static String[] getEnumStringValues(Class<? extends Enum<?>> e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}

}
