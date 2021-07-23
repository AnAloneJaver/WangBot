package com.wangbot.dao;

import com.wangbot.entitys.IntegralRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 签到积分记录Dao接口
 * @author God
 */
public interface IntegralRecordDao {
    /**
     * 多次签到
     * @param ir
     * @return
     */
    int update(@Param("ir") IntegralRecord ir);

    /**
     * 第一次签到
     * @param ir
     * @return
     */
    int insert(@Param("ir") IntegralRecord ir);

    /**
     * 查询单个的积分记录
     * @param accountCode
     * @return
     */
    IntegralRecord selectOneByAccount(String accountCode);
}
