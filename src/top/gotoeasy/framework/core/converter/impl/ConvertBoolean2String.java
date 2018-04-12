package top.gotoeasy.framework.core.converter.impl;

import top.gotoeasy.framework.core.converter.Converter;

public class ConvertBoolean2String implements Converter<Boolean, String> {

    @Override
    public String convert(Boolean orig) {
        return orig.toString();
    }

}
