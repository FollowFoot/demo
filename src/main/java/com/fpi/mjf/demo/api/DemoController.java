package com.fpi.mjf.demo.api;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fpi.mjf.demo.entity.po.DemoEntity;
import com.fpi.mjf.demo.mapper.DemoMapper;
import com.fpi.mjf.demo.model.DemoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/test")
@Api(value="这是一个例子", description="仅供参考")
public class DemoController {
    @Autowired
    private DemoMapper mapper;
    
    @ApiOperation("测试插入")
    @RequestMapping(value = "/insert", method = {RequestMethod.GET})
    public String insert(DemoModel demo) {
        DemoEntity test = new DemoEntity();
        test.setName(demo.getName());
        test.setEnName(demo.getName());
        mapper.insert(test);
        return "Hello World! 你好" + demo.getName();
    }
    
    @ApiOperation("测试查询")
    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public List<DemoEntity> search(DemoModel demo) {
        if(StringUtils.isEmpty(demo.getName())) {
            return mapper.findAll();
        }
        return mapper.findByName(demo.getName());
    }
    
    @ApiOperation("测试删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public List<DemoEntity> delete(DemoModel demo) {
        //mapper.deleteByName(demo.getName());
        mapper.deleteBatch(Arrays.asList(new Integer[] {111,222,333}));
        return mapper.findAll();
    }
}
