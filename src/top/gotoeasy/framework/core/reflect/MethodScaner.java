package top.gotoeasy.framework.core.reflect;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import top.gotoeasy.framework.core.util.CmnString;

/**
 * 方法扫描类
 * @since 2018/03
 * @author 青松
 */
public class MethodScaner {

    /**
     * 从指定包中获取所有的Public方法列表
     * @param packages 包名
     * @return Class列表
     */
    public static List<Method> getDeclaredPublicMethods(String ... packages) {
        Map<Method, Method> map = new LinkedHashMap<>();
        List<Class<?>> classes;
        List<Method> methods;
        for ( String pack : packages ) {
            classes = ClassScaner.getClasses(pack);
            for ( Class<?> claz : classes ) {
                methods = getDeclaredPublicMethods(claz);
                for ( Method method : methods ) {
                    map.put(method, method);
                }
            }
        }

        List<Method> list = new ArrayList<>();
        list.addAll(map.values());
        return list;
    }

    /**
     * 取得指定类的Public方法列表（含父类）
     * @param claz 类
     * @return Public方法列表
     */
    public static List<Method> getPublicMethods(Class<?> claz) {
        List<Method> list = new ArrayList<>();
        Method[] methods = claz.getMethods(); // 含父类的全部方法
        for ( Method method : methods ) {
            if ( Modifier.isPublic(method.getModifiers()) ) {
                list.add(method);
            }
        }
        return list;
    }

    /**
     * 取得指定类自己声明的Public方法列表（仅本类）
     * @param claz 类
     * @return Public方法列表
     */
    public static List<Method> getDeclaredPublicMethods(Class<?> claz) {
        List<Method> list = new ArrayList<>();
        Method[] methods = claz.getDeclaredMethods(); // 本类声明方法
        for ( Method method : methods ) {
            if ( Modifier.isPublic(method.getModifiers()) ) {
                list.add(method);
            }
        }
        return list;
    }

    /**
     * 取得指定类自己声明的Public方法列表，且带指定方法注解类（仅本类）
     * @param claz 类
     * @param methodAnnotations 方法注解类
     * @return Public方法列表
     */
    @SuppressWarnings("unchecked")
    public static List<Method> getDeclaredPublicMethods(Class<?> claz, Class<? extends Annotation> ... methodAnnotations) {
        List<Method> list = new ArrayList<>();
        Method[] methods = claz.getDeclaredMethods(); // 本类声明方法
        for ( Method method : methods ) {
            if ( !Modifier.isPublic(method.getModifiers()) ) {
                continue;
            }

            for ( Class<? extends Annotation> methodAnnotation : methodAnnotations ) {
                if ( method.isAnnotationPresent(methodAnnotation) ) {
                    list.add(method);
                    break;
                }
            }

        }
        return list;
    }

    /**
     * 取得指定类的Getter方法列表（含父类）
     * @param claz 类
     * @return Getter方法列表
     */
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

    /**
     * 取得指定类的Getter方法Map（含父类）
     * @param claz 类
     * @return Getter方法Map
     */
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

    /**
     * 取得指定类的Setter方法列表（含父类）
     * @param claz 类
     * @return Setter方法列表
     */
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

    /**
     * 取得指定类的Setter方法Map（含父类）
     * @param claz 类
     * @return Setter方法Map
     */
    public static Map<String, Method> getSetterMethodMap(Class<?> claz) {
        Map<String, Method> result = new HashMap<>();
        List<Method> setters = getGetterMethods(claz);
        for ( Method method : setters ) {
            result.put(CmnString.uncapitalize(method.getName().substring(3)), method);
        }
        return result;
    }

}
