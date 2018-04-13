package top.gotoeasy.framework.core.converter.impl;

import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnDate;
import top.gotoeasy.framework.core.util.CmnString;

/**
 * 数据类型转换实现类(String to Date)
 * @since 2018/01
 * @author 青松
 */
public class ConvertString2Date implements Converter<String, Date> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public Date convert(String orig) {
		if ( CmnString.isBlank(orig) ) {
			return null;
		}
		return CmnDate.toDate(orig);
	}

}
