package top.gotoeasy.framework.core.log.impl;

import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerProvider;

/**
 * 日志提供者 Slf4j
 * <p>
 * 优先顺序为10，数值越小优先度越高
 * </p>
 * 
 * @since 2018/04
 * @author 青松
 */
public class Slf4jLoggerProvider implements LoggerProvider {

    private static final Slf4jLoggerProvider instance = new Slf4jLoggerProvider();

    /**
     * 取得实例
     * 
     * @return 实例
     */
    public static Slf4jLoggerProvider getInstance() {
        return instance;
    }

    /**
     * 取得日志
     * 
     * @param clas 类
     * @return 日志
     */
    @Override
    public Log getLogger(Class<?> clas) {
        return new Slf4jLogger(clas);
    }

    /**
     * 判断能否提供日志服务
     * 
     * @return true:能提供/false:不能提供
     */
    @Override
    public boolean accept() {
        try {

            Class.forName("org.slf4j.LoggerFactory");

            return true;
        } catch (Exception e) {
            System.err.println("当前环境不支持Slf4j");
            return false;
        }
    }

    /**
     * 取得优先顺序，数值越小优先度越高
     * 
     * @return 优先顺序
     */
    @Override
    public int getOrder() {
        return 10;
    }

}
