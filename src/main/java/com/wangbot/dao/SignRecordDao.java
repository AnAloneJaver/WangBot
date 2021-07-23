package com.wangbot.dao;


import com.wangbot.entitys.SignRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 *  签到记录Dao接口
 * @author God
 */
public interface SignRecordDao {
    /**
     * 根据条件查询个数
     * @param props
     * @return
     */
    int countByMap(@Param("props")Map<String, Object> props);

    /**
     * 多次签到
     * @param accountCode
     * @param today
     * @return
     */
    int signIned(@Param("accountCode") String accountCode, @Param("today") String today);


    /**
     * 第一次签到
     * @param signRecord
     * @return
     */
    int save(@Param("signRecord") SignRecord signRecord);
}
