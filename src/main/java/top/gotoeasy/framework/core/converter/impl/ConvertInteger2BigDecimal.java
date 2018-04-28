package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;

/**
 * 数据类型转换实现类(Integer to BigDecimal)
 * @since 2018/01
 * @author 青松
 */
public class ConvertInteger2BigDecimal implements Converter<Integer, BigDecimal> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public BigDecimal convert(Integer orig) {
		return CmnMath.toBigDecimal(orig);
	}

}
