package top.gotoeasy.framework.core.exception;

/**
 * Core模块异常
 * 
 * @since 2018/03
 * @author 青松
 */
public class CoreException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造方法
     */
    public CoreException() {
        super();
    }

    /**
     * 构造方法
     * 
     * @param message 消息
     */
    public CoreException(String message) {
        super(message);
    }

    /**
     * 构造方法
     * 
     * @param message 消息
     * @param cause 异常
     */
    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法
     * 
     * @param cause 异常
     */
    public CoreException(Throwable cause) {
        super(cause);
    }
}
