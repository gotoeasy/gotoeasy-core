package top.gotoeasy.core.converter.impl;

import java.util.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertDate2SqlDate implements Converter<Date, java.sql.Date> {

    @Override
    public java.sql.Date convert(Date orig) {
        return new java.sql.Date(orig.getTime());
    }

}
