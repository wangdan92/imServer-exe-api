package com.im;

import com.im.entity.ImGroup;
import com.im.entity.User;

import java.util.List;

public interface ImGroupService {

    List<ImGroup> getChatGroups(Integer id);

    List<User> getChatUserList(String chatId);
}
