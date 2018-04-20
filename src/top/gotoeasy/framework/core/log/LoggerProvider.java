package top.gotoeasy.framework.core.log;

public interface LoggerProvider {

	public Log getLogger(String name);

	public Log getLogger(Class<?> clas);

	public boolean accept();
}
