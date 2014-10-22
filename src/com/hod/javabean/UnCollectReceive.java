package com.hod.javabean;

public class UnCollectReceive {

	private int clientId;
	private String clientName;
	private String clientTel;
	private double preMoney;
	private double receiveMoney;
	private String meterName;
	private String address;
	private double meterInit;
	private int roomId;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public double getPreMoney() {
		return preMoney;
	}
	public void setPreMoney(double preMoney) {
		this.preMoney = preMoney;
	}
	public double getReceiveMoney() {
		return receiveMoney;
	}
	public void setReceiveMoney(double receiveMoney) {
		this.receiveMoney = receiveMoney;
	}
	public String getMeterName() {
		return meterName;
	}
	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}
	public void setMeterInit(double meterInit) {
		this.meterInit = meterInit;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public double getMeterInit() {
		return meterInit;
	}
	public int getRoomId() {
		return roomId;
	}
	public String getClientTel() {
		return clientTel;
	}
	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}
	
}
