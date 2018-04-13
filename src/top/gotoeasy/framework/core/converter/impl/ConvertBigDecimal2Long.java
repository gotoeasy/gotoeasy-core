package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据类型转换实现类(BigDecimal to Long)
 * @since 2018/01
 * @author 青松
 */
public class ConvertBigDecimal2Long implements Converter<BigDecimal, Long> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public Long convert(BigDecimal orig) {
		return orig.longValue();
	}

}
