package com.im.dao;


import com.im.entity.ImChatGroup;
import com.im.util.MyBatisDao;
import org.apache.ibatis.annotations.*;

import java.util.List;

@MyBatisDao
public interface ImChatGroupDao {
    @Select("select * from im_chat_group")
    List<ImChatGroup> listAll();

    @Select("SELECT a.* FROM im_chat_group a INNER JOIN im_chat_group_user b ON a.id=b.chatGroupId where a.groupType=2 " +
            "and a.`status`=1 and b.userId=#{userId} and case when #{searchMsg}!='' then a.name like '%${searchMsg}%' else 1=1 end")
    List<ImChatGroup> listHistory(@Param("userId") Integer userId, @Param("searchMsg") String searchMsg);

    @Insert("insert into im_chat_group (name,createTime,createId,notice,master,status,refId,groupType,noticeUpdateTime) " +
            "values(#{name},#{createTime},#{createId},#{notice},#{master},#{status},#{refId},#{groupType},#{noticeUpdateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addImChatGroup(ImChatGroup imChatGroup);

    @Select("select a.*,b.realName as masterName from im_chat_group a INNER JOIN `user` b ON a.`master`=b.id where a.id=#{id}")
    ImChatGroup getImChatGroup(@Param("id") int id);

    @Select("SELECT CASE WHEN t.groupType = 0 THEN ( SELECT tu.realName FROM im_chat_group_user icgu, `user` tu WHERE icgu.chatGroupId = t.id AND icgu.userId = tu.id AND icgu.userId != #{userId} ) ELSE t.`name` END AS `name`, \n" +
            "t.id,t.createTime, t.createId, t.notice, t.`master`, t.`status`, t.refId, t.groupType\n" +
            "FROM im_chat_group t WHERE id=#{id}")
    ImChatGroup getImChatGroupByUserId(@Param("id") int id, @Param("userId") int userId);

    @Select("SELECT a.* FROM im_chat_group a\n" +
            "INNER JOIN im_chat_group_user b ON a.id = b.chatGroupId\n" +
            "INNER JOIN im_chat_group_user c ON a.id = c.chatGroupId AND b.chatGroupId = c.chatGroupId\n" +
            "WHERE a.groupType = 0 AND b.userId = #{userId1} AND c.userId = #{userId2}")
    ImChatGroup getImChatGroupSingleChat(@Param("userId1") int userId1, @Param("userId2") int userId2);

    @Select("select * from im_chat_group where groupType=3 and master=#{master}")
    ImChatGroup getImChatGroupSystem(@Param("master") long master);

    @Select("select * from im_chat_group where refId=#{refId}")
    ImChatGroup getImChatGroupByTaskId(@Param("refId") Integer refId);

    @Update("update im_chat_group set name=#{name},notice=#{notice},master=#{master},status=#{status},noticeUpdateTime=#{noticeUpdateTime} where id=#{id}")
    int updateImChatGroup(ImChatGroup imChatGroup);

    @Delete("delete from im_chat_group where id=#{id}")
    int delete(@Param("id") int id);
}
