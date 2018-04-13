package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据类型转换实现类(BigDecimal to String)
 * @since 2018/01
 * @author 青松
 */
public class ConvertBigDecimal2String implements Converter<BigDecimal, String> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public String convert(BigDecimal orig) {
		return orig.toPlainString();
	}

}
