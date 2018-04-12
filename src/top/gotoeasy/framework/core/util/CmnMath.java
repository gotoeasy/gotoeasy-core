package top.gotoeasy.framework.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CmnMath {

	public static int random(int min, int max) {
		return (int)(min + Math.random() * (max - min + 1));
	}

	public static BigDecimal multiply(BigDecimal val1, BigDecimal val2) {
		if ( val1 == null || val2 == null ) {
			return null;
		}
		return val1.multiply(val2);
	}

	public static BigDecimal multiply(BigDecimal val1, Integer val2) {
		if ( val1 == null || val2 == null ) {
			return null;
		}
		return multiply(val1, new BigDecimal(val2));
	}

	public static BigDecimal add(BigDecimal ... values) {
		BigDecimal total = BigDecimal.ZERO;
		for ( int i = 0; i < values.length; i++ ) {
			if ( values[i] != null ) {
				total = total.add(values[i]);
			}
		}
		return total;
	}

	public static String toPlainString(Integer val) {
		return new BigDecimal(val).toPlainString();
	}

	public static String toPlainString(Long val) {
		return new BigDecimal(val).toPlainString();
	}

	public static BigDecimal toBigDecimal(String val) {
		if ( CmnString.isBlank(val) ) {
			return null;
		}
		String num = val.replaceAll(",", "");
		if ( CmnString.isBlank(num) ) {
			return null;
		}
		return new BigDecimal(num);
	}

	public static BigDecimal toBigDecimal(Integer val) {
		if ( val == null ) {
			return null;
		}
		return new BigDecimal(val);
	}

	public static BigDecimal toBigDecimal(Long val) {
		if ( val == null ) {
			return null;
		}
		return new BigDecimal(val);
	}

	public static BigDecimal toBigDecimal(Double val) {
		if ( val == null ) {
			return null;
		}
		return new BigDecimal(val);
	}

	public static BigDecimal toBigDecimal(Float val) {
		if ( val == null ) {
			return null;
		}
		return new BigDecimal(val);
	}

	public static String format(BigDecimal val, String format) {
		if ( val == null ) {
			return null;
		}

		DecimalFormat df = new DecimalFormat();
		df.applyPattern(format);
		return df.format(val);
	}

	public static String formatMoney(BigDecimal val) {
		return format(val, "###,##0");
	}

}
