package com.zhangsl.service;

import com.zhangsl.dao.SysDeptMapper;
import com.zhangsl.model.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Zhangsl on 2018/2/9.
 */
@Service
public class TestService {

    @Autowired
    public SysDeptMapper mSysDeptMapper;

    public SysDept findOne(Integer id){

        SysDept sysDept = mSysDeptMapper.selectByPrimaryKey(id);
        return sysDept;
    }

    public static void main(String[] args) {
        TestService testService = new TestService();
        SysDept sysDept = testService.findOne(1);
        System.out.println(sysDept);
    }
}
