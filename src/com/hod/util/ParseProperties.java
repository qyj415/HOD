package com.hod.util;

import java.io.InputStream;
import java.util.Properties;

public class ParseProperties {

	/**
	 * 改方法用于读取sysconfig.properties配置文件，如果key值database的值为MSSQL时sql语句为sqlserver独有的，如果为MYSQL时sql语句为mysql独有的
	 * @return
	 */
	public static Properties getProperties()
	{
		Properties properties=new Properties();
		try {
			InputStream ins=ParseProperties.class.getResourceAsStream("../../../sysconfig.properties"); 
			properties.load(ins);
		} catch (Exception e) {
			System.out.println("配置文件不存在...");
		}
		return properties;
	}
	
	public static void main(String[] args) {
		Properties properties=ParseProperties.getProperties();
		System.out.println(properties.getProperty("database"));
	}
}
