package com.hod.pojo;
// Generated Mar 29, 2013 3:52:39 PM by Hibernate Tools 3.2.2.GA



/**
 * Hod2000Sysparameter generated by hbm2java
 */
public class Hod2000Sysparameter  implements java.io.Serializable {


     private Integer pid  ;
     private String pname  ;
     private String pvalue;
     private Integer ptype;
     private String pdesc  ;

    public Hod2000Sysparameter() {
    }

	
    public Hod2000Sysparameter(Integer pid) {
        this.pid = pid;
    }
    public Hod2000Sysparameter(Integer pid, String pname, String pdesc,Integer ptype,String pvalue) {
       this.pid = pid;
       this.pname = pname;
       this.pdesc = pdesc;
       this.ptype=ptype;
       this.pvalue=pvalue;
    }
   
    public Integer getPid() {
        return this.pid;
    }
    
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public String getPname() {
        return this.pname;
    }
    
    public void setPname(String pname) {
        this.pname = pname;
    }
    public String getPdesc() {
        return this.pdesc;
    }
    
    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

	public Integer getPtype() {
		return ptype;
	}
	
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public String getPvalue() {
		return pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
    



}


