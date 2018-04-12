package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;
import top.gotoeasy.core.util.CmnMath;

public class ConvertLong2BigDecimal implements Converter<Long, BigDecimal> {

    @Override
    public BigDecimal convert(Long orig) {
        return CmnMath.toBigDecimal(orig);
    }

}
