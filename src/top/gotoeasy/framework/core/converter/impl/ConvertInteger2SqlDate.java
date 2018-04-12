package top.gotoeasy.framework.core.converter.impl;

import java.sql.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertInteger2SqlDate implements Converter<Integer, Date> {

    @Override
    public Date convert(Integer orig) {
        return new Date(orig);
    }

}
