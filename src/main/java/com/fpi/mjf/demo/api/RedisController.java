package com.fpi.mjf.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fpi.mjf.demo.entity.po.DemoEntity;
import com.fpi.mjf.demo.mapper.DemoMapper;
import com.fpi.mjf.demo.utils.restructure.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Redis测试", description = "Redis测试")
@RestController
@RequestMapping("/redis")
public class RedisController {
    
    @Autowired
    private DemoMapper mapper;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("使用stringRedisTemplate插入字符串缓存")
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insert(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return key + ":" + value;
    }
    
    @ApiOperation("使用stringRedisTemplate获取字符串缓存")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
    
    @ApiOperation("使用自定义RedisUtil插入字符串缓存")
    @RequestMapping(value = "/setString", method = RequestMethod.GET)
    public String setString(String key, String value) {
        RedisUtil.set(key, value);
        return key + ":" + value;
    }
    
    @ApiOperation("保存Object对象")
    @RequestMapping(value = "/setObject", method = RequestMethod.GET)
    public String insertObject(String name) {
        DemoEntity demo = mapper.findOneByName(name);
        RedisUtil.set(name, demo);
        return "success";
    }
}
