package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;

public class ConvertDouble2BigDecimal implements Converter<Double, BigDecimal> {

    @Override
    public BigDecimal convert(Double orig) {
        return CmnMath.toBigDecimal(orig);
    }

}
