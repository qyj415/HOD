package com.hod.pojo;
// Generated Feb 27, 2013 9:09:57 AM by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Hod2000Community generated by hbm2java
 */
public class Hod2000Community  implements java.io.Serializable,Comparable {


     private Integer communityId  ;
     private String communityName  ;
     private String communityCode  ;
     private String communityRemark;
     private Set<Hod2000Building> hod2000Buildings   = new HashSet<Hod2000Building>(0);
     private Hod2000Village hod2000Village  = new Hod2000Village();

    public Hod2000Community() {
    }

	
    public Hod2000Community(Integer communityId, String communityName, String communityCode,String communityRemark) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.communityCode = communityCode;
        this.communityRemark = communityRemark;
    }
    public Hod2000Community(Integer communityId, String communityName, String communityCode,String communityRemark, Set<Hod2000Building> hod2000Buildings, Hod2000Village hod2000Village) {
       this.communityId = communityId;
       this.communityName = communityName;
       this.communityCode = communityCode;
       this.hod2000Buildings = hod2000Buildings;
       this.hod2000Village = hod2000Village;
       this.communityRemark=communityRemark;
    }
   
    public Integer getCommunityId() {
        return this.communityId;
    }
    
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }
    public String getCommunityName() {
        return this.communityName;
    }
    
    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
    public String getCommunityCode() {
        return this.communityCode;
    }
    
    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }
    public Set<Hod2000Building> getHod2000Buildings() {
        return this.hod2000Buildings;
    }
    
    public void setHod2000Buildings(Set<Hod2000Building> hod2000Buildings) {
        this.hod2000Buildings = hod2000Buildings;
    }
    public Hod2000Village getHod2000Village() {
        return this.hod2000Village;
    }
    
    public void setHod2000Village(Hod2000Village hod2000Village) {
        this.hod2000Village = hod2000Village;
    }

	public String getCommunityRemark() {
		return communityRemark;
	}

	public void setCommunityRemark(String communityRemark) {
		this.communityRemark = communityRemark;
	}

	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Hod2000Community o = (Hod2000Community)arg0;
		if(Integer.parseInt(this.getCommunityCode())>Integer.parseInt(o.getCommunityCode())){
			return -1;
		}else if(Integer.parseInt(this.getCommunityCode())<Integer.parseInt(o.getCommunityCode())){
			return 1;
		}else{
			return 0;
		}
	}



}


