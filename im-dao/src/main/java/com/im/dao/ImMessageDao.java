package com.im.dao;
import com.alibaba.fastjson.JSONObject;
import com.im.entity.ImMessage;
import com.im.util.MyBatisDao;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ImMessageDao {
    @Select("SELECT case when b.groupType=0 then (SELECT tu.realName from `user` tu,im_chat_group_user icgu WHERE tu.id=icgu.userId and icgu.chatGroupId=b.id and a.userId<>icgu.userId) else b.name end as chatGroupName,\n" +
            "case b.groupType when 1 then '' when 2 then '' else (SELECT tu.photoPath from `user` tu,im_chat_group_user icgu WHERE icgu.chatGroupId=b.id and tu.id=icgu.userId and a.userId<>icgu.userId) end as url,\n" +
            "c.content,c.contentType,c.messageType,c.sendTime as updateTime,b.groupType,a.*,tu.realName as sendName,cgu.silenceFlag \n" +
            "FROM `im_message` a \n" +
            "INNER JOIN im_chat_group b ON a.chatGroupId = b.id and (b.`status`=0 or b.`status` is NULL) \n" +
            "LEFT JOIN ( SELECT im.* FROM im_chat_group_message im INNER JOIN ( SELECT max(t.id) AS id FROM im_chat_group_message t GROUP BY t.chatGroupId ) ti ON ti.id = im.id ) c ON a.chatGroupId = c.chatGroupId \n" +
            "LEFT JOIN `user` tu ON c.fromId=tu.id \n"+
            "LEFT JOIN im_chat_group_user cgu ON c.chatGroupId=cgu.chatGroupId WHERE cgu.userId=#{userId}  \n"+
            "and a.userId = #{userId} and (case when b.groupType=3 and b.`status` is not null then b.`status` else 0 end)=0 " +
            "and case when #{groupName} is not null and #{groupName}!=''  then b.name like '%${groupName}%' else 1=1 end\n" +
            "ORDER BY b.groupType DESC,CASE when a.topFlag is null then 0 else a.topFlag END DESC,c.sendTime DESC")
    List<ImMessage> listAllByUserId(Map<String, Object> map);

    @Insert("insert into im_message (userId,chatGroupId,createTime,createId,unreadNum,atFlag,topFlag) " +
            "values(#{userId},#{chatGroupId},#{createTime},#{createId},#{unreadNum},#{atFlag},#{topFlag})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addImMessage(ImMessage imMessage);

    @Select("select * from im_message where id=#{id}")
    ImMessage getImMessage(@Param("id") int id);

    @Select("select * from im_message where chatGroupId=#{chatGroupId} and userId=#{userId}")
    ImMessage getImMessageByGroupId(@Param("userId") Integer userId, @Param("chatGroupId") int chatGroupId);

    @Delete("delete from im_message where id=#{id}")
    int delete(@Param("id") int id);

    @Delete("delete from im_message where chatGroupId=#{chatGroupId} and userId=#{userId}")
    int deleteByChatGroupId(@Param("chatGroupId") int chatGroupId, @Param("userId") int userId);

    @Update("update im_message set unreadNum=#{unreadNum} where id=#{id}")
    int updateUnreadNum(ImMessage imMessage);

    @Update("update im_message set atFlag=#{atFlag} where id=#{id}")
    int updateAtFlag(ImMessage imMessage);

    @Update("update im_message set unreadNum=#{unreadNum},atFlag=#{atFlag} where id=#{id}")
    int updateImMessage(ImMessage imMessage);

    @Select("SELECT SUM(unreadNum) FROM im_message m LEFT JOIN im_chat_group cg ON m.chatGroupId=cg.id WHERE m.userId=#{userId} AND cg.status=0 GROUP BY userId")
    Integer countUnreadNum(@Param("userId") int userId);


    /**
     * 查询新的会话信息(单聊)
     * @param object
     * @return
     */
    @Select("SELECT " +
            " im_message.id," +
            " im_chat_group.groupType, " +
            " im_message.chatGroupId, " +
            " im_chat_group.createTime, " +
            " im_chat_group.createId, " +
            " im_message.unreadNum, " +
            " im_message.atFlag, " +
            " im_message.topFlag, " +
            " im_message.userId, " +
            " lastmsg.content, " +
            " lastmsg.contentType, " +
            " lastmsg.messageType, " +
            " lastmsg.sendTime AS updateTime, " +
            " lastmsg.sendName, " +
            " lastmsg.silenceFlag,  " +
            " chat.url,  " +
            " chat.chatGroupName " +
            " FROM " +
            " im_message AS im_message " +
            " JOIN im_chat_group AS im_chat_group ON im_chat_group.id = im_message.chatGroupId " +
            " LEFT JOIN (" +
            " select u2.photoPath as url,u2.realName as chatGroupName ,im_message.chatGroupId from `user` as  u2  " +
            " JOIN im_message " +
            " on im_message.userId=u2.id WHERE im_message.chatGroupId=#{chatGroupId} and im_message.userId <> #{userId} " +
            ") as chat " +
            " on im_chat_group.id=chat.chatGroupId"+
            " LEFT JOIN ( " +
            " SELECT " +
            " im_chat_group_message.content, " +
            " im_chat_group_message.contentType, " +
            " im_chat_group_message.messageType, " +
            " im_chat_group_message.sendTime, " +
            " im_chat_group_message.chatGroupId, " +
            " u.realName AS sendName, " +
            " ( SELECT im_chat_group_user.silenceFlag FROM im_chat_group_user AS im_chat_group_user WHERE im_chat_group_user.chatGroupId = #{chatGroupId} AND userId = #{userId} ) AS silenceFlag  " +
            " FROM " +
            " im_chat_group_message AS im_chat_group_message, " +
            " `user` AS u  " +
            " WHERE " +
            " im_chat_group_message.chatGroupId = #{chatGroupId}  " +
            " AND u.id = im_chat_group_message.fromId  " +
            " ORDER BY " +
            " im_chat_group_message.id DESC  " +
            " LIMIT 1  " +
            " ) AS lastmsg ON im_chat_group.id = lastmsg.chatGroupId  " +
            " WHERE " +
            " im_message.chatGroupId = #{chatGroupId} " +
            " AND userId =#{userId}")
    ImMessage getSingleChatMessageInfoById(JSONObject object);


    /**
     * 群聊
     * @param object
     * @return
     */
    @Select("SELECT " +
            " im_message.id," +
            " im_chat_group.groupType, " +
            " im_chat_group.`name` as chatGroupName, "+
            " im_message.chatGroupId, " +
            " im_chat_group.createTime, " +
            " im_chat_group.createId, " +
            " im_message.unreadNum, " +
            " im_message.atFlag, " +
            " im_message.topFlag, " +
            " im_message.userId, " +
            " lastmsg.content, " +
            " lastmsg.contentType, " +
            " lastmsg.messageType, " +
            " lastmsg.sendTime AS updateTime, " +
            " lastmsg.sendName, " +
            " lastmsg.silenceFlag  " +
            " FROM " +
            " im_message AS im_message " +
            " JOIN im_chat_group AS im_chat_group ON im_chat_group.id = im_message.chatGroupId " +
            " LEFT JOIN ( " +
            " SELECT " +
            " im_chat_group_message.content, " +
            " im_chat_group_message.contentType, " +
            " im_chat_group_message.messageType, " +
            " im_chat_group_message.sendTime, " +
            " im_chat_group_message.chatGroupId, " +
            " u.realName AS sendName, " +
            " ( SELECT im_chat_group_user.silenceFlag FROM im_chat_group_user AS im_chat_group_user WHERE im_chat_group_user.chatGroupId = #{chatGroupId} AND userId = #{userId} ) AS silenceFlag  " +
            " FROM " +
            " im_chat_group_message AS im_chat_group_message, " +
            " `user` AS u  " +
            " WHERE " +
            " im_chat_group_message.chatGroupId = #{chatGroupId}  " +
            " AND u.id = im_chat_group_message.fromId  " +
            " ORDER BY " +
            " im_chat_group_message.id DESC  " +
            " LIMIT 1  " +
            " ) AS lastmsg ON im_chat_group.id = lastmsg.chatGroupId  " +
            " WHERE " +
            " im_message.chatGroupId = #{chatGroupId} " +
            " AND userId =#{userId}")
    ImMessage getChatGroupMessageInfoById(JSONObject object);
}
