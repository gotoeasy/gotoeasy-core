package top.gotoeasy.framework.core.log.impl;

import java.net.URL;

import top.gotoeasy.framework.core.compiler.MemoryJavaCompiler;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerProvider;
import top.gotoeasy.framework.core.util.CmnClass;
import top.gotoeasy.framework.core.util.CmnFile;

/**
 * 日志提供者 Log4j-api
 * <p>
 * 优先顺序为10，数值越小优先度越高
 * </p>
 * @since 2018/04
 * @author 青松
 */
public class Log4jApiLoggerProvider implements LoggerProvider {

	// 动态编译类 Slf4j$Logger
	private static Class<?>						loggerImplClass	= null;

	private static final Log4jApiLoggerProvider	instance		= new Log4jApiLoggerProvider();

	/**
	 * 取得实例
	 * @return 实例
	 */
	public static Log4jApiLoggerProvider getInstance() {
		return instance;
	}

	/**
	 * 取得日志
	 * @param name 名称
	 * @return 日志
	 */
	@Override
	public Log getLogger(String name) {
		return (Log)CmnClass.createInstance(loggerImplClass, new Class<?>[] {String.class}, new String[] {name});
	}

	/**
	 * 取得日志
	 * @param clas 类
	 * @return 日志
	 */
	@Override
	public Log getLogger(Class<?> clas) {
		return (Log)CmnClass.createInstance(loggerImplClass, new Class<?>[] {Class.class}, new Class<?>[] {clas});
	}

	/**
	 * 判断能否提供日志服务
	 * @return true:能提供/false:不能提供
	 */
	@Override
	public boolean accept() {
		try {
			if ( loggerImplClass != null ) {
				return true;
			}

			Class.forName("org.apache.logging.log4j.LogManager");

			// 读取日志包装类源码
			String file = Log4jApiLoggerProvider.class.getPackage().getName().replace(".", "/") + "/Log4jApi$Logger.txt";
			URL url = Thread.currentThread().getContextClassLoader().getResource(file);
			String className = Log4jApiLoggerProvider.class.getPackage().getName() + ".Log4jApi$Logger";
			String sourceCode = CmnFile.readFileText(url.getPath(), "utf-8");

			// 编译&装载类
			MemoryJavaCompiler compiler = new MemoryJavaCompiler();
			loggerImplClass = compiler.compileAndLoadClass(className, sourceCode);

			return true;
		} catch (ClassNotFoundException e) {
			System.err.println("没有找到 \"org.apache.logging.log4j.LogManager\"，当前环境不支持 Log4j-api");
			return false;
		}
	}

	/**
	 * 取得优先顺序，数值越小优先度越高
	 * @return 优先顺序
	 */
	@Override
	public int getOrder() {
		return 20;
	}

}
