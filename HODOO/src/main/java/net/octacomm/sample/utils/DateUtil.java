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
    
}
