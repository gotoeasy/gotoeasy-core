package top.gotoeasy.framework.core.converter.impl;

import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertSqlDate2Date implements Converter<java.sql.Date, Date> {

    @Override
    public Date convert(java.sql.Date orig) {
        return new Date(orig.getTime());
    }

}
