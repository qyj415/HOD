package com.hod.util;

/**
 * 类说明,md5加密算法，不可以解密
 * 使用方法，
 * 加密时：MD5.getMd5(需要加密的串)
 * @author xp1204 E-mail:xp1204@gmail.com
 * @version 创建时间：Jun 27, 2003 5:44:38 PM
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMd5(String str) {
		MessageDigest md;
		// 生成一个MD5加密计算摘要
		try {
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String pwd = new BigInteger(1, md.digest()).toString(16);
			return pwd;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
