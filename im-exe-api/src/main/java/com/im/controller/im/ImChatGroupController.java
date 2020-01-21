package com.im.controller.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.im.ImChatGroupService;
import com.im.ImChatGroupUserService;
import com.im.UserService;
import com.im.bean.BaseConfig;
import com.im.bean.MsgInfo;
import com.im.entity.ImChatGroup;
import com.im.entity.ImChatGroupMessage;
import com.im.entity.User;
import com.im.enums.ChatGroupTypeEnum;
import com.im.enums.CommonEnum;
import com.im.util.DateTimeUtil;
import com.im.websocket.SocketServer;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "群")
@RestController
@RequestMapping(value = "/imCharGroup")
public class ImChatGroupController {
    @Autowired
    private ImChatGroupService imChatGroupService;
    @Autowired
    private ImChatGroupUserService imChatGroupUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseConfig baseConfig;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @PostMapping(value = "/listAllImChatGroup")
    public Object listAllImChatGroup() {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            List<ImChatGroup> imChatGroupList = imChatGroupService.listAll();
            msgInfo.setData(imChatGroupList);
        } catch (Exception e) {
            logger.error("群列表获取异常：{}", e);
            msgInfo.setMsg("群列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/listHistory")
    public Object listHistory(@RequestBody JSONObject object) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            List<ImChatGroup> imChatGroupList = imChatGroupService.listHistory(object.getInteger("userId"), object.getString("searchMsg"));
            msgInfo.setData(imChatGroupList);
        } catch (Exception e) {
            logger.error("历史任务列表获取异常：{}", e);
            msgInfo.setMsg("历史任务列表获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    /**
     * 创建点对点聊天
     *
     * @param userId1
     * @param userId2
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/singleChat/{userId1}/{userId2}")
    public Object singleChat(@PathVariable Integer userId1, @PathVariable Integer userId2) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            ImChatGroup imChatGroup = imChatGroupService.addSingleChat(userId1, userId2);
            int userNum = imChatGroupUserService.countChatGroupUserNum(imChatGroup.getId());
            User user = userService.getUserById(userId2);
            imChatGroup.setUserNum(userNum);
            imChatGroup.setName(user.getRealName());
            msgInfo.setData(imChatGroup);
        } catch (Exception e) {
            logger.error("点对点聊天获取异常：{}", e);
            msgInfo.setMsg("点对点聊天获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }




    /**
     * 创建普通群聊方法
     *
     * @param imChatGroup
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/addImChatGroup")
    public Object addImChatGroup(@RequestBody ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try {
            Date currentTime = new Date();
            imChatGroup.setCreateTime(currentTime);
            imChatGroup.setMaster(imChatGroup.getCreateId());
            imChatGroup.setStatus(0);
            imChatGroup.setGroupType(ChatGroupTypeEnum.ORDINARYGROUPCHAT.getCode());
            imChatGroup.setNoticeUpdateTime(currentTime);
            MsgInfo tmpMsg = imChatGroupService.addImChatGroup(imChatGroup);
            if (tmpMsg.getCode()== CommonEnum.SUCCESS.getCode()) {
                msgInfo.setMsg("群新增成功！");
                msgInfo.setCode(CommonEnum.SUCCESS.getCode());
                msgInfo.setData(imChatGroup);
                JSONObject dataObject = (JSONObject) JSON.toJSON(tmpMsg.getData());
                sendSocketMessage(dataObject);
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群新增失败！");
            }
        } catch (Exception e) {
            logger.error("群新增异常：{}", e);
            msgInfo.setMsg("群新增异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    private void sendSocketMessage(JSONObject dataObject){
        ImChatGroup imChatGroup = JSON.toJavaObject(dataObject.getJSONObject("imChatGroup"), ImChatGroup.class);
        ImChatGroupMessage imChatGroupMessage = JSON.toJavaObject(dataObject.getJSONObject("imChatGroupMessage"), ImChatGroupMessage.class);
        if(null != imChatGroup && null != imChatGroupMessage){
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

    @ResponseBody
    @PostMapping(value = "/updateImChatGroup")
    public Object updateImChatGroup(@RequestBody ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            if (imChatGroupService.updateImChatGroup(imChatGroup) > 0) {
                msgInfo.setMsg("群修改成功！");
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群修改失败！");
            }
        } catch (Exception e) {
            logger.error("群修改异常：{}", e);
            msgInfo.setMsg("群修改异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/updateImChatGroupName")
    public Object updateImChatGroupName(@RequestBody ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            if (null != imChatGroup.getName() && !"".equals(imChatGroup.getName())) {
                ImChatGroup originalImChatGroup = imChatGroupService.getImChatGroup(imChatGroup.getId());
                if (null != originalImChatGroup) {
                    originalImChatGroup.setName(imChatGroup.getName());
                    if (imChatGroupService.updateImChatGroupName(originalImChatGroup) > 0) {
                        msgInfo.setMsg("群名称修改成功！");
                    } else {
                        msgInfo.setCode(0);
                        msgInfo.setMsg("群名称修改失败！");
                    }
                }

            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群名称不能为空！");
            }
        } catch (Exception e) {
            logger.error("群名称修改异常：{}", e);
            msgInfo.setMsg("群名称修改异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/updateImChatGroupNotice")
    public Object updateImChatGroupNotice(@RequestBody ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try {
            if (null != imChatGroup.getId()) {
                ImChatGroup imChatGroupOld = imChatGroupService.getImChatGroup(imChatGroup.getId());
                if(null != imChatGroupOld){
                    imChatGroupOld.setNotice(imChatGroup.getNotice());
                    imChatGroupOld.setNoticeUpdateTime(new Date());
                    MsgInfo tmpMsg = imChatGroupService.updateImChatGroupNotice(imChatGroupOld);
                    if (tmpMsg.getCode()== CommonEnum.SUCCESS.getCode()) {
                        msgInfo.setCode(CommonEnum.SUCCESS.getCode());
                        JSONObject dataObject = (JSONObject) JSON.toJSON(tmpMsg.getData());
                        ImChatGroup tmpGroup = JSON.toJavaObject(dataObject.getJSONObject("imChatGroup"), ImChatGroup.class);
                        tmpGroup.setNoticeUpdateTimeString(DateTimeUtil.format(tmpGroup.getNoticeUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
                        msgInfo.setData(imChatGroupOld);
                        ImChatGroupMessage imChatGroupMessage = JSON.toJavaObject(dataObject.getJSONObject("imChatGroupMessage"), ImChatGroupMessage.class);
                        JSONArray atUserArray = dataObject.getJSONArray("atUserList");
                        sendSocketMessage(tmpGroup,imChatGroupMessage,atUserArray);
                    } else {
                        msgInfo.setCode(0);
                        msgInfo.setMsg("群新增失败！");
                    }

                }else {
                    msgInfo.setCode(0);
                    msgInfo.setMsg("群公告修改失败！");
                }
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群公告不能为空！");
            }
        } catch (Exception e) {
            logger.error("群公告修改异常：{}", e);
            msgInfo.setMsg("群公告修改异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    private void sendSocketMessage(ImChatGroup imChatGroup, ImChatGroupMessage imChatGroupMessage, JSONArray atUserArray){

        if(null != imChatGroup){
            JSONObject returnObject = new JSONObject();
            returnObject.put("type", "user");
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
            msgObject.put("refId", imChatGroupMessage.getRefId());
            returnObject.put("msg", msgObject);
            SocketServer.sendChatGroup(imChatGroup.getCreateId(),imChatGroup.getId(),returnObject,atUserArray);
        }
    }

    @ResponseBody
    @PostMapping(value = "/detail/{id}")
    public Object detail(@PathVariable("id") int id) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            ImChatGroup imChatGroup = imChatGroupService.getImChatGroup(id);
            if (null != imChatGroup) {
                if(null != imChatGroup.getNoticeUpdateTime()){
                    imChatGroup.setNoticeUpdateTimeString(DateTimeUtil.format(imChatGroup.getNoticeUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
                }
                int userNum = imChatGroupUserService.countChatGroupUserNum(imChatGroup.getId());
                imChatGroup.setUserNum(userNum);
                msgInfo.setCode(1);
                msgInfo.setMsg("群详情获取成功！");
                msgInfo.setData(imChatGroup);
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群详情获取失败！");
            }
        } catch (Exception e) {
            logger.error("群详情获取异常：{}", e);
            msgInfo.setMsg("群详情获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/detailByUserId/{id}/{userId}")
    public Object detailByUserId(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            ImChatGroup imChatGroup = imChatGroupService.getImChatGroupByUserId(id, userId);
            if (imChatGroup != null) {
                int userNum = imChatGroupUserService.countChatGroupUserNum(imChatGroup.getId());
                imChatGroup.setUserNum(userNum);
                msgInfo.setCode(1);
                msgInfo.setMsg("群详情获取成功！");
                msgInfo.setData(imChatGroup);
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群详情获取失败！");
            }
        } catch (Exception e) {
            logger.error("群详情获取异常：{}", e);
            msgInfo.setMsg("群详情获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/detailByTaskID/{taskID}")
    public Object detailByTaskID(@PathVariable("taskID") int taskID) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            ImChatGroup imChatGroup = imChatGroupService.getImChatGroupByTaskId(taskID);
            if (imChatGroup != null) {
                int userNum = imChatGroupUserService.countChatGroupUserNum(imChatGroup.getId());
                imChatGroup.setUserNum(userNum);
                msgInfo.setCode(1);
                msgInfo.setMsg("群详情获取成功！");
                msgInfo.setData(imChatGroup);
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群详情获取失败！");
            }
        } catch (Exception e) {
            logger.error("群详情获取异常：{}", e);
            msgInfo.setMsg("群详情获取异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }

    @ResponseBody
    @PostMapping(value = "/delete/{id}")
    public Object delete(@PathVariable("id") int id) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        try {
            if (imChatGroupService.delete(id) > 0) {
                msgInfo.setMsg("群删除成功！");
            } else {
                msgInfo.setCode(0);
                msgInfo.setMsg("群删除失败！");
            }
        } catch (Exception e) {
            logger.error("群删除异常：{}", e);
            msgInfo.setMsg("群删除异常！");
            msgInfo.setCode(CommonEnum.FAIL.getCode());
        }
        return msgInfo;
    }
}
