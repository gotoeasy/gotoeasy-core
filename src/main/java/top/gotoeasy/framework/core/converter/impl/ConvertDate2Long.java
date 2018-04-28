package top.gotoeasy.framework.core.converter.impl;

import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据类型转换实现类(Date to Long)
 * @since 2018/01
 * @author 青松
 */
public class ConvertDate2Long implements Converter<Date, Long> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public Long convert(Date orig) {
		return orig.getTime();
	}

}
