package top.gotoeasy.framework.core.converter.impl;

import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertLong2Date implements Converter<Long, Date> {

    @Override
    public Date convert(Long orig) {
        return new Date(orig);
    }

}
