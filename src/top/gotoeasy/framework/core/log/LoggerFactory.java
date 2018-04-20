package top.gotoeasy.framework.core.log;

import java.util.ServiceLoader;

import top.gotoeasy.framework.core.log.impl.SimpleLogger;

public final class LoggerFactory {

	private static LoggerProvider loggerProvider = null;

	static {
		ServiceLoader<LoggerProvider> providers = ServiceLoader.load(LoggerProvider.class);
		for ( LoggerProvider provider : providers ) {
			if ( provider.accept() ) {
				loggerProvider = provider;
				break;
			}
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
