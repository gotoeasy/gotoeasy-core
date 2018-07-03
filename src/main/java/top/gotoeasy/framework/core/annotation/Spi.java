package top.gotoeasy.framework.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务提供者接口的实例声明注解
 * <p>
 * 配置文件与Java标准SPI配置文件的异同：<br>
 * 1）配置文件目录不同，本配置文件的目录固定为：META-INF/gotoeasy/<br>
 * 2）配置文件内容结构不同，本配置文件的内容结构固定为：java属性文件(UTF-8编码)<br>
 * 3）文件名规则相同，都是接口类的全限定名<br>
 * 4）都支持配置文件多点分布，如各jar包都有相同路径文件名的配置文件，各自配置不同内容
 * </p>
 * 
 * @since 2018/07
 * @author 青松
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Spi {

    /**
     * 键名
     * <p>
     * 默认为空白，即使用最大键名<br>
     * </p>
     * 
     * @return 键名
     */
    String value() default "";
}
