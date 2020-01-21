package com.im.dao;

import com.im.entity.ImChatGroupMessage;
import com.im.util.MyBatisDao;
import org.apache.ibatis.annotations.*;

import java.util.List;

@MyBatisDao
public interface ImChatGroupMessageDao {
    @Select("select * from im_chat_group_message t where t.chatGroupId=#{chatGroupId} order by id desc")
    List<ImChatGroupMessage> list(ImChatGroupMessage imChatGroupMessage);

    @Insert("insert into im_chat_group_message (content,sendTime,fromId,chatGroupId,contentType,messageType,refId) " +
            "values(#{content},#{sendTime},#{fromId},#{chatGroupId},#{contentType},#{messageType},#{refId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addImChatGroupMessage(ImChatGroupMessage imChatGroupMessage);

    @Delete("delete from im_chat_group_message where id=#{id}")
    int delete(@Param("id") int id);

    @Select("select * from im_chat_group_message t where t.id=#{id}")
    ImChatGroupMessage getImChatGroupMessageById(@Param("id") int id);
}
