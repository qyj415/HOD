package com.hod.message;

import org.apache.log4j.Logger;

import com.hod.util.Int2Byte;


public class MessageBase {
	/**instance of Logger*/
	private static final Logger logger = Logger.getLogger(MessageBase.class);
	/**��ͷ�Ĺ̶�����=֡��ʼ��+�Ǳ�����+��ַ��+������*/
	protected static final int HEADER_LENGTH=10;
	/**֡��ʼ��*/
	private static final byte  HEAD   = (byte)  0x68;  
	/**�Ǳ�����,0x20Ϊ��������������*/
	public  byte  meterType   = (byte)  0x20;  
	/**��ַ�򣬺�3���ֽڱ�ʾ���̱��룬�����ֽ����ն˱��(�豸�����ϵ�8λ���)*/
	public  byte[]  address   = new byte[7];  
	/**������,����������վͨ������֡�����룺���У�3F�����У�BF*/
	public byte  controlCode   = (byte)  0x3F;  
	/**������*/
	protected static final byte  TAIL   = (byte)  0x16;  
	/**������Э��Ŀ�����*/
	private byte concentrateCC = (byte)  0x3F;
	/**HOD�Ŀ�����*/
	private byte meterHODCC = (byte)  0x04;
	/**CJ/188Э��Ŀ�����*/
	private byte meter188CC = (byte)  0x24;
	
	
	/**
	 * ȱʡ���캯��
	 */
	public MessageBase() {
	}
	
	/**
	 * @param type
	 * @param addr
	 * @param deviceType 1����������2��HOD��ƣ�3��CJ/188���
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
	 * �������ǰ�沿�ֹ�ͬ�İ�ͷ
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
	 * ������ݳ��ȣ�������У����CS���Լ�֡β
	 * @param data
	 * @return
	 */
	public byte[] parseDownInfoToBytes(byte[] data){
		int dataLength = data.length;
		//��Э�鳤��
		int msgLength = HEADER_LENGTH+1+data.length+1+1;
		if(this.controlCode==this.concentrateCC){//����Ǽ�����������Ϊ2��byte,����Ϊ1��byte
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
		
		//У����
		//byte cs = (byte)CRC16.caluCRC2(msgBytes,0,msgLength-2);
		msgBytes[index] = 0;
		index =index + 1;
		
		msgBytes[index] = this.TAIL;
		
		//�滻CSУ��λ
		byte cs =0;
		for(int i=0;i<(HEADER_LENGTH+2+data.length);i++){
			cs +=msgBytes[i];
		}
		msgBytes[index-1] = cs;
		
		return msgBytes;
	}
	
	
	
	/**
	 * �����ݳ�����תΪbyte���飬��λ��ǰ����λ�ں�
	 * @param msglen
	 * @return byte[]
	 */
	protected byte[] msglenToBytes(int msglen){
		byte[] length; 
		byte bytes[] = Int2Byte.int2bytes(msglen);
		
		if(this.controlCode==this.concentrateCC){//����Ǽ�����������Ϊ2��byte,����Ϊ1��byte
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
	 * ��ȡ�������ݳ���
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
