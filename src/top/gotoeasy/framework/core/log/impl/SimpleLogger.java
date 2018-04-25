package top.gotoeasy.framework.core.log.impl;

import java.io.PrintStream;
import java.util.Date;
import java.util.regex.Matcher;

import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.util.CmnDate;

/**
 * 简易的控制台日志实现类
 * @since 2018/04
 * @author 青松
 */
public class SimpleLogger implements Log {

	static {
		System.err.println("没有配置日志实现，当前使用简单的控制台方式输出日志。");
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
		sb.append(format.replaceFirst("\\{\\}", Matcher.quoteReplacement(arg.toString())));

		printStream.println(sb.toString());
	}

	private void println(String level, PrintStream printStream, String format, Object arg1, Object arg2) {
		StringBuilder sb = new StringBuilder();
		sb.append(CmnDate.format(new Date(), CmnDate.HHmmssSSS)).append(" ");
		sb.append(level).append(" ");
		sb.append(name).append(" - ");
		sb.append(format.replaceFirst("\\{\\}", Matcher.quoteReplacement(arg1.toString())).replaceFirst("\\{\\}",
				Matcher.quoteReplacement(arg2.toString())));

		printStream.println(sb.toString());
	}

	private void println(String level, PrintStream printStream, String format, Object ... arguments) {
		StringBuilder sb = new StringBuilder();
		sb.append(CmnDate.format(new Date(), CmnDate.HHmmssSSS)).append(" ");
		sb.append(level).append(" ");
		sb.append(name).append(" - ");

		String msg = format;
		for ( int i = 0; i < arguments.length; i++ ) {
			msg = msg.replaceFirst("\\{\\}", Matcher.quoteReplacement(arguments[i].toString()));
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

		System.err.println(msg);
		t.printStackTrace();
	}

	@Override
	public void trace(String msg) {
		println("跟踪", System.out, msg);
	}

	@Override
	public void trace(String format, Object arg) {
		println("跟踪", System.out, format, arg);
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		println("跟踪", System.out, format, arg1, arg2);
	}

	@Override
	public void trace(String format, Object ... arguments) {
		println("跟踪", System.out, format, arguments);
	}

	@Override
	public void trace(String msg, Throwable t) {
		println("跟踪", msg, t);
	}

	@Override
	public void debug(String msg) {
		println("调试", System.out, msg);
	}

	@Override
	public void debug(String format, Object arg) {
		println("调试", System.out, format, arg);
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		println("调试", System.out, format, arg1, arg2);
	}

	@Override
	public void debug(String format, Object ... arguments) {
		println("调试", System.out, format, arguments);
	}

	@Override
	public void debug(String msg, Throwable t) {
		println("调试", msg, t);
	}

	@Override
	public void info(String msg) {
		println("信息", System.err, msg);
	}

	@Override
	public void info(String format, Object arg) {
		println("信息", System.err, format, arg);
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		println("信息", System.err, format, arg1, arg2);
	}

	@Override
	public void info(String format, Object ... arguments) {
		println("信息", System.err, format, arguments);
	}

	@Override
	public void info(String msg, Throwable t) {
		println("信息", msg, t);
	}

	@Override
	public void warn(String msg) {
		println("警告", System.err, msg);
	}

	@Override
	public void warn(String format, Object arg) {
		println("警告", System.err, format, arg);
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		println("警告", System.err, format, arg1, arg2);
	}

	@Override
	public void warn(String format, Object ... arguments) {
		println("警告", System.err, format, arguments);
	}

	@Override
	public void warn(String msg, Throwable t) {
		println("警告", msg, t);
	}

	@Override
	public void error(String msg) {
		println("错误", System.err, msg);
	}

	@Override
	public void error(String format, Object arg) {
		println("错误", System.err, format, arg);
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		println("错误", System.err, format, arg1, arg2);
	}

	@Override
	public void error(String format, Object ... arguments) {
		println("错误", System.err, format, arguments);
	}

	@Override
	public void error(String msg, Throwable t) {
		println("错误", msg, t);
	}

}
