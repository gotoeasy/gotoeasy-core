package top.gotoeasy.framework.core.log.impl;

import java.net.URL;
import java.net.URLClassLoader;

import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerProvider;
import top.gotoeasy.framework.core.util.CmnClass;
import top.gotoeasy.framework.core.util.CmnCore;
import top.gotoeasy.framework.core.util.CmnResource;

/**
 * 日志提供者 Slf4j
 * 
 * @since 2018/04
 * @author 青松
 */
public class Slf4jLoggerProvider implements LoggerProvider {

    // 从Slf4j$Logger.klass装载
    private static Class<?>                  loggerImplClass = null;

    private static final Slf4jLoggerProvider instance        = new Slf4jLoggerProvider();

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
        return (Log)CmnClass.createInstance(loggerImplClass, new Class<?>[] {Class.class}, new Class<?>[] {clas});
    }

    /**
     * 判断能否提供日志服务
     * 
     * @return true:能提供/false:不能提供
     */
    @Override
    public boolean accept() {
        ResourceClassLoader loader = null;
        try {
            if ( loggerImplClass != null ) {
                return true;
            }

            Class.forName("org.slf4j.LoggerFactory");

            // 装载提前编译好的Slf4j$Logger.klass，免于额外引入jar包
            loader = new ResourceClassLoader();
            loggerImplClass = loader.loadClass("top.gotoeasy.framework.core.log.impl.Slf4j$Logger");

            return true;
        } catch (Exception e) {
            System.err.println("当前环境不支持Slf4j");
            return false;
        } finally {
            CmnCore.closeQuietly(loader);
        }
    }

    private class ResourceClassLoader extends URLClassLoader {

        public ResourceClassLoader() {
            super(new URL[0], ResourceClassLoader.class.getClassLoader());
        }

        @Override
        protected Class<?> findClass(String className) throws ClassNotFoundException {
            // 从同目录读取Slf4j$Logger.klass并装载
            byte[] buf = CmnResource.getResourceBytes(className.substring(className.lastIndexOf('.') + 1) + ".klass", Slf4jLoggerProvider.class);
            return defineClass(className, buf, 0, buf.length);
        }

    }
}
