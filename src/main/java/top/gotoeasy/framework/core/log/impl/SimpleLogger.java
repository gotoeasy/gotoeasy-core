package top.gotoeasy.framework.core.log.impl;

import java.io.PrintStream;
import java.util.Date;
import java.util.regex.Matcher;

import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.util.CmnDate;

/**
 * 简易的控制台日志实现类
 * 
 * @since 2018/04
 * @author 青松
 */
public class SimpleLogger implements Log {

    private static PrintStream  out   = System.out;
    private static PrintStream  err   = System.err;

    private static final String REGEX = "\\{\\}";

    private boolean             TRACE = DefaultConfig.getInstance().getBoolean("log.level.trace", false);
    private boolean             DEBUG = DefaultConfig.getInstance().getBoolean("log.level.debug", true);
    private boolean             INFO  = DefaultConfig.getInstance().getBoolean("log.level.info", true);
    private boolean             WARN  = DefaultConfig.getInstance().getBoolean("log.level.warn", true);
    private boolean             ERROR = DefaultConfig.getInstance().getBoolean("log.level.error", true);

    static {
        err.println("没有配置日志实现，当前使用简单的控制台方式输出日志。");
    }

    private String name;

    public SimpleLogger(Class<?> clas) {
        this.name = clas.getName();
    }

    private void println(String level, PrintStream printStream, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(CmnDate.format(new Date(), CmnDate.HHmmssSSS)).append(" ");
        sb.append(level).append(" ");
        sb.append(name).append(" - ");
        sb.append(msg);

        printStream.println(sb.toString());
    }

    private void println(String level, PrintStream printStream, String format, Object arg) {
        StringBuilder sb = new StringBuilder();
        sb.append(CmnDate.format(new Date(), CmnDate.HHmmssSSS)).append(" ");
        sb.append(level).append(" ");
        sb.append(name).append(" - ");
        sb.append(format.replaceFirst(REGEX, Matcher.quoteReplacement(String.valueOf(arg))));

        printStream.println(sb.toString());
    }

    private void println(String level, PrintStream printStream, String format, Object arg1, Object arg2) {
        StringBuilder sb = new StringBuilder();
        sb.append(CmnDate.format(new Date(), CmnDate.HHmmssSSS)).append(" ");
        sb.append(level).append(" ");
        sb.append(name).append(" - ");
        sb.append(format.replaceFirst(REGEX, Matcher.quoteReplacement(String.valueOf(arg1))).replaceFirst(REGEX,
                Matcher.quoteReplacement(String.valueOf(arg2))));

        printStream.println(sb.toString());
    }

    private void println(String level, PrintStream printStream, String format, Object ... arguments) {
        StringBuilder sb = new StringBuilder();
        sb.append(CmnDate.format(new Date(), CmnDate.HHmmssSSS)).append(" ");
        sb.append(level).append(" ");
        sb.append(name).append(" - ");

        String msg = format;
        for ( int i = 0; i < arguments.length; i++ ) {
            msg = msg.replaceFirst(REGEX, Matcher.quoteReplacement(String.valueOf(arguments[i])));
        }

        sb.append(msg);

        printStream.println(sb.toString());
    }

    private void println(String level, String msg, Throwable t) {
        StringBuilder sb = new StringBuilder();
        sb.append(CmnDate.format(new Date(), CmnDate.HHmmssSSS)).append(" ");
        sb.append(level).append(" ");
        sb.append(name).append(" - ");
        sb.append(msg);

        err.println(msg);
        t.printStackTrace();
    }

    @Override
    public void trace(String msg) {
        if ( TRACE ) {
            println("跟踪", out, msg);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        if ( TRACE ) {
            println("跟踪", out, format, arg);
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if ( TRACE ) {
            println("跟踪", out, format, arg1, arg2);
        }
    }

    @Override
    public void trace(String format, Object ... arguments) {
        if ( TRACE ) {
            println("跟踪", out, format, arguments);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {
        if ( TRACE ) {
            println("跟踪", msg, t);
        }
    }

    @Override
    public void debug(String msg) {
        if ( DEBUG ) {
            println("调试", out, msg);
        }
    }

    @Override
    public void debug(String format, Object arg) {
        if ( DEBUG ) {
            println("调试", out, format, arg);
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if ( DEBUG ) {
            println("调试", out, format, arg1, arg2);
        }
    }

    @Override
    public void debug(String format, Object ... arguments) {
        if ( DEBUG ) {
            println("调试", out, format, arguments);
        }
    }

    @Override
    public void debug(String msg, Throwable t) {
        if ( DEBUG ) {
            println("调试", msg, t);
        }
    }

    @Override
    public void info(String msg) {
        if ( INFO ) {
            println("信息", err, msg);
        }
    }

    @Override
    public void info(String format, Object arg) {
        if ( INFO ) {
            println("信息", err, format, arg);
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        if ( INFO ) {
            println("信息", err, format, arg1, arg2);
        }
    }

    @Override
    public void info(String format, Object ... arguments) {
        if ( INFO ) {
            println("信息", err, format, arguments);
        }
    }

    @Override
    public void info(String msg, Throwable t) {
        if ( INFO ) {
            println("信息", msg, t);
        }
    }

    @Override
    public void warn(String msg) {
        if ( WARN ) {
            println("警告", err, msg);
        }
    }

    @Override
    public void warn(String format, Object arg) {
        if ( WARN ) {
            println("警告", err, format, arg);
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        if ( WARN ) {
            println("警告", err, format, arg1, arg2);
        }
    }

    @Override
    public void warn(String format, Object ... arguments) {
        if ( WARN ) {
            println("警告", err, format, arguments);
        }
    }

    @Override
    public void warn(String msg, Throwable t) {
        if ( WARN ) {
            println("警告", msg, t);
        }
    }

    @Override
    public void error(String msg) {
        if ( ERROR ) {
            println("错误", err, msg);
        }
    }

    @Override
    public void error(String format, Object arg) {
        if ( ERROR ) {
            println("错误", err, format, arg);
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if ( ERROR ) {
            println("错误", err, format, arg1, arg2);
        }
    }

    @Override
    public void error(String format, Object ... arguments) {
        if ( ERROR ) {
            println("错误", err, format, arguments);
        }
    }

    @Override
    public void error(String msg, Throwable t) {
        if ( ERROR ) {
            println("错误", msg, t);
        }
    }

}
