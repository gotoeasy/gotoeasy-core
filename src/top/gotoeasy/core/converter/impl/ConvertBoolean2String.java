package top.gotoeasy.core.converter.impl;

import top.gotoeasy.core.converter.Converter;

public class ConvertBoolean2String implements Converter<Boolean, String> {

    @Override
    public String convert(Boolean orig) {
        return orig.toString();
    }

}
