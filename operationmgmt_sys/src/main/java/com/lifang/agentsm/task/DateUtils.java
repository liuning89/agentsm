package com.lifang.agentsm.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

/**
 * 日期操作类
 * @author zhuwenlin
 */
public class DateUtils {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将日期转换为指定格式的日期字符串
	 * @author 
	 * @param date 日期
	 * @param format 格式化模型,如："yyyy-MM-dd HH:mm:ss"
	 * @return 格式化后的日期字符串
	 */
	public static String formatDate(Date date, String format) {
		if (date == null){
			return "";
		}
		
		DateFormat df = new SimpleDateFormat(format);
		String dateString = df.format(date);
		return dateString;
	}

	/**
	 * 将日期字符串转换成指定格式的日期
	 * @author 
	 * @param dateString 日期字符串
	 * @param format 格式化模型,如："yyyy-MM-dd HH:mm:ss"
	 * @return date 指定格式的日期
	 */
	public static Date parseDate(String dateString, String format) {
		Date date = null;
		
		if (!StringUtils.isEmpty(dateString)){
			try {
				DateFormat df = new SimpleDateFormat(format);
				date = df.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 获取当前时间
	 * @author 
	 * @return 当前时间
	 */
	public static Date getNowTime() {
		return new Date();
	}

	/**
	 * 获取指定日期所在月份的月初
	 * @author 
	 * @param date 当前时间
	 * @return 指定日期所在月份的月初
	 */
	public static Date getFirstDay(Date date) {
		if (date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(5, 1);
			cal.set(11, 0);
			cal.set(12, 0);
			cal.set(13, 0);
			cal.set(14, 0);
			return cal.getTime();
		}
		return null;
	}
	
	/**
	 * 获取指定日期前一天的日期
	 * @author 
	 * @param date 日期参数
	 * @return 指定日期前一天的日期
	 */
	public static Date getLastDay(Date date) {
		if (date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(2, 1);
			cal.add(5, -1);
			cal.set(11, 23);
			cal.set(12, 59);
			cal.set(13, 59);
			cal.set(14, 999);
			return cal.getTime();
		}
		return null;
	}
	
	/**
	 * 获取指定日期的最后时刻
	 * 
	 * @author FUMENG
	 * @param date
	 *            日期参数
	 * @return 指定日期最后一秒的日期
	 */
	public static Date getCurDayLastTime(Date date) {
		if (null != date) {
			Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
			cal.clear();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			return cal.getTime();
		}
		return null;
	}
	
	/**
	 * 获取指定日期的最早时刻
	 * 
	 * @author FUMENG
	 * @param date
	 *            日期参数
	 * @return 指定日期最后一秒的日期
	 */
	public static Date getCurDayEarlyTime(Date date) {
		if (null != date) {
			Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
			cal.clear();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 00);
			cal.set(Calendar.MINUTE, 00);
			cal.set(Calendar.SECOND, 00);
			return cal.getTime();
		}
		return null;
	}

	/**
	 * 根据日期获取 Calendar实例
	 * 
	 * @author
	 * @param date
	 *            日期参数
	 * @return calendar Calendar实例
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 获取指定日期所在月份的最大天数
	 * @author 
	 * @param date 日期参数
	 * @return 日期所在月份的最大天数
	 */
	public static int getDaysInMonth(Date date) {
		if (date == null){
			return 0;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(5);
	}

	/**
	 * 获取指定日期的月份加  1 后的日期
	 * @author 
	 * @param date 日期参数
	 * @return 日期的月份加  1 后的日期
	 */
	public static Date getNexMonthDate(Date d) {
		return addMonth(d, 1);
	}

	/**
	 * 将日期的月份加 指定的数值
	 * @author 
	 * @param date 日期
	 * @param monthNumber 数值,如加一个月,则是 1
	 * @return 日期的月份加指定的数值后的日期
	 */
	public static Date addMonth(Date date, int monthNumber) {
		if (date == null){
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.add(2, monthNumber);
		return cal.getTime();
	}

	/**
	 * 获取指定日期的月份减  1 后的日期
	 * @author 
	 * @param date 日期参数
	 * @return 日期的月份加减 1 后的日期
	 */
	public static Date getPreviousMonth(Date d) {
		return addMonth(d, -1);
	}

	/**
	 * 获取指定年份所有月份的第一天的日期
	 * @author 
	 * @param year 年份
	 * @return dates 指定年份所有月份的第一天的日期
	 */
	public static Date[] getYearMonthList(int year) {
		Calendar cal = Calendar.getInstance();
		
		if (year > 0){
			cal.set(1, year);
		}
		
		cal.set(5, 1);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);

		Date[] dates = new Date[12];
		
		for (int i = 0; i < 12; i++) {
			Calendar c = (Calendar) cal.clone();
			c.set(2, i);
			dates[i] = c.getTime();
		}
		return dates;
	}

	/**
	 * 获取指定年月的月份中工作日的天数(即:该月非周六周日的天数)
	 * @author 
	 * @param year 年份
	 * @param month 月份
	 * @return 工作日的天数(即:该月非周六周日的天数)
	 */
	public static int getWorkingDay(String year, String month) {
		Calendar cal_start = Calendar.getInstance();
		cal_start.set(1, Integer.parseInt(year));
		cal_start.set(2, Integer.parseInt(month) - 1);
		cal_start.set(5, 1);

		Calendar cal_end = Calendar.getInstance();
		cal_end.set(1, Integer.parseInt(year));
		cal_end.set(2, Integer.parseInt(month) - 1);

		int monthdays = cal_end.getActualMaximum(5);
		cal_end.set(5, monthdays);

		return getWorkingDay(cal_start, cal_end);
	}
	
	/**
	 * 获取两个日期间相差的天数
	 * @author 
	 * @param calendar1 日期1
	 * @param calendar2 日期2
	 * @return 两个日期间相差的天数
	 */
	public static int getDaysBetween(Calendar calendar1, Calendar calendar2) {
		if (calendar1.after(calendar2)) {
			Calendar calendar = calendar1;
			calendar1 = calendar2;
			calendar2 = calendar;
		}
		
		int days = calendar2.get(6) - calendar1.get(6) + 1;
		int y2 = calendar2.get(1);
		
		if (calendar1.get(1) != y2) {
			calendar1 = (Calendar) calendar1.clone();
			do {
				days += calendar1.getActualMaximum(6);
				calendar1.add(1, 1);
			} while (calendar1.get(1) != y2);
		}
		return days;
	}

	/**
	 * 获取两个日期间的工作日的天数(即:非周六周日的天数)
	 * @author 
	 * @param calendar1   日期1
	 * @param calendar2   日期2
	 * @return result 两个日期间的工作日的天数(即:非周六周日的天数)
	 */
	public static int getWorkingDay(Calendar calendar1, Calendar calendar2) {
		int result = 0;
		int betweendays = getDaysBetween(calendar1, calendar2);
		int firstDayInWeek = calendar1.get(7);

		if (firstDayInWeek > 1) {
			result = 7 - firstDayInWeek;
			betweendays -= result + 1;
		}
		
		result += betweendays / 7 * 5;
		int days = betweendays % 7;
		
		if (days > 0) {
			result += days - 1;
		}
		return result;
	}
	
	/**
	 * 判断指定年份是否是闰年
	 * @author 
	 * @param year 年份
	 * @return result true表示是闰年,false表示不是
	 */
	public static boolean checkLeapYear(int year) {
		boolean result = false;
		
		if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){
			result = true;
		}
		return result;
	}
	
	/**
	 * 判断指定日期所在月份的最大天数
	 * @author 
	 * @param date 日期
	 * @return maxDay 日期所在月份的最大天数
	 */
	public static int getMaxDay(Date date){
		int maxDay = 0;
		
		if(date != null){
			Calendar calendar = DateUtils.getCalendar(date);
			maxDay = calendar.getActualMaximum(Calendar.DATE);
		}
		return maxDay;
	}
	
	public static void main(String[] args) {
		String dateString = "2011-02-08";
		Date date = DateUtils.parseDate(dateString, "yyyy-MM-dd");
		System.out.println("**************:" + DateUtils.getMaxDay(date));

		System.out.println(getCurDayLastTime(new Date()));
		
		System.out.println(getCurDayEarlyTime(new Date()));
	}
	
	/**
	 * 返回距离给定时间N天的时间。
	 * @param number:要返回的时间，距离今天最后一秒的天数。
	 * @return 返回距离今天count天的一个时间点。Date类型。
	 * */
	public static Date getDate(Date date, int number){
		//如果number为0，就不用计算，直接返回当前
		if(number==0){
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, number);
		
		return cal.getTime();
	}
	
	/**
	 * @param number:要返回的时间，距离今天的天数。
	 * @return 返回距离今天count天的一个时间点。String类型， yyyy-MM-dd HH:mm:ss格式。
	 * */
	public static String getDate(int number){
		Date date = new Date();
		String countDate = "";
		//如果number为0，就不用计算，直接返回当前
		if(number==0){
			countDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
			return countDate;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, number);
		countDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
		return countDate;
	}
}
