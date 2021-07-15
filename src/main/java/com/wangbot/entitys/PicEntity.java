package com.wangbot.entitys;


import lombok.Data;

/**
 * 图片实体
 * @Author God
 */
@Data
public class PicEntity {

   private String id;
    /**
     * 文件的来源
     */
   private String fromcode;
    /**
     * 文件的来源群
     */
   private String groupcode;
    /**
     * 文件的md5
     */
   private String md5;
    /**
     * 文件的关键字
     */
   private String keyword;
    /**
     * 文件的保存地址
     */
   private String path;
}
