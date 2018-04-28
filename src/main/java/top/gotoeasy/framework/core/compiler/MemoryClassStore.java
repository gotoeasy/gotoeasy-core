package top.gotoeasy.framework.core.compiler;

import java.util.HashMap;
import java.util.Map;

/**
 * 编译结果字节码存放类
 * <p>
 * 功能如同编译输出的磁盘路径，保存编译结果类名和字节码<br>
 * 这里单独做成静态类以方便读写操作
 * </p>
 * 
 * @since 2018/04
 * @author 青松
 */
public class MemoryClassStore {

    /** 类名：字节码 */
    private static Map<String, byte[]> map = new HashMap<>();

    private MemoryClassStore() {
    }

    /**
     * 存字节码
     * 
     * @param className 类名
     * @param classBytes 字节码
     */
    public static void put(String className, byte[] classBytes) {
        if ( map == null ) {
            map = new HashMap<>();
        }
        map.put(className, classBytes);
    }

    /**
     * 取字节码
     * 
     * @param className 类名
     * @return 字节码
     */
    public static byte[] get(String className) {
        if ( map == null ) {
            return null;
        }
        return map.get(className);
    }

    /**
     * 从内存中删除指定类字节码
     * 
     * @param className 类名
     * @return 指定类字节码(不存在时为null)
     */
    public static byte[] remove(String className) {
        if ( map == null ) {
            return null;
        }
        return map.remove(className);
    }

    /**
     * 清空
     */
    public static void clear() {
        map.clear();
        map = null;
    }

}
