package top.gotoeasy.framework.core.log;

import java.util.ServiceLoader;

import top.gotoeasy.framework.core.log.impl.SimpleLogger;

/**
 * 日志工厂
 * @since 2018/04
 * @author 青松
 */
public final class LoggerFactory {

	private static LoggerProvider loggerProvider = null;

	static {
		// SPI方式取得最优先的有效日志提供者
		ServiceLoader<LoggerProvider> providers = ServiceLoader.load(LoggerProvider.class);
		int order = 1000;
		for ( LoggerProvider provider : providers ) {
			if ( provider.accept() ) {
				if ( provider.getOrder() < order ) {
					loggerProvider = provider;
					order = provider.getOrder();
				}
			}
		}
	}

	private LoggerFactory() {
	}

	/**
	 * 取得日志
	 * @param name 名称
	 * @return 日志
	 */
	public static Log getLogger(String name) {
		if ( loggerProvider != null ) {
			return loggerProvider.getLogger(name);
		}

		// 没有日志提供者时，使用默认的简易控制台输出日志
		return new SimpleLogger(name);
	}

	/**
	 * 取得日志
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
