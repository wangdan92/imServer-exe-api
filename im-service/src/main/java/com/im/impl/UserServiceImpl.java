package com.im.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.im.RedisService;
import com.im.UserService;
import com.im.constant.CacheConstants;
import com.im.constant.Constants;
import com.im.dao.DepartmentDao;
import com.im.dao.UserDao;
import com.im.entity.Department;
import com.im.entity.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ZhangPeng
 * @date 2017/12/3
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DepartmentDao departmentDao;


    @Override
    public List<User> listUser(User user) {
        if (user != null && user.getPageNum() != 0) {
            PageHelper.startPage(user.getPageNum(), user.getPageSize(), true);
        }
        List<User> userList = userDao.listUser(user);
        for (User u:userList) {
            this.addExtendInfoToUser(u);
        }
        return userList;
    }

    @Override
    public List<User> listByType(User user) {
        List<User> userList = userDao.listByType(user);
        for (User u:userList) {
            this.addExtendInfoToUser(u);
        }
        return userList;
    }

    @Override
    public List<User> listByIds(List<Integer> idList) {
        return userDao.listByIds(idList);
    }


    @Override
    public User getUserById(int id) {
        User user;
        String key = CacheConstants.USER + Constants.SPLIT_UNDERLINE + id;
        String value = redisService.getData(key);
        if (value != null) {
            user = JSON.parseObject(value, User.class);
        } else {
            user = userDao.getUserById(id);
            this.addExtendInfoToUser(user);
            if (user != null) {
                redisService.addData(key, JSON.toJSONString(user));
            }
        }
        return user;
    }


    @Override
    public Boolean updatePhotoPath(User user) {
        boolean isSuccess = false;
        if (userDao.udpatePhotoPath(user)==1){
            isSuccess=true;
            User userById = userDao.getUserById(user.getId());
            addUserCache(userById);
        }
        return isSuccess;
    }




    @Override
    public void updateUserPassword(User user) {
        this.userDao.udpateUserPassword(user);
        this.addExtendInfoToUser(user);
        addUserCache(user);
    }

    @Override
    public User getUserByUserNameAndPassword(String userName, String password) {
        User user;
        String key = CacheConstants.USER + Constants.SPLIT_UNDERLINE + userName;
        String value = redisService.getData(key);
        if (value != null) {
            user = JSON.parseObject(value, User.class);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } else {
            user = userDao.getUserByUserNameAndPassword(userName, password);
            this.addExtendInfoToUser(user);
            if (user != null) {
                redisService.addData(key, JSON.toJSONString(user));
            }
        }
        return user;
    }

    @Override
    public User getUserByToken(HttpServletRequest request) {
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        String[] param = authorization.split(Constants.SPLIT_UNDERLINE);
        int needLength = 2;
        if (param.length != needLength) {
            return null;
        }
        //使用userName和源token简单拼接成的token，可以增加加密措施
        String userName = new String(Base64.decodeBase64(param[0]));
        User user = getUserByUserName(userName);
        this.addExtendInfoToUser(user);
        return user;
    }


    @Override
    public User getUserByToken(String token) {
        String[] param = token.split(Constants.SPLIT_UNDERLINE);
        int needLength = 2;
        if (param.length != needLength) {
            return null;
        }
        //使用userName和源token简单拼接成的token
        User user = getUserByUserName(param[0].split(Constants.SPLIT_LINE_THROUGH)[1]);
        this.addExtendInfoToUser(user);
        return user;
    }




    private void addUserCache(User user) {
        String userNameKey = CacheConstants.USER + Constants.SPLIT_UNDERLINE + user.getUserName();
        String idKey = CacheConstants.USER + Constants.SPLIT_UNDERLINE + user.getId();
        String value = JSON.toJSONString(user);
        if(null!=user.getMobile()&&user.getMobile().length()>0){
            String mobileKey = CacheConstants.USER + Constants.SPLIT_UNDERLINE + user.getMobile();
            redisService.deleteData(mobileKey);
        }
        redisService.addData(userNameKey, value);
        redisService.addData(idKey, value);
    }

    @Override
    public void deleteUserCache(User user) {
        String userNameKey = CacheConstants.USER + Constants.SPLIT_UNDERLINE + user.getUserName();
        redisService.deleteData(userNameKey);
        String idKey = CacheConstants.USER + Constants.SPLIT_UNDERLINE + user.getId();
        redisService.deleteData(idKey);
    }

    @Override
    public boolean checkUserNameIllegal(String userName) {
        if (userDao.checkUserNameIllegal(userName) == 1) {
            return false;
        }
        return true;
    }




    @Override
    public List<User> getUserByDepartment(int departmentId) {
        List<User> userList = userDao.getUserByDepartment(departmentId);
        for (User u:userList) {
            this.addExtendInfoToUser(u);
        }
        return userList;
    }

    @Override
    public List<User> getUserByValid(int departmentId) {
        List<User> userList = userDao.getUserByValid(departmentId);
        for (User u:userList) {
            this.addExtendInfoToUser(u);
        }
        return userList;
    }

    @Override
    public List<User> listByBureauIdAndDepartmentId(Integer bureauId, Integer departmentId) {
        List<User> userList =  userDao.listByBureauIdAndDepartmentId(bureauId, departmentId);
        for (User u:userList) {
            this.addExtendInfoToUser(u);
        }
        return userList;
    }

    @Override
    public List<User> allList() {
        List<User> userList =   userDao.allList();
        for (User u:userList) {
            this.addExtendInfoToUser(u);
        }
        return userList;
    }

    @Override
    public User activeUser(User user) {
        userDao.activeUser(user);
        this.addExtendInfoToUser(user);
        addUserCache(user);
        return user;
    }


    @Override
    public List<User> allUser(User user) {
        return userDao.allUser(user);
    }

    @Override
    public User adminUser() {
        return userDao.adminUser();
    }

    @Override
    public List<User> listServiceUser(User user) {
        if (user != null && user.getPageNum() != 0) {
            PageHelper.startPage(user.getPageNum(), user.getPageSize(), true);
        }
        List<User> userList = userDao.listServiceUser(user);
        for (User u:userList) {
            this.addExtendInfoToUser(u);
        }
        return userList;
    }

    @Override
    public List<User> userMobileList(String mobile) {
        List<User> userList = userDao.userMobileList(mobile);
        for (User u:userList) {
            this.addExtendInfoToUser(u);
        }
        return userList;
    }


    @Override
    public List<HashMap<Integer,Object>> getAllDepartments() {
        List<HashMap<Integer,Object>> depts = userDao.getAllDepts();
        for (HashMap dept : depts) {
            long expansion = (long) dept.get("expansion");
            if (expansion==1){
                dept.replace("expansion", true);
            }else{
                dept.replace("expansion", false);
            }
        }
        return depts;
    }

    /**
     * 根据部门名称查询用户
     * @param param
     * @return
     */
    @Override
    public List<User> getDeptMembersByDeptId(User param) {
        return  userDao.getDeptMembersByDeptId(param);
    }

    /**
     * 获取所有内部人员列表信息
     * @param param
     * @return
     */
    @Override
    public List<JSONObject> getContactsList(JSONObject param) {
        Integer currentUserId = param.getInteger("currentUserId");
        List<JSONObject> userList=null;
        List<HashMap<Integer, Object>> contactsList = userDao.getContactsList(param);
        if(contactsList!=null&&contactsList.size()>0){
            userList=new ArrayList<JSONObject>();
            for (HashMap<Integer, Object> userObj : contactsList) {
                JSONObject obj=new JSONObject();
                Integer id= (Integer) userObj.get("id");
                obj.put("key",id.toString());
                obj.put("realName",userObj.get("realName"));
                obj.put("photoPath", userObj.get("photoPath"));
                obj.put("departmentName", userObj.get("departmentName"));
                if(currentUserId!=null&&currentUserId.equals(id)){
                    obj.put("disabled",true);
                }else{
                    obj.put("disabled",false);
                }
                userList.add(obj);
            }
        }
       return userList;
    }


    @Override
    public List<User> listByDepartmentId(int departmentId) {
        return userDao.listByDepartmentId(departmentId);
    }

    @Override
    public List<User> listByDepartmentIdAndPosition(int departmentId) {
        return userDao.listByDepartmentIdAndPosition(departmentId);
    }

    @Override
    public User getUserByRoleName(String roleName) {
        return userDao.getUserByRoleName(roleName);
    }



    /**给User添加department和bureau信息*/
    @Override
    public User addExtendInfoToUser(User user) {
        if(null!=user){
            if(null!=user.getDepartmentId()){
                Department department = departmentDao.getDepartmentById(user.getDepartmentId());
                if(null!=department&&null!=department.getDepartmentName()){
                    user.setDepartments(department.getDepartmentName());
                }
            }
        }
        return user;
    }


    @Override
    public User getUserByUserName(String userName) {
        User user;
        String key = CacheConstants.USER + Constants.SPLIT_UNDERLINE + userName;
        String value = redisService.getData(key);
        if (value != null) {
            user = JSON.parseObject(value, User.class);
        } else {
            user = userDao.getUserByUserName(userName);
            if (null!=user) {
                user=this.addExtendInfoToUser(user);
                redisService.addData(key, JSON.toJSONString(user));
            }
        }
        return user;
    }

}
