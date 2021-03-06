package top.gotoeasy.framework.core.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.core.util.CmnClass;

/**
 * 数据类型转换工具类
 * 
 * @since 2018/01
 * @author 青松
 */
public class ConvertUtil {

    private static final Log                    log          = LoggerFactory.getLogger(ConvertUtil.class);

    private static Map<String, Converter<?, ?>> mapConverter = new HashMap<>();

    private static Map<Class<?>, Class<?>>      mapTypeClass = new HashMap<>();

    static {
        mapTypeClass.put(byte.class, Byte.class);
        mapTypeClass.put(int.class, Integer.class);
        mapTypeClass.put(long.class, Long.class);
        mapTypeClass.put(short.class, Short.class);
        mapTypeClass.put(float.class, Float.class);
        mapTypeClass.put(double.class, Double.class);
        mapTypeClass.put(boolean.class, Boolean.class);
        mapTypeClass.put(char.class, Character.class);
    }

    private ConvertUtil() {

    }

    /**
     * 判断是否能转换
     * 
     * @param orig 转换前对象
     * @param toClass 转换目标类
     * @return true:能转换/false:不能转换
     */
    public static boolean canConvert(Object orig, Class<?> toClass) {
        if ( orig == null || toClass.isInstance(orig) ) {
            return true;
        }
        return mapConverter.containsKey(getConvertKey(orig, toClass));
    }

    /**
     * 类型转换
     * 
     * @param orig 转换前对象
     * @param toClass 转换目标类
     * @param <T> 目标类
     * @return 转换后对象
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T convert(Object orig, Class<T> toClass) {

        if ( orig == null ) {
            return null;
        }

        if ( toClass.isInstance(orig) ) {
            return (T)orig;
        }

        Class<?> toClas = toClass;
        if ( mapTypeClass.containsKey(toClass) ) {
            toClas = mapTypeClass.get(toClass);
        }
        Converter converter = mapConverter.get(getConvertKey(orig, toClas));
        if ( converter != null ) {
            return (T)converter.convert(orig);
        }

        throw new UnsupportedOperationException("不支持从[" + orig.getClass() + "]转换到[" + toClass + "]");
    }

    private static String getConvertKey(Converter<?, ?> converter) {
        Type[] genType = converter.getClass().getGenericInterfaces();
        Type[] params = ((ParameterizedType)genType[0]).getActualTypeArguments();
        return params[0] + "#" + params[1];
    }

    private static String getConvertKey(Object orig, Class<?> toClass) {
        return orig == null ? "" : (orig.getClass() + "#" + toClass);
    }

    /**
     * 添加数据类型转换器
     * 
     * @param converter 数据类型转换器
     */
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

}
