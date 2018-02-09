package com.zhangsl.dao;

import com.zhangsl.model.SysDept;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Zhangsl on 2018/2/9.
 */
@Transactional//事物回滚
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SysDeptMapperTest {

    @Autowired
    private SysDeptMapper mSysDeptMapper;

    @Test
    public void deleteByPrimaryKey() throws Exception {

        int key = mSysDeptMapper.deleteByPrimaryKey(4);

        Assert.assertEquals(1,key);
    }

    @Test
    public void insert() throws Exception {
        SysDept sysDept = new SysDept();
        sysDept.setLevel("0");
        sysDept.setName("xsc");
        sysDept.setSeq(1);
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setParentId(1);
        sysDept.setRemark("哈哈哈");
        sysDept.setOperator("admin");
        sysDept.setOperateTime(new Date());
        int i = mSysDeptMapper.insert(sysDept);
        Assert.assertNotNull(i);
    }

    @Test
    public void insertSelective() throws Exception {
    }

    @Test
    public void selectByPrimaryKey() throws Exception {
        SysDept sysDept = mSysDeptMapper.selectByPrimaryKey(1);
        Assert.assertNotNull(sysDept);
    }

    @Test
    public void updateByPrimaryKeySelective() throws Exception {
    }

    @Test
    public void updateByPrimaryKey() throws Exception {
        SysDept sysDept = new SysDept();
        sysDept.setId(1);
        sysDept.setLevel("0");
        sysDept.setName("xsc");
        sysDept.setSeq(1);
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setParentId(1);
        sysDept.setRemark("哈哈哈");
        sysDept.setOperator("admin");
        sysDept.setOperateTime(new Date());
        int key = mSysDeptMapper.updateByPrimaryKey(sysDept);
    }

}