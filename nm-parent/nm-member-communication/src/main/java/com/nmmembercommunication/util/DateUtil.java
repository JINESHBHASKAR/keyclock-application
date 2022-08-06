package com.nmmembercommunication.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil {
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	public static final String DATE_PATTERN_SERVICE = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN_SERVICE_NO_SECONDS = "yyyy-MM-dd HH:mm";
	public static final String DATE_PATTERN_dd_MM_YYYY = "dd/MM/YYYY";
	public static final String DATE_PATTERN_dd_MMM_YYYY_HH_MM = "dd/MMM/YYYY HH:MM";
	public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_PATTERN_HH_mm = "HH:mm";
	
	public static Date getCurrentDate() {		
		log.info("inside getCurrentDate method - DateUtil");
		DateTime jodaDate = new DateTime(DateTimeZone.forID("Asia/Kolkata"));
		java.util.Date date = jodaDate.toDateTime().toDate();		
		return date;
	}
	
	public static String convertDateToStringPattern(Date date,String pattern) {
		log.info("inside convertDateToString method - DateUtil");
		DateFormat dateFormat = new SimpleDateFormat(pattern);  
        String strDate = dateFormat.format(date);  
		return strDate;
	}
		
	public static Date convertStringToDate(final String str){
        try{
        	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN_dd_MM_YYYY);
        	return DATE_FORMAT.parse(str);
        } catch(Exception ex){
           log.error(ex.getMessage());
        }
		return null;
    }
	
	public static Date convertStringToDateTime(final String str){
        try{
        	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
        	return DATE_FORMAT.parse(str);
        } catch(Exception ex){
           log.error(ex.getMessage());
        }
		return null;
    }
	
	//Adjust time for meeting , increase by 5 hours 30 Minutes
	public static Date addHoursToJavaUtilDate(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.MINUTE, 330);
	    return calendar.getTime();
	}
		
}
