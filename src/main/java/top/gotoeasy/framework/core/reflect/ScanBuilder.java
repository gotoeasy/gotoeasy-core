package top.gotoeasy.framework.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.gotoeasy.framework.core.util.CmnString;

/**
 * 扫描创建器
 * 
 * @since 2018/03
 * @author 青松
 */
public class ScanBuilder {

    private List<String>                      listPackage    = new ArrayList<>();
    private List<Class<? extends Annotation>> listTypeAnno   = new ArrayList<>();
    private List<Class<? extends Annotation>> listMethodAnno = new ArrayList<>();

    /**
     * 生成创建器
     * 
     * @return 创建器
     */
    public static ScanBuilder get() {
        return new ScanBuilder();
    }

    /**
     * 指定包
     * 
     * @param packages 包
     * @return 创建器
     */
    public ScanBuilder packages(String ... packages) {
        String[] packs;
        for ( String pack : packages ) {
            packs = CmnString.nullToBlank(pack).split(",");
            for ( String pkg : packs ) {
                if ( CmnString.isNotBlank(pkg) && !listPackage.contains(pkg.trim()) ) {
                    listPackage.add(pkg.trim());
                }
            }
        }
        return this;
    }

    /**
     * 指定包
     * 
     * @param packages 包
     * @return 创建器
     */
    public ScanBuilder packages(List<String> packages) {
        String[] packs;
        for ( String pack : packages ) {
            packs = pack.split(",");
            for ( String pkg : packs ) {
                if ( CmnString.isNotBlank(pkg) && !listPackage.contains(pkg.trim()) ) {
                    listPackage.add(pkg.trim());
                }
            }
        }
        return this;
    }

    /**
     * 指定类注解
     * 
     * @param typeAnnotations 类注解
     * @return 创建器
     */
    @SuppressWarnings("unchecked")
    public ScanBuilder typeAnnotations(Class<? extends Annotation> ... typeAnnotations) {
        for ( Class<? extends Annotation> anno : typeAnnotations ) {
            if ( !listTypeAnno.contains(anno) ) {
                listTypeAnno.add(anno);
            }
        }
        return this;
    }

    /**
     * 指定类注解
     * 
     * @param typeAnnotations 类注解
     * @return 创建器
     */
    public ScanBuilder typeAnnotations(List<Class<? extends Annotation>> typeAnnotations) {
        for ( Class<? extends Annotation> anno : typeAnnotations ) {
            if ( !listTypeAnno.contains(anno) ) {
                listTypeAnno.add(anno);
            }
        }
        return this;
    }

    /**
     * 指定方法注解
     * 
     * @param methodAnnotations 方法注解
     * @return 创建器
     */
    @SuppressWarnings("unchecked")
    public ScanBuilder methodAnnotations(Class<? extends Annotation> ... methodAnnotations) {
        for ( Class<? extends Annotation> anno : methodAnnotations ) {
            if ( !listMethodAnno.contains(anno) ) {
                listMethodAnno.add(anno);
            }
        }
        return this;
    }

    /**
     * 取得满足[包、类注解]条件的类列表(无重复类)
     * 
     * @return 类列表
     */
    public List<Class<?>> getClasses() {
        Map<Class<?>, Class<?>> map = new HashMap<>();

        for ( String pack : listPackage ) {
            List<Class<?>> list = ClassScaner.getClasses(pack);
            for ( Class<?> claz : list ) {
                if ( listTypeAnno.isEmpty() ) {
                    // 未指定类注解时
                    map.put(claz, claz);
                } else {
                    // 指定类注解时
                    for ( Class<? extends Annotation> anno : listTypeAnno ) {
                        if ( claz.isAnnotationPresent(anno) ) {
                            map.put(claz, claz);
                            break;
                        }
                    }
                }
            }
        }

        List<Class<?>> list = new ArrayList<>();
        list.addAll(map.values());
        return list;
    }

    /**
     * 取得满足[包、类注解、方法注解]条件的方法列表
     * 
     * @return 方法列表
     */
    public List<Method> getMethods() {
        List<Method> result = new ArrayList<>();
        List<Class<?>> classes = getClasses();

        List<Method> list;
        for ( Class<?> claz : classes ) {
            list = MethodScaner.getDeclaredPublicMethods(claz);
            for ( Method method : list ) {
                for ( Class<? extends Annotation> anno : listMethodAnno ) {
                    if ( method.isAnnotationPresent(anno) ) {
                        result.add(method);
                        break;
                    }
                }
            }
        }

        return result;
    }

}
