package top.gotoeasy.framework.core.converter.impl;

import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;
import top.gotoeasy.framework.core.util.CmnDate;
import top.gotoeasy.framework.core.util.CmnString;

public class ConvertString2Date implements Converter<String, Date> {

    @Override
    public Date convert(String orig) {
        if ( CmnString.isBlank(orig) ) {
            return null;
        }
        return CmnDate.toDate(orig);
    }

}
