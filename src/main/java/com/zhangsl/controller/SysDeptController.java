package com.zhangsl.controller;

import com.zhangsl.common.JsonData;
import com.zhangsl.dto.DeptLevelDto;
import com.zhangsl.param.DeptParam;
import com.zhangsl.service.SysDeptService;
import com.zhangsl.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Zhangsl on 2018/2/9.
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private SysDeptService mSysDeptService;

    @Autowired
    private SysTreeService mSysTreeService;


    /**
     * 创建部门
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        mSysDeptService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        mSysDeptService.update(param);
        return JsonData.success();
    }

    /**
     * 部门层级树json
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> dtoList = mSysTreeService.deptTree();
        return JsonData.success(dtoList);
    }
}
