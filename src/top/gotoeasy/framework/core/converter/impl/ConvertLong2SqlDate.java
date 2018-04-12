package top.gotoeasy.framework.core.converter.impl;

import java.sql.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertLong2SqlDate implements Converter<Long, Date> {

    @Override
    public Date convert(Long orig) {
        return new Date(orig);
    }

}
