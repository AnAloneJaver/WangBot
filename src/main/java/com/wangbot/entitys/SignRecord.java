package com.wangbot.entitys;

import lombok.Data;

/**
 * 签到记录
 * @author God
 */
@Data
public class SignRecord {
    private String id;
    /**
     * QQ号
     */
    private String accountcode;
    /**
     * 签到时间
     */
    private String signtime;

}
