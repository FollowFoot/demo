package com.fpi.mjf.demo.api;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fpi.mjf.demo.utils.LocationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "逆地理信息接口", description = "逆地理信息接口")
@RestController
@RequestMapping("/geo-api")
public class LocationApiController {

    @RequestMapping(value = "/heweather", method = RequestMethod.GET)
    @ApiOperation("使用和风气象API")
    public String geoRefHeWeather(long longitude, long latitude, String key) {
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(key.trim())) {
            return LocationUtil.searchCityNameWithHeWeather(longitude, latitude);
        }else {
            return LocationUtil.searchCityNameWithHeWeather(longitude, latitude, key);
        }
    }
    
}
