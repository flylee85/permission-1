package com.zhangsl.service;

import com.zhangsl.model.SysDept;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/2/9.
 */
@Transactional//事物回滚
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestServiceTest {

    @Autowired
    private TestService mTestService;


    @Test
    public void findOne() throws Exception {

        SysDept sysDept = mTestService.findOne(1);
        Assert.assertNotNull(sysDept);

    }
}