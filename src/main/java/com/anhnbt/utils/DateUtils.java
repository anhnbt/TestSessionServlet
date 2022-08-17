package com.anhnbt.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static int getYearFromDate(Date date) {
		int year = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			year = cal.get(Calendar.YEAR);
		}
		return year;
	}

	public static int getMonthFromDate(Date date) {
		int month = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			month = cal.get(Calendar.MONTH);
		}
		return month;
	}

	public static int getDayFromDate(Date date) {
		int day = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			day = cal.get(Calendar.DATE);
		}
		return day;
	}
}
