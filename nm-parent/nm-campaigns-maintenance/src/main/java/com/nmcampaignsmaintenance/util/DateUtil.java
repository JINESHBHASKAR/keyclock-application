package com.nmcampaignsmaintenance.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	
	public static final String TIME_PATTERN_HH_mm = "HH:mm";
	
	public static Date getCurrentDate() {		
		log.info("inside getCurrentDate method - DateUtil");
		DateTime jodaDate = new DateTime(DateTimeZone.forID("Asia/Kolkata"));
		java.util.Date date = jodaDate.toDate();				
		return date;
	}
	
	public static String convertDateToGivenStringPattern(Date date,String pattern) {
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
	
	/*public static void main(String[] args) {
		Date date = new Date();
		System.out.println(convertDateToString(date));
	}*/
}
