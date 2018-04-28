package top.gotoeasy.framework.core.compiler;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 装载器类
 * <p>
 * 针对已编译好的字节码，由于是存放在内存中，需要自定义这些类的查找和装载操作
 * </p>
 * 
 * @since 2018/04
 * @author 青松
 */
public class MemoryClassLoader extends URLClassLoader {

    /**
     * 构造方法
     */
    public MemoryClassLoader() {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
    }

    /**
     * 按类名查找并装载类
     * 
     * @param className 类名
     * @return 类
     */
    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        byte[] buf = MemoryClassStore.remove(className); // 类装载后会被缓存，可以从内存中移除
        if ( buf != null ) {
            return defineClass(className, buf, 0, buf.length);
        }

        return super.findClass(className);
    }

}
