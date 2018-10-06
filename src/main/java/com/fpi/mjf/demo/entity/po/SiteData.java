package com.fpi.mjf.demo.entity.po;

import java.util.Date;

public class SiteData {
    private Integer id;
    
    private String area;
    
    private Date date;
    
    private Integer AQI;
    
    private Double v_106;
    
    private Double v_141;
    
    private Double v_108;
    
    private Double v_107;
    
    private Double v_121;
    
    private Double v_101;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAQI() {
        return AQI;
    }

    public void setAQI(Integer aQI) {
        AQI = aQI;
    }

    public Double getV_106() {
        return v_106;
    }

    public void setV_106(Double v_106) {
        this.v_106 = v_106;
    }

    public Double getV_141() {
        return v_141;
    }

    public void setV_141(Double v_141) {
        this.v_141 = v_141;
    }

    public Double getV_108() {
        return v_108;
    }

    public void setV_108(Double v_108) {
        this.v_108 = v_108;
    }

    public Double getV_107() {
        return v_107;
    }

    public void setV_107(Double v_107) {
        this.v_107 = v_107;
    }

    public Double getV_121() {
        return v_121;
    }

    public void setV_121(Double v_121) {
        this.v_121 = v_121;
    }

    public Double getV_101() {
        return v_101;
    }

    public void setV_101(Double v_101) {
        this.v_101 = v_101;
    }
    
    public Double getV(String key) {
        if("101".equals(key)) {
            return this.v_101;
        }
        if("121".equals(key)) {
            return this.v_121;
        }
        if("107".equals(key)) {
            return this.v_107;
        }
        if("108".equals(key)) {
            return this.v_108;
        }
        if("141".equals(key)) {
            return this.v_141;
        }
        if("106".equals(key)) {
            return Double.parseDouble(String.format("%.1f", this.v_106));
        }
        return null;
    }
}
