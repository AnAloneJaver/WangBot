package com.wangbot.wangbot.linstener;

import catcode.CatCodeUtil;
import cn.hutool.core.util.StrUtil;
import com.wangbot.wangbot.util.SenderMsgUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.annotation.OnPrivate;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MyPrivateListen {

    /**
     * 通过依赖注入获取一个 "消息正文构建器工厂"。
     *
     */
    private final MessageContentBuilderFactory messageContentBuilderFactory;

    @Autowired
    public MyPrivateListen(MessageContentBuilderFactory messageContentBuilderFactory) {
        this.messageContentBuilderFactory = messageContentBuilderFactory;
    }


    private final String HELP_INFO = "#h || #help || #帮助 \n 会显示WangBot的基本用法 \n #图片 上传 名称[图片] \n " +
            "#图片 类别 || 随机一张指定类别库的图片 \n #骰子 x <事件名称>|| 扔出 最大 X 的随机数 \n 返回值为: XXX 做 <事件名称> 的成功率为 x \n" +
            "#info || 查看机器人版本等信息";

    /**
     * 私聊监听
     */
    @OnPrivate
    @Filter(value = "#", matchType = MatchType.STARTS_WITH)
    public void replyPrivateMsg1(PrivateMsg privateMsg, Sender sender){
        // 获取消息正文。
        MessageContent msgContent = privateMsg.getMsgContent();
        String msg1 = msgContent.getMsg();
        System.out.println(msg1);
        String[] helpArr = {"h","help","帮助"};
        List<String> helpList = Arrays.asList(helpArr);
        if(StrUtil.isNotBlank(msg1)){
            msg1 = msg1.replace("#","").trim().toLowerCase();
            if(helpList.contains(msg1)){
                SenderMsgUtil.sendPrivateMsg(sender,privateMsg.getAccountInfo().getAccountCode(),HELP_INFO);
            }else if("info".equals(msg1)){
                SenderMsgUtil.sendPrivateMsg(sender,privateMsg.getAccountInfo().getAccountCode(),"WangBot serion: v 1.0.0");
            }else if(msg1.startsWith("图片")){
                dealWithPic(msg1,privateMsg,sender);
            }
        }
    }


    public void dealWithPic(String msg,PrivateMsg privateMsg, Sender sender){
        if("上传".indexOf(msg) >= 0 ){
            //拆分出图片信息
            //TODO
            SenderMsgUtil.sendPrivateMsg(sender,privateMsg.getAccountInfo().getAccountCode(),"上传成功");
        }else{
            //查找图片
            SenderMsgUtil.sendPrivatePic(sender,privateMsg.getAccountInfo().getAccountCode(),"");
        }
    }
}
