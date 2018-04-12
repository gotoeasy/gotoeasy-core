package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertInteger2String implements Converter<Integer, String> {

    @Override
    public String convert(Integer orig) {
        return new BigDecimal(orig).toPlainString();
    }

}
