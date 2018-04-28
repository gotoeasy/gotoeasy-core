package top.gotoeasy.framework.core.converter.impl;

import java.sql.Timestamp;
import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据类型转换实现类(Date to java.sql.Timestamp)
 * @since 2018/01
 * @author 青松
 */
public class ConvertDate2SqlTimestamp implements Converter<Date, Timestamp> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public Timestamp convert(Date orig) {
		return new Timestamp(orig.getTime());
	}

}
