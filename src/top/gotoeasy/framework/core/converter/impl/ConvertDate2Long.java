package top.gotoeasy.framework.core.converter.impl;

import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertDate2Long implements Converter<Date, Long> {

    @Override
    public Long convert(Date orig) {
        return orig.getTime();
    }

}
