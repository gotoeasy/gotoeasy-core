package top.gotoeasy.framework.core.converter.impl;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;
import top.gotoeasy.framework.core.util.CmnString;

public class ConvertString2Long implements Converter<String, Long> {

    @Override
    public Long convert(String orig) {
        if ( CmnString.isBlank(orig) ) {
            return null;
        }
        return CmnMath.toBigDecimal(orig).longValue();
    }

}
