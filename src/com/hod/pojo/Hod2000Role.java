package com.hod.pojo;
// Generated Feb 27, 2013 9:09:57 AM by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Hod2000Role generated by hbm2java
 */
public class Hod2000Role  implements java.io.Serializable {


     private Integer rid  ;
     private String rname  ;
     private String rpurview  ;
     private Set<Hod2000Operator> hod2000Operators   = new HashSet<Hod2000Operator>(0);

    public Hod2000Role() {
    }

	
    public Hod2000Role(Integer rid) {
        this.rid = rid;
    }
    public Hod2000Role(Integer rid, String rname, String rpurview, Set<Hod2000Operator> hod2000Operators) {
       this.rid = rid;
       this.rname = rname;
       this.rpurview = rpurview;
       this.hod2000Operators = hod2000Operators;
    }
   
    public Integer getRid() {
        return this.rid;
    }
    
    public void setRid(Integer rid) {
        this.rid = rid;
    }
    public String getRname() {
        return this.rname;
    }
    
    public void setRname(String rname) {
        this.rname = rname;
    }
    public String getRpurview() {
        return this.rpurview;
    }
    
    public void setRpurview(String rpurview) {
        this.rpurview = rpurview;
    }
    public Set<Hod2000Operator> getHod2000Operators() {
        return this.hod2000Operators;
    }
    
    public void setHod2000Operators(Set<Hod2000Operator> hod2000Operators) {
        this.hod2000Operators = hod2000Operators;
    }




}


