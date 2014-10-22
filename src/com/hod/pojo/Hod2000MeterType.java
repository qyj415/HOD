package com.hod.pojo;

import java.util.HashSet;
import java.util.Set;
// Generated Feb 27, 2013 9:09:57 AM by Hibernate Tools 3.2.2.GA



/**
 * Hod2000MeterType generated by hbm2java
 */
public class Hod2000MeterType  implements java.io.Serializable {


     private Integer typeIndex  ;
     private String typeName  ;
     private String typeCode  ;
     private String companyNum;
     private String companyName;
     private Integer communicationProtocol;
     private Set<Hod2000Meter> hod2000Meters   = new HashSet<Hod2000Meter>(0);

    public Hod2000MeterType() {
    }

	
    public Hod2000MeterType(Integer typeIndex) {
        this.typeIndex = typeIndex;
    }
    public Hod2000MeterType(Integer typeIndex, String typeName, String typeCode,String companyNum,String companyName,Integer communicationProtocol,Set<Hod2000Meter> hod2000Meters) {
       this.typeIndex = typeIndex;
       this.typeName = typeName;
       this.typeCode = typeCode;
       this.companyNum=companyNum;
       this.companyName=companyName;
       this.communicationProtocol=communicationProtocol;
       this.hod2000Meters = hod2000Meters;
    }
   
    public Integer getTypeIndex() {
        return this.typeIndex;
    }
    
    public void setTypeIndex(Integer typeIndex) {
        this.typeIndex = typeIndex;
    }
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeCode() {
        return this.typeCode;
    }
    
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }


	public String getCompanyNum() {
		return companyNum;
	}


	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}


	public Integer getCommunicationProtocol() {
		return communicationProtocol;
	}

	public void setCommunicationProtocol(Integer communicationProtocol) {
		this.communicationProtocol = communicationProtocol;
	}
	 public Set<Hod2000Meter> getHod2000Meters() {
        return this.hod2000Meters;
    }
    
    public void setHod2000Meters(Set<Hod2000Meter> hod2000Meters) {
        this.hod2000Meters = hod2000Meters;
    }

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}


