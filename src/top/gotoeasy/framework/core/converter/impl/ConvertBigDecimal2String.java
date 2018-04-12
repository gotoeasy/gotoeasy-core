package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertBigDecimal2String implements Converter<BigDecimal, String> {

    @Override
    public String convert(BigDecimal orig) {
        return orig.toPlainString();
    }

}
