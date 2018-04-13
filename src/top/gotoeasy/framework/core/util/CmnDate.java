package top.gotoeasy.framework.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @since 2018/01
 * @author 青松
 */
public class CmnDate {

	/** yyyyMMdd */
	public static final String	yyyyMMdd		= "yyyyMMdd";
	/** yyyy-MM-dd */
	public static final String	yyyy_MM_dd		= "yyyy-MM-dd";
	/** yyyy-MM-dd HH:mm */
	public static final String	yyyyMMddHHmm	= "yyyy-MM-dd HH:mm";
	/** yyyy-MM-dd HH:mm:ss */
	public static final String	yyyyMMddHHmmss	= "yyyy-MM-dd HH:mm:ss";

	public static Date tomorrow() {
		return addDay(today(), 1);
	}

	public static boolean isToday(Date date) {
		return CmnDate.format(new Date(), "yyyyMMdd").compareTo(CmnDate.format(date, "yyyyMMdd")) == 0;
	}

	public static Date today() {
		return toDate(CmnDate.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	public static String now() {
		return CmnDate.format(new Date(), yyyyMMddHHmmss);
	}

	public static Date yesterday() {
		return addDay(today(), -1);
	}

	public static Date addDay(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, amount);

		return cal.getTime();
	}

	public static String getPreMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);

		return format(cal.getTime(), "yyyy-MM") + "-01";
	}

	public static Date toDate(String strDate) {
		if ( CmnString.isBlank(strDate) ) {
			return null;
		}

		Date result = null;
		String strInput = strDate.replace("/", "-");

		if ( result == null ) {
			result = toDate(strInput, "yyyy-M-d");
		}
		if ( result == null ) {
			result = toDate(strInput, "yyyy-M-d HH:mm:ss");
		}
		if ( result == null ) {
			result = toDate(strInput, "yyyyMMdd");
		}
		if ( result == null ) {
			result = toDate(strInput, "yyyy-M-d HH:mm");
		}
		if ( result == null ) {
			result = toDate(strInput, "yyyy-M-d HH");
		}
		if ( result == null ) {
			result = toDate(strInput, "yyyy-M-d HH:mm:ss.SSS");
		}

		return result;
	}

	public static Date toDate(String strDate, String format) {
		if ( CmnString.isBlank(strDate) ) {
			return null;
		}

		Date result = null;

		try {
			SimpleDateFormat sdf = getSimpleDateFormat(format);
			result = sdf.parse(strDate);
		} catch (ParseException e) {
		}

		return result;
	}

	public static String format(Object date, String fmt) {
		if ( date == null ) {
			return "";
		}

		SimpleDateFormat sdf = getSimpleDateFormat(fmt);
		return sdf.format(date);
	}

	private static ThreadLocal<SimpleDateFormat>	threadLocal	= new ThreadLocal<SimpleDateFormat>();
	private static Object							lockObject	= new Object();

	private static SimpleDateFormat getSimpleDateFormat(String format) {
		SimpleDateFormat simpleDateFormat = threadLocal.get();
		if ( simpleDateFormat == null ) {
			synchronized ( lockObject ) {
				if ( simpleDateFormat == null ) {
					simpleDateFormat = new SimpleDateFormat(format);
					simpleDateFormat.setLenient(false);
					threadLocal.set(simpleDateFormat);
				}
			}
		}
		simpleDateFormat.applyPattern(format);
		return simpleDateFormat;
	}

}
