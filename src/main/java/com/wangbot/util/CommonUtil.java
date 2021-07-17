package com.wangbot.util;

import java.util.UUID;

/**
 * 通用型工具类
 * @author God
 */
public class CommonUtil {
    private CommonUtil(){}

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}