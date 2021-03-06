package top.gotoeasy.framework.core.bus;

import top.gotoeasy.framework.core.bus.impl.DefaultBus;
import top.gotoeasy.framework.core.util.CmnClass;

/**
 * 全局总线
 * 
 * @since 2018/04
 * @author 青松
 */
public class CmnBus {

    private static final Bus bus = new DefaultBus();

    /**
     * 以类作为标识，并创建类的实例注册为唯一监听器
     * 
     * @param claz 标识类
     */
    public static void one(Class<?> claz) {
        bus.one(claz.getCanonicalName(), CmnClass.createInstance(claz, null, null));
    }

    /**
     * 触发指定标识的监听处理
     * 
     * @param claz 标识类
     * @param args 触发参数
     * @return 结果
     */
    public static Object trigger(Class<?> claz, Object ... args) {
        return bus.trigger(claz.getCanonicalName(), args);
    }

    /**
     * 注册指定标识的唯一监听器
     * 
     * @param key 标识
     * @param listener 监听器
     */
    public static void one(String key, Object listener) {
        bus.one(key, listener);
    }

    /**
     * 以类作为标识，并创建类的实例注册为监听器
     * 
     * @param claz 标识类
     */
    public static void on(Class<?> claz) {
        bus.on(claz.getCanonicalName(), CmnClass.createInstance(claz, null, null));
    }

    /**
     * 注册指定标识的监听器，同一标识可以有多个监听器
     * 
     * @param key 标识
     * @param listener 监听器
     */
    public static void on(String key, Object listener) {
        bus.on(key, listener);
    }

    /**
     * 取消指定标识的指定监听器
     * 
     * @param key 标识
     * @param listener 监听器
     */
    public static void off(String key, Object listener) {
        bus.off(key, listener);
    }

    /**
     * 取消指定标识的全部监听器
     * 
     * @param key 标识
     */
    public static void off(String key) {
        bus.off(key);
    }

    /**
     * 触发指定标识的监听处理
     * 
     * @param key 标识
     * @param args 触发参数
     * @return 处理结果
     */
    public static Object trigger(String key, Object ... args) {
        return bus.trigger(key, args);
    }

    /**
     * 重置总线
     */
    public static void reset() {
        bus.reset();
    }
}
