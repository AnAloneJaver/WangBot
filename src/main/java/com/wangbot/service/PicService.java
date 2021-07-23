package com.wangbot.service;

import com.wangbot.entitys.PicEntity;

/**
 * WangBotService接口
 * @author God
 */
public interface PicService {

    /**
     * 保存图片
     * @param pic
     * @return
     */
    int savePic(PicEntity pic);

    /**
     * 根据关键字和QQ号找图片
     * @param keyword
     * @param accountcode
     * @return
     */
    PicEntity findPicByKeyword(String keyword,String accountcode);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int delById(String id);
}
