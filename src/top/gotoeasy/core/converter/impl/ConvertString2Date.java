package top.gotoeasy.core.converter.impl;

import java.util.Date;

import top.gotoeasy.core.converter.Converter;
import top.gotoeasy.core.util.CmnDate;
import top.gotoeasy.core.util.CmnString;

public class ConvertString2Date implements Converter<String, Date> {

    @Override
    public Date convert(String orig) {
        if ( CmnString.isBlank(orig) ) {
            return null;
        }
        return CmnDate.toDate(orig);
    }

}
