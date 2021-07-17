package com.wangbot.linstener;

import catcode.CatCodeUtil;
import catcode.CodeBuilder;
import catcode.CodeTemplate;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.wangbot.entitys.PicEntity;
import com.wangbot.service.WangBotService;
import com.wangbot.util.FileUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

/**
 * WangBot Listener
 * @author God
 */
@Component
public class WangBotListen {

    /**
     * 通过依赖注入获取一个 "消息正文构建器工厂"。
     *
     */
    private final MessageContentBuilderFactory messageContentBuilderFactory;

    @Autowired
    public WangBotListen(MessageContentBuilderFactory messageContentBuilderFactory) {
        this.messageContentBuilderFactory = messageContentBuilderFactory;
    }


    private final Integer MSG_LENGTH = 2;

    @Autowired
    private WangBotService wangBotService;

    private final String HELP_INFO = "#h || #help || #帮助 \n 会显示WangBot的基本用法 \n #图片 上传 名称[图片] \n " +
            "#图片 类别 || 随机一张指定类别库的图片 \n #骰子 x <事件名称>|| 扔出 最大 X 的随机数 \n 返回值为: XXX 做 <事件名称> 的成功率为 x \n" +
            "#info || 查看机器人版本等信息";

    /**
     * 私聊监听
     *
     * 监听info
     */
    @OnGroup
    @Filter(value = "#i", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#I", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#info", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#INFO", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#信息", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#v", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#V", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#vsersion", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#VERSION", matchType = MatchType.STARTS_WITH)
    public void infoListen(GroupMsg groupMsg, Sender sender){
        sender.sendGroupMsg(groupMsg,"WangBot verion: v 1.0.0");
    }

    /**
     * 私聊监听
     *
     * 监听help
     */
    @OnGroup
    @Filter(value = "#h", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#help", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#H", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#HELP", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#帮助", matchType = MatchType.STARTS_WITH)
    public void helpListen(GroupMsg groupMsg, Sender sender){
        sender.sendGroupMsg(groupMsg,HELP_INFO);
    }
    @OnGroup
    @Filter(value = "#汪", matchType = MatchType.EQUALS)
    public void wangListen(GroupMsg groupMsg, Sender sender){
        sender.sendGroupMsg(groupMsg,"汪~");
    }

    @OnGroup
    @Filter(value = "#上传图片", matchType = MatchType.STARTS_WITH)
    public void uploadPicListen(GroupMsg groupMsg, Sender sender) throws FileNotFoundException {
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String text = groupMsg.getMsg();
        System.out.println(text);
        String[] s = text.split(" ");
        if(s.length < MSG_LENGTH){
            sender.sendGroupMsg(groupMsg,"参数不太对哦 汪~");
            return;
        }
        String keyword = StrUtil.isBlank(s[1]) ? s[2] : s[1];
        if(StrUtil.isBlank(keyword)){
            sender.sendGroupMsg(groupMsg,"参数不太对哦 汪~");
            return;
        }
        PicEntity pic = new PicEntity();
        String file = util.getParam(text, "image", "url");
        String identification = util.getParam(text, "image", "id");
        String hz = identification.substring(identification.lastIndexOf(".")+1);
        System.out.println(identification);
        pic.setIdentification(identification);
        JSONObject json  = FileUtil.downloadPic(file, hz);
        pic.setFromcode(groupMsg.getAccountInfo().getAccountCode());
        pic.setGroupcode(groupMsg.getGroupInfo().getGroupCode());
        pic.setKeyword(keyword);
        if(!json.isEmpty()){
            pic.setFilename(json.getStr("filename"));
            pic.setMd5(json.getStr("md5"));
        }else{
            sender.sendGroupMsg(groupMsg,"图片上传失败了汪~");
            return;
        }
        int res = wangBotService.savePic(pic);
        if(res > 0){
            sender.sendGroupMsg(groupMsg,"图片上传成功！汪~");
        }else{
            sender.sendGroupMsg(groupMsg,"图片上传失败! 汪~");
        }
    }


    @OnGroup
    @Filter(value = "#图片", matchType = MatchType.STARTS_WITH)
    public void picListen(GroupMsg groupMsg, Sender sender) throws FileNotFoundException {
        // 获取猫猫码工具
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String text = groupMsg.getMsg();
        String[] s = text.split(" ");
        if(s.length < MSG_LENGTH){
            sender.sendGroupMsg(groupMsg,"你要找什么图片呢？ 汪~");
            return;
        }
        String keyword = s[1];
        System.out.println(keyword);
        PicEntity pic = wangBotService.findPicByKeyword(keyword);
        System.out.println(pic.toString());
        String filepath = FileUtil.getFilePath(pic.getFilename());
        // 获取构建器
        CodeBuilder<String> builder = util.getStringCodeBuilder("image", true);

        // 构建器可以使构建的内容更加明了易懂
        String image = builder.key("id").value(pic.getIdentification())
                .key("file").value(filepath)
                .build();
        sender.sendGroupMsg(groupMsg,image);
    }

    @OnGroup
    @Filter(value = "#签到", matchType = MatchType.STARTS_WITH)
    public void SignInListen(GroupMsg groupMsg, Sender sender){
        // 获取猫猫码工具
        CatCodeUtil util = CatCodeUtil.INSTANCE;

        // 获取模板
        CodeTemplate<String> template = util.getStringTemplate();

        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String msg = "签到成功，当前积分"+getrandom(0,100);
        sender.sendGroupMsg(groupMsg,at+msg);
    }



    @OnGroup
    @Filter(value = "#骰子", matchType = MatchType.STARTS_WITH)
    @Filter(value = "#摇色子", matchType = MatchType.STARTS_WITH)
    public void randomNumListen(GroupMsg groupMsg, Sender sender){
        String[] s = groupMsg.getMsg().split(" ");
        if(s.length < MSG_LENGTH){
            return;
        }
        //事件
        String thing = s[1];
        //最小值和最大值
        Integer min = 0;
        Integer max = 0;
        try{
             min = Integer.valueOf(s[2]);
             max = Integer.valueOf(s[3]);
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg,"注意哦 参数要为数字汪！");
            return;
        }
        int random = getrandom(min, max);
        String msg = "你"+thing+"的成功率是"+random+"汪!";
        if(thing.contains("{r}") || thing.contains("{R}")){
            thing = thing.replace("{r}",random+"").replace("{R}",random+"");
            msg = "你"+thing+"汪!";
        }
        sender.sendGroupMsg(groupMsg,msg);
    }


    public static int getrandom(int start,int end) {
        int num=(int) (Math.random()*(end-start+1)+start);
        return num;
    }
}
