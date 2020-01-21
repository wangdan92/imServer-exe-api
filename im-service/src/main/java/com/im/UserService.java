package com.im;
import com.alibaba.fastjson.JSONObject;
import com.im.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author zp
 * @date 2012-6-1
 */
public interface UserService {




	/**
	 * 更改用户头像 1
	 * @param user
	 * @return
	 */
	Boolean updatePhotoPath(User user);



	/** 2
	 * 加载用户详情
	 * @param id 唯一编号
	 * @return 用户详情
	 */
	User getUserById(int id);

	/**
	 * @Author WD 3
	 * @Description 根据字段 positions 分组查询所有的部门以及部门里面的人数
	 * @Date 14:33 2019/11/25
	 */
	List<HashMap<Integer,Object>> getAllDepartments();



	/** 4
	 * 根据部门名称,字段(positions)查询用户
	 * @param user
	 * @return
	 */
	List<User> getDeptMembersByDeptId(User user);


	/**
	 *  获取通讯录列表(type=0,表示普通用户列表,1表示转格式,用于前端渲染穿梭框)
	 *
	 * @Author WD
	 * @Description  获取通讯录列表
	 * @Date 19:43 2019/11/27
	 */
	List<JSONObject> getContactsList(JSONObject object);




	/**
	 * 获取用户列表
	 * @param user User
	 * @return 用户列表
	 */
	List<User> listUser(User user);

	/**
	 * 根据类型获取用户
	 * @param user
	 * @return
	 */
	List<User> listByType(User user);

	/**
	 * 通过id列表获取User列表
	 * @param idList
	 * @return
	 */
	List<User> listByIds(List<Integer> idList);











	/**
	 * 用户修改密码
	 * @param user
	 */
	void updateUserPassword(User user);

	/**
	 * 根据用户名和密码获取用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
    User getUserByUserNameAndPassword(String userName, String password);

	/**
	 * 根据token获取用户信息
	 * @param request
	 * @return
	 */
	User getUserByToken(HttpServletRequest request);

	User getUserByToken(String token);







	/**
	 * 检查手机号是否存在
	 * @param userName 用户名
	 * @return int 0存在,1不存在
	 */
	boolean checkUserNameIllegal(String userName);






    List<User> getUserByDepartment(int id);

    List<User> getUserByValid(int id);

	List<User> listByBureauIdAndDepartmentId(Integer bureauId, Integer departmentId);

	List<User> allList();



	//APP端账号注册，找回.
	User activeUser(User user);

	List<User> userMobileList(String mobile);

	//所有用户列表
	List<User> allUser(User user);



	//获取第一个管理员
	User adminUser();

	List<User> listServiceUser(User user);

	void deleteUserCache(User user);











	/**
	 * 法务管理 查询该部门的部门负责人
	 * @param departmentId
	 * @return
	 */
	List<User> listByDepartmentId(int departmentId);

	/**
	 * 房屋设备管理 查询该部门的部门负责人
	 * @param departmentId
	 * @return
	 */
	List<User> listByDepartmentIdAndPosition(int departmentId);


	/**
	 * 获取党办主任
	 * @return 党办主任数据
	 */
	User getUserByRoleName(String roleName);

	/**添加部门信息*/
	User addExtendInfoToUser(User user);

	/**
	 * 根据用户名加载用户信息
	 * @param name
	 * @return
	 */
	User getUserByUserName(String name);
}