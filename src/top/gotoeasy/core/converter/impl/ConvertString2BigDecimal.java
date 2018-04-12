package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.core.converter.Converter;
import top.gotoeasy.core.util.CmnMath;
import top.gotoeasy.core.util.CmnString;

public class ConvertString2BigDecimal implements Converter<String, BigDecimal> {

    @Override
    public BigDecimal convert(String orig) {
        if ( CmnString.isBlank(orig) ) {
            return null;
        }
        return CmnMath.toBigDecimal(orig);
    }

}
