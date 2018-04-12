package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertLong2String implements Converter<Long, String> {

    @Override
    public String convert(Long orig) {
        return new BigDecimal(orig).toPlainString();
    }

}
