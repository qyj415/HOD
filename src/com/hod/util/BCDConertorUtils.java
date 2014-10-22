package com.hod.util;

/**
 * BCD码 转换
 * 
 * @author 瞿勇金
 * @Jul 11, 2013 10:51:31 AM
 * @version V0.1
 */

public class BCDConertorUtils {

	/**
	 * 将数转为BCD码
	 * 
	 * @param c
	 * @return
	 */
	public static String BCDStr(char c){
		String bcd = null;
		switch(c)
		{
		case '0':
			bcd = "0000";
			break;
		case '1':
			bcd = "0001";
			break;
		case '2':
			bcd = "0010";
			break;	
		case '3':
			bcd = "0011";
			break;
		case '4':
			bcd = "0100";
			break;
		case '5':
			bcd = "0101";
			break;
		case '6':
			bcd = "0110";
			break;
		case '7':
			bcd = "0111";
			break;
		case '8':
			bcd = "1000";
			break;
		case '9':
			bcd = "1001";
			break;
		default:
			bcd = "0000";
		}
		return bcd;
	}
	
}
