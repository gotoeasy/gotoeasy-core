package top.gotoeasy.framework.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import top.gotoeasy.framework.core.exception.CoreException;

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

    private static final String PREFIX = "META-INF/gotoeasy/";

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
    public static <T> T loadSpiInstance(Class<T> interfaceClass) {
        Class<?> clas = loadSpiClass(interfaceClass);
        return clas == null ? null : (T)CmnClass.createInstance(clas, null, null);
    }

    /**
     * 读取配置文件，创建指定键名对应Class的实例
     * 
     * @param <T> 类型
     * @param interfaceClass 接口名
     * @param key 键名
     * @return 类对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadSpiInstance(Class<T> interfaceClass, String key) {
        Class<?> clas = loadSpiClass(interfaceClass, key);
        return clas == null ? null : (T)CmnClass.createInstance(clas, null, null);
    }

    /**
     * 读取配置文件，键名按升序排序，装载最小键名对应的Class
     * 
     * @param interfaceClass 接口名
     * @return 类对象
     */
    public static Class<?> loadSpiClass(Class<?> interfaceClass) {

        Properties prop = new Properties();
        String fileName = PREFIX + interfaceClass.getName();
        try {
            Enumeration<URL> em = Thread.currentThread().getContextClassLoader().getResources(fileName);
            while ( em.hasMoreElements() ) {
                URL url = em.nextElement();
                try ( InputStream in = new FileInputStream(new File(url.getPath())); ) {
                    prop.load(in);
                }
            }
        } catch (Exception e) {
            throw new CoreException(e);
        }

        if ( prop.isEmpty() ) {
            return null;
        }

        // 键名排序，取最小键名对应的类名，装载返回该类
        List<String> list = new ArrayList<>();
        prop.keySet().forEach(key -> list.add((String)key));
        list.sort((s1, s2) -> s1.compareTo(s2));
        return CmnClass.loadClass(prop.getProperty(list.get(0)));
    }

    /**
     * 读取配置文件，装载指定键名对应的Class
     * 
     * @param interfaceClass 接口名
     * @param key 键名
     * @return 类对象
     */
    public static Class<?> loadSpiClass(Class<?> interfaceClass, String key) {
        String fileName = PREFIX + interfaceClass.getName();
        try {
            Enumeration<URL> em = Thread.currentThread().getContextClassLoader().getResources(fileName);
            while ( em.hasMoreElements() ) {
                URL url = em.nextElement();

                String fullClassName;
                try ( InputStream in = new FileInputStream(new File(url.getPath())); ) {
                    Properties prop = new Properties();
                    prop.load(in);
                    fullClassName = prop.getProperty(key);
                }
                return CmnClass.loadClass(fullClassName);
            }
        } catch (Exception e) {
            throw new CoreException(e);
        }
        return null;
    }

}
