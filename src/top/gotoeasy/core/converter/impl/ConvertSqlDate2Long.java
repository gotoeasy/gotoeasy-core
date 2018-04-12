package top.gotoeasy.core.converter.impl;

import java.sql.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertSqlDate2Long implements Converter<Date, Long> {

    @Override
    public Long convert(Date orig) {
        return orig.getTime();
    }

}
