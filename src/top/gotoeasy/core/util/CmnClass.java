package top.gotoeasy.core.util;

import java.beans.Transient;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类扫描工具类
 */
public class CmnClass {

    private static final Logger log = LoggerFactory.getLogger(CmnClass.class);

    /**
     * 从包package中获取带指定类注解的所有Class
     */
    public static List<Class<?>> getClasses(String pack, Class<? extends Annotation> typeAnnotationClass) {
        List<Class<?>> result = new ArrayList<>();
        List<Class<?>> list = getClasses(pack);
        for ( Class<?> claz : list ) {
            if ( claz.isAnnotationPresent(typeAnnotationClass) ) {
                result.add(claz);
            }
        }

        return result;
    }

    /**
     * 从包package中获取所有的Class
     */
    public static List<Class<?>> getClasses(String pack) {

        List<Class<?>> classes = new ArrayList<>();
        boolean recursive = true;	// 是否循环迭代  
        String packageName = pack;	// 获取包的名字 并进行替换  
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;	// 定义一个枚举的集合 并进行循环来处理这个目录下的things 

        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while ( dirs.hasMoreElements() ) {

                URL url = dirs.nextElement();	// 获取下一个元素  

                String protocol = url.getProtocol();	// 得到协议的名称  
                log.trace("[{}]类型扫描", protocol);

                if ( "file".equals(protocol) ) {
                    // 如果是以文件的形式保存在服务器上
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");	// 获取包的物理路径  
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);	// 以文件的方式扫描整个包下的文件 并添加到集合中  
                } else if ( "jar".equals(protocol) ) {
                    // 如果是jar包文件  
                    JarFile jar; // 定义一个JarFile  
                    try {
                        jar = ((JarURLConnection)url.openConnection()).getJarFile();	// 获取jar  
                        Enumeration<JarEntry> entries = jar.entries();	// 从此jar包 得到一个枚举类  
                        while ( entries.hasMoreElements() ) {

                            JarEntry entry = entries.nextElement();	// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
                            String name = entry.getName();
                            // 如果是以/开头的  
                            if ( name.charAt(0) == '/' ) {
                                name = name.substring(1);	// 获取后面的字符串  
                            }
                            // 如果前半部分和定义的包名相同  
                            if ( name.startsWith(packageDirName) ) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包  
                                if ( idx != -1 ) {
                                    packageName = name.substring(0, idx).replace('/', '.');	// 获取包名 把"/"替换成"."  
                                }
                                // 如果可以迭代下去 并且是一个包  
                                if ( (idx != -1) || recursive ) {
                                    // 如果是一个.class文件 而且不是目录  
                                    if ( name.endsWith(".class") && !entry.isDirectory() ) {
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);		// 去掉后面的".class" 获取真正的类名  
                                        try {
                                            classes.add(loadClass(packageName + '.' + className));	// 添加到classes
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

        File dir = new File(packagePath);	// 获取此包的目录 建立一个File  
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
     * 装载Class
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

    /**
     * 装载Class
     */
    public static Object createInstance(Class<?> claz, Class<?>[] argClasses, Object[] argObjs) {
        if ( argClasses == null ) {
            try {
                return claz.newInstance();
            } catch (Exception e) {
                log.error("按无参数构造函数创建对象失败[{}]", claz);
                throw new RuntimeException("按无参数构造函数创建对象失败[" + claz + "]", e);
            }
        }

        boolean bFindByClass = true;
        for ( Class<?> cls : argClasses ) {
            if ( cls == null ) {
                bFindByClass = false;
                break;
            }
        }

        try {
            if ( bFindByClass ) {
                return claz.getConstructor(argClasses).newInstance(argObjs);
            }

            Constructor<?>[] constructors = claz.getConstructors();
            for ( Constructor<?> constructor : constructors ) {
                if ( constructor.getParameterCount() != argObjs.length ) {
                    continue;
                }

                try {
                    return constructor.newInstance(argObjs);
                } catch (Exception e) {
                    log.warn("尝试初始化失败");
                }
            }

        } catch (Exception e) {
            log.error("按指定参数构造函数创建对象失败[{}({})]{}", claz, argClasses, argObjs);
            log.error("按指定参数构造函数创建对象失败", e);
            throw new RuntimeException("按指定参数构造函数创建对象失败[" + claz + "]", e);
        }

        log.error("类[{}]中指定的构造函数找不到{}", claz, argClasses);
        throw new RuntimeException("类中指定的构造函数找不到");
    }

    public static boolean hasMethodWithAnnotation(Class<?> claz, Class<? extends Annotation> annotationClass) {
        Method[] methods = claz.getMethods();
        for ( Method method : methods ) {
            if ( method.isAnnotationPresent(annotationClass) ) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getDeclaredPublicMethodNames(Class<?> claz) {
        List<String> list = new ArrayList<>();
        Method[] methods = claz.getDeclaredMethods();
        for ( Method method : methods ) {
            if ( Modifier.isPublic(method.getModifiers()) ) {
                list.add(method.getName());
            }
        }
        return list;
    }

    public static List<Method> getPublicMethods(Class<?> claz) {
        List<Method> list = new ArrayList<>();
        Method[] methods = claz.getMethods();
        for ( Method method : methods ) {
            if ( Modifier.isPublic(method.getModifiers()) ) {
                list.add(method);
            }
        }
        return list;
    }

    public static List<Method> getSetterMethods(Class<?> claz) {
        List<Method> result = new ArrayList<>();
        List<Method> publicMethods = getPublicMethods(claz);
        String name;
        for ( Method method : publicMethods ) {
            if ( method.getParameterCount() != 1 || method.getReturnType() != null || method.isAnnotationPresent(Transient.class) ) {
                continue;
            }

            name = method.getName();
            if ( name.startsWith("set") && name.length() > 3 && Character.isUpperCase(name.charAt(3)) ) {
                result.add(method);
            }
        }
        return result;
    }

    public static Map<String, Method> getSetterMethodMap(Class<?> claz) {
        Map<String, Method> result = new HashMap<>();
        List<Method> setters = getGetterMethods(claz);
        for ( Method method : setters ) {
            result.put(CmnString.uncapitalize(method.getName().substring(3)), method);
        }
        return result;
    }

    public static Map<String, Method> getGetterMethodMap(Class<?> claz) {
        Map<String, Method> result = new HashMap<>();
        List<Method> getters = getGetterMethods(claz);
        String name;
        for ( Method method : getters ) {
            name = method.getName().startsWith("is") ? method.getName().substring(2) : method.getName().substring(3);
            result.put(CmnString.uncapitalize(name), method);
        }
        return result;
    }

    public static List<Method> getGetterMethods(Class<?> claz) {
        List<Method> result = new ArrayList<>();
        List<Method> publicMethods = getPublicMethods(claz);
        String name;
        for ( Method method : publicMethods ) {
            if ( method.getParameterCount() != 0 || method.getReturnType() == null || method.isAnnotationPresent(Transient.class) ) {
                continue;
            }

            name = method.getName();
            if ( name.startsWith("is") && name.length() > 2 && Character.isUpperCase(name.charAt(2))
                    && method.getReturnType().equals(Boolean.class) ) {
                result.add(method); // public boolean isXxx()
            } else if ( name.startsWith("get") && name.length() > 3 && Character.isUpperCase(name.charAt(3)) ) {
                result.add(method); // public Object getXxx()
            }
        }
        return result;
    }

    public static List<String> getSetterPropertyNames(Class<?> claz) {
        List<String> result = new ArrayList<>();
        List<Method> setterMethods = getGetterMethods(claz);
        for ( Method method : setterMethods ) {
            result.add(CmnString.uncapitalize(method.getName().substring(3)));
        }
        return result;
    }

    public static List<String> getGetterPropertyNames(Class<?> claz) {
        List<String> result = new ArrayList<>();
        List<Method> getterMethods = getGetterMethods(claz);
        String name;
        for ( Method method : getterMethods ) {
            name = method.getName().startsWith("is") ? method.getName().substring(2) : method.getName().substring(3);
            result.add(CmnString.uncapitalize(name));
        }
        return result;
    }

    //////////////////
    public static List<String> getDeclaredPublicPropertyNames(Class<?> beanClass) {
        // TODO
        List<String> list = getDeclaredPublicFieldNames(beanClass);
        return list;
    }

    public static List<String> getDeclaredPublicFieldNames(Class<?> beanClass) {
        List<String> list = new ArrayList<>();
        Field fields[] = beanClass.getDeclaredFields(); // 获得所有public字段，包括父类
        for ( Field field : fields ) {
            if ( Modifier.isPublic(field.getModifiers()) ) {
                list.add(field.getName()); // 忽略@Transient及final字段
            }
        }

        return list;
    }

    public static Map<String, Field> getDeclaredPublicFields(Class<?> claz) {
        Map<String, Field> map = new HashMap<>();
        Field fields[] = claz.getDeclaredFields();
        for ( Field field : fields ) {
            if ( Modifier.isPublic(field.getModifiers()) ) {
                map.put(field.getName(), field); // 忽略@Transient及final字段
            }
        }
        return map;
    }

    /**
     * 检查注解的继承关系
     * @param subAnnoClass 子注解
     * @param superAnnoClass 父注解
     * @return true:同一注解、或子注解标注了父注解/false:没有注解标注关联
     */
    public static boolean isExtendAnnotation(Class<? extends Annotation> subAnnoClass, Class<? extends Annotation> superAnnoClass) {

        if ( subAnnoClass.equals(superAnnoClass) ) {
            return true;
        }

        Annotation[] annotations = subAnnoClass.getAnnotations();
        for ( Annotation anno : annotations ) {
            if ( anno instanceof Documented || anno instanceof Retention || anno instanceof Target ) {
                continue;
            }

            if ( superAnnoClass.isInstance(anno) ) {
                return true;
            }

            if ( isExtendAnnotation(anno.getClass(), superAnnoClass) ) {
                return true;
            }
        }

        return false;
    }
}
