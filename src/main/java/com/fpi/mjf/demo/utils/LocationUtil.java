package com.fpi.mjf.demo.utils;

import java.text.MessageFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.fpi.mjf.demo.CommonConfig;

public class LocationUtil {
    
    private static RestTemplate template = new RestTemplate();
    
    //使用时初始化读取配置
    public static String heweather_api_uri = "";
    
    //使用时初始化读取配置
    public static String heweather_api_key = "";
   
    /**
     * 使用和风天气API根据经纬度获取城市名，输入自己的key
     * @return
     */
    public static String searchCityNameWithHeWeather(double longitude, double latitude, String key) {
        if(StringUtils.isEmpty(heweather_api_uri)) {
            heweather_api_uri = CommonConfig.getProperty("heweather.api.uri");
        }
//        https://free-api.heweather.com/s6/weather/forecast?location=116.40,41&key=9c9bc5a2fdfc4cf4b38d36694bef97c5
//        StringBuilder url = new StringBuilder(heweather_api_uri);
//        url.append("?");
//        url.append("location=").append(longitude).append(",").append(latitude).append("&key=").append(key);
        String url = MessageFormat.format(heweather_api_uri, longitude, latitude, key);
        //{"HeWeather6":[{"basic":{"cid":"CN101090408","location":"丰宁","parent_city":"承德","admin_area":"河北","cnty":"中国","lat":"41.20990372","lon":"116.65120697","tz":"+8.00"},"update":{"loc":"2018-09-29 09:45","utc":"2018-09-29 01:45"},"status":"ok","daily_forecast":[{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-09-29","hum":"73","mr":"20:37","ms":"10:01","pcpn":"0.0","pop":"0","pres":"1016","sr":"06:09","ss":"17:56","tmp_max":"16","tmp_min":"3","uv_index":"4","vis":"16","wind_deg":"-1","wind_dir":"无持续风向","wind_sc":"1-2","wind_spd":"7"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-09-30","hum":"68","mr":"21:19","ms":"11:08","pcpn":"0.0","pop":"0","pres":"1013","sr":"06:10","ss":"17:55","tmp_max":"17","tmp_min":"5","uv_index":"4","vis":"18","wind_deg":"344","wind_dir":"西北风","wind_sc":"5-6","wind_spd":"40"},{"cond_code_d":"101","cond_code_n":"100","cond_txt_d":"多云","cond_txt_n":"晴","date":"2018-10-01","hum":"69","mr":"22:07","ms":"12:12","pcpn":"1.0","pop":"55","pres":"1019","sr":"06:11","ss":"17:53","tmp_max":"21","tmp_min":"4","uv_index":"4","vis":"20","wind_deg":"348","wind_dir":"西北风","wind_sc":"3-4","wind_spd":"13"}]}]}
        JSONObject response = request(url);
        return response.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("basic").getString("parent_city");
    }
    
    /**
     * 使用和风天气API根据经纬度获取城市名
     * @return
     */
    public static String searchCityNameWithHeWeather(double longitude, double latitude) {
        if(StringUtils.isEmpty(heweather_api_key)) {
            heweather_api_key = CommonConfig.getProperty("heweather.api.key");
        }
        return searchCityNameWithHeWeather(longitude, latitude, heweather_api_key);
    }
    
    /**
     * 发送请求返回响应体
     * @param url
     * @return
     */
    public static JSONObject request(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        String responseBody = template.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        return JSONObject.parseObject(responseBody);
    }
}
