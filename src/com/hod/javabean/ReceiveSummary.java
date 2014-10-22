package com.hod.javabean;

public class ReceiveSummary {

	private int userNum;//已收费用户数
	private int unCollectUserNum;//未收费用户数
	private double returnMoney;//退款金额
	private double fillMoney;//补交金额
	private double preMoney;//预收款
	private double totalMoney;//应交总金额
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public int getUnCollectUserNum() {
		return unCollectUserNum;
	}
	public void setUnCollectUserNum(int unCollectUserNum) {
		this.unCollectUserNum = unCollectUserNum;
	}
	public double getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(double returnMoney) {
		this.returnMoney = returnMoney;
	}
	public double getFillMoney() {
		return fillMoney;
	}
	public void setFillMoney(double fillMoney) {
		this.fillMoney = fillMoney;
	}
	public double getPreMoney() {
		return preMoney;
	}
	public void setPreMoney(double preMoney) {
		this.preMoney = preMoney;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
}
