package com.wangbot.service;

import java.util.Map;

/**
 * 签到记录Service
 * @author God
 */
public interface SignRecordService {
    int countByMap(Map<String, Object> props);

    boolean signIned(String accountCode);

    int signIn(String accountCode, String today);
}
