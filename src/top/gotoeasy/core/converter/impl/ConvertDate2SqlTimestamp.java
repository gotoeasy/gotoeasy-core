package top.gotoeasy.core.converter.impl;

import java.sql.Timestamp;
import java.util.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertDate2SqlTimestamp implements Converter<Date, Timestamp> {

    @Override
    public Timestamp convert(Date orig) {
        return new Timestamp(orig.getTime());
    }

}
