package top.gotoeasy.framework.core.log;

/**
 * 日志提供者接口
 * <p>
 * 实现类需要通过SPI方式配置才能生效<br>
 * 配置参考文件 META-INF\services\top.gotoeasy.framework.core.log.LoggerProvider
 * </p>
 * <p>
 * 默认检查使用的顺序是：先Slf4j再log4j-api<br>
 * 如果需要替换，实现LoggerProvider并返回比10更小的优先度，然后以SPI方式配置就行
 * </p>
 * 
 * @since 2018/04
 * @author 青松
 */
public interface LoggerProvider {

    /**
     * 取得日志
     * 
     * @param clas 类
     * @return 日志
     */
    public Log getLogger(Class<?> clas);

    /**
     * 判断能否提供日志服务
     * 
     * @return true:能提供/false:不能提供
     */
    public boolean accept();

}
