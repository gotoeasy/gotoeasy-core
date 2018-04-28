package top.gotoeasy.framework.core.converter.impl;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;
import top.gotoeasy.framework.core.util.CmnString;

/**
 * 数据类型转换实现类(String to Long)
 * @since 2018/01
 * @author 青松
 */
public class ConvertString2Long implements Converter<String, Long> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public Long convert(String orig) {
		if ( CmnString.isBlank(orig) ) {
			return null;
		}
		return CmnMath.toBigDecimal(orig).longValue();
	}

}
