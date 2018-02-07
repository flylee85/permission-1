package com.zhangsl.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhangsl on 2018/2/6.
 */
@Getter
@Setter
public class JsonData {

    private boolean ret;

    private String msg;

    private Object data;

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    /**
     * 请求成功 统一返回数据和消息
     * @param data
     * @param msg
     * @return
     */
    public static JsonData success(Object data,String msg){

        JsonData jsonData = new JsonData(true);

        jsonData.setMsg(msg);

        jsonData.setData(data);

        return jsonData;
    }

    /**
     * 请求成功 统一返回数据(不含消息)
     * @param data
     * @return
     */
    public static JsonData success(Object data){

        JsonData jsonData = new JsonData(true);

        jsonData.setData(data);

        return jsonData;
    }

    /**
     * 请求成功(不含数据与消息)
     * @return
     */
    public static JsonData success(){

        JsonData jsonData = new JsonData(true);

        return jsonData;
    }

    /**
     * 请求失败(返回错误消息提示)
     * @param msg
     * @return
     */
    public static JsonData fail(String msg){

        JsonData jsonData = new JsonData(false);

        jsonData.setMsg(msg);

        return jsonData;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
