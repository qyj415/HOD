package com.hod.util;

import java.math.BigDecimal;

public class Arith {

	/**
	 * 三个数相乘
	 * @param a 数值1
	 * @param b 数值2
	 * @param c 数值3
	 * @param scale 精度
	 * @return
	 */
	public static double multiply(double a,double b,double c,int scale)
	{
		BigDecimal data1=new BigDecimal(Double.toString(a));
		BigDecimal data2=new BigDecimal(Double.toString(b));
		BigDecimal data3=new BigDecimal(Double.toString(c));
		BigDecimal result=data1.multiply(data2).multiply(data3);
		return scaleData(result, 2);
	}
	
	/**
	 * 两个数相乘
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static double multiply(double a,double b,int scale)
	{
		BigDecimal data1=new BigDecimal(Double.toString(a));
		BigDecimal data2=new BigDecimal(Double.toString(b));
		BigDecimal result=data1.multiply(data2);
		return scaleData(result, 2);
	}
	
	/**
	 * 两个数相减a-b的值
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static double subtract(double a,double b,int scale)
	{
		BigDecimal data1=new BigDecimal(Double.toString(a));
		BigDecimal data2=new BigDecimal(Double.toString(b));
		BigDecimal result=data1.subtract(data2);
		return scaleData(result, 2);
	}
	
	/**
	 * 两个数相减a-b的值
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static double subtract(String a,String b,int scale)
	{
		BigDecimal data1=new BigDecimal(a);
		BigDecimal data2=new BigDecimal(b);
		BigDecimal result=data1.subtract(data2);
		return scaleData(result, 2);
	}
	
	/**
	 * 两个数相减a-b的值
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static double subtract(String a,double b,int scale)
	{
		BigDecimal data1=new BigDecimal(a);
		BigDecimal data2=new BigDecimal(Double.toString(b));
		BigDecimal result=data1.subtract(data2);
		return scaleData(result, 2);
	}
	
	/**
	 * 两个数相减a-b的值
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static double subtract(double a,String b,int scale)
	{
		BigDecimal data1=new BigDecimal(Double.toString(a));
		BigDecimal data2=new BigDecimal(b);
		BigDecimal result=data1.subtract(data2);
		return scaleData(result, 2);
	}
	
	/**
	 * 两个数相加a+b的值
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static double add(double a,double b,int scale)
	{
		BigDecimal data1=new BigDecimal(Double.toString(a));
		BigDecimal data2=new BigDecimal(Double.toString(b));
		BigDecimal result=data1.add(data2);
		return scaleData(result, 2);
	}
	
	/**
	 * 两个数相加a+b的值
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static double add(double a,String b,int scale)
	{
		BigDecimal data1=new BigDecimal(Double.toString(a));
		BigDecimal data2=new BigDecimal(b);
		BigDecimal result=data1.add(data2);
		return scaleData(result, 2);
	}
	
	/**
	 * 兆焦转为千瓦时
	 * @param obj
	 * @return
	 */
	public static double dataFormat(Object obj)
	{
		if(obj!=null)
		{
			BigDecimal data1=new BigDecimal(obj.toString());
			BigDecimal data2=new BigDecimal(Double.valueOf(1.0/3.6).toString());
			BigDecimal result=data1.multiply(data2);
			return scaleData(result, 2);
		}
		return 0;
	}
	
	/**
	 * 兆焦转为千瓦时
	 * @param obj
	 * @return
	 */
	public static double dataFormat(double a)
	{
		BigDecimal data1=new BigDecimal(Double.toString(a));
		BigDecimal data2=new BigDecimal(Double.valueOf(1.0/3.6).toString());
		BigDecimal result=data1.multiply(data2);
		return scaleData(result, 2);
	}
	
	/**
	 * 千瓦时转兆焦
	 * @return
	 */
	public static double KWHToMJ(String a)
	{
		BigDecimal data1=new BigDecimal(a);
		BigDecimal data2=new BigDecimal(Double.valueOf(1.0/3.6).toString());
		BigDecimal result=data1.divide(data2,2,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	
	/**
	 * 精确化
	 * @param result
	 * @param scale
	 * @return
	 */
	public static double scaleData(BigDecimal result,int scale)
	{
		result=result.setScale(scale,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	
	public static void main(String[] args) {
		System.out.println(dataFormat("0.1260"));
		System.out.println(KWHToMJ(Double.toString(0.10)));
		System.out.println(dataFormat(0.1260));
	}
}
