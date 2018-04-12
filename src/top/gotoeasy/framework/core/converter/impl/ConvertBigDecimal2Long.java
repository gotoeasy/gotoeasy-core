package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertBigDecimal2Long implements Converter<BigDecimal, Long> {

    @Override
    public Long convert(BigDecimal orig) {
        return orig.longValue();
    }

}
