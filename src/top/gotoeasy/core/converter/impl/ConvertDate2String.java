package top.gotoeasy.core.converter.impl;

import java.sql.Date;

import top.gotoeasy.core.converter.Converter;
import top.gotoeasy.core.util.CmnDate;

public class ConvertDate2String implements Converter<Date, String> {

    @Override
    public String convert(Date orig) {
        return CmnDate.format(orig, "yyyy-MM-dd HH:mm:ss.SSS");
    }

}
