package top.gotoeasy.core.converter.impl;

import java.sql.Timestamp;
import java.util.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertSqlTimestamp2Date implements Converter<Timestamp, Date> {

    @Override
    public Date convert(Timestamp orig) {
        return new Date(orig.getTime());
    }

}
