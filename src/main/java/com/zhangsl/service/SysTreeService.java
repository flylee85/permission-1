package com.zhangsl.service;

import com.zhangsl.dao.SysDeptMapper;
import com.zhangsl.dto.DeptLevelDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.ArrayListMultimap;
import com.zhangsl.model.SysDept;
import com.zhangsl.util.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 层级树相关服务
 * Created by Zhangsl on 2018/2/9.
 */
@Service
public class SysTreeService {

    @Autowired
    private SysDeptMapper mSysDeptMapper;

    //部门层级树处理，controller最终调用
    public List<DeptLevelDto> deptTree(){

        //1.查询所有部门数据 deptList
        List<SysDept> deptList = mSysDeptMapper.getAllDept();
        //2.新建部门层级对外数据类型dtoList,遍历转换，交给 deptListToTree 处理
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept sysDept : deptList) {
            DeptLevelDto deptLevelDto = DeptLevelDto.adapt(sysDept);
            dtoList.add(deptLevelDto);
        }
        return deptListToTree(dtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList){

        //1. 参数非空校验,为空返回新建list
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        //2.新建 muitimap 数据结构存储 level -> [dept1, dept2,...] 类型数据 levelDeptMap
        Multimap<String,DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        //3.新建 rootList 部门对外层级list 用以返回结果处理
        List<DeptLevelDto> rootList = Lists.newArrayList();
        //4.遍历 deptLevelList 转换结果为 muitimap,level为顶级部门(0)则直接添加 ,
        for (DeptLevelDto deptLevelDto : deptLevelList) {
            levelDeptMap.put(deptLevelDto.getLevel(),deptLevelDto);
            if (LevelUtil.ROOT.equals(deptLevelDto.getLevel())) {
                rootList.add(deptLevelDto);
            }
        }
        //5.按照seq从小到大排序  使用Collections.sort
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        //6.递归生成树 transformDeptTree
        transformDeptTree(rootList,LevelUtil.ROOT,levelDeptMap);
        return deptLevelList;
    }

    //Level：0,0, all 0->0.1,0.2
    //level: 0.1
    //Level: 0.2
    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String,DeptLevelDto> levelDeptMap){

        for (int i = 0; i < deptLevelList.size(); i++) {
            //1. 遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            //2.处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //3.处理下一层
            List<DeptLevelDto> tempDeptList = ((List<DeptLevelDto>) levelDeptMap.get(nextLevel));
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(tempDeptList)) {
                //排序
                Collections.sort(tempDeptList,deptSeqComparator);
                //设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }

    }
    //部门排序
    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

}
