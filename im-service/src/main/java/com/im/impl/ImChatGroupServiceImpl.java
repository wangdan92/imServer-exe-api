package com.im.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.im.ImChatGroupMessageService;
import com.im.ImChatGroupService;
import com.im.ImChatGroupUserService;
import com.im.UserService;
import com.im.bean.MsgInfo;
import com.im.dao.ImChatGroupDao;
import com.im.dao.ImChatGroupUserDao;
import com.im.entity.ImChatGroup;
import com.im.entity.ImChatGroupMessage;
import com.im.entity.ImChatGroupUser;
import com.im.entity.User;
import com.im.enums.ChatGroupTypeEnum;
import com.im.enums.CommonEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ImChatGroupServiceImpl implements ImChatGroupService {
    @Autowired
    private ImChatGroupDao imChatGroupDao;
    @Autowired
    private ImChatGroupUserDao imChatGroupUserDao;

    @Autowired
    private ImChatGroupUserService imChatGroupUserService;
    @Autowired
    private ImChatGroupMessageService imChatGroupMessageService;
    @Autowired
    private UserService userService;


    /**
     * 查询所以群组
     * @return
     */
    @Override
    public List<ImChatGroup> listAll() {
        List<ImChatGroup> imChatGroups = imChatGroupDao.listAll();
        if(imChatGroups!=null&&imChatGroups.size()>0){
            for (ImChatGroup imChatGroup : imChatGroups) {
                imChatGroup.setExpansion(false);
                Integer chatGroupId = imChatGroup.getId();
                //根据chatGroupid去查询当期群组的用户
                List<ImChatGroupUser> imChatGroupUsers = imChatGroupUserDao.listByChatGroupId(chatGroupId);
                if (imChatGroupUsers!=null&&imChatGroupUsers.size()>0){
                    imChatGroup.setUserList(imChatGroupUsers);
                    imChatGroup.setUserNum(imChatGroupUsers.size());
                }
            }
        }
        return imChatGroups;
    }

    @Override
    public List<ImChatGroup> listHistory(Integer userId, String searchMsg) {
        return imChatGroupDao.listHistory(userId,searchMsg);
    }

    @Override
    public MsgInfo addImChatGroup(ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        imChatGroupDao.addImChatGroup(imChatGroup);
        JSONObject dataObject = new JSONObject();
        dataObject.put("imChatGroup",imChatGroup);
        Date currentTime = new Date();
        for (ImChatGroupUser imChatGroupUser : imChatGroup.getUserList()) {
            //增加群成员
            imChatGroupUser.setCreateId(imChatGroup.getMaster());
            imChatGroupUser.setCreateTime(currentTime);
            imChatGroupUser.setChatGroupId(imChatGroup.getId());
            imChatGroupUser.setSilenceFlag(false);
            imChatGroupUserService.addImChatGroupUser(imChatGroup.getGroupType(),imChatGroupUser);
        }
        //系统消息群组不发送消息，其他群组创建成功发送消息
        if(!imChatGroup.getGroupType().equals(ChatGroupTypeEnum.SYSTEM.getCode())){
            //群组创建成功需要插入一条系统消息
            ImChatGroupMessage imChatGroupMessage = new ImChatGroupMessage();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text","群组创建成功，开始聊天吧");
            imChatGroupMessage.setContent(jsonObject.toJSONString());
            if (imChatGroup.getGroupType().equals(ChatGroupTypeEnum.ORDINARYCHAT.getCode())) {
                jsonObject.put("text","会话创建成功，开始聊天吧");
                imChatGroupMessage.setContent(jsonObject.toJSONString());
            }
            imChatGroupMessage.setFromId(imChatGroup.getMaster());
            imChatGroupMessage.setChatGroupId(imChatGroup.getId());
            imChatGroupMessage.setSendTime(currentTime);
            imChatGroupMessage.setContentType((short)1);
            imChatGroupMessage.setMessageType((short)2);
            imChatGroupMessageService.addImChatGroupMessage(imChatGroupMessage);
            dataObject.put("imChatGroupMessage",imChatGroupMessage);
        }
        msgInfo.setData(dataObject);
        return msgInfo;
    }

    @Override
    public ImChatGroup getImChatGroup(int id) {
        return imChatGroupDao.getImChatGroup(id);
    }

    @Override
    public ImChatGroup getImChatGroupByUserId(int id, int userId) {
        return imChatGroupDao.getImChatGroupByUserId(id,userId);
    }

    @Override
    public ImChatGroup getImChatGroupSingleChat(int userId1, int userId2) {
        return imChatGroupDao.getImChatGroupSingleChat(userId1, userId2);
    }

    @Override
    public ImChatGroup getImChatGroupSystem(long master) {
        return imChatGroupDao.getImChatGroupSystem(master);
    }

    @Override
    public ImChatGroup addSingleChat(int userId1, int userId2) {
        //查询点对点聊天是否存在，存在则直接返回群信息
        ImChatGroup imChatGroup=getImChatGroupSingleChat(userId1,userId2);
        if(null!=imChatGroup){
            return imChatGroup;
        }
        //不存在则创建群信息
        User user1=userService.getUserById(userId1);
        User user2=userService.getUserById(userId2);
        imChatGroup=new ImChatGroup();
        imChatGroup.setName(user1.getRealName()+"、"+user2.getRealName());
        imChatGroup.setCreateId(userId1);
        imChatGroup.setCreateTime(new Date());
        imChatGroup.setGroupType(ChatGroupTypeEnum.ORDINARYCHAT.getCode());
        imChatGroup.setMaster(userId1);
        imChatGroup.setStatus(0);
        ImChatGroupUser imChatGroupUser1=new ImChatGroupUser();
        imChatGroupUser1.setUserId(userId1);
        ImChatGroupUser imChatGroupUser2=new ImChatGroupUser();
        imChatGroupUser2.setUserId(userId2);
        List<ImChatGroupUser> list=new ArrayList<>();
        list.add(imChatGroupUser1);
        list.add(imChatGroupUser2);
        imChatGroup.setUserList(list);
        addImChatGroup(imChatGroup);
        imChatGroup=getImChatGroup(imChatGroup.getId());
        return imChatGroup;
    }

    @Override
    public ImChatGroup getImChatGroupByTaskId(Integer taskId) {
        return imChatGroupDao.getImChatGroupByTaskId(taskId);
    }

    @Override
    public int updateImChatGroup(ImChatGroup imChatGroup) {
        return imChatGroupDao.updateImChatGroup(imChatGroup);
    }

    @Override
    public int updateImChatGroupName(ImChatGroup imChatGroup) {
        ImChatGroup imChatGroupOld=getImChatGroup(imChatGroup.getId());
        imChatGroupOld.setName(imChatGroup.getName());
        return updateImChatGroup(imChatGroupOld);
    }

    @Override
    public MsgInfo updateImChatGroupNotice(ImChatGroup imChatGroup) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.SUCCESS);
        Date currentTime = new Date();
        updateImChatGroup(imChatGroup);
        JSONObject dataObject = new JSONObject();
        dataObject.put("imChatGroup",imChatGroup);
        //群组创建成功需要插入一条系统消息
        ImChatGroupMessage imChatGroupMessage = new ImChatGroupMessage();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text","@所有人 "+imChatGroup.getNotice());
        jsonObject.put("title","任务终止");
        List<ImChatGroupUser> imChatGroupUserList = imChatGroupUserService.listByChatGroupId(imChatGroup.getId());
        if(CollectionUtils.isNotEmpty(imChatGroupUserList)){
            JSONArray jsonArray = new JSONArray();
            for(ImChatGroupUser imChatGroupUser:imChatGroupUserList){
                if(!imChatGroupUser.getUserId().equals(imChatGroup.getMaster())){
                    JSONObject userObject = new JSONObject();
                    userObject.put("userId",imChatGroupUser.getUserId());
                    jsonArray.add(userObject);
                }
            }
            jsonObject.put("atUserList",jsonArray);
            dataObject.put("atUserList",jsonArray);
        }
        imChatGroupMessage.setContent(jsonObject.toJSONString());
        imChatGroupMessage.setFromId(imChatGroup.getMaster());
        imChatGroupMessage.setChatGroupId(imChatGroup.getId());
        imChatGroupMessage.setSendTime(currentTime);
        imChatGroupMessage.setContentType((short)1);
        imChatGroupMessage.setMessageType((short)1);
        imChatGroupMessageService.addImChatGroupMessage(imChatGroupMessage);
        dataObject.put("imChatGroupMessage",imChatGroupMessage);
        msgInfo.setData(dataObject);
        return msgInfo;
    }

    @Override
    public int updateImChatGroupStatusByRefId(int refId) {
        ImChatGroup imChatGroupOld = getImChatGroupByTaskId(refId);
        if(null != imChatGroupOld){
            imChatGroupOld.setStatus(1);
            return imChatGroupDao.updateImChatGroup(imChatGroupOld);
        }else {
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        return imChatGroupDao.delete(id);
    }
}
