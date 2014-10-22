package com.hod.javabean;

public class Recevie {

	private String roomName;
	private Double roomSize;
	private String meterName;
	private Double pdPower;//耗热量
	private Double startPower;//供暖开始累计热量
	private Double endPower;//供暖结束累计热量
	private String priceType;//价格方案
	private Double moneyToPay;//应交金额
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Double getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(Double roomSize) {
		this.roomSize = roomSize;
	}
	public String getMeterName() {
		return meterName;
	}
	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}
	public Double getPdPower() {
		return pdPower;
	}
	public void setPdPower(Double pdPower) {
		this.pdPower = pdPower;
	}
	public Double getStartPower() {
		return startPower;
	}
	public void setStartPower(Double startPower) {
		this.startPower = startPower;
	}
	public Double getEndPower() {
		return endPower;
	}
	public void setEndPower(Double endPower) {
		this.endPower = endPower;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public Double getMoneyToPay() {
		return moneyToPay;
	}
	public void setMoneyToPay(Double moneyToPay) {
		this.moneyToPay = moneyToPay;
	}
	
}
