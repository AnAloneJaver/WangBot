package com.wangbot.linstener;

import catcode.CatCodeUtil;
import catcode.CodeBuilder;
import catcode.CodeTemplate;
import catcode.Neko;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.wangbot.entitys.PicEntity;
import com.wangbot.service.SignRecordService;
import com.wangbot.service.SignService;
import com.wangbot.service.PicService;
import com.wangbot.util.CommonUtil;
import com.wangbot.util.FileUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.*;

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
    private PicService picService;

    @Autowired
    private SignRecordService signRecordService;

    @Autowired
    private SignService signService;

    private final String HELP_INFO = "#h || #help || #帮助 \n 会显示WangBot的基本用法 \n #图片 上传 名称[图片] \n " +
            "#图片 类别 || 随机一张指定类别库的图片 \n #骰子 x <事件名称>|| 扔出 最大 X 的随机数 \n 返回值为: XXX 做 <事件名称> 的成功率为 x \n" +
            "#info || 查看机器人版本等信息";

    /**
     * 版本信息
     */
    private final String VERSION_INFO = "v1.0.0";

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
        sender.sendGroupMsg(groupMsg,"WangBot verion:"+VERSION_INFO);
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
    @Filter(value = "#图片上传", matchType = MatchType.STARTS_WITH)
    public void uploadPicListen(GroupMsg groupMsg, Sender sender) throws FileNotFoundException {
        MessageContent msgContent = groupMsg.getMsgContent();
        String msg = msgContent.getMsg();
        //拆分关键字
        String[] s = msg.split(" ");
        if(s.length < MSG_LENGTH){
            sender.sendGroupMsg(groupMsg,"参数不太对哦 汪~");
            return;
        }
        String keyword = StrUtil.isBlank(s[1]) ? s[2] : s[1];
        if(StrUtil.isBlank(keyword)){
            sender.sendGroupMsg(groupMsg,"参数不太对哦 汪~");
            return;
        }
        //去掉猫猫码
        keyword = keyword.substring(0,keyword.indexOf("\n"));
        //去掉\n
        keyword = keyword.replace("\\n","");
        //解析猫猫码
        PicEntity pic = new PicEntity();
        List<Neko> image = msgContent.getCats("image");
        String file = image.get(0).get("url");
        String identification = image.get(0).get("id");
        pic.setIdentification(identification);
        String hz = identification.substring(identification.lastIndexOf(".")+1);
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
        //查看当前这个人是否已经上传过该keyword的图片
        PicEntity pic1 = picService.findPicByKeyword(keyword,pic.getFromcode());
        if(pic1 != null && StrUtil.isNotBlank(pic1.getId())){
            int i = picService.delById(pic1.getId());
        }
        int res = picService.savePic(pic);
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
        CodeTemplate<String> template = util.getStringTemplate();
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String text = groupMsg.getMsg();
        String[] s = text.split(" ");
        if(s.length < MSG_LENGTH){
            sender.sendGroupMsg(groupMsg,at+"你要找什么图片呢？ 汪~");
            return;
        }
        String keyword = s[1];
        if(StrUtil.isBlank(keyword)) {
            keyword = s[s.length - 1];
        }
        if(StrUtil.isBlank(keyword)){
            sender.sendGroupMsg(groupMsg,at+"注意参数位置哦 汪~");
            return;
        }
        String accountCode = groupMsg.getAccountInfo().getAccountCode();
        PicEntity pic = picService.findPicByKeyword(keyword,accountCode);
        if(pic == null){
            sender.sendGroupMsg(groupMsg,at+"您未上传该类别图片 汪~");
            return;
        }
        String filepath = FileUtil.getFilePath(pic.getFilename());
        // 获取构建器
        CodeBuilder<String> builder = util.getStringCodeBuilder("image", true);

        // 构建器可以使构建的内容更加明了易懂
        try {
            String image = builder.key("id").value(pic.getIdentification())
                    .key("file").value(filepath)
                    .build();
            sender.sendGroupMsg(groupMsg,image);
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg,"图可能被咬坏了");
        }
    }

    /**
     * 连续签到7天的获得的积分数
     */
    public static final Integer SEVEN_INTEGRAL = 30;
    /**
     * 连续签到2天的获得的积分数
     */
    public static final Integer TWO_INTEGRAL = 20;
    /**
     * 基础获得的积分数
     */
    public static final Integer ONE_INTEGRAL = 10;

    /**
     * 签到监听
     *
     *  签到 计算方式
     *  条件                   计算方式
     *  昨天签到+今天签到        +20
     *  昨天未签到+今天签到      +10
     *  连续签到七天            +30
     * @param groupMsg
     * @param sender
     */
    @OnGroup
    @Filter(value = "#签到", matchType = MatchType.STARTS_WITH)
    public void SignInListen(GroupMsg groupMsg, Sender sender){
        String accountCode = groupMsg.getAccountInfo().getAccountCode();
        // 获取猫猫码工具
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        // 获取模板
        CodeTemplate<String> template = util.getStringTemplate();
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        if(signRecordService.signIned(accountCode)){
            String msg = "今天已经签过到了汪~";
            sender.sendGroupMsg(groupMsg,at+msg);
            return;
        }
        String s = CommonUtil.beforeSixDay();
        Map<String,Object> props = new HashMap<String,Object>(2);
        props.put("accountCode",accountCode);
        props.put("signtime",s);
        int res = signRecordService.countByMap(props);
        if(res == 6){
            res = signService.signIn(accountCode,SEVEN_INTEGRAL);
        }else {
            s = CommonUtil.yesterday();
            res = signRecordService.countByMap(props);
            if (res == 1) {
                res = signService.signIn(accountCode, TWO_INTEGRAL);
            } else {
                res = signService.signIn(accountCode, ONE_INTEGRAL);
            }
        }
        if(res > 0) {
            String msg = "签到成功，当前积分：" + res;
            sender.sendGroupMsg(groupMsg, at + msg);
            return;
        }else{
            String msg = "签到失败了，联系一下管理员吧！汪~";
            sender.sendGroupMsg(groupMsg, at + msg);
            return;
        }

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
