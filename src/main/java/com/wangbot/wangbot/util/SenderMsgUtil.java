package com.wangbot.wangbot.util;

import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SenderMsgUtil {


    /**
     * 通过依赖注入获取一个 "消息正文构建器工厂"。
     *
     */
    private static  MessageContentBuilderFactory messageContentBuilderFactory;

    @Autowired
    public SenderMsgUtil(MessageContentBuilderFactory messageContentBuilderFactory) {
        SenderMsgUtil.messageContentBuilderFactory = messageContentBuilderFactory;
    }

    public static void sendPrivateMsg( Sender sender,String code,String msg){
        sender.sendPrivateMsg(code, msg);
    }

    /**
     * 发送图片
     * @param sender
     * @param code
     * @param name
     */
    public static void sendPrivatePic( Sender sender,String code,String name){
        System.out.println("send pic");
        MessageContent build = messageContentBuilderFactory.getMessageContentBuilder().image("https://pics3.baidu.com/feed/91ef76c6a7efce1b3f8ed8a1ac3f30d6b58f65ae.jpeg?token=d818a8f7b1ce627dfe46971794bd2837").build();
        String msg = build.getMsg();
        sender.sendPrivateMsg(code, msg);
    }


}
