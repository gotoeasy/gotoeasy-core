package top.gotoeasy.framework.core.converter.impl;

import java.sql.Time;
import java.util.Date;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertDate2SqlTime implements Converter<Date, Time> {

    @Override
    public Time convert(Date orig) {
        return new Time(orig.getTime());
    }

}
