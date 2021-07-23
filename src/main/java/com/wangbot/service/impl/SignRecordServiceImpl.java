package com.wangbot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.wangbot.dao.SignRecordDao;
import com.wangbot.entitys.SignRecord;
import com.wangbot.service.SignRecordService;
import com.wangbot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 签到记录实现类
 * @author God
 */
@Service
public class SignRecordServiceImpl implements SignRecordService {

    @Autowired
    private SignRecordDao signRecordDao;

    @Override
    public int countByMap(Map<String, Object> props) {
        return signRecordDao.countByMap(props);
    }

    /**
     * 今天是否签到过了，如果参数错误的话，直接提示今天签到过了
     * @param accountCode
     * @return
     */
    @Override
    public boolean signIned(String accountCode) {
        if(StrUtil.isBlank(accountCode)){
            return true;
        }
        return signRecordDao.signIned(accountCode,DateUtil.today()) == 1;
    }

    @Override
    public int signIn(String accountCode, String today) {
        SignRecord sr = new SignRecord();
        sr.setId(CommonUtil.getSnowFlakeId());
        sr.setAccountcode(accountCode);
        sr.setSigntime(today);
        return signRecordDao.save(sr);
    }
}
