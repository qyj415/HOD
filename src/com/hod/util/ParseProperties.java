package com.hod.util;

import java.io.InputStream;
import java.util.Properties;

public class ParseProperties {

	/**
	 * �ķ������ڶ�ȡsysconfig.properties�����ļ������keyֵdatabase��ֵΪMSSQLʱsql���Ϊsqlserver���еģ����ΪMYSQLʱsql���Ϊmysql���е�
	 * @return
	 */
	public static Properties getProperties()
	{
		Properties properties=new Properties();
		try {
			InputStream ins=ParseProperties.class.getResourceAsStream("../../../sysconfig.properties"); 
			properties.load(ins);
		} catch (Exception e) {
			System.out.println("�����ļ�������...");
		}
		return properties;
	}
	
	public static void main(String[] args) {
		Properties properties=ParseProperties.getProperties();
		System.out.println(properties.getProperty("database"));
	}
}
