package com.hod.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * 获取网络时间
 * 当网络时间获取失败时，使用本地时间
 * 
 * @author 瞿勇金
 * @2013-5-28 下午05:37:21
 * @version V0.1
 */

public class NetworkTimeUtil {
	
	private static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Logger log = Logger.getLogger(NetworkTimeUtil.class.getName());
	/**
	 * 获取时间
	 * 
	 * @param sdf SimpleDateFormat实例
	 * @return 返回时间格式化的字符串
	 */
	public static String getTime(SimpleDateFormat sdf) {
		
		String timeStr = null;
		
		try {
			URL url = new URL("http://www.baidu.com");
			URLConnection uc;
			uc = url.openConnection();
			// 生成连接对象
			uc.connect(); // 发出连接
			long ld = uc.getDate(); // 取得网站日期时间
			Date date = new Date(ld); // 转换为标准时间对象
			timeStr = sdf.format(date);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (timeStr == null) {
				timeStr = sdf.format(new Date());
			}
		}
		return timeStr;
	}
	
	/**
	 * 获取时间 使用默认的SimpleDateFormat
	 * 
	 * @return 返回时间格式化的字符串
	 */
	public static String getTime() {
		
		String timeStr = null;
		
		try {
			URL url = new URL("http://www.baidu.com");
			URLConnection uc;
			uc = url.openConnection();
			// 生成连接对象
			uc.connect(); // 发出连接
			long ld = uc.getDate(); // 取得网站日期时间
			Date date = new Date(ld); // 转换为标准时间对象
			timeStr = sdfTime.format(date);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (timeStr == null) {
				timeStr = sdfTime.format(new Date());
			}
		}
		return timeStr;
	}
	
	/**
	 * 获取时间 Date
	 * 
	 * @return 返回时间格式化的字符串
	 */
	public static Date getDate() {
		
		Date time = null;
		
		try {
			URL url = new URL("http://www.baidu.com");
			URLConnection uc;
			uc = url.openConnection();
			// 生成连接对象
			uc.connect(); // 发出连接
			long ld = uc.getDate(); // 取得网站日期时间
			time = new Date(ld); // 转换为标准时间对象

		} catch (IOException e) {
			//e.printStackTrace();
			log.info("获取网络时间超时");
		} finally {
			if (time == null) {
				time = new Date();
			}
		}
		return time;
	}


	public static void main(String[] args){
		NetworkTimeUtil.getTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	
}
