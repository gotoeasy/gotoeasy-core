package top.gotoeasy.framework.core.converter.impl;

import java.sql.Date;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnDate;

/**
 * 数据类型转换实现类(Date to String)
 * @since 2018/01
 * @author 青松
 */
public class ConvertDate2String implements Converter<Date, String> {

	/**
	 * 类型转换
	 * @param orig 转换前对象
	 * @return 转换后对象
	 */
	@Override
	public String convert(Date orig) {
		return CmnDate.format(orig, "yyyy-MM-dd HH:mm:ss.SSS");
	}

}
