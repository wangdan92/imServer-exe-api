package com.im.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.im.ImChatGroupMessageService;
import com.im.dao.ImChatGroupMessageDao;
import com.im.entity.ImChatGroupMessage;
import com.im.util.KeyWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhangPeng
 * @date 2019/9/26
 */
@Service
public class ImChatGroupMessageServiceImpl implements ImChatGroupMessageService {
    @Autowired
    private ImChatGroupMessageDao imChatGroupMessageDao;

    @Override
    public int addImChatGroupMessage(ImChatGroupMessage imChatGroupMessage) {
        Short contentType = imChatGroupMessage.getContentType();
        if (contentType==1){
            String content = imChatGroupMessage.getContent();
            JSONObject object = JSONObject.parseObject(content);
            String text = object.getString("text");
            text = KeyWordUtil.checkBadWordAndReplace(text, 2);
            object.replace("text",text);
            content= object.toJSONString();
            imChatGroupMessage.setContent(content);
        }

        return imChatGroupMessageDao.addImChatGroupMessage(imChatGroupMessage);
    }

    @Override
    public List<ImChatGroupMessage> list(ImChatGroupMessage imChatGroupMessage) {
        if (imChatGroupMessage != null && imChatGroupMessage.getPageNum() != 0) {
            PageHelper.startPage(imChatGroupMessage.getPageNum(), imChatGroupMessage.getPageSize());
            PageHelper.orderBy(" id desc ");
        }
        return imChatGroupMessageDao.list(imChatGroupMessage);
    }

    @Override
    public int delete(int id) {
        return imChatGroupMessageDao.delete(id);
    }

    @Override
    public ImChatGroupMessage getImChatGroupMessageById(int id) {
        return imChatGroupMessageDao.getImChatGroupMessageById(id);
    }
}
