package com.wangbot.dao;

import com.wangbot.entitys.PicEntity;
import org.apache.ibatis.annotations.Param;

/**
 * WangBotDao 接口
 * @author God
 */
public interface WangBotDao {

    int save(PicEntity pic);

    PicEntity findPicByMd5(@Param("md5") String md5);

    PicEntity findPicByKeyword(@Param("keyword") String keyword);
}
