package com.wangbot.service;

/**
 * 签到Service
 * @author God
 */
public interface SignService {

    /**
     * 签到  返回值为积分数
     * @param accountCode
     * @param integral
     * @return
     */
    int signIn(String accountCode,Integer integral);
}
