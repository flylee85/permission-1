package com.zhangsl.service;

import com.google.common.base.Preconditions;
import com.zhangsl.dao.SysDeptMapper;
import com.zhangsl.exception.ParamException;
import com.zhangsl.model.SysDept;
import com.zhangsl.param.DeptParam;
import com.zhangsl.util.BeanValidator;
import com.zhangsl.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by Zhangsl on 2018/2/9.
 */
@Service
public class SysDeptService {

    @Autowired
    private SysDeptMapper mSysDeptMapper;


    /**
     * 创建部门
     * @param param
     */
    public void save(DeptParam param){

        //表单校验
        BeanValidator.check(param);

        if (checkExist(param)) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        //组装入库数据
        SysDept sysDept = SysDept.builder()
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getId()),param.getParentId()));
        // TODO: 2018/2/9 操作者，操作ip待处理
        sysDept.setOperator("system");
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setOperateTime(new Date());
        //入库
        mSysDeptMapper.insertSelective(sysDept);
        // TODO: 2018/2/9 添加日志保存service记录操作日志

    }

    /***
     * 更新部门
     * @param param
     */
    public void update(DeptParam param){
        BeanValidator.check(param);
        if (checkExist(param)) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept before = mSysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");
        if(checkExist(param)) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept after = SysDept.builder()
                .id(param.getId())
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator("system-zhangsl");
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());

        updateWithChild(before,after);
        // TODO: 18-2-10 记录操作日志
    }

    @Transactional
    public void updateWithChild(SysDept before, SysDept after){
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptListByLevel = mSysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(deptListByLevel)) {
                for (SysDept dept : deptListByLevel) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                mSysDeptMapper.batchUpdateLevel(deptListByLevel);
            }
        }
        mSysDeptMapper.updateByPrimaryKey(after);
    }
    /**
     * 检查同级部门是否已存在
     * @param param
     * @return
     */
    private boolean checkExist(DeptParam param) {
        return mSysDeptMapper.countByNameAndParentId(param.getParentId(),param.getName(),param.getId()) > 0;
    }

    /**
     * 获取上级部门的level
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId){
        SysDept sysDept = mSysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept == null) {
            return null;
        }
        return sysDept.getLevel();
    }

}
