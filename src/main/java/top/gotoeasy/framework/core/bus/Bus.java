package top.gotoeasy.framework.core.bus;

/**
 * 总线接口
 * @since 2018/03
 * @author 青松
 */
public interface Bus {

	/**
	 * 注册指定标识的唯一监听器
	 * @param key 标识
	 * @param listener 监听器
	 */
	public void one(String key, Object listener);

	/**
	 * 注册指定标识的监听器，同一标识可以有多个监听器
	 * @param key 标识
	 * @param listener 监听器
	 */
	public void on(String key, Object listener);

	/**
	 * 取消指定标识的指定监听器
	 * @param key 标识
	 * @param listener 监听器
	 */
	public void off(String key, Object listener);

	/**
	 * 取消指定标识的全部监听器
	 * @param key 标识
	 */
	public void off(String key);

	/**
	 * 触发指定标识的监听处理
	 * @param key 标识
	 * @param args 触发参数
	 * @return 结果
	 */
	public Object trigger(String key, Object ... args);

	/**
	 * 重置总线
	 */
	public void reset();

}
