package top.gotoeasy.framework.core.reflect;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.core.util.CmnString;

/**
 * 类扫描器
 * @since 2018/03
 * @author 青松
 */
public class ClassScaner {

	private static final Log log = LoggerFactory.getLogger(ClassScaner.class);

	/**
	 * 从包package中获取带指定类注解的所有Class
	 * @param pack 包名
	 * @param typeAnnotation 类注解
	 * @return Class列表
	 */
	public static List<Class<?>> getClasses(String pack, Class<? extends Annotation> typeAnnotation) {
		List<Class<?>> result = new ArrayList<>();
		List<Class<?>> list = getClasses(pack);
		for ( Class<?> claz : list ) {
			if ( claz.isAnnotationPresent(typeAnnotation) ) {
				result.add(claz);
			}
		}

		return result;
	}

	/**
	 * 从包package中获取所有的Class列表
	 * @param pack 包名
	 * @return Class列表
	 */
	public static List<Class<?>> getClasses(String pack) {

		List<Class<?>> classes = new ArrayList<>();
		boolean recursive = true;   // 是否循环迭代  
		String packageName = pack;  // 获取包的名字 并进行替换  
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;  // 定义一个枚举的集合 并进行循环来处理这个目录下的things 

		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while ( dirs.hasMoreElements() ) {

				URL url = dirs.nextElement();   // 获取下一个元素  

				String protocol = url.getProtocol();    // 得到协议的名称  
				log.trace("[{}]类型扫描", protocol);

				if ( "file".equals(protocol) ) {
					// 如果是以文件的形式保存在服务器上
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");    // 获取包的物理路径  
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);    // 以文件的方式扫描整个包下的文件 并添加到集合中  
				} else if ( "jar".equals(protocol) ) {
					// 如果是jar包文件  
					JarFile jar; // 定义一个JarFile  
					try {
						jar = ((JarURLConnection)url.openConnection()).getJarFile();    // 获取jar  
						Enumeration<JarEntry> entries = jar.entries();  // 从此jar包 得到一个枚举类  
						while ( entries.hasMoreElements() ) {

							JarEntry entry = entries.nextElement(); // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
							String name = entry.getName();
							// 如果是以/开头的  
							if ( name.charAt(0) == '/' ) {
								name = name.substring(1);   // 获取后面的字符串  
							}
							// 如果前半部分和定义的包名相同  
							if ( name.startsWith(packageDirName) ) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包  
								if ( idx != -1 ) {
									packageName = name.substring(0, idx).replace('/', '.'); // 获取包名 把"/"替换成"."  
								}
								// 如果可以迭代下去 并且是一个包  
								if ( (idx != -1) || recursive ) {
									// 如果是一个.class文件 而且不是目录  
									if ( name.endsWith(".class") && !entry.isDirectory() ) {
										String className = name.substring(packageName.length() + 1, name.length() - 6);     // 去掉后面的".class" 获取真正的类名  
										try {
											classes.add(loadClass(packageName + '.' + className));  // 添加到classes
											log.info("找到类[{}.{}]", packageName, className);
										} catch (Exception e) {
											log.warn("指定类找不到，忽略[{}.{}]", packageName, className);
										}
									}
								}
							}
						}
					} catch (IOException e) {
						log.warn("读写异常，忽略", e);
					}
				} else {
					log.warn("尚未支持[{}]类型扫描", protocol);
				}
			}
		} catch (IOException e) {
			log.warn("读写异常，忽略", e);
		}

		log.trace("按包名[{}]扫描找到类共[{}]个", pack, classes.size());
		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 */
	private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {

		File dir = new File(packagePath);   // 获取此包的目录 建立一个File  
		// 如果不存在或者 也不是目录就直接返回  
		if ( !dir.exists() || !dir.isDirectory() ) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");  
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录  
		File[] dirfiles = dir.listFiles(new FileFilter() {

			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
			@Override
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});

		for ( File file : dirfiles ) {
			String pks = packageName;
			if ( CmnString.isNotBlank(pks) ) {
				pks = pks + ".";
			}

			if ( file.isDirectory() ) {
				// 如果是目录 则继续扫描  
				findAndAddClassesInPackageByFile(pks + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名  
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(loadClass(pks + className));
					log.trace("找到类[{}.{}]", packageName, className);
				} catch (Exception e) {
					log.warn("指定类找不到，忽略[{}{}]", pks, className);
				}
			}
		}
	}

	/**
	 * 装载指定Class
	 * @param fullClassName 类全名
	 * @return Class
	 */
	public static Class<?> loadClass(String fullClassName) {
		try {
			Class<?> rs = Thread.currentThread().getContextClassLoader().loadClass(fullClassName);
			log.trace("类装载成功[{}]", fullClassName);
			return rs;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
