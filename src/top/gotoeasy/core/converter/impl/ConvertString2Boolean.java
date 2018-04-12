package top.gotoeasy.core.converter.impl;

import top.gotoeasy.core.converter.Converter;
import top.gotoeasy.core.util.CmnString;

public class ConvertString2Boolean implements Converter<String, Boolean> {

    @Override
    public Boolean convert(String orig) {
        if ( CmnString.isBlank(orig) ) {
            return null;
        }
        return Boolean.valueOf(orig);
    }

}
