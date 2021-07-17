package com.wangbot.service.impl;

import com.wangbot.dao.WangBotDao;
import com.wangbot.entitys.PicEntity;
import com.wangbot.service.WangBotService;
import com.wangbot.util.CommonUtil;
import com.wangbot.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WangBotService 接口实现类
 * @author God
 */
@Service
public class WangBotServiceImpl implements WangBotService {

    @Autowired
    WangBotDao wangBotDao;

    @Override
    public int savePic(PicEntity pic) {
        //检查当前文件的MD5 是否已经存在
        PicEntity pic1 = wangBotDao.findPicByMd5(pic.getMd5());
        if(pic1 != null){
            /**
             * 此图片已经上传过了
             * 删掉当前上传的图片
             * 此操作保证了服务器上保存的图片不会重复
             */
            FileUtil.delPic(pic.getFilename());
            pic.setFilename(pic1.getFilename());
            pic.setMd5(pic1.getMd5());
        }
        pic.setId(CommonUtil.getUUID());
        return wangBotDao.save(pic);
    }

    @Override
    public PicEntity findPicByKeyword(String keyword) {
        return wangBotDao.findPicByKeyword(keyword);
    }
}
