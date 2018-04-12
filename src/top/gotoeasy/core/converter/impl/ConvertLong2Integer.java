package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;

public class ConvertLong2Integer implements Converter<Long, Integer> {

    @Override
    public Integer convert(Long orig) {
        return new BigDecimal(orig).intValue();
    }

}
