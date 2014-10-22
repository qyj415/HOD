package com.hod.javabean;

public class PreReceive {

	private int userNumber;//已收款用户数
	private int unCollectUserNumber;//未收款用户数
	private Double moneyTotal;//已收款金额
	private Double unCollectMoneyTotal;//未收款金额
	private int toZero;//置零用户数
	public int getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}
	public int getUnCollectUserNumber() {
		return unCollectUserNumber;
	}
	public void setUnCollectUserNumber(int unCollectUserNumber) {
		this.unCollectUserNumber = unCollectUserNumber;
	}
	public double getMoneyTotal() {
		return moneyTotal;
	}
	public void setMoneyTotal(double moneyTotal) {
		this.moneyTotal = moneyTotal;
	}
	public double getUnCollectMoneyTotal() {
		return unCollectMoneyTotal;
	}
	public void setUnCollectMoneyTotal(double unCollectMoneyTotal) {
		this.unCollectMoneyTotal = unCollectMoneyTotal;
	}
	public int getToZero() {
		return toZero;
	}
	public void setToZero(int toZero) {
		this.toZero = toZero;
	}
	
}
