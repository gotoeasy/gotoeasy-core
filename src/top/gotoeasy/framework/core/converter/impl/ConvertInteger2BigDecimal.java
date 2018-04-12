package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;

public class ConvertInteger2BigDecimal implements Converter<Integer, BigDecimal> {

    @Override
    public BigDecimal convert(Integer orig) {
        return CmnMath.toBigDecimal(orig);
    }

}
