package top.gotoeasy.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CmnMessageDigest {

	public static String sha(String strInput) {

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
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
			throw new RuntimeException(e);
		}
		messageDigest.update(strInput.getBytes());
		BigInteger rs = new BigInteger(messageDigest.digest());
		return rs.toString(32);
	}

}
