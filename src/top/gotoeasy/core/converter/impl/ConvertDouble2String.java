package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;

public class ConvertDouble2String implements Converter<Double, String> {

    @Override
    public String convert(Double orig) {
        return new BigDecimal(orig).toPlainString();
    }

}
