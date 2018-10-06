package com.fpi.mjf.demo.entity.po;

//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "test")
public class DemoEntity extends MySqlBasePersistent implements MongoDBPersistent{

    /**
     * 
     */
    private static final long serialVersionUID = -1462388025681814810L;
    
    private String name;
    
    private String enName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getEnName() {
        return enName;
    }
    
    public void setEnName(String enName) {
        this.enName = enName;
    }
}
