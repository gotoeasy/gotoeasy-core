package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertBigDecimal2Integer implements Converter<BigDecimal, Integer> {

    @Override
    public Integer convert(BigDecimal orig) {
        return orig.intValue();
    }

}
