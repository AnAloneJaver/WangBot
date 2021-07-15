package com.wangbot.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * 文件工具类
 * @author God
 */
public class FileUtil {


    private FileUtil(){}

    private static String FILE_PATH_PREFIX = null;

    public static String downloadFile(String url) throws FileNotFoundException {
        String filePathPrefix = FileUtil.getFilePathPrefix();
        //TODO
        return filePathPrefix;

    }


    /**
     * 获取文件保存地址前缀
     * @return
     */
    public static String getFilePathPrefix(){
        if(FILE_PATH_PREFIX == null ){
            try {
                FILE_PATH_PREFIX = new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent();
            }catch (Exception e){
                FILE_PATH_PREFIX = "E:\\";
            }
        }
        return FILE_PATH_PREFIX;
    }


    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
       return UUID.randomUUID().toString().replace("-","");
    }
}
