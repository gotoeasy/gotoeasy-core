package top.gotoeasy.framework.core.compiler;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * 编译输出类
 * <p>
 * 修改编译结果的输出过程，由输出到Class文件改成存放在内存{@link MemoryClassStore}中
 * </p>
 * @since 2018/04
 * @author 青松
 */
public class MemoryClassOutputObject extends SimpleJavaFileObject {

	private final String className;

	/**
	 * 构造方法
	 * @param className 类名
	 */
	MemoryClassOutputObject(String className) {
		super(URI.create("string:///" + className), Kind.CLASS);
		this.className = className;
	}

	/**
	 * 修改编译结果的输出位置，由输出到Class文件改成存放在内存
	 * @return OutputStream 输出流
	 */
	@Override
	public OutputStream openOutputStream() {
		return new FilterOutputStream(new ByteArrayOutputStream()) {

			@Override
			public void close() throws IOException {
				out.close();
				// 把编译结果字节码，存放在内存
				MemoryClassStore.put(className, ((ByteArrayOutputStream)out).toByteArray());
			}
		};
	}

}
