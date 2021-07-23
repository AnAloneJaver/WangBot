package com.wangbot.entitys;

import lombok.Data;

/**
 * 积分记录
 * @author God
 */
@Data
public class IntegralRecord {
    private String id;
    /**
     * QQ号
     */
    private String accountcode;
    /**
     * 积分
     */
    private Integer integral;
}
