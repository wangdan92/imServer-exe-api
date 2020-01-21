package com.im.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.im.*;
import com.im.bean.BaseConfig;
import com.im.bean.MsgInfo;
import com.im.dao.ImChatGroupUserDao;
import com.im.entity.*;
import com.im.enums.ChatGroupTypeEnum;
import com.im.enums.CommonEnum;
import com.im.model.UserContactsItemModel;
import com.im.model.UserContactsModel;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ImChatGroupUserServiceImpl implements ImChatGroupUserService {
    @Autowired
    private ImChatGroupUserDao imChatGroupUserDao;
    @Autowired
    private ImChatGroupService imChatGroupService;
    @Autowired
    private ImMessageService imMessageService;
    @Autowired
    private ImChatGroupMessageService imChatGroupMessageService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseConfig baseConfig;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<ImChatGroupUser> listByChatGroupId(int chatGroupId) {
        List<ImChatGroupUser> imChatGroupUserList = imChatGroupUserDao.listByChatGroupId(chatGroupId);
        for (ImChatGroupUser imChatGroupUser : imChatGroupUserList) {
            if (null != imChatGroupUser.getPhotoPath() && !imChatGroupUser.getPhotoPath().equals("")) {
                String base64Str = Base64Utils.encodeToUrlSafeString(imChatGroupUser.getPhotoPath().getBytes());
                String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                imChatGroupUser.setPhotoPath(url);
            } else if (null == imChatGroupUser.getPhotoPath() || imChatGroupUser.getPhotoPath().equals("")) {
                String base64Str = Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                imChatGroupUser.setPhotoPath(url);
            }
        }
        return imChatGroupUserList;
    }

    @Override
    public List<UserContactsModel> listByChatGroupIdForDelete(int chatGroupId) {
        List<UserContactsModel> imChatGroupUserList = imChatGroupUserDao.listByChatGroupIdForDelete(chatGroupId);
        for (UserContactsModel userContactsModel : imChatGroupUserList) {
            for (UserContactsItemModel userContactsItemModel : userContactsModel.getItem()) {
                if (null == userContactsItemModel.getUrl() || "".equals(userContactsItemModel.getUrl())) {
                    String base64Str = Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                    String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                    userContactsItemModel.setUrl(url);
                } else {
                    String base64Str = Base64Utils.encodeToUrlSafeString(userContactsItemModel.getUrl().getBytes());
                    String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                    userContactsItemModel.setUrl(url);
                }
            }
        }
        return imChatGroupUserList;
    }

    @Override
    public List<UserContactsModel> listByChatGroupIdForAt(int chatGroupId,int userId) {
        List<UserContactsModel> imChatGroupUserList = imChatGroupUserDao.listByChatGroupIdForAt(chatGroupId,userId);
        for (UserContactsModel userContactsModel : imChatGroupUserList) {
            for (UserContactsItemModel userContactsItemModel : userContactsModel.getItem()) {
                if (null == userContactsItemModel.getUrl() || "".equals(userContactsItemModel.getUrl())) {
                    String base64Str = Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                    String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                    userContactsItemModel.setUrl(url);
                } else {
                    String base64Str = Base64Utils.encodeToUrlSafeString(userContactsItemModel.getUrl().getBytes());
                    String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                    userContactsItemModel.setUrl(url);
                }
            }
        }
        return imChatGroupUserList;
    }

    @Override
    public List<UserContactsModel> listByChatGroupIdForAdd(int chatGroupId) {
        List<UserContactsModel> imChatGroupUserList = imChatGroupUserDao.listByChatGroupIdForAdd(chatGroupId);
        for (UserContactsModel userContactsModel : imChatGroupUserList) {
            for (UserContactsItemModel userContactsItemModel : userContactsModel.getItem()) {
                if (null == userContactsItemModel.getUrl() || "".equals(userContactsItemModel.getUrl())) {
                    String base64Str = Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                    String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                    userContactsItemModel.setUrl(url);
                } else {
                    String base64Str = Base64Utils.encodeToUrlSafeString(userContactsItemModel.getUrl().getBytes());
                    String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                    userContactsItemModel.setUrl(url);
                }
            }
        }
        return imChatGroupUserList;
    }

    @Override
    public List<ImChatGroupUser> listByChatGroupIdAndUserId(int chatGroupId, int userId) {
        List<ImChatGroupUser> imChatGroupUserList = imChatGroupUserDao.listByChatGroupIdAndUserId(chatGroupId, userId);
        for (ImChatGroupUser imChatGroupUser : imChatGroupUserList) {
            if (null != imChatGroupUser.getPhotoPath() && !imChatGroupUser.getPhotoPath().equals("")) {
                String base64Str = Base64Utils.encodeToUrlSafeString(imChatGroupUser.getPhotoPath().getBytes());
                String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                imChatGroupUser.setPhotoPath(url);
            } else if (null == imChatGroupUser.getPhotoPath() || imChatGroupUser.getPhotoPath().equals("")) {
                String base64Str = Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                imChatGroupUser.setPhotoPath(url);
            }
        }
        return imChatGroupUserList;
    }

    @Override
    public int addImChatGroupUser(Integer groupType, ImChatGroupUser imChatGroupUser) {
        ImChatGroupUser imChatGroupUserOld = getDetailByUserIdAndChatGroupId(imChatGroupUser.getUserId(), imChatGroupUser.getChatGroupId());
        Date currentTime = new Date();
        if (null == imChatGroupUserOld) {
            //假如是点对点聊天，群成员超过二个时，则直接升级成普通群
            if (groupType.equals(ChatGroupTypeEnum.ORDINARYCHAT.getCode())) {
                List<ImChatGroupUser> imChatGroupUserList = listByChatGroupId(imChatGroupUser.getChatGroupId());
                if (imChatGroupUserList.size() == 2) {
                    groupType = ChatGroupTypeEnum.ORDINARYGROUPCHAT.getCode();
                    ImChatGroup imChatGroup = imChatGroupService.getImChatGroup(imChatGroupUser.getChatGroupId());
                    //设置成普通群聊
                    imChatGroup.setGroupType(ChatGroupTypeEnum.ORDINARYGROUPCHAT.getCode());
                    User user = userService.getUserById(imChatGroupUser.getUserId());
                    imChatGroup.setName(imChatGroup.getName() + "、" + user.getRealName());
                    imChatGroup.setCreateTime(currentTime);
                    imChatGroupService.addImChatGroup(imChatGroup);

                    for (ImChatGroupUser chatGroupUser : imChatGroupUserList) {
                        chatGroupUser.setChatGroupId(imChatGroup.getId());
                        chatGroupUser.setCreateTime(currentTime);
                        imChatGroupUserDao.addImChatGroupUser(chatGroupUser);

                        ImMessage imMessageOld = imMessageService.getImMessageByGroupId(chatGroupUser.getUserId(), chatGroupUser.getChatGroupId());
                        if (null == imMessageOld) {
                            ImMessage imMessage = new ImMessage();
                            imMessage.setUserId(chatGroupUser.getUserId());
                            imMessage.setChatGroupId(chatGroupUser.getChatGroupId());
                            imMessage.setCreateTime(currentTime);
                            imMessage.setCreateId(chatGroupUser.getUserId());
                            imMessage.setUnreadNum(0);
                            imMessage.setTopFlag(false);
                            if (imChatGroup.getGroupType().equals(ChatGroupTypeEnum.TASKGROUPCHAT.getCode())) {
                                imMessage.setTopFlag(true);
                            }
                            imMessageService.addImMessage(imMessage);
                        }
                    }
                    imChatGroupUser.setChatGroupId(imChatGroup.getId());
                }
            }
            imChatGroupUser.setCreateTime(currentTime);
            imChatGroupUserDao.addImChatGroupUser(imChatGroupUser);
        } else {
            imChatGroupUser = imChatGroupUserOld;
        }

        //增加到个人的列表中
        ImMessage imMessageOld = imMessageService.getImMessageByGroupId(imChatGroupUser.getUserId(), imChatGroupUser.getChatGroupId());
        if (null == imMessageOld) {
            ImMessage imMessage = new ImMessage();
            imMessage.setUserId(imChatGroupUser.getUserId());
            imMessage.setChatGroupId(imChatGroupUser.getChatGroupId());
            imMessage.setCreateTime(currentTime);
            imMessage.setCreateId(imChatGroupUser.getUserId());
            imMessage.setUnreadNum(0);
            imMessage.setTopFlag(false);
            if (groupType.equals(ChatGroupTypeEnum.TASKGROUPCHAT.getCode())) {
                imMessage.setTopFlag(true);
            }
            imMessageService.addImMessage(imMessage);
        }
        return 1;
    }

    @Override
    public MsgInfo addImChatGroupUsers(ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        JSONObject dataObject = new JSONObject();
        ImChatGroup imChatGroupOld = imChatGroupService.getImChatGroup(imChatGroup.getId());
        if (null != imChatGroupOld) {
            Date currentTime = new Date();
            //如果是点对点聊天，添加成员，自动升级成群聊(暂时不允许加人)
            if (imChatGroupOld.getGroupType().equals(ChatGroupTypeEnum.ORDINARYCHAT.getCode().intValue())) {
                imChatGroupOld.setCreateTime(new Date());
                imChatGroupOld.setNoticeUpdateTime(new Date());
                imChatGroupOld.setName("群聊");
                imChatGroupOld.setGroupType(ChatGroupTypeEnum.ORDINARYGROUPCHAT.getCode().intValue());
                imChatGroupService.addImChatGroup(imChatGroupOld);

                List<ImChatGroupUser> imChatGroupUserList = listByChatGroupId(imChatGroup.getId());
                for (ImChatGroupUser imChatGroupUser : imChatGroupUserList) {
                    imChatGroupUser.setChatGroupId(imChatGroupOld.getId());
                    imChatGroupUser.setCreateTime(currentTime);
                    imChatGroupUserDao.addImChatGroupUser(imChatGroupUser);

                    ImMessage imMessage = new ImMessage();
                    imMessage.setUserId(imChatGroupUser.getUserId());
                    imMessage.setChatGroupId(imChatGroupUser.getChatGroupId());
                    imMessage.setCreateTime(currentTime);
                    imMessage.setCreateId(imChatGroupUser.getUserId());
                    imMessage.setUnreadNum(0);
                    imMessage.setTopFlag(false);
                    imMessageService.addImMessage(imMessage);
                }

                for (ImChatGroupUser imChatGroupUser : imChatGroup.getUserList()) {
                    imChatGroupUser.setChatGroupId(imChatGroupOld.getId());
                    imChatGroupUser.setCreateTime(currentTime);
                    imChatGroupUserDao.addImChatGroupUser(imChatGroupUser);

                    ImMessage imMessage = new ImMessage();
                    imMessage.setUserId(imChatGroupUser.getUserId());
                    imMessage.setChatGroupId(imChatGroupUser.getChatGroupId());
                    imMessage.setCreateTime(currentTime);
                    imMessage.setCreateId(imChatGroupUser.getUserId());
                    imMessage.setUnreadNum(0);
                    imMessage.setTopFlag(false);
                    imMessageService.addImMessage(imMessage);
                }
            } else {
                dataObject.put("imChatGroup",imChatGroupOld);
                JSONArray imChatGroupMessageArray = new JSONArray();
                for (ImChatGroupUser imChatGroupUser : imChatGroup.getUserList()) {
                    ImChatGroupUser imChatGroupUserOld = getDetailByUserIdAndChatGroupId(imChatGroupUser.getUserId(), imChatGroup.getId());
                    //如果用户不在群组里，创建用户
                    if (null == imChatGroupUserOld) {
                        imChatGroupUser.setChatGroupId(imChatGroupOld.getId());
                        imChatGroupUser.setCreateTime(currentTime);
                        imChatGroupUser.setCreateId(imChatGroup.getCreateId());
                        imChatGroupUser.setSilenceFlag(false);
                        imChatGroupUserDao.addImChatGroupUser(imChatGroupUser);
                        //即时通讯增加一条记录
                        ImMessage imMessage = new ImMessage();
                        imMessage.setUserId(imChatGroupUser.getUserId());
                        imMessage.setChatGroupId(imChatGroupUser.getChatGroupId());
                        imMessage.setCreateTime(currentTime);
                        imMessage.setCreateId(imChatGroupUser.getUserId());
                        imMessage.setUnreadNum(0);
                        imMessage.setTopFlag(false);
                        imMessageService.addImMessage(imMessage);
                        //群组消息表增加一条系统消息的记录
                        ImChatGroupMessage imChatGroupMessage = new ImChatGroupMessage();
                        User user = userService.getUserById(imChatGroupUser.getUserId());
                        if (null != user) {
                            String text = user.getRealName() + "加入了群聊";
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("text", text);
                            imChatGroupMessage.setContent(jsonObject.toJSONString());
                            imChatGroupMessage.setFromId(imChatGroup.getCreateId());
                            imChatGroupMessage.setChatGroupId(imChatGroup.getId());
                            imChatGroupMessage.setSendTime(currentTime);
                            imChatGroupMessage.setContentType((short) 1);
                            imChatGroupMessage.setMessageType((short) 2);
                            imChatGroupMessageService.addImChatGroupMessage(imChatGroupMessage);
                            imChatGroupMessageArray.add(JSON.toJSON(imChatGroupMessage));
                        }

                    } else {
                        logger.error("{}用户已存在群组{}中", imChatGroupUser.getUserId(), imChatGroupUser.getChatGroupId());
                    }
                }
                dataObject.put("imChatGroupMessageArray",imChatGroupMessageArray);
            }
        } else {
            msgInfo.setCodeAndMsg(CommonEnum.FAIL);
            return msgInfo;
        }
        msgInfo.setData(dataObject);
        return msgInfo;
    }

    @Override
    public int updateImChatGroupUser(ImChatGroupUser imChatGroupUser) {
        return imChatGroupUserDao.updateImChatGroupUser(imChatGroupUser);
    }

    @Override
    public MsgInfo deleteImChatGroupUsers(ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        JSONObject dataObject = new JSONObject();
        ImChatGroup originalImChatGroup = imChatGroupService.getImChatGroup(imChatGroup.getId());
        Date currentTime = new Date();
        if (null != originalImChatGroup) {
            dataObject.put("imChatGroup",originalImChatGroup);
            //群成员最后一个退群，则删除群
            List<ImChatGroupUser> imChatGroupUserList = listByChatGroupId(imChatGroup.getId());
            if (CollectionUtils.isEmpty(imChatGroupUserList)) {
                msgInfo.setCodeAndMsg(CommonEnum.FAIL.getCode(),"群已无成员");
                logger.error("群已无成员");
                return msgInfo;
            } else if (CollectionUtils.isEmpty(imChatGroup.getUserList())) {
                logger.error("请选择要删除的群成员");
                msgInfo.setCodeAndMsg(CommonEnum.FAIL.getCode(),"请选择要删除的群成员");
                return msgInfo;
            } else if (imChatGroupUserList.size() == imChatGroup.getUserList().size()) {
                logger.error("无法删除所有群成员");
                msgInfo.setCodeAndMsg(CommonEnum.FAIL.getCode(),"无法删除所有群成员");
                return msgInfo;
            }
            JSONArray imChatGroupMessageArray = new JSONArray();
            for (ImChatGroupUser imChatGroupUser : imChatGroup.getUserList()) {
                ImChatGroupUser imChatGroupUserOld = imChatGroupUserDao.getDetailByUserIdAndChatGroupId(imChatGroupUser.getUserId(), imChatGroup.getId());
                if (null != imChatGroupUserOld) {
                    //群组用户中删除
                    imChatGroupUserDao.delete(imChatGroupUserOld.getId());
                    //即时通讯列表中删除
                    imMessageService.deleteByChatGroupId(imChatGroup.getId(), imChatGroupUser.getUserId());
                    //群消息中通知用户退出群聊
                    ImChatGroupMessage imChatGroupMessage = new ImChatGroupMessage();
                    User user = userService.getUserById(imChatGroupUser.getUserId());
                    if (null != user) {
                        String text = user.getRealName() + "退出了群聊";
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("text", text);
                        imChatGroupMessage.setContent(jsonObject.toJSONString());
                        imChatGroupMessage.setFromId(imChatGroup.getCreateId());
                        imChatGroupMessage.setChatGroupId(imChatGroup.getId());
                        imChatGroupMessage.setSendTime(currentTime);
                        imChatGroupMessage.setContentType((short) 1);
                        imChatGroupMessage.setMessageType((short) 2);
                        imChatGroupMessageService.addImChatGroupMessage(imChatGroupMessage);
                        imChatGroupMessageArray.add(JSON.toJSON(imChatGroupMessage));
                    }
                }
            }
            dataObject.put("imChatGroupMessageArray",imChatGroupMessageArray);

        } else {
            msgInfo.setCodeAndMsg(CommonEnum.FAIL.getCode(),"群不存在");
            logger.error("群不存在");
        }
        msgInfo.setData(dataObject);
        return msgInfo;
    }

    @Override
    public int countChatGroupUserNum(int chatGroupId) {
        return imChatGroupUserDao.countChatGroupUserNum(chatGroupId);
    }

    @Override
    public ImChatGroupUser getDetail(int id) {
        return imChatGroupUserDao.getDetail(id);
    }

    @Override
    public ImChatGroupUser getDetailByUserIdAndChatGroupId(int userId, int chatGroupId) {
        return imChatGroupUserDao.getDetailByUserIdAndChatGroupId(userId, chatGroupId);
    }

    @Override
    public int updateSilenceFlag(ImChatGroupUser imChatGroupUser) {
        return imChatGroupUserDao.updateSilenceFlag(imChatGroupUser);
    }

    /**
     * 查询我的群组列表
     * @param userId
     * @return
     */
    @Override
    public List<HashMap<Integer,Object>> listChatByUserId(Integer userId) {
        return imChatGroupUserDao.listChatByUserId(userId);
    }
}