package com.im.dao;

import com.im.entity.ImChatGroupUser;
import com.im.model.UserContactsModel;
import com.im.util.MyBatisDao;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

@MyBatisDao
public interface ImChatGroupUserDao {
    @Select("select a.*,b.realName,b.photoPath from im_chat_group_user a LEFT JOIN `user` b ON a.userId=b.id where a.chatGroupId=#{chatGroupId}")
    List<ImChatGroupUser> listByChatGroupId(@Param("chatGroupId") int chatGroupId);

    List<UserContactsModel> listByChatGroupIdForDelete(@Param("chatGroupId") int chatGroupId);

    List<UserContactsModel> listByChatGroupIdForAt(@Param("chatGroupId") int chatGroupId, @Param("userId") int userId);

    List<UserContactsModel> listByChatGroupIdForAdd(@Param("chatGroupId") int chatGroupId);

    @Select("SELECT a.*, b.realName, b.photoPath FROM im_chat_group_user a\n" +
            "LEFT JOIN im_chat_group c ON a.chatGroupId=c.id\n" +
            "LEFT JOIN `user` b ON a.userId = b.id\n" +
            "where  case when c.groupType=0 then a.userId!=#{userId} else 1=1 end and a.chatGroupId=#{chatGroupId}")
    List<ImChatGroupUser> listByChatGroupIdAndUserId(@Param("chatGroupId") int chatGroupId, @Param("userId") int userId);

    @Insert("insert into im_chat_group_user (chatGroupId,createTime,createId,userId,updateTime,silenceFlag) " +
            "values(#{chatGroupId},#{createTime},#{createId},#{userId},#{updateTime},false)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addImChatGroupUser(ImChatGroupUser imChatGroupUser);

    @Update("update im_chat_group_user set name=#{name},notice=#{notice},master=#{master},status=#{status} where id=#{id}")
    int updateImChatGroupUser(ImChatGroupUser imChatGroupUser);

    @Delete("delete from im_chat_group_user where id=#{id}")
    int delete(@Param("id") int id);


    @Delete("delete from im_chat_group_user where chatGroupId=#{chatGroupId}")
    int deleteByChatGroupId(@Param("chatGroupId") int chatGroupId);

    @Select("select * from im_chat_group_user where id=#{id}")
    ImChatGroupUser getDetail(@Param("id") int id);

    @Select("select * from im_chat_group_user where chatGroupId=#{chatGroupId} and userId=#{userId}")
    ImChatGroupUser getDetailByUserIdAndChatGroupId(@Param("userId") int userId, @Param("chatGroupId") int chatGroupId);

    @Select("select count(0) from im_chat_group_user where chatGroupId=#{chatGroupId}")
    int countChatGroupUserNum(@Param("chatGroupId") int chatGroupId);

    @Update("update im_chat_group_user set silenceFlag=#{silenceFlag} where userId=#{userId} and chatGroupId=#{chatGroupId}")
    int updateSilenceFlag(ImChatGroupUser imChatGroupUser);

    /**
     * @Author WD
     * @Description //查询我的群组列表,群组类型为1和2 ,普通群组和任务群组
     * @Date 13:57 2019/11/30
    */
    @Select("select chatgroup.*,group_user.silenceFlag from im_chat_group_user as group_user " +
            " left join im_chat_group as chatgroup " +
            " on group_user.chatGroupId=chatgroup.id " +
            " WHERE group_user.userId=#{userId}" +
            " and chatgroup.groupType in ('1','2')")
    @MapKey("id")
    List<HashMap<Integer,Object>> listChatByUserId(Integer userId);
}
