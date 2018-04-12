package top.gotoeasy.framework.core.converter.impl;

import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertInteger2Date implements Converter<Integer, Date> {

    @Override
    public Date convert(Integer orig) {
        return new Date(orig);
    }

}
