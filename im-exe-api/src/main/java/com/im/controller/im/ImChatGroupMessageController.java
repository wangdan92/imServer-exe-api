package com.im.controller.im;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.im.ImChatGroupMessageService;
import com.im.ImChatGroupService;
import com.im.UserService;
import com.im.bean.BaseConfig;
import com.im.bean.MsgInfo;
import com.im.entity.ImChatGroup;
import com.im.entity.ImChatGroupMessage;
import com.im.entity.User;
import com.im.enums.ChatContentTypeEnum;
import com.im.enums.CommonEnum;
import com.im.util.DateTimeUtil;
import com.im.util.KeyWordUtil;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(tags = "群消息")
@RestController
@RequestMapping(value = "/imChatGroupMessage")
public class ImChatGroupMessageController {
    @Autowired
    private ImChatGroupMessageService imChatGroupMessageService;
    @Autowired
    private ImChatGroupService imChatGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseConfig baseConfig;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @ResponseBody
    @PostMapping(value = "/listImChatGroupMessage")
    public  Object listImChatGroupMessage(@RequestBody ImChatGroupMessage imChatGroupMessage){
        JSONObject result=new JSONObject();
        result.put("msg" , "成功");
        result.put("code",1 );
        try {
            List<ImChatGroupMessage> imChatGroupMessageList = imChatGroupMessageService.list(imChatGroupMessage);
            if(CollectionUtils.isNotEmpty(imChatGroupMessageList)){
                JSONArray jsonArray = new JSONArray();
                PageInfo<ImChatGroupMessage> pageInfo=new PageInfo<>(imChatGroupMessageList);
                for(ImChatGroupMessage tmpMsg:imChatGroupMessageList){
                    JSONObject returnObject = new JSONObject();
                    Integer pageNum = tmpMsg.getPageNum();
                    returnObject.put("pageNum",pageNum);
                    Integer pageSize = tmpMsg.getPageSize();
                    returnObject.put("pageSize",pageSize);
                    if(tmpMsg.getMessageType()!=null&&tmpMsg.getMessageType()==2){
                        returnObject.put("type","system");
                    }else {
                        returnObject.put("type","user");
                    }
                    User user = userService.getUserById(tmpMsg.getFromId());
                    if(null!=user.getPhotoPath()&&!user.getPhotoPath().equals("")){
                        String base64Str= Base64Utils.encodeToUrlSafeString(user.getPhotoPath().getBytes());
                        String url = baseConfig.getMultipartUrl()+"/getFileFromEncodeParam?encodePath="+base64Str;
                        user.setPhotoPath(url);
                    }else if(null==user.getPhotoPath()||user.getPhotoPath().equals("")){
                        String base64Str= Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                        String url = baseConfig.getMultipartUrl()+"/getFileFromEncodeParam?encodePath="+base64Str;
                        user.setPhotoPath(url);
                    }
                    JSONObject msgObject = new JSONObject();
                    msgObject.put("id",tmpMsg.getId());
                    String tmpType = "";

                    if(tmpMsg.getContentType().equals(ChatContentTypeEnum.TEXT.getCode())){
                        tmpType = "text";
                    }else if(tmpMsg.getContentType().equals(ChatContentTypeEnum.IMG.getCode())){
                        tmpType = "img";
                    }else if(tmpMsg.getContentType().equals(ChatContentTypeEnum.VOICE.getCode())){
                        tmpType = "voice";
                    }else if(tmpMsg.getContentType().equals(ChatContentTypeEnum.VIDEO.getCode())){
                        tmpType = "video";
                    }else if(tmpMsg.getContentType().equals(ChatContentTypeEnum.FILE.getCode())){
                        tmpType = "file";
                    }else{
                        tmpType="text";
                    }
                    msgObject.put("type",tmpType);
                    msgObject.put("time", DateTimeUtil.formatQQ(tmpMsg.getSendTime()));
                    msgObject.put("userinfo",user);
                    msgObject.put("chatGroupId",tmpMsg.getChatGroupId());
                    msgObject.put("content",JSONObject.parseObject(tmpMsg.getContent()));
                    returnObject.put("msg",msgObject);
                    jsonArray.add(returnObject);
                }
                result.put("data",jsonArray);
                result.put("pageInfo",pageInfo);
            }else{
                result.put("msg" , "失败");
                result.put("code", CommonEnum.FAIL.getCode());
            }
        }catch (Exception e){
            logger.error("群消息获取异常：{}", e);
            result.put("msg","群消息获取异常");
            result.put("code",CommonEnum.FAIL.getCode());
        }
        return result;
    }


    @ResponseBody
    @PostMapping(value = "/addImChatGroupMessage")
    public Object addImChatGroupMessage(@RequestBody ImChatGroupMessage imChatGroupMessage){
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try{
            if(imChatGroupMessageService.addImChatGroupMessage(imChatGroupMessage)>0){
                msgInfo.setMsg("群消息新增成功！");
            }else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群消息新增失败！");
            }
        }catch (Exception e){
            logger.error("群消息新增异常：{}", e);
            msgInfo.setMsg("群消息新增异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/delete/{id}")
    public Object delete(@PathVariable("id") int id){
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try{
            if(imChatGroupMessageService.delete(id)>0){
                msgInfo.setMsg("群消息删除成功！");
            }else{
                msgInfo.setCode(0);
                msgInfo.setMsg("群消息删除失败！");
            }
        }catch (Exception e){
            logger.error("群消息删除异常：{}", e);
            msgInfo.setMsg("群消息删除异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }


    @ResponseBody
    @PostMapping(value = "/getRefItemGroup")
    public Object getRefItemGroup(@RequestBody ImChatGroupMessage imChatGroupMessage){
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try{
           if(null != imChatGroupMessage && null != imChatGroupMessage.getId()){
               ImChatGroupMessage tmpMsg = imChatGroupMessageService.getImChatGroupMessageById(imChatGroupMessage.getId());
               if(null != tmpMsg && null != tmpMsg.getRefId()){
                   ImChatGroup imChatGroup = imChatGroupService.getImChatGroupByTaskId(tmpMsg.getRefId());
                   msgInfo.setData(imChatGroup);
                   msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
               }
           }
        }catch (Exception e){
            logger.error("群消息删除异常：{}", e);
            msgInfo.setMsg("群消息删除异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }


    /**
     * 校验发送信息内容是否包含敏感词,过滤
     * @param msg
     * @return
     */
    @ResponseBody
    @PostMapping(value="/validateMsg")
    public Object validateMsg(@RequestBody JSONObject msg){
        MsgInfo result=new MsgInfo(CommonEnum.FAIL);
        List<String> keywords=new ArrayList<>();
        try {
            String text = msg.getString("text");
            if (StringUtils.isBlank(text)){
                return result;
            }
            text=text.replaceAll(" ", "").replaceAll("\r\n","").replaceAll("\n","").replaceAll("\r","");
            Set<String> badWords = KeyWordUtil.getBadWord(text, 2);
            if (!badWords.isEmpty()){
                for (String badWord : badWords) {
                    keywords.add(badWord);
                }
                result.setCodeAndMsg(CommonEnum.SUCCESS);
                result.setData(keywords);
                return result;
            }
        }catch (Exception e){
            logger.info("检验信息是否包含敏感词触发异常",e);
            result.setCodeAndMsg(0,"检验信息是否包含敏感词触发异常");
        }
        return result;
    }




}
