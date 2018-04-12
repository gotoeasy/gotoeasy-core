package top.gotoeasy.core.converter.impl;

import java.sql.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertInteger2SqlDate implements Converter<Integer, Date> {

    @Override
    public Date convert(Integer orig) {
        return new Date(orig);
    }

}
