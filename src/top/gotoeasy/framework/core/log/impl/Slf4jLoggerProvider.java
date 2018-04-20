package top.gotoeasy.framework.core.log.impl;

import java.net.URL;

import top.gotoeasy.framework.core.compiler.MemoryClassLoader;
import top.gotoeasy.framework.core.compiler.MemoryJavaCompiler;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerProvider;
import top.gotoeasy.framework.core.util.CmnClass;
import top.gotoeasy.framework.core.util.CmnCore;
import top.gotoeasy.framework.core.util.CmnFile;

public class Slf4jLoggerProvider implements LoggerProvider {

	// 动态编译类 Slf4j$Logger
	private static Class<?>						loggerImplClass	= null;

	private static final Slf4jLoggerProvider	instance		= new Slf4jLoggerProvider();

	public static Slf4jLoggerProvider getInstance() {
		return instance;
	}

	@Override
	public Log getLogger(String name) {
		return (Log)CmnClass.createInstance(loggerImplClass, new Class<?>[] {String.class}, new String[] {name});
	}

	@Override
	public Log getLogger(Class<?> clas) {
		return (Log)CmnClass.createInstance(loggerImplClass, new Class<?>[] {Class.class}, new Class<?>[] {clas});
	}

	@Override
	public boolean accept() {
		try {
			Class.forName("org.slf4j.LoggerFactory");

			String file = Slf4jLoggerProvider.class.getPackage().getName().replace(".", "/") + "/Slf4jLogger.properties";
			URL url = Thread.currentThread().getContextClassLoader().getResource(file);
			String className = Slf4jLoggerProvider.class.getPackage().getName() + ".Slf4j$Logger";
			String src = CmnFile.readFileText(url.getPath(), "utf-8");

			MemoryJavaCompiler compiler = new MemoryJavaCompiler();
			compiler.compile(className, src);

			MemoryClassLoader loader = new MemoryClassLoader();
			loggerImplClass = loader.loadClass(className);
			CmnCore.closeQuietly(loader);

			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

}
