package com.hod.util;

/**
 * ��˵��,md5�����㷨�������Խ���
 * ʹ�÷�����
 * ����ʱ��MD5.getMd5(��Ҫ���ܵĴ�)
 * @author xp1204 E-mail:xp1204@gmail.com
 * @version ����ʱ�䣺Jun 27, 2003 5:44:38 PM
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMd5(String str) {
		MessageDigest md;
		// ����һ��MD5���ܼ���ժҪ
		try {
			md = MessageDigest.getInstance("MD5");
			// ����md5����
			md.update(str.getBytes());
			// digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ�������Ϊmd5 hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�
			// BigInteger������8λ���ַ���ת����16λhexֵ�����ַ�������ʾ���õ��ַ�����ʽ��hashֵ
			String pwd = new BigInteger(1, md.digest()).toString(16);
			return pwd;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
