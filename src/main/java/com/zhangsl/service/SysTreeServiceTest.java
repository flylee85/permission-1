package com.zhangsl.service;

import com.zhangsl.dto.DeptLevelDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional//事物回滚
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SysTreeServiceTest {

    private SysTreeService mSysTreeService;

    @Test
    public void deptTree() {
        List<DeptLevelDto> dtoList = mSysTreeService.deptTree();
        Assert.assertNotNull(dtoList);
    }
}