package top.gotoeasy.framework.core.converter.impl;

import java.math.BigDecimal;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;
import top.gotoeasy.framework.core.util.CmnString;

public class ConvertString2BigDecimal implements Converter<String, BigDecimal> {

    @Override
    public BigDecimal convert(String orig) {
        if ( CmnString.isBlank(orig) ) {
            return null;
        }
        return CmnMath.toBigDecimal(orig);
    }

}
