package com.wangbot.dao;

import com.wangbot.entitys.PicEntity;
import org.apache.ibatis.annotations.Param;

/**
 * WangBotDao 接口
 * @author God
 */
public interface PicDao {


    /**
     * 保存图片
     * @param pic
     * @return
     */
    int save(PicEntity pic);

    /**
     * 根据md5和QQ查询图片
     * @param md5
     * @param accountcode
     * @return
     */
    PicEntity findPicByMd5(@Param("md5") String md5,@Param("accountcode") String accountcode);

    /**
     * 根据关键字和QQ查询图片
     * @param keyword
     * @param accountcode
     * @return
     */
    PicEntity findPicByKeyword(@Param("keyword") String keyword,@Param("accountcode") String accountcode);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int delById(String id);
}
