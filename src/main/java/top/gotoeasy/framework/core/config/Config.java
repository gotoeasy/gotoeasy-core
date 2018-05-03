package top.gotoeasy.framework.core.config;

import java.util.Map;

/**
 * 配置信息读取接口
 * <p>
 * 以SPI形式初始化汇总配置信息
 * </p>
 * 
 * @since 2018/05
 * @author 青松
 */
public interface Config {

    /**
     * 取得配置Map
     * 
     * @return 配置Map
     */
    public Map<Object, Object> getConfigMap();
}
