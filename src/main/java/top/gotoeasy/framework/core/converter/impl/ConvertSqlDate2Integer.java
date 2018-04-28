package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;
import java.sql.Date;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据类型转换实现类(java.sql.Date to Integer)
 * @since 2018/01
 * @author 青松
 */
public class ConvertSqlDate2Integer implements Converter<Date, Integer> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public Integer convert(Date orig) {
		return new BigDecimal(orig.getTime()).intValue();
	}

}
