package top.gotoeasy.framework.core.util;

import java.io.Closeable;

/**
 * 常用工具类
 * @since 2018/01
 * @author 青松
 */
public class CmnCore {

	/**
	 * 关闭资源
	 * @param closeables 资源
	 */
	public static void closeQuietly(Closeable ... closeables) {
		if ( closeables == null ) {
			return;
		}

		for ( Closeable closeable : closeables ) {
			if ( closeable != null ) {
				try {
					closeable.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
