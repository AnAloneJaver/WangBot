package com.wangbot.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wangbot.dao.PicDao;
import com.wangbot.entitys.PicEntity;
import com.wangbot.service.PicService;
import com.wangbot.util.CommonUtil;
import com.wangbot.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WangBotService 接口实现类
 * @author God
 */
@Service
public class PicServiceImpl implements PicService {

    @Autowired
    private PicDao picDao;

    @Override
    public int savePic(PicEntity pic) {
        //检查当前文件的MD5 是否已经存在
        PicEntity pic1 = picDao.findPicByMd5(pic.getMd5(),pic.getFromcode());
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
        pic.setId(CommonUtil.getSnowFlakeId());
        return picDao.save(pic);
    }

    /**
     * 根据关键字和QQ号查询图片
     * @param keyword
     * @param accountcode
     * @return
     */
    @Override
    public PicEntity findPicByKeyword(String keyword,String accountcode) {
        return picDao.findPicByKeyword(keyword,accountcode);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public int delById(String id) {
        if(StrUtil.isBlank(id)){
            return 0;
        }
        return picDao.delById(id);
    }
}
