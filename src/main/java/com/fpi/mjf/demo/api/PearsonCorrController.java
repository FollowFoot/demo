package com.fpi.mjf.demo.api;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fpi.mjf.demo.utils.PearsonCorrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pearson-corr")
@Api(value = "皮尔逊相关系数", description = "皮尔逊相关系数")
public class PearsonCorrController {
    
    @RequestMapping(value = "/corr", method = RequestMethod.GET)
    @ApiOperation("计算皮尔逊相关系数，x和y是一系列数据，用逗号隔开,eg：1,2,3,4,5")
    public Double corr(String x, String y) {
        String[] xs = x.trim().split(",");
        String[] ys = y.trim().split(",");
        List<Double> xd = new ArrayList<Double>();
        List<Double> yd = new ArrayList<Double>();
        for(String x_ : xs) {
            xd.add(Double.parseDouble(x_));
        }
        for(String y_ : ys) {
            yd.add(Double.parseDouble(y_));
        }
        return PearsonCorrUtil.getCorr(xd, yd);
    }
}
