package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;

public class ConvertInteger2String implements Converter<Integer, String> {

    @Override
    public String convert(Integer orig) {
        return new BigDecimal(orig).toPlainString();
    }

}
