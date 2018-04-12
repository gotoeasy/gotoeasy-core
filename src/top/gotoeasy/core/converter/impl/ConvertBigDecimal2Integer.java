package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;

public class ConvertBigDecimal2Integer implements Converter<BigDecimal, Integer> {

    @Override
    public Integer convert(BigDecimal orig) {
        return orig.intValue();
    }

}
