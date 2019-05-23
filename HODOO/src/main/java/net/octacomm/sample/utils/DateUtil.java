package net.octacomm.sample.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	public static String getMonday(String yyyy, String mm, String wk, int cur) {

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		int y = Integer.parseInt(yyyy);
		int m = Integer.parseInt(mm) - 1;
		int w = Integer.parseInt(wk);

		c.set(Calendar.YEAR, y);
		c.set(Calendar.MONTH, m);
		//
		c.set(Calendar.WEEK_OF_MONTH, w);
		if (cur == 1) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY - 1);
		} else {
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		return formatter.format(c.getTime());
	}

	/**
	 * 현재 요일 기준 월요일
	 * 
	 * @param dateStr
	 * @return String yyyy-MM-dd
	 */
	public static String getCurMonday(String dateStr) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//일요일일 경우 전주 월요일 구하기
		System.err.println("cal.get(Calendar.DAY_OF_WEEK) : " + cal.get(Calendar.DAY_OF_WEEK));
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			cal.add(Calendar.DATE, -1);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			return formatter.format(cal.getTime());
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return formatter.format(cal.getTime());

	}

	/**
	 * 월요일 기준 + 6일 후 일욜일
	 * 
	 * @param dateStr
	 *            (월요일 yyyy-MM-dd)
	 * @return String yyyy-MM-dd
	 */
	public static String getCurSunday(String dateStr) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, +6);
		return formatter.format(cal.getTime());

	}

}
