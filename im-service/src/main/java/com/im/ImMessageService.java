package com.im;

import com.alibaba.fastjson.JSONObject;
import com.im.entity.ImMessage;

import java.util.List;
import java.util.Map;

public interface ImMessageService {
    List<ImMessage> listAllByUserId(Map<String, Object> map);

    int addImMessage(ImMessage imMessage);

    ImMessage getImMessage(int id);

    ImMessage getImMessageByGroupId(Integer userId, Integer chatGroupId);

    int delete(int id);

    int updateImMessage(ImMessage imMessage);

    int updateUnreadNum(ImMessage imMessage);

    int updateAtFlag(ImMessage imMessage);

    int deleteByChatGroupId(int chatGroupId, int userId);

    int countUnreadNum(int userId);

    ImMessage getSingleChatMessageInfoById(JSONObject object);

    ImMessage getChatGroupMessageInfoById(JSONObject object);
}
