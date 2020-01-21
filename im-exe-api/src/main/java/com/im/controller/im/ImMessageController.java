package com.im.controller.im;


import com.alibaba.fastjson.JSONObject;
import com.im.ImChatGroupUserService;
import com.im.ImMessageService;
import com.im.UserService;
import com.im.bean.BaseConfig;
import com.im.bean.MsgInfo;
import com.im.constant.Constants;
import com.im.entity.ImMessage;
import com.im.entity.User;
import com.im.enums.ChatContentTypeEnum;
import com.im.enums.ChatGroupTypeEnum;
import com.im.enums.CommonEnum;
import com.im.util.DateTimeUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "消息列表")
@RestController
@RequestMapping(value = "/immessage")
public class ImMessageController {
    @Autowired
    private ImMessageService imMessageService;
    @Autowired
    private ImChatGroupUserService imChatGroupUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseConfig baseConfig;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @PostMapping(value = "/listAllByUserId")
    public Object listAllByUserId(@RequestBody JSONObject object) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            List<ImMessage> groupList = new ArrayList<ImMessage>();
            Map<String, Object> map = new HashMap<>();
            map.put("userId", object.getIntValue("userId"));
            map.put("groupName", object.getString("groupName"));
            List<ImMessage> imMessageList = imMessageService.listAllByUserId(map);
            for (ImMessage imMessage : imMessageList) {
                ImMessage updateMessage=resetImMessage(imMessage);
                groupList.add(updateMessage);
            }
            msgInfo.setData(groupList);
        } catch (Exception e) {
            logger.error("消息列表获取异常：{}", e);
            msgInfo.setMsg("消息列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }
   /**
    * 设置对象属性
    * */
    private ImMessage resetImMessage(ImMessage imMessage) {
        Date tmpDate = null;
        if (null != imMessage.getUpdateTime()) {
            tmpDate = imMessage.getUpdateTime();
        } else {
            tmpDate = imMessage.getCreateTime();
        }
        String dateString = DateTimeUtil.formatQQ(tmpDate);
        imMessage.setUpdateTimeStr(dateString);
        Integer messageType = imMessage.getMessageType();
        Short contentType = 1;
        if (null != imMessage.getContentType()) {
            contentType = Short.valueOf(imMessage.getContentType().toString());
        }
        String content = imMessage.getContent();
        String sendName = imMessage.getSendName();
        if (null == sendName) {
            sendName = "";
        }
        if (content != null) {
            String sendContext = "";
            if (contentType.equals(ChatContentTypeEnum.TEXT.getCode())) {
                JSONObject contentObj = JSONObject.parseObject(content);
                String text = contentObj.getString("text") == null ? "" : contentObj.getString("text");
                sendContext = text;
            } else if (contentType.equals(ChatContentTypeEnum.IMG.getCode())) {
                sendContext = "[图片]";
            } else if (contentType.equals(ChatContentTypeEnum.VOICE.getCode())) {
                sendContext = "[音频]";
            } else if (contentType.equals(ChatContentTypeEnum.VIDEO.getCode())) {
                sendContext = "[视频]";
            } else if (contentType.equals(ChatContentTypeEnum.FILE.getCode())) {
                sendContext = "[文件]";
            }
            imMessage.setContent(sendContext);
        } else {
            imMessage.setContent("");
        }
        if (imMessage.getGroupType().equals(ChatGroupTypeEnum.ORDINARYGROUPCHAT.getCode())) {
            String base64Str = Base64Utils.encodeToUrlSafeString("G-1.png".getBytes());
            String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
            imMessage.setUrl(url);
        } else if (imMessage.getGroupType().equals(ChatGroupTypeEnum.TASKGROUPCHAT.getCode())) {
            String base64Str = Base64Utils.encodeToUrlSafeString("G-2.png".getBytes());
            String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
            imMessage.setUrl(url);
        } else {
            if (null != imMessage.getUrl() && !imMessage.getUrl().equals("")) {
                String base64Str = Base64Utils.encodeToUrlSafeString(imMessage.getUrl().getBytes());
                String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                imMessage.setUrl(url);
            } else if (null == imMessage.getUrl() || imMessage.getUrl().equals("")) {
                String base64Str = Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                imMessage.setUrl(url);
            }
        }
        return imMessage;
    }


    /**
     * 创建新的群组后查询新的群组会话信息
     * 根据群组id和用户id查询当前群组会话信息
     * @param object
     *                  chatGroupId
     *                  userId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/getGroupInfoById")
    public MsgInfo getChatGroupMessageInfoById(@RequestBody JSONObject object){
        MsgInfo result =new MsgInfo(CommonEnum.FAIL);
        try {
            Long userId = object.getLong("userId");
            Long chatGroupId = object.getLong("chatGroupId");
            Integer groupType = object.getInteger("groupType");
            if (userId==null||chatGroupId==null){
                result.setMsg("参数丢失");
                return result;
            }else{
                ImMessage imMessage=null;
                if(groupType==0){
                    imMessage =imMessageService.getSingleChatMessageInfoById(object);
                }
                else{
                     imMessage=imMessageService.getChatGroupMessageInfoById(object);
                }
                if(imMessage!=null){
                    ImMessage resetImMessage = resetImMessage(imMessage);
                    result.setCodeAndMsg(CommonEnum.SUCCESS);
                    result.setData(resetImMessage);
                }
                else{
                    result.setMsg("暂无相关会话信息");
                }
            }
        }catch (Exception e){
            logger.info("查询会话详情触发异常",e);
            result.setMsg("查询会话详情触发异常");
        }
        return result;
    }

    @ResponseBody
    @PostMapping(value = "/addImMessage")
    public Object addImMessage(@RequestBody ImMessage imMessage) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            if (imMessageService.addImMessage(imMessage) > 0) {
                msgInfo.setMsg("消息列表新增成功！");
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("消息列表新增失败！");
            }
        } catch (Exception e) {
            logger.error("消息列表新增异常：{}", e);
            msgInfo.setMsg("消息列表新增异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/detail/{id}")
    public Object detail(@PathVariable("id") int id) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            ImMessage imMessage = imMessageService.getImMessage(id);
            if (imMessage != null) {
                msgInfo.setCode(1);
                msgInfo.setMsg("消息列表获取成功！");
                msgInfo.setData(imMessage);
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("消息列表获取失败！");
            }
        } catch (Exception e) {
            logger.error("消息列表获取异常：{}", e);
            msgInfo.setMsg("消息列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/markRead")
    public Object markRead(@RequestBody ImMessage imMessage) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            ImMessage originalImMessage = imMessageService.getImMessageByGroupId(imMessage.getUserId(), imMessage.getChatGroupId());
            if (null != originalImMessage) {
                originalImMessage.setUnreadNum(0);
                originalImMessage.setAtFlag(false);
                imMessageService.updateImMessage(originalImMessage);
            }
        } catch (Exception e) {
            logger.error("消息标记已读异常：{}", e);
            msgInfo.setMsg("消息标记已读异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/delete/{id}")
    public Object delete(@PathVariable("id") int id){
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try{
            if(imMessageService.delete(id)>0){
                msgInfo.setMsg("消息列表删除成功！");
            }else{
                msgInfo.setCode(0);
                msgInfo.setMsg("消息列表删除失败！");
            }
        }catch (Exception e){
            logger.error("消息列表删除异常：{}", e);
            msgInfo.setMsg("消息列表删除异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }


    @ResponseBody
    @PostMapping(value = "/countUnreadNum")
    public Object countUnreadNum(@RequestHeader(value = Constants.AUTHORIZATION) String token) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try {
            if(StringUtils.isNotEmpty(token)){
                User tokenUser = userService.getUserByToken(token);
                if(null != tokenUser){
                    int unreadNum = imMessageService.countUnreadNum(tokenUser.getId());
                    msgInfo.setData(unreadNum);
                    msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
                }
            }

        } catch (Exception e) {
            logger.error("消息标记已读异常：{}", e);
            msgInfo.setMsg("消息标记已读异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }
}
