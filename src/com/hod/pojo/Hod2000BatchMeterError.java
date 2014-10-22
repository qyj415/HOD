package com.hod.pojo;


public class Hod2000BatchMeterError implements java.io.Serializable{
	
	private Integer batchMeterId  ;
	private String errorMsg  ;
    private String meterName  ;
    private String meterCaliber  ;
    private String meterBaudrate  ;
    private String typeName  ;
    private String meterPosition  ;
    private String meterParent;
    
    public Hod2000BatchMeterError() {
    }
	
    public Hod2000BatchMeterError(Integer batchMeterId) {
        this.batchMeterId = batchMeterId;
    }
    
	public Integer getBatchMeterId() {
		return batchMeterId;
	}
	public void setBatchMeterId(Integer batchMeterId) {
		this.batchMeterId = batchMeterId;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getMeterName() {
		return meterName;
	}
	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}
	public String getMeterCaliber() {
		return meterCaliber;
	}
	public void setMeterCaliber(String meterCaliber) {
		this.meterCaliber = meterCaliber;
	}
	public String getMeterBaudrate() {
		return meterBaudrate;
	}
	public void setMeterBaudrate(String meterBaudrate) {
		this.meterBaudrate = meterBaudrate;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getMeterPosition() {
		return meterPosition;
	}
	public void setMeterPosition(String meterPosition) {
		this.meterPosition = meterPosition;
	}
	public String getMeterParent() {
		return meterParent;
	}
	public void setMeterParent(String meterParent) {
		this.meterParent = meterParent;
	}
    
    
    
}
