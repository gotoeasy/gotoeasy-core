package top.gotoeasy.framework.core.log.impl;

import java.net.URL;

import top.gotoeasy.framework.core.compiler.MemoryJavaCompiler;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerProvider;
import top.gotoeasy.framework.core.util.CmnClass;
import top.gotoeasy.framework.core.util.CmnFile;

/**
 * 编译源代码输入类
 * <p>
 * 修改编译时源代码的输入位置，由文件输入改成直接字符串输入
 * </p>
 * @since 2018/04
 * @author 青松
 */
public class Slf4jLoggerProvider implements LoggerProvider {

	// 动态编译类 Slf4j$Logger
	private static Class<?>						loggerImplClass	= null;

	private static final Slf4jLoggerProvider	instance		= new Slf4jLoggerProvider();

	/**
	 * 取得实例
	 * @return 实例
	 */
	public static Slf4jLoggerProvider getInstance() {
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

			Class.forName("org.slf4j.LoggerFactory");

			// 读取日志包装类源码
			String file = Slf4jLoggerProvider.class.getPackage().getName().replace(".", "/") + "/Slf4j$Logger.txt";
			URL url = Thread.currentThread().getContextClassLoader().getResource(file);
			String className = Slf4jLoggerProvider.class.getPackage().getName() + ".Slf4j$Logger";
			String sourceCode = CmnFile.readFileText(url.getPath(), "utf-8");

			// 编译&装载类
			MemoryJavaCompiler compiler = new MemoryJavaCompiler();
			loggerImplClass = compiler.compileAndLoadClass(className, sourceCode);

			return true;
		} catch (ClassNotFoundException e) {
			System.err.println("没有找到 \"org.slf4j.LoggerFactory\"，当前环境不支持Slf4j");
			return false;
		}
	}

}
