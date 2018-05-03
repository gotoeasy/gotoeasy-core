package top.gotoeasy.framework.core.config.impl;

import java.util.Map;

import top.gotoeasy.framework.core.config.Config;

/**
 * 系统Properties配置信息读取
 * 
 * @since 2018/05
 * @author 青松
 */
public class SystemPropertiesConfig implements Config {

    /**
     * 取得配置Map
     * 
     * @return 配置Map
     */
    @Override
    public Map<Object, Object> getConfigMap() {
        return System.getProperties();
    }

}
