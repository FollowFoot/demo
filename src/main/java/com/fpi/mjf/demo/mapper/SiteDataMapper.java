package com.fpi.mjf.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.fpi.mjf.demo.entity.po.SiteData;

public interface SiteDataMapper {
    @Select("select * from site_data where area = #{area}")
    List<SiteData> getSiteDataByArea(String area);
    
    @Update("update site_data set AQI = #{AQI} where id = #{id}")
    void updateAqi(SiteData siteData);
}
