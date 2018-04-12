package top.gotoeasy.framework.core.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.gotoeasy.framework.core.util.CmnResource;
import top.gotoeasy.framework.core.util.CmnString;

public class JsConfig implements JsConfigKeys {

    private static final Logger                    log          = LoggerFactory.getLogger(JsConfig.class);

    private List<String>                           files;
    private List<String>                           scanPackages;
    private Map<String, Map<String, Object>>       beans;
    private Map<String, List<Map<String, Object>>> beanProps;
    private Map<String, Object>                    values;
    private ScriptEngine                           engine;
    private Map<String, String>                    classNameMap = new HashMap<>();

    public JsConfig() {
        files = new ArrayList<>();
        scanPackages = new ArrayList<>();
        beans = new HashMap<>();
        beanProps = new HashMap<>();
        values = new HashMap<>();

        engine = new ScriptEngineManager().getEngineByName("js");
        engine.put("jsConfig", this);

    }

    public JsConfig(String file) {
        this();
        loadConfigFile(file);
    }

    public String[] getScanPackages() {
        String[] ary = new String[scanPackages.size()];
        for ( int i = 0; i < scanPackages.size(); i++ ) {
            ary[i] = scanPackages.get(i);
        }
        return ary;
    }

    public List<String> getScanPackageList() {
        List<String> list = new ArrayList<>();
        for ( int i = 0; i < scanPackages.size(); i++ ) {
            list.add(scanPackages.get(i));
        }
        return list;
    }

    public Map<String, Map<String, Object>> getBeans() {
        return beans;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public Map<String, List<Map<String, Object>>> getBeanProps() {
        return beanProps;
    }

    public void addScanPackage(String pack) {
        log.trace("添加扫描目标包名[{}]", pack);
        if ( !scanPackages.contains(pack) ) {
            scanPackages.add(pack);
        }
    }

    private String convertClassName(String className) {
        if ( classNameMap.containsKey(className) ) {
            return classNameMap.get(className);
        }
        return className;
    }

    /**
     * 定义类名匿名
     * @param obj 类名匿名Map
     */
    @SuppressWarnings("unchecked")
    public void anonymousClass(Object anonymous) {
        if ( anonymous == null || !(anonymous instanceof Map) ) {
            return;
        }

        Map<String, Object> map = (Map<String, Object>)anonymous;
        for ( String key : map.keySet() ) {
            classNameMap.put(key, (String)map.get(key));
        }
    }

    @SuppressWarnings("unchecked")
    public void addBeans(Object obj) {
        Map<String, Object> map = toMap(obj);
        Map<String, Object> bean = null;
        for ( String name : map.keySet() ) {
            if ( beans.containsKey(name) ) {
                log.error("Bean名称[{}]重复", name);
                throw new RuntimeException("Bean名称[" + name + "]重复");
            }

            bean = (Map<String, Object>)map.get(name);
            editClassName(bean);

            beans.put(name, bean);
        }
    }

    @SuppressWarnings({"unchecked"})
    private void editClassName(Map<String, Object> map) {
        if ( CmnString.isNotBlank((String)map.get(BEAN_CLASS)) ) {
            map.put(BEAN_CLASS, convertClassName((String)map.get(BEAN_CLASS)));
        }

        List<Map<String, Object>> args = (List<Map<String, Object>>)map.get(BEAN_ARGS);
        if ( args != null ) {
            for ( Map<String, Object> arg : args ) {
                editClassName(arg);
            }
        }
    }

    public void addProperties(Object obj) {
        Map<String, Object> map = toMap(obj);
        for ( String name : map.keySet() ) {
            if ( values.containsKey(name) ) {
                log.error("属性名称[{}]重复", name);
                throw new RuntimeException("属性名称[" + name + "]重复");
            }
        }
        values.putAll(map);
        log.trace("添加简单类型属性定义[{}]", map);
    }

    public void loadConfigFile(String file) {
        if ( files.contains(file) ) {
            log.warn("配置文件重复，不做重复装载处理[{}]", file);
            return;
        }
        files.add(file);

        log.debug("读取配置文件[{}]", file);
        try {
            engine.eval(CmnResource.getResourceContext("conf.js", JsConfig.class) + CmnResource.getResourceContext(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object toJavaObject(Object jsObj) {
        if ( jsObj == null ) {
            return null;
        }

        if ( isScriptObjectMirror(jsObj) ) {
            return isJsArray(jsObj) ? toList(jsObj) : toMap(jsObj);
        }

        return jsObj;
    }

    @SuppressWarnings("rawtypes")
    private List toList(Object jsObj) {
        List<Object> list = new ArrayList<>();
        try {
            Class<?> cls = getClassScriptObjectMirror();

            Method values = cls.getMethod("values");
            Object vals = values.invoke(jsObj);

            if ( vals instanceof Collection<?> ) {
                Collection<?> collection = (Collection<?>)vals;
                for ( Object item : collection ) {
                    if ( cls.isAssignableFrom(item.getClass()) ) {
                        list.add(toJavaObject(item));
                    } else {
                        list.add(item);
                    }
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map<String, Object> toMap(Object jsObj) {
        Map<String, Object> rs = new HashMap<>();
        Map mapJs = (Map)jsObj;

        Iterator<String> iterator = mapJs.keySet().iterator();
        String key = null;
        while ( iterator.hasNext() ) {
            key = iterator.next();
            rs.put(key, toJavaObject(mapJs.get(key)));
        }

        return rs;
    }

    private boolean isJsArray(Object jsObj) {
        try {
            Class<?> cls = getClassScriptObjectMirror();
            if ( cls.isAssignableFrom(jsObj.getClass()) ) {
                Method isArray = cls.getMethod("isArray");
                Object result = isArray.invoke(jsObj);

                return result != null && result.equals(true);
            }
        } catch (Exception ignored) {
            log.warn("不处理的异常", ignored);
        }
        return false;
    }

    private boolean isScriptObjectMirror(Object jsObj) {
        return getClassScriptObjectMirror().isAssignableFrom(jsObj.getClass());
    }

    private Class<?> getClassScriptObjectMirror() {
        try {
            return Class.forName("jdk.nashorn.api.scripting.ScriptObjectMirror");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
