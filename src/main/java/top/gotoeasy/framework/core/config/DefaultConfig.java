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

}
