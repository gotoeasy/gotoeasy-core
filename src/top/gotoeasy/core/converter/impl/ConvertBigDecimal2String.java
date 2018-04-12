package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;

public class ConvertBigDecimal2String implements Converter<BigDecimal, String> {

    @Override
    public String convert(BigDecimal orig) {
        return orig.toPlainString();
    }

}
