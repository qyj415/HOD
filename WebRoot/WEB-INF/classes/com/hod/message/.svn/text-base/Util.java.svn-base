package com.hod.message;

import java.math.BigInteger;

/**
 * 
 * @author gngyf15
 * 帮助
 *
 */
public class Util {
	
	/**
	 * 
	 * @param conNum 集中器编号
	 * @param productNum 厂商编号
	 * @return 地址域=（集中器编号+厂商编号）
	 */
	public static byte[] getAddr(String conNum,char[] productNum)
	{
		StringBuffer sb=new StringBuffer();
		//sb.append(HexString2Byte(conNum,8));
		//sb.append(conNum);
		for (int i = conNum.length(); i>0 ; i--) {
			if(i%2==0)
			{
				sb.append(conNum.substring(i-2,i));
			}
		}
		for (int i = productNum.length; i >0; i--) {
			sb.append((int)productNum[i-1]);
		}
		return convert2HexArray(sb.toString());
	}
	
	/**
	 * 通信地址
	 * @param conNum
	 * @param productNum
	 * @return
	 */
	public static String getAddress(String conNum,char[] productNum)
	{
		StringBuffer sb=new StringBuffer();
		for (int i = conNum.length(); i>0 ; i--) {
			if(i%2==0)
			{
				sb.append(conNum.substring(i-2,i));
			}
		}
		for (int i = productNum.length; i >0; i--) {
			sb.append((int)productNum[i-1]);
		}
		return sb.toString();
	}
	
	public static String getAddress(String meterName)
	{
		StringBuffer sb=new StringBuffer();
		for (int i = meterName.length(); i>0 ; i--) {
			if(i%2==0)
			{
				sb.append(meterName.substring(i-2,i));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param identificationId 数据标识
	 * @param date 数据域
	 * @param SER 序号
	 * @return
	 */
	public static byte[] getData(String identificationId,String data,String SER)
	{
		StringBuffer sb=new StringBuffer();
		if(identificationId!=null&&!identificationId.equals(""))
			sb.append(identificationId);
		if(SER!=null&&!SER.equals(""))
			sb.append(SER);
		if(data!=null&&!data.equals(""))
			sb.append(data);
		return convert2HexArray(sb.toString());
	}
	
	/**
	 * 十进制转换为十六进制
	 * @param data
	 * @return
	 */
	public static String decimalToHexadecimal(int data,int len)
	{
		String str=Integer.toHexString(data);
		StringBuffer sb=new StringBuffer();
		while(str.length()<len)
		{
			str="0"+str;
		}
		if(str.length()>2)
		{
			for (int i=str.length();i>0;i--) {
				if(i%2==0)
				{
					sb.append(str.substring(i-2,i));
				}
			}
			return sb.toString();
		}
		else
		{
			return str;
		}
	}
	
	public static String getIpStr(String str[])
	{
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			sb.append(decimalToHexadecimal(Integer.parseInt(str[i]), 2));
		}
		return sb.toString();
	}
	
	//将16进制字符串转成字节数组
	public static byte[] convert2HexArray(String apdu)  
	{              
		int len = apdu.length() / 2;  
		char[] chars = apdu.toCharArray();  
		String[] hexes = new String[len];  
		byte[] bytes = new byte[len];  
		for (int i = 0, j = 0; j < len; i = i + 2, j++)  
		{  
			hexes[j] = "" + chars[i] + chars[i + 1];  
			bytes[j] = (byte)Integer.parseInt(hexes[j],16);  
		}  
		return bytes;  
	} 
	
	//BCD码转字符串
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
         sTemp = Integer.toHexString(0xFF & bArray[i]);
         if (sTemp.length() < 2)
          sb.append(0);
         sb.append(sTemp.toUpperCase()+" ");
        }
        return sb.toString().trim();
    }
    
    //十进制转换为二进制码
    public static String getBin(String data,int len)
    {
    	data=Integer.toBinaryString(Integer.parseInt(data));
    	while(data.length()<len)
		{
    		data="0"+data;
		}
    	return data;
    }
    
    //终端等待从动站响应的超时时间和重发次数
    public static String toByte(int time,int times)
    {
    	String s1="00"+getBin(""+times,2)+getBin(""+time,12);
    	StringBuffer sb=new StringBuffer();
    	String str=Integer.toHexString(Integer.parseInt(s1,2));//转成16进制
    	for (int i = str.length(); i >0 ; i--) {//倒过来
			if(i%2==0)
			{
				sb.append(str.substring(i-2,i));
			}
		}
    	return sb.toString();  
    }
    
    public static String toByte(String meter_baudrate)
    {
    	String s1="00"+getBin(meter_baudrate, 3)+"000";
    	String str=Integer.toHexString(Integer.parseInt(s1,2));
    	if(str.length()!=2)
    		str="0"+str;
    	return str;
    }
    
    /**
     * 
     * @param freezeInterval 冻结间隔
     * @param freezeIntervalValue 冻结时间
     * @return
     */
    public static String freezeInterval(String freezeInterval,String freezeIntervalValue)
    {
    	//String s1=toFan(getBin(freezeInterval,2)+getBin(freezeIntervalValue, 6));
    	String s1=getBin(freezeInterval,2)+getBin(freezeIntervalValue, 6);
    	String str=Integer.toHexString(Integer.parseInt(s1,2));
    	if(str.length()<2)
    		str="0"+str;
    	return str;
    }
    
    public static String getCompanyNum(String companyNum)
    {
    	char[] chars=companyNum.toCharArray();
    	StringBuffer sb=new StringBuffer();
    	for (int i = chars.length; i >0; i--) {
			sb.append((int)chars[i-1]);
		}
    	return sb.toString();
    }
    
//    public String toByte(int count,int times)
//    {
//    	int result1 =count<<4;
//		result1 = result1<<8;
//		result1=result1|times;
//		byte byte1=(byte) (result1&0xff);
//		byte byte2 =  (byte) (result1>>8);
//		byte[] bytes=new byte[2];
//		bytes[0]=byte1;
//		bytes[1]=byte2;
//		return bytesToHexString(bytes);
//    }

    public static String getAPN(String apn,int length)
    {
    	char[] chars=apn.toUpperCase().toCharArray();
    	String str="";
    	for (int i = chars.length; i >0; i--) {
			str+=(int)chars[i-1];
		}
    	while(str.length()<length)
    		str="0"+str;
    	return str;
    }
    
    public static String getConAddress(String conAddress)
    {
    	StringBuffer str=new StringBuffer();
    	while(conAddress.length()<15)
    	{
    		conAddress+="0";
    	}
    	conAddress=conAddress.substring(0,9)+"000"+conAddress.substring(9,15);
    	for (int i = conAddress.length(); i >0; i--) {
    		if(i%2==0)
			{
    			str.append(conAddress.substring(i-2,i));
			}
		}
    	return str.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(Util.getAPN("cmnet",32));
    	System.out.println(Util.getConAddress("440305002001222"));
	}

}
