package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    public static Date plusDays(Date date, int days) {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); 
        return cal.getTime();
    }
    
    public static Date createDate(int year, int month, int day ){
    	 Calendar cal = Calendar.getInstance();
    	 cal.set(year, month, day); //Year, month and day of month
    	 Date date = cal.getTime();
    	 return date;
    }
   
    
}
