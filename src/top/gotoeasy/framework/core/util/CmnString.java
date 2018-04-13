package top.gotoeasy.framework.core.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.gotoeasy.framework.core.converter.ConvertUtil;

/**
 * 字符串工具类
 * @since 2018/01
 * @author 青松
 */
public class CmnString {

	public static boolean wildcardsMatch(String pattern, String source) {
		if ( isEmpty(pattern) || isEmpty(source) ) {
			return false;
		}

		String regStrs = "$^[]{}()|.\\";
		StringBuilder buf = new StringBuilder();
		buf.append("^");
		char ch;
		for ( int i = 0; i < pattern.length(); i++ ) {
			ch = pattern.charAt(i);
			if ( ch == '*' ) {
				buf.append(".*");
			} else if ( ch == '?' ) {
				buf.append(".+");
			} else if ( regStrs.indexOf(ch) >= 0 ) {
				buf.append("\\").append(ch);
			} else {
				buf.append(ch);
			}
		}
		buf.append("$");

		return Pattern.compile(buf.toString()).matcher(source).matches();
	}

	public static String nullToDefault(String str, String defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}

	public static String nullToBlank(String str) {
		return isBlank(str) ? "" : str;
	}

	public static String left(String cs, int len) {
		if ( isBlank(cs) ) {
			return "";
		}

		if ( cs.length() <= len ) {
			return cs;
		}

		return cs.substring(0, len);
	}

	public static String right(String cs, int len) {
		if ( isBlank(cs) ) {
			return "";
		}

		if ( cs.length() <= len ) {
			return cs;
		}

		return cs.substring(cs.length() - len);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if ( obj == null ) {
			return true;
		}
		if ( obj instanceof String ) {
			return isBlank((String)obj);
		}
		if ( obj instanceof Collection ) {
			return ((Collection)obj).isEmpty();
		}

		return false;
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static String capitalize(String str) {
		if ( isBlank(str) ) {
			return str;
		}
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	public static String uncapitalize(String str) {
		if ( isBlank(str) ) {
			return str;
		}
		return Character.toLowerCase(str.charAt(0)) + str.substring(1);
	}

	public static String join(String[] strs, String separator) {
		StringBuilder buf = new StringBuilder();
		for ( String str : strs ) {
			if ( buf.length() > 0 ) {
				buf.append(separator);
			}
			buf.append(str);
		}
		return buf.toString();
	}

	/**
	 * 字符串参数替换
	 * <p>
	 * 对字符串中的“{任意字符}”按参数顺序进行替换
	 * </p>
	 * @param str 待替换字符串
	 * @param params 参数列表
	 * @return 替换结果
	 */
	public static String format(String str, Object ... params) {
		String rs = str;
		Pattern pattern = Pattern.compile("[\\{]{1}[\\w|\\s]*[\\}]{1}");  // {任意}
		Matcher matcher = pattern.matcher(str);
		int i = 0;
		String tmp = null;
		while ( matcher.find() ) {
			if ( i < params.length ) {
				tmp = ConvertUtil.canConvert(params[i], String.class) ? ConvertUtil.convert(params[i], String.class) : params[i].toString();
				rs = rs.replace(matcher.group(), tmp == null ? "null" : tmp);
			}
			i++;
		}
		return rs;
	}

	/**
	 * 重复字符串
	 * @param text 字符串
	 * @param count 重复次数
	 * @return 字符串
	 */
	public static String repeat(String text, int count) {
		StringBuilder buf = new StringBuilder();
		for ( int i = 0; i < count; i++ ) {
			buf.append(text);
		}
		return buf.toString();
	}

}
