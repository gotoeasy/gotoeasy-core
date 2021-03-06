package top.gotoeasy.framework.core.log;

import java.util.List;

import top.gotoeasy.framework.core.log.impl.SimpleLogger;
import top.gotoeasy.framework.core.util.CmnSpi;

/**
 * 日志工厂
 * 
 * @since 2018/04
 * @author 青松
 */
public final class LoggerFactory {

    // 类SPI方式取得最优先的有效日志提供者
    private static LoggerProvider loggerProvider = null;

    static {
        List<LoggerProvider> list = CmnSpi.loadInstances(LoggerProvider.class);
        for ( LoggerProvider provider : list ) {
            if ( provider.accept() ) {
                loggerProvider = provider;
            }
        }
    }

    private LoggerFactory() {
    }

    /**
     * 取得日志
     * 
     * @param clas 类
     * @return 日志
     */
    public static Log getLogger(Class<?> clas) {
        if ( loggerProvider != null ) {
            return loggerProvider.getLogger(clas);
        }

        // 没有日志提供者时，使用默认的简易控制台输出日志
        return new SimpleLogger(clas);
    }

}
