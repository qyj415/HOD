package com.hod.pojo;
// Generated Mar 29, 2013 3:52:39 PM by Hibernate Tools 3.2.2.GA


import java.util.Date;

/**
 * Hod2000Receive generated by hbm2java
 */
public class Hod2000Receive  implements java.io.Serializable {


     private Integer rid  ;
     private Integer roomId  ;
     private Double prMoney  ;
     private Double rmoney  ;
     private Double returnMoney  ;
     private Double fillMoney  ;
     private Integer rtype  ;
     private Date rdate  ;
     private String roperator;

    public Hod2000Receive() {
    }

	
    public Hod2000Receive(Integer rid, Integer roomId) {
        this.rid = rid;
        this.roomId = roomId;
    }
    public Hod2000Receive(Integer rid, Integer roomId, Double prMoney, Double rmoney, Double returnMoney, Double fillMoney, Integer rtype, Date rdate,String roperator) {
       this.rid = rid;
       this.roomId = roomId;
       this.prMoney = prMoney;
       this.rmoney = rmoney;
       this.returnMoney = returnMoney;
       this.fillMoney = fillMoney;
       this.rtype = rtype;
       this.rdate = rdate;
       this.roperator=roperator;
    }
   
    public Integer getRid() {
        return this.rid;
    }
    
    public void setRid(Integer rid) {
        this.rid = rid;
    }
   
    public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

    public Double getPrMoney() {
        return this.prMoney;
    }
    
    public void setPrMoney(Double prMoney) {
        this.prMoney = prMoney;
    }
    public Double getRmoney() {
        return this.rmoney;
    }
    
    public void setRmoney(Double rmoney) {
        this.rmoney = rmoney;
    }
    public Double getReturnMoney() {
        return this.returnMoney;
    }
    
    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }
    public Double getFillMoney() {
        return this.fillMoney;
    }
    
    public void setFillMoney(Double fillMoney) {
        this.fillMoney = fillMoney;
    }
    public Integer getRtype() {
        return this.rtype;
    }
    
    public void setRtype(Integer rtype) {
        this.rtype = rtype;
    }
    public Date getRdate() {
        return this.rdate;
    }
    
    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

	public String getRoperator() {
		return roperator;
	}

	public void setRoperator(String roperator) {
		this.roperator = roperator;
	}
}


