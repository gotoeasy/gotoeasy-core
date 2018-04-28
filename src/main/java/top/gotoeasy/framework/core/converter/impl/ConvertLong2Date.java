package top.gotoeasy.framework.core.converter.impl;

import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据类型转换实现类(Long to Date)
 * @since 2018/01
 * @author 青松
 */
public class ConvertLong2Date implements Converter<Long, Date> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public Date convert(Long orig) {
		return new Date(orig);
	}

}
