package com.im.impl;

import com.im.ImGroupService;
import com.im.entity.ImGroup;
import com.im.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImGroupServiceImpl implements ImGroupService {
    @Override
    public List<ImGroup> getChatGroups(Integer id) {
        return null;
    }

    @Override
    public List<User> getChatUserList(String chatId) {
        return null;
    }
}
