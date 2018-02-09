package com.zhangsl.controller;

import com.zhangsl.exception.PermissionException;
import com.zhangsl.model.SysDept;
import com.zhangsl.service.TestService;
import com.zhangsl.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Zhangsl on 2018/2/3.
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    TestService mTestService;

    @RequestMapping("/hello.json")
    @ResponseBody
    public String hello() {
        log.info("hello");
        SysDept sysDept = mTestService.findOne(1);
        return JsonMapper.obj2String(sysDept);
        // return JsonData.success("hello, permission");
        //return "hello permisson";
    }
}
