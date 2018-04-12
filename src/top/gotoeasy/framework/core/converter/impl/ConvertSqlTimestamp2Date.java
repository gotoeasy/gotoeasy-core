package top.gotoeasy.framework.core.converter.impl;

import java.sql.Timestamp;
import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertSqlTimestamp2Date implements Converter<Timestamp, Date> {

    @Override
    public Date convert(Timestamp orig) {
        return new Date(orig.getTime());
    }

}
