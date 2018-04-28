package top.gotoeasy.framework.core.converter.impl;

import top.gotoeasy.framework.core.converter.Converter;

/**
 * 数据类型转换实现类(Boolean to String)
 * @since 2018/01
 * @author 青松
 */
public class ConvertBoolean2String implements Converter<Boolean, String> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public String convert(Boolean orig) {
		return orig.toString();
	}

}
