package com.hod.util;


//高位在前，低位在后
public class Int2Byte {
	/**
	 * 将一个字节16进制的字符串转换为16进制
	 * @param src0
	 * @param src1
	 * @return
	 */
	public static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}
	
	/**
	   * 将两个ASCII字符合成一个字节；
	   * 如："EF"–> 0xEF
	   * @param src0 byte
	   * @param src1 byte
	   * @return byte
	   */
	  public static byte uniteBytes(byte src0, byte src1) {
	    byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
	    _b0 = (byte)(_b0 << 4);
	    byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
	    byte ret = (byte)(_b0 ^ _b1);
	    return ret;
	  }
	  /**
	   * 将指定字符串src，以每两个字符分割转换为16进制形式
	   * 如："2B44EFD9" –> byte[]{0x2B, 0×44, 0xEF, 0xD9}
	   * @param src String
	   * @return byte[]
	   */
	public static byte[] HexString2Bytes(String src){
	    byte[] ret = new byte[8];
	    byte[] tmp = src.getBytes();
	    for(int i=0; i<8; i++){
	      ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]);
	    }
	    return ret;
	  }
	
	public static int bytes2int(byte[] bytes) {
		int in2 = (bytes[3] & 0xff) | ((bytes[2] << 8) & 0xff00)
				| ((bytes[1] << 24) >>> 8) | (bytes[0] << 24);
		return in2;
	}

	public static int bytes2int(byte[] bytes,int start) {
		int in2 = (bytes[start] & 0xff) | ((bytes[start+1] << 8) & 0xff00)
				| ((bytes[start+2] << 24) >>> 8) | (bytes[start+3] << 24);
		return in2;
	}
	
	public static int byte2int(byte  byte1,byte  byte2,byte  byte3,byte  byte4) {
		int in2 = (byte4 & 0xff) | ((byte3 << 8) & 0xff00)
				| ((byte2 << 24) >>> 8) | (byte1 << 24);
		return in2;
	}
	
	public static byte[] int2bytes(int in) {
		byte[] bytes = new byte[4];
		byte b1 = (byte) (in & 0xff);
		bytes[3] = b1;
		byte b2 = (byte) ((in >> 8) & 0xff);
		bytes[2] = b2;
		byte b3 = (byte) ((in >> 16) & 0xff);
		bytes[1] = b3;
		byte b4 = (byte) (in >>> 24);
		bytes[0] = b4;
		return bytes;
	}

}
