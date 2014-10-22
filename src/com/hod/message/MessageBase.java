package com.hod.message;

import org.apache.log4j.Logger;

import com.hod.util.Int2Byte;


public class MessageBase {
	/**instance of Logger*/
	private static final Logger logger = Logger.getLogger(MessageBase.class);
	/**包头的固定长度=帧起始符+仪表类型+地址域+控制码*/
	protected static final int HEADER_LENGTH=10;
	/**帧起始符*/
	private static final byte  HEAD   = (byte)  0x68;  
	/**仪表类型,0x20为热量表（计热量）*/
	public  byte  meterType   = (byte)  0x20;  
	/**地址域，后3个字节表示厂商编码，其余字节是终端编号(设备铭牌上的8位编号)*/
	public  byte[]  address   = new byte[7];  
	/**控制码,集中器与主站通信数据帧控制码：下行，3F；上行，BF*/
	public byte  controlCode   = (byte)  0x3F;  
	/**结束符*/
	protected static final byte  TAIL   = (byte)  0x16;  
	/**集中器协议的控制码*/
	private byte concentrateCC = (byte)  0x3F;
	/**HOD的控制码*/
	private byte meterHODCC = (byte)  0x04;
	/**CJ/188协议的控制码*/
	private byte meter188CC = (byte)  0x24;
	
	
	/**
	 * 缺省构造函数
	 */
	public MessageBase() {
	}
	
	/**
	 * @param type
	 * @param addr
	 * @param deviceType 1：集中器；2：HOD表计；3：CJ/188表计
	 */
	public MessageBase(byte type,byte[] addr,int deviceType) {
		this.meterType=type;
		this.address=addr;
		if(deviceType==1){
			this.controlCode = this.concentrateCC;
		}else if(deviceType==2){
			this.controlCode = this.meterHODCC;
		}else{
			this.controlCode = this.meter188CC;
		}
		
	}
	
	/**
	 * 打包报文前面部分共同的包头
	 * @return byte[]
	 */
	public byte[] parseHeadToBytes(){
		byte[] header = new byte[HEADER_LENGTH];
		int index = 0;
		header[index] = HEAD;
		index = index+1;
		
		header[index] = meterType;
		index = index+1;
		
		System.arraycopy(address, 0, header, index, address.length);
		index = index+7;
		
		header[index] = controlCode;
		index = index+1;
		
		
		return header;
	}
	
	/**
	 * 打包数据长度，数据域，校验码CS，以及帧尾
	 * @param data
	 * @return
	 */
	public byte[] parseDownInfoToBytes(byte[] data){
		int dataLength = data.length;
		//包协议长度
		int msgLength = HEADER_LENGTH+1+data.length+1+1;
		if(this.controlCode==this.concentrateCC){//如果是集中器，长度为2个byte,否则为1个byte
			msgLength = HEADER_LENGTH+2+data.length+1+1;
		}
		int index =0;
		byte[] msgBytes = new byte[msgLength];
		byte[] header = this.parseHeadToBytes();
		byte[] dLength = this.msglenToBytes(dataLength);
		
		System.arraycopy(header, 0, msgBytes, index, HEADER_LENGTH);
		index = index+HEADER_LENGTH;
		
		System.arraycopy(dLength, 0, msgBytes, index, dLength.length);
		index = index+dLength.length;
		
		System.arraycopy(data, 0, msgBytes, index, dataLength);
		index = index+dataLength;
		
		//校验码
		//byte cs = (byte)CRC16.caluCRC2(msgBytes,0,msgLength-2);
		msgBytes[index] = 0;
		index =index + 1;
		
		msgBytes[index] = this.TAIL;
		
		//替换CS校验位
		byte cs =0;
		for(int i=0;i<(HEADER_LENGTH+2+data.length);i++){
			cs +=msgBytes[i];
		}
		msgBytes[index-1] = cs;
		
		return msgBytes;
	}
	
	
	
	/**
	 * 将数据长度域转为byte数组，高位在前，低位在后
	 * @param msglen
	 * @return byte[]
	 */
	protected byte[] msglenToBytes(int msglen){
		byte[] length; 
		byte bytes[] = Int2Byte.int2bytes(msglen);
		
		if(this.controlCode==this.concentrateCC){//如果是集中器，长度为2个byte,否则为1个byte
			length= new byte[2]; 
			length[0]    = bytes[3];
			length[1]  = bytes[2];
		}else{
			length= new byte[1]; 
			length[0]    = bytes[3];
		}
		
		return length;
		
   }
	
	/**
	 * 获取包的数据长度
	 * @return length
	 */
	public int getmsglen(byte[] message,int position)
	{		
		return Int2Byte.byte2int( (byte)0,(byte)0, message[position+1],message[position]);

	}
	public static void main(String[] args) {
		
		//MessageBase m= new MessageBase();
		//byte[] t= m.parseDownInfoToBytes("12345".getBytes());
		//System.out.print(t.length);
	}
}
