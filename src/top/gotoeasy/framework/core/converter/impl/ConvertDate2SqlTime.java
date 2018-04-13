package top.gotoeasy.framework.core.converter.impl;

import java.sql.Time;
import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据类型转换实现类(Date to java.sql.Time)
 * @since 2018/01
 * @author 青松
 */
public class ConvertDate2SqlTime implements Converter<Date, Time> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public Time convert(Date orig) {
		return new Time(orig.getTime());
	}

}
