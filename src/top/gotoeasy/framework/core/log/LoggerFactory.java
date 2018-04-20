package top.gotoeasy.framework.core.log;

import top.gotoeasy.framework.core.log.impl.SimpleLogger;
import top.gotoeasy.framework.core.log.impl.Slf4jLoggerProvider;

public final class LoggerFactory {

	private static LoggerProvider loggerProvider = null;

	static {
		LoggerProvider provider = Slf4jLoggerProvider.getInstance();
		if ( provider.accept() ) {
			loggerProvider = provider;
		}
	}

	private LoggerFactory() {
	}

	public static Log getLogger(String name) {
		if ( loggerProvider != null ) {
			return loggerProvider.getLogger(name);
		}
		return new SimpleLogger(name);
	}

	public static Log getLogger(Class<?> clas) {
		if ( loggerProvider != null ) {
			return loggerProvider.getLogger(clas);
		}
		return new SimpleLogger(clas);
	}
}
