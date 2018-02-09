package com.zhangsl.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Zhangsl on 2018/2/9.
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public static final String ROOT = "0";


    /**
     * 计算部门层级
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String parentLevel,int parentId){

        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        }else {
            return StringUtils.join(parentLevel,SEPARATOR,parentId);
        }
    }
}
