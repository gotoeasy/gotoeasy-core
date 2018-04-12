package top.gotoeasy.framework.core.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.gotoeasy.framework.core.util.CmnClass;

public class ConvertUtil {

    private static final Logger                 log          = LoggerFactory.getLogger(ConvertUtil.class);

    private static Map<String, Converter<?, ?>> mapConverter = new HashMap<>();

    public static boolean canConvert(Object orig, Class<?> toClass) {
        if ( orig == null || toClass.isInstance(orig) ) {
            return true;
        }
        return mapConverter.containsKey(getConvertKey(orig, toClass));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T convert(Object orig, Class<T> toClass) {

        if ( orig == null ) {
            return null;
        }

        if ( toClass.isInstance(orig) ) {
            return (T)orig;
        }

        Converter converter = mapConverter.get(getConvertKey(orig, toClass));
        if ( converter != null ) {
            return (T)converter.convert(orig);
        }

        throw new UnsupportedOperationException("不支持从[" + orig.getClass() + "]转换到[" + toClass + "]");
    }

    private static <T> String getConvertKey(Converter<?, ?> converter) {
        Type[] genType = converter.getClass().getGenericInterfaces();
        Type[] params = ((ParameterizedType)genType[0]).getActualTypeArguments();
        return params[0] + "#" + params[1];
    }

    private static String getConvertKey(Object orig, Class<?> toClass) {
        return orig == null ? "" : (orig.getClass() + "#" + toClass);
    }

    public static void addConverter(Converter<?, ?> converter) {
        String key = getConvertKey(converter);
        if ( mapConverter.containsKey(key) ) {
            log.warn("转换器重复，[{}]被覆盖为[{}]", mapConverter.get(key).getClass(), converter.getClass());
        }
        mapConverter.put(getConvertKey(converter), converter);
    }

    static {
        List<Class<?>> list = CmnClass.getClasses(Converter.class.getPackage().getName());
        for ( Class<?> claz : list ) {
            if ( !claz.isInterface() ) {
                try {
                    Object obj = claz.newInstance();
                    if ( obj instanceof Converter ) {
                        log.trace("添加转换器[{}]", claz);
                        addConverter((Converter<?, ?>)obj);
                    }
                } catch (Exception e) {
                    log.warn("转换器[{}]初始化失败", claz);
                }
            }
        }
    }

//	public static void main(String[] args) {
//		log.debug(mapConverter.toString());
//		Object s = null;
//		Object ss = convert(s, int.class);
//		log.debug(ss + "");
//	}

}