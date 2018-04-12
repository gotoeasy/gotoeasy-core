package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;
import top.gotoeasy.core.util.CmnMath;

public class ConvertDouble2BigDecimal implements Converter<Double, BigDecimal> {

    @Override
    public BigDecimal convert(Double orig) {
        return CmnMath.toBigDecimal(orig);
    }

}
