package top.gotoeasy.framework.core.compiler;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

/**
 * 动态编译的文件管理器
 * <p>
 * 针对编译结果的类字节码，重定向输出，改成保存到内存中
 * </p>
 * @since 2018/04
 * @author 青松
 */
public class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

	/**
	 * 构造方法
	 * @param standardJavaFileManager 标准Java文件管理器
	 */
	protected MemoryFileManager(JavaFileManager standardJavaFileManager) {
		super(standardJavaFileManager);
	}

	/**
	 * 输出文件
	 * @param location 位置
	 * @param className 类名
	 * @param kind 类型
	 * @param sibling 文件对象
	 * @return Java文件对象
	 */
	@Override
	public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, Kind kind, FileObject sibling)
			throws IOException {
		if ( kind == Kind.CLASS ) {
			// 针对编译结果的类字节码，重定向输出，改成保存到内存中
			return new MemoryClassOutputObject(className);
		} else {
			return super.getJavaFileForOutput(location, className, kind, sibling);
		}
	}

}
