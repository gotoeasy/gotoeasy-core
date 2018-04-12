package top.gotoeasy.core.converter.impl;

import java.math.BigDecimal;
import java.util.Date;

import top.gotoeasy.core.converter.Converter;

public class ConvertDate2Integer implements Converter<Date, Integer> {

    @Override
    public Integer convert(Date orig) {
        return new BigDecimal(orig.getTime()).intValue();
    }

}
