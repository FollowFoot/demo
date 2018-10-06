package com.fpi.mjf.demo.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fpi.mjf.demo.entity.po.DemoEntity;
import com.fpi.mjf.demo.entity.po.SiteData;
import com.fpi.mjf.demo.mapper.DemoMapper;
import com.fpi.mjf.demo.mapper.SiteDataMapper;
import com.fpi.mjf.demo.model.DemoModel;
import com.fpi.mjf.demo.tools.AQITools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/test")
@Api(value="这是一个例子", description="仅供参考")
public class DemoController {
    @Autowired
    private DemoMapper mapper;
    
    @Autowired
    private SiteDataMapper siteDataMapper;
    
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
    
    @ApiOperation("测试删除")
    @RequestMapping(value = "/update", method = {RequestMethod.GET})
    public Map<String, Object> updateSiteData(String area) {
        String[] areas = new String[] {"菏泽学院", "市气象局", "市政协"};
        List<List<SiteData>> datas = new ArrayList<List<SiteData>>();
        for(int i = 0; i < areas.length; i++) {
            datas.add(siteDataMapper.getSiteDataByArea(areas[i]));
        }
        String[] factors = new String[] {"106", "141", "108", "107", "121", "101"};
        Double[][] d1 = new Double[31 * areas.length * factors.length][3];
        Integer[][] d2 = new Integer[31 * areas.length* factors.length][3];
        int index = 0;
        for(int n = 0; n < 18; n++) {
            for(int m = 0; m < 31; m++) {
                String factor = factors[n % 6];
                Double v = datas.get(n / 6).get(m).getV(factor);
                Integer iaqi = AQITools.calIaqi(factor, v, false);
                d1[index][0] = 1D * n;
                d1[index][1] = 1D * m;
                d1[index][2] = v;
                d2[index][0] = n;
                d2[index][1] = m;
                d2[index][2] = iaqi;
                index ++;
            }
        }
//        int m = 0;
//        int n = 0;
//        for(int i = 0; i < areas.length; i ++) {
//            List<SiteData> siteDatas = siteDataMapper.getSiteDataByArea(areas[i]);
//            for(int j = 0; j < 31; j++) {
//                SiteData siteData = siteDatas.get(j);
//                for(int k = 0; k < 6; k++) {
//                    Double v = siteData.getV(factors[k]);
//                    d1[m][0] = 1.0 * m % 31;
//                    d2[m][0] = m % 31;
//                    d1[m][1] = 1.0 * n % 18;
//                    d2[m][1] = n % 18;
//                    d1[m][2] = v;
//                    d2[m][2] = AQITools.calIaqi(factors[k], v, false);
//                    m ++;
//                    n ++;
//                }
//            }
//        }
        Map<String, Object> map = new HashMap<String, Object>(); 
        map.put("d1", d1);
        map.put("d2", d2);
        return map;
    }
}
