package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertBigDecimal2Double implements Converter<BigDecimal, Double> {

    @Override
    public Double convert(BigDecimal orig) {
        return orig.doubleValue();
    }

}
