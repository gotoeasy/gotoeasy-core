package top.gotoeasy.framework.core.converter.impl;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnMath;
import top.gotoeasy.framework.core.util.CmnString;

public class ConvertString2Integer implements Converter<String, Integer> {

    @Override
    public Integer convert(String orig) {
        if ( CmnString.isBlank(orig) ) {
            return null;
        }
        return CmnMath.toBigDecimal(orig).intValue();
    }

}
