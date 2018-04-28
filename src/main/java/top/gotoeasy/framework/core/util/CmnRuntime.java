package top.gotoeasy.framework.core.util;

import java.lang.management.ManagementFactory;

/**
 * 运行期工具类
 * 
 * @since 2018/01
 * @author 青松
 */
public class CmnRuntime {

    private CmnRuntime() {

    }

    /**
     * 是否调试模式
     * 
     * @return true:是/false：否
     */
    public static boolean isDebug() {
        String inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
        return inputArguments.contains("-Xdebug") || inputArguments.contains("-agentlib:jdwp=");
    }
}
