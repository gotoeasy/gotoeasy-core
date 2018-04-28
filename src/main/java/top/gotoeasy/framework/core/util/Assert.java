package top.gotoeasy.framework.core.util;

/**
 * 断言检查类
 * 
 * @since 2018/01
 * @author 青松
 */
public class Assert {

    private Assert() {

    }

    /**
     * 非空检查
     * 
     * @param obj 检查对象
     * @param msg 检查错误时的异常消息
     */
    public static void notNull(Object obj, String msg) {
        if ( obj == null ) {
            throw new IllegalArgumentException(msg);
        }
    }

}
