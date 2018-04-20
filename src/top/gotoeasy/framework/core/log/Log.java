package top.gotoeasy.framework.core.log;

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
