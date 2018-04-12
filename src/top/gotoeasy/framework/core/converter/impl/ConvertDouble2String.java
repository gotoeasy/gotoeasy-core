package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertDouble2String implements Converter<Double, String> {

    @Override
    public String convert(Double orig) {
        return new BigDecimal(orig).toPlainString();
    }

}
