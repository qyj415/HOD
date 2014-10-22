package com.hod.pojo;
// Generated Feb 27, 2013 9:09:57 AM by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Hod2000Client generated by hbm2java
 */
public class Hod2000Client  implements java.io.Serializable {


     private Integer clientId  ;
     private String clientName  ;
     private String clientSex  ;
     private Integer clientCardType;
     private String clientIdentity  ;
     private String clientAddress  ;
     private String clientTel  ;
     private String clientRemark  ;
     private Integer clientEnable  ;
     private Date clientOpenTime;
     private Set<Hod2000Room> hod2000Rooms   = new HashSet<Hod2000Room>(0);

    public Hod2000Client() {
    }

	
    public Hod2000Client(Integer clientId) {
        this.clientId = clientId;
    }
    public Hod2000Client(Integer clientId, String clientName, String clientSex,Integer clientCardType, String clientIdentity, String clientAddress, String clientTel, String clientRemark, Integer clientEnable,Date clientOpenTime, Set<Hod2000Room> hod2000Rooms) {
       this.clientId = clientId;
       this.clientName = clientName;
       this.clientSex = clientSex;
       this.clientCardType=clientCardType;
       this.clientIdentity = clientIdentity;
       this.clientAddress = clientAddress;
       this.clientTel = clientTel;
       this.clientRemark = clientRemark;
       this.clientEnable = clientEnable;
       this.hod2000Rooms = hod2000Rooms;
       this.clientOpenTime=clientOpenTime;
    }
   
    public Integer getClientId() {
        return this.clientId;
    }
    
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    public String getClientName() {
        return this.clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getClientSex() {
        return this.clientSex;
    }
    
    public void setClientSex(String clientSex) {
        this.clientSex = clientSex;
    }
    public String getClientIdentity() {
        return this.clientIdentity;
    }
    
    public void setClientIdentity(String clientIdentity) {
        this.clientIdentity = clientIdentity;
    }
    public String getClientAddress() {
        return this.clientAddress;
    }
    
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
    public String getClientTel() {
        return this.clientTel;
    }
    
    public void setClientTel(String clientTel) {
        this.clientTel = clientTel;
    }
    public String getClientRemark() {
        return this.clientRemark;
    }
    
    public void setClientRemark(String clientRemark) {
        this.clientRemark = clientRemark;
    }
    public Integer getClientEnable() {
        return this.clientEnable;
    }
    
    public void setClientEnable(Integer clientEnable) {
        this.clientEnable = clientEnable;
    }
    public Set<Hod2000Room> getHod2000Rooms() {
        return this.hod2000Rooms;
    }
    
    public void setHod2000Rooms(Set<Hod2000Room> hod2000Rooms) {
        this.hod2000Rooms = hod2000Rooms;
    }

	public Integer getClientCardType() {
		return clientCardType;
	}

	public void setClientCardType(Integer clientCardType) {
		this.clientCardType = clientCardType;
	}

	public Date getClientOpenTime() {
		return clientOpenTime;
	}

	public void setClientOpenTime(Date clientOpenTime) {
		this.clientOpenTime = clientOpenTime;
	}
	
}

