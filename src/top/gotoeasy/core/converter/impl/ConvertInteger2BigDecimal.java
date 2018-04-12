package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;
import top.gotoeasy.core.util.CmnMath;

public class ConvertInteger2BigDecimal implements Converter<Integer, BigDecimal> {

    @Override
    public BigDecimal convert(Integer orig) {
        return CmnMath.toBigDecimal(orig);
    }

}
