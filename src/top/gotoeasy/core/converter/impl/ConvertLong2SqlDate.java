package top.gotoeasy.core.converter.impl;

import java.sql.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertLong2SqlDate implements Converter<Long, Date> {

    @Override
    public Date convert(Long orig) {
        return new Date(orig);
    }

}
