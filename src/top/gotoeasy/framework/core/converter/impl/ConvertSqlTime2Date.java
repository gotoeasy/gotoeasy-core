package top.gotoeasy.framework.core.converter.impl;

import java.sql.Time;
import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertSqlTime2Date implements Converter<Time, Date> {

    @Override
    public Date convert(Time orig) {
        return new Date(orig.getTime());
    }

}
