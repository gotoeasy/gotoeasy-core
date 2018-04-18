package top.gotoeasy.framework.core.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 * 运行期的Java编译器类
 * <p>
 * 使用 {@link JavaCompiler}
 * </p>
 * @since 2018/04
 * @author 青松
 */
public class MemoryJavaCompiler {

	private JavaCompiler						compiler;
	private MemoryFileManager					manager;

	private DiagnosticListener<JavaFileObject>	listener;

	/**
	 * 构造方法
	 */
	public MemoryJavaCompiler() {
		compiler = ToolProvider.getSystemJavaCompiler();
		manager = new MemoryFileManager(compiler.getStandardFileManager(null, null, null));
	}

	/**
	 * 编译一个源文件
	 * <p>
	 * 编译结果存放于{@link MemoryClassStore}中
	 * @param className 类名/源文件名（不含扩展名“.java”）
	 * @param sourceCode 源代码
	 */
	public void compile(String className, String sourceCode) {

		JavaFileObject javaFileObject = new MemorySourceInputObject(className, sourceCode);

		// TODO 更多编译参数?
		CompilationTask task = compiler.getTask(null, manager, getDiagnosticListener(), null, null, Arrays.asList(javaFileObject));

		Boolean ok = task.call();
		if ( !Boolean.TRUE.equals(ok) ) {
			throw new RuntimeException("Compile failed.");
		}

	}

	/**
	 * 编译多个源文件
	 * <p>
	 * 编译结果存放于{@link MemoryClassStore}中
	 * @param map Map<类名,源文件名>
	 */
	public void compile(Map<String, String> map) {

		List<JavaFileObject> list = new ArrayList<>();
		for ( String className : map.keySet() ) {
			list.add(new MemorySourceInputObject(className, map.get(className)));
		}

		// TODO 更多编译参数?
		CompilationTask task = compiler.getTask(null, manager, getDiagnosticListener(), null, null, list);

		Boolean ok = task.call();
		if ( !Boolean.TRUE.equals(ok) ) {
			throw new RuntimeException("Compile failed.");
		}

	}

	/**
	 * 取得一个编译错误的监听器
	 * @return 监听器
	 */
	private DiagnosticListener<JavaFileObject> getDiagnosticListener() {
		if ( listener == null ) {
			listener = new DiagnosticListener<JavaFileObject>() {

				@Override
				public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
					if ( diagnostic.getKind() == Diagnostic.Kind.ERROR ) {
						System.err.println(diagnostic); // TODO 输出编译错误信息
					}
				}
			};
		}

		return listener;
	}

}
