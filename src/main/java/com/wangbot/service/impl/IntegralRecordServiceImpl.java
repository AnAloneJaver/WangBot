package com.wangbot.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wangbot.dao.IntegralRecordDao;
import com.wangbot.entitys.IntegralRecord;
import com.wangbot.service.IntegralRecordService;
import com.wangbot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 签到积分
 * @author God
 */
@Service
public class IntegralRecordServiceImpl implements IntegralRecordService {

    @Autowired
    private IntegralRecordDao integralRecordDao;


    /**
     * 累计积分
     * @param ir
     * @return
     */
    @Override
    public int addUpIntegral(IntegralRecord ir) {
        return integralRecordDao.update(ir);
    }

    /**
     * 第一次签到
     * @param ir
     * @return
     */
    @Override
    public int insert(IntegralRecord ir) {
        ir.setId(CommonUtil.getSnowFlakeId());
        return integralRecordDao.insert(ir);
    }

    /**
     * 查询单个的积分数
     *
     * @param accountCode
     * @return
     */
    @Override
    public IntegralRecord selectOneByAccountcode(String accountCode) {
        return integralRecordDao.selectOneByAccount(accountCode);
    }
}
