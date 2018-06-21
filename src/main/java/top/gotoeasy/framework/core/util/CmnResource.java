package top.gotoeasy.framework.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import top.gotoeasy.framework.core.exception.CoreException;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;

/**
 * 资源工具类
 * 
 * @since 2018/01
 * @author 青松
 */
public class CmnResource {

    private static final Log log = LoggerFactory.getLogger(CmnResource.class);

    private CmnResource() {

    }

    /**
     * 取得文件对象
     * 
     * @param fileName 文件名（ 根路径下:abc.xml， 指定包路径下:com/test/abc.xml）
     * @return 文件对象
     */
    public static File getFile(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if ( url == null ) {
            log.error("文件找不到({})", fileName);
            return null;
        }

        return new File(url.getPath());
    }

    /**
     * 取得指定文本文件内容
     * 
     * @param fileName 文件名（ 根路径下:abc.xml， 指定包路径下:com/test/abc.xml）
     * @return 文件内容
     */
    public static String getResourceContext(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if ( url == null ) {
            log.error("文件找不到({})", fileName);
            return null;
        }
        return CmnFile.readFileText(url.getPath(), "utf-8");
    }

    /**
     * 按UTF_8编码读取指定文本文件内容
     * 
     * @param fileName 文件名（ 根路径下:abc.xml， 指定包路径下:com/test/abc.xml）
     * @return 文件内容
     */
    public static String getContext(String fileName) {
        return getContext(fileName, StandardCharsets.UTF_8.name());
    }

    /**
     * 按指定编码读取指定文本文件内容
     * 
     * @param fileName 文件名（ 根路径下:abc.xml， 指定包路径下:com/test/abc.xml）
     * @param charset 编码
     * @return 文件内容
     */
    public static String getContext(String fileName, String charset) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if ( url == null ) {
            log.error("文件找不到({})", fileName);
            return null;
        }

        try ( InputStream io = url.openStream(); ByteArrayOutputStream bos = new ByteArrayOutputStream(); ) {
            byte[] buffer = new byte[1024];
            int length;
            while ( (length = io.read(buffer)) != -1 ) {
                bos.write(buffer, 0, length);
            }
            return bos.toString(CmnString.isNotBlank(charset) ? charset : StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new CoreException(e);
        }

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

    /**
     * 读取指定文件内容
     * 
     * @param fileName 文件名（ 根路径下:abc.xml， 指定包路径下:com/test/abc.xml）
     * @param claz 文件所在包和此类包相同
     * @return 文件内容
     */
    public static byte[] getResourceBytes(String fileName, Class<?> claz) {
        String name = claz.getPackage().getName().replace('.', '/') + "/" + fileName;
        URL url = Thread.currentThread().getContextClassLoader().getResource(name);
        if ( url == null ) {
            log.error("文件找不到({})", name);
            return null;
        }

        try {
            return Files.readAllBytes(Paths.get(new File(url.getPath()).getAbsolutePath()));
        } catch (IOException e) {
            throw new CoreException(e);
        }
    }

}
