//package com.fpi.mjf.demo.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import com.fpi.mjf.demo.entity.po.PortMessage;
//import com.fpi.mjf.demo.entity.vo.PageData;
//import com.fpi.mjf.demo.repo.PortMessageRepository;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//
//@Api(value = "端口占用信息", description = "端口占用信息")
//@RestController
//@RequestMapping("/port-manager")
//public class PortManagerController {
//    
//    @Autowired
//    private PortMessageRepository service;
//    
//    @ApiOperation(value = "查询分页")
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ApiImplicitParams({
//        @ApiImplicitParam(value = "")
//    })
//    public PageData<PortMessage> list(int page, int size, String port) {
//        Pageable pageable = new PageRequest(page, size);
//        PageData<PortMessage> pageData = new PageData<PortMessage>();
//        pageData.setData(service.findByPortContaining(port, pageable));
//        pageData.setPageable(pageable);
//        return pageData;
//    }
//    
//    @ApiOperation("插入")
//    @RequestMapping(value = "/insert", method = {RequestMethod.GET})
//    public String insert(PortMessage entity) {
//        service.save(entity);
//        return "保存成功";
//    }
//}
