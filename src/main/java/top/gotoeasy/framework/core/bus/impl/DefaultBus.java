package top.gotoeasy.framework.core.bus.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import top.gotoeasy.framework.core.bus.Bus;
import top.gotoeasy.framework.core.bus.annotation.BusListener;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.core.util.Assert;

/**
 * 默认总线实现类
 * @since 2018/03
 * @author 青松
 */
public class DefaultBus implements Bus {

	private static final Log					log	= LoggerFactory.getLogger(DefaultBus.class);
	private Map<String, List<ListenerMethod>>	map	= new ConcurrentHashMap<>();

	/**
	 * 注册指定标识的唯一监听器
	 * @param key 标识
	 * @param listener 监听器
	 */
	@Override
	public void one(String key, Object listener) {
		Assert.notNull(key, "key必须非空");
		Assert.notNull(listener, "listener非空");

		List<ListenerMethod> list = getListenerList(key);
		synchronized ( list ) {
			ListenerMethod lm = getListener(listener);
			list.clear();
			list.add(lm);
		}
	}

	/**
	 * 注册指定标识的监听器，同一标识可以有多个监听器
	 * @param key 标识
	 * @param listener 监听器
	 */
	@Override
	public void on(String key, Object listener) {
		Assert.notNull(key, "key必须非空");
		Assert.notNull(listener, "listener非空");

		List<ListenerMethod> list = getListenerList(key);
		synchronized ( list ) {
			ListenerMethod lm = getListener(listener);
			if ( !list.contains(lm) ) {
				list.add(lm);
			}
		}
	}

	/**
	 * 取消指定标识的指定监听器
	 * @param key 标识
	 * @param listener 监听器
	 */
	@Override
	public void off(String key, Object listener) {
		if ( key == null || listener == null ) {
			return;
		}

		List<ListenerMethod> list = getListenerList(key);
		synchronized ( list ) {
			if ( list.contains(listener) ) {
				list.remove(listener);
			}
		}
	}

	/**
	 * 取消指定标识的全部监听器
	 * @param key 标识
	 */
	@Override
	public void off(String key) {
		if ( key == null ) {
			return;
		}

		List<ListenerMethod> list = getListenerList(key);
		synchronized ( list ) {
			list.clear();
		}
	}

	/**
	 * 触发指定标识的监听处理
	 * @param key 标识
	 * @param args 触发参数
	 * @return 结果
	 */
	@Override
	public Object trigger(String key, Object ... args) {
		if ( key == null ) {
			return null;
		}

		List<ListenerMethod> list = getListenerList(key);
		boolean one = (list.size() == 1);
		try {
			Object rs;
			for ( ListenerMethod lm : list ) {
				if ( lm.method.isVarArgs() ) {
					rs = lm.method.invoke(lm.bean, (Object)args); // 可变参数
				} else {
					rs = lm.method.invoke(lm.bean, args);
				}
				if ( one ) {
					return rs;   // 仅一个监听器时，返回处理结果
				}
			}
		} catch (Exception e) {
			log.error("key=[{}], args={}, ListenerMethod={}", key, args, list);
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * 重置总线
	 */
	@Override
	public void reset() {
		map.clear();
	}

	/**
	 * 取得指定标识的监听器列表
	 * @param key 标识
	 * @return 监听器列表
	 */
	private List<ListenerMethod> getListenerList(String key) {
		List<ListenerMethod> list = map.get(key);
		if ( list == null ) {
			synchronized ( map ) {
				if ( list == null ) {
					list = new ArrayList<>();
					map.put(key, list);
				}
			}
		}
		return list;
	}

	/**
	 * 取得指定对象的监听方法(@BusListener方法)
	 * @param bean 对象
	 * @return 对象监听方法
	 */
	private ListenerMethod getListener(Object bean) {
		if ( bean == null ) {
			return null;
		}

		Method[] methods = bean.getClass().getDeclaredMethods();
		for ( Method method : methods ) {
			if ( Modifier.isPublic(method.getModifiers()) && method.isAnnotationPresent(BusListener.class) ) {
				// public方法上@BusListener
				return new ListenerMethod(bean, method);
			}
		}

		return null;
	}

	/**
	 * 对象监听方法
	 * <p>
	 * 内部结构类
	 * </p>
	 */
	private class ListenerMethod {

		private Object	bean;
		private Method	method;

		private ListenerMethod(Object bean, Method method) {
			this.bean = bean;
			this.method = method;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ListenerMethod [bean=" + bean + ", method=" + method + "]";
		}

	}
}
