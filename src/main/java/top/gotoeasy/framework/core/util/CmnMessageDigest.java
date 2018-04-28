package top.gotoeasy.framework.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import top.gotoeasy.framework.core.exception.CoreException;

/**
 * 加密解密工具类
 * 
 * @since 2018/01
 * @author 青松
 */
public class CmnMessageDigest {

    private CmnMessageDigest() {

    }

    public static String sha(String strInput) {

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new CoreException(e);
        }
        messageDigest.update(strInput.getBytes());
        BigInteger rs = new BigInteger(messageDigest.digest());
        return rs.toString(Character.MAX_RADIX);
    }

    public static String md5(String strInput) {

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new CoreException(e);
        }
        messageDigest.update(strInput.getBytes());
        BigInteger rs = new BigInteger(messageDigest.digest());
        return rs.toString(32);
    }

}
