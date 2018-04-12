package top.gotoeasy.core.converter.impl;

import java.util.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertSqlDate2Date implements Converter<java.sql.Date, Date> {

    @Override
    public Date convert(java.sql.Date orig) {
        return new Date(orig.getTime());
    }

}
