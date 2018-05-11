package top.gotoeasy.framework.core.util;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import top.gotoeasy.framework.core.converter.ConvertUtil;
import top.gotoeasy.framework.core.exception.CoreException;

/**
 * Bean对象工具类
 * 
 * @since 2018/01
 * @author 青松
 */
public class CmnBean {

    private static Map<Class<?>, Map<String, Field>>  mapClassFields  = null;
    private static Map<Class<?>, Map<String, Method>> mapClassSetters = null;

    private CmnBean() {

    }

    /**
     * 取得指定类的public字段Map对象
     * 
     * @param beanClass Bean类
     * @return public字段Map对象
     */
    public static synchronized Map<String, Field> getFieldMap(Class<?> beanClass) {
        if ( mapClassFields == null ) {
            mapClassFields = new HashMap<>();
        }

        if ( mapClassFields.get(beanClass) != null ) {
            return mapClassFields.get(beanClass);
        }

        Map<String, Field> mapFiled = new HashMap<>();
        Field fields[] = beanClass.getFields(); // 获得所有public字段，包括父类
        for ( Field field : fields ) {
            if ( field.getAnnotation(Transient.class) == null && !Modifier.isFinal(field.getModifiers()) ) { // 忽略@Transient及final字段
                mapFiled.put(field.getName(), field);
            }
        }

        mapClassFields.put(beanClass, mapFiled);

        return mapFiled;
    }

    /**
     * 取得指定类的Setter方法的Map对象
     * 
     * @param beanClass Bean类
     * @return Setter方法Map对象
     */
    public static synchronized Map<String, Method> getSetterMap(Class<?> beanClass) {
        if ( mapClassSetters == null ) {
            mapClassSetters = new HashMap<>();
        }

        if ( mapClassSetters.get(beanClass) != null ) {
            return mapClassSetters.get(beanClass);
        }

        Map<String, Method> mapSetter = new HashMap<>();
        Method methods[] = beanClass.getMethods(); // 获得所有public方法，包括父类
        for ( Method method : methods ) {
            // 方法名set/is开头，无返回值，一个参数
            if ( !void.class.equals(method.getReturnType()) ) {
                continue;
            }

            Class<?>[] argCls = method.getParameterTypes();
            if ( argCls.length != 1 ) {
                continue;
            }

            String name = method.getName();
            if ( !name.startsWith("set") || name.length() < 4 ) {
                continue;
            }
            name = CmnString.uncapitalize(name.substring(3));

            mapSetter.put(name, method);
        }

        mapClassSetters.put(beanClass, mapSetter);

        return mapSetter;
    }

    /**
     * 复制public字段 （空值也复制）
     * 
     * @param orig 来源对象
     * @param target 目标对象
     */
    public static void copyFields(Object orig, Object target) {
        copyFields(orig, target, true);
    }

    /**
     * 复制public字段
     * 
     * @param orig 来源对象
     * @param target 目标对象
     * @param copyNullValue 是否复制Null值
     */
    @SuppressWarnings("rawtypes")
    public static void copyFields(Object orig, Object target, boolean copyNullValue) {

        try {
            Map origMap = null;
            if ( orig instanceof Map ) {
                origMap = (Map)orig;
            }

            Map<String, Field> mapOrigField = getFieldMap(orig.getClass());
            Map<String, Field> mapTargetField = getFieldMap(target.getClass());
            Iterator<String> it = mapTargetField.keySet().iterator();
            Object origValue = null;
            Field fieldTarget = null;
            while ( it.hasNext() ) {
                fieldTarget = mapTargetField.get(it.next());

                if ( origMap != null ) {
                    // 来源是Map
                    if ( !origMap.containsKey(fieldTarget.getName()) ) {
                        // 来源中没有该字段
                        continue;
                    } else {
                        // 来源中有该字段
                        origValue = origMap.get(fieldTarget.getName());
                        if ( origValue == null ) {
                            if ( copyNullValue ) {
                                setFieldValue(target, fieldTarget, origValue);
                            }
                            continue;
                        }

                        if ( fieldTarget.getType().equals(origValue.getClass()) ) {
                            setFieldValue(target, fieldTarget, origValue);
                        } else {
                            setFieldValue(target, fieldTarget, ConvertUtil.convert(origValue, fieldTarget.getType()));
                        }
                    }
                } else if ( !mapOrigField.containsKey(fieldTarget.getName()) ) {
                    // 来源中没有该字段
                    continue;
                } else {
                    // 来源中有该字段
                    origValue = getFieldValue(orig, fieldTarget.getName());
                    if ( origValue == null ) {
                        if ( copyNullValue ) {
                            setFieldValue(target, fieldTarget, origValue);
                        }
                        continue;
                    }

                    if ( fieldTarget.getType().equals(origValue.getClass()) ) {
                        setFieldValue(target, fieldTarget, origValue);
                    } else {
                        setFieldValue(target, fieldTarget, ConvertUtil.convert(origValue, fieldTarget.getType()));
                    }
                }

            }
        } catch (IllegalArgumentException e) {
            throw new CoreException(e);
        }
    }

    /**
     * 从指定对象取得指定public字段的值
     * 
     * @param obj 指定对象
     * @param fieldName 字段名
     * @return 字段值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = getFieldMap(obj.getClass()).get(fieldName);
            if ( field == null ) {
                return null;
            }
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new CoreException(e);
        }
    }

    /**
     * 从指定对象取得指定字段的值
     * 
     * @param obj 指定对象
     * @return 字段值
     */
    public static Object getFieldValue(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new CoreException(e);
        }
    }

    /**
     * 按指定属性名设定值
     * 
     * @param obj 对象
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        try {
            Field field = getFieldMap(obj.getClass()).get(fieldName);
            if ( field == null ) {
                return;
            }
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new CoreException(e);
        }
    }

    /**
     * 按指定属性名设定值
     * 
     * @param target 对象
     * @param targetField 字段
     * @param value 字段值
     */
    public static void setFieldValue(Object target, Field targetField, Object value) {
        try {
            if ( !Modifier.isPublic(targetField.getModifiers()) ) {
                targetField.setAccessible(true);
            }
            targetField.set(target, ConvertUtil.convert(value, targetField.getType()));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new CoreException(e);
        }
    }

    /**
     * 为指定对象属性设定值
     * <p>
     * 目标对象是Map时直接put属性值
     * </p>
     * 
     * @param target 目标对象
     * @param property 属性名
     * @param value 设定值
     * @return true:成功/false:失败
     */
    @SuppressWarnings("unchecked")
    public static boolean setPropertyValue(Object target, String property, Object value) {
        if ( target instanceof Map ) {
            ((Map<String, Object>)target).put(property, value);
            return true;
        }

        Map<String, Method> methodMap = getSetterMap(target.getClass());
        if ( methodMap.containsKey(property) ) {
            Method method = methodMap.get(property);
            try {
                method.invoke(target, value);
            } catch (Exception e) {
                throw new CoreException(e);
            }
            return true;
        }

        Map<String, Field> fieldMap = getFieldMap(target.getClass());
        if ( fieldMap.containsKey(property) ) {
            setFieldValue(target, fieldMap.get(property), value);
            return true;
        }

        // 没找到指定属性
        return false;
    }

    /**
     * 取得对象指定属性的值
     * <p>
     * 对象是Map时直接get属性值
     * </p>
     * 
     * @param target 目标对象
     * @param property 属性名
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public static Object getPropertyValue(Object target, String property) {
        if ( target instanceof Map ) {
            return ((Map<String, Object>)target).get(property);
        }

        // 尝试从getter方法取值
        Map<String, Method> mapMethod = CmnClass.getGetterMethodMap(target.getClass());
        Method method = mapMethod.get(property);
        if ( method != null ) {
            try {
                return method.invoke(target);
            } catch (Exception e) {
                throw new CoreException(e);
            }
        }

        // 按public属性取值，取不到返回null
        return CmnBean.getFieldValue(target, property);
    }

}
