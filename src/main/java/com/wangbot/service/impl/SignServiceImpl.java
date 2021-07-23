package com.wangbot.service.impl;

import cn.hutool.core.date.DateUtil;
import com.wangbot.entitys.IntegralRecord;
import com.wangbot.service.IntegralRecordService;
import com.wangbot.service.SignRecordService;
import com.wangbot.service.SignService;
import com.wangbot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 签到Service的实现类
 * @author God
 */
@Service
public class SignServiceImpl implements SignService {

    @Autowired
    private SignRecordService signRecordService;

    @Autowired
    private IntegralRecordService integralRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int signIn(String accountCode,Integer integral) {
        //先插入到记录表
        int res = signRecordService.signIn(accountCode, DateUtil.today());
        //插入到积分表
        IntegralRecord ir = integralRecordService.selectOneByAccountcode(accountCode);
        if(res == 1) {
            if (ir != null ) {
                ir.setIntegral(ir.getIntegral() +integral);
                res = integralRecordService.addUpIntegral(ir);
            } else {
                ir = new IntegralRecord();
                ir.setAccountcode(accountCode);
                ir.setIntegral(integral);
                res = integralRecordService.insert(ir);
            }
        }else{
            return 0;
        }
        return ir.getIntegral();
    }
}
