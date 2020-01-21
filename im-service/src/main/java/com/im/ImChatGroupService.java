package com.im;


import com.im.bean.MsgInfo;
import com.im.entity.ImChatGroup;

import java.util.List;

public interface ImChatGroupService {
    List<ImChatGroup> listAll();

    List<ImChatGroup> listHistory(Integer userId, String searchMsg);

    MsgInfo addImChatGroup(ImChatGroup imChatGroup);

    ImChatGroup getImChatGroup(int id);

    ImChatGroup getImChatGroupByUserId(int id, int userId);

    ImChatGroup getImChatGroupSingleChat(int userId1, int userId2);

    ImChatGroup getImChatGroupSystem(long master);

    ImChatGroup addSingleChat(int userId1, int userId2);

    ImChatGroup getImChatGroupByTaskId(Integer taskId);

    int updateImChatGroup(ImChatGroup imChatGroup);

    int updateImChatGroupName(ImChatGroup imChatGroup);

    MsgInfo updateImChatGroupNotice(ImChatGroup imChatGroup);

    int updateImChatGroupStatusByRefId(int refId);

    int delete(int id);
}
