package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;
import top.gotoeasy.framework.core.util.CmnString;

/**
 * 数据类型转换实现类(String to BigDecimal)
 * @since 2018/01
 * @author 青松
 */
public class ConvertString2BigDecimal implements Converter<String, BigDecimal> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public BigDecimal convert(String orig) {
		if ( CmnString.isBlank(orig) ) {
			return null;
		}
		return CmnMath.toBigDecimal(orig);
	}

}
