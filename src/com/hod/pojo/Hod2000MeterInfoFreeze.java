package com.hod.pojo;
// Generated Feb 27, 2013 9:09:57 AM by Hibernate Tools 3.2.2.GA


import java.util.Date;

/**
 * Hod2000MeterInfoFreeze generated by hbm2java
 */
public class Hod2000MeterInfoFreeze  implements java.io.Serializable {


     private Integer tid  ;
     private String meterName  ;
     private String clearEnergy  ;
     private String clearFlow  ;
     private Date readTime  ;
     private Date cleatDate;

    public Hod2000MeterInfoFreeze() {
    }

	
    public Hod2000MeterInfoFreeze(Integer tid) {
        this.tid = tid;
    }
    public Hod2000MeterInfoFreeze(Integer tid, String meterName, String clearEnergy, String clearFlow, Date readTime,Date cleatDate) {
       this.tid = tid;
       this.meterName = meterName;
       this.clearEnergy = clearEnergy;
       this.clearFlow = clearFlow;
       this.readTime = readTime;
       this.cleatDate=cleatDate;
    }
   
    public Integer getTid() {
        return this.tid;
    }
    
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public String getMeterName() {
        return this.meterName;
    }
    
    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }
    public String getClearEnergy() {
        return this.clearEnergy;
    }
    
    public void setClearEnergy(String clearEnergy) {
        this.clearEnergy = clearEnergy;
    }
    public String getClearFlow() {
        return this.clearFlow;
    }
    
    public void setClearFlow(String clearFlow) {
        this.clearFlow = clearFlow;
    }
    public Date getReadTime() {
        return this.readTime;
    }
    
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

	public Date getCleatDate() {
		return cleatDate;
	}

	public void setCleatDate(Date cleatDate) {
		this.cleatDate = cleatDate;
	}
    



}


