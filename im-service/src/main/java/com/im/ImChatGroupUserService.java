package com.im;


import com.im.bean.MsgInfo;
import com.im.entity.ImChatGroup;
import com.im.entity.ImChatGroupUser;
import com.im.model.UserContactsModel;

import java.util.HashMap;
import java.util.List;

public interface ImChatGroupUserService {
    List<ImChatGroupUser> listByChatGroupId(int chatGroupId);

    List<UserContactsModel> listByChatGroupIdForDelete(int chatGroupId);

    List<UserContactsModel> listByChatGroupIdForAt(int chatGroupId, int userId);

    List<UserContactsModel> listByChatGroupIdForAdd(int chatGroupId);

    List<ImChatGroupUser> listByChatGroupIdAndUserId(int chatGroupId, int userId);

    int addImChatGroupUser(Integer groupType, ImChatGroupUser imChatGroupUser);

    MsgInfo addImChatGroupUsers(ImChatGroup imChatGroup);

    int updateImChatGroupUser(ImChatGroupUser imChatGroupUser);

    MsgInfo deleteImChatGroupUsers(ImChatGroup imChatGroup);

    int countChatGroupUserNum(int chatGroupId);

    ImChatGroupUser getDetail(int id);

    ImChatGroupUser getDetailByUserIdAndChatGroupId(int userId, int chatGroupId);

    int updateSilenceFlag(ImChatGroupUser imChatGroupUser);
    /**
     * @Author WD
     * @Description //查询我的群组列表
     * @Date 13:54 2019/11/30
    */
    List<HashMap<Integer, Object>> listChatByUserId(Integer userId);
}
