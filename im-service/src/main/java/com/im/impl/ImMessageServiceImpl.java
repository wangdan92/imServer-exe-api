package com.im.impl;

import com.alibaba.fastjson.JSONObject;
import com.im.ImMessageService;
import com.im.dao.ImMessageDao;
import com.im.entity.ImMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImMessageServiceImpl implements ImMessageService {
    @Autowired
    private ImMessageDao imMessageDao;

    @Override
    public List<ImMessage> listAllByUserId(Map<String,Object> map) {
        return imMessageDao.listAllByUserId(map);
    }

    @Override
    public ImMessage getSingleChatMessageInfoById(JSONObject object) {
        ImMessage imMessageInfo = imMessageDao.getSingleChatMessageInfoById(object);
        return imMessageInfo;
    }


    @Override
    public ImMessage getChatGroupMessageInfoById(JSONObject object) {
        ImMessage imMessageInfo = imMessageDao.getChatGroupMessageInfoById(object);
        return imMessageInfo;
    }

    @Override
    public int addImMessage(ImMessage imMessage) {
        return imMessageDao.addImMessage(imMessage);
    }

    @Override
    public ImMessage getImMessage(int id) {
        return imMessageDao.getImMessage(id);
    }

    @Override
    public ImMessage getImMessageByGroupId(Integer userId, Integer chatGroupId) {
        return imMessageDao.getImMessageByGroupId(userId,chatGroupId);
    }

    @Override
    public int delete(int id) {
        return imMessageDao.delete(id);
    }

    @Override
    public int updateImMessage(ImMessage imMessage) {
        return imMessageDao.updateImMessage(imMessage);
    }

    @Override
    public int updateUnreadNum(ImMessage imMessage) {
        return imMessageDao.updateUnreadNum(imMessage);
    }

    @Override
    public int updateAtFlag(ImMessage imMessage) {
        return imMessageDao.updateAtFlag(imMessage);
    }

    @Override
    public int deleteByChatGroupId(int chatGroupId,int userId) {
        return imMessageDao.deleteByChatGroupId(chatGroupId,userId);
    }

    @Override
    public int countUnreadNum(int userId) {
        int unReadNum = 0;
        Integer count = imMessageDao.countUnreadNum(userId);
        if(null != count){
            unReadNum = count;
        }
        return unReadNum;
    }
}
