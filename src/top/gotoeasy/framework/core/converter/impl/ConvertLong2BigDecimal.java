package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;

public class ConvertLong2BigDecimal implements Converter<Long, BigDecimal> {

    @Override
    public BigDecimal convert(Long orig) {
        return CmnMath.toBigDecimal(orig);
    }

}
