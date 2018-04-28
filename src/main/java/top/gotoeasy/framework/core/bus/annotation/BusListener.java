package top.gotoeasy.framework.core.bus.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 总线监听器注解
 * @since 2018/03
 * @author 青松
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BusListener {

	/**
	 * 区分名
	 * <p>
	 * 用于区分同一个类里面的不同监听方法，不能重复
	 * </p>
	 * @return 区分名
	 */
	public String value() default "";
}
