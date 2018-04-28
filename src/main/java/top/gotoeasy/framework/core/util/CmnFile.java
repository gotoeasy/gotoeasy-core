package top.gotoeasy.framework.core.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

import top.gotoeasy.framework.core.exception.CoreException;

/**
 * 文件工具类
 * 
 * @since 2018/01
 * @author 青松
 */
public class CmnFile {

    private CmnFile() {

    }

    public static File getTempFile() {
        String uuid = UUID.randomUUID().toString();
        File tempFile = null;
        try {
            File dir = File.createTempFile("tmp", null).getParentFile();
            tempFile = new File(dir, uuid);
        } catch (Exception e) {
            throw new CoreException(e);
        }

        return tempFile;
    }

    /**
     * 删除文件
     * 
     * @param file 文件
     */
    public static void deleteFile(File file) {
        try {
            if ( file.exists() ) {
                file.delete();
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 删除文件
     * 
     * @param fileName 文件名
     */
    public static void deleteFile(String fileName) {
        deleteFile(new File(fileName));
    }

    /**
     * 删除文件
     * 
     * @param folder 目录
     * @param name 文件名
     */
    public static void deleteFile(File folder, String name) {
        try {
            File file = new File(folder, name);
            if ( file.exists() ) {
                file.delete();
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 关闭对象
     * 
     * @param closeables Closeable对象
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
                    // ignore
                }
            }
        }
    }

    public static String readFileText(String pathname, String encoding) {
        String filecodding = "UTF-8";
        if ( CmnString.isNotBlank(encoding) ) {
            filecodding = encoding;
        }

        File file = new File(pathname);
        if ( file.isDirectory() || !file.exists() ) {
            return null;
        }

        Long length = file.length();
        byte[] content = new byte[length.intValue()];
        try ( FileInputStream in = new FileInputStream(file); ) {
            in.read(content);

            return new String(content, filecodding);
        } catch (Exception e) {
            throw new CoreException(e);
        }
    }

    public static void writeFileText(String pathname, String content, String encoding) {

        try ( FileOutputStream out = new FileOutputStream(new File(pathname)); ) {
            out.write(content.getBytes(encoding));
            out.flush();
        } catch (Exception e) {
            throw new CoreException(e);
        }
    }

}
