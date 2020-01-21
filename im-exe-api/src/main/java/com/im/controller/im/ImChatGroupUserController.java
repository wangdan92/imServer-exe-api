package com.im.controller.im;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.im.ImChatGroupUserService;
import com.im.UserService;
import com.im.bean.BaseConfig;
import com.im.bean.MsgInfo;
import com.im.constant.Constants;
import com.im.entity.ImChatGroup;
import com.im.entity.ImChatGroupMessage;
import com.im.entity.ImChatGroupUser;
import com.im.entity.User;
import com.im.enums.CommonEnum;
import com.im.model.UserContactsModel;
import com.im.util.DateTimeUtil;
import com.im.websocket.SocketServer;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "群成员")
@RestController
@RequestMapping(value = "/imCharGroupUser")
public class ImChatGroupUserController {
    @Autowired
    private ImChatGroupUserService imChatGroupUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseConfig baseConfig;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @PostMapping(value = "/listByChatGroupId/{chatGroupId}")
    public Object listByChatGroupId(@PathVariable Integer chatGroupId) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            List<ImChatGroupUser> imChatGroupUserList = imChatGroupUserService.listByChatGroupId(chatGroupId);
            msgInfo.setData(imChatGroupUserList);
        } catch (Exception e) {
            logger.error("群成员列表获取异常：{}", e);
            msgInfo.setMsg("群成员列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }


    @ResponseBody
    @PostMapping(value = "/listByChatGroupIdForDelete/{chatGroupId}")
    public Object listByChatGroupIdForDelete(@PathVariable Integer chatGroupId) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        long start= System.currentTimeMillis();
        try {
            List<UserContactsModel> imChatGroupUserList = imChatGroupUserService.listByChatGroupIdForDelete(chatGroupId);
            msgInfo.setData(imChatGroupUserList);
            msgInfo.setCode(CommonEnum.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("群成员列表获取异常：{}", e);
            msgInfo.setMsg("群成员列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        long end= System.currentTimeMillis();
        System.out.println("end-start="+(end-start));
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/listByChatGroupIdForAt/{chatGroupId}")
    public Object listByChatGroupIdForAt(@PathVariable Integer chatGroupId, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try {
            if(StringUtils.isNotEmpty(token)){
                User tokenUser = userService.getUserByToken(token);
                if(null != tokenUser){
                    List<UserContactsModel> imChatGroupUserList = imChatGroupUserService.listByChatGroupIdForAt(chatGroupId,tokenUser.getId());
                    msgInfo.setData(imChatGroupUserList);
                    msgInfo.setCode(CommonEnum.SUCCESS.getCode());
                }
            }
        } catch (Exception e) {
            logger.error("群成员列表获取异常：{}", e);
            msgInfo.setMsg("群成员列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }


    @ResponseBody
    @PostMapping(value = "/listByChatGroupIdForAdd/{chatGroupId}")
    public Object listByChatGroupIdForAdd(@PathVariable Integer chatGroupId) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try {
            List<UserContactsModel> imChatGroupUserList = imChatGroupUserService.listByChatGroupIdForAdd(chatGroupId);
            msgInfo.setData(imChatGroupUserList);
            msgInfo.setCode(CommonEnum.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("群成员列表获取异常：{}", e);
            msgInfo.setMsg("群成员列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/listByChatGroupIdAndUserId/{chatGroupId}/{userId}")
    public Object listByChatGroupIdAndUserId(@PathVariable Integer chatGroupId, @PathVariable Integer userId) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            List<ImChatGroupUser> imChatGroupUserList = imChatGroupUserService.listByChatGroupIdAndUserId(chatGroupId,userId);
            msgInfo.setData(imChatGroupUserList);
        } catch (Exception e) {
            logger.error("群成员列表获取异常：{}", e);
            msgInfo.setMsg("群成员列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/addImChatGroupUsers")
    public Object addImChatGroupUsers(@RequestBody ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            MsgInfo tmpMsg = imChatGroupUserService.addImChatGroupUsers(imChatGroup);
            JSONObject dataObject = (JSONObject) JSON.toJSON(tmpMsg.getData());
            sendSocketMessage(dataObject);
            if (tmpMsg.getCode()== CommonEnum.SUCCESS.getCode()) {
                msgInfo.setMsg("群成员新增成功！");
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群成员新增失败！");
            }
        } catch (Exception e) {
            logger.error("群成员新增异常：{}", e);
            msgInfo.setMsg("群成员新增异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    private void sendSocketMessage(JSONObject dataObject){
        ImChatGroup imChatGroup = JSON.toJavaObject(dataObject.getJSONObject("imChatGroup"), ImChatGroup.class);
        List<ImChatGroupMessage> imChatGroupMessageList = dataObject.getJSONArray("imChatGroupMessageArray").toJavaList(ImChatGroupMessage.class);
        if(null != imChatGroup && CollectionUtils.isNotEmpty(imChatGroupMessageList)){
            for(ImChatGroupMessage imChatGroupMessage:imChatGroupMessageList){
                JSONObject returnObject = new JSONObject();
                returnObject.put("type", "system");
                User user = userService.getUserById(imChatGroup.getCreateId());
                if (null != user.getPhotoPath() && !user.getPhotoPath().equals("")) {
                    String base64Str = Base64Utils.encodeToUrlSafeString(user.getPhotoPath().getBytes());
                    String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                    user.setPhotoPath(url);
                } else if (null == user.getPhotoPath() || user.getPhotoPath().equals("")) {
                    String base64Str = Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                    String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                    user.setPhotoPath(url);
                }
                JSONObject msgObject = new JSONObject();
                msgObject.put("id", imChatGroupMessage.getId());
                String tmpType = "text";
                msgObject.put("type", tmpType);
                msgObject.put("time", DateTimeUtil.formatQQ(imChatGroupMessage.getSendTime()));
                msgObject.put("userinfo", user);
                msgObject.put("chatGroupId", imChatGroup.getId());
                msgObject.put("userId", imChatGroup.getCreateId());
                msgObject.put("content", JSONObject.parseObject(imChatGroupMessage.getContent()));
                msgObject.put("id", imChatGroupMessage.getId());
                returnObject.put("msg", msgObject);
                SocketServer.sendChatGroup(imChatGroup.getCreateId(),imChatGroup.getId(),returnObject,null);
            }
        }
    }

    @ResponseBody
    @PostMapping(value = "/updateImChatGroupUser")
    public Object updateImChatGroupUser(@RequestBody ImChatGroupUser imChatGroupUser) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            if (imChatGroupUserService.updateImChatGroupUser(imChatGroupUser) > 0) {
                msgInfo.setMsg("群成员修改成功！");
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群成员修改失败！");
            }
        } catch (Exception e) {
            logger.error("群成员修改异常：{}", e);
            msgInfo.setMsg("群成员修改异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/deleteImChatGroupUsers")
    public Object deleteImChatGroupUsers(@RequestBody ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try {
            if(null == imChatGroup || null == imChatGroup.getId()){
                msgInfo.setMsg("参数非法！");
                msgInfo.setCode(CommonEnum.FAIL.getCode());
            }
            MsgInfo tmpMsg = imChatGroupUserService.deleteImChatGroupUsers(imChatGroup);
            JSONObject dataObject = (JSONObject) JSON.toJSON(tmpMsg.getData());
            sendSocketMessage(dataObject);
            if (tmpMsg.getCode()== CommonEnum.SUCCESS.getCode()) {
                msgInfo.setMsg("群成员删除成功！");
                msgInfo.setCode(CommonEnum.SUCCESS.getCode());
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群成员删除失败！");
            }
        } catch (Exception e) {
            logger.error("群成员删除异常：{}", e);
            msgInfo.setMsg("群成员删除异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/updateSilenceFlag")
    public Object updateSilenceFlag(@RequestBody ImChatGroupUser imChatGroupUser) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try {
            if (null != imChatGroupUser) {
                    imChatGroupUserService.updateSilenceFlag(imChatGroupUser);
                    msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("修改信息免打扰异常{}", e);
            msgInfo.setMsg("修改信息免打扰异常");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/detail")
    public Object detail(@RequestBody ImChatGroupUser imChatGroupUser) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try {
            if (null != imChatGroupUser) {
                ImChatGroupUser originalImChatGroupUser = imChatGroupUserService.getDetailByUserIdAndChatGroupId(imChatGroupUser.getUserId(),imChatGroupUser.getChatGroupId());
                msgInfo.setData(originalImChatGroupUser);
                msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("获取免打扰标识异常", e);
            msgInfo.setMsg("获取免打扰标识异常");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }


    @ResponseBody
    @PostMapping(value = "/listGroupByUserId/{userId}")
    public Object listGroupByUserId(@PathVariable Integer userId){
        MsgInfo msgInfo=new MsgInfo(CommonEnum.FAIL);
        try {
            if (userId == null) {
                   msgInfo.setMsg("用户身份已失效,请重新登录!");
                   return msgInfo;
            }
            List<HashMap<Integer,Object>> groupList=imChatGroupUserService.listChatByUserId(userId);
            if (CollectionUtils.isNotEmpty(groupList)){
                msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
                msgInfo.setData(groupList);
            }
        }catch (Exception e){
            logger.info("获取群列表触发异常",e);
            msgInfo.setMsg("获取群列表触发异常");
        }
        return  msgInfo;
    }
}
