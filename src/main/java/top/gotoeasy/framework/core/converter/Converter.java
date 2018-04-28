package top.gotoeasy.framework.core.converter;

/**
 * 数据类型转换接口
 * 
 * @since 2018/01
 * @author 青松
 */
public interface Converter<F, T> {

    /**
     * 类型转换
     * 
     * @param orig 转换前对象
     * @return 转换后对象
     */
    public T convert(F orig);
}
