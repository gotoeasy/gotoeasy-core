package top.gotoeasy.framework.core.compiler;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * 编译源代码输入类
 * <p>
 * 修改编译时源代码的输入位置，由文件输入改成直接字符串输入
 * </p>
 * @since 2018/04
 * @author 青松
 */
public class MemorySourceInputObject extends SimpleJavaFileObject {

	/** 源代码 */
	private final String sourceCode;

	/**
	 * 构造方法
	 * <p>
	 * 构造一个源文件对象<br>
	 * 一个java文件如果含内部类的话会编译出多个类<br>
	 * 参数中的类名，使用的是java文件单位的类名<br>
	 * 比如编译top.gotoeasy.test.Hello.java的话，传入top.gotoeasy.test.Hello
	 * </p>
	 * @param className 类名/源文件名（不含扩展名“.java”）
	 * @param sourceCode 源代码
	 */
	MemorySourceInputObject(String className, String sourceCode) {
		super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.sourceCode = sourceCode;
	}

	/**
	 * 取得源代码
	 * @return 源代码
	 */
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return sourceCode;
	}

}
