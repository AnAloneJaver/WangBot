package com.wangbot.service;

import com.wangbot.entitys.PicEntity;

/**
 * WangBotService接口
 * @author God
 */
public interface WangBotService {
    int savePic(PicEntity pic);

    PicEntity findPicByKeyword(String keyword);
}
