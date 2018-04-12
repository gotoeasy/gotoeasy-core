package top.gotoeasy.core.converter.impl;

import java.util.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertLong2Date implements Converter<Long, Date> {

    @Override
    public Date convert(Long orig) {
        return new Date(orig);
    }

}
