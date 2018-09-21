package com.fpi.mjf.demo.entity.po;

//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "port_message")
public class PortMessage extends MySqlBasePersistent {
    private static final long serialVersionUID = 5754033778449398182L;

    private String port;
    
    private String outeruri;
    
    private String inneruri;
    
    private String context;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getOuteruri() {
        return outeruri;
    }

    public void setOuteruri(String outeruri) {
        this.outeruri = outeruri;
    }

    public String getInneruri() {
        return inneruri;
    }

    public void setInneruri(String inneruri) {
        this.inneruri = inneruri;
    }
}
