package top.gotoeasy.framework.core.log;

/**
 * 日志接口
 * <p>
 * 本日志接口通常用于框架本身使用，主要目的在于排除对其他日志的强制依赖性<br>
 * 在没有第三方日志环境的情况下，默认提供简易的控制台方式输出<br>
 * 在有第三方日志环境的情况下，能按优先顺序自动选择日志系统<br>
 * </p>
 * 
 * @since 2018/04
 * @author 青松
 */
public interface Log {

    public void trace(String msg);

    public void trace(String format, Object arg);

    public void trace(String format, Object arg1, Object arg2);

    public void trace(String format, Object ... arguments);

    public void trace(String msg, Throwable t);

    public void debug(String msg);

    public void debug(String format, Object arg);

    public void debug(String format, Object arg1, Object arg2);

    public void debug(String format, Object ... arguments);

    public void debug(String msg, Throwable t);

    public void info(String msg);

    public void info(String format, Object arg);

    public void info(String format, Object arg1, Object arg2);

    public void info(String format, Object ... arguments);

    public void info(String msg, Throwable t);

    public void warn(String msg);

    public void warn(String format, Object arg);

    public void warn(String format, Object ... arguments);

    public void warn(String format, Object arg1, Object arg2);

    public void warn(String msg, Throwable t);

    public void error(String msg);

    public void error(String format, Object arg);

    public void error(String format, Object arg1, Object arg2);

    public void error(String format, Object ... arguments);

    public void error(String msg, Throwable t);

}
