package com.wangbot.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;

import java.util.Date;
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


    /**
     * 获取雪花id
     * @return
     */
    public static String getSnowFlakeId(){
        SnowflakeIdWorker idWorker = SnowflakeIdWorker.getSnowflakerIdWorker();
        return Long.valueOf(idWorker.nextId()).toString();
    }


    /**
     *  今天是否为周日
     * @return
     */
    public static boolean todayIsSunday(){
        Date today = new Date();
        int i = DateUtil.dayOfWeek(today);
        return i == 1;
    }

    /**
     * 获取六天前的
     * @return
     */
    public static String beforeSixDay(){
        DateTime dateTime = DateUtil.offsetDay(new Date(), -6);
        return dateTime.toString("yyyy-MM-dd");
    }

    /**
     * 昨天的日期
     * @return
     */
    public static String yesterday() {
        DateTime dateTime = DateUtil.yesterday();
        return dateTime.toString("yyyy-MM-dd");
    }
}