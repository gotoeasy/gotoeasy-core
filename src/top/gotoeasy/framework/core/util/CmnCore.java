package top.gotoeasy.framework.core.util;

import java.io.Closeable;

public class CmnCore {

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
