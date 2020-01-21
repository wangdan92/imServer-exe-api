package com.im;


import com.im.entity.ImChatGroupMessage;

import java.util.List;

/**
 * @author ZhangPeng
 * @date 2019/9/26
 */
public interface ImChatGroupMessageService {
       int addImChatGroupMessage(ImChatGroupMessage imChatGroupMessage);

    List<ImChatGroupMessage> list(ImChatGroupMessage imChatGroupMessage);

    int delete(int id);

    ImChatGroupMessage getImChatGroupMessageById(int id);
}
