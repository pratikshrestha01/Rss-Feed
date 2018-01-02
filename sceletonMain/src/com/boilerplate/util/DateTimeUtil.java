package com.boilerplate.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	
	public static Date convertStringToDate(String dateStr){
		Date date = new Date();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(dateStr);
		}catch(Exception ex){
			BugMail.Bugmail(ex);
			ex.printStackTrace();
		}
		
		return date;
	}
	
	public static String convertDateToString(Date date){
		String dateStr = null ;
		if(date != null){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dateStr = sdf.format(date);
			}catch(Exception ex){
				BugMail.Bugmail(ex);
				ex.printStackTrace();
			}
		}
		return dateStr;
	}

}
