package com.wangbot.service;

import com.wangbot.entitys.IntegralRecord;

/**
 * 签到积分
 * @author God
 */
public interface IntegralRecordService {

    /**
     * 累计积分
     * @param ir
     * @return
     */
    int addUpIntegral(IntegralRecord ir);

    /**
     * 第一次签到
     * @param accountCode
     * @param integral
     * @return
     */
    int insert(IntegralRecord ir);

    /**
     * 查询单个的积分数
     * @param accountCode
     * @return
     */
    IntegralRecord selectOneByAccountcode(String accountCode);
}
