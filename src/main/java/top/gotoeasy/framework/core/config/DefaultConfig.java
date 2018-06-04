package top.gotoeasy.framework.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 默认配置信息汇总读取类
 * <p>
 * 汇总SPI形式配置的配置信息，统一读取
 * </p>
 * 
 * @since 2018/05
 * @author 青松
 */
public class DefaultConfig {

    private static final DefaultConfig instance = new DefaultConfig();

    private Map<Object, Object>        map      = new HashMap<>();

    static {
        ServiceLoader<Config> configs = ServiceLoader.load(Config.class);
        for ( Config config : configs ) {
            instance.map.putAll(config.getConfigMap());
        }
    }

    private DefaultConfig() {
    }

    /**
     * 取得DefaultConfig单例对象
     * 
     * @return DefaultConfig单例对象
     */
    public static DefaultConfig getInstance() {
        return instance;
    }

    /**
     * 按名称取得配置值
     * 
     * @param name 名称
     * @return 配置值
     */
    public Object getObject(String name) {
        return map.get(name);
    }

    /**
     * 按名称取得配置值
     * 
     * @param name 名称
     * @param defaultVal 默认值
     * @return 配置值
     */
    public Object getObject(String name, Object defaultVal) {
        Object obj = map.get(name);
        return obj != null ? obj : defaultVal;
    }

    /**
     * 按名称取得配置值
     * 
     * @param name 名称
     * @return 配置值
     */
    public String getString(String name) {
        return (String)map.get(name);
    }

    /**
     * 按名称取得配置值
     * 
     * @param name 名称
     * @param defaultVal 默认值
     * @return 配置值
     */
    public String getString(String name, String defaultVal) {
        return (String)getObject(name, defaultVal);
    }

    /**
     * 按名称取得配置值
     * 
     * @param name 名称
     * @return 配置值
     */
    public Integer getInteger(String name) {
        return (Integer)map.get(name);
    }

    /**
     * 按名称取得配置值
     * 
     * @param name 名称
     * @param defaultVal 默认值
     * @return 配置值
     */
    public Integer getInteger(String name, Integer defaultVal) {
        return (Integer)getObject(name, defaultVal);
    }

    /**
     * 按名称取得配置值
     * <p>
     * 配置值[true、yes、y、t、1]都被视为true，且不区分大小写，其他值都视为false
     * </p>
     * 
     * @param name 名称
     * @return 配置值(配置值[true、yes、y、t、1]都被视为true，且不区分大小写，其他值都视为false)
     */
    public boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

    /**
     * 按名称取得配置值
     * <p>
     * 配置值[true、yes、y、t、1]都被视为true，且不区分大小写，其他值都视为false
     * </p>
     * 
     * @param name 名称
     * @param defaultVal 默认值
     * @return 配置值(配置值[true、yes、y、t、1]都被视为true，且不区分大小写，其他值都视为false)
     */
    public boolean getBoolean(String name, boolean defaultVal) {
        String str = getString(name, defaultVal ? "true" : "false").trim();
        return "true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str) || "y".equalsIgnoreCase(str) || "t".equalsIgnoreCase(str)
                || "1".equalsIgnoreCase(str);
    }

    /**
     * 设定一个配置值
     * 
     * @param name 名称
     * @param val 配置值
     */
    public void set(String name, Object val) {
        map.put(name, val);
    }

    /**
     * 设定一个配置值为指定类的包名
     * 
     * @param name 名称
     * @param clas 类
     */
    public void setPackage(String name, Class<?> clas) {
        map.put(name, clas.getPackage().getName());
    }

    /**
     * 删除一个配置项
     * 
     * @param name 名称
     */
    public void remove(String name) {
        map.remove(name);
    }
}
