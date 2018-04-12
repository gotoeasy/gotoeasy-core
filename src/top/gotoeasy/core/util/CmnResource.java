package top.gotoeasy.core.util;

import java.net.URL;

public class CmnResource {

	/**
	 * 取得指定文本文件内容
	 * 
	 * @param file 文件名<br/>
	 *            根路径下: abc.xml<br/>
	 *            指定包路径下: com/test/abc.xml
	 * @return 文件内容
	 */
	public static String getResourceContext(String file) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(file);
		if ( url == null ) {
			return null;
		}
		return CmnFile.readFileText(url.getPath(), "utf-8");
	}

	/**
	 * 取得指定文本文件内容
	 * 
	 * @param file 文件名
	 * @param claz 文件所在包和此类包相同
	 * @return 文件内容
	 */
	public static String getResourceContext(String file, Class<?> claz) {
		return getResourceContext(claz.getPackage().getName().replace(".", "/") + "/" + file);
	}

}
