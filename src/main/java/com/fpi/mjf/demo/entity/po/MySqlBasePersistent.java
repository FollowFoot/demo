package com.fpi.mjf.demo.entity.po;

import java.io.Serializable;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;

//@MappedSuperclass
public class MySqlBasePersistent implements Serializable {
    
    private static final long serialVersionUID = 4460087841341870233L;
//    /** id */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Integer id;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
