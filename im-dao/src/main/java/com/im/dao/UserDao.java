package com.im.dao;
import com.alibaba.fastjson.JSONObject;
import com.im.entity.User;
import com.im.util.MyBatisDao;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author ZhangPeng
 * @date 2017/12/3
 */
@MyBatisDao
public interface UserDao {

    /**
     * 用户修改照片
     * @param user User
     * @return int 影响记录数
     */
    @Update("update user set photoPath = #{photoPath} where id = #{id}")
    int udpatePhotoPath(User user);

    /**
     * 修改密码
     * @param user User
     * @return int 影响记录数
     */
    @Update("update user set password = #{password} where id = #{id}")
	int udpateUserPassword(User user);

    @Update("update user set password = #{password},active=#{active} where id = #{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int activeUser(User user);

    /**
     * 获取用户列表
     * @param user User
     * @return 用户列表
     */
    List<User> listUser(User user);

    /**
     * 获取所有用户列表
     * @param user User
     * @return 用户列表
     */
    @Select("select * from user")
    List<User> allUser(User user);

    @Select("select * from user where userType=#{userType}")
    List<User> listByType(User user);

    /**
     * 通过id数组获取用户列表
     * @param idList id数组
     * @return 用户列表
     */
    List<User> listByIds(@Param("idList") List<Integer> idList);

    /**
     * 加载用户详情
     * @param id 唯一编号
     * @return 用户详情
     */
    @Select("SELECT u.*,r.name AS roleName,cr.realName AS createRealName,up.realName AS updateRealName\n" +
            "FROM `user` u LEFT JOIN role r ON (u.roleId=r.id)\n" +
            "LEFT JOIN `user` AS cr ON(u.createMan = cr.id)\n" +
            "LEFT JOIN `user` AS up ON(u.updateMan =up.id)\n" +
            "WHERE u.id = #{id}")
    User getUserById(int id);

    /**
     * 加载用户详情
     * @param personnelId 唯一编号
     * @return 用户详情
     */
    @Select("SELECT u.*,r.name AS roleName,cr.realName AS createRealName,up.realName AS updateRealName\n" +
            "FROM `user` u LEFT JOIN role r ON (u.roleId=r.id)\n" +
            "LEFT JOIN `user` AS cr ON(u.createMan = cr.id)\n" +
            "LEFT JOIN `user` AS up ON(u.updateMan =up.id)\n" +
            "WHERE u.personnelId = #{personnelId}")
    User getUserByPersonnelId(int personnelId);

    /**
     * 增加用户
     * @param user User
     * @return int 影响记录数
     */
    @Insert("insert into user (userName,realName,password,adminFlag,telephone,mobile,email,departments,positions,status,roleId,createMan,createTime,updateTime,expireTime,description,address,noticeFlag,bureauId,departmentId,internalFlag,userType,idCard,dataAuthority,merchantId,personnelId,compound,building,floor,room,accountValidity)" +
            "values(#{userName},#{realName},#{password},#{adminFlag},#{telephone},#{mobile},#{email},#{departments},#{positions},#{status},#{roleId},#{createMan},#{createTime},#{updateTime},#{expireTime},#{description},#{address},#{noticeFlag},#{bureauId},#{departmentId},#{internalFlag},#{userType},#{idCard},#{dataAuthority},#{merchantId},#{personnelId},#{compound},#{building},#{floor},#{room},#{accountValidity})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(User user);

    /**
     * 删除用户
     * @param id 唯一编号
     * @return int 影响记录数
     */
    @Delete("delete from user WHERE id = #{id}")
    int deleteUserById(@Param("id") int id);

    /**
     * 更新用户信息
     * @param user User
     * @return int 影响记录数
     */
    int updateUser(User user);

    /**
     * 根据用户名获取用户信息
     * @param userName 用户名
     * @return User 用户对象
     */
    @Select("SELECT u.*,r.name roleName,cr.realName createRealName,up.realName updateRealName \n" +
            "FROM `user` u LEFT JOIN role r ON (u.roleId=r.id) \n" +
            "LEFT JOIN `user` AS cr ON(u.createMan = cr.id) \n" +
            "LEFT JOIN `user` AS up ON(u.updateMan =up.id) \n" +
            "WHERE (u.userName=#{userName} or u.mobile=#{userName}) group by u.id")
    User getUserByUserName(@Param("userName") String userName);

    /**
     * 根据用户名和密码获取用户信息
     * @param userName 用户名
     * @param password 密码
     * @return User 用户对象
     */
    @Select("select * from user u where (u.userName=#{userName} or u.mobile=#{userName}) and u.password=#{password} group by u.id")
    User getUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    /**
     * 根据角色编号获取用户数量
     * @param roleId 角色编号
     * @return int 用户数量
     */
    @Select("select count(id) from user where roleId=#{roleId}")
    int getUserNumByRoleId(@Param("roleId") int roleId);

    /**
     * 更新用户推送信息
     * @param user User
     * @return int 影响记录数
     */
    @Update("update user set pushCode=#{pushCode},pushPlatform=#{pushPlatform} where id=#{id}")
    int updatePushCode(User user);

    /**
     * 获取是否通知标识
     * @param id 唯一编号
     * @return Boolean 是否通知
     */
    @Select("select noticeFlag from user where id=#{id}")
    Boolean getNotifceFlagByUserId(@Param("id") int id);

    /**
     * 更新是否通知标识
     * @param user User
     * @return int 影响记录数
     */
    @Update("update user set noticeFlag=#{noticeFlag} where id=#{id}")
    int updateUserNotifceFlag(User user);

    /**
     * 检查手机号
     * @param userName 用户名
     * @return int 条数
     */
    @Select("select count(*) from user where (userName=#{userName} or mobile=#{userName}) and status=1")
    int checkUserNameIllegal(@Param("userName") String userName);

    /**
     * 获取物业维修人员列表
     * @return 物业维修人员列表
     */
    @Select("select * from user u,role r where u.roleId = r.id and r.description like '%物业维修%' and status=1")
    List<User> repairManList();

    /**
     * 获取物业巡检人员列表
     * @return 物业巡检人员列表
     */
    @Select("select * from user u,role r where u.roleId = r.id and r.description like '%物业巡检%' and status=1")
    List<User> inspectManList();
    /**
     * 获取保安巡检人员列表
     * @return 物业巡检人员列表
     */
    @Select("select * from user u,role r where u.roleId = r.id and r.description like '%保安巡检%' and status=1")
    List<User> inspectGuardList();

    /**
     * 获取巡检人员列表
     * @return 巡检人员列表
     * @param description 角色描述
     */
    @Select("select u.* from user u,role r where u.roleId = r.id and (r.description like concat('%',#{description},'%') or r.name like concat('%',#{description},'%')) and status=1")
    List<User> inspectUserList(@Param("description") String description);

    /**
     * 获取直属主管信息
     * @return 直属主管信息
     */
    @Select("select u2.id,p2.id leaderId,p2.name leaderName,p.departmentId from user u inner join personnel p "+
            "on p.id=u.personnelId inner join personnel p2 on p2.departmentId=p.departmentId and "+
            "(p2.positionId=2 or p2.positionId=6 or p2.positionId=5) inner join user u2 on u2.personnelId=p2.id where u.id=#{id}")
    List<JSONObject> getLeaderByUserId(@Param("id") Integer id);

    @Select("select u2.id,p2.id leaderId,p2.name leaderName,p.departmentId from user u inner join personnel p "+
            "on p.id=u.personnelId inner join personnel p2 on p2.departmentId=p.departmentId and "+
            "p2.positionId=2 inner join user u2 on u2.personnelId=p2.id where u.id=#{id}")
    List<JSONObject> getLeadersByUserId(@Param("id") Integer id);
    /**
     * 获取部门某个头衔的人
     * @return 某个头衔的人
     */
    @Select("select u.id,u.realName,u.departments,p.positionId,pst.name from department d inner join user u on "+
            "u.departmentId=d.id inner join personnel p on p.id=u.personnelId inner join position pst on "+
            "pst.id=p.positionId where (d.id=#{departmentId} or u.bureauId=#{bureauId}) and pst.name=#{title}")
    List<JSONObject> getPersionByTitleDepartmentId(@Param("departmentId") Integer departmentId, @Param("bureauId") Integer bureauId, @Param("title") String title);

    @Select("select *,CONCAT('2-',id) as aid from user where departmentId=#{departmentId}")
    List<User> getUserByDepartment(@Param("departmentId") int departmentId);

    @Select("select *,CONCAT('2-',id) as aid from user where departmentId=#{departmentId} and ((SELECT CURDATE())< accountValidity OR accountValidity IS NULL)")
    List<User> getUserByValid(@Param("departmentId") int departmentId);



    @Select("select * from user where bureauId=#{bureauId} and departmentId=#{departmentId}")
    List<User> listByBureauIdAndDepartmentId(@Param("bureauId") Integer bureauId, @Param("departmentId") Integer departmentId);

    @Select("select * from user")
    List<User> allList();

    /**
     * 通过personnel同步user信息
     * @param user
     * @return
     */
    @Update("update user set realName=#{realName}," +
            "telephone=#{telephone},mobile=#{mobile}," +
            "updateMan=#{updateMan},updateTime=#{updateTime},address=#{address},bureauId=#{bureauId},departmentId=#{departmentId} where id=#{id}")
    int updateUserByPersonnel(User user);

    @Select("select * from user where mobile=#{mobile} ")
    User userMobile(@Param("mobile") String mobile);

    @Select("select * from user where mobile=#{mobile} ")
    List<User> userMobileList(@Param("mobile") String mobile);

    @Select("select * from user where departmentId=#{departmentId} ")
    List<User> departmentIdById(@Param("departmentId") int departmentId);


    @Select("select * from user u,role r where u.roleId = r.id and (r.description like '%管理员%' or r.name like '%管理员%') and status=1 limit 1")
    User adminUser();

    /**
     * 服务用户列表
     * @param user
     * @return
     */
    List<User> listServiceUser(User user);



    @Select("select D.departmentName,U.departmentId,count(U.id) userCounts,false as expansion " +
            "from user AS U,department AS D  where U.internalFlag=1 AND U.departmentId=D.id group by U.departmentId")
    @MapKey("id")
    List<HashMap<Integer,Object>> getAllDepts();


    /**
     * 字段不全,如果其他接口有调用,自己可以补上其他字段
     * 根据部门名称查询用户列表,并且internalFlag=true
     *
     * @param user
     *            internalFlag  是否是内部人员
     *            positions      部门名称
     * @return
     */
    @Select(" select  U.id ,U.userName,U.realName,U.photoPath,U.mobile,U.email,U.`status`,U.adminFlag,D.departmentName " +
            " from user AS U join department AS D ON D.id=U.departmentId where U.internalFlag=#{internalFlag} and D.id=#{departmentId}")
    List<User> getDeptMembersByDeptId(User user);


    /**
     * 查询所有内部人员列表
     *
     * @param param
     * @return
     */
    @Select(" select U.id ,U.userName,U.realName,U.photoPath,U.mobile,U.email,U.`status`,U.adminFlag,D.departmentName " +
            " from user AS U join department AS D ON D.id=U.departmentId where U.internalFlag=1 ")
    @MapKey("id")
    List<HashMap<Integer, Object>> getContactsList(JSONObject param);


    /**
     * 法务管理 查询该部门的部门负责人
     * @param departmentId
     * @return
     */
    @Select("select * from user left join userresource  on user.id=userresource.userid  " +
            "where user.departmentId=#{departmentId} and userresource.code='csfzr'")
    List<User> listByDepartmentId(@Param(value = "departmentId") int departmentId);

    /**
     * 法务管理 查询该部门的部门负责人
     * @param departmentId
     * @return
     */
    @Select("select * from user left join userresource  on user.id=userresource.userid  " +
            "where user.departmentId=#{departmentId} and userresource.code='fgld'")
    List<User> listByDepartmentIdAndPosition(@Param(value = "departmentId") int departmentId);

    @Select("select a.* from user a left join role b on a.roleId = b.id where b.name = #{roleName}")
    User getUserByRoleName(String roleName);

}
