package top.gotoeasy.core.converter.impl;

import java.sql.Time;
import java.util.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertSqlTime2Date implements Converter<Time, Date> {

    @Override
    public Date convert(Time orig) {
        return new Date(orig.getTime());
    }

}
