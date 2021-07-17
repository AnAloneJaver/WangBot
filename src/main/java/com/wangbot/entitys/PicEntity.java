package com.wangbot.entitys;


import lombok.Data;

/**
 * 图片实体
 * @author GodLike
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
     * 文件保存到服务器的名称
     */
   private String filename;
    /**
     * 文件的标识id
     */
   private String identification;
}
