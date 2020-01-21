package com.im.controller.user;


import com.alibaba.fastjson.JSONObject;
import com.im.UserService;
import com.im.bean.MsgInfo;
import com.im.entity.User;
import com.im.enums.CommonEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author WD
 * @title: UserController
 * @projectName wjw-back
 * @description: im用户相关的信息
 * @date 2019/11/18--17:50
 */
@RestController
@RequestMapping(value = "/imUser")
public class UserController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
   private UserService userService;

    @ResponseBody
    @PostMapping(value = "/getUserInfoById")
     public MsgInfo getUserInfoById(@RequestBody JSONObject object){
           MsgInfo result=new MsgInfo(CommonEnum.FAIL);
           try {
               Integer userId = object.getInteger("userId");
               if (userId==null){
                   result.setMsg("参数丢失");
                   return result;
               }
               User userInfo = userService.getUserById(userId);
               if (userInfo!=null){
                   result.setCodeAndMsg(CommonEnum.SUCCESS);
                   result.setData(userInfo);
               }
           }catch (Exception e){
               logger.info("查询用户信息触发异常",e);
           }
           return result;
     }



     /**
      * @Author WD
      * @Description 修改用户信息
      * @Date 17:10 2019/11/29
     */
     @ResponseBody
     @PostMapping(value = "/updatePhotoPath")
     public MsgInfo updatePhotoPath(@RequestBody JSONObject param){
         MsgInfo result=new MsgInfo(CommonEnum.FAIL);
         try {
             if (param.isEmpty()){
                 result.setMsg("参数丢失");
                 return result;
             }
             Integer userId=param.getInteger("id");
             String  photoPath=param.getString("photoPath");
             User user=new User();
             user.setId(userId);
             user.setPhotoPath(photoPath);
             boolean flag=  userService.updatePhotoPath(user);
             if (flag){
                 result.setCodeAndMsg(CommonEnum.SUCCESS);
                 result.setData(flag);
             }
         }catch (Exception e){
             logger.info("修改用户信息触发异常",e);
         }
         return result;
     }


     /**
      * @Author WD
      * @Description 查询所有内部的部分以及部门的人数
      * @Date 14:25 2019/11/25
     */
    @ResponseBody
    @PostMapping(value = "/getAllDepartment")
     public MsgInfo getAllDepartment(){
         MsgInfo result=new MsgInfo(CommonEnum.FAIL);
         try {
             List<HashMap<Integer,Object>> list=  userService.getAllDepartments();
             if (list!=null){
                 result.setCodeAndMsg(CommonEnum.SUCCESS);
                 result.setData(list);
             }
         }catch (Exception e){
             result.setMsg("查询部门处罚异常");
             logger.info("查询所有部门触发异常",e);
         }
         return  result;
     }

    /**
     * 根据部门名称和是否是内部人员查询用户列表
     *
     * @param object
     * @return
     */
     @ResponseBody
     @PostMapping(value = "/getDeptMembers")
     public MsgInfo getDeptMembers(@RequestBody JSONObject object){
         MsgInfo result=new MsgInfo(CommonEnum.FAIL);
         try {
             Integer departmentId = object.getInteger("departmentId");
             if (departmentId==null){
                 result.setMsg("参数丢失,请提供部门ID");
                 return result;
             }
             User user=new User();
             user.setDepartmentId(departmentId);
             user.setInternalFlag(true);
             List<User> list=  userService.getDeptMembersByDeptId(user);
             if (list!=null){
                 result.setCodeAndMsg(CommonEnum.SUCCESS);
                 result.setData(list);
             }
         }catch (Exception e){
             result.setMsg("查询部门处罚异常");
             logger.info("查询所有部门触发异常",e);
         }
         return  result;
     }

    /**
     * 获取通讯录列表(type=0,表示普通用户列表,1表示转格式,用于前端渲染穿梭框)
     * @param object
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/getContactsList")
     public MsgInfo getContactsList(@RequestBody JSONObject object){
         MsgInfo msgInfo=new MsgInfo(CommonEnum.FAIL);
         try {
             List<JSONObject> list=  userService.getContactsList(object);
             if (list!=null){
                 msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
                 msgInfo.setData(list);
                 return msgInfo;
             }
         }catch (Exception e){
             logger.info("获取通讯录列表触发异常",e);
         }
         return msgInfo;
     }
}
