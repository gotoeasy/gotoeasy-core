package top.gotoeasy.framework.core.util;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import top.gotoeasy.framework.core.exception.CoreException;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;

/**
 * 服务提供者接口工具类
 * <p>
 * 本配置文件与SPI配置文件的异同：<br>
 * 1）配置文件目录不同，本配置文件的目录固定为：META-INF/gotoeasy/<br>
 * 2）配置文件内容结构不同，本配置文件的内容结构固定为：java属性文件<br>
 * 3）文件名规则相同，都是接口类的全限定名<br>
 * 4）都支持配置文件多点分布，如各jar包都有相同路径文件名的配置文件，各自配置不同内容
 * </p>
 * 
 * @since 2018/06
 * @author 青松
 */
public class CmnSpi {

    private static final Log                   log    = LoggerFactory.getLogger(CmnSpi.class);

    private static final String                PREFIX = "META-INF/gotoeasy/";

    private static final Map<Class<?>, Object> mapObj = new HashMap<>();

    private CmnSpi() {

    }

    /**
     * 读取配置文件，键名按升序排序，创建最小键名对应Class的实例
     * 
     * @param <T> 类型
     * @param interfaceClass 接口名
     * @return 类对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadInstance(Class<T> interfaceClass) {
        if ( mapObj.containsKey(interfaceClass) ) {
            return (T)mapObj.get(interfaceClass);
        }

        Class<?> clas = loadClass(interfaceClass);
        Object obj = clas == null ? null : newInstance(clas);
        mapObj.put(interfaceClass, obj);
        return (T)obj;
    }

    /**
     * 读取配置文件，创建指定键名对应Class的实例
     * 
     * @param <T> 类型
     * @param interfaceClass 接口名
     * @param key 键名
     * @return 类对象
     */
    public static <T> T loadInstance(Class<T> interfaceClass, String key) {
        Class<T> clas = loadClass(interfaceClass, key);
        return newInstance(clas);
    }

    /**
     * 读取配置文件，键名按升序排序，创建全部Class的实例
     * 
     * @param <T> 类型
     * @param interfaceClass 接口名
     * @return 类对象列表
     */
    public static <T> List<T> loadInstances(Class<T> interfaceClass) {
        List<Class<T>> listClass = loadClasses(interfaceClass);
        List<T> listObj = new ArrayList<>();
        for ( Class<T> clas : listClass ) {
            listObj.add(newInstance(clas));
        }
        return listObj;
    }

    /**
     * 读取配置文件，键名按升序排序，装载最小键名对应的Class
     * 
     * @param interfaceClass 接口名
     * @return 类对象
     */
    public static Class<?> loadClass(Class<?> interfaceClass) {
        Map<String, String> map = loadProperties(interfaceClass);
        Iterator<String> it = map.values().iterator();
        if ( it.hasNext() ) {
            return loadClassByName(it.next());
        }
        return null;
    }

    /**
     * 读取配置文件，键名按升序排序，装载全部Class
     * 
     * @param <T> 类型
     * @param interfaceClass 接口名
     * @return 类对象列表
     */
    public static <T> List<Class<T>> loadClasses(Class<T> interfaceClass) {
        Map<String, String> map = loadProperties(interfaceClass);
        List<Class<T>> list = new ArrayList<>();

        Iterator<String> it = map.values().iterator();
        while ( it.hasNext() ) {
            list.add(loadClassByName(it.next()));
        }
        return list;
    }

    /**
     * 读取配置文件，装载指定键名对应的Class
     * 
     * @param <T> 类型
     * @param interfaceClass 接口名
     * @param key 键名
     * @return 类对象
     */
    public static <T> Class<T> loadClass(Class<T> interfaceClass, String key) {
        Map<String, String> map = loadProperties(interfaceClass);
        return loadClassByName(map.get(key));
    }

    /**
     * 读取指定接口相关的全部SPI属性配置文件，合并后按键名按升序排序，封装为LinkedHashMap返回
     * 
     * @param interfaceClass 接口名
     * @return 配置信息Map
     */
    public static Map<String, String> loadProperties(Class<?> interfaceClass) {

        Properties prop = new Properties();
        String fileName = PREFIX + interfaceClass.getName();
        try {
            Enumeration<URL> em = Thread.currentThread().getContextClassLoader().getResources(fileName);
            while ( em.hasMoreElements() ) {
                URL url = em.nextElement();
                try ( InputStream in = url.openStream(); ) {
                    prop.load(in);
                }
            }
        } catch (Exception e) {
            if ( log != null ) {
                log.error("接口[{}]的属性配置文件读取失败", interfaceClass);
            }
            throw new CoreException(e);
        }

        // 键名排序，取最小键名对应的类名，装载返回该类
        List<String> list = new ArrayList<>();
        prop.keySet().forEach(key -> list.add((String)key));
        list.sort((s1, s2) -> s1.compareTo(s2));

        // 返回
        Map<String, String> map = new LinkedHashMap<>();
        String val;
        for ( String key : list ) {
            val = prop.getProperty(key);
            if ( CmnString.isNotBlank(val) ) {
                map.put(key, prop.getProperty(key));
            }
        }

        if ( log != null ) {
            log.debug("接口[{}]的属性配置内容:{}", interfaceClass, map);
        }
        return map;
    }

    private static <T> T newInstance(Class<T> clas) {
        try {
            return clas.newInstance();
        } catch (Exception e) {
            throw new CoreException("创建对象失败[" + clas + "]", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> loadClassByName(String fullClassName) {
        try {
            return (Class<T>)Thread.currentThread().getContextClassLoader().loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            throw new CoreException(e);
        }
    }

}
