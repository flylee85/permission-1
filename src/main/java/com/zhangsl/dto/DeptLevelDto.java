package com.zhangsl.dto;

import com.google.common.collect.Lists;
import com.zhangsl.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by Zhangsl on 2018/2/9.
 */
@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    /**
     * 对象转换
     * @param sysDept
     * @return
     */
    public static DeptLevelDto adapt(SysDept sysDept){
        DeptLevelDto deptLevelDto = new DeptLevelDto();
        BeanUtils.copyProperties(sysDept,deptLevelDto);
        return deptLevelDto;
    }
}
