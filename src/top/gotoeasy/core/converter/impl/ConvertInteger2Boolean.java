package top.gotoeasy.core.converter.impl;

import top.gotoeasy.core.converter.Converter;

public class ConvertInteger2Boolean implements Converter<Integer, Boolean> {

    @Override
    public Boolean convert(Integer orig) {
        return orig != 0;
    }

}
