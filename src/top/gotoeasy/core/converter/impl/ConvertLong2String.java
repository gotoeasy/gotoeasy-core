package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;

public class ConvertLong2String implements Converter<Long, String> {

    @Override
    public String convert(Long orig) {
        return new BigDecimal(orig).toPlainString();
    }

}
