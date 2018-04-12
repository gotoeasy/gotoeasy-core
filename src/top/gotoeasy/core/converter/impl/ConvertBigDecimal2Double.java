package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;

public class ConvertBigDecimal2Double implements Converter<BigDecimal, Double> {

    @Override
    public Double convert(BigDecimal orig) {
        return orig.doubleValue();
    }

}
