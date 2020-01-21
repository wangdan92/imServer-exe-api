package com.im.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.im.LoginLogService;
import com.im.RedisService;
import com.im.TokenService;
import com.im.UserService;
import com.im.bean.MsgInfo;
import com.im.bean.TokenInfo;
import com.im.constant.Constants;
import com.im.entity.LoginLog;
import com.im.entity.User;
import com.im.enums.CommonEnum;
import com.im.util.DateTimeUtil;
import com.im.util.ToJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangPeng
 * @date 2018/9/4
 */
@Api(tags = "登录控制")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private RedisService redisService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value="账户密码登录", notes="code：0-失败，1-成功")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User user, HttpServletResponse response) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setType(0);
        if(user == null && user.getUserName() == null){
            msgInfo.setMsg("请输入用户名或密码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            user = userService.getUserByUserNameAndPassword(user.getUserName(), DigestUtils.md5Hex(user.getPassword()));
            if(user != null){

                if (user.getAccountValidity()!=null){
                    try {
                        Date now = DateTimeUtil.parse(DateTimeUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
                        Date acc = user.getAccountValidity();
                        long i = DateTimeUtil.getDistDate(now,acc);
                        if (i<0){
                            msgInfo.setMsg("账号已过期");
                            return msgInfo;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                msgInfo.setCode(1);
                tokenInfo.setUserName(user.getUserName());
                tokenInfo = tokenService.createTokenOA(tokenInfo);
                JSONObject jsonObject = new JSONObject();
                JSONObject userInfo = ToJSON.objectToJson(user);
                if (user.getUpdateTime()!=null){
                    userInfo.put("updateTime", DateTimeUtil.format(user.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
                }
                if (user.getCreateTime()!=null){
                    userInfo.put("createTime", DateTimeUtil.format(user.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                }
                jsonObject.put("userInfo", userInfo);
                jsonObject.put("token",tokenInfo.getToken());
                try {
                    LoginLog loginLog = new LoginLog();
                    loginLog.setUserId(user.getId());
                    loginLog.setLoginDatetime(new Date());
                    loginLog.setWhereLogin(0);
                    loginLogService.addLoginLog(loginLog);
                } catch (Exception e) {
                    logger.error("记录登录信息FAIL");
                }
                msgInfo.setData(jsonObject);
                msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
            }else{
                msgInfo.setMsg("用户名或密码错误");
            }
        }
        return msgInfo;
    }


    @ApiOperation(value="token方式登录", notes="code：0-失败，1-成功")
    @PostMapping("/loginByToken")
    @ResponseBody
    public Object loginByToken(HttpServletRequest request) {
        MsgInfo msgInfo = new MsgInfo(0,"");
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        TokenInfo tokenInfo = tokenService.getTokenInfo(authorization);
        if (tokenInfo != null && tokenService.checkToken(tokenInfo)) {
            User user = this.userService.getUserByUserName(tokenInfo.getUserName());
            if (user.getAccountValidity()!=null){
                try {
                    Date now = DateTimeUtil.parse(DateTimeUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
                    Date acc = user.getAccountValidity();
                    long i = DateTimeUtil.getDistDate(now,acc);
                    if (i<0){
                        msgInfo.setMsg("账号已过期");
                        return msgInfo;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            msgInfo.setCode(1);
            JSONObject jsonObject = new JSONObject();
            JSONObject userInfo = ToJSON.objectToJson(user);
            if (user.getUpdateTime()!=null){
                userInfo.put("updateTime", DateTimeUtil.format(user.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (user.getCreateTime()!=null){
                userInfo.put("createTime", DateTimeUtil.format(user.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            }
            jsonObject.put("userInfo", userInfo);
            msgInfo.setData(jsonObject);
        }else {
            msgInfo.setMsg("token校验失败");
        }
        return msgInfo;
    }


    @ApiOperation(value="账户密码登录", notes="code：0-失败，1-成功")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PostMapping("/oaLogin")
    @ResponseBody
    public Object oaLogin(@RequestBody User user, HttpServletResponse response) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setType(0);
        if(null==user||null== user.getUserName()){
            msgInfo.setMsg("请输入用户名或密码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String userName = user.getUserName();
            user = userService.getUserByUserNameAndPassword(user.getUserName(), DigestUtils.md5Hex(user.getPassword()));
            User redis = userService.getUserByUserName(userName);
            if (redis!=null){
                String s = redisService.getData("wjw_user_"+redis.getId()+"_times");
                if (s!=null){
                    Integer times = Integer.valueOf(s);
                    if (times>=5){
                        msgInfo.setMsg("登录限制,20分钟后重试");
                        return msgInfo;
                    }else {
                        if(user==null){
                            redisService.addData("wjw_user_"+redis.getId()+"_times",times+1+"",20, TimeUnit.MINUTES);
                        }
                    }
                }else {
                    if(user==null){
                        redisService.addData("wjw_user_"+redis.getId()+"_times","1",20, TimeUnit.MINUTES);
                    }
                }
            }
            if(user != null){
                if (user.getAccountValidity()!=null){
                    try {
                        Date now = DateTimeUtil.parse(DateTimeUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
                        Date acc = user.getAccountValidity();
                        long i = DateTimeUtil.getDistDate(now,acc);
                        if (i<0){
                            msgInfo.setMsg("账号已过期");
                            return msgInfo;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                User userByUserName = userService.getUserByUserName(user.getUserName());
                //判断是否OA用户
                if(null==user.getInternalFlag()||!user.getInternalFlag()){
                    msgInfo.setMsg("权限不足,不能登录此系统!");
                    return msgInfo;
                }
                try {
                    LoginLog loginLog = new LoginLog();
                    loginLog.setUserId(user.getId());
                    loginLog.setLoginDatetime(new Date());
                    loginLog.setWhereLogin(1);
                    loginLogService.addLoginLog(loginLog);
                } catch (Exception e) {
                    logger.error("记录登录信息FAIL");
                }
                msgInfo.setCode(1);
                tokenInfo.setUserName(user.getUserName());
                tokenInfo = tokenService.createTokenOA(tokenInfo);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token",tokenInfo.getToken());
                msgInfo.setData(jsonObject);
                redisService.deleteData("wjw_user_"+user.getId()+"_times");
                msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
            }else{
                msgInfo.setMsg("用户名或密码错误");
            }
        }
        return msgInfo;
    }


    @ApiOperation(value="token方式登录", notes="code：0-失败，1-成功")
    @PostMapping("/oaLoginByToken")
    @ResponseBody
    public Object oaLoginByToken(HttpServletRequest request) {
        MsgInfo msgInfo = new MsgInfo(0,"");
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        TokenInfo tokenInfo = tokenService.getTokenInfo(authorization);
        if (tokenInfo != null && tokenService.checkTokenOA(tokenInfo)) {
            User user = this.userService.getUserByUserName(tokenInfo.getUserName());
            if (user.getAccountValidity()!=null){
                try {
                    Date now = DateTimeUtil.parse(DateTimeUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
                    Date acc = user.getAccountValidity();
                    long i = DateTimeUtil.getDistDate(now,acc);
                    if (i<0){
                        msgInfo.setMsg("账号已过期");
                        return msgInfo;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            msgInfo.setCode(1);
            JSONObject jsonObject = new JSONObject();
            msgInfo.setData(jsonObject);
        }else {
            msgInfo.setMsg("自动登录身份已失效!");
        }
        return msgInfo;
    }




    @ApiOperation(value = "登出", notes = "code：0-失败，1-成功")
    @PostMapping("/logout")
    @ResponseBody
    public Object logout(HttpServletRequest request) {
        MsgInfo msgInfo = new MsgInfo(CommonEnum.FAIL);
        try{
            String authorization = request.getHeader(Constants.AUTHORIZATION);
            if(null != authorization && authorization.length()>0){
                tokenService.deleteToken(authorization);
            }
            msgInfo.setCodeAndMsg(CommonEnum.SUCCESS);
        }catch (Exception e){
            logger.error("登出失败{}",e);
        }
        return msgInfo;
    }
}
