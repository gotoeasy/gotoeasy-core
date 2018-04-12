package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据转换类
 * @since 2018/01
 * @author 青松
 */
public class ConvertBigDecimal2Double implements Converter<BigDecimal, Double> {

	@Override
	public Double convert(BigDecimal orig) {
		return orig.doubleValue();
	}

}
