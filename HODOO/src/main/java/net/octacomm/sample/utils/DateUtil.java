package net.octacomm.sample.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    public static String getCurrentDatetime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }
    public static String getOnlyCurrentDateAndHour() {
    	return new SimpleDateFormat("yyyy-MM-dd:HH").format(Calendar.getInstance().getTime());
    }
    
    public static String getCurrentMonth() {
        return new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
    }
    
    public static String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
    }
    
    
    public static String getMonday(String yyyy,String mm, String wk , int cur){

 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
 		Calendar c = Calendar.getInstance();

 		int y=Integer.parseInt(yyyy);
 		int m=Integer.parseInt(mm)-1;
 		int w = Integer.parseInt(wk);

 		c.set(Calendar.YEAR,y);
 		c.set(Calendar.MONTH,m);
 		//
 		c.set(Calendar.WEEK_OF_MONTH ,w);
 		if(cur == 1) {
 			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY - 1);
 	 	}else{
 	 		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
 	 	}
 		return formatter.format(c.getTime());
 	}
}
