package com.wangbot.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * 文件工具类
 * @author God
 */
public class FileUtil {


    private FileUtil(){}

    private static String FILE_PATH_PREFIX = null;


    /**
     * 下载图片
     * @param url
     * @param fileNameSuffix
     * @return
     */
    public static JSONObject downloadPic(String url, String fileNameSuffix){
        JSONObject json = new JSONObject();
        try {
            String filePathPrefix = FileUtil.getFilePathPrefix();
            System.out.println(filePathPrefix);
            File file = new File(filePathPrefix + File.separator+"pic");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            String fileName = CommonUtil.getUUID() + "." + fileNameSuffix;
            String filePath = getFilePath(fileName);
            file = new File(filePath);
            long l = HttpUtil.downloadFile(url, file);
            if(l > 0){
                String md5 = DigestUtil.md5Hex(file);
                json.set("md5",md5);
                json.set("filename",fileName);
                return json;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;

    }


    /**
     * 获取文件保存地址前缀
     * @return
     */
    public static String getFilePathPrefix(){
        if(FILE_PATH_PREFIX == null ){
                FILE_PATH_PREFIX = File.separator+"usr"+File.separator+"local"+File.separator+"src"+File.separator+"wangbot"+File.separator+"file";
        }
        return FILE_PATH_PREFIX;
    }

    /**
     * 删除掉存在的图片
     * @param filename
     */
    public static void delPic(String filename) {
        String filePath = getFilePath(filename);
        File file = new File(filePath);
        if(file.exists()){
            cn.hutool.core.io.FileUtil.del(file);
        }
    }

    /**
     * 获取保存文件的地址
     * @param filename
     * @return
     */
    public static String getFilePath(String filename){
        return getFilePathPrefix()+ File.separator+ "pic"+File.separator+filename;
    }

}
